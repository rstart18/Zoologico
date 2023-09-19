package com.nelumbo.zoo.security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
