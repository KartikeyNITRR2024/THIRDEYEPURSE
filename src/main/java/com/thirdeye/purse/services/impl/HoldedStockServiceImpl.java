package com.thirdeye.purse.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirdeye.purse.dtos.HoldedStockDto;
import com.thirdeye.purse.dtos.HoldedStockStatusDto;
import com.thirdeye.purse.entity.HoldedStock;
import com.thirdeye.purse.entity.HoldedStockStatus;
import com.thirdeye.purse.externalcontrollers.Thirdeye_Holded_Stock_Viewer_Connection;
import com.thirdeye.purse.externalcontrollers.Thirdeye_Stock_Market_Viewer_Connection;
import com.thirdeye.purse.repositories.HoldedStockRepo;
import com.thirdeye.purse.services.HoldedStockService;
import com.thirdeye.purse.utils.TimeManagementUtil;

@Service
public class HoldedStockServiceImpl implements HoldedStockService {

    @Autowired
    private HoldedStockRepo holdedStockRepo;

    @Autowired
    private TimeManagementUtil timeManagementUtil;
    
    @Autowired
    private Thirdeye_Holded_Stock_Viewer_Connection thirdeyeHoldedStockViewerConnection;
    
    private static final Logger logger = LoggerFactory.getLogger(HoldedStockServiceImpl.class);

    @Override
    public HoldedStockDto buyStock(HoldedStockDto holdedStockDto) {
       HoldedStock holdedStock = toEntity(holdedStockDto);
       HoldedStockStatus holdedStockStatus = new HoldedStockStatus();
       holdedStockStatus.setStatusId(0L);
       if(holdedStockDto.getType() == 0)
       {
    	   holdedStockStatus.setStatusPrice(0.00);
       }
       else
       {
    	   holdedStockStatus.setStatusPrice(holdedStockDto.getBuyingPriceOfSingleStock());
       }
       holdedStockStatus.setCurrentHoldedStock(holdedStock);
       holdedStockStatus.setHoldedstock(holdedStock);
       holdedStock.setCurrent(holdedStockStatus);
       holdedStock.setHolding(1);
       List<HoldedStockStatus> holdedStockStatusList = holdedStock.getAllStatus();
       for(HoldedStockStatus holdedStockStatus1: holdedStockStatusList)
	   {
    	   holdedStockStatus1.setHoldedstock(holdedStock);
	   }
       holdedStock.setAllStatus(holdedStockStatusList);
       HoldedStock savedHoldedStock = holdedStockRepo.save(holdedStock);
       boolean update = thirdeyeHoldedStockViewerConnection.updateStockMarketViewer();
       logger.info("Update Holded Stock Viewer Microservice : {} ", update);
       return toDto(savedHoldedStock);
    }
    
    @Override
    public HoldedStockDto sellStock(HoldedStockDto holdedStockDto) throws Exception {
        Optional<HoldedStock> isHoldedStock = holdedStockRepo.findById(holdedStockDto.getHoldedStockId());
        if (isHoldedStock.isEmpty()) {
            throw new Exception("HoldedStock not found with ID: " + holdedStockDto.getHoldedStockId());
        }
        HoldedStock holdedStock = isHoldedStock.get();
        if(holdedStock.getUserId() != holdedStockDto.getUserId())
        {
        	throw new Exception("Invalid User");
        }
        System.out.println(holdedStock.getStockId());
        System.out.println(holdedStockDto.getStockId());
        if(!holdedStock.getStockId().equals(holdedStock.getStockId()))
        {
        	System.out.println(holdedStock.getStockId() != holdedStockDto.getStockId());
        	throw new Exception("Invalid Stock");
        }
        holdedStock.setSellingPriceOfSingleStoke(holdedStockDto.getSellingPriceOfSingleStoke());
        holdedStock.setSellingTotalPrice(holdedStockDto.getSellingTotalPrice());
        holdedStock.setSellingTime(timeManagementUtil.getCurrentTime());
        holdedStock.setHolding(0);
        HoldedStock savedHoldedStock = holdedStockRepo.save(holdedStock);
        boolean update = thirdeyeHoldedStockViewerConnection.updateStockMarketViewer();
        logger.info("Update Holded Stock Viewer Microservice : {} ", update);
        return toDto(savedHoldedStock);
    }

    
    @Override
    public List<HoldedStockDto> getAllBuyStockData()
    {
    	List<HoldedStockDto> holdedStockDtoList= new ArrayList<>();
    	List<HoldedStock> holdedStockList = holdedStockRepo.findAll();
    	for(HoldedStock holdedStock : holdedStockList)
    	{
    		holdedStockDtoList.add(toDto(holdedStock));
    	}
    	return holdedStockDtoList;
    }
    
    @Override
    public List<HoldedStockDto> getAllBuyStockDataForUserId(Long userId)
    {
    	List<HoldedStockDto> holdedStockDtoList= new ArrayList<>();
    	List<HoldedStock> holdedStockList = holdedStockRepo.getAllHoldedStockByUserId(userId);
    	for(HoldedStock holdedStock : holdedStockList)
    	{
    		holdedStockDtoList.add(toDto(holdedStock));
    	}
    	return holdedStockDtoList;
    }
    
    @Override
    public HoldedStockDto getStockData(Long holdedStockId) throws Exception
    {
    	Optional<HoldedStock> isHoldedStock = holdedStockRepo.findById(holdedStockId);
    	if(isHoldedStock.isEmpty())
    	{
    		throw new Exception("HoldedStock not found with ID: " + holdedStockId);
    	}
    	HoldedStock holdedStock = isHoldedStock.get();
        return toDto(holdedStock);
    }
    
    
    private HoldedStockStatus toEntity(HoldedStockStatusDto holdedStockStatusDto)
    {
    	HoldedStockStatus holdedStockStatus = new HoldedStockStatus();
    	holdedStockStatus.setStatusId(holdedStockStatusDto.getStatusId());
    	holdedStockStatus.setStatusPrice(holdedStockStatusDto.getStatusPrice());
    	return holdedStockStatus;
    }
    
    private HoldedStock toEntity(HoldedStockDto holdedStockDto)
    {
    	HoldedStock holdedStock = new HoldedStock();
    	holdedStock.setStockId(holdedStockDto.getStockId());
    	holdedStock.setUserId(holdedStockDto.getUserId());
    	holdedStock.setBuyingPriceOfSingleStock(holdedStockDto.getBuyingPriceOfSingleStock());
    	holdedStock.setBuyingTime(timeManagementUtil.getCurrentTime());
    	holdedStock.setBuyingTotalPrice(holdedStockDto.getBuyingTotalPrice());
    	holdedStock.setNoOfStock(holdedStockDto.getNoOfStock());
    	holdedStock.setType(holdedStockDto.getType());
    	for(HoldedStockStatusDto holdedStockStatusDto: holdedStockDto.getAllStatus())
    	{
    		holdedStock.getAllStatus().add(toEntity(holdedStockStatusDto));
    	}
    	return holdedStock;
    }
    
    private HoldedStockStatusDto toDto(HoldedStockStatus holdedStockStatus)
    {
    	HoldedStockStatusDto holdedStockStatusDto = new HoldedStockStatusDto();
    	holdedStockStatusDto.setStatusId(holdedStockStatus.getStatusId());
    	holdedStockStatusDto.setStatusPrice(holdedStockStatus.getStatusPrice());
    	return holdedStockStatusDto;
    }
    
    private HoldedStockDto toDto(HoldedStock holdedStock)
    {
    	HoldedStockDto holdedStockDto = new HoldedStockDto();
    	holdedStockDto.setHoldedStockId(holdedStock.getHoldedStockId());
    	holdedStockDto.setStockId(holdedStock.getStockId());
    	holdedStockDto.setUserId(holdedStock.getUserId());
    	holdedStockDto.setBuyingPriceOfSingleStock(holdedStock.getBuyingPriceOfSingleStock());
    	holdedStockDto.setBuyingTime(holdedStock.getBuyingTime());
    	holdedStockDto.setBuyingTotalPrice(holdedStock.getBuyingTotalPrice());
    	holdedStockDto.setNoOfStock(holdedStock.getNoOfStock());
    	holdedStockDto.setType(holdedStock.getType());
    	holdedStockDto.setCurrent(toDto(holdedStock.getCurrent()));
    	holdedStockDto.setSellingPriceOfSingleStoke(holdedStock.getSellingPriceOfSingleStoke());
    	holdedStockDto.setSellingTime(holdedStock.getSellingTime());
    	holdedStockDto.setSellingTotalPrice(holdedStock.getSellingTotalPrice());
    	holdedStockDto.setHolding(holdedStock.getHolding());
    	for(HoldedStockStatus holdedStockStatus: holdedStock.getAllStatus())
    	{
    		holdedStockDto.getAllStatus().add(toDto(holdedStockStatus));
    	}
    	return holdedStockDto;
    } 

    
}
