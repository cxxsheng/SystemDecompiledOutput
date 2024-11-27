package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public interface IStatusBarService extends android.os.IInterface {
    void addTile(android.content.ComponentName componentName) throws android.os.RemoteException;

    void cancelRequestAddTile(java.lang.String str) throws android.os.RemoteException;

    void clearInlineReplyUriPermissions(java.lang.String str) throws android.os.RemoteException;

    void clearNotificationEffects() throws android.os.RemoteException;

    void clickTile(android.content.ComponentName componentName) throws android.os.RemoteException;

    void collapsePanels() throws android.os.RemoteException;

    void disable(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    void disable2(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException;

    void disable2ForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) throws android.os.RemoteException;

    void disableForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) throws android.os.RemoteException;

    void dismissInattentiveSleepWarning(boolean z) throws android.os.RemoteException;

    void expandNotificationsPanel() throws android.os.RemoteException;

    void expandSettingsPanel(java.lang.String str) throws android.os.RemoteException;

    int[] getDisableFlags(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    int getLastSystemKey() throws android.os.RemoteException;

    int getNavBarMode() throws android.os.RemoteException;

    void grantInlineReplyUriPermission(java.lang.String str, android.net.Uri uri, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException;

    void handleSystemKey(android.view.KeyEvent keyEvent) throws android.os.RemoteException;

    void hideAuthenticationDialog(long j) throws android.os.RemoteException;

    void hideCurrentInputMethodForBubbles(int i) throws android.os.RemoteException;

    boolean isTracing() throws android.os.RemoteException;

    void onBiometricAuthenticated(int i) throws android.os.RemoteException;

    void onBiometricError(int i, int i2, int i3) throws android.os.RemoteException;

    void onBiometricHelp(int i, java.lang.String str) throws android.os.RemoteException;

    void onBubbleMetadataFlagChanged(java.lang.String str, int i) throws android.os.RemoteException;

    void onClearAllNotifications(int i) throws android.os.RemoteException;

    void onGlobalActionsHidden() throws android.os.RemoteException;

    void onGlobalActionsShown() throws android.os.RemoteException;

    void onNotificationActionClick(java.lang.String str, int i, android.app.Notification.Action action, com.android.internal.statusbar.NotificationVisibility notificationVisibility, boolean z) throws android.os.RemoteException;

    void onNotificationBubbleChanged(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    void onNotificationClear(java.lang.String str, int i, java.lang.String str2, int i2, int i3, com.android.internal.statusbar.NotificationVisibility notificationVisibility) throws android.os.RemoteException;

    void onNotificationClick(java.lang.String str, com.android.internal.statusbar.NotificationVisibility notificationVisibility) throws android.os.RemoteException;

    void onNotificationDirectReplied(java.lang.String str) throws android.os.RemoteException;

    void onNotificationError(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3, int i4) throws android.os.RemoteException;

    void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2, int i) throws android.os.RemoteException;

    void onNotificationFeedbackReceived(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void onNotificationSettingsViewed(java.lang.String str) throws android.os.RemoteException;

    void onNotificationSmartReplySent(java.lang.String str, int i, java.lang.CharSequence charSequence, int i2, boolean z) throws android.os.RemoteException;

    void onNotificationSmartSuggestionsAdded(java.lang.String str, int i, int i2, boolean z, boolean z2) throws android.os.RemoteException;

    void onNotificationVisibilityChanged(com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr, com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr2) throws android.os.RemoteException;

    void onPanelHidden() throws android.os.RemoteException;

    void onPanelRevealed(boolean z, int i) throws android.os.RemoteException;

    void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException;

    void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException;

    void reboot(boolean z, java.lang.String str) throws android.os.RemoteException;

    void registerNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException;

    void registerSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) throws android.os.RemoteException;

    com.android.internal.statusbar.RegisterStatusBarResult registerStatusBar(com.android.internal.statusbar.IStatusBar iStatusBar) throws android.os.RemoteException;

    void remTile(android.content.ComponentName componentName) throws android.os.RemoteException;

    void removeIcon(java.lang.String str) throws android.os.RemoteException;

    void requestAddTile(android.content.ComponentName componentName, java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, int i, com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback) throws android.os.RemoteException;

    void requestTileServiceListeningState(android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void restart() throws android.os.RemoteException;

    void setBiometicContextListener(android.hardware.biometrics.IBiometricContextListener iBiometricContextListener) throws android.os.RemoteException;

    void setIcon(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) throws android.os.RemoteException;

    void setIconVisibility(java.lang.String str, boolean z) throws android.os.RemoteException;

    void setImeWindowStatus(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) throws android.os.RemoteException;

    void setNavBarMode(int i) throws android.os.RemoteException;

    void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws android.os.RemoteException;

    void showAuthenticationDialog(android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, java.lang.String str, long j2) throws android.os.RemoteException;

    void showInattentiveSleepWarning() throws android.os.RemoteException;

    void showPinningEnterExitToast(boolean z) throws android.os.RemoteException;

    void showPinningEscapeToast() throws android.os.RemoteException;

    void showRearDisplayDialog(int i) throws android.os.RemoteException;

    void shutdown() throws android.os.RemoteException;

    void startAssist(android.os.Bundle bundle) throws android.os.RemoteException;

    void startTracing() throws android.os.RemoteException;

    void stopTracing() throws android.os.RemoteException;

    void suppressAmbientDisplay(boolean z) throws android.os.RemoteException;

    void togglePanel() throws android.os.RemoteException;

    void unregisterNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException;

    void unregisterSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) throws android.os.RemoteException;

    void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void updateMediaTapToTransferSenderDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, com.android.internal.statusbar.IUndoMediaTransferCallback iUndoMediaTransferCallback) throws android.os.RemoteException;

    public static class Default implements com.android.internal.statusbar.IStatusBarService {
        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandNotificationsPanel() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void collapsePanels() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void togglePanel() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disableForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void disable2ForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int[] getDisableFlags(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIcon(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setIconVisibility(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void removeIcon(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setImeWindowStatus(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void expandSettingsPanel(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public com.android.internal.statusbar.RegisterStatusBarResult registerStatusBar(com.android.internal.statusbar.IStatusBar iStatusBar) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onPanelRevealed(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onPanelHidden() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clearNotificationEffects() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationClick(java.lang.String str, com.android.internal.statusbar.NotificationVisibility notificationVisibility) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationActionClick(java.lang.String str, int i, android.app.Notification.Action action, com.android.internal.statusbar.NotificationVisibility notificationVisibility, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationError(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3, int i4) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onClearAllNotifications(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationClear(java.lang.String str, int i, java.lang.String str2, int i2, int i3, com.android.internal.statusbar.NotificationVisibility notificationVisibility) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationVisibilityChanged(com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr, com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationDirectReplied(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSmartSuggestionsAdded(java.lang.String str, int i, int i2, boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSmartReplySent(java.lang.String str, int i, java.lang.CharSequence charSequence, int i2, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationSettingsViewed(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationBubbleChanged(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBubbleMetadataFlagChanged(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void hideCurrentInputMethodForBubbles(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void grantInlineReplyUriPermission(java.lang.String str, android.net.Uri uri, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clearInlineReplyUriPermissions(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onNotificationFeedbackReceived(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onGlobalActionsShown() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onGlobalActionsHidden() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void shutdown() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void reboot(boolean z, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void restart() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void addTile(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void remTile(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void clickTile(android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void handleSystemKey(android.view.KeyEvent keyEvent) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int getLastSystemKey() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showPinningEnterExitToast(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showPinningEscapeToast() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showAuthenticationDialog(android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, java.lang.String str, long j2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricAuthenticated(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricHelp(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onBiometricError(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void hideAuthenticationDialog(long j) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setBiometicContextListener(android.hardware.biometrics.IBiometricContextListener iBiometricContextListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showInattentiveSleepWarning() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void dismissInattentiveSleepWarning(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void startTracing() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void stopTracing() throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public boolean isTracing() throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void suppressAmbientDisplay(boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void requestTileServiceListeningState(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void requestAddTile(android.content.ComponentName componentName, java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, int i, com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void cancelRequestAddTile(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void setNavBarMode(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public int getNavBarMode() throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void unregisterSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void updateMediaTapToTransferSenderDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, com.android.internal.statusbar.IUndoMediaTransferCallback iUndoMediaTransferCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void registerNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void unregisterNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void showRearDisplayDialog(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.statusbar.IStatusBarService
        public void startAssist(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.statusbar.IStatusBarService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.statusbar.IStatusBarService";
        static final int TRANSACTION_addTile = 40;
        static final int TRANSACTION_cancelRequestAddTile = 62;
        static final int TRANSACTION_clearInlineReplyUriPermissions = 33;
        static final int TRANSACTION_clearNotificationEffects = 17;
        static final int TRANSACTION_clickTile = 42;
        static final int TRANSACTION_collapsePanels = 2;
        static final int TRANSACTION_disable = 4;
        static final int TRANSACTION_disable2 = 6;
        static final int TRANSACTION_disable2ForUser = 7;
        static final int TRANSACTION_disableForUser = 5;
        static final int TRANSACTION_dismissInattentiveSleepWarning = 55;
        static final int TRANSACTION_expandNotificationsPanel = 1;
        static final int TRANSACTION_expandSettingsPanel = 13;
        static final int TRANSACTION_getDisableFlags = 8;
        static final int TRANSACTION_getLastSystemKey = 44;
        static final int TRANSACTION_getNavBarMode = 64;
        static final int TRANSACTION_grantInlineReplyUriPermission = 32;
        static final int TRANSACTION_handleSystemKey = 43;
        static final int TRANSACTION_hideAuthenticationDialog = 51;
        static final int TRANSACTION_hideCurrentInputMethodForBubbles = 31;
        static final int TRANSACTION_isTracing = 58;
        static final int TRANSACTION_onBiometricAuthenticated = 48;
        static final int TRANSACTION_onBiometricError = 50;
        static final int TRANSACTION_onBiometricHelp = 49;
        static final int TRANSACTION_onBubbleMetadataFlagChanged = 30;
        static final int TRANSACTION_onClearAllNotifications = 21;
        static final int TRANSACTION_onGlobalActionsHidden = 36;
        static final int TRANSACTION_onGlobalActionsShown = 35;
        static final int TRANSACTION_onNotificationActionClick = 19;
        static final int TRANSACTION_onNotificationBubbleChanged = 29;
        static final int TRANSACTION_onNotificationClear = 22;
        static final int TRANSACTION_onNotificationClick = 18;
        static final int TRANSACTION_onNotificationDirectReplied = 25;
        static final int TRANSACTION_onNotificationError = 20;
        static final int TRANSACTION_onNotificationExpansionChanged = 24;
        static final int TRANSACTION_onNotificationFeedbackReceived = 34;
        static final int TRANSACTION_onNotificationSettingsViewed = 28;
        static final int TRANSACTION_onNotificationSmartReplySent = 27;
        static final int TRANSACTION_onNotificationSmartSuggestionsAdded = 26;
        static final int TRANSACTION_onNotificationVisibilityChanged = 23;
        static final int TRANSACTION_onPanelHidden = 16;
        static final int TRANSACTION_onPanelRevealed = 15;
        static final int TRANSACTION_onSessionEnded = 68;
        static final int TRANSACTION_onSessionStarted = 67;
        static final int TRANSACTION_reboot = 38;
        static final int TRANSACTION_registerNearbyMediaDevicesProvider = 71;
        static final int TRANSACTION_registerSessionListener = 65;
        static final int TRANSACTION_registerStatusBar = 14;
        static final int TRANSACTION_remTile = 41;
        static final int TRANSACTION_removeIcon = 11;
        static final int TRANSACTION_requestAddTile = 61;
        static final int TRANSACTION_requestTileServiceListeningState = 60;
        static final int TRANSACTION_restart = 39;
        static final int TRANSACTION_setBiometicContextListener = 52;
        static final int TRANSACTION_setIcon = 9;
        static final int TRANSACTION_setIconVisibility = 10;
        static final int TRANSACTION_setImeWindowStatus = 12;
        static final int TRANSACTION_setNavBarMode = 63;
        static final int TRANSACTION_setUdfpsRefreshRateCallback = 53;
        static final int TRANSACTION_showAuthenticationDialog = 47;
        static final int TRANSACTION_showInattentiveSleepWarning = 54;
        static final int TRANSACTION_showPinningEnterExitToast = 45;
        static final int TRANSACTION_showPinningEscapeToast = 46;
        static final int TRANSACTION_showRearDisplayDialog = 73;
        static final int TRANSACTION_shutdown = 37;
        static final int TRANSACTION_startAssist = 74;
        static final int TRANSACTION_startTracing = 56;
        static final int TRANSACTION_stopTracing = 57;
        static final int TRANSACTION_suppressAmbientDisplay = 59;
        static final int TRANSACTION_togglePanel = 3;
        static final int TRANSACTION_unregisterNearbyMediaDevicesProvider = 72;
        static final int TRANSACTION_unregisterSessionListener = 66;
        static final int TRANSACTION_updateMediaTapToTransferReceiverDisplay = 70;
        static final int TRANSACTION_updateMediaTapToTransferSenderDisplay = 69;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.statusbar.IStatusBarService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.statusbar.IStatusBarService)) {
                return (com.android.internal.statusbar.IStatusBarService) queryLocalInterface;
            }
            return new com.android.internal.statusbar.IStatusBarService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "expandNotificationsPanel";
                case 2:
                    return "collapsePanels";
                case 3:
                    return "togglePanel";
                case 4:
                    return "disable";
                case 5:
                    return "disableForUser";
                case 6:
                    return "disable2";
                case 7:
                    return "disable2ForUser";
                case 8:
                    return "getDisableFlags";
                case 9:
                    return "setIcon";
                case 10:
                    return "setIconVisibility";
                case 11:
                    return "removeIcon";
                case 12:
                    return "setImeWindowStatus";
                case 13:
                    return "expandSettingsPanel";
                case 14:
                    return "registerStatusBar";
                case 15:
                    return "onPanelRevealed";
                case 16:
                    return "onPanelHidden";
                case 17:
                    return "clearNotificationEffects";
                case 18:
                    return "onNotificationClick";
                case 19:
                    return "onNotificationActionClick";
                case 20:
                    return "onNotificationError";
                case 21:
                    return "onClearAllNotifications";
                case 22:
                    return "onNotificationClear";
                case 23:
                    return "onNotificationVisibilityChanged";
                case 24:
                    return "onNotificationExpansionChanged";
                case 25:
                    return "onNotificationDirectReplied";
                case 26:
                    return "onNotificationSmartSuggestionsAdded";
                case 27:
                    return "onNotificationSmartReplySent";
                case 28:
                    return "onNotificationSettingsViewed";
                case 29:
                    return "onNotificationBubbleChanged";
                case 30:
                    return "onBubbleMetadataFlagChanged";
                case 31:
                    return "hideCurrentInputMethodForBubbles";
                case 32:
                    return "grantInlineReplyUriPermission";
                case 33:
                    return "clearInlineReplyUriPermissions";
                case 34:
                    return "onNotificationFeedbackReceived";
                case 35:
                    return "onGlobalActionsShown";
                case 36:
                    return "onGlobalActionsHidden";
                case 37:
                    return "shutdown";
                case 38:
                    return "reboot";
                case 39:
                    return "restart";
                case 40:
                    return "addTile";
                case 41:
                    return "remTile";
                case 42:
                    return "clickTile";
                case 43:
                    return "handleSystemKey";
                case 44:
                    return "getLastSystemKey";
                case 45:
                    return "showPinningEnterExitToast";
                case 46:
                    return "showPinningEscapeToast";
                case 47:
                    return "showAuthenticationDialog";
                case 48:
                    return "onBiometricAuthenticated";
                case 49:
                    return "onBiometricHelp";
                case 50:
                    return "onBiometricError";
                case 51:
                    return "hideAuthenticationDialog";
                case 52:
                    return "setBiometicContextListener";
                case 53:
                    return "setUdfpsRefreshRateCallback";
                case 54:
                    return "showInattentiveSleepWarning";
                case 55:
                    return "dismissInattentiveSleepWarning";
                case 56:
                    return "startTracing";
                case 57:
                    return "stopTracing";
                case 58:
                    return "isTracing";
                case 59:
                    return "suppressAmbientDisplay";
                case 60:
                    return "requestTileServiceListeningState";
                case 61:
                    return "requestAddTile";
                case 62:
                    return "cancelRequestAddTile";
                case 63:
                    return "setNavBarMode";
                case 64:
                    return "getNavBarMode";
                case 65:
                    return "registerSessionListener";
                case 66:
                    return "unregisterSessionListener";
                case 67:
                    return "onSessionStarted";
                case 68:
                    return "onSessionEnded";
                case 69:
                    return "updateMediaTapToTransferSenderDisplay";
                case 70:
                    return "updateMediaTapToTransferReceiverDisplay";
                case 71:
                    return "registerNearbyMediaDevicesProvider";
                case 72:
                    return "unregisterNearbyMediaDevicesProvider";
                case 73:
                    return "showRearDisplayDialog";
                case 74:
                    return "startAssist";
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
                    expandNotificationsPanel();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    collapsePanels();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    togglePanel();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disable(readInt, readStrongBinder, readString);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableForUser(readInt2, readStrongBinder2, readString2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disable2(readInt4, readStrongBinder3, readString3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disable2ForUser(readInt5, readStrongBinder4, readString4, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] disableFlags = getDisableFlags(readStrongBinder5, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(disableFlags);
                    return true;
                case 9:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIcon(readString5, readString6, readInt8, readInt9, readString7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString8 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setIconVisibility(readString8, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeIcon(readString9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt10 = parcel.readInt();
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setImeWindowStatus(readInt10, readStrongBinder6, readInt11, readInt12, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    expandSettingsPanel(readString10);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    com.android.internal.statusbar.IStatusBar asInterface = com.android.internal.statusbar.IStatusBar.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    com.android.internal.statusbar.RegisterStatusBarResult registerStatusBar = registerStatusBar(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerStatusBar, 1);
                    return true;
                case 15:
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPanelRevealed(readBoolean3, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    onPanelHidden();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    clearNotificationEffects();
                    return true;
                case 18:
                    java.lang.String readString11 = parcel.readString();
                    com.android.internal.statusbar.NotificationVisibility notificationVisibility = (com.android.internal.statusbar.NotificationVisibility) parcel.readTypedObject(com.android.internal.statusbar.NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationClick(readString11, notificationVisibility);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString12 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    android.app.Notification.Action action = (android.app.Notification.Action) parcel.readTypedObject(android.app.Notification.Action.CREATOR);
                    com.android.internal.statusbar.NotificationVisibility notificationVisibility2 = (com.android.internal.statusbar.NotificationVisibility) parcel.readTypedObject(com.android.internal.statusbar.NotificationVisibility.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationActionClick(readString12, readInt14, action, notificationVisibility2, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationError(readString13, readString14, readInt15, readInt16, readInt17, readString15, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onClearAllNotifications(readInt19);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    java.lang.String readString16 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    java.lang.String readString17 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    com.android.internal.statusbar.NotificationVisibility notificationVisibility3 = (com.android.internal.statusbar.NotificationVisibility) parcel.readTypedObject(com.android.internal.statusbar.NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationClear(readString16, readInt20, readString17, readInt21, readInt22, notificationVisibility3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr = (com.android.internal.statusbar.NotificationVisibility[]) parcel.createTypedArray(com.android.internal.statusbar.NotificationVisibility.CREATOR);
                    com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr2 = (com.android.internal.statusbar.NotificationVisibility[]) parcel.createTypedArray(com.android.internal.statusbar.NotificationVisibility.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationVisibilityChanged(notificationVisibilityArr, notificationVisibilityArr2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString18 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationExpansionChanged(readString18, readBoolean5, readBoolean6, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationDirectReplied(readString19);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString20 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationSmartSuggestionsAdded(readString20, readInt24, readInt25, readBoolean7, readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    java.lang.String readString21 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt27 = parcel.readInt();
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onNotificationSmartReplySent(readString21, readInt26, charSequence, readInt27, readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onNotificationSettingsViewed(readString22);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    java.lang.String readString23 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNotificationBubbleChanged(readString23, readBoolean10, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    java.lang.String readString24 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBubbleMetadataFlagChanged(readString24, readInt29);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hideCurrentInputMethodForBubbles(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    java.lang.String readString25 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    grantInlineReplyUriPermission(readString25, uri, userHandle, readString26);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearInlineReplyUriPermissions(readString27);
                    return true;
                case 34:
                    java.lang.String readString28 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNotificationFeedbackReceived(readString28, bundle);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    onGlobalActionsShown();
                    parcel2.writeNoException();
                    return true;
                case 36:
                    onGlobalActionsHidden();
                    parcel2.writeNoException();
                    return true;
                case 37:
                    shutdown();
                    parcel2.writeNoException();
                    return true;
                case 38:
                    boolean readBoolean11 = parcel.readBoolean();
                    java.lang.String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reboot(readBoolean11, readString29);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    restart();
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    addTile(componentName);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    remTile(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    clickTile(componentName3);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleSystemKey(keyEvent);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int lastSystemKey = getLastSystemKey();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastSystemKey);
                    return true;
                case 45:
                    boolean readBoolean12 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    showPinningEnterExitToast(readBoolean12);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    showPinningEscapeToast();
                    parcel2.writeNoException();
                    return true;
                case 47:
                    android.hardware.biometrics.PromptInfo promptInfo = (android.hardware.biometrics.PromptInfo) parcel.readTypedObject(android.hardware.biometrics.PromptInfo.CREATOR);
                    android.hardware.biometrics.IBiometricSysuiReceiver asInterface2 = android.hardware.biometrics.IBiometricSysuiReceiver.Stub.asInterface(parcel.readStrongBinder());
                    int[] createIntArray = parcel.createIntArray();
                    boolean readBoolean13 = parcel.readBoolean();
                    boolean readBoolean14 = parcel.readBoolean();
                    int readInt31 = parcel.readInt();
                    long readLong = parcel.readLong();
                    java.lang.String readString30 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    showAuthenticationDialog(promptInfo, asInterface2, createIntArray, readBoolean13, readBoolean14, readInt31, readLong, readString30, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricAuthenticated(readInt32);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt33 = parcel.readInt();
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiometricHelp(readInt33, readString31);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiometricError(readInt34, readInt35, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    hideAuthenticationDialog(readLong3);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    android.hardware.biometrics.IBiometricContextListener asInterface3 = android.hardware.biometrics.IBiometricContextListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setBiometicContextListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback asInterface4 = android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setUdfpsRefreshRateCallback(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    showInattentiveSleepWarning();
                    parcel2.writeNoException();
                    return true;
                case 55:
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    dismissInattentiveSleepWarning(readBoolean15);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    startTracing();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    stopTracing();
                    parcel2.writeNoException();
                    return true;
                case 58:
                    boolean isTracing = isTracing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTracing);
                    return true;
                case 59:
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressAmbientDisplay(readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestTileServiceListeningState(componentName4, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.CharSequence charSequence2 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    int readInt38 = parcel.readInt();
                    com.android.internal.statusbar.IAddTileResultCallback asInterface5 = com.android.internal.statusbar.IAddTileResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestAddTile(componentName5, charSequence2, icon, readInt38, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelRequestAddTile(readString32);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setNavBarMode(readInt39);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int navBarMode = getNavBarMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(navBarMode);
                    return true;
                case 65:
                    int readInt40 = parcel.readInt();
                    com.android.internal.statusbar.ISessionListener asInterface6 = com.android.internal.statusbar.ISessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSessionListener(readInt40, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt41 = parcel.readInt();
                    com.android.internal.statusbar.ISessionListener asInterface7 = com.android.internal.statusbar.ISessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSessionListener(readInt41, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    int readInt42 = parcel.readInt();
                    com.android.internal.logging.InstanceId instanceId = (com.android.internal.logging.InstanceId) parcel.readTypedObject(com.android.internal.logging.InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionStarted(readInt42, instanceId);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    int readInt43 = parcel.readInt();
                    com.android.internal.logging.InstanceId instanceId2 = (com.android.internal.logging.InstanceId) parcel.readTypedObject(com.android.internal.logging.InstanceId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionEnded(readInt43, instanceId2);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int readInt44 = parcel.readInt();
                    android.media.MediaRoute2Info mediaRoute2Info = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    com.android.internal.statusbar.IUndoMediaTransferCallback asInterface8 = com.android.internal.statusbar.IUndoMediaTransferCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferSenderDisplay(readInt44, mediaRoute2Info, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 70:
                    int readInt45 = parcel.readInt();
                    android.media.MediaRoute2Info mediaRoute2Info2 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    android.graphics.drawable.Icon icon2 = (android.graphics.drawable.Icon) parcel.readTypedObject(android.graphics.drawable.Icon.CREATOR);
                    java.lang.CharSequence charSequence3 = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateMediaTapToTransferReceiverDisplay(readInt45, mediaRoute2Info2, icon2, charSequence3);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    android.media.INearbyMediaDevicesProvider asInterface9 = android.media.INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerNearbyMediaDevicesProvider(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 72:
                    android.media.INearbyMediaDevicesProvider asInterface10 = android.media.INearbyMediaDevicesProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterNearbyMediaDevicesProvider(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showRearDisplayDialog(readInt46);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startAssist(bundle2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.statusbar.IStatusBarService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandNotificationsPanel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void collapsePanels() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void togglePanel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disableForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2(int i, android.os.IBinder iBinder, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void disable2ForUser(int i, android.os.IBinder iBinder, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int[] getDisableFlags(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIcon(java.lang.String str, java.lang.String str2, int i, int i2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setIconVisibility(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void removeIcon(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setImeWindowStatus(int i, android.os.IBinder iBinder, int i2, int i3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void expandSettingsPanel(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public com.android.internal.statusbar.RegisterStatusBarResult registerStatusBar(com.android.internal.statusbar.IStatusBar iStatusBar) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStatusBar);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.statusbar.RegisterStatusBarResult) obtain2.readTypedObject(com.android.internal.statusbar.RegisterStatusBarResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelRevealed(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onPanelHidden() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearNotificationEffects() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClick(java.lang.String str, com.android.internal.statusbar.NotificationVisibility notificationVisibility) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(notificationVisibility, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationActionClick(java.lang.String str, int i, android.app.Notification.Action action, com.android.internal.statusbar.NotificationVisibility notificationVisibility, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(action, 0);
                    obtain.writeTypedObject(notificationVisibility, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationError(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onClearAllNotifications(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationClear(java.lang.String str, int i, java.lang.String str2, int i2, int i3, com.android.internal.statusbar.NotificationVisibility notificationVisibility) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(notificationVisibility, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationVisibilityChanged(com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr, com.android.internal.statusbar.NotificationVisibility[] notificationVisibilityArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(notificationVisibilityArr, 0);
                    obtain.writeTypedArray(notificationVisibilityArr2, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationExpansionChanged(java.lang.String str, boolean z, boolean z2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationDirectReplied(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartSuggestionsAdded(java.lang.String str, int i, int i2, boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSmartReplySent(java.lang.String str, int i, java.lang.CharSequence charSequence, int i2, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationSettingsViewed(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationBubbleChanged(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBubbleMetadataFlagChanged(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideCurrentInputMethodForBubbles(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void grantInlineReplyUriPermission(java.lang.String str, android.net.Uri uri, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clearInlineReplyUriPermissions(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onNotificationFeedbackReceived(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsShown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onGlobalActionsHidden() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void shutdown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void reboot(boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void restart() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void addTile(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void remTile(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void clickTile(android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void handleSystemKey(android.view.KeyEvent keyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getLastSystemKey() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEnterExitToast(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showPinningEscapeToast() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showAuthenticationDialog(android.hardware.biometrics.PromptInfo promptInfo, android.hardware.biometrics.IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, java.lang.String str, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(promptInfo, 0);
                    obtain.writeStrongInterface(iBiometricSysuiReceiver);
                    obtain.writeIntArray(iArr);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeLong(j2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricAuthenticated(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricHelp(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onBiometricError(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void hideAuthenticationDialog(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setBiometicContextListener(android.hardware.biometrics.IBiometricContextListener iBiometricContextListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iBiometricContextListener);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setUdfpsRefreshRateCallback(android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iUdfpsRefreshRateRequestCallback);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showInattentiveSleepWarning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void dismissInattentiveSleepWarning(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void startTracing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void stopTracing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public boolean isTracing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void suppressAmbientDisplay(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestTileServiceListeningState(android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void requestAddTile(android.content.ComponentName componentName, java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, int i, com.android.internal.statusbar.IAddTileResultCallback iAddTileResultCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeTypedObject(icon, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAddTileResultCallback);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void cancelRequestAddTile(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void setNavBarMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public int getNavBarMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSessionListener);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterSessionListener(int i, com.android.internal.statusbar.ISessionListener iSessionListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSessionListener);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionStarted(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void onSessionEnded(int i, com.android.internal.logging.InstanceId instanceId) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(instanceId, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferSenderDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, com.android.internal.statusbar.IUndoMediaTransferCallback iUndoMediaTransferCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeStrongInterface(iUndoMediaTransferCallback);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(icon, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void registerNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void unregisterNearbyMediaDevicesProvider(android.media.INearbyMediaDevicesProvider iNearbyMediaDevicesProvider) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNearbyMediaDevicesProvider);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void showRearDisplayDialog(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IStatusBarService
            public void startAssist(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IStatusBarService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 73;
        }
    }
}
