package edu3431.matiukhin.tddlab2.config;/*
@author sasha
@project SoftwareQuality8
@class AuditonConfiguration
@version 1.0.0
@since 16.03.2026 - 14 - 38
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@Configuration
public class AuditionConfiguration {
    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }


}
