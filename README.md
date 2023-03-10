# Song Requests in Camunda 8

<img src="https://img.shields.io/badge/Tutorial%20Reference%20Project-Tutorials%20for%20getting%20started%20with%20Camunda-%2338A3E1" alt="A blue badge that reads: 'Tutorial Reference Project - Tutorials for getting started with Camunda'">

This is an example of how to use the [Camunda 8 Platform](https://docs.camunda.io) to collect song requests from friends before a party.

Here's the idea: Let's say you're in charge of planning a family reunion, or a school dance. You're planning to have a DJ or Band. You'd like to allow your friends to recommend songs for the party. One simple solution is to use a Google Form. But that leaves a lot of manual work for  you. You'll have to follow up with everyone to see if they were able to access the form, you might want to manual periodic reminders and then you'll have to manually send the results to the Band or DJ. Let's see how we can improve and automate this process. We can use Camunda Platform 8 to not only make this a better experience for your friends, but this should also make less work for you. 

This example shows how easy it is to orchestrate a variety of services with Camunda 8. We'll use Send grid to send emails, use a Google Form and Cloud Function to get song requests, and even use OpenAI to check if the songs are appropriate for this party.

## How to Run

Here are the steps to see this working: 

1. Sign up for a trial account on https://camunda.io. Create a cluster and create [API Client Credentials](https://docs.camunda.io/docs/guides/setup-client-connection-credentials).
2. Edit `AppConstants.java` and add your cluster id, client id, and secret. Note that this code can be improved to use Environment variables, but for now, these values are hard coded. 
3. Use the following command to compile and build a jar file: 
```shell
mvn clean install
```
4. If you don't have one already, create a GCP account and setup the `gcloud` command line tool
5. Deploy this project as a Google Cloud Function like so. 
```shell
gcloud functions deploy dave-song-request-gcp-http \
--entry-point org.springframework.cloud.function.adapter.gcp.GcfJarLauncher \
--runtime java17 \
--trigger-http \
--source target/deploy \
--memory 512MB
```
6. If you don't have one already, create an OpenAI account and create an OpenAI API Key
8. Upload the `src/main/resources/song-request.bpm` to your SaaS web modeler (or open in desktop modeler). 
9. Deploy the Song Request process to your Camunda 8 SaaS Cluster. 
11. Create the following secrets in your Camunda 8 SaaS environment.

```shell
SENDGRID_API_KEY
OPENAI_API_KEY
```

11. Run the Lambda function passing a payload like the following. This should create an instance of the Trip Booking Process Instance: 

## Inputs

- SENDGRID_API_KEY (secret)
- person.name
- person.email
- google.form.url https://docs.google.com/forms/d/e/1FAIpQLSdA11QjZGguCtKnaEpssxt7lsgGv42D_Z22qEQ3jqInQ58PHw/viewform?usp=sf_link
- cloud function https://us-central1-camunda-researchanddevelopment.cloudfunctions.net/dave-song-request-gcp-http

## Payload to start an Instance: 

```json
{
  "person": {
    "name": "Dave",
    "email": "david.paroulek@camunda.com"
  },
  "google": {
    "form": {
      "url": "https://docs.google.com/forms/d/e/1FAIpQLSdA11QjZGguCtKnaEpssxt7lsgGv42D_Z22qEQ3jqInQ58PHw/viewform?usp=sf_link"
    }
  }
}
```

## Payload to submit Song Request: 

```json
{
  "messageName": "Message_formSubmitted",
  "correlationKey": "david.paroulek@camunda.com",
  "formData": {
    "artist": "Beatles",
    "songTitle": "Here comes the sun"
  }
}
```

## Test Locally

```shell
mvn function:run
```

