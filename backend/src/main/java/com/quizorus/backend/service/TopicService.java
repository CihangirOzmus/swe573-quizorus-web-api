package com.quizorus.backend.service;

import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.ContentEntity;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.model.UserEntity;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.CurrentUser;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;

    private UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    //private static final Logger logger = LoggerFactory.getLogger(TopicService.class);

    public ResponseEntity<List<TopicEntity>> getAllTopics(UserPrincipal currentUser){
        List<TopicEntity> topics = topicRepository.findAll();
        return ResponseEntity.ok().body(topics);
    }

    public ResponseEntity<List<TopicEntity>> getTopicsCreatedBy(String username, UserPrincipal currentUser) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        List<TopicEntity> topicList = topicRepository.findByCreatedBy(user.getId());

        return ResponseEntity.ok().body(topicList);
    }

    public ResponseEntity<TopicEntity> getTopicById(Long topicId, UserPrincipal currentUser) {
        TopicEntity topicById = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("TopicEntity", "id", topicId));

        return ResponseEntity.ok().body(topicById);
    }

    public ResponseEntity<TopicEntity> createTopic(TopicEntity topicRequest) {
        TopicEntity topic = new TopicEntity();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());
        topic.setImageUrl(topicRequest.getImageUrl());
        TopicEntity createdTopic = topicRepository.save(topic);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{topicId}")
                .buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(location).body(createdTopic);
    }

    public ResponseEntity<ApiResponse> createContentByTopicId(UserPrincipal currentUser, Long topicId, ContentEntity contentRequest){
        TopicEntity topic = topicRepository.findById(topicId).orElse(null);

        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            contentRequest.setTopic(topic);
            topic.getContentList().add(contentRequest);
            topicRepository.save(topic);

            return ResponseEntity.ok().body(new ApiResponse(true, "Content created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create content"));
    }

    public ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser){
        TopicEntity topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            topicRepository.deleteById(topicId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Topic deleted"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete topic"));
    }

}
