package com.quizorus.backend.service;

import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.ContentEntity;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.model.UserEntity;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<List<TopicEntity>> getTopicsCreatedByUsername(String username, UserPrincipal currentUser) {
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

    public ResponseEntity<List<TopicEntity>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId){
        UserEntity userById = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<TopicEntity> enrolledTopicList = topicRepository.findTopicEntitiesByEnrolledUserListContains(userById);
        return ResponseEntity.ok().body(enrolledTopicList);
    }

    public ResponseEntity<TopicEntity> createTopic(UserPrincipal currentUser, TopicEntity topicRequest) {

        if (topicRequest.getId() != null){
            TopicEntity existingTopic = topicRepository.findById(topicRequest.getId()).orElse(null);

            if (existingTopic != null && currentUser.getId().equals(existingTopic.getCreatedBy())){
                existingTopic.setTitle(topicRequest.getTitle());
                existingTopic.setDescription(topicRequest.getDescription());
                //existingTopic.setWikiData(topicRequest.getWikiData());
                existingTopic.setImageUrl(topicRequest.getImageUrl());
                TopicEntity updatedTopic = topicRepository.save(existingTopic);
                return ResponseEntity.ok().body(updatedTopic);
            }
        }

        TopicEntity topic = new TopicEntity();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());
        topic.setImageUrl(topicRequest.getImageUrl());
        TopicEntity createdTopic = topicRepository.save(topic);

        return ResponseEntity.ok().body(createdTopic);
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

    public ResponseEntity<ApiResponse> enrollToTopicByUsername(UserPrincipal currentUser, Long topicId, String username){
        TopicEntity topicToEnroll = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("TopicEntity", "topicId", topicId));
        UserEntity userToEnroll = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        topicToEnroll.getEnrolledUserList().add(userToEnroll);
        topicRepository.save(topicToEnroll);

        return ResponseEntity.ok().body(new ApiResponse(true, "Enrolled to topic successfully"));
    }

}
