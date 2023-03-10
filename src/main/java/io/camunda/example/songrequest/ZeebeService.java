package io.camunda.example.songrequest;

import io.camunda.zeebe.client.CredentialsProvider;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.response.PublishMessageResponse;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class ZeebeService {

  public ZeebeClient fromAppConstants() {
    CredentialsProvider cp =
        CredentialsProvider.newCredentialsProviderBuilder()
            .audience(
                String.format(
                    "%s.%s.%s",
                    AppConstants.ZEEBE_CLUSTER_ID,
                    AppConstants.DEFAULT_REGION,
                    AppConstants.BASE_ADDRESS))
            .clientId(AppConstants.ZEEBE_CLIENT_ID)
            .clientSecret(AppConstants.ZEEBE_CLIENT_SECRET)
            .authorizationServerUrl(AppConstants.BASE_AUTH_URL)
            .credentialsCachePath("/tmp/zeebe/cache")
            .build();

    return ZeebeClient.newClientBuilder()
        .credentialsProvider(cp)
        .gatewayAddress(AppConstants.ZEEBE_ADDRESS)
        .build();
  }

  ZeebeClient zeebeClient;

  public ZeebeService() {
    zeebeClient = fromAppConstants();
  }

  public long createInstance(Map<String, Object> variables) {

    final ProcessInstanceEvent event =
        zeebeClient
            .newCreateInstanceCommand()
            .bpmnProcessId(AppConstants.PROCESS_ID)
            .latestVersion()
            .variables(variables)
            .send()
            .join();

    return event.getProcessDefinitionKey();
  }

  public long sendMessage(Map<String, Object> variables) {

    String messageName = (String) variables.get("messageName");
    String correlationKey = (String) variables.get("correlationKey");

    final PublishMessageResponse response =
        zeebeClient
            .newPublishMessageCommand()
            .messageName(messageName)
            .correlationKey(correlationKey)
            .variables(variables)
            .send()
            .join();

    return response.getMessageKey();
  }
}
