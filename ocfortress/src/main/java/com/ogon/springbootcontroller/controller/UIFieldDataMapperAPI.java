package com.ogon.springbootcontroller.controller;

import com.ogon.apidatamapper.NewTestCaseInputData;
import com.ogon.service.UIFieldTestDataMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1")
public class UIFieldDataMapperAPI {

    @Autowired
    private UIFieldTestDataMapService uiFieldTestDataMapService;

    @GetMapping("/getuifielddata")
    public String fetchUIFieldData(@RequestParam String carrierId, @RequestParam String stateCd, @RequestParam String lob) {
        return uiFieldTestDataMapService.getUIFieldDataForCarrierStateLob(carrierId, stateCd, lob);
    }

    @GetMapping("/gettestdatabytestcase")
    public String fetchTestDataByTestCase(@RequestParam String carrierId, @RequestParam String stateCd, @RequestParam String lob, @RequestParam String testCaseId) {
        return uiFieldTestDataMapService.getTestDataByTestCaseID(carrierId, stateCd, lob, testCaseId);
    }

//    @GetMapping("/createnewtestcase")
//    public String createNewTestCase(@RequestParam String carrierId, @RequestParam String stateCd, @RequestParam String lob, @RequestParam String testCaseName, @RequestParam String testCaseData) {
//        return uiFieldTestDataMapService.createNewTestCase(carrierId, stateCd, lob, testCaseName, testCaseData);
//    }

    @PostMapping("/createnewtestcase")
    public String createNewTestCase(@RequestBody NewTestCaseInputData newTestCaseInputData) {
        return uiFieldTestDataMapService.createNewTestCase(newTestCaseInputData);
    }
}
