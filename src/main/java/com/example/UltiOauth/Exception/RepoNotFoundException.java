package com.example.UltiOauth.Exception;

import java.util.NoSuchElementException;

public class RepoNotFoundException extends NoSuchElementException {

    public RepoNotFoundException(String username, long repoId) {
        super(String.format("Repo %d of User %s not found", repoId, username));
    }

    public RepoNotFoundException(String message) {
        super(message);
    }
}
