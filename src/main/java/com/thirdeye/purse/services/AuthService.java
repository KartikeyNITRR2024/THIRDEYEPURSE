package com.thirdeye.purse.services;

import com.thirdeye.purse.dtos.UserInfoDto;
import com.thirdeye.purse.pojos.LoginResponsePayload;

public interface AuthService {

	LoginResponsePayload loginUser(UserInfoDto userInfoDto) throws Exception;

}
