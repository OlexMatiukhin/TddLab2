package edu3431.matiukhin.tddlab2.response;/*
@author sasha
@project TddLab2
@class BaseMetaData
@version 1.0.0
@since 16.03.2026 - 16 - 49
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseMetaData {
    @Builder.Default
    private int code = 200;
    @Builder.Default
    private boolean success = true;
    @Builder.Default
    private String errorMessage = null;

    public BaseMetaData(int code, boolean success) {
        this.code = code;
        this.success = success;
    }
}
