package com.ogon.umv.product.homeowners.pages.quote;

import com.ogon.common.utility.Buttons;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class InlandMarine {

    private static List<String> inlandMarineTabResult = new ArrayList<>();
    public static List<String> fillInlandMarineTab(){
        try {
            $(byId(Buttons.SAVE)).shouldBe(enabled).click();

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
                inlandMarineTabResult.add("Failed");
                inlandMarineTabResult.add(quoteNumber);
                inlandMarineTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            inlandMarineTabResult.add("Failed");
            inlandMarineTabResult.add("Add Quote - InlandMarine Tab");
            inlandMarineTabResult.add(result);
        }

        return inlandMarineTabResult;
    }
}
