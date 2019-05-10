package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.TopicResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.Topic;
import com.quizorus.backend.model.User;
import com.quizorus.backend.controller.dto.ApiResponse;
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
        List<Topic> topics = topicRepository.findAll();
        List<TopicResponse> topicResponseList = topics.stream()
                .map(topicEntity -> modelMapper.map(topicEntity, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(topicResponseList);
    }

    public ResponseEntity<List<TopicResponse>> getTopicsCreatedByUsername(UserPrincipal currentUser, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        List<Topic> topicList = topicRepository.findByCreatedBy(user.getId());
        List<TopicResponse> topicResponseList = topicList.stream()
                .map(topicEntity -> modelMapper.map(topicEntity, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(topicResponseList);
    }

    public ResponseEntity<TopicResponse> getCreatedTopicById(UserPrincipal currentUser, Long topicId) {
        Topic topicById = topicRepository.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "id", topicId));
        TopicResponse topicResponse = modelMapper.map(topicById, TopicResponse.class);
        return ResponseEntity.ok().body(topicResponse);
    }

    public ResponseEntity<TopicResponse> createTopic(UserPrincipal currentUser, Topic topicRequest) {
        if (topicRequest.getId() != null){
            Topic existingTopic = topicRepository.findById(topicRequest.getId()).orElse(null);
            if (existingTopic != null && currentUser.getId().equals(existingTopic.getCreatedBy())){
                existingTopic.setTitle(topicRequest.getTitle());
                existingTopic.setDescription(topicRequest.getDescription());
                //existingTopic.setWikiData(topicRequest.getWikiData());
                existingTopic.setImageUrl(topicRequest.getImageUrl());
                Topic updatedTopic;
                updatedTopic = topicRepository.save(existingTopic);
                TopicResponse updatedTopicResponse = modelMapper.map(updatedTopic, TopicResponse.class);
                return ResponseEntity.ok().body(updatedTopicResponse);
            }
        }
        Topic topic = new Topic();
        topic.setTitle(topicRequest.getTitle());
        topic.setDescription(topicRequest.getDescription());
        topic.setWikiData(topicRequest.getWikiData());
        topic.setImageUrl(topicRequest.getImageUrl());
        Topic createdTopic = topicRepository.save(topic);
        TopicResponse createdTopicDTO = modelMapper.map(createdTopic, TopicResponse.class);
        return ResponseEntity.ok().body(createdTopicDTO);
    }

    public ResponseEntity<ApiResponse> createContentByTopicId(UserPrincipal currentUser, Long topicId, Content contentRequest){
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            contentRequest.setTopic(topic);
            topic.getContentList().add(contentRequest);
            topicRepository.save(topic);
            return ResponseEntity.ok().body(new ApiResponse(true, "Content created successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create content"));
    }

    public ResponseEntity<ApiResponse> deleteTopicById(Long topicId, UserPrincipal currentUser){
        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic != null && currentUser.getId().equals(topic.getCreatedBy())){
            topicRepository.deleteById(topicId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Topic deleted"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete topic"));
    }

    public ResponseEntity<ApiResponse> enrollToTopicByUsername(UserPrincipal currentUser, Long topicId, String username){
        Topic topicToEnroll = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Topic", "topicId", topicId));
        User userToEnroll = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        topicToEnroll.getEnrolledUserList().add(userToEnroll);
        topicRepository.save(topicToEnroll);
        return ResponseEntity.ok().body(new ApiResponse(true, "Enrolled to topic successfully"));
    }

    public ResponseEntity<List<TopicResponse>> getTopicsByEnrolledUserId(UserPrincipal currentUser, Long userId){
        User userById = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Topic> enrolledTopicList = topicRepository.findTopicEntitiesByEnrolledUserListContains(userById);
        List<TopicResponse> enrolledTopicDTOList = enrolledTopicList.stream()
                .map(topicEntity -> modelMapper.map(topicEntity, TopicResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(enrolledTopicDTOList);
    }
}
