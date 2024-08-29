package com.ogon.umv.product.homeowners.handler;

import com.ogon.common.utility.StaticVariables;
import com.ogon.common.utility.TestCaseList;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.apidatamapper.InputData;
import com.ogon.entity.TestResultEntity;
import com.ogon.service.UIFieldTestDataMapService;
import com.ogon.service.TestResultService;
import com.ogon.umv.common.pages.*;
import com.ogon.umv.common.utility.HelperClass;
import com.ogon.umv.product.homeowners.testcases.AddQuote;
import com.ogon.umv.product.homeowners.testcases.EndorsementAddRisk;
import com.ogon.umv.product.homeowners.testcases.NewBusiness;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Epic("Homeowers Test Suite")
public class HomeownersHandlerExcel {

    private static String homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
    private static final Map<String, String> testCasesMap = new TestCaseList().getTestCasesMap();
    private static TestResultService testResultService;

    private static UIFieldTestDataMapService testDataService;
    private static String carrierId = "";
    private static String product = "";
    private static String stateCd = "";
    private static String effectiveDt = "";
    private static String executedBy = "";
    private static String formattedDate = "";

    private static String jobId = "";

    private static boolean execute_TC_ADD_CUSTOMER = false;
    private static boolean execute_TC_ADD_PRODUCER = false;
    private static boolean execute_TC_ADD_QUOTE = false;
    private static boolean execute_TC_NB = false;
    private static boolean execute_TC_ENDO_ADD_RISK = false;
    private static boolean execute_TC_ENDO_ADD_COV = false;
    private static boolean execute_TC_CANCEL_INSURED = false;
    private static boolean execute_TC_REINS = false;
    private static boolean execute_TC_PAY_PLAN_CHG = false;
    private static boolean execute_TC_MK_PAYMENT = false;
    private static boolean otherTestCasesAvailable = false;

    private static List<String> addProducerResultList = new ArrayList<>();
    private static List<String> addCustomerResultList = new ArrayList<>();
    private static List<String> addQuoteResultList = new ArrayList<>();
    private static List<String> newBusinessResultList = new ArrayList<>();
    private static List<String> endorsementResultList = new ArrayList<>();

    private static List<String> insCancellationResultList = new ArrayList<>();

    private static List<String> reinstatementResultList = new ArrayList<>();

    private static List<String> makePaymentResultList = new ArrayList<>();
    private static List<String> payPlanChangeResultList = new ArrayList<>();

    public static String executeHomeOwnersTestSuite(InputData inputData) {

        initializeFields();
        carrierId = inputData.getCarrierId();
        product = inputData.getProduct();
        stateCd = inputData.getStateCd();
        effectiveDt = inputData.getEffectDt();
        executedBy = inputData.getExecutedBy();
        jobId = inputData.getNextJobId();
        String[] testCaseList = inputData.getTestCaseList();
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        testResultService = appCtx.getBean("testResultService", TestResultService.class);

        testDataService = appCtx.getBean("testDataService", UIFieldTestDataMapService.class);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss z");
        formattedDate = sdf.format(date);

        String policyNumber = "";
        String providerNumber = "";
        boolean isProducerExistsAlready = false;

//        try {
//            InputStream inp = new FileInputStream("C:\\Users\\vigne\\OneDrive\\Documents\\TestData_umv1.xlsx");
//            List<UIFieldTestDataMapEntity> testDataEntities = ExcelHelper.excelToDatabase(inp);
//            testDataService.saveAll(testDataEntities);
//        } catch (IOException e) {
//            throw new RuntimeException("fail to store excel data: " + e.getMessage());
//        }

        assignTestCasesForExecution(testCaseList);


        // Search if Producer already exists. This is done only if Add Producer test case is not run.
        // Suite will create Producer if not available before it runs other test cases.
        String producerCd = HelperClass.generateProducerCd(carrierId, product, stateCd);
        if (!execute_TC_ADD_PRODUCER) {
            List<String> producerSearchResultList = SearchProducer.searchProducer(producerCd);
            if (producerSearchResultList != null && !producerSearchResultList.isEmpty() && "Found".equalsIgnoreCase(producerSearchResultList.get(0))) {
                providerNumber = producerCd;
                isProducerExistsAlready = true;
            }
        }

        // Add New Producer
        if (execute_TC_ADD_PRODUCER || !isProducerExistsAlready) {
            //if (execute_TC_ADD_PRODUCER || execute_TC_ADD_QUOTE || execute_TC_NB || otherTestCasesAvailable) {
            //addProducerResultList = CreateProducer.createNewProducer(carrierId, product, stateCd);
            if (addProducerResultList != null && !addProducerResultList.isEmpty() && "success".equalsIgnoreCase(addProducerResultList.get(0))) {
                providerNumber = addProducerResultList.get(1);
            }
            if (execute_TC_ADD_PRODUCER && addProducerResultList != null && !addProducerResultList.isEmpty() && addProducerResultList.size() > 1) {
                String executionResult;
                if ("Failed".equalsIgnoreCase(addProducerResultList.get(0))) {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                    executionResult = addProducerResultList.get(2);
                } else {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                    executionResult = addProducerResultList.get(2) + " ( " + addProducerResultList.get(1) + " )";
                }
                saveTestResult(providerNumber, StaticVariables.TX_ADD_PRODUCER, "TC_ADD_PRODUCER", executionResult);
            }
        }

        String firstName = HelperClass.generateCustomerFirstName(carrierId, product, stateCd);
        String lastName = HelperClass.generateCustomerLastName(carrierId, product, stateCd);
        boolean isCustomerExistsAlready = false;
        if(!execute_TC_ADD_CUSTOMER){
            String searchName = firstName+" "+lastName;
            List<String> customerSearchResultList = SearchCustomer.searchCustomer(searchName);
            if (customerSearchResultList != null && !customerSearchResultList.isEmpty() && "Found".equalsIgnoreCase(customerSearchResultList.get(0))) {
                isCustomerExistsAlready = true;
            }
        }
        // Add New Customer
        if (execute_TC_ADD_CUSTOMER || !isCustomerExistsAlready){
            //if (execute_TC_ADD_CUSTOMER || execute_TC_ADD_QUOTE || execute_TC_NB || otherTestCasesAvailable) {
            //addCustomerResultList = CreateCustomer.createNewCustomer(firstName, lastName);
            if (execute_TC_ADD_CUSTOMER && addCustomerResultList != null && !addCustomerResultList.isEmpty() && addCustomerResultList.size() > 1) {
                String executionResult;
                if ("Failed".equalsIgnoreCase(addCustomerResultList.get(0))) {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                    executionResult = addCustomerResultList.get(1) + " ( " + addCustomerResultList.get(0) + " - " + addCustomerResultList.get(2) + " )";
                } else {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                    executionResult = addCustomerResultList.get(2);
                }
                saveTestResult(firstName + " " + lastName, StaticVariables.TX_ADD_CUSTOMER, "TC_ADD_CUSTOMER", executionResult);
            }
        }

        if (execute_TC_ADD_QUOTE || execute_TC_NB || otherTestCasesAvailable) {
            if (providerNumber.isEmpty()) {
                providerNumber = producerCd;
            }
            //Create Quote
            addQuoteResultList = executeAddQuoteTestCase(providerNumber, firstName, lastName);
            if (execute_TC_ADD_QUOTE && addQuoteResultList != null && !addQuoteResultList.isEmpty() && addQuoteResultList.size() > 1) {
                String executionResult = "";
                if ("Failed".equalsIgnoreCase(addQuoteResultList.get(0))) {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                    executionResult = addQuoteResultList.get(1) + " ( " + addQuoteResultList.get(0) + " - " + addQuoteResultList.get(2) + " )";
                } else {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                    executionResult = addQuoteResultList.get(2);
                }
                saveTestResult(addQuoteResultList.get(1), StaticVariables.TX_ADD_QUOTE, "TC_ADD_QUOTE", executionResult);
            }
        }

        if (execute_TC_NB || (otherTestCasesAvailable && policyNumber.isEmpty())) {
            //Convert Quote to Application and Bind it
            newBusinessResultList = executeNewBusinessTestCase();
            if (execute_TC_NB && newBusinessResultList != null && !newBusinessResultList.isEmpty() && newBusinessResultList.size() > 1) {
                String executionResult = "";
                if ("Failed".equalsIgnoreCase(newBusinessResultList.get(0))) {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                    executionResult = newBusinessResultList.get(2) + " ( " + newBusinessResultList.get(1) + " )";
                } else {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                    policyNumber = newBusinessResultList.get(1);
                    executionResult = newBusinessResultList.get(2);
                }
                saveTestResult(newBusinessResultList.get(1), StaticVariables.TX_NEW_BUSINESS, "TC_NB", executionResult);
            } else if ((otherTestCasesAvailable && policyNumber.isEmpty())) {
                policyNumber = newBusinessResultList.get(1);
            }
        }

        if (execute_TC_ENDO_ADD_RISK) {
            endorsementResultList = executeEndorsementCase(policyNumber, effectiveDt);
            String executionResult = "";
            if ("Failed".equalsIgnoreCase(endorsementResultList.get(0))) {
                homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                executionResult = endorsementResultList.get(1) + " ( " + endorsementResultList.get(0) + " - " + endorsementResultList.get(2) + " )";
            } else {
                homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                executionResult = endorsementResultList.get(2) + " ( " + endorsementResultList.get(3) + " )";
            }
            saveTestResult(endorsementResultList.get(1), StaticVariables.TX_ENDORSEMENT, "TC_ENDO_ADD_RISK", executionResult);
        }
        if (execute_TC_ENDO_ADD_COV) {

        }
        if (execute_TC_CANCEL_INSURED || execute_TC_REINS) {
            //insCancellationResultList = executeInsuredCancellationTestCase(policyNumber, effectiveDt);
            if(execute_TC_CANCEL_INSURED) {
                String executionResult = "";
                if ("Failed".equalsIgnoreCase(insCancellationResultList.get(0))) {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                    executionResult = insCancellationResultList.get(1) + " ( " + insCancellationResultList.get(0) + " - " + insCancellationResultList.get(2) + " )";
                } else {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                    executionResult = insCancellationResultList.get(2);
                }
                saveTestResult(insCancellationResultList.get(1), StaticVariables.TX_CANCELLATION, "TC_CANCEL_INSURED", executionResult);
            }
        }
        if (execute_TC_REINS) {
            //reinstatementResultList = executeReinstatementTestCase(policyNumber);
            String executionResult = "";
            if ("Failed".equalsIgnoreCase(reinstatementResultList.get(0))) {
                homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                executionResult = reinstatementResultList.get(1) + " ( " + reinstatementResultList.get(0) + " - " + reinstatementResultList.get(2) + " )";
            } else {
                homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                executionResult = reinstatementResultList.get(2);
            }
            saveTestResult(reinstatementResultList.get(1), StaticVariables.TX_REINSTATEMENT, "TC_REINS", executionResult);
        }
        if (execute_TC_PAY_PLAN_CHG) {
            //payPlanChangeResultList = payPlanChangeTestCase(policyNumber);
            if (execute_TC_PAY_PLAN_CHG && payPlanChangeResultList != null && !payPlanChangeResultList.isEmpty() && payPlanChangeResultList.size() > 1) {
                String executionResult = "";
                if ("Failed".equalsIgnoreCase(payPlanChangeResultList.get(0))) {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                    executionResult = payPlanChangeResultList.get(1) + " ( " + payPlanChangeResultList.get(0) + " - " + payPlanChangeResultList.get(2) + " )";
                } else {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                    executionResult = payPlanChangeResultList.get(2);
                }
                saveTestResult(payPlanChangeResultList.get(1), StaticVariables.TX_PAY_PLAN_CHANGE, "TC_PAY_PLAN_CHG", executionResult);
            }
        }

        if (execute_TC_MK_PAYMENT) {
            //makePaymentResultList = makePaymentTestCase(policyNumber);
            if (execute_TC_MK_PAYMENT && makePaymentResultList != null && !makePaymentResultList.isEmpty() && makePaymentResultList.size() > 1) {
                String executionResult = "";
                if ("Failed".equalsIgnoreCase(makePaymentResultList.get(0))) {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_FAILED;
                    executionResult = makePaymentResultList.get(1) + " ( " + makePaymentResultList.get(0) + " - " + makePaymentResultList.get(2) + " )";
                } else {
                    homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
                    executionResult = makePaymentResultList.get(2);
                }

                saveTestResult(makePaymentResultList.get(1), StaticVariables.TX_MAKE_PAYMENT, "TC_MK_PAYMENT", executionResult);
            }
        }
        return homeOwnersTestSuiteResult;
    }

    private static void initializeFields() {
        homeOwnersTestSuiteResult = StaticVariables.EXECUTION_SUCCESS;
        product = "";
        stateCd = "";
        effectiveDt = "";
        executedBy = "";
        formattedDate = "";
        jobId = "";
        execute_TC_ADD_CUSTOMER = false;
        execute_TC_ADD_PRODUCER = false;
        execute_TC_ADD_QUOTE = false;
        execute_TC_NB = false;
        execute_TC_ENDO_ADD_RISK = false;
        execute_TC_ENDO_ADD_COV = false;
        execute_TC_CANCEL_INSURED = false;
        execute_TC_REINS = false;
        execute_TC_PAY_PLAN_CHG = false;
        execute_TC_MK_PAYMENT = false;
        otherTestCasesAvailable = false;
        addProducerResultList.clear();
        addCustomerResultList.clear();
        addQuoteResultList.clear();
        newBusinessResultList.clear();
        endorsementResultList.clear();
        makePaymentResultList.clear();
        payPlanChangeResultList.clear();
        insCancellationResultList.clear();
        reinstatementResultList.clear();
    }

    private static void assignTestCasesForExecution(String[] testCaseList) {
        for (String selectedTestCase : testCaseList) {
            switch (selectedTestCase) {
                case "TC_ADD_CUSTOMER":
                    execute_TC_ADD_CUSTOMER = true;
                    break;
                case "TC_ADD_PRODUCER":
                    execute_TC_ADD_PRODUCER = true;
                    break;
                case "TC_ADD_QUOTE":
                    execute_TC_ADD_QUOTE = true;
                    break;
                case "TC_NB":
                    execute_TC_NB = true;
                    break;
                case "TC_ENDO_ADD_RISK":
                    execute_TC_ENDO_ADD_RISK = true;
                    otherTestCasesAvailable = true;
                    break;
                case "TC_ENDO_ADD_COV":
                    execute_TC_ENDO_ADD_COV = true;
                    otherTestCasesAvailable = true;
                    break;
                case "TC_CANCEL_INSURED":
                    execute_TC_CANCEL_INSURED = true;
                    otherTestCasesAvailable = true;
                    break;
                case "TC_REINS":
                    execute_TC_REINS = true;
                    otherTestCasesAvailable = true;
                    break;
                case "TC_PAY_PLAN_CHG":
                    execute_TC_PAY_PLAN_CHG = true;
                    otherTestCasesAvailable = true;
                    break;
                case "TC_MK_PAYMENT":
                    execute_TC_MK_PAYMENT = true;
                    otherTestCasesAvailable = true;
                    break;
                default:
                    System.out.println("Unknown test case");
            }
        }
    }

    @Feature("Add Quote Test case")
    @Story("Add Quote should be created successfully")
    @Description("This test verifies New Quote workflow")
    public static List<String> executeAddQuoteTestCase(String providerNumber,String firstName, String lastName) {
        long startTime = System.currentTimeMillis();
        List<String> addQuoteResultList = AddQuote.addNewQuoteFromExcel(carrierId, product, stateCd, effectiveDt, providerNumber,firstName, lastName);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        return addQuoteResultList;
    }

    @Feature("New Business Test case")
    @Story("New Business Policy should be created successfully")
    @Description("This test verifies New Business workflow")
    public static List<String> executeNewBusinessTestCase() {
        long startTime = System.currentTimeMillis();
        List<String> nbResultList = NewBusiness.executeNewBusinessTestCase();
        long endTime = System.currentTimeMillis();
        return nbResultList;
    }

    @Feature("Endorsement Test case")
    @Story("Endorsement should be Performed successfully")
    @Description("This test verifies Endorsement workflow")
    public static List<String> executeEndorsementCase(String policyNumber, String effectiveDt) {
        long startTime = System.currentTimeMillis();
        List<String> endorsementResult = EndorsementAddRisk.endorsementTestCase(policyNumber, effectiveDt);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        return endorsementResult;
    }

//    @Feature("Insured Cancellation Test case")
//    @Story("Insured Cancellation should be Performed successfully")
//    @Description("This test verifies Insured Cancellation workflow")
//    public static List<String> executeInsuredCancellationTestCase(String policyNumber, String effectiveDt) {
//        long startTime = System.currentTimeMillis();
//        List<String> insCancelResult = InsuredCancellation.cancelPolicy(policyNumber, effectiveDt);
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        return insCancelResult;
//    }
//
//    @Feature("Reinstatement Test case")
//    @Story("Reinstatement should be Performed successfully")
//    @Description("This test verifies Reinstatement workflow")
//    public static List<String> executeReinstatementTestCase(String policyNumber) {
//        long startTime = System.currentTimeMillis();
//        List<String> reinstatementResult = Reinstatement.reinstatePolicy(policyNumber);
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        return reinstatementResult;
//    }
//
//    @Feature("Make Payment Test case")
//    @Story("Payment Should be done successfully")
//    @Description("This test verifies Make Payment")
//    public static List<String> makePaymentTestCase(String policyNumber) {
//        long startTime = System.currentTimeMillis();
//        List<String> mkPaymentResultList = MakePayment.makePayment(policyNumber);
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        return mkPaymentResultList;
//    }
//
//    @Feature("Pay Plan Change Test case")
//    @Story("Pay Plan Change be done successfully")
//    @Description("This test verifies Make Payment")
//    public static List<String> payPlanChangeTestCase(String policyNumber) {
//        long startTime = System.currentTimeMillis();
//        List<String> payplanChangeResultList = PayPlanChange.payPlanChange(policyNumber);
//        long endTime = System.currentTimeMillis();
//        long elapsedTime = endTime - startTime;
//        return payplanChangeResultList;
//    }

    private static void saveTestResult(String policyNumber, String transactionCd, String testCaseId, String testResult) {
        TestResultEntity testResultEntity = new TestResultEntity();
        testResultEntity.setCarrierId(carrierId);
        testResultEntity.setStateCd(stateCd);
        testResultEntity.setLob(product);
        testResultEntity.setTestCaseId(testCaseId);
        testResultEntity.setExecutedBy(executedBy);
        testResultEntity.setExecutedOn(formattedDate);
        testResultEntity.setReferenceNumber(policyNumber);
        testResultEntity.setTestCaseName(testCasesMap.get(testCaseId));
        testResultEntity.setTransactionCd(transactionCd);
        testResultEntity.setTransactionNumber("");
        testResultEntity.setTestResult(testResult);
        testResultEntity.setTestStatus(homeOwnersTestSuiteResult);
        testResultEntity.setJobId(jobId);
        testResultService.saveTestResults(testResultEntity);
    }
}
