package com.ogon.umv.product.homeowners.testcases;

import com.ogon.umv.common.pages.ProductSelection;
import com.ogon.common.utility.Buttons;
import com.ogon.umv.product.homeowners.pages.quote.*;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class AddQuote {

    private static List<String> addQuoteResult = new ArrayList<>();
    public static List<String> addNewQuoteFromExcel(String carrierId, String product, String stateCd, String effectDt, String providerNumber, String firstName, String lastName){

        try {
            addQuoteResult.clear();
            $(byId("Menu_Workflow")).shouldBe(enabled).click();
            $(byId("QuickAction_NewQuote_Holder")).shouldBe(enabled).click();
            $(byId("QuickAction_EffectiveDt")).shouldBe(enabled, visible).setValue(effectDt);
            $(byId("QuickAction_StateCd")).shouldBe(enabled, visible).selectOptionByValue(stateCd);
            $(byId("QuickAction_CarrierGroupCd")).shouldBe(enabled, visible).selectOptionByValue(carrierId);

            $(byId("QuickAction_NewQuote")).shouldBe(enabled,visible).click();

            addQuoteResult = ProductSelection.productSelection(carrierId, product, stateCd);
            if (!addQuoteResult.isEmpty()) {
                return addQuoteResult;
            }

            addQuoteResult = Policy.fillPolicyTab(providerNumber, firstName, lastName);
            $(byId(Buttons.NEXTPAGE)).shouldBe(enabled).click();

            addQuoteResult = Risks.fillRisksTab();
            $(byId(Buttons.NEXTPAGE)).shouldBe(enabled).click();

            addQuoteResult = LossHistory.fillLossHistoryTab();
            $(byId(Buttons.NEXTPAGE)).shouldBe(enabled).click();

            validateError();
            if (addQuoteResult.size() != 0) {
                return addQuoteResult;
            }
            String quoteNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
            addQuoteResult.add("Success");
            addQuoteResult.add(quoteNumber);
            addQuoteResult.add("Quote Created");
            return addQuoteResult;
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            addQuoteResult.add("Failed");
            addQuoteResult.add("Add Quote");
            addQuoteResult.add(result);
            return addQuoteResult;
        }
    }

    public static List<String> addNewQuote(String carrierId, String product, String stateCd, String effectDt, String providerNumber, String firstName, String lastName){

        try {
            addQuoteResult.clear();
            $(byId("Menu_Workflow")).shouldBe(enabled).click();
            $(byId("QuickAction_NewQuote_Holder")).shouldBe(enabled).click();
            $(byId("QuickAction_EffectiveDt")).shouldBe(enabled, visible).setValue(effectDt);
            $(byId("QuickAction_StateCd")).shouldBe(enabled, visible).selectOptionByValue(stateCd);
            $(byId("QuickAction_CarrierGroupCd")).shouldBe(enabled, visible).selectOptionByValue(carrierId);

            $(byId("QuickAction_NewQuote")).shouldBe(enabled,visible).click();

            addQuoteResult = ProductSelection.productSelection(carrierId, product, stateCd);
            if (!addQuoteResult.isEmpty()) {
                return addQuoteResult;
            }

            addQuoteResult = Policy.fillPolicyTab(providerNumber, firstName, lastName);
            $(byId(Buttons.NEXTPAGE)).shouldBe(enabled).click();

            addQuoteResult = Risks.fillRisksTab();
            $(byId(Buttons.NEXTPAGE)).shouldBe(enabled).click();

            addQuoteResult = LossHistory.fillLossHistoryTab();
            $(byId(Buttons.NEXTPAGE)).shouldBe(enabled).click();

            validateError();
            if (addQuoteResult.size() != 0) {
                return addQuoteResult;
            }
            String quoteNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
            addQuoteResult.add("Success");
            addQuoteResult.add(quoteNumber);
            addQuoteResult.add("Quote Created");
            return addQuoteResult;
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            addQuoteResult.add("Failed");
            addQuoteResult.add("Add Quote");
            addQuoteResult.add(result);
            return addQuoteResult;
        }
    }

    private static void validateError() {
        try {
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
            if(!validationMessage.isEmpty()) {
                addQuoteResult.add("Failed");
                addQuoteResult.add(quoteNumber);
                addQuoteResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            addQuoteResult.add("Failed");
            addQuoteResult.add("Add Producer");
            addQuoteResult.add(result);
        }
    }
}
