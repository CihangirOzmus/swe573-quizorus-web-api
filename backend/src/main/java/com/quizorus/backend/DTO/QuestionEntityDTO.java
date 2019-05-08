package com.quizorus.backend.DTO;

import java.util.List;

public class QuestionEntityDTO {

    Long id;
    String text;
    ContentEntityDTO contentEntityDTO;
    List<ChoiceEntityDTO> choiceEntityDTOList;
}
