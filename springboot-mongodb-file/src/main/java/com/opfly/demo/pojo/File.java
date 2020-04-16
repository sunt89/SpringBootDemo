package com.opfly.demo.pojo;

import java.util.Date;

public class File {
    private String id;
	private int type;
    private String filename;
    private String fileId;
    private String md5;
    private String contentType;
    private Long size;
    private Date uploadDate;
    
    public File() {
	}
    
    public File(String id, int type, String filename, String md5, 
    		String contentType, Long size, Date uploadDate, String fileId) {
    	this.id = id;
    	this.type = type;
    	this.filename = filename;
    	this.md5 = md5;
    	this.contentType = contentType;
    	this.size = size;
    	this.uploadDate = uploadDate;
    	this.fileId = fileId;
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}
