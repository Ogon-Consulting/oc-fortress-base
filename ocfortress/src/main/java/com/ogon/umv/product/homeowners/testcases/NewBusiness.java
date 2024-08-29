package com.ogon.umv.product.homeowners.testcases;

import com.ogon.umv.common.utility.HelperClass;
import com.ogon.umv.product.homeowners.pages.application.Application;
import com.ogon.umv.product.homeowners.pages.application.CloseOut;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class NewBusiness {

    private static List<String> newBusinessResult = new ArrayList<>();
    public static List<String> executeNewBusinessTestCase() {
        try {
            String applicationNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
            newBusinessResult = Application.processApplication();
            if (newBusinessResult.size() != 0) {
                return newBusinessResult;
            }

            newBusinessResult = CloseOut.process();
            if (newBusinessResult.size() != 0) {
                return newBusinessResult;
            }

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
                validationMessage = $(byId("GenericBusinessError")).getText();
            }
            if (!validationMessage.isEmpty()&& !(validationMessage.contains("Policy has been created:"))) {
                newBusinessResult.add("Failed");
                newBusinessResult.add(applicationNumber);
                newBusinessResult.add(validationMessage);
                return newBusinessResult;
            }
            if(!$(byId("PolicySummary_PolicyNumber")).exists()){
                HelperClass.waitForCompletion();
            }
            if(!$(byId("PolicySummary_PolicyNumber")).exists()){
                HelperClass.waitForCompletion();
            }
            String policyNumber = $(byId("PolicySummary_PolicyNumber")).getText();
            newBusinessResult.add("Success");
            newBusinessResult.add(policyNumber);
            newBusinessResult.add("New Business Policy Created");
            return newBusinessResult;
        }catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            newBusinessResult.add("Failed");
            newBusinessResult.add("New Business");
            newBusinessResult.add(result);
            return newBusinessResult;
        }
    }
}
