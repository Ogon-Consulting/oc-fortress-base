package com.ogon.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class CarriersDTO {
    private String carrierGroupIdRef;
    private String carrierGroupName;


    public String getCarrierGroupIdRef() {
        return carrierGroupIdRef;
    }

    public void setCarrierGroupIdRef(String carrierGroupIdRef) {
        this.carrierGroupIdRef = carrierGroupIdRef;
    }

    public String getCarrierGroupName() {
        return carrierGroupName;
    }

    public void setCarrierGroupName(String carrierGroupName) {
        this.carrierGroupName = carrierGroupName;
    }
}
