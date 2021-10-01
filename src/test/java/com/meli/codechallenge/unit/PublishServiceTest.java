package com.meli.codechallenge.unit;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.meli.codechallenge.dto.Message;
import com.meli.codechallenge.service.PublishService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PublishServiceTest {

    private Publisher publisher = Mockito.mock(Publisher.class);


    private PublishService publishService = new PublishService(publisher);


    @Test
    void test_PublishSucessfullObject(){

        Mockito.when(publisher.publish(Mockito.any()))
                .thenReturn(getSuccessfulPublishFuture());
        Message message = new Message("",true);
        Assertions.assertDoesNotThrow(
                () -> publishService.publishMessage(message)
        );

    }

    @Test
    void test_PublishErrorMessageObject(){

        Mockito.when(publisher.publish(Mockito.any()))
                .thenReturn(getFailedPublishFuture());
        Message message = new Message("",true);
        Assertions.assertDoesNotThrow(
                () -> publishService.publishMessage(message)
        );

    }

    private ApiFuture<String> getSuccessfulPublishFuture() {
        SpyableFuture<String> future = new SpyableFuture("abcd");
        return Mockito.spy(future);
    }

    private ApiFuture<String> getFailedPublishFuture() {
        SpyableFuture<String> future = new SpyableFuture(new Exception());
        return Mockito.spy(future);
    }
}
