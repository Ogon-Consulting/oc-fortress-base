package com.ogon.umv.common.pages;

import com.ogon.apidatamapper.InputData;
import com.ogon.common.utility.Buttons;
import com.ogon.common.utility.StaticVariables;
import com.ogon.config.ConfigProperties;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.entity.TestResultEntity;
import com.ogon.service.TestResultService;
import com.ogon.service.UserInfoService;
import com.ogon.umv.common.utility.HelperClass;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.By;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.tinylog.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

@Epic("Login")
public class UserLogin {

    private List<String> loginResult = new ArrayList<>();
    @Feature("Login with valid credentials")
    @Story("User should be able to log in successfully")
    @Description("This test verifies the login functionality with valid credentials")
    public List<String> executeLoginTest(String loginUserId, String loginPassword) {

        try {
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);
            open(configProperties.getInsuranceNow_URL());

            $(byName("j_username"))
                    .shouldBe(visible)
                    .shouldBe(editable)
                    .setValue(loginUserId);
            $(byName("j_password"))
                    .shouldBe(visible)
                    .shouldBe(editable)
                    .setValue(loginPassword);
            $(byName(Buttons.SIGNIN))
                    .shouldBe(enabled)
                    .click();
            HelperClass.waitBySleep(500);

            if( $(byClassName("error_content_right")).exists() ){
                String errorMsg = $(byClassName("error_content_right")).getText();
                if( !errorMsg.isEmpty() && errorMsg.contains("Invalid user or password")) {
                    loginResult.add("Failed");
                    loginResult.add($(byClassName("error_content_right")).getText());
                    loginResult.add(loginUserId);
                    return loginResult;
                }
            }
            loginResult.add("Success");
            loginResult.add("User Login Successful");
            loginResult.add(loginUserId);
            return loginResult;
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            Logger.error(result);
            loginResult.add("Failed");
            loginResult.add(result);
            loginResult.add(loginUserId);
            return loginResult;
        } catch (Exception ex){
            Logger.error(ex.getMessage());
            loginResult.add("Failed");
            loginResult.add(ex.getMessage());
            loginResult.add(loginUserId);
            return loginResult;
        }
    }

    private void saveTestResult(InputData inputData, String testResult, String statusCd) {
        TestResultService testResultService = new TestResultService();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
        String formattedDate = sdf.format(date);
        TestResultEntity testResultEntity = new TestResultEntity();
        testResultEntity.setCarrierId(inputData.getCarrierId());
        testResultEntity.setStateCd(inputData.getStateCd());
        testResultEntity.setLob(inputData.getStateCd());
        testResultEntity.setTestCaseId("User Login");
        testResultEntity.setExecutedBy(inputData.getExecutedBy());
        testResultEntity.setExecutedOn(formattedDate);
        testResultEntity.setReferenceNumber(inputData.getExecutedBy());
        testResultEntity.setTestCaseName("User Login");
        testResultEntity.setTransactionCd("User Login");
        testResultEntity.setTransactionNumber("");
        testResultEntity.setTestResult(testResult);
        testResultEntity.setTestStatus(statusCd);
        testResultEntity.setJobId(inputData.getNextJobId());
        testResultService.saveTestResults(testResultEntity);
    }

    @Feature("Logout USer")
    @Story("User should be logged out after test execution")
    @Description("User should be logged out after test execution")
    public String logout(){
        try{
            if($(byId("UserMenu")).exists()){
                $(byId("UserMenu")).click();
                $(byId("SignOutInMenu")).click();
                HelperClass.waitBySleep(500);
            }

        }catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            Logger.error(result);
            return StaticVariables.EXECUTION_FAILED;
        } catch (Exception ex){
            Logger.error(ex.getMessage());
            return StaticVariables.EXECUTION_FAILED;
        }
        return StaticVariables.EXECUTION_SUCCESS;
    }
}
