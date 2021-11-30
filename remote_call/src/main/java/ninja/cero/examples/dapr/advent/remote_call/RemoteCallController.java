package ninja.cero.examples.dapr.advent.remote_call;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class RemoteCallController {
    private RestTemplate restTemplate;

    @Value("${baseUrl}")
    private String baseUrl;

    public RemoteCallController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/remote_call")
    public Map<String, ?> hello() {
        Map<?, ?> result = restTemplate.getForObject(baseUrl + "/hello", Map.class);
        return Map.of("baseUrl", baseUrl, "remoteMessage", result);
    }
}
