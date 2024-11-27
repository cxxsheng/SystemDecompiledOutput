package android.view.accessibility;

/* loaded from: classes4.dex */
public interface FeatureFlags {
    boolean a11yOverlayCallbacks();

    boolean a11yQsShortcut();

    boolean addTypeWindowControl();

    boolean allowShortcutChooserOnLockscreen();

    boolean brailleDisplayHid();

    boolean cleanupAccessibilityWarningDialog();

    boolean collectionInfoItemCounts();

    boolean copyEventsForGestureDetection();

    boolean enableSystemPinchZoomGesture();

    boolean fixMergedContentChangeEvent();

    boolean flashNotificationSystemApi();

    boolean forceInvertColor();

    boolean granularScrolling();

    boolean motionEventObserving();

    boolean reduceWindowContentChangedEventThrottle();

    boolean skipAccessibilityWarningDialogForTrustedServices();

    boolean supportSystemPinchZoomOptOutApis();

    boolean updateAlwaysOnA11yService();
}
