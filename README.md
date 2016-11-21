# User Registration

This is a simple example of using anonymous access to an application, i.e. taking some actions before login. Users can click the *Register* link on the login screen and register in the application with a new login name and password. 

* ExtAppLoginWindow is an extension of the standard login screen. It adds the *Register* link and can receive new user credentials back from the register screen to set them to the fields.

* RegisterScreen accepts new user credentials and creates a new user in the database with the specified group and role. 

Based on CUBA Platform 6.3.3