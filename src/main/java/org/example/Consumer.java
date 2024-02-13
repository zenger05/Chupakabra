package org.example;

import org.example.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";
        HttpEntity<String> response = restTemplate.getForEntity(url, String.class);
        HttpHeaders headers2 = response.getHeaders();
        List<String> cookies = headers2.get("Set-Cookie");
        String sessionCookie = cookies.get(0);
        String sessionId = sessionCookie.substring(0, sessionCookie.indexOf(';'));
        HttpHeaders headers = new HttpHeaders();
        headers.set("cookie", sessionId);


        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 22);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response2 = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(response2.getBody());

        user.setId(3L);
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> request2 = new HttpEntity<>(user, headers);
        ResponseEntity<String> response3 = restTemplate.exchange(url, HttpMethod.PUT, request2, String.class);
        String response3String = response3.getBody();
        System.out.println(response3String);

        url = url + "/" +user.getId().toString();
        HttpEntity<User> entity = new HttpEntity<>(headers);
        String response4 = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class).getBody();
        System.out.println(response4);
    }


}
