# Azure Metrics Advisor client library for Java
Azure Metrics Advisor is a new Cognitive  Service that uses time series based decision AI to identify and assist
trouble shooting the incidents of online services, and monitor the business health by automating the slice and dice
of business metrics.

[Source code][source_code] | [Package (Maven)]<!--TODO: (once available)--> | [API reference documentation][api_reference_doc] | [Product Documentation][product_documentation] | [Samples][samples]

## Getting started

### Prerequisites
- Java Development Kit [JDK][jdk_link] with version 8 or above
- [Azure Subscription][azure_subscription]
- [Cognitive Services or Metrics Advisor account][metrics_advisor_account] to use this package.

### Include the Package
**Note:** This beta version targets Azure Metrics Advisor service API version v2.0-preview.

[//]: # ({x-version-update-start;com.azure:azure-ai-metricsadvisor;current})
```xml
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-ai-metricsadvisor</artifactId>
    <version>1.0.0-beta.1</version>
</dependency>
```
[//]: # ({x-version-update-end})

#### Create a Metrics Advisor resource

### Authenticate the client
You will need two keys to authenticate the client:

- The subscription key to your Metrics Advisor resource. You can find this in the Keys and Endpoint section of your resource in the Azure portal.
- The API key for your Metrics Advisor instance. You can find this on the web portal for Metrics Advisor, in API keys on the left navigation menu.

We can use the keys to create a new `MetricsAdvisorClient` or `MetricsAdvisorAdministrationClient`.

##### Looking up the endpoint
You can find the **endpoint** for your Metric Advisor resource in the [Azure Portal][azure_portal],
or [Azure CLI][azure_cli_endpoint].
```bash
# Get the endpoint for the resource
az cognitiveservices account show --name "resource-name" --resource-group "resource-group-name" --query "endpoint"
```

#### Create a Metrics Advisor client using MetricsAdvisorKeyCredential
<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L66-L70 -->
```java
MetricsAdvisorKeyCredential credential = new MetricsAdvisorKeyCredential("subscription_key", "api_key");
MetricsAdvisorClient metricsAdvisorClient = new MetricsAdvisorClientBuilder()
    .endpoint("{endpoint}")
    .credential(credential)
    .buildClient();
```

#### Create a Metrics Administration client using MetricsAdvisorKeyCredential
<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L77-L82 -->
```java
MetricsAdvisorKeyCredential credential = new MetricsAdvisorKeyCredential("subscription_key", "api_key");
MetricsAdvisorAdministrationClient metricsAdvisorAdministrationClient =
    new MetricsAdvisorAdministrationClientBuilder()
        .endpoint("{endpoint}")
        .credential(credential)
        .buildClient();
```

## Key concepts
### MetricsAdvisorClient
`MetricsAdvisorClient` helps with:

- listing incidents
- listing root causes of incidents
- retrieving original time series data and time series data enriched by the service.
- listing alerts
- adding feedback to tune your model

### MetricsAdvisorAdministrationClient
`MetricsAdvisorAdministrationClient` allows you to

- manage data feeds
- configure anomaly detection configurations
- configure anomaly alerting configurations
- manage hooks

### Data feed
A `data feed` is what Metrics Advisor ingests from the user-specified data source such as Cosmos structure stream, SQL query result, and so on.
A data feed contains rows of timestamps, zero or more dimensions, one or more metrics. Therefore, multiple metrics could share the same data source and even the same data feed.

### Metric
A `metric` is a quantifiable measure that is used to track and assess the status of a specific business process. It can be a combination of multiple time series values divided by dimensions, for example user count for a web vertical and en-us market.

### Dimension
A `dimension` is one or more categorical values. The combination of those values identifies a particular univariate time series, for example: country, language, tenant, and so on.

### Time series
A time series is a series of data points indexed (or listed or graphed) in time order. Most commonly, a time series is a sequence taken at successive equally spaced points in time. Therefore, it is a sequence of discrete-time data.

### AnomalyDetectionConfiguration
A detection configuration for a time series to identify if the data point is detected as an Anomaly.

### Incident
`Incidents` are generated for series when it has an Anomaly depending on the applied Anomaly detection configurations.

### Alert
`Alerts` can be configured to be triggered when certain anomalies are met. You can set multiple alerts with different settings. For example, you could create an alert for anomalies with lower business impact, and another for more important alerts.

### Hook
`Hook` is the entry point that allows the users to subscribe to real-time alerts. These alerts are sent over the internet, using a Hook.

## Examples

* [Add a data feed from a sample or data source](#add-a-data-feed-from-a-sample-or-data-source "Add a data feed from a sample or data source")
* [Check ingestion status](#check-ingestion-status "Check ingestion status")
* [Configure anomaly detection configuration](#configure-anomaly-detection-configuration "Configure anomaly detection configuration")
* [Add hooks for receiving anomaly alerts](#add-hooks-for-receiving-anomaly-alerts "Add hooks for receiving anomaly alerts")
* [Configure alert configuration](#configure-alert-configuration "Configure alert configuration")
* [Query anomaly detection results](#query-anomaly-detection-results "Query anomaly detection results")

#### Add a data feed from a sample or data source
This example ingests the user specified `SQLServerDataFeedSource` data feed source data to the service.
<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L89-L122 -->
```java
final DataFeed createdSqlDataFeed = metricsAdvisorAdministrationClient.createDataFeed(
    "My data feed name",
    new SQLServerDataFeedSource("sql_server_connection_string", "query"),
    new DataFeedGranularity().setGranularityType(DataFeedGranularityType.DAILY),
    new DataFeedSchema(Arrays.asList(
        new Metric().setName("cost"), new Metric().setName("revenue")))
        .setDimensions(Arrays.asList(
            new Dimension().setName("category"), new Dimension().setName("city"))),
    new DataFeedIngestionSettings(OffsetDateTime.parse("2020-01-01T00:00:00Z")),
    new DataFeedOptions()
        .setDescription("My data feed description")
        .setRollupSettings(
            new DataFeedRollupSettings()
                .setAutoRollup(DataFeedAutoRollUpMethod.SUM, Arrays.asList("cost"), "__CUSTOM_SUM__"))
        .setMissingDataPointFillSettings(
            new DataFeedMissingDataPointFillSettings()
                .setFillType(DataSourceMissingDataPointFillType.SMART_FILLING))
        .setAccessMode(DataFeedAccessMode.PUBLIC));

System.out.printf("Data feed Id : %s%n", createdSqlDataFeed.getId());
System.out.printf("Data feed name : %s%n", createdSqlDataFeed.getName());
System.out.printf("Is the query user is one of data feed administrator : %s%n", createdSqlDataFeed.isAdmin());
System.out.printf("Data feed created time : %s%n", createdSqlDataFeed.getCreatedTime());
System.out.printf("Data feed granularity type : %s%n",
    createdSqlDataFeed.getGranularity().getGranularityType());
System.out.printf("Data feed granularity value : %d%n",
    createdSqlDataFeed.getGranularity().getCustomGranularityValue());
System.out.println("Data feed related metric Ids:");
createdSqlDataFeed.getMetricIds().forEach(metricId -> System.out.println(metricId));
System.out.printf("Data feed source type: %s%n", createdSqlDataFeed.getSourceType());
if (SQL_SERVER_DB.equals(createdSqlDataFeed.getSourceType())) {
    System.out.printf("Data feed sql server query: %s%n",
        ((SQLServerDataFeedSource) createdSqlDataFeed.getSource()).getQuery());
}
```
#### Check ingestion status
This example checks the ingestion status of a previously provided data feed source.

<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L130-L139 -->
```java
String dataFeedId = "3d48er30-6e6e-4391-b78f-b00dfee1e6f5";

metricsAdvisorAdministrationClient.listDataFeedIngestionStatus(
    dataFeedId, new ListDataFeedIngestionOptions(
        OffsetDateTime.parse("2020-01-01T00:00:00Z"), OffsetDateTime.parse("2020-09-09T00:00:00Z"))
).forEach(dataFeedIngestionStatus -> {
    System.out.printf("Message : %s%n", dataFeedIngestionStatus.getMessage());
    System.out.printf("Timestamp value : %s%n", dataFeedIngestionStatus.getTimestamp());
    System.out.printf("Status : %s%n", dataFeedIngestionStatus.getStatus());
});
```
#### Configure anomaly detection configuration
This example demonstrates how a user can configure an anomaly detection configuration for their data.

<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L146-L176 -->
```java
String metricId = "3d48er30-6e6e-4391-b78f-b00dfee1e6f5";

ChangeThresholdCondition changeThresholdCondition = new ChangeThresholdCondition()
    .setAnomalyDetectorDirection(AnomalyDetectorDirection.BOTH)
    .setChangePercentage(20)
    .setShiftPoint(10)
    .setWithinRange(true)
    .setSuppressCondition(new SuppressCondition().setMinNumber(1).setMinRatio(2));

HardThresholdCondition hardThresholdCondition = new HardThresholdCondition()
    .setAnomalyDetectorDirection(AnomalyDetectorDirection.DOWN)
    .setLowerBound(5.0)
    .setSuppressCondition(new SuppressCondition().setMinNumber(1).setMinRatio(1));

SmartDetectionCondition smartDetectionCondition = new SmartDetectionCondition()
    .setAnomalyDetectorDirection(AnomalyDetectorDirection.UP)
    .setSensitivity(10.0)
    .setSuppressCondition(new SuppressCondition().setMinNumber(1).setMinRatio(2));

final AnomalyDetectionConfiguration anomalyDetectionConfiguration =
    metricsAdvisorAdministrationClient.createMetricAnomalyDetectionConfiguration(
        metricId,
        new AnomalyDetectionConfiguration("My Anomaly detection configuration")
            .setDescription("anomaly detection config description")
            .setWholeSeriesDetectionCondition(
                new MetricWholeSeriesDetectionCondition()
                    .setChangeThresholdCondition(changeThresholdCondition)
                    .setHardThresholdCondition(hardThresholdCondition)
                    .setSmartDetectionCondition(smartDetectionCondition)
                    .setCrossConditionOperator(DetectionConditionsOperator.OR))
    );
```

### Add hooks for receiving anomaly alerts
This example creates an email hook that receives anomaly incident alerts.
<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L183-L194 -->
```java
Hook emailHook = new EmailHook("email hook")
    .setDescription("my email hook")
    .addEmailToAlert("alertme@alertme.com")
    .setExternalLink("https://adwiki.azurewebsites.net/articles/howto/alerts/create-hooks.html");

final Hook hook = metricsAdvisorAdministrationClient.createHook(emailHook);
EmailHook createdEmailHook = (EmailHook) hook;
System.out.printf("Hook Id: %s%n", createdEmailHook.getId());
System.out.printf("Hook Name: %s%n", createdEmailHook.getName());
System.out.printf("Hook Description: %s%n", createdEmailHook.getDescription());
System.out.printf("Hook External Link: %s%n", createdEmailHook.getExternalLink());
System.out.printf("Hook Emails: %s%n", String.join(",", createdEmailHook.getEmailsToAlert()));
```

#### Configure alert configuration
This example demonstrates how a user can configure an alerting configuration for detected anomalies in their data.

<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L201-L218 -->
```java
String detectionConfigurationId1 = "9ol48er30-6e6e-4391-b78f-b00dfee1e6f5";
String detectionConfigurationId2 = "3e58er30-6e6e-4391-b78f-b00dfee1e6f5";
String hookId1 = "5f48er30-6e6e-4391-b78f-b00dfee1e6f5";
String hookId2 = "8i48er30-6e6e-4391-b78f-b00dfee1e6f5";

final AnomalyAlertConfiguration anomalyAlertConfiguration
    = metricsAdvisorAdministrationClient.createAnomalyAlertConfiguration(
    new AnomalyAlertConfiguration("My Alert config name")
        .setDescription("alert config description")
        .setMetricAlertConfigurations(Arrays.asList(
            new MetricAnomalyAlertConfiguration(detectionConfigurationId1,
                MetricAnomalyAlertScope.forWholeSeries()),
            new MetricAnomalyAlertConfiguration(detectionConfigurationId2,
                MetricAnomalyAlertScope.forWholeSeries())
                .setAlertConditions(new MetricAnomalyAlertConditions()
                    .setSeverityCondition(new SeverityCondition().setMaxAlertSeverity(Severity.HIGH)))))
        .setCrossMetricsOperator(MetricAnomalyAlertConfigurationsOperator.AND)
        .setIdOfHooksToAlert(Arrays.asList(hookId1, hookId2)));
```

#### Query anomaly detection results
This example demonstrates how a user can query alerts triggered for an anomaly detection configuration and get anomalies for that alert.

```java
String alertConfigurationId = "9ol48er30-6e6e-4391-b78f-b00dfee1e6f5";
metricsAdvisorClient.listAlerts(
    alertConfigurationId,
    new ListAlertOptions(OffsetDateTime.parse("2020-01-01T00:00:00Z"), OffsetDateTime.now(),
        TimeMode.ANOMALY_TIME))
    .forEach(alert -> {
        System.out.printf("Alert Id: %s%n", alert.getId());
        System.out.printf("Alert created on: %s%n", alert.getCreatedTime());

        // List anomalies for returned alerts
        metricsAdvisorClient.listAnomaliesForAlert(
            alertConfigurationId,
            alert.getId())
            .forEach(anomaly -> {
                System.out.printf("Anomaly was created on: %s%n", anomaly.getCreatedTime());
                System.out.printf("Anomaly severity: %s%n", anomaly.getSeverity().toString());
                System.out.printf("Anomaly status: %s%n", anomaly.getStatus());
                System.out.printf("Anomaly related series key: %s%n", anomaly.getSeriesKey().asMap());
            });
    });
```

## Troubleshooting
### General
Metrics Advisor clients raises `HttpResponseException` [exceptions][http_response_exception]. For example, if you try
to provide a non existing feedback Id an `HttpResponseException` would be raised with an error indicating the failure cause.
In the following code snippet, the error is handled
gracefully by catching the exception and display the additional information about the error.
<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L251-L255 -->
```java
try {
    metricsAdvisorClient.getMetricFeedback("non_existing_feedback_id");
} catch (HttpResponseException e) {
    System.out.println(e.getMessage());
}
```


### Enable client logging
Azure SDKs for Java offer a consistent logging story to help aid in troubleshooting application errors and expedite
their resolution. The logs produced will capture the flow of an application before reaching the terminal state to help
locate the root issue. View the [logging][logging] wiki for guidance about enabling logging.

### Default HTTP Client
All client libraries by default use the Netty HTTP client. Adding the above dependency will automatically configure
the client library to use the Netty HTTP client. Configuring or changing the HTTP client is detailed in the
[HTTP clients wiki][http_clients_wiki].

## Next steps
For more details see the [samples README][samples_readme].

#### Async APIs
All the examples shown so far have been using synchronous APIs, but we provide full support for async APIs as well.
You'll need to use `MetricsAdvisorAsyncClient`
<!-- embedme ./src/samples/java/com/azure/ai/metricsadvisor/ReadmeSamples.java#L262-L266 -->
```java
MetricsAdvisorKeyCredential credential = new MetricsAdvisorKeyCredential("subscription_key", "api_key");
MetricsAdvisorAsyncClient formRecognizerAsyncClient = new MetricsAdvisorClientBuilder()
    .credential(credential)
    .endpoint("{endpoint}")
    .buildAsyncClient();
```

### Additional documentation

For more extensive documentation on Azure Cognitive Services Metrics Advisor, see the [Metrics Advisor documentation][api_reference_doc].

## Contributing

This project welcomes contributions and suggestions. Most contributions require you to agree to a [Contributor License Agreement (CLA)][cla] declaring that you have the right to, and actually do, grant us the rights to use your contribution.

When you submit a pull request, a CLA-bot will automatically determine whether you need to provide a CLA and decorate the PR appropriately (e.g., label, comment). Simply follow the instructions provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct][coc]. For more information see the [Code of Conduct FAQ][coc_faq] or contact [opencode@microsoft.com][coc_contact] with any additional questions or comments.

<!-- LINKS -->
[aad_authorization]: https://docs.microsoft.com/azure/cognitive-services/authentication#authenticate-with-azure-active-directory
[key]: https://docs.microsoft.com/azure/cognitive-services/cognitive-services-apis-create-account?tabs=multiservice%2Cwindows#get-the-keys-for-your-resource
[api_reference_doc]: https://docs.microsoft.com/azure/cognitive-services/metrics-advisor/glossary
[azure_identity_credential_type]: https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/identity/azure-identity#credentials
[azure_cli]: https://docs.microsoft.com/azure/cognitive-services/cognitive-services-apis-create-account-cli?tabs=windows
[azure_cli_endpoint]: https://docs.microsoft.com/cli/azure/cognitiveservices/account?view=azure-cli-latest#az-cognitiveservices-account-show
[azure_identity]: https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/identity/azure-identity#credentials
[azure_portal]: https://ms.portal.azure.com
[azure_subscription]: https://azure.microsoft.com/free
[cla]: https://cla.microsoft.com
[coc]: https://opensource.microsoft.com/codeofconduct/
[coc_faq]: https://opensource.microsoft.com/codeofconduct/faq/
[coc_contact]: mailto:opencode@microsoft.com
[create_new_resource]: https://docs.microsoft.com/azure/cognitive-services/cognitive-services-apis-create-account?tabs=multiservice%2Cwindows#create-a-new-azure-cognitive-services-resource
[jdk_link]: https://docs.microsoft.com/java/azure/jdk/?view=azure-java-stable
[metrics_advisor_account]: https://ms.portal.azure.com/#create/Microsoft.CognitiveServicesMetricsAdvisor
[product_documentation]: https://docs.microsoft.com/azure/cognitive-services/metrics-advisor/overview
[samples]: https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/metricsadvisor/azure-ai-metricsadvisor/src/samples
[samples_readme]: https://github.com/Azure/azure-sdk-for-java/blob/master/sdk/metricsadvisor/azure-ai-metricsadvisor/src/samples/README.md

![Impressions](https://azure-sdk-impressions.azurewebsites.net/api/impressions/azure-sdk-for-java%2Fsdk%metricsadvisor%2Fazure-ai-metricsadvisor%2FREADME.png)