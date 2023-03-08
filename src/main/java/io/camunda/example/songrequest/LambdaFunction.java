package io.camunda.example.songrequest;

// import com.amazonaws.services.lambda.runtime.Context;
// import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.HashMap;
import java.util.Map;

public class LambdaFunction
// implements RequestHandler<Map<String, Object>, Map<String, Object>>
{

  private Map<String, Object> createInstance(Map<String, Object> variables) {

    ZeebeService zeebeService = new ZeebeService(ZeebeService.fromAppConstants());
    long processInstanceKey = zeebeService.createInstance(variables);

    Map<String, Object> result = new HashMap<>();
    result.put("createInstance", processInstanceKey);
    return result;
  }

  private Map<String, Object> submitForm(Map<String, Object> variables) {
    Map<String, Object> result = new HashMap<>();
    result.put("bookHotelResult", true);
    return result;
  }

  /*@Override
  public Map<String, Object> handleRequest(Map<String, Object> variables, Context context) {

    String eventType = (String) variables.get("event");

    if (eventType != null) {
      if (eventType.equals("createInstance")) {
        return createInstance(variables);
      } else if (eventType.equals("submitForm")) {
        return submitForm(variables);
      }
    }

    return null;
  } */
}
