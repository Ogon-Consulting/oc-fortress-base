package com.ogon.umv.product.personalauto.pages.application;

import com.codeborne.selenide.SelenideElement;
import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class Application {

    private static List<String> processApplicationResult = new ArrayList<>();
    public static List<String> processApplication(){
        try {

            if($(byId("Select Customer")).exists()){
                SelenideElement element = $("input[name='QuoteCustomerClearingRef'][value='New']");
                element.click();
            }

            $(byId(Buttons.BIND)).shouldBe(enabled).click();
            HelperClass.waitForCompletion();

            processApplicationResult = AppPolicy.fillPolicyTab();
            processApplicationResult = UnderwritingQuestions.fillUnderwritingTab();
            processApplicationResult = VehicleUnderwritingQuestions.fillVehicleUnderwritingTab();

            $(byId(Buttons.CLOSEOUT)).shouldBe(enabled).click();
            validateError();
            if (!processApplicationResult.isEmpty()) {
                return processApplicationResult;
            }
            return processApplicationResult;
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            processApplicationResult.add("Failed");
            processApplicationResult.add("Create Application");
            processApplicationResult.add(result);
            return processApplicationResult;
        }
    }
    private static void validateError() {
        try {
            String validationMessage = "";
            String applicationNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
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
            } else if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
                validationMessage = $(byId("GenericBusinessError")).getText();
                if("Application has been created".equalsIgnoreCase(validationMessage)){
                    validationMessage = "";
                }
            }
            if(!validationMessage.isEmpty()) {
                processApplicationResult.add("Failed");
                processApplicationResult.add(applicationNumber);
                processApplicationResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            processApplicationResult.add("Failed");
            processApplicationResult.add("Create Application");
            processApplicationResult.add(result);
        }
    }
}
