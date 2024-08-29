package com.ogon.controller;

import com.codeborne.selenide.SelenideElement;
import com.ogon.entity.TestDataEntity;
import com.ogon.umv.common.utility.HelperClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class SelenideSuiteGenerator {

    public List<String> executeSelenideSuite(List<TestDataEntity> testDataFromDB) {
        List<String> executionResult = new ArrayList<>();
        try {
            for (TestDataEntity entity : testDataFromDB) {
                String fieldId = entity.getFieldId();
                String fieldType = entity.getFieldType();
                String defaultValue = (entity.getDefaultValue() == null || entity.getDefaultValue().isEmpty()) ? "" : entity.getDefaultValue();
                if ("Current_Date".equalsIgnoreCase(defaultValue)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    defaultValue = sdf.format(new Date());
                } else if ("CarrierCd_Selected".equalsIgnoreCase(defaultValue)) {
                    defaultValue = entity.getCarrierId();
                } else if ("StateCd_Selected".equalsIgnoreCase(defaultValue)) {
                    defaultValue = entity.getStateCd();
                } else if ("ProductCd_Selected".equalsIgnoreCase(defaultValue)) {
                    defaultValue = entity.getLob();
                }
                boolean isExists = false;
                boolean isDisplayed = false;
                boolean isEditable = false;
                boolean isLinkedExists = false;
                boolean isWithTextExists = false;
                boolean isEnabled = false;
                boolean shouldBeEmpty = false;
                boolean isSleepNeeded = entity.getIsSleepNeeded() != null && !entity.getIsSleepNeeded().isEmpty() && ("Yes".equalsIgnoreCase(entity.getIsSleepNeeded()));
                int sleepTime = (entity.getSleepTime() == null || entity.getSleepTime().isEmpty()) ? 0 : Integer.parseInt(entity.getSleepTime());
                boolean shouldWaitForCompletion = entity.getShouldWaitForCompletion() != null && !entity.getShouldWaitForCompletion().isEmpty() && ("Yes".equalsIgnoreCase(entity.getShouldWaitForCompletion()));
                boolean shouldWaitForAddressValidation = entity.getShouldWaitForAddrVerification() != null && !entity.getShouldWaitForAddrVerification().isEmpty() && ("Yes".equalsIgnoreCase(entity.getShouldWaitForAddrVerification()));
                String addressVerificationImgField = (entity.getAddrVerificationImgField() == null || entity.getAddrVerificationImgField().isEmpty()) ? "" : entity.getAddrVerificationImgField();
                boolean shouldPressTab = entity.getShouldPressTab() != null && !entity.getShouldPressTab().isEmpty() && ("Yes".equalsIgnoreCase(entity.getShouldPressTab()));
                String linkedFieldId = (entity.getLinkedFieldId() == null || entity.getLinkedFieldId().isEmpty()) ? "" : entity.getLinkedFieldId();

                String validations = entity.getValidations() == null ? "" : entity.getValidations();
                boolean validationsAvailable = false;
                String[] validationArray = new String[]{""};
                if (!validations.isEmpty()) {
                    validationsAvailable = true;
                    validationArray = validations.split(",");
                }
                if (validationsAvailable) {
                    for (String validation : validationArray) {
                        if ("exists".equalsIgnoreCase(validation)) {
                            isExists = true;
                        } else if ("displayed".equalsIgnoreCase(validation.replaceAll(" ", ""))) {
                            isDisplayed = true;
                        } else if ("editable".equalsIgnoreCase(validation.replaceAll(" ", ""))) {
                            isEditable = true;
                        } else if ("linkedexists".equalsIgnoreCase(validation.replaceAll(" ", ""))) {
                            isLinkedExists = true;
                        } else if ("withtextexists".equalsIgnoreCase(validation.replaceAll(" ", ""))) {
                            isWithTextExists = true;
                        } else if ("enabled".equalsIgnoreCase(validation.replaceAll(" ", ""))) {
                            isEnabled = true;
                        } else if ("empty".equalsIgnoreCase(validation.replaceAll(" ", ""))) {
                            shouldBeEmpty = true;
                        }
                    }
                }
                if (!validationsAvailable || (isLinkedExists && $(byId(linkedFieldId)).exists())
                        || (isWithTextExists && $(withText(linkedFieldId)).exists())
                        || (isExists && $(byId(fieldId)).exists())
                        && ((!shouldBeEmpty || (Objects.requireNonNull($(byId(fieldId)).getValue()).isEmpty()))
                        && (!isDisplayed || ($(byId(fieldId)).isDisplayed()))
                        && (!isEditable || ($(byId(fieldId)).is(editable)))
                        && (!isEnabled || ($(byId(fieldId)).is(enabled))))) {
                    if ("Select".equalsIgnoreCase(fieldType)) {
                        $(byId(fieldId)).shouldBe(visible, editable).selectOptionByValue(defaultValue);
                    } else if ("SelectOptionContainingText".equalsIgnoreCase(fieldType)) {
                        $(byId(fieldId)).shouldBe(visible, editable).selectOptionContainingText(defaultValue);
                    } else if ("Text".equalsIgnoreCase(fieldType)) {
                        $(byId(fieldId)).shouldBe(visible, editable).setValue(defaultValue);
                    } else if ("Link".equalsIgnoreCase(fieldType)) {
                        $(byId(fieldId)).shouldBe(visible, enabled).click();
                    } else if ("LinkText".equalsIgnoreCase(fieldType)) {
                        $(byLinkText(fieldId)).click();
                    } else if ("Linkbyvalue".equalsIgnoreCase(fieldType)) {
                        $(byValue(defaultValue)).click();
                    } else if ("CSSSelectorclick".equalsIgnoreCase(fieldType)) {
                        SelenideElement element = $(fieldId);
                        element.click();
                    } else if ("TextWithTextExists".equalsIgnoreCase(fieldType)) {
                        String fieldValue = $(withText(linkedFieldId)).getText();
                        String[] fieldValues = fieldValue.split(linkedFieldId);
                        if (fieldValues.length > 1) {
                            $(byId(fieldId)).shouldBe(visible, editable).setValue(fieldValues[1]);
                        }
                    } else if ("Button".equalsIgnoreCase(fieldType)) {
                        $(byId(fieldId)).shouldBe(enabled).click();
                    } else if ("SelectOption".equalsIgnoreCase(fieldType)) {
                        $(byId(fieldId)).selectOption(Integer.parseInt(defaultValue));
                    }
                }
                // This is temp code to bypass Local error and should be removed while running in NPE
                if ("InsuredInsuranceScore.InsuranceScoreCd".equalsIgnoreCase(fieldId)) {
                    executeJavaScript("document.getElementById('InsuredInsuranceScore.InsuranceScoreCd').value='A';");
                }

                if (isSleepNeeded && sleepTime > 0) {
                    HelperClass.waitBySleep(sleepTime);
                }
                if (shouldWaitForCompletion) {
                    HelperClass.waitForCompletion();
                }
                if (shouldWaitForAddressValidation && !addressVerificationImgField.isEmpty()) {
                    HelperClass.waitForAddressVerification(addressVerificationImgField);
                }
                if (shouldPressTab) {
                    HelperClass.pressTab(fieldId);
                }

                if ("Save".equalsIgnoreCase(fieldId) || "NextPage".equalsIgnoreCase(fieldId) || "Closeout".equalsIgnoreCase(fieldId)) {
                    executionResult = HelperClass.validateError();
                    if ($(byClassName("error_severity_error")).exists()) {// && ("Closeout".equalsIgnoreCase(fieldId))) {
                        return executionResult;
                    }
                    if ("Closeout".equalsIgnoreCase(fieldId)) {
                        if (!executionResult.isEmpty()) {
                            return executionResult;
                        }

                    }
                } else if ("SubmitPayment".equalsIgnoreCase(fieldId)) {
                    if ($(byClassName("ui-dialog-buttonset")).exists()) {
                        if ($(byId("dialogOK")).exists()) {
                            $(byId("dialogOK")).click();
                        }
                    }
                }
            }
            return executionResult;
        } catch (AssertionError ex) {
            String result = ex.toString().split("\n")[0];
            if (!executionResult.isEmpty()) {
                String message = executionResult.get(2) + " | " + result;
                executionResult.set(2, message);
            }
            executionResult.add("Failed");
            executionResult.add(result);
            return executionResult;
        } catch (Exception ex) {
            String result = ex.getMessage();
            if (!executionResult.isEmpty()) {
                String message = executionResult.get(2) + " | " + result;
                executionResult.set(2, message);
            }
            executionResult.add("Failed");
            executionResult.add(result);
            return executionResult;
        }
    }
}
