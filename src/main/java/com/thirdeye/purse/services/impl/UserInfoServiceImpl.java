package com.thirdeye.purse.services.impl;

import com.thirdeye.purse.dtos.UserInfoDto;
import com.thirdeye.purse.entity.HoldedStock;
import com.thirdeye.purse.entity.UserInfo;
import com.thirdeye.purse.repositories.UserInfoRepo;
import com.thirdeye.purse.services.UserInfoService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepo userInfoRepo;

    private UserInfoDto mapToDto(UserInfo userInfo) {
        UserInfoDto dto = new UserInfoDto();
        dto.setId(userInfo.getId());
        dto.setName(userInfo.getName());
        dto.setMobileNo(userInfo.getMobileNo());
        dto.setEmail(userInfo.getEmail());
//        dto.setPassword(userInfo.getPassword());
        dto.setTelegramGroupId1(userInfo.getTelegramGroupId1());
        dto.setTelegramGroupId2(userInfo.getTelegramGroupId2());
        dto.setTelegramGroupId3(userInfo.getTelegramGroupId3());
        dto.setIsAdmin(userInfo.getIsAdmin());
        dto.setIsActive(userInfo.getIsActive());
        return dto;
    }

    private UserInfo mapToEntity(UserInfoDto dto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(dto.getId());
        userInfo.setName(dto.getName());
        userInfo.setMobileNo(dto.getMobileNo());
        userInfo.setEmail(dto.getEmail());
        userInfo.setPassword(dto.getPassword());
        userInfo.setTelegramGroupId1(dto.getTelegramGroupId1());
        userInfo.setTelegramGroupId2(dto.getTelegramGroupId2());
        userInfo.setTelegramGroupId3(dto.getTelegramGroupId3());
        userInfo.setIsAdmin(dto.getIsAdmin());
        userInfo.setIsActive(dto.getIsActive());
        return userInfo;
    }

    @Override
    public UserInfoDto createUser(UserInfoDto userInfoDto) throws Exception {
        UserInfo user = mapToEntity(userInfoDto);
        user = userInfoRepo.save(user);
        return mapToDto(user);
    }

    @Override
    public UserInfoDto updateUser(UserInfoDto userInfoDto) throws Exception {
        if (!userInfoRepo.existsById(userInfoDto.getId())) {
            throw new Exception("User not found");
        }
        UserInfo user = mapToEntity(userInfoDto);
        user = userInfoRepo.save(user);
        return mapToDto(user);
    }

    @Override
    public UserInfoDto getUser(Long userId) throws Exception {
        return userInfoRepo.findById(userId)
                .map(this::mapToDto)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public void deleteUser(Long userId) throws Exception {
    	if(userId.equals(1L))
    	{
    		throw new Exception("Cannot Delete User");
    	}
        if (!userInfoRepo.existsById(userId)) {
            throw new Exception("User not found");
        }
        userInfoRepo.deleteById(userId);
    }

    @Override
    public Boolean isUserExists(String email, String password) throws Exception {
        if (!userInfoRepo.existsByEmailAndPassword(email, password)) {
            throw new Exception("User not found");
        }
        return true;
    }

    @Override
    public UserInfoDto getUser(String email, String password) throws Exception {
        return userInfoRepo.findByEmailAndPassword(email, password)
                .map(this::mapToDto)
                .orElseThrow(() -> new Exception("User not found"));
    }

    @Override
    public Boolean isUserExists(Long userId) throws Exception {
        if (!userInfoRepo.existsById(userId)) {
            throw new Exception("User not found");
        }
        return true;
    }

    @Override
    public Boolean isUserAdmin(Long userId) throws Exception {
        UserInfo user = userInfoRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        return user.getIsAdmin() != null && user.getIsAdmin() == 1;
    }

    @Override
    public UserInfoDto makeUserAdmin(Long userId) throws Exception {
        UserInfo user = userInfoRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setIsAdmin(1);
        return mapToDto(userInfoRepo.save(user));
    }

    @Override
    public UserInfoDto removeUserAdmin(Long userId) throws Exception {
    	if(userId.equals(1L))
    	{
    		throw new Exception("Cannot Remove Rights");
    	}
        UserInfo user = userInfoRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setIsAdmin(0);
        return mapToDto(userInfoRepo.save(user));
    }

    @Override
    public Boolean isUserActive(Long userId) throws Exception {
        UserInfo user = userInfoRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        return user.getIsActive() != null && user.getIsActive() == 1;
    }

    @Override
    public UserInfoDto makeUserActive(Long userId) throws Exception {
        UserInfo user = userInfoRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setIsActive(1);
        return mapToDto(userInfoRepo.save(user));
    }

    @Override
    public UserInfoDto removeUserActive(Long userId) throws Exception {
    	if(userId.equals(1L))
    	{
    		throw new Exception("Cannot Make Unactive");
    	}
        UserInfo user = userInfoRepo.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setIsActive(0);
        return mapToDto(userInfoRepo.save(user));
    }
    
    @Override
    public List<UserInfoDto> getAllUsers() {
       List<UserInfo> users = userInfoRepo.findAll();
       List<UserInfoDto> usersDto = new ArrayList<>();
	   for(UserInfo userInfo : users)
	   {
		   usersDto.add(mapToDto(userInfo));
	   }
       return usersDto;
    }
}
