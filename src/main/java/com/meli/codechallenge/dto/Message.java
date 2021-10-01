package com.meli.codechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {

    private String dnaSequence;
    private Boolean mutant;

}
