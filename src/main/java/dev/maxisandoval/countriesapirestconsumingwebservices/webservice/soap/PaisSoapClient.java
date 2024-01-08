package dev.maxisandoval.countriesapirestconsumingwebservices.webservice.soap;

import localhost.ws.ObtenerPaisRequest;
import localhost.ws.ObtenerPaisResponse;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.slf4j.Logger;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class PaisSoapClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(PaisSoapClient.class);
    private static final String baseUrl = "https://soap-paises.onrender.com/ws";

    public ObtenerPaisResponse obtenerPais (String pais) {
        ObtenerPaisRequest request = new ObtenerPaisRequest();
        request.setName(pais);

        log.info("Obteniendo info del pais: " + pais);

        ObtenerPaisResponse response = (ObtenerPaisResponse) getWebServiceTemplate()
                .marshalSendAndReceive(baseUrl+"/paises", request,
                        new SoapActionCallback(baseUrl+"/obtenerPaisRequest"));

        return  response;
    }
}