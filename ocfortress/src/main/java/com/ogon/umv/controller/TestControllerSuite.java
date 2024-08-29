package com.ogon.umv.controller;

import com.codeborne.selenide.Configuration;
import com.ogon.apidatamapper.InputData;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.common.utility.StaticVariables;
import com.ogon.config.ConfigProperties;
import com.ogon.umv.product.homeowners.handler.HomeownersHandlerExcel;
import com.ogon.umv.product.personalauto.handler.PersonalAutoHandlerUpdated;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.Test;
import org.tinylog.Logger;

@Test
public class TestControllerSuite {

    public String runTestSuite(InputData inputData) {

        try {
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);
            Configuration.headless = "Yes".equalsIgnoreCase(configProperties.getHeadlessExecution());
            String product = inputData.getProduct();
            String result = StaticVariables.EXECUTION_FAILED;
            if ("HOP".equalsIgnoreCase(product)) {
                try {
                    result = HomeownersHandlerExcel.executeHomeOwnersTestSuite(inputData);
                } catch (AssertionError ex) {
                    result = ex.toString().split("\n")[0];
                }
            }
            if ("PAP".equalsIgnoreCase(product)) {
                try {
                    result = new PersonalAutoHandlerUpdated().executePersonalAutoTestSuite(inputData);
                } catch (AssertionError ex) {
                    result = ex.toString().split("\n")[0];
                }
            }
            return result;
        } catch (Exception e) {
            Logger.error(e.getMessage());
            return StaticVariables.EXECUTION_FAILED;
        }
    }
}
