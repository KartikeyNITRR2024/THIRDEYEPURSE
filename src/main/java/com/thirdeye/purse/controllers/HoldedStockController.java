package com.thirdeye.purse.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirdeye.purse.annotation.AdminRequired;
import com.thirdeye.purse.dtos.HoldedStockDto;
import com.thirdeye.purse.services.impl.HoldedStockServiceImpl;
import com.thirdeye.purse.utils.AllMicroservicesData;

@RestController
@RequestMapping("/api/holdedstock")
public class HoldedStockController {

	@Autowired
	AllMicroservicesData allMicroservicesData;
    
    @Autowired
    private HoldedStockServiceImpl holdedStockServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(HoldedStockController.class);

    @PostMapping("/{uniqueId}/buystock")
    public ResponseEntity<HoldedStockDto> buyStock(
            @PathVariable("uniqueId") Integer pathUniqueId,
            @RequestBody HoldedStockDto holdedStockDto) {

        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            HoldedStockDto savedStock = holdedStockServiceImpl.buyStock(holdedStockDto);
            logger.info("Added new HoldedStock with ID: {}", savedStock.getHoldedStockId());
            return ResponseEntity.ok(savedStock);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{uniqueId}/sellstock")
    public ResponseEntity<HoldedStockDto> sellStock(
            @PathVariable("uniqueId") Integer pathUniqueId,
            @RequestBody HoldedStockDto holdedStockDto) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            System.out.println("check "+holdedStockDto);
            HoldedStockDto savedStock;
            try {
                savedStock = holdedStockServiceImpl.sellStock(holdedStockDto);
                logger.info("Added new HoldedStock with ID: {}", savedStock.getHoldedStockId());
                return ResponseEntity.ok(savedStock);
            } catch (Exception e) {
                logger.error("Error occurred while selling stock: {}", e.getMessage());
                return ResponseEntity.badRequest().build();
            }
            
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{uniqueId}/getstock/{holdedStockId}")
    public ResponseEntity<HoldedStockDto> getStock(
            @PathVariable("uniqueId") Integer pathUniqueId,
            @PathVariable("holdedStockId") Long holdedStockId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            
            HoldedStockDto savedStock;
            try {
                savedStock = holdedStockServiceImpl.getStockData(holdedStockId);
                logger.info("Stock data is : {}", savedStock);
                return ResponseEntity.ok(savedStock);
            } catch (Exception e) {
                logger.error("Error occurred while fetching stock: {}", e.getMessage());
                return ResponseEntity.badRequest().build();
            }
            
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }

    
    @GetMapping("/{uniqueId}/getAllBuyStockData")
    @AdminRequired
    public ResponseEntity<List<HoldedStockDto>> getAllBuyStockData(
            @PathVariable("uniqueId") Integer pathUniqueId) {

        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            List<HoldedStockDto> allBuyStockData = holdedStockServiceImpl.getAllBuyStockData();
            logger.info("Added new HoldedStock with ID: {}", allBuyStockData);
            return ResponseEntity.ok(allBuyStockData);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{uniqueId}/getAllBuyStockData/{userId}")
    public ResponseEntity<List<HoldedStockDto>> getAllBuyStockDataForUserId(
            @PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("userId") Long userId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            List<HoldedStockDto> allBuyStockData = holdedStockServiceImpl.getAllBuyStockDataForUserId(userId);
            logger.info("Added new HoldedStock with ID: {}", allBuyStockData);
            return ResponseEntity.ok(allBuyStockData);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
}
