package test;

import dev.maxisandoval.countriesapirestconsumingwebservices.CountriesApiRestConsumingWebServices;
import dev.maxisandoval.countriesapirestconsumingwebservices.controller.PaisController;
import dev.maxisandoval.countriesapirestconsumingwebservices.model.PaisDTO;
import dev.maxisandoval.countriesapirestconsumingwebservices.model.apiResponse.CountryData;
import dev.maxisandoval.countriesapirestconsumingwebservices.webservice.apirest.PaisRestService;
import dev.maxisandoval.countriesapirestconsumingwebservices.webservice.soap.PaisSoapClient;
import localhost.ws.Moneda;
import localhost.ws.Pais;
import localhost.ws.ObtenerPaisResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ContextConfiguration(classes = CountriesApiRestConsumingWebServices.class)
public class PaisControllerTest {

    @Mock
    private PaisSoapClient paisSoapClient;

    @Mock
    private PaisRestService paisRestService;

    @InjectMocks
    private PaisController paisController;

    private static final String NOMBRE_PAIS = "Argentina";

    @Test
    void testObtenerPaisSOAPyAPIDisponibles() {//Ambos servicios webs
        ObtenerPaisResponse soapResponse = new ObtenerPaisResponse();
        Pais paisSOAPAux = construirPaisMockSoap();
        soapResponse.setPais(paisSOAPAux);

        CountryData paisAPIAux = construirPaisMockApiRest();
        CountryData apiResponse = new CountryData();
        apiResponse.setLanguages(paisAPIAux.getLanguages());
        apiResponse.setMaps(paisAPIAux.getMaps());

        when(paisSoapClient.obtenerPais(any())).thenReturn(soapResponse);
        when(paisRestService.obtenerPaisInfoApi(any())).thenReturn(apiResponse);

        PaisDTO result = paisController.obtenerPais(paisSOAPAux.getNombre());

        verify(paisSoapClient, times(1)).obtenerPais(paisSOAPAux.getNombre());
        verify(paisRestService, times(1)).obtenerPaisInfoApi(paisSOAPAux.getNombre());

        assertAll(
                () -> assertEquals(paisSOAPAux.getNombre(), result.getNombre()),
                () -> assertEquals(paisSOAPAux.getCapital(), result.getCapital()),
                () -> assertEquals(paisSOAPAux.getMoneda(), result.getMoneda()),
                () -> assertEquals(paisSOAPAux.getPoblacion(), result.getPoblacion()),
                () -> assertEquals(paisSOAPAux.getBandera(), result.getBandera()),
                () -> assertEquals(paisAPIAux.getLanguages(), result.getLenguajes()),
                () -> assertEquals(paisAPIAux.getMaps(), result.getMapas())
        );
    }

    @Test
    void testObtenerPaisSOAPDisponible() {
        ObtenerPaisResponse soapResponse = new ObtenerPaisResponse();
        Pais paisSOAPAux = construirPaisMockSoap();
        soapResponse.setPais(paisSOAPAux);

        when(paisSoapClient.obtenerPais(any())).thenReturn(soapResponse);

        PaisDTO result = paisController.obtenerPais(NOMBRE_PAIS);

        verify(paisSoapClient, times(1)).obtenerPais(NOMBRE_PAIS);

        assertAll(
                () -> assertEquals(paisSOAPAux.getNombre(), result.getNombre()),
                () -> assertEquals(paisSOAPAux.getCapital(), result.getCapital()),
                () -> assertEquals(paisSOAPAux.getMoneda(), result.getMoneda()),
                () -> assertEquals(paisSOAPAux.getPoblacion(), result.getPoblacion()),
                () -> assertEquals(paisSOAPAux.getBandera(), result.getBandera()),
                () -> assertNull(result.getLenguajes()),
                () -> assertNull(result.getMapas())
        );
    }

    @Test
    void testObtenerPaisAPIDisponible() {
        CountryData paisAPIAux = construirPaisMockApiRest();
        CountryData apiResponse = new CountryData();
        apiResponse.setLanguages(paisAPIAux.getLanguages());
        apiResponse.setMaps(paisAPIAux.getMaps());

        when(paisRestService.obtenerPaisInfoApi(any())).thenReturn(apiResponse);

        PaisDTO result = paisController.obtenerPais(NOMBRE_PAIS);

        verify(paisRestService, times(1)).obtenerPaisInfoApi(NOMBRE_PAIS);

        assertAll(
                () -> assertNull(result.getNombre()),
                () -> assertNull(result.getCapital()),
                () -> assertNull(result.getMoneda()),
                () -> assertNull(result.getPoblacion()),
                () -> assertNull(result.getBandera()),
                () -> assertEquals(paisAPIAux.getLanguages(), result.getLenguajes()),
                () -> assertEquals(paisAPIAux.getMaps(), result.getMapas())
        );
    }

    @Test
    void testObtenerPaisSOAPyAPICaidos() {
        when(paisSoapClient.obtenerPais(any())).thenReturn(null);
        when(paisRestService.obtenerPaisInfoApi(any())).thenReturn(null);

        PaisDTO result = paisController.obtenerPais(NOMBRE_PAIS);

        verify(paisSoapClient, times(1)).obtenerPais(NOMBRE_PAIS);
        verify(paisRestService, times(1)).obtenerPaisInfoApi(NOMBRE_PAIS);

        assertAll(
                () -> assertNull(result.getNombre()),
                () -> assertNull(result.getCapital()),
                () -> assertNull(result.getMoneda()),
                () -> assertNull(result.getPoblacion()),
                () -> assertNull(result.getBandera()),
                () -> assertNull(result.getLenguajes()),
                () -> assertNull(result.getMapas())
        );
    }

    private Pais construirPaisMockSoap() {
        Pais paisSoap = new Pais();

        paisSoap.setNombre(NOMBRE_PAIS);
        paisSoap.setCapital("Ciudad Autónoma de Buenos Aires");
        paisSoap.setMoneda(Moneda.ARS);
        paisSoap.setPoblacion(46234830);
        paisSoap.setBandera("https://flagcdn.com/w2560/ar.png");

        return paisSoap;
    }

    private CountryData construirPaisMockApiRest(){
        CountryData paisApiRest = new CountryData();

        Map<String, String> lenguajes = new HashMap<>();
        lenguajes.put("spa", "Spanish");
        paisApiRest.setLanguages(lenguajes);

        Map<String, String> maps = new HashMap<>();
        maps.put("googleMaps", "https://goo.gl/maps/Z9DXNxhf2o93kvyc6");
        paisApiRest.setMaps(maps);

        return paisApiRest;
    }
}