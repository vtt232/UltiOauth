package com.example.UltiOauth.Controller;

import com.example.UltiOauth.Annotation.RoutingWith;
import com.example.UltiOauth.DTO.AdminRequestDTO;
import com.example.UltiOauth.DTO.SystemStatisticsDTO;
import com.example.UltiOauth.Entity.ServerEvent;
import com.example.UltiOauth.Service.AdminService;
import com.example.UltiOauth.Service.SystemStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/jwt/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final SystemStatisticsService systemStatisticsService;

    public AdminController(AdminService adminService, SystemStatisticsService systemStatisticsService) {
        this.adminService = adminService;
        this.systemStatisticsService = systemStatisticsService;
    }

    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RoutingWith("${app.masterdb}")
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

    @GetMapping("/system-infor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<SystemStatisticsDTO> getSystemInfor() {

        log.info("ADMIN WANT TO GET SYSTEM INFORMATION");
        SystemStatisticsDTO systemStatisticsDTO = systemStatisticsService.updateAndGet();

        log.info("GET SYSTEM INFORMATION SUCCESSFULLY");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(systemStatisticsDTO);


    }

}
