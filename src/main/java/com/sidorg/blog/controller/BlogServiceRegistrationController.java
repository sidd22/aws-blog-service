package com.sidorg.blog.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.sidorg.blog.controller.helper.BlogServiceControllerHelper;
import com.sidorg.blog.model.BlogAuthor;
import com.sidorg.blog.model.BlogEntry;
import com.sidorg.blog.repository.BlogServiceRepository;

@RestController
@RequestMapping("/api/sidOrg/blogService/admin")
public class BlogServiceRegistrationController {

	 private static final Logger logger = LoggerFactory.getLogger(BlogServiceRegistrationController.class);
	
	@Autowired
	private BlogServiceRepository repository;

	@PostMapping
	@RequestMapping("/registration")
	public String registerBlogAuthor(@RequestBody BlogAuthor blogAuthor) {
		repository.insertIntoDynamoDB(blogAuthor);
		return "Blog Author succefully registred , record inserted into DynamoDB table";
	}

	@GetMapping
	@RequestMapping("/getBlogAuthor")
	public ResponseEntity<BlogAuthor> getOneBlogAuthorDetails(@RequestParam String blogAuthorId, @RequestParam String lastName) {
		BlogAuthor blogAuthor = repository.getOneBlogAuthorDetails(blogAuthorId, lastName);
		return new ResponseEntity<BlogAuthor>(blogAuthor, HttpStatus.OK);
	}

	@PutMapping
	@RequestMapping("/createBlogSpace")
	public void updateBlogAuthorDetails(@RequestBody BlogAuthor blogAuthor) {
		repository.updateBlogAuthorDetails(blogAuthor);
	}

	@DeleteMapping
	@RequestMapping("/deleteBlogAuthor")
	public void deleteBlogAuthorDetails(@RequestParam String blogAuthorId, @RequestParam String lastName) {
		BlogAuthor blogAuthor = new BlogAuthor();
		blogAuthor.setBlogAuthorId(blogAuthorId);
		blogAuthor.setLastName(lastName);
		repository.deleteBlogAuthorDetails(blogAuthor);
	}
	
	//grant access to other blogAuthor to add blog entries to blog space
	// The argument should contain json of the BlogAuthor with blogSpace
	@PutMapping
	@RequestMapping("/addBlogAuthorToSpace")
	public void addBlogAuthorToExistingSpace(@RequestBody BlogAuthor blogAuthor) {
		repository.updateBlogAuthorDetails(blogAuthor);
	}
	
	@GetMapping
	@RequestMapping("/viewBlogEntriesToApprove")
	public ResponseEntity<List<BlogEntry>> viewBlogEntriesToApprove(
			@RequestParam String blogAuthorId, String lastName) {
		
		BlogAuthor blogAuthor = repository.getOneBlogAuthorDetails(
				blogAuthorId, lastName);
		if(BlogServiceControllerHelper.getPendingBlogEntries(blogAuthor).isEmpty()) {
			logger.info("There are not pending blog entries for you to approve");
		}
		return new ResponseEntity<List<BlogEntry>>(
				BlogServiceControllerHelper.getPendingBlogEntries(blogAuthor),
				HttpStatus.OK);
	}
	
	@PutMapping
	@RequestMapping("/approveBlogEntry")
	public void approveBlogEntry(@RequestBody BlogAuthor blogAuthor) {
		repository.updateBlogAuthorDetails(blogAuthor);
	}

}