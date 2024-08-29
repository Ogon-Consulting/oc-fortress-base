package com.ogon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/config.properties")
public class ConfigProperties {

    @Value("${config.allProductURL}")
    private String allProductURL;

    @Value("${config.insuranceNow_User}")
    private String insuranceNow_User;

    @Value("${config.insuranceNow_Password}")
    private String insuranceNow_Password;

    @Value("${config.insuranceNow_URL}")
    private String insuranceNow_URL;

    @Value("${config.routingNumber}")
    private String routingNumber;

    @Value("${config.loadTestCasesOnStartup}")
    private String loadTestCasesOnStartup;

    @Value("${config.headlessExecution}")
    private String headlessExecution;

    @Value("${config.loadProductsOnStartup}")
    private String loadProductsOnStartup;

    @Value("${config.loadInsuranceNowUsersOnStartup}")
    private String loadInsuranceNowUsersOnStartup;

    @Value("${config.allUsersURL}")
    private String allUsersURL;

    public String getAllProductURL() {
        return allProductURL;
    }

    public void setAllProductURL(String allProductURL) {
        this.allProductURL = allProductURL;
    }

    public String getInsuranceNow_User() {
        return insuranceNow_User;
    }

    public void setInsuranceNow_User(String insuranceNow_User) {
        this.insuranceNow_User = insuranceNow_User;
    }

    public String getInsuranceNow_Password() {
        return insuranceNow_Password;
    }

    public void setInsuranceNow_Password(String insuranceNow_Password) {
        this.insuranceNow_Password = insuranceNow_Password;
    }

    public String getInsuranceNow_URL() {
        return insuranceNow_URL;
    }

    public void setInsuranceNow_URL(String insuranceNow_URL) {
        this.insuranceNow_URL = insuranceNow_URL;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getLoadTestCasesOnStartup() {
        return loadTestCasesOnStartup;
    }

    public void setLoadTestCasesOnStartup(String loadTestCasesOnStartup) {
        this.loadTestCasesOnStartup = loadTestCasesOnStartup;
    }

    public String getHeadlessExecution() {
        return headlessExecution;
    }

    public void setHeadlessExecution(String headlessExecution) {
        this.headlessExecution = headlessExecution;
    }

    public String getLoadProductsOnStartup() {
        return loadProductsOnStartup;
    }

    public void setLoadProductsOnStartup(String loadProductsOnStartup) {
        this.loadProductsOnStartup = loadProductsOnStartup;
    }

    public String getLoadInsuranceNowUsersOnStartup() {
        return loadInsuranceNowUsersOnStartup;
    }

    public void setLoadInsuranceNowUsersOnStartup(String loadInsuranceNowUsersOnStartup) {
        this.loadInsuranceNowUsersOnStartup = loadInsuranceNowUsersOnStartup;
    }

    public String getAllUsersURL() {
        return allUsersURL;
    }

    public void setAllUsersURL(String allUsersURL) {
        this.allUsersURL = allUsersURL;
    }
}