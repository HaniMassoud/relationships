# relationships
Try out one to many and many to many relationships

This application consists of three model classes, User, Tenant and UserTenant.

User and Tenant have multiple relationships defined:

	• ManyToMany  - Many Users can be assigned to many Tenants at a time 
	
	• OneToMany - A User can own many Tenants but a Tenant can belong to only one User at a time 
	
	• OneToMany - A Tenant can be selected by many Users but a User can only select one Tenant at a time 
	

Currently, I can configure these relationships in the model classes and compile cleanly, but I get a problem when I try to create some test data to test the relationships. 

Problem:

When I comment out Line 41 in CreateTestData.scala and run the app, I can edit the Tenant name in the home page (have to log in as user1 first).

However, When Line 41 is not commented out, I get a StackOverflowError when I try to edit the Tenant name in the home page.

Cause / Solution:

Not sure but I suspect it is because the two OneToMany relationships that point to/from both classes. Solution is to avoid such relationships.

