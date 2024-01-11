# Countries API Rest Consuming Web Services - Educación IT 2023 (Curso dictado por mi autoría)

Consumo de SOAP y API de terceros, para finalemente exponer los datos resultantes en una API REST de países, personalizada.

## Tecnologías Utilizadas

- **Java 17**
- **Maven**
- **Dockerfile**
- **Deploy en Render.com**
- **Spring Boot Starter Web Services**
- **Filtros CORS**
- **Spring Boot Starter Tomcat**
- **Lombok**
- **Spring Boot Starter WebFlux:** Llamadas a APIs de forma asíncrona.
- **Spring Boot Starter Test**
- **Mockito Core:** Utilizado para realizar pruebas con objetos simulados (**Mocks**).
- **JAX-WS Maven Plugin:** Utilizado para generar clases Java a partir de un archivo WSDL.
- **Marshalling**

## Construcción y Ejecución

Utiliza el siguiente comando Maven:

```bash
mvn clean install
```

## Configuración del Plugin JAX-WS
El proyecto utiliza el plugin jaxws-maven-plugin para generar clases Java a partir de un archivo WSDL. Configurar correctamente la ubicación en el POM.

```xml
<plugins>
    <plugin>
        <groupId>com.sun.xml.ws</groupId>
        <artifactId>jaxws-maven-plugin</artifactId>
        <version>3.0.0</version>
        <executions>
            <execution>
                <goals>
                    <goal>wsimport</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <packageName>localhost.ws</packageName>
            <wsdlUrls>
                <wsdlUrl>https://soap-paises.onrender.com/ws/paises.wsdl</wsdlUrl>
            </wsdlUrls>
            <sourceDestDir>${sourcesDir}</sourceDestDir>
            <destDir>${classesDir}</destDir>
            <extension>true</extension>
        </configuration>
    </plugin>
</plugins>
```

## Endpoint

El endpoint principal de la aplicación se encuentra en:

`/contriesapi/obtenerPais?nombre=argentina`

## Live Demo

[onrender.com](https://countries-api-rest.onrender.com/contriesapi/obtenerPais?nombre=argentina)

## Información Adicional
Para cualquier información adicional o consultas: <maxisandoval98@gmail.com>

Muchas gracias!
