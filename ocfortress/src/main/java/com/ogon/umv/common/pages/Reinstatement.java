package com.ogon.umv.common.pages;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class Reinstatement {

    private List<String> reinsTransResult = new ArrayList<>();

    public List<String> reinstatement(List<TestDataEntity> testDataFromDB) {
        String policyNumber = "";
        try {
            reinsTransResult = new SelenideSuiteGenerator().executeSelenideSuite(testDataFromDB);

            if (!reinsTransResult.isEmpty()) {
                if($(byId("PolicySummary_PolicyNumber")).exists()) {
                    policyNumber = $(byId("PolicySummary_PolicyNumber")).getText();
                    reinsTransResult.add(policyNumber);
                }else if($(byId("QuoteAppSummary_QuoteAppNumber")).exists()) {
                    policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
                    if (policyNumber.isEmpty()) {
                        policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getOwnText();
                    }
                    if (!policyNumber.isEmpty()) {
                        reinsTransResult.add(policyNumber);
                    }
                }
                return reinsTransResult;
            }

            String result = "";
            if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
                if ($(byClassName("error_severity_info")).exists()) {
                    result = $(byId("GenericBusinessError")).getText();
                }
            }
            reinsTransResult.add("Success");
            reinsTransResult.add(result);
            reinsTransResult.add(result);
            return reinsTransResult;
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            reinsTransResult.add("Failed");
            reinsTransResult.add("Reinstatement Failed for "+policyNumber+". Reason: "+result);
            reinsTransResult.add(result);
            return reinsTransResult;
        }
    }
//    public List<String> reinstatePolicy(String policyNumber) {
//        try {
//            reinsTransResult = new ArrayList<>();
//
//            $(byId("ToolbarSearchText")).shouldBe(visible, editable).setValue(policyNumber);
//            $(byId("ToolbarSearch")).shouldBe(enabled).click();
//
//            $(byId("Transaction")).shouldBe(enabled).click();
//            $(byId("TransactionCd")).shouldBe(visible, editable).selectOptionByValue("Reinstatement");
//            $(byId(Buttons.SELECT)).shouldBe(enabled).click();
//
//            $(byId(Buttons.START)).shouldBe(enabled).click();
//            HelperClass.waitForCompletion();
//
//            $(byId(Buttons.PROCESS)).shouldBe(enabled).click();
//            HelperClass.waitForCompletion();
//
//            validateError(policyNumber);
//
//            if (reinsTransResult.size() != 0) {
//                return reinsTransResult;
//            }
//
//            String result = "";
//            if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
//                if ($(byClassName("error_severity_info")).exists()) {
//                    result = $(byId("GenericBusinessError")).getText();
//                }
//            }
//            if(result.isEmpty()){
//                result = "Reinstatement has been processed for "+policyNumber;
//            }
//            reinsTransResult.add("Success");
//            reinsTransResult.add(policyNumber);
//            reinsTransResult.add(result);
//            return reinsTransResult;
//        } catch (AssertionError ex) {
//            String result = ex.toString().split("\n")[0];
//            reinsTransResult.add("Failed");
//            reinsTransResult.add("Policy Reinstatement");
//            reinsTransResult.add(result);
//            return reinsTransResult;
//        }
//    }
//    private void validateError(String policyNumber) {
//        try {
//            String validationMessage = "";
//            if ($(byId("Issues")).exists() && $(byId("ErrorIssues")).exists() && !($(byId("ErrorIssues")).getText().isEmpty())) {
//                validationMessage = $(byId("ErrorIssues")).getText();
//            } else if ($(byId("Issues")).exists() && $(byId("ApprovalIssues")).exists() && !($(byId("ApprovalIssues")).getText().isEmpty())) {
//                validationMessage = $(byId("ApprovalIssues")).getText();
//            } else if (($(byId("MissingFieldError")).exists() && !$(byId("MissingFieldError")).getText().isEmpty())) {
//                validationMessage = $(byId("MissingFieldError")).getText();
//            } else if ($(byId("FieldConstraintError")).exists() && !$(byId("FieldConstraintError")).getText().isEmpty()) {
//                validationMessage = $(byId("FieldConstraintError")).getText();
//            } else if ($(byId("ServiceError")).exists() && !$(byId("ServiceError")).getText().isEmpty()) {
//                validationMessage = $(byId("ServiceError")).getText();
//            } else if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
//                if($(byClassName("error_severity_error")).exists()){
//                    validationMessage = $(byId("GenericBusinessError")).getText();
//                }
//            }
//            if(!validationMessage.isEmpty()) {
//                reinsTransResult.add("Failed");
//                reinsTransResult.add(policyNumber);
//                reinsTransResult.add(validationMessage);
//            }
//        }catch(AssertionError ex){
//            String result = ex.toString().split("\n")[0];
//            reinsTransResult.add("Failed");
//            reinsTransResult.add("Policy Reinstatement");
//            reinsTransResult.add(result);
//        }
//    }
}
