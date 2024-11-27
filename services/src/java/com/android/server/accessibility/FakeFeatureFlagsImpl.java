package com.android.server.accessibility;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements com.android.server.accessibility.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_ADD_WINDOW_TOKEN_WITHOUT_LOCK, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_CLEANUP_A11Y_OVERLAYS, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_COMPUTE_WINDOW_CHANGES_ON_A11Y, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_DEPRECATE_PACKAGE_LIST_OBSERVER, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_DISABLE_CONTINUOUS_SHORTCUT_ON_FORCE_STOP, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_ENABLE_MAGNIFICATION_JOYSTICK, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_ENABLE_MAGNIFICATION_MULTIPLE_FINGER_MULTIPLE_TAP_GESTURE, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_FIX_DRAG_POINTER_WHEN_ENDING_DRAG, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_FULLSCREEN_FLING_GESTURE, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_HANDLE_MULTI_DEVICE_INPUT, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_PINCH_ZOOM_ZERO_MIN_SPAN, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_PROXY_USE_APPS_ON_VIRTUAL_DEVICE_LISTENER, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_RESET_HOVER_EVENT_TIMER_ON_ACTION_UP, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_RESETTABLE_DYNAMIC_PROPERTIES, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_SCAN_PACKAGES_WITHOUT_LOCK, false), java.util.Map.entry(com.android.server.accessibility.Flags.FLAG_SEND_A11Y_EVENTS_BASED_ON_STATE, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.server.accessibility.Flags.FLAG_ADD_WINDOW_TOKEN_WITHOUT_LOCK, com.android.server.accessibility.Flags.FLAG_CLEANUP_A11Y_OVERLAYS, com.android.server.accessibility.Flags.FLAG_COMPUTE_WINDOW_CHANGES_ON_A11Y, com.android.server.accessibility.Flags.FLAG_DEPRECATE_PACKAGE_LIST_OBSERVER, com.android.server.accessibility.Flags.FLAG_DISABLE_CONTINUOUS_SHORTCUT_ON_FORCE_STOP, com.android.server.accessibility.Flags.FLAG_ENABLE_MAGNIFICATION_JOYSTICK, com.android.server.accessibility.Flags.FLAG_ENABLE_MAGNIFICATION_MULTIPLE_FINGER_MULTIPLE_TAP_GESTURE, com.android.server.accessibility.Flags.FLAG_FIX_DRAG_POINTER_WHEN_ENDING_DRAG, com.android.server.accessibility.Flags.FLAG_FULLSCREEN_FLING_GESTURE, com.android.server.accessibility.Flags.FLAG_HANDLE_MULTI_DEVICE_INPUT, com.android.server.accessibility.Flags.FLAG_PINCH_ZOOM_ZERO_MIN_SPAN, com.android.server.accessibility.Flags.FLAG_PROXY_USE_APPS_ON_VIRTUAL_DEVICE_LISTENER, com.android.server.accessibility.Flags.FLAG_RESET_HOVER_EVENT_TIMER_ON_ACTION_UP, com.android.server.accessibility.Flags.FLAG_RESETTABLE_DYNAMIC_PROPERTIES, com.android.server.accessibility.Flags.FLAG_SCAN_PACKAGES_WITHOUT_LOCK, com.android.server.accessibility.Flags.FLAG_SEND_A11Y_EVENTS_BASED_ON_STATE, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean addWindowTokenWithoutLock() {
        return getValue(com.android.server.accessibility.Flags.FLAG_ADD_WINDOW_TOKEN_WITHOUT_LOCK);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean cleanupA11yOverlays() {
        return getValue(com.android.server.accessibility.Flags.FLAG_CLEANUP_A11Y_OVERLAYS);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean computeWindowChangesOnA11y() {
        return getValue(com.android.server.accessibility.Flags.FLAG_COMPUTE_WINDOW_CHANGES_ON_A11Y);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean deprecatePackageListObserver() {
        return getValue(com.android.server.accessibility.Flags.FLAG_DEPRECATE_PACKAGE_LIST_OBSERVER);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean disableContinuousShortcutOnForceStop() {
        return getValue(com.android.server.accessibility.Flags.FLAG_DISABLE_CONTINUOUS_SHORTCUT_ON_FORCE_STOP);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableMagnificationJoystick() {
        return getValue(com.android.server.accessibility.Flags.FLAG_ENABLE_MAGNIFICATION_JOYSTICK);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean enableMagnificationMultipleFingerMultipleTapGesture() {
        return getValue(com.android.server.accessibility.Flags.FLAG_ENABLE_MAGNIFICATION_MULTIPLE_FINGER_MULTIPLE_TAP_GESTURE);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean fixDragPointerWhenEndingDrag() {
        return getValue(com.android.server.accessibility.Flags.FLAG_FIX_DRAG_POINTER_WHEN_ENDING_DRAG);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean fullscreenFlingGesture() {
        return getValue(com.android.server.accessibility.Flags.FLAG_FULLSCREEN_FLING_GESTURE);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean handleMultiDeviceInput() {
        return getValue(com.android.server.accessibility.Flags.FLAG_HANDLE_MULTI_DEVICE_INPUT);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean pinchZoomZeroMinSpan() {
        return getValue(com.android.server.accessibility.Flags.FLAG_PINCH_ZOOM_ZERO_MIN_SPAN);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean proxyUseAppsOnVirtualDeviceListener() {
        return getValue(com.android.server.accessibility.Flags.FLAG_PROXY_USE_APPS_ON_VIRTUAL_DEVICE_LISTENER);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean resetHoverEventTimerOnActionUp() {
        return getValue(com.android.server.accessibility.Flags.FLAG_RESET_HOVER_EVENT_TIMER_ON_ACTION_UP);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean resettableDynamicProperties() {
        return getValue(com.android.server.accessibility.Flags.FLAG_RESETTABLE_DYNAMIC_PROPERTIES);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean scanPackagesWithoutLock() {
        return getValue(com.android.server.accessibility.Flags.FLAG_SCAN_PACKAGES_WITHOUT_LOCK);
    }

    @Override // com.android.server.accessibility.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean sendA11yEventsBasedOnState() {
        return getValue(com.android.server.accessibility.Flags.FLAG_SEND_A11Y_EVENTS_BASED_ON_STATE);
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
        if (this.mReadOnlyFlagsSet.contains(str)) {
            isOptimizationEnabled();
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

    @com.android.aconfig.annotations.AssumeTrueForR8
    private boolean isOptimizationEnabled() {
        return false;
    }
}
