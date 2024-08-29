package com.ogon.umv.common.utility;

import org.tinylog.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class HelperClass {

    public static String generateProducerCd(String carrierId, String product, String stateCd){
        return carrierId+"_"+product+"_"+stateCd+"_"+"PRODCD4";
    }

    public static String generateProducerAgency(String carrierId, String product, String stateCd){
        return carrierId+"_"+product+"_"+stateCd+"_"+"AGENCYCD4";
    }

    public static String generateCustomerFirstName(String carrierId, String product, String stateCd){
        return carrierId+"_"+product+"_"+stateCd+"_"+"CUST_FN";
    }

    public static String generateCustomerLastName(String carrierId, String product, String stateCd){
        return carrierId+"_"+product+"_"+stateCd+"_"+"CUST_LN";
    }

    public static void waitForCompletion() {
        boolean busyBoxIsOpened = true;
        int totaltime = 0;
        System.out.println("LongRunningBusyBox exists: "+ $(byId("LongRunningBusyBox")).exists());
        while(busyBoxIsOpened && totaltime < 20000) {
            if ($(byId("LongRunningBusyBox")).exists() && $(byId("LongRunningBusyBox")).isDisplayed()) {
                sleep(1000);
                totaltime = totaltime+1000;
            }else{
                busyBoxIsOpened = false;
            }
        }
    }
    public static void waitForAddressVerification(String fieldName) {
        int totaltime = 0;
        while(!$(byId(fieldName)).is(visible) && totaltime < 10000) {
            sleep(1000);
            totaltime = totaltime+1000;
        }
        if ($(byId("ui-id-7")).isDisplayed()) {
            $(byId("dialogOK")).click();
        }
    }

    public static void waitBySleep(int milliSecs){
        sleep(milliSecs);
    }

    public static void pressTab(String fieldId){
        $(byId(fieldId)).pressTab();
    }

    public static List<String> validateError() {
        List<String> resultList = new ArrayList<>();
        try {
            String validationMessage = "";
            if ($(byId("Issues")).exists() && $(byId("ErrorIssues")).exists() && !($(byId("ErrorIssues")).getText().isEmpty())) {
                validationMessage = $(byId("ErrorIssues")).getText();
            } else if ($(byId("Issues")).exists() && $(byId("ApprovalIssues")).exists() && !($(byId("ApprovalIssues")).getText().isEmpty())) {
                validationMessage = $(byId("ApprovalIssues")).getText();
            } else if (($(byId("MissingFieldError")).exists() && !$(byId("MissingFieldError")).getText().isEmpty())) {
                validationMessage = $(byId("MissingFieldError")).getText();
            } else if ($(byId("FieldConstraintError")).exists() && !$(byId("FieldConstraintError")).getText().isEmpty()) {
                validationMessage = $(byId("FieldConstraintError")).getText();
            } else if ($(byId("ServiceError")).exists() && !$(byId("ServiceError")).getText().isEmpty()) {
                validationMessage = $(byId("ServiceError")).getText();
            } else if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty() && $(byClassName("error_severity_error")).exists()) {
                validationMessage = $(byId("GenericBusinessError")).getText();
            }
            if(!validationMessage.isEmpty()) {
                resultList.add("Failed");
                resultList.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            resultList.add("Failed");
            resultList.add(result);
        }
        return resultList;
    }
    public static List<String> validateError(List<String> resultList) {
        try {
            String validationMessage = "";
            resultList = new ArrayList<>();
            if ($(byId("Issues")).exists() && $(byId("ErrorIssues")).exists() && !($(byId("ErrorIssues")).getText().isEmpty())) {
                validationMessage = $(byId("ErrorIssues")).getText();
            } else if ($(byId("Issues")).exists() && $(byId("ApprovalIssues")).exists() && !($(byId("ApprovalIssues")).getText().isEmpty())) {
                validationMessage = $(byId("ApprovalIssues")).getText();
            } else if (($(byId("MissingFieldError")).exists() && !$(byId("MissingFieldError")).getText().isEmpty())) {
                validationMessage = $(byId("MissingFieldError")).getText();
            } else if ($(byId("FieldConstraintError")).exists() && !$(byId("FieldConstraintError")).getText().isEmpty()) {
                validationMessage = $(byId("FieldConstraintError")).getText();
            } else if ($(byId("ServiceError")).exists() && !$(byId("ServiceError")).getText().isEmpty()) {
                validationMessage = $(byId("ServiceError")).getText();
            } else if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty() && $(byClassName("error_severity_error")).exists()) {
                validationMessage = $(byId("GenericBusinessError")).getText();
            }
            if(!validationMessage.isEmpty()) {
                resultList.add("Failed");
                resultList.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            resultList.add("Failed");
            resultList.add(result);
        }
        return resultList;
    }

    public static String calculateTimeDuration(String startTime, String endTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");

            Date startDateTime = format.parse(startTime);
            Date endDateTime = format.parse(endTime);

            long durationInMillis = endDateTime.getTime() - startDateTime.getTime();

            long seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis);
            long hours = TimeUnit.MILLISECONDS.toHours(durationInMillis);

            return String.valueOf(seconds);

        } catch (Exception e) {
            Logger.error(e);
        }
            return "0";
    }
}
