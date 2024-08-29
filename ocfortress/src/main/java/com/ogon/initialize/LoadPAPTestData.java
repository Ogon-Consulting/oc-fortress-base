package com.ogon.initialize;

import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.config.ConfigProperties;
import com.ogon.entity.TestDataEntity;
import com.ogon.service.TestResultService;
import com.ogon.service.UIFieldTestDataMapService;
import com.ogon.umv.common.utility.ExcelHelper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class LoadPAPTestData {

    @Autowired
    private UIFieldTestDataMapService uiFieldTestDataMapService;

    @Autowired
    private TestResultService testResultService;

    @PostConstruct
    public void loadFieldTestDataForPAP(){
        try {
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);
            String loadTestCasesOnStartup = configProperties.getLoadTestCasesOnStartup();
            if("Yes".equalsIgnoreCase(loadTestCasesOnStartup)) {
                InputStream inp = new FileInputStream("D:\\uifieldvaluemapping.xlsx");
                List<TestDataEntity> testDataEntities = ExcelHelper.excelToDatabase(inp);
                uiFieldTestDataMapService.clearAll();
                testResultService.clearAllTestResults();
                uiFieldTestDataMapService.saveAll(testDataEntities);
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
}
