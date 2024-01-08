package dev.maxisandoval.countriesapirestconsumingwebservices.webservice.soap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PaisSoapConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        //Debe llamarse igual que el packageName del pom
        marshaller.setContextPath("localhost.ws");
        return marshaller;
    }

    @Bean
    public PaisSoapClient paisSoapClient (Jaxb2Marshaller marshaller) {
        PaisSoapClient client = new PaisSoapClient();
        client.setDefaultUri("https://soap-paises.onrender.com/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);

        return client;
    }
}
