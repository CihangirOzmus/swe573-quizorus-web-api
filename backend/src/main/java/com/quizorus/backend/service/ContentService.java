package com.quizorus.backend.service;

import com.quizorus.backend.model.ContentEntity;
import com.quizorus.backend.model.QuestionEntity;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.ContentRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    //private static final Logger logger = LoggerFactory.getLogger(ContentService.class);
    // delete after refactor getContent(topicId, contentId)
    public ContentEntity getContentById(Long contentId){
        ContentEntity contentById = contentRepository.findById(contentId).orElse(null);
        return contentById;
    }

    public ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser, Long contentId, QuestionEntity questionRequest){
        ContentEntity contentById = contentRepository.findById(contentId).orElse(null);

        if (contentById != null && currentUser.getId().equals(contentById.getCreatedBy())){
            questionRequest.setContent(contentById);
            contentById.getQuestionList().add(questionRequest);
            contentRepository.save(contentById);

            return ResponseEntity.ok().body(new ApiResponse(true, "Question created successfully"));
        }

        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to create question"));
    }

    public ResponseEntity<ApiResponse> deleteContentById(UserPrincipal currentUser, Long contentId){
        ContentEntity content = contentRepository.findById(contentId).orElse(null);
        if (content != null && currentUser.getId().equals(content.getCreatedBy())){
            contentRepository.deleteContentById(contentId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Content deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Content not found"));
    }

}
