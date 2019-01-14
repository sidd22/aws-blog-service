package com.sidorg.blog.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.sidorg.blog.controller.helper.BlogPostDetail;


/**
 * TODO 
 * Future design enhancement or considerations  
 * This class holds the lambda function that can be triggered 
 * when there is any blog posted on a owner's blog to notify him/her of the new post and a pending approval
 * awaiting for his action.
 * 
 * The approval action can also be enabled using a response to that email.
 * 
 * 1. This can be invoked either by API Gateway set the GET Endpoint to invoke this lambda function
 * 
 * 2. The controller instead of invoking the mapper and interacting with DynamoDB could 
 * invoke the landa functions implemented at this tier. 
 * 
 *  It would be design choice given how much lamda function would want to be invoked and leveraged
 *  based on volume and other factors 
 *
 */
public class BlogEntryNotificationHandler implements RequestHandler<BlogPostDetail, String> {

	@Override
	public String handleRequest(BlogPostDetail input, Context context) {
		// call the email service and send the email
		return "A new blog entry has been posted by" + input.getBlogEntry().getBlogAuthorName() 
				+" awaiting your approval";
		
	}
	
}
