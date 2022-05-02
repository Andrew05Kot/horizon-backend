package com.kot.horizon.api.v1;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

public class ApiBinding {

    private RestTemplate restTemplate;

    public ApiBinding(String accessToken) {
        this.restTemplate = new RestTemplate();
        if(accessToken !=null && !accessToken.isEmpty()) {
            restTemplate.getInterceptors().add(getBearerInterceptor(accessToken));
        } else {
            restTemplate.getInterceptors().add(getNoTokenInterceptor());
        }
    }

    private ClientHttpRequestInterceptor getBearerInterceptor(String accessToken) {
        return (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);
            return execution.execute(request, body);
        };
    }

    private ClientHttpRequestInterceptor getNoTokenInterceptor() {
        return (request, body, execution) -> {
            throw new IllegalStateException("Access token is missing.");
        };
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }
}