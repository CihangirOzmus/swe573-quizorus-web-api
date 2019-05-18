package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.*;
import com.quizorus.backend.exception.NotValidTopicException;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.model.User;
import com.quizorus.backend.model.WikiData;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.repository.WikiDataRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private static final String TOPIC = "Topic";

    private TopicRepository topicRepository;

    private UserRepository userRepository;

    private WikiDataRepository wikiDataRepository;

    private ConfigurableConversionService quizorusConversionService;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository, WikiDataRepository wikiDataRepository, ConfigurableConversionService quizorusConversionService) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.wikiDataRepository = wikiDataRepository;
        this.quizorusConversionService = quizorusConversionService;
    }

    public ResponseEntity<List<TopicResponse>> getAllTopics(UserPrincipal currentUser) {
        return ResponseEntity.ok().body(topicRepository.findByPublished(true).stream()
                .map(topic -> quizorusConversionService.convert(topic, TopicResponse.class)).collect(
                        Collectors.toList()));
    }

    public ResponseEntity<List<TopicResponse>> getTopicsCreatedBy(String username, UserPrincipal currentUser) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return ResponseEntity.ok().body(topicRepository.findByCreatedBy(user.getId()).stream()
                .map(topic -> quizorusConversionService.convert(topic, TopicResponse.class)).collect(
                        Collectors.toList()));
    }

    public ResponseEntity<TopicResponse> getTopicById(Long topicId, UserPrincipal currentUser) {
        final Topic topic = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException(TOPIC, "id", topicId.toString()));

        return ResponseEntity.ok().body(quizorusConversionService.convert(topic, TopicResponse.class));
    }

    public ResponseEntity<TopicResponse> createTopic(TopicRequest topicRequest) {

        final List<WikiData> nonExistWikiDataSet =
                topicRequest.getWikiData() != null ? topicRequest.getWikiData().stream()
                        .filter(wikiData -> !wikiDataRepository.existsById(wikiData.getId()))
                        .collect(Collectors.toList()) : null;

        wikiDataRepository.saveAll(nonExistWikiDataSet);

        topicRepository.findById(topicRequest.getId())
                .ifPresent(topic -> topicRequest.setEnrolledUsers(topic.getEnrolledUsers()));

        final Topic topic = topicRepository.save(quizorusConversionService.convert(topicRequest, Topic.class));

        return ResponseEntity.ok().body(quizorusConversionService.convert(topic, TopicResponse.class));
    }

    public ResponseEntity<ApiResponse> publishStatusUpdate(UserPrincipal currentUser, PublishRequest publishRequest) {
        final Topic topic = topicRepository.findById(publishRequest.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "id", publishRequest.getTopicId().toString()));

        QuizorusUtils.checkCreatedBy(TOPIC, currentUser.getId(), topic.getCreatedBy());

        if (publishRequest.isPublish()) {

            if(topic.getContentList() == null || topic.getContentList().isEmpty()){
                throw new NotValidTopicException(topic.getTitle(),
                        "All topics must have at least one content. Please Check Your Topic!");
            }

            topic.getContentList().forEach(content -> {
                if (content.getQuestionList() == null || content.getQuestionList().isEmpty()) {
                    throw new NotValidTopicException(topic.getTitle(),
                            "All contents must have at least one question. Please Check Your Contents!");
                }
            });
        }

        topic.setPublished(publishRequest.isPublish());
        topicRepository.save(topic);
        return ResponseEntity.ok().body(new ApiResponse(true, "Topic published successfully"));
    }

    public ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser) {
        final Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "id", topicId.toString()));

        QuizorusUtils.checkCreatedBy(TOPIC, currentUser.getId(), topic.getCreatedBy());

        topicRepository.delete(topic);
        return ResponseEntity.ok().body(new ApiResponse(true, "Topic deleted"));
    }

    public ResponseEntity<ApiResponse> enrollToTopicByUsername(UserPrincipal currentUser,
                                                               EnrollmentRequest enrollmentRequest) {
        final Topic topic = topicRepository.findById(enrollmentRequest.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException(TOPIC, "topicId",
                        enrollmentRequest.getTopicId().toString()));
        final User user = userRepository.findByUsername(enrollmentRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", enrollmentRequest.getUsername()));
        topic.getEnrolledUsers().add(user);
        topicRepository.save(topic);
        return ResponseEntity.ok().body(new ApiResponse(true, "Enrolled to topic successfully"));

    }

    public ResponseEntity<List<TopicResponse>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));
        final List<Topic> enrolledTopics = topicRepository.findTopicByEnrolledUsersContainsAndPublished(user, true);

        return ResponseEntity.ok()
                .body(enrolledTopics.stream().map(topic -> quizorusConversionService.convert(topic, TopicResponse.class))
                        .collect(Collectors.toList()));
    }
}
