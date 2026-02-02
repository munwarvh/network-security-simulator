package com.forescout.challenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Map<String, List<String>> people = new HashMap<>();
        people.put("John", Arrays.asList("555-1123", "555-3389"));
        people.put("Mary", Arrays.asList("444-2243", "555-5264"));
        people.put("Steve", Arrays.asList("555-6654", "333-3242"));
//Return name of person whose number starts with 3, in our case Steve
        System.out.println(getPersonNames(people));

    }

    private static List<String> getPersonNames(Map<String, List<String>> people) {
        return people.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(number -> number.startsWith("3")))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
