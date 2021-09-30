package com.meli.codechallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {

    private List<String> dna;

    public List<String> getDna() {

        if(dna==null) dna = new ArrayList<>();
        return dna;
    }
}
