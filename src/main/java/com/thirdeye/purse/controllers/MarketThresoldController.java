
package com.thirdeye.purse.controllers;

import java.util.List;

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
import com.thirdeye.purse.dtos.MarketThresoldDto;
import com.thirdeye.purse.services.impl.MarketThresoldServiceImpl;
import com.thirdeye.purse.utils.AllMicroservicesData;

@RestController
@RequestMapping("/api/marketthresold")
public class MarketThresoldController {

	@Autowired
	AllMicroservicesData allMicroservicesData;
    
    @Autowired
    private MarketThresoldServiceImpl marketThresoldServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(MarketThresoldController.class);

    @PostMapping("/{uniqueId}/addmarketthresold")
    public ResponseEntity<MarketThresoldDto> addMarketThresold(
            @PathVariable("uniqueId") Integer pathUniqueId,
            @RequestBody MarketThresoldDto marketThresoldDto) {

        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            MarketThresoldDto newMarketThresoldDto = marketThresoldServiceImpl.addMarketThresold(marketThresoldDto);
            logger.info("Added new MarketThresold with ID: {}", newMarketThresoldDto.getId());
            return ResponseEntity.ok(newMarketThresoldDto);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @PostMapping("/{uniqueId}/deletemarketthresold/{marketThresoldId}")
    public ResponseEntity<Boolean> deleteMarketThresoldById(
            @PathVariable("uniqueId") Integer pathUniqueId,@PathVariable("marketThresoldId") Long marketThresoldId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
			try {
				marketThresoldServiceImpl.deleteMarketThresoldDto(marketThresoldId);
			} catch (Exception e) {
				logger.error("Error occurred while deleteing market thresold: {}", e.getMessage());
				return ResponseEntity.status(404).body(false); 
			}
            return ResponseEntity.ok(true);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/{uniqueId}/getallmarketthresold")
    @AdminRequired
    public ResponseEntity<List<MarketThresoldDto>> getAllMarketThresold(
            @PathVariable("uniqueId") Integer pathUniqueId) {

        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            List<MarketThresoldDto> allMarketThresoldData = marketThresoldServiceImpl.getAllMarketThresoldDto();
            logger.info("All MarketThresold are : {}", allMarketThresoldData);
            return ResponseEntity.ok(allMarketThresoldData);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{uniqueId}/getallmarketthresold/{userid}")
    public ResponseEntity<List<MarketThresoldDto>> getAllMarketThresoldByUserid(
            @PathVariable("uniqueId") Integer pathUniqueId,@PathVariable("userid") Long userid) {

        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            List<MarketThresoldDto> allMarketThresoldData = marketThresoldServiceImpl.getAllMarketThresoldDtoForUserId(userid);
            logger.info("All MarketThresold are : {}", allMarketThresoldData);
            return ResponseEntity.ok(allMarketThresoldData);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{uniqueId}/getmarketthresold/{marketThresoldId}")
    public ResponseEntity<MarketThresoldDto> getMarketThresoldById(
            @PathVariable("uniqueId") Integer pathUniqueId,@PathVariable("marketThresoldId") Long marketThresoldId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            MarketThresoldDto marketThresoldDto = null;
			try {
				marketThresoldDto = marketThresoldServiceImpl.getMarketThresoldDto(marketThresoldId);
			} catch (Exception e) {
				logger.error("Error occurred while fetching market thresold: {}", e.getMessage());
                return ResponseEntity.badRequest().build();
			}
            logger.info("MarketThresold is : {}", marketThresoldDto);
            return ResponseEntity.ok(marketThresoldDto);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
}

