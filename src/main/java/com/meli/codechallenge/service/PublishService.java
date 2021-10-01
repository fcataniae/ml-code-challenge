package com.meli.codechallenge.service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.rpc.ApiException;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PublishService {


    private final Publisher publisher;
    private final Gson gson = new Gson();

    @Autowired
    public PublishService(Publisher publisher) {
        this.publisher = publisher;
    }

    public <T> void publishMessage(T object){

        publishMessage(gson.toJson(object));
    }

    public void publishMessage(String message){

        ByteString data = ByteString.copyFromUtf8(message);
        PubsubMessage pubsub = PubsubMessage.newBuilder()
                .setData(data)
                .build();
        ApiFuture<String> future = publisher.publish(pubsub);
        ApiFutures.addCallback(
                future,
                new ApiFutureCallback<>() {

                    @Override
                    public void onFailure(Throwable throwable) {
                        if (throwable instanceof ApiException) {
                            ApiException apiException = ((ApiException) throwable);
                            log.info("{}",apiException.getStatusCode().getCode());
                            log.info("{}",apiException.isRetryable());
                        }
                        log.info("Error publishing message : " + message);
                    }

                    @Override
                    public void onSuccess(String messageId) {
                        log.info("Published message ID: {}", messageId);
                    }
                },
                MoreExecutors.directExecutor()
            );
        }


}
