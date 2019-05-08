package com.quizorus.backend.DTO;

import java.util.List;

public class ContentEntityDTO {

    Long id;
    String title;
    String text;
    TopicEntityDTO topicEntityDTO;
    List<QuestionEntityDTO> questionEntityDTOList;

}
