package com.thirdeye.purse.pojos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginResponsePayload {
	String userName;
	String token;
	Integer isAdmin;
	Long userId;
}
