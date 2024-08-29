package com.ogon.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TestCaseBundleIDDTO {

    private int bundleMapperId;

    public int getBundleMapperId() {
        return bundleMapperId;
    }

    public void setBundleMapperId(int bundleMapperId) {
        this.bundleMapperId = bundleMapperId;
    }
}
