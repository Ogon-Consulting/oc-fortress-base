package com.ogon.umv.product.personalauto.testcases;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class AddQuote {
    private List<String> addQuoteResult = new ArrayList<>();
    public List<String> addNewQuote(List<TestDataEntity> quoteTestDataFromDB){
        String quoteNumber = "";
        try {
            addQuoteResult.clear();
            addQuoteResult = new SelenideSuiteGenerator().executeSelenideSuite(quoteTestDataFromDB);
            quoteNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
            if (!addQuoteResult.isEmpty()) {
                addQuoteResult.add(quoteNumber);
                return addQuoteResult;
            }
            String message;
            if($(byId("ErrorIssues")).exists() && !$(byId("ErrorIssues")).getText().isEmpty()){
                message = "Quote Created with Error(s) " + $(byId("ErrorIssues")).getText();
            }else{
                message = "Quote has been created: "+quoteNumber;
            }
            addQuoteResult.add("Success");
            addQuoteResult.add(message);
            addQuoteResult.add(quoteNumber);
            return addQuoteResult;
        }catch (AssertionError ex){
            Logger.error(ex);
            String result = ex.toString().split("\n")[0];
            addQuoteResult.add("Failed");
            addQuoteResult.add("Add Quote Failed for "+quoteNumber+". Reason: " + result);
            addQuoteResult.add("Add Quote Failed for "+quoteNumber+". Reason: " + result);
            return addQuoteResult;
        }catch(Exception ex){
            Logger.error(ex);
            addQuoteResult.add("Failed");
            addQuoteResult.add("Add Quote Failed for "+quoteNumber+". Reason: " +ex.getMessage());
            addQuoteResult.add(ex.getMessage());
            return addQuoteResult;
        }
    }
}
