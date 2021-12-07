package ninja.cero.examples.dapr.advent.tracing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class TracingController {
    private RestTemplate restTemplate;

    @Value("http://localhost:${DAPR_HTTP_PORT}/v1.0/invoke/hello-app/method")
    private String helloUrl;

    @Value("http://localhost:${DAPR_HTTP_PORT}/v1.0/invoke/publish-app/method")
    private String publishUrl;

    public TracingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/invokeHello")
    public Map<String, ?> invokeHello() {
        Map<?, ?> result = restTemplate.getForObject(helloUrl + "/hello", Map.class);
        return Map.of("baseUrl", helloUrl, "remoteMessage", result);
    }

    @GetMapping("/invokeHello2")
    public Map<String, ?> invokeHello(@RequestHeader("traceparent") String traceparent) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("traceparent", traceparent);
        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        Map<?, ?> result = restTemplate.exchange(helloUrl + "/hello", HttpMethod.GET, request, Map.class).getBody();
        return Map.of("baseUrl", helloUrl, "remoteMessage", result);
    }

    @PostMapping("/invokePublish")
    public void invokePublish(@RequestBody Object message, @RequestHeader("traceparent") String traceparent) {
        System.out.println(traceparent);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("traceparent", traceparent);
        HttpEntity<?> request = new HttpEntity<>(message, httpHeaders);

        restTemplate.exchange(publishUrl + "/publish2", HttpMethod.POST, request, Void.class);
    }
}
