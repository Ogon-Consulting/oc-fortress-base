package com.ogon.umv.common.pages;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.editable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;

public class SearchCustomer {

    private static final List<String> customerSearchResult = new ArrayList<>();

    public static List<String> searchCustomer(String searchName) {
      try {

        $(byId("Menu_Policy")).click();
        $(byId("Menu_Policy_PolicyProcessing")).click();
        $(byId("CustomerSearchText")).shouldBe(visible, editable).setValue(searchName);

        $(byId(Buttons.SEARCH)).click();

        HelperClass.waitForCompletion();

        validateError();
        if (!customerSearchResult.isEmpty()) {
          return customerSearchResult;
        }

        if($(byLinkText(searchName)).exists()){
          customerSearchResult.add("Found");
          customerSearchResult.add(searchName);
          customerSearchResult.add("Customer Exists");
        }else{
          customerSearchResult.add("Not Found");
          customerSearchResult.add(searchName);
          customerSearchResult.add("Customer Does Exists");
        }
      }catch (AssertionError ex) {
        String result = ex.toString().split("\n")[0];
        customerSearchResult.add("Failed");
        customerSearchResult.add("Customer Search");
        customerSearchResult.add(result);
      }
        return customerSearchResult;
    }

  private static void validateError() {
      try {
        String validationMessage = "";
        if ($(byId("Issues")).exists() && $(byId("ErrorIssues")).exists() && !($(byId("ErrorIssues")).getText().isEmpty())) {
          validationMessage = $(byId("ErrorIssues")).getText().replaceAll("'","");
        } else if ($(byId("Issues")).exists() && $(byId("ApprovalIssues")).exists() && !($(byId("ApprovalIssues")).getText().isEmpty())) {
          validationMessage = $(byId("ApprovalIssues")).getText().replaceAll("'","");
        } else if (($(byId("MissingFieldError")).exists() && !$(byId("MissingFieldError")).getText().isEmpty())) {
          validationMessage = $(byId("MissingFieldError")).getText().replaceAll("'","");
        } else if ($(byId("FieldConstraintError")).exists() && !$(byId("FieldConstraintError")).getText().isEmpty()) {
          validationMessage = $(byId("FieldConstraintError")).getText().replaceAll("'","");
        } else if ($(byId("ServiceError")).exists() && !$(byId("ServiceError")).getText().isEmpty()) {
          validationMessage = $(byId("ServiceError")).getText().replaceAll("'","");
        } else if ($(byId("GenericBusinessError")).exists() && !$(byId("GenericBusinessError")).getText().isEmpty()) {
          validationMessage = $(byId("GenericBusinessError")).getText().replaceAll("'","");
        }
        if(!validationMessage.isEmpty()) {
          customerSearchResult.add("Failed");
          customerSearchResult.add("Search Customer");
          customerSearchResult.add(validationMessage);
        }
      }catch(AssertionError ex){
        String result = ex.toString().split("\n")[0];
        customerSearchResult.add("Failed");
        customerSearchResult.add("Search Customer");
        customerSearchResult.add(result);
      }
    }
}
