/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.hybridcompute.v2020_08_02.implementation;

import com.microsoft.azure.management.hybridcompute.v2020_08_02.MachineExtension;
import com.microsoft.azure.arm.model.implementation.CreatableUpdatableImpl;
import rx.Observable;
import com.microsoft.azure.management.hybridcompute.v2020_08_02.MachineExtensionUpdate;
import java.util.Map;
import com.microsoft.azure.management.hybridcompute.v2020_08_02.MachineExtensionPropertiesInstanceView;
import rx.functions.Func1;

class MachineExtensionImpl extends CreatableUpdatableImpl<MachineExtension, MachineExtensionInner, MachineExtensionImpl> implements MachineExtension, MachineExtension.Definition, MachineExtension.Update {
    private final HybridComputeManager manager;
    private String resourceGroupName;
    private String name;
    private String extensionName;
    private MachineExtensionUpdate updateParameter;

    MachineExtensionImpl(String name, HybridComputeManager manager) {
        super(name, new MachineExtensionInner());
        this.manager = manager;
        // Set resource name
        this.extensionName = name;
        //
        this.updateParameter = new MachineExtensionUpdate();
    }

    MachineExtensionImpl(MachineExtensionInner inner, HybridComputeManager manager) {
        super(inner.name(), inner);
        this.manager = manager;
        // Set resource name
        this.extensionName = inner.name();
        // set resource ancestor and positional variables
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.id(), "resourceGroups");
        this.name = IdParsingUtils.getValueFromIdByName(inner.id(), "machines");
        this.extensionName = IdParsingUtils.getValueFromIdByName(inner.id(), "extensions");
        //
        this.updateParameter = new MachineExtensionUpdate();
    }

    @Override
    public HybridComputeManager manager() {
        return this.manager;
    }

    @Override
    public Observable<MachineExtension> createResourceAsync() {
        MachineExtensionsInner client = this.manager().inner().machineExtensions();
        return client.createOrUpdateAsync(this.resourceGroupName, this.name, this.extensionName, this.inner())
            .map(new Func1<MachineExtensionInner, MachineExtensionInner>() {
               @Override
               public MachineExtensionInner call(MachineExtensionInner resource) {
                   resetCreateUpdateParameters();
                   return resource;
               }
            })
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<MachineExtension> updateResourceAsync() {
        MachineExtensionsInner client = this.manager().inner().machineExtensions();
        return client.updateAsync(this.resourceGroupName, this.name, this.extensionName, this.updateParameter)
            .map(new Func1<MachineExtensionInner, MachineExtensionInner>() {
               @Override
               public MachineExtensionInner call(MachineExtensionInner resource) {
                   resetCreateUpdateParameters();
                   return resource;
               }
            })
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<MachineExtensionInner> getInnerAsync() {
        MachineExtensionsInner client = this.manager().inner().machineExtensions();
        return client.getAsync(this.resourceGroupName, this.name, this.extensionName);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    private void resetCreateUpdateParameters() {
        this.updateParameter = new MachineExtensionUpdate();
    }

    @Override
    public Boolean autoUpgradeMinorVersion() {
        return this.inner().autoUpgradeMinorVersion();
    }

    @Override
    public String forceUpdateTag() {
        return this.inner().forceUpdateTag();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public MachineExtensionPropertiesInstanceView instanceView() {
        return this.inner().instanceView();
    }

    @Override
    public String location() {
        return this.inner().location();
    }

    @Override
    public String machineExtensionType() {
        return this.inner().machineExtensionType();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public Object protectedSettings() {
        return this.inner().protectedSettings();
    }

    @Override
    public String provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public String publisher() {
        return this.inner().publisher();
    }

    @Override
    public Object settings() {
        return this.inner().settings();
    }

    @Override
    public Map<String, String> tags() {
        return this.inner().getTags();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public String typeHandlerVersion() {
        return this.inner().typeHandlerVersion();
    }

    @Override
    public MachineExtensionImpl withExistingMachine(String resourceGroupName, String name) {
        this.resourceGroupName = resourceGroupName;
        this.name = name;
        return this;
    }

    @Override
    public MachineExtensionImpl withLocation(String location) {
        this.inner().withLocation(location);
        return this;
    }

    @Override
    public MachineExtensionImpl withInstanceView(MachineExtensionPropertiesInstanceView instanceView) {
        this.inner().withInstanceView(instanceView);
        return this;
    }

    @Override
    public MachineExtensionImpl withMachineExtensionType(String machineExtensionType) {
        this.inner().withMachineExtensionType(machineExtensionType);
        return this;
    }

    @Override
    public MachineExtensionImpl withType(String type) {
        this.updateParameter.withType(type);
        return this;
    }

    @Override
    public MachineExtensionImpl withAutoUpgradeMinorVersion(Boolean autoUpgradeMinorVersion) {
        if (isInCreateMode()) {
            this.inner().withAutoUpgradeMinorVersion(autoUpgradeMinorVersion);
        } else {
            this.updateParameter.withAutoUpgradeMinorVersion(autoUpgradeMinorVersion);
        }
        return this;
    }

    @Override
    public MachineExtensionImpl withForceUpdateTag(String forceUpdateTag) {
        if (isInCreateMode()) {
            this.inner().withForceUpdateTag(forceUpdateTag);
        } else {
            this.updateParameter.withForceUpdateTag(forceUpdateTag);
        }
        return this;
    }

    @Override
    public MachineExtensionImpl withProtectedSettings(Object protectedSettings) {
        if (isInCreateMode()) {
            this.inner().withProtectedSettings(protectedSettings);
        } else {
            this.updateParameter.withProtectedSettings(protectedSettings);
        }
        return this;
    }

    @Override
    public MachineExtensionImpl withPublisher(String publisher) {
        if (isInCreateMode()) {
            this.inner().withPublisher(publisher);
        } else {
            this.updateParameter.withPublisher(publisher);
        }
        return this;
    }

    @Override
    public MachineExtensionImpl withSettings(Object settings) {
        if (isInCreateMode()) {
            this.inner().withSettings(settings);
        } else {
            this.updateParameter.withSettings(settings);
        }
        return this;
    }

    @Override
    public MachineExtensionImpl withTags(Map<String, String> tags) {
        if (isInCreateMode()) {
            this.inner().withTags(tags);
        } else {
            this.updateParameter.withTags(tags);
        }
        return this;
    }

    @Override
    public MachineExtensionImpl withTypeHandlerVersion(String typeHandlerVersion) {
        if (isInCreateMode()) {
            this.inner().withTypeHandlerVersion(typeHandlerVersion);
        } else {
            this.updateParameter.withTypeHandlerVersion(typeHandlerVersion);
        }
        return this;
    }

}