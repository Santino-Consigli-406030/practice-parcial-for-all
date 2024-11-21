package com.example.practic_parcia_2_lc.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

public interface HttpClient {
    <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables);
    <T, R> ResponseEntity<R> put(String url, T request, ParameterizedTypeReference<R> responseType, Object... uriVariables);
    <T, R> ResponseEntity<R> post(String url, T request, ParameterizedTypeReference<R> responseType, Object... uriVariables);
}

