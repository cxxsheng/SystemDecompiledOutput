package com.android.server.statusbar;

/* loaded from: classes2.dex */
public class StatusBarManagerService extends com.android.internal.statusbar.IStatusBarService.Stub implements android.hardware.display.DisplayManager.DisplayListener {
    private static final long LOCK_DOWN_COLLAPSE_STATUS_BAR = 173031413;
    static final long REQUEST_LISTENING_MUST_MATCH_PACKAGE = 172251878;
    static final long REQUEST_LISTENING_OTHER_USER_NOOP = 242194868;
    private static final long REQUEST_TIME_OUT = java.util.concurrent.TimeUnit.MINUTES.toNanos(5);
    private static final boolean SPEW = false;
    private static final java.lang.String TAG = "StatusBarManagerService";
    private final android.app.ActivityManagerInternal mActivityManagerInternal;
    private final com.android.server.wm.ActivityTaskManagerInternal mActivityTaskManager;
    private volatile com.android.internal.statusbar.IStatusBar mBar;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.biometrics.IBiometricContextListener mBiometricContextListener;
    private final android.content.Context mContext;
    private int mCurrentUserId;
    private com.android.server.policy.GlobalActionsProvider.GlobalActionsListener mGlobalActionListener;
    private com.android.server.notification.NotificationDelegate mNotificationDelegate;
    private android.content.om.IOverlayManager mOverlayManager;
    private final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    private final com.android.server.statusbar.SessionMonitor mSessionMonitor;
    private final com.android.server.statusbar.TileRequestTracker mTileRequestTracker;
    private boolean mTracingEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback mUdfpsRefreshRateRequestCallback;
    private final android.os.Handler mHandler = new android.os.Handler();
    private final android.util.ArrayMap<java.lang.String, com.android.internal.statusbar.StatusBarIcon> mIcons = new android.util.ArrayMap<>();
    private final java.util.ArrayList<com.android.server.statusbar.StatusBarManagerService.DisableRecord> mDisableRecords = new java.util.ArrayList<>();
    private final android.os.IBinder mSysUiVisToken = new android.os.Binder();
    private final java.lang.Object mLock = new java.lang.Object();
    private final com.android.server.statusbar.StatusBarManagerService.DeathRecipient mDeathRecipient = new com.android.server.statusbar.StatusBarManagerService.DeathRecipient();
    private int mLastSystemKey = -1;
    private final android.util.SparseArray<com.android.server.statusbar.StatusBarManagerService.UiState> mDisplayUiState = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mCurrentRequestAddTilePackages"})
    private final android.util.ArrayMap<java.lang.String, java.lang.Long> mCurrentRequestAddTilePackages = new android.util.ArrayMap<>();
    private final com.android.server.statusbar.StatusBarManagerInternal mInternalService = new com.android.server.statusbar.StatusBarManagerInternal() { // from class: com.android.server.statusbar.StatusBarManagerService.1
        private boolean mNotificationLightOn;

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setNotificationDelegate(com.android.server.notification.NotificationDelegate notificationDelegate) {
            com.android.server.statusbar.StatusBarManagerService.this.mNotificationDelegate = notificationDelegate;
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showScreenPinningRequest(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showScreenPinningRequest(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showAssistDisclosure() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showAssistDisclosure();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void startAssist(android.os.Bundle bundle) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.startAssist(bundle);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void onCameraLaunchGestureDetected(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.onCameraLaunchGestureDetected(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void onEmergencyActionLaunchGestureDetected() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.onEmergencyActionLaunchGestureDetected();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setDisableFlags(int i, int i2, java.lang.String str) {
            com.android.server.statusbar.StatusBarManagerService.this.setDisableFlags(i, i2, str);
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void toggleSplitScreen() {
            com.android.server.statusbar.StatusBarManagerService.this.enforceStatusBarService();
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.toggleSplitScreen();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void appTransitionFinished(int i) {
            com.android.server.statusbar.StatusBarManagerService.this.enforceStatusBarService();
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.appTransitionFinished(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void toggleTaskbar() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.toggleTaskbar();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void toggleRecentApps() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.toggleRecentApps();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setCurrentUser(int i) {
            com.android.server.statusbar.StatusBarManagerService.this.mCurrentUserId = i;
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void preloadRecentApps() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.preloadRecentApps();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void cancelPreloadRecentApps() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.cancelPreloadRecentApps();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showRecentApps(boolean z) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showRecentApps(z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void hideRecentApps(boolean z, boolean z2) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.hideRecentApps(z, z2);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void collapsePanels() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.animateCollapsePanels();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void dismissKeyboardShortcutsMenu() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.dismissKeyboardShortcutsMenu();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void toggleKeyboardShortcutsMenu(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.toggleKeyboardShortcutsMenu(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setImeWindowStatus(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) {
            com.android.server.statusbar.StatusBarManagerService.this.setImeWindowStatus(i, iBinder, i2, i3, z);
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setIcon(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) {
            com.android.server.statusbar.StatusBarManagerService.this.setIcon(str, str2, i, i2, str3);
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setIconVisibility(java.lang.String str, boolean z) {
            com.android.server.statusbar.StatusBarManagerService.this.setIconVisibility(str, z);
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showChargingAnimation(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showWirelessChargingAnimation(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showPictureInPictureMenu() {
            if (com.android.server.statusbar.StatusBarManagerService.this.mBar != null) {
                try {
                    com.android.server.statusbar.StatusBarManagerService.this.mBar.showPictureInPictureMenu();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setWindowState(int i, int i2, int i3) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.setWindowState(i, i2, i3);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void appTransitionPending(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.appTransitionPending(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void appTransitionCancelled(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.appTransitionCancelled(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void appTransitionStarting(int i, long j, long j2) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.appTransitionStarting(i, j, j2);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setTopAppHidesStatusBar(boolean z) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.setTopAppHidesStatusBar(z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public boolean showShutdownUi(boolean z, java.lang.String str, boolean z2) {
            com.android.internal.statusbar.IStatusBar iStatusBar;
            if (com.android.server.statusbar.StatusBarManagerService.this.mContext.getResources().getBoolean(android.R.bool.config_showMenuShortcutsWhenKeyboardPresent) && (iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar) != null) {
                try {
                    iStatusBar.showShutdownUi(z, str, z2);
                    return true;
                } catch (android.os.RemoteException e) {
                }
            }
            return false;
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void confirmImmersivePrompt() {
            if (com.android.server.statusbar.StatusBarManagerService.this.mBar == null) {
                return;
            }
            try {
                com.android.server.statusbar.StatusBarManagerService.this.mBar.confirmImmersivePrompt();
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void immersiveModeChanged(int i, boolean z) {
            if (com.android.server.statusbar.StatusBarManagerService.this.mBar != null && !android.view.ViewRootImpl.CLIENT_TRANSIENT) {
                try {
                    com.android.server.statusbar.StatusBarManagerService.this.mBar.immersiveModeChanged(i, z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void onProposedRotationChanged(int i, boolean z) {
            if (com.android.server.statusbar.StatusBarManagerService.this.mBar != null) {
                try {
                    com.android.server.statusbar.StatusBarManagerService.this.mBar.onProposedRotationChanged(i, z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void onDisplayReady(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.onDisplayReady(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void onRecentsAnimationStateChanged(boolean z) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.onRecentsAnimationStateChanged(z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void onSystemBarAttributesChanged(int i, int i2, com.android.internal.view.AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, java.lang.String str, com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr) {
            com.android.server.statusbar.StatusBarManagerService.this.getUiState(i).setBarAttributes(i2, appearanceRegionArr, z, i3, i4, str, letterboxDetailsArr);
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.onSystemBarAttributesChanged(i, i2, appearanceRegionArr, z, i3, i4, str, letterboxDetailsArr);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showTransient(int i, int i2, boolean z) {
            com.android.server.statusbar.StatusBarManagerService.this.getUiState(i).showTransient(i2);
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showTransient(i, i2, z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void abortTransient(int i, int i2) {
            com.android.server.statusbar.StatusBarManagerService.this.getUiState(i).clearTransient(i2);
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.abortTransient(i, i2);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showToast(int i, java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.IBinder iBinder2, int i2, @android.annotation.Nullable android.app.ITransientNotificationCallback iTransientNotificationCallback, int i3) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showToast(i, str, iBinder, charSequence, iBinder2, i2, iTransientNotificationCallback, i3);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void hideToast(java.lang.String str, android.os.IBinder iBinder) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.hideToast(str, iBinder);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public boolean requestMagnificationConnection(boolean z) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.requestMagnificationConnection(z);
                    return true;
                } catch (android.os.RemoteException e) {
                    return false;
                }
            }
            return false;
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.setNavigationBarLumaSamplingEnabled(i, z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
            synchronized (com.android.server.statusbar.StatusBarManagerService.this.mLock) {
                com.android.server.statusbar.StatusBarManagerService.this.mUdfpsRefreshRateRequestCallback = iUdfpsRefreshRateRequestCallback;
            }
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.setUdfpsRefreshRateCallback(iUdfpsRefreshRateRequestCallback);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showRearDisplayDialog(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showRearDisplayDialog(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void moveFocusedTaskToFullscreen(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.moveFocusedTaskToFullscreen(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void enterStageSplitFromRunningApp(boolean z) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.enterStageSplitFromRunningApp(z);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void enterDesktop(int i) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.enterDesktop(i);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void showMediaOutputSwitcher(java.lang.String str) {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showMediaOutputSwitcher(str);
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void addQsTileToFrontOrEnd(android.content.ComponentName componentName, boolean z) {
            if (android.view.accessibility.Flags.a11yQsShortcut()) {
                com.android.server.statusbar.StatusBarManagerService.this.addQsTileToFrontOrEnd(componentName, z);
            }
        }

        @Override // com.android.server.statusbar.StatusBarManagerInternal
        public void removeQsTile(android.content.ComponentName componentName) {
            if (android.view.accessibility.Flags.a11yQsShortcut()) {
                com.android.server.statusbar.StatusBarManagerService.this.remTile(componentName);
            }
        }
    };
    private final com.android.server.policy.GlobalActionsProvider mGlobalActionsProvider = new com.android.server.policy.GlobalActionsProvider() { // from class: com.android.server.statusbar.StatusBarManagerService.2
        @Override // com.android.server.policy.GlobalActionsProvider
        public boolean isGlobalActionsDisabled() {
            return (((com.android.server.statusbar.StatusBarManagerService.UiState) com.android.server.statusbar.StatusBarManagerService.this.mDisplayUiState.get(0)).getDisabled2() & 8) != 0;
        }

        @Override // com.android.server.policy.GlobalActionsProvider
        public void setGlobalActionsListener(com.android.server.policy.GlobalActionsProvider.GlobalActionsListener globalActionsListener) {
            com.android.server.statusbar.StatusBarManagerService.this.mGlobalActionListener = globalActionsListener;
            com.android.server.statusbar.StatusBarManagerService.this.mGlobalActionListener.onGlobalActionsAvailableChanged(com.android.server.statusbar.StatusBarManagerService.this.mBar != null);
        }

        @Override // com.android.server.policy.GlobalActionsProvider
        public void showGlobalActions() {
            com.android.internal.statusbar.IStatusBar iStatusBar = com.android.server.statusbar.StatusBarManagerService.this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.showGlobalActionsMenu();
                } catch (android.os.RemoteException e) {
                }
            }
        }
    };

    private class DeathRecipient implements android.os.IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.statusbar.StatusBarManagerService.this.mBar.asBinder().unlinkToDeath(this, 0);
            com.android.server.statusbar.StatusBarManagerService.this.mBar = null;
            com.android.server.statusbar.StatusBarManagerService.this.notifyBarAttachChanged();
        }

        public void linkToDeath() {
            try {
                com.android.server.statusbar.StatusBarManagerService.this.mBar.asBinder().linkToDeath(com.android.server.statusbar.StatusBarManagerService.this.mDeathRecipient, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.statusbar.StatusBarManagerService.TAG, "Unable to register Death Recipient for status bar", e);
            }
        }
    }

    private class DisableRecord implements android.os.IBinder.DeathRecipient {
        java.lang.String pkg;
        android.os.IBinder token;
        int userId;
        int what1;
        int what2;

        public DisableRecord(int i, android.os.IBinder iBinder) {
            this.userId = i;
            this.token = iBinder;
            try {
                iBinder.linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Slog.i(com.android.server.statusbar.StatusBarManagerService.TAG, "binder died for pkg=" + this.pkg);
            com.android.server.statusbar.StatusBarManagerService.this.disableForUser(0, this.token, this.pkg, this.userId);
            com.android.server.statusbar.StatusBarManagerService.this.disable2ForUser(0, this.token, this.pkg, this.userId);
            this.token.unlinkToDeath(this, 0);
        }

        public void setFlags(int i, int i2, java.lang.String str) {
            switch (i2) {
                case 1:
                    this.what1 = i;
                    break;
                case 2:
                    this.what2 = i;
                    break;
                default:
                    android.util.Slog.w(com.android.server.statusbar.StatusBarManagerService.TAG, "Can't set unsupported disable flag " + i2 + ": 0x" + java.lang.Integer.toHexString(i));
                    break;
            }
            this.pkg = str;
        }

        public int getFlags(int i) {
            switch (i) {
                case 1:
                    return this.what1;
                case 2:
                    return this.what2;
                default:
                    android.util.Slog.w(com.android.server.statusbar.StatusBarManagerService.TAG, "Can't get unsupported disable flag " + i);
                    return 0;
            }
        }

        public boolean isEmpty() {
            return this.what1 == 0 && this.what2 == 0;
        }

        public java.lang.String toString() {
            return java.lang.String.format("userId=%d what1=0x%08X what2=0x%08X pkg=%s token=%s", java.lang.Integer.valueOf(this.userId), java.lang.Integer.valueOf(this.what1), java.lang.Integer.valueOf(this.what2), this.pkg, this.token);
        }
    }

    public StatusBarManagerService(android.content.Context context) {
        this.mContext = context;
        com.android.server.LocalServices.addService(com.android.server.statusbar.StatusBarManagerInternal.class, this.mInternalService);
        this.mDisplayUiState.put(0, new com.android.server.statusbar.StatusBarManagerService.UiState());
        ((android.hardware.display.DisplayManager) context.getSystemService("display")).registerDisplayListener(this, this.mHandler);
        this.mActivityTaskManager = (com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class);
        this.mPackageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mTileRequestTracker = new com.android.server.statusbar.TileRequestTracker(this.mContext);
        this.mSessionMonitor = new com.android.server.statusbar.SessionMonitor(this.mContext);
    }

    public void publishGlobalActionsProvider() {
        if (com.android.server.LocalServices.getService(com.android.server.policy.GlobalActionsProvider.class) == null) {
            com.android.server.LocalServices.addService(com.android.server.policy.GlobalActionsProvider.class, this.mGlobalActionsProvider);
        }
    }

    private android.content.om.IOverlayManager getOverlayManager() {
        if (this.mOverlayManager == null) {
            this.mOverlayManager = android.content.om.IOverlayManager.Stub.asInterface(android.os.ServiceManager.getService("overlay"));
            if (this.mOverlayManager == null) {
                android.util.Slog.w("StatusBarManager", "warning: no OVERLAY_SERVICE");
            }
        }
        return this.mOverlayManager;
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
        synchronized (this.mLock) {
            this.mDisplayUiState.remove(i);
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
    }

    private boolean isDisable2FlagSet(int i) {
        return (this.mDisplayUiState.get(0).getDisabled2() & i) == i;
    }

    public void expandNotificationsPanel() {
        enforceExpandStatusBar();
        if (!isDisable2FlagSet(4) && this.mBar != null) {
            try {
                this.mBar.animateExpandNotificationsPanel();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void collapsePanels() {
        if (checkCanCollapseStatusBar("collapsePanels") && this.mBar != null) {
            try {
                this.mBar.animateCollapsePanels();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void togglePanel() {
        if (checkCanCollapseStatusBar("togglePanel") && !isDisable2FlagSet(4) && this.mBar != null) {
            try {
                this.mBar.togglePanel();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void expandSettingsPanel(java.lang.String str) {
        enforceExpandStatusBar();
        if (this.mBar != null) {
            try {
                this.mBar.animateExpandSettingsPanel(str);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void addTile(android.content.ComponentName componentName) {
        if (android.view.accessibility.Flags.a11yQsShortcut()) {
            addQsTileToFrontOrEnd(componentName, false);
            return;
        }
        enforceStatusBarOrShell();
        if (this.mBar != null) {
            try {
                this.mBar.addQsTile(componentName);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addQsTileToFrontOrEnd(android.content.ComponentName componentName, boolean z) {
        enforceStatusBarOrShell();
        if (this.mBar != null) {
            try {
                this.mBar.addQsTileToFrontOrEnd(componentName, z);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void remTile(android.content.ComponentName componentName) {
        enforceStatusBarOrShell();
        if (this.mBar != null) {
            try {
                this.mBar.remQsTile(componentName);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void setTiles(java.lang.String str) {
        enforceStatusBarOrShell();
        if (this.mBar != null) {
            try {
                this.mBar.setQsTiles(str.split(","));
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void clickTile(android.content.ComponentName componentName) {
        enforceStatusBarOrShell();
        if (this.mBar != null) {
            try {
                this.mBar.clickQsTile(componentName);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void handleSystemKey(android.view.KeyEvent keyEvent) throws android.os.RemoteException {
        if (!checkCanCollapseStatusBar("handleSystemKey")) {
            return;
        }
        this.mLastSystemKey = keyEvent.getKeyCode();
        if (this.mBar != null) {
            try {
                this.mBar.handleSystemKey(keyEvent);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public int getLastSystemKey() {
        enforceStatusBar();
        return this.mLastSystemKey;
    }

    public void showPinningEnterExitToast(boolean z) throws android.os.RemoteException {
        if (this.mBar != null) {
            try {
                this.mBar.showPinningEnterExitToast(z);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void showPinningEscapeToast() throws android.os.RemoteException {
        if (this.mBar != null) {
            try {
                this.mBar.showPinningEscapeToast();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void showAuthenticationDialog(android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, java.lang.String str, long j2) {
        enforceBiometricDialog();
        if (this.mBar != null) {
            try {
                this.mBar.showAuthenticationDialog(promptInfo, iBiometricSysuiReceiver, iArr, z, z2, i, j, str, j2);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void onBiometricAuthenticated(int i) {
        enforceBiometricDialog();
        if (this.mBar != null) {
            try {
                this.mBar.onBiometricAuthenticated(i);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void onBiometricHelp(int i, java.lang.String str) {
        enforceBiometricDialog();
        if (this.mBar != null) {
            try {
                this.mBar.onBiometricHelp(i, str);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void onBiometricError(int i, int i2, int i3) {
        enforceBiometricDialog();
        if (this.mBar != null) {
            try {
                this.mBar.onBiometricError(i, i2, i3);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void hideAuthenticationDialog(long j) {
        enforceBiometricDialog();
        if (this.mBar != null) {
            try {
                this.mBar.hideAuthenticationDialog(j);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void setBiometicContextListener(android.hardware.biometrics.IBiometricContextListener iBiometricContextListener) {
        enforceStatusBarService();
        synchronized (this.mLock) {
            this.mBiometricContextListener = iBiometricContextListener;
        }
        if (this.mBar != null) {
            try {
                this.mBar.setBiometicContextListener(iBiometricContextListener);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) {
        enforceStatusBarService();
        if (this.mBar != null) {
            try {
                this.mBar.setUdfpsRefreshRateCallback(iUdfpsRefreshRateRequestCallback);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void startTracing() {
        if (this.mBar != null) {
            try {
                this.mBar.startTracing();
                this.mTracingEnabled = true;
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void stopTracing() {
        if (this.mBar != null) {
            try {
                this.mTracingEnabled = false;
                this.mBar.stopTracing();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public boolean isTracing() {
        return this.mTracingEnabled;
    }

    public void disable(int i, android.os.IBinder iBinder, java.lang.String str) {
        disableForUser(i, iBinder, str, this.mCurrentUserId);
    }

    public void disableForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) {
        enforceStatusBar();
        synchronized (this.mLock) {
            disableLocked(0, i2, i, iBinder, str, 1);
        }
    }

    public void disable2(int i, android.os.IBinder iBinder, java.lang.String str) {
        disable2ForUser(i, iBinder, str, this.mCurrentUserId);
    }

    public void disable2ForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) {
        enforceStatusBar();
        synchronized (this.mLock) {
            disableLocked(0, i2, i, iBinder, str, 2);
        }
    }

    private void disableLocked(int i, int i2, int i3, android.os.IBinder iBinder, java.lang.String str, int i4) {
        manageDisableListLocked(i2, i3, iBinder, str, i4);
        final int gatherDisableActionsLocked = gatherDisableActionsLocked(this.mCurrentUserId, 1);
        int gatherDisableActionsLocked2 = gatherDisableActionsLocked(this.mCurrentUserId, 2);
        com.android.server.statusbar.StatusBarManagerService.UiState uiState = getUiState(i);
        if (!uiState.disableEquals(gatherDisableActionsLocked, gatherDisableActionsLocked2)) {
            uiState.setDisabled(gatherDisableActionsLocked, gatherDisableActionsLocked2);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.statusbar.StatusBarManagerService.this.lambda$disableLocked$0(gatherDisableActionsLocked);
                }
            });
            com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.disable(i, gatherDisableActionsLocked, gatherDisableActionsLocked2);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disableLocked$0(int i) {
        this.mNotificationDelegate.onSetDisabled(i);
    }

    public int[] getDisableFlags(android.os.IBinder iBinder, int i) {
        int i2;
        int i3;
        enforceStatusBar();
        synchronized (this.mLock) {
            try {
                com.android.server.statusbar.StatusBarManagerService.DisableRecord disableRecord = (com.android.server.statusbar.StatusBarManagerService.DisableRecord) findMatchingRecordLocked(iBinder, i).second;
                if (disableRecord == null) {
                    i2 = 0;
                    i3 = 0;
                } else {
                    i2 = disableRecord.what1;
                    i3 = disableRecord.what2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return new int[]{i2, i3};
    }

    void runGcForTest() {
        if (!android.os.Build.IS_DEBUGGABLE) {
            throw new java.lang.SecurityException("runGcForTest requires a debuggable build");
        }
        com.android.internal.util.GcUtils.runGcAndFinalizersSync();
        if (this.mBar != null) {
            try {
                this.mBar.runGcForTest();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void setIcon(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) {
        enforceStatusBar();
        synchronized (this.mIcons) {
            com.android.internal.statusbar.StatusBarIcon statusBarIcon = new com.android.internal.statusbar.StatusBarIcon(str2, android.os.UserHandle.SYSTEM, i, i2, 0, str3);
            this.mIcons.put(str, statusBarIcon);
            com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.setIcon(str, statusBarIcon);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    public void setIconVisibility(java.lang.String str, boolean z) {
        enforceStatusBar();
        synchronized (this.mIcons) {
            try {
                com.android.internal.statusbar.StatusBarIcon statusBarIcon = this.mIcons.get(str);
                if (statusBarIcon == null) {
                    return;
                }
                if (statusBarIcon.visible != z) {
                    statusBarIcon.visible = z;
                    com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
                    if (iStatusBar != null) {
                        try {
                            iStatusBar.setIcon(str, statusBarIcon);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeIcon(java.lang.String str) {
        enforceStatusBar();
        synchronized (this.mIcons) {
            this.mIcons.remove(str);
            com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
            if (iStatusBar != null) {
                try {
                    iStatusBar.removeIcon(str);
                } catch (android.os.RemoteException e) {
                }
            }
        }
    }

    public void setImeWindowStatus(final int i, final android.os.IBinder iBinder, final int i2, final int i3, final boolean z) {
        enforceStatusBar();
        synchronized (this.mLock) {
            getUiState(i).setImeWindowState(i2, i3, z, iBinder);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.statusbar.StatusBarManagerService.this.lambda$setImeWindowStatus$1(i, iBinder, i2, i3, z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setImeWindowStatus$1(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) {
        if (this.mBar == null) {
            return;
        }
        try {
            this.mBar.setImeWindowStatus(i, iBinder, i2, i3, z);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDisableFlags(int i, int i2, java.lang.String str) {
        enforceStatusBarService();
        int i3 = (-134152193) & i2;
        if (i3 != 0) {
            android.util.Slog.e(TAG, "Unknown disable flags: 0x" + java.lang.Integer.toHexString(i3), new java.lang.RuntimeException());
        }
        synchronized (this.mLock) {
            disableLocked(i, this.mCurrentUserId, i2, this.mSysUiVisToken, str, 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.statusbar.StatusBarManagerService.UiState getUiState(int i) {
        com.android.server.statusbar.StatusBarManagerService.UiState uiState = this.mDisplayUiState.get(i);
        if (uiState == null) {
            com.android.server.statusbar.StatusBarManagerService.UiState uiState2 = new com.android.server.statusbar.StatusBarManagerService.UiState();
            this.mDisplayUiState.put(i, uiState2);
            return uiState2;
        }
        return uiState;
    }

    private static class UiState {
        private int mAppearance;
        private com.android.internal.view.AppearanceRegion[] mAppearanceRegions;
        private int mBehavior;
        private int mDisabled1;
        private int mDisabled2;
        private int mImeBackDisposition;
        private android.os.IBinder mImeToken;
        private int mImeWindowVis;
        private com.android.internal.statusbar.LetterboxDetails[] mLetterboxDetails;
        private boolean mNavbarColorManagedByIme;
        private java.lang.String mPackageName;
        private int mRequestedVisibleTypes;
        private boolean mShowImeSwitcher;
        private int mTransientBarTypes;

        private UiState() {
            this.mAppearance = 0;
            this.mAppearanceRegions = new com.android.internal.view.AppearanceRegion[0];
            this.mNavbarColorManagedByIme = false;
            this.mRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
            this.mPackageName = "none";
            this.mDisabled1 = 0;
            this.mDisabled2 = 0;
            this.mImeWindowVis = 0;
            this.mImeBackDisposition = 0;
            this.mShowImeSwitcher = false;
            this.mImeToken = null;
            this.mLetterboxDetails = new com.android.internal.statusbar.LetterboxDetails[0];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setBarAttributes(int i, com.android.internal.view.AppearanceRegion[] appearanceRegionArr, boolean z, int i2, int i3, java.lang.String str, com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr) {
            this.mAppearance = i;
            this.mAppearanceRegions = appearanceRegionArr;
            this.mNavbarColorManagedByIme = z;
            this.mBehavior = i2;
            this.mRequestedVisibleTypes = i3;
            this.mPackageName = str;
            this.mLetterboxDetails = letterboxDetailsArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void showTransient(int i) {
            this.mTransientBarTypes = i | this.mTransientBarTypes;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTransient(int i) {
            this.mTransientBarTypes = (~i) & this.mTransientBarTypes;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getDisabled1() {
            return this.mDisabled1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getDisabled2() {
            return this.mDisabled2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDisabled(int i, int i2) {
            this.mDisabled1 = i;
            this.mDisabled2 = i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean disableEquals(int i, int i2) {
            return this.mDisabled1 == i && this.mDisabled2 == i2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setImeWindowState(int i, int i2, boolean z, android.os.IBinder iBinder) {
            this.mImeWindowVis = i;
            this.mImeBackDisposition = i2;
            this.mShowImeSwitcher = z;
            this.mImeToken = iBinder;
        }
    }

    private void enforceStatusBarOrShell() {
        if (android.os.Binder.getCallingUid() == 2000) {
            return;
        }
        enforceStatusBar();
    }

    private void enforceStatusBar() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR", TAG);
    }

    private void enforceExpandStatusBar() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.EXPAND_STATUS_BAR", TAG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceStatusBarService() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.STATUS_BAR_SERVICE", TAG);
    }

    private void enforceBiometricDialog() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_BIOMETRIC_DIALOG", TAG);
    }

    private void enforceMediaContentControl() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MEDIA_CONTENT_CONTROL", TAG);
    }

    @android.annotation.RequiresPermission("android.permission.CONTROL_DEVICE_STATE")
    private void enforceControlDeviceStatePermission() {
        this.mContext.enforceCallingOrSelfPermission("android.permission.CONTROL_DEVICE_STATE", TAG);
    }

    private boolean doesCallerHoldInteractAcrossUserPermission() {
        return this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == 0 || this.mContext.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == 0;
    }

    private boolean checkCanCollapseStatusBar(java.lang.String str) {
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        if (android.app.compat.CompatChanges.isChangeEnabled(LOCK_DOWN_COLLAPSE_STATUS_BAR, callingUid)) {
            enforceStatusBar();
            return true;
        }
        if (this.mContext.checkPermission("android.permission.STATUS_BAR", callingPid, callingUid) != 0) {
            enforceExpandStatusBar();
            if (!this.mActivityTaskManager.canCloseSystemDialogs(callingPid, callingUid)) {
                android.util.Slog.e(TAG, "Permission Denial: Method " + str + "() requires permission android.permission.STATUS_BAR, ignoring call.");
                return false;
            }
            return true;
        }
        return true;
    }

    public com.android.internal.statusbar.RegisterStatusBarResult registerStatusBar(com.android.internal.statusbar.IStatusBar iStatusBar) {
        android.util.ArrayMap arrayMap;
        com.android.internal.statusbar.RegisterStatusBarResult registerStatusBarResult;
        enforceStatusBarService();
        android.util.Slog.i(TAG, "registerStatusBar bar=" + iStatusBar);
        this.mBar = iStatusBar;
        this.mDeathRecipient.linkToDeath();
        notifyBarAttachChanged();
        synchronized (this.mIcons) {
            arrayMap = new android.util.ArrayMap(this.mIcons);
        }
        synchronized (this.mLock) {
            com.android.server.statusbar.StatusBarManagerService.UiState uiState = this.mDisplayUiState.get(0);
            registerStatusBarResult = new com.android.internal.statusbar.RegisterStatusBarResult(arrayMap, gatherDisableActionsLocked(this.mCurrentUserId, 1), uiState.mAppearance, uiState.mAppearanceRegions, uiState.mImeWindowVis, uiState.mImeBackDisposition, uiState.mShowImeSwitcher, gatherDisableActionsLocked(this.mCurrentUserId, 2), uiState.mImeToken, uiState.mNavbarColorManagedByIme, uiState.mBehavior, uiState.mRequestedVisibleTypes, uiState.mPackageName, uiState.mTransientBarTypes, uiState.mLetterboxDetails);
        }
        return registerStatusBarResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyBarAttachChanged() {
        com.android.server.UiThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.statusbar.StatusBarManagerService.this.lambda$notifyBarAttachChanged$2();
            }
        });
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.statusbar.StatusBarManagerService.this.lambda$notifyBarAttachChanged$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyBarAttachChanged$2() {
        if (this.mGlobalActionListener == null) {
            return;
        }
        this.mGlobalActionListener.onGlobalActionsAvailableChanged(this.mBar != null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyBarAttachChanged$3() {
        synchronized (this.mLock) {
            setUdfpsRefreshRateCallback(this.mUdfpsRefreshRateRequestCallback);
            setBiometicContextListener(this.mBiometricContextListener);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void registerOverlayManager(android.content.om.IOverlayManager iOverlayManager) {
        this.mOverlayManager = iOverlayManager;
    }

    public void onPanelRevealed(boolean z, int i) {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onPanelRevealed(z, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void clearNotificationEffects() throws android.os.RemoteException {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.clearEffects();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onPanelHidden() throws android.os.RemoteException {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onPanelHidden();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void shutdown() {
        enforceStatusBarService();
        final java.lang.String str = "userrequested";
        com.android.server.power.ShutdownCheckPoints.recordCheckPoint(android.os.Binder.getCallingPid(), "userrequested");
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.prepareForPossibleShutdown();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.statusbar.StatusBarManagerService.lambda$shutdown$4(str);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$shutdown$4(java.lang.String str) {
        com.android.server.power.ShutdownThread.shutdown(getUiContext(), str, false);
    }

    public void reboot(final boolean z, final java.lang.String str) {
        enforceStatusBarService();
        com.android.server.power.ShutdownCheckPoints.recordCheckPoint(android.os.Binder.getCallingPid(), str);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.prepareForPossibleShutdown();
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.statusbar.StatusBarManagerService.lambda$reboot$5(z, str);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$reboot$5(boolean z, java.lang.String str) {
        if (z) {
            com.android.server.power.ShutdownThread.rebootSafeMode(getUiContext(), true);
        } else {
            com.android.server.power.ShutdownThread.rebootCustom(getUiContext(), str, false);
        }
    }

    public void restart() {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.statusbar.StatusBarManagerService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.statusbar.StatusBarManagerService.this.lambda$restart$6();
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$restart$6() {
        this.mActivityManagerInternal.restart();
    }

    public void onGlobalActionsShown() {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mGlobalActionListener == null) {
                return;
            }
            this.mGlobalActionListener.onGlobalActionsShown();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onGlobalActionsHidden() {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (this.mGlobalActionListener == null) {
                return;
            }
            this.mGlobalActionListener.onGlobalActionsDismissed();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationClick(java.lang.String str, com.android.internal.statusbar.NotificationVisibility notificationVisibility) {
        enforceStatusBarService();
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationClick(callingUid, callingPid, str, notificationVisibility);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationActionClick(java.lang.String str, int i, android.app.Notification.Action action, com.android.internal.statusbar.NotificationVisibility notificationVisibility, boolean z) {
        enforceStatusBarService();
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationActionClick(callingUid, callingPid, str, i, action, notificationVisibility, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationError(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3, int i4) {
        enforceStatusBarService();
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationError(callingUid, callingPid, str, str2, i, i2, i3, str3, i4);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationClear(java.lang.String str, int i, java.lang.String str2, int i2, int i3, com.android.internal.statusbar.NotificationVisibility notificationVisibility) {
        enforceStatusBarService();
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationClear(callingUid, callingPid, str, i, str2, i2, i3, notificationVisibility);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationVisibilityChanged(com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr, com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr2) throws android.os.RemoteException {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationVisibilityChanged(notificationVisibilityArr, notificationVisibilityArr2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2, int i) throws android.os.RemoteException {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationExpansionChanged(str, z, z2, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationDirectReplied(java.lang.String str) throws android.os.RemoteException {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationDirectReplied(str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationSmartSuggestionsAdded(java.lang.String str, int i, int i2, boolean z, boolean z2) {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationSmartSuggestionsAdded(str, i, i2, z, z2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationSmartReplySent(java.lang.String str, int i, java.lang.CharSequence charSequence, int i2, boolean z) throws android.os.RemoteException {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationSmartReplySent(str, i, charSequence, i2, z);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationSettingsViewed(java.lang.String str) throws android.os.RemoteException {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationSettingsViewed(str);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onClearAllNotifications(int i) {
        enforceStatusBarService();
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onClearAll(callingUid, callingPid, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationBubbleChanged(java.lang.String str, boolean z, int i) {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationBubbleChanged(str, z, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onBubbleMetadataFlagChanged(java.lang.String str, int i) {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onBubbleMetadataFlagChanged(str, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void hideCurrentInputMethodForBubbles(int i) {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.inputmethod.InputMethodManagerInternal.get().hideAllInputMethods(20, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void grantInlineReplyUriPermission(java.lang.String str, android.net.Uri uri, android.os.UserHandle userHandle, java.lang.String str2) {
        enforceStatusBarService();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.grantInlineReplyUriPermission(str, uri, userHandle, str2, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void clearInlineReplyUriPermissions(java.lang.String str) {
        enforceStatusBarService();
        int callingUid = android.os.Binder.getCallingUid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.clearInlineReplyUriPermissions(str, callingUid);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void onNotificationFeedbackReceived(java.lang.String str, android.os.Bundle bundle) {
        enforceStatusBarService();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mNotificationDelegate.onNotificationFeedbackReceived(str, bundle);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
        new com.android.server.statusbar.StatusBarShellCommand(this, this.mContext).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public void showInattentiveSleepWarning() {
        enforceStatusBarService();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.showInattentiveSleepWarning();
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void dismissInattentiveSleepWarning(boolean z) {
        enforceStatusBarService();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.dismissInattentiveSleepWarning(z);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void suppressAmbientDisplay(boolean z) {
        enforceStatusBarService();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.suppressAmbientDisplay(z);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    private void checkCallingUidPackage(java.lang.String str, int i, int i2) {
        if (android.os.UserHandle.getAppId(i) != android.os.UserHandle.getAppId(this.mPackageManagerInternal.getPackageUid(str, 0L, i2))) {
            throw new java.lang.SecurityException("Package " + str + " does not belong to the calling uid " + i);
        }
    }

    private android.content.pm.ResolveInfo isComponentValidTileService(android.content.ComponentName componentName, int i) {
        android.content.Intent intent = new android.content.Intent("android.service.quicksettings.action.QS_TILE");
        intent.setComponent(componentName);
        android.content.pm.ResolveInfo resolveService = this.mPackageManagerInternal.resolveService(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 0L, i, android.os.Process.myUid());
        int componentEnabledSetting = this.mPackageManagerInternal.getComponentEnabledSetting(componentName, android.os.Process.myUid(), i);
        if (resolveService != null && resolveService.serviceInfo != null && resolveEnabledComponent(resolveService.serviceInfo.enabled, componentEnabledSetting) && "android.permission.BIND_QUICK_SETTINGS_TILE".equals(resolveService.serviceInfo.permission)) {
            return resolveService;
        }
        return null;
    }

    private boolean resolveEnabledComponent(boolean z, int i) {
        if (i == 1) {
            return true;
        }
        if (i == 0) {
            return z;
        }
        return false;
    }

    public void requestTileServiceListeningState(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        int callingUid = android.os.Binder.getCallingUid();
        java.lang.String packageName = componentName.getPackageName();
        if (android.app.compat.CompatChanges.isChangeEnabled(REQUEST_LISTENING_MUST_MATCH_PACKAGE, callingUid)) {
            int handleIncomingUser = this.mActivityManagerInternal.handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, 0, "requestTileServiceListeningState", packageName);
            checkCallingUidPackage(packageName, callingUid, handleIncomingUser);
            if (handleIncomingUser != this.mActivityManagerInternal.getCurrentUserId()) {
                if (android.app.compat.CompatChanges.isChangeEnabled(REQUEST_LISTENING_OTHER_USER_NOOP, callingUid)) {
                    return;
                }
                throw new java.lang.IllegalArgumentException("User " + handleIncomingUser + " is not the current user.");
            }
        }
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.requestTileServiceListeningState(componentName);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "requestTileServiceListeningState", e);
            }
        }
    }

    public void requestAddTile(@android.annotation.NonNull final android.content.ComponentName componentName, @android.annotation.NonNull java.lang.CharSequence charSequence, @android.annotation.NonNull android.graphics.drawable.Icon icon, final int i, @android.annotation.NonNull final com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback) {
        com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback2;
        com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback3;
        int callingUid = android.os.Binder.getCallingUid();
        final java.lang.String packageName = componentName.getPackageName();
        this.mActivityManagerInternal.handleIncomingUser(android.os.Binder.getCallingPid(), callingUid, i, false, 0, "requestAddTile", packageName);
        checkCallingUidPackage(packageName, callingUid, i);
        if (i != this.mActivityManagerInternal.getCurrentUserId()) {
            try {
                iAddTileResultCallback.onTileRequest(1003);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "requestAddTile", e);
                return;
            }
        }
        android.content.pm.ResolveInfo isComponentValidTileService = isComponentValidTileService(componentName, i);
        if (isComponentValidTileService == null) {
            iAddTileResultCallback2 = iAddTileResultCallback;
        } else {
            if (isComponentValidTileService.serviceInfo.exported) {
                if (android.app.ActivityManager.RunningAppProcessInfo.procStateToImportance(this.mActivityManagerInternal.getUidProcessState(callingUid)) != 100) {
                    try {
                        iAddTileResultCallback.onTileRequest(1004);
                        return;
                    } catch (android.os.RemoteException e2) {
                        android.util.Slog.e(TAG, "requestAddTile", e2);
                        return;
                    }
                }
                synchronized (this.mCurrentRequestAddTilePackages) {
                    java.lang.Long l = this.mCurrentRequestAddTilePackages.get(packageName);
                    long nanoTime = java.lang.System.nanoTime();
                    if (l != null && nanoTime - l.longValue() < REQUEST_TIME_OUT) {
                        try {
                            iAddTileResultCallback.onTileRequest(1001);
                        } catch (android.os.RemoteException e3) {
                            android.util.Slog.e(TAG, "requestAddTile", e3);
                        }
                        return;
                    }
                    if (l != null) {
                        cancelRequestAddTileInternal(packageName);
                    }
                    this.mCurrentRequestAddTilePackages.put(packageName, java.lang.Long.valueOf(nanoTime));
                    if (this.mTileRequestTracker.shouldBeDenied(i, componentName)) {
                        if (clearTileAddRequest(packageName)) {
                            try {
                                iAddTileResultCallback.onTileRequest(0);
                                return;
                            } catch (android.os.RemoteException e4) {
                                android.util.Slog.e(TAG, "requestAddTile - callback", e4);
                                return;
                            }
                        }
                        return;
                    }
                    com.android.internal.statusbar.IAddTileResultCallback.Stub stub = new com.android.internal.statusbar.IAddTileResultCallback.Stub() { // from class: com.android.server.statusbar.StatusBarManagerService.3
                        public void onTileRequest(int i2) {
                            if (i2 == 3) {
                                i2 = 0;
                            } else if (i2 == 0) {
                                com.android.server.statusbar.StatusBarManagerService.this.mTileRequestTracker.addDenial(i, componentName);
                            } else if (i2 == 2) {
                                com.android.server.statusbar.StatusBarManagerService.this.mTileRequestTracker.resetRequests(i, componentName);
                            }
                            if (com.android.server.statusbar.StatusBarManagerService.this.clearTileAddRequest(packageName)) {
                                try {
                                    iAddTileResultCallback.onTileRequest(i2);
                                } catch (android.os.RemoteException e5) {
                                    android.util.Slog.e(com.android.server.statusbar.StatusBarManagerService.TAG, "requestAddTile - callback", e5);
                                }
                            }
                        }
                    };
                    java.lang.CharSequence loadLabel = isComponentValidTileService.serviceInfo.applicationInfo.loadLabel(this.mContext.getPackageManager());
                    com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
                    if (iStatusBar == null) {
                        iAddTileResultCallback3 = iAddTileResultCallback;
                    } else {
                        iAddTileResultCallback3 = iAddTileResultCallback;
                        try {
                            iStatusBar.requestAddTile(callingUid, componentName, loadLabel, charSequence, icon, stub);
                            return;
                        } catch (android.os.RemoteException e5) {
                            android.util.Slog.e(TAG, "requestAddTile", e5);
                        }
                    }
                    clearTileAddRequest(packageName);
                    try {
                        iAddTileResultCallback3.onTileRequest(1005);
                        return;
                    } catch (android.os.RemoteException e6) {
                        android.util.Slog.e(TAG, "requestAddTile", e6);
                        return;
                    }
                }
            }
            iAddTileResultCallback2 = iAddTileResultCallback;
        }
        try {
            iAddTileResultCallback2.onTileRequest(1002);
        } catch (android.os.RemoteException e7) {
            android.util.Slog.e(TAG, "requestAddTile", e7);
        }
    }

    public void cancelRequestAddTile(@android.annotation.NonNull java.lang.String str) {
        enforceStatusBar();
        cancelRequestAddTileInternal(str);
    }

    private void cancelRequestAddTileInternal(java.lang.String str) {
        clearTileAddRequest(str);
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.cancelRequestAddTile(str);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "requestAddTile", e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean clearTileAddRequest(java.lang.String str) {
        boolean z;
        synchronized (this.mCurrentRequestAddTilePackages) {
            z = this.mCurrentRequestAddTilePackages.remove(str) != null;
        }
        return z;
    }

    public void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) {
        this.mSessionMonitor.onSessionStarted(i, instanceId);
    }

    public void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) {
        this.mSessionMonitor.onSessionEnded(i, instanceId);
    }

    public void registerSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) {
        this.mSessionMonitor.registerSessionListener(i, iSessionListener);
    }

    public void unregisterSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) {
        this.mSessionMonitor.unregisterSessionListener(i, iSessionListener);
    }

    public java.lang.String[] getStatusBarIcons() {
        return this.mContext.getResources().getStringArray(android.R.array.config_smallAreaDetectionAllowlist);
    }

    public void setNavBarMode(int i) {
        enforceStatusBar();
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Supplied navBarMode not supported: " + i);
        }
        int i2 = this.mCurrentUserId;
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        if (this.mCurrentUserId != userId && !doesCallerHoldInteractAcrossUserPermission()) {
            throw new java.lang.SecurityException("Calling user id: " + userId + ", cannot call on behalf of current user id: " + this.mCurrentUserId + ".");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "nav_bar_kids_mode", i, i2);
                android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "nav_bar_force_visible", i, i2);
                android.content.om.IOverlayManager overlayManager = getOverlayManager();
                if (overlayManager != null && i == 1 && isPackageSupported("com.android.internal.systemui.navbar.threebutton")) {
                    overlayManager.setEnabledExclusiveInCategory("com.android.internal.systemui.navbar.threebutton", i2);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public int getNavBarMode() {
        enforceStatusBar();
        int i = this.mCurrentUserId;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int intForUser = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "nav_bar_kids_mode", i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return intForUser;
        } catch (android.provider.Settings.SettingNotFoundException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private boolean isPackageSupported(java.lang.String str) {
        if (str == null) {
            return false;
        }
        try {
            return this.mContext.getPackageManager().getPackageInfo(str, android.content.pm.PackageManager.PackageInfoFlags.of(0L)) != null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void updateMediaTapToTransferSenderDisplay(int i, @android.annotation.NonNull android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.Nullable com.android.internal.statusbar.IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        enforceMediaContentControl();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.updateMediaTapToTransferSenderDisplay(i, mediaRoute2Info, iUndoMediaTransferCallback);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "updateMediaTapToTransferSenderDisplay", e);
            }
        }
    }

    public void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, @android.annotation.Nullable android.graphics.drawable.Icon icon, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        enforceMediaContentControl();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.updateMediaTapToTransferReceiverDisplay(i, mediaRoute2Info, icon, charSequence);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "updateMediaTapToTransferReceiverDisplay", e);
            }
        }
    }

    public void registerNearbyMediaDevicesProvider(@android.annotation.NonNull android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        enforceMediaContentControl();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.registerNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "registerNearbyMediaDevicesProvider", e);
            }
        }
    }

    public void unregisterNearbyMediaDevicesProvider(@android.annotation.NonNull android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) {
        enforceMediaContentControl();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.unregisterNearbyMediaDevicesProvider(iNearbyMediaDevicesProvider);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "unregisterNearbyMediaDevicesProvider", e);
            }
        }
    }

    @android.annotation.RequiresPermission("android.permission.CONTROL_DEVICE_STATE")
    public void showRearDisplayDialog(int i) {
        enforceControlDeviceStatePermission();
        com.android.internal.statusbar.IStatusBar iStatusBar = this.mBar;
        if (iStatusBar != null) {
            try {
                iStatusBar.showRearDisplayDialog(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "showRearDisplayDialog", e);
            }
        }
    }

    public void startAssist(android.os.Bundle bundle) {
        enforceStatusBarService();
        if (this.mBar != null) {
            try {
                this.mBar.startAssist(bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "startAssist", e);
            }
        }
    }

    public void passThroughShellCommand(java.lang.String[] strArr, java.io.FileDescriptor fileDescriptor) {
        enforceStatusBarOrShell();
        if (this.mBar == null) {
            return;
        }
        try {
            com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
            try {
                transferPipe.setBufferPrefix("  ");
                this.mBar.passThroughShellCommand(strArr, transferPipe.getWriteFd());
                transferPipe.go(fileDescriptor);
                transferPipe.close();
            } finally {
            }
        } catch (java.lang.Throwable th) {
            android.util.Slog.e(TAG, "Error sending command to IStatusBar", th);
        }
    }

    void manageDisableListLocked(int i, int i2, android.os.IBinder iBinder, java.lang.String str, int i3) {
        android.util.Pair<java.lang.Integer, com.android.server.statusbar.StatusBarManagerService.DisableRecord> findMatchingRecordLocked = findMatchingRecordLocked(iBinder, i);
        int intValue = ((java.lang.Integer) findMatchingRecordLocked.first).intValue();
        com.android.server.statusbar.StatusBarManagerService.DisableRecord disableRecord = (com.android.server.statusbar.StatusBarManagerService.DisableRecord) findMatchingRecordLocked.second;
        if (!iBinder.isBinderAlive()) {
            if (disableRecord != null) {
                this.mDisableRecords.remove(intValue);
                disableRecord.token.unlinkToDeath(disableRecord, 0);
                return;
            }
            return;
        }
        if (disableRecord != null) {
            disableRecord.setFlags(i2, i3, str);
            if (disableRecord.isEmpty()) {
                this.mDisableRecords.remove(intValue);
                disableRecord.token.unlinkToDeath(disableRecord, 0);
                return;
            }
            return;
        }
        com.android.server.statusbar.StatusBarManagerService.DisableRecord disableRecord2 = new com.android.server.statusbar.StatusBarManagerService.DisableRecord(i, iBinder);
        disableRecord2.setFlags(i2, i3, str);
        this.mDisableRecords.add(disableRecord2);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private android.util.Pair<java.lang.Integer, com.android.server.statusbar.StatusBarManagerService.DisableRecord> findMatchingRecordLocked(android.os.IBinder iBinder, int i) {
        com.android.server.statusbar.StatusBarManagerService.DisableRecord disableRecord;
        int size = this.mDisableRecords.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                disableRecord = null;
                break;
            }
            disableRecord = this.mDisableRecords.get(i2);
            if (disableRecord.token == iBinder && disableRecord.userId == i) {
                break;
            }
            i2++;
        }
        return new android.util.Pair<>(java.lang.Integer.valueOf(i2), disableRecord);
    }

    int gatherDisableActionsLocked(int i, int i2) {
        int size = this.mDisableRecords.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            com.android.server.statusbar.StatusBarManagerService.DisableRecord disableRecord = this.mDisableRecords.get(i4);
            if (disableRecord.userId == i) {
                i3 |= disableRecord.getFlags(i2);
            }
        }
        return i3;
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        java.util.ArrayList arrayList;
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            boolean z = false;
            for (java.lang.String str : strArr) {
                if ("--proto".equals(str)) {
                    z = true;
                }
            }
            if (z) {
                if (this.mBar == null) {
                    return;
                }
                try {
                    com.android.internal.os.TransferPipe transferPipe = new com.android.internal.os.TransferPipe();
                    try {
                        this.mBar.dumpProto(strArr, transferPipe.getWriteFd());
                        transferPipe.go(fileDescriptor);
                        transferPipe.close();
                        return;
                    } finally {
                    }
                } catch (java.lang.Throwable th) {
                    android.util.Slog.e(TAG, "Error sending command to IStatusBar", th);
                    return;
                }
            }
            synchronized (this.mLock) {
                for (int i = 0; i < this.mDisplayUiState.size(); i++) {
                    try {
                        int keyAt = this.mDisplayUiState.keyAt(i);
                        com.android.server.statusbar.StatusBarManagerService.UiState uiState = this.mDisplayUiState.get(keyAt);
                        printWriter.println("  displayId=" + keyAt);
                        printWriter.println("    mDisabled1=0x" + java.lang.Integer.toHexString(uiState.getDisabled1()));
                        printWriter.println("    mDisabled2=0x" + java.lang.Integer.toHexString(uiState.getDisabled2()));
                    } catch (java.lang.Throwable th2) {
                        throw th2;
                    }
                }
                int size = this.mDisableRecords.size();
                printWriter.println("  mDisableRecords.size=" + size);
                for (int i2 = 0; i2 < size; i2++) {
                    printWriter.println("    [" + i2 + "] " + this.mDisableRecords.get(i2));
                }
                printWriter.println("  mCurrentUserId=" + this.mCurrentUserId);
                printWriter.println("  mIcons=");
                for (java.lang.String str2 : this.mIcons.keySet()) {
                    printWriter.println("    ");
                    printWriter.print(str2);
                    printWriter.print(" -> ");
                    com.android.internal.statusbar.StatusBarIcon statusBarIcon = this.mIcons.get(str2);
                    printWriter.print(statusBarIcon);
                    if (!android.text.TextUtils.isEmpty(statusBarIcon.contentDescription)) {
                        printWriter.print(" \"");
                        printWriter.print(statusBarIcon.contentDescription);
                        printWriter.print("\"");
                    }
                    printWriter.println();
                }
                synchronized (this.mCurrentRequestAddTilePackages) {
                    arrayList = new java.util.ArrayList(this.mCurrentRequestAddTilePackages.keySet());
                }
                printWriter.println("  mCurrentRequestAddTilePackages=[");
                int size2 = arrayList.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    printWriter.println("    " + ((java.lang.String) arrayList.get(i3)) + ",");
                }
                printWriter.println("  ]");
                this.mTileRequestTracker.dump(fileDescriptor, new android.util.IndentingPrintWriter(printWriter, "  ").increaseIndent(), strArr);
            }
        }
    }

    private static final android.content.Context getUiContext() {
        return android.app.ActivityThread.currentActivityThread().getSystemUiContext();
    }
}
