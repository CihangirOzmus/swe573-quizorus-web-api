package com.quizorus.backend.config;

import com.quizorus.backend.conventer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.GenericConversionService;

@Configuration
public class ConverterConfig {

    @Bean
    public ConfigurableConversionService quizorusConversionService(){
        TopicRequestToTopic topicRequestToTopic = new TopicRequestToTopic();
        TopicToTopicResponse topicToTopicResponse = new TopicToTopicResponse();
        ContentRequestToContent contentRequestToContent = new ContentRequestToContent();
        ContentToContentResponse contentToContentResponse = new ContentToContentResponse();
        QuestionRequestToQuestion questionRequestToQuestion = new QuestionRequestToQuestion();
        ChoiceRequestToChoice choiceRequestToChoice = new ChoiceRequestToChoice();
        ConfigurableConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(topicRequestToTopic);
        conversionService.addConverter(topicToTopicResponse);
        conversionService.addConverter(contentRequestToContent);
        conversionService.addConverter(contentToContentResponse);
        conversionService.addConverter(questionRequestToQuestion);
        conversionService.addConverter(choiceRequestToChoice);
        return conversionService;
    }

}
