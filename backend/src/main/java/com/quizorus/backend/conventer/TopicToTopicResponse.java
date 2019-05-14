package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.TopicResponse;
import com.quizorus.backend.model.Topic;
import org.springframework.core.convert.converter.Converter;

public class TopicToTopicResponse implements Converter<Topic, TopicResponse> {

    @Override
    public TopicResponse convert(Topic source) {
        return TopicResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .imageUrl(source.getImageUrl())
                .createdBy(source.getCreatedBy())
                .wikiDataList(source.getWikiDataList())
                .contentList(source.getContentList())
                .enrolledUserList(source.getEnrolledUserList())
                .build();
    }
}
