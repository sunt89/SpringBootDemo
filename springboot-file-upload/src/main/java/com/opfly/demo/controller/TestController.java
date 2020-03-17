package com.opfly.demo.controller;

import java.io.File;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/test")
public class TestController {
	@PostMapping("upload")
	public String uploadFile(@RequestPart("file") MultipartFile[] multipartFiles) {
		for (int i = 0; i < multipartFiles.length; i++) {
			try {
				MultipartFile multipartFile = multipartFiles[i];
				File dest = new File(multipartFile.getOriginalFilename());
				multipartFile.transferTo(dest);
			} catch (Exception e) {
				return "failed";
			}
		}
		
		return "succeed";
	} 
}
