package com.example.UltiOauth.Service;

import com.example.UltiOauth.Event.SetAdminEvent;

public interface RestClientService {
    void notifySetAdmin(SetAdminEvent setAdminEvent);
}
