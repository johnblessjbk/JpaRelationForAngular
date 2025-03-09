package com.jpa.code.dto;
import java.util.Set;

import com.jpa.code.entity.Address;
import com.jpa.code.entity.login;

import java.util.List;
import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private login login;

    private List<Address> address;
    private Set<String> roles;
}
