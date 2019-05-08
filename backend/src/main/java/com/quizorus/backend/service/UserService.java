package com.quizorus.backend.service;

import com.quizorus.backend.DTO.UserEntityDTO;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.UserEntity;
import com.quizorus.backend.payload.UserIdentityAvailability;
import com.quizorus.backend.payload.UserProfile;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

    public UserEntityDTO getCurrentUser(UserPrincipal currentUser){
        return modelMapper.map(currentUser, UserEntityDTO.class);
    }

    public UserIdentityAvailability checkUsernameAvailability(String email){
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    public UserProfile getUserProfileByUsername(String username){
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("UserEntity", "username", username));

        long topicCount = topicRepository.countByCreatedBy(user.getId());
        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), topicCount);
        return userProfile;
    }

}
