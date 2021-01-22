package com.earth.hellojpa.service;

import com.earth.hellojpa.model.*;
import org.springframework.http.ResponseEntity;

public interface UserInfoService {
    ResponseEntity<UserInfoResponse> register(UserInfoRequest request);

    ResponseEntity<UserInfoSearchResponse> search(UserInfoSearchRequest request);

    ResponseEntity<UserInfoUpdateResponse> update(UserInfoUpdateRequest request);

    ResponseEntity<UserInfoDeleteResponse> delete(UserInfoDeleteRequest request);
}
