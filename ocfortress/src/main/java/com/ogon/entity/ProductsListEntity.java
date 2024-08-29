package com.ogon.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "productslist")
@Data
public class ProductsListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "carrierGroupIdRef")
    private String carrierGroupIdRef;
    @Column(name = "carrierGroupName")
    private String carrierGroupName;
    @Column(name = "stateCode")
    private String stateCode;
    @Column(name = "productName")
    private String productName;
    @Column(name = "licenseClass")
    private String licenseClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicenseClass() {
        return licenseClass;
    }

    public void setLicenseClass(String licenseClass) {
        this.licenseClass = licenseClass;
    }

}
