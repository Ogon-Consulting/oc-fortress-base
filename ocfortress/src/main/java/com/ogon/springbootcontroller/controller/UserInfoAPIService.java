package com.ogon.springbootcontroller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.apidatamapper.UserInfoData;
import com.ogon.entity.UserInfoEntity;
import com.ogon.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class UserInfoAPIService {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<String> getAllUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            ArrayNode usersArrayNode = objectMapper.createArrayNode();
            List<UserInfoEntity> userInfoEntities = userInfoService.getAllUserInfo();
            for (UserInfoEntity userInfoEntity : userInfoEntities) {
                ObjectNode userDataNode = objectMapper.createObjectNode();
                userDataNode.put("userName", userInfoEntity.getUserName());
                userDataNode.put("password", "");
                userDataNode.put("role", userInfoEntity.getRole());
                userDataNode.put("emailId", userInfoEntity.getEmailId());
                userDataNode.put("addedDate", userInfoEntity.getAddedDate());
                userDataNode.put("updatedDate", userInfoEntity.getUpdatedDate());
                usersArrayNode.add(userDataNode);
            }
            response.put("status", "Success");
            if (!userInfoEntities.isEmpty()) {
                response.put("message", "All Users Fetched");
            } else {
                response.put("message", "No Users Fetched");
            }
            response.set("users", usersArrayNode);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            Logger.error(e);
            response.put("status", "Failed");
            response.put("message", "Users Fetch Failed");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userLookup")
    public ResponseEntity<String> searchUser(@RequestParam String searchUserName, @RequestParam String searchOption, @RequestParam String searchText, @RequestParam String searchRole) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            ArrayNode usersArrayNode = objectMapper.createArrayNode();
            if (searchUserName.isEmpty() && searchOption.isEmpty() && searchText.isEmpty() && searchRole.isEmpty()) {
                List<UserInfoEntity> userInfoEntities = userInfoService.getAllUserInfo();
                for (UserInfoEntity userInfoEntity : userInfoEntities) {
                    ObjectNode userDataNode = objectMapper.createObjectNode();
                    userDataNode.put("userName", userInfoEntity.getUserName());
                    userDataNode.put("password", "");
                    userDataNode.put("role", userInfoEntity.getRole());
                    userDataNode.put("emailId", userInfoEntity.getEmailId());
                    userDataNode.put("addedDate", userInfoEntity.getAddedDate());
                    userDataNode.put("updatedDate", userInfoEntity.getUpdatedDate());
                    usersArrayNode.add(userDataNode);
                }
            } else if (!searchUserName.isEmpty()) {
                UserInfoEntity userInfoEntity = userInfoService.getUserInfo(searchUserName);
                if (userInfoEntity != null) {
                    ObjectNode userDataNode = objectMapper.createObjectNode();
                    userDataNode.put("userName", userInfoEntity.getUserName());
                    userDataNode.put("password", "");
                    userDataNode.put("role", userInfoEntity.getRole());
                    userDataNode.put("emailId", userInfoEntity.getEmailId());
                    userDataNode.put("addedDate", userInfoEntity.getAddedDate());
                    userDataNode.put("updatedDate", userInfoEntity.getUpdatedDate());
                    usersArrayNode.add(userDataNode);
                    response.put("message", "User Fetched");
                } else {
                    response.put("message", "No User Found");
                }
            } else if (!searchOption.isEmpty()) {
                List<UserInfoEntity> userInfoEntities = new ArrayList<>();
                if ("Contains".equalsIgnoreCase(searchOption)) {
                    userInfoEntities = userInfoService.searchUserContainsText(searchText);
                } else if ("Starts With".equalsIgnoreCase(searchOption)) {
                    userInfoEntities = userInfoService.searchUserStartsWithText(searchText);
                }
                for (UserInfoEntity userInfoEntity : userInfoEntities) {
                    ObjectNode userDataNode = objectMapper.createObjectNode();
                    userDataNode.put("userName", userInfoEntity.getUserName());
                    userDataNode.put("password", "");
                    userDataNode.put("role", userInfoEntity.getRole());
                    userDataNode.put("emailId", userInfoEntity.getEmailId());
                    userDataNode.put("addedDate", userInfoEntity.getAddedDate());
                    userDataNode.put("updatedDate", userInfoEntity.getUpdatedDate());
                    usersArrayNode.add(userDataNode);
                }
                response.put("status", "Success");
                if (!userInfoEntities.isEmpty()) {
                    response.put("message", "Users Fetched");
                } else {
                    response.put("message", "No Users Fetched");
                }
            } else if (!searchRole.isEmpty()) {
                List<UserInfoEntity> userInfoEntities = userInfoService.searchUsersByRole(searchRole);
                for (UserInfoEntity userInfoEntity : userInfoEntities) {
                    ObjectNode userDataNode = objectMapper.createObjectNode();
                    userDataNode.put("userName", userInfoEntity.getUserName());
                    userDataNode.put("password", "");
                    userDataNode.put("role", userInfoEntity.getRole());
                    userDataNode.put("emailId", userInfoEntity.getEmailId());
                    userDataNode.put("addedDate", userInfoEntity.getAddedDate());
                    userDataNode.put("updatedDate", userInfoEntity.getUpdatedDate());
                    usersArrayNode.add(userDataNode);
                }
                response.put("status", "Success");
                if (!userInfoEntities.isEmpty()) {
                    response.put("message", "Users Fetched for Role '" + searchRole + "'");
                } else {
                    response.put("message", "No Users Found for Role '" + searchRole + "'");
                }
            }
            response.put("status", "Success");
            response.set("user", usersArrayNode);
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            Logger.error(e);
            response.put("status", "Failed");
            response.put("message", "User Fetch Failed");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfoData userInfoData) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            if (userInfoService.getUserInfo(userInfoData.getUserName()) != null) {
                response.put("status", "User " + userInfoData.getUserName() + " already exists");
            } else {
                UserInfoEntity userInfoEntity = getUserInfoEntity(userInfoData);
                userInfoService.addUserInfo(userInfoEntity);
                response.put("status", "User " + userInfoData.getUserName() + " added");
            }
        } catch (Exception e) {
            response.put("status", "User Add Failed for " + userInfoData.getUserName());
        }
        return response.toString();
    }

    private UserInfoEntity getUserInfoEntity(UserInfoData userInfoData) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUserName(userInfoData.getUserName());
        userInfoEntity.setPassword(userInfoData.getPassword());
        userInfoEntity.setRole(userInfoData.getRole());
        userInfoEntity.setEmailId(userInfoData.getEmailId());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
        String formattedDate = sdf.format(date);
        userInfoEntity.setAddedDate(formattedDate);
        userInfoEntity.setUpdatedDate(formattedDate);
        return userInfoEntity;
    }

    @PostMapping("/updateUserPassword")
    public String updateUserPassword(@RequestBody UserInfoData userInfoData) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
            String formattedDate = sdf.format(date);
            int updateUsers = userInfoService.updateUserPassword(userInfoData.getUserName(), userInfoData.getPassword(), formattedDate);
            if (updateUsers == 1) {
                response.put("status", "Password Updated for User '" + userInfoData.getUserName() + "'");
            } else if (updateUsers == 0) {
                response.put("status", "User '" + userInfoData.getUserName() + "' does not exists");
            } else if (updateUsers > 1) {
                response.put("status", "More than one row was update for User '" + userInfoData.getUserName() + "' updated");
            }
        } catch (Exception e) {
            response.put("status", "User Password Update Failed for '" + userInfoData.getUserName() + "'");
        }
        return response.toString();
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestBody UserInfoData userInfoData) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
            String formattedDate = sdf.format(date);
            UserInfoEntity userEntity = userInfoService.getUserInfo(userInfoData.getUserName());
            if (userEntity != null) {
                if (!userInfoData.getPassword().isEmpty() && !userInfoData.getPassword().equals(userEntity.getPassword())) {
                    userEntity.setPassword(userInfoData.getPassword());
                }
                if (!userInfoData.getEmailId().isEmpty() && !userInfoData.getEmailId().equals(userEntity.getEmailId())) {
                    userEntity.setEmailId(userInfoData.getEmailId());
                }
                if (!userInfoData.getRole().isEmpty() && !userInfoData.getRole().equals(userEntity.getRole())) {
                    userEntity.setRole(userInfoData.getRole());
                }
                userEntity.setUpdatedDate(formattedDate);
                userInfoService.updateUser(userEntity);
                response.put("status", "User Details Updated for '" + userInfoData.getUserName() + "'");
            } else {
                response.put("status", "User '" + userInfoData.getUserName() + "' does not exists");
            }
        } catch (Exception e) {
            response.put("status", "User Update Failed for '" + userInfoData.getUserName() + "'");
        }
        return response.toString();
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam String userName) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            int deletedUsers = userInfoService.deleteUser(userName);
            if (deletedUsers == 1) {
                response.put("status", "User '" + userName + "' deleted");
            } else if (deletedUsers == 0) {
                response.put("status", "User '" + userName + "' does not exists");
            } else if (deletedUsers > 1) {
                response.put("status", "More than one users with same user name '" + userName + "' were deleted");
            }
        } catch (Exception e) {
            response.put("status", "User Deletion Failed for '" + userName + "'");
        }
        return response.toString();
    }
}
