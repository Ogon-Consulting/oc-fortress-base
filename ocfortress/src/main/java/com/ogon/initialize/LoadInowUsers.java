package com.ogon.initialize;

import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.common.utility.CommonFunctions;
import com.ogon.config.ConfigProperties;
import com.ogon.entity.InowUsersEntity;
import com.ogon.service.InowUsersService;
import com.ogon.springbootcontroller.model.InsuranceNowUserLoadResponse;
import com.ogon.springbootcontroller.model.UserInfoListItems;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tinylog.Logger;

import java.util.List;

@Component
public class LoadInowUsers {

    @Autowired
    private InowUsersService inowUsersService;
    
    @PostConstruct
    public void loadAllInsuranceNowUsers() {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);
        try {
            String loadInsuranceNowUsers = configProperties.getLoadInsuranceNowUsersOnStartup();
            if("Yes".equalsIgnoreCase(loadInsuranceNowUsers)) {
                System.out.println("************** InsuranceNow Users Load Started ************");
                String getAllInowUsersURL = configProperties.getAllUsersURL();
                RestTemplate restTemplate = CommonFunctions.createRestTemplate();
                HttpEntity<String> request = new HttpEntity<>(null, CommonFunctions.getHttpHeaders(configProperties));
                ResponseEntity<InsuranceNowUserLoadResponse> responseEntity = restTemplate.exchange(getAllInowUsersURL, HttpMethod.GET, request, InsuranceNowUserLoadResponse.class);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    InsuranceNowUserLoadResponse insuranceNowUsersLoadResponse = responseEntity.getBody();
                    assert insuranceNowUsersLoadResponse != null;
                    List<UserInfoListItems> userInfoListItems = insuranceNowUsersLoadResponse.getUserInfoListItems();
                    if(userInfoListItems !=null && !userInfoListItems.isEmpty()) {
                        inowUsersService.deleteAllInowUsers();
                        for (UserInfoListItems userInfoListItem : userInfoListItems) {
                            if(!"Guest".equalsIgnoreCase(userInfoListItem.getUserTypeCd()) && !(userInfoListItem.getLoginId().contains("listener") || userInfoListItem.getLoginId().contains("automation"))) {
                                InowUsersEntity inowUsersEntity = getInowUsersListEntity(userInfoListItem);
                                inowUsersService.addInowUser(inowUsersEntity);
                            }
                        }
                    }
                } else if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    Logger.error(responseEntity.getStatusCode().value() + responseEntity.toString());
                }
                System.out.println("************** InsuranceNow Users Load completed ************");
            }
        } catch (Exception e) {
            Logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private InowUsersEntity getInowUsersListEntity(UserInfoListItems userInfoListItems) {
        InowUsersEntity inowUsersEntity = new InowUsersEntity();
        inowUsersEntity.setFirstName(userInfoListItems.getFirstName());
        inowUsersEntity.setLastName(userInfoListItems.getLastName());
        inowUsersEntity.setLoginId(userInfoListItems.getLoginId());
        inowUsersEntity.setUserTypeCd(userInfoListItems.getUserTypeCd());
        return inowUsersEntity;
    }
}
