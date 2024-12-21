package com.thirdeye.purse.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserInfoDto {
    private Long id;
    private String Name;
    private String mobileNo;
    private String email;
    private String password;
    private String telegramGroupId1;
    private String telegramGroupId2;
    private String telegramGroupId3;
    private Integer isAdmin;
    private Integer isActive;
}
