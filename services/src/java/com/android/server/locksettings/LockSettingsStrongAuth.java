package com.android.server.locksettings;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class LockSettingsStrongAuth {
    private static final boolean DEBUG;
    public static final long DEFAULT_NON_STRONG_BIOMETRIC_IDLE_TIMEOUT_MS = 14400000;
    public static final long DEFAULT_NON_STRONG_BIOMETRIC_TIMEOUT_MS = 86400000;
    private static final int MSG_NO_LONGER_REQUIRE_STRONG_AUTH = 6;
    private static final int MSG_REFRESH_STRONG_AUTH_TIMEOUT = 10;
    private static final int MSG_REGISTER_TRACKER = 2;
    private static final int MSG_REMOVE_USER = 4;
    private static final int MSG_REQUIRE_STRONG_AUTH = 1;
    private static final int MSG_SCHEDULE_NON_STRONG_BIOMETRIC_IDLE_TIMEOUT = 9;
    private static final int MSG_SCHEDULE_NON_STRONG_BIOMETRIC_TIMEOUT = 7;
    private static final int MSG_SCHEDULE_STRONG_AUTH_TIMEOUT = 5;
    private static final int MSG_STRONG_BIOMETRIC_UNLOCK = 8;
    private static final int MSG_UNREGISTER_TRACKER = 3;

    @com.android.internal.annotations.VisibleForTesting
    protected static final java.lang.String NON_STRONG_BIOMETRIC_IDLE_TIMEOUT_ALARM_TAG = "LockSettingsPrimaryAuth.nonStrongBiometricIdleTimeoutForUser";

    @com.android.internal.annotations.VisibleForTesting
    protected static final java.lang.String NON_STRONG_BIOMETRIC_TIMEOUT_ALARM_TAG = "LockSettingsPrimaryAuth.nonStrongBiometricTimeoutForUser";

    @com.android.internal.annotations.VisibleForTesting
    protected static final java.lang.String STRONG_AUTH_TIMEOUT_ALARM_TAG = "LockSettingsStrongAuth.timeoutForUser";
    private static final java.lang.String TAG = "LockSettingsStrongAuth";
    private final android.app.AlarmManager mAlarmManager;
    private final android.content.Context mContext;
    private final boolean mDefaultIsNonStrongBiometricAllowed;
    private final int mDefaultStrongAuthFlags;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.os.Handler mHandler;
    private final com.android.server.locksettings.LockSettingsStrongAuth.Injector mInjector;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.SparseBooleanArray mIsNonStrongBiometricAllowedForUser;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.ArrayMap<java.lang.Integer, com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricIdleTimeoutAlarmListener> mNonStrongBiometricIdleTimeoutAlarmListener;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.ArrayMap<java.lang.Integer, com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricTimeoutAlarmListener> mNonStrongBiometricTimeoutAlarmListener;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.SparseIntArray mStrongAuthForUser;

    @com.android.internal.annotations.VisibleForTesting
    protected final android.util.ArrayMap<java.lang.Integer, com.android.server.locksettings.LockSettingsStrongAuth.StrongAuthTimeoutAlarmListener> mStrongAuthTimeoutAlarmListenerForUser;
    private final android.os.RemoteCallbackList<android.app.trust.IStrongAuthTracker> mTrackers;

    static {
        DEBUG = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 3);
    }

    public LockSettingsStrongAuth(android.content.Context context) {
        this(context, new com.android.server.locksettings.LockSettingsStrongAuth.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    protected LockSettingsStrongAuth(android.content.Context context, com.android.server.locksettings.LockSettingsStrongAuth.Injector injector) {
        this.mTrackers = new android.os.RemoteCallbackList<>();
        this.mStrongAuthForUser = new android.util.SparseIntArray();
        this.mIsNonStrongBiometricAllowedForUser = new android.util.SparseBooleanArray();
        this.mStrongAuthTimeoutAlarmListenerForUser = new android.util.ArrayMap<>();
        this.mNonStrongBiometricTimeoutAlarmListener = new android.util.ArrayMap<>();
        this.mNonStrongBiometricIdleTimeoutAlarmListener = new android.util.ArrayMap<>();
        this.mDefaultIsNonStrongBiometricAllowed = true;
        this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper()) { // from class: com.android.server.locksettings.LockSettingsStrongAuth.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleRequireStrongAuth(message.arg1, message.arg2);
                        break;
                    case 2:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleAddStrongAuthTracker((android.app.trust.IStrongAuthTracker) message.obj);
                        break;
                    case 3:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleRemoveStrongAuthTracker((android.app.trust.IStrongAuthTracker) message.obj);
                        break;
                    case 4:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleRemoveUser(message.arg1);
                        break;
                    case 5:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleScheduleStrongAuthTimeout(message.arg1);
                        break;
                    case 6:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleNoLongerRequireStrongAuth(message.arg1, message.arg2);
                        break;
                    case 7:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleScheduleNonStrongBiometricTimeout(message.arg1);
                        break;
                    case 8:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleStrongBiometricUnlock(message.arg1);
                        break;
                    case 9:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleScheduleNonStrongBiometricIdleTimeout(message.arg1);
                        break;
                    case 10:
                        com.android.server.locksettings.LockSettingsStrongAuth.this.handleRefreshStrongAuthTimeout(message.arg1);
                        break;
                }
            }
        };
        this.mContext = context;
        this.mInjector = injector;
        this.mDefaultStrongAuthFlags = this.mInjector.getDefaultStrongAuthFlags(context);
        this.mAlarmManager = this.mInjector.getAlarmManager(context);
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class Injector {
        @com.android.internal.annotations.VisibleForTesting
        public android.app.AlarmManager getAlarmManager(android.content.Context context) {
            return (android.app.AlarmManager) context.getSystemService(android.app.AlarmManager.class);
        }

        @com.android.internal.annotations.VisibleForTesting
        public int getDefaultStrongAuthFlags(android.content.Context context) {
            return com.android.internal.widget.LockPatternUtils.StrongAuthTracker.getDefaultFlags(context);
        }

        @com.android.internal.annotations.VisibleForTesting
        public long getNextAlarmTimeMs(long j) {
            return android.os.SystemClock.elapsedRealtime() + j;
        }

        @com.android.internal.annotations.VisibleForTesting
        public long getElapsedRealtimeMs() {
            return android.os.SystemClock.elapsedRealtime();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAddStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) {
        this.mTrackers.register(iStrongAuthTracker);
        for (int i = 0; i < this.mStrongAuthForUser.size(); i++) {
            try {
                iStrongAuthTracker.onStrongAuthRequiredChanged(this.mStrongAuthForUser.valueAt(i), this.mStrongAuthForUser.keyAt(i));
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Exception while adding StrongAuthTracker.", e);
            }
        }
        for (int i2 = 0; i2 < this.mIsNonStrongBiometricAllowedForUser.size(); i2++) {
            try {
                iStrongAuthTracker.onIsNonStrongBiometricAllowedChanged(this.mIsNonStrongBiometricAllowedForUser.valueAt(i2), this.mIsNonStrongBiometricAllowedForUser.keyAt(i2));
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Exception while adding StrongAuthTracker: IsNonStrongBiometricAllowedChanged.", e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) {
        this.mTrackers.unregister(iStrongAuthTracker);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequireStrongAuth(int i, int i2) {
        if (i2 == -1) {
            for (int i3 = 0; i3 < this.mStrongAuthForUser.size(); i3++) {
                handleRequireStrongAuthOneUser(i, this.mStrongAuthForUser.keyAt(i3));
            }
            return;
        }
        handleRequireStrongAuthOneUser(i, i2);
    }

    private void handleRequireStrongAuthOneUser(int i, int i2) {
        int i3;
        int i4 = this.mStrongAuthForUser.get(i2, this.mDefaultStrongAuthFlags);
        if (i == 0) {
            i3 = 0;
        } else {
            i3 = i | i4;
        }
        if (i4 != i3) {
            this.mStrongAuthForUser.put(i2, i3);
            notifyStrongAuthTrackers(i3, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleNoLongerRequireStrongAuth(int i, int i2) {
        if (i2 == -1) {
            for (int i3 = 0; i3 < this.mStrongAuthForUser.size(); i3++) {
                handleNoLongerRequireStrongAuthOneUser(i, this.mStrongAuthForUser.keyAt(i3));
            }
            return;
        }
        handleNoLongerRequireStrongAuthOneUser(i, i2);
    }

    private void handleNoLongerRequireStrongAuthOneUser(int i, int i2) {
        int i3 = this.mStrongAuthForUser.get(i2, this.mDefaultStrongAuthFlags);
        int i4 = (~i) & i3;
        if (i3 != i4) {
            this.mStrongAuthForUser.put(i2, i4);
            notifyStrongAuthTrackers(i4, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRemoveUser(int i) {
        int indexOfKey = this.mStrongAuthForUser.indexOfKey(i);
        if (indexOfKey >= 0) {
            this.mStrongAuthForUser.removeAt(indexOfKey);
            notifyStrongAuthTrackers(this.mDefaultStrongAuthFlags, i);
        }
        int indexOfKey2 = this.mIsNonStrongBiometricAllowedForUser.indexOfKey(i);
        if (indexOfKey2 >= 0) {
            this.mIsNonStrongBiometricAllowedForUser.removeAt(indexOfKey2);
            notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(true, i);
        }
    }

    private void rescheduleStrongAuthTimeoutAlarm(long j, int i) {
        com.android.server.locksettings.LockSettingsStrongAuth.StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener;
        android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) this.mContext.getSystemService("device_policy");
        com.android.server.locksettings.LockSettingsStrongAuth.StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener2 = this.mStrongAuthTimeoutAlarmListenerForUser.get(java.lang.Integer.valueOf(i));
        if (strongAuthTimeoutAlarmListener2 != null) {
            this.mAlarmManager.cancel(strongAuthTimeoutAlarmListener2);
            strongAuthTimeoutAlarmListener2.setLatestStrongAuthTime(j);
            strongAuthTimeoutAlarmListener = strongAuthTimeoutAlarmListener2;
        } else {
            com.android.server.locksettings.LockSettingsStrongAuth.StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener3 = new com.android.server.locksettings.LockSettingsStrongAuth.StrongAuthTimeoutAlarmListener(j, i);
            this.mStrongAuthTimeoutAlarmListenerForUser.put(java.lang.Integer.valueOf(i), strongAuthTimeoutAlarmListener3);
            strongAuthTimeoutAlarmListener = strongAuthTimeoutAlarmListener3;
        }
        this.mAlarmManager.setExact(2, j + devicePolicyManager.getRequiredStrongAuthTimeout(null, i), STRONG_AUTH_TIMEOUT_ALARM_TAG, strongAuthTimeoutAlarmListener, this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScheduleStrongAuthTimeout(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleScheduleStrongAuthTimeout for userId=" + i);
        }
        rescheduleStrongAuthTimeoutAlarm(this.mInjector.getElapsedRealtimeMs(), i);
        cancelNonStrongBiometricAlarmListener(i);
        cancelNonStrongBiometricIdleAlarmListener(i);
        setIsNonStrongBiometricAllowed(true, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRefreshStrongAuthTimeout(int i) {
        com.android.server.locksettings.LockSettingsStrongAuth.StrongAuthTimeoutAlarmListener strongAuthTimeoutAlarmListener = this.mStrongAuthTimeoutAlarmListenerForUser.get(java.lang.Integer.valueOf(i));
        if (strongAuthTimeoutAlarmListener != null) {
            rescheduleStrongAuthTimeoutAlarm(strongAuthTimeoutAlarmListener.getLatestStrongAuthTime(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScheduleNonStrongBiometricTimeout(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleScheduleNonStrongBiometricTimeout for userId=" + i);
        }
        long nextAlarmTimeMs = this.mInjector.getNextAlarmTimeMs(86400000L);
        if (this.mNonStrongBiometricTimeoutAlarmListener.get(java.lang.Integer.valueOf(i)) != null) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "There is an existing alarm for non-strong biometric fallback timeout, so do not re-schedule");
            }
        } else {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Schedule a new alarm for non-strong biometric fallback timeout");
            }
            com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricTimeoutAlarmListener nonStrongBiometricTimeoutAlarmListener = new com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricTimeoutAlarmListener(i);
            this.mNonStrongBiometricTimeoutAlarmListener.put(java.lang.Integer.valueOf(i), nonStrongBiometricTimeoutAlarmListener);
            this.mAlarmManager.setExact(2, nextAlarmTimeMs, NON_STRONG_BIOMETRIC_TIMEOUT_ALARM_TAG, nonStrongBiometricTimeoutAlarmListener, this.mHandler);
        }
        cancelNonStrongBiometricIdleAlarmListener(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStrongBiometricUnlock(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleStrongBiometricUnlock for userId=" + i);
        }
        cancelNonStrongBiometricAlarmListener(i);
        cancelNonStrongBiometricIdleAlarmListener(i);
        setIsNonStrongBiometricAllowed(true, i);
    }

    private void cancelNonStrongBiometricAlarmListener(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "cancelNonStrongBiometricAlarmListener for userId=" + i);
        }
        com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricTimeoutAlarmListener nonStrongBiometricTimeoutAlarmListener = this.mNonStrongBiometricTimeoutAlarmListener.get(java.lang.Integer.valueOf(i));
        if (nonStrongBiometricTimeoutAlarmListener != null) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Cancel alarm for non-strong biometric fallback timeout");
            }
            this.mAlarmManager.cancel(nonStrongBiometricTimeoutAlarmListener);
            this.mNonStrongBiometricTimeoutAlarmListener.remove(java.lang.Integer.valueOf(i));
        }
    }

    private void cancelNonStrongBiometricIdleAlarmListener(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "cancelNonStrongBiometricIdleAlarmListener for userId=" + i);
        }
        com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener = this.mNonStrongBiometricIdleTimeoutAlarmListener.get(java.lang.Integer.valueOf(i));
        if (nonStrongBiometricIdleTimeoutAlarmListener != null) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Cancel alarm for non-strong biometric idle timeout");
            }
            this.mAlarmManager.cancel(nonStrongBiometricIdleTimeoutAlarmListener);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void setIsNonStrongBiometricAllowed(boolean z, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "setIsNonStrongBiometricAllowed for allowed=" + z + ", userId=" + i);
        }
        if (i == -1) {
            for (int i2 = 0; i2 < this.mIsNonStrongBiometricAllowedForUser.size(); i2++) {
                setIsNonStrongBiometricAllowedOneUser(z, this.mIsNonStrongBiometricAllowedForUser.keyAt(i2));
            }
            return;
        }
        setIsNonStrongBiometricAllowedOneUser(z, i);
    }

    private void setIsNonStrongBiometricAllowedOneUser(boolean z, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "setIsNonStrongBiometricAllowedOneUser for allowed=" + z + ", userId=" + i);
        }
        boolean z2 = this.mIsNonStrongBiometricAllowedForUser.get(i, true);
        if (z != z2) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "mIsNonStrongBiometricAllowedForUser value changed: oldValue=" + z2 + ", allowed=" + z);
            }
            this.mIsNonStrongBiometricAllowedForUser.put(i, z);
            notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(z, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScheduleNonStrongBiometricIdleTimeout(int i) {
        com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener;
        if (DEBUG) {
            android.util.Slog.d(TAG, "handleScheduleNonStrongBiometricIdleTimeout for userId=" + i);
        }
        long nextAlarmTimeMs = this.mInjector.getNextAlarmTimeMs(14400000L);
        com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener2 = this.mNonStrongBiometricIdleTimeoutAlarmListener.get(java.lang.Integer.valueOf(i));
        if (nonStrongBiometricIdleTimeoutAlarmListener2 != null) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Cancel existing alarm for non-strong biometric idle timeout");
            }
            this.mAlarmManager.cancel(nonStrongBiometricIdleTimeoutAlarmListener2);
            nonStrongBiometricIdleTimeoutAlarmListener = nonStrongBiometricIdleTimeoutAlarmListener2;
        } else {
            com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricIdleTimeoutAlarmListener nonStrongBiometricIdleTimeoutAlarmListener3 = new com.android.server.locksettings.LockSettingsStrongAuth.NonStrongBiometricIdleTimeoutAlarmListener(i);
            this.mNonStrongBiometricIdleTimeoutAlarmListener.put(java.lang.Integer.valueOf(i), nonStrongBiometricIdleTimeoutAlarmListener3);
            nonStrongBiometricIdleTimeoutAlarmListener = nonStrongBiometricIdleTimeoutAlarmListener3;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Schedule a new alarm for non-strong biometric idle timeout");
        }
        this.mAlarmManager.setExact(2, nextAlarmTimeMs, NON_STRONG_BIOMETRIC_IDLE_TIMEOUT_ALARM_TAG, nonStrongBiometricIdleTimeoutAlarmListener, this.mHandler);
    }

    private void notifyStrongAuthTrackers(int i, int i2) {
        int beginBroadcast = this.mTrackers.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                try {
                    this.mTrackers.getBroadcastItem(beginBroadcast).onStrongAuthRequiredChanged(i, i2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Exception while notifying StrongAuthTracker.", e);
                }
            } finally {
                this.mTrackers.finishBroadcast();
            }
        }
    }

    private void notifyStrongAuthTrackersForIsNonStrongBiometricAllowed(boolean z, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "notifyStrongAuthTrackersForIsNonStrongBiometricAllowed for allowed=" + z + ", userId=" + i);
        }
        int beginBroadcast = this.mTrackers.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                try {
                    this.mTrackers.getBroadcastItem(beginBroadcast).onIsNonStrongBiometricAllowedChanged(z, i);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Exception while notifying StrongAuthTracker: IsNonStrongBiometricAllowedChanged.", e);
                }
            } finally {
                this.mTrackers.finishBroadcast();
            }
        }
    }

    public void registerStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) {
        this.mHandler.obtainMessage(2, iStrongAuthTracker).sendToTarget();
    }

    public void unregisterStrongAuthTracker(android.app.trust.IStrongAuthTracker iStrongAuthTracker) {
        this.mHandler.obtainMessage(3, iStrongAuthTracker).sendToTarget();
    }

    public void removeUser(int i) {
        this.mHandler.obtainMessage(4, i, 0).sendToTarget();
    }

    public void requireStrongAuth(int i, int i2) {
        if (i2 == -1 || i2 >= 0) {
            this.mHandler.obtainMessage(1, i, i2).sendToTarget();
            return;
        }
        throw new java.lang.IllegalArgumentException("userId must be an explicit user id or USER_ALL");
    }

    void noLongerRequireStrongAuth(int i, int i2) {
        if (i2 == -1 || i2 >= 0) {
            this.mHandler.obtainMessage(6, i, i2).sendToTarget();
            return;
        }
        throw new java.lang.IllegalArgumentException("userId must be an explicit user id or USER_ALL");
    }

    public void reportUnlock(int i) {
        requireStrongAuth(0, i);
    }

    public void reportSuccessfulStrongAuthUnlock(int i) {
        this.mHandler.obtainMessage(5, i, 0).sendToTarget();
    }

    public void refreshStrongAuthTimeout(int i) {
        this.mHandler.obtainMessage(10, i, 0).sendToTarget();
    }

    public void reportSuccessfulBiometricUnlock(boolean z, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "reportSuccessfulBiometricUnlock for isStrongBiometric=" + z + ", userId=" + i);
        }
        if (z) {
            this.mHandler.obtainMessage(8, i, 0).sendToTarget();
        } else {
            this.mHandler.obtainMessage(7, i, 0).sendToTarget();
        }
    }

    public void scheduleNonStrongBiometricIdleTimeout(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "scheduleNonStrongBiometricIdleTimeout for userId=" + i);
        }
        this.mHandler.obtainMessage(9, i, 0).sendToTarget();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected class StrongAuthTimeoutAlarmListener implements android.app.AlarmManager.OnAlarmListener {
        private long mLatestStrongAuthTime;
        private final int mUserId;

        public StrongAuthTimeoutAlarmListener(long j, int i) {
            this.mLatestStrongAuthTime = j;
            this.mUserId = i;
        }

        public void setLatestStrongAuthTime(long j) {
            this.mLatestStrongAuthTime = j;
        }

        public long getLatestStrongAuthTime() {
            return this.mLatestStrongAuthTime;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            com.android.server.locksettings.LockSettingsStrongAuth.this.requireStrongAuth(16, this.mUserId);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected class NonStrongBiometricTimeoutAlarmListener implements android.app.AlarmManager.OnAlarmListener {
        private final int mUserId;

        NonStrongBiometricTimeoutAlarmListener(int i) {
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            com.android.server.locksettings.LockSettingsStrongAuth.this.requireStrongAuth(128, this.mUserId);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected class NonStrongBiometricIdleTimeoutAlarmListener implements android.app.AlarmManager.OnAlarmListener {
        private final int mUserId;

        NonStrongBiometricIdleTimeoutAlarmListener(int i) {
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            com.android.server.locksettings.LockSettingsStrongAuth.this.setIsNonStrongBiometricAllowed(false, this.mUserId);
        }
    }

    public void dump(com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("PrimaryAuthFlags state:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mStrongAuthForUser.size(); i++) {
            indentingPrintWriter.println("userId=" + this.mStrongAuthForUser.keyAt(i) + ", primaryAuthFlags=" + java.lang.Integer.toHexString(this.mStrongAuthForUser.valueAt(i)));
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("NonStrongBiometricAllowed state:");
        indentingPrintWriter.increaseIndent();
        for (int i2 = 0; i2 < this.mIsNonStrongBiometricAllowedForUser.size(); i2++) {
            indentingPrintWriter.println("userId=" + this.mIsNonStrongBiometricAllowedForUser.keyAt(i2) + ", allowed=" + this.mIsNonStrongBiometricAllowedForUser.valueAt(i2));
        }
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
    }
}
