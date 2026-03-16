package edu3431.matiukhin.tddlab2.config;/*
@author sasha
@project SoftwareQuality8
@class AuditorAwareImpl
@version 1.0.0
@since 16.03.2026 - 14 - 40
*/


import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(System.getProperty("user.name"));
    }
}