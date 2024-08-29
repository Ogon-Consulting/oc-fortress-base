package com.ogon.umv.product.homeowners.pages.application;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class DwellingQuestions {

    private static final List<String> vehicleUnderwritingTabResult = new ArrayList<>();

    public static List<String> fillDwellingQuestions(){

        // Liability/Medical
        try {
            $(byId("Wizard_Risks")).shouldBe(enabled).click();
            sleep(2000);
            HelperClass.waitForCompletion();

            $(byId("Question_NumberOfHouseholdResidents")).shouldBe(visible, editable).setValue("4");
            $(byId("Question_PurchaseDate")).shouldBe(visible, editable).setValue("01/01/2020");

            $(byId("Question_FarmingBusinessOperation")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_AnyResidenceEmployees")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_DogsOrExoticPets")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_BldgUnderConstApplicantContractor")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_BldgUnderMajorRenovation")).shouldBe(visible, editable).selectOptionByValue("NO");

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
                vehicleUnderwritingTabResult.add("Failed");
                vehicleUnderwritingTabResult.add(quoteNumber);
                vehicleUnderwritingTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            vehicleUnderwritingTabResult.add("Failed");
            vehicleUnderwritingTabResult.add("Application - Underwriting Questions Tab");
            vehicleUnderwritingTabResult.add(result);
        }

        return vehicleUnderwritingTabResult;
    }
}
