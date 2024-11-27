package com.android.server.wm;

/* loaded from: classes3.dex */
final class WindowManagerConstants {
    static final java.lang.String KEY_SYSTEM_GESTURE_EXCLUSION_LOG_DEBOUNCE_MILLIS = "system_gesture_exclusion_log_debounce_millis";
    private static final int MIN_GESTURE_EXCLUSION_LIMIT_DP = 200;
    private final android.provider.DeviceConfigInterface mDeviceConfig;
    private final com.android.server.wm.WindowManagerGlobalLock mGlobalLock;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mListenerAndroid;
    private final android.provider.DeviceConfig.OnPropertiesChangedListener mListenerWindowManager;
    boolean mSystemGestureExcludedByPreQStickyImmersive;
    int mSystemGestureExclusionLimitDp;
    long mSystemGestureExclusionLogDebounceTimeoutMillis;
    private final java.lang.Runnable mUpdateSystemGestureExclusionCallback;

    WindowManagerConstants(final com.android.server.wm.WindowManagerService windowManagerService, android.provider.DeviceConfigInterface deviceConfigInterface) {
        this(windowManagerService.mGlobalLock, new java.lang.Runnable() { // from class: com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.WindowManagerConstants.lambda$new$0(com.android.server.wm.WindowManagerService.this);
            }
        }, deviceConfigInterface);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(com.android.server.wm.WindowManagerService windowManagerService) {
        windowManagerService.mRoot.forAllDisplays(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.wm.DisplayContent) obj).updateSystemGestureExclusionLimit();
            }
        });
    }

    @com.android.internal.annotations.VisibleForTesting
    WindowManagerConstants(com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock, java.lang.Runnable runnable, android.provider.DeviceConfigInterface deviceConfigInterface) {
        java.util.Objects.requireNonNull(windowManagerGlobalLock);
        this.mGlobalLock = windowManagerGlobalLock;
        java.util.Objects.requireNonNull(runnable);
        this.mUpdateSystemGestureExclusionCallback = runnable;
        this.mDeviceConfig = deviceConfigInterface;
        this.mListenerAndroid = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.wm.WindowManagerConstants.this.onAndroidPropertiesChanged(properties);
            }
        };
        this.mListenerWindowManager = new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.wm.WindowManagerConstants$$ExternalSyntheticLambda1
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.wm.WindowManagerConstants.this.onWindowPropertiesChanged(properties);
            }
        };
    }

    void start(java.util.concurrent.Executor executor) {
        this.mDeviceConfig.addOnPropertiesChangedListener(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, executor, this.mListenerAndroid);
        this.mDeviceConfig.addOnPropertiesChangedListener("window_manager", executor, this.mListenerWindowManager);
        updateSystemGestureExclusionLogDebounceMillis();
        updateSystemGestureExclusionLimitDp();
        updateSystemGestureExcludedByPreQStickyImmersive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void onAndroidPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
        char c;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                boolean z = false;
                for (java.lang.String str : properties.getKeyset()) {
                    if (str == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    switch (str.hashCode()) {
                        case -1271675449:
                            if (str.equals("system_gestures_excluded_by_pre_q_sticky_immersive")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case 316878247:
                            if (str.equals("system_gesture_exclusion_limit_dp")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            updateSystemGestureExclusionLimitDp();
                            z = true;
                            break;
                        case 1:
                            updateSystemGestureExcludedByPreQStickyImmersive();
                            z = true;
                            break;
                    }
                }
                if (z) {
                    this.mUpdateSystemGestureExclusionCallback.run();
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onWindowPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
        char c;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                for (java.lang.String str : properties.getKeyset()) {
                    if (str == null) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    switch (str.hashCode()) {
                        case -125834358:
                            if (str.equals(KEY_SYSTEM_GESTURE_EXCLUSION_LOG_DEBOUNCE_MILLIS)) {
                                c = 0;
                                break;
                            }
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            updateSystemGestureExclusionLogDebounceMillis();
                            break;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    private void updateSystemGestureExclusionLogDebounceMillis() {
        this.mSystemGestureExclusionLogDebounceTimeoutMillis = this.mDeviceConfig.getLong("window_manager", KEY_SYSTEM_GESTURE_EXCLUSION_LOG_DEBOUNCE_MILLIS, 0L);
    }

    private void updateSystemGestureExclusionLimitDp() {
        this.mSystemGestureExclusionLimitDp = java.lang.Math.max(200, this.mDeviceConfig.getInt(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, "system_gesture_exclusion_limit_dp", 0));
    }

    private void updateSystemGestureExcludedByPreQStickyImmersive() {
        this.mSystemGestureExcludedByPreQStickyImmersive = this.mDeviceConfig.getBoolean(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME, "system_gestures_excluded_by_pre_q_sticky_immersive", false);
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("WINDOW MANAGER CONSTANTS (dumpsys window constants):");
        printWriter.print("  ");
        printWriter.print(KEY_SYSTEM_GESTURE_EXCLUSION_LOG_DEBOUNCE_MILLIS);
        printWriter.print("=");
        printWriter.println(this.mSystemGestureExclusionLogDebounceTimeoutMillis);
        printWriter.print("  ");
        printWriter.print("system_gesture_exclusion_limit_dp");
        printWriter.print("=");
        printWriter.println(this.mSystemGestureExclusionLimitDp);
        printWriter.print("  ");
        printWriter.print("system_gestures_excluded_by_pre_q_sticky_immersive");
        printWriter.print("=");
        printWriter.println(this.mSystemGestureExcludedByPreQStickyImmersive);
        printWriter.println();
    }
}
