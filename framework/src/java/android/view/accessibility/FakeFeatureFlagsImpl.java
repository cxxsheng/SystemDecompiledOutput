package android.view.accessibility;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements android.view.accessibility.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.view.accessibility.Flags.FLAG_A11Y_OVERLAY_CALLBACKS, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_A11Y_QS_SHORTCUT, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_ADD_TYPE_WINDOW_CONTROL, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_BRAILLE_DISPLAY_HID, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_FIX_MERGED_CONTENT_CHANGE_EVENT, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_FORCE_INVERT_COLOR, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_GRANULAR_SCROLLING, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_MOTION_EVENT_OBSERVING, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS, false), java.util.Map.entry(android.view.accessibility.Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.view.accessibility.Flags.FLAG_A11Y_OVERLAY_CALLBACKS, android.view.accessibility.Flags.FLAG_A11Y_QS_SHORTCUT, android.view.accessibility.Flags.FLAG_ADD_TYPE_WINDOW_CONTROL, android.view.accessibility.Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN, android.view.accessibility.Flags.FLAG_BRAILLE_DISPLAY_HID, android.view.accessibility.Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG, android.view.accessibility.Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS, android.view.accessibility.Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION, android.view.accessibility.Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE, android.view.accessibility.Flags.FLAG_FIX_MERGED_CONTENT_CHANGE_EVENT, android.view.accessibility.Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API, android.view.accessibility.Flags.FLAG_FORCE_INVERT_COLOR, android.view.accessibility.Flags.FLAG_GRANULAR_SCROLLING, android.view.accessibility.Flags.FLAG_MOTION_EVENT_OBSERVING, android.view.accessibility.Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE, android.view.accessibility.Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES, android.view.accessibility.Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS, android.view.accessibility.Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yOverlayCallbacks() {
        return getValue(android.view.accessibility.Flags.FLAG_A11Y_OVERLAY_CALLBACKS);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean a11yQsShortcut() {
        return getValue(android.view.accessibility.Flags.FLAG_A11Y_QS_SHORTCUT);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean addTypeWindowControl() {
        return getValue(android.view.accessibility.Flags.FLAG_ADD_TYPE_WINDOW_CONTROL);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean allowShortcutChooserOnLockscreen() {
        return getValue(android.view.accessibility.Flags.FLAG_ALLOW_SHORTCUT_CHOOSER_ON_LOCKSCREEN);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean brailleDisplayHid() {
        return getValue(android.view.accessibility.Flags.FLAG_BRAILLE_DISPLAY_HID);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean cleanupAccessibilityWarningDialog() {
        return getValue(android.view.accessibility.Flags.FLAG_CLEANUP_ACCESSIBILITY_WARNING_DIALOG);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean collectionInfoItemCounts() {
        return getValue(android.view.accessibility.Flags.FLAG_COLLECTION_INFO_ITEM_COUNTS);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean copyEventsForGestureDetection() {
        return getValue(android.view.accessibility.Flags.FLAG_COPY_EVENTS_FOR_GESTURE_DETECTION);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean enableSystemPinchZoomGesture() {
        return getValue(android.view.accessibility.Flags.FLAG_ENABLE_SYSTEM_PINCH_ZOOM_GESTURE);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean fixMergedContentChangeEvent() {
        return getValue(android.view.accessibility.Flags.FLAG_FIX_MERGED_CONTENT_CHANGE_EVENT);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean flashNotificationSystemApi() {
        return getValue(android.view.accessibility.Flags.FLAG_FLASH_NOTIFICATION_SYSTEM_API);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean forceInvertColor() {
        return getValue(android.view.accessibility.Flags.FLAG_FORCE_INVERT_COLOR);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean granularScrolling() {
        return getValue(android.view.accessibility.Flags.FLAG_GRANULAR_SCROLLING);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean motionEventObserving() {
        return getValue(android.view.accessibility.Flags.FLAG_MOTION_EVENT_OBSERVING);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean reduceWindowContentChangedEventThrottle() {
        return getValue(android.view.accessibility.Flags.FLAG_REDUCE_WINDOW_CONTENT_CHANGED_EVENT_THROTTLE);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean skipAccessibilityWarningDialogForTrustedServices() {
        return getValue(android.view.accessibility.Flags.FLAG_SKIP_ACCESSIBILITY_WARNING_DIALOG_FOR_TRUSTED_SERVICES);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean supportSystemPinchZoomOptOutApis() {
        return getValue(android.view.accessibility.Flags.FLAG_SUPPORT_SYSTEM_PINCH_ZOOM_OPT_OUT_APIS);
    }

    @Override // android.view.accessibility.FeatureFlags
    public boolean updateAlwaysOnA11yService() {
        return getValue(android.view.accessibility.Flags.FLAG_UPDATE_ALWAYS_ON_A11Y_SERVICE);
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
