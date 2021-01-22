package com.earth.hellojpa.model;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoSearchResponse {
    private String status;
    private String statusDesc;
    private List<UserInfo> userInfos;
}
