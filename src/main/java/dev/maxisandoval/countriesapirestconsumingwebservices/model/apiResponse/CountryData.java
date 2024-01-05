package dev.maxisandoval.countriesapirestconsumingwebservices.model.apiResponse;

import lombok.Data;
import java.util.Map;

@Data
public class CountryData {
    private Map<String, String> languages;
    private Map<String, String> maps;
}