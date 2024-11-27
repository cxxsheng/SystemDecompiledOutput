package com.android.media.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.media.flags.FeatureFlags {
    @Override // com.android.media.flags.FeatureFlags
    public boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean disableScreenOffBroadcastReceiver() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableAudioPoliciesDeviceAndBluetoothController() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableBuiltInSpeakerRouteSuitabilityStatuses() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableCrossUserRoutingInMediaRouter2() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableGetTransferableRoutes() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNewMediaRoute2InfoTypes() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNotifyingActivityManagerWithMediaSessionStatusChange() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableNullSessionInMediaBrowserService() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePreventionOfKeepAliveRouteProviders() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enablePrivilegedRoutingForMediaRoutingControl() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableRlpCallbacksInMediaRouter2() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableScreenOffScanning() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName() {
        return false;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean enableWaitingStateForSystemSessionCreationRequest() {
        return true;
    }

    @Override // com.android.media.flags.FeatureFlags
    public boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling() {
        return true;
    }
}
