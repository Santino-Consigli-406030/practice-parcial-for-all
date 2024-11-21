package com.example.practic_parcia_2_lc.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RtHttpClient implements HttpClient {
    private final RestTemplate restTemplate;


    @Autowired
    public RtHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, HttpMethod.GET, null, responseType, uriVariables);
    }

    @Override
    public <T, R> ResponseEntity<R> put(String url, T request, ParameterizedTypeReference<R> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, HttpMethod.PUT, null, responseType, request);
    }

    @Override
    public <T, R> ResponseEntity<R> post(String url, T request, ParameterizedTypeReference<R> responseType, Object... uriVariables) {
        return restTemplate.exchange(url, HttpMethod.POST, null, responseType, request);
    }
}
