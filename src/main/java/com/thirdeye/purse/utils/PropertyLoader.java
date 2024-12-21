package com.thirdeye.purse.utils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thirdeye.purse.repositories.ConfigUsedRepo;
import com.thirdeye.purse.entity.ConfigUsed;
import com.thirdeye.purse.entity.ConfigTable;
import com.thirdeye.purse.repositories.ConfigTableRepo;

@Component 
public class PropertyLoader {
    public long noofuser;
    private Long configId;

    private static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    @Autowired
    private ConfigTableRepo configTableRepo;
    
    @Autowired
    private ConfigUsedRepo configUsedRepo;

    public void updatePropertyLoader() {
        try {
        	logger.info("Fetching currently config used.");
            ConfigUsed configUsed = configUsedRepo.findById(1L).get();
            configId = configUsed.getId();
            logger.debug("Fetching configuration for configId: {}", configId);
            Optional<ConfigTable> configTable = configTableRepo.findById(configId);
            if (configTable.isPresent()) {
            	noofuser = configTable.get().getNoofuser();
                logger.info("Maximum numbers of user loaded: {}", noofuser);
            } else {
                logger.warn("No configuration found for configId: {}", configId);
            }
        } catch (Exception e) {
            logger.error("An error occurred while fetching configuration: {}", e.getMessage(), e);
        }
    }
}
