package com.sidorg.blog.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class BlogEntry implements Serializable{

	private String title;
	private String createdDate;
	private String content;
	private String blogAuthorName;
	private String approvalStatus;
	
	public BlogEntry(){}
	
	@DynamoDBAttribute
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@DynamoDBAttribute
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	@DynamoDBAttribute
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@DynamoDBAttribute
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	@DynamoDBAttribute
	public String getBlogAuthorName() {
		return blogAuthorName;
	}

	public void setBlogAuthorName(String blogAuthorName) {
		this.blogAuthorName = blogAuthorName;
	}

	@Override
	public String toString() {
		return "BlogEntry [title=" + title + ", createdDate=" + createdDate
				+ ", content=" + content + ", blogAuthorName=" + blogAuthorName
				+ ", approvalStatus=" + approvalStatus + "]";
	}
	
	
}
