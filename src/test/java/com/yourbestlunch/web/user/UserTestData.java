package com.yourbestlunch.web.user;


import com.yourbestlunch.model.Role;
import com.yourbestlunch.model.User;
import com.yourbestlunch.util.JsonUtil;
import com.yourbestlunch.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = 1;
    public static final int USER_ID_2 = 3;
    public static final int USER_ID_3 = 4;
    public static final int USER_ID_4 = 5;
    public static final int ADMIN_ID = 2;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String USER_MAIL_2 = "user2@yandex.ru";
    public static final String USER_MAIL_3 = "user3@yandex.ru";
    public static final String USER_MAIL_4 = "user4@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User user2 = new User(USER_ID_2, "User2", USER_MAIL_2, "password", Role.USER);
    public static final User user3 = new User(USER_ID_3, "User3", USER_MAIL_3, "password", Role.USER);
    public static final User user4 = new User(USER_ID_4, "User4", USER_MAIL_4, "password", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
