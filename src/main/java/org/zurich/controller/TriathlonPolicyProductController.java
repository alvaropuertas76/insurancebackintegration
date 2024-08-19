package org.zurich.controller;

import org.springframework.web.client.RestTemplate;
import org.zurich.api.TriatlhonPolicyProductApi;
import org.zurich.model.Weather;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

@RestController
public class TriathlonPolicyProductController implements TriatlhonPolicyProductApi {

    private final RestTemplate restTemplate;

    private static final String CALL_WEATHER = "http://my.meteoblue.com/packages/basic-1h_basic-day?";

    private static final String API_KEY = "apikey=aHijO4GsJcuzhvyA";

    public TriathlonPolicyProductController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Double> getForecast(String lat, String lon) {
        String llamada = CALL_WEATHER + "lat=" + lat + "&" + "lon=" + lon + "&" + API_KEY;
        Weather b = restTemplate.getForObject(llamada, Weather.class);
        double maxProbability = findMax(b.getData_day().getPrecipitation_probability());
        // Devolver el valor máximo en el ResponseEntity
        return ResponseEntity.ok(maxProbability);
    }

    // Función que devuelve el valor más grande en una lista de Doubles
    private double findMax(ArrayList<Double> list) {
        // Verificar si la lista no está vacía
        if (list.isEmpty()) {
            // If we dont have any information about precipitation,
            // we return 0.0 No raining
            double v = 0.0d;
            return v;
        }
        // Use Collections.max to find higher value
        return Collections.max(list);
    }
}