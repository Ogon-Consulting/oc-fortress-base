package com.ogon.umv.product.homeowners.pages.quote;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class Risks {

    private static List<String> addRiskTabResult = new ArrayList<>();

    public static List<String> fillRisksTab(){

        // Liability/Medical
        try {
            $(byId(Buttons.SAVE)).shouldBe(enabled).click();

            HelperClass.waitForCompletion();

            $(byId("Building.FoundationType")).shouldBe(visible, editable).selectOptionByValue("Basement");
            $(byId("Building.DistanceToHydrant")).shouldBe(visible, editable).selectOptionByValue("1000");
            $(byId("Building.DistanceToFireStation")).shouldBe(visible, editable).selectOptionByValue("5");
            $(byId("Building.DistanceToFireStation")).shouldBe(visible, editable).selectOptionByValue("5");

            //Risk Questions
            $(byId("Question_RoofYearReplaced")).shouldBe(visible, editable).setValue("2020");

            $(byId("Question_AmpService")).shouldBe(visible, editable).selectOptionByValue("100");
            $(byId("Question_WoodHeat")).shouldBe(visible, editable).selectOptionByValue("YES");
            $(byId("Question_PelletStove")).shouldBe(visible, editable).selectOptionByValue("YES");

            $(byId("Question_HomeSharing")).shouldBe(visible, editable).selectOptionByValue("NO");

            $(byId("Question_ElectricalType")).shouldBe(visible, editable).selectOptionByValue("Circuit Breakers");
            $(byId("Question_WiringType")).shouldBe(visible, editable).selectOptionByValue("Romex");
            $(byId("Question_WiringYearUpdated")).shouldBe(visible, editable).setValue("2020");

            $(byId("Question_PlumbingType")).shouldBe(visible, editable).selectOptionByValue("PVC");
            $(byId("Question_PlumbingYearUpdated")).shouldBe(visible, editable).setValue("2020");

            $(byId("Question_HeatSourceType")).shouldBe(visible, editable).selectOptionByValue("Electric");
            $(byId("Question_HeatSourceYearUpdated")).shouldBe(visible, editable).setValue("2020");

            $(byId("Question_SwimmingPoolPremises")).shouldBe(visible, editable).selectOptionByValue("Above Ground");
            $(byId("Question_DivingBoard")).shouldBe(visible, editable).selectOptionByValue("NO");
            $(byId("Question_LockableGateFoldingPoolLadder")).shouldBe(visible, editable).selectOptionByValue("YES");

            $(byId("Question_DayCareOnPremises")).shouldBe(visible, editable).selectOptionByValue("YES");

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
                addRiskTabResult.add("Failed");
                addRiskTabResult.add(quoteNumber);
                addRiskTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            addRiskTabResult.add("Failed");
            addRiskTabResult.add("Add Quote - Risk Tab");
            addRiskTabResult.add(result);
        }

      return addRiskTabResult;
    }
}
