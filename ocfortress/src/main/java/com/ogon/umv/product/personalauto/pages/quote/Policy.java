package com.ogon.umv.product.personalauto.pages.quote;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class Policy {

    private static final List<String> policyTabResult = new ArrayList<>();

    public static List<String> fillPolicyTab(String producerCd, String firstName, String lastName){
        try {

            $(byId("ProviderNumber")).shouldBe(visible, editable).setValue(producerCd);
            HelperClass.waitForCompletion();

            $(byId("BasicPolicy.PreviousCarrierCd")).shouldBe(visible, editable).selectOptionByValue("No Prior");
            $(byId("BasicPolicy.TransisitionBookRollInd")).shouldBe(visible, editable).selectOptionByValue("Yes");
            sleep(100);
            if($(byId("BasicPolicy.TransisitionBookRollExpPrem")).exists() && $(byId("BasicPolicy.TransisitionBookRollExpPrem")).is(editable)) {
                $(byId("BasicPolicy.TransisitionBookRollExpPrem")).setValue("1000");
            }

            // Insurance Score
            $(byId("InsuredInsuranceScore.InsuranceScoreCd")).shouldBe(visible, editable).selectOptionByValue("A");

            // Insured Information
            // Individual Info
            $(byId("Insured.EntityTypeCd")).shouldBe(visible, editable).selectOptionByValue("Individual");
            sleep(300);
            $(byId("InsuredName.GivenName")).shouldBe(visible, editable).setValue(firstName);
            $(byId("InsuredName.Surname")).shouldBe(visible, editable).setValue(lastName);
            $(byId("ResetCommercialName")).click();
            $(byId("InsuredPersonal.BirthDt")).shouldBe(visible, editable).setValue("01/01/1980");
            // Address Info
            $(byId("InsuredMailingAddr.Addr1")).shouldBe(visible, editable).setValue("83 Camp Road");
            $(byId("InsuredMailingAddr.City")).shouldBe(visible, editable).setValue("Franklin");
            $(byId("InsuredMailingAddr.StateProvCd")).shouldBe(visible, editable).selectOptionByValue("VT");
            $(byId("InsuredMailingAddr.PostalCode")).shouldBe(visible, editable).setValue("05457");
            if($(byId("InsuredMailingAddr.addrVerifyImg")).exists() && $(byId("InsuredMailingAddr.addrVerifyImg")).isDisplayed()) {
                $(byId("InsuredMailingAddr.addrVerifyImg")).click();
                HelperClass.waitForAddressVerification("InsuredMailingAddr.verifyStatusImg");
            }

            if ($(byId("ui-id-7")).isDisplayed()) {
                $(byId("dialogOK")).click();
            }

            $(byId("CopyAddress")).shouldBe(visible, enabled).click();
            if($(byId("InsuredBillingAddr.addrVerifyImg")).exists() && $(byId("InsuredBillingAddr.addrVerifyImg")).isDisplayed()) {
                $(byId("InsuredBillingAddr.addrVerifyImg")).click();
                HelperClass.waitForAddressVerification("InsuredBillingAddr.verifyStatusImg");
            }

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
                policyTabResult.add("Failed");
                policyTabResult.add(quoteNumber);
                policyTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            policyTabResult.add("Failed");
            policyTabResult.add("Add Quote - Policy Tab");
            policyTabResult.add(result);
        }
        return policyTabResult;
    }
}