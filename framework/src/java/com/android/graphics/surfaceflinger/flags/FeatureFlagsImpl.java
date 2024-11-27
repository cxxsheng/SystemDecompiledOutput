package com.android.graphics.surfaceflinger.flags;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements com.android.graphics.surfaceflinger.flags.FeatureFlags {
    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean addSfSkippedFramesToTrace() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean cacheWhenSourceCropLayerOnlyMoved() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean connectedDisplay() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean displayProtected() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean dontSkipOnEarlyRo() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean dontSkipOnEarlyRo2() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableFroDependentFeatures() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableLayerCommandBatching() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean enableSmallAreaDetection() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean fp16ClientTarget() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean gameDefaultFrameRate() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean graphiteRenderengine() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hdcpLevelHal() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean hotplug2() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean misc1() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean multithreadedPresent() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean protectedIfClient() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean refreshRateOverlayOnExternalDisplay() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean renderableBufferUsage() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean restoreBlurStep() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean screenshotFencePreservation() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean useKnownRefreshRateForFpsConsistency() {
        return false;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vrrConfig() {
        return true;
    }

    @Override // com.android.graphics.surfaceflinger.flags.FeatureFlags
    public boolean vulkanRenderengine() {
        return false;
    }
}
