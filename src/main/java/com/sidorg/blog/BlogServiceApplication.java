package com.sidorg.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class BlogServiceApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BlogServiceApplication.class, args);
		System.out.println("Blog Service App is running............");
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogServiceApplication.class);
    }
	
}