package com.thirdeye.purse.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirdeye.purse.annotation.AdminRequired;
import com.thirdeye.purse.dtos.UserInfoDto;
import com.thirdeye.purse.pojos.LoginResponsePayload;
import com.thirdeye.purse.services.AuthService;
import com.thirdeye.purse.utils.AllMicroservicesData;

@RestController
@RequestMapping("/api/authmicroservices")
public class AuthMicroservicesController {

	@Autowired
	AllMicroservicesData allMicroservicesData;

    private static final Logger logger = LoggerFactory.getLogger(AuthMicroservicesController.class);

    @GetMapping("/{uniqueId}/nonadminuser")
    public ResponseEntity<Boolean> authNonAdminUser(
            @PathVariable("uniqueId") Integer pathUniqueId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
			return ResponseEntity.ok(Boolean.TRUE);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{uniqueId}/adminuser")
    @AdminRequired
    public ResponseEntity<Boolean> authAdminUser(
            @PathVariable("uniqueId") Integer pathUniqueId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
			return ResponseEntity.ok(Boolean.TRUE);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
}



