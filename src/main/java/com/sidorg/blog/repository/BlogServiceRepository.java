package com.sidorg.blog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.sidorg.blog.model.BlogAuthor;

@Repository
public class BlogServiceRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceRepository.class);

	@Autowired
	private DynamoDBMapper mapper;

	public void insertIntoDynamoDB(BlogAuthor blogAuthor) {
		mapper.save(blogAuthor);
	}

	public BlogAuthor getOneBlogAuthorDetails(String blogAuthorId, String lastName) {
		return mapper.load(BlogAuthor.class, blogAuthorId, lastName);
	}
	
	public List<BlogAuthor> getAllBlogAuthors() {
		return mapper.scan(BlogAuthor.class,new DynamoDBScanExpression());
	}

	public void updateBlogAuthorDetails(BlogAuthor blogAuthor) {
		try {
			mapper.save(blogAuthor, buildDynamoDBSaveExpression(blogAuthor));
		} catch (ConditionalCheckFailedException exception) {
			LOGGER.error("invalid data - " + exception.getMessage());
		}
	}

	public void deleteBlogAuthorDetails(BlogAuthor blogAuthor) {
		mapper.delete(blogAuthor);
	}

	public DynamoDBSaveExpression buildDynamoDBSaveExpression(BlogAuthor blogAuthor) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("blogAuthorId", new ExpectedAttributeValue(new AttributeValue(blogAuthor.getBlogAuthorId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}
}