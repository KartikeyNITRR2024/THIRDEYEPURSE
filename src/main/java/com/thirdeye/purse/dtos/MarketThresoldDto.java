package com.thirdeye.purse.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MarketThresoldDto {
    private Long id;
	private Long userId;
	private Long thresoldTime;
	private Double thresoldPrice;
	private Integer thresoldType;
}
