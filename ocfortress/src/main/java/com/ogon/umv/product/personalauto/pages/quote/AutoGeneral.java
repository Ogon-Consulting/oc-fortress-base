package com.ogon.umv.product.personalauto.pages.quote;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class AutoGeneral {

    private static final List<String> autoGeneralTabResult = new ArrayList<>();

    public static List<String> fillAutoGeneralTab(){

        // Liability/Medical
        try {
            $(byId("Line.CoverageType")).shouldBe(visible, editable).selectOptionByValue("CombinedLimits");
            $(byId("Line.BICLLimit")).shouldBe(visible, editable).selectOptionByValue("300000");
            $(byId("Line.MedPayLimit")).shouldBe(visible, editable).selectOptionByValue("2000");
            $(byId("Line.UMCLLimit")).shouldBe(visible, editable).selectOptionByValue("300000");
            $(byId("Line.HasFullPayCredit")).shouldBe(visible, editable).selectOptionByValue("Yes");

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
                autoGeneralTabResult.add("Failed");
                autoGeneralTabResult.add(quoteNumber);
                autoGeneralTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            autoGeneralTabResult.add("Failed");
            autoGeneralTabResult.add("Add Quote - Auto General Tab");
            autoGeneralTabResult.add(result);
        }

        return autoGeneralTabResult;
    }
}
