package com.ogon.springbootcontroller.model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;

import java.util.List;

public class InsuranceNowUserLoadResponse {
    public InsuranceNowUserLoadResponse() {
    }

    @JsonProperty("id")
    private String id;

    @JsonProperty("userInfoListItems")
    private List<UserInfoListItems> userInfoListItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<UserInfoListItems> getUserInfoListItems() {
        return userInfoListItems;
    }

    public void setUserInfoListItems(List<UserInfoListItems> userInfoListItems) {
        this.userInfoListItems = userInfoListItems;
    }
}
