package com.ogon.umv.common.pages;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class CreateCustomer {

    public List<String> createNewCustomer(List<TestDataEntity> custTestDataFromDB) {
        String customerName = "";
        List<String> customerResult = new ArrayList<>();
        try {
            customerResult = new SelenideSuiteGenerator().executeSelenideSuite(custTestDataFromDB);
            customerName = $(byId("CustomerSummary.CustomerName")).getText();
            if (!customerResult.isEmpty()) {
                customerResult.add(customerName);
                return customerResult;
            }
            customerResult.add("success");
            customerResult.add("Customer Added :" + customerName);
            customerResult.add(customerName);
            return customerResult;
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            customerResult.add("Failed");
            customerResult.add("Customer Add Failed for " + customerName + " Reason: " + result);
            customerResult.add("Customer Add Failed for " + customerName + " Reason: " + result);
            return customerResult;
        } catch (Exception e) {
            System.out.println("Exception at Create Customer Test Suite : " + e);
            throw new RuntimeException(e);
        }
    }
}
