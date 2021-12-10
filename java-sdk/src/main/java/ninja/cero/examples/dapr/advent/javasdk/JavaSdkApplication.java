package ninja.cero.examples.dapr.advent.javasdk;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavaSdkApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaSdkApplication.class, args);
    }

    @Bean
    DaprClient daprClient() {
        return new DaprClientBuilder().build();
    }
}
