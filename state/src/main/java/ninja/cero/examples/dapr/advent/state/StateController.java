package ninja.cero.examples.dapr.advent.state;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StateController {
    private RestTemplate restTemplate;

    @Value("http://localhost:${DAPR_HTTP_PORT}/v1.0/state")
    private String baseUrl;

    public StateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/write")
    public void write(@RequestBody Object message) {
        restTemplate.postForObject(baseUrl + "/statestore", message, Void.class);
    }

    @GetMapping("/read/{key}")
    public Object read(@PathVariable String key) {
        return restTemplate.getForObject(baseUrl + "/statestore" + "/" + key, Object.class);
    }
}
