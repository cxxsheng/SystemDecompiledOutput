package com.android.internal.camera.flags;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean cameraAeModeLowLightBoost();

    boolean cameraDeviceSetup();

    boolean cameraExtensionsCharacteristicsGet();

    boolean cameraHsumPermission();

    boolean cameraManualFlashStrengthControl();

    boolean cameraPrivacyAllowlist();

    boolean concertMode();

    boolean delayLazyHalInstantiation();

    boolean extension10Bit();

    boolean featureCombinationQuery();

    boolean injectSessionParams();

    boolean lazyAidlWaitForService();

    boolean logUltrawideUsage();

    boolean logZoomOverrideUsage();

    boolean multiresolutionImagereaderUsageConfig();

    boolean returnBuffersOutsideLocks();

    boolean sessionHalBufManager();

    boolean useRoBoardApiLevelForVndkVersion();

    boolean watchForegroundChanges();
}
