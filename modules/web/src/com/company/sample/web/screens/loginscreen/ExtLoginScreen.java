package com.company.sample.web.screens.loginscreen;

import com.company.sample.web.screens.register.RegisterScreen;
import com.company.sample.web.screens.restorepassword.RestorePasswordScreen;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.app.login.LoginScreen;

import javax.inject.Inject;

/**
 * Extended login screen.
 */
@UiController("login")
@UiDescriptor("ext-login-screen.xml")
public class ExtLoginScreen extends LoginScreen {

    /**
     * Injected component from the base screen.
     */
    @Inject
    private Button loginButton;

    /**
     * "Register" link click handler.
     */
    @Subscribe("registerBtn")
    public void onRegisterBtnClick(Button.ClickEvent event) {
        // Create "Register" screen with dialog mode
        RegisterScreen registerScreen = screens.create(RegisterScreen.class, OpenMode.DIALOG);

        // Add a listener to be notified when the "Register" screen is closed with COMMIT_ACTION_ID
        registerScreen.addAfterCloseListener(afterCloseEvent -> {
            CloseAction closeAction = afterCloseEvent.getCloseAction();
            if (closeAction == WINDOW_COMMIT_AND_CLOSE_ACTION) {
                // Set the new registered user credentials to login and password fields
                loginField.setValue(registerScreen.getLogin());
                passwordField.setValue(registerScreen.getPassword());
                // Set focus on "Submit" button
                loginButton.focus();
            }
        });

        // Show "Register" screen
        registerScreen.show();
    }

    /**
     * "Restore password" link click handler.
     */
    @Subscribe("restorePasswordBtn")
    public void onRestorePasswordBtnClick(Button.ClickEvent event) {
        // Create "RestorePassword" screen with dialog mode
        RestorePasswordScreen restorePasswordScreen = screens.create(RestorePasswordScreen.class, OpenMode.DIALOG);

        // Add a listener to be notified when the "Restore password" screen is closed with COMMIT_ACTION_ID
        restorePasswordScreen.addAfterCloseListener(afterCloseEvent -> {
            CloseAction closeAction = afterCloseEvent.getCloseAction();
            if (closeAction == WINDOW_COMMIT_AND_CLOSE_ACTION) {
                loginField.setValue(restorePasswordScreen.getLogin());
                // clear password field
                passwordField.setValue(null);
                // Set focus in login field
                passwordField.focus();
            }
        });

        // Show "RestorePassword" screen
        restorePasswordScreen.show();
    }
}
