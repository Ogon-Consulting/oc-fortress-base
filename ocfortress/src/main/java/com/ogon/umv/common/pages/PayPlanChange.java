package com.ogon.umv.common.pages;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class PayPlanChange {
    private List<String> payPlanChangeResult = new ArrayList<>();

    public List<String> payPlanChange(List<TestDataEntity> testDataFromDB) {
        String policyNumber = "";
        try {
            payPlanChangeResult = new SelenideSuiteGenerator().executeSelenideSuite(testDataFromDB);

            if (!payPlanChangeResult.isEmpty()) {
                if($(byId("PolicySummary_PolicyNumber")).exists()) {
                    policyNumber = $(byId("PolicySummary_PolicyNumber")).getText();
                    payPlanChangeResult.add(policyNumber);
                }else if($(byId("QuoteAppSummary_QuoteAppNumber")).exists()) {
                    policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
                    if (policyNumber.isEmpty()) {
                        policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getOwnText();
                    }
                    if (!policyNumber.isEmpty()) {
                        payPlanChangeResult.add(policyNumber);
                    }
                }
                return payPlanChangeResult;
            }

            String result = "";
            if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
                if ($(byClassName("error_severity_info")).exists()) {
                    result = $(byId("GenericBusinessError")).getText();
                }
            }
            payPlanChangeResult.add("Success");
            payPlanChangeResult.add("PayPlan change processed. "+ result);
            payPlanChangeResult.add(result);
            return payPlanChangeResult;
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            payPlanChangeResult.add("Failed");
            payPlanChangeResult.add("Pay plan change Failed for "+policyNumber+". Reason: "+result);
            payPlanChangeResult.add(result);
            return payPlanChangeResult;
        }
    }
//    public List<String> payPlanChange(String policyNumber) {
//
//        try {
//            payPlanChangeResult = new ArrayList<>();
//
//            $(byId("ToolbarSearchText")).shouldBe(visible, editable).setValue(policyNumber);
//            $(byId("ToolbarSearch")).shouldBe(enabled).click();
//
//            if (!$(byId("Tab_Billing")).exists()) {
//                sleep(2000);
//            }
//
//            $(byId("Tab_Billing")).click();
//            sleep(2000);
//
//            $(byId("_ChangePayplan_Link")).click();
//            sleep(2000);
//            String newPayPlan = "";
//
//            if (policyNumber.startsWith("HOP")) {
//                $(byId("BasicPolicy.PayPlanCd_3")).click();
//                newPayPlan = $(byId("BasicPolicy.PayPlanCd_3")).getValue();
//            } else {
//                $(byId("BasicPolicy.PayPlanCd_2")).click();
//                HelperClass.waitForCompletion();
//                newPayPlan = $(byId("BasicPolicy.PayPlanCd_2")).getValue();
//            }
//
//
//            if ($(byValue("BasicPolicy.PaymentDay")).exists()) {
//                $(byId("BasicPolicy.PaymentDay")).shouldBe(visible, editable).setValue("30");
//            }
//
//            $(byId(Buttons.PROCESS)).shouldBe(enabled).click();
//
//            HelperClass.waitForCompletion();
//
//            validateError(policyNumber);
//
//            if (payPlanChangeResult.size() != 0) {
//                return payPlanChangeResult;
//            }
//
//            payPlanChangeResult.add("Success");
//            payPlanChangeResult.add(policyNumber);
//            payPlanChangeResult.add("Pay Plan Changed to " + newPayPlan);
//            return payPlanChangeResult;
//        }catch (AssertionError ex){
//            String result = ex.toString().split("\n")[0];
//            payPlanChangeResult.add("Failed");
//            payPlanChangeResult.add("Pay plan change");
//            payPlanChangeResult.add(result);
//            return payPlanChangeResult;
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
//                payPlanChangeResult.add("Failed");
//                payPlanChangeResult.add(policyNumber);
//                payPlanChangeResult.add(validationMessage);
//            }
//        }catch(AssertionError ex){
//            String result = ex.toString().split("\n")[0];
//            payPlanChangeResult.add("Failed");
//            payPlanChangeResult.add("Pay plan change");
//            payPlanChangeResult.add(result);
//        }
//    }
}
