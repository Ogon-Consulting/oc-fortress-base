package com.ogon.umv.product.personalauto.testcases;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class BackdatedPoliciesApprovalRule {

    private List<String> resultList = new ArrayList<>();
    public List<String> triggerApprovalRule(List<TestDataEntity> quoteTestDataFromDB) {
        try {
            resultList = new SelenideSuiteGenerator().executeSelenideSuite(quoteTestDataFromDB);
            if (!resultList.isEmpty()) {
                if($(byId("ApprovalIssues")).exists() && $(byId("ApprovalIssues")).getText().contains("Must Be Approved")){
                    resultList.set(0,"Success");
                    resultList.set(1,$(byId("QuoteAppSummary_QuoteAppNumber")).getText()+". Message: " + resultList.get(1));
                }
                resultList.add($(byId("QuoteAppSummary_QuoteAppNumber")).getText());
                return resultList;
            }
            String policyNumber="";
            if($(byClassName("error_severity_info")).exists() && $(withText("Policy has been created:")).exists()){
                policyNumber = $(byClassName("error_severity_info")).getText();
            }

            resultList.add("Failed");
            resultList.add("Approval Rule Not Triggered for: " + policyNumber);
            resultList.add(policyNumber);
            return resultList;
        }catch (AssertionError ex) {
            Logger.error(ex);
            String result = ex.toString().split("\n")[0];
            resultList.add("Failed");
            resultList.add("Approval Rule Not Triggered for: "+$(byId("QuoteAppSummary_QuoteAppNumber")).getText() +". Reason: "+result);
            resultList.add(result);
            return resultList;
        }catch(Exception ex){
            Logger.error(ex);
            resultList.add("Failed");
            resultList.add("Approval Rule Not Triggered for: "+$(byId("QuoteAppSummary_QuoteAppNumber")).getText() +". Reason: "+ex.getMessage());
            resultList.add(ex.getMessage());
            return resultList;
        }
    }
}
