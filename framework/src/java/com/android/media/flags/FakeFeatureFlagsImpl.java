package com.android.media.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.media.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.media.flags.Flags.FLAG_ADJUST_VOLUME_FOR_FOREGROUND_APP_PLAYING_AUDIO_WITHOUT_MEDIA_SESSION, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_DISABLE_SCREEN_OFF_BROADCAST_RECEIVER, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_AUDIO_POLICIES_DEVICE_AND_BLUETOOTH_CONTROLLER, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_BUILT_IN_SPEAKER_ROUTE_SUITABILITY_STATUSES, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_CROSS_USER_ROUTING_IN_MEDIA_ROUTER2, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_GET_TRANSFERABLE_ROUTES, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_NEW_MEDIA_ROUTE_2_INFO_TYPES, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_NOTIFYING_ACTIVITY_MANAGER_WITH_MEDIA_SESSION_STATUS_CHANGE, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_NULL_SESSION_IN_MEDIA_BROWSER_SERVICE, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_PREVENTION_OF_KEEP_ALIVE_ROUTE_PROVIDERS, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_PRIVILEGED_ROUTING_FOR_MEDIA_ROUTING_CONTROL, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_RLP_CALLBACKS_IN_MEDIA_ROUTER2, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_SCREEN_OFF_SCANNING, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_USE_OF_BLUETOOTH_DEVICE_GET_ALIAS_FOR_MR2INFO_GET_NAME, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_ENABLE_WAITING_STATE_FOR_SYSTEM_SESSION_CREATION_REQUEST, false), java.util.Map.entry(com.android.media.flags.Flags.FLAG_FALLBACK_TO_DEFAULT_HANDLING_WHEN_MEDIA_SESSION_HAS_FIXED_VOLUME_HANDLING, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.media.flags.Flags.FLAG_ADJUST_VOLUME_FOR_FOREGROUND_APP_PLAYING_AUDIO_WITHOUT_MEDIA_SESSION, com.android.media.flags.Flags.FLAG_DISABLE_SCREEN_OFF_BROADCAST_RECEIVER, com.android.media.flags.Flags.FLAG_ENABLE_AUDIO_POLICIES_DEVICE_AND_BLUETOOTH_CONTROLLER, com.android.media.flags.Flags.FLAG_ENABLE_BUILT_IN_SPEAKER_ROUTE_SUITABILITY_STATUSES, com.android.media.flags.Flags.FLAG_ENABLE_CROSS_USER_ROUTING_IN_MEDIA_ROUTER2, com.android.media.flags.Flags.FLAG_ENABLE_GET_TRANSFERABLE_ROUTES, com.android.media.flags.Flags.FLAG_ENABLE_NEW_MEDIA_ROUTE_2_INFO_TYPES, com.android.media.flags.Flags.FLAG_ENABLE_NOTIFYING_ACTIVITY_MANAGER_WITH_MEDIA_SESSION_STATUS_CHANGE, com.android.media.flags.Flags.FLAG_ENABLE_NULL_SESSION_IN_MEDIA_BROWSER_SERVICE, com.android.media.flags.Flags.FLAG_ENABLE_PREVENTION_OF_KEEP_ALIVE_ROUTE_PROVIDERS, com.android.media.flags.Flags.FLAG_ENABLE_PRIVILEGED_ROUTING_FOR_MEDIA_ROUTING_CONTROL, com.android.media.flags.Flags.FLAG_ENABLE_RLP_CALLBACKS_IN_MEDIA_ROUTER2, com.android.media.flags.Flags.FLAG_ENABLE_SCREEN_OFF_SCANNING, com.android.media.flags.Flags.FLAG_ENABLE_USE_OF_BLUETOOTH_DEVICE_GET_ALIAS_FOR_MR2INFO_GET_NAME, com.android.media.flags.Flags.FLAG_ENABLE_WAITING_STATE_FOR_SYSTEM_SESSION_CREATION_REQUEST, com.android.media.flags.Flags.FLAG_FALLBACK_TO_DEFAULT_HANDLING_WHEN_MEDIA_SESSION_HAS_FIXED_VOLUME_HANDLING, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession() {
        return getValue(com.android.media.flags.Flags.FLAG_ADJUST_VOLUME_FOR_FOREGROUND_APP_PLAYING_AUDIO_WITHOUT_MEDIA_SESSION);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean disableScreenOffBroadcastReceiver() {
        return getValue(com.android.media.flags.Flags.FLAG_DISABLE_SCREEN_OFF_BROADCAST_RECEIVER);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableAudioPoliciesDeviceAndBluetoothController() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_AUDIO_POLICIES_DEVICE_AND_BLUETOOTH_CONTROLLER);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableBuiltInSpeakerRouteSuitabilityStatuses() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_BUILT_IN_SPEAKER_ROUTE_SUITABILITY_STATUSES);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableCrossUserRoutingInMediaRouter2() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_CROSS_USER_ROUTING_IN_MEDIA_ROUTER2);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableGetTransferableRoutes() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_GET_TRANSFERABLE_ROUTES);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNewMediaRoute2InfoTypes() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_NEW_MEDIA_ROUTE_2_INFO_TYPES);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNotifyingActivityManagerWithMediaSessionStatusChange() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_NOTIFYING_ACTIVITY_MANAGER_WITH_MEDIA_SESSION_STATUS_CHANGE);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNullSessionInMediaBrowserService() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_NULL_SESSION_IN_MEDIA_BROWSER_SERVICE);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePreventionOfKeepAliveRouteProviders() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_PREVENTION_OF_KEEP_ALIVE_ROUTE_PROVIDERS);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePrivilegedRoutingForMediaRoutingControl() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_PRIVILEGED_ROUTING_FOR_MEDIA_ROUTING_CONTROL);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableRlpCallbacksInMediaRouter2() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_RLP_CALLBACKS_IN_MEDIA_ROUTER2);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableScreenOffScanning() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_SCREEN_OFF_SCANNING);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_USE_OF_BLUETOOTH_DEVICE_GET_ALIAS_FOR_MR2INFO_GET_NAME);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableWaitingStateForSystemSessionCreationRequest() {
        return getValue(com.android.media.flags.Flags.FLAG_ENABLE_WAITING_STATE_FOR_SYSTEM_SESSION_CREATION_REQUEST);
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling() {
        return getValue(com.android.media.flags.Flags.FLAG_FALLBACK_TO_DEFAULT_HANDLING_WHEN_MEDIA_SESSION_HAS_FIXED_VOLUME_HANDLING);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
