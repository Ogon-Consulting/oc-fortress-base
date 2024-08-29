package com.ogon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.apidatamapper.NewTestCaseInputData;
import com.ogon.dto.TestCaseDriverDTO;
import com.ogon.dto.UIFieldValueDataMapDTO;
import com.ogon.entity.TestDataEntity;
import com.ogon.repository.UIFieldTestDataMapRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UIFieldTestDataMapService {

    @Autowired
    private UIFieldTestDataMapRepo testDataRepo;

    public void saveAll(List<TestDataEntity> testDataEntities) {
        testDataRepo.saveAll(testDataEntities);
    }

    public void clearAll() {
        testDataRepo.deleteAll();
    }

    public List<TestDataEntity> fetchTestDataByTestCase(String testCaseId) {
        return testDataRepo.findByTestCaseId(testCaseId);
    }

    public TestCaseDriverDTO getTestCaseDriver(String testCaseId) {
        return testDataRepo.getDriverName(testCaseId);
    }

    public String createNewTestCase(NewTestCaseInputData newTestCaseInputData) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            String carrierId = newTestCaseInputData.getCarrierId();
            String stateCd = newTestCaseInputData.getStateCd();
            String lob = newTestCaseInputData.getLob();
            String testCaseName = newTestCaseInputData.getTestCaseName();
            String newTestCase = newTestCaseInputData.getTestCaseData();
            if (testCaseName.isEmpty()) {
                response.put("status", "failed");
                response.put("message", "Test Case Name Cannot be Empty");
            } else {
                List<TestDataEntity> existingTestCaseList = testDataRepo.findByTestCaseName(testCaseName.trim());
                if (existingTestCaseList != null && !existingTestCaseList.isEmpty()) {
                    response.put("status", "failed");
                    response.put("message", "Test Case Exists Already");
                } else {
                    Map<String, Map<String, Map<String, ArrayList<String>>>> masterTestDataMap = processInputTestData(newTestCase);
                    createNewTestData(carrierId, stateCd, lob, testCaseName, masterTestDataMap);
                    response.put("status", "success");
                    response.put("message", "New Test Data Created Successfully");
                }
            }
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "Facing Exception, UI Fields and Default Test Data fetch unsuccessful");
        }
        return response.toString();
    }

    public Map<String, Map<String, Map<String, ArrayList<String>>>> processInputTestData(String testData) {

        try {
            Map<String, Map<String, Map<String, ArrayList<String>>>> masterTestDataMap = new HashMap<>();
            JSONArray testCaseJSONArray = new JSONArray(testData);
            for (int testCaseIndex = 0; testCaseIndex < testCaseJSONArray.length(); testCaseIndex++) {
                JSONObject testCaseJSONobject = testCaseJSONArray.getJSONObject(testCaseIndex);
                String testCaseId = testCaseJSONobject.getString("testCaseId");
                JSONArray menuJSONArray = new JSONArray(testCaseJSONobject.getString("menus"));
                Map<String, Map<String, ArrayList<String>>> menuTestDataMap = new HashMap<>();
                for (int menuIndex = 0; menuIndex < menuJSONArray.length(); menuIndex++) {
                    JSONObject menuJSONobject = menuJSONArray.getJSONObject(menuIndex);
                    String menuName = menuJSONobject.getString("menuName");
                    JSONArray fieldsJSONArray = new JSONArray(menuJSONobject.getString("fields"));
                    Map<String, ArrayList<String>> fieldTestDataMap = new HashMap<>();
                    for (int fieldIndex = 0; fieldIndex < fieldsJSONArray.length(); fieldIndex++) {
                        JSONObject jsonFieldObject = fieldsJSONArray.getJSONObject(fieldIndex);
                        ArrayList<String> fieldTestDataList = new ArrayList<>();
                        fieldTestDataList.add(jsonFieldObject.getString("defaultValue"));
                        fieldTestDataList.add(jsonFieldObject.getString("newValue"));
                        fieldTestDataMap.put(jsonFieldObject.getString("fieldId"), fieldTestDataList);
                    }
                    menuTestDataMap.put(menuName, fieldTestDataMap);
                }
                masterTestDataMap.put(testCaseId, menuTestDataMap);
            }
            return masterTestDataMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createNewTestData(String carrierId, String stateCd, String lob, String testCaseName, Map<String, Map<String, Map<String, ArrayList<String>>>> masterTestDataMap) {
        String newTestCaseId = "TC_" + (testCaseName.trim().replaceAll(" ", "_")).toUpperCase();
        try {
            for (Map.Entry<String, Map<String, Map<String, ArrayList<String>>>> masterTestDataMapEntry : masterTestDataMap.entrySet()) {
                String testCaseId = masterTestDataMapEntry.getKey();
                List<TestDataEntity> testCaseEntitiesFromDB = testDataRepo.getAllTestDataByTestCaseID(carrierId, stateCd, lob, testCaseId);
                List<TestDataEntity> newTestCaseDataEntities = new ArrayList<>();
                //newTestCaseDataEntities.addAll(testCaseEntitiesFromDB);
                Map<String, Map<String, ArrayList<String>>> menuMap = masterTestDataMapEntry.getValue();
                for (TestDataEntity testCaseEntity : testCaseEntitiesFromDB) {
                    //UIFieldTestDataMapEntity newTestCaseData = newTestCaseDataEntities.get(index);
                    TestDataEntity newTestCaseEntity = new TestDataEntity();
                    newTestCaseEntity.setCarrierId(carrierId);
                    newTestCaseEntity.setStateCd(stateCd);
                    newTestCaseEntity.setLob(lob);
                    newTestCaseEntity.setTestCaseName(testCaseName);
                    newTestCaseEntity.setTestCaseId(newTestCaseId);
                    newTestCaseEntity.setUiName(testCaseEntity.getUiName());
                    newTestCaseEntity.setUiSequence(testCaseEntity.getUiSequence());
                    newTestCaseEntity.setFieldName(testCaseEntity.getFieldName());
                    newTestCaseEntity.setFieldId(testCaseEntity.getFieldId());
                    newTestCaseEntity.setFieldSequence(testCaseEntity.getFieldSequence());
                    newTestCaseEntity.setFieldType(testCaseEntity.getFieldType());
                    newTestCaseEntity.setShowField(testCaseEntity.getShowField());
                    newTestCaseEntity.setIsSleepNeeded(testCaseEntity.getIsSleepNeeded());
                    newTestCaseEntity.setSleepTime(testCaseEntity.getSleepTime());
                    newTestCaseEntity.setShouldWaitForCompletion(testCaseEntity.getShouldWaitForCompletion());
                    newTestCaseEntity.setValidations(testCaseEntity.getValidations());
                    newTestCaseEntity.setReadOnly(testCaseEntity.getReadOnly());
                    newTestCaseEntity.setShouldWaitForAddrVerification(testCaseEntity.getShouldWaitForAddrVerification());
                    newTestCaseEntity.setAddrVerificationImgField(testCaseEntity.getAddrVerificationImgField());
                    newTestCaseEntity.setShouldPressTab(testCaseEntity.getShouldPressTab());
                    newTestCaseEntity.setDriverName(testCaseEntity.getDriverName());
                    newTestCaseEntity.setLinkedFieldId(testCaseEntity.getLinkedFieldId());
                    newTestCaseEntity.setDefaultValue(testCaseEntity.getDefaultValue());
                    if (!(testCaseEntity.getFieldType().equalsIgnoreCase("Button") || testCaseEntity.getFieldType().equalsIgnoreCase("Link") || "No".equalsIgnoreCase(testCaseEntity.getShowField()))) {
                        String newValue = menuMap.get(testCaseEntity.getUiName()).get(testCaseEntity.getFieldId()).get(1);
                        if (!newValue.isEmpty()) {
                            newTestCaseEntity.setDefaultValue(newValue);
                        }
                    }
                    newTestCaseDataEntities.add(newTestCaseEntity);
                }
                System.out.println("end");
                testDataRepo.saveAll(newTestCaseDataEntities);
            }
        } catch (Exception e) {
            try {
                testDataRepo.deleteByTestCaseId(carrierId, stateCd, lob, newTestCaseId);
            } catch (Exception ex) {
                System.out.println("Exception in createNewTestData : " + ex);
                throw new RuntimeException(ex);
            }
            System.out.println("Exception in createNewTestData : " + e);
            throw new RuntimeException(e);
        }
    }

    public String getTestDataByTestCaseID(String carrierId, String stateCd, String lob, String testCaseId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            List<UIFieldValueDataMapDTO> uiFieldValueDataMapDTOs = testDataRepo.getTestDataByTestCaseID(carrierId, stateCd, lob, testCaseId);
            String priorTestCaseId = "";
            String currentTestCaseId;
            ObjectNode testCaseDataNode = objectMapper.createObjectNode();
            ObjectNode uiFieldDataNode = objectMapper.createObjectNode();
            ObjectNode uiTestDataNode = objectMapper.createObjectNode();
            int priorUISequence = 0;
            String priorUIName = "";
            for (UIFieldValueDataMapDTO uiFieldValueDataMapDTO : uiFieldValueDataMapDTOs) {
                currentTestCaseId = uiFieldValueDataMapDTO.getTestCaseId();
                int currentUISequence = uiFieldValueDataMapDTO.getUiSequence();
                if (priorTestCaseId.isEmpty()) {
                    priorTestCaseId = currentTestCaseId;
                }
                if (priorUISequence == 0) {
                    priorUISequence = currentUISequence;
                }
                if (!priorTestCaseId.equalsIgnoreCase(currentTestCaseId)) {
                    uiFieldDataNode.set(priorUIName, uiTestDataNode);
                    testCaseDataNode.set(priorTestCaseId, uiFieldDataNode);
                    uiFieldDataNode = objectMapper.createObjectNode();
                    uiTestDataNode = objectMapper.createObjectNode();
                    ObjectNode valueNode = objectMapper.createObjectNode();
                    valueNode.put("label", uiFieldValueDataMapDTO.getFieldName());
                    valueNode.put("fieldId", uiFieldValueDataMapDTO.getFieldId());
                    valueNode.put("defaultValue", uiFieldValueDataMapDTO.getDefaultValue());
                    String readOnlyFlag = uiFieldValueDataMapDTO.getReadOnly().isEmpty() ? "No" : uiFieldValueDataMapDTO.getReadOnly();
                    valueNode.put("readOnly", readOnlyFlag);
                    valueNode.put("newValue", "");
                    uiTestDataNode.set(uiFieldValueDataMapDTO.getFieldId(), valueNode);
                    priorUIName = uiFieldValueDataMapDTO.getUiName();
                    priorUISequence = currentUISequence;
                    priorTestCaseId = currentTestCaseId;
                } else {
                    if (currentUISequence != priorUISequence) {
                        uiFieldDataNode.set(priorUIName, uiTestDataNode);
                        uiTestDataNode = objectMapper.createObjectNode();
                        priorUIName = uiFieldValueDataMapDTO.getUiName();
                        priorUISequence = currentUISequence;
                        ObjectNode valueNode = objectMapper.createObjectNode();
                        valueNode.put("label", uiFieldValueDataMapDTO.getFieldName());
                        valueNode.put("fieldId", uiFieldValueDataMapDTO.getFieldId());
                        valueNode.put("defaultValue", uiFieldValueDataMapDTO.getDefaultValue());
                        String readOnlyFlag = uiFieldValueDataMapDTO.getReadOnly().isEmpty() ? "No" : uiFieldValueDataMapDTO.getReadOnly();
                        valueNode.put("readOnly", readOnlyFlag);
                        valueNode.put("newValue", "");
                        uiTestDataNode.set(uiFieldValueDataMapDTO.getFieldId(), valueNode);
                    } else {
                        priorUIName = uiFieldValueDataMapDTO.getUiName();
                        ObjectNode valueNode = objectMapper.createObjectNode();
                        valueNode.put("label", uiFieldValueDataMapDTO.getFieldName());
                        valueNode.put("fieldId", uiFieldValueDataMapDTO.getFieldId());
                        valueNode.put("defaultValue", uiFieldValueDataMapDTO.getDefaultValue());
                        String readOnlyFlag = uiFieldValueDataMapDTO.getReadOnly().isEmpty() ? "No" : uiFieldValueDataMapDTO.getReadOnly();
                        valueNode.put("readOnly", readOnlyFlag);
                        valueNode.put("newValue", "");
                        uiTestDataNode.set(uiFieldValueDataMapDTO.getFieldId(), valueNode);
                    }
                }
            }
            uiFieldDataNode.set(priorUIName, uiTestDataNode);
            testCaseDataNode.set(priorTestCaseId, uiFieldDataNode);
            response.set("uifieldtestdatamap", testCaseDataNode);
            response.put("status", "success");
            response.put("message", "UI Fields and Default Test Data retrieved successfully");
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "UI Fields and Default Test Data fetch unsuccessful");
        }
        return response.toString();
    }

    public String getUIFieldDataForCarrierStateLob(String carrierId, String stateCd, String lob) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {

            List<UIFieldValueDataMapDTO> uiFieldValueDataMapDTOs = testDataRepo.getAllUIFieldDataForCarrierStateLob(carrierId, stateCd, lob);
            String priorTestCaseId = "";
            String currentTestCaseId;
            ObjectNode testCaseDataNode = objectMapper.createObjectNode();
            ObjectNode uiFieldDataNode = objectMapper.createObjectNode();
            ObjectNode uiTestDataNode = objectMapper.createObjectNode();
            int priorUISequence = 1;
            String priorUIName = "";
            for (UIFieldValueDataMapDTO uiFieldValueDataMapDTO : uiFieldValueDataMapDTOs) {
                currentTestCaseId = uiFieldValueDataMapDTO.getTestCaseId();
                int currentUISequence = uiFieldValueDataMapDTO.getUiSequence();
                if (priorTestCaseId.isEmpty()) {
                    priorTestCaseId = currentTestCaseId;
                }
                if (!priorTestCaseId.equalsIgnoreCase(currentTestCaseId)) {
                    uiFieldDataNode.set(priorUIName, uiTestDataNode);
                    testCaseDataNode.set(priorTestCaseId, uiFieldDataNode);
                    uiFieldDataNode = objectMapper.createObjectNode();
                    uiTestDataNode = objectMapper.createObjectNode();
                    ObjectNode valueNode = objectMapper.createObjectNode();
                    valueNode.put(uiFieldValueDataMapDTO.getFieldName(), uiFieldValueDataMapDTO.getDefaultValue());
                    uiTestDataNode.set(uiFieldValueDataMapDTO.getFieldId(), valueNode);
                    priorUIName = uiFieldValueDataMapDTO.getUiName();
                    priorUISequence = currentUISequence;
                    priorTestCaseId = currentTestCaseId;
                } else {
                    if (currentUISequence != priorUISequence) {
                        uiFieldDataNode.set(priorUIName, uiTestDataNode);
                        uiTestDataNode = objectMapper.createObjectNode();
                        priorUIName = uiFieldValueDataMapDTO.getUiName();
                        priorUISequence = currentUISequence;
                        ObjectNode valueNode = objectMapper.createObjectNode();
                        valueNode.put(uiFieldValueDataMapDTO.getFieldName(), uiFieldValueDataMapDTO.getDefaultValue());
                        uiTestDataNode.set(uiFieldValueDataMapDTO.getFieldId(), valueNode);
                    } else {
                        priorUIName = uiFieldValueDataMapDTO.getUiName();
                        ObjectNode valueNode = objectMapper.createObjectNode();
                        valueNode.put(uiFieldValueDataMapDTO.getFieldName(), uiFieldValueDataMapDTO.getDefaultValue());
                        uiTestDataNode.set(uiFieldValueDataMapDTO.getFieldId(), valueNode);
                    }
                }
            }
            uiFieldDataNode.set(priorUIName, uiTestDataNode);
            testCaseDataNode.set(priorTestCaseId, uiFieldDataNode);
            response.set("uifieldtestdatamap", testCaseDataNode);
            response.put("status", "success");
            response.put("message", "UI Fields and Default Test Data retrieved successfully");
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "UI Fields and Default Test Data fetch unsuccessful");
        }
        return response.toString();
    }
}
