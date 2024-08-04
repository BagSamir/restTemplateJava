package com.example.demo;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DemoApplication {

    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders headers = new HttpHeaders();
    private String sessionId;


    public void fetchAllUsers() {
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        System.out.println("Fetched users: " + response.getBody());

        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (cookies != null && !cookies.isEmpty()) {
            sessionId = cookies.get(0).split(";")[0];
            headers.add(HttpHeaders.COOKIE, sessionId);
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
    }

    public String addUser() {
        String body = "{\"id\":3,\"name\":\"James\",\"lastName\":\"Brown\",\"age\":30}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, String.class);
        System.out.println("User added: " + response.getBody());
        return response.getBody();
    }

    public String updateUser() {
        String body = "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":30}";
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, entity, String.class);
        System.out.println("User updated: " + response.getBody());
        return response.getBody();
    }

    public String deleteUser() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/3", HttpMethod.DELETE, entity, String.class);
        System.out.println("User deleted: " + response.getBody());
        return response.getBody();
    }

    public static void main(String[] args) {
        DemoApplication client = new DemoApplication();

        client.fetchAllUsers();
        String part1 = client.addUser();
        String part2 = client.updateUser();
        String part3 = client.deleteUser();

        String finalCode = part1 + part2 + part3;
        System.out.println("Final code: " + finalCode);
        System.out.println("Character count: " + finalCode.length());
    }
}

