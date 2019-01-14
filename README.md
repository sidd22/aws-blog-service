# Blog Service AWS Project 

This is a spring boot application, that needs a live instance of AWS DynamoDB instance. 

STEP 1: Provide the AWS credentials 
   
a. Please access the application.yml under the resources directory and enter following 
values entries per account profile
       
        key: XXXX
        secret-key: XXXXXXXXX
        region: us-west-1
        
   b.Build the maven project   
      mvn clean package

STEP 2 : Using main class BlogServiceSetUpUtil.java to create table in AWS DynamoDB       

STEP 3 : Confirm the table creation by login in AWS 
   
   
Run Spring boot application, the pom.xml has starter class, however if running from eclipse
IDE , since there are 2 class with main method it may prompt , choose BlogServiceApplication
and start Spring boot application.

OR you can take the generated WAR file and deploy in TOMCAT instance.

Following are end points per use cases to implement

Blog Service admin services 

1. A blog author is able to register to the platform
@POST
http://localhost:9001/api/sidOrg/blogService/admin/registration

creates a very basic bare minimum entry for BlogAuthor 
sample json to post for creation

{
"firstName": "John" ,
"lastName" : "Doe",
"blogSpace": "john_blog",
"email": "john@something.com"
}

To verify the above POST went well, verify the at this time, log in to AWS and see 
record and get hash value for the column blogAuthorId from table,for example it was

 below and there is a
admin service end point to view details of BlogAuthor who is been created 
"da697576-3bfd-411d-9235-d213e8981cfe". So for the URL as below appending 
blogAuthorId and lastName to verify the entry 

@GET
http://localhost:9001/api/sidOrg/blogService/admin/getBlogAuthor?blogAuthorId=da697576-3bfd-411d-9235-d213e8981cfe&lastName=Sansawal

{
    "blogAuthorId": "da697576-3bfd-411d-9235-d213e8981cfe",
    "firstName": "Sanjay",
    "lastName": "Sansawal",
    "blogSpace": null,
    "email": null
}


2. A blog author may create a space to publish their blogs

@PUT 
http://localhost:9001/api/sidOrg/blogService/admin/createBlogSpace

 updates an existing blogAuthor created in step #1 

{
    "blogAuthorId": "da697576-3bfd-411d-9235-d213e8981cfe",
    "firstName": "Sanjay",
    "lastName": "Sansawal",
    "blogSpace": {
      "blogSpaceName" : "sanjay's blog"
   },
    "email": "something@something.com"
}

3. A blog author can grant access to other authors to publish to their space

@PUT 
http://localhost:9001/api/sidOrg/blogService/admin/addBlogAuthorToSpace

Similar to above use case this is also an update PUT operation on the same object 
where document blogSpace will get updated with the list of authorizedBloggers

sample json : in below example another blogger named Brad Nick is getting added to the 
blog space of John Doe 

{
    "blogAuthorId": "da697576-3bfd-411d-9235-d213e8981cfe",
    "firstName": "Sanjay",
    "lastName": "Sansawal",
    "blogSpace": {
      "blogSpaceName" : "sanjay's blog",
       "authorizedBloggers": [
            {
                "blogAuthorId": "3f86fb30-71bd-4de1-82d8-b7bc0cf0f66f",
                "firstName": "Brad",
                "lastName": "Nick",
                "blogSpace": null
            }
        ]
   },
    "email": "somethig@some.com"
}


4. A registered author can create a new blog entry in their own, or another author’s space

@PUT 
http://localhost:9001/api/sidOrg/blogService/blogEntry/createBlog

This is update operation, same document will get the, in below example 2 blog entries are getting created 
i.e same record is getting updated 

{
    "blogAuthorId": "da697576-3bfd-411d-9235-d213e8981cfe",
    "firstName": "Sanjay",
    "lastName": "Sansawal",
    "blogSpace": {
      "blogSpaceName" : "sanjay's blog",
        "blogEntries": [ {
        	"title": "2019 Presidential Election",
	         "createdDate":"01-15-2019 19:10:00",
	         "content": "Per my openinon the best democratic candidate with be Joe Biden"
        },
        {
        	"title": "2019 Summer Travel",
	         "createdDate":"01-15-2019 19:10:00",
	         "content": "Looking forward to the trip to British Columbia"
        }
        	],
       "authorizedBloggers": [
            {
                "blogAuthorId": "3f86fb30-71bd-4de1-82d8-b7bc0cf0f66f",
                "firstName": "Brad",
                "lastName": "Nick",
                "blogSpace": null
            }
        ]
   },
    "email": "somethig@some.com"
}


5. A blog author must approve any blog entries published in their space

@GET
http://localhost:9001/api/sidOrg/blogService/admin/viewBlogEntriesToApprove?blogAuthorId=fa4b88e1-7ca9-49b8-9522-113b150e4068&lastName=Abraham

The results from this request can be used to verify the status of approved, once a blog entry is approved
it will disappear from above response

NOTE . Please update records with "Approved" [ case sensitive] 

http://localhost:9001/api/sidOrg/blogService/admin/approveBlogEntry

{
    "blogAuthorId": "da697576-3bfd-411d-9235-d213e8981cfe",
    "firstName": "Sanjay",
    "lastName": "Sansawal",
    "blogSpace": {
        "blogSpaceName": "sanjay's blog",
        "blogEntries": [
            {
                "title": "2019 Presidential Election",
                "createdDate": "01-15-2019 19:10:00",
                "content": "Per my openinon the best democratic candidate with be Joe Biden",
                "blogAuthorName": null,
                "approvalStatus": "Approved"
            },
            {
                "title": "2019 Summer Travel",
                "createdDate": "01-15-2019 19:10:00",
                "content": "Looking forward to the trip to British Columbia",
                "blogAuthorName": null,
                "approvalStatus": "Approved"
            }
        ],
        "authorizedBloggers": [
            {
                "blogAuthorId": "3f86fb30-71bd-4de1-82d8-b7bc0cf0f66f",
                "firstName": "Brad",
                "lastName": "Nick",
                "blogSpace": null,
                "email": null
            }
        ]
    },
    "email": "somethig@some.com"
}

6. A registered author can fetch a list of their blog entries, select one and view.
@GET
http://localhost:9001/api/sidOrg/blogService/admin/viewBlogEntriesToApprove?blogAuthorId=3f86fb30-71bd-4de1-82d8-b7bc0cf0f66f&lastName=Abraham


7. Any user can view and read blog entries in any space.

The following services will return all blog entries, this services does filter 
and presents only blog entries and not dump entire records BlogAuthor record from the dynamoDB

@GET
http://localhost:9001/api/sidOrg/blogService/blogEntry/viewAllBlogs

If you don't see anything its probably because the blog entries are not approved yet


I have a public REST end points running on AWS beanstalk form my own asw account , and following is sample URL 

http://localhost:9001  can be replaced by
http://springboot-blog.us-west-1.elasticbeanstalk.com 

Then all above end points should work on the following EBS instance 

http://springboot-blog.us-west-1.elasticbeanstalk.com/api/sidOrg/blogService/blogEntry/viewAllBlogs

This is one example that can give the data
http://springboot-blog.us-west-1.elasticbeanstalk.com/api/sidOrg/blogService/admin/getBlogAuthor?blogAuthorId=fa4b88e1-7ca9-49b8-9522-113b150e4068&lastName=Abraham
