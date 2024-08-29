package com.ogon.springbootcontroller.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.apidatamapper.BundleDataMapper;
import com.ogon.apidatamapper.BundleInputData;
import com.ogon.entity.BundlesEntity;
import com.ogon.service.BundlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class BundlesAPIService {
    @Autowired
    private BundlesService bundlesService;

    @GetMapping("/getbundles")
    public ResponseEntity<String> getAllBundlesForCarrierStateProduct(@RequestParam String carrierId, @RequestParam String stateCd, @RequestParam String lob) {
        return bundlesService.getAllBundlesForCarrierStateProduct(carrierId, stateCd, lob);
    }

    @GetMapping("/getactivebundles")
    public ResponseEntity<String> getAllActiveBundlesForCarrierStateProduct(@RequestParam String carrierId, @RequestParam String stateCd, @RequestParam String lob) {
        return bundlesService.getAllActiveBundlesForCarrierStateProduct(carrierId, stateCd, lob);
    }

    @GetMapping("/getbundlebyid")
    public ResponseEntity<String> getBundleById(@RequestParam String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            BundlesEntity bundlesEntity = bundlesService.getBundleById(Integer.parseInt(id));
            ObjectNode bundleDataNode = objectMapper.createObjectNode();
            bundleDataNode.put("id", bundlesEntity.getId());
            bundleDataNode.put("bundleName", bundlesEntity.getBundleName());
            bundleDataNode.put("description", bundlesEntity.getDescription());
            bundleDataNode.put("statusCd", bundlesEntity.getStatusCd());
            response.set("bundle", bundleDataNode);
            response.put("status", "success");
            response.put("message", "Test Case Bundle retrieved successfully");
            return new ResponseEntity<>(response.toString(), HttpStatus.OK);
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Test Case Bundle fetch unsuccessful");
            return new ResponseEntity<>(response.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updatebundle")
    public ResponseEntity<String> updateBundleById(@RequestParam String id, @RequestParam String testCaseBundle) {
        return bundlesService.updateBundleByID(id, testCaseBundle);
    }

    @GetMapping("/deletebundle")
    public ResponseEntity<String> deleteBundleById(@RequestParam int bundleId) {
        return bundlesService.deleteBundleById(bundleId);
    }

    @PostMapping("/addbundle")
    public ResponseEntity<String> addNewBundle(@RequestBody BundleDataMapper bundleDataMapper) {
        return bundlesService.addNewBundle(bundleDataMapper);
    }

    @PostMapping("/executebundlebyid")
    public ResponseEntity<String> executeBundleById(@RequestBody BundleInputData bundleInputData) {
        return bundlesService.executeBundleById(bundleInputData);
    }
}
