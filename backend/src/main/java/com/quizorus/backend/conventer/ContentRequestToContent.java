package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.ContentRequest;
import com.quizorus.backend.model.Content;
import org.springframework.core.convert.converter.Converter;

public class ContentRequestToContent implements Converter<ContentRequest, Content> {

    @Override
    public Content convert(ContentRequest source) {
        final Content content = Content.builder()
                .title(source.getTitle())
                .text(source.getText())
                .build();

        if (source.getId() != 0L) {
            content.setId(source.getId());
        }

        return content;
    }

}
