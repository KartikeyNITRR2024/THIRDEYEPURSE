package com.thirdeye.purse.services;

import java.util.List;

import com.thirdeye.purse.dtos.UserInfoDto;

public interface UserInfoService {
    UserInfoDto createUser(UserInfoDto userInfoDto) throws Exception;
    UserInfoDto updateUser(UserInfoDto userInfoDto) throws Exception;
    UserInfoDto getUser(Long userId) throws Exception;
    void deleteUser(Long userId) throws Exception;

    Boolean isUserExists(String email, String password) throws Exception;
    UserInfoDto getUser(String email, String password) throws Exception;
    
    Boolean isUserExists(Long userId) throws Exception;

    Boolean isUserAdmin(Long userId) throws Exception;
    UserInfoDto makeUserAdmin(Long userId) throws Exception;
    UserInfoDto removeUserAdmin(Long userId) throws Exception;

    Boolean isUserActive(Long userId) throws Exception;
    UserInfoDto makeUserActive(Long userId) throws Exception;
    UserInfoDto removeUserActive(Long userId) throws Exception;
    
	List<UserInfoDto> getAllUsers();
}
