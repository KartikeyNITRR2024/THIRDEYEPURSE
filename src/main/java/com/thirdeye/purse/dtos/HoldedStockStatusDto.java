package com.thirdeye.purse.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HoldedStockStatusDto {
	private Long holdedStockStatusId;
    private Double statusPrice;
    private Long statusId;
	private HoldedStockDto holdedstock;
	private HoldedStockDto currentHoldedStock;
}
