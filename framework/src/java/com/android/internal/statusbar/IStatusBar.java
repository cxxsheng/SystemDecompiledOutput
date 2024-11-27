package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public interface IStatusBar extends android.os.IInterface {
    void abortTransient(int i, int i2) throws android.os.RemoteException;

    void addQsTile(android.content.ComponentName componentName) throws android.os.RemoteException;

    void addQsTileToFrontOrEnd(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException;

    void animateCollapsePanels() throws android.os.RemoteException;

    void animateExpandNotificationsPanel() throws android.os.RemoteException;

    void animateExpandSettingsPanel(java.lang.String str) throws android.os.RemoteException;

    void appTransitionCancelled(int i) throws android.os.RemoteException;

    void appTransitionFinished(int i) throws android.os.RemoteException;

    void appTransitionPending(int i) throws android.os.RemoteException;

    void appTransitionStarting(int i, long j, long j2) throws android.os.RemoteException;

    void cancelPreloadRecentApps() throws android.os.RemoteException;

    void cancelRequestAddTile(java.lang.String str) throws android.os.RemoteException;

    void clickQsTile(android.content.ComponentName componentName) throws android.os.RemoteException;

    void confirmImmersivePrompt() throws android.os.RemoteException;

    void disable(int i, int i2, int i3) throws android.os.RemoteException;

    void dismissInattentiveSleepWarning(boolean z) throws android.os.RemoteException;

    void dismissKeyboardShortcutsMenu() throws android.os.RemoteException;

    void dumpProto(java.lang.String[] strArr, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void enterDesktop(int i) throws android.os.RemoteException;

    void enterStageSplitFromRunningApp(boolean z) throws android.os.RemoteException;

    void handleSystemKey(android.view.KeyEvent keyEvent) throws android.os.RemoteException;

    void hideAuthenticationDialog(long j) throws android.os.RemoteException;

    void hideRecentApps(boolean z, boolean z2) throws android.os.RemoteException;

    void hideToast(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void immersiveModeChanged(int i, boolean z) throws android.os.RemoteException;

    void moveFocusedTaskToFullscreen(int i) throws android.os.RemoteException;

    void onBiometricAuthenticated(int i) throws android.os.RemoteException;

    void onBiometricError(int i, int i2, int i3) throws android.os.RemoteException;

    void onBiometricHelp(int i, java.lang.String str) throws android.os.RemoteException;

    void onCameraLaunchGestureDetected(int i) throws android.os.RemoteException;

    void onDisplayReady(int i) throws android.os.RemoteException;

    void onEmergencyActionLaunchGestureDetected() throws android.os.RemoteException;

    void onProposedRotationChanged(int i, boolean z) throws android.os.RemoteException;

    void onRecentsAnimationStateChanged(boolean z) throws android.os.RemoteException;

    void onSystemBarAttributesChanged(int i, int i2, com.android.internal.view.AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, java.lang.String str, com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr) throws android.os.RemoteException;

    void passThroughShellCommand(java.lang.String[] strArr, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void preloadRecentApps() throws android.os.RemoteException;

    void registerNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException;

    void remQsTile(android.content.ComponentName componentName) throws android.os.RemoteException;

    void removeIcon(java.lang.String str) throws android.os.RemoteException;

    void requestAddTile(int i, android.content.ComponentName componentName, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.graphics.drawable.Icon icon, com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback) throws android.os.RemoteException;

    void requestMagnificationConnection(boolean z) throws android.os.RemoteException;

    void requestTileServiceListeningState(android.content.ComponentName componentName) throws android.os.RemoteException;

    void runGcForTest() throws android.os.RemoteException;

    void setBiometicContextListener(android.hardware.biometrics.IBiometricContextListener iBiometricContextListener) throws android.os.RemoteException;

    void setIcon(java.lang.String str, com.android.internal.statusbar.StatusBarIcon statusBarIcon) throws android.os.RemoteException;

    void setImeWindowStatus(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) throws android.os.RemoteException;

    void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws android.os.RemoteException;

    void setQsTiles(java.lang.String[] strArr) throws android.os.RemoteException;

    void setTopAppHidesStatusBar(boolean z) throws android.os.RemoteException;

    void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws android.os.RemoteException;

    void setWindowState(int i, int i2, int i3) throws android.os.RemoteException;

    void showAssistDisclosure() throws android.os.RemoteException;

    void showAuthenticationDialog(android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, java.lang.String str, long j2) throws android.os.RemoteException;

    void showGlobalActionsMenu() throws android.os.RemoteException;

    void showInattentiveSleepWarning() throws android.os.RemoteException;

    void showMediaOutputSwitcher(java.lang.String str) throws android.os.RemoteException;

    void showPictureInPictureMenu() throws android.os.RemoteException;

    void showPinningEnterExitToast(boolean z) throws android.os.RemoteException;

    void showPinningEscapeToast() throws android.os.RemoteException;

    void showRearDisplayDialog(int i) throws android.os.RemoteException;

    void showRecentApps(boolean z) throws android.os.RemoteException;

    void showScreenPinningRequest(int i) throws android.os.RemoteException;

    void showShutdownUi(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException;

    void showToast(int i, java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.IBinder iBinder2, int i2, android.app.ITransientNotificationCallback iTransientNotificationCallback, int i3) throws android.os.RemoteException;

    void showTransient(int i, int i2, boolean z) throws android.os.RemoteException;

    void showWirelessChargingAnimation(int i) throws android.os.RemoteException;

    void startAssist(android.os.Bundle bundle) throws android.os.RemoteException;

    void startTracing() throws android.os.RemoteException;

    void stopTracing() throws android.os.RemoteException;

    void suppressAmbientDisplay(boolean z) throws android.os.RemoteException;

    void toggleKeyboardShortcutsMenu(int i) throws android.os.RemoteException;

    void togglePanel() throws android.os.RemoteException;

    void toggleRecentApps() throws android.os.RemoteException;

    void toggleSplitScreen() throws android.os.RemoteException;

    void toggleTaskbar() throws android.os.RemoteException;

    void unregisterNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException;

    void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void updateMediaTapToTransferSenderDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, com.android.internal.statusbar.IUndoMediaTransferCallback iUndoMediaTransferCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.statusbar.IStatusBar {
        @Override // com.android.internal.statusbar.IStatusBar
        public void setIcon(java.lang.String str, com.android.internal.statusbar.StatusBarIcon statusBarIcon) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void removeIcon(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void disable(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateExpandNotificationsPanel() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateExpandSettingsPanel(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void animateCollapsePanels() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void togglePanel() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showWirelessChargingAnimation(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setImeWindowStatus(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setWindowState(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showRecentApps(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideRecentApps(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleRecentApps() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleTaskbar() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleSplitScreen() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void preloadRecentApps() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void cancelPreloadRecentApps() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showScreenPinningRequest(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void confirmImmersivePrompt() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void immersiveModeChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dismissKeyboardShortcutsMenu() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void toggleKeyboardShortcutsMenu(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionPending(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionCancelled(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionStarting(int i, long j, long j2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void appTransitionFinished(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showAssistDisclosure() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startAssist(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onCameraLaunchGestureDetected(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onEmergencyActionLaunchGestureDetected() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPictureInPictureMenu() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showGlobalActionsMenu() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onProposedRotationChanged(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setTopAppHidesStatusBar(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void addQsTile(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void addQsTileToFrontOrEnd(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void remQsTile(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setQsTiles(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void clickQsTile(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void handleSystemKey(android.view.KeyEvent keyEvent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPinningEnterExitToast(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showPinningEscapeToast() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showShutdownUi(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showAuthenticationDialog(android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, java.lang.String str, long j2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricAuthenticated(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricHelp(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onBiometricError(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideAuthenticationDialog(long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setBiometicContextListener(android.hardware.biometrics.IBiometricContextListener iBiometricContextListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onDisplayReady(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onRecentsAnimationStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void onSystemBarAttributesChanged(int i, int i2, com.android.internal.view.AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, java.lang.String str, com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showTransient(int i, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void abortTransient(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showInattentiveSleepWarning() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dismissInattentiveSleepWarning(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showToast(int i, java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.IBinder iBinder2, int i2, android.app.ITransientNotificationCallback iTransientNotificationCallback, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void hideToast(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void startTracing() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void stopTracing() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void suppressAmbientDisplay(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestMagnificationConnection(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void passThroughShellCommand(java.lang.String[] strArr, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void runGcForTest() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestTileServiceListeningState(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void requestAddTile(int i, android.content.ComponentName componentName, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.graphics.drawable.Icon icon, com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void cancelRequestAddTile(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void updateMediaTapToTransferSenderDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, com.android.internal.statusbar.IUndoMediaTransferCallback iUndoMediaTransferCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void registerNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void unregisterNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void dumpProto(java.lang.String[] strArr, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showRearDisplayDialog(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void moveFocusedTaskToFullscreen(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void enterStageSplitFromRunningApp(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void showMediaOutputSwitcher(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBar
        public void enterDesktop(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.statusbar.IStatusBar {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.statusbar.IStatusBar";
        static final int TRANSACTION_abortTransient = 55;
        static final int TRANSACTION_addQsTile = 35;
        static final int TRANSACTION_addQsTileToFrontOrEnd = 36;
        static final int TRANSACTION_animateCollapsePanels = 6;
        static final int TRANSACTION_animateExpandNotificationsPanel = 4;
        static final int TRANSACTION_animateExpandSettingsPanel = 5;
        static final int TRANSACTION_appTransitionCancelled = 24;
        static final int TRANSACTION_appTransitionFinished = 26;
        static final int TRANSACTION_appTransitionPending = 23;
        static final int TRANSACTION_appTransitionStarting = 25;
        static final int TRANSACTION_cancelPreloadRecentApps = 17;
        static final int TRANSACTION_cancelRequestAddTile = 69;
        static final int TRANSACTION_clickQsTile = 39;
        static final int TRANSACTION_confirmImmersivePrompt = 19;
        static final int TRANSACTION_disable = 3;
        static final int TRANSACTION_dismissInattentiveSleepWarning = 57;
        static final int TRANSACTION_dismissKeyboardShortcutsMenu = 21;
        static final int TRANSACTION_dumpProto = 74;
        static final int TRANSACTION_enterDesktop = 79;
        static final int TRANSACTION_enterStageSplitFromRunningApp = 77;
        static final int TRANSACTION_handleSystemKey = 40;
        static final int TRANSACTION_hideAuthenticationDialog = 48;
        static final int TRANSACTION_hideRecentApps = 12;
        static final int TRANSACTION_hideToast = 59;
        static final int TRANSACTION_immersiveModeChanged = 20;
        static final int TRANSACTION_moveFocusedTaskToFullscreen = 76;
        static final int TRANSACTION_onBiometricAuthenticated = 45;
        static final int TRANSACTION_onBiometricError = 47;
        static final int TRANSACTION_onBiometricHelp = 46;
        static final int TRANSACTION_onCameraLaunchGestureDetected = 29;
        static final int TRANSACTION_onDisplayReady = 51;
        static final int TRANSACTION_onEmergencyActionLaunchGestureDetected = 30;
        static final int TRANSACTION_onProposedRotationChanged = 33;
        static final int TRANSACTION_onRecentsAnimationStateChanged = 52;
        static final int TRANSACTION_onSystemBarAttributesChanged = 53;
        static final int TRANSACTION_passThroughShellCommand = 64;
        static final int TRANSACTION_preloadRecentApps = 16;
        static final int TRANSACTION_registerNearbyMediaDevicesProvider = 72;
        static final int TRANSACTION_remQsTile = 37;
        static final int TRANSACTION_removeIcon = 2;
        static final int TRANSACTION_requestAddTile = 68;
        static final int TRANSACTION_requestMagnificationConnection = 63;
        static final int TRANSACTION_requestTileServiceListeningState = 67;
        static final int TRANSACTION_runGcForTest = 66;
        static final int TRANSACTION_setBiometicContextListener = 49;
        static final int TRANSACTION_setIcon = 1;
        static final int TRANSACTION_setImeWindowStatus = 9;
        static final int TRANSACTION_setNavigationBarLumaSamplingEnabled = 65;
        static final int TRANSACTION_setQsTiles = 38;
        static final int TRANSACTION_setTopAppHidesStatusBar = 34;
        static final int TRANSACTION_setUdfpsRefreshRateCallback = 50;
        static final int TRANSACTION_setWindowState = 10;
        static final int TRANSACTION_showAssistDisclosure = 27;
        static final int TRANSACTION_showAuthenticationDialog = 44;
        static final int TRANSACTION_showGlobalActionsMenu = 32;
        static final int TRANSACTION_showInattentiveSleepWarning = 56;
        static final int TRANSACTION_showMediaOutputSwitcher = 78;
        static final int TRANSACTION_showPictureInPictureMenu = 31;
        static final int TRANSACTION_showPinningEnterExitToast = 41;
        static final int TRANSACTION_showPinningEscapeToast = 42;
        static final int TRANSACTION_showRearDisplayDialog = 75;
        static final int TRANSACTION_showRecentApps = 11;
        static final int TRANSACTION_showScreenPinningRequest = 18;
        static final int TRANSACTION_showShutdownUi = 43;
        static final int TRANSACTION_showToast = 58;
        static final int TRANSACTION_showTransient = 54;
        static final int TRANSACTION_showWirelessChargingAnimation = 8;
        static final int TRANSACTION_startAssist = 28;
        static final int TRANSACTION_startTracing = 60;
        static final int TRANSACTION_stopTracing = 61;
        static final int TRANSACTION_suppressAmbientDisplay = 62;
        static final int TRANSACTION_toggleKeyboardShortcutsMenu = 22;
        static final int TRANSACTION_togglePanel = 7;
        static final int TRANSACTION_toggleRecentApps = 13;
        static final int TRANSACTION_toggleSplitScreen = 15;
        static final int TRANSACTION_toggleTaskbar = 14;
        static final int TRANSACTION_unregisterNearbyMediaDevicesProvider = 73;
        static final int TRANSACTION_updateMediaTapToTransferReceiverDisplay = 71;
        static final int TRANSACTION_updateMediaTapToTransferSenderDisplay = 70;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.statusbar.IStatusBar asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.statusbar.IStatusBar)) {
                return (com.android.internal.statusbar.IStatusBar) queryLocalInterface;
            }
            return new com.android.internal.statusbar.IStatusBar.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setIcon";
                case 2:
                    return "removeIcon";
                case 3:
                    return "disable";
                case 4:
                    return "animateExpandNotificationsPanel";
                case 5:
                    return "animateExpandSettingsPanel";
                case 6:
                    return "animateCollapsePanels";
                case 7:
                    return "togglePanel";
                case 8:
                    return "showWirelessChargingAnimation";
                case 9:
                    return "setImeWindowStatus";
                case 10:
                    return "setWindowState";
                case 11:
                    return "showRecentApps";
                case 12:
                    return "hideRecentApps";
                case 13:
                    return "toggleRecentApps";
                case 14:
                    return "toggleTaskbar";
                case 15:
                    return "toggleSplitScreen";
                case 16:
                    return "preloadRecentApps";
                case 17:
                    return "cancelPreloadRecentApps";
                case 18:
                    return "showScreenPinningRequest";
                case 19:
                    return "confirmImmersivePrompt";
                case 20:
                    return "immersiveModeChanged";
                case 21:
                    return "dismissKeyboardShortcutsMenu";
                case 22:
                    return "toggleKeyboardShortcutsMenu";
                case 23:
                    return "appTransitionPending";
                case 24:
                    return "appTransitionCancelled";
                case 25:
                    return "appTransitionStarting";
                case 26:
                    return "appTransitionFinished";
                case 27:
                    return "showAssistDisclosure";
                case 28:
                    return "startAssist";
                case 29:
                    return "onCameraLaunchGestureDetected";
                case 30:
                    return "onEmergencyActionLaunchGestureDetected";
                case 31:
                    return "showPictureInPictureMenu";
                case 32:
                    return "showGlobalActionsMenu";
                case 33:
                    return "onProposedRotationChanged";
                case 34:
                    return "setTopAppHidesStatusBar";
                case 35:
                    return "addQsTile";
                case 36:
                    return "addQsTileToFrontOrEnd";
                case 37:
                    return "remQsTile";
                case 38:
                    return "setQsTiles";
                case 39:
                    return "clickQsTile";
                case 40:
                    return "handleSystemKey";
                case 41:
                    return "showPinningEnterExitToast";
                case 42:
                    return "showPinningEscapeToast";
                case 43:
                    return "showShutdownUi";
                case 44:
                    return "showAuthenticationDialog";
                case 45:
                    return "onBiometricAuthenticated";
                case 46:
                    return "onBiometricHelp";
                case 47:
                    return "onBiometricError";
                case 48:
                    return "hideAuthenticationDialog";
                case 49:
                    return "setBiometicContextListener";
                case 50:
                    return "setUdfpsRefreshRateCallback";
                case 51:
                    return "onDisplayReady";
                case 52:
                    return "onRecentsAnimationStateChanged";
                case 53:
                    return "onSystemBarAttributesChanged";
                case 54:
                    return "showTransient";
                case 55:
                    return "abortTransient";
                case 56:
                    return "showInattentiveSleepWarning";
                case 57:
                    return "dismissInattentiveSleepWarning";
                case 58:
                    return "showToast";
                case 59:
                    return "hideToast";
                case 60:
                    return "startTracing";
                case 61:
                    return "stopTracing";
                case 62:
                    return "suppressAmbientDisplay";
                case 63:
                    return "requestMagnificationConnection";
                case 64:
                    return "passThroughShellCommand";
                case 65:
                    return "setNavigationBarLumaSamplingEnabled";
                case 66:
                    return "runGcForTest";
                case 67:
                    return "requestTileServiceListeningState";
                case 68:
                    return "requestAddTile";
                case 69:
                    return "cancelRequestAddTile";
                case 70:
                    return "updateMediaTapToTransferSenderDisplay";
                case 71:
                    return "updateMediaTapToTransferReceiverDisplay";
                case 72:
                    return "registerNearbyMediaDevicesProvider";
                case 73:
                    return "unregisterNearbyMediaDevicesProvider";
                case 74:
                    return "dumpProto";
                case 75:
                    return "showRearDisplayDialog";
                case 76:
                    return "moveFocusedTaskToFullscreen";
                case 77:
                    return "enterStageSplitFromRunningApp";
                case 78:
                    return "showMediaOutputSwitcher";
                case 79:
                    return "enterDesktop";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    com.android.internal.statusbar.StatusBarIcon statusBarIcon = (com.android.internal.statusbar.StatusBarIcon) parcel.readTypedObject(com.android.internal.statusbar.StatusBarIcon.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIcon(readString, statusBarIcon);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeIcon(readString2);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable(readInt, readInt2, readInt3);
                    return true;
                case 4:
                    animateExpandNotificationsPanel();
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    animateExpandSettingsPanel(readString3);
                    return true;
                case 6:
                    animateCollapsePanels();
                    return true;
                case 7:
                    togglePanel();
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showWirelessChargingAnimation(readInt4);
                    return true;
                case 9:
                    int readInt5 = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatus(readInt5, readStrongBinder, readInt6, readInt7, readBoolean);
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setWindowState(readInt8, readInt9, readInt10);
                    return true;
                case 11:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showRecentApps(readBoolean2);
                    return true;
                case 12:
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    hideRecentApps(readBoolean3, readBoolean4);
                    return true;
                case 13:
                    toggleRecentApps();
                    return true;
                case 14:
                    toggleTaskbar();
                    return true;
                case 15:
                    toggleSplitScreen();
                    return true;
                case 16:
                    preloadRecentApps();
                    return true;
                case 17:
                    cancelPreloadRecentApps();
                    return true;
                case 18:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showScreenPinningRequest(readInt11);
                    return true;
                case 19:
                    confirmImmersivePrompt();
                    return true;
                case 20:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    immersiveModeChanged(readInt12, readBoolean5);
                    return true;
                case 21:
                    dismissKeyboardShortcutsMenu();
                    return true;
                case 22:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    toggleKeyboardShortcutsMenu(readInt13);
                    return true;
                case 23:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionPending(readInt14);
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionCancelled(readInt15);
                    return true;
                case 25:
                    int readInt16 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    appTransitionStarting(readInt16, readLong, readLong2);
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    appTransitionFinished(readInt17);
                    return true;
                case 27:
                    showAssistDisclosure();
                    return true;
                case 28:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAssist(bundle);
                    return true;
                case 29:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCameraLaunchGestureDetected(readInt18);
                    return true;
                case 30:
                    onEmergencyActionLaunchGestureDetected();
                    return true;
                case 31:
                    showPictureInPictureMenu();
                    return true;
                case 32:
                    showGlobalActionsMenu();
                    return true;
                case 33:
                    int readInt19 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onProposedRotationChanged(readInt19, readBoolean6);
                    return true;
                case 34:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTopAppHidesStatusBar(readBoolean7);
                    return true;
                case 35:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    addQsTile(componentName);
                    return true;
                case 36:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addQsTileToFrontOrEnd(componentName2, readBoolean8);
                    return true;
                case 37:
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    remQsTile(componentName3);
                    return true;
                case 38:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setQsTiles(createStringArray);
                    return true;
                case 39:
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    clickQsTile(componentName4);
                    return true;
                case 40:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleSystemKey(keyEvent);
                    return true;
                case 41:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showPinningEnterExitToast(readBoolean9);
                    return true;
                case 42:
                    showPinningEscapeToast();
                    return true;
                case 43:
                    boolean readBoolean10 = parcel.readBoolean();
                    java.lang.String readString4 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showShutdownUi(readBoolean10, readString4, readBoolean11);
                    return true;
                case 44:
                    android.hardware.biometrics.PromptInfo promptInfo = (android.hardware.biometrics.PromptInfo) parcel.readTypedObject(android.hardware.biometrics.PromptInfo.CREATOR);
                    android.hardware.biometrics.IBiometricSysuiReceiver asInterface = android.hardware.biometrics.IBiometricSysuiReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean12 = parcel.readBoolean();
                    boolean readBoolean13 = parcel.readBoolean();
                    int readInt20 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    java.lang.String readString5 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    showAuthenticationDialog(promptInfo, asInterface, createIntArray, readBoolean12, readBoolean13, readInt20, readLong3, readString5, readLong4);
                    return true;
                case 45:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(readInt21);
                    return true;
                case 46:
                    int readInt22 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(readInt22, readString6);
                    return true;
                case 47:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricError(readInt23, readInt24, readInt25);
                    return true;
                case 48:
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    hideAuthenticationDialog(readLong5);
                    return true;
                case 49:
                    android.hardware.biometrics.IBiometricContextListener asInterface2 = android.hardware.biometrics.IBiometricContextListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setBiometicContextListener(asInterface2);
                    return true;
                case 50:
                    android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback asInterface3 = android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsRefreshRateCallback(asInterface3);
                    return true;
                case 51:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDisplayReady(readInt26);
                    return true;
                case 52:
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRecentsAnimationStateChanged(readBoolean14);
                    return true;
                case 53:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    com.android.internal.view.AppearanceRegion[] appearanceRegionArr = (com.android.internal.view.AppearanceRegion[]) parcel.createTypedArray(com.android.internal.view.AppearanceRegion.CREATOR);
                    boolean readBoolean15 = parcel.readBoolean();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr = (com.android.internal.statusbar.LetterboxDetails[]) parcel.createTypedArray(com.android.internal.statusbar.LetterboxDetails.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSystemBarAttributesChanged(readInt27, readInt28, appearanceRegionArr, readBoolean15, readInt29, readInt30, readString7, letterboxDetailsArr);
                    return true;
                case 54:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showTransient(readInt31, readInt32, readBoolean16);
                    return true;
                case 55:
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    abortTransient(readInt33, readInt34);
                    return true;
                case 56:
                    showInattentiveSleepWarning();
                    return true;
                case 57:
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissInattentiveSleepWarning(readBoolean17);
                    return true;
                case 58:
                    int readInt35 = parcel.readInt();
                    java.lang.String readString8 = parcel.readString();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt36 = parcel.readInt();
                    android.app.ITransientNotificationCallback asInterface4 = android.app.ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showToast(readInt35, readString8, readStrongBinder2, charSequence, readStrongBinder3, readInt36, asInterface4, readInt37);
                    return true;
                case 59:
                    java.lang.String readString9 = parcel.readString();
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    hideToast(readString9, readStrongBinder4);
                    return true;
                case 60:
                    startTracing();
                    return true;
                case 61:
                    stopTracing();
                    return true;
                case 62:
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(readBoolean18);
                    return true;
                case 63:
                    boolean readBoolean19 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    requestMagnificationConnection(readBoolean19);
                    return true;
                case 64:
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    passThroughShellCommand(createStringArray2, parcelFileDescriptor);
                    return true;
                case 65:
                    int readInt38 = parcel.readInt();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNavigationBarLumaSamplingEnabled(readInt38, readBoolean20);
                    return true;
                case 66:
                    runGcForTest();
                    return true;
                case 67:
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestTileServiceListeningState(componentName5);
                    return true;
                case 68:
                    int readInt39 = parcel.readInt();
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    java.lang.CharSequence charSequence3 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    com.android.internal.statusbar.IAddTileResultCallback asInterface5 = com.android.internal.statusbar.IAddTileResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAddTile(readInt39, componentName6, charSequence2, charSequence3, icon, asInterface5);
                    return true;
                case 69:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelRequestAddTile(readString10);
                    return true;
                case 70:
                    int readInt40 = parcel.readInt();
                    android.media.MediaRoute2Info mediaRoute2Info = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    com.android.internal.statusbar.IUndoMediaTransferCallback asInterface6 = com.android.internal.statusbar.IUndoMediaTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferSenderDisplay(readInt40, mediaRoute2Info, asInterface6);
                    return true;
                case 71:
                    int readInt41 = parcel.readInt();
                    android.media.MediaRoute2Info mediaRoute2Info2 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    android.graphics.drawable.Icon icon2 = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    java.lang.CharSequence charSequence4 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferReceiverDisplay(readInt41, mediaRoute2Info2, icon2, charSequence4);
                    return true;
                case 72:
                    android.media.INearbyMediaDevicesProvider asInterface7 = android.media.INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerNearbyMediaDevicesProvider(asInterface7);
                    return true;
                case 73:
                    android.media.INearbyMediaDevicesProvider asInterface8 = android.media.INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNearbyMediaDevicesProvider(asInterface8);
                    return true;
                case 74:
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    dumpProto(createStringArray3, parcelFileDescriptor2);
                    return true;
                case 75:
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showRearDisplayDialog(readInt42);
                    return true;
                case 76:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    moveFocusedTaskToFullscreen(readInt43);
                    return true;
                case 77:
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enterStageSplitFromRunningApp(readBoolean21);
                    return true;
                case 78:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    showMediaOutputSwitcher(readString11);
                    return true;
                case 79:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enterDesktop(readInt44);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.statusbar.IStatusBar {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setIcon(java.lang.String str, com.android.internal.statusbar.StatusBarIcon statusBarIcon) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(statusBarIcon, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void removeIcon(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void disable(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandNotificationsPanel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateExpandSettingsPanel(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void animateCollapsePanels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void togglePanel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showWirelessChargingAnimation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setImeWindowStatus(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setWindowState(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRecentApps(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideRecentApps(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleRecentApps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleTaskbar() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleSplitScreen() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void preloadRecentApps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelPreloadRecentApps() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showScreenPinningRequest(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void confirmImmersivePrompt() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void immersiveModeChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissKeyboardShortcutsMenu() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void toggleKeyboardShortcutsMenu(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionPending(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionCancelled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionStarting(int i, long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void appTransitionFinished(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAssistDisclosure() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startAssist(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onCameraLaunchGestureDetected(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onEmergencyActionLaunchGestureDetected() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPictureInPictureMenu() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showGlobalActionsMenu() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onProposedRotationChanged(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setTopAppHidesStatusBar(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void addQsTile(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void addQsTileToFrontOrEnd(android.content.ComponentName componentName, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void remQsTile(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setQsTiles(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void clickQsTile(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void handleSystemKey(android.view.KeyEvent keyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEnterExitToast(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showPinningEscapeToast() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showShutdownUi(boolean z, java.lang.String str, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showAuthenticationDialog(android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, java.lang.String str, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(promptInfo, 0);
                    obtain.writeStrongInterface(iBiometricSysuiReceiver);
                    obtain.writeIntArray(iArr);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeLong(j2);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricAuthenticated(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricHelp(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onBiometricError(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(47, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideAuthenticationDialog(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(48, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setBiometicContextListener(android.hardware.biometrics.IBiometricContextListener iBiometricContextListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricContextListener);
                    this.mRemote.transact(49, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUdfpsRefreshRateRequestCallback);
                    this.mRemote.transact(50, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onDisplayReady(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onRecentsAnimationStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void onSystemBarAttributesChanged(int i, int i2, com.android.internal.view.AppearanceRegion[] appearanceRegionArr, boolean z, int i3, int i4, java.lang.String str, com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(appearanceRegionArr, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    obtain.writeTypedArray(letterboxDetailsArr, 0);
                    this.mRemote.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showTransient(int i, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void abortTransient(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showInattentiveSleepWarning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dismissInattentiveSleepWarning(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(57, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showToast(int i, java.lang.String str, android.os.IBinder iBinder, java.lang.CharSequence charSequence, android.os.IBinder iBinder2, int i2, android.app.ITransientNotificationCallback iTransientNotificationCallback, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iTransientNotificationCallback);
                    obtain.writeInt(i3);
                    this.mRemote.transact(58, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void hideToast(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(59, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void startTracing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void stopTracing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void suppressAmbientDisplay(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestMagnificationConnection(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(63, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void passThroughShellCommand(java.lang.String[] strArr, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(64, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void setNavigationBarLumaSamplingEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(65, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void runGcForTest() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestTileServiceListeningState(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(67, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void requestAddTile(int i, android.content.ComponentName componentName, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.graphics.drawable.Icon icon, com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (charSequence2 != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence2, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeStrongInterface(iAddTileResultCallback);
                    this.mRemote.transact(68, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void cancelRequestAddTile(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(69, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferSenderDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, com.android.internal.statusbar.IUndoMediaTransferCallback iUndoMediaTransferCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeStrongInterface(iUndoMediaTransferCallback);
                    this.mRemote.transact(70, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(icon, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(71, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void registerNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(72, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void unregisterNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(73, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void dumpProto(java.lang.String[] strArr, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(74, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showRearDisplayDialog(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void moveFocusedTaskToFullscreen(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void enterStageSplitFromRunningApp(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(77, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void showMediaOutputSwitcher(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(78, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBar
            public void enterDesktop(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBar.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(79, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 78;
        }
    }
}
