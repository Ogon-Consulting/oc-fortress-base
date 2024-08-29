package com.ogon.umv.common.pages;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class InsuredCancellation {

    private List<String> cancelTransResult =  new ArrayList<>();

    public List<String> insuredCancellation(List<TestDataEntity> testDataFromDB) {
        String policyNumber = "";
        try {

            cancelTransResult = new SelenideSuiteGenerator().executeSelenideSuite(testDataFromDB);

            if (!cancelTransResult.isEmpty()) {
                if($(byId("PolicySummary_PolicyNumber")).exists()) {
                    policyNumber = $(byId("PolicySummary_PolicyNumber")).getText();
                    cancelTransResult.add(policyNumber);
                }else if($(byId("QuoteAppSummary_QuoteAppNumber")).exists()) {
                    policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
                    if (policyNumber.isEmpty()) {
                        policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getOwnText();
                    }
                    if (!policyNumber.isEmpty()) {
                        cancelTransResult.add(policyNumber);
                    }
                }
                return cancelTransResult;
            }

            String result = "";
            if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
                if ($(byClassName("error_severity_info")).exists()) {
                    result = $(byId("GenericBusinessError")).getText();
                }
            }
            cancelTransResult.add("Success");
            cancelTransResult.add(result);
            cancelTransResult.add(result);
            return cancelTransResult;
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            cancelTransResult.add("Failed");
            cancelTransResult.add("Insured Cancellation Failed for "+policyNumber+". Reason: "+result);
            cancelTransResult.add(result);
            return cancelTransResult;
        }
    }
//    public List<String> cancelPolicy(String policyNumber, String effectiveDt) {
//        try {
//
//            cancelTransResult = new ArrayList<>();
//
//            $(byId("ToolbarSearchText")).shouldBe(visible, editable).setValue(policyNumber);
//            $(byId("ToolbarSearch")).shouldBe(enabled).click();
//
//            $(byId("Transaction")).shouldBe(enabled).click();
//            $(byId("TransactionCd")).shouldBe(visible, editable).selectOptionByValue("Cancellation");
//            $(byId(Buttons.SELECT)).shouldBe(enabled).click();
//            HelperClass.waitForCompletion();
//
//            $(byId("CancelRequestedByCd")).shouldBe(visible, editable).selectOptionByValue("Insured");
//            HelperClass.waitForCompletion();
//
//            $(byId("ReasonCd")).shouldBe(visible, editable).selectOptionByValue("SoldVehicles");
//
//            $(byId(Buttons.ADD)).shouldBe(enabled).click();
//            HelperClass.waitForCompletion();
//
//            if($(byId("TransactionEffectiveDt")).exists() && Objects.requireNonNull($(byId("TransactionEffectiveDt")).getValue()).isEmpty()) {
//                $(byId("TransactionEffectiveDt")).setValue(effectiveDt);
//            }
//
//            $(byId("CancelTypeCd")).shouldBe(visible, editable).selectOptionByValue("Pro-Rate");
//            HelperClass.waitForCompletion();
//
//            $(byId(Buttons.START)).shouldBe(enabled).click();
//            HelperClass.waitForCompletion();
//
//            $(byId(Buttons.PROCESS)).shouldBe(enabled).click();
//            HelperClass.waitForCompletion();
//
//            validateError(policyNumber);
//
//            if (!cancelTransResult.isEmpty()) {
//                return cancelTransResult;
//            }
//
//            String result = "";
//            if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
//                if ($(byClassName("error_severity_info")).exists()) {
//                    result = $(byId("GenericBusinessError")).getText();
//                }
//            }
//            cancelTransResult.add("Success");
//            cancelTransResult.add(policyNumber);
//            cancelTransResult.add(result);
//            return cancelTransResult;
//        } catch (AssertionError ex) {
//            String result = ex.toString().split("\n")[0];
//            cancelTransResult.add("Failed");
//            cancelTransResult.add("Policy Cancellation");
//            cancelTransResult.add(result);
//            return cancelTransResult;
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
//                cancelTransResult.add("Failed");
//                cancelTransResult.add(policyNumber);
//                cancelTransResult.add(validationMessage);
//            }
//        }catch(AssertionError ex){
//            String result = ex.toString().split("\n")[0];
//            cancelTransResult.add("Failed");
//            cancelTransResult.add("Policy Cancellation");
//            cancelTransResult.add(result);
//        }
//    }
}
