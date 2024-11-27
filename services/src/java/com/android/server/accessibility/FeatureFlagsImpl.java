package com.android.server.accessibility;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements com.android.server.accessibility.FeatureFlags {
    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean addWindowTokenWithoutLock() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean cleanupA11yOverlays() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean computeWindowChangesOnA11y() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean deprecatePackageListObserver() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean disableContinuousShortcutOnForceStop() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableMagnificationJoystick() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableMagnificationMultipleFingerMultipleTapGesture() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean fixDragPointerWhenEndingDrag() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean fullscreenFlingGesture() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean handleMultiDeviceInput() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean pinchZoomZeroMinSpan() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean proxyUseAppsOnVirtualDeviceListener() {
        return true;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean resetHoverEventTimerOnActionUp() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean resettableDynamicProperties() {
        return false;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean scanPackagesWithoutLock() {
        return true;
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean sendA11yEventsBasedOnState() {
        return false;
    }
}
