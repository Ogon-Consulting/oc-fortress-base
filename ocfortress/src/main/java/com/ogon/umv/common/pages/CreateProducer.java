package com.ogon.umv.common.pages;

import com.ogon.controller.SelenideSuiteGenerator;
import com.ogon.entity.TestDataEntity;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

public class CreateProducer {

    private List<String> producerResult;

    public List<String> addAgencyAndProducer(List<TestDataEntity> testDataFromDB) {
        try {
            producerResult = new ArrayList<>();

            producerResult = new SelenideSuiteGenerator().executeSelenideSuite(testDataFromDB);
            String producerCd = $(byId("ProducerSummary_CommercialName")).getText();
            if (!producerResult.isEmpty()) {
                if (!producerResult.get(1).isEmpty()) {
                    producerResult.set(1, producerCd + " - " + producerResult.get(1));
                }
                producerResult.add(producerCd);
                if ($(byId("DiscardChanges")).exists()) {
                    $(byId("DiscardChanges")).click();
                    HelperClass.waitBySleep(500);
                    if ($(byId("DiscardPage")).exists()) {
                        $(byId("DiscardPage")).click();
                        HelperClass.waitBySleep(500);
                    }
                }
                return producerResult;
            }
            producerResult.add("Success");
            producerResult.add("Producer Added: " + producerCd);
            producerResult.add(producerCd);
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            producerResult.add("Failed");
            producerResult.add("Add Producer");
            producerResult.add(result);
        }
        return producerResult;
    }
}
