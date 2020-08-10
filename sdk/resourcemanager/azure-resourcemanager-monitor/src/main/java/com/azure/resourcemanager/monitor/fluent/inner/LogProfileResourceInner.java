// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.monitor.fluent.inner;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.Resource;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.monitor.models.RetentionPolicy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The LogProfileResource model. */
@JsonFlatten
@Fluent
public class LogProfileResourceInner extends Resource {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(LogProfileResourceInner.class);

    /*
     * the resource id of the storage account to which you would like to send
     * the Activity Log.
     */
    @JsonProperty(value = "properties.storageAccountId")
    private String storageAccountId;

    /*
     * The service bus rule ID of the service bus namespace in which you would
     * like to have Event Hubs created for streaming the Activity Log. The rule
     * ID is of the format: '{service bus resource ID}/authorizationrules/{key
     * name}'.
     */
    @JsonProperty(value = "properties.serviceBusRuleId")
    private String serviceBusRuleId;

    /*
     * List of regions for which Activity Log events should be stored or
     * streamed. It is a comma separated list of valid ARM locations including
     * the 'global' location.
     */
    @JsonProperty(value = "properties.locations", required = true)
    private List<String> locations;

    /*
     * the categories of the logs. These categories are created as is
     * convenient to the user. Some values are: 'Write', 'Delete', and/or
     * 'Action.'
     */
    @JsonProperty(value = "properties.categories", required = true)
    private List<String> categories;

    /*
     * the retention policy for the events in the log.
     */
    @JsonProperty(value = "properties.retentionPolicy", required = true)
    private RetentionPolicy retentionPolicy;

    /**
     * Get the storageAccountId property: the resource id of the storage account to which you would like to send the
     * Activity Log.
     *
     * @return the storageAccountId value.
     */
    public String storageAccountId() {
        return this.storageAccountId;
    }

    /**
     * Set the storageAccountId property: the resource id of the storage account to which you would like to send the
     * Activity Log.
     *
     * @param storageAccountId the storageAccountId value to set.
     * @return the LogProfileResourceInner object itself.
     */
    public LogProfileResourceInner withStorageAccountId(String storageAccountId) {
        this.storageAccountId = storageAccountId;
        return this;
    }

    /**
     * Get the serviceBusRuleId property: The service bus rule ID of the service bus namespace in which you would like
     * to have Event Hubs created for streaming the Activity Log. The rule ID is of the format: '{service bus resource
     * ID}/authorizationrules/{key name}'.
     *
     * @return the serviceBusRuleId value.
     */
    public String serviceBusRuleId() {
        return this.serviceBusRuleId;
    }

    /**
     * Set the serviceBusRuleId property: The service bus rule ID of the service bus namespace in which you would like
     * to have Event Hubs created for streaming the Activity Log. The rule ID is of the format: '{service bus resource
     * ID}/authorizationrules/{key name}'.
     *
     * @param serviceBusRuleId the serviceBusRuleId value to set.
     * @return the LogProfileResourceInner object itself.
     */
    public LogProfileResourceInner withServiceBusRuleId(String serviceBusRuleId) {
        this.serviceBusRuleId = serviceBusRuleId;
        return this;
    }

    /**
     * Get the locations property: List of regions for which Activity Log events should be stored or streamed. It is a
     * comma separated list of valid ARM locations including the 'global' location.
     *
     * @return the locations value.
     */
    public List<String> locations() {
        return this.locations;
    }

    /**
     * Set the locations property: List of regions for which Activity Log events should be stored or streamed. It is a
     * comma separated list of valid ARM locations including the 'global' location.
     *
     * @param locations the locations value to set.
     * @return the LogProfileResourceInner object itself.
     */
    public LogProfileResourceInner withLocations(List<String> locations) {
        this.locations = locations;
        return this;
    }

    /**
     * Get the categories property: the categories of the logs. These categories are created as is convenient to the
     * user. Some values are: 'Write', 'Delete', and/or 'Action.'.
     *
     * @return the categories value.
     */
    public List<String> categories() {
        return this.categories;
    }

    /**
     * Set the categories property: the categories of the logs. These categories are created as is convenient to the
     * user. Some values are: 'Write', 'Delete', and/or 'Action.'.
     *
     * @param categories the categories value to set.
     * @return the LogProfileResourceInner object itself.
     */
    public LogProfileResourceInner withCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    /**
     * Get the retentionPolicy property: the retention policy for the events in the log.
     *
     * @return the retentionPolicy value.
     */
    public RetentionPolicy retentionPolicy() {
        return this.retentionPolicy;
    }

    /**
     * Set the retentionPolicy property: the retention policy for the events in the log.
     *
     * @param retentionPolicy the retentionPolicy value to set.
     * @return the LogProfileResourceInner object itself.
     */
    public LogProfileResourceInner withRetentionPolicy(RetentionPolicy retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (locations() == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        "Missing required property locations in model LogProfileResourceInner"));
        }
        if (categories() == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        "Missing required property categories in model LogProfileResourceInner"));
        }
        if (retentionPolicy() == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        "Missing required property retentionPolicy in model LogProfileResourceInner"));
        } else {
            retentionPolicy().validate();
        }
    }
}