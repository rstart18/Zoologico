package com.nelumbo.zoo.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class  EmailReplieOfComment{

    @Async
    public static void sendEmail(String receiver, String emitterName, String replie) {
        try {
            System.out.println("Iniciando envío de correo asincrónico");
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String url = "http://localhost:5000/mail-replie-of-comment";

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("receiver", receiver);
            requestBody.put("emitterName", emitterName);
            requestBody.put("replie", replie);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            System.out.println("Envío de correo asincrónico completado");
        } catch (Exception ex) {
            System.out.println("El envío de correo asincróno no pudo ser completado.");
            ex.printStackTrace();
        }
    }
}
