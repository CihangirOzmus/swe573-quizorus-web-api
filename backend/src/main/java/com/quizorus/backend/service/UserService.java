package com.quizorus.backend.service;

import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.UserEntity;
import com.quizorus.backend.payload.UserIdentityAvailability;
import com.quizorus.backend.payload.UserProfile;
import com.quizorus.backend.payload.UserSummary;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private TopicRepository topicRepository;

    public UserService(UserRepository userRepository, TopicRepository topicRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public UserSummary getCurrentUser(UserPrincipal currentUser){
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
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
