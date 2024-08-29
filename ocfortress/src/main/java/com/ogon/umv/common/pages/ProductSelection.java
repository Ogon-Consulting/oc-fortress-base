package com.ogon.umv.common.pages;

import com.ogon.common.utility.AppVariables;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;

public class ProductSelection {

    private static List<String>productSelectionResult = new ArrayList<>();
    public static List<String> productSelection(String carrierId, String product, String stateCd){
        try {

            $(byLinkText(AppVariables.productNamesMap.get(carrierId+"_"+stateCd+"_"+product))).click();

            HelperClass.waitForCompletion();

            String validationMessage = "";
            String referenceNumber = "Product Selection";
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
                productSelectionResult.add("Failed");
                productSelectionResult.add(referenceNumber);
                productSelectionResult.add(validationMessage);
            }
        }catch (AssertionError ex){
            String result = ex.toString().split("\n")[0];
            productSelectionResult.add("Failed");
            productSelectionResult.add("Product Selection");
            productSelectionResult.add(result);
        }
        return productSelectionResult;
    }
}
