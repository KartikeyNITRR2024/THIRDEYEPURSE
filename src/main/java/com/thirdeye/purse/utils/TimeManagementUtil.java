package com.thirdeye.purse.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeManagementUtil {
	
    @Autowired
    PropertyLoader propertyLoader;
	
	public Timestamp getCurrentTime() {
        ZonedDateTime indianTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime localDateTime = indianTime.toLocalDateTime();
        return Timestamp.valueOf(localDateTime);
    }
}
