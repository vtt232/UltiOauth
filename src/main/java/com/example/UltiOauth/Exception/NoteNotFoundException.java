package com.example.UltiOauth.Exception;

import java.util.NoSuchElementException;

public class NoteNotFoundException extends NoSuchElementException {
    public NoteNotFoundException(String username, long repoId, long noteId) {
        super(String.format("Note %d in repo %d of user %s not found", noteId, repoId, username));
    }

    public NoteNotFoundException(String message) {
        super(message);
    }

}
