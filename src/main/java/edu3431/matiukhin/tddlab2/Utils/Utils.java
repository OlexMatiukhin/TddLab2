package edu3431.matiukhin.tddlab2.Utils;/*
@author sasha
@project SoftwareQuality8
@class Utils
@version 1.0.0
@since 29.05.2025 - 15 - 59
*/
import com.fasterxml.jackson.core.JsonProcessingException;
import tools.jackson.databind.ObjectMapper;

public class Utils {
    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
