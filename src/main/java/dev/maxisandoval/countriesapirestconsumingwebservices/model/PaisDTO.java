package dev.maxisandoval.countriesapirestconsumingwebservices.model;

import localhost.ws.Moneda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data //toString, equals, hashcode, getters y setters
@NoArgsConstructor
@AllArgsConstructor
public class PaisDTO {
    private String nombre;
    private String capital;
    private Moneda moneda;
    private Integer poblacion;
    private String bandera;
    private Map<String, String> lenguajes;
    private Map<String, String> mapas;
}