package ninja.cero.examples.dapr.advent.secrets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class SecretsController {
    private RestTemplate restTemplate;

    @Value("http://localhost:${DAPR_HTTP_PORT}/v1.0/secrets/my-secret")
    private String secretsUrl;

    public SecretsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{key}")
    public String read(@PathVariable String key) {
        return restTemplate.getForObject(secretsUrl + "/" + key, String.class);
    }
}
