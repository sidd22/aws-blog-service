package com.sidorg.blog.controller;

import java.util.List;

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

import com.sidorg.blog.controller.helper.BlogServiceControllerHelper;
import com.sidorg.blog.model.BlogAuthor;
import com.sidorg.blog.model.BlogEntry;
import com.sidorg.blog.repository.BlogServiceRepository;

@RestController
@RequestMapping("/api/sidOrg/blogService/blogEntry")
public class BlogServiceEntryController {

	@Autowired
	private BlogServiceRepository repository;

	@PostMapping
	@RequestMapping("/createBlog")
	public String createBlog(@RequestBody BlogAuthor blogAuthor) {
		repository.updateBlogAuthorDetails(blogAuthor);
		return "Blog Entry successfully saved in dynamoDB";
	}

	@GetMapping
	@RequestMapping("/viewAllBlogsByAuthor")
	public ResponseEntity<BlogAuthor> viewAllBlogsByAuthor(@RequestParam String blogAuthorId, @RequestParam String lastName) {
		BlogAuthor blogAuthor = repository.getOneBlogAuthorDetails(blogAuthorId, lastName);
		return new ResponseEntity<BlogAuthor>(blogAuthor, HttpStatus.OK);
	}


	@GetMapping
	@RequestMapping("/viewAllBlogs")
	public ResponseEntity<List<BlogEntry>> viewAllBlogs() {
		List<BlogEntry> blogEntries = BlogServiceControllerHelper.getAllApprovedBlogEntries(repository.getAllBlogAuthors());
		
		return new ResponseEntity<List<BlogEntry>>(blogEntries, HttpStatus.OK);
	}

	
	
	@PutMapping
	@RequestMapping("/updateBlogAuthor")
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
	
	//grant access to other blogAuthor
	// The argument should contain json of the BlogAuthor with blogSpace
	@PutMapping
	@RequestMapping("/addBlogAuthorToSpace")
	public void addBlogAuthorToExistingSpace(@RequestBody BlogAuthor blogAuthor) {
		repository.updateBlogAuthorDetails(blogAuthor);
	}

}