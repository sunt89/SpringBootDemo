package com.opfly.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.opfly.demo.pojo.SmallFile;

public interface SmallFileRepository extends MongoRepository<SmallFile, String> {

}
