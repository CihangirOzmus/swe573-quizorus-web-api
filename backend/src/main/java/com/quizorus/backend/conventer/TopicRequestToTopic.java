package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.TopicRequest;
import com.quizorus.backend.model.Topic;
import org.springframework.core.convert.converter.Converter;

public class TopicRequestToTopic implements Converter<TopicRequest, Topic> {

    @Override
    public Topic convert(TopicRequest source) {
        return Topic.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .imageUrl(source.getImageUrl())
                .wikiData(source.getWikiData())
                .build();
    }
}
