package com.ogon.repository;

import com.ogon.dto.CarrierStateProductsDTO;
import com.ogon.dto.CarrierStatesDTO;
import com.ogon.dto.CarriersDTO;
import com.ogon.entity.ProductsListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductListRepo extends JpaRepository<ProductsListEntity, Integer> {

    @Query("select distinct new com.ogon.dto.CarriersDTO( carrierGroupIdRef, carrierGroupName) from ProductsListEntity")
    List<CarriersDTO> getAllCarriers();

    @Query("select distinct new com.ogon.dto.CarrierStatesDTO( stateCode) from ProductsListEntity where carrierGroupIdRef = :carrierGroupIdRef")
    //@Query("select distinct new com.ogon.dto.CarrierStatesDTO( stateCode) from ProductsListEntity")
    List<CarrierStatesDTO> getAllStatesForCarriers(@Param("carrierGroupIdRef") String carrierGroupIdRef);

    @Query("select distinct new com.ogon.dto.CarrierStateProductsDTO( licenseClass, productName) from ProductsListEntity where carrierGroupIdRef = :carrierGroupIdRef and stateCode = :stateCode")
    List<CarrierStateProductsDTO> getAllProductsForStateAndCarriers(@Param("carrierGroupIdRef") String carrierGroupIdRef,
                                                                    @Param("stateCode") String stateCode);


}
