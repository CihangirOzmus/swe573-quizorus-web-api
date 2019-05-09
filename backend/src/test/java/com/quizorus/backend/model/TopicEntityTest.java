package com.quizorus.backend.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TopicEntityTest {

    TopicEntity topicEntity;

    @Before
    public void setUp(){
        topicEntity = new TopicEntity();
    }

    @Test
    public void getId() {
        Long idValue = 4L;
        topicEntity.setId(idValue);
        assertEquals(idValue, topicEntity.getId());
    }

    @Test
    public void getTitle() {
        topicEntity.setTitle("Mustang");
        assertEquals("Mustang", topicEntity.getTitle());
    }

    @Test
    public void getDescription() {
        topicEntity.setDescription("American muscle car");
        assertEquals("American muscle car", topicEntity.getDescription());
    }


    @Test
    public void getImageUrl() {
        topicEntity.setImageUrl("https://images.pexels.com/photos/57409/ford-mustang-stallion-red-57409.jpeg");
        assertEquals("https://images.pexels.com/photos/57409/ford-mustang-stallion-red-57409.jpeg", topicEntity.getImageUrl());
    }

}