package com.opfly.demo.controller;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.opfly.demo.pojo.File;
import com.opfly.demo.pojo.SmallFile;
import com.opfly.demo.repository.FileRepository;
import com.opfly.demo.service.SmallFileService;
import com.opfly.demo.service.LargeFileService;

@RestController
@RequestMapping("api/file")
public class FileController {
	@Autowired
	private SmallFileService smallFileService;
	@Autowired
	private LargeFileService largeFileService;
	@Autowired
	private FileRepository fileRepository;
	
	@Autowired
	private MongoDbFactory mongoDbFactory;
	
	/**
	 * 上传文件
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping("upload")
	public File uploadFile(@RequestPart("file") MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException  {
		//单个的文档的大小不能超过16M，当小于16M时，将文件单独存成一个独立的文档，当文件大于16M时，使用GridFS进行分块存储
		if (multipartFile.getSize() < 16 * 1024 * 1024) {
			return smallFileService.addFile(multipartFile);
		}
		else {
			return largeFileService.addFile(multipartFile);
		}
	}
	
	/**
	 * 获取文件列表
	 * @return
	 */
	@GetMapping("list")
	public List<File> getFileList(){
		return fileRepository.findAll();
	}
	
	/**
	 * 下载文件
	 * @param id
	 * @param response
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@GetMapping("{id}")
	public void downloadFile(@PathVariable("id") String id, HttpServletResponse response) throws IllegalStateException, IOException {
		Optional<File> file = fileRepository.findById(id);
        if(file.isPresent()){
        	String fileId = file.get().getFileId();
        	int type = file.get().getType();
        	//预览
        	//response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + file.get().getFilename() + "\"");
        	//下载
    		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.get().getFilename() + "\"");
        	response.setContentType(file.get().getContentType());
    		response.setHeader(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "");
        	if (type == 0) {
        		SmallFile smallFile = smallFileService.findById(fileId);
        		OutputStream outputStream = response.getOutputStream();
        		outputStream.write(smallFile.getFile().getData());
        	}
        	else {
        		GridFSFile gridFSFile = largeFileService.getFile(fileId);
        		GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDbFactory.getDb());
        		GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        		GridFsResource resource = new GridFsResource(gridFSFile, gridFSDownloadStream);

        		OutputStream outputStream = response.getOutputStream();
                IOUtils.copy(resource.getInputStream(), outputStream);
        	}
        }else {
        	response.setStatus(HttpStatus.NOT_FOUND.value());
        }
	} 
	
	/**
	 * 只删除文件列表中的数据，不实际删除文件
	 * @param id
	 */
	@DeleteMapping("delete/{id}")
	public void deleteFile(@PathVariable String id){
		fileRepository.deleteById(id);
	}
}
