package com.thirdeye.purse.dtos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HoldedStockDto {
	private Long holdedStockId;
    private Long stockId;
    private Long userId;
    private Double buyingPriceOfSingleStock;
    private Timestamp buyingTime;
    private Double buyingTotalPrice;
    private Long noOfStock;
    private Integer holding;
    private Double sellingPriceOfSingleStoke;
    private Timestamp sellingTime;
    private Double sellingTotalPrice;
    private Integer type;
    private List<HoldedStockStatusDto> allStatus = new ArrayList<>();
	private HoldedStockStatusDto current;
}

