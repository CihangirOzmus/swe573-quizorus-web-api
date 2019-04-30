package com.quizorus.backend.service;

import com.quizorus.backend.model.Content;
import com.quizorus.backend.repository.ContentRepository;
import com.quizorus.backend.repository.TopicRepository;
import com.quizorus.backend.repository.UserRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ContentService.class);

    public Content getContentById(Long contentId){
        Content content = contentRepository.findById(contentId).orElse(null);
        return content;
    }

    public Content createContent(Content contentRequest){
        Content content = new Content();
        content.setTitle(contentRequest.getTitle());
        content.setText(contentRequest.getText());
        return contentRepository.save(content);
    }

    public boolean deleteContentById(Long contentId, UserPrincipal currentUser){
        Content content = contentRepository.findById(contentId).orElse(null);
        if (content != null && currentUser.getId().equals(content.getCreatedBy())){
            contentRepository.deleteContentById(contentId);
            return true;
        }
        return false;
    }

}
