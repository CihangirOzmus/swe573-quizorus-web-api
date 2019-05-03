package com.quizorus.backend.service;

import com.quizorus.backend.model.ContentEntity;
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

    public ContentEntity getContentById(Long contentId){
        ContentEntity content = contentRepository.findById(contentId).orElse(null);
        return content;
    }

    public ContentEntity createContent(ContentEntity contentRequest){
        ContentEntity content = new ContentEntity();
        content.setTitle(contentRequest.getTitle());
        content.setText(contentRequest.getText());
        return contentRepository.save(content);
    }

    public boolean deleteContentById(Long contentId, UserPrincipal currentUser){
        ContentEntity content = contentRepository.findById(contentId).orElse(null);
        if (content != null && currentUser.getId().equals(content.getCreatedBy())){
            contentRepository.deleteContentById(contentId);
            return true;
        }
        return false;
    }

}
