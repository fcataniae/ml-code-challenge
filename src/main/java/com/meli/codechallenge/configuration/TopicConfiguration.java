package com.meli.codechallenge.configuration;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
@Profile("production")
public class TopicConfiguration {

    @Value("${project.topic.name}")
    private String topicName;

    @Value("${project.id}")
    private String projectId;

    @Bean
    public Publisher publisher() throws IOException {

        TopicName topic = TopicName.of(projectId, topicName);
        return Publisher.newBuilder(topic)
                .build();
    }

}
