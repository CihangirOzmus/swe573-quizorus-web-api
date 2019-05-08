package com.quizorus.backend.DTO;


import java.util.List;

public class TopicEntityDTO {

    Long id;
    String title;
    String description;
    String imageUrl;
    // wikidata
    List<ContentEntityDTO> contentDTOList;
    List<UserEntityDTO> enrolledUserList;

}
