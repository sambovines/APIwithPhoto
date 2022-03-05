package com.example.spring1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

public class Test {

    public static class help{
        LocalDate localDate;

        public help() {
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        help h = new help();
        h.localDate = LocalDate.now();

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(h);
        System.out.println();
    }
}
