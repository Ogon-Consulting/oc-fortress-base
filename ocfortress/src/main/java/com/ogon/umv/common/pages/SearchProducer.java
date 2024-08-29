package com.ogon.umv.common.pages;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.editable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class SearchProducer {

    private static final List<String> producerSearchResult = new ArrayList<>();

    public static List<String> searchProducer(String producerCd) {
      try {
        $(byId("Menu_Policy")).click();
        $(byId("Menu_Policy_UnderwritingMaintenance")).click();

        // Search Producer
        $(byLinkText("Producer")).click();
        //$(byId("SearchFor")).shouldBe(visible, editable).selectOptionByValue("Producer");
        $(byId("SearchBy")).shouldBe(visible, editable).selectOptionByValue("ProviderNumber");
        $(byId("SearchText")).shouldBe(visible, editable).setValue(producerCd);

        $(byId("Search")).click();
        sleep(300);

        validateError();
        if (!producerSearchResult.isEmpty()) {
          return producerSearchResult;
        }

        if($(byLinkText(producerCd)).exists()){
          producerSearchResult.add("Found");
          producerSearchResult.add(producerCd);
          producerSearchResult.add("Producer Exists");
        }else{
          producerSearchResult.add("Not Found");
          producerSearchResult.add(producerCd);
          producerSearchResult.add("Producer Does Exists");
        }
      }catch (AssertionError ex) {
        String result = ex.toString().split("\n")[0];
        producerSearchResult.add("Failed");
        producerSearchResult.add("Producer Search");
        producerSearchResult.add(result);
      }
        return producerSearchResult;
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
          producerSearchResult.add("Failed");
          producerSearchResult.add("Search Producer");
          producerSearchResult.add(validationMessage);
        }
      }catch(AssertionError ex){
        String result = ex.toString().split("\n")[0];
        producerSearchResult.add("Failed");
        producerSearchResult.add("Search Producer");
        producerSearchResult.add(result);
      }
    }
}
