package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.ContentResponse;
import com.quizorus.backend.model.Content;
import org.springframework.core.convert.converter.Converter;

public class ContentToContentResponse implements Converter<Content, ContentResponse> {

    @Override
    public ContentResponse convert(Content source) {
        return ContentResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .text(source.getText())
                .topicId(source.getTopic().getId())
                .topicTitle(source.getTopic().getTitle())
                .questionCount((long) (source.getQuestionList() != null ? source.getQuestionList().size() : 0))
                .build();
    }
}
