package com.mahmoud.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserPojo {
    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;
}
