package com.ogon.umv.product.homeowners.pages.application;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class AppPolicy {

    private static final List<String> policyTabResult = new ArrayList<>();

    public static List<String> fillPolicyTab(){
        try {
            // Policy General

            $(byId("Wizard_Policy")).shouldBe(enabled).click();
            sleep(200);

            $(byId("CopyAddress")).shouldBe(enabled).click();

            $(byId("InsuredEmail.EmailAddr")).shouldBe(visible, editable).setValue("insured@gmail.com");
            $(byId("InsuredPhonePrimary.PhoneNumber")).shouldBe(visible, editable).setValue("8236548976");

            $(byId(Buttons.SAVE)).shouldBe(enabled).click();
            HelperClass.waitForCompletion();

            String validationMessage = "";
            String quoteNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
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
            }
            if (!validationMessage.isEmpty()) {
                policyTabResult.add("Failed");
                policyTabResult.add(quoteNumber);
                policyTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            policyTabResult.add("Failed");
            policyTabResult.add("Add Quote - Policy Tab");
            policyTabResult.add(result);
        }
        return policyTabResult;
    }
}
