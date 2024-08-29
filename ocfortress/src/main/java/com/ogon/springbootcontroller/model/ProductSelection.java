package com.ogon.springbootcontroller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductSelection {

    public ProductSelection() {
    }

    @JsonProperty("id")
    private String id;

    @JsonProperty("ref")
    private String ref;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productVersionId")
    private String productVersionId;

    @JsonProperty("carrierGroupId")
    private String carrierGroupId;

    @JsonProperty("carrierGroupName")
    private String carrierGroupName;

    @JsonProperty("stateCode")
    private String stateCode;

    @JsonProperty("licenseClass")
    private String licenseClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersionId() {
        return productVersionId;
    }

    public void setProductVersionId(String productVersionId) {
        this.productVersionId = productVersionId;
    }

    public String getCarrierGroupId() {
        return carrierGroupId;
    }

    public void setCarrierGroupId(String carrierGroupId) {
        this.carrierGroupId = carrierGroupId;
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

    public String getLicenseClass() {
        return licenseClass;
    }

    public void setLicenseClass(String licenseClass) {
        this.licenseClass = licenseClass;
    }

    public boolean isQuickQuoteEnabled() {
        return quickQuoteEnabled;
    }

    public void setQuickQuoteEnabled(boolean quickQuoteEnabled) {
        this.quickQuoteEnabled = quickQuoteEnabled;
    }

    @JsonProperty("quickQuoteEnabledInd")
    private boolean quickQuoteEnabled;
}
