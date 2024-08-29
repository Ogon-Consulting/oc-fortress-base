package com.ogon.umv.product.homeowners.pages.application;

import com.ogon.common.utility.Buttons;
import com.ogon.config.ConfigProperties;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.umv.common.utility.HelperClass;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class CloseOut {

    private static final List<String> closeOutprocessResult = new ArrayList<>();

    public static List<String> process() {

        try {
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);

            String applicationNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
            $(byId("TransactionInfo.PaymentTypeCd")).shouldBe(visible, editable).selectOptionByValue("None");

            /*
            Below block is need only when down payment is must. Skipping this to feature makepayment TC,
             as in UMV buildout once payment is done and no due to pay, make payment options are not listed

            if ($(byId("PremiumSource.ACHStandardEntryClassCd")).exists()) {
                $(byId("PremiumSource.ACHStandardEntryClassCd")).shouldBe(visible, editable).selectOptionByValue("WEB");
            }

            if ($(byId("PremiumSource.ACHBankAccountTypeCd")).exists()) {
                $(byId("PremiumSource.ACHBankAccountTypeCd")).shouldBe(visible, editable).selectOptionByValue("Checking");
            }

            if ($(byId("PremiumSource.ACHBankName")).exists()) {
                $(byId("PremiumSource.ACHBankName")).shouldBe(visible, editable).setValue("Bank Of America");
            }

            if ($(byId("PremiumSource.ACHBankAccountNumber")).exists()) {
                $(byId("PremiumSource.ACHBankAccountNumber")).shouldBe(visible, editable).setValue("753159963");
            }

            if ($(byId("PremiumSource.ACHRoutingNumber")).exists()) {
                $(byId("PremiumSource.ACHRoutingNumber")).shouldBe(visible, editable).setValue(configProperties.getRoutingNumber());
            }

            if ($(byId("ByTelephoneInd")).exists()) {
                $(byId("ByTelephoneInd")).shouldBe(visible, editable).selectOptionByValue("NO");
            }

            if ($(byId("PremiumSource.ACHName")).exists() && $(byId("PremiumSource.ACHName")).getText().isEmpty()) {
                if ($(byId("GoToCustomer")).exists()) {
                    $(byId("PremiumSource.ACHName")).shouldBe(visible, editable).setValue($(byId("GoToCustomer")).getText());
                } else {
                    $(byId("PremiumSource.ACHName")).shouldBe(visible, editable).setValue("Insured");
                }
            }*/

            $(byId(Buttons.PROCESS)).shouldBe(enabled).click();
            HelperClass.waitForCompletion();
            validateError(applicationNumber);
            if (!closeOutprocessResult.isEmpty()) {
                return closeOutprocessResult;
            }

            return closeOutprocessResult;
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            closeOutprocessResult.add("Failed");
            closeOutprocessResult.add("Create Application");
            closeOutprocessResult.add(result);
            return closeOutprocessResult;
        }

    }

    private static void validateError(String applicationNumber) {
        try {
            String validationMessage = "";
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
                if(!$(byId("GenericBusinessError")).getText().contains("Policy has been created")) {
                    validationMessage = $(byId("GenericBusinessError")).getText();
                }
            }
            if (!validationMessage.isEmpty()) {
                closeOutprocessResult.add("Failed");
                closeOutprocessResult.add(applicationNumber);
                closeOutprocessResult.add(validationMessage);
            }
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            closeOutprocessResult.add("Failed");
            closeOutprocessResult.add("Create Application - Closeout Process");
            closeOutprocessResult.add(result);
        }
    }
}
