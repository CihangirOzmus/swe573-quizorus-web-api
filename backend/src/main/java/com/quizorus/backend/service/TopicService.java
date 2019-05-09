package com.quizorus.backend.service;

import com.quizorus.backend.dto.TopicResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.ContentEntity;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.model.UserEntity;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<List<TopicResponse>> getAllTopics(UserPrincipal currentUser){
        List<TopicEntity> topics = topicRepository.findAll();
        List<TopicResponse> topicResponseList = topics.stream()
                .map(topicEntity -> modelMapper.map(topicEntity, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(topicResponseList);
    }

    public ResponseEntity<List<TopicResponse>> getTopicsCreatedByUsername(UserPrincipal currentUser, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));
        List<TopicEntity> topicList = topicRepository.findByCreatedBy(user.getId());
        List<TopicResponse> topicResponseList = topicList.stream()
                .map(topicEntity -> modelMapper.map(topicEntity, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(topicResponseList);
    }

    public ResponseEntity<TopicResponse> getCreatedTopicById(UserPrincipal currentUser, Long topicId) {
        TopicEntity topicById = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("TopicEntity", "id", topicId));
        TopicResponse topicResponse = modelMapper.map(topicById, TopicResponse.class);
        return ResponseEntity.ok().body(topicResponse);
    }

    public ResponseEntity<TopicResponse> createTopic(UserPrincipal currentUser, TopicEntity topicRequest) {
        if (topicRequest.getId() != null){
            TopicEntity existingTopic = topicRepository.findById(topicRequest.getId()).orElse(null);
            if (existingTopic != null && currentUser.getId().equals(existingTopic.getCreatedBy())){
                existingTopic.setTitle(topicRequest.getTitle());
                existingTopic.setDescription(topicRequest.getDescription());
                //existingTopic.setWikiData(topicRequest.getWikiData());
                existingTopic.setImageUrl(topicRequest.getImageUrl());
                TopicEntity updatedTopic;
                updatedTopic = topicRepository.save(existingTopic);
                TopicResponse updatedTopicResponse = modelMapper.map(updatedTopic, TopicResponse.class);
                return ResponseEntity.ok().body(updatedTopicResponse);
            }
        }
        TopicEntity topic = new TopicEntity();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());
        topic.setImageUrl(topicRequest.getImageUrl());
        TopicEntity createdTopic = topicRepository.save(topic);
        TopicResponse createdTopicDTO = modelMapper.map(createdTopic, TopicResponse.class);
        return ResponseEntity.ok().body(createdTopicDTO);
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

    public ResponseEntity<List<TopicResponse>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId){
        UserEntity userById = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<TopicEntity> enrolledTopicList = topicRepository.findTopicEntitiesByEnrolledUserListContains(userById);
        List<TopicResponse> enrolledTopicDTOList = enrolledTopicList.stream()
                .map(topicEntity -> modelMapper.map(topicEntity, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(enrolledTopicDTOList);
    }
}
