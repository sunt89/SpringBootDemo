package com.opfly.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.opfly.demo.pojo.File;

public interface FileRepository extends MongoRepository<File, String>{
	@Query("{'md5': ?0}}")
	List<File> findFiles(String md5);
	
	default File findOneFile(String md5) {
	    return findFiles(md5).stream().findFirst().orElse(null);
	}
}
