package com.ogon.springbootcontroller.controller;

import com.ogon.service.ProductsListService;
import com.ogon.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class ProductSelectionAPIService {
    @Autowired
    private TestResultService testResultService;
    @Autowired
    private ProductsListService productsListService;

    @GetMapping("/gethistory")
    public String getHistory() {
        return testResultService.getTestHistory();
    }

    @GetMapping("/getjoblog")
    public String getjoblog(@RequestParam String carrierId, @RequestParam String stateCd, @RequestParam String product) {
        return testResultService.getJobLog(carrierId,stateCd,product);
    }

    @GetMapping("/getnextjobid")
    public String getnextjobid() {
        return testResultService.nextJobId();
    }

    @GetMapping("/getcarriers")
    public String getCarriers() {
        return productsListService.getAllCarriers();
    }

    @GetMapping("/getcarrierstates")
    public String getCarrierStates(@RequestParam String carrierId) {
        return productsListService.getCarrierStates(carrierId);
    }

    @GetMapping("/getcarrierstateproduct")
    public String getProductsForCarrierState(@RequestParam String carrierId, @RequestParam String stateCode) {
        return productsListService.getCarrierStateProducts(carrierId, stateCode);
    }

    @GetMapping("/refreshProducts")
    public String refreshCarriersStatesProducts() {
        productsListService.refreshCarriersStatesProducts();
        return productsListService.getAllCarriers();
    }
}