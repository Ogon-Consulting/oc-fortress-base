package com.ogon.springbootcontroller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class LoginAPIService {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/verifyuser")
    public String verifyUser(@RequestParam String userId, @RequestParam String password){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        String result = "Failed";
        try {
            result = userInfoService.verifyUserInfo(userId, password);
            response.put("message", "User "+userId+" Verified ");
        }catch (Exception e){
            response.put("message", "User Verification Failed for " + userId);
        }
        response.put("status", result);
        return  response.toString();
    }
}
