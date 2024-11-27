package com.android.server.am;

/* loaded from: classes.dex */
class UserController implements android.os.Handler.Callback {
    static final int CLEAR_USER_JOURNEY_SESSION_MSG = 200;
    static final int COMPLETE_USER_SWITCH_MSG = 130;
    static final int CONTINUE_USER_SWITCH_MSG = 20;
    static final int DEFAULT_USER_SWITCH_TIMEOUT_MS = 3000;
    private static final int DISMISS_KEYGUARD_TIMEOUT_MS = 2000;
    static final int FOREGROUND_PROFILE_CHANGED_MSG = 70;
    private static final int LONG_USER_SWITCH_OBSERVER_WARNING_TIME_MS = 500;
    private static final int NO_ARG2 = 0;
    static final int REPORT_LOCKED_BOOT_COMPLETE_MSG = 110;
    static final int REPORT_USER_SWITCH_COMPLETE_MSG = 80;
    static final int REPORT_USER_SWITCH_MSG = 10;
    static final int SHOW_KEYGUARD_TIMEOUT_MS = 20000;
    static final int START_PROFILES_MSG = 40;
    static final int START_USER_SWITCH_FG_MSG = 120;
    static final int START_USER_SWITCH_UI_MSG = 1000;
    private static final java.lang.String TAG = "ActivityManager";
    private static final int USER_COMPLETED_EVENT_DELAY_MS = 5000;
    static final int USER_COMPLETED_EVENT_MSG = 140;
    static final int USER_CURRENT_MSG = 60;
    private static final int USER_JOURNEY_TIMEOUT_MS = 90000;
    static final int USER_START_MSG = 50;
    private static final int USER_SWITCH_CALLBACKS_TIMEOUT_MS = 5000;
    static final int USER_SWITCH_CALLBACKS_TIMEOUT_MSG = 90;
    static final int USER_SWITCH_TIMEOUT_MSG = 30;
    static final int USER_UNLOCKED_MSG = 105;
    static final int USER_UNLOCK_MSG = 100;
    private volatile boolean mAllowUserUnlocking;
    volatile boolean mBootCompleted;

    @com.android.internal.annotations.GuardedBy({"mCompletedEventTypes"})
    private final android.util.SparseIntArray mCompletedEventTypes;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private volatile android.util.ArraySet<java.lang.String> mCurWaitingUserSwitchCallbacks;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] mCurrentProfileIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private volatile int mCurrentUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mDelayUserDataLocking;
    private final android.os.Handler mHandler;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mInitialized;
    private final com.android.server.am.UserController.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsBroadcastSentForSystemUserStarted;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsBroadcastSentForSystemUserStarting;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<java.lang.Integer> mLastActiveUsersForDelayedLocking;
    private volatile long mLastUserUnlockingUptime;
    private final java.lang.Object mLock;
    private final com.android.internal.widget.LockPatternUtils mLockPatternUtils;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mMaxRunningUsers;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayDeque<java.lang.Integer> mPendingTargetUserIds;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<com.android.server.am.UserController.PendingUserStart> mPendingUserStarts;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] mStartedUserArray;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.am.UserState> mStartedUsers;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mStopUserOnSwitch;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mSwitchingFromSystemUserMessage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mSwitchingToSystemUserMessage;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private volatile int mTargetUserId;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArraySet<java.lang.String> mTimeoutUserSwitchCallbacks;
    private final android.os.Handler mUiHandler;
    private final com.android.server.pm.UserManagerInternal.UserLifecycleListener mUserLifecycleListener;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<java.lang.Integer> mUserLru;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mUserProfileGroupIds;
    private final android.os.RemoteCallbackList<android.app.IUserSwitchObserver> mUserSwitchObservers;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mUserSwitchUiEnabled;

    UserController(com.android.server.am.ActivityManagerService activityManagerService) {
        this(new com.android.server.am.UserController.Injector(activityManagerService));
    }

    @com.android.internal.annotations.VisibleForTesting
    UserController(com.android.server.am.UserController.Injector injector) {
        this.mLock = new java.lang.Object();
        this.mCurrentUserId = 0;
        this.mTargetUserId = com.android.server.am.ProcessList.INVALID_ADJ;
        this.mPendingTargetUserIds = new java.util.ArrayDeque<>();
        this.mStartedUsers = new android.util.SparseArray<>();
        this.mUserLru = new java.util.ArrayList<>();
        this.mStartedUserArray = new int[]{0};
        this.mCurrentProfileIds = new int[0];
        this.mUserProfileGroupIds = new android.util.SparseIntArray();
        this.mUserSwitchObservers = new android.os.RemoteCallbackList<>();
        this.mUserSwitchUiEnabled = true;
        this.mLastActiveUsersForDelayedLocking = new java.util.ArrayList<>();
        this.mCompletedEventTypes = new android.util.SparseIntArray();
        this.mStopUserOnSwitch = -1;
        this.mLastUserUnlockingUptime = 0L;
        this.mPendingUserStarts = new java.util.ArrayList();
        this.mUserLifecycleListener = new com.android.server.pm.UserManagerInternal.UserLifecycleListener() { // from class: com.android.server.am.UserController.1
            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public void onUserCreated(android.content.pm.UserInfo userInfo, java.lang.Object obj) {
                com.android.server.am.UserController.this.onUserAdded(userInfo);
            }

            @Override // com.android.server.pm.UserManagerInternal.UserLifecycleListener
            public void onUserRemoved(android.content.pm.UserInfo userInfo) {
                com.android.server.am.UserController.this.onUserRemoved(userInfo.id);
            }
        };
        this.mInjector = injector;
        this.mHandler = this.mInjector.getHandler(this);
        this.mUiHandler = this.mInjector.getUiHandler(this);
        com.android.server.am.UserState userState = new com.android.server.am.UserState(android.os.UserHandle.SYSTEM);
        userState.mUnlockProgress.addListener(new com.android.server.am.UserController.UserProgressListener());
        this.mStartedUsers.put(0, userState);
        this.mUserLru.add(0);
        this.mLockPatternUtils = this.mInjector.getLockPatternUtils();
        updateStartedUserArrayLU();
    }

    void setInitialConfig(boolean z, int i, boolean z2) {
        synchronized (this.mLock) {
            this.mUserSwitchUiEnabled = z;
            this.mMaxRunningUsers = i;
            this.mDelayUserDataLocking = z2;
            this.mInitialized = true;
        }
    }

    private boolean isUserSwitchUiEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mUserSwitchUiEnabled;
        }
        return z;
    }

    int getMaxRunningUsers() {
        int i;
        synchronized (this.mLock) {
            i = this.mMaxRunningUsers;
        }
        return i;
    }

    void setStopUserOnSwitch(int i) {
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") == -1 && this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") == -1) {
            throw new java.lang.SecurityException("You either need MANAGE_USERS or INTERACT_ACROSS_USERS permission to call setStopUserOnSwitch()");
        }
        synchronized (this.mLock) {
            com.android.server.utils.Slogf.i(TAG, "setStopUserOnSwitch(): %d -> %d", java.lang.Integer.valueOf(this.mStopUserOnSwitch), java.lang.Integer.valueOf(i));
            this.mStopUserOnSwitch = i;
        }
    }

    private boolean shouldStopUserOnSwitch() {
        synchronized (this.mLock) {
            try {
                if (this.mStopUserOnSwitch != -1) {
                    boolean z = this.mStopUserOnSwitch == 1;
                    com.android.server.utils.Slogf.i(TAG, "shouldStopUserOnSwitch(): returning overridden value (%b)", java.lang.Boolean.valueOf(z));
                    return z;
                }
                int i = android.os.SystemProperties.getInt("fw.stop_bg_users_on_switch", -1);
                return i == -1 ? this.mDelayUserDataLocking : i == 1;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void finishUserSwitch(final com.android.server.am.UserState userState) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$finishUserSwitch$0(userState);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserSwitch$0(com.android.server.am.UserState userState) {
        finishUserBoot(userState);
        startProfiles();
        stopExcessRunningUsers();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    java.util.List<java.lang.Integer> getRunningUsersLU() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.lang.Integer> it = this.mUserLru.iterator();
        while (it.hasNext()) {
            java.lang.Integer next = it.next();
            com.android.server.am.UserState userState = this.mStartedUsers.get(next.intValue());
            if (userState != null && userState.state != 4 && userState.state != 5) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    private void stopExcessRunningUsers() {
        android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
        java.util.List<android.content.pm.UserInfo> users = this.mInjector.getUserManager().getUsers(true);
        for (int i = 0; i < users.size(); i++) {
            int i2 = users.get(i).id;
            if (isAlwaysVisibleUser(i2)) {
                arraySet.add(java.lang.Integer.valueOf(i2));
            }
        }
        synchronized (this.mLock) {
            stopExcessRunningUsersLU(this.mMaxRunningUsers, arraySet);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopExcessRunningUsersLU(int i, android.util.ArraySet<java.lang.Integer> arraySet) {
        java.util.List<java.lang.Integer> runningUsersLU = getRunningUsersLU();
        java.util.Iterator<java.lang.Integer> it = runningUsersLU.iterator();
        while (runningUsersLU.size() > i && it.hasNext()) {
            java.lang.Integer next = it.next();
            if (next.intValue() != 0 && next.intValue() != this.mCurrentUserId && !arraySet.contains(next) && stopUsersLU(next.intValue(), false, true, null, null) == 0) {
                it.remove();
            }
        }
    }

    boolean canStartMoreUsers() {
        boolean z;
        synchronized (this.mLock) {
            z = getRunningUsersLU().size() < this.mMaxRunningUsers;
        }
        return z;
    }

    private void finishUserBoot(com.android.server.am.UserState userState) {
        finishUserBoot(userState, null);
    }

    private void finishUserBoot(com.android.server.am.UserState userState, android.content.IIntentReceiver iIntentReceiver) {
        int identifier = userState.mHandle.getIdentifier();
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_FINISH_USER_BOOT, identifier);
        synchronized (this.mLock) {
            try {
                if (this.mStartedUsers.get(identifier) != userState) {
                    return;
                }
                if (userState.setState(0, 1)) {
                    this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(identifier, 4, 0);
                    this.mInjector.getUserManagerInternal().setUserState(identifier, userState.state);
                    if (identifier == 0 && !this.mInjector.isRuntimeRestarted() && !this.mInjector.isFirstBootOrUpgrade()) {
                        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED, 12, elapsedRealtime);
                        if (elapsedRealtime > 120000) {
                            com.android.server.utils.Slogf.wtf(com.android.server.utils.TimingsTraceAndSlog.SYSTEM_SERVER_TIMING_TAG, "finishUserBoot took too long. elapsedTimeMs=" + elapsedRealtime);
                        }
                    }
                    if (!this.mInjector.getUserManager().isPreCreated(identifier)) {
                        this.mHandler.sendMessage(this.mHandler.obtainMessage(110, identifier, 0));
                        if (this.mAllowUserUnlocking) {
                            sendLockedBootCompletedBroadcast(iIntentReceiver, identifier);
                        }
                    }
                }
                android.content.pm.UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(identifier);
                if (profileParent == null) {
                    maybeUnlockUser(identifier);
                    return;
                }
                if (isUserRunning(profileParent.id, 4)) {
                    com.android.server.utils.Slogf.d(TAG, "User " + identifier + " (parent " + profileParent.id + "): attempting unlock because parent is unlocked");
                    maybeUnlockUser(identifier);
                    return;
                }
                com.android.server.utils.Slogf.d(TAG, "User " + identifier + " (parent " + profileParent.id + "): delaying unlock because parent is locked");
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void sendLockedBootCompletedBroadcast(android.content.IIntentReceiver iIntentReceiver, int i) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.LOCKED_BOOT_COMPLETED", (android.net.Uri) null);
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.addFlags(-1996488704);
        this.mInjector.broadcastIntent(intent, null, iIntentReceiver, 0, null, null, new java.lang.String[]{"android.permission.RECEIVE_BOOT_COMPLETED"}, -1, getTemporaryAppAllowlistBroadcastOptions(202).toBundle(), true, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), i);
    }

    private boolean finishUserUnlocking(final com.android.server.am.UserState userState) {
        final int identifier = userState.mHandle.getIdentifier();
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_FINISH_USER_UNLOCKING, identifier);
        this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(identifier, 5, 1);
        if (!android.os.storage.StorageManager.isCeStorageUnlocked(identifier)) {
            return false;
        }
        synchronized (this.mLock) {
            if (this.mStartedUsers.get(identifier) != userState || userState.state != 1) {
                return false;
            }
            userState.mUnlockProgress.start();
            userState.mUnlockProgress.setProgress(5, this.mInjector.getContext().getString(android.R.string.alternate_eri_file));
            com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.UserController.this.lambda$finishUserUnlocking$1(identifier, userState);
                }
            });
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserUnlocking$1(int i, com.android.server.am.UserState userState) {
        if (!android.os.storage.StorageManager.isCeStorageUnlocked(i)) {
            com.android.server.utils.Slogf.w(TAG, "User's CE storage got locked unexpectedly, leaving user locked.");
            return;
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("UM.onBeforeUnlockUser-" + i);
        this.mInjector.getUserManager().onBeforeUnlockUser(i);
        timingsTraceAndSlog.traceEnd();
        synchronized (this.mLock) {
            try {
                if (userState.setState(1, 2)) {
                    this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                    userState.mUnlockProgress.setProgress(20);
                    this.mLastUserUnlockingUptime = android.os.SystemClock.uptimeMillis();
                    this.mHandler.obtainMessage(100, i, 0, userState).sendToTarget();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void finishUserUnlocked(final com.android.server.am.UserState userState) {
        android.content.pm.UserInfo profileParent;
        int identifier = userState.mHandle.getIdentifier();
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_FINISH_USER_UNLOCKED, identifier);
        if (android.os.storage.StorageManager.isCeStorageUnlocked(identifier)) {
            synchronized (this.mLock) {
                try {
                    if (this.mStartedUsers.get(userState.mHandle.getIdentifier()) != userState) {
                        return;
                    }
                    if (userState.setState(2, 3)) {
                        this.mInjector.getUserManagerInternal().setUserState(identifier, userState.state);
                        userState.mUnlockProgress.finish();
                        if (identifier == 0) {
                            this.mInjector.startPersistentApps(262144);
                        }
                        this.mInjector.installEncryptionUnawareProviders(identifier);
                        if (!this.mInjector.getUserManager().isPreCreated(identifier)) {
                            android.content.Intent intent = new android.content.Intent("android.intent.action.USER_UNLOCKED");
                            intent.putExtra("android.intent.extra.user_handle", identifier);
                            intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
                            this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), identifier);
                        }
                        android.content.pm.UserInfo userInfo = getUserInfo(identifier);
                        if (userInfo.isProfile() && (profileParent = this.mInjector.getUserManager().getProfileParent(identifier)) != null) {
                            broadcastProfileAccessibleStateChanged(identifier, profileParent.id, "android.intent.action.PROFILE_ACCESSIBLE");
                            if (userInfo.isManagedProfile()) {
                                android.content.Intent intent2 = new android.content.Intent("android.intent.action.MANAGED_PROFILE_UNLOCKED");
                                intent2.putExtra("android.intent.extra.USER", android.os.UserHandle.of(identifier));
                                intent2.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
                                this.mInjector.broadcastIntent(intent2, null, null, 0, null, null, null, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), profileParent.id);
                            }
                        }
                        android.content.pm.UserInfo userInfo2 = getUserInfo(identifier);
                        if (!java.util.Objects.equals(userInfo2.lastLoggedInFingerprint, android.content.pm.PackagePartitions.FINGERPRINT) || android.os.SystemProperties.getBoolean("persist.pm.mock-upgrade", false)) {
                            this.mInjector.sendPreBootBroadcast(identifier, userInfo2.isManagedProfile(), new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda22
                                @Override // java.lang.Runnable
                                public final void run() {
                                    com.android.server.am.UserController.this.lambda$finishUserUnlocked$2(userState);
                                }
                            });
                        } else {
                            lambda$finishUserUnlocked$2(userState);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: finishUserUnlockedCompleted, reason: merged with bridge method [inline-methods] */
    public void lambda$finishUserUnlocked$2(com.android.server.am.UserState userState) {
        final int identifier = userState.mHandle.getIdentifier();
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_FINISH_USER_UNLOCKED_COMPLETED, identifier);
        synchronized (this.mLock) {
            try {
                if (this.mStartedUsers.get(userState.mHandle.getIdentifier()) != userState) {
                    return;
                }
                final android.content.pm.UserInfo userInfo = getUserInfo(identifier);
                if (userInfo != null && android.os.storage.StorageManager.isCeStorageUnlocked(identifier)) {
                    this.mInjector.getUserManager().onUserLoggedIn(identifier);
                    final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda9
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.UserController.this.lambda$finishUserUnlockedCompleted$3(userInfo);
                        }
                    };
                    if (!userInfo.isInitialized()) {
                        com.android.server.utils.Slogf.d(TAG, "Initializing user #" + identifier);
                        if (userInfo.preCreated) {
                            runnable.run();
                        } else if (identifier != 0) {
                            android.content.Intent intent = new android.content.Intent("android.intent.action.USER_INITIALIZE");
                            intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.EVRCB);
                            this.mInjector.broadcastIntent(intent, null, new android.content.IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.2
                                public void performReceive(android.content.Intent intent2, int i, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i2) {
                                    runnable.run();
                                }
                            }, 0, null, null, null, -1, null, true, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), identifier);
                        }
                    }
                    if (userInfo.preCreated) {
                        com.android.server.utils.Slogf.i(TAG, "Stopping pre-created user " + userInfo.toFullString());
                        stopUser(userInfo.id, true, false, null, null);
                        return;
                    }
                    this.mInjector.startUserWidgets(identifier);
                    this.mHandler.obtainMessage(105, identifier, 0).sendToTarget();
                    com.android.server.utils.Slogf.i(TAG, "Posting BOOT_COMPLETED user #" + identifier);
                    if (identifier == 0 && !this.mInjector.isRuntimeRestarted() && !this.mInjector.isFirstBootOrUpgrade()) {
                        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED, 13, android.os.SystemClock.elapsedRealtime());
                    }
                    final android.content.Intent intent2 = new android.content.Intent("android.intent.action.BOOT_COMPLETED", (android.net.Uri) null);
                    intent2.putExtra("android.intent.extra.user_handle", identifier);
                    intent2.addFlags(-1996488704);
                    final int callingUid = android.os.Binder.getCallingUid();
                    final int callingPid = android.os.Binder.getCallingPid();
                    com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda10
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.am.UserController.this.lambda$finishUserUnlockedCompleted$4(intent2, identifier, callingUid, callingPid);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserUnlockedCompleted$3(android.content.pm.UserInfo userInfo) {
        this.mInjector.getUserManager().makeInitialized(userInfo.id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserUnlockedCompleted$4(android.content.Intent intent, final int i, int i2, int i3) {
        this.mInjector.broadcastIntent(intent, null, new android.content.IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.3
            public void performReceive(android.content.Intent intent2, int i4, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i5) throws android.os.RemoteException {
                com.android.server.utils.Slogf.i(com.android.server.am.UserController.TAG, "Finished processing BOOT_COMPLETED for u" + i);
                com.android.server.am.UserController.this.mBootCompleted = true;
            }
        }, 0, null, null, new java.lang.String[]{"android.permission.RECEIVE_BOOT_COMPLETED"}, -1, getTemporaryAppAllowlistBroadcastOptions(200).toBundle(), true, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, i2, i3, i);
    }

    /* renamed from: com.android.server.am.UserController$4, reason: invalid class name */
    class AnonymousClass4 implements com.android.server.am.UserState.KeyEvictedCallback {
        final /* synthetic */ int val$userStartMode;

        AnonymousClass4(int i) {
            this.val$userStartMode = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$keyEvicted$0(int i, int i2) {
            com.android.server.am.UserController.this.startUser(i, i2);
        }

        @Override // com.android.server.am.UserState.KeyEvictedCallback
        public void keyEvicted(final int i) {
            android.os.Handler handler = com.android.server.am.UserController.this.mHandler;
            final int i2 = this.val$userStartMode;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.UserController.AnonymousClass4.this.lambda$keyEvicted$0(i, i2);
                }
            });
        }
    }

    int restartUser(int i, @com.android.server.pm.UserManagerInternal.UserStartMode int i2) {
        return stopUser(i, true, false, null, new com.android.server.am.UserController.AnonymousClass4(i2));
    }

    boolean stopProfile(int i) {
        boolean z;
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") == -1 && this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == -1) {
            throw new java.lang.SecurityException("You either need MANAGE_USERS or INTERACT_ACROSS_USERS_FULL permission to stop a profile");
        }
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        if (userInfo == null || !userInfo.isProfile()) {
            throw new java.lang.IllegalArgumentException("User " + i + " is not a profile");
        }
        enforceShellRestriction("no_debugging_features", i);
        synchronized (this.mLock) {
            z = stopUsersLU(i, true, false, null, null) == 0;
        }
        return z;
    }

    int stopUser(int i, boolean z, boolean z2, android.app.IStopUserCallback iStopUserCallback, com.android.server.am.UserState.KeyEvictedCallback keyEvictedCallback) {
        int stopUsersLU;
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("UserController");
        sb.append(z ? "-force" : "");
        sb.append(z2 ? "-allowDelayedLocking" : "");
        sb.append(iStopUserCallback != null ? "-withStopUserCallback" : "");
        sb.append("-");
        sb.append(i);
        sb.append("-[stopUser]");
        timingsTraceAndSlog.traceBegin(sb.toString());
        try {
            checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "stopUser");
            com.android.internal.util.Preconditions.checkArgument(i >= 0, "Invalid user id %d", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
            enforceShellRestriction("no_debugging_features", i);
            synchronized (this.mLock) {
                stopUsersLU = stopUsersLU(i, z, z2, iStopUserCallback, keyEvictedCallback);
            }
            return stopUsersLU;
        } finally {
            timingsTraceAndSlog.traceEnd();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int stopUsersLU(int i, boolean z, boolean z2, android.app.IStopUserCallback iStopUserCallback, com.android.server.am.UserState.KeyEvictedCallback keyEvictedCallback) {
        if (i == 0) {
            return -3;
        }
        if (isCurrentUserLU(i)) {
            return -2;
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        int[] usersToStopLU = getUsersToStopLU(i);
        for (int i2 : usersToStopLU) {
            if (i2 == 0 || isCurrentUserLU(i2)) {
                if (z) {
                    com.android.server.utils.Slogf.i(TAG, "Force stop user " + i + ". Related users will not be stopped");
                    timingsTraceAndSlog.traceBegin("stopSingleUserLU-force-" + i + "-[stopUser]");
                    stopSingleUserLU(i, z2, iStopUserCallback, keyEvictedCallback);
                    timingsTraceAndSlog.traceEnd();
                    return 0;
                }
                return -4;
            }
        }
        int length = usersToStopLU.length;
        for (int i3 = 0; i3 < length; i3++) {
            int i4 = usersToStopLU[i3];
            timingsTraceAndSlog.traceBegin("stopSingleUserLU-" + i4 + "-[stopUser]");
            com.android.server.am.UserState.KeyEvictedCallback keyEvictedCallback2 = null;
            android.app.IStopUserCallback iStopUserCallback2 = i4 == i ? iStopUserCallback : null;
            if (i4 == i) {
                keyEvictedCallback2 = keyEvictedCallback;
            }
            stopSingleUserLU(i4, z2, iStopUserCallback2, keyEvictedCallback2);
            timingsTraceAndSlog.traceEnd();
        }
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void stopSingleUserLU(final int i, final boolean z, final android.app.IStopUserCallback iStopUserCallback, com.android.server.am.UserState.KeyEvictedCallback keyEvictedCallback) {
        java.util.ArrayList arrayList;
        com.android.server.utils.Slogf.i(TAG, "stopSingleUserLU userId=" + i);
        final com.android.server.am.UserState userState = this.mStartedUsers.get(i);
        if (userState == null) {
            if (canDelayDataLockingForUser(i)) {
                if (z && keyEvictedCallback != null) {
                    com.android.server.utils.Slogf.wtf(TAG, "allowDelayedLocking set with KeyEvictedCallback, ignore it and lock user:" + i, new java.lang.RuntimeException());
                    z = false;
                }
                if (!z && this.mLastActiveUsersForDelayedLocking.remove(java.lang.Integer.valueOf(i))) {
                    if (keyEvictedCallback != null) {
                        arrayList = new java.util.ArrayList(1);
                        arrayList.add(keyEvictedCallback);
                    } else {
                        arrayList = null;
                    }
                    dispatchUserLocking(i, arrayList);
                }
            }
            if (iStopUserCallback != null) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.UserController.lambda$stopSingleUserLU$5(iStopUserCallback, i);
                    }
                });
                return;
            }
            return;
        }
        logUserJourneyBegin(i, 5);
        if (iStopUserCallback != null) {
            userState.mStopCallbacks.add(iStopUserCallback);
        }
        if (keyEvictedCallback != null) {
            userState.mKeyEvictedCallbacks.add(keyEvictedCallback);
        }
        if (userState.state != 4 && userState.state != 5) {
            userState.setState(4);
            com.android.server.pm.UserManagerInternal userManagerInternal = this.mInjector.getUserManagerInternal();
            com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
            timingsTraceAndSlog.traceBegin("setUserState-STATE_STOPPING-" + i + "-[stopUser]");
            userManagerInternal.setUserState(i, userState.state);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("unassignUserFromDisplayOnStop-" + i + "-[stopUser]");
            userManagerInternal.unassignUserFromDisplayOnStop(i);
            timingsTraceAndSlog.traceEnd();
            updateStartedUserArrayLU();
            final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.UserController.this.lambda$stopSingleUserLU$7(i, userState, z);
                }
            };
            if (this.mInjector.getUserManager().isPreCreated(i)) {
                runnable.run();
            } else {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.UserController.this.lambda$stopSingleUserLU$8(i, runnable);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$stopSingleUserLU$5(android.app.IStopUserCallback iStopUserCallback, int i) {
        try {
            iStopUserCallback.userStopped(i);
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopSingleUserLU$7(final int i, final com.android.server.am.UserState userState, final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$stopSingleUserLU$6(i, userState, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopSingleUserLU$6(int i, com.android.server.am.UserState userState, boolean z) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("finishUserStopping-" + i + "-[stopUser]");
        finishUserStopping(i, userState, z);
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$stopSingleUserLU$8(final int i, final java.lang.Runnable runnable) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.USER_STOPPING");
        intent.addFlags(1073741824);
        intent.putExtra("android.intent.extra.user_handle", i);
        intent.putExtra("android.intent.extra.SHUTDOWN_USERSPACE_ONLY", true);
        android.content.IIntentReceiver iIntentReceiver = new android.content.IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.5
            public void performReceive(android.content.Intent intent2, int i2, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i3) {
                com.android.server.am.UserController.asyncTraceEnd("broadcast-ACTION_USER_STOPPING-" + i + "-[stopUser]", i);
                runnable.run();
            }
        };
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("clearBroadcastQueueForUser-" + i + "-[stopUser]");
        this.mInjector.clearBroadcastQueueForUser(i);
        timingsTraceAndSlog.traceEnd();
        asyncTraceBegin("broadcast-ACTION_USER_STOPPING-" + i + "-[stopUser]", i);
        this.mInjector.broadcastIntent(intent, null, iIntentReceiver, 0, null, null, new java.lang.String[]{"android.permission.INTERACT_ACROSS_USERS"}, -1, null, true, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), -1);
    }

    private void finishUserStopping(final int i, final com.android.server.am.UserState userState, final boolean z) {
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_FINISH_USER_STOPPING, i);
        synchronized (this.mLock) {
            try {
                if (userState.state != 4) {
                    com.android.server.pm.UserJourneyLogger.UserJourneySession logUserJourneyFinishWithError = this.mInjector.getUserJourneyLogger().logUserJourneyFinishWithError(-1, getUserInfo(i), 5, 3);
                    if (logUserJourneyFinishWithError != null) {
                        this.mHandler.removeMessages(200, logUserJourneyFinishWithError);
                    } else {
                        this.mInjector.getUserJourneyLogger().logUserJourneyFinishWithError(-1, getUserInfo(i), 5, 0);
                    }
                    return;
                }
                userState.setState(5);
                com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("setUserState-STATE_SHUTDOWN-" + i + "-[stopUser]");
                this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                timingsTraceAndSlog.traceEnd();
                this.mInjector.batteryStatsServiceNoteEvent(16391, java.lang.Integer.toString(i), i);
                this.mInjector.getSystemServiceManager().onUserStopping(i);
                final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda24
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.UserController.this.lambda$finishUserStopping$10(i, userState, z);
                    }
                };
                if (this.mInjector.getUserManager().isPreCreated(i)) {
                    runnable.run();
                    return;
                }
                android.content.Intent intent = new android.content.Intent("android.intent.action.ACTION_SHUTDOWN");
                android.content.IIntentReceiver iIntentReceiver = new android.content.IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.6
                    public void performReceive(android.content.Intent intent2, int i2, java.lang.String str, android.os.Bundle bundle, boolean z2, boolean z3, int i3) {
                        com.android.server.am.UserController.asyncTraceEnd("broadcast-ACTION_SHUTDOWN-" + i + "-[stopUser]", i);
                        runnable.run();
                    }
                };
                asyncTraceBegin("broadcast-ACTION_SHUTDOWN-" + i + "-[stopUser]", i);
                this.mInjector.broadcastIntent(intent, null, iIntentReceiver, 0, null, null, null, -1, null, true, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserStopping$10(final int i, final com.android.server.am.UserState userState, final boolean z) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$finishUserStopping$9(i, userState, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishUserStopping$9(int i, com.android.server.am.UserState userState, boolean z) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("finishUserStopped-" + i + "-[stopUser]");
        finishUserStopped(userState, z);
        timingsTraceAndSlog.traceEnd();
    }

    @com.android.internal.annotations.VisibleForTesting
    void finishUserStopped(com.android.server.am.UserState userState, boolean z) {
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        boolean z2;
        boolean z3;
        int i;
        int identifier = userState.mHandle.getIdentifier();
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_FINISH_USER_STOPPED, identifier);
        android.content.pm.UserInfo userInfo = getUserInfo(identifier);
        synchronized (this.mLock) {
            try {
                arrayList = new java.util.ArrayList(userState.mStopCallbacks);
                arrayList2 = new java.util.ArrayList(userState.mKeyEvictedCallbacks);
                z2 = false;
                z3 = true;
                if (this.mStartedUsers.get(identifier) != userState || userState.state != 5) {
                    i = identifier;
                } else {
                    com.android.server.utils.Slogf.i(TAG, "Removing user state from UserController.mStartedUsers for user #" + identifier + " as a result of user being stopped");
                    this.mStartedUsers.remove(identifier);
                    this.mUserLru.remove(java.lang.Integer.valueOf(identifier));
                    updateStartedUserArrayLU();
                    if (z && !arrayList2.isEmpty()) {
                        com.android.server.utils.Slogf.wtf(TAG, "Delayed locking enabled while KeyEvictedCallbacks not empty, userId:" + identifier + " callbacks:" + arrayList2);
                        z = false;
                    }
                    i = updateUserToLockLU(identifier, z);
                    if (i != -10000) {
                        z2 = true;
                    } else {
                        z3 = false;
                        z2 = true;
                    }
                }
            } finally {
            }
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        if (z2) {
            com.android.server.utils.Slogf.i(TAG, "Removing user state from UserManager.mUserStates for user #" + identifier + " as a result of user being stopped");
            this.mInjector.getUserManagerInternal().removeUserState(identifier);
            this.mInjector.activityManagerOnUserStopped(identifier);
            timingsTraceAndSlog.traceBegin("forceStopUser-" + identifier + "-[stopUser]");
            forceStopUser(identifier, "finish user");
            timingsTraceAndSlog.traceEnd();
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            android.app.IStopUserCallback iStopUserCallback = (android.app.IStopUserCallback) it.next();
            if (z2) {
                try {
                    timingsTraceAndSlog.traceBegin("stopCallbacks.userStopped-" + identifier + "-[stopUser]");
                    iStopUserCallback.userStopped(identifier);
                    timingsTraceAndSlog.traceEnd();
                } catch (android.os.RemoteException e) {
                }
            } else {
                timingsTraceAndSlog.traceBegin("stopCallbacks.userStopAborted-" + identifier + "-[stopUser]");
                iStopUserCallback.userStopAborted(identifier);
                timingsTraceAndSlog.traceEnd();
            }
        }
        if (z2) {
            timingsTraceAndSlog.traceBegin("systemServiceManagerOnUserStopped-" + identifier + "-[stopUser]");
            this.mInjector.systemServiceManagerOnUserStopped(identifier);
            timingsTraceAndSlog.traceEnd();
            timingsTraceAndSlog.traceBegin("taskSupervisorRemoveUser-" + identifier + "-[stopUser]");
            this.mInjector.taskSupervisorRemoveUser(identifier);
            timingsTraceAndSlog.traceEnd();
            if (userInfo.isEphemeral() && !userInfo.preCreated) {
                this.mInjector.getUserManager().removeUserEvenWhenDisallowed(identifier);
            }
            com.android.server.pm.UserJourneyLogger.UserJourneySession logUserJourneyFinish = this.mInjector.getUserJourneyLogger().logUserJourneyFinish(-1, userInfo, 5);
            if (logUserJourneyFinish != null) {
                this.mHandler.removeMessages(200, logUserJourneyFinish);
            }
            if (z3) {
                dispatchUserLocking(i, arrayList2);
            }
            resumePendingUserStarts(identifier);
            return;
        }
        com.android.server.pm.UserJourneyLogger.UserJourneySession finishAndClearIncompleteUserJourney = this.mInjector.getUserJourneyLogger().finishAndClearIncompleteUserJourney(identifier, 5);
        if (finishAndClearIncompleteUserJourney != null) {
            this.mHandler.removeMessages(200, finishAndClearIncompleteUserJourney);
        }
    }

    private void resumePendingUserStarts(int i) {
        synchronized (this.mLock) {
            try {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (final com.android.server.am.UserController.PendingUserStart pendingUserStart : this.mPendingUserStarts) {
                    if (pendingUserStart.userId == i) {
                        com.android.server.utils.Slogf.i(TAG, "resumePendingUserStart for" + pendingUserStart);
                        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda20
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.am.UserController.this.lambda$resumePendingUserStarts$11(pendingUserStart);
                            }
                        });
                        arrayList.add(pendingUserStart);
                    }
                }
                this.mPendingUserStarts.removeAll(arrayList);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resumePendingUserStarts$11(com.android.server.am.UserController.PendingUserStart pendingUserStart) {
        startUser(pendingUserStart.userId, pendingUserStart.userStartMode, pendingUserStart.unlockListener);
    }

    private void dispatchUserLocking(final int i, @android.annotation.Nullable final java.util.List<com.android.server.am.UserState.KeyEvictedCallback> list) {
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$dispatchUserLocking$12(i, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchUserLocking$12(int i, java.util.List list) {
        synchronized (this.mLock) {
            try {
                if (this.mStartedUsers.get(i) != null) {
                    com.android.server.utils.Slogf.w(TAG, "User was restarted, skipping key eviction");
                    return;
                }
                try {
                    com.android.server.utils.Slogf.i(TAG, "Locking CE storage for user #" + i);
                    this.mInjector.getStorageManager().lockCeStorage(i);
                    if (list == null) {
                        return;
                    }
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        ((com.android.server.am.UserState.KeyEvictedCallback) list.get(i2)).keyEvicted(i);
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int updateUserToLockLU(int i, boolean z) {
        if (!canDelayDataLockingForUser(i) || !z || getUserInfo(i).isEphemeral() || hasUserRestriction("no_run_in_background", i)) {
            return i;
        }
        if (this.mDelayUserDataLocking) {
            this.mLastActiveUsersForDelayedLocking.remove(java.lang.Integer.valueOf(i));
            this.mLastActiveUsersForDelayedLocking.add(0, java.lang.Integer.valueOf(i));
            if (this.mStartedUsers.size() + this.mLastActiveUsersForDelayedLocking.size() > this.mMaxRunningUsers) {
                int intValue = this.mLastActiveUsersForDelayedLocking.get(this.mLastActiveUsersForDelayedLocking.size() - 1).intValue();
                this.mLastActiveUsersForDelayedLocking.remove(this.mLastActiveUsersForDelayedLocking.size() - 1);
                com.android.server.utils.Slogf.i(TAG, "finishUserStopped: should stop user " + i + " but should lock user " + intValue);
                return intValue;
            }
        }
        com.android.server.utils.Slogf.i(TAG, "finishUserStopped: should stop user " + i + " but without any locking");
        return com.android.server.am.ProcessList.INVALID_ADJ;
    }

    private boolean canDelayDataLockingForUser(int i) {
        if (allowBiometricUnlockForPrivateProfile()) {
            android.content.pm.UserProperties userProperties = getUserProperties(i);
            return this.mDelayUserDataLocking || (userProperties != null && userProperties.getAllowStoppingUserWithDelayedLocking());
        }
        return this.mDelayUserDataLocking;
    }

    private boolean allowBiometricUnlockForPrivateProfile() {
        return android.os.Flags.allowPrivateProfile() && android.multiuser.Flags.enableBiometricsToUnlockPrivateSpace();
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] getUsersToStopLU(int i) {
        boolean z;
        int size = this.mStartedUsers.size();
        android.util.IntArray intArray = new android.util.IntArray();
        intArray.add(i);
        int i2 = this.mUserProfileGroupIds.get(i, com.android.server.am.ProcessList.INVALID_ADJ);
        for (int i3 = 0; i3 < size; i3++) {
            int identifier = this.mStartedUsers.valueAt(i3).mHandle.getIdentifier();
            int i4 = this.mUserProfileGroupIds.get(identifier, com.android.server.am.ProcessList.INVALID_ADJ);
            boolean z2 = true;
            if (i2 == -10000 || i2 != i4) {
                z = false;
            } else {
                z = true;
            }
            if (identifier != i) {
                z2 = false;
            }
            if (z && !z2) {
                intArray.add(identifier);
            }
        }
        return intArray.toArray();
    }

    private void forceStopUser(int i, java.lang.String str) {
        android.content.pm.UserInfo profileParent;
        this.mInjector.activityManagerForceStopPackage(i, str);
        if (this.mInjector.getUserManager().isPreCreated(i)) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.USER_STOPPED");
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), -1);
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        if (userInfo != null && userInfo.isProfile() && (profileParent = this.mInjector.getUserManager().getProfileParent(i)) != null) {
            broadcastProfileAccessibleStateChanged(i, profileParent.id, "android.intent.action.PROFILE_INACCESSIBLE");
        }
    }

    private void stopGuestOrEphemeralUserIfBackground(int i) {
        synchronized (this.mLock) {
            com.android.server.am.UserState userState = this.mStartedUsers.get(i);
            if (i == 0 || i == this.mCurrentUserId || userState == null || userState.state == 4 || userState.state == 5) {
                return;
            }
            android.content.pm.UserInfo userInfo = getUserInfo(i);
            if (userInfo.isEphemeral()) {
                ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).onEphemeralUserStop(i);
            }
            if (userInfo.isGuest() || userInfo.isEphemeral()) {
                synchronized (this.mLock) {
                    stopUsersLU(i, true, false, null, null);
                }
            }
        }
    }

    void scheduleStartProfiles() {
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$scheduleStartProfiles$13();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleStartProfiles$13() {
        if (!this.mHandler.hasMessages(40)) {
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(40), 1000L);
        }
    }

    private void startProfiles() {
        int currentUserId = getCurrentUserId();
        int i = 0;
        java.util.List<android.content.pm.UserInfo> profiles = this.mInjector.getUserManager().getProfiles(currentUserId, false);
        java.util.ArrayList arrayList = new java.util.ArrayList(profiles.size());
        for (android.content.pm.UserInfo userInfo : profiles) {
            if ((userInfo.flags & 16) == 16 && userInfo.id != currentUserId && shouldStartWithParent(userInfo)) {
                arrayList.add(userInfo);
            }
        }
        int size = arrayList.size();
        while (i < size && i < getMaxRunningUsers() - 1) {
            startUser(((android.content.pm.UserInfo) arrayList.get(i)).id, 3);
            i++;
        }
        if (i < size) {
            com.android.server.utils.Slogf.w(TAG, "More profiles than MAX_RUNNING_USERS");
        }
    }

    private boolean shouldStartWithParent(android.content.pm.UserInfo userInfo) {
        android.content.pm.UserProperties userProperties = getUserProperties(userInfo.id);
        return (userProperties == null || !userProperties.getStartWithParent() || userInfo.isQuietModeEnabled()) ? false : true;
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.MANAGE_USERS", "android.permission.INTERACT_ACROSS_USERS_FULL"})
    boolean startProfile(int i, boolean z, @android.annotation.Nullable android.os.IProgressListener iProgressListener) {
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") == -1 && this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") == -1) {
            throw new java.lang.SecurityException("You either need MANAGE_USERS or INTERACT_ACROSS_USERS_FULL permission to start a profile");
        }
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        if (userInfo == null || !userInfo.isProfile()) {
            throw new java.lang.IllegalArgumentException("User " + i + " is not a profile");
        }
        if (!userInfo.isEnabled() && !z) {
            com.android.server.utils.Slogf.w(TAG, "Cannot start disabled profile #%d", java.lang.Integer.valueOf(i));
            return false;
        }
        return startUserNoChecks(i, 0, 3, iProgressListener);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean startUser(int i, @com.android.server.pm.UserManagerInternal.UserStartMode int i2) {
        return startUser(i, i2, null);
    }

    boolean startUser(int i, @com.android.server.pm.UserManagerInternal.UserStartMode int i2, @android.annotation.Nullable android.os.IProgressListener iProgressListener) {
        checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "startUser");
        return startUserNoChecks(i, 0, i2, iProgressListener);
    }

    boolean startUserVisibleOnDisplay(int i, int i2, @android.annotation.Nullable android.os.IProgressListener iProgressListener) {
        checkCallingHasOneOfThosePermissions("startUserOnDisplay", "android.permission.MANAGE_USERS", "android.permission.INTERACT_ACROSS_USERS");
        try {
            return startUserNoChecks(i, i2, 3, iProgressListener);
        } catch (java.lang.RuntimeException e) {
            com.android.server.utils.Slogf.e(TAG, "startUserOnSecondaryDisplay(%d, %d) failed: %s", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), e);
            return false;
        }
    }

    private boolean startUserNoChecks(int i, int i2, @com.android.server.pm.UserManagerInternal.UserStartMode int i3, @android.annotation.Nullable android.os.IProgressListener iProgressListener) {
        java.lang.String str;
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("UserController.startUser-");
        sb.append(i);
        if (i2 == 0) {
            str = "";
        } else {
            str = "-display-" + i2;
        }
        sb.append(str);
        sb.append("-");
        sb.append(i3 == 1 ? "fg" : "bg");
        sb.append("-start-mode-");
        sb.append(i3);
        timingsTraceAndSlog.traceBegin(sb.toString());
        try {
            return startUserInternal(i, i2, i3, iProgressListener, timingsTraceAndSlog);
        } finally {
            timingsTraceAndSlog.traceEnd();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0324 A[Catch: all -> 0x0066, TryCatch #4 {all -> 0x0066, blocks: (B:10:0x004d, B:12:0x0058, B:14:0x005e, B:16:0x006b, B:19:0x0070, B:21:0x0075, B:22:0x0078, B:26:0x0080, B:28:0x0085, B:29:0x0095, B:31:0x00a3, B:35:0x00c1, B:37:0x00c7, B:42:0x0111, B:44:0x0128, B:47:0x014b, B:48:0x0153, B:56:0x01c9, B:57:0x01ce, B:59:0x01d3, B:60:0x01e7, B:62:0x01ef, B:63:0x01f8, B:67:0x01fe, B:69:0x0216, B:70:0x023b, B:72:0x0243, B:73:0x025b, B:77:0x0261, B:79:0x0295, B:81:0x0299, B:82:0x02bb, B:84:0x02c3, B:85:0x02f9, B:89:0x0300, B:94:0x0312, B:96:0x0317, B:101:0x0333, B:102:0x0324, B:103:0x030f, B:109:0x0268, B:110:0x0269, B:112:0x026e, B:113:0x0285, B:117:0x028b, B:122:0x0292, B:127:0x0223, B:128:0x0224, B:129:0x022f, B:136:0x0347, B:147:0x0349, B:148:0x00ec, B:150:0x00f0, B:115:0x0286, B:116:0x0289, B:65:0x01f9, B:66:0x01fd, B:131:0x0230, B:132:0x023a, B:75:0x025c, B:76:0x025f, B:50:0x0154, B:52:0x015e, B:53:0x01b6, B:54:0x01c6, B:137:0x0181, B:139:0x0186, B:140:0x01ae), top: B:9:0x004d, inners: #0, #1, #2, #3, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0269 A[Catch: all -> 0x0066, TryCatch #4 {all -> 0x0066, blocks: (B:10:0x004d, B:12:0x0058, B:14:0x005e, B:16:0x006b, B:19:0x0070, B:21:0x0075, B:22:0x0078, B:26:0x0080, B:28:0x0085, B:29:0x0095, B:31:0x00a3, B:35:0x00c1, B:37:0x00c7, B:42:0x0111, B:44:0x0128, B:47:0x014b, B:48:0x0153, B:56:0x01c9, B:57:0x01ce, B:59:0x01d3, B:60:0x01e7, B:62:0x01ef, B:63:0x01f8, B:67:0x01fe, B:69:0x0216, B:70:0x023b, B:72:0x0243, B:73:0x025b, B:77:0x0261, B:79:0x0295, B:81:0x0299, B:82:0x02bb, B:84:0x02c3, B:85:0x02f9, B:89:0x0300, B:94:0x0312, B:96:0x0317, B:101:0x0333, B:102:0x0324, B:103:0x030f, B:109:0x0268, B:110:0x0269, B:112:0x026e, B:113:0x0285, B:117:0x028b, B:122:0x0292, B:127:0x0223, B:128:0x0224, B:129:0x022f, B:136:0x0347, B:147:0x0349, B:148:0x00ec, B:150:0x00f0, B:115:0x0286, B:116:0x0289, B:65:0x01f9, B:66:0x01fd, B:131:0x0230, B:132:0x023a, B:75:0x025c, B:76:0x025f, B:50:0x0154, B:52:0x015e, B:53:0x01b6, B:54:0x01c6, B:137:0x0181, B:139:0x0186, B:140:0x01ae), top: B:9:0x004d, inners: #0, #1, #2, #3, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0243 A[Catch: all -> 0x0066, TryCatch #4 {all -> 0x0066, blocks: (B:10:0x004d, B:12:0x0058, B:14:0x005e, B:16:0x006b, B:19:0x0070, B:21:0x0075, B:22:0x0078, B:26:0x0080, B:28:0x0085, B:29:0x0095, B:31:0x00a3, B:35:0x00c1, B:37:0x00c7, B:42:0x0111, B:44:0x0128, B:47:0x014b, B:48:0x0153, B:56:0x01c9, B:57:0x01ce, B:59:0x01d3, B:60:0x01e7, B:62:0x01ef, B:63:0x01f8, B:67:0x01fe, B:69:0x0216, B:70:0x023b, B:72:0x0243, B:73:0x025b, B:77:0x0261, B:79:0x0295, B:81:0x0299, B:82:0x02bb, B:84:0x02c3, B:85:0x02f9, B:89:0x0300, B:94:0x0312, B:96:0x0317, B:101:0x0333, B:102:0x0324, B:103:0x030f, B:109:0x0268, B:110:0x0269, B:112:0x026e, B:113:0x0285, B:117:0x028b, B:122:0x0292, B:127:0x0223, B:128:0x0224, B:129:0x022f, B:136:0x0347, B:147:0x0349, B:148:0x00ec, B:150:0x00f0, B:115:0x0286, B:116:0x0289, B:65:0x01f9, B:66:0x01fd, B:131:0x0230, B:132:0x023a, B:75:0x025c, B:76:0x025f, B:50:0x0154, B:52:0x015e, B:53:0x01b6, B:54:0x01c6, B:137:0x0181, B:139:0x0186, B:140:0x01ae), top: B:9:0x004d, inners: #0, #1, #2, #3, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0299 A[Catch: all -> 0x0066, TryCatch #4 {all -> 0x0066, blocks: (B:10:0x004d, B:12:0x0058, B:14:0x005e, B:16:0x006b, B:19:0x0070, B:21:0x0075, B:22:0x0078, B:26:0x0080, B:28:0x0085, B:29:0x0095, B:31:0x00a3, B:35:0x00c1, B:37:0x00c7, B:42:0x0111, B:44:0x0128, B:47:0x014b, B:48:0x0153, B:56:0x01c9, B:57:0x01ce, B:59:0x01d3, B:60:0x01e7, B:62:0x01ef, B:63:0x01f8, B:67:0x01fe, B:69:0x0216, B:70:0x023b, B:72:0x0243, B:73:0x025b, B:77:0x0261, B:79:0x0295, B:81:0x0299, B:82:0x02bb, B:84:0x02c3, B:85:0x02f9, B:89:0x0300, B:94:0x0312, B:96:0x0317, B:101:0x0333, B:102:0x0324, B:103:0x030f, B:109:0x0268, B:110:0x0269, B:112:0x026e, B:113:0x0285, B:117:0x028b, B:122:0x0292, B:127:0x0223, B:128:0x0224, B:129:0x022f, B:136:0x0347, B:147:0x0349, B:148:0x00ec, B:150:0x00f0, B:115:0x0286, B:116:0x0289, B:65:0x01f9, B:66:0x01fd, B:131:0x0230, B:132:0x023a, B:75:0x025c, B:76:0x025f, B:50:0x0154, B:52:0x015e, B:53:0x01b6, B:54:0x01c6, B:137:0x0181, B:139:0x0186, B:140:0x01ae), top: B:9:0x004d, inners: #0, #1, #2, #3, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02c3 A[Catch: all -> 0x0066, TryCatch #4 {all -> 0x0066, blocks: (B:10:0x004d, B:12:0x0058, B:14:0x005e, B:16:0x006b, B:19:0x0070, B:21:0x0075, B:22:0x0078, B:26:0x0080, B:28:0x0085, B:29:0x0095, B:31:0x00a3, B:35:0x00c1, B:37:0x00c7, B:42:0x0111, B:44:0x0128, B:47:0x014b, B:48:0x0153, B:56:0x01c9, B:57:0x01ce, B:59:0x01d3, B:60:0x01e7, B:62:0x01ef, B:63:0x01f8, B:67:0x01fe, B:69:0x0216, B:70:0x023b, B:72:0x0243, B:73:0x025b, B:77:0x0261, B:79:0x0295, B:81:0x0299, B:82:0x02bb, B:84:0x02c3, B:85:0x02f9, B:89:0x0300, B:94:0x0312, B:96:0x0317, B:101:0x0333, B:102:0x0324, B:103:0x030f, B:109:0x0268, B:110:0x0269, B:112:0x026e, B:113:0x0285, B:117:0x028b, B:122:0x0292, B:127:0x0223, B:128:0x0224, B:129:0x022f, B:136:0x0347, B:147:0x0349, B:148:0x00ec, B:150:0x00f0, B:115:0x0286, B:116:0x0289, B:65:0x01f9, B:66:0x01fd, B:131:0x0230, B:132:0x023a, B:75:0x025c, B:76:0x025f, B:50:0x0154, B:52:0x015e, B:53:0x01b6, B:54:0x01c6, B:137:0x0181, B:139:0x0186, B:140:0x01ae), top: B:9:0x004d, inners: #0, #1, #2, #3, #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x02fd  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0317 A[Catch: all -> 0x0066, TryCatch #4 {all -> 0x0066, blocks: (B:10:0x004d, B:12:0x0058, B:14:0x005e, B:16:0x006b, B:19:0x0070, B:21:0x0075, B:22:0x0078, B:26:0x0080, B:28:0x0085, B:29:0x0095, B:31:0x00a3, B:35:0x00c1, B:37:0x00c7, B:42:0x0111, B:44:0x0128, B:47:0x014b, B:48:0x0153, B:56:0x01c9, B:57:0x01ce, B:59:0x01d3, B:60:0x01e7, B:62:0x01ef, B:63:0x01f8, B:67:0x01fe, B:69:0x0216, B:70:0x023b, B:72:0x0243, B:73:0x025b, B:77:0x0261, B:79:0x0295, B:81:0x0299, B:82:0x02bb, B:84:0x02c3, B:85:0x02f9, B:89:0x0300, B:94:0x0312, B:96:0x0317, B:101:0x0333, B:102:0x0324, B:103:0x030f, B:109:0x0268, B:110:0x0269, B:112:0x026e, B:113:0x0285, B:117:0x028b, B:122:0x0292, B:127:0x0223, B:128:0x0224, B:129:0x022f, B:136:0x0347, B:147:0x0349, B:148:0x00ec, B:150:0x00f0, B:115:0x0286, B:116:0x0289, B:65:0x01f9, B:66:0x01fd, B:131:0x0230, B:132:0x023a, B:75:0x025c, B:76:0x025f, B:50:0x0154, B:52:0x015e, B:53:0x01b6, B:54:0x01c6, B:137:0x0181, B:139:0x0186, B:140:0x01ae), top: B:9:0x004d, inners: #0, #1, #2, #3, #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean startUserInternal(int i, int i2, @com.android.server.pm.UserManagerInternal.UserStartMode int i3, @android.annotation.Nullable android.os.IProgressListener iProgressListener, com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i4 = i3 == 1 ? 1 : 0;
        boolean z5 = i2 != 0;
        if (z5) {
            com.android.internal.util.Preconditions.checkArgument(i4 ^ 1, "Cannot start user %d in foreground AND on secondary display (%d)", new java.lang.Object[]{java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)});
        }
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_START_USER_INTERNAL, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i2));
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            timingsTraceAndSlog.traceBegin("getStartedUserState");
            int currentUserId = getCurrentUserId();
            if (currentUserId == i) {
                com.android.server.am.UserState startedUserState = getStartedUserState(i);
                if (startedUserState == null) {
                    com.android.server.utils.Slogf.wtf(TAG, "Current user has no UserState");
                } else if (i != 0 || startedUserState.state != 0) {
                    if (startedUserState.state == 3) {
                        notifyFinished(i, iProgressListener);
                    }
                    timingsTraceAndSlog.traceEnd();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return true;
                }
            }
            timingsTraceAndSlog.traceEnd();
            if (i4 != 0) {
                timingsTraceAndSlog.traceBegin("clearAllLockedTasks");
                this.mInjector.clearAllLockedTasks("startUser");
                timingsTraceAndSlog.traceEnd();
            }
            timingsTraceAndSlog.traceBegin("getUserInfo");
            android.content.pm.UserInfo userInfo = getUserInfo(i);
            timingsTraceAndSlog.traceEnd();
            if (userInfo == null) {
                com.android.server.utils.Slogf.w(TAG, "No user info for user #" + i);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            if (i4 != 0 && userInfo.isProfile()) {
                com.android.server.utils.Slogf.w(TAG, "Cannot switch to User #" + i + ": not a full user");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            if ((i4 != 0 || z5) && userInfo.preCreated) {
                com.android.server.utils.Slogf.w(TAG, "Cannot start pre-created user #" + i + " in foreground or on secondary display");
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            timingsTraceAndSlog.traceBegin("assignUserToDisplayOnStart");
            int assignUserToDisplayOnStart = this.mInjector.getUserManagerInternal().assignUserToDisplayOnStart(i, userInfo.profileGroupId, i3, i2);
            timingsTraceAndSlog.traceEnd();
            if (assignUserToDisplayOnStart == -1) {
                com.android.server.utils.Slogf.e(TAG, "%s user(%d) / display (%d) assignment failed: %s", com.android.server.pm.UserManagerInternal.userStartModeToString(i3), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), com.android.server.pm.UserManagerInternal.userAssignmentResultToString(assignUserToDisplayOnStart));
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return false;
            }
            timingsTraceAndSlog.traceBegin("updateStartedUserArrayStarting");
            synchronized (this.mLock) {
                try {
                    com.android.server.am.UserState userState = this.mStartedUsers.get(i);
                    if (userState == null) {
                        userState = new com.android.server.am.UserState(android.os.UserHandle.of(i));
                        userState.mUnlockProgress.addListener(new com.android.server.am.UserController.UserProgressListener());
                        this.mStartedUsers.put(i, userState);
                        updateStartedUserArrayLU();
                        z = true;
                        z2 = true;
                    } else {
                        if (userState.state == 5) {
                            com.android.server.utils.Slogf.i(TAG, "User #" + i + " is shutting down - will start after full shutdown");
                            this.mPendingUserStarts.add(new com.android.server.am.UserController.PendingUserStart(i, i3, iProgressListener));
                            timingsTraceAndSlog.traceEnd();
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return true;
                        }
                        z = false;
                        z2 = false;
                    }
                    java.lang.Integer valueOf = java.lang.Integer.valueOf(i);
                    boolean z6 = z;
                    this.mUserLru.remove(valueOf);
                    this.mUserLru.add(valueOf);
                    if (iProgressListener != null) {
                        userState.mUnlockProgress.addListener(iProgressListener);
                    }
                    timingsTraceAndSlog.traceEnd();
                    if (z2) {
                        timingsTraceAndSlog.traceBegin("setUserState");
                        this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                        timingsTraceAndSlog.traceEnd();
                    }
                    timingsTraceAndSlog.traceBegin("updateConfigurationAndProfileIds");
                    if (i4 != 0) {
                        this.mInjector.reportGlobalUsageEvent(16);
                        synchronized (this.mLock) {
                            this.mCurrentUserId = i;
                            z4 = this.mUserSwitchUiEnabled;
                        }
                        this.mInjector.updateUserConfiguration();
                        updateProfileRelatedCaches();
                        this.mInjector.getWindowManager().setCurrentUser(i);
                        this.mInjector.reportCurWakefulnessUsageEvent();
                        if (z4) {
                            this.mInjector.getWindowManager().setSwitchingUser(true);
                        }
                        timingsTraceAndSlog.traceEnd();
                        if (userState.state == 4) {
                            if (userState.state == 5) {
                                timingsTraceAndSlog.traceBegin("updateStateShutdown");
                                userState.setState(0);
                                this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                                synchronized (this.mLock) {
                                    updateStartedUserArrayLU();
                                }
                                timingsTraceAndSlog.traceEnd();
                                z3 = true;
                            } else {
                                z3 = z6;
                            }
                            if (userState.state != 0) {
                            }
                            timingsTraceAndSlog.traceBegin("sendMessages");
                            if (i4 != 0) {
                            }
                            if (userInfo.preCreated) {
                            }
                            if (i != 0) {
                            }
                            if (!z3) {
                            }
                            sendUserStartedBroadcast(i, callingUid, callingPid);
                            timingsTraceAndSlog.traceEnd();
                            if (i4 == 0) {
                            }
                            if (!z3) {
                            }
                            timingsTraceAndSlog.traceBegin("sendRestartBroadcast");
                            sendUserStartingBroadcast(i, callingUid, callingPid);
                            timingsTraceAndSlog.traceEnd();
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return true;
                        }
                        timingsTraceAndSlog.traceBegin("updateStateStopping");
                        userState.setState(userState.lastState);
                        this.mInjector.getUserManagerInternal().setUserState(i, userState.state);
                        synchronized (this.mLock) {
                            updateStartedUserArrayLU();
                        }
                        timingsTraceAndSlog.traceEnd();
                        z3 = true;
                        if (userState.state != 0) {
                            timingsTraceAndSlog.traceBegin("updateStateBooting");
                            this.mInjector.getUserManager().onBeforeStartUser(i);
                            this.mHandler.sendMessage(this.mHandler.obtainMessage(50, i, 0));
                            timingsTraceAndSlog.traceEnd();
                        }
                        timingsTraceAndSlog.traceBegin("sendMessages");
                        if (i4 != 0) {
                            this.mHandler.sendMessage(this.mHandler.obtainMessage(60, i, currentUserId));
                            this.mHandler.removeMessages(10);
                            this.mHandler.removeMessages(30);
                            this.mHandler.sendMessage(this.mHandler.obtainMessage(10, currentUserId, i, userState));
                            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(30, currentUserId, i, userState), getUserSwitchTimeoutMs());
                        }
                        if (userInfo.preCreated) {
                            z3 = false;
                        }
                        boolean z7 = i != 0 && this.mInjector.isHeadlessSystemUserMode();
                        if (!z3 || z7) {
                            sendUserStartedBroadcast(i, callingUid, callingPid);
                        }
                        timingsTraceAndSlog.traceEnd();
                        if (i4 == 0) {
                            timingsTraceAndSlog.traceBegin("moveUserToForeground");
                            moveUserToForeground(userState, i);
                            timingsTraceAndSlog.traceEnd();
                        } else {
                            timingsTraceAndSlog.traceBegin("finishUserBoot");
                            finishUserBoot(userState);
                            timingsTraceAndSlog.traceEnd();
                        }
                        if (!z3 || z7) {
                            timingsTraceAndSlog.traceBegin("sendRestartBroadcast");
                            sendUserStartingBroadcast(i, callingUid, callingPid);
                            timingsTraceAndSlog.traceEnd();
                        }
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return true;
                    }
                    java.lang.Integer valueOf2 = java.lang.Integer.valueOf(this.mCurrentUserId);
                    updateProfileRelatedCaches();
                    synchronized (this.mLock) {
                        this.mUserLru.remove(valueOf2);
                        this.mUserLru.add(valueOf2);
                    }
                    timingsTraceAndSlog.traceEnd();
                    if (userState.state == 4) {
                    }
                } finally {
                }
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    void startUserInForeground(int i) {
        if (android.multiuser.Flags.setPowerModeDuringUserSwitch()) {
            this.mInjector.setPerformancePowerMode(true);
        }
        if (!startUser(i, 1)) {
            this.mInjector.getWindowManager().setSwitchingUser(false);
            lambda$completeUserSwitch$18(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.am.UserController.this.endUserSwitch();
                }
            });
        }
    }

    boolean unlockUser(int i, @android.annotation.Nullable android.os.IProgressListener iProgressListener) {
        checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "unlockUser");
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_UNLOCK_USER, i);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return maybeUnlockUser(i, iProgressListener);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static void notifyFinished(int i, @android.annotation.Nullable android.os.IProgressListener iProgressListener) {
        if (iProgressListener == null) {
            return;
        }
        try {
            iProgressListener.onFinished(i, (android.os.Bundle) null);
        } catch (android.os.RemoteException e) {
        }
    }

    private boolean maybeUnlockUser(int i) {
        return maybeUnlockUser(i, null);
    }

    private boolean maybeUnlockUser(int i, @android.annotation.Nullable android.os.IProgressListener iProgressListener) {
        com.android.server.am.UserState userState;
        int size;
        int[] iArr;
        if (!this.mAllowUserUnlocking) {
            com.android.server.utils.Slogf.i(TAG, "Not unlocking user %d yet because boot hasn't completed", java.lang.Integer.valueOf(i));
            notifyFinished(i, iProgressListener);
            return false;
        }
        if (!android.os.storage.StorageManager.isCeStorageUnlocked(i)) {
            this.mLockPatternUtils.unlockUserKeyIfUnsecured(i);
        }
        synchronized (this.mLock) {
            try {
                userState = this.mStartedUsers.get(i);
                if (userState != null) {
                    userState.mUnlockProgress.addListener(iProgressListener);
                }
            } finally {
            }
        }
        if (userState == null) {
            notifyFinished(i, iProgressListener);
            return false;
        }
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("finishUserUnlocking-" + i);
        boolean finishUserUnlocking = finishUserUnlocking(userState);
        timingsTraceAndSlog.traceEnd();
        if (!finishUserUnlocking) {
            notifyFinished(i, iProgressListener);
            return false;
        }
        synchronized (this.mLock) {
            try {
                size = this.mStartedUsers.size();
                iArr = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    iArr[i2] = this.mStartedUsers.keyAt(i2);
                }
            } finally {
            }
        }
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = iArr[i3];
            android.content.pm.UserInfo profileParent = this.mInjector.getUserManager().getProfileParent(i4);
            if (profileParent != null && profileParent.id == i && i4 != i) {
                com.android.server.utils.Slogf.d(TAG, "User " + i4 + " (parent " + profileParent.id + "): attempting unlock because parent was just unlocked");
                maybeUnlockUser(i4);
            }
        }
        return true;
    }

    boolean switchUser(int i) {
        enforceShellRestriction("no_debugging_features", i);
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_SWITCH_USER, i);
        int currentUserId = getCurrentUserId();
        android.content.pm.UserInfo userInfo = getUserInfo(i);
        synchronized (this.mLock) {
            if (i == currentUserId) {
                try {
                    if (this.mTargetUserId == -10000) {
                        com.android.server.utils.Slogf.i(TAG, "user #" + i + " is already the current user");
                        return true;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (userInfo == null) {
                com.android.server.utils.Slogf.w(TAG, "No user info for user #" + i);
                return false;
            }
            if (!userInfo.supportsSwitchTo()) {
                com.android.server.utils.Slogf.w(TAG, "Cannot switch to User #" + i + ": not supported");
                return false;
            }
            if (com.android.server.FactoryResetter.isFactoryResetting()) {
                com.android.server.utils.Slogf.w(TAG, "Cannot switch to User #" + i + ": factory reset in progress");
                return false;
            }
            if (this.mInitialized) {
                if (this.mTargetUserId != -10000) {
                    com.android.server.utils.Slogf.w(TAG, "There is already an ongoing user switch to User #" + this.mTargetUserId + ". User #" + i + " will be added to the queue.");
                    this.mPendingTargetUserIds.offer(java.lang.Integer.valueOf(i));
                    return true;
                }
                this.mTargetUserId = i;
                boolean z = this.mUserSwitchUiEnabled;
                if (z) {
                    android.util.Pair pair = new android.util.Pair(getUserInfo(currentUserId), userInfo);
                    this.mUiHandler.removeMessages(1000);
                    this.mUiHandler.sendMessage(this.mUiHandler.obtainMessage(1000, pair));
                } else {
                    sendStartUserSwitchFgMessage(i);
                }
                return true;
            }
            com.android.server.utils.Slogf.e(TAG, "Cannot switch to User #" + i + ": UserController not ready yet");
            return false;
        }
    }

    private void sendStartUserSwitchFgMessage(int i) {
        this.mHandler.removeMessages(120);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(120, i, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dismissUserSwitchDialog, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$completeUserSwitch$18(final java.lang.Runnable runnable) {
        this.mUiHandler.post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$dismissUserSwitchDialog$14(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dismissUserSwitchDialog$14(java.lang.Runnable runnable) {
        this.mInjector.dismissUserSwitchingDialog(runnable);
    }

    private void showUserSwitchDialog(final android.util.Pair<android.content.pm.UserInfo, android.content.pm.UserInfo> pair) {
        this.mInjector.showUserSwitchingDialog((android.content.pm.UserInfo) pair.first, (android.content.pm.UserInfo) pair.second, getSwitchingFromSystemUserMessageUnchecked(), getSwitchingToSystemUserMessageUnchecked(), new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$showUserSwitchDialog$15(pair);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUserSwitchDialog$15(android.util.Pair pair) {
        sendStartUserSwitchFgMessage(((android.content.pm.UserInfo) pair.second).id);
    }

    private void dispatchForegroundProfileChanged(int i) {
        int beginBroadcast = this.mUserSwitchObservers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mUserSwitchObservers.getBroadcastItem(i2).onForegroundProfileSwitch(i);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mUserSwitchObservers.finishBroadcast();
    }

    @com.android.internal.annotations.VisibleForTesting
    void dispatchUserSwitchComplete(int i, int i2) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("dispatchUserSwitchComplete-" + i2);
        this.mInjector.getWindowManager().setSwitchingUser(false);
        int beginBroadcast = this.mUserSwitchObservers.beginBroadcast();
        for (int i3 = 0; i3 < beginBroadcast; i3++) {
            try {
                timingsTraceAndSlog.traceBegin("onUserSwitchComplete-" + i2 + " #" + i3 + " " + this.mUserSwitchObservers.getBroadcastCookie(i3));
                this.mUserSwitchObservers.getBroadcastItem(i3).onUserSwitchComplete(i2);
                timingsTraceAndSlog.traceEnd();
            } catch (android.os.RemoteException e) {
            }
        }
        this.mUserSwitchObservers.finishBroadcast();
        timingsTraceAndSlog.traceBegin("sendUserSwitchBroadcasts-" + i + "-" + i2);
        sendUserSwitchBroadcasts(i, i2);
        timingsTraceAndSlog.traceEnd();
        timingsTraceAndSlog.traceEnd();
        endUserSwitch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endUserSwitch() {
        int intValue;
        if (android.multiuser.Flags.setPowerModeDuringUserSwitch()) {
            this.mInjector.setPerformancePowerMode(false);
        }
        synchronized (this.mLock) {
            intValue = ((java.lang.Integer) com.android.internal.util.ObjectUtils.getOrElse(this.mPendingTargetUserIds.poll(), java.lang.Integer.valueOf(com.android.server.am.ProcessList.INVALID_ADJ))).intValue();
            this.mTargetUserId = com.android.server.am.ProcessList.INVALID_ADJ;
        }
        if (intValue != -10000) {
            switchUser(intValue);
        }
    }

    private void dispatchLockedBootComplete(int i) {
        int beginBroadcast = this.mUserSwitchObservers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mUserSwitchObservers.getBroadcastItem(i2).onLockedBootComplete(i);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mUserSwitchObservers.finishBroadcast();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001d A[Catch: all -> 0x0018, DONT_GENERATE, TryCatch #0 {all -> 0x0018, blocks: (B:16:0x000f, B:10:0x001d, B:12:0x001f, B:13:0x0028), top: B:15:0x000f }] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x001f A[Catch: all -> 0x0018, TryCatch #0 {all -> 0x0018, blocks: (B:16:0x000f, B:10:0x001d, B:12:0x001f, B:13:0x0028), top: B:15:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void stopUserOnSwitchIfEnforced(int i) {
        boolean z;
        if (i == 0) {
            return;
        }
        boolean hasUserRestriction = hasUserRestriction("no_run_in_background", i);
        synchronized (this.mLock) {
            if (!hasUserRestriction) {
                try {
                    if (!shouldStopUserOnSwitch()) {
                        z = false;
                        if (z) {
                            return;
                        }
                        stopUsersLU(i, false, true, null, null);
                        return;
                    }
                } finally {
                }
            }
            z = true;
            if (z) {
            }
        }
    }

    private void timeoutUserSwitch(com.android.server.am.UserState userState, int i, int i2) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("timeoutUserSwitch-" + i + "-to-" + i2);
        synchronized (this.mLock) {
            com.android.server.utils.Slogf.e(TAG, "User switch timeout: from " + i + " to " + i2);
            this.mTimeoutUserSwitchCallbacks = this.mCurWaitingUserSwitchCallbacks;
            this.mHandler.removeMessages(90);
            sendContinueUserSwitchLU(userState, i, i2);
            this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(90, i, i2), 5000L);
        }
        timingsTraceAndSlog.traceEnd();
    }

    private void timeoutUserSwitchCallbacks(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mTimeoutUserSwitchCallbacks != null && !this.mTimeoutUserSwitchCallbacks.isEmpty()) {
                    com.android.server.utils.Slogf.wtf(TAG, "User switch timeout: from " + i + " to " + i2 + ". Observers that didn't respond: " + this.mTimeoutUserSwitchCallbacks);
                    this.mTimeoutUserSwitchCallbacks = null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r18v0, types: [long] */
    /* JADX WARN: Type inference failed for: r18v1 */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r18v3 */
    /* JADX WARN: Type inference failed for: r18v4 */
    /* JADX WARN: Type inference failed for: r18v5, types: [java.util.concurrent.atomic.AtomicInteger] */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v17, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v12, types: [java.lang.String] */
    @com.android.internal.annotations.VisibleForTesting
    void dispatchUserSwitch(final com.android.server.am.UserState userState, final int i, final int i2) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog;
        com.android.server.am.UserController userController;
        final long j;
        final android.util.ArraySet<java.lang.String> arraySet;
        int i3;
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog2;
        ?? sb;
        ?? r4;
        com.android.server.am.UserController userController2 = this;
        int i4 = i2;
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog3 = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog3.traceBegin("dispatchUserSwitch-" + i + "-to-" + i4);
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_DISPATCH_USER_SWITCH, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        int beginBroadcast = userController2.mUserSwitchObservers.beginBroadcast();
        if (beginBroadcast > 0) {
            for (int i5 = 0; i5 < beginBroadcast; i5++) {
                java.lang.String str = "#" + i5 + " " + userController2.mUserSwitchObservers.getBroadcastCookie(i5);
                sb = new java.lang.StringBuilder();
                r4 = "onBeforeUserSwitching-";
                sb.append("onBeforeUserSwitching-");
                sb.append(str);
                timingsTraceAndSlog3.traceBegin(sb.toString());
                try {
                    userController2.mUserSwitchObservers.getBroadcastItem(i5).onBeforeUserSwitching(i4);
                } catch (android.os.RemoteException e) {
                } catch (java.lang.Throwable th) {
                    timingsTraceAndSlog3.traceEnd();
                    throw th;
                }
                timingsTraceAndSlog3.traceEnd();
            }
            android.util.ArraySet<java.lang.String> arraySet2 = new android.util.ArraySet<>();
            ?? r2 = userController2.mLock;
            synchronized (r2) {
                userState.switching = true;
                userController2.mCurWaitingUserSwitchCallbacks = arraySet2;
            }
            java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(beginBroadcast);
            long userSwitchTimeoutMs = getUserSwitchTimeoutMs();
            final long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            int i6 = 0;
            int i7 = r2;
            com.android.server.am.UserController userController3 = sb;
            int i8 = r4;
            while (i6 < beginBroadcast) {
                final ?? elapsedRealtime2 = android.os.SystemClock.elapsedRealtime();
                try {
                    final java.lang.String str2 = "#" + i6 + " " + userController2.mUserSwitchObservers.getBroadcastCookie(i6);
                    synchronized (userController2.mLock) {
                        try {
                            arraySet2.add(str2);
                        } finally {
                            th = th;
                            while (true) {
                                try {
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                }
                            }
                        }
                    }
                    j = userSwitchTimeoutMs;
                    elapsedRealtime2 = atomicInteger;
                    int i9 = i6;
                    arraySet = arraySet2;
                    i3 = beginBroadcast;
                    timingsTraceAndSlog2 = timingsTraceAndSlog3;
                    int i10 = i4;
                    try {
                        android.os.IRemoteCallback.Stub stub = new android.os.IRemoteCallback.Stub() { // from class: com.android.server.am.UserController.7
                            public void sendResult(android.os.Bundle bundle) throws android.os.RemoteException {
                                com.android.server.am.UserController.asyncTraceEnd("onUserSwitching-" + str2, i2);
                                synchronized (com.android.server.am.UserController.this.mLock) {
                                    try {
                                        long elapsedRealtime3 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime2;
                                        if (elapsedRealtime3 > 500) {
                                            com.android.server.utils.Slogf.w(com.android.server.am.UserController.TAG, "User switch slowed down by observer " + str2 + ": result took " + elapsedRealtime3 + " ms to process.");
                                        }
                                        long elapsedRealtime4 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime;
                                        if (elapsedRealtime4 > j) {
                                            com.android.server.utils.Slogf.e(com.android.server.am.UserController.TAG, "User switch timeout: observer " + str2 + "'s result was received " + elapsedRealtime4 + " ms after dispatchUserSwitch.");
                                        }
                                        arraySet.remove(str2);
                                        if (elapsedRealtime2.decrementAndGet() == 0 && arraySet == com.android.server.am.UserController.this.mCurWaitingUserSwitchCallbacks) {
                                            com.android.server.am.UserController.this.sendContinueUserSwitchLU(userState, i, i2);
                                        }
                                    } catch (java.lang.Throwable th3) {
                                        throw th3;
                                    }
                                }
                            }
                        };
                        asyncTraceBegin("onUserSwitching-" + str2, i10);
                        userController3 = this;
                        i8 = i10;
                        try {
                            i7 = i9;
                            try {
                                userController3.mUserSwitchObservers.getBroadcastItem(i7).onUserSwitching(i8, stub);
                            } catch (android.os.RemoteException e2) {
                            }
                        } catch (android.os.RemoteException e3) {
                            i7 = i9;
                            i6 = i7 + 1;
                            userController2 = userController3;
                            i4 = i8;
                            atomicInteger = elapsedRealtime2;
                            arraySet2 = arraySet;
                            userSwitchTimeoutMs = j;
                            beginBroadcast = i3;
                            timingsTraceAndSlog3 = timingsTraceAndSlog2;
                            i7 = i7;
                            userController3 = userController3;
                            i8 = i8;
                        }
                    } catch (android.os.RemoteException e4) {
                        userController3 = this;
                        i8 = i10;
                    }
                } catch (android.os.RemoteException e5) {
                    j = userSwitchTimeoutMs;
                    elapsedRealtime2 = atomicInteger;
                    i7 = i6;
                    arraySet = arraySet2;
                    i3 = beginBroadcast;
                    timingsTraceAndSlog2 = timingsTraceAndSlog3;
                    i8 = i4;
                    userController3 = userController2;
                }
                i6 = i7 + 1;
                userController2 = userController3;
                i4 = i8;
                atomicInteger = elapsedRealtime2;
                arraySet2 = arraySet;
                userSwitchTimeoutMs = j;
                beginBroadcast = i3;
                timingsTraceAndSlog3 = timingsTraceAndSlog2;
                i7 = i7;
                userController3 = userController3;
                i8 = i8;
            }
            timingsTraceAndSlog = timingsTraceAndSlog3;
            userController = userController2;
        } else {
            timingsTraceAndSlog = timingsTraceAndSlog3;
            userController = userController2;
            synchronized (userController.mLock) {
                sendContinueUserSwitchLU(userState, i, i2);
            }
        }
        userController.mUserSwitchObservers.finishBroadcast();
        timingsTraceAndSlog.traceEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void sendContinueUserSwitchLU(com.android.server.am.UserState userState, int i, int i2) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog(TAG);
        timingsTraceAndSlog.traceBegin("sendContinueUserSwitchLU-" + i + "-to-" + i2);
        this.mCurWaitingUserSwitchCallbacks = null;
        this.mHandler.removeMessages(30);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(20, i, i2, userState));
        timingsTraceAndSlog.traceEnd();
    }

    @com.android.internal.annotations.VisibleForTesting
    void continueUserSwitch(com.android.server.am.UserState userState, int i, int i2) {
        com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("continueUserSwitch-" + i + "-to-" + i2);
        android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_CONTINUE_USER_SWITCH, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        this.mHandler.removeMessages(130);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(130, i, i2));
        userState.switching = false;
        stopGuestOrEphemeralUserIfBackground(i);
        stopUserOnSwitchIfEnforced(i);
        timingsTraceAndSlog.traceEnd();
    }

    @com.android.internal.annotations.VisibleForTesting
    void completeUserSwitch(final int i, final int i2) {
        final java.lang.Runnable runnable = new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.this.lambda$completeUserSwitch$16(i, i2);
            }
        };
        if (isUserSwitchUiEnabled()) {
            if (this.mInjector.getKeyguardManager().isDeviceSecure(i2)) {
                showKeyguard(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.UserController.this.lambda$completeUserSwitch$17(runnable);
                    }
                });
                return;
            } else {
                dismissKeyguard(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda19
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.UserController.this.lambda$completeUserSwitch$18(runnable);
                    }
                });
                return;
            }
        }
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$completeUserSwitch$16(int i, int i2) {
        this.mHandler.removeMessages(80);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(80, i, i2));
    }

    protected void showKeyguard(java.lang.Runnable runnable) {
        final com.android.server.am.UserController.Injector injector = this.mInjector;
        java.util.Objects.requireNonNull(injector);
        runWithTimeout(new java.util.function.Consumer() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.am.UserController.Injector.this.showKeyguard((java.lang.Runnable) obj);
            }
        }, SHOW_KEYGUARD_TIMEOUT_MS, runnable, new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.lambda$showKeyguard$19();
            }
        }, "showKeyguard");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$showKeyguard$19() {
        throw new java.lang.RuntimeException("Keyguard is not shown in 20000 ms.");
    }

    protected void dismissKeyguard(java.lang.Runnable runnable) {
        final com.android.server.am.UserController.Injector injector = this.mInjector;
        java.util.Objects.requireNonNull(injector);
        runWithTimeout(new java.util.function.Consumer() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.am.UserController.Injector.this.dismissKeyguard((java.lang.Runnable) obj);
            }
        }, 2000, runnable, runnable, "dismissKeyguard");
    }

    private void runWithTimeout(java.util.function.Consumer<java.lang.Runnable> consumer, final int i, final java.lang.Runnable runnable, final java.lang.Runnable runnable2, final java.lang.String str) {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
        asyncTraceBegin(str, 0);
        this.mHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.lambda$runWithTimeout$20(atomicInteger, str, i, runnable2);
            }
        }, i);
        consumer.accept(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.am.UserController.lambda$runWithTimeout$21(atomicInteger, str, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runWithTimeout$20(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.lang.String str, int i, java.lang.Runnable runnable) {
        if (atomicInteger.compareAndSet(0, 1)) {
            asyncTraceEnd(str, 0);
            com.android.server.utils.Slogf.w(TAG, "Timeout: %s did not finish in %d ms", str, java.lang.Integer.valueOf(i));
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$runWithTimeout$21(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.lang.String str, java.lang.Runnable runnable) {
        if (atomicInteger.compareAndSet(0, 2)) {
            asyncTraceEnd(str, 0);
            runnable.run();
        }
    }

    private void moveUserToForeground(com.android.server.am.UserState userState, int i) {
        if (this.mInjector.taskSupervisorSwitchUser(i, userState)) {
            this.mInjector.startHomeActivity(i, "moveUserToForeground");
        } else {
            this.mInjector.taskSupervisorResumeFocusedStackTopActivity();
        }
        com.android.server.am.EventLogTags.writeAmSwitchUser(i);
    }

    void sendUserStartedBroadcast(int i, int i2, int i3) {
        if (i == 0) {
            synchronized (this.mLock) {
                try {
                    if (this.mIsBroadcastSentForSystemUserStarted) {
                        return;
                    } else {
                        this.mIsBroadcastSentForSystemUserStarted = true;
                    }
                } finally {
                }
            }
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.USER_STARTED");
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, i2, i3, i);
    }

    void sendUserStartingBroadcast(int i, int i2, int i3) {
        if (i == 0) {
            synchronized (this.mLock) {
                try {
                    if (this.mIsBroadcastSentForSystemUserStarting) {
                        return;
                    } else {
                        this.mIsBroadcastSentForSystemUserStarting = true;
                    }
                } finally {
                }
            }
        }
        android.content.Intent intent = new android.content.Intent("android.intent.action.USER_STARTING");
        intent.addFlags(1073741824);
        intent.putExtra("android.intent.extra.user_handle", i);
        this.mInjector.broadcastIntent(intent, null, new android.content.IIntentReceiver.Stub() { // from class: com.android.server.am.UserController.8
            public void performReceive(android.content.Intent intent2, int i4, java.lang.String str, android.os.Bundle bundle, boolean z, boolean z2, int i5) throws android.os.RemoteException {
            }
        }, 0, null, null, new java.lang.String[]{"android.permission.INTERACT_ACROSS_USERS"}, -1, null, true, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, i2, i3, -1);
    }

    void sendUserSwitchBroadcasts(int i, int i2) {
        java.lang.String str;
        java.lang.String str2;
        int callingUid = android.os.Binder.getCallingUid();
        int callingPid = android.os.Binder.getCallingPid();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        java.lang.String str3 = "android.intent.extra.USER";
        java.lang.String str4 = "android.intent.extra.user_handle";
        int i3 = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE;
        if (i < 0) {
            str = "android.intent.extra.user_handle";
            str2 = "android.intent.extra.USER";
        } else {
            try {
                java.util.List<android.content.pm.UserInfo> profiles = this.mInjector.getUserManager().getProfiles(i, false);
                int size = profiles.size();
                int i4 = 0;
                while (i4 < size) {
                    int i5 = profiles.get(i4).id;
                    android.content.Intent intent = new android.content.Intent("android.intent.action.USER_BACKGROUND");
                    intent.addFlags(i3);
                    intent.putExtra(str4, i5);
                    intent.putExtra(str3, android.os.UserHandle.of(i5));
                    this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, callingUid, callingPid, i5);
                    i4++;
                    size = size;
                    str4 = str4;
                    str3 = str3;
                    i3 = com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE;
                }
                str = str4;
                str2 = str3;
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        if (i2 >= 0) {
            java.util.List<android.content.pm.UserInfo> profiles2 = this.mInjector.getUserManager().getProfiles(i2, false);
            int size2 = profiles2.size();
            int i6 = 0;
            while (i6 < size2) {
                int i7 = profiles2.get(i6).id;
                android.content.Intent intent2 = new android.content.Intent("android.intent.action.USER_FOREGROUND");
                intent2.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
                java.lang.String str5 = str;
                intent2.putExtra(str5, i7);
                java.lang.String str6 = str2;
                intent2.putExtra(str6, android.os.UserHandle.of(i7));
                this.mInjector.broadcastIntent(intent2, null, null, 0, null, null, null, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, callingUid, callingPid, i7);
                i6++;
                size2 = size2;
                str2 = str6;
                str = str5;
            }
            android.content.Intent intent3 = new android.content.Intent("android.intent.action.USER_SWITCHED");
            intent3.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
            intent3.putExtra(str, i2);
            intent3.putExtra(str2, android.os.UserHandle.of(i2));
            this.mInjector.broadcastIntent(intent3, null, null, 0, null, null, new java.lang.String[]{"android.permission.MANAGE_USERS"}, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, callingUid, callingPid, -1);
        }
        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    private void broadcastProfileAccessibleStateChanged(int i, int i2, java.lang.String str) {
        android.content.Intent intent = new android.content.Intent(str);
        intent.putExtra("android.intent.extra.USER", android.os.UserHandle.of(i));
        intent.addFlags(com.android.server.tare.AlarmManagerEconomicPolicy.ACTION_ALARM_WAKEUP_EXACT_ALLOW_WHILE_IDLE);
        this.mInjector.broadcastIntent(intent, null, null, 0, null, null, null, -1, null, false, false, com.android.server.am.ActivityManagerService.MY_PID, 1000, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid(), i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0106  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int handleIncomingUser(int i, int i2, int i3, boolean z, int i4, java.lang.String str, java.lang.String str2) {
        boolean z2;
        int userId = android.os.UserHandle.getUserId(i2);
        if (userId == i3) {
            return i3;
        }
        int unsafeConvertIncomingUser = unsafeConvertIncomingUser(i3);
        if (i2 != 0 && i2 != 1000) {
            boolean isSameProfileGroup = isSameProfileGroup(userId, unsafeConvertIncomingUser);
            boolean z3 = true;
            if (this.mInjector.isCallerRecents(i2) && isSameProfileGroup) {
                z2 = true;
            } else if (this.mInjector.checkComponentPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2, -1, true) == 0) {
                z2 = true;
                z3 = true;
            } else if (i4 == 2) {
                z3 = false;
                z2 = true;
            } else if (canInteractWithAcrossProfilesPermission(i4, isSameProfileGroup, i, i2, str2)) {
                z2 = true;
                z3 = true;
            } else if (this.mInjector.checkComponentPermission("android.permission.INTERACT_ACROSS_USERS", i, i2, -1, true) != 0) {
                z3 = false;
                z2 = true;
            } else {
                if (i4 != 0) {
                    if (i4 == 3) {
                        z2 = true;
                    } else {
                        z2 = true;
                        if (i4 == 1) {
                            z3 = isSameProfileGroup;
                        } else {
                            throw new java.lang.IllegalArgumentException("Unknown mode: " + i4);
                        }
                    }
                } else {
                    z2 = true;
                }
                z3 = z2;
            }
            if (!z3) {
                if (i3 != -3) {
                    java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                    sb.append("Permission Denial: ");
                    sb.append(str);
                    if (str2 != null) {
                        sb.append(" from ");
                        sb.append(str2);
                    }
                    sb.append(" asks to run as user ");
                    sb.append(i3);
                    sb.append(" but is calling from uid ");
                    android.os.UserHandle.formatUid(sb, i2);
                    sb.append("; this requires ");
                    sb.append("android.permission.INTERACT_ACROSS_USERS_FULL");
                    if (i4 != 2) {
                        if (i4 == 0 || i4 == 3 || (i4 == z2 && isSameProfileGroup)) {
                            sb.append(" or ");
                            sb.append("android.permission.INTERACT_ACROSS_USERS");
                        }
                        if (isSameProfileGroup && i4 == 3) {
                            sb.append(" or ");
                            sb.append("android.permission.INTERACT_ACROSS_PROFILES");
                        }
                    }
                    java.lang.String sb2 = sb.toString();
                    com.android.server.utils.Slogf.w(TAG, sb2);
                    throw new java.lang.SecurityException(sb2);
                }
                if (!z) {
                    ensureNotSpecialUser(userId);
                }
                if (i2 != 2000 && userId >= 0 && hasUserRestriction("no_debugging_features", userId)) {
                    throw new java.lang.SecurityException("Shell does not have permission to access user " + userId + "\n " + android.os.Debug.getCallers(3));
                }
                return userId;
            }
        }
        userId = unsafeConvertIncomingUser;
        if (!z) {
        }
        if (i2 != 2000) {
        }
        return userId;
    }

    private boolean canInteractWithAcrossProfilesPermission(int i, boolean z, int i2, int i3, java.lang.String str) {
        if (i == 3 && z) {
            return this.mInjector.checkPermissionForPreflight("android.permission.INTERACT_ACROSS_PROFILES", i2, i3, str);
        }
        return false;
    }

    int unsafeConvertIncomingUser(int i) {
        return (i == -2 || i == -3) ? getCurrentUserId() : i;
    }

    void ensureNotSpecialUser(int i) {
        if (i >= 0) {
            return;
        }
        throw new java.lang.IllegalArgumentException("Call does not support special user #" + i);
    }

    void registerUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver, java.lang.String str) {
        java.util.Objects.requireNonNull(str, "Observer name cannot be null");
        checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "registerUserSwitchObserver");
        this.mUserSwitchObservers.register(iUserSwitchObserver, str);
    }

    void sendForegroundProfileChanged(int i) {
        this.mHandler.removeMessages(70);
        this.mHandler.obtainMessage(70, i, 0).sendToTarget();
    }

    void unregisterUserSwitchObserver(android.app.IUserSwitchObserver iUserSwitchObserver) {
        this.mUserSwitchObservers.unregister(iUserSwitchObserver);
    }

    com.android.server.am.UserState getStartedUserState(int i) {
        com.android.server.am.UserState userState;
        synchronized (this.mLock) {
            userState = this.mStartedUsers.get(i);
        }
        return userState;
    }

    boolean hasStartedUserState(int i) {
        boolean z;
        synchronized (this.mLock) {
            z = this.mStartedUsers.get(i) != null;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateStartedUserArrayLU() {
        int i = 0;
        for (int i2 = 0; i2 < this.mStartedUsers.size(); i2++) {
            com.android.server.am.UserState valueAt = this.mStartedUsers.valueAt(i2);
            if (valueAt.state != 4 && valueAt.state != 5) {
                i++;
            }
        }
        this.mStartedUserArray = new int[i];
        int i3 = 0;
        for (int i4 = 0; i4 < this.mStartedUsers.size(); i4++) {
            com.android.server.am.UserState valueAt2 = this.mStartedUsers.valueAt(i4);
            if (valueAt2.state != 4 && valueAt2.state != 5) {
                this.mStartedUserArray[i3] = this.mStartedUsers.keyAt(i4);
                i3++;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setAllowUserUnlocking(boolean z) {
        this.mAllowUserUnlocking = z;
    }

    void onBootComplete(android.content.IIntentReceiver iIntentReceiver) {
        android.util.SparseArray<com.android.server.am.UserState> clone;
        boolean z = true;
        setAllowUserUnlocking(true);
        synchronized (this.mLock) {
            clone = this.mStartedUsers.clone();
        }
        if (clone.keyAt(0) != 0) {
            z = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z);
        for (int i = 0; i < clone.size(); i++) {
            int keyAt = clone.keyAt(i);
            com.android.server.am.UserState valueAt = clone.valueAt(i);
            if (!this.mInjector.isHeadlessSystemUserMode()) {
                finishUserBoot(valueAt, iIntentReceiver);
            } else {
                sendLockedBootCompletedBroadcast(iIntentReceiver, keyAt);
                maybeUnlockUser(keyAt);
            }
        }
    }

    void onSystemReady() {
        this.mInjector.getUserManagerInternal().addUserLifecycleListener(this.mUserLifecycleListener);
        updateProfileRelatedCaches();
        this.mInjector.reportCurWakefulnessUsageEvent();
    }

    void onSystemUserStarting() {
        if (!this.mInjector.isHeadlessSystemUserMode()) {
            this.mInjector.onUserStarting(0);
            this.mInjector.onSystemUserVisibilityChanged(true);
        }
    }

    private void updateProfileRelatedCaches() {
        java.util.List<android.content.pm.UserInfo> profiles = this.mInjector.getUserManager().getProfiles(getCurrentUserId(), false);
        int size = profiles.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = profiles.get(i).id;
        }
        java.util.List<android.content.pm.UserInfo> users = this.mInjector.getUserManager().getUsers(false);
        synchronized (this.mLock) {
            try {
                this.mCurrentProfileIds = iArr;
                this.mUserProfileGroupIds.clear();
                for (int i2 = 0; i2 < users.size(); i2++) {
                    android.content.pm.UserInfo userInfo = users.get(i2);
                    if (userInfo.profileGroupId != -10000) {
                        this.mUserProfileGroupIds.put(userInfo.id, userInfo.profileGroupId);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    int[] getStartedUserArray() {
        int[] iArr;
        synchronized (this.mLock) {
            iArr = this.mStartedUserArray;
        }
        return iArr;
    }

    boolean isUserRunning(int i, int i2) {
        com.android.server.am.UserState startedUserState = getStartedUserState(i);
        if (startedUserState == null) {
            return false;
        }
        if ((i2 & 1) != 0) {
            return true;
        }
        if ((i2 & 2) != 0) {
            switch (startedUserState.state) {
                case 0:
                case 1:
                    return true;
                default:
                    return false;
            }
        }
        if ((i2 & 8) != 0) {
            switch (startedUserState.state) {
                case 2:
                case 3:
                    return true;
                case 4:
                case 5:
                    return android.os.storage.StorageManager.isCeStorageUnlocked(i);
                default:
                    return false;
            }
        }
        if ((i2 & 4) == 0) {
            return (startedUserState.state == 4 || startedUserState.state == 5) ? false : true;
        }
        switch (startedUserState.state) {
            case 3:
                return true;
            case 4:
            case 5:
                return android.os.storage.StorageManager.isCeStorageUnlocked(i);
            default:
                return false;
        }
    }

    boolean isSystemUserStarted() {
        synchronized (this.mLock) {
            try {
                com.android.server.am.UserState userState = this.mStartedUsers.get(0);
                if (userState == null) {
                    return false;
                }
                return userState.state == 1 || userState.state == 2 || userState.state == 3;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void checkGetCurrentUserPermissions() {
        if (this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS") != 0 && this.mInjector.checkCallingPermission("android.permission.INTERACT_ACROSS_USERS_FULL") != 0) {
            java.lang.String str = "Permission Denial: getCurrentUser() from pid=" + android.os.Binder.getCallingPid() + ", uid=" + android.os.Binder.getCallingUid() + " requires android.permission.INTERACT_ACROSS_USERS";
            com.android.server.utils.Slogf.w(TAG, str);
            throw new java.lang.SecurityException(str);
        }
    }

    android.content.pm.UserInfo getCurrentUser() {
        android.content.pm.UserInfo currentUserLU;
        checkGetCurrentUserPermissions();
        if (this.mTargetUserId == -10000) {
            return getUserInfo(this.mCurrentUserId);
        }
        synchronized (this.mLock) {
            currentUserLU = getCurrentUserLU();
        }
        return currentUserLU;
    }

    int getCurrentUserIdChecked() {
        checkGetCurrentUserPermissions();
        if (this.mTargetUserId == -10000) {
            return this.mCurrentUserId;
        }
        return getCurrentOrTargetUserId();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.UserInfo getCurrentUserLU() {
        return getUserInfo(getCurrentOrTargetUserIdLU());
    }

    int getCurrentOrTargetUserId() {
        int currentOrTargetUserIdLU;
        synchronized (this.mLock) {
            currentOrTargetUserIdLU = getCurrentOrTargetUserIdLU();
        }
        return currentOrTargetUserIdLU;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getCurrentOrTargetUserIdLU() {
        return this.mTargetUserId != -10000 ? this.mTargetUserId : this.mCurrentUserId;
    }

    android.util.Pair<java.lang.Integer, java.lang.Integer> getCurrentAndTargetUserIds() {
        android.util.Pair<java.lang.Integer, java.lang.Integer> pair;
        synchronized (this.mLock) {
            pair = new android.util.Pair<>(java.lang.Integer.valueOf(this.mCurrentUserId), java.lang.Integer.valueOf(this.mTargetUserId));
        }
        return pair;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int getCurrentUserIdLU() {
        return this.mCurrentUserId;
    }

    int getCurrentUserId() {
        int i;
        synchronized (this.mLock) {
            i = this.mCurrentUserId;
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isCurrentUserLU(int i) {
        return i == getCurrentOrTargetUserIdLU();
    }

    private boolean isAlwaysVisibleUser(int i) {
        android.content.pm.UserProperties userProperties = getUserProperties(i);
        return userProperties != null && userProperties.getAlwaysVisible();
    }

    int[] getUsers() {
        com.android.server.pm.UserManagerService userManager = this.mInjector.getUserManager();
        return userManager != null ? userManager.getUserIds() : new int[]{0};
    }

    private android.content.pm.UserInfo getUserInfo(int i) {
        return this.mInjector.getUserManager().getUserInfo(i);
    }

    @android.annotation.Nullable
    private android.content.pm.UserProperties getUserProperties(int i) {
        return this.mInjector.getUserManagerInternal().getUserProperties(i);
    }

    int[] getUserIds() {
        return this.mInjector.getUserManager().getUserIds();
    }

    int[] expandUserId(int i) {
        if (i != -1) {
            return new int[]{i};
        }
        return getUsers();
    }

    boolean exists(int i) {
        return this.mInjector.getUserManager().exists(i);
    }

    private void checkCallingPermission(java.lang.String str, java.lang.String str2) {
        checkCallingHasOneOfThosePermissions(str2, str);
    }

    private void checkCallingHasOneOfThosePermissions(java.lang.String str, java.lang.String... strArr) {
        for (java.lang.String str2 : strArr) {
            if (this.mInjector.checkCallingPermission(str2) == 0) {
                return;
            }
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Permission denial: ");
        sb.append(str);
        sb.append("() from pid=");
        sb.append(android.os.Binder.getCallingPid());
        sb.append(", uid=");
        sb.append(android.os.Binder.getCallingUid());
        sb.append(" requires ");
        sb.append(strArr.length == 1 ? strArr[0] : "one of " + java.util.Arrays.toString(strArr));
        java.lang.String sb2 = sb.toString();
        com.android.server.utils.Slogf.w(TAG, sb2);
        throw new java.lang.SecurityException(sb2);
    }

    private void enforceShellRestriction(java.lang.String str, int i) {
        if (android.os.Binder.getCallingUid() == 2000) {
            if (i < 0 || hasUserRestriction(str, i)) {
                throw new java.lang.SecurityException("Shell does not have permission to access user " + i);
            }
        }
    }

    boolean hasUserRestriction(java.lang.String str, int i) {
        return this.mInjector.getUserManager().hasUserRestriction(str, i);
    }

    boolean isSameProfileGroup(int i, int i2) {
        boolean z = true;
        if (i == i2) {
            return true;
        }
        synchronized (this.mLock) {
            int i3 = this.mUserProfileGroupIds.get(i, com.android.server.am.ProcessList.INVALID_ADJ);
            int i4 = this.mUserProfileGroupIds.get(i2, com.android.server.am.ProcessList.INVALID_ADJ);
            if (i3 == -10000 || i3 != i4) {
                z = false;
            }
        }
        return z;
    }

    boolean isUserOrItsParentRunning(int i) {
        synchronized (this.mLock) {
            try {
                if (isUserRunning(i, 0)) {
                    return true;
                }
                int i2 = this.mUserProfileGroupIds.get(i, com.android.server.am.ProcessList.INVALID_ADJ);
                if (i2 == -10000) {
                    return false;
                }
                return isUserRunning(i2, 0);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isCurrentProfile(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = com.android.internal.util.ArrayUtils.contains(this.mCurrentProfileIds, i);
        }
        return contains;
    }

    int[] getCurrentProfileIds() {
        int[] iArr;
        synchronized (this.mLock) {
            iArr = this.mCurrentProfileIds;
        }
        return iArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserAdded(android.content.pm.UserInfo userInfo) {
        if (!userInfo.isProfile()) {
            return;
        }
        synchronized (this.mLock) {
            try {
                if (userInfo.profileGroupId == this.mCurrentUserId) {
                    this.mCurrentProfileIds = com.android.internal.util.ArrayUtils.appendInt(this.mCurrentProfileIds, userInfo.id);
                }
                if (userInfo.profileGroupId != -10000) {
                    this.mUserProfileGroupIds.put(userInfo.id, userInfo.profileGroupId);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                for (int size = this.mUserProfileGroupIds.size() - 1; size >= 0; size--) {
                    if (this.mUserProfileGroupIds.keyAt(size) != i && this.mUserProfileGroupIds.valueAt(size) != i) {
                    }
                    this.mUserProfileGroupIds.removeAt(size);
                }
                this.mCurrentProfileIds = com.android.internal.util.ArrayUtils.removeInt(this.mCurrentProfileIds, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected boolean shouldConfirmCredentials(int i) {
        android.content.pm.UserProperties userProperties;
        if (getStartedUserState(i) == null || (userProperties = getUserProperties(i)) == null || !userProperties.isCredentialShareableWithParent()) {
            return false;
        }
        if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            android.app.KeyguardManager keyguardManager = this.mInjector.getKeyguardManager();
            return keyguardManager.isDeviceLocked(i) && keyguardManager.isDeviceSecure(i);
        }
        return isUserRunning(i, 2);
    }

    boolean isLockScreenDisabled(int i) {
        return this.mLockPatternUtils.isLockScreenDisabled(i);
    }

    void setSwitchingFromSystemUserMessage(java.lang.String str) {
        synchronized (this.mLock) {
            this.mSwitchingFromSystemUserMessage = str;
        }
    }

    void setSwitchingToSystemUserMessage(java.lang.String str) {
        synchronized (this.mLock) {
            this.mSwitchingToSystemUserMessage = str;
        }
    }

    java.lang.String getSwitchingFromSystemUserMessage() {
        checkHasManageUsersPermission("getSwitchingFromSystemUserMessage()");
        return getSwitchingFromSystemUserMessageUnchecked();
    }

    java.lang.String getSwitchingToSystemUserMessage() {
        checkHasManageUsersPermission("getSwitchingToSystemUserMessage()");
        return getSwitchingToSystemUserMessageUnchecked();
    }

    private java.lang.String getSwitchingFromSystemUserMessageUnchecked() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mSwitchingFromSystemUserMessage;
        }
        return str;
    }

    private java.lang.String getSwitchingToSystemUserMessageUnchecked() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mSwitchingToSystemUserMessage;
        }
        return str;
    }

    private void checkHasManageUsersPermission(java.lang.String str) {
        if (this.mInjector.checkCallingPermission("android.permission.MANAGE_USERS") == -1) {
            throw new java.lang.SecurityException("You need MANAGE_USERS permission to call " + str);
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        synchronized (this.mLock) {
            try {
                long start = protoOutputStream.start(j);
                for (int i = 0; i < this.mStartedUsers.size(); i++) {
                    com.android.server.am.UserState valueAt = this.mStartedUsers.valueAt(i);
                    long start2 = protoOutputStream.start(2246267895809L);
                    protoOutputStream.write(1120986464257L, valueAt.mHandle.getIdentifier());
                    valueAt.dumpDebug(protoOutputStream, 1146756268034L);
                    protoOutputStream.end(start2);
                }
                for (int i2 = 0; i2 < this.mStartedUserArray.length; i2++) {
                    protoOutputStream.write(2220498092034L, this.mStartedUserArray[i2]);
                }
                for (int i3 = 0; i3 < this.mUserLru.size(); i3++) {
                    protoOutputStream.write(2220498092035L, this.mUserLru.get(i3).intValue());
                }
                if (this.mUserProfileGroupIds.size() > 0) {
                    for (int i4 = 0; i4 < this.mUserProfileGroupIds.size(); i4++) {
                        long start3 = protoOutputStream.start(2246267895812L);
                        protoOutputStream.write(1120986464257L, this.mUserProfileGroupIds.keyAt(i4));
                        protoOutputStream.write(1120986464258L, this.mUserProfileGroupIds.valueAt(i4));
                        protoOutputStream.end(start3);
                    }
                }
                protoOutputStream.write(1120986464261L, this.mCurrentUserId);
                for (int i5 = 0; i5 < this.mCurrentProfileIds.length; i5++) {
                    protoOutputStream.write(2220498092038L, this.mCurrentProfileIds[i5]);
                }
                protoOutputStream.end(start);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("  mStartedUsers:");
                for (int i = 0; i < this.mStartedUsers.size(); i++) {
                    com.android.server.am.UserState valueAt = this.mStartedUsers.valueAt(i);
                    printWriter.print("    User #");
                    printWriter.print(valueAt.mHandle.getIdentifier());
                    printWriter.print(": ");
                    valueAt.dump("", printWriter);
                }
                printWriter.print("  mStartedUserArray: [");
                for (int i2 = 0; i2 < this.mStartedUserArray.length; i2++) {
                    if (i2 > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print(this.mStartedUserArray[i2]);
                }
                printWriter.println("]");
                printWriter.print("  mUserLru: [");
                for (int i3 = 0; i3 < this.mUserLru.size(); i3++) {
                    if (i3 > 0) {
                        printWriter.print(", ");
                    }
                    printWriter.print(this.mUserLru.get(i3));
                }
                printWriter.println("]");
                if (this.mUserProfileGroupIds.size() > 0) {
                    printWriter.println("  mUserProfileGroupIds:");
                    for (int i4 = 0; i4 < this.mUserProfileGroupIds.size(); i4++) {
                        printWriter.print("    User #");
                        printWriter.print(this.mUserProfileGroupIds.keyAt(i4));
                        printWriter.print(" -> profile #");
                        printWriter.println(this.mUserProfileGroupIds.valueAt(i4));
                    }
                }
                printWriter.println("  mCurrentProfileIds:" + java.util.Arrays.toString(this.mCurrentProfileIds));
                printWriter.println("  mCurrentUserId:" + this.mCurrentUserId);
                printWriter.println("  mTargetUserId:" + this.mTargetUserId);
                printWriter.println("  mLastActiveUsersForDelayedLocking:" + this.mLastActiveUsersForDelayedLocking);
                printWriter.println("  mDelayUserDataLocking:" + this.mDelayUserDataLocking);
                printWriter.println("  mAllowUserUnlocking:" + this.mAllowUserUnlocking);
                printWriter.println("  shouldStopUserOnSwitch():" + shouldStopUserOnSwitch());
                printWriter.println("  mStopUserOnSwitch:" + this.mStopUserOnSwitch);
                printWriter.println("  mMaxRunningUsers:" + this.mMaxRunningUsers);
                printWriter.println("  mUserSwitchUiEnabled:" + this.mUserSwitchUiEnabled);
                printWriter.println("  mInitialized:" + this.mInitialized);
                printWriter.println("  mIsBroadcastSentForSystemUserStarted:" + this.mIsBroadcastSentForSystemUserStarted);
                printWriter.println("  mIsBroadcastSentForSystemUserStarting:" + this.mIsBroadcastSentForSystemUserStarting);
                if (this.mSwitchingFromSystemUserMessage != null) {
                    printWriter.println("  mSwitchingFromSystemUserMessage: " + this.mSwitchingFromSystemUserMessage);
                }
                if (this.mSwitchingToSystemUserMessage != null) {
                    printWriter.println("  mSwitchingToSystemUserMessage: " + this.mSwitchingToSystemUserMessage);
                }
                printWriter.println("  mLastUserUnlockingUptime: " + this.mLastUserUnlockingUptime);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 10:
                dispatchUserSwitch((com.android.server.am.UserState) message.obj, message.arg1, message.arg2);
                break;
            case 20:
                continueUserSwitch((com.android.server.am.UserState) message.obj, message.arg1, message.arg2);
                break;
            case 30:
                timeoutUserSwitch((com.android.server.am.UserState) message.obj, message.arg1, message.arg2);
                break;
            case 40:
                startProfiles();
                break;
            case 50:
                this.mInjector.batteryStatsServiceNoteEvent(32775, java.lang.Integer.toString(message.arg1), message.arg1);
                logUserJourneyBegin(message.arg1, 3);
                this.mInjector.onUserStarting(message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 1, 5000);
                this.mInjector.getUserJourneyLogger().logUserJourneyFinish(-1, getUserInfo(message.arg1), 3);
                break;
            case 60:
                this.mInjector.batteryStatsServiceNoteEvent(16392, java.lang.Integer.toString(message.arg2), message.arg2);
                this.mInjector.batteryStatsServiceNoteEvent(32776, java.lang.Integer.toString(message.arg1), message.arg1);
                this.mInjector.getSystemServiceManager().onUserSwitching(message.arg2, message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 4, 5000);
                break;
            case 70:
                dispatchForegroundProfileChanged(message.arg1);
                break;
            case 80:
                dispatchUserSwitchComplete(message.arg1, message.arg2);
                com.android.server.pm.UserJourneyLogger.UserJourneySession logUserSwitchJourneyFinish = this.mInjector.getUserJourneyLogger().logUserSwitchJourneyFinish(message.arg1, getUserInfo(message.arg2));
                if (logUserSwitchJourneyFinish != null) {
                    this.mHandler.removeMessages(200, logUserSwitchJourneyFinish);
                    break;
                }
                break;
            case 90:
                timeoutUserSwitchCallbacks(message.arg1, message.arg2);
                break;
            case 100:
                final int i = message.arg1;
                this.mInjector.getSystemServiceManager().onUserUnlocking(i);
                com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.am.UserController.this.lambda$handleMessage$22(i);
                    }
                });
                this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(message.arg1, 5, 2);
                this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(message.arg1, 6, 1);
                com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("finishUserUnlocked-" + i);
                finishUserUnlocked((com.android.server.am.UserState) message.obj);
                timingsTraceAndSlog.traceEnd();
                break;
            case 105:
                this.mInjector.getSystemServiceManager().onUserUnlocked(message.arg1);
                scheduleOnUserCompletedEvent(message.arg1, 2, this.mCurrentUserId != message.arg1 ? 1000 : 5000);
                this.mInjector.getUserJourneyLogger().logUserLifecycleEvent(message.arg1, 6, 2);
                break;
            case 110:
                dispatchLockedBootComplete(message.arg1);
                break;
            case 120:
                logUserJourneyBegin(message.arg1, 2);
                startUserInForeground(message.arg1);
                break;
            case 130:
                completeUserSwitch(message.arg1, message.arg2);
                break;
            case 140:
                reportOnUserCompletedEvent((java.lang.Integer) message.obj);
                break;
            case 200:
                this.mInjector.getUserJourneyLogger().finishAndClearIncompleteUserJourney(message.arg1, message.arg2);
                this.mHandler.removeMessages(200, message.obj);
                break;
            case 1000:
                android.util.Pair<android.content.pm.UserInfo, android.content.pm.UserInfo> pair = (android.util.Pair) message.obj;
                logUserJourneyBegin(((android.content.pm.UserInfo) pair.second).id, 1);
                showUserSwitchDialog(pair);
                break;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleMessage$22(int i) {
        this.mInjector.loadUserRecents(i);
    }

    @com.android.internal.annotations.VisibleForTesting
    void scheduleOnUserCompletedEvent(int i, int i2, int i3) {
        if (i2 != 0) {
            synchronized (this.mCompletedEventTypes) {
                this.mCompletedEventTypes.put(i, i2 | this.mCompletedEventTypes.get(i, 0));
            }
        }
        java.lang.Integer valueOf = java.lang.Integer.valueOf(i);
        this.mHandler.removeEqualMessages(140, valueOf);
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(140, valueOf), i3);
    }

    @com.android.internal.annotations.VisibleForTesting
    void reportOnUserCompletedEvent(java.lang.Integer num) {
        int i;
        int i2;
        this.mHandler.removeEqualMessages(140, num);
        synchronized (this.mCompletedEventTypes) {
            i = 0;
            i2 = this.mCompletedEventTypes.get(num.intValue(), 0);
            this.mCompletedEventTypes.delete(num.intValue());
        }
        synchronized (this.mLock) {
            try {
                com.android.server.am.UserState userState = this.mStartedUsers.get(num.intValue());
                if (userState != null && userState.state != 5) {
                    i = 1;
                }
                if (userState != null && userState.state == 3) {
                    i |= 2;
                }
                if (num.intValue() == this.mCurrentUserId) {
                    i |= 4;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        com.android.server.utils.Slogf.i(TAG, "reportOnUserCompletedEvent(%d): stored=%s, eligible=%s", num, java.lang.Integer.toBinaryString(i2), java.lang.Integer.toBinaryString(i));
        this.mInjector.systemServiceManagerOnUserCompletedEvent(num.intValue(), i2 & i);
    }

    private void logUserJourneyBegin(int i, @com.android.server.pm.UserJourneyLogger.UserJourney int i2) {
        com.android.server.pm.UserJourneyLogger.UserJourneySession finishAndClearIncompleteUserJourney = this.mInjector.getUserJourneyLogger().finishAndClearIncompleteUserJourney(i, i2);
        if (finishAndClearIncompleteUserJourney != null) {
            this.mHandler.removeMessages(200, finishAndClearIncompleteUserJourney);
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(200, i, i2, this.mInjector.getUserJourneyLogger().logUserJourneyBegin(i, i2)), 90000L);
    }

    android.app.BroadcastOptions getTemporaryAppAllowlistBroadcastOptions(int i) {
        long j;
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if (activityManagerInternal == null) {
            j = 10000;
        } else {
            j = activityManagerInternal.getBootTimeTempAllowListDuration();
        }
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(j, 0, i, "");
        return makeBasic;
    }

    private static int getUserSwitchTimeoutMs() {
        java.lang.String str = android.os.SystemProperties.get("debug.usercontroller.user_switch_timeout_ms");
        if (!android.text.TextUtils.isEmpty(str)) {
            try {
                return java.lang.Integer.parseInt(str);
            } catch (java.lang.NumberFormatException e) {
                return 3000;
            }
        }
        return 3000;
    }

    private static void asyncTraceBegin(java.lang.String str, int i) {
        com.android.server.utils.Slogf.d(TAG, "%s - asyncTraceBegin(%d)", str, java.lang.Integer.valueOf(i));
        android.os.Trace.asyncTraceBegin(64L, str, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void asyncTraceEnd(java.lang.String str, int i) {
        android.os.Trace.asyncTraceEnd(64L, str, i);
        com.android.server.utils.Slogf.d(TAG, "%s - asyncTraceEnd(%d)", str, java.lang.Integer.valueOf(i));
    }

    public long getLastUserUnlockingUptime() {
        return this.mLastUserUnlockingUptime;
    }

    private static class UserProgressListener extends android.os.IProgressListener.Stub {
        private volatile long mUnlockStarted;

        private UserProgressListener() {
        }

        public void onStarted(int i, android.os.Bundle bundle) throws android.os.RemoteException {
            com.android.server.utils.Slogf.d(com.android.server.am.UserController.TAG, "Started unlocking user " + i);
            this.mUnlockStarted = android.os.SystemClock.uptimeMillis();
        }

        public void onProgress(int i, int i2, android.os.Bundle bundle) throws android.os.RemoteException {
            com.android.server.utils.Slogf.d(com.android.server.am.UserController.TAG, "Unlocking user " + i + " progress " + i2);
        }

        public void onFinished(int i, android.os.Bundle bundle) throws android.os.RemoteException {
            long uptimeMillis = android.os.SystemClock.uptimeMillis() - this.mUnlockStarted;
            if (i == 0) {
                new com.android.server.utils.TimingsTraceAndSlog().logDuration("SystemUserUnlock", uptimeMillis);
                return;
            }
            new com.android.server.utils.TimingsTraceAndSlog().logDuration("User" + i + "Unlock", uptimeMillis);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PendingUserStart {
        public final android.os.IProgressListener unlockListener;
        public final int userId;

        @com.android.server.pm.UserManagerInternal.UserStartMode
        public final int userStartMode;

        PendingUserStart(int i, @com.android.server.pm.UserManagerInternal.UserStartMode int i2, android.os.IProgressListener iProgressListener) {
            this.userId = i;
            this.userStartMode = i2;
            this.unlockListener = iProgressListener;
        }

        public java.lang.String toString() {
            return "PendingUserStart{userId=" + this.userId + ", userStartMode=" + com.android.server.pm.UserManagerInternal.userStartModeToString(this.userStartMode) + ", unlockListener=" + this.unlockListener + '}';
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        private android.os.Handler mHandler;
        private android.os.PowerManagerInternal mPowerManagerInternal;
        private final com.android.server.am.ActivityManagerService mService;
        private com.android.server.pm.UserManagerService mUserManager;
        private com.android.server.pm.UserManagerInternal mUserManagerInternal;

        @com.android.internal.annotations.GuardedBy({"mUserSwitchingDialogLock"})
        private com.android.server.am.UserSwitchingDialog mUserSwitchingDialog;
        private final java.lang.Object mUserSwitchingDialogLock = new java.lang.Object();

        Injector(com.android.server.am.ActivityManagerService activityManagerService) {
            this.mService = activityManagerService;
        }

        protected android.os.Handler getHandler(android.os.Handler.Callback callback) {
            android.os.Handler handler = new android.os.Handler(this.mService.mHandlerThread.getLooper(), callback);
            this.mHandler = handler;
            return handler;
        }

        protected android.os.Handler getUiHandler(android.os.Handler.Callback callback) {
            return new android.os.Handler(this.mService.mUiHandler.getLooper(), callback);
        }

        protected com.android.server.pm.UserJourneyLogger getUserJourneyLogger() {
            return getUserManager().getUserJourneyLogger();
        }

        protected android.content.Context getContext() {
            return this.mService.mContext;
        }

        protected com.android.internal.widget.LockPatternUtils getLockPatternUtils() {
            return new com.android.internal.widget.LockPatternUtils(getContext());
        }

        protected int broadcastIntent(android.content.Intent intent, java.lang.String str, android.content.IIntentReceiver iIntentReceiver, int i, java.lang.String str2, android.os.Bundle bundle, java.lang.String[] strArr, int i2, android.os.Bundle bundle2, boolean z, boolean z2, int i3, int i4, int i5, int i6, int i7) {
            boolean z3;
            int broadcastIntentLocked;
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ);
            if (intExtra == -10000) {
                intExtra = i7;
            }
            android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_SEND_USER_BROADCAST, java.lang.Integer.valueOf(intExtra), intent.getAction());
            if (!this.mService.mEnableModernQueue) {
                z3 = z;
            } else {
                z3 = false;
            }
            com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
            com.android.server.am.ActivityManagerService activityManagerService = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    timingsTraceAndSlog.traceBegin("broadcastIntent-" + i7 + "-" + intent.getAction());
                    broadcastIntentLocked = this.mService.broadcastIntentLocked(null, null, null, intent, str, iIntentReceiver, i, str2, bundle, strArr, null, null, i2, bundle2, z3, z2, i3, i4, i5, i6, i7);
                    timingsTraceAndSlog.traceEnd();
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
            return broadcastIntentLocked;
        }

        int checkCallingPermission(java.lang.String str) {
            return this.mService.checkCallingPermission(str);
        }

        com.android.server.wm.WindowManagerService getWindowManager() {
            return this.mService.mWindowManager;
        }

        com.android.server.wm.ActivityTaskManagerInternal getActivityTaskManagerInternal() {
            return this.mService.mAtmInternal;
        }

        void activityManagerOnUserStopped(int i) {
            ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).onUserStopped(i);
        }

        void systemServiceManagerOnUserStopped(int i) {
            getSystemServiceManager().onUserStopped(i);
        }

        void systemServiceManagerOnUserCompletedEvent(int i, int i2) {
            getSystemServiceManager().onUserCompletedEvent(i, i2);
        }

        protected com.android.server.pm.UserManagerService getUserManager() {
            if (this.mUserManager == null) {
                this.mUserManager = android.os.IUserManager.Stub.asInterface(android.os.ServiceManager.getService("user"));
            }
            return this.mUserManager;
        }

        com.android.server.pm.UserManagerInternal getUserManagerInternal() {
            if (this.mUserManagerInternal == null) {
                this.mUserManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
            }
            return this.mUserManagerInternal;
        }

        android.os.PowerManagerInternal getPowerManagerInternal() {
            if (this.mPowerManagerInternal == null) {
                this.mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
            }
            return this.mPowerManagerInternal;
        }

        android.app.KeyguardManager getKeyguardManager() {
            return (android.app.KeyguardManager) this.mService.mContext.getSystemService(android.app.KeyguardManager.class);
        }

        void batteryStatsServiceNoteEvent(int i, java.lang.String str, int i2) {
            this.mService.mBatteryStatsService.noteEvent(i, str, i2);
        }

        boolean isRuntimeRestarted() {
            return getSystemServiceManager().isRuntimeRestarted();
        }

        com.android.server.SystemServiceManager getSystemServiceManager() {
            return this.mService.mSystemServiceManager;
        }

        boolean isFirstBootOrUpgrade() {
            android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            try {
                if (!packageManager.isFirstBoot()) {
                    if (!packageManager.isDeviceUpgrading()) {
                        return false;
                    }
                }
                return true;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        void sendPreBootBroadcast(int i, boolean z, final java.lang.Runnable runnable) {
            android.util.EventLog.writeEvent(com.android.server.am.EventLogTags.UC_SEND_USER_BROADCAST, java.lang.Integer.valueOf(i), "android.intent.action.PRE_BOOT_COMPLETED");
            new com.android.server.am.PreBootBroadcaster(this.mService, i, null, z) { // from class: com.android.server.am.UserController.Injector.1
                @Override // com.android.server.am.PreBootBroadcaster
                public void onFinished() {
                    runnable.run();
                }
            }.sendNext();
        }

        void activityManagerForceStopPackage(int i, java.lang.String str) {
            com.android.server.am.ActivityManagerService activityManagerService = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    this.mService.forceStopPackageLocked(null, -1, false, false, true, false, false, false, i, str);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        int checkComponentPermission(java.lang.String str, int i, int i2, int i3, boolean z) {
            return com.android.server.am.ActivityManagerService.checkComponentPermission(str, i, i2, i3, z);
        }

        boolean checkPermissionForPreflight(java.lang.String str, int i, int i2, java.lang.String str2) {
            return android.content.PermissionChecker.checkPermissionForPreflight(getContext(), str, i, i2, str2) == 0;
        }

        protected void startHomeActivity(int i, java.lang.String str) {
            this.mService.mAtmInternal.startHomeActivity(i, str);
        }

        void startUserWidgets(final int i) {
            final android.appwidget.AppWidgetManagerInternal appWidgetManagerInternal = (android.appwidget.AppWidgetManagerInternal) com.android.server.LocalServices.getService(android.appwidget.AppWidgetManagerInternal.class);
            if (appWidgetManagerInternal != null) {
                com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.am.UserController$Injector$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        appWidgetManagerInternal.unlockUser(i);
                    }
                });
            }
        }

        void updateUserConfiguration() {
            this.mService.mAtmInternal.updateUserConfiguration();
        }

        void clearBroadcastQueueForUser(int i) {
            com.android.server.am.ActivityManagerService activityManagerService = this.mService;
            com.android.server.am.ActivityManagerService.boostPriorityForLockedSection();
            synchronized (activityManagerService) {
                try {
                    this.mService.clearBroadcastQueueForUserLocked(i);
                } catch (java.lang.Throwable th) {
                    com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.am.ActivityManagerService.resetPriorityAfterLockedSection();
        }

        void loadUserRecents(int i) {
            this.mService.mAtmInternal.loadRecentTasksForUser(i);
        }

        void startPersistentApps(int i) {
            this.mService.startPersistentApps(i);
        }

        void installEncryptionUnawareProviders(int i) {
            this.mService.mCpHelper.installEncryptionUnawareProviders(i);
        }

        void dismissUserSwitchingDialog(@android.annotation.Nullable java.lang.Runnable runnable) {
            synchronized (this.mUserSwitchingDialogLock) {
                try {
                    if (this.mUserSwitchingDialog != null) {
                        this.mUserSwitchingDialog.dismiss(runnable);
                        this.mUserSwitchingDialog = null;
                    } else if (runnable != null) {
                        runnable.run();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void showUserSwitchingDialog(android.content.pm.UserInfo userInfo, android.content.pm.UserInfo userInfo2, java.lang.String str, java.lang.String str2, @android.annotation.NonNull java.lang.Runnable runnable) {
            if (this.mService.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive")) {
                com.android.server.utils.Slogf.w(com.android.server.am.UserController.TAG, "Showing user switch dialog on UserController, it could cause a race condition if it's shown by CarSystemUI as well");
            }
            synchronized (this.mUserSwitchingDialogLock) {
                dismissUserSwitchingDialog(null);
                this.mUserSwitchingDialog = new com.android.server.am.UserSwitchingDialog(this.mService.mContext, userInfo, userInfo2, str, str2, getWindowManager());
                this.mUserSwitchingDialog.show(runnable);
            }
        }

        void reportGlobalUsageEvent(int i) {
            this.mService.reportGlobalUsageEvent(i);
        }

        void reportCurWakefulnessUsageEvent() {
            this.mService.reportCurWakefulnessUsageEvent();
        }

        void taskSupervisorRemoveUser(int i) {
            this.mService.mAtmInternal.removeUser(i);
        }

        protected boolean taskSupervisorSwitchUser(int i, com.android.server.am.UserState userState) {
            return this.mService.mAtmInternal.switchUser(i, userState);
        }

        protected void taskSupervisorResumeFocusedStackTopActivity() {
            this.mService.mAtmInternal.resumeTopActivities(false);
        }

        protected void clearAllLockedTasks(java.lang.String str) {
            this.mService.mAtmInternal.clearLockedTasks(str);
        }

        boolean isCallerRecents(int i) {
            return this.mService.mAtmInternal.isCallerRecents(i);
        }

        protected android.os.storage.IStorageManager getStorageManager() {
            return android.os.storage.IStorageManager.Stub.asInterface(android.os.ServiceManager.getService("mount"));
        }

        protected void showKeyguard(final java.lang.Runnable runnable) {
            if (getWindowManager().isKeyguardLocked()) {
                runnable.run();
            } else {
                getActivityTaskManagerInternal().registerScreenObserver(new com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver() { // from class: com.android.server.am.UserController.Injector.2
                    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                    public void onAwakeStateChanged(boolean z) {
                    }

                    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
                    public void onKeyguardStateChanged(boolean z) {
                        if (z) {
                            com.android.server.am.UserController.Injector.this.getActivityTaskManagerInternal().unregisterScreenObserver(this);
                            runnable.run();
                        }
                    }
                });
                getWindowManager().lockDeviceNow();
            }
        }

        protected void dismissKeyguard(final java.lang.Runnable runnable) {
            getWindowManager().dismissKeyguard(new com.android.internal.policy.IKeyguardDismissCallback.Stub() { // from class: com.android.server.am.UserController.Injector.3
                public void onDismissError() throws android.os.RemoteException {
                    runnable.run();
                }

                public void onDismissSucceeded() throws android.os.RemoteException {
                    runnable.run();
                }

                public void onDismissCancelled() throws android.os.RemoteException {
                    runnable.run();
                }
            }, null);
        }

        boolean isHeadlessSystemUserMode() {
            return android.os.UserManager.isHeadlessSystemUserMode();
        }

        boolean isUsersOnSecondaryDisplaysEnabled() {
            return android.os.UserManager.isVisibleBackgroundUsersEnabled();
        }

        void onUserStarting(int i) {
            getSystemServiceManager().onUserStarting(com.android.server.utils.TimingsTraceAndSlog.newAsyncLog(), i);
        }

        void setPerformancePowerMode(boolean z) {
            com.android.server.utils.Slogf.i(com.android.server.am.UserController.TAG, "Setting power mode MODE_FIXED_PERFORMANCE to " + z);
            getPowerManagerInternal().setPowerMode(3, z);
        }

        void onSystemUserVisibilityChanged(boolean z) {
            getUserManagerInternal().onSystemUserVisibilityChanged(z);
        }
    }
}
