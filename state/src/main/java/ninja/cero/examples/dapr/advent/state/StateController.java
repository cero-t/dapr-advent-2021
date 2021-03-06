package ninja.cero.examples.dapr.advent.state;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class StateController {
    private RestTemplate restTemplate;

    @Value("http://localhost:${DAPR_HTTP_PORT}/v1.0/state/statestore")
    private String stateUrl;

    public StateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/write")
    public void write(@RequestBody Object message) {
        restTemplate.postForObject(stateUrl, message, Void.class);
    }

    @GetMapping("/read/{key}")
    public Object read(@PathVariable String key) {
        return restTemplate.getForObject(stateUrl + "/" + key, Object.class);
    }
}
