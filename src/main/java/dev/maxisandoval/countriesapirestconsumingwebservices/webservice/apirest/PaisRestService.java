package dev.maxisandoval.countriesapirestconsumingwebservices.webservice.apirest;

import dev.maxisandoval.countriesapirestconsumingwebservices.model.apiResponse.CountryData;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaisRestService {
    private static final String API_URL = "https://restcountries.com/v3.1/name/{pais}?fields=languages,maps";
    private final WebClient webClient;

    public PaisRestService (WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    public CountryData obtenerPaisInfoApi(String pais) {
        return webClient
                .get()
                .uri(API_URL, pais)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(CountryData.class)
                .blockLast();
    }
}