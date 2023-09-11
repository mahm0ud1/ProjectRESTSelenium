package com.mahmoud.rest;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class RestAssuredExecutor {
    private final String baseUrl;
    private final Method methodType;
    private String body=null;
    private final static String token = "2583718f9ccdcb4681a46589edb81a3c4a446f904b07f94f0ae968c143f81490";

    public RestAssuredExecutor(String baseUrl, Method methodType) {
        this.baseUrl = baseUrl;
        this.methodType = methodType;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Response execute() {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);

        if(this.body!=null) {
            request.header("Accept", "application/json");
            request.header("Content-Type", "application/json");
            request.body(this.body);
        }

        switch (methodType) {
            case GET:
                return request.get(baseUrl);
            case POST:
                return request.post(baseUrl);
            case PUT:
                return request.put(baseUrl);
            case DELETE:
                return request.delete(baseUrl);
            case PATCH:
                return request.patch(baseUrl);
            default:
                throw new IllegalArgumentException("Unsupported method type: " + methodType);
        }
    }
}
