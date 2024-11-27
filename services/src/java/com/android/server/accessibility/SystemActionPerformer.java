package com.android.server.accessibility;

/* loaded from: classes.dex */
public class SystemActionPerformer {
    private static final java.lang.String TAG = "SystemActionPerformer";
    private final android.content.Context mContext;
    private final com.android.server.accessibility.SystemActionPerformer.DisplayUpdateCallBack mDisplayUpdateCallBack;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyBackAction;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyHomeAction;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyLockScreenAction;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyNotificationsAction;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyPowerDialogAction;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyQuickSettingsAction;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyRecentsAction;
    private final android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction mLegacyTakeScreenshotAction;
    private final com.android.server.accessibility.SystemActionPerformer.SystemActionsChangedListener mListener;

    @com.android.internal.annotations.GuardedBy({"mSystemActionLock"})
    private final java.util.Map<java.lang.Integer, android.app.RemoteAction> mRegisteredSystemActions;
    private java.util.function.Supplier<com.android.internal.util.ScreenshotHelper> mScreenshotHelperSupplier;
    private final java.lang.Object mSystemActionLock;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerService;

    interface DisplayUpdateCallBack {
        int getLastNonProxyTopFocusedDisplayId();

        void moveNonProxyTopFocusedDisplayToTopIfNeeded();
    }

    interface SystemActionsChangedListener {
        void onSystemActionsChanged();
    }

    public SystemActionPerformer(android.content.Context context, com.android.server.wm.WindowManagerInternal windowManagerInternal) {
        this(context, windowManagerInternal, null, null, null);
    }

    @com.android.internal.annotations.VisibleForTesting
    public SystemActionPerformer(android.content.Context context, com.android.server.wm.WindowManagerInternal windowManagerInternal, java.util.function.Supplier<com.android.internal.util.ScreenshotHelper> supplier) {
        this(context, windowManagerInternal, supplier, null, null);
    }

    public SystemActionPerformer(android.content.Context context, com.android.server.wm.WindowManagerInternal windowManagerInternal, java.util.function.Supplier<com.android.internal.util.ScreenshotHelper> supplier, com.android.server.accessibility.SystemActionPerformer.SystemActionsChangedListener systemActionsChangedListener, com.android.server.accessibility.SystemActionPerformer.DisplayUpdateCallBack displayUpdateCallBack) {
        this.mSystemActionLock = new java.lang.Object();
        this.mRegisteredSystemActions = new android.util.ArrayMap();
        this.mContext = context;
        this.mWindowManagerService = windowManagerInternal;
        this.mListener = systemActionsChangedListener;
        this.mDisplayUpdateCallBack = displayUpdateCallBack;
        this.mScreenshotHelperSupplier = supplier;
        this.mLegacyHomeAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(2, this.mContext.getResources().getString(android.R.string.accessibility_system_action_dpad_down_label));
        this.mLegacyBackAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(1, this.mContext.getResources().getString(android.R.string.accessibility_shortcut_on));
        this.mLegacyRecentsAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(3, this.mContext.getResources().getString(android.R.string.accessibility_system_action_lock_screen_label));
        this.mLegacyNotificationsAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(4, this.mContext.getResources().getString(android.R.string.accessibility_system_action_dpad_right_label));
        this.mLegacyQuickSettingsAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(5, this.mContext.getResources().getString(android.R.string.accessibility_system_action_home_label));
        this.mLegacyPowerDialogAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(6, this.mContext.getResources().getString(android.R.string.accessibility_system_action_headset_hook_label));
        this.mLegacyLockScreenAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(8, this.mContext.getResources().getString(android.R.string.accessibility_system_action_dpad_left_label));
        this.mLegacyTakeScreenshotAction = new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(9, this.mContext.getResources().getString(android.R.string.accessibility_system_action_notifications_label));
    }

    @com.android.internal.annotations.VisibleForTesting
    public void registerSystemAction(int i, android.app.RemoteAction remoteAction) {
        synchronized (this.mSystemActionLock) {
            this.mRegisteredSystemActions.put(java.lang.Integer.valueOf(i), remoteAction);
        }
        if (this.mListener != null) {
            this.mListener.onSystemActionsChanged();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void unregisterSystemAction(int i) {
        synchronized (this.mSystemActionLock) {
            this.mRegisteredSystemActions.remove(java.lang.Integer.valueOf(i));
        }
        if (this.mListener != null) {
            this.mListener.onSystemActionsChanged();
        }
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> getSystemActions() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mSystemActionLock) {
            try {
                for (java.util.Map.Entry<java.lang.Integer, android.app.RemoteAction> entry : this.mRegisteredSystemActions.entrySet()) {
                    arrayList.add(new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(entry.getKey().intValue(), entry.getValue().getTitle()));
                }
                addLegacySystemActions(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    private void addLegacySystemActions(java.util.List<android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction> list) {
        if (!this.mRegisteredSystemActions.containsKey(1)) {
            list.add(this.mLegacyBackAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(2)) {
            list.add(this.mLegacyHomeAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(3)) {
            list.add(this.mLegacyRecentsAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(4)) {
            list.add(this.mLegacyNotificationsAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(5)) {
            list.add(this.mLegacyQuickSettingsAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(6)) {
            list.add(this.mLegacyPowerDialogAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(8)) {
            list.add(this.mLegacyLockScreenAction);
        }
        if (!this.mRegisteredSystemActions.containsKey(9)) {
            list.add(this.mLegacyTakeScreenshotAction);
        }
    }

    public boolean performSystemAction(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            synchronized (this.mSystemActionLock) {
                this.mDisplayUpdateCallBack.moveNonProxyTopFocusedDisplayToTopIfNeeded();
                android.app.RemoteAction remoteAction = this.mRegisteredSystemActions.get(java.lang.Integer.valueOf(i));
                if (remoteAction != null) {
                    try {
                        remoteAction.getActionIntent().send();
                        return true;
                    } catch (android.app.PendingIntent.CanceledException e) {
                        android.util.Slog.e(TAG, "canceled PendingIntent for global action " + ((java.lang.Object) remoteAction.getTitle()), e);
                        return false;
                    }
                }
                switch (i) {
                    case 1:
                        sendDownAndUpKeyEvents(4, 257);
                        return true;
                    case 2:
                        sendDownAndUpKeyEvents(3, 257);
                        return true;
                    case 3:
                        return openRecents();
                    case 4:
                        expandNotifications();
                        return true;
                    case 5:
                        expandQuickSettings();
                        return true;
                    case 6:
                        showGlobalActions();
                        return true;
                    case 7:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    default:
                        android.util.Slog.e(TAG, "Invalid action id: " + i);
                        return false;
                    case 8:
                        return lockScreen();
                    case 9:
                        return takeScreenshot();
                    case 10:
                        if (!com.android.internal.accessibility.util.AccessibilityUtils.interceptHeadsetHookForActiveCall(this.mContext)) {
                            sendDownAndUpKeyEvents(79, 257);
                        }
                        return true;
                    case 16:
                        sendDownAndUpKeyEvents(19, com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_SPEAKER);
                        return true;
                    case 17:
                        sendDownAndUpKeyEvents(20, com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_SPEAKER);
                        return true;
                    case 18:
                        sendDownAndUpKeyEvents(21, com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_SPEAKER);
                        return true;
                    case 19:
                        sendDownAndUpKeyEvents(22, com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_SPEAKER);
                        return true;
                    case 20:
                        sendDownAndUpKeyEvents(23, com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_OUT_SPEAKER);
                        return true;
                }
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void sendDownAndUpKeyEvents(int i, int i2) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            sendKeyEventIdentityCleared(i, 0, uptimeMillis, uptimeMillis, i2);
            sendKeyEventIdentityCleared(i, 1, uptimeMillis, android.os.SystemClock.uptimeMillis(), i2);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void sendKeyEventIdentityCleared(int i, int i2, long j, long j2, int i3) {
        android.view.KeyEvent obtain = android.view.KeyEvent.obtain(j, j2, i2, i, 0, 0, -1, 0, 8, i3, this.mDisplayUpdateCallBack.getLastNonProxyTopFocusedDisplayId(), null);
        ((android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class)).injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    private void expandNotifications() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            ((android.app.StatusBarManager) this.mContext.getSystemService("statusbar")).expandNotificationsPanel();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void expandQuickSettings() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            ((android.app.StatusBarManager) this.mContext.getSystemService("statusbar")).expandSettingsPanel();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private boolean openRecents() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.toggleRecentApps();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return true;
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void showGlobalActions() {
        this.mWindowManagerService.showGlobalActions();
    }

    private boolean lockScreen() {
        ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).goToSleep(android.os.SystemClock.uptimeMillis(), 7, 0);
        this.mWindowManagerService.lockNow();
        return true;
    }

    private boolean takeScreenshot() {
        (this.mScreenshotHelperSupplier != null ? this.mScreenshotHelperSupplier.get() : new com.android.internal.util.ScreenshotHelper(this.mContext)).takeScreenshot(4, new android.os.Handler(android.os.Looper.getMainLooper()), (java.util.function.Consumer) null);
        return true;
    }
}
