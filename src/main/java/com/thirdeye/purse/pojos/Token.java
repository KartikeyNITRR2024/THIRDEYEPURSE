package com.thirdeye.purse.pojos;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class Token {
	Long userId;
	String userName;
	Timestamp loginTime;
	Integer hasAdminRights;
	
	@Override
    public String toString() {
        return String.format("TKN|%d|%s|%s|%d|END",
                userId,
                userName,
                loginTime.toString(),
                hasAdminRights);
    }
}
