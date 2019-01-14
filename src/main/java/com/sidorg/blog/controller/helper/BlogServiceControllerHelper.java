package com.sidorg.blog.controller.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sidorg.blog.model.BlogAuthor;
import com.sidorg.blog.model.BlogEntry;

public class BlogServiceControllerHelper {

	public final static String BLOG_ENTRY_APPROVED_STATUS = "Approved";

	// return list of BlogEntries that are pending for approval
	public static List<BlogEntry> getPendingBlogEntries(BlogAuthor blogAuthor) {

		if (null == blogAuthor.getBlogSpace()
				|| null == blogAuthor.getBlogSpace().getBlogEntries()) {
			return Collections.emptyList();
		}

		return blogAuthor.getBlogSpace().getBlogEntries().stream()
				.filter(blog -> (null == blog.getApprovalStatus()))
				.collect(Collectors.toList());
	}

	public static List<BlogEntry> getAllApprovedBlogEntries(
			BlogAuthor blogAuthor) {

		if (null == blogAuthor.getBlogSpace()
				|| null == blogAuthor.getBlogSpace().getBlogEntries()) {
			return Collections.emptyList();
		}

		return blogAuthor.getBlogSpace().getBlogEntries().stream()
				.filter(blog -> (null == blog.getApprovalStatus()))
				.collect(Collectors.toList());
	}

	public static List<BlogEntry> getAllApprovedBlogEntries(
			List<BlogAuthor> allBlogAuthors) {

		List<BlogEntry> listOfAllBlog = new ArrayList<BlogEntry>();

		for (BlogAuthor blogAuthor : allBlogAuthors) {

			if (null != blogAuthor.getBlogSpace()
					&& null != blogAuthor.getBlogSpace().getBlogEntries()) {

				listOfAllBlog.addAll(blogAuthor
						.getBlogSpace()
						.getBlogEntries()
						.stream()
						.filter(blog -> (BLOG_ENTRY_APPROVED_STATUS.equals(blog
								.getApprovalStatus())))
						.collect(Collectors.toList()));
			}
		}
		
		return listOfAllBlog;

	}

}
