package com.ogon.umv.product.personalauto.pages.application;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class VehicleUnderwritingQuestions {

    private static final List<String> vehicleUnderwritingTabResult = new ArrayList<>();

    public static List<String> fillVehicleUnderwritingTab(){

        // Liability/Medical
        try {
            $(byId("Wizard_Vehicles")).shouldBe(enabled).click();
            sleep(200);

            $(byLinkText("Edit")).click();
            HelperClass.waitForCompletion();

            $(byId("Question_VehicleSpecialEquipment")).shouldBe(visible, editable).selectOptionByValue("NO");

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
