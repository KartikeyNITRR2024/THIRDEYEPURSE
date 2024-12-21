package com.thirdeye.purse.services;

import java.util.List;

import com.thirdeye.purse.dtos.MarketThresoldDto;

public interface MarketThresoldService {
	MarketThresoldDto addMarketThresold(MarketThresoldDto marketThresoldDto);
	List<MarketThresoldDto> getAllMarketThresoldDto();
	List<MarketThresoldDto> getAllMarketThresoldDtoForUserId(Long userId);
	MarketThresoldDto getMarketThresoldDto(Long marketThresoldId) throws Exception;
	void deleteMarketThresoldDto(Long marketThresoldId) throws Exception;
}
