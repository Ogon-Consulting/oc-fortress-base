package com.ogon.umv.product.personalauto.pages.quote;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPlan {

    private static final List<String> fillPayPlanResult = new ArrayList<>();
    public static List<String> fillPaymentPlanTab(){
        try {

            $(byId("BasicPolicy.PayPlanCd_1")).shouldBe(enabled).click();

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
                fillPayPlanResult.add("Failed");
                fillPayPlanResult.add(quoteNumber);
                fillPayPlanResult.add(validationMessage);
            }
        }catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            fillPayPlanResult.add("Failed");
            fillPayPlanResult.add("Add Quote - Select Pay Plan");
            fillPayPlanResult.add(result);
        }
        return fillPayPlanResult;
    }
}
