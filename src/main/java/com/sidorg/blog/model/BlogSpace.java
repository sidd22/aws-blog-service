package com.sidorg.blog.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class BlogSpace implements Serializable {

	private String blogSpaceName;
	private List<BlogEntry> blogEntries;
	private List<BlogAuthor> authorizedBloggers;
	
	public BlogSpace() {}
	
	@DynamoDBAttribute
	public List<BlogAuthor> getAuthorizedBloggers() {
		return authorizedBloggers;
	}
	public void setAuthorizedBloggers(List<BlogAuthor> authorizedBloggers) {
		this.authorizedBloggers = authorizedBloggers;
	}
	@DynamoDBAttribute
	public String getBlogSpaceName() {
		return blogSpaceName;
	}
	public void setBlogSpaceName(String blogSpaceName) {
		this.blogSpaceName = blogSpaceName;
	}
	
	@DynamoDBAttribute
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}
	@Override
	public String toString() {
		return "BlogSpace [blogSpaceName=" + blogSpaceName + ", blogEntries="
				+ blogEntries + ", authorizedBloggers=" + authorizedBloggers
				+ "]";
	}
	
}
