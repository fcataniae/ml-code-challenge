package com.meli.codechallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

@SpringBootApplication
public class MutantApplication {

    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;

    public static void main(String[] args) {
        SpringApplication.run(MutantApplication.class, args);
    }

    @Bean
    public JedisPool jedisPool() {


        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        return new JedisPool(poolConfig, host, port);
    }
}
