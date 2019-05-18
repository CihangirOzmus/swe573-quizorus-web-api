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
                .imageUrl(source.getImageUrl())
                .wikiDataList(source.getWikiData())
                .enrolledUsers(source.getEnrolledUsers())
                .build();

        if (source.getId() != 0L) {
            topic.setId(source.getId());
        }

        return topic;
    }
}
