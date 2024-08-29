package com.ogon.umv.product.personalauto.testcases;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;
import io.qameta.allure.Step;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class EndorsementAddRisk {

    private List<String> endorsementResultList = new ArrayList<>();

    @Step("Create endorsements")
    public List<String> addRiskEndorsement(List<TestDataEntity> testDataFromDB) {
        String policyNumber = "";
        try {
            endorsementResultList = new SelenideSuiteGenerator().executeSelenideSuite(testDataFromDB);
            if (!endorsementResultList.isEmpty()) {
                if ($(byId("PolicySummary_PolicyNumber")).exists()) {
                    policyNumber = $(byId("PolicySummary_PolicyNumber")).getText();
                    endorsementResultList.add(policyNumber);
                } else if ($(byId("QuoteAppSummary_QuoteAppNumber")).exists()) {
                    policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
                    if (policyNumber.isEmpty()) {
                        policyNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getOwnText();
                    }
                    if (!policyNumber.isEmpty()) {
                        endorsementResultList.add(policyNumber);
                    }
                }
                return endorsementResultList;
            }

            String result = "";
            if ($(byClassName("error_severity_info")).exists() && $(withText("Endorsement has been processed for")).exists()) {
                result = $(byClassName("error_severity_info")).getText();
            }
            endorsementResultList.add("Success");
            endorsementResultList.add(result);
            endorsementResultList.add(result);
            return endorsementResultList;
        } catch (AssertionError ex) {
            Logger.error(ex);
            String result = ex.toString().split("\n")[0];
            endorsementResultList.add("Failed");
            endorsementResultList.add("Endorsement - Add Risk Failed for " + policyNumber + ". Reason: " + result);
            endorsementResultList.add(result);
            return endorsementResultList;
        } catch (Exception ex) {
            Logger.error(ex);
            endorsementResultList.add("Failed");
            endorsementResultList.add("Endorsement - Add Risk Failed for " + policyNumber + ". Reason: " + ex.getMessage());
            endorsementResultList.add(ex.getMessage());
            return endorsementResultList;
        }
    }
}
