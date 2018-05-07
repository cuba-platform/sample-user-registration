package com.company.sample.web.register;

import com.company.sample.service.RegistrationService;
import com.haulmont.cuba.core.global.validation.MethodParametersValidationException;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.PasswordField;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;

/**
 * "Register" screen.
 */
public class RegisterScreen extends AbstractWindow {

    @Inject
    private TextField loginField;

    @Inject
    private PasswordField passwordField;

    @Inject
    private RegistrationService registrationService;

    /**
     * "Cancel" button click handler.
     */
    public void onCancelBtnClick() {
        close(CLOSE_ACTION_ID);
    }

    /**
     * "OK" button click handler.
     */
    public void onOkBtnClick() {
        try {
            registrationService.registerUser(getLogin(), getPassword());
            showNotification("Created user " + getLogin(), NotificationType.TRAY);
            close(COMMIT_ACTION_ID);
        } catch (MethodParametersValidationException e) {
            showNotification(messages.getMessage("com.company.sample.validation", "UserExistsValidator.message"), NotificationType.TRAY);
        }
    }

    /**
     * @return entered login
     */
    public String getLogin() {
        return loginField.getValue();
    }

    /**
     * @return entered password
     */
    public String getPassword() {
        return passwordField.getValue();
    }
}