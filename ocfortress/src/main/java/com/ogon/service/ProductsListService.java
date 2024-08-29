package com.ogon.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ogon.common.utility.AppVariables;
import com.ogon.common.utility.CommonFunctions;
import com.ogon.config.ConfigProperties;
import com.ogon.common.utility.ApplicationContextUtils;
import com.ogon.dto.CarrierStateProductsDTO;
import com.ogon.dto.CarrierStatesDTO;
import com.ogon.dto.CarriersDTO;
import com.ogon.entity.ProductsListEntity;
import com.ogon.repository.ProductListRepo;
import com.ogon.springbootcontroller.model.ProductSelection;
import com.ogon.springbootcontroller.model.ProductSelectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductsListService {

    @Autowired
    private ProductListRepo productListRepo;

    public String getAllCarriers() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            ArrayNode carrierDataArray = objectMapper.createArrayNode();
            List<CarriersDTO> carriersDTOs = productListRepo.getAllCarriers();
            for (CarriersDTO carrierDTO : carriersDTOs) {
                ObjectNode carrierNode = objectMapper.createObjectNode();
                carrierNode.put("carrierId", carrierDTO.getCarrierGroupIdRef());
                carrierNode.put("carrierName", carrierDTO.getCarrierGroupName());
                carrierDataArray.add(carrierNode);
            }
            response.set("carriergroups", carrierDataArray);
            response.put("status", "success");
            response.put("message", "carriers retrieved successfully");
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "carriers fetch unsuccessful");
        }
        return response.toString();
    }

    public String getCarrierStates(String carrierId) {
        AppVariables.carrierCd = carrierId;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            ArrayNode statesDataArray = objectMapper.createArrayNode();
            List<CarrierStatesDTO> carrierStatesDTOs = productListRepo.getAllStatesForCarriers(carrierId);
            for (CarrierStatesDTO carrierStatesDTO : carrierStatesDTOs) {
                ObjectNode stateNode = objectMapper.createObjectNode();
                String stateCode = carrierStatesDTO.getStateCode();
                stateNode.put("stateCode", stateCode);
                stateNode.put("stateName", AppVariables.stateMap.get(stateCode));
                statesDataArray.add(stateNode);
            }
            response.set("carrierstates", statesDataArray);
            response.put("status", "success");
            response.put("message", "carrier states retrieved successfully");
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "carrier states fetch unsuccessful");
        }
        return response.toString();
    }

    public String getCarrierStateProducts(String carrierId, String stateCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            ArrayNode productsDataArray = objectMapper.createArrayNode();
            List<CarrierStateProductsDTO> carrierStateProductsDTOs = productListRepo.getAllProductsForStateAndCarriers(carrierId, stateCode);
            for (CarrierStateProductsDTO carrierStateProductsDTO : carrierStateProductsDTOs) {
                ObjectNode productNode = objectMapper.createObjectNode();
                productNode.put("productCode", carrierStateProductsDTO.getProductName());
                productNode.put("productName", carrierStateProductsDTO.getLicenseClass());
                productsDataArray.add(productNode);
            }
            response.set("carrierstateproducts", productsDataArray);
            response.put("status", "success");
            response.put("message", "carrier state products retrieved successfully");
        } catch (Exception e) {
            response.put("status", "failed");
            response.put("message", "carrier state products fetch unsuccessful");
        }
        return response.toString();
    }

    public void refreshCarriersStatesProducts() {
        try {
            ApplicationContext appCtx = ApplicationContextUtils.getApplicationContext();
            ConfigProperties configProperties = appCtx.getBean("configProperties", ConfigProperties.class);

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ProductsListEntity getProductsListEntity(ProductSelection productSelection) {
        ProductsListEntity productsListEntity = new ProductsListEntity();
        productsListEntity.setProductName(productSelection.getProductName());
        productsListEntity.setStateCode(productSelection.getStateCode());
        productsListEntity.setCarrierGroupIdRef(productSelection.getCarrierGroupId());
        productsListEntity.setLicenseClass(productSelection.getLicenseClass());
        productsListEntity.setCarrierGroupName(productSelection.getCarrierGroupName());
        String productMapKey = productSelection.getCarrierGroupId() + "_" + productSelection.getStateCode() + "_" + productSelection.getLicenseClass();
        AppVariables.productNamesMap.put(productMapKey, productSelection.getProductName());
        return productsListEntity;
    }
}
