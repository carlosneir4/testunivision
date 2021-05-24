package co.com.univision.widgets.utils;

import co.com.univision.widgets.config.ConfigProperties;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;


/**
 * Class Utilities to handle Http stuff
 */
@Slf4j
public class HttpClient {

    /**
     * Builds Custom headers
     *
     * @param json
     * @return
     */
    public static HttpEntity<String> createHeaders(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(json, headers);
    }

    /**
     * Makes a Http request to an external resource
     *
     * @param url
     * @param json
     * @param method
     * @return
     */
    public static ResponseEntity callExternalUrl(RestTemplate restTemplate, String url, String json, HttpMethod method) {
        log.info("Get object mapped from a response body  {}");
        return restTemplate.exchange(url, method, createHeaders(json), String.class);
    }

    /**
     * Builds a string Url depending from path
     * @param path
     * @param config
     * @return
     */
    public static String buildUrlFromPath(String path, ConfigProperties config) {
        String url;
       switch(path) {
           case "news" :
               url = buildUrl(config.getUrlContent(),config.getNews(),config.getLazyLoad());
               break;
           default :
               url = buildUrl(config.getUrlContent(),config.getBase(),config.getLazyLoad());
       }
       return url;
    }

    /**
     * Builds a string Url to call an univision externals resources
     *
     * @return
     */
    private static String buildUrl(String urlContent, String pathUrl, String lazyLoad) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlContent)
                .queryParam("url", pathUrl)
                .queryParam("lazyload", lazyLoad);
        return builder.toUriString();
    }

}
