package com.example.UltiOauth.Exception;

public class UserNotAuthenticated extends RuntimeException{
    public UserNotAuthenticated() {
        super("User is not authenticated");
    }
}
