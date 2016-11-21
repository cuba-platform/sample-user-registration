package com.company.sample.web.loginwindow;

import com.company.sample.web.register.RegisterScreen;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.web.app.loginwindow.AppLoginWindow;

import javax.inject.Inject;

/**
 * Extended login screen.
 */
public class ExtAppLoginWindow extends AppLoginWindow {

    /**
     * Injected component from the base screen.
     */
    @Inject
    private Button loginButton;

    /**
     * "Register" link click handler.
     */
    public void onRegisterBtnClick() {
        // Open "Register" screen in dialog mode
        RegisterScreen registerScreen = (RegisterScreen) openWindow("register-screen", WindowManager.OpenType.DIALOG);
        // Add a listener to be notified when the "Register" screen is closed with COMMIT_ACTION_ID
        registerScreen.addCloseWithCommitListener(() -> {
            // Set the new registered user credentials to login and password fields
            loginField.setValue(registerScreen.getLogin());
            passwordField.setValue(registerScreen.getPassword());
            // Set focus on "Submit" button
            loginButton.requestFocus();
        });
    }
}
