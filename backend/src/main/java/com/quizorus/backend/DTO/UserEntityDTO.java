package com.quizorus.backend.DTO;

import java.util.List;

public class UserEntityDTO {

    Long id;
    String username;
    List<TopicEntityDTO> enrolledTopicDTOList;
    List<TopicEntityDTO> createdTopicDTOList;

}
