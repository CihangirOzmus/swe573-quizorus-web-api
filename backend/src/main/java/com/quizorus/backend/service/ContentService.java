package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.ApiResponse;
import com.quizorus.backend.controller.dto.ContentRequest;
import com.quizorus.backend.controller.dto.ContentResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.repository.ContentRepository;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ContentService {

    private ContentRepository contentRepository;

    private TopicRepository topicRepository;

    private ConfigurableConversionService quizorusConversionService;

    public ContentService(ContentRepository contentRepository, TopicRepository topicRepository, ConfigurableConversionService quizorusConversionService) {
        this.contentRepository = contentRepository;
        this.topicRepository = topicRepository;
        this.quizorusConversionService = quizorusConversionService;
    }

    public ResponseEntity<ApiResponse> createContentByTopicId(UserPrincipal currentUser,
                                                              ContentRequest contentRequest) {

        final Topic topic = topicRepository.findById(contentRequest.getTopicId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("topic", "id", contentRequest.getTopicId().toString()));

        QuizorusUtils.checkCreatedBy("topic", currentUser.getId(), topic.getCreatedBy());

        final Content content = quizorusConversionService.convert(contentRequest, Content.class);
        content.setTopic(topic);
        contentRepository.save(content);
        return ResponseEntity.ok().body(new ApiResponse(true, "Content created successfully"));
    }


    public ResponseEntity<ContentResponse> getContentById(UserPrincipal currentUser, Long contentId) {

        final Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("content", "id", contentId.toString()));

        final ContentResponse contentResponse = quizorusConversionService.convert(content, ContentResponse.class);

        final AtomicLong nextContentId = new AtomicLong(0L);

        content.getTopic().getContentList().stream()
                .map(Content::getId).collect(Collectors.toList()).stream().filter(id -> id > contentId).min(
                Comparator.comparing(Long::valueOf)).ifPresent(nextContentId::set);

        Objects.requireNonNull(contentResponse).setNextContentId(nextContentId.get());

        return ResponseEntity.ok().body(contentResponse);
    }


    public ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId) {

        final Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new ResourceNotFoundException("content", "id", contentId.toString()));

        QuizorusUtils.checkCreatedBy("content", currentUser.getId(), content.getCreatedBy());

        contentRepository.delete(content);
        return ResponseEntity.ok().body(new ApiResponse(true, "Content deleted successfully"));
    }

}
