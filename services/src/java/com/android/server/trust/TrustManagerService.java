package com.android.server.trust;

/* loaded from: classes2.dex */
public class TrustManagerService extends com.android.server.SystemService {
    static final boolean DEBUG;
    private static final int MSG_CLEANUP_USER = 8;
    private static final int MSG_DISPATCH_UNLOCK_ATTEMPT = 3;
    private static final int MSG_DISPATCH_UNLOCK_LOCKOUT = 13;
    private static final int MSG_ENABLED_AGENTS_CHANGED = 4;
    private static final int MSG_FLUSH_TRUST_USUALLY_MANAGED = 10;
    private static final int MSG_KEYGUARD_SHOWING_CHANGED = 6;
    private static final int MSG_REFRESH_DEVICE_LOCKED_FOR_USER = 14;
    private static final int MSG_REFRESH_TRUSTABLE_TIMERS_AFTER_AUTH = 17;
    private static final int MSG_REGISTER_LISTENER = 1;
    private static final int MSG_SCHEDULE_TRUST_TIMEOUT = 15;
    private static final int MSG_START_USER = 7;
    private static final int MSG_STOP_USER = 12;
    private static final int MSG_SWITCH_USER = 9;
    private static final int MSG_UNLOCK_USER = 11;
    private static final int MSG_UNREGISTER_LISTENER = 2;
    private static final int MSG_USER_MAY_REQUEST_UNLOCK = 18;
    private static final int MSG_USER_REQUESTED_UNLOCK = 16;
    private static final java.lang.String PERMISSION_PROVIDE_AGENT = "android.permission.PROVIDE_TRUST_AGENT";
    private static final java.lang.String PRIV_NAMESPACE = "http://schemas.android.com/apk/prv/res/android";
    private static final java.lang.String REFRESH_DEVICE_LOCKED_EXCEPT_USER = "except";
    private static final java.lang.String TAG = "TrustManagerService";
    private static final long TRUSTABLE_IDLE_TIMEOUT_IN_MILLIS = 28800000;
    private static final long TRUSTABLE_TIMEOUT_IN_MILLIS = 86400000;
    private static final android.content.Intent TRUST_AGENT_INTENT;
    private static final java.lang.String TRUST_TIMEOUT_ALARM_TAG = "TrustManagerService.trustTimeoutForUser";
    private static final long TRUST_TIMEOUT_IN_MILLIS = 14400000;
    private static final int TRUST_USUALLY_MANAGED_FLUSH_DELAY = 120000;
    private final android.util.ArraySet<com.android.server.trust.TrustManagerService.AgentInfo> mActiveAgents;
    private final android.app.ActivityManager mActivityManager;
    private final java.lang.Object mAlarmLock;
    private android.app.AlarmManager mAlarmManager;
    final com.android.server.trust.TrustArchive mArchive;
    private final android.content.Context mContext;
    private int mCurrentUser;

    @com.android.internal.annotations.GuardedBy({"mDeviceLockedForUser"})
    private final android.util.SparseBooleanArray mDeviceLockedForUser;
    private android.hardware.face.FaceManager mFaceManager;
    private android.hardware.fingerprint.FingerprintManager mFingerprintManager;
    private final android.os.Handler mHandler;
    private final android.util.SparseArray<com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener> mIdleTrustableTimeoutAlarmListenerForUser;
    private final android.util.SparseBooleanArray mLastActiveUnlockRunningState;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.internal.content.PackageMonitor mPackageMonitor;
    private final com.android.server.trust.TrustManagerService.Receiver mReceiver;
    private final android.os.IBinder mService;
    private final com.android.server.trust.TrustManagerService.StrongAuthTracker mStrongAuthTracker;
    private boolean mTrustAgentsCanRun;
    private final java.util.ArrayList<android.app.trust.ITrustListener> mTrustListeners;
    private final android.util.ArrayMap<java.lang.Integer, com.android.server.trust.TrustManagerService.TrustedTimeoutAlarmListener> mTrustTimeoutAlarmListenerForUser;

    @com.android.internal.annotations.GuardedBy({"mTrustUsuallyManagedForUser"})
    private final android.util.SparseBooleanArray mTrustUsuallyManagedForUser;
    private final android.util.SparseArray<com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener> mTrustableTimeoutAlarmListenerForUser;
    private final android.os.UserManager mUserManager;

    @com.android.internal.annotations.GuardedBy({"mUserTrustState"})
    private final android.util.SparseArray<com.android.server.trust.TrustManagerService.TrustState> mUserTrustState;

    @com.android.internal.annotations.GuardedBy({"mUsersUnlockedByBiometric"})
    private final android.util.SparseBooleanArray mUsersUnlockedByBiometric;

    private enum TimeoutType {
        TRUSTED,
        TRUSTABLE
    }

    private enum TrustState {
        UNTRUSTED,
        TRUSTABLE,
        TRUSTED
    }

    static {
        DEBUG = android.os.Build.IS_DEBUGGABLE && android.util.Log.isLoggable(TAG, 2);
        TRUST_AGENT_INTENT = new android.content.Intent("android.service.trust.TrustAgentService");
    }

    protected static class Injector {
        private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;
        private final android.os.Looper mLooper;

        public Injector(com.android.internal.widget.LockPatternUtils lockPatternUtils, android.os.Looper looper) {
            this.mLockPatternUtils = lockPatternUtils;
            this.mLooper = looper;
        }

        com.android.internal.widget.LockPatternUtils getLockPatternUtils() {
            return this.mLockPatternUtils;
        }

        android.os.Looper getLooper() {
            return this.mLooper;
        }
    }

    public TrustManagerService(android.content.Context context) {
        this(context, new com.android.server.trust.TrustManagerService.Injector(new com.android.internal.widget.LockPatternUtils(context), android.os.Looper.myLooper()));
    }

    protected TrustManagerService(android.content.Context context, com.android.server.trust.TrustManagerService.Injector injector) {
        super(context);
        this.mActiveAgents = new android.util.ArraySet<>();
        this.mLastActiveUnlockRunningState = new android.util.SparseBooleanArray();
        this.mTrustListeners = new java.util.ArrayList<>();
        this.mReceiver = new com.android.server.trust.TrustManagerService.Receiver();
        this.mArchive = new com.android.server.trust.TrustArchive();
        this.mUserTrustState = new android.util.SparseArray<>();
        this.mDeviceLockedForUser = new android.util.SparseBooleanArray();
        this.mTrustUsuallyManagedForUser = new android.util.SparseBooleanArray();
        this.mUsersUnlockedByBiometric = new android.util.SparseBooleanArray();
        this.mTrustTimeoutAlarmListenerForUser = new android.util.ArrayMap<>();
        this.mTrustableTimeoutAlarmListenerForUser = new android.util.SparseArray<>();
        this.mIdleTrustableTimeoutAlarmListenerForUser = new android.util.SparseArray<>();
        this.mAlarmLock = new java.lang.Object();
        this.mTrustAgentsCanRun = false;
        this.mCurrentUser = 0;
        this.mService = new com.android.server.trust.TrustManagerService.AnonymousClass1();
        this.mPackageMonitor = new com.android.internal.content.PackageMonitor() { // from class: com.android.server.trust.TrustManagerService.3
            public void onSomePackagesChanged() {
                com.android.server.trust.TrustManagerService.this.refreshAgentList(-1);
            }

            public void onPackageAdded(java.lang.String str, int i) {
                com.android.server.trust.TrustManagerService.this.checkNewAgentsForUser(android.os.UserHandle.getUserId(i));
            }

            public boolean onPackageChanged(java.lang.String str, int i, java.lang.String[] strArr) {
                com.android.server.trust.TrustManagerService.this.checkNewAgentsForUser(android.os.UserHandle.getUserId(i));
                return true;
            }

            public void onPackageDisappeared(java.lang.String str, int i) {
                com.android.server.trust.TrustManagerService.this.removeAgentsOfPackage(str);
            }
        };
        this.mContext = context;
        this.mHandler = createHandler(injector.getLooper());
        this.mUserManager = (android.os.UserManager) this.mContext.getSystemService("user");
        this.mActivityManager = (android.app.ActivityManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.HOSTING_TYPE_ACTIVITY);
        this.mLockPatternUtils = injector.getLockPatternUtils();
        this.mStrongAuthTracker = new com.android.server.trust.TrustManagerService.StrongAuthTracker(context, injector.getLooper());
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("trust", this.mService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (isSafeMode()) {
            return;
        }
        if (i == 500) {
            checkNewAgents();
            this.mPackageMonitor.register(this.mContext, this.mHandler.getLooper(), android.os.UserHandle.ALL, true);
            this.mReceiver.register(this.mContext);
            this.mLockPatternUtils.registerStrongAuthTracker(this.mStrongAuthTracker);
            this.mFingerprintManager = (android.hardware.fingerprint.FingerprintManager) this.mContext.getSystemService(android.hardware.fingerprint.FingerprintManager.class);
            this.mFaceManager = (android.hardware.face.FaceManager) this.mContext.getSystemService(android.hardware.face.FaceManager.class);
            return;
        }
        if (i == 600) {
            this.mTrustAgentsCanRun = true;
            refreshAgentList(-1);
            refreshDeviceLockedForUser(-1);
        } else if (i == 1000) {
            maybeEnableFactoryTrustAgents(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAutomotive() {
        return getContext().getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    }

    private void scheduleTrustTimeout(boolean z, boolean z2) {
        this.mHandler.obtainMessage(15, z ? 1 : 0, z2 ? 1 : 0).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleScheduleTrustTimeout(boolean z, com.android.server.trust.TrustManagerService.TimeoutType timeoutType) {
        int i = this.mCurrentUser;
        if (timeoutType == com.android.server.trust.TrustManagerService.TimeoutType.TRUSTABLE) {
            handleScheduleTrustableTimeouts(i, z, false);
        } else {
            handleScheduleTrustedTimeout(i, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshTrustableTimers(int i) {
        handleScheduleTrustableTimeouts(i, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelBothTrustableAlarms(int i) {
        com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener = this.mIdleTrustableTimeoutAlarmListenerForUser.get(i);
        com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener2 = this.mTrustableTimeoutAlarmListenerForUser.get(i);
        if (trustableTimeoutAlarmListener != null && trustableTimeoutAlarmListener.isQueued()) {
            trustableTimeoutAlarmListener.setQueued(false);
            this.mAlarmManager.cancel(trustableTimeoutAlarmListener);
        }
        if (trustableTimeoutAlarmListener2 != null && trustableTimeoutAlarmListener2.isQueued()) {
            trustableTimeoutAlarmListener2.setQueued(false);
            this.mAlarmManager.cancel(trustableTimeoutAlarmListener2);
        }
    }

    private void handleScheduleTrustedTimeout(int i, boolean z) {
        com.android.server.trust.TrustManagerService.TrustedTimeoutAlarmListener trustedTimeoutAlarmListener;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + 14400000;
        com.android.server.trust.TrustManagerService.TrustedTimeoutAlarmListener trustedTimeoutAlarmListener2 = this.mTrustTimeoutAlarmListenerForUser.get(java.lang.Integer.valueOf(i));
        if (trustedTimeoutAlarmListener2 != null) {
            if (!z && trustedTimeoutAlarmListener2.isQueued()) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Found existing trust timeout alarm. Skipping.");
                    return;
                }
                return;
            }
            this.mAlarmManager.cancel(trustedTimeoutAlarmListener2);
            trustedTimeoutAlarmListener = trustedTimeoutAlarmListener2;
        } else {
            com.android.server.trust.TrustManagerService.TrustedTimeoutAlarmListener trustedTimeoutAlarmListener3 = new com.android.server.trust.TrustManagerService.TrustedTimeoutAlarmListener(i);
            this.mTrustTimeoutAlarmListenerForUser.put(java.lang.Integer.valueOf(i), trustedTimeoutAlarmListener3);
            trustedTimeoutAlarmListener = trustedTimeoutAlarmListener3;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "\tSetting up trust timeout alarm");
        }
        trustedTimeoutAlarmListener.setQueued(true);
        this.mAlarmManager.setExact(2, elapsedRealtime, TRUST_TIMEOUT_ALARM_TAG, trustedTimeoutAlarmListener, this.mHandler);
    }

    private void handleScheduleTrustableTimeouts(int i, boolean z, boolean z2) {
        setUpIdleTimeout(i, z);
        setUpHardTimeout(i, z2);
    }

    private void setUpIdleTimeout(int i, boolean z) {
        com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + TRUSTABLE_IDLE_TIMEOUT_IN_MILLIS;
        com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener2 = this.mIdleTrustableTimeoutAlarmListenerForUser.get(i);
        this.mContext.enforceCallingOrSelfPermission("android.permission.SCHEDULE_EXACT_ALARM", null);
        if (trustableTimeoutAlarmListener2 != null) {
            if (!z && trustableTimeoutAlarmListener2.isQueued()) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "Found existing trustable timeout alarm. Skipping.");
                    return;
                }
                return;
            }
            this.mAlarmManager.cancel(trustableTimeoutAlarmListener2);
            trustableTimeoutAlarmListener = trustableTimeoutAlarmListener2;
        } else {
            com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener3 = new com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener(i);
            this.mIdleTrustableTimeoutAlarmListenerForUser.put(i, trustableTimeoutAlarmListener3);
            trustableTimeoutAlarmListener = trustableTimeoutAlarmListener3;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "\tSetting up trustable idle timeout alarm");
        }
        trustableTimeoutAlarmListener.setQueued(true);
        this.mAlarmManager.setExact(2, elapsedRealtime, TRUST_TIMEOUT_ALARM_TAG, trustableTimeoutAlarmListener, this.mHandler);
    }

    private void setUpHardTimeout(int i, boolean z) {
        com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener;
        this.mContext.enforceCallingOrSelfPermission("android.permission.SCHEDULE_EXACT_ALARM", null);
        com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener2 = this.mTrustableTimeoutAlarmListenerForUser.get(i);
        if (trustableTimeoutAlarmListener2 == null || !trustableTimeoutAlarmListener2.isQueued() || z) {
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + 86400000;
            if (trustableTimeoutAlarmListener2 == null) {
                com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener3 = new com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener(i);
                this.mTrustableTimeoutAlarmListenerForUser.put(i, trustableTimeoutAlarmListener3);
                trustableTimeoutAlarmListener = trustableTimeoutAlarmListener3;
            } else {
                if (z) {
                    this.mAlarmManager.cancel(trustableTimeoutAlarmListener2);
                }
                trustableTimeoutAlarmListener = trustableTimeoutAlarmListener2;
            }
            if (DEBUG) {
                android.util.Slog.d(TAG, "\tSetting up trustable hard timeout alarm");
            }
            trustableTimeoutAlarmListener.setQueued(true);
            this.mAlarmManager.setExact(2, elapsedRealtime, TRUST_TIMEOUT_ALARM_TAG, trustableTimeoutAlarmListener, this.mHandler);
        }
    }

    private static final class AgentInfo {
        com.android.server.trust.TrustAgentWrapper agent;
        android.content.ComponentName component;
        android.graphics.drawable.Drawable icon;
        java.lang.CharSequence label;
        com.android.server.trust.TrustManagerService.SettingsAttrs settings;
        int userId;

        private AgentInfo() {
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.trust.TrustManagerService.AgentInfo)) {
                return false;
            }
            com.android.server.trust.TrustManagerService.AgentInfo agentInfo = (com.android.server.trust.TrustManagerService.AgentInfo) obj;
            return this.component.equals(agentInfo.component) && this.userId == agentInfo.userId;
        }

        public int hashCode() {
            return (this.component.hashCode() * 31) + this.userId;
        }
    }

    private void updateTrustAll() {
        java.util.Iterator it = this.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
            updateTrust(((android.content.pm.UserInfo) it.next()).id, 0);
        }
    }

    public void updateTrust(int i, int i2) {
        updateTrust(i, i2, null);
    }

    public void updateTrust(int i, int i2, @android.annotation.Nullable com.android.internal.infra.AndroidFuture<android.service.trust.GrantTrustResult> androidFuture) {
        updateTrust(i, i2, false, androidFuture);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateTrust(int i, int i2, boolean z, @android.annotation.Nullable com.android.internal.infra.AndroidFuture<android.service.trust.GrantTrustResult> androidFuture) {
        boolean z2;
        boolean z3;
        com.android.server.trust.TrustManagerService.TrustState trustState;
        boolean z4;
        boolean z5;
        boolean aggregateIsTrustManaged = aggregateIsTrustManaged(i);
        dispatchOnTrustManagedChanged(aggregateIsTrustManaged, i);
        if (this.mStrongAuthTracker.isTrustAllowedForUser(i) && isTrustUsuallyManagedInternal(i) != aggregateIsTrustManaged) {
            updateTrustUsuallyManaged(i, aggregateIsTrustManaged);
        }
        boolean aggregateIsTrusted = aggregateIsTrusted(i);
        boolean aggregateIsTrustable = aggregateIsTrustable(i);
        try {
            z2 = !android.view.WindowManagerGlobal.getWindowManagerService().isKeyguardLocked();
        } catch (android.os.RemoteException e) {
            z2 = false;
        }
        synchronized (this.mUserTrustState) {
            try {
                boolean z6 = this.mUserTrustState.get(i) == com.android.server.trust.TrustManagerService.TrustState.TRUSTED;
                boolean z7 = this.mUserTrustState.get(i) == com.android.server.trust.TrustManagerService.TrustState.TRUSTABLE;
                boolean z8 = z7 && (i2 & 4) != 0;
                if (!z2 && !z && !z8 && !isAutomotive()) {
                    z3 = false;
                    boolean z9 = i != this.mCurrentUser;
                    if (aggregateIsTrusted || !z6) {
                        if (!aggregateIsTrusted && z3 && z9) {
                            trustState = com.android.server.trust.TrustManagerService.TrustState.TRUSTED;
                        } else if (!aggregateIsTrustable && ((z6 || z7) && z9)) {
                            trustState = com.android.server.trust.TrustManagerService.TrustState.TRUSTABLE;
                        } else {
                            trustState = com.android.server.trust.TrustManagerService.TrustState.UNTRUSTED;
                        }
                        this.mUserTrustState.put(i, trustState);
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "pendingTrustState: " + trustState);
                        }
                        z4 = trustState != com.android.server.trust.TrustManagerService.TrustState.TRUSTED;
                        z5 = z2 && z4;
                        maybeActiveUnlockRunningChanged(i);
                        dispatchOnTrustChanged(z4, z5, i, i2, getTrustGrantedMessages(i));
                        if (z4 != z6) {
                            refreshDeviceLockedForUser(i);
                            if (z4) {
                                boolean z10 = (i2 & 4) != 0;
                                scheduleTrustTimeout(z10, z10);
                            }
                        }
                        if (z5 && androidFuture != null) {
                            if (DEBUG) {
                                android.util.Slog.d(TAG, "calling back with UNLOCKED_BY_GRANT");
                            }
                            androidFuture.complete(new android.service.trust.GrantTrustResult(1));
                        }
                        if ((!z6 || z7) && trustState == com.android.server.trust.TrustManagerService.TrustState.UNTRUSTED) {
                            if (DEBUG) {
                                android.util.Slog.d(TAG, "Trust was revoked, destroy trustable alarms");
                            }
                            cancelBothTrustableAlarms(i);
                        }
                        return;
                    }
                    return;
                }
                z3 = true;
                if (i != this.mCurrentUser) {
                }
                if (aggregateIsTrusted) {
                }
                if (!aggregateIsTrusted) {
                }
                if (!aggregateIsTrustable) {
                }
                trustState = com.android.server.trust.TrustManagerService.TrustState.UNTRUSTED;
                this.mUserTrustState.put(i, trustState);
                if (DEBUG) {
                }
                if (trustState != com.android.server.trust.TrustManagerService.TrustState.TRUSTED) {
                }
                if (z2) {
                }
                maybeActiveUnlockRunningChanged(i);
                dispatchOnTrustChanged(z4, z5, i, i2, getTrustGrantedMessages(i));
                if (z4 != z6) {
                }
                if (z5) {
                    if (DEBUG) {
                    }
                    androidFuture.complete(new android.service.trust.GrantTrustResult(1));
                }
                if (!z6) {
                }
                if (DEBUG) {
                }
                cancelBothTrustableAlarms(i);
            } finally {
            }
        }
    }

    private void updateTrustUsuallyManaged(int i, boolean z) {
        synchronized (this.mTrustUsuallyManagedForUser) {
            this.mTrustUsuallyManagedForUser.put(i, z);
        }
        this.mHandler.removeMessages(10);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(10), 120000L);
    }

    public long addEscrowToken(byte[] bArr, int i) {
        return this.mLockPatternUtils.addEscrowToken(bArr, i, new com.android.internal.widget.LockPatternUtils.EscrowTokenStateChangeCallback() { // from class: com.android.server.trust.TrustManagerService$$ExternalSyntheticLambda0
            public final void onEscrowTokenActivated(long j, int i2) {
                com.android.server.trust.TrustManagerService.this.lambda$addEscrowToken$0(j, i2);
            }
        });
    }

    public boolean removeEscrowToken(long j, int i) {
        return this.mLockPatternUtils.removeEscrowToken(j, i);
    }

    public boolean isEscrowTokenActive(long j, int i) {
        return this.mLockPatternUtils.isEscrowTokenActive(j, i);
    }

    public void unlockUserWithToken(long j, byte[] bArr, int i) {
        this.mLockPatternUtils.unlockUserWithToken(j, bArr, i);
    }

    public void lockUser(int i) {
        this.mLockPatternUtils.requireStrongAuth(256, i);
        try {
            android.view.WindowManagerGlobal.getWindowManagerService().lockNow((android.os.Bundle) null);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error locking screen when called from trust agent");
        }
    }

    void showKeyguardErrorMessage(java.lang.CharSequence charSequence) {
        dispatchOnTrustError(charSequence);
    }

    void refreshAgentList(int i) {
        java.util.List arrayList;
        com.android.internal.widget.LockPatternUtils lockPatternUtils;
        java.util.Iterator<android.content.pm.ResolveInfo> it;
        boolean z;
        int strongAuthForUser;
        int i2 = i;
        if (DEBUG) {
            android.util.Slog.d(TAG, "refreshAgentList(" + i2 + ")");
        }
        if (!this.mTrustAgentsCanRun) {
            return;
        }
        if (i2 != -1 && i2 < 0) {
            android.util.Log.e(TAG, "refreshAgentList(userId=" + i2 + "): Invalid user handle, must be USER_ALL or a specific user.", new java.lang.Throwable("here"));
            i2 = -1;
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        if (i2 == -1) {
            arrayList = this.mUserManager.getAliveUsers();
        } else {
            arrayList = new java.util.ArrayList();
            arrayList.add(this.mUserManager.getUserInfo(i2));
        }
        com.android.internal.widget.LockPatternUtils lockPatternUtils2 = this.mLockPatternUtils;
        android.util.ArraySet arraySet = new android.util.ArraySet();
        arraySet.addAll((android.util.ArraySet) this.mActiveAgents);
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) it2.next();
            if (userInfo != null && !userInfo.partial && userInfo.isEnabled()) {
                if (!userInfo.guestToRemove) {
                    if (!userInfo.supportsSwitchToByUser()) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "refreshAgentList: skipping user " + userInfo.id + ": switchToByUser=false");
                        }
                    } else if (!this.mActivityManager.isUserRunning(userInfo.id)) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "refreshAgentList: skipping user " + userInfo.id + ": user not started");
                        }
                    } else if (!lockPatternUtils2.isSecure(userInfo.id)) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "refreshAgentList: skipping user " + userInfo.id + ": no secure credential");
                        }
                    } else {
                        android.app.admin.DevicePolicyManager devicePolicyManager = lockPatternUtils2.getDevicePolicyManager();
                        boolean z2 = (devicePolicyManager.getKeyguardDisabledFeatures(null, userInfo.id) & 16) != 0;
                        java.util.List enabledTrustAgents = lockPatternUtils2.getEnabledTrustAgents(userInfo.id);
                        if (enabledTrustAgents.isEmpty()) {
                            if (DEBUG) {
                                android.util.Slog.d(TAG, "refreshAgentList: skipping user " + userInfo.id + ": no agents enabled by user");
                            }
                        } else {
                            java.util.Iterator<android.content.pm.ResolveInfo> it3 = resolveAllowedTrustAgents(packageManager, userInfo.id).iterator();
                            while (it3.hasNext()) {
                                android.content.pm.ResolveInfo next = it3.next();
                                android.content.ComponentName componentName = getComponentName(next);
                                java.util.Iterator it4 = it2;
                                if (!enabledTrustAgents.contains(componentName)) {
                                    if (DEBUG) {
                                        android.util.Slog.d(TAG, "refreshAgentList: skipping " + componentName.flattenToShortString() + " u" + userInfo.id + ": not enabled by user");
                                    }
                                    it2 = it4;
                                } else {
                                    if (!z2) {
                                        lockPatternUtils = lockPatternUtils2;
                                        it = it3;
                                    } else {
                                        lockPatternUtils = lockPatternUtils2;
                                        it = it3;
                                        java.util.List trustAgentConfiguration = devicePolicyManager.getTrustAgentConfiguration(null, componentName, userInfo.id);
                                        if (trustAgentConfiguration == null || trustAgentConfiguration.isEmpty()) {
                                            if (DEBUG) {
                                                android.util.Slog.d(TAG, "refreshAgentList: skipping " + componentName.flattenToShortString() + " u" + userInfo.id + ": not allowed by DPM");
                                            }
                                            lockPatternUtils2 = lockPatternUtils;
                                            it2 = it4;
                                            it3 = it;
                                        }
                                    }
                                    com.android.server.trust.TrustManagerService.AgentInfo agentInfo = new com.android.server.trust.TrustManagerService.AgentInfo();
                                    agentInfo.component = componentName;
                                    agentInfo.userId = userInfo.id;
                                    if (!this.mActiveAgents.contains(agentInfo)) {
                                        agentInfo.label = next.loadLabel(packageManager);
                                        agentInfo.icon = next.loadIcon(packageManager);
                                        agentInfo.settings = getSettingsAttrs(packageManager, next);
                                    } else {
                                        agentInfo = this.mActiveAgents.valueAt(this.mActiveAgents.indexOf(agentInfo));
                                    }
                                    if (agentInfo.settings == null) {
                                        z = false;
                                    } else {
                                        z = next.serviceInfo.directBootAware && agentInfo.settings.canUnlockProfile;
                                    }
                                    if (z && DEBUG) {
                                        android.util.Slog.d(TAG, "refreshAgentList: trustagent " + componentName + "of user " + userInfo.id + "can unlock user profile.");
                                    }
                                    if (!this.mUserManager.isUserUnlockingOrUnlocked(userInfo.id) && !z) {
                                        if (DEBUG) {
                                            android.util.Slog.d(TAG, "refreshAgentList: skipping user " + userInfo.id + "'s trust agent " + componentName + ": FBE still locked and  the agent cannot unlock user profile.");
                                        }
                                        lockPatternUtils2 = lockPatternUtils;
                                        it2 = it4;
                                        it3 = it;
                                    } else {
                                        if (!this.mStrongAuthTracker.canAgentsRunForUser(userInfo.id) && (strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(userInfo.id)) != 8 && (strongAuthForUser != 1 || !z)) {
                                            if (DEBUG) {
                                                android.util.Slog.d(TAG, "refreshAgentList: skipping user " + userInfo.id + ": prevented by StrongAuthTracker = 0x" + java.lang.Integer.toHexString(this.mStrongAuthTracker.getStrongAuthForUser(userInfo.id)));
                                                lockPatternUtils2 = lockPatternUtils;
                                                it2 = it4;
                                                it3 = it;
                                            } else {
                                                lockPatternUtils2 = lockPatternUtils;
                                                it2 = it4;
                                                it3 = it;
                                            }
                                        }
                                        if (agentInfo.agent == null) {
                                            agentInfo.agent = new com.android.server.trust.TrustAgentWrapper(this.mContext, this, new android.content.Intent().setComponent(componentName), userInfo.getUserHandle());
                                        }
                                        if (!this.mActiveAgents.contains(agentInfo)) {
                                            this.mActiveAgents.add(agentInfo);
                                        } else {
                                            arraySet.remove(agentInfo);
                                        }
                                        lockPatternUtils2 = lockPatternUtils;
                                        it2 = it4;
                                        it3 = it;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        boolean z3 = false;
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            com.android.server.trust.TrustManagerService.AgentInfo agentInfo2 = (com.android.server.trust.TrustManagerService.AgentInfo) arraySet.valueAt(i3);
            if (i2 == -1 || i2 == agentInfo2.userId) {
                if (agentInfo2.agent.isManagingTrust()) {
                    z3 = true;
                }
                agentInfo2.agent.destroy();
                this.mActiveAgents.remove(agentInfo2);
            }
        }
        if (z3) {
            if (i2 != -1) {
                updateTrust(i2, 0);
            } else {
                updateTrustAll();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.trust.TrustManagerService.TrustState getUserTrustStateInner(int i) {
        com.android.server.trust.TrustManagerService.TrustState trustState;
        synchronized (this.mUserTrustState) {
            trustState = this.mUserTrustState.get(i, com.android.server.trust.TrustManagerService.TrustState.UNTRUSTED);
        }
        return trustState;
    }

    boolean isDeviceLockedInner(int i) {
        boolean z;
        synchronized (this.mDeviceLockedForUser) {
            z = this.mDeviceLockedForUser.get(i, true);
        }
        return z;
    }

    private void maybeActiveUnlockRunningChanged(int i) {
        boolean z = this.mLastActiveUnlockRunningState.get(i);
        boolean aggregateIsActiveUnlockRunning = aggregateIsActiveUnlockRunning(i);
        if (z == aggregateIsActiveUnlockRunning) {
            return;
        }
        this.mLastActiveUnlockRunningState.put(i, aggregateIsActiveUnlockRunning);
        for (int i2 = 0; i2 < this.mTrustListeners.size(); i2++) {
            notifyListenerIsActiveUnlockRunning(this.mTrustListeners.get(i2), aggregateIsActiveUnlockRunning, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDeviceLockedForUser(int i) {
        refreshDeviceLockedForUser(i, com.android.server.am.ProcessList.INVALID_ADJ);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDeviceLockedForUser(int i, int i2) {
        java.util.List list;
        boolean aggregateIsTrusted;
        boolean z;
        boolean z2;
        boolean z3;
        if (i != -1 && i < 0) {
            android.util.Log.e(TAG, "refreshDeviceLockedForUser(userId=" + i + "): Invalid user handle, must be USER_ALL or a specific user.", new java.lang.Throwable("here"));
            i = -1;
        }
        if (i == -1) {
            list = this.mUserManager.getAliveUsers();
        } else {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(this.mUserManager.getUserInfo(i));
            list = arrayList;
        }
        android.view.IWindowManager windowManagerService = android.view.WindowManagerGlobal.getWindowManagerService();
        for (int i3 = 0; i3 < list.size(); i3++) {
            android.content.pm.UserInfo userInfo = (android.content.pm.UserInfo) list.get(i3);
            if (userInfo != null && !userInfo.partial && userInfo.isEnabled() && !userInfo.guestToRemove) {
                int i4 = userInfo.id;
                boolean isSecure = this.mLockPatternUtils.isSecure(i4);
                if (!userInfo.supportsSwitchToByUser()) {
                    if (userInfo.isProfile() && !isSecure && !this.mLockPatternUtils.isProfileWithUnifiedChallenge(i4)) {
                        setDeviceLockedForUser(i4, false);
                    }
                } else {
                    if (android.security.Flags.fixUnlockedDeviceRequiredKeysV2()) {
                        aggregateIsTrusted = getUserTrustStateInner(i4) == com.android.server.trust.TrustManagerService.TrustState.TRUSTED;
                    } else {
                        aggregateIsTrusted = aggregateIsTrusted(i4);
                    }
                    if (this.mCurrentUser != i4) {
                        z = false;
                        z2 = false;
                        z3 = true;
                    } else {
                        synchronized (this.mUsersUnlockedByBiometric) {
                            z = this.mUsersUnlockedByBiometric.get(i4, false);
                        }
                        try {
                            z3 = windowManagerService.isKeyguardLocked();
                        } catch (android.os.RemoteException e) {
                            android.util.Log.w(TAG, "Unable to check keyguard lock state", e);
                            z3 = true;
                        }
                        z2 = i2 == i4;
                    }
                    boolean z4 = isSecure && z3 && !aggregateIsTrusted && !z;
                    if (!z4 || !z2) {
                        setDeviceLockedForUser(i4, z4);
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDeviceLockedForUser(int i, boolean z) {
        int i2;
        boolean z2;
        synchronized (this.mDeviceLockedForUser) {
            z2 = isDeviceLockedInner(i) != z;
            this.mDeviceLockedForUser.put(i, z);
        }
        if (z2) {
            notifyTrustAgentsOfDeviceLockState(i, z);
            notifyKeystoreOfDeviceLockState(i, z);
            for (int i3 : this.mUserManager.getEnabledProfileIds(i)) {
                if (this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(i3)) {
                    notifyKeystoreOfDeviceLockState(i3, z);
                }
            }
        }
    }

    private void notifyTrustAgentsOfDeviceLockState(int i, boolean z) {
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i) {
                if (z) {
                    valueAt.agent.onDeviceLocked();
                } else {
                    valueAt.agent.onDeviceUnlocked();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyKeystoreOfDeviceLockState(int i, boolean z) {
        if (z) {
            if (android.security.Flags.fixUnlockedDeviceRequiredKeysV2()) {
                int resolveProfileParent = this.mLockPatternUtils.isProfileWithUnifiedChallenge(i) ? resolveProfileParent(i) : i;
                android.security.Authorization.onDeviceLocked(i, getBiometricSids(resolveProfileParent), isWeakUnlockMethodEnabled(resolveProfileParent));
                return;
            } else {
                android.security.Authorization.onDeviceLocked(i, getBiometricSids(i), false);
                return;
            }
        }
        android.security.Authorization.onDeviceUnlocked(i, (byte[]) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dispatchEscrowTokenActivatedLocked, reason: merged with bridge method [inline-methods] */
    public void lambda$addEscrowToken$0(long j, int i) {
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i) {
                valueAt.agent.onEscrowTokenActivated(j, i);
            }
        }
    }

    void updateDevicePolicyFeatures() {
        boolean z = false;
        for (int i = 0; i < this.mActiveAgents.size(); i++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i);
            if (valueAt.agent.isConnected()) {
                valueAt.agent.updateDevicePolicyFeatures();
                z = true;
            }
        }
        if (z) {
            this.mArchive.logDevicePolicyChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeAgentsOfPackage(java.lang.String str) {
        boolean z = false;
        for (int size = this.mActiveAgents.size() - 1; size >= 0; size--) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(size);
            if (str.equals(valueAt.component.getPackageName())) {
                android.util.Log.i(TAG, "Resetting agent " + valueAt.component.flattenToShortString());
                if (valueAt.agent.isManagingTrust()) {
                    z = true;
                }
                valueAt.agent.destroy();
                this.mActiveAgents.removeAt(size);
            }
        }
        if (z) {
            updateTrustAll();
        }
    }

    public void resetAgent(android.content.ComponentName componentName, int i) {
        boolean z = false;
        for (int size = this.mActiveAgents.size() - 1; size >= 0; size--) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(size);
            if (componentName.equals(valueAt.component) && i == valueAt.userId) {
                android.util.Log.i(TAG, "Resetting agent " + valueAt.component.flattenToShortString());
                if (valueAt.agent.isManagingTrust()) {
                    z = true;
                }
                valueAt.agent.destroy();
                this.mActiveAgents.removeAt(size);
            }
        }
        if (z) {
            updateTrust(i, 0);
        }
        refreshAgentList(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ae, code lost:
    
        if (r3 == null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a3, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a9, code lost:
    
        if (r3 == null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a1, code lost:
    
        if (r3 == null) goto L63;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.trust.TrustManagerService.SettingsAttrs getSettingsAttrs(android.content.pm.PackageManager packageManager, android.content.pm.ResolveInfo resolveInfo) {
        android.content.res.XmlResourceParser xmlResourceParser;
        java.lang.String str;
        int next;
        android.content.res.XmlResourceParser xmlResourceParser2 = null;
        if (resolveInfo == null || resolveInfo.serviceInfo == null || resolveInfo.serviceInfo.metaData == null) {
            return null;
        }
        boolean z = false;
        try {
            xmlResourceParser = resolveInfo.serviceInfo.loadXmlMetaData(packageManager, "android.service.trust.trustagent");
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e = e;
            xmlResourceParser = null;
            str = null;
        } catch (java.io.IOException e2) {
            e = e2;
            xmlResourceParser = null;
            str = null;
        } catch (org.xmlpull.v1.XmlPullParserException e3) {
            e = e3;
            xmlResourceParser = null;
            str = null;
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            try {
            } catch (android.content.pm.PackageManager.NameNotFoundException e4) {
                e = e4;
                str = null;
            } catch (java.io.IOException e5) {
                e = e5;
                str = null;
            } catch (org.xmlpull.v1.XmlPullParserException e6) {
                e = e6;
                str = null;
            }
            if (xmlResourceParser == null) {
                android.util.Slog.w(TAG, "Can't find android.service.trust.trustagent meta-data");
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                return null;
            }
            android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(resolveInfo.serviceInfo.applicationInfo);
            android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlResourceParser);
            do {
                next = xmlResourceParser.next();
                if (next == 1) {
                    break;
                }
            } while (next != 2);
            if (!"trust-agent".equals(xmlResourceParser.getName())) {
                android.util.Slog.w(TAG, "Meta-data does not start with trust-agent tag");
                xmlResourceParser.close();
                return null;
            }
            android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.TrustAgent);
            str = obtainAttributes.getString(2);
            try {
                z = asAttributeSet.getAttributeBooleanValue(PRIV_NAMESPACE, "unlockProfile", false);
                obtainAttributes.recycle();
                xmlResourceParser.close();
                e = null;
            } catch (android.content.pm.PackageManager.NameNotFoundException e7) {
                e = e7;
            } catch (java.io.IOException e8) {
                e = e8;
            } catch (org.xmlpull.v1.XmlPullParserException e9) {
                e = e9;
            }
            if (e != null) {
                android.util.Slog.w(TAG, "Error parsing : " + resolveInfo.serviceInfo.packageName, e);
                return null;
            }
            if (str == null) {
                return null;
            }
            if (str.indexOf(47) < 0) {
                str = resolveInfo.serviceInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str;
            }
            return new com.android.server.trust.TrustManagerService.SettingsAttrs(android.content.ComponentName.unflattenFromString(str), z);
        } catch (java.lang.Throwable th2) {
            th = th2;
            xmlResourceParser2 = xmlResourceParser;
            if (xmlResourceParser2 != null) {
                xmlResourceParser2.close();
            }
            throw th;
        }
    }

    private android.content.ComponentName getComponentName(android.content.pm.ResolveInfo resolveInfo) {
        if (resolveInfo == null || resolveInfo.serviceInfo == null) {
            return null;
        }
        return new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeEnableFactoryTrustAgents(int i) {
        if (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "trust_agents_initialized", 0, i) != 0) {
            return;
        }
        java.util.List<android.content.pm.ResolveInfo> resolveAllowedTrustAgents = resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
        android.content.ComponentName defaultFactoryTrustAgent = getDefaultFactoryTrustAgent(this.mContext);
        boolean z = defaultFactoryTrustAgent != null;
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if (z) {
            arraySet.add(defaultFactoryTrustAgent);
            android.util.Log.i(TAG, "Enabling " + defaultFactoryTrustAgent + " because it is a default agent.");
        } else {
            for (android.content.pm.ResolveInfo resolveInfo : resolveAllowedTrustAgents) {
                android.content.ComponentName componentName = getComponentName(resolveInfo);
                if (!isSystemTrustAgent(resolveInfo)) {
                    android.util.Log.i(TAG, "Leaving agent " + componentName + " disabled because package is not a system package.");
                } else {
                    arraySet.add(componentName);
                }
            }
        }
        enableNewAgents(arraySet, i);
        android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "trust_agents_initialized", 1, i);
    }

    private void checkNewAgents() {
        java.util.Iterator it = this.mUserManager.getAliveUsers().iterator();
        while (it.hasNext()) {
            checkNewAgentsForUser(((android.content.pm.UserInfo) it.next()).id);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNewAgentsForUser(int i) {
        if (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "known_trust_agents_initialized", 0, i) == 0) {
            initializeKnownAgents(i);
            return;
        }
        java.util.List knownTrustAgents = this.mLockPatternUtils.getKnownTrustAgents(i);
        java.util.List<android.content.pm.ResolveInfo> resolveAllowedTrustAgents = resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
        android.util.ArraySet arraySet = new android.util.ArraySet(resolveAllowedTrustAgents.size());
        android.util.ArraySet arraySet2 = new android.util.ArraySet(resolveAllowedTrustAgents.size());
        for (android.content.pm.ResolveInfo resolveInfo : resolveAllowedTrustAgents) {
            android.content.ComponentName componentName = getComponentName(resolveInfo);
            if (!knownTrustAgents.contains(componentName)) {
                arraySet.add(componentName);
                if (isSystemTrustAgent(resolveInfo)) {
                    arraySet2.add(componentName);
                }
            }
        }
        if (arraySet.isEmpty()) {
            return;
        }
        android.util.ArraySet arraySet3 = new android.util.ArraySet(knownTrustAgents);
        arraySet3.addAll(arraySet);
        this.mLockPatternUtils.setKnownTrustAgents(arraySet3, i);
        if (!(getDefaultFactoryTrustAgent(this.mContext) != null)) {
            enableNewAgents(arraySet2, i);
        }
    }

    private void enableNewAgents(java.util.Collection<android.content.ComponentName> collection, int i) {
        if (collection.isEmpty()) {
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet(collection);
        arraySet.addAll(this.mLockPatternUtils.getEnabledTrustAgents(i));
        this.mLockPatternUtils.setEnabledTrustAgents(arraySet, i);
    }

    private void initializeKnownAgents(int i) {
        java.util.List<android.content.pm.ResolveInfo> resolveAllowedTrustAgents = resolveAllowedTrustAgents(this.mContext.getPackageManager(), i);
        android.util.ArraySet arraySet = new android.util.ArraySet(resolveAllowedTrustAgents.size());
        java.util.Iterator<android.content.pm.ResolveInfo> it = resolveAllowedTrustAgents.iterator();
        while (it.hasNext()) {
            arraySet.add(getComponentName(it.next()));
        }
        this.mLockPatternUtils.setKnownTrustAgents(arraySet, i);
        android.provider.Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "known_trust_agents_initialized", 1, i);
    }

    private static android.content.ComponentName getDefaultFactoryTrustAgent(android.content.Context context) {
        java.lang.String string = context.getResources().getString(android.R.string.config_defaultRotationResolverService);
        if (android.text.TextUtils.isEmpty(string)) {
            return null;
        }
        return android.content.ComponentName.unflattenFromString(string);
    }

    private java.util.List<android.content.pm.ResolveInfo> resolveAllowedTrustAgents(android.content.pm.PackageManager packageManager, int i) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(TRUST_AGENT_INTENT, 786560, i);
        java.util.ArrayList arrayList = new java.util.ArrayList(queryIntentServicesAsUser.size());
        for (android.content.pm.ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            if (resolveInfo.serviceInfo != null && resolveInfo.serviceInfo.applicationInfo != null) {
                if (packageManager.checkPermission(PERMISSION_PROVIDE_AGENT, resolveInfo.serviceInfo.packageName) != 0) {
                    android.util.Log.w(TAG, "Skipping agent " + getComponentName(resolveInfo) + " because package does not have permission " + PERMISSION_PROVIDE_AGENT + ".");
                } else {
                    arrayList.add(resolveInfo);
                }
            }
        }
        return arrayList;
    }

    private static boolean isSystemTrustAgent(android.content.pm.ResolveInfo resolveInfo) {
        return (resolveInfo.serviceInfo.applicationInfo.flags & 1) != 0;
    }

    private boolean aggregateIsTrusted(int i) {
        if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
            return false;
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i && valueAt.agent.isTrusted()) {
                return true;
            }
        }
        return false;
    }

    private boolean aggregateIsTrustable(int i) {
        if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
            return false;
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i && valueAt.agent.isTrustable()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean aggregateIsActiveUnlockRunning(int i) {
        if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
            return false;
        }
        synchronized (this.mUserTrustState) {
            try {
                com.android.server.trust.TrustManagerService.TrustState trustState = this.mUserTrustState.get(i);
                if (trustState != com.android.server.trust.TrustManagerService.TrustState.TRUSTED && trustState != com.android.server.trust.TrustManagerService.TrustState.TRUSTABLE) {
                    return false;
                }
                for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
                    com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
                    if (valueAt.userId == i && valueAt.agent.isTrustableOrWaitingForDowngrade()) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchTrustableDowngrade() {
        for (int i = 0; i < this.mActiveAgents.size(); i++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i);
            if (valueAt.userId == this.mCurrentUser) {
                valueAt.agent.downgradeToTrustable();
            }
        }
    }

    private java.util.List<java.lang.String> getTrustGrantedMessages(int i) {
        if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i && valueAt.agent.isTrusted() && valueAt.agent.shouldDisplayTrustGrantedMessage() && valueAt.agent.getMessage() != null) {
                arrayList.add(valueAt.agent.getMessage().toString());
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean aggregateIsTrustManaged(int i) {
        if (!this.mStrongAuthTracker.isTrustAllowedForUser(i)) {
            return false;
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i && valueAt.agent.isManagingTrust()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUnlockAttempt(boolean z, int i) {
        if (z) {
            this.mStrongAuthTracker.allowTrustFromUnlock(i);
            updateTrust(i, 0, true, null);
            this.mHandler.obtainMessage(17, java.lang.Integer.valueOf(i)).sendToTarget();
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i) {
                valueAt.agent.onUnlockAttempt(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUserRequestedUnlock(int i, boolean z) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "dispatchUserRequestedUnlock(user=" + i + ", dismissKeyguard=" + z + ")");
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i) {
                valueAt.agent.onUserRequestedUnlock(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUserMayRequestUnlock(int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "dispatchUserMayRequestUnlock(user=" + i + ")");
        }
        for (int i2 = 0; i2 < this.mActiveAgents.size(); i2++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i2);
            if (valueAt.userId == i) {
                valueAt.agent.onUserMayRequestUnlock();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchUnlockLockout(int i, int i2) {
        for (int i3 = 0; i3 < this.mActiveAgents.size(); i3++) {
            com.android.server.trust.TrustManagerService.AgentInfo valueAt = this.mActiveAgents.valueAt(i3);
            if (valueAt.userId == i2) {
                valueAt.agent.onUnlockLockout(i);
            }
        }
    }

    private void notifyListenerIsActiveUnlockRunningInitialState(android.app.trust.ITrustListener iTrustListener) {
        int size = this.mLastActiveUnlockRunningState.size();
        for (int i = 0; i < size; i++) {
            int keyAt = this.mLastActiveUnlockRunningState.keyAt(i);
            notifyListenerIsActiveUnlockRunning(iTrustListener, aggregateIsActiveUnlockRunning(keyAt), keyAt);
        }
    }

    private void notifyListenerIsActiveUnlockRunning(android.app.trust.ITrustListener iTrustListener, boolean z, int i) {
        try {
            iTrustListener.onIsActiveUnlockRunningChanged(z, i);
        } catch (android.os.DeadObjectException e) {
            android.util.Slog.d(TAG, "TrustListener dead while trying to notify Active Unlock running state");
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "Exception while notifying TrustListener.", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addListener(android.app.trust.ITrustListener iTrustListener) {
        for (int i = 0; i < this.mTrustListeners.size(); i++) {
            if (this.mTrustListeners.get(i).asBinder() == iTrustListener.asBinder()) {
                return;
            }
        }
        this.mTrustListeners.add(iTrustListener);
        notifyListenerIsActiveUnlockRunningInitialState(iTrustListener);
        updateTrustAll();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeListener(android.app.trust.ITrustListener iTrustListener) {
        for (int i = 0; i < this.mTrustListeners.size(); i++) {
            if (this.mTrustListeners.get(i).asBinder() == iTrustListener.asBinder()) {
                this.mTrustListeners.remove(i);
                return;
            }
        }
    }

    private void dispatchOnTrustChanged(boolean z, boolean z2, int i, int i2, @android.annotation.NonNull java.util.List<java.lang.String> list) {
        if (DEBUG) {
            android.util.Log.i(TAG, "onTrustChanged(" + z + ", " + z2 + ", " + i + ", 0x" + java.lang.Integer.toHexString(i2) + ")");
        }
        int i3 = 0;
        if (!z) {
            i2 = 0;
        }
        while (i3 < this.mTrustListeners.size()) {
            try {
                this.mTrustListeners.get(i3).onTrustChanged(z, z2, i, i2, list);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.d(TAG, "Removing dead TrustListener.");
                this.mTrustListeners.remove(i3);
                i3--;
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Exception while notifying TrustListener.", e2);
            }
            i3++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnEnabledTrustAgentsChanged(int i) {
        if (DEBUG) {
            android.util.Log.i(TAG, "onEnabledTrustAgentsChanged(" + i + ")");
        }
        int i2 = 0;
        while (i2 < this.mTrustListeners.size()) {
            try {
                this.mTrustListeners.get(i2).onEnabledTrustAgentsChanged(i);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.d(TAG, "Removing dead TrustListener.");
                this.mTrustListeners.remove(i2);
                i2--;
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Exception while notifying TrustListener.", e2);
            }
            i2++;
        }
    }

    private void dispatchOnTrustManagedChanged(boolean z, int i) {
        if (DEBUG) {
            android.util.Log.i(TAG, "onTrustManagedChanged(" + z + ", " + i + ")");
        }
        int i2 = 0;
        while (i2 < this.mTrustListeners.size()) {
            try {
                this.mTrustListeners.get(i2).onTrustManagedChanged(z, i);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.d(TAG, "Removing dead TrustListener.");
                this.mTrustListeners.remove(i2);
                i2--;
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Exception while notifying TrustListener.", e2);
            }
            i2++;
        }
    }

    private void dispatchOnTrustError(java.lang.CharSequence charSequence) {
        if (DEBUG) {
            android.util.Log.i(TAG, "onTrustError(" + ((java.lang.Object) charSequence) + ")");
        }
        int i = 0;
        while (i < this.mTrustListeners.size()) {
            try {
                this.mTrustListeners.get(i).onTrustError(charSequence);
            } catch (android.os.DeadObjectException e) {
                android.util.Slog.d(TAG, "Removing dead TrustListener.");
                this.mTrustListeners.remove(i);
                i--;
            } catch (android.os.RemoteException e2) {
                android.util.Slog.e(TAG, "Exception while notifying TrustListener.", e2);
            }
            i++;
        }
    }

    @android.annotation.NonNull
    private long[] getBiometricSids(int i) {
        android.hardware.biometrics.BiometricManager biometricManager = (android.hardware.biometrics.BiometricManager) this.mContext.getSystemService(android.hardware.biometrics.BiometricManager.class);
        if (biometricManager == null) {
            return new long[0];
        }
        return biometricManager.getAuthenticatorIds(i);
    }

    private boolean isWeakUnlockMethodEnabled(int i) {
        if (this.mStrongAuthTracker.isBiometricAllowedForUser(false, i)) {
            int keyguardDisabledFeatures = this.mLockPatternUtils.getDevicePolicyManager().getKeyguardDisabledFeatures(null, i);
            if (this.mFingerprintManager != null && (keyguardDisabledFeatures & 32) == 0 && this.mFingerprintManager.hasEnrolledTemplates(i) && isWeakOrConvenienceSensor((android.hardware.biometrics.SensorProperties) this.mFingerprintManager.getSensorProperties().get(0))) {
                android.util.Slog.i(TAG, "User is unlockable by non-strong fingerprint auth");
                return true;
            }
            if (this.mFaceManager != null && (keyguardDisabledFeatures & 128) == 0 && this.mFaceManager.hasEnrolledTemplates(i) && isWeakOrConvenienceSensor((android.hardware.biometrics.SensorProperties) this.mFaceManager.getSensorProperties().get(0))) {
                android.util.Slog.i(TAG, "User is unlockable by non-strong face auth");
                return true;
            }
        }
        if (getUserTrustStateInner(i) != com.android.server.trust.TrustManagerService.TrustState.TRUSTABLE && (!isAutomotive() || !isTrustUsuallyManagedInternal(i))) {
            return false;
        }
        android.util.Slog.i(TAG, "User is unlockable by trust agent");
        return true;
    }

    private static boolean isWeakOrConvenienceSensor(android.hardware.biometrics.SensorProperties sensorProperties) {
        return sensorProperties.getSensorStrength() == 1 || sensorProperties.getSensorStrength() == 0;
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mHandler.obtainMessage(7, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mHandler.obtainMessage(8, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        this.mHandler.obtainMessage(9, targetUser2.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mHandler.obtainMessage(11, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    @Override // com.android.server.SystemService
    public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        this.mHandler.obtainMessage(12, targetUser.getUserIdentifier(), 0, null).sendToTarget();
    }

    /* renamed from: com.android.server.trust.TrustManagerService$1, reason: invalid class name */
    class AnonymousClass1 extends android.app.trust.ITrustManager.Stub {
        AnonymousClass1() {
        }

        public void reportUnlockAttempt(boolean z, int i) throws android.os.RemoteException {
            enforceReportPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(3, z ? 1 : 0, i).sendToTarget();
        }

        public void reportUserRequestedUnlock(int i, boolean z) throws android.os.RemoteException {
            enforceReportPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(16, i, z ? 1 : 0).sendToTarget();
        }

        public void reportUserMayRequestUnlock(int i) throws android.os.RemoteException {
            enforceReportPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(18, i, 0).sendToTarget();
        }

        public void reportUnlockLockout(int i, int i2) throws android.os.RemoteException {
            enforceReportPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(13, i, i2).sendToTarget();
        }

        public void reportEnabledTrustAgentsChanged(int i) throws android.os.RemoteException {
            enforceReportPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(4, i, 0).sendToTarget();
        }

        public void reportKeyguardShowingChanged() throws android.os.RemoteException {
            enforceReportPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.removeMessages(6);
            com.android.server.trust.TrustManagerService.this.mHandler.sendEmptyMessage(6);
            com.android.server.trust.TrustManagerService.this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.trust.TrustManagerService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.trust.TrustManagerService.AnonymousClass1.lambda$reportKeyguardShowingChanged$0();
                }
            }, 0L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$reportKeyguardShowingChanged$0() {
        }

        public void registerTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException {
            enforceListenerPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(1, iTrustListener).sendToTarget();
        }

        public void unregisterTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException {
            enforceListenerPermission();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(2, iTrustListener).sendToTarget();
        }

        public boolean isDeviceLocked(int i, int i2) throws android.os.RemoteException {
            if (i2 != 0) {
                return false;
            }
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.app.trust.ITrustManager.Stub.getCallingPid(), android.app.trust.ITrustManager.Stub.getCallingUid(), i, false, true, "isDeviceLocked", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(handleIncomingUser)) {
                    handleIncomingUser = com.android.server.trust.TrustManagerService.this.resolveProfileParent(handleIncomingUser);
                }
                boolean isDeviceLockedInner = com.android.server.trust.TrustManagerService.this.isDeviceLockedInner(handleIncomingUser);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return isDeviceLockedInner;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean isDeviceSecure(int i, int i2) throws android.os.RemoteException {
            if (i2 != 0) {
                return false;
            }
            int handleIncomingUser = android.app.ActivityManager.handleIncomingUser(android.app.trust.ITrustManager.Stub.getCallingPid(), android.app.trust.ITrustManager.Stub.getCallingUid(), i, false, true, "isDeviceSecure", null);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(handleIncomingUser)) {
                    handleIncomingUser = com.android.server.trust.TrustManagerService.this.resolveProfileParent(handleIncomingUser);
                }
                boolean isSecure = com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isSecure(handleIncomingUser);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return isSecure;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        private void enforceReportPermission() {
            com.android.server.trust.TrustManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_KEYGUARD_SECURE_STORAGE", "reporting trust events");
        }

        private void enforceListenerPermission() {
            com.android.server.trust.TrustManagerService.this.mContext.enforceCallingOrSelfPermission("android.permission.TRUST_LISTENER", "register trust listener");
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, final java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.trust.TrustManagerService.this.mContext, com.android.server.trust.TrustManagerService.TAG, printWriter)) {
                if (com.android.server.trust.TrustManagerService.this.isSafeMode()) {
                    printWriter.println("disabled because the system is in safe mode.");
                } else if (!com.android.server.trust.TrustManagerService.this.mTrustAgentsCanRun) {
                    printWriter.println("disabled because the third-party apps can't run yet.");
                } else {
                    final java.util.List aliveUsers = com.android.server.trust.TrustManagerService.this.mUserManager.getAliveUsers();
                    com.android.server.trust.TrustManagerService.this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.trust.TrustManagerService.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            printWriter.println("Trust manager state:");
                            for (android.content.pm.UserInfo userInfo : aliveUsers) {
                                com.android.server.trust.TrustManagerService.AnonymousClass1.this.dumpUser(printWriter, userInfo, userInfo.id == com.android.server.trust.TrustManagerService.this.mCurrentUser);
                            }
                        }
                    }, 1500L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dumpUser(java.io.PrintWriter printWriter, android.content.pm.UserInfo userInfo, boolean z) {
            boolean isDeviceLockedInner;
            printWriter.printf(" User \"%s\" (id=%d, flags=%#x)", userInfo.name, java.lang.Integer.valueOf(userInfo.id), java.lang.Integer.valueOf(userInfo.flags));
            if (!userInfo.supportsSwitchToByUser()) {
                if (com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isProfileWithUnifiedChallenge(userInfo.id)) {
                    printWriter.print(" (profile with unified challenge)");
                    isDeviceLockedInner = com.android.server.trust.TrustManagerService.this.isDeviceLockedInner(com.android.server.trust.TrustManagerService.this.resolveProfileParent(userInfo.id));
                } else if (com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(userInfo.id)) {
                    printWriter.print(" (profile with separate challenge)");
                    isDeviceLockedInner = com.android.server.trust.TrustManagerService.this.isDeviceLockedInner(userInfo.id);
                } else {
                    printWriter.println(" (user that cannot be switched to)");
                    isDeviceLockedInner = com.android.server.trust.TrustManagerService.this.isDeviceLockedInner(userInfo.id);
                }
                printWriter.println(": deviceLocked=" + dumpBool(isDeviceLockedInner));
                printWriter.println("   Trust agents disabled because switching to this user is not possible.");
                return;
            }
            if (z) {
                printWriter.print(" (current)");
            }
            printWriter.print(": trustState=" + com.android.server.trust.TrustManagerService.this.getUserTrustStateInner(userInfo.id));
            printWriter.print(", trustManaged=" + dumpBool(com.android.server.trust.TrustManagerService.this.aggregateIsTrustManaged(userInfo.id)));
            printWriter.print(", deviceLocked=" + dumpBool(com.android.server.trust.TrustManagerService.this.isDeviceLockedInner(userInfo.id)));
            printWriter.print(", isActiveUnlockRunning=" + dumpBool(com.android.server.trust.TrustManagerService.this.aggregateIsActiveUnlockRunning(userInfo.id)));
            printWriter.print(", strongAuthRequired=" + dumpHex(com.android.server.trust.TrustManagerService.this.mStrongAuthTracker.getStrongAuthForUser(userInfo.id)));
            printWriter.println();
            printWriter.println("   Enabled agents:");
            android.util.ArraySet arraySet = new android.util.ArraySet();
            java.util.Iterator it = com.android.server.trust.TrustManagerService.this.mActiveAgents.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                com.android.server.trust.TrustManagerService.AgentInfo agentInfo = (com.android.server.trust.TrustManagerService.AgentInfo) it.next();
                if (agentInfo.userId == userInfo.id) {
                    boolean isTrusted = agentInfo.agent.isTrusted();
                    printWriter.print("    ");
                    printWriter.println(agentInfo.component.flattenToShortString());
                    printWriter.print("     bound=" + dumpBool(agentInfo.agent.isBound()));
                    printWriter.print(", connected=" + dumpBool(agentInfo.agent.isConnected()));
                    printWriter.print(", managingTrust=" + dumpBool(agentInfo.agent.isManagingTrust()));
                    printWriter.print(", trusted=" + dumpBool(isTrusted));
                    printWriter.println();
                    if (isTrusted) {
                        printWriter.println("      message=\"" + ((java.lang.Object) agentInfo.agent.getMessage()) + "\"");
                    }
                    if (!agentInfo.agent.isConnected()) {
                        printWriter.println("      restartScheduledAt=" + com.android.server.trust.TrustArchive.formatDuration(agentInfo.agent.getScheduledRestartUptimeMillis() - android.os.SystemClock.uptimeMillis()));
                    }
                    if (!arraySet.add(com.android.server.trust.TrustArchive.getSimpleName(agentInfo.component))) {
                        z2 = true;
                    }
                }
            }
            printWriter.println("   Events:");
            com.android.server.trust.TrustManagerService.this.mArchive.dump(printWriter, 50, userInfo.id, "    ", z2);
            printWriter.println();
        }

        private java.lang.String dumpBool(boolean z) {
            return z ? "1" : "0";
        }

        private java.lang.String dumpHex(int i) {
            return "0x" + java.lang.Integer.toHexString(i);
        }

        public void setDeviceLockedForUser(int i, boolean z) {
            enforceReportPermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i) && com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isSecure(i)) {
                    synchronized (com.android.server.trust.TrustManagerService.this.mDeviceLockedForUser) {
                        com.android.server.trust.TrustManagerService.this.mDeviceLockedForUser.put(i, z);
                    }
                    com.android.server.trust.TrustManagerService.this.notifyKeystoreOfDeviceLockState(i, z);
                    if (z) {
                        try {
                            android.app.ActivityManager.getService().notifyLockedProfile(i);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                    android.content.Intent intent = new android.content.Intent("android.intent.action.DEVICE_LOCKED_CHANGED");
                    intent.addFlags(1073741824);
                    intent.putExtra("android.intent.extra.user_handle", i);
                    com.android.server.trust.TrustManagerService.this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.SYSTEM, "android.permission.TRUST_LISTENER", null);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.EnforcePermission("android.permission.TRUST_LISTENER")
        public boolean isTrustUsuallyManaged(int i) {
            super.isTrustUsuallyManaged_enforcePermission();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.trust.TrustManagerService.this.isTrustUsuallyManagedInternal(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void unlockedByBiometricForUser(int i, android.hardware.biometrics.BiometricSourceType biometricSourceType) {
            enforceReportPermission();
            synchronized (com.android.server.trust.TrustManagerService.this.mUsersUnlockedByBiometric) {
                com.android.server.trust.TrustManagerService.this.mUsersUnlockedByBiometric.put(i, true);
            }
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(14, i, !com.android.server.trust.TrustManagerService.this.isAutomotive() ? 1 : 0).sendToTarget();
            com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(17, java.lang.Integer.valueOf(i)).sendToTarget();
        }

        public void clearAllBiometricRecognized(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i) {
            enforceReportPermission();
            synchronized (com.android.server.trust.TrustManagerService.this.mUsersUnlockedByBiometric) {
                com.android.server.trust.TrustManagerService.this.mUsersUnlockedByBiometric.clear();
            }
            android.os.Message obtainMessage = com.android.server.trust.TrustManagerService.this.mHandler.obtainMessage(14, -1, 0);
            if (i >= 0) {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putInt(com.android.server.trust.TrustManagerService.REFRESH_DEVICE_LOCKED_EXCEPT_USER, i);
                obtainMessage.setData(bundle);
            }
            obtainMessage.sendToTarget();
        }

        public boolean isActiveUnlockRunning(int i) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                return com.android.server.trust.TrustManagerService.this.aggregateIsActiveUnlockRunning(i);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$waitForIdle$1() {
    }

    @com.android.internal.annotations.VisibleForTesting
    void waitForIdle() {
        this.mHandler.runWithScissors(new java.lang.Runnable() { // from class: com.android.server.trust.TrustManagerService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.trust.TrustManagerService.lambda$waitForIdle$1();
            }
        }, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTrustUsuallyManagedInternal(int i) {
        synchronized (this.mTrustUsuallyManagedForUser) {
            try {
                int indexOfKey = this.mTrustUsuallyManagedForUser.indexOfKey(i);
                if (indexOfKey >= 0) {
                    return this.mTrustUsuallyManagedForUser.valueAt(indexOfKey);
                }
                boolean isTrustUsuallyManaged = this.mLockPatternUtils.isTrustUsuallyManaged(i);
                synchronized (this.mTrustUsuallyManagedForUser) {
                    try {
                        int indexOfKey2 = this.mTrustUsuallyManagedForUser.indexOfKey(i);
                        if (indexOfKey2 >= 0) {
                            return this.mTrustUsuallyManagedForUser.valueAt(indexOfKey2);
                        }
                        this.mTrustUsuallyManagedForUser.put(i, isTrustUsuallyManaged);
                        return isTrustUsuallyManaged;
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int resolveProfileParent(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.UserInfo profileParent = this.mUserManager.getProfileParent(i);
            return profileParent != null ? profileParent.getUserHandle().getIdentifier() : i;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private android.os.Handler createHandler(android.os.Looper looper) {
        return new android.os.Handler(looper) { // from class: com.android.server.trust.TrustManagerService.2
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                android.util.SparseBooleanArray clone;
                switch (message.what) {
                    case 1:
                        com.android.server.trust.TrustManagerService.this.addListener((android.app.trust.ITrustListener) message.obj);
                        return;
                    case 2:
                        com.android.server.trust.TrustManagerService.this.removeListener((android.app.trust.ITrustListener) message.obj);
                        return;
                    case 3:
                        com.android.server.trust.TrustManagerService.this.dispatchUnlockAttempt(message.arg1 != 0, message.arg2);
                        return;
                    case 4:
                        com.android.server.trust.TrustManagerService.this.refreshAgentList(-1);
                        com.android.server.trust.TrustManagerService.this.refreshDeviceLockedForUser(-1);
                        com.android.server.trust.TrustManagerService.this.dispatchOnEnabledTrustAgentsChanged(message.arg1);
                        return;
                    case 5:
                    default:
                        return;
                    case 6:
                        com.android.server.trust.TrustManagerService.this.dispatchTrustableDowngrade();
                        com.android.server.trust.TrustManagerService.this.refreshDeviceLockedForUser(com.android.server.trust.TrustManagerService.this.mCurrentUser);
                        return;
                    case 7:
                    case 8:
                    case 11:
                        com.android.server.trust.TrustManagerService.this.refreshAgentList(message.arg1);
                        return;
                    case 9:
                        com.android.server.trust.TrustManagerService.this.mCurrentUser = message.arg1;
                        com.android.server.trust.TrustManagerService.this.refreshDeviceLockedForUser(-1);
                        return;
                    case 10:
                        synchronized (com.android.server.trust.TrustManagerService.this.mTrustUsuallyManagedForUser) {
                            clone = com.android.server.trust.TrustManagerService.this.mTrustUsuallyManagedForUser.clone();
                        }
                        for (int i = 0; i < clone.size(); i++) {
                            int keyAt = clone.keyAt(i);
                            boolean valueAt = clone.valueAt(i);
                            if (valueAt != com.android.server.trust.TrustManagerService.this.mLockPatternUtils.isTrustUsuallyManaged(keyAt)) {
                                com.android.server.trust.TrustManagerService.this.mLockPatternUtils.setTrustUsuallyManaged(valueAt, keyAt);
                            }
                        }
                        return;
                    case 12:
                        com.android.server.trust.TrustManagerService.this.setDeviceLockedForUser(message.arg1, true);
                        return;
                    case 13:
                        com.android.server.trust.TrustManagerService.this.dispatchUnlockLockout(message.arg1, message.arg2);
                        return;
                    case 14:
                        if (message.arg2 == 1) {
                            com.android.server.trust.TrustManagerService.this.updateTrust(message.arg1, 0, true, null);
                        }
                        com.android.server.trust.TrustManagerService.this.refreshDeviceLockedForUser(message.arg1, message.getData().getInt(com.android.server.trust.TrustManagerService.REFRESH_DEVICE_LOCKED_EXCEPT_USER, com.android.server.am.ProcessList.INVALID_ADJ));
                        return;
                    case 15:
                        com.android.server.trust.TrustManagerService.this.handleScheduleTrustTimeout(message.arg1 == 1, message.arg2 == 1 ? com.android.server.trust.TrustManagerService.TimeoutType.TRUSTABLE : com.android.server.trust.TrustManagerService.TimeoutType.TRUSTED);
                        return;
                    case 16:
                        com.android.server.trust.TrustManagerService.this.dispatchUserRequestedUnlock(message.arg1, message.arg2 != 0);
                        return;
                    case 17:
                        com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener = (com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener) com.android.server.trust.TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(message.arg1);
                        if (trustableTimeoutAlarmListener != null && trustableTimeoutAlarmListener.isQueued()) {
                            com.android.server.trust.TrustManagerService.this.refreshTrustableTimers(message.arg1);
                            return;
                        }
                        return;
                    case 18:
                        com.android.server.trust.TrustManagerService.this.dispatchUserMayRequestUnlock(message.arg1);
                        return;
                }
            }
        };
    }

    private static class SettingsAttrs {
        public boolean canUnlockProfile;
        public android.content.ComponentName componentName;

        public SettingsAttrs(android.content.ComponentName componentName, boolean z) {
            this.componentName = componentName;
            this.canUnlockProfile = z;
        }
    }

    private class Receiver extends android.content.BroadcastReceiver {
        private Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            int userId;
            java.lang.String action = intent.getAction();
            if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                com.android.server.trust.TrustManagerService.this.refreshAgentList(getSendingUserId());
                com.android.server.trust.TrustManagerService.this.updateDevicePolicyFeatures();
                return;
            }
            if ("android.intent.action.USER_ADDED".equals(action) || "android.intent.action.USER_STARTED".equals(action)) {
                int userId2 = getUserId(intent);
                if (userId2 > 0) {
                    com.android.server.trust.TrustManagerService.this.maybeEnableFactoryTrustAgents(userId2);
                    return;
                }
                return;
            }
            if ("android.intent.action.USER_REMOVED".equals(action) && (userId = getUserId(intent)) > 0) {
                synchronized (com.android.server.trust.TrustManagerService.this.mDeviceLockedForUser) {
                    com.android.server.trust.TrustManagerService.this.mDeviceLockedForUser.delete(userId);
                }
                synchronized (com.android.server.trust.TrustManagerService.this.mTrustUsuallyManagedForUser) {
                    com.android.server.trust.TrustManagerService.this.mTrustUsuallyManagedForUser.delete(userId);
                }
                synchronized (com.android.server.trust.TrustManagerService.this.mUsersUnlockedByBiometric) {
                    com.android.server.trust.TrustManagerService.this.mUsersUnlockedByBiometric.delete(userId);
                }
                com.android.server.trust.TrustManagerService.this.refreshAgentList(userId);
                com.android.server.trust.TrustManagerService.this.refreshDeviceLockedForUser(userId);
            }
        }

        private int getUserId(android.content.Intent intent) {
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -100);
            if (intExtra > 0) {
                return intExtra;
            }
            android.util.Log.w(com.android.server.trust.TrustManagerService.TAG, "EXTRA_USER_HANDLE missing or invalid, value=" + intExtra);
            return -100;
        }

        public void register(android.content.Context context) {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
            intentFilter.addAction("android.intent.action.USER_ADDED");
            intentFilter.addAction("android.intent.action.USER_REMOVED");
            intentFilter.addAction("android.intent.action.USER_STARTED");
            context.registerReceiverAsUser(this, android.os.UserHandle.ALL, intentFilter, null, null);
        }
    }

    private class StrongAuthTracker extends com.android.internal.widget.LockPatternUtils.StrongAuthTracker {
        android.util.SparseBooleanArray mStartFromSuccessfulUnlock;

        StrongAuthTracker(android.content.Context context, android.os.Looper looper) {
            super(context, looper);
            this.mStartFromSuccessfulUnlock = new android.util.SparseBooleanArray();
        }

        public void onStrongAuthRequiredChanged(int i) {
            this.mStartFromSuccessfulUnlock.delete(i);
            if (com.android.server.trust.TrustManagerService.DEBUG) {
                android.util.Log.i(com.android.server.trust.TrustManagerService.TAG, "onStrongAuthRequiredChanged(" + i + ") -> trustAllowed=" + isTrustAllowedForUser(i) + " agentsCanRun=" + canAgentsRunForUser(i));
            }
            if (!isTrustAllowedForUser(i)) {
                cancelPendingAlarm((com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener) com.android.server.trust.TrustManagerService.this.mTrustTimeoutAlarmListenerForUser.get(java.lang.Integer.valueOf(i)));
                cancelPendingAlarm((com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener) com.android.server.trust.TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(i));
                cancelPendingAlarm((com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener) com.android.server.trust.TrustManagerService.this.mIdleTrustableTimeoutAlarmListenerForUser.get(i));
            }
            com.android.server.trust.TrustManagerService.this.refreshAgentList(i);
            com.android.server.trust.TrustManagerService.this.updateTrust(i, 0);
        }

        private void cancelPendingAlarm(@android.annotation.Nullable com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener trustTimeoutAlarmListener) {
            if (trustTimeoutAlarmListener != null && trustTimeoutAlarmListener.isQueued()) {
                trustTimeoutAlarmListener.setQueued(false);
                com.android.server.trust.TrustManagerService.this.mAlarmManager.cancel(trustTimeoutAlarmListener);
            }
        }

        boolean canAgentsRunForUser(int i) {
            return this.mStartFromSuccessfulUnlock.get(i) || super.isTrustAllowedForUser(i);
        }

        void allowTrustFromUnlock(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("userId must be a valid user: " + i);
            }
            boolean canAgentsRunForUser = canAgentsRunForUser(i);
            this.mStartFromSuccessfulUnlock.put(i, true);
            if (com.android.server.trust.TrustManagerService.DEBUG) {
                android.util.Log.i(com.android.server.trust.TrustManagerService.TAG, "allowTrustFromUnlock(" + i + ") -> trustAllowed=" + isTrustAllowedForUser(i) + " agentsCanRun=" + canAgentsRunForUser(i));
            }
            if (canAgentsRunForUser(i) != canAgentsRunForUser) {
                com.android.server.trust.TrustManagerService.this.refreshAgentList(i);
            }
        }
    }

    private abstract class TrustTimeoutAlarmListener implements android.app.AlarmManager.OnAlarmListener {
        protected boolean mIsQueued = false;
        protected final int mUserId;

        protected abstract void handleAlarm();

        TrustTimeoutAlarmListener(int i) {
            this.mUserId = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            this.mIsQueued = false;
            handleAlarm();
            if (com.android.server.trust.TrustManagerService.this.mStrongAuthTracker.isTrustAllowedForUser(this.mUserId)) {
                if (com.android.server.trust.TrustManagerService.DEBUG) {
                    android.util.Slog.d(com.android.server.trust.TrustManagerService.TAG, "Revoking all trust because of trust timeout");
                }
                com.android.internal.widget.LockPatternUtils lockPatternUtils = com.android.server.trust.TrustManagerService.this.mLockPatternUtils;
                com.android.server.trust.TrustManagerService.StrongAuthTracker unused = com.android.server.trust.TrustManagerService.this.mStrongAuthTracker;
                lockPatternUtils.requireStrongAuth(256, this.mUserId);
            }
        }

        public boolean isQueued() {
            return this.mIsQueued;
        }

        public void setQueued(boolean z) {
            this.mIsQueued = z;
        }
    }

    private class TrustedTimeoutAlarmListener extends com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener {
        TrustedTimeoutAlarmListener(int i) {
            super(i);
        }

        @Override // com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener
        public void handleAlarm() {
            com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener trustableTimeoutAlarmListener = (com.android.server.trust.TrustManagerService.TrustableTimeoutAlarmListener) com.android.server.trust.TrustManagerService.this.mTrustableTimeoutAlarmListenerForUser.get(this.mUserId);
            if (trustableTimeoutAlarmListener != null && trustableTimeoutAlarmListener.isQueued()) {
                synchronized (com.android.server.trust.TrustManagerService.this.mAlarmLock) {
                    disableNonrenewableTrustWhileRenewableTrustIsPresent();
                }
            }
        }

        private void disableNonrenewableTrustWhileRenewableTrustIsPresent() {
            synchronized (com.android.server.trust.TrustManagerService.this.mUserTrustState) {
                try {
                    if (com.android.server.trust.TrustManagerService.this.mUserTrustState.get(this.mUserId) == com.android.server.trust.TrustManagerService.TrustState.TRUSTED) {
                        com.android.server.trust.TrustManagerService.this.mUserTrustState.put(this.mUserId, com.android.server.trust.TrustManagerService.TrustState.TRUSTABLE);
                        com.android.server.trust.TrustManagerService.this.updateTrust(this.mUserId, 0);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class TrustableTimeoutAlarmListener extends com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener {
        TrustableTimeoutAlarmListener(int i) {
            super(i);
        }

        @Override // com.android.server.trust.TrustManagerService.TrustTimeoutAlarmListener
        public void handleAlarm() {
            com.android.server.trust.TrustManagerService.this.cancelBothTrustableAlarms(this.mUserId);
            com.android.server.trust.TrustManagerService.TrustedTimeoutAlarmListener trustedTimeoutAlarmListener = (com.android.server.trust.TrustManagerService.TrustedTimeoutAlarmListener) com.android.server.trust.TrustManagerService.this.mTrustTimeoutAlarmListenerForUser.get(java.lang.Integer.valueOf(this.mUserId));
            if (trustedTimeoutAlarmListener != null && trustedTimeoutAlarmListener.isQueued()) {
                synchronized (com.android.server.trust.TrustManagerService.this.mAlarmLock) {
                    disableRenewableTrustWhileNonrenewableTrustIsPresent();
                }
            }
        }

        private void disableRenewableTrustWhileNonrenewableTrustIsPresent() {
            java.util.Iterator it = com.android.server.trust.TrustManagerService.this.mActiveAgents.iterator();
            while (it.hasNext()) {
                ((com.android.server.trust.TrustManagerService.AgentInfo) it.next()).agent.setUntrustable();
            }
            com.android.server.trust.TrustManagerService.this.updateTrust(this.mUserId, 0);
        }
    }
}
