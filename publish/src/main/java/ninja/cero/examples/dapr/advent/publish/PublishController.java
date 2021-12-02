package ninja.cero.examples.dapr.advent.publish;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PublishController {
    private RestTemplate restTemplate;

    @Value("${pubsubUrl}")
    private String pubsubUrl;

    public PublishController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/publish")
    public void publish(@RequestBody Object message) {
        restTemplate.postForObject(pubsubUrl, message, Void.class);
    }
}
