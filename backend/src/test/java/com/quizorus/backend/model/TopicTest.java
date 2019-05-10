package com.quizorus.backend.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TopicTest {

    Topic topic;

    @Before
    public void setUp(){
        topic = new Topic();
    }

    @Test
    public void getId() {
        Long idValue = 4L;
        topic.setId(idValue);
        assertEquals(idValue, topic.getId());
    }

    @Test
    public void getTitle() {
        topic.setTitle("Mustang");
        assertEquals("Mustang", topic.getTitle());
    }

    @Test
    public void getDescription() {
        topic.setDescription("American muscle car");
        assertEquals("American muscle car", topic.getDescription());
    }


    @Test
    public void getImageUrl() {
        topic.setImageUrl("https://images.pexels.com/photos/57409/ford-mustang-stallion-red-57409.jpeg");
        assertEquals("https://images.pexels.com/photos/57409/ford-mustang-stallion-red-57409.jpeg", topic.getImageUrl());
    }

}