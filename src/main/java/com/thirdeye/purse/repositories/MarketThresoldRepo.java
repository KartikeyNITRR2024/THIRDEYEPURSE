package com.thirdeye.purse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thirdeye.purse.entity.MarketThresold;

public interface MarketThresoldRepo extends JpaRepository<MarketThresold, Long> {
	List<MarketThresold> findByUserId(Long userId);
}
