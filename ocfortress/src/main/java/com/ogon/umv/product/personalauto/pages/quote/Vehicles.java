package com.ogon.umv.product.personalauto.pages.quote;

import com.ogon.common.utility.Buttons;
import com.ogon.umv.common.utility.HelperClass;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class Vehicles {

    private static final List<String> vehiclesTabResult = new ArrayList<>();
    public static List<String> fillVehicleTab(){
        try {

            $(byId("AddAutomobile")).shouldBe(enabled).click();

            sleep(300);

            $(byId("Vehicle.VehIdentificationNumber")).shouldBe(enabled,visible).setValue("1C4HJXDGXLW238312");
            $(byId("Vehicle.VehIdentificationNumber")).pressTab();
            sleep(2000);

            if($(byId("Vehicle.ValidVinInd")).exists() && "No".equalsIgnoreCase($(byId("Vehicle.ValidVinInd")).getValue())){
                $(byId("Vehicle.ModelYr")).shouldBe(enabled,visible).setValue("2020");
                $(byId("Vehicle.Manufacturer")).shouldBe(enabled,visible).setValue("DODGE");
                $(byId("Vehicle.Model")).shouldBe(enabled,visible).setValue("RAM PICKUP 1500 CREW CAB");
                $(byId("Vehicle.AntiTheftCd")).shouldBe(enabled,visible).selectOptionByValue("Passive");
                $(byId("Vehicle.AntiBrakingSystemCd")).shouldBe(enabled,visible).selectOptionByValue("Anti-Lock Brakes Standard");
                $(byId("Vehicle.VehBodyTypeCd")).shouldBe(enabled,visible).setValue("Pickup Truck - Two-Wheel Drive 4-Door");
                $(byId("Vehicle.PerformanceCd")).shouldBe(enabled,visible).setValue("High");
                $(byId("Vehicle.EngineType")).shouldBe(enabled,visible).setValue("Other Type of Engine");
                $(byId("Vehicle.EngineSize")).shouldBe(enabled,visible).setValue("5.7");
                $(byId("Vehicle.EngineCylinders")).shouldBe(enabled,visible).setValue("Eight-Cylinder Engine");
                $(byId("Vehicle.RestraintCd")).shouldBe(enabled,visible).setValue("DualAirbags");
                $(byId("Vehicle.EngineHorsePower")).shouldBe(enabled,visible).setValue("390");
                $(byId("Vehicle.MedicalPaymentsSymbol")).shouldBe(enabled,visible).setValue("Unknown");
                $(byId("Vehicle.LiabilitySymbol")).shouldBe(enabled,visible).setValue("Unknown");
                $(byId("Vehicle.ComprehensiveRatingValue")).shouldBe(enabled,visible).setValue("23");
                $(byId("Vehicle.CollisionRatingValue")).shouldBe(enabled,visible).setValue("18");
                $(byId("Vehicle.DaytimeRunningLights")).shouldBe(enabled,visible).setValue("Daytime Running Lights Optional Equipment");
            }
            $(byId("Vehicle.CostNewAmt")).shouldBe(enabled,visible).setValue("50000");
            $(byId("Vehicle.VehUseCd")).shouldBe(enabled,visible).selectOptionByValue("Pleasure");
            $(byId("Vehicle.OdometerReading")).shouldBe(enabled,visible).setValue("10000");
            $(byId("Vehicle.EstimatedAnnualDistance")).shouldBe(enabled,visible).setValue("15000");

            if($(byId("Question_VehicleSpecialEquipment")).exists() && $(byId("Question_VehicleSpecialEquipment")).isEnabled()){
                $(byId("Question_VehicleSpecialEquipment")).shouldBe(visible).selectOptionByValue("NO");
            }

            $(byId(Buttons.SAVE)).shouldBe(enabled).click();
            HelperClass.waitForCompletion();

            String validationMessage = "";
            String quoteNumber = $(byId("QuoteAppSummary_QuoteAppNumber")).getText();
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
                vehiclesTabResult.add("Failed");
                vehiclesTabResult.add(quoteNumber);
                vehiclesTabResult.add(validationMessage);
            }
        }catch(AssertionError ex){
            String result = ex.toString().split("\n")[0];
            vehiclesTabResult.add("Failed");
            vehiclesTabResult.add("Add Quote - Vehicles Tab");
            vehiclesTabResult.add(result);
        }
        return vehiclesTabResult;
    }
}
