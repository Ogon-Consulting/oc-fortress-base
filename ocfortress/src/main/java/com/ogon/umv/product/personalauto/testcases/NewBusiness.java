package com.ogon.umv.product.personalauto.testcases;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class NewBusiness {

    private List<String> newBusinessResult = new ArrayList<>();
    public List<String> createNewBusiness(List<TestDataEntity> quoteTestDataFromDB) {
        String policyNumber="";
        try {
            newBusinessResult = new SelenideSuiteGenerator().executeSelenideSuite(quoteTestDataFromDB);
            if (!newBusinessResult.isEmpty()) {
                newBusinessResult.add($(byId("QuoteAppSummary_QuoteAppNumber")).getText());
                return newBusinessResult;
            }

            if($(byClassName("error_severity_info")).exists() && $(withText("Policy has been created:")).exists()){
                policyNumber = $(byClassName("error_severity_info")).getText();
            }

            newBusinessResult.add("Success");
            newBusinessResult.add(policyNumber);
            newBusinessResult.add(policyNumber);
            return newBusinessResult;
        }catch (AssertionError ex) {
            Logger.error(ex);
            String result = ex.toString().split("\n")[0];
            newBusinessResult.add("Failed");
            newBusinessResult.add("New Business Failed for "+policyNumber+". Reason: "+result);
            newBusinessResult.add(result);
            return newBusinessResult;
        }catch(Exception ex){
            Logger.error(ex);
            newBusinessResult.add("Failed");
            newBusinessResult.add("New Business Failed for "+policyNumber+". Reason: "+ex.getMessage());
            newBusinessResult.add(ex.getMessage());
            return newBusinessResult;
        }
    }
}
