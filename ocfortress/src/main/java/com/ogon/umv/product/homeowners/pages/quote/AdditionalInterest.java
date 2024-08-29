package com.ogon.umv.product.homeowners.pages.quote;

import com.ogon.common.utility.Buttons;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class AdditionalInterest {

    private static List<String> fillAIResult = new ArrayList<>();
    public static List<String> fillAdditionalInterestTab(){
        try{
            $(byId(Buttons.SAVE)).shouldBe(enabled).click();

            //$(byId("AddAdditionalInterest")).shouldBe(enabled).click();
            /*$(By.name("AI.InterestTypeCd")).selectOptionByValue("DML-41 Additional Insured");
            $(By.name("AI.ChargeTypeCd")).selectRadio("NoCharge");

            $(By.name("AI.InterestName")).setValue("Henry");
            $(By.name("AI.Interest")).setValue("Henry Richard");

            $(By.name("AIMailingAddr.PrimaryNumber")).setValue("2000");
            $(By.name("AIMailingAddr.StreetName")).setValue("Berry st");
            $(By.name("AIMailingAddr.City")).setValue("San Jose");
            $(By.name("AIMailingAddr.StateProvCd")).selectOptionByValue("NY");
            $(By.name("AIMailingAddr.PostalCode")).setValue("95136");*/

            sleep(1000);

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
                fillAIResult.add("Failed");
                fillAIResult.add(quoteNumber);
                fillAIResult.add(validationMessage);
            }
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            fillAIResult.add("Failed");
            fillAIResult.add("Add Quote - Additional Interest");
            fillAIResult.add(result);
        }
        return fillAIResult;
    }
}
