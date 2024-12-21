package com.thirdeye.purse.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thirdeye.purse.dtos.UserInfoDto;
import com.thirdeye.purse.pojos.LoginResponsePayload;
import com.thirdeye.purse.services.AuthService;
import com.thirdeye.purse.utils.AllMicroservicesData;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AllMicroservicesData allMicroservicesData;
    
    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/{uniqueId}/login")
    public ResponseEntity<?> login(
            @PathVariable("uniqueId") Integer pathUniqueId,
            @RequestBody UserInfoDto userInfoDto) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
			try {
				LoginResponsePayload loginResponsePayload = authService.loginUser(userInfoDto);
				return ResponseEntity.ok(loginResponsePayload);
			} catch (Exception e) {
				logger.error("Login failed: {}", e.getMessage());
				return ResponseEntity.badRequest().body(e.getMessage());
			}
            
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
}


