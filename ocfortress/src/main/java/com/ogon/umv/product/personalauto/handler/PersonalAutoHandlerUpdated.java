package com.ogon.umv.product.personalauto.handler;

import com.ogon.apidatamapper.InputData;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.common.utility.StaticVariables;
import com.ogon.dto.TestCaseDriverDTO;
import com.ogon.entity.TestCasesEntity;
import com.ogon.entity.TestResultEntity;
import com.ogon.entity.TestDataEntity;
import com.ogon.service.TestCaseService;
import com.ogon.service.TestResultService;
import com.ogon.service.UIFieldTestDataMapService;
import com.ogon.umv.common.pages.*;
import com.ogon.umv.product.personalauto.testcases.*;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Epic("Personal Auto Test Suite")
public class PersonalAutoHandlerUpdated {
    private final Logger logger = LogManager.getLogger("PersonalAutoHandlerUpdated");
    private String personalAutoTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
    private TestResultService testResultService;
    private TestCaseService testCaseService;
    private UIFieldTestDataMapService uiFieldTestDataMapService;
    private String carrierId = "";
    private String product = "";
    private String stateCd = "";
    private String executedBy = "";
    private String formattedDate = "";
    private String jobId = "";

    public String executePersonalAutoTestSuite(InputData inputData) {
        try {
            initializeFields();
            carrierId = inputData.getCarrierId();
            product = inputData.getProduct();
            stateCd = inputData.getStateCd();
            executedBy = inputData.getExecutedBy();
            jobId = inputData.getNextJobId();
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            testResultService = appCtx.getBean("testResultService", TestResultService.class);
            testCaseService = appCtx.getBean("testCaseService", TestCaseService.class);
            uiFieldTestDataMapService = appCtx.getBean("UIFieldTestDataMapService", UIFieldTestDataMapService.class);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
            formattedDate = sdf.format(date);
            String result = "";
            String[] testCaseList = inputData.getTestCaseList();
            personalAutoTestSuiteResult = executeTestCaseList(testCaseList, inputData.getLoginUserId(), inputData.getLoginPassword());
            if (StaticVariables.EXECUTION_SUCCESS.equalsIgnoreCase(personalAutoTestSuiteResult)) {
                if ("Failed".equalsIgnoreCase(result)) {
                    personalAutoTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                }
            }
            return personalAutoTestSuiteResult;
        } catch (Exception e) {
            personalAutoTestSuiteResult = StaticVariables.EXECUTION_FAILED;
            logger.error(e);
            return personalAutoTestSuiteResult;
        }
    }

    private String executeTestCaseList(String[] testCaseList, String loginUserId, String loginPassword) {

        String result;
        String currentTestCaseId = "";
        String currentTestCaseName = "";
        String currentDriver = "";
        try {
            result = "";
            // New Logic Starts
            List<String> loginResultList = userLogin(loginUserId, loginPassword);
            if (loginResultList != null && !loginResultList.isEmpty() && loginResultList.get(0).equalsIgnoreCase(StaticVariables.EXECUTION_FAILED)) {
                saveTestResult(loginUserId, "User Login", "User Login", "User Login", loginResultList.get(1), loginResultList.get(0), "USER_LOGIN");
                return "Failed";
            }

            for (String testCaseId : testCaseList) {
                currentTestCaseId = testCaseId;
                List<TestDataEntity> testDataFromDB = uiFieldTestDataMapService.fetchTestDataByTestCase(testCaseId);
                TestCaseDriverDTO testCaseDriverDTO = uiFieldTestDataMapService.getTestCaseDriver(testCaseId);
                String testCaseName = testCaseDriverDTO.getTestCaseName();
                currentTestCaseName = testCaseName;
                String driverName = testCaseDriverDTO.getDriverName();
                currentDriver = driverName;
                String transactionCd = "";

                List<String> resultList = new ArrayList<>();
                switch (driverName) {
                    case "ADD_PRODUCER":
                        resultList = addProducer(testDataFromDB);
                        transactionCd = StaticVariables.TX_ADD_PRODUCER;
                        break;
                    case "ADD_CUSTOMER":
                        resultList = addCustomer(testDataFromDB);
                        break;
                    case "ADD_QUOTE":
                        resultList = addQuote(testDataFromDB);
                        break;
                    case "NEW_BUSINESS":
                        resultList = newBusiness(testDataFromDB);
                        break;
                    case "ENDORSE_ADD_RISK":
                        resultList = addRiskEndorsement(testDataFromDB);
                        break;
                    case "INSURED_CANCELLATION":
                        resultList = insuredCancellation(testDataFromDB);
                        break;
                    case "REINSTATEMENT":
                        resultList = reinstatement(testDataFromDB);
                        break;
                    case "PAY_PLAN_CHANGE":
                        resultList = payPlanChange(testDataFromDB);
                        break;
                    case "ACH_PAYMENT", "MAKE_PAYMENT":
                        resultList = makePayment(testDataFromDB);
                        break;
                    case "BACKDATED_POLICY_APPROVAL_RULE":
                        resultList = backdatedPolicyApprovalRule(testDataFromDB);
                        break;
                    case "PD_LIMIT_APPROVAL_RULE":
                        resultList = pdLimitApprovalRule(testDataFromDB);
                        break;
                    default:
                        System.out.println("Unknown driver");
                }
                if (resultList != null && !resultList.isEmpty()) {
                    saveTestResult(resultList.get(2), transactionCd, testCaseId, testCaseName, resultList.get(1), resultList.get(0), driverName);
                    result = resultList.get(0);
                }
            }
        } catch (Exception e) {
            personalAutoTestSuiteResult = StaticVariables.EXECUTION_FAILED;
            saveTestResult("", "", currentTestCaseId, currentTestCaseName, e.getMessage(), "Failed with Exception", currentDriver);
            logger.error(e);
            return personalAutoTestSuiteResult;
        }
        return result;
    }

    @Feature("Add New Customer Test case")
    @Story("New Customer should be added successfully")
    @Description("This test verifies New Customer Add workflow")
    private List<String> userLogin(String loginUserId, String loginPassword) {
        return new UserLogin().executeLoginTest(loginUserId, loginPassword);
    }

    @Feature("Add New Customer Test case")
    @Story("New Customer should be added successfully")
    @Description("This test verifies New Customer Add workflow")
    private List<String> addCustomer(List<TestDataEntity> testDataFromDB) {
        return new CreateCustomer().createNewCustomer(testDataFromDB);
    }

    @Feature("Add New Producer and Agency Test case")
    @Story("New Producer and Agency should be added successfully")
    @Description("This test verifies New Producer and Agency Add workflow")
    private List<String> addProducer(List<TestDataEntity> testDataFromDB) {
        return new CreateProducer().addAgencyAndProducer(testDataFromDB);
    }

    @Feature("Add Quote Test case")
    @Story("Add Quote should be created successfully")
    @Description("This test verifies New Quote workflow")
    private List<String> addQuote(List<TestDataEntity> testDataFromDB) {
        return new AddQuote().addNewQuote(testDataFromDB);
    }

    @Feature("New Business Test case")
    @Story("New Business Policy should be created successfully")
    @Description("This test verifies New Business workflow")
    public List<String> newBusiness(List<TestDataEntity> testDataFromDB) {
        return new NewBusiness().createNewBusiness(testDataFromDB);
    }

    @Feature("Endorsement Test case")
    @Story("Endorsement should be Performed successfully")
    @Description("This test verifies Endorsement workflow")
    public List<String> addRiskEndorsement(List<TestDataEntity> testDataFromDB) {
        return new EndorsementAddRisk().addRiskEndorsement(testDataFromDB);
    }

    @Feature("Insured Cancellation Test case")
    @Story("Insured Cancellation should be Performed successfully")
    @Description("This test verifies Insured Cancellation workflow")
    public List<String> insuredCancellation(List<TestDataEntity> testDataFromDB) {
        return new InsuredCancellation().insuredCancellation(testDataFromDB);
    }

    @Feature("Reinstatement Test case")
    @Story("Reinstatement should be Performed successfully")
    @Description("This test verifies Reinstatement workflow")
    public List<String> reinstatement(List<TestDataEntity> testDataFromDB) {
        return new Reinstatement().reinstatement(testDataFromDB);
    }

    @Feature("Pay Plan Change Test case")
    @Story("Pay Plan Change be done successfully")
    @Description("This test verifies Make Payment")
    public List<String> payPlanChange(List<TestDataEntity> testDataFromDB) {
        return new PayPlanChange().payPlanChange(testDataFromDB);
    }

    @Feature("Make Payment Test case")
    @Story("Payment Should be done successfully")
    @Description("This test verifies Make Payment")
    public List<String> makePayment(List<TestDataEntity> testDataFromDB) {
        return new MakePayment().makePayment(testDataFromDB);
    }

    @Feature("Approval Rule For Back dated policies")
    @Story("Policies back dated greater than 15 days Must Be Approved")
    @Description("Policies back dated greater than 15 days Must Be Approved")
    public List<String> backdatedPolicyApprovalRule(List<TestDataEntity> testDataFromDB) {
        return new BackdatedPoliciesApprovalRule().triggerApprovalRule(testDataFromDB);
    }

    @Feature("Approval Rule For PD Limit")
    @Story("PD Limit greater than MaX PD Limit must be approved")
    @Description("PD Limit greater than MaX PD Limit must be approved")
    public List<String> pdLimitApprovalRule(List<TestDataEntity> testDataFromDB) {
        return new PDLimitApprovalRule().triggerApprovalRule(testDataFromDB);
    }

    private void initializeFields() {
        personalAutoTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
        product = "";
        stateCd = "";
        executedBy = "";
        formattedDate = "";
        jobId = "";
    }

    private void saveTestResult(String policyNumber, String transactionCd, String testCaseId, String testCaseName, String testResult, String status, String driverName) {
        TestResultEntity testResultEntity = new TestResultEntity();
        testResultEntity.setCarrierId(carrierId);
        testResultEntity.setStateCd(stateCd);
        testResultEntity.setLob(product);
        testResultEntity.setTestCaseId(testCaseId);
        testResultEntity.setExecutedBy(executedBy);
        testResultEntity.setExecutedOn(formattedDate);
        testResultEntity.setReferenceNumber(policyNumber);
        testResultEntity.setTestCaseName(testCaseName);
        testResultEntity.setTransactionCd(transactionCd);
        testResultEntity.setTransactionNumber("");
        testResultEntity.setTestResult(testResult);
        testResultEntity.setTestStatus(status);
        testResultEntity.setJobId(jobId);
        testResultService.saveTestResults(testResultEntity);

        if (testCaseService.getTestCaseByID(carrierId, stateCd, product, testCaseId) != null) {
            testCaseService.deleteTestCase(carrierId, stateCd, product, testCaseId);
        }
        TestCasesEntity testCasesEntity = new TestCasesEntity();
        testCasesEntity.setCarrierId(carrierId);
        testCasesEntity.setStateCd(stateCd);
        testCasesEntity.setLob(product);
        testCasesEntity.setTestCaseId(testCaseId);
        testCasesEntity.setTestCaseName(testCaseName);
        testCasesEntity.setTestDriver(driverName);
        testCasesEntity.setLastJobID(jobId);
        testCasesEntity.setLastRunDate(formattedDate);
        testCasesEntity.setLastRunBy(executedBy);
        testCasesEntity.setLastRunOutcome(testResult);
        testCasesEntity.setLastRunStatus(status);
        testCaseService.saveTestCase(testCasesEntity);
    }
}
