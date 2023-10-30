package com.example.UltiOauth.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @GetMapping("/access/redirect")
    @ResponseBody
    public String clientLogins() {
        return "http://localhost:8080/access/login";
    }

    @GetMapping("/access/login")
    public String getLoginPage() {
        return "login";
    }
}