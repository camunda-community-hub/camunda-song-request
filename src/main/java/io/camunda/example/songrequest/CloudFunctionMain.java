package io.camunda.example.songrequest;

import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudFunctionMain {

  @Autowired ZeebeService zeebeService;

  public static void main(String[] args) {
    SpringApplication.run(CloudFunctionMain.class, args);
  }

  @Bean
  public Function<Map<String, Object>, Long> function() {
    return variables -> zeebeService.sendMessage(variables);
  }
}
