package com.opfly.demo.pojo;

public class MessageInfo {
	private String sourceClientId;
	private String targetClientId;
	private String type;
	private String content;
	

	public String getSourceClientId() {
		return sourceClientId;
	}
	public void setSourceClientId(String sourceClientId) {
		this.sourceClientId = sourceClientId;
	}
	public String getTargetClientId() {
		return targetClientId;
	}
	public void setTargetClientId(String targetClientId) {
		this.targetClientId = targetClientId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
