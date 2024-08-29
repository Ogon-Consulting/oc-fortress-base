package com.ogon.umv.common.pages;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;

import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class MakePayment {
    private List<String> makePaymentResult;

    public List<String> makePayment(List<TestDataEntity> testDataFromDB) {
        String policyNumber = "";
        try {

            makePaymentResult = new SelenideSuiteGenerator().executeSelenideSuite(testDataFromDB);

            if (!makePaymentResult.isEmpty()) {
                if($(byId("PolicySummary_PolicyNumber")).exists()) {
                    policyNumber = $(byId("PolicySummary_PolicyNumber")).getText();
                    makePaymentResult.add(policyNumber);
                }else if($(byId("QuoteAppSummary_QuoteAppNumber")).exists()) {
                    policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
                    if (policyNumber.isEmpty()) {
                        policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getOwnText();
                    }
                    if (!policyNumber.isEmpty()) {
                        makePaymentResult.add(policyNumber);
                    }
                }
                return makePaymentResult;
            }

            String result = "";
            if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
                if ($(byClassName("error_severity_info")).exists()) {
                    result = $(byId("GenericBusinessError")).getText();
                }
            }
            if(result.isEmpty() && $(byId("ARSummary_PolicyNumber")).exists()){
                result = $(byId("ARSummary_PolicyNumber")).getText();
            }
            makePaymentResult.add("Success");
            makePaymentResult.add("Payment processed for "+policyNumber + result);
            makePaymentResult.add(result);
            return makePaymentResult;
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            makePaymentResult.add("Failed");
            makePaymentResult.add("Make Payment Failed for "+policyNumber+". Reason: "+result);
            makePaymentResult.add(result);
            return makePaymentResult;
        }
    }
//    public List<String> makePayment(String policyNumber) {
//        try {
//            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
//            ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);
//
//            makePaymentResult = new ArrayList<>();
//
//            $(byId("QuickAction_MakePayment_Holder")).click();
//
//            $(byId("QuickAction_MakePaymentPolicyNumber"))
//                    .shouldBe(visible)
//                    .shouldBe(editable)
//                    .setValue(policyNumber);
//
//            $(byId("QuickAction_SubmitPayment")).click();
//            sleep(2000);
//            String amount = "";
//
//            SelenideElement element = $("[id*='_TotalDue']");
//
//            if ($(byId("ARSummary_TotalDue")).exists()) {
//                amount = $(byId("ARSummary_TotalDue")).getText();
//            }
//
//            if (amount.isEmpty()) {
//                amount = "100";
//            }
//
//            $(byName("PaymentTypeCd"))
//                    .shouldBe(visible)
//                    .shouldBe(editable)
//                    .selectOptionByValue("ACH");
//
//            $(byName("ReceiptAmt"))
//                    .shouldBe(visible)
//                    .shouldBe(editable)
//                    .setValue(amount);
//
//
//            if ($(byId("ACHStandardEntryClassCd")).exists()) {
//                $(byId("ACHStandardEntryClassCd")).shouldBe(visible, editable).selectOptionByValue("WEB");
//            }
//
//            if ($(byId("ACHBankAccountTypeCd")).exists()) {
//                $(byId("ACHBankAccountTypeCd")).shouldBe(visible, editable).selectOptionByValue("Checking");
//            }
//
//            if ($(byId("ACHBankName")).exists()) {
//                $(byId("ACHBankName")).shouldBe(visible, editable).setValue("Bank Of America");
//            }
//
//            if ($(byId("ACHBankAccountNumber")).exists()) {
//                $(byId("ACHBankAccountNumber")).shouldBe(visible, editable).setValue("753159963");
//            }
//
//            if ($(byId("ACHRoutingNumber")).exists()) {
//                $(byId("ACHRoutingNumber")).shouldBe(visible, editable).setValue(configProperties.getRoutingNumber());
//            }
//
//            if ($(byId("ACHName")).exists() && $(byId("ACHName")).getValue().isEmpty()) {
//                if ($(byId("ARSummary_InsuredName")).exists()) {
//                    $(byId("ACHName")).shouldBe(visible, editable).setValue($(byId("ARSummary_InsuredName")).getText());
//                } else {
//                    $(byId("ACHName")).shouldBe(visible, editable).setValue("Insured");
//                }
//            }
//
//            if ($(byId("ByTelephoneInd")).exists()) {
//                $(byId("ByTelephoneInd")).shouldBe(visible, editable).selectOptionByValue("NO");
//            }
//
//            $(byId(Buttons.SUBMIT_PAYMENT)).click();
//
//            if ($(byClassName("ui-dialog-buttonset")).exists()) {
//                if ($(byId("dialogOK")).exists()) {
//                    $(byId("dialogOK")).click();
//                }
//            }
//
//            HelperClass.waitForCompletion();
//
//            validateError(policyNumber);
//
//            if (makePaymentResult.size() != 0) {
//                return makePaymentResult;
//            }
//
//            makePaymentResult.add("Success");
//            makePaymentResult.add(policyNumber);
//            makePaymentResult.add("Payment Made for " + policyNumber);
//            return makePaymentResult;
//        }catch (AssertionError ex){
//            String result = ex.toString().split("\n")[0];
//            makePaymentResult.add("Failed");
//            makePaymentResult.add("Make Payment Failed");
//            makePaymentResult.add(result);
//            return makePaymentResult;
//        }
//    }
//
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
//                makePaymentResult.add("Failed");
//                makePaymentResult.add(policyNumber);
//                makePaymentResult.add(validationMessage);
//            }
//        }catch(AssertionError ex){
//            String result = ex.toString().split("\n")[0];
//            makePaymentResult.add("Failed");
//            makePaymentResult.add("Make Payment");
//            makePaymentResult.add(result);
//        }
//    }
}
