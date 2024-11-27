package com.android.server.display;

/* loaded from: classes.dex */
class ExternalDisplayPolicy {

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String ENABLE_ON_CONNECT = "persist.sys.display.enable_on_connect.external";

    @android.annotation.NonNull
    private final com.android.server.display.notifications.DisplayNotificationManager mDisplayNotificationManager;

    @android.annotation.NonNull
    private final com.android.server.display.ExternalDisplayStatsService mExternalDisplayStatsService;

    @android.annotation.NonNull
    private final com.android.server.display.feature.DisplayManagerFlags mFlags;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final com.android.server.display.ExternalDisplayPolicy.Injector mInjector;
    private boolean mIsBootCompleted;

    @android.annotation.NonNull
    private final com.android.server.display.LogicalDisplayMapper mLogicalDisplayMapper;

    @android.annotation.NonNull
    private final com.android.server.display.DisplayManagerService.SyncRoot mSyncRoot;
    private static final java.lang.String TAG = "ExternalDisplayPolicy";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);
    private volatile int mStatus = 0;
    private final java.util.Set<java.lang.Integer> mDisplayIdsWaitingForBootCompletion = new java.util.HashSet();

    interface Injector {
        @android.annotation.NonNull
        com.android.server.display.notifications.DisplayNotificationManager getDisplayNotificationManager();

        @android.annotation.NonNull
        com.android.server.display.ExternalDisplayStatsService getExternalDisplayStatsService();

        @android.annotation.NonNull
        com.android.server.display.feature.DisplayManagerFlags getFlags();

        @android.annotation.NonNull
        android.os.Handler getHandler();

        @android.annotation.NonNull
        com.android.server.display.LogicalDisplayMapper getLogicalDisplayMapper();

        @android.annotation.NonNull
        com.android.server.display.DisplayManagerService.SyncRoot getSyncRoot();

        @android.annotation.Nullable
        android.os.IThermalService getThermalService();

        void sendExternalDisplayEventLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay, int i);
    }

    static boolean isExternalDisplayLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        return logicalDisplay.getDisplayInfoLocked().type == 2;
    }

    ExternalDisplayPolicy(@android.annotation.NonNull com.android.server.display.ExternalDisplayPolicy.Injector injector) {
        this.mInjector = injector;
        this.mLogicalDisplayMapper = this.mInjector.getLogicalDisplayMapper();
        this.mSyncRoot = this.mInjector.getSyncRoot();
        this.mFlags = this.mInjector.getFlags();
        this.mDisplayNotificationManager = this.mInjector.getDisplayNotificationManager();
        this.mHandler = this.mInjector.getHandler();
        this.mExternalDisplayStatsService = this.mInjector.getExternalDisplayStatsService();
    }

    void onBootCompleted() {
        synchronized (this.mSyncRoot) {
            try {
                this.mIsBootCompleted = true;
                java.util.Iterator<java.lang.Integer> it = this.mDisplayIdsWaitingForBootCompletion.iterator();
                while (it.hasNext()) {
                    com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(it.next().intValue());
                    if (displayLocked != null) {
                        handleExternalDisplayConnectedLocked(displayLocked);
                    }
                }
                this.mDisplayIdsWaitingForBootCompletion.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "External display management is not enabled on your device: cannot register thermal listener.");
            }
        } else if (!this.mFlags.isConnectedDisplayErrorHandlingEnabled()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "ConnectedDisplayErrorHandlingEnabled is not enabled on your device: cannot register thermal listener.");
            }
        } else if (!registerThermalServiceListener(new com.android.server.display.ExternalDisplayPolicy.SkinThermalStatusObserver())) {
            android.util.Slog.e(TAG, "Failed to register thermal listener");
        }
    }

    void setExternalDisplayEnabledLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay, boolean z) {
        if (!isExternalDisplayLocked(logicalDisplay)) {
            android.util.Slog.e(TAG, "setExternalDisplayEnabledLocked called for non external display");
            return;
        }
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "setExternalDisplayEnabledLocked: External display management is not enabled on your device, cannot enable/disable display.");
            }
        } else {
            if (z && !isExternalDisplayAllowed()) {
                android.util.Slog.w(TAG, "setExternalDisplayEnabledLocked: External display can not be enabled because it is currently not allowed.");
                android.os.Handler handler = this.mHandler;
                final com.android.server.display.notifications.DisplayNotificationManager displayNotificationManager = this.mDisplayNotificationManager;
                java.util.Objects.requireNonNull(displayNotificationManager);
                handler.post(new java.lang.Runnable() { // from class: com.android.server.display.ExternalDisplayPolicy$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.display.notifications.DisplayNotificationManager.this.onHighTemperatureExternalDisplayNotAllowed();
                    }
                });
                return;
            }
            this.mLogicalDisplayMapper.setDisplayEnabledLocked(logicalDisplay, z);
        }
    }

    void handleExternalDisplayConnectedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        if (!isExternalDisplayLocked(logicalDisplay)) {
            android.util.Slog.e(TAG, "handleExternalDisplayConnectedLocked called for non-external display");
            return;
        }
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "handleExternalDisplayConnectedLocked connected display management flag is off");
                return;
            }
            return;
        }
        if (!this.mIsBootCompleted) {
            this.mDisplayIdsWaitingForBootCompletion.add(java.lang.Integer.valueOf(logicalDisplay.getDisplayIdLocked()));
            return;
        }
        this.mExternalDisplayStatsService.onDisplayConnected(logicalDisplay);
        if ((android.os.Build.IS_ENG || android.os.Build.IS_USERDEBUG) && android.os.SystemProperties.getBoolean(ENABLE_ON_CONNECT, false)) {
            android.util.Slog.w(TAG, "External display is enabled by default, bypassing user consent.");
            this.mInjector.sendExternalDisplayEventLocked(logicalDisplay, 6);
            return;
        }
        logicalDisplay.setEnabledLocked(false);
        if (!isExternalDisplayAllowed()) {
            android.util.Slog.w(TAG, "handleExternalDisplayConnectedLocked: External display can not be used because it is currently not allowed.");
            this.mDisplayNotificationManager.onHighTemperatureExternalDisplayNotAllowed();
            return;
        }
        this.mInjector.sendExternalDisplayEventLocked(logicalDisplay, 6);
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleExternalDisplayConnectedLocked complete displayId=" + logicalDisplay.getDisplayIdLocked());
        }
    }

    void handleLogicalDisplayDisconnectedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            return;
        }
        int displayIdLocked = logicalDisplay.getDisplayIdLocked();
        if (this.mDisplayIdsWaitingForBootCompletion.remove(java.lang.Integer.valueOf(displayIdLocked))) {
            return;
        }
        this.mExternalDisplayStatsService.onDisplayDisconnected(displayIdLocked);
    }

    void handleLogicalDisplayAddedLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        if (!isExternalDisplayLocked(logicalDisplay) || !this.mFlags.isConnectedDisplayManagementEnabled()) {
            return;
        }
        this.mExternalDisplayStatsService.onDisplayAdded(logicalDisplay.getDisplayIdLocked());
    }

    void onPresentation(int i, boolean z) {
        synchronized (this.mSyncRoot) {
            com.android.server.display.LogicalDisplay displayLocked = this.mLogicalDisplayMapper.getDisplayLocked(i);
            if (displayLocked == null || !isExternalDisplayLocked(displayLocked)) {
                return;
            }
            if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
                return;
            }
            if (z) {
                this.mExternalDisplayStatsService.onPresentationWindowAdded(i);
            } else {
                this.mExternalDisplayStatsService.onPresentationWindowRemoved(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mSyncRoot"})
    public void disableExternalDisplayLocked(@android.annotation.NonNull com.android.server.display.LogicalDisplay logicalDisplay) {
        if (!isExternalDisplayLocked(logicalDisplay)) {
            return;
        }
        if (!this.mFlags.isConnectedDisplayManagementEnabled()) {
            android.util.Slog.e(TAG, "disableExternalDisplayLocked shouldn't be called when the connected display management flag is off");
            return;
        }
        if (!this.mFlags.isConnectedDisplayErrorHandlingEnabled()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "disableExternalDisplayLocked shouldn't be called when the error handling flag is off");
                return;
            }
            return;
        }
        if (!logicalDisplay.isEnabledLocked()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "disableExternalDisplayLocked is not allowed: displayId=" + logicalDisplay.getDisplayIdLocked() + " isEnabledLocked=false");
                return;
            }
            return;
        }
        if (!isExternalDisplayAllowed()) {
            android.util.Slog.w(TAG, "External display is currently not allowed and is getting disabled.");
            this.mDisplayNotificationManager.onHighTemperatureExternalDisplayNotAllowed();
        }
        this.mLogicalDisplayMapper.setDisplayEnabledLocked(logicalDisplay, false);
        this.mExternalDisplayStatsService.onDisplayDisabled(logicalDisplay.getDisplayIdLocked());
        if (DEBUG) {
            android.util.Slog.d(TAG, "disableExternalDisplayLocked complete displayId=" + logicalDisplay.getDisplayIdLocked());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isExternalDisplayAllowed() {
        return this.mStatus < 4;
    }

    private boolean registerThermalServiceListener(@android.annotation.NonNull android.os.IThermalEventListener.Stub stub) {
        android.os.IThermalService thermalService = this.mInjector.getThermalService();
        if (thermalService == null) {
            android.util.Slog.w(TAG, "Could not observe thermal status. Service not available");
            return false;
        }
        try {
            thermalService.registerThermalEventListenerWithType(stub, 3);
            if (DEBUG) {
                android.util.Slog.d(TAG, "registerThermalServiceListener complete.");
                return true;
            }
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Failed to register thermal status listener", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disableExternalDisplays() {
        synchronized (this.mSyncRoot) {
            this.mLogicalDisplayMapper.forEachLocked(new java.util.function.Consumer() { // from class: com.android.server.display.ExternalDisplayPolicy$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.display.ExternalDisplayPolicy.this.disableExternalDisplayLocked((com.android.server.display.LogicalDisplay) obj);
                }
            });
        }
    }

    private final class SkinThermalStatusObserver extends android.os.IThermalEventListener.Stub {
        private SkinThermalStatusObserver() {
        }

        public void notifyThrottling(@android.annotation.NonNull android.os.Temperature temperature) {
            int status = temperature.getStatus();
            int i = com.android.server.display.ExternalDisplayPolicy.this.mStatus;
            com.android.server.display.ExternalDisplayPolicy.this.mStatus = status;
            if (4 > i && 4 <= status) {
                com.android.server.display.ExternalDisplayPolicy.this.disableExternalDisplays();
            }
        }
    }
}
