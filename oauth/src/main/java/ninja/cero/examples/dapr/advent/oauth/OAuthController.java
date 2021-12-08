package ninja.cero.examples.dapr.advent.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class OAuthController {
    private RestTemplate restTemplate;

    @Value("https://api.github.com/user")
    private String userApi;

    public OAuthController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello, world!");
    }

    @GetMapping("/token")
    public Map<String, ?> token(@RequestHeader("x-oauth-token") String oauthToken) {
        return Map.of("x-oauth-token", oauthToken);
    }

    @GetMapping("/user")
    public Map<?, ?> user(@RequestHeader("x-oauth-token") String oauthToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", oauthToken);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(userApi, HttpMethod.GET, request, Map.class).getBody();
    }
}
