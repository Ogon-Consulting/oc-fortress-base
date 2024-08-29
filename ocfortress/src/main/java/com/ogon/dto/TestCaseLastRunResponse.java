package com.ogon.dto;

import com.ogon.entity.TestResultEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseLastRunResponse {
    private String status;
    private String message;
    private List<TestResultEntity> testResultEntities;

}
