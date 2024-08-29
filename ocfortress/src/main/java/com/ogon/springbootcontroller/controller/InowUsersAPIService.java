package com.ogon.springbootcontroller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.entity.InowUsersEntity;
import com.ogon.service.InowUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class InowUsersAPIService {

    @Autowired
    private InowUsersService inowUsersService;

    @GetMapping("/getInsuranceNowUsers")
    public ResponseEntity<String> loadCarrierStateProducts() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        ArrayNode inowUsersArrayNode = objectMapper.createArrayNode();
        try {
            List<InowUsersEntity> inowUsersEntities = inowUsersService.getAllInowUsers();
            for(InowUsersEntity inowUsersEntity : inowUsersEntities) {
                ObjectNode inowUsersDataNode = objectMapper.createObjectNode();
                inowUsersDataNode.put("firstName", inowUsersEntity.getFirstName());
                inowUsersDataNode.put("lastName", inowUsersEntity.getLastName());
                inowUsersDataNode.put("loginId", inowUsersEntity.getLoginId());
                inowUsersDataNode.put("userTypeCd", inowUsersEntity.getUserTypeCd());
                inowUsersArrayNode.add(inowUsersDataNode);
            }
            response.set("inowusers",inowUsersArrayNode);
            response.put("status", "success");
            response.put("message", "InsuranceNow Users Fetch Successful");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            response.put("status", "InsuranceNow Users Fetch Failed");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
