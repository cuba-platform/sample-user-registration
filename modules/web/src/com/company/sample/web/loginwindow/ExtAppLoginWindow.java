package com.company.sample.web.loginwindow;

import com.company.sample.web.register.RegisterScreen;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.web.app.loginwindow.AppLoginWindow;

import javax.inject.Inject;

public class ExtAppLoginWindow extends AppLoginWindow {

    @Inject
    private Button loginButton;

    public void onRegisterBtnClick() {
        RegisterScreen registerScreen = (RegisterScreen) openWindow("register-screen", WindowManager.OpenType.DIALOG);
        registerScreen.addCloseWithCommitListener(() -> {
            loginField.setValue(registerScreen.getLogin());
            passwordField.setValue(registerScreen.getPassword());

            loginButton.requestFocus();
        });
    }
}
