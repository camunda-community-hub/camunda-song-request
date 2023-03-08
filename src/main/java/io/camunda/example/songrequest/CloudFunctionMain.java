package io.camunda.example.songrequest;

import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudFunctionMain {

  public static void main(String[] args) {
    SpringApplication.run(CloudFunctionMain.class, args);
  }

  @Bean
  public Function<String, String> function() {
    return value -> value.toUpperCase();
  }
}
