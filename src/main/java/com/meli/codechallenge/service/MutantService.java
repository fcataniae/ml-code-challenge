package com.meli.codechallenge.service;

import com.meli.codechallenge.dto.Message;
import com.meli.codechallenge.dto.RequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Service
public class MutantService {


    private static final int MIN_SEQUENCE_DNA = 4;

    private static final int MIN_MUTANT_SEQUENCE_COUNT = 2;

    private static final String A_SEQUENCE = "AAAA";

    private static final String T_SEQUENCE = "TTTT";

    private static final String C_SEQUENCE = "CCCC";

    private static final String G_SEQUENCE = "GGGG";

    private static final String VALID_SEQUENCE_REGEX = "[ATCG]+";

    private final PublishService publishService;

    @Autowired
    public MutantService(PublishService publishService) {
        this.publishService = publishService;
    }

    public Mono<ServerResponse> applyMutantValidation(RequestData requestData) {

        return Mono.just(isMutant(requestData.getDna()))
                .flatMap(isMutant ->
                        TRUE.equals(isMutant) ?
                        ServerResponse.ok().build() :
                        ServerResponse.status(HttpStatus.FORBIDDEN).build()
                );
    }

    @Cacheable(value = "mutants", key = "T(java.util.Arrays).toString(#dna)")
    public boolean isMutant(String[] dna){

        if(validateDna(dna)) {
            List<String> dnaList = new ArrayList<>();
            Collections.addAll(dnaList, dna);
            dnaList.addAll(getDiagonalsReversed(dna));
            dnaList.addAll(getColumns(dna));
            dnaList.addAll(getDiagonals(dna));
            boolean mutant = dnaList.stream()
                    .filter(this::validateSequence)
                    .count() >= MIN_MUTANT_SEQUENCE_COUNT;
            publishService.publishMessage(new Message(Arrays.toString(dna), mutant));
            return mutant;
        }
        return false;
    }


    private boolean validateSequence(String s) {

        return s.contains(A_SEQUENCE) ||
                s.contains(T_SEQUENCE) ||
                s.contains(C_SEQUENCE) ||
                s.contains(G_SEQUENCE);
    }


    private boolean validateDna(String[] dna) {

        boolean isValid = dna != null && dna.length >= MIN_SEQUENCE_DNA;
        if(isValid){
            int length = dna.length;
            for (String sequence : dna) {
                isValid = isValid && sequence != null
                        && sequence.length() == length
                        && sequence.matches(VALID_SEQUENCE_REGEX);
            }
        }
        return isValid;
    }

    private List<String> getDiagonalsReversed(String[] dna){

        return getDiagonals(dna, true);
    }

    private List<String> getDiagonals(String[] dna){

        return getDiagonals(dna, false);
    }

    private List<String> getDiagonals(String[] dna, boolean reverse) {

        if(reverse){
            dna = reverseArray(dna);
        }
        List<String> diagonals = new ArrayList<>();
        int k, j , i;
        for(i = 0; i < dna.length; i++){
            StringBuilder word = new StringBuilder();
            k = i;
            j = 0;
            while (k >= 0) {
                word.append(dna[k].charAt(j));
                k--;
                j++;
            }
            diagonals.add(word.toString());
        }
        for(i = 1; i < dna.length ; i++){
            StringBuilder word = new StringBuilder();
            k = dna.length - 1;
            j = i;
            while (j < dna.length) {
                word.append(dna[k].charAt(j));
                k--;
                j++;
            }
            diagonals.add(word.toString());
        }
        return diagonals;
    }

    private String[] reverseArray(String[] array) {

        List<String> reversedList = new ArrayList<>();
        Collections.addAll(reversedList, array);
        Collections.reverse(reversedList);
        return reversedList.toArray(new String[0]);
    }

    private List<String> getColumns(String[] dna) {
        List<String> columns = new ArrayList<>();
        for(int i = 0 ; i< dna.length; i++) {
            StringBuilder word = new StringBuilder();
            for (String s : dna) {
                word.append(s.charAt(i));
            }
            columns.add(word.toString());
        }
        return columns;
    }

}
