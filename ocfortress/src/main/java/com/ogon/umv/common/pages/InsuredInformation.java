package com.ogon.umv.common.pages;

import com.ogon.common.utility.Buttons;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class InsuredInformation {

    public static boolean addInsuredInformation(String product, String providerNumber){
        if($(byName("Insured.EntityTypeCd")).exists()){
            $(byName("Insured.EntityTypeCd")).shouldBe(visible).selectOptionByValue("Individual");
        }
        $(byName(providerNumber)).setValue(providerNumber);
        $(byName("BasicPolicy.NoLapse")).setValue("Yes");
        $(byName("BasicPolicy.FCRARegulatedInd")).setValue("Yes");

        $(byId(Buttons.NEXTPAGE)).shouldBe(enabled).click();
        return true;
    }
}
