package ninja.cero.examples.dapr.advent.publish;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping("/publish2")
    public void publish(@RequestBody Object message, @RequestHeader("traceparent") String traceparent) {
        System.out.println(traceparent);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("traceparent", traceparent);
        HttpEntity<?> request = new HttpEntity<>(message, httpHeaders);

        restTemplate.exchange(pubsubUrl, HttpMethod.POST, request, Void.class);
    }
}
