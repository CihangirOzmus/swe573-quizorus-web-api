package com.quizorus.backend.service;

import com.quizorus.backend.controller.dto.*;
import com.quizorus.backend.exception.ResourceNotFoundException;
import com.quizorus.backend.model.Choice;
import com.quizorus.backend.model.Content;
import com.quizorus.backend.model.LearningStep;
import com.quizorus.backend.model.Question;
import com.quizorus.backend.repository.ContentRepository;
import com.quizorus.backend.repository.LearningStepRepository;
import com.quizorus.backend.repository.QuestionRepository;
import com.quizorus.backend.security.UserPrincipal;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private QuestionRepository questionRepository;
    private ContentRepository contentRepository;
    private ConfigurableConversionService quizorusConversionService;
    private LearningStepRepository learningStepRepository;

    public QuestionService(QuestionRepository questionRepository, ContentRepository contentRepository, ConfigurableConversionService quizorusConversionService, LearningStepRepository learningStepRepository) {
        this.questionRepository = questionRepository;
        this.contentRepository = contentRepository;
        this.quizorusConversionService = quizorusConversionService;
        this.learningStepRepository = learningStepRepository;
    }

    public ResponseEntity<ApiResponse> createQuestionByContentId(UserPrincipal currentUser,
                                                                 QuestionRequest questionRequest) {

        final Content content = contentRepository.findById(questionRequest.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException("content", "id",
                        questionRequest.getContentId().toString()));

        QuizorusUtils.checkCreatedBy("content", currentUser.getId(), content.getCreatedBy());

        final Question question = quizorusConversionService.convert(questionRequest, Question.class);
        question.setContent(content);
        questionRepository.save(question);
        return ResponseEntity.ok().body(new ApiResponse(true, "Question created successfully"));
    }

    public ResponseEntity<ApiResponse> deleteQuestionById(Long questionId, UserPrincipal currentUser) {

        final Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("question", "id", questionId.toString()));

        QuizorusUtils.checkCreatedBy("question", currentUser.getId(), question.getCreatedBy());

        questionRepository.delete(question);
        return ResponseEntity.ok().body(new ApiResponse(true, "Question deleted successfully"));
    }

    public ResponseEntity<LearningStepsResponse> getLearningSteps(UserPrincipal currentUser, Long contentId) {

        final List<QuestionResponse> questionResponseList = new ArrayList<>();
        final AtomicReference<String> contentTitle = new AtomicReference<>();
        final AtomicReference<String> topicTitle = new AtomicReference<>();
        final AtomicReference<Long> topicId = new AtomicReference<>();
        final AtomicReference<Long> nextContentId = new AtomicReference<>();

        contentRepository.findById(contentId)
                .ifPresent(content -> {
                    content.getTopic().getContentList().stream()
                            .map(Content::getId).collect(Collectors.toList()).stream().filter(id -> id > contentId).min(
                            Comparator.comparing(Long::valueOf)).ifPresent(nextContentId::set);
                    final List<LearningStep> learningSteps = learningStepRepository.findByUserIdAndContentId(currentUser.getId(), contentId);
                    final List<Question> questionList = content.getQuestionList();
                    if (questionList != null) {
                        for (Question question : questionList) {
                            final QuestionResponse resp = new QuestionResponse();
                            final List<Choice> choiceList = question.getChoiceList();
                            resp.setChoiceList(choiceList);
                            resp.setId(question.getId());
                            resp.setText(question.getText());
                            learningSteps.stream()
                                    .filter(learningStep -> learningStep.getQuestionId().equals(question.getId()))
                                    .findAny()
                                    .ifPresent(step -> choiceList.stream()
                                            .filter(choice -> choice.getId().equals(step.getAnswerId())).findFirst()
                                            .ifPresent(
                                                    resp::setUserAnswer));
                            questionResponseList.add(resp);
                        }
                    }
                    contentTitle.set(content.getTitle());
                    topicTitle.set(content.getTopic().getTitle());
                    topicId.set(content.getTopic().getId());
                });

        return ResponseEntity.ok()
                .body(LearningStepsResponse.builder().questions(questionResponseList).contentId(contentId)
                        .topicId(topicId.get()).contentTitle(contentTitle.get())
                        .topicTitle(topicTitle.get()).nextContentId(nextContentId.get()).build());
    }

    public ResponseEntity<ApiResponse> giveAnswer(UserPrincipal currentUser, AnswerRequest answerRequest) {

        final Content content = questionRepository.findById(answerRequest.getQuestionId()).map(Question::getContent)
                .get();

        final LearningStep learningStep = learningStepRepository
                .findByUserIdAndContentIdAndQuestionIdAndAnswerId(currentUser.getId(), content.getId(),
                        answerRequest.getQuestionId(),
                        answerRequest.getChoiceId()).orElse(learningStepRepository
                        .save(LearningStep.builder().userId(currentUser.getId())
                                .answerId(answerRequest.getChoiceId())
                                .questionId(answerRequest.getQuestionId()).contentId(content.getId()).build()));

        return ResponseEntity.ok().body(new ApiResponse(true,
                "For User " + learningStep.getUserId() + " LearningStep is created successfully"));
    }
}
