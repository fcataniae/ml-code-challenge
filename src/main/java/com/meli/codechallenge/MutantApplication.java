package com.meli.codechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class MutantApplication {


    public static void main(String[] args) {
        SpringApplication.run(MutantApplication.class, args);
    }

}
