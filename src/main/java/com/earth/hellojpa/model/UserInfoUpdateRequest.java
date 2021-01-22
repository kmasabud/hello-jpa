package com.earth.hellojpa.model;

import lombok.Data;

@Data
public class UserInfoUpdateRequest {
    private int id;
    private UserInfo userInfo;
}
