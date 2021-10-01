package com.meli.codechallenge.unit;


import com.meli.codechallenge.dto.RequestData;
import com.meli.codechallenge.service.MutantService;
import com.meli.codechallenge.service.PublishService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MutantServiceTest {

    @InjectMocks
    private MutantService mutantService;

    @Mock
    private PublishService publishService;

    @BeforeAll
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_isMutantValidationOK(){

        boolean resp = Assertions.assertDoesNotThrow(
                () ->mutantService.isMutant(new String[]{"AAAAT", "AAAAT", "TCAGT", "TCCAT", "TCCAT"})
        );

        Assertions.assertTrue(resp);
    }

    @Test
    void test_isMutantValidationOK_diagonals(){

        boolean resp = Assertions.assertDoesNotThrow(
                () ->mutantService.isMutant(new String[]{"AAATA", "TAGAT", "TCAGT", "TACAT", "TCCAT"})
        );

        Assertions.assertTrue(resp);
    }


    @Test
    void test_isMutantValidationFalse_notMxN(){

        boolean resp = Assertions.assertDoesNotThrow(
                () ->mutantService.isMutant(new String[]{"AAAT", "TAGAT", "TCAGT", "TACAT", "TCCAT"})
        );

        Assertions.assertFalse(resp);
    }

    @Test
    void test_isMutantValidationFalse_NullValue(){

        boolean resp = Assertions.assertDoesNotThrow(
                () ->mutantService.isMutant(null)
        );

        Assertions.assertFalse(resp);
    }

    @Test
    void test_isMutantValidationFalse_NotEnoughLenth(){

        boolean resp = Assertions.assertDoesNotThrow(
                () ->mutantService.isMutant(new String[]{"AAA", "TAG", "TCA"})
        );

        Assertions.assertFalse(resp);
    }

}
