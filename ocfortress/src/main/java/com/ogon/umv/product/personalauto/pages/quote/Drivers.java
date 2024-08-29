package com.ogon.umv.product.personalauto.pages.quote;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class Drivers {

    private static final List<String> fillDriversTabResult = new ArrayList<>();

    public static List<String> fillDriversTab(){
        try {
            $(byId("AddDriver")).shouldBe(enabled).click();
            sleep(200);

            $(byId("DriverInfo.RelationshipToInsuredCd")).shouldBe(visible, editable).selectOptionByValue("FirstNamedInsured");
            $(byId("DriverInfo.DriverStatusCd")).shouldBe(visible, editable).selectOptionByValue("Principal");
            $(byId("DriverInfo.VehicleDriven")).selectOption(1);
            $(byId("PersonInfo.GenderCd")).shouldBe(visible, editable).selectOptionByValue("Male");
            $(byId("PersonInfo.MaritalStatusCd")).shouldBe(visible, editable).selectOptionByValue("Married");
            $(byId("DriverInfo.LicenseDt")).shouldBe(visible, editable).setValue("05/17/2000");
            $(byId("DriverInfo.LicenseNumber")).shouldBe(visible, editable).setValue("VT342342");

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
                fillDriversTabResult.add("Failed");
                fillDriversTabResult.add(quoteNumber);
                fillDriversTabResult.add(validationMessage);
            }
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            fillDriversTabResult.add("Failed");
            fillDriversTabResult.add("Add Quote - Drivers Tab");
            fillDriversTabResult.add(result);
        }
        return fillDriversTabResult;
    }
}
