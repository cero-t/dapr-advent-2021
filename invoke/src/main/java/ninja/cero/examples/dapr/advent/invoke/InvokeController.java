package ninja.cero.examples.dapr.advent.invoke;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class InvokeController {
    private RestTemplate restTemplate;

    @Value("${baseUrl}")
    private String baseUrl;

    public InvokeController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/invokeHello")
    public Map<String, ?> invokeHello() {
        Map<?, ?> result = restTemplate.getForObject(baseUrl + "/hello", Map.class);
        return Map.of("baseUrl", baseUrl, "remoteMessage", result);
    }
}
