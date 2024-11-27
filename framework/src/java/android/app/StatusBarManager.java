package android.app;

/* loaded from: classes.dex */
public class StatusBarManager {
    public static final java.lang.String ACTION_KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED = "android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED";
    public static final java.util.Set<java.lang.Integer> ALL_SESSIONS = java.util.Set.of(1, 2);
    public static final int CAMERA_LAUNCH_SOURCE_LIFT_TRIGGER = 2;
    public static final int CAMERA_LAUNCH_SOURCE_POWER_DOUBLE_TAP = 1;
    public static final int CAMERA_LAUNCH_SOURCE_QUICK_AFFORDANCE = 3;
    public static final int CAMERA_LAUNCH_SOURCE_SCREEN_GESTURE = 4;
    public static final int CAMERA_LAUNCH_SOURCE_WIGGLE = 0;
    public static final int DEFAULT_SETUP_DISABLE2_FLAGS = 0;
    public static final int DEFAULT_SETUP_DISABLE_FLAGS = 61145088;
    private static final int DEFAULT_SIM_LOCKED_DISABLED_FLAGS = 65536;
    public static final int DISABLE2_GLOBAL_ACTIONS = 8;
    public static final int DISABLE2_MASK = 31;
    public static final int DISABLE2_NONE = 0;
    public static final int DISABLE2_NOTIFICATION_SHADE = 4;
    public static final int DISABLE2_QUICK_SETTINGS = 1;
    public static final int DISABLE2_ROTATE_SUGGESTIONS = 16;
    public static final int DISABLE2_SYSTEM_ICONS = 2;
    public static final int DISABLE_BACK = 4194304;
    public static final int DISABLE_CLOCK = 8388608;
    public static final int DISABLE_EXPAND = 65536;
    public static final int DISABLE_HOME = 2097152;
    public static final int DISABLE_MASK = 134152192;

    @java.lang.Deprecated
    public static final int DISABLE_NAVIGATION = 18874368;
    public static final int DISABLE_NONE = 0;
    public static final int DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int DISABLE_NOTIFICATION_ICONS = 131072;

    @java.lang.Deprecated
    public static final int DISABLE_NOTIFICATION_TICKER = 524288;
    public static final int DISABLE_ONGOING_CALL_CHIP = 67108864;
    public static final int DISABLE_RECENT = 16777216;
    public static final int DISABLE_SEARCH = 33554432;
    public static final int DISABLE_SYSTEM_INFO = 1048576;
    public static final java.lang.String EXTRA_KM_PRIVATE_NOTIFS_ALLOWED = "android.app.extra.KM_PRIVATE_NOTIFS_ALLOWED";
    private static final long MEDIA_CONTROL_BLANK_TITLE = 274775190;
    private static final long MEDIA_CONTROL_SESSION_ACTIONS = 203800354;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_CLOSE_TO_SENDER = 0;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_FAR_FROM_SENDER = 1;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_TRANSFER_TO_RECEIVER_FAILED = 3;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_TRANSFER_TO_RECEIVER_SUCCEEDED = 2;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_ALMOST_CLOSE_TO_END_CAST = 1;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_ALMOST_CLOSE_TO_START_CAST = 0;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_FAR_FROM_RECEIVER = 8;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_RECEIVER_FAILED = 6;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_RECEIVER_SUCCEEDED = 4;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_RECEIVER_TRIGGERED = 2;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_THIS_DEVICE_FAILED = 7;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_THIS_DEVICE_SUCCEEDED = 5;

    @android.annotation.SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_THIS_DEVICE_TRIGGERED = 3;
    public static final int NAVIGATION_HINT_BACK_ALT = 1;
    public static final int NAVIGATION_HINT_IME_SHOWN = 2;
    public static final int NAVIGATION_HINT_IME_SWITCHER_SHOWN = 4;

    @android.annotation.SystemApi
    public static final int NAV_BAR_MODE_DEFAULT = 0;

    @android.annotation.SystemApi
    public static final int NAV_BAR_MODE_KIDS = 1;
    public static final int SESSION_BIOMETRIC_PROMPT = 2;
    public static final int SESSION_KEYGUARD = 1;
    private static final java.lang.String TAG = "StatusBarManager";
    public static final int TILE_ADD_REQUEST_ERROR_APP_NOT_IN_FOREGROUND = 1004;
    public static final int TILE_ADD_REQUEST_ERROR_BAD_COMPONENT = 1002;
    public static final int TILE_ADD_REQUEST_ERROR_MISMATCHED_PACKAGE = 1000;
    public static final int TILE_ADD_REQUEST_ERROR_NOT_CURRENT_USER = 1003;
    public static final int TILE_ADD_REQUEST_ERROR_NO_STATUS_BAR_SERVICE = 1005;
    public static final int TILE_ADD_REQUEST_ERROR_REQUEST_IN_PROGRESS = 1001;
    private static final int TILE_ADD_REQUEST_FIRST_ERROR_CODE = 1000;
    public static final int TILE_ADD_REQUEST_RESULT_DIALOG_DISMISSED = 3;
    public static final int TILE_ADD_REQUEST_RESULT_TILE_ADDED = 2;
    public static final int TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED = 1;
    public static final int TILE_ADD_REQUEST_RESULT_TILE_NOT_ADDED = 0;
    public static final int WINDOW_NAVIGATION_BAR = 2;
    public static final int WINDOW_STATE_HIDDEN = 2;
    public static final int WINDOW_STATE_HIDING = 1;
    public static final int WINDOW_STATE_SHOWING = 0;
    public static final int WINDOW_STATUS_BAR = 1;
    private android.content.Context mContext;
    private com.android.internal.statusbar.IStatusBarService mService;
    private final java.util.Map<android.media.NearbyMediaDevicesProvider, android.app.StatusBarManager.NearbyMediaDevicesProviderWrapper> nearbyMediaDevicesProviderMap = new java.util.HashMap();
    private android.os.IBinder mToken = new android.os.Binder();
    private final com.android.internal.compat.IPlatformCompat mPlatformCompat = com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.PLATFORM_COMPAT_SERVICE));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Disable2Flags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DisableFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediaTransferReceiverState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediaTransferSenderState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NavBarMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WindowType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface WindowVisibleState {
    }

    StatusBarManager(android.content.Context context) {
        this.mContext = context;
    }

    private synchronized com.android.internal.statusbar.IStatusBarService getService() {
        if (this.mService == null) {
            this.mService = com.android.internal.statusbar.IStatusBarService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.STATUS_BAR_SERVICE));
            if (this.mService == null) {
                android.util.Slog.w(TAG, "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mService;
    }

    public void disable(int i) {
        try {
            int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.disableForUser(i, this.mToken, this.mContext.getPackageName(), identifier);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disable2(int i) {
        try {
            int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.disable2ForUser(i, this.mToken, this.mContext.getPackageName(), identifier);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clickNotification(java.lang.String str, int i, int i2, boolean z) {
        clickNotificationInternal(str, i, i2, z);
    }

    private void clickNotificationInternal(java.lang.String str, int i, int i2, boolean z) {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.onNotificationClick(str, com.android.internal.statusbar.NotificationVisibility.obtain(str, i, i2, z));
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendNotificationFeedback(java.lang.String str, android.os.Bundle bundle) {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.onNotificationFeedbackReceived(str, bundle);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void expandNotificationsPanel() {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.expandNotificationsPanel();
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void collapsePanels() {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.collapsePanels();
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void togglePanel() {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.togglePanel();
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void handleSystemKey(android.view.KeyEvent keyEvent) {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.handleSystemKey(keyEvent);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLastSystemKey() {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                return service.getLastSystemKey();
            }
            return -1;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void expandSettingsPanel() {
        expandSettingsPanel(null);
    }

    public void expandSettingsPanel(java.lang.String str) {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.expandSettingsPanel(str);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIcon(java.lang.String str, int i, int i2, java.lang.String str2) {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.setIcon(str, this.mContext.getPackageName(), i, i2, str2);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeIcon(java.lang.String str) {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.removeIcon(str);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIconVisibility(java.lang.String str, boolean z) {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.setIconVisibility(str, z);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setDisabledForSetup(boolean z) {
        try {
            int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.disableForUser(z ? DEFAULT_SETUP_DISABLE_FLAGS : 0, this.mToken, this.mContext.getPackageName(), identifier);
                if (z) {
                }
                service.disable2ForUser(0, this.mToken, this.mContext.getPackageName(), identifier);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setExpansionDisabledForSimNetworkLock(boolean z) {
        try {
            int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.disableForUser(z ? 65536 : 0, this.mToken, this.mContext.getPackageName(), identifier);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.app.StatusBarManager.DisableInfo getDisableInfo() {
        try {
            int identifier = android.os.Binder.getCallingUserHandle().getIdentifier();
            com.android.internal.statusbar.IStatusBarService service = getService();
            int[] iArr = {0, 0};
            if (service != null) {
                iArr = service.getDisableFlags(this.mToken, identifier);
            }
            return new android.app.StatusBarManager.DisableInfo(iArr[0], iArr[1]);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestTileServiceListeningState(android.content.ComponentName componentName) {
        java.util.Objects.requireNonNull(componentName);
        try {
            getService().requestTileServiceListeningState(componentName, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestAddTileService(android.content.ComponentName componentName, java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(componentName);
        java.util.Objects.requireNonNull(charSequence);
        java.util.Objects.requireNonNull(icon);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        if (!componentName.getPackageName().equals(this.mContext.getPackageName())) {
            executor.execute(new java.lang.Runnable() { // from class: android.app.StatusBarManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(1000);
                }
            });
            return;
        }
        try {
            getService().requestAddTile(componentName, charSequence, icon, this.mContext.getUserId(), new android.app.StatusBarManager.RequestResultCallback(executor, consumer));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void cancelRequestAddTile(java.lang.String str) {
        java.util.Objects.requireNonNull(str);
        try {
            getService().cancelRequestAddTile(str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setNavBarMode(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Supplied navBarMode not supported: " + i);
        }
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service != null) {
                service.setNavBarMode(i);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getNavBarMode() {
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            if (service == null) {
                return 0;
            }
            return service.getNavBarMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void updateMediaTapToTransferSenderDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
        android.app.StatusBarManager.UndoCallback undoCallback;
        java.util.Objects.requireNonNull(mediaRoute2Info);
        if (i != 4 && i != 5 && runnable != null) {
            throw new java.lang.IllegalArgumentException("The undoCallback should only be provided when the state is a transfer succeeded state");
        }
        if (runnable != null && executor == null) {
            throw new java.lang.IllegalArgumentException("You must pass an executor when you pass an undo callback");
        }
        com.android.internal.statusbar.IStatusBarService service = getService();
        if (executor == null) {
            undoCallback = null;
        } else {
            try {
                undoCallback = new android.app.StatusBarManager.UndoCallback(executor, runnable);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        service.updateMediaTapToTransferSenderDisplay(i, mediaRoute2Info, undoCallback);
    }

    @android.annotation.SystemApi
    public void updateMediaTapToTransferReceiverDisplay(int i, android.media.MediaRoute2Info mediaRoute2Info, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) {
        java.util.Objects.requireNonNull(mediaRoute2Info);
        try {
            getService().updateMediaTapToTransferReceiverDisplay(i, mediaRoute2Info, icon, charSequence);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void registerNearbyMediaDevicesProvider(android.media.NearbyMediaDevicesProvider nearbyMediaDevicesProvider) {
        java.util.Objects.requireNonNull(nearbyMediaDevicesProvider);
        if (this.nearbyMediaDevicesProviderMap.containsKey(nearbyMediaDevicesProvider)) {
            return;
        }
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            android.app.StatusBarManager.NearbyMediaDevicesProviderWrapper nearbyMediaDevicesProviderWrapper = new android.app.StatusBarManager.NearbyMediaDevicesProviderWrapper(nearbyMediaDevicesProvider);
            this.nearbyMediaDevicesProviderMap.put(nearbyMediaDevicesProvider, nearbyMediaDevicesProviderWrapper);
            service.registerNearbyMediaDevicesProvider(nearbyMediaDevicesProviderWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void unregisterNearbyMediaDevicesProvider(android.media.NearbyMediaDevicesProvider nearbyMediaDevicesProvider) {
        java.util.Objects.requireNonNull(nearbyMediaDevicesProvider);
        if (!this.nearbyMediaDevicesProviderMap.containsKey(nearbyMediaDevicesProvider)) {
            return;
        }
        try {
            com.android.internal.statusbar.IStatusBarService service = getService();
            android.app.StatusBarManager.NearbyMediaDevicesProviderWrapper nearbyMediaDevicesProviderWrapper = this.nearbyMediaDevicesProviderMap.get(nearbyMediaDevicesProvider);
            this.nearbyMediaDevicesProviderMap.remove(nearbyMediaDevicesProvider);
            service.unregisterNearbyMediaDevicesProvider(nearbyMediaDevicesProviderWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean useMediaSessionActionsForApp(java.lang.String str, android.os.UserHandle userHandle) {
        return android.app.compat.CompatChanges.isChangeEnabled(MEDIA_CONTROL_SESSION_ACTIONS, str, userHandle);
    }

    public void logBlankMediaTitle(java.lang.String str, int i) throws java.lang.RuntimeException {
        try {
            this.mPlatformCompat.reportChangeByPackageName(MEDIA_CONTROL_BLANK_TITLE, str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean canLaunchCaptureContentActivityForNote(android.app.Activity activity) {
        java.util.Objects.requireNonNull(activity);
        return new com.android.internal.statusbar.AppClipsServiceConnector(this.mContext).canLaunchCaptureContentActivityForNote(android.app.ActivityClient.getInstance().getTaskForActivity(activity.getActivityToken(), false));
    }

    public static java.lang.String windowStateToString(int i) {
        return i == 1 ? "WINDOW_STATE_HIDING" : i == 2 ? "WINDOW_STATE_HIDDEN" : i == 0 ? "WINDOW_STATE_SHOWING" : "WINDOW_STATE_UNKNOWN";
    }

    @android.annotation.SystemApi
    public static final class DisableInfo {
        private boolean mClock;
        private boolean mNavigateHome;
        private boolean mNotificationIcons;
        private boolean mNotificationPeeking;
        private boolean mRecents;
        private boolean mRotationSuggestion;
        private boolean mSearch;
        private boolean mStatusBarExpansion;
        private boolean mSystemIcons;

        public DisableInfo(int i, int i2) {
            this.mStatusBarExpansion = (65536 & i) != 0;
            this.mNavigateHome = (2097152 & i) != 0;
            this.mNotificationPeeking = (262144 & i) != 0;
            this.mRecents = (16777216 & i) != 0;
            this.mSearch = (33554432 & i) != 0;
            this.mSystemIcons = (1048576 & i) != 0;
            this.mClock = (8388608 & i) != 0;
            this.mNotificationIcons = (i & 131072) != 0;
            this.mRotationSuggestion = (i2 & 16) != 0;
        }

        public DisableInfo() {
        }

        @android.annotation.SystemApi
        public boolean isStatusBarExpansionDisabled() {
            return this.mStatusBarExpansion;
        }

        public void setStatusBarExpansionDisabled(boolean z) {
            this.mStatusBarExpansion = z;
        }

        @android.annotation.SystemApi
        public boolean isNavigateToHomeDisabled() {
            return this.mNavigateHome;
        }

        public void setNagivationHomeDisabled(boolean z) {
            this.mNavigateHome = z;
        }

        @android.annotation.SystemApi
        public boolean isNotificationPeekingDisabled() {
            return this.mNotificationPeeking;
        }

        public void setNotificationPeekingDisabled(boolean z) {
            this.mNotificationPeeking = z;
        }

        @android.annotation.SystemApi
        public boolean isRecentsDisabled() {
            return this.mRecents;
        }

        public void setRecentsDisabled(boolean z) {
            this.mRecents = z;
        }

        @android.annotation.SystemApi
        public boolean isSearchDisabled() {
            return this.mSearch;
        }

        public void setSearchDisabled(boolean z) {
            this.mSearch = z;
        }

        public boolean areSystemIconsDisabled() {
            return this.mSystemIcons;
        }

        public void setSystemIconsDisabled(boolean z) {
            this.mSystemIcons = z;
        }

        public boolean isClockDisabled() {
            return this.mClock;
        }

        public void setClockDisabled(boolean z) {
            this.mClock = z;
        }

        public boolean areNotificationIconsDisabled() {
            return this.mNotificationIcons;
        }

        public void setNotificationIconsDisabled(boolean z) {
            this.mNotificationIcons = z;
        }

        public boolean isRotationSuggestionDisabled() {
            return this.mRotationSuggestion;
        }

        @android.annotation.SystemApi
        public boolean areAllComponentsEnabled() {
            return (this.mStatusBarExpansion || this.mNavigateHome || this.mNotificationPeeking || this.mRecents || this.mSearch || this.mSystemIcons || this.mClock || this.mNotificationIcons || this.mRotationSuggestion) ? false : true;
        }

        public void setEnableAll() {
            this.mStatusBarExpansion = false;
            this.mNavigateHome = false;
            this.mNotificationPeeking = false;
            this.mRecents = false;
            this.mSearch = false;
            this.mSystemIcons = false;
            this.mClock = false;
            this.mNotificationIcons = false;
            this.mRotationSuggestion = false;
        }

        public boolean areAllComponentsDisabled() {
            return this.mStatusBarExpansion && this.mNavigateHome && this.mNotificationPeeking && this.mRecents && this.mSearch && this.mSystemIcons && this.mClock && this.mNotificationIcons && this.mRotationSuggestion;
        }

        public void setDisableAll() {
            this.mStatusBarExpansion = true;
            this.mNavigateHome = true;
            this.mNotificationPeeking = true;
            this.mRecents = true;
            this.mSearch = true;
            this.mSystemIcons = true;
            this.mClock = true;
            this.mNotificationIcons = true;
            this.mRotationSuggestion = true;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("DisableInfo: ");
            sb.append(" mStatusBarExpansion=").append(this.mStatusBarExpansion ? "disabled" : "enabled");
            sb.append(" mNavigateHome=").append(this.mNavigateHome ? "disabled" : "enabled");
            sb.append(" mNotificationPeeking=").append(this.mNotificationPeeking ? "disabled" : "enabled");
            sb.append(" mRecents=").append(this.mRecents ? "disabled" : "enabled");
            sb.append(" mSearch=").append(this.mSearch ? "disabled" : "enabled");
            sb.append(" mSystemIcons=").append(this.mSystemIcons ? "disabled" : "enabled");
            sb.append(" mClock=").append(this.mClock ? "disabled" : "enabled");
            sb.append(" mNotificationIcons=").append(this.mNotificationIcons ? "disabled" : "enabled");
            sb.append(" mRotationSuggestion=").append(this.mRotationSuggestion ? "disabled" : "enabled");
            return sb.toString();
        }

        public android.util.Pair<java.lang.Integer, java.lang.Integer> toFlags() {
            int i = this.mStatusBarExpansion ? 65536 : 0;
            if (this.mNavigateHome) {
                i |= 2097152;
            }
            if (this.mNotificationPeeking) {
                i |= 262144;
            }
            if (this.mRecents) {
                i |= 16777216;
            }
            if (this.mSearch) {
                i |= 33554432;
            }
            if (this.mSystemIcons) {
                i |= 1048576;
            }
            if (this.mClock) {
                i |= 8388608;
            }
            if (this.mNotificationIcons) {
                i |= 131072;
            }
            return new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.mRotationSuggestion ? 16 : 0));
        }
    }

    static final class RequestResultCallback extends com.android.internal.statusbar.IAddTileResultCallback.Stub {
        private final java.util.function.Consumer<java.lang.Integer> mCallback;
        private final java.util.concurrent.Executor mExecutor;

        RequestResultCallback(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
            this.mExecutor = executor;
            this.mCallback = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTileRequest$0(int i) {
            this.mCallback.accept(java.lang.Integer.valueOf(i));
        }

        @Override // com.android.internal.statusbar.IAddTileResultCallback
        public void onTileRequest(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.StatusBarManager$RequestResultCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.StatusBarManager.RequestResultCallback.this.lambda$onTileRequest$0(i);
                }
            });
        }
    }

    static final class UndoCallback extends com.android.internal.statusbar.IUndoMediaTransferCallback.Stub {
        private final java.lang.Runnable mCallback;
        private final java.util.concurrent.Executor mExecutor;

        UndoCallback(java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
            this.mExecutor = executor;
            this.mCallback = runnable;
        }

        @Override // com.android.internal.statusbar.IUndoMediaTransferCallback
        public void onUndoTriggered() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(this.mCallback);
            } finally {
                restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    static final class NearbyMediaDevicesProviderWrapper extends android.media.INearbyMediaDevicesProvider.Stub {
        private final android.media.NearbyMediaDevicesProvider mProvider;
        private final java.util.Map<android.media.INearbyMediaDevicesUpdateCallback, java.util.function.Consumer<java.util.List<android.media.NearbyDevice>>> mRegisteredCallbacks = new java.util.HashMap();

        NearbyMediaDevicesProviderWrapper(android.media.NearbyMediaDevicesProvider nearbyMediaDevicesProvider) {
            this.mProvider = nearbyMediaDevicesProvider;
        }

        @Override // android.media.INearbyMediaDevicesProvider
        public void registerNearbyDevicesCallback(final android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) {
            java.util.function.Consumer<java.util.List<android.media.NearbyDevice>> consumer = new java.util.function.Consumer() { // from class: android.app.StatusBarManager$NearbyMediaDevicesProviderWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.app.StatusBarManager.NearbyMediaDevicesProviderWrapper.lambda$registerNearbyDevicesCallback$0(android.media.INearbyMediaDevicesUpdateCallback.this, (java.util.List) obj);
                }
            };
            this.mRegisteredCallbacks.put(iNearbyMediaDevicesUpdateCallback, consumer);
            this.mProvider.registerNearbyDevicesCallback(consumer);
        }

        static /* synthetic */ void lambda$registerNearbyDevicesCallback$0(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback, java.util.List list) {
            try {
                iNearbyMediaDevicesUpdateCallback.onDevicesUpdated(list);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.INearbyMediaDevicesProvider
        public void unregisterNearbyDevicesCallback(android.media.INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) {
            if (!this.mRegisteredCallbacks.containsKey(iNearbyMediaDevicesUpdateCallback)) {
                return;
            }
            this.mProvider.unregisterNearbyDevicesCallback(this.mRegisteredCallbacks.get(iNearbyMediaDevicesUpdateCallback));
            this.mRegisteredCallbacks.remove(iNearbyMediaDevicesUpdateCallback);
        }
    }
}
