# User Registration

This is a simple example of using anonymous access to an application, i.e. taking some actions before login. Users can click the *Register* link on the login screen and register in the application with a new login name and password. 

* [ExtAppLoginWindow](https://github.com/cuba-platform/sample-user-registration/blob/master/modules/web/src/com/company/sample/web/loginwindow/ExtAppLoginWindow.java) is an extension of the standard login screen. It adds the *Register* link and can receive new user credentials back from the register screen to set them to the fields.

* [RegisterScreen](https://github.com/cuba-platform/sample-user-registration/blob/master/modules/web/src/com/company/sample/web/register/RegisterScreen.java) accepts new user credentials and creates a new user in the database with the specified group and role. 

* [RestorePasswordScreen](https://github.com/cuba-platform/sample-user-registration/blob/master/modules/web/src/com/company/sample/web/restorepassword/RestorePasswordScreen.java) generates a new password for an existing user and sends e-mail message with the generated password.

* [app.properties](https://github.com/cuba-platform/sample-user-registration/blob/master/modules/core/src/com/company/sample/app.properties) sets location of e-mail templates for password reset.

Based on CUBA Platform 6.9.1

## Issues
Please use https://www.cuba-platform.com/discuss for discussion, support, and reporting problems coressponding to this sample.
