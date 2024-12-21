package com.thirdeye.purse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.thirdeye.purse.entity.HoldedStock;


@Repository
public interface HoldedStockRepo extends JpaRepository<HoldedStock, Long> {
	List<HoldedStock> getAllHoldedStockByUserId(Long userId);
}
