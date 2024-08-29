package com.ogon.umv.product.personalauto.pages.application;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class UnderwritingQuestions {

    private static final List<String> underwritingTabResult = new ArrayList<>();

    public static List<String> fillUnderwritingTab(){

        // Liability/Medical
        try {
            $(byId("Wizard_Underwriting")).shouldBe(enabled).click();
            HelperClass.waitForCompletion();
            $(byId("Question_OtherLicensedInHH")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_OtherInsWithCo")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_CovDeclinedin3Years")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_DrivLiscSusp")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_PhysMentalImpairment")).shouldBe(visible, editable).selectOptionByValue("NO");

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
                underwritingTabResult.add("Failed");
                underwritingTabResult.add(quoteNumber);
                underwritingTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            underwritingTabResult.add("Failed");
            underwritingTabResult.add("Application - Underwriting Questions Tab");
            underwritingTabResult.add(result);
        }

        return underwritingTabResult;
    }
}
