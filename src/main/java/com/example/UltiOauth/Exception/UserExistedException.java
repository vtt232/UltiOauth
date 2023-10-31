package com.example.UltiOauth.Exception;

public class UserExistedException extends RuntimeException{

    public UserExistedException(String username) {
        super("User" + username + " existed already");
    }
}
