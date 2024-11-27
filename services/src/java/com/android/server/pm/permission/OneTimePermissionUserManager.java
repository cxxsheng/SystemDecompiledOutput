package com.android.server.pm.permission;

/* loaded from: classes2.dex */
public class OneTimePermissionUserManager {
    private static final boolean DEBUG = false;
    private static final long DEFAULT_KILLED_DELAY_MILLIS = 5000;
    private static final java.lang.String LOG_TAG = com.android.server.pm.permission.OneTimePermissionUserManager.class.getSimpleName();
    public static final java.lang.String PROPERTY_KILLED_DELAY_CONFIG_KEY = "one_time_permissions_killed_delay_millis";

    @android.annotation.NonNull
    private final android.app.AlarmManager mAlarmManager;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;

    @android.annotation.NonNull
    private final android.permission.PermissionControllerManager mPermissionControllerManager;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.content.BroadcastReceiver mUninstallListener = new android.content.BroadcastReceiver() { // from class: com.android.server.pm.permission.OneTimePermissionUserManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.UID_REMOVED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener packageInactivityListener = (com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener) com.android.server.pm.permission.OneTimePermissionUserManager.this.mListeners.get(intExtra);
                if (packageInactivityListener != null) {
                    packageInactivityListener.cancel();
                    com.android.server.pm.permission.OneTimePermissionUserManager.this.mListeners.remove(intExtra);
                }
            }
        }
    };

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener> mListeners = new android.util.SparseArray<>();

    @android.annotation.NonNull
    private final android.app.IActivityManager mIActivityManager = android.app.ActivityManager.getService();

    @android.annotation.NonNull
    private final android.app.ActivityManagerInternal mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);

    OneTimePermissionUserManager(@android.annotation.NonNull android.content.Context context) {
        this.mContext = context;
        this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(android.app.AlarmManager.class);
        this.mPermissionControllerManager = new android.permission.PermissionControllerManager(this.mContext, com.android.server.PermissionThread.getHandler());
        this.mHandler = context.getMainThreadHandler();
    }

    void startPackageOneTimeSession(@android.annotation.NonNull java.lang.String str, int i, long j, long j2) {
        try {
            int packageUid = this.mContext.getPackageManager().getPackageUid(str, 0);
            synchronized (this.mLock) {
                try {
                    com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener packageInactivityListener = this.mListeners.get(packageUid);
                    if (packageInactivityListener != null) {
                        packageInactivityListener.updateSessionParameters(j, j2);
                    } else {
                        this.mListeners.put(packageUid, new com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener(packageUid, str, i, j, j2));
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(LOG_TAG, "Unknown package name " + str + ", device ID " + i, e);
        }
    }

    void stopPackageOneTimeSession(@android.annotation.NonNull java.lang.String str) {
        try {
            int packageUid = this.mContext.getPackageManager().getPackageUid(str, 0);
            synchronized (this.mLock) {
                try {
                    com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener packageInactivityListener = this.mListeners.get(packageUid);
                    if (packageInactivityListener != null) {
                        this.mListeners.remove(packageUid);
                        packageInactivityListener.cancel();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(LOG_TAG, "Unknown package name " + str, e);
        }
    }

    void registerUninstallListener() {
        this.mContext.registerReceiver(this.mUninstallListener, new android.content.IntentFilter("android.intent.action.UID_REMOVED"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class PackageInactivityListener implements android.app.AlarmManager.OnAlarmListener {
        private static final int STATE_ACTIVE = 2;
        private static final int STATE_GONE = 0;
        private static final int STATE_TIMER = 1;
        private static final long TIMER_INACTIVE = -1;
        private final int mDeviceId;
        private final java.lang.Object mInnerLock;
        private boolean mIsAlarmSet;
        private boolean mIsFinished;
        private final android.app.IUidObserver mObserver;

        @android.annotation.NonNull
        private final java.lang.String mPackageName;
        private long mRevokeAfterKilledDelay;
        private long mTimeout;
        private long mTimerStart;
        private final java.lang.Object mToken;
        private final int mUid;

        private PackageInactivityListener(int i, @android.annotation.NonNull java.lang.String str, int i2, long j, long j2) {
            this.mTimerStart = -1L;
            this.mInnerLock = new java.lang.Object();
            this.mToken = new java.lang.Object();
            this.mObserver = new android.app.UidObserver() { // from class: com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.1
                public void onUidGone(int i3, boolean z) {
                    if (i3 == com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.this.mUid) {
                        com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.this.updateUidState(0);
                    }
                }

                public void onUidStateChanged(int i3, int i4, long j3, int i5) {
                    if (i3 == com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.this.mUid) {
                        if (i4 > 4 && i4 != 20) {
                            com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.this.updateUidState(1);
                        } else {
                            com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.this.updateUidState(2);
                        }
                    }
                }
            };
            android.util.Log.i(com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, "Start tracking " + str + ". uid=" + i + " timeout=" + j + " killedDelay=" + j2);
            this.mUid = i;
            this.mPackageName = str;
            this.mDeviceId = i2;
            this.mTimeout = j;
            this.mRevokeAfterKilledDelay = j2 == -1 ? android.provider.DeviceConfig.getLong("permissions", com.android.server.pm.permission.OneTimePermissionUserManager.PROPERTY_KILLED_DELAY_CONFIG_KEY, com.android.server.pm.permission.OneTimePermissionUserManager.DEFAULT_KILLED_DELAY_MILLIS) : j2;
            try {
                com.android.server.pm.permission.OneTimePermissionUserManager.this.mIActivityManager.registerUidObserver(this.mObserver, 3, 4, (java.lang.String) null);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, "Couldn't check uid proc state", e);
                synchronized (this.mInnerLock) {
                    onPackageInactiveLocked();
                }
            }
            updateUidState();
        }

        public void updateSessionParameters(long j, long j2) {
            synchronized (this.mInnerLock) {
                try {
                    this.mTimeout = java.lang.Math.min(this.mTimeout, j);
                    long j3 = this.mRevokeAfterKilledDelay;
                    if (j2 == -1) {
                        j2 = android.provider.DeviceConfig.getLong("permissions", com.android.server.pm.permission.OneTimePermissionUserManager.PROPERTY_KILLED_DELAY_CONFIG_KEY, com.android.server.pm.permission.OneTimePermissionUserManager.DEFAULT_KILLED_DELAY_MILLIS);
                    }
                    this.mRevokeAfterKilledDelay = java.lang.Math.min(j3, j2);
                    android.util.Log.v(com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, "Updated params for " + this.mPackageName + ", device ID " + this.mDeviceId + ". timeout=" + this.mTimeout + " killedDelay=" + this.mRevokeAfterKilledDelay);
                    updateUidState();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private int getCurrentState() {
            return getStateFromProcState(com.android.server.pm.permission.OneTimePermissionUserManager.this.mActivityManagerInternal.getUidProcessState(this.mUid));
        }

        private int getStateFromProcState(int i) {
            if (i == 20) {
                return 0;
            }
            if (i > 4) {
                return 1;
            }
            return 2;
        }

        private void updateUidState() {
            updateUidState(getCurrentState());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateUidState(int i) {
            android.util.Log.v(com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, "Updating state for " + this.mPackageName + " (" + this.mUid + "). device ID=" + this.mDeviceId + ", state=" + i);
            synchronized (this.mInnerLock) {
                try {
                    com.android.server.pm.permission.OneTimePermissionUserManager.this.mHandler.removeCallbacksAndMessages(this.mToken);
                    if (i == 0) {
                        if (this.mRevokeAfterKilledDelay == 0) {
                            onPackageInactiveLocked();
                            return;
                        } else {
                            com.android.server.pm.permission.OneTimePermissionUserManager.this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.pm.permission.OneTimePermissionUserManager$PackageInactivityListener$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.this.lambda$updateUidState$0();
                                }
                            }, this.mToken, this.mRevokeAfterKilledDelay);
                            return;
                        }
                    }
                    if (i == 1) {
                        if (this.mTimerStart == -1) {
                            this.mTimerStart = java.lang.System.currentTimeMillis();
                            setAlarmLocked();
                        }
                    } else if (i == 2) {
                        this.mTimerStart = -1L;
                        cancelAlarmLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateUidState$0() {
            synchronized (this.mInnerLock) {
                try {
                    int currentState = getCurrentState();
                    if (currentState == 0) {
                        onPackageInactiveLocked();
                    } else {
                        updateUidState(currentState);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void cancel() {
            synchronized (this.mInnerLock) {
                this.mIsFinished = true;
                cancelAlarmLocked();
                try {
                    com.android.server.pm.permission.OneTimePermissionUserManager.this.mIActivityManager.unregisterUidObserver(this.mObserver);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, "Unable to unregister uid observer.", e);
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mInnerLock"})
        private void setAlarmLocked() {
            if (this.mIsAlarmSet) {
                return;
            }
            long j = this.mTimerStart + this.mTimeout;
            if (j > java.lang.System.currentTimeMillis()) {
                com.android.server.pm.permission.OneTimePermissionUserManager.this.mAlarmManager.setExact(0, j, com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, this, com.android.server.pm.permission.OneTimePermissionUserManager.this.mHandler);
                this.mIsAlarmSet = true;
            } else {
                this.mIsAlarmSet = true;
                onAlarm();
            }
        }

        @com.android.internal.annotations.GuardedBy({"mInnerLock"})
        private void cancelAlarmLocked() {
            if (this.mIsAlarmSet) {
                com.android.server.pm.permission.OneTimePermissionUserManager.this.mAlarmManager.cancel(this);
                this.mIsAlarmSet = false;
            }
        }

        @com.android.internal.annotations.GuardedBy({"mInnerLock"})
        private void onPackageInactiveLocked() {
            if (this.mIsFinished) {
                return;
            }
            this.mIsFinished = true;
            cancelAlarmLocked();
            com.android.server.pm.permission.OneTimePermissionUserManager.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.permission.OneTimePermissionUserManager$PackageInactivityListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.this.lambda$onPackageInactiveLocked$1();
                }
            });
            try {
                com.android.server.pm.permission.OneTimePermissionUserManager.this.mIActivityManager.unregisterUidObserver(this.mObserver);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, "Unable to unregister uid observer.", e);
            }
            synchronized (com.android.server.pm.permission.OneTimePermissionUserManager.this.mLock) {
                com.android.server.pm.permission.OneTimePermissionUserManager.this.mListeners.remove(this.mUid);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPackageInactiveLocked$1() {
            android.util.Log.i(com.android.server.pm.permission.OneTimePermissionUserManager.LOG_TAG, "One time session expired for " + this.mPackageName + " (" + this.mUid + "). deviceID " + this.mDeviceId);
            com.android.server.pm.permission.OneTimePermissionUserManager.this.mPermissionControllerManager.notifyOneTimePermissionSessionTimeout(this.mPackageName, this.mDeviceId);
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            synchronized (this.mInnerLock) {
                try {
                    if (this.mIsAlarmSet) {
                        this.mIsAlarmSet = false;
                        onPackageInactiveLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }
}
