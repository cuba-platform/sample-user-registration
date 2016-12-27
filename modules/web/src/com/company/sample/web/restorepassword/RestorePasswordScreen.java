package com.company.sample.web.restorepassword;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.security.app.UserManagementService;
import com.haulmont.cuba.security.entity.User;

import javax.inject.Inject;
import java.util.Collections;

public class RestorePasswordScreen extends AbstractWindow {
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
    private TextField loginField;

    @Inject
    private Label warningLabel;

    /**
     * "Cancel" button click handler.
     */
    public void onCancelBtnClick() {
        close(CLOSE_ACTION_ID);
    }

    /**
     * "Send new password" button click handler.
     */
    public void onSendPasswordBtnClick() {
        // validate required fields first
        if (validateAll()) {
            LoadContext<User> lc = LoadContext.create(User.class);
            lc.setView(View.MINIMAL);
            lc.setQueryString("select u from sec$User u where u.loginLowerCase = :login and (u.active = true or u.active is null)")
                    .setParameter("login", loginField.getValue());

            User targetUser = dataManager.load(lc);
            if (targetUser == null) {
                warningLabel.setVisible(true);

                loginField.requestFocus();
            } else {
                // generate new temporary password and send email
                // user must have specified e-mail in the database
                userManagementService.changePasswordsAtLogonAndSendEmails(Collections.singletonList(targetUser.getId()));

                showNotification("Success", "E-mail with your new password has been sent", NotificationType.TRAY);
                close(COMMIT_ACTION_ID);
            }
        }
    }

    public String getLogin() {
        return loginField.getValue();
    }
}