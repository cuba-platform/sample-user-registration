package com.company.sample.web.screens.register;

import com.company.sample.service.RegistrationService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.validation.MethodParametersValidationException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.PasswordField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;

/**
 * "Register" screen.
 */
@UiController("sample_RegisterScreen")
@UiDescriptor("register-screen.xml")
public class RegisterScreen extends Screen {

    @Inject
    private TextField<String> loginField;
    @Inject
    private PasswordField passwordField;

    @Inject
    private RegistrationService registrationService;
    @Inject
    private Notifications notifications;
    @Inject
    private Messages messages;

    /**
     * "OK" button click handler.
     */
    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        try {
            registrationService.registerUser(getLogin(), getPassword());

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption("Created user " + getLogin())
                    .show();

            close(WINDOW_COMMIT_AND_CLOSE_ACTION);
        } catch (MethodParametersValidationException e) {
            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(
                            messages.getMessage(
                                    "com.company.sample.validation",
                                    "UserExistsValidator.message"))
                    .show();
        }
    }

    /**
     * "Cancel" button click handler.
     */
    @Subscribe("cancelBtn")
    public void onCancelBtnClick(Button.ClickEvent event) {
        close(WINDOW_CLOSE_ACTION);
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