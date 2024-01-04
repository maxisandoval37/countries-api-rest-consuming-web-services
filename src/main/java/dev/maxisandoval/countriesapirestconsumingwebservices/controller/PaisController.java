package dev.maxisandoval.countriesapirestconsumingwebservices.controller;

import dev.maxisandoval.countriesapirestconsumingwebservices.webservice.soap.PaisSoapClient;
import localhost.ws.ObtenerPaisResponse;
import localhost.ws.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaisController {

    private final PaisSoapClient paisSoapClient;

    @Autowired
    public PaisController (PaisSoapClient paisSoapClient) {
        this.paisSoapClient = paisSoapClient;
    }

    @GetMapping("obtenerPais")
    public Pais obtenerPais(@RequestParam String nombre){
        ObtenerPaisResponse response = paisSoapClient.obtenerPais(nombre);

        Pais pais = new Pais();
        pais.setNombre(response.getPais().getNombre());
        pais.setCapital(response.getPais().getCapital());
        pais.setMoneda(response.getPais().getMoneda());
        pais.setPoblacion(response.getPais().getPoblacion());
        pais.setBandera(response.getPais().getBandera());

        return pais;
    }
}