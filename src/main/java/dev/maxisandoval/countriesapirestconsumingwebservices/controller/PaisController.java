package dev.maxisandoval.countriesapirestconsumingwebservices.controller;

import dev.maxisandoval.countriesapirestconsumingwebservices.model.PaisDTO;
import dev.maxisandoval.countriesapirestconsumingwebservices.model.apiResponse.CountryData;
import dev.maxisandoval.countriesapirestconsumingwebservices.webservice.apirest.PaisRestService;
import dev.maxisandoval.countriesapirestconsumingwebservices.webservice.soap.PaisSoapClient;
import localhost.ws.ObtenerPaisResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaisController {

    private final PaisSoapClient paisSoapClient;
    private final PaisRestService paisRestService;
    private static final Logger log = LoggerFactory.getLogger(PaisController.class);

    @Autowired
    public PaisController (PaisSoapClient paisSoapClient, PaisRestService paisRestService) {
        this.paisSoapClient = paisSoapClient;
        this.paisRestService = paisRestService;
    }

    @GetMapping("/obtenerPais")
    public PaisDTO obtenerPais(@RequestParam String nombre) {
        ObtenerPaisResponse soapResponse = obtenerDatosSOAP(nombre);
        CountryData  apiResponse = obtenerDatosAPI(nombre);

        return construirPaisDTO(soapResponse, apiResponse);
    }

    private ObtenerPaisResponse obtenerDatosSOAP(String nombre) {
        try {
            return paisSoapClient.obtenerPais(nombre);
        }
        catch (Exception e) {
            log.error("No se obtuvo info del SOAP");
            return null;
        }
    }

    private CountryData obtenerDatosAPI(String nombre) {
        try {
            return paisRestService.obtenerPaisInfoApi(nombre);
        }
        catch (Exception e) {
            log.error("No se obtuvo info de la API");
            return null;
        }
    }

    private PaisDTO construirPaisDTO(ObtenerPaisResponse soapResponse, CountryData apiResponse){
        PaisDTO paisDTO = new PaisDTO();

        if (soapResponse != null) {
            paisDTO.setNombre(soapResponse.getPais().getNombre());
            paisDTO.setCapital(soapResponse.getPais().getCapital());
            paisDTO.setMoneda(soapResponse.getPais().getMoneda());
            paisDTO.setPoblacion(soapResponse.getPais().getPoblacion());
            paisDTO.setBandera(soapResponse.getPais().getBandera());
        }
        else {
            log.error("La respuesta del SOAP es nula");
        }

        if (apiResponse != null) {
            paisDTO.setLenguajes(apiResponse.getLanguages());
            paisDTO.setMapas(apiResponse.getMaps());
        }
        else {
            log.error("La respuesta de la API es nula");
        }

        return paisDTO;
    }
}