package com.company.sample.web.screens.restorepassword;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.app.UserManagementService;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.Collections;

@UiController("sample_RestorePasswordScreen")
@UiDescriptor("restore-password-screen.xml")
public class RestorePasswordScreen extends Screen {

    /**
     * Injected service for user management operations.
     */
    @Inject
    private UserManagementService userManagementService;

    /**
     * Injected DataManager for loading data from middleware
     */
    @Inject
    private DataManager dataManager;

    @Inject
    private Notifications notifications;

    @Inject
    private TextField<String> loginField;

    @Inject
    private Label<String> warningLabel;

    public String getLogin() {
        return loginField.getValue();
    }

    /**
     * "Send new password" button click handler.
     */
    @Subscribe("okBtn")
    public void onOkBtnClick(Button.ClickEvent event) {
        // validate required fields first
        if (validateScreen()) {
            LoadContext<User> lc = LoadContext.create(User.class);
            lc.setView(View.MINIMAL);
            lc.setQueryString("select u from sec$User u where u.loginLowerCase = :login and (u.active = true or u.active is null)")
                    .setParameter("login", loginField.getValue().toLowerCase());

            User targetUser = dataManager.load(lc);
            if (targetUser == null) {
                warningLabel.setVisible(true);

                loginField.focus();
            } else {
                // generate new temporary password and send email
                // user must have specified e-mail in the database
                userManagementService.changePasswordsAtLogonAndSendEmails(Collections.singletonList(targetUser.getId()));

                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption("Success")
                        .withDescription("E-mail with your new password has been sent")
                        .show();

                close(WINDOW_COMMIT_AND_CLOSE_ACTION);
            }
        }
    }

    /**
     * "Cancel" button click handler.
     */
    @Subscribe("cancelBtn")
    public void onCancelBtnClick1(Button.ClickEvent event) {
        close(WINDOW_CLOSE_ACTION);
    }

    private boolean validateScreen() {
        ScreenValidation screenValidation = getBeanLocator().get(ScreenValidation.NAME);
        ValidationErrors errors = screenValidation.validateUiComponents(getWindow());

        if (errors.isEmpty())
            return true;

        screenValidation.showValidationErrors(this, errors);

        return false;
    }
}