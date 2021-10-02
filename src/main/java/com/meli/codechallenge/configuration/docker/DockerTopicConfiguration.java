package com.meli.codechallenge.configuration.docker;

import com.google.api.gax.core.NoCredentialsProvider;
import com.google.api.gax.grpc.GrpcTransportChannel;
import com.google.api.gax.rpc.FixedTransportChannelProvider;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.TopicName;
import io.grpc.ManagedChannelBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Getter
@Setter
@Configuration
@Profile("docker")
public class DockerTopicConfiguration {

    @Value("${project.id}")
    private String projectId;

    @Value("${PUBSUB_EMULATOR_HOST}")
    private String pubSubUrl;

    @Value("${project.topic.name}")
    private String topicName;


    @Bean
    public Publisher publisher() throws IOException {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forTarget(pubSubUrl);
        channelBuilder.usePlaintext();
        TopicName topic = TopicName.of(projectId, topicName);
        return Publisher.newBuilder(topic)
                .setCredentialsProvider(NoCredentialsProvider.create())
                .setChannelProvider(FixedTransportChannelProvider.create(GrpcTransportChannel.create(channelBuilder.build())))
                .build();
    }

}
