package com.example.spring1.exceptions.helper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Объект для возврата ошибок в json
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {
    private String message;
}
