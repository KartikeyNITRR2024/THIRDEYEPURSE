package com.thirdeye.purse.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirdeye.purse.controllers.MarketThresoldController;
import com.thirdeye.purse.dtos.MarketThresoldDto;
import com.thirdeye.purse.entity.MarketThresold;
import com.thirdeye.purse.externalcontrollers.Thirdeye_Stock_Market_Viewer_Connection;
import com.thirdeye.purse.repositories.MarketThresoldRepo;
import com.thirdeye.purse.services.MarketThresoldService;

@Service
public class MarketThresoldServiceImpl implements MarketThresoldService {

    @Autowired
    private MarketThresoldRepo marketThresoldRepo;
    
    @Autowired
    private Thirdeye_Stock_Market_Viewer_Connection thirdeyeStockMarketViewerConnection;
    
    private static final Logger logger = LoggerFactory.getLogger(MarketThresoldServiceImpl.class);

    @Override
    public MarketThresoldDto addMarketThresold(MarketThresoldDto marketThresoldDto) {
        MarketThresold marketThresold = toEntity(marketThresoldDto);
        MarketThresold savedThresold = marketThresoldRepo.save(marketThresold);
        boolean update = thirdeyeStockMarketViewerConnection.updateStockMarketViewer();
        logger.info("Update Live Stock Market Microservice : {} ", update);
        return toDto(savedThresold);
    }

    @Override
    public List<MarketThresoldDto> getAllMarketThresoldDto() {
        return marketThresoldRepo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarketThresoldDto> getAllMarketThresoldDtoForUserId(Long userId) {
        List<MarketThresold> marketThresolds = marketThresoldRepo.findByUserId(userId);
        return marketThresolds.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public MarketThresoldDto getMarketThresoldDto(Long marketThresoldId) throws Exception {
        Optional<MarketThresold> marketThresold = marketThresoldRepo.findById(marketThresoldId);
        if (marketThresold.isEmpty()) {
            throw new Exception("MarketThresold not found with ID: " + marketThresoldId);
        }
        return toDto(marketThresold.get());
    }

    @Override
    public void deleteMarketThresoldDto(Long marketThresoldId) throws Exception {
        if (!marketThresoldRepo.existsById(marketThresoldId)) {
            throw new Exception("MarketThresold not found with ID: " + marketThresoldId);
        }
        marketThresoldRepo.deleteById(marketThresoldId);
        boolean update = thirdeyeStockMarketViewerConnection.updateStockMarketViewer();
    }

    private MarketThresoldDto toDto(MarketThresold marketThresold) {
        MarketThresoldDto marketThresoldDto = new MarketThresoldDto();
        marketThresoldDto.setId(marketThresold.getId());
        marketThresoldDto.setUserId(marketThresold.getUserId());
        marketThresoldDto.setThresoldType(marketThresold.getThresoldType());
        marketThresoldDto.setThresoldPrice(marketThresold.getThresoldPrice());
        marketThresoldDto.setThresoldTime(marketThresold.getThresoldTime());
        return marketThresoldDto;
    }

    private MarketThresold toEntity(MarketThresoldDto marketThresoldDto) {
        MarketThresold marketThresold = new MarketThresold();
        marketThresold.setUserId(marketThresoldDto.getUserId());
        marketThresold.setThresoldType(marketThresoldDto.getThresoldType());
        marketThresold.setThresoldPrice(marketThresoldDto.getThresoldPrice());
        marketThresold.setThresoldTime(marketThresoldDto.getThresoldTime());
        return marketThresold;
    }
}
