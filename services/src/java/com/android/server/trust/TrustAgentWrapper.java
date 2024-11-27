package com.android.server.trust;

@android.annotation.TargetApi(21)
/* loaded from: classes2.dex */
public class TrustAgentWrapper {
    private static final java.lang.String DATA_DURATION = "duration";
    private static final java.lang.String DATA_ESCROW_TOKEN = "escrow_token";
    private static final java.lang.String DATA_HANDLE = "handle";
    private static final java.lang.String DATA_MESSAGE = "message";
    private static final java.lang.String DATA_USER_ID = "user_id";
    private static final boolean DEBUG = com.android.server.trust.TrustManagerService.DEBUG;
    private static final java.lang.String EXTRA_COMPONENT_NAME = "componentName";
    private static final int MSG_ADD_ESCROW_TOKEN = 7;
    private static final int MSG_ESCROW_TOKEN_STATE = 9;
    private static final int MSG_GRANT_TRUST = 1;
    private static final int MSG_LOCK_USER = 12;
    private static final int MSG_MANAGING_TRUST = 6;
    private static final int MSG_REMOVE_ESCROW_TOKEN = 8;
    private static final int MSG_RESTART_TIMEOUT = 4;
    private static final int MSG_REVOKE_TRUST = 2;
    private static final int MSG_SET_TRUST_AGENT_FEATURES_COMPLETED = 5;
    private static final int MSG_SHOW_KEYGUARD_ERROR_MESSAGE = 11;
    private static final int MSG_TRUST_TIMEOUT = 3;
    private static final int MSG_UNLOCK_USER = 10;
    private static final java.lang.String PERMISSION = "android.permission.PROVIDE_TRUST_AGENT";
    private static final long RESTART_TIMEOUT_MILLIS = 300000;
    private static final java.lang.String TAG = "TrustAgentWrapper";
    private static final java.lang.String TRUST_EXPIRED_ACTION = "android.server.trust.TRUST_EXPIRED_ACTION";
    private final android.content.Intent mAlarmIntent;
    private android.app.AlarmManager mAlarmManager;
    private android.app.PendingIntent mAlarmPendingIntent;
    private boolean mBound;
    private final android.content.Context mContext;
    private boolean mDisplayTrustGrantedMessage;
    private boolean mManagingTrust;
    private long mMaximumTimeToLock;
    private java.lang.CharSequence mMessage;
    private final android.content.ComponentName mName;
    private long mScheduledRestartUptimeMillis;
    private android.os.IBinder mSetTrustAgentFeaturesToken;
    private android.service.trust.ITrustAgentService mTrustAgentService;
    private boolean mTrustDisabledByDpm;
    private final com.android.server.trust.TrustManagerService mTrustManagerService;
    private boolean mTrustable;
    private boolean mTrusted;
    private final int mUserId;
    private boolean mPendingSuccessfulUnlock = false;
    private boolean mWaitingForTrustableDowngrade = false;
    private boolean mWithinSecurityLockdownWindow = false;
    private final android.content.BroadcastReceiver mTrustableDowngradeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.trust.TrustAgentWrapper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                com.android.server.trust.TrustAgentWrapper.this.downgradeToTrustable();
            }
        }
    };
    private final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.trust.TrustAgentWrapper.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.content.ComponentName componentName = (android.content.ComponentName) intent.getParcelableExtra(com.android.server.trust.TrustAgentWrapper.EXTRA_COMPONENT_NAME, android.content.ComponentName.class);
            if (com.android.server.trust.TrustAgentWrapper.TRUST_EXPIRED_ACTION.equals(intent.getAction()) && com.android.server.trust.TrustAgentWrapper.this.mName.equals(componentName)) {
                com.android.server.trust.TrustAgentWrapper.this.mHandler.removeMessages(3);
                com.android.server.trust.TrustAgentWrapper.this.mHandler.sendEmptyMessage(3);
            }
        }
    };
    private final android.os.Handler mHandler = new com.android.server.trust.TrustAgentWrapper.AnonymousClass3();
    private android.service.trust.ITrustAgentServiceCallback mCallback = new android.service.trust.ITrustAgentServiceCallback.Stub() { // from class: com.android.server.trust.TrustAgentWrapper.4
        public void grantTrust(java.lang.CharSequence charSequence, long j, int i, com.android.internal.infra.AndroidFuture androidFuture) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "grantTrust(message=\"%s\", durationMs=%d, flags=0x%x)", charSequence, java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
            }
            android.os.Message obtainMessage = com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(1, i, 0, android.util.Pair.create(charSequence, androidFuture));
            obtainMessage.getData().putLong(com.android.server.trust.TrustAgentWrapper.DATA_DURATION, j);
            obtainMessage.sendToTarget();
        }

        public void revokeTrust() {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                android.util.Slog.d(com.android.server.trust.TrustAgentWrapper.TAG, "revokeTrust()");
            }
            com.android.server.trust.TrustAgentWrapper.this.mHandler.sendEmptyMessage(2);
        }

        public void lockUser() {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                android.util.Slog.d(com.android.server.trust.TrustAgentWrapper.TAG, "lockUser()");
            }
            com.android.server.trust.TrustAgentWrapper.this.mHandler.sendEmptyMessage(12);
        }

        public void setManagingTrust(boolean z) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "setManagingTrust(%s)", java.lang.Boolean.valueOf(z));
            }
            com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(6, z ? 1 : 0, 0).sendToTarget();
        }

        public void onConfigureCompleted(boolean z, android.os.IBinder iBinder) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "onConfigureCompleted(result=%s)", java.lang.Boolean.valueOf(z));
            }
            com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(5, z ? 1 : 0, 0, iBinder).sendToTarget();
        }

        public void addEscrowToken(byte[] bArr, int i) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "addEscrowToken(userId=%d)", java.lang.Integer.valueOf(i));
            }
            if (com.android.server.trust.TrustAgentWrapper.this.mContext.getResources().getBoolean(android.R.bool.config_allowEscrowTokenForTrustAgent)) {
                throw new java.lang.SecurityException("Escrow token API is not allowed.");
            }
            android.os.Message obtainMessage = com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(7);
            obtainMessage.getData().putByteArray(com.android.server.trust.TrustAgentWrapper.DATA_ESCROW_TOKEN, bArr);
            obtainMessage.getData().putInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID, i);
            obtainMessage.sendToTarget();
        }

        public void isEscrowTokenActive(long j, int i) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "isEscrowTokenActive(handle=%016x, userId=%d)", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
            }
            if (com.android.server.trust.TrustAgentWrapper.this.mContext.getResources().getBoolean(android.R.bool.config_allowEscrowTokenForTrustAgent)) {
                throw new java.lang.SecurityException("Escrow token API is not allowed.");
            }
            android.os.Message obtainMessage = com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(9);
            obtainMessage.getData().putLong(com.android.server.trust.TrustAgentWrapper.DATA_HANDLE, j);
            obtainMessage.getData().putInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID, i);
            obtainMessage.sendToTarget();
        }

        public void removeEscrowToken(long j, int i) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "removeEscrowToken(handle=%016x, userId=%d)", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
            }
            if (com.android.server.trust.TrustAgentWrapper.this.mContext.getResources().getBoolean(android.R.bool.config_allowEscrowTokenForTrustAgent)) {
                throw new java.lang.SecurityException("Escrow token API is not allowed.");
            }
            android.os.Message obtainMessage = com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(8);
            obtainMessage.getData().putLong(com.android.server.trust.TrustAgentWrapper.DATA_HANDLE, j);
            obtainMessage.getData().putInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID, i);
            obtainMessage.sendToTarget();
        }

        public void unlockUserWithToken(long j, byte[] bArr, int i) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "unlockUserWithToken(handle=%016x, userId=%d)", java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
            }
            if (com.android.server.trust.TrustAgentWrapper.this.mContext.getResources().getBoolean(android.R.bool.config_allowEscrowTokenForTrustAgent)) {
                throw new java.lang.SecurityException("Escrow token API is not allowed.");
            }
            android.os.Message obtainMessage = com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(10);
            obtainMessage.getData().putInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID, i);
            obtainMessage.getData().putLong(com.android.server.trust.TrustAgentWrapper.DATA_HANDLE, j);
            obtainMessage.getData().putByteArray(com.android.server.trust.TrustAgentWrapper.DATA_ESCROW_TOKEN, bArr);
            obtainMessage.sendToTarget();
        }

        public void showKeyguardErrorMessage(java.lang.CharSequence charSequence) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                com.android.server.utils.Slogf.d(com.android.server.trust.TrustAgentWrapper.TAG, "showKeyguardErrorMessage(\"%s\")", charSequence);
            }
            android.os.Message obtainMessage = com.android.server.trust.TrustAgentWrapper.this.mHandler.obtainMessage(11);
            obtainMessage.getData().putCharSequence(com.android.server.trust.TrustAgentWrapper.DATA_MESSAGE, charSequence);
            obtainMessage.sendToTarget();
        }
    };
    private final android.content.ServiceConnection mConnection = new android.content.ServiceConnection() { // from class: com.android.server.trust.TrustAgentWrapper.5
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                android.util.Slog.d(com.android.server.trust.TrustAgentWrapper.TAG, "TrustAgent started : " + componentName.flattenToString());
            }
            com.android.server.trust.TrustAgentWrapper.this.mHandler.removeMessages(4);
            com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService = android.service.trust.ITrustAgentService.Stub.asInterface(iBinder);
            com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.mArchive.logAgentConnected(com.android.server.trust.TrustAgentWrapper.this.mUserId, componentName);
            com.android.server.trust.TrustAgentWrapper.this.setCallback(com.android.server.trust.TrustAgentWrapper.this.mCallback);
            com.android.server.trust.TrustAgentWrapper.this.updateDevicePolicyFeatures();
            if (com.android.server.trust.TrustAgentWrapper.this.mPendingSuccessfulUnlock) {
                com.android.server.trust.TrustAgentWrapper.this.onUnlockAttempt(true);
                com.android.server.trust.TrustAgentWrapper.this.mPendingSuccessfulUnlock = false;
            }
            if (com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.isDeviceLockedInner(com.android.server.trust.TrustAgentWrapper.this.mUserId)) {
                com.android.server.trust.TrustAgentWrapper.this.onDeviceLocked();
            } else {
                com.android.server.trust.TrustAgentWrapper.this.onDeviceUnlocked();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                android.util.Slog.d(com.android.server.trust.TrustAgentWrapper.TAG, "TrustAgent disconnected : " + componentName.flattenToShortString());
            }
            com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService = null;
            com.android.server.trust.TrustAgentWrapper.this.mManagingTrust = false;
            com.android.server.trust.TrustAgentWrapper.this.mSetTrustAgentFeaturesToken = null;
            com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.mArchive.logAgentDied(com.android.server.trust.TrustAgentWrapper.this.mUserId, componentName);
            com.android.server.trust.TrustAgentWrapper.this.mHandler.sendEmptyMessage(2);
            if (com.android.server.trust.TrustAgentWrapper.this.mBound) {
                com.android.server.trust.TrustAgentWrapper.this.scheduleRestart();
            }
            if (com.android.server.trust.TrustAgentWrapper.this.mWithinSecurityLockdownWindow) {
                com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.lockUser(com.android.server.trust.TrustAgentWrapper.this.mUserId);
            }
        }
    };

    /* renamed from: com.android.server.trust.TrustAgentWrapper$3, reason: invalid class name */
    class AnonymousClass3 extends android.os.Handler {
        AnonymousClass3() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            long j;
            int i = 1;
            boolean z = true;
            boolean z2 = false;
            switch (message.what) {
                case 1:
                    if (!com.android.server.trust.TrustAgentWrapper.this.isConnected()) {
                        android.util.Log.w(com.android.server.trust.TrustAgentWrapper.TAG, "Agent is not connected, cannot grant trust: " + com.android.server.trust.TrustAgentWrapper.this.mName.flattenToShortString());
                        return;
                    }
                    com.android.server.trust.TrustAgentWrapper.this.mTrusted = true;
                    com.android.server.trust.TrustAgentWrapper.this.mTrustable = false;
                    android.util.Pair pair = (android.util.Pair) message.obj;
                    com.android.server.trust.TrustAgentWrapper.this.mMessage = (java.lang.CharSequence) pair.first;
                    com.android.internal.infra.AndroidFuture<android.service.trust.GrantTrustResult> androidFuture = (com.android.internal.infra.AndroidFuture) pair.second;
                    int i2 = message.arg1;
                    com.android.server.trust.TrustAgentWrapper.this.mDisplayTrustGrantedMessage = (i2 & 8) != 0;
                    if ((i2 & 4) != 0) {
                        com.android.server.trust.TrustAgentWrapper.this.mWaitingForTrustableDowngrade = true;
                        androidFuture.thenAccept(new java.util.function.Consumer() { // from class: com.android.server.trust.TrustAgentWrapper$3$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                com.android.server.trust.TrustAgentWrapper.AnonymousClass3.this.lambda$handleMessage$0((android.service.trust.GrantTrustResult) obj);
                            }
                        });
                    } else {
                        com.android.server.trust.TrustAgentWrapper.this.mWaitingForTrustableDowngrade = false;
                    }
                    long j2 = message.getData().getLong(com.android.server.trust.TrustAgentWrapper.DATA_DURATION);
                    if (j2 > 0) {
                        if (com.android.server.trust.TrustAgentWrapper.this.mMaximumTimeToLock != 0) {
                            j = java.lang.Math.min(j2, com.android.server.trust.TrustAgentWrapper.this.mMaximumTimeToLock);
                            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                                android.util.Slog.d(com.android.server.trust.TrustAgentWrapper.TAG, "DPM lock timeout in effect. Timeout adjusted from " + j2 + " to " + j);
                            }
                        } else {
                            j = j2;
                        }
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() + j;
                        com.android.server.trust.TrustAgentWrapper.this.mAlarmPendingIntent = android.app.PendingIntent.getBroadcast(com.android.server.trust.TrustAgentWrapper.this.mContext, 0, com.android.server.trust.TrustAgentWrapper.this.mAlarmIntent, android.hardware.audio.common.V2_0.AudioFormat.EVRCWB);
                        com.android.server.trust.TrustAgentWrapper.this.mAlarmManager.set(2, elapsedRealtime, com.android.server.trust.TrustAgentWrapper.this.mAlarmPendingIntent);
                    }
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.mArchive.logGrantTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, com.android.server.trust.TrustAgentWrapper.this.mName, com.android.server.trust.TrustAgentWrapper.this.mMessage != null ? com.android.server.trust.TrustAgentWrapper.this.mMessage.toString() : null, j2, i2);
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.updateTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, i2, androidFuture);
                    return;
                case 2:
                    break;
                case 3:
                    if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                        android.util.Slog.d(com.android.server.trust.TrustAgentWrapper.TAG, "Trust timed out : " + com.android.server.trust.TrustAgentWrapper.this.mName.flattenToShortString());
                    }
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.mArchive.logTrustTimeout(com.android.server.trust.TrustAgentWrapper.this.mUserId, com.android.server.trust.TrustAgentWrapper.this.mName);
                    com.android.server.trust.TrustAgentWrapper.this.onTrustTimeout();
                    break;
                case 4:
                    android.util.Slog.w(com.android.server.trust.TrustAgentWrapper.TAG, "Connection attempt to agent " + com.android.server.trust.TrustAgentWrapper.this.mName.flattenToShortString() + " timed out, rebinding");
                    com.android.server.trust.TrustAgentWrapper.this.destroy();
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.resetAgent(com.android.server.trust.TrustAgentWrapper.this.mName, com.android.server.trust.TrustAgentWrapper.this.mUserId);
                    return;
                case 5:
                    android.os.IBinder iBinder = (android.os.IBinder) message.obj;
                    int i3 = message.arg1 == 0 ? 0 : 1;
                    if (com.android.server.trust.TrustAgentWrapper.this.mSetTrustAgentFeaturesToken == iBinder) {
                        com.android.server.trust.TrustAgentWrapper.this.mSetTrustAgentFeaturesToken = null;
                        if (com.android.server.trust.TrustAgentWrapper.this.mTrustDisabledByDpm && i3 != 0) {
                            if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                                android.util.Slog.d(com.android.server.trust.TrustAgentWrapper.TAG, "Re-enabling agent because it acknowledged enabled features: " + com.android.server.trust.TrustAgentWrapper.this.mName.flattenToShortString());
                            }
                            com.android.server.trust.TrustAgentWrapper.this.mTrustDisabledByDpm = false;
                            com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.updateTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, 0);
                            return;
                        }
                        return;
                    }
                    if (com.android.server.trust.TrustAgentWrapper.DEBUG) {
                        android.util.Slog.w(com.android.server.trust.TrustAgentWrapper.TAG, "Ignoring MSG_SET_TRUST_AGENT_FEATURES_COMPLETED with obsolete token: " + com.android.server.trust.TrustAgentWrapper.this.mName.flattenToShortString());
                        return;
                    }
                    return;
                case 6:
                    com.android.server.trust.TrustAgentWrapper.this.mManagingTrust = message.arg1 != 0;
                    if (!com.android.server.trust.TrustAgentWrapper.this.mManagingTrust) {
                        com.android.server.trust.TrustAgentWrapper.this.mTrusted = false;
                        com.android.server.trust.TrustAgentWrapper.this.mDisplayTrustGrantedMessage = false;
                        com.android.server.trust.TrustAgentWrapper.this.mMessage = null;
                    }
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.mArchive.logManagingTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, com.android.server.trust.TrustAgentWrapper.this.mName, com.android.server.trust.TrustAgentWrapper.this.mManagingTrust);
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.updateTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, 0);
                    return;
                case 7:
                    byte[] byteArray = message.getData().getByteArray(com.android.server.trust.TrustAgentWrapper.DATA_ESCROW_TOKEN);
                    int i4 = message.getData().getInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID);
                    long addEscrowToken = com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.addEscrowToken(byteArray, i4);
                    try {
                        if (com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService == null) {
                            z = false;
                        } else {
                            com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService.onEscrowTokenAdded(byteArray, addEscrowToken, android.os.UserHandle.of(i4));
                        }
                        z2 = z;
                    } catch (android.os.RemoteException e) {
                        com.android.server.trust.TrustAgentWrapper.this.onError(e);
                    }
                    if (!z2) {
                        com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.removeEscrowToken(addEscrowToken, i4);
                        return;
                    }
                    return;
                case 8:
                    long j3 = message.getData().getLong(com.android.server.trust.TrustAgentWrapper.DATA_HANDLE);
                    boolean removeEscrowToken = com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.removeEscrowToken(j3, message.getData().getInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID));
                    try {
                        if (com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService != null) {
                            com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService.onEscrowTokenRemoved(j3, removeEscrowToken);
                            return;
                        }
                        return;
                    } catch (android.os.RemoteException e2) {
                        com.android.server.trust.TrustAgentWrapper.this.onError(e2);
                        return;
                    }
                case 9:
                    long j4 = message.getData().getLong(com.android.server.trust.TrustAgentWrapper.DATA_HANDLE);
                    boolean isEscrowTokenActive = com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.isEscrowTokenActive(j4, message.getData().getInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID));
                    try {
                        if (com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService != null) {
                            android.service.trust.ITrustAgentService iTrustAgentService = com.android.server.trust.TrustAgentWrapper.this.mTrustAgentService;
                            if (!isEscrowTokenActive) {
                                i = 0;
                            }
                            iTrustAgentService.onTokenStateReceived(j4, i);
                            return;
                        }
                        return;
                    } catch (android.os.RemoteException e3) {
                        com.android.server.trust.TrustAgentWrapper.this.onError(e3);
                        return;
                    }
                case 10:
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.unlockUserWithToken(message.getData().getLong(com.android.server.trust.TrustAgentWrapper.DATA_HANDLE), message.getData().getByteArray(com.android.server.trust.TrustAgentWrapper.DATA_ESCROW_TOKEN), message.getData().getInt(com.android.server.trust.TrustAgentWrapper.DATA_USER_ID));
                    return;
                case 11:
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.showKeyguardErrorMessage(message.getData().getCharSequence(com.android.server.trust.TrustAgentWrapper.DATA_MESSAGE));
                    return;
                case 12:
                    com.android.server.trust.TrustAgentWrapper.this.mTrusted = false;
                    com.android.server.trust.TrustAgentWrapper.this.mTrustable = false;
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.updateTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, 0);
                    com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.lockUser(com.android.server.trust.TrustAgentWrapper.this.mUserId);
                    return;
                default:
                    return;
            }
            com.android.server.trust.TrustAgentWrapper.this.mTrusted = false;
            com.android.server.trust.TrustAgentWrapper.this.mTrustable = false;
            com.android.server.trust.TrustAgentWrapper.this.mWaitingForTrustableDowngrade = false;
            com.android.server.trust.TrustAgentWrapper.this.mDisplayTrustGrantedMessage = false;
            com.android.server.trust.TrustAgentWrapper.this.mMessage = null;
            com.android.server.trust.TrustAgentWrapper.this.mHandler.removeMessages(3);
            if (message.what == 2) {
                com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.mArchive.logRevokeTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, com.android.server.trust.TrustAgentWrapper.this.mName);
            }
            com.android.server.trust.TrustAgentWrapper.this.mTrustManagerService.updateTrust(com.android.server.trust.TrustAgentWrapper.this.mUserId, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(android.service.trust.GrantTrustResult grantTrustResult) {
            if (grantTrustResult.getStatus() == 1) {
                com.android.server.trust.TrustAgentWrapper.this.setSecurityWindowTimer();
            }
        }
    }

    public TrustAgentWrapper(android.content.Context context, com.android.server.trust.TrustManagerService trustManagerService, android.content.Intent intent, android.os.UserHandle userHandle) {
        this.mContext = context;
        this.mTrustManagerService = trustManagerService;
        this.mAlarmManager = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        this.mUserId = userHandle.getIdentifier();
        this.mName = intent.getComponent();
        this.mAlarmIntent = new android.content.Intent(TRUST_EXPIRED_ACTION).putExtra(EXTRA_COMPONENT_NAME, this.mName);
        this.mAlarmIntent.setData(android.net.Uri.parse(this.mAlarmIntent.toUri(1)));
        this.mAlarmIntent.setPackage(context.getPackageName());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter(TRUST_EXPIRED_ACTION);
        intentFilter.addDataScheme(this.mAlarmIntent.getScheme());
        intentFilter.addDataPath(this.mAlarmIntent.toUri(1), 0);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter("android.intent.action.SCREEN_OFF");
        scheduleRestart();
        this.mBound = context.bindServiceAsUser(intent, this.mConnection, android.hardware.audio.common.V2_0.AudioFormat.AAC_MAIN, userHandle);
        if (this.mBound) {
            this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, PERMISSION, null, 2);
            this.mContext.registerReceiver(this.mTrustableDowngradeReceiver, intentFilter2);
        } else {
            android.util.Log.e(TAG, "Can't bind to TrustAgent " + this.mName.flattenToShortString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onError(java.lang.Exception exc) {
        android.util.Slog.w(TAG, "Exception ", exc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTrustTimeout() {
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.onTrustTimeout();
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    public void onUnlockAttempt(boolean z) {
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.onUnlockAttempt(z);
            } else {
                this.mPendingSuccessfulUnlock = z;
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    public void onUserRequestedUnlock(boolean z) {
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.onUserRequestedUnlock(z);
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    public void onUserMayRequestUnlock() {
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.onUserMayRequestUnlock();
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    public void onUnlockLockout(int i) {
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.onUnlockLockout(i);
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    public void onDeviceLocked() {
        this.mWithinSecurityLockdownWindow = false;
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.onDeviceLocked();
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    public void onDeviceUnlocked() {
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.onDeviceUnlocked();
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    public void onEscrowTokenActivated(long j, int i) {
        if (DEBUG) {
            android.util.Slog.d(TAG, "onEscrowTokenActivated: " + j + " user: " + i);
        }
        if (this.mTrustAgentService != null) {
            try {
                this.mTrustAgentService.onTokenStateReceived(j, 1);
            } catch (android.os.RemoteException e) {
                onError(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCallback(android.service.trust.ITrustAgentServiceCallback iTrustAgentServiceCallback) {
        try {
            if (this.mTrustAgentService != null) {
                this.mTrustAgentService.setCallback(iTrustAgentServiceCallback);
            }
        } catch (android.os.RemoteException e) {
            onError(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean updateDevicePolicyFeatures() {
        boolean z;
        if (DEBUG) {
            android.util.Slog.d(TAG, "updateDevicePolicyFeatures(" + this.mName + ")");
        }
        try {
            if (this.mTrustAgentService == null) {
                z = false;
            } else {
                android.app.admin.DevicePolicyManager devicePolicyManager = (android.app.admin.DevicePolicyManager) this.mContext.getSystemService("device_policy");
                if ((devicePolicyManager.getKeyguardDisabledFeatures(null, this.mUserId) & 16) != 0) {
                    java.util.List trustAgentConfiguration = devicePolicyManager.getTrustAgentConfiguration(null, this.mName, this.mUserId);
                    z = true;
                    try {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "Detected trust agents disabled. Config = " + trustAgentConfiguration);
                        }
                        if (trustAgentConfiguration != null && trustAgentConfiguration.size() > 0) {
                            if (DEBUG) {
                                android.util.Slog.d(TAG, "TrustAgent " + this.mName.flattenToShortString() + " disabled until it acknowledges " + trustAgentConfiguration);
                            }
                            this.mSetTrustAgentFeaturesToken = new android.os.Binder();
                            this.mTrustAgentService.onConfigure(trustAgentConfiguration, this.mSetTrustAgentFeaturesToken);
                        }
                    } catch (android.os.RemoteException e) {
                        e = e;
                        onError(e);
                        if (this.mTrustDisabledByDpm != z) {
                        }
                        return z;
                    }
                } else {
                    this.mTrustAgentService.onConfigure(java.util.Collections.EMPTY_LIST, (android.os.IBinder) null);
                    z = false;
                }
                long maximumTimeToLock = devicePolicyManager.getMaximumTimeToLock(null, this.mUserId);
                if (maximumTimeToLock != this.mMaximumTimeToLock) {
                    this.mMaximumTimeToLock = maximumTimeToLock;
                    if (this.mAlarmPendingIntent != null) {
                        this.mAlarmManager.cancel(this.mAlarmPendingIntent);
                        this.mAlarmPendingIntent = null;
                        this.mHandler.sendEmptyMessage(3);
                    }
                }
            }
        } catch (android.os.RemoteException e2) {
            e = e2;
            z = false;
        }
        if (this.mTrustDisabledByDpm != z) {
            this.mTrustDisabledByDpm = z;
            this.mTrustManagerService.updateTrust(this.mUserId, 0);
        }
        return z;
    }

    public boolean isTrusted() {
        return this.mTrusted && this.mManagingTrust && !this.mTrustDisabledByDpm;
    }

    public boolean isTrustable() {
        return this.mTrustable && this.mManagingTrust && !this.mTrustDisabledByDpm;
    }

    public boolean isTrustableOrWaitingForDowngrade() {
        return this.mWaitingForTrustableDowngrade || isTrustable();
    }

    public void setUntrustable() {
        this.mTrustable = false;
    }

    public void downgradeToTrustable() {
        if (this.mWaitingForTrustableDowngrade) {
            this.mWaitingForTrustableDowngrade = false;
            this.mTrusted = false;
            this.mTrustable = true;
            this.mTrustManagerService.updateTrust(this.mUserId, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSecurityWindowTimer() {
        this.mWithinSecurityLockdownWindow = true;
        this.mAlarmManager.setExact(2, android.os.SystemClock.elapsedRealtime() + 15000, TAG, new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.trust.TrustAgentWrapper.6
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                com.android.server.trust.TrustAgentWrapper.this.mWithinSecurityLockdownWindow = false;
            }
        }, android.os.Handler.getMain());
    }

    public boolean isManagingTrust() {
        return this.mManagingTrust && !this.mTrustDisabledByDpm;
    }

    public java.lang.CharSequence getMessage() {
        return this.mMessage;
    }

    public boolean shouldDisplayTrustGrantedMessage() {
        return this.mDisplayTrustGrantedMessage;
    }

    public void destroy() {
        this.mHandler.removeMessages(4);
        if (!this.mBound) {
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "TrustAgent unbound : " + this.mName.flattenToShortString());
        }
        this.mTrustManagerService.mArchive.logAgentStopped(this.mUserId, this.mName);
        this.mContext.unbindService(this.mConnection);
        this.mBound = false;
        this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        this.mContext.unregisterReceiver(this.mTrustableDowngradeReceiver);
        this.mTrustAgentService = null;
        this.mSetTrustAgentFeaturesToken = null;
        this.mHandler.sendEmptyMessage(2);
    }

    public boolean isConnected() {
        return this.mTrustAgentService != null;
    }

    public boolean isBound() {
        return this.mBound;
    }

    public long getScheduledRestartUptimeMillis() {
        return this.mScheduledRestartUptimeMillis;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleRestart() {
        this.mHandler.removeMessages(4);
        this.mScheduledRestartUptimeMillis = android.os.SystemClock.uptimeMillis() + 300000;
        this.mHandler.sendEmptyMessageAtTime(4, this.mScheduledRestartUptimeMillis);
    }
}
