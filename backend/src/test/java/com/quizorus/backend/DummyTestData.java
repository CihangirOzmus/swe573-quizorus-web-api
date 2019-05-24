package com.quizorus.backend;

import com.quizorus.backend.security.UserPrincipal;

public class DummyTestData {

    public static UserPrincipal createDummyUser(){
        UserPrincipal user = new UserPrincipal();
        user.setId(0L);
        user.setName("name");
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        return user;
    }

}
