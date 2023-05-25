/**
 * This is a generic function that sends data to a url which publishes a message to Camunda 8 environment.
 *
 * A POST request is sent to a url which represents a REST Endpoint.
 * The code running at the REST Endpoint will use a Zeebe Client to publish a message using `messageName`
 * and `correlationKey`. The `formDataJson` argument will be stored as Camunda process instance variables
 *
 * To use this in your own Google Form, copy and past it as the contents of `Code.gs` and be sure to change the
 * url to point to your own REST API endpoint.
 */
function publishCamundaMessage(url, camundaMessageName, correlationKey, formDataJson) {

  var data = {
    'messageName': camundaMessageName,
    'correlationKey': correlationKey,
    'formData': formDataJson
  };

  var options = {
    'method': 'post',
    'contentType': 'application/json',
    'payload': JSON.stringify(data)
  };

  console.log("Attempting POST");
  console.log(options);

  var response = UrlFetchApp.fetch(url, options);
  console.log(response.getContentText());

  return response;
}

/**
 * This is very simplistic converstion of a FormResponse to json
 */
function formResponseToJson(formResponse) {

  var result = {
    'itemResponses': []
  }

  var itemResponses = formResponse.getItemResponses();
  for (var i=0; i<itemResponses.length; i++) {
    var itemResponse = itemResponses[i];
    var answer = itemResponse.getResponse();

    var itemResponseJson = {
      'title': itemResponse.getItem().getTitle(),
      'answer': answer
    };
    result.itemResponses.push(itemResponseJson);
  }

  return result;
}

/**
 * This is the function that is registered to run whenever the Google Form is submitted
 */
function onFormSubmit(e) {
  var formResponse = e.response;

  var url = 'https://us-central1-YOUR_PROJECT.cloudfunctions.net/YOUR_FUNCTION';
  var messageName = 'Message_formSubmitted';
  var correlationKey = formResponse.getItemResponses()[2].getResponse();
  console.log(correlationKey);

  return publishCamundaMessage(url, messageName, correlationKey, formResponseToJson(formResponse));

}
