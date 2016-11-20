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

public class RegisterScreen extends AbstractWindow {

    private static final String COMPANY_GROUP_ID = "0fa2b1a5-1d68-4d69-9fbd-dff348347f93";
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

    public void onCancelBtnClick() {
        close(CLOSE_ACTION_ID);
    }

    public void onOkBtnClick() {
        long existing = dataManager.getCount(LoadContext.create(User.class).setQuery(
                LoadContext.createQuery("select u from sec$User u where u.loginLowerCase = :login")
                        .setParameter("login", getLogin())));
        if (existing > 0) {
            showNotification("A user with the same login already exists", NotificationType.HUMANIZED);
        } else {
            Group group = dataManager.load(LoadContext.create(Group.class).setId(UUID.fromString(COMPANY_GROUP_ID)));
            Role role = dataManager.load(LoadContext.create(Role.class).setId(UUID.fromString(DEFAULT_ROLE_ID)));

            User user = metadata.create(User.class);
            user.setLogin(getLogin());
            user.setPassword(passwordEncryption.getPasswordHash(user.getId(), getPassword()));
            user.setGroup(group);

            UserRole userRole = metadata.create(UserRole.class);
            userRole.setUser(user);
            userRole.setRole(role);

            dataManager.commit(new CommitContext(user, userRole));

            close(COMMIT_ACTION_ID);
        }
    }

    public String getLogin() {
        return loginField.getValue();
    }

    public String getPassword() {
        return passwordField.getValue();
    }
}