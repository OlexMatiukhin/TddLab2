package edu3431.matiukhin.tddlab2.model;/*
@author sasha
@project SoftwareQuality8
@class AuditMetadata
@version 1.0.0
@since 16.03.2026 - 14 - 50
*/

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class AuditMetadata {
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private LocalDateTime lastModifiedDate;
    @LastModifiedDate
    private String lastModifiedBy;

}
