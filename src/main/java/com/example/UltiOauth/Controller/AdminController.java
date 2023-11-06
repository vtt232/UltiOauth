package com.example.UltiOauth.Controller;

import com.example.UltiOauth.DTO.AdminRequestDTO;
import com.example.UltiOauth.DTO.WebSocketAnnouncementDTO;
import com.example.UltiOauth.Entity.ServerEvent;
import com.example.UltiOauth.Service.AdminService;
import com.example.UltiOauth.Service.WebSocketService;
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
    private final WebSocketService webSocketService;

    public AdminController(AdminService adminService, WebSocketService webSocketService) {
        this.adminService = adminService;
        this.webSocketService = webSocketService;
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

    @MessageMapping("/announce")
    public ResponseEntity<String> announceNewAdmin(@Payload String newAdmin) {
        log.info(newAdmin+"IS SET TO ADMIN");
        WebSocketAnnouncementDTO webSocketAnnouncementDTO = new WebSocketAnnouncementDTO(ServerEvent.EVENT_SET_ADMIN, newAdmin);
        webSocketService.sendMessage(webSocketAnnouncementDTO);
        return ResponseEntity.ok("SEND MESSAGE SUCCESSFULLY");
    }


}
