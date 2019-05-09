package com.quizorus.backend.service;

import com.quizorus.backend.dto.UserResponse;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.TopicEntity;
import com.quizorus.backend.model.UserEntity;
import com.quizorus.backend.payload.UserIdentityAvailability;
import com.quizorus.backend.payload.UserProfile;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private TopicRepository topicRepository;
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository, TopicRepository topicRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponse getCurrentUser(UserPrincipal currentUser){
        return modelMapper.map(currentUser, UserResponse.class);
    }

    public UserIdentityAvailability checkUsernameAvailability(String email){
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    public UserProfile getUserProfileByUsername(String username){
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        Long createdTopicCount = topicRepository.countByCreatedBy(user.getId());
        List<TopicEntity> enrolledTopicEntityList = topicRepository.findTopicEntitiesByEnrolledUserListContains(user);
        Long enrolledTopicCount = (long) enrolledTopicEntityList.size();
        return new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), createdTopicCount, enrolledTopicCount);
    }

}
