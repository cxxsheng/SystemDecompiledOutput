package com.android.media.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession();

    boolean disableScreenOffBroadcastReceiver();

    boolean enableAudioPoliciesDeviceAndBluetoothController();

    boolean enableBuiltInSpeakerRouteSuitabilityStatuses();

    boolean enableCrossUserRoutingInMediaRouter2();

    boolean enableGetTransferableRoutes();

    boolean enableNewMediaRoute2InfoTypes();

    boolean enableNotifyingActivityManagerWithMediaSessionStatusChange();

    boolean enableNullSessionInMediaBrowserService();

    boolean enablePreventionOfKeepAliveRouteProviders();

    boolean enablePrivilegedRoutingForMediaRoutingControl();

    boolean enableRlpCallbacksInMediaRouter2();

    boolean enableScreenOffScanning();

    boolean enableUseOfBluetoothDeviceGetAliasForMr2infoGetName();

    boolean enableWaitingStateForSystemSessionCreationRequest();

    boolean fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling();
}
