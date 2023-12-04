package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.AdminRequestDTO;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Entity.ServerEvent;
import com.example.UltiOauth.Service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jwt/admin/")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> setAdminRole(@RequestBody AdminRequestDTO adminRequestDTO) {
        String username = adminRequestDTO.getUsernameOfAdmin();
        log.info("USER WANT TO SET ADMIN ROLE");
        boolean setAdminRoleResult = adminService.setAdminRole(username);
        if(setAdminRoleResult){
            log.info("USER SET ADMIN ROLE SUCCESSFULLY");
            return ResponseEntity.ok(setAdminRoleResult);
        }else{
            log.info("USER SET ADMIN ROLE FAILED");
            return ResponseEntity.badRequest().body(setAdminRoleResult);
        }
    }

}
