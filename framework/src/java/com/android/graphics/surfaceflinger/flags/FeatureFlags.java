package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean addSfSkippedFramesToTrace();

    boolean cacheWhenSourceCropLayerOnlyMoved();

    boolean connectedDisplay();

    boolean displayProtected();

    boolean dontSkipOnEarlyRo();

    boolean dontSkipOnEarlyRo2();

    boolean enableFroDependentFeatures();

    boolean enableLayerCommandBatching();

    boolean enableSmallAreaDetection();

    boolean fp16ClientTarget();

    boolean gameDefaultFrameRate();

    boolean graphiteRenderengine();

    boolean hdcpLevelHal();

    boolean hotplug2();

    boolean misc1();

    boolean multithreadedPresent();

    boolean protectedIfClient();

    boolean refreshRateOverlayOnExternalDisplay();

    boolean renderableBufferUsage();

    boolean restoreBlurStep();

    boolean screenshotFencePreservation();

    boolean useKnownRefreshRateForFpsConsistency();

    boolean vrrConfig();

    boolean vulkanRenderengine();
}
