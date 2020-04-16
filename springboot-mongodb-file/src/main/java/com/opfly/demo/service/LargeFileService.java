package com.opfly.demo.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.opfly.demo.pojo.File;
import com.opfly.demo.repository.FileRepository;
import com.opfly.demo.utils.MD5Util;

@Service
public class LargeFileService {
	@Autowired
    private GridFsTemplate gridFsTemplate;
	
	@Autowired
	private FileRepository fileRepository;
	
	/**
	 * 添加文件，文件超过16M，使用GridFS存储文件
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public File addFile(MultipartFile multipartFile) throws IOException, NoSuchAlgorithmException {
    	String md5 = MD5Util.getMD5(multipartFile.getInputStream());
    	File existFile = fileRepository.findOneFile(md5);
    	if (existFile != null) {
    		return existFile;
    	}
    	
        DBObject metaData = new BasicDBObject(); 
        metaData.put("md5", md5);
        ObjectId id = gridFsTemplate.store(
        		multipartFile.getInputStream(), 
        		multipartFile.getOriginalFilename(),
        		multipartFile.getContentType(),
        		metaData);
        
        GridFSFile gridFSFile = getFile(id.toString());
        File file = new File(null, 1, gridFSFile.getFilename(), 
        		gridFSFile.getMetadata().get("md5").toString(),
        		gridFSFile.getMetadata().get("_contentType").toString(),
        		gridFSFile.getLength(),
        		gridFSFile.getUploadDate(), id.toString());
        
        return fileRepository.insert(file);
    }
    
	/**
	 * 获取文件
	 * @param id
	 * @return
	 */
    public GridFSFile getFile(String id) {
    	GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
    	return file;
    }
    
}
