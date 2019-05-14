package com.quizorus.backend.conventer;

import com.quizorus.backend.controller.dto.TopicRequest;
import com.quizorus.backend.model.Topic;
import org.springframework.core.convert.converter.Converter;

public class TopicRequestToTopic implements Converter<TopicRequest, Topic> {

    @Override
    public Topic convert(TopicRequest source) {

        final Topic topic = Topic.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .wikiDataList(source.getWikiDataList())
                .enrolledUserList(source.getEnrolledUserList())
                .imageUrl(source.getImageUrl())
                .build();

        if (source.getId() != 0L) {
            topic.setId(source.getId());
        }

        return topic;
    }
}
