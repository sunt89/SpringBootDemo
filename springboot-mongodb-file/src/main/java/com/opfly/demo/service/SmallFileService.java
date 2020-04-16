package com.opfly.demo.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opfly.demo.pojo.File;
import com.opfly.demo.pojo.SmallFile;
import com.opfly.demo.repository.FileRepository;
import com.opfly.demo.repository.SmallFileRepository;
import com.opfly.demo.utils.MD5Util;

@Service
public class SmallFileService {
	@Autowired
    private SmallFileRepository smallFileRepository;
	@Autowired
	private FileRepository fileRepository;
	
	/**
	 * 添加文件
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
    	
        SmallFile smallFile = new SmallFile();
        smallFile.setFilename(multipartFile.getOriginalFilename());
        smallFile.setContentType(multipartFile.getContentType());
        smallFile.setFile(
          new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()));
        smallFile.setSize(multipartFile.getSize());
        smallFile.setMd5(md5);
        smallFile.setUploadDate(new Date());
        smallFile = smallFileRepository.insert(smallFile);
        
        File file = new File(null,
        		0,
        		smallFile.getFilename(),
        		smallFile.getMd5(),
        		smallFile.getContentType(),
        		smallFile.getSize(),
        		smallFile.getUploadDate(),
        		smallFile.getId());
        
        return fileRepository.insert(file);
    }
 
    /**
     * 获取文件
     * @param id
     * @return
     */
    public SmallFile findById(String id) { 
        return smallFileRepository.findById(id).get(); 
    }
}
