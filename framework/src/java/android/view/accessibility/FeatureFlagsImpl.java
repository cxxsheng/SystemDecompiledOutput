package android.view.accessibility;

/* loaded from: classes4.dex */
public final class FeatureFlagsImpl implements android.view.accessibility.FeatureFlags {
    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yOverlayCallbacks() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yQsShortcut() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean addTypeWindowControl() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean allowShortcutChooserOnLockscreen() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean brailleDisplayHid() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean cleanupAccessibilityWarningDialog() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean collectionInfoItemCounts() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean copyEventsForGestureDetection() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean enableSystemPinchZoomGesture() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean fixMergedContentChangeEvent() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean flashNotificationSystemApi() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean forceInvertColor() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean granularScrolling() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean motionEventObserving() {
        return true;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean reduceWindowContentChangedEventThrottle() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean skipAccessibilityWarningDialogForTrustedServices() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean supportSystemPinchZoomOptOutApis() {
        return false;
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean updateAlwaysOnA11yService() {
        return false;
    }
}
