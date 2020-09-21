// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.cdn.fluent.inner;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.ProxyResource;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.cdn.models.IpAddressGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The EdgeNode model. */
@JsonFlatten
@Fluent
public class EdgeNodeInner extends ProxyResource {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(EdgeNodeInner.class);

    /*
     * List of ip address groups.
     */
    @JsonProperty(value = "properties.ipAddressGroups")
    private List<IpAddressGroup> ipAddressGroups;

    /**
     * Get the ipAddressGroups property: List of ip address groups.
     *
     * @return the ipAddressGroups value.
     */
    public List<IpAddressGroup> ipAddressGroups() {
        return this.ipAddressGroups;
    }

    /**
     * Set the ipAddressGroups property: List of ip address groups.
     *
     * @param ipAddressGroups the ipAddressGroups value to set.
     * @return the EdgeNodeInner object itself.
     */
    public EdgeNodeInner withIpAddressGroups(List<IpAddressGroup> ipAddressGroups) {
        this.ipAddressGroups = ipAddressGroups;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (ipAddressGroups() != null) {
            ipAddressGroups().forEach(e -> e.validate());
        }
    }
}