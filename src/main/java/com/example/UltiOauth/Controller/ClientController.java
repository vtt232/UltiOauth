package com.example.UltiOauth.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClientController {

    @GetMapping("oauth/client2")
    @ResponseBody
    public Integer clientLogins2() {
        return 9;
    }

    @GetMapping("jwt/client3")
    @ResponseBody
    public Integer clientLogins3() {
        return 10;
    }

    @GetMapping("/access")
    @ResponseBody
    public String clientLogins() {
        return "http://localhost:8080/oauth/login";
    }


}
