package com.company.sample.web.register;

import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.PasswordField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.Role;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.entity.UserRole;

import javax.inject.Inject;
import java.util.UUID;

/**
 * "Register" screen.
 */
public class RegisterScreen extends AbstractWindow {

    /**
     * ID of the Group for self-registered users.
     */
    private static final String COMPANY_GROUP_ID = "0fa2b1a5-1d68-4d69-9fbd-dff348347f93";

    /**
     * ID of the Role to be assigned to self-registered users.
     */
    private static final String DEFAULT_ROLE_ID = "3ec31528-dc0e-c341-7727-7b46771ae9ff";

    @Inject
    private TextField loginField;

    @Inject
    private PasswordField passwordField;

    @Inject
    private DataManager dataManager;

    @Inject
    private Metadata metadata;

    @Inject
    private PasswordEncryption passwordEncryption;

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
        // Check if a user with the same login already exists
        long existing = dataManager.getCount(LoadContext.create(User.class).setQuery(
                LoadContext.createQuery("select u from sec$User u where u.loginLowerCase = :login")
                        .setParameter("login", getLogin())));
        if (existing > 0) {
            showNotification("A user with the same login already exists", NotificationType.TRAY);
        } else {
            // Load group and role to be assigned to the new user
            Group group = dataManager.load(LoadContext.create(Group.class).setId(UUID.fromString(COMPANY_GROUP_ID)));
            Role role = dataManager.load(LoadContext.create(Role.class).setId(UUID.fromString(DEFAULT_ROLE_ID)));
            // Create a user instance
            User user = metadata.create(User.class);
            user.setLogin(getLogin());
            user.setPassword(passwordEncryption.getPasswordHash(user.getId(), getPassword()));
            user.setGroup(group);
            // Create a link to the role
            UserRole userRole = metadata.create(UserRole.class);
            userRole.setUser(user);
            userRole.setRole(role);
            // Save new entities
            dataManager.commit(new CommitContext(user, userRole));

            showNotification("Created user " + user.getLogin(), NotificationType.TRAY);
            close(COMMIT_ACTION_ID);
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