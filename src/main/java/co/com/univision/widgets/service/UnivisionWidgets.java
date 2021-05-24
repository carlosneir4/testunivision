package co.com.univision.widgets.service;

import co.com.univision.widgets.config.ConfigProperties;
import co.com.univision.widgets.domain.WidgetDTO;
import co.com.univision.widgets.utils.HttpClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpMethod;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.schedulers.Schedulers;

import java.io.IOException;

@Slf4j
@Service
public class UnivisionWidgets implements UnivisionWidgetsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ConfigProperties config;

    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * Implementation to fetch a list of widgets.
     *
     * @return
     */
    @Override
    public Observable<WidgetDTO> getWidgets(String path) {
        long start = System.currentTimeMillis();
        Observable<WidgetDTO> widgetDTO = wrapCallExternalAsAsync(HttpClient.buildUrlFromPath(path, config), null, HttpMethod.GET);
        long end = System.currentTimeMillis();
        log.debug("time to fetch widgets took " + (end - start) + " MilliSeconds");
        return widgetDTO;
    }



    /**
     * handles Asynchronous call to an external resource
     *
     * @param url
     * @param json
     * @param method
     * @return
     */
    private Observable<WidgetDTO> wrapCallExternalAsAsync(String url, String json, HttpMethod method) {
        return Observable.fromCallable(() -> HttpClient.callExternalUrl(restTemplate, url, json, method))
                .subscribeOn(Schedulers.io())
                .flatMap(re -> {
                    if (re.hasBody()) {

                        try {
                            log.info("Mapping widgets response from Response.body");
                            return Observable.just(mapper.readValue(re.getBody().toString(), WidgetDTO.class));
                        }  catch (JsonParseException e) {
                            log.error("Error parsing widgets list", e.getStackTrace());
                            throw Exceptions.propagate(e);
                        } catch (JsonMappingException e) {
                            log.error("Error mapping widgets list", e.getStackTrace());
                            throw Exceptions.propagate(e);
                        } catch (IOException e) {
                            log.error("Error reading widgets list", e.getStackTrace());
                            throw Exceptions.propagate(e);
                        }
                    } else {
                        return Observable.error(new Exception("Bad response status " + re.getStatusCode()));
                    }
                }, Observable::error, Observable::empty)
                .observeOn(Schedulers.computation());
    }

}
