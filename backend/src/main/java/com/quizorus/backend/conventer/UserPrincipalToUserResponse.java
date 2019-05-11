package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.UserResponse;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.core.convert.converter.Converter;

public class UserPrincipalToUserResponse implements Converter<UserPrincipal, UserResponse> {

    @Override
    public UserResponse convert(UserPrincipal source) {
        return UserResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .username(source.getUsername())
                .build();
    }

}
