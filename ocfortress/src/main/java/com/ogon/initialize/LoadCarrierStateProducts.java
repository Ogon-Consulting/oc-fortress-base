package com.ogon.initialize;

import com.ogon.common.utility.AppVariables;
import com.ogon.common.utility.CommonFunctions;
import com.ogon.config.ConfigProperties;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.entity.ProductsListEntity;
import com.ogon.repository.ProductListRepo;
import com.ogon.springbootcontroller.model.ProductSelection;
import com.ogon.springbootcontroller.model.ProductSelectionResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class LoadCarrierStateProducts {


    @Autowired
    private ProductListRepo productListRepo;

    @PostConstruct
    private void setAllUSStates(){
        AppVariables.stateMap.put("AL", "Alabama");
        AppVariables.stateMap.put("AK", "Alaska");
        AppVariables.stateMap.put("AZ", "Arizona");
        AppVariables.stateMap.put("AR", "Arkansas");
        AppVariables.stateMap.put("CA", "California");
        AppVariables.stateMap.put("CO", "Colorado");
        AppVariables.stateMap.put("CT", "Connecticut");
        AppVariables.stateMap.put("DE", "Delaware");
        AppVariables.stateMap.put("FL", "Florida");
        AppVariables.stateMap.put("GA", "Georgia");
        AppVariables.stateMap.put("HI", "Hawaii");
        AppVariables.stateMap.put("ID", "Idaho");
        AppVariables.stateMap.put("IL", "Illinois");
        AppVariables.stateMap.put("IN", "Indiana");
        AppVariables.stateMap.put("IA", "Iowa");
        AppVariables.stateMap.put("KS", "Kansas");
        AppVariables.stateMap.put("KY", "Kentucky");
        AppVariables.stateMap.put("LA", "Louisiana");
        AppVariables.stateMap.put("ME", "Maine");
        AppVariables.stateMap.put("MD", "Maryland");
        AppVariables.stateMap.put("MA", "Massachusetts");
        AppVariables.stateMap.put("MI", "Michigan");
        AppVariables.stateMap.put("MN", "Minnesota");
        AppVariables.stateMap.put("MS", "Mississippi");
        AppVariables.stateMap.put("MO", "Missouri");
        AppVariables.stateMap.put("MT", "Montana");
        AppVariables.stateMap.put("NE", "Nebraska");
        AppVariables.stateMap.put("NV", "Nevada");
        AppVariables.stateMap.put("NH", "New Hampshire");
        AppVariables.stateMap.put("NJ", "New Jersey");
        AppVariables.stateMap.put("NM", "New Mexico");
        AppVariables.stateMap.put("NY", "New York");
        AppVariables.stateMap.put("NC", "North Carolina");
        AppVariables.stateMap.put("ND", "North Dakota");
        AppVariables.stateMap.put("OH", "Ohio");
        AppVariables.stateMap.put("OK", "Oklahoma");
        AppVariables.stateMap.put("OR", "Oregon");
        AppVariables.stateMap.put("PA", "Pennsylvania");
        AppVariables.stateMap.put("RI", "Rhode Island");
        AppVariables.stateMap.put("SC", "South Carolina");
        AppVariables.stateMap.put("SD", "South Dakota");
        AppVariables.stateMap.put("TN", "Tennessee");
        AppVariables.stateMap.put("TX", "Texas");
        AppVariables.stateMap.put("UT", "Utah");
        AppVariables.stateMap.put("VT", "Vermont");
        AppVariables.stateMap.put("VA", "Virginia");
        AppVariables.stateMap.put("WA", "Washington");
        AppVariables.stateMap.put("WV", "West Virginia");
        AppVariables.stateMap.put("WI", "Wisconsin");
        AppVariables.stateMap.put("WY", "Wyoming");
    }

    @PostConstruct
    public void loadCarrierStateProducts() {
        ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
        ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);
        try {
            String loadProducts = configProperties.getLoadProductsOnStartup();
            if("Yes".equalsIgnoreCase(loadProducts)) {
                System.out.println("************** Product Load Started ************");
                String url = configProperties.getAllProductURL();
                RestTemplate restTemplate = CommonFunctions.createRestTemplate();
                HttpEntity<String> request = new HttpEntity<>(null, CommonFunctions.getHttpHeaders(configProperties));
                ResponseEntity<ProductSelectionResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, ProductSelectionResponse.class);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    ProductSelectionResponse productSelectionResponse = responseEntity.getBody();
                    assert productSelectionResponse != null;
                    List<ProductSelection> productSelectionList = productSelectionResponse.getProductListItems();
                    if (productSelectionList != null) {
                        productListRepo.deleteAll();
                        AppVariables.productNamesMap.clear();
                        for (ProductSelection productSelection : productSelectionList) {
                            ProductsListEntity productsListEntity = getProductsListEntity(productSelection);
                            productListRepo.save(productsListEntity);
                        }
                    }
                } else if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    System.out.println(responseEntity.getStatusCode().value() + responseEntity.toString());
                }
                System.out.println("************** Product Load Ended ************");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ProductsListEntity getProductsListEntity(ProductSelection productSelection) {
        ProductsListEntity productsListEntity = new ProductsListEntity();
        productsListEntity.setProductName(productSelection.getProductName());
        productsListEntity.setStateCode(productSelection.getStateCode());
        productsListEntity.setCarrierGroupIdRef(productSelection.getCarrierGroupId());
        productsListEntity.setLicenseClass(productSelection.getLicenseClass());
        productsListEntity.setCarrierGroupName(productSelection.getCarrierGroupName());
        String productMapKey = productSelection.getCarrierGroupId()+"_"+productSelection.getStateCode()+"_"+productSelection.getLicenseClass();
        AppVariables.productNamesMap.put(productMapKey, productSelection.getProductName());
        return productsListEntity;
    }
}
