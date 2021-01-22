package com.earth.hellojpa.service.impl;

import com.earth.hellojpa.entity.UserInfoEntity;
import com.earth.hellojpa.model.*;
import com.earth.hellojpa.repository.UserInfoRepository;
import com.earth.hellojpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.earth.hellojpa.common.ApplicationConstant.*;


@Component //component ? service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public ResponseEntity<UserInfoResponse> register(UserInfoRequest request) {
        UserInfoResponse response = new UserInfoResponse();
        try {
            if (request == null) {
                throw new Exception("request is required.");
            }
            UserInfo userInfo = validateRequest(request.getUserInfo(), VERIFY_TYPE_ALL);
            createUserInfo(userInfo);
            response.setStatus(STATUS_OK);
        } catch (Exception e) {
            response.setStatus(STATUS_FAIL);
            response.setStatusDesc(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserInfoSearchResponse> search(UserInfoSearchRequest request) {
        UserInfoSearchResponse response = new UserInfoSearchResponse();
        try {
            if (request == null) {
                throw new Exception("request is required.");
            }
            UserInfo userInfo = validateRequest(request.getUserInfo(), VERIFY_TYPE_SEARCH);
            List<UserInfoEntity> result = userInfoRepository.findByTell(userInfo.getTell());
            List<UserInfo> userInfos = convertEntityToUserInfo(result);
            response.setUserInfos(userInfos);
            response.setStatus(STATUS_OK);
        } catch (Exception e) {
            response.setStatus(STATUS_FAIL);
            response.setStatusDesc(e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserInfoUpdateResponse> update(UserInfoUpdateRequest request) {
        UserInfoUpdateResponse response = new UserInfoUpdateResponse();
        try {
            if (request == null) {
                throw new Exception("request is required.");
            }

            if (request.getId() == 0) {
                throw new Exception("id is required.");
            }
            UserInfo userInfo = validateRequest(request.getUserInfo(), VERIFY_TYPE_ALL);

            UserInfoEntity userInfoEntity = userInfoRepository.findById(request.getId());
            if (userInfoEntity == null) {
                response.setStatus(STATUS_FAIL);
                response.setStatusDesc("User Information not found.");
                return ResponseEntity.ok(response);
            } else {
                userInfoEntity.setFirstName(userInfo.getFirstName());
                userInfoEntity.setLastName(userInfo.getLastName());
                userInfoEntity.setTell(userInfo.getTell());
                userInfoEntity.setSex(userInfo.getSex());
                userInfoRepository.save(userInfoEntity); // -> update user_info
                response.setStatus(STATUS_OK);
            }
        } catch (Exception e) {
            response.setStatus(STATUS_FAIL);
            response.setStatusDesc(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserInfoDeleteResponse> delete(UserInfoDeleteRequest request) {
        UserInfoDeleteResponse response = new UserInfoDeleteResponse();
        try {
            if (request == null) {
                throw new Exception("request is required.");
            }

            if (request.getId() == 0) {
                throw new Exception("id is required.");
            }
            UserInfoEntity userInfoEntity = userInfoRepository.findById(request.getId());
            if (userInfoEntity == null) {
                response.setStatus(STATUS_FAIL);
                response.setStatusDesc("Invalid Id.");
                return ResponseEntity.ok(response);
            } else {
                userInfoRepository.delete(userInfoEntity);
                response.setStatus(STATUS_OK);
            }
        } catch (Exception e) {
            response.setStatus(STATUS_FAIL);
            response.setStatusDesc(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    private void createUserInfo(UserInfo userInfo) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setFirstName(userInfo.getFirstName());
        entity.setLastName(userInfo.getLastName());
        entity.setSex(userInfo.getSex());
        entity.setTell(userInfo.getTell());

        //save to db
        userInfoRepository.save(entity); //Insert into user_info (fisr_name, last_name, sex, tell) VALUES(?????)
    }

    private List<UserInfo> convertEntityToUserInfo(List<UserInfoEntity> userInfoEntities) {
        List<UserInfo> userInfos = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userInfoEntities)) {
            for (UserInfoEntity entity : userInfoEntities) {
                UserInfo tmp = new UserInfo();
                tmp.setTell(entity.getTell());
                tmp.setSex(entity.getSex());
                tmp.setFirstName(entity.getFirstName());
                tmp.setLastName(entity.getLastName());

                userInfos.add(tmp);
            }
        }
        return userInfos;
    }

    private UserInfo validateRequest(UserInfo userInfo, String verCase) throws Exception {
        switch (verCase) {
            case VERIFY_TYPE_ALL:
                if (userInfo == null) {
                    throw new Exception("userInfo is required.");
                }

                if (StringUtils.isEmpty(userInfo.getFirstName())) {
                    throw new Exception("FirstName is required.");
                }

                if (StringUtils.isEmpty(userInfo.getLastName())) {
                    throw new Exception("LastName is required.");
                }

                if (StringUtils.isEmpty(userInfo.getTell())) {
                    throw new Exception("Tell is required.");
                }
                return userInfo;
            case VERIFY_TYPE_SEARCH:
                if (userInfo == null) {
                    throw new Exception("userInfo is required.");
                }

                if (StringUtils.isEmpty(userInfo.getTell())) {
                    throw new Exception("Tell is required.");
                }
                return userInfo;
        }
        throw new Exception("Internal Server Error.");
    }

}
