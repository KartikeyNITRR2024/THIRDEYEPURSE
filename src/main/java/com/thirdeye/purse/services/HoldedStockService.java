package com.thirdeye.purse.services;

import java.util.List;

import com.thirdeye.purse.dtos.HoldedStockDto;

public interface HoldedStockService {
	HoldedStockDto buyStock(HoldedStockDto holdedStockDto);
	List<HoldedStockDto> getAllBuyStockData();
	List<HoldedStockDto> getAllBuyStockDataForUserId(Long userId);
	HoldedStockDto sellStock(HoldedStockDto holdedStockDto) throws Exception;
	HoldedStockDto getStockData(Long holdedStockId) throws Exception;
}
