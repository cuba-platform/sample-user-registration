# User Registration

This is a simple example of using anonymous access to an application, i.e. taking some actions before login. Users can click the *Register* link on the login screen and register in the application with a new login name and password. 

* [ExtAppLoginWindow](/modules/web/src/com/company/sample/web/loginwindow/ExtAppLoginWindow.java) is an extension of the standard login screen. It adds the *Register* link and can receive new user credentials back from the register screen to set them to the fields.

* [RegisterScreen](/modules/web/src/com/company/sample/web/register/RegisterScreen.java) accepts new user credentials and creates a new user in the database with the specified group and role. 

Based on CUBA Platform 6.3.3
