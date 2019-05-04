package com.quizorus.backend.service;

import com.quizorus.backend.model.ChoiceEntity;
import com.quizorus.backend.payload.ApiResponse;
import com.quizorus.backend.repository.ChoiceRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ChoiceService {

    private ChoiceRepository choiceRepository;

    public ChoiceService(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    //private static final Logger logger = LoggerFactory.getLogger(ChoiceService.class);

    public ResponseEntity<ApiResponse> deleteChoiceById(UserPrincipal currentUser, Long choiceId) {
        ChoiceEntity choice = choiceRepository.findById(choiceId).orElse(null);
        if (choice != null && currentUser.getId().equals(choice.getCreatedBy())){
            choiceRepository.deleteById(choiceId);
            return ResponseEntity.ok().body(new ApiResponse(true, "Choice deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse(false, "Failed to delete choice"));
    }
}
