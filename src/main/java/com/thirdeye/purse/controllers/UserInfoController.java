package com.thirdeye.purse.controllers;

import com.thirdeye.purse.annotation.AdminRequired;
import com.thirdeye.purse.dtos.UserInfoDto;
import com.thirdeye.purse.services.UserInfoService;
import com.thirdeye.purse.utils.AllMicroservicesData;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    
    @Autowired
	AllMicroservicesData allMicroservicesData;
    
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @PostMapping("/{uniqueId}")
    @AdminRequired
    public ResponseEntity<?> createUser(@PathVariable("uniqueId") Integer pathUniqueId, @RequestBody UserInfoDto userInfoDto) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            UserInfoDto createdUser = userInfoService.createUser(userInfoDto);
	            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{uniqueId}")
    @AdminRequired
    public ResponseEntity<?> getAllUser(@PathVariable("uniqueId") Integer pathUniqueId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	        	List<UserInfoDto> usersDto = userInfoService.getAllUsers();
	            return new ResponseEntity<>(usersDto, HttpStatus.CREATED);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{uniqueId}/{userId}")
    @AdminRequired
    public ResponseEntity<?> updateUser(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId, @RequestBody UserInfoDto userInfoDto) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        userInfoDto.setId(userId);
	        try {
	            UserInfoDto updatedUser = userInfoService.updateUser(userInfoDto);
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uniqueId}/{userId}")
    @AdminRequired
    public ResponseEntity<?> getUser(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            UserInfoDto user = userInfoService.getUser(userId);
	            return new ResponseEntity<>(user, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uniqueId}/{userId}")
    @AdminRequired
    public ResponseEntity<?> deleteUser(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            userInfoService.deleteUser(userId);
	            return new ResponseEntity<>(Boolean.TRUE, HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uniqueId}/{userId}/admin")
    @AdminRequired
    public ResponseEntity<?> isUserAdmin(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        try {
            Boolean isAdmin = userInfoService.isUserAdmin(userId);
            return new ResponseEntity<>(isAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{uniqueId}/{userId}/admin")
    @AdminRequired
    public ResponseEntity<?> makeUserAdmin(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            UserInfoDto updatedUser = userInfoService.makeUserAdmin(userId);
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uniqueId}/{userId}/admin")
    @AdminRequired
    public ResponseEntity<?> removeUserAdmin(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            UserInfoDto updatedUser = userInfoService.removeUserAdmin(userId);
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{uniqueId}/{userId}/active")
    @AdminRequired
    public ResponseEntity<?> isUserActive(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            Boolean isActive = userInfoService.isUserActive(userId);
	            return new ResponseEntity<>(isActive, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{uniqueId}/{userId}/active")
    @AdminRequired
    public ResponseEntity<?> makeUserActive(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            UserInfoDto updatedUser = userInfoService.makeUserActive(userId);
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{uniqueId}/{userId}/active")
    @AdminRequired
    public ResponseEntity<?> removeUserActive(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
	        try {
	            UserInfoDto updatedUser = userInfoService.removeUserActive(userId);
	            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	        }
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
}

