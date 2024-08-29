package com.ogon.umv.product.homeowners.testcases;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;
import com.ogon.umv.product.homeowners.pages.quote.Risks;
import com.ogon.umv.product.personalauto.pages.quote.Vehicles;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class EndorsementAddRisk {

    private static List<String> endorsementResultList = new ArrayList<>();
    @Step("Create endorsements")
    public static List<String> endorsementTestCase(String policyNumber, String effectiveDt) {
        try {

            sleep(500);

            $(byId("ToolbarSearchText")).shouldBe(visible, editable).setValue(policyNumber);
            $(byId("ToolbarSearch")).shouldBe(enabled).click();

            $(byId("Transaction")).shouldBe(enabled).click();
            $(byId("TransactionCd")).shouldBe(visible, editable).selectOptionContainingText("Endorsement");
            $(byId(Buttons.SELECT)).shouldBe(enabled).click();

            $(byId("TransactionEffectiveDt")).setValue(effectiveDt);
            HelperClass.waitForCompletion();
            $(byId("TransactionShortDescription")).shouldBe(enabled).setValue("Updating coverage A limit");
            $(byId(Buttons.START)).shouldBe(enabled).click();
            HelperClass.waitForCompletion();

            String applicationNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();

            if(applicationNumber.isEmpty()){
                applicationNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getOwnText();
            }

            $(byId("Wizard_Risks")).shouldBe(enabled).click();
            HelperClass.waitForCompletion();
            $(byId("Building.CovALimit")).shouldBe(editable,enabled).setValue("500000");

            $(byId(Buttons.CLOSEOUT)).shouldBe(enabled).click();
            HelperClass.waitForCompletion();

            validateError(policyNumber);
            if (!endorsementResultList.isEmpty()) {
                return endorsementResultList;
            }

            $(byId(Buttons.PROCESS)).shouldBe(enabled).click();
            HelperClass.waitForCompletion();

            validateError(policyNumber);
            if (!endorsementResultList.isEmpty()) {
                return endorsementResultList;
            }

            String result = "";
            if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
                if ($(byClassName("error_severity_info")).exists()) {
                    result = $(byId("GenericBusinessError")).getText();
                }
            }
            if(result.isEmpty()){
                result = "Endorsement Processed for " + policyNumber;
            }
            endorsementResultList.add("Success");
            endorsementResultList.add(policyNumber);
            endorsementResultList.add(result);
            if(!applicationNumber.isEmpty()) {
                endorsementResultList.add(applicationNumber);
            }
            return endorsementResultList;
        }catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            endorsementResultList.add("Failed");
            endorsementResultList.add("Endorsement - Update coverage A");
            endorsementResultList.add(result);
            return endorsementResultList;
        }
    }

    private static void validateError(String policyNumber) {
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
            } else if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty() && !$(byClassName("error_severity_info")).exists()) {
                validationMessage = $(byId("GenericBusinessError")).getText();
            }
            if(!validationMessage.isEmpty()) {
                endorsementResultList.add("Failed");
                endorsementResultList.add(policyNumber);
                endorsementResultList.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            endorsementResultList.add("Failed");
            endorsementResultList.add("Endorsement - Update coverage A");
            endorsementResultList.add(result);
        }
    }
}
