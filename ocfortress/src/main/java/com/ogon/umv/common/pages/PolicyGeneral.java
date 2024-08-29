package com.ogon.umv.common.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class PolicyGeneral {

    public static boolean addPolicyGeneral(String product, String providerNumber){
        if($(byName("RewriteToProductVersionIdRef")).exists() && $(byName("RewriteToProductVersionIdRef")).isDisplayed()){
            $(byName("RewriteToProductVersionIdRef")).shouldBe(visible).selectOptionByValue(product);
        }
        $(byName("BasicPolicy.SubTypeCd"))
                .shouldBe(visible)
                .shouldBe(editable)
                .selectOptionByValue(product);
        Duration.ofMillis(3000);
        $(byName(providerNumber)).setValue(providerNumber);

        if($(byName("BasicPolicy.NoLapse")).exists() && $(byName("BasicPolicy.NoLapse")).isDisplayed()){
            $(byName("BasicPolicy.NoLapse")).setValue("Yes");
        }
        if($(byName("BasicPolicy.FCRARegulatedInd")).exists() && $(byName("BasicPolicy.FCRARegulatedInd")).isDisplayed()) {
            $(byName("BasicPolicy.FCRARegulatedInd")).setValue("Yes");
        }

        return true;
    }
}
