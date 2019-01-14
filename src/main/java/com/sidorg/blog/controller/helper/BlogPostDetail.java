package com.sidorg.blog.controller.helper;

import java.io.Serializable;

import com.sidorg.blog.model.BlogEntry;

public class BlogPostDetail implements Serializable{
	
	private BlogEntry blogEntry;
	private String spaceOwnerEmail;
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}
	public String getSpaceOwnerEmail() {
		return spaceOwnerEmail;
	}
	public void setSpaceOwnerEmail(String spaceOwnerEmail) {
		this.spaceOwnerEmail = spaceOwnerEmail;
	}
	
	
	
	

}
