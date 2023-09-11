package com.mahmoud.rest.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahmoud.rest.RestAssuredExecutor;
import com.mahmoud.rest.models.UserPojo;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.List;

public class Users {
    private final static String basePath = "https://gorest.co.in/public/v2/users";
    public static List<UserPojo> getUsersList() {
        RestAssuredExecutor restAssuredExecutor = new RestAssuredExecutor(basePath, Method.GET);
        Response response = restAssuredExecutor.execute();
        if(response.getStatusCode()!=200)
        {
            System.out.println(response.getBody().asString());
            return null;
        }
        String responseString = response.getBody().asString();
        try {
            return new ObjectMapper().readValue(responseString, new TypeReference<List<UserPojo>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addUser(String name, String gender, String email, String status) {
        RestAssuredExecutor restAssuredExecutor = new RestAssuredExecutor(basePath, Method.POST);
        restAssuredExecutor.setBody(String.format(
                "{\"name\":\"%s\", \"gender\":\"%s\", \"email\":\"%s\", \"status\":\"%s\"}",
                name, gender, email, status));
        Response response = restAssuredExecutor.execute();
        return response != null && response.getStatusCode() == 201;
    }

    public static boolean removeUser(int userID) {
        RestAssuredExecutor restAssuredExecutor = new RestAssuredExecutor(
                String.format("%s/%d",basePath,userID), Method.DELETE);
        Response response = restAssuredExecutor.execute();
        return response != null && response.getStatusCode() == 204;
    }

    public static boolean changeUserEmail(UserPojo user, String email) {
        String userJson;
        try {
            user.setEmail(email);
            ObjectMapper mapper = new ObjectMapper();
            userJson = mapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        RestAssuredExecutor restAssuredExecutor = new RestAssuredExecutor(
                String.format("%s/%d",basePath,user.getId()), Method.PUT);
        restAssuredExecutor.setBody(userJson);
        Response response = restAssuredExecutor.execute();
        return response != null && response.getStatusCode() == 200;
    }
}
