package com.earth.hellojpa.controller;

import com.earth.hellojpa.model.*;
import com.earth.hellojpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> register(@RequestBody UserInfoRequest request) {
        return userInfoService.register(request);
    }

    @PostMapping("/search")
    public ResponseEntity<UserInfoSearchResponse> search(@RequestBody UserInfoSearchRequest request) {
        return userInfoService.search(request);
    }

    @PatchMapping
    public ResponseEntity<UserInfoUpdateResponse> update(@RequestBody UserInfoUpdateRequest request) {
        return userInfoService.update(request);
    }

    @DeleteMapping
    public ResponseEntity<UserInfoDeleteResponse> delete(@RequestBody UserInfoDeleteRequest request) {
        return userInfoService.delete(request);
    }
}
