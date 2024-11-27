package com.android.server.media;

/* loaded from: classes2.dex */
public class MediaSessionService extends com.android.server.SystemService implements com.android.server.Watchdog.Monitor {
    static final boolean DEBUG_KEY_EVENT = true;
    private static final java.lang.String MEDIA_BUTTON_RECEIVER = "media_button_receiver";
    private static final int MEDIA_KEY_LISTENER_TIMEOUT = 1000;
    private static final int SESSION_CREATION_LIMIT_PER_UID = 100;
    private static final java.lang.String USAGE_STATS_ACTION_START = "start";
    private static final java.lang.String USAGE_STATS_ACTION_STOP = "stop";
    private static final java.lang.String USAGE_STATS_CATEGORY = "android.media";
    private static final int WAKELOCK_TIMEOUT = 5000;
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private android.media.AudioManager mAudioManager;
    private com.android.server.media.AudioPlayerStateMonitor mAudioPlayerStateMonitor;
    private android.media.MediaCommunicationManager mCommunicationManager;
    private final android.content.Context mContext;
    private com.android.server.media.MediaSessionService.FullUserRecord mCurrentFullUserRecord;
    private com.android.server.media.MediaKeyDispatcher mCustomMediaKeyDispatcher;
    private com.android.server.media.MediaSessionPolicyProvider mCustomMediaSessionPolicyProvider;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mFullUserIds;
    private com.android.server.media.MediaSessionRecord mGlobalPrioritySession;
    private final com.android.server.media.MediaSessionService.MessageHandler mHandler;
    private boolean mHasFeatureLeanback;
    private android.app.KeyguardManager mKeyguardManager;
    private final java.lang.Object mLock;
    private final android.os.PowerManager.WakeLock mMediaEventWakeLock;
    private final android.content.BroadcastReceiver mNotificationListenerEnabledChangedReceiver;
    private final android.app.NotificationManager mNotificationManager;
    private final android.os.HandlerThread mRecordThread;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    final android.os.RemoteCallbackList<android.media.IRemoteSessionCallback> mRemoteVolumeControllers;
    private final android.media.MediaCommunicationManager.SessionCallback mSession2TokenCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<com.android.server.media.MediaSessionService.Session2TokensListenerRecord> mSession2TokensListenerRecords;
    private final com.android.server.media.MediaSessionService.SessionManagerImpl mSessionManagerImpl;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.ArrayList<com.android.server.media.MediaSessionService.SessionsListenerRecord> mSessionsListeners;
    private android.app.usage.UsageStatsManagerInternal mUsageStatsManagerInternal;
    private final android.util.SparseArray<java.util.Set<com.android.server.media.MediaSessionRecordImpl>> mUserEngagingSessions;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.media.MediaSessionService.FullUserRecord> mUserRecords;
    private static final java.lang.String TAG = "MediaSessionService";
    static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final int LONG_PRESS_TIMEOUT = android.view.ViewConfiguration.getLongPressTimeout() + 50;
    private static final int MULTI_TAP_TIMEOUT = android.view.ViewConfiguration.getMultiPressTimeout();

    public MediaSessionService(android.content.Context context) {
        super(context);
        this.mHandler = new com.android.server.media.MediaSessionService.MessageHandler();
        this.mLock = new java.lang.Object();
        this.mRecordThread = new android.os.HandlerThread("SessionRecordThread");
        this.mFullUserIds = new android.util.SparseIntArray();
        this.mUserRecords = new android.util.SparseArray<>();
        this.mSessionsListeners = new java.util.ArrayList<>();
        this.mSession2TokensListenerRecords = new java.util.ArrayList();
        this.mUserEngagingSessions = new android.util.SparseArray<>();
        this.mRemoteVolumeControllers = new android.os.RemoteCallbackList<>();
        this.mSession2TokenCallback = new android.media.MediaCommunicationManager.SessionCallback() { // from class: com.android.server.media.MediaSessionService.1
            public void onSession2TokenCreated(android.media.Session2Token session2Token) {
                addSession(session2Token, -1);
            }

            public void onSession2TokenCreated(android.media.Session2Token session2Token, int i) {
                addSession(session2Token, i);
            }

            private void addSession(android.media.Session2Token session2Token, int i) {
                if (com.android.server.media.MediaSessionService.DEBUG) {
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Session2 is created " + session2Token);
                }
                com.android.server.media.MediaSession2Record mediaSession2Record = new com.android.server.media.MediaSession2Record(session2Token, com.android.server.media.MediaSessionService.this, com.android.server.media.MediaSessionService.this.mRecordThread.getLooper(), i, 0);
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(mediaSession2Record.getUserId());
                        if (fullUserRecordLocked != null) {
                            fullUserRecordLocked.mPriorityStack.addSession(mediaSession2Record);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mNotificationListenerEnabledChangedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.media.MediaSessionService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                com.android.server.media.MediaSessionService.this.updateActiveSessionListeners();
            }
        };
        this.mContext = context;
        this.mSessionManagerImpl = new com.android.server.media.MediaSessionService.SessionManagerImpl();
        this.mMediaEventWakeLock = ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).newWakeLock(1, "handleMediaEvent");
        this.mNotificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("media_session", this.mSessionManagerImpl);
        com.android.server.Watchdog.getInstance().addMonitor(this);
        this.mKeyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService("keyguard");
        this.mAudioPlayerStateMonitor = com.android.server.media.AudioPlayerStateMonitor.getInstance(this.mContext);
        this.mAudioPlayerStateMonitor.registerListener(new com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener() { // from class: com.android.server.media.MediaSessionService$$ExternalSyntheticLambda0
            @Override // com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener
            public final void onAudioPlayerActiveStateChanged(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
                com.android.server.media.MediaSessionService.this.lambda$onStart$0(audioPlaybackConfiguration, z);
            }
        }, null);
        this.mHasFeatureLeanback = this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        updateUser();
        instantiateCustomProvider(this.mContext.getResources().getString(android.R.string.config_customMediaSessionPolicyProvider));
        instantiateCustomDispatcher(this.mContext.getResources().getString(android.R.string.config_customMediaKeyDispatcher));
        this.mRecordThread.start();
        this.mContext.registerReceiver(this.mNotificationListenerEnabledChangedReceiver, new android.content.IntentFilter("android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED"));
        this.mActivityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        this.mUsageStatsManagerInternal = (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onStart$0(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Audio playback is changed, config=" + audioPlaybackConfiguration + ", removed=" + z);
        }
        if (audioPlaybackConfiguration.getPlayerType() == 3) {
            return;
        }
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(android.os.UserHandle.getUserHandleForUid(audioPlaybackConfiguration.getClientUid()).getIdentifier());
                if (fullUserRecordLocked != null) {
                    fullUserRecordLocked.mPriorityStack.updateMediaButtonSessionIfNeeded();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        super.onBootPhase(i);
        switch (i) {
            case 550:
                com.android.server.media.MediaSessionDeviceConfig.initialize(this.mContext);
                break;
            case 1000:
                this.mCommunicationManager = (android.media.MediaCommunicationManager) this.mContext.getSystemService(android.media.MediaCommunicationManager.class);
                this.mCommunicationManager.registerSessionCallback(new com.android.server.media.HandlerExecutor(this.mHandler), this.mSession2TokenCallback);
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isGlobalPriorityActiveLocked() {
        return this.mGlobalPrioritySession != null && this.mGlobalPrioritySession.isActive();
    }

    void onSessionActiveStateChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, @android.annotation.Nullable android.media.session.PlaybackState playbackState) {
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
                if (fullUserRecordLocked == null) {
                    android.util.Log.w(TAG, "Unknown session updated. Ignoring.");
                    return;
                }
                if (mediaSessionRecordImpl.isSystemPriority()) {
                    android.util.Log.d(TAG, "Global priority session updated - user id=" + mediaSessionRecordImpl.getUserId() + " package=" + mediaSessionRecordImpl.getPackageName() + " active=" + mediaSessionRecordImpl.isActive());
                    fullUserRecordLocked.pushAddressedPlayerChangedLocked();
                } else {
                    if (!fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecordImpl)) {
                        android.util.Log.w(TAG, "Unknown session updated. Ignoring.");
                        return;
                    }
                    fullUserRecordLocked.mPriorityStack.onSessionActiveStateChanged(mediaSessionRecordImpl);
                }
                boolean isUserEngaged = isUserEngaged(mediaSessionRecordImpl, playbackState);
                android.util.Log.d(TAG, "onSessionActiveStateChanged: record=" + mediaSessionRecordImpl + "playbackState=" + playbackState + "allowRunningInForeground=" + isUserEngaged);
                setForegroundServiceAllowance(mediaSessionRecordImpl, isUserEngaged);
                reportMediaInteractionEvent(mediaSessionRecordImpl, isUserEngaged);
                this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean isUserEngaged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, @android.annotation.Nullable android.media.session.PlaybackState playbackState) {
        if (playbackState == null) {
            return mediaSessionRecordImpl.checkPlaybackActiveState(true);
        }
        return playbackState.isActive() && mediaSessionRecordImpl.isActive();
    }

    void setGlobalPrioritySession(com.android.server.media.MediaSessionRecord mediaSessionRecord) {
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecord.getUserId());
                if (this.mGlobalPrioritySession != mediaSessionRecord) {
                    android.util.Log.d(TAG, "Global priority session is changed from " + this.mGlobalPrioritySession + " to " + mediaSessionRecord);
                    this.mGlobalPrioritySession = mediaSessionRecord;
                    if (fullUserRecordLocked != null && fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecord)) {
                        fullUserRecordLocked.mPriorityStack.removeSession(mediaSessionRecord);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.util.List<com.android.server.media.MediaSessionRecord> getActiveSessionsLocked(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (i == android.os.UserHandle.ALL.getIdentifier()) {
            int size = this.mUserRecords.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.addAll(this.mUserRecords.valueAt(i2).mPriorityStack.getActiveSessions(i));
            }
        } else {
            com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i);
            if (fullUserRecordLocked == null) {
                android.util.Log.w(TAG, "getSessions failed. Unknown user " + i);
                return arrayList;
            }
            arrayList.addAll(fullUserRecordLocked.mPriorityStack.getActiveSessions(i));
        }
        if (isGlobalPriorityActiveLocked() && (i == android.os.UserHandle.ALL.getIdentifier() || i == this.mGlobalPrioritySession.getUserId())) {
            arrayList.add(0, this.mGlobalPrioritySession);
        }
        return arrayList;
    }

    java.util.List<android.media.Session2Token> getSession2TokensLocked(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (i == android.os.UserHandle.ALL.getIdentifier()) {
            int size = this.mUserRecords.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.addAll(this.mUserRecords.valueAt(i2).mPriorityStack.getSession2Tokens(i));
            }
        } else {
            arrayList.addAll(getFullUserRecordLocked(i).mPriorityStack.getSession2Tokens(i));
        }
        return arrayList;
    }

    public void notifyRemoteVolumeChanged(int i, com.android.server.media.MediaSessionRecord mediaSessionRecord) {
        if (!mediaSessionRecord.isActive()) {
            return;
        }
        synchronized (this.mLock) {
            int beginBroadcast = this.mRemoteVolumeControllers.beginBroadcast();
            android.media.session.MediaSession.Token sessionToken = mediaSessionRecord.getSessionToken();
            for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                try {
                    this.mRemoteVolumeControllers.getBroadcastItem(i2).onVolumeChanged(sessionToken, i);
                } catch (java.lang.Exception e) {
                    android.util.Log.w(TAG, "Error sending volume change.", e);
                }
            }
            this.mRemoteVolumeControllers.finishBroadcast();
        }
    }

    void onSessionPlaybackStateChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, boolean z, @android.annotation.Nullable android.media.session.PlaybackState playbackState) {
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
                if (fullUserRecordLocked == null || !fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecordImpl)) {
                    android.util.Log.d(TAG, "Unknown session changed playback state. Ignoring.");
                    return;
                }
                fullUserRecordLocked.mPriorityStack.onPlaybackStateChanged(mediaSessionRecordImpl, z);
                boolean isUserEngaged = isUserEngaged(mediaSessionRecordImpl, playbackState);
                android.util.Log.d(TAG, "onSessionPlaybackStateChanged: record=" + mediaSessionRecordImpl + "playbackState=" + playbackState + "allowRunningInForeground=" + isUserEngaged);
                setForegroundServiceAllowance(mediaSessionRecordImpl, isUserEngaged);
                reportMediaInteractionEvent(mediaSessionRecordImpl, isUserEngaged);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onSessionPlaybackTypeChanged(com.android.server.media.MediaSessionRecord mediaSessionRecord) {
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecord.getUserId());
                if (fullUserRecordLocked == null || !fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecord)) {
                    android.util.Log.d(TAG, "Unknown session changed playback type. Ignoring.");
                } else {
                    pushRemoteVolumeUpdateLocked(mediaSessionRecord.getUserId());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onUserStarting(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        if (DEBUG) {
            android.util.Log.d(TAG, "onStartUser: " + targetUser);
        }
        updateUser();
    }

    @Override // com.android.server.SystemService
    public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
        if (DEBUG) {
            android.util.Log.d(TAG, "onSwitchUser: " + targetUser2);
        }
        updateUser();
    }

    @Override // com.android.server.SystemService
    public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        if (DEBUG) {
            android.util.Log.d(TAG, "onCleanupUser: " + userIdentifier);
        }
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(userIdentifier);
                if (fullUserRecordLocked != null) {
                    if (fullUserRecordLocked.mFullUserId == userIdentifier) {
                        fullUserRecordLocked.destroySessionsForUserLocked(android.os.UserHandle.ALL.getIdentifier());
                        this.mUserRecords.remove(userIdentifier);
                    } else {
                        fullUserRecordLocked.destroySessionsForUserLocked(userIdentifier);
                    }
                }
                updateUser();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        synchronized (this.mLock) {
        }
    }

    protected void enforcePhoneStatePermission(int i, int i2) {
        if (this.mContext.checkPermission("android.permission.MODIFY_PHONE_STATE", i, i2) != 0) {
            throw new java.lang.SecurityException("Must hold the MODIFY_PHONE_STATE permission.");
        }
    }

    void onSessionDied(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        synchronized (this.mLock) {
            destroySessionLocked(mediaSessionRecordImpl);
        }
    }

    private void updateUser() {
        synchronized (this.mLock) {
            try {
                android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService("user");
                this.mFullUserIds.clear();
                java.util.List<android.os.UserHandle> userHandles = userManager.getUserHandles(false);
                if (userHandles != null) {
                    for (android.os.UserHandle userHandle : userHandles) {
                        android.os.UserHandle profileParent = userManager.getProfileParent(userHandle);
                        if (profileParent != null) {
                            this.mFullUserIds.put(userHandle.getIdentifier(), profileParent.getIdentifier());
                        } else {
                            this.mFullUserIds.put(userHandle.getIdentifier(), userHandle.getIdentifier());
                            if (this.mUserRecords.get(userHandle.getIdentifier()) == null) {
                                this.mUserRecords.put(userHandle.getIdentifier(), new com.android.server.media.MediaSessionService.FullUserRecord(userHandle.getIdentifier()));
                            }
                        }
                    }
                }
                int currentUser = android.app.ActivityManager.getCurrentUser();
                this.mCurrentFullUserRecord = this.mUserRecords.get(currentUser);
                if (this.mCurrentFullUserRecord == null) {
                    android.util.Log.w(TAG, "Cannot find FullUserInfo for the current user " + currentUser);
                    this.mCurrentFullUserRecord = new com.android.server.media.MediaSessionService.FullUserRecord(currentUser);
                    this.mUserRecords.put(currentUser, this.mCurrentFullUserRecord);
                }
                this.mFullUserIds.put(currentUser, currentUser);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActiveSessionListeners() {
        synchronized (this.mLock) {
            for (int size = this.mSessionsListeners.size() - 1; size >= 0; size--) {
                com.android.server.media.MediaSessionService.SessionsListenerRecord sessionsListenerRecord = this.mSessionsListeners.get(size);
                try {
                    enforceMediaPermissions(sessionsListenerRecord.componentName == null ? null : sessionsListenerRecord.componentName.getPackageName(), sessionsListenerRecord.pid, sessionsListenerRecord.uid, sessionsListenerRecord.userId);
                } catch (java.lang.SecurityException e) {
                    android.util.Log.i(TAG, "ActiveSessionsListener " + sessionsListenerRecord.componentName + " is no longer authorized. Disconnecting.");
                    this.mSessionsListeners.remove(size);
                    try {
                        sessionsListenerRecord.listener.onActiveSessionsChanged(new java.util.ArrayList());
                    } catch (java.lang.Exception e2) {
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroySessionLocked(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Destroying " + mediaSessionRecordImpl);
        }
        if (mediaSessionRecordImpl.isClosed()) {
            android.util.Log.w(TAG, "Destroying already destroyed session. Ignoring.");
            return;
        }
        com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
        if (fullUserRecordLocked != null && (mediaSessionRecordImpl instanceof com.android.server.media.MediaSessionRecord)) {
            int uid = mediaSessionRecordImpl.getUid();
            int i = fullUserRecordLocked.mUidToSessionCount.get(uid, 0);
            if (i <= 0) {
                android.util.Log.w(TAG, "destroySessionLocked: sessionCount should be positive. sessionCount=" + i);
            } else {
                fullUserRecordLocked.mUidToSessionCount.put(uid, i - 1);
            }
        }
        if (this.mGlobalPrioritySession == mediaSessionRecordImpl) {
            this.mGlobalPrioritySession = null;
            if (mediaSessionRecordImpl.isActive() && fullUserRecordLocked != null) {
                fullUserRecordLocked.pushAddressedPlayerChangedLocked();
            }
        } else if (fullUserRecordLocked != null) {
            fullUserRecordLocked.mPriorityStack.removeSession(mediaSessionRecordImpl);
        }
        mediaSessionRecordImpl.close();
        android.util.Log.d(TAG, "destroySessionLocked: record=" + mediaSessionRecordImpl);
        setForegroundServiceAllowance(mediaSessionRecordImpl, false);
        reportMediaInteractionEvent(mediaSessionRecordImpl, false);
        this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
    }

    private void setForegroundServiceAllowance(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, boolean z) {
        android.app.ForegroundServiceDelegationOptions foregroundServiceDelegationOptions;
        if (!com.android.media.flags.Flags.enableNotifyingActivityManagerWithMediaSessionStatusChange() || (foregroundServiceDelegationOptions = mediaSessionRecordImpl.getForegroundServiceDelegationOptions()) == null) {
            return;
        }
        if (z) {
            this.mActivityManagerInternal.startForegroundServiceDelegate(foregroundServiceDelegationOptions, (android.content.ServiceConnection) null);
        } else {
            this.mActivityManagerInternal.stopForegroundServiceDelegate(foregroundServiceDelegationOptions);
        }
    }

    private void reportMediaInteractionEvent(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, boolean z) {
        if (!android.app.usage.Flags.userInteractionTypeApi()) {
            return;
        }
        java.lang.String packageName = mediaSessionRecordImpl.getPackageName();
        int uid = mediaSessionRecordImpl.getUid();
        if (z) {
            if (!this.mUserEngagingSessions.contains(uid)) {
                this.mUserEngagingSessions.put(uid, new java.util.HashSet());
                reportUserInteractionEvent(USAGE_STATS_ACTION_START, mediaSessionRecordImpl.getUserId(), packageName);
            }
            this.mUserEngagingSessions.get(uid).add(mediaSessionRecordImpl);
            return;
        }
        if (this.mUserEngagingSessions.contains(uid)) {
            this.mUserEngagingSessions.get(uid).remove(mediaSessionRecordImpl);
            if (this.mUserEngagingSessions.get(uid).isEmpty()) {
                reportUserInteractionEvent(USAGE_STATS_ACTION_STOP, mediaSessionRecordImpl.getUserId(), packageName);
                this.mUserEngagingSessions.remove(uid);
            }
        }
    }

    private void reportUserInteractionEvent(java.lang.String str, int i, java.lang.String str2) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        persistableBundle.putString("android.app.usage.extra.EVENT_CATEGORY", USAGE_STATS_CATEGORY);
        persistableBundle.putString("android.app.usage.extra.EVENT_ACTION", str);
        this.mUsageStatsManagerInternal.reportUserInteractionEvent(str2, i, persistableBundle);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005d A[Catch: all -> 0x001f, TryCatch #0 {all -> 0x001f, blocks: (B:3:0x0004, B:5:0x000b, B:7:0x0014, B:11:0x0022, B:15:0x004a, B:18:0x0051, B:20:0x005d, B:22:0x0068), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0068 A[Catch: all -> 0x001f, TRY_LEAVE, TryCatch #0 {all -> 0x001f, blocks: (B:3:0x0004, B:5:0x000b, B:7:0x0014, B:11:0x0022, B:15:0x004a, B:18:0x0051, B:20:0x005d, B:22:0x0068), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void tempAllowlistTargetPkgIfPossible(int i, java.lang.String str, int i2, int i3, java.lang.String str2, java.lang.String str3) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            com.android.server.media.MediaServerUtils.enforcePackageName(this.mContext, str2, i3);
            if (i != i3) {
                boolean canAllowWhileInUsePermissionInFgs = this.mActivityManagerInternal.canAllowWhileInUsePermissionInFgs(i2, i3, str2);
                if (!canAllowWhileInUsePermissionInFgs && !this.mActivityManagerInternal.canStartForegroundService(i2, i3, str2)) {
                    z = false;
                    java.lang.StringBuilder sb = new java.lang.StringBuilder();
                    sb.append("tempAllowlistTargetPkgIfPossible callingPackage:");
                    sb.append(str2);
                    sb.append(" targetPackage:");
                    sb.append(str);
                    sb.append(" reason:");
                    sb.append(str3);
                    sb.append(!canAllowWhileInUsePermissionInFgs ? " [WIU]" : "");
                    sb.append(z ? " [FGS]" : "");
                    android.util.Log.i(TAG, sb.toString());
                    if (canAllowWhileInUsePermissionInFgs) {
                        this.mActivityManagerInternal.tempAllowWhileInUsePermissionInFgs(i, com.android.server.media.MediaSessionDeviceConfig.getMediaSessionCallbackFgsWhileInUseTempAllowDurationMs());
                    }
                    if (z) {
                        ((android.os.PowerExemptionManager) this.mContext.createContextAsUser(android.os.UserHandle.of(android.os.UserHandle.getUserId(i)), 0).getSystemService(android.os.PowerExemptionManager.class)).addToTemporaryAllowList(str, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_SESSION_CALLBACK, str3, com.android.server.media.MediaSessionDeviceConfig.getMediaSessionCallbackFgsAllowlistDurationMs());
                    }
                }
                z = true;
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
                sb2.append("tempAllowlistTargetPkgIfPossible callingPackage:");
                sb2.append(str2);
                sb2.append(" targetPackage:");
                sb2.append(str);
                sb2.append(" reason:");
                sb2.append(str3);
                sb2.append(!canAllowWhileInUsePermissionInFgs ? " [WIU]" : "");
                sb2.append(z ? " [FGS]" : "");
                android.util.Log.i(TAG, sb2.toString());
                if (canAllowWhileInUsePermissionInFgs) {
                }
                if (z) {
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceMediaPermissions(java.lang.String str, int i, int i2, int i3) {
        if (hasStatusBarServicePermission(i, i2) || hasMediaControlPermission(i, i2)) {
            return;
        }
        if (str == null || !hasEnabledNotificationListener(str, android.os.UserHandle.getUserHandleForUid(i2), i3)) {
            throw new java.lang.SecurityException("Missing permission to control media.");
        }
    }

    private boolean hasStatusBarServicePermission(int i, int i2) {
        return this.mContext.checkPermission("android.permission.STATUS_BAR_SERVICE", i, i2) == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enforceStatusBarServicePermission(java.lang.String str, int i, int i2) {
        if (!hasStatusBarServicePermission(i, i2)) {
            throw new java.lang.SecurityException("Only System UI and Settings may " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasMediaControlPermission(int i, int i2) {
        if (i2 == 1000 || this.mContext.checkPermission("android.permission.MEDIA_CONTENT_CONTROL", i, i2) == 0) {
            return true;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "uid(" + i2 + ") hasn't granted MEDIA_CONTENT_CONTROL");
            return false;
        }
        return false;
    }

    private boolean hasEnabledNotificationListener(java.lang.String str, android.os.UserHandle userHandle, int i) {
        if (userHandle.getIdentifier() != i) {
            return false;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "Checking whether the package " + str + " has an enabled notification listener.");
        }
        return this.mNotificationManager.hasEnabledNotificationListener(str, userHandle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.media.MediaSessionRecord createSessionInternal(int i, int i2, int i3, java.lang.String str, android.media.session.ISessionCallback iSessionCallback, java.lang.String str2, android.os.Bundle bundle) {
        int i4;
        com.android.server.media.MediaSessionRecord mediaSessionRecord;
        synchronized (this.mLock) {
            try {
                if (this.mCustomMediaSessionPolicyProvider == null) {
                    i4 = 0;
                } else {
                    i4 = this.mCustomMediaSessionPolicyProvider.getSessionPoliciesForApplication(i2, str);
                }
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i3);
                if (fullUserRecordLocked == null) {
                    android.util.Log.w(TAG, "Request from invalid user: " + i3 + ", pkg=" + str);
                    throw new java.lang.RuntimeException("Session request from invalid user.");
                }
                int i5 = fullUserRecordLocked.mUidToSessionCount.get(i2, 0);
                if (i5 >= 100 && !hasMediaControlPermission(i, i2)) {
                    throw new java.lang.RuntimeException("Created too many sessions. count=" + i5 + ")");
                }
                try {
                    com.android.server.media.MediaSessionRecord mediaSessionRecord2 = new com.android.server.media.MediaSessionRecord(i, i2, i3, str, iSessionCallback, str2, bundle, this, this.mRecordThread.getLooper(), i4);
                    fullUserRecordLocked.mUidToSessionCount.put(i2, i5 + 1);
                    fullUserRecordLocked.mPriorityStack.addSession(mediaSessionRecord2);
                    this.mHandler.postSessionsChanged(mediaSessionRecord2);
                    if (!DEBUG) {
                        mediaSessionRecord = mediaSessionRecord2;
                    } else {
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("Created session for ");
                        mediaSessionRecord = mediaSessionRecord2;
                        sb.append(str);
                        sb.append(" with tag ");
                        sb.append(str2);
                        android.util.Log.d(TAG, sb.toString());
                    }
                } catch (android.os.RemoteException e) {
                    throw new java.lang.RuntimeException("Media Session owner died prematurely.", e);
                }
            } finally {
            }
        }
        return mediaSessionRecord;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findIndexOfSessionsListenerLocked(android.media.session.IActiveSessionsListener iActiveSessionsListener) {
        for (int size = this.mSessionsListeners.size() - 1; size >= 0; size--) {
            if (this.mSessionsListeners.get(size).listener.asBinder() == iActiveSessionsListener.asBinder()) {
                return size;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findIndexOfSession2TokensListenerLocked(android.media.session.ISession2TokensListener iSession2TokensListener) {
        for (int size = this.mSession2TokensListenerRecords.size() - 1; size >= 0; size--) {
            if (this.mSession2TokensListenerRecords.get(size).listener.asBinder() == iSession2TokensListener.asBinder()) {
                return size;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushSession1Changed(int i) {
        synchronized (this.mLock) {
            try {
                if (getFullUserRecordLocked(i) == null) {
                    android.util.Log.w(TAG, "pushSession1ChangedOnHandler failed. No user with id=" + i);
                    return;
                }
                java.util.List<com.android.server.media.MediaSessionRecord> activeSessionsLocked = getActiveSessionsLocked(i);
                int size = activeSessionsLocked.size();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.add(activeSessionsLocked.get(i2).getSessionToken());
                }
                pushRemoteVolumeUpdateLocked(i);
                for (int size2 = this.mSessionsListeners.size() - 1; size2 >= 0; size2--) {
                    com.android.server.media.MediaSessionService.SessionsListenerRecord sessionsListenerRecord = this.mSessionsListeners.get(size2);
                    if (sessionsListenerRecord.userId == android.os.UserHandle.ALL.getIdentifier() || sessionsListenerRecord.userId == i) {
                        try {
                            sessionsListenerRecord.listener.onActiveSessionsChanged(arrayList);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.w(TAG, "Dead ActiveSessionsListener in pushSessionsChanged, removing", e);
                            this.mSessionsListeners.remove(size2);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void pushSession2Changed(int i) {
        synchronized (this.mLock) {
            try {
                java.util.List<android.media.Session2Token> session2TokensLocked = getSession2TokensLocked(android.os.UserHandle.ALL.getIdentifier());
                java.util.List<android.media.Session2Token> session2TokensLocked2 = getSession2TokensLocked(i);
                for (int size = this.mSession2TokensListenerRecords.size() - 1; size >= 0; size--) {
                    com.android.server.media.MediaSessionService.Session2TokensListenerRecord session2TokensListenerRecord = this.mSession2TokensListenerRecords.get(size);
                    try {
                        if (session2TokensListenerRecord.userId == android.os.UserHandle.ALL.getIdentifier()) {
                            session2TokensListenerRecord.listener.onSession2TokensChanged(session2TokensLocked);
                        } else if (session2TokensListenerRecord.userId == i) {
                            session2TokensListenerRecord.listener.onSession2TokensChanged(session2TokensLocked2);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(TAG, "Failed to notify Session2Token change. Removing listener.", e);
                        this.mSession2TokensListenerRecords.remove(size);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void pushRemoteVolumeUpdateLocked(int i) {
        com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i);
        if (fullUserRecordLocked == null) {
            android.util.Log.w(TAG, "pushRemoteVolumeUpdateLocked failed. No user with id=" + i);
            return;
        }
        synchronized (this.mLock) {
            try {
                int beginBroadcast = this.mRemoteVolumeControllers.beginBroadcast();
                com.android.server.media.MediaSessionRecordImpl defaultRemoteSession = fullUserRecordLocked.mPriorityStack.getDefaultRemoteSession(i);
                if (defaultRemoteSession instanceof com.android.server.media.MediaSession2Record) {
                    return;
                }
                android.media.session.MediaSession.Token sessionToken = defaultRemoteSession == null ? null : ((com.android.server.media.MediaSessionRecord) defaultRemoteSession).getSessionToken();
                for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                    try {
                        this.mRemoteVolumeControllers.getBroadcastItem(i2).onSessionChanged(sessionToken);
                    } catch (java.lang.Exception e) {
                        android.util.Log.w(TAG, "Error sending default remote volume.", e);
                    }
                }
                this.mRemoteVolumeControllers.finishBroadcast();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void onMediaButtonReceiverChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
        synchronized (this.mLock) {
            try {
                com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
                com.android.server.media.MediaSessionRecordImpl mediaButtonSession = fullUserRecordLocked.mPriorityStack.getMediaButtonSession();
                if (mediaSessionRecordImpl == mediaButtonSession) {
                    fullUserRecordLocked.rememberMediaButtonReceiverLocked(mediaButtonSession);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getCallingPackageName(int i) {
        java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length > 0) {
            return packagesForUid[0];
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchVolumeKeyLongPressLocked(android.view.KeyEvent keyEvent) {
        if (this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener == null) {
            return;
        }
        try {
            this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener.onVolumeKeyLongPress(keyEvent);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to send " + keyEvent + " to volume key long-press listener");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.media.MediaSessionService.FullUserRecord getFullUserRecordLocked(int i) {
        int i2 = this.mFullUserIds.get(i, -1);
        if (i2 < 0) {
            return null;
        }
        return this.mUserRecords.get(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.media.MediaSessionRecord getMediaSessionRecordLocked(android.media.session.MediaSession.Token token) {
        com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(android.os.UserHandle.getUserHandleForUid(token.getUid()).getIdentifier());
        if (fullUserRecordLocked != null) {
            return fullUserRecordLocked.mPriorityStack.getMediaSessionRecord(token);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void instantiateCustomDispatcher(java.lang.String str) {
        synchronized (this.mLock) {
            this.mCustomMediaKeyDispatcher = null;
            if (str != null) {
                try {
                    if (!android.text.TextUtils.isEmpty(str)) {
                        this.mCustomMediaKeyDispatcher = (com.android.server.media.MediaKeyDispatcher) java.lang.Class.forName(str).getDeclaredConstructor(android.content.Context.class).newInstance(this.mContext);
                    }
                } catch (java.lang.ClassNotFoundException | java.lang.IllegalAccessException | java.lang.InstantiationException | java.lang.NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
                    this.mCustomMediaKeyDispatcher = null;
                    android.util.Log.w(TAG, "Encountered problem while using reflection", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void instantiateCustomProvider(java.lang.String str) {
        synchronized (this.mLock) {
            this.mCustomMediaSessionPolicyProvider = null;
            if (str != null) {
                try {
                    if (!android.text.TextUtils.isEmpty(str)) {
                        this.mCustomMediaSessionPolicyProvider = (com.android.server.media.MediaSessionPolicyProvider) java.lang.Class.forName(str).getDeclaredConstructor(android.content.Context.class).newInstance(this.mContext);
                    }
                } catch (java.lang.ClassNotFoundException | java.lang.IllegalAccessException | java.lang.InstantiationException | java.lang.NoSuchMethodException | java.lang.reflect.InvocationTargetException e) {
                    android.util.Log.w(TAG, "Encountered problem while using reflection", e);
                }
            }
        }
    }

    final class FullUserRecord implements com.android.server.media.MediaSessionStack.OnMediaButtonSessionChangedListener {
        private final android.content.ContentResolver mContentResolver;
        private final int mFullUserId;
        private com.android.server.media.MediaButtonReceiverHolder mLastMediaButtonReceiverHolder;
        private android.media.session.IOnMediaKeyListener mOnMediaKeyListener;
        private int mOnMediaKeyListenerUid;
        private android.media.session.IOnVolumeKeyLongPressListener mOnVolumeKeyLongPressListener;
        private int mOnVolumeKeyLongPressListenerUid;
        private final com.android.server.media.MediaSessionStack mPriorityStack;
        private final java.util.HashMap<android.os.IBinder, com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventDispatchedListenerRecord> mOnMediaKeyEventDispatchedListeners = new java.util.HashMap<>();
        private final java.util.HashMap<android.os.IBinder, com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventSessionChangedListenerRecord> mOnMediaKeyEventSessionChangedListeners = new java.util.HashMap<>();
        private final android.util.SparseIntArray mUidToSessionCount = new android.util.SparseIntArray();

        FullUserRecord(int i) {
            this.mFullUserId = i;
            this.mContentResolver = com.android.server.media.MediaSessionService.this.mContext.createContextAsUser(android.os.UserHandle.of(this.mFullUserId), 0).getContentResolver();
            this.mPriorityStack = new com.android.server.media.MediaSessionStack(com.android.server.media.MediaSessionService.this.mAudioPlayerStateMonitor, this);
            this.mLastMediaButtonReceiverHolder = com.android.server.media.MediaButtonReceiverHolder.unflattenFromString(com.android.server.media.MediaSessionService.this.mContext, android.provider.Settings.Secure.getString(this.mContentResolver, com.android.server.media.MediaSessionService.MEDIA_BUTTON_RECEIVER));
        }

        public void destroySessionsForUserLocked(int i) {
            java.util.Iterator<com.android.server.media.MediaSessionRecord> it = this.mPriorityStack.getPriorityList(false, i).iterator();
            while (it.hasNext()) {
                com.android.server.media.MediaSessionService.this.destroySessionLocked(it.next());
            }
        }

        public void addOnMediaKeyEventDispatchedListenerLocked(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener, int i) {
            android.os.IBinder asBinder = iOnMediaKeyEventDispatchedListener.asBinder();
            com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventDispatchedListenerRecord onMediaKeyEventDispatchedListenerRecord = new com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventDispatchedListenerRecord(iOnMediaKeyEventDispatchedListener, i);
            this.mOnMediaKeyEventDispatchedListeners.put(asBinder, onMediaKeyEventDispatchedListenerRecord);
            try {
                asBinder.linkToDeath(onMediaKeyEventDispatchedListenerRecord, 0);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to add listener", e);
                this.mOnMediaKeyEventDispatchedListeners.remove(asBinder);
            }
        }

        public void removeOnMediaKeyEventDispatchedListenerLocked(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) {
            android.os.IBinder asBinder = iOnMediaKeyEventDispatchedListener.asBinder();
            asBinder.unlinkToDeath(this.mOnMediaKeyEventDispatchedListeners.remove(asBinder), 0);
        }

        public void addOnMediaKeyEventSessionChangedListenerLocked(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, int i) {
            android.os.IBinder asBinder = iOnMediaKeyEventSessionChangedListener.asBinder();
            com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventSessionChangedListenerRecord onMediaKeyEventSessionChangedListenerRecord = new com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventSessionChangedListenerRecord(iOnMediaKeyEventSessionChangedListener, i);
            this.mOnMediaKeyEventSessionChangedListeners.put(asBinder, onMediaKeyEventSessionChangedListenerRecord);
            try {
                asBinder.linkToDeath(onMediaKeyEventSessionChangedListenerRecord, 0);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to add listener", e);
                this.mOnMediaKeyEventSessionChangedListeners.remove(asBinder);
            }
        }

        public void removeOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) {
            android.os.IBinder asBinder = iOnMediaKeyEventSessionChangedListener.asBinder();
            asBinder.unlinkToDeath(this.mOnMediaKeyEventSessionChangedListeners.remove(asBinder), 0);
        }

        public void dumpLocked(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str + "Record for full_user=" + this.mFullUserId);
            int size = com.android.server.media.MediaSessionService.this.mFullUserIds.size();
            for (int i = 0; i < size; i++) {
                if (com.android.server.media.MediaSessionService.this.mFullUserIds.keyAt(i) != com.android.server.media.MediaSessionService.this.mFullUserIds.valueAt(i) && com.android.server.media.MediaSessionService.this.mFullUserIds.valueAt(i) == this.mFullUserId) {
                    printWriter.print(", profile_user=" + com.android.server.media.MediaSessionService.this.mFullUserIds.keyAt(i));
                }
            }
            printWriter.println();
            java.lang.String str2 = str + "  ";
            printWriter.println(str2 + "Volume key long-press listener: " + this.mOnVolumeKeyLongPressListener);
            printWriter.println(str2 + "Volume key long-press listener package: " + com.android.server.media.MediaSessionService.this.getCallingPackageName(this.mOnVolumeKeyLongPressListenerUid));
            printWriter.println(str2 + "Media key listener: " + this.mOnMediaKeyListener);
            printWriter.println(str2 + "Media key listener package: " + com.android.server.media.MediaSessionService.this.getCallingPackageName(this.mOnMediaKeyListenerUid));
            printWriter.println(str2 + "OnMediaKeyEventDispatchedListener: added " + this.mOnMediaKeyEventDispatchedListeners.size() + " listener(s)");
            java.util.Iterator<com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventDispatchedListenerRecord> it = this.mOnMediaKeyEventDispatchedListeners.values().iterator();
            while (it.hasNext()) {
                printWriter.println(str2 + "  from " + com.android.server.media.MediaSessionService.this.getCallingPackageName(it.next().uid));
            }
            printWriter.println(str2 + "OnMediaKeyEventSessionChangedListener: added " + this.mOnMediaKeyEventSessionChangedListeners.size() + " listener(s)");
            java.util.Iterator<com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventSessionChangedListenerRecord> it2 = this.mOnMediaKeyEventSessionChangedListeners.values().iterator();
            while (it2.hasNext()) {
                printWriter.println(str2 + "  from " + com.android.server.media.MediaSessionService.this.getCallingPackageName(it2.next().uid));
            }
            printWriter.println(str2 + "Last MediaButtonReceiver: " + this.mLastMediaButtonReceiverHolder);
            this.mPriorityStack.dump(printWriter, str2);
        }

        @Override // com.android.server.media.MediaSessionStack.OnMediaButtonSessionChangedListener
        public void onMediaButtonSessionChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl, com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl2) {
            android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Media button session is changed to " + mediaSessionRecordImpl2);
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                if (mediaSessionRecordImpl != null) {
                    try {
                        com.android.server.media.MediaSessionService.this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                if (mediaSessionRecordImpl2 != null) {
                    rememberMediaButtonReceiverLocked(mediaSessionRecordImpl2);
                    com.android.server.media.MediaSessionService.this.mHandler.postSessionsChanged(mediaSessionRecordImpl2);
                }
                pushAddressedPlayerChangedLocked();
            }
        }

        public void rememberMediaButtonReceiverLocked(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
            if (mediaSessionRecordImpl instanceof com.android.server.media.MediaSession2Record) {
                return;
            }
            this.mLastMediaButtonReceiverHolder = ((com.android.server.media.MediaSessionRecord) mediaSessionRecordImpl).getMediaButtonReceiver();
            android.provider.Settings.Secure.putString(this.mContentResolver, com.android.server.media.MediaSessionService.MEDIA_BUTTON_RECEIVER, this.mLastMediaButtonReceiverHolder == null ? "" : this.mLastMediaButtonReceiverHolder.flattenToString());
        }

        private void pushAddressedPlayerChangedLocked(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) {
            try {
                com.android.server.media.MediaSessionRecordImpl mediaButtonSessionLocked = getMediaButtonSessionLocked();
                if (mediaButtonSessionLocked != null) {
                    if (mediaButtonSessionLocked instanceof com.android.server.media.MediaSessionRecord) {
                        com.android.server.media.MediaSessionRecord mediaSessionRecord = (com.android.server.media.MediaSessionRecord) mediaButtonSessionLocked;
                        iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(mediaSessionRecord.getPackageName(), mediaSessionRecord.getSessionToken());
                    }
                } else if (com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mLastMediaButtonReceiverHolder != null) {
                    iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mLastMediaButtonReceiverHolder.getPackageName(), (android.media.session.MediaSession.Token) null);
                } else {
                    iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged("", (android.media.session.MediaSession.Token) null);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to pushAddressedPlayerChangedLocked", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void pushAddressedPlayerChangedLocked() {
            java.util.Iterator<com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventSessionChangedListenerRecord> it = this.mOnMediaKeyEventSessionChangedListeners.values().iterator();
            while (it.hasNext()) {
                pushAddressedPlayerChangedLocked(it.next().callback);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.media.MediaSessionRecordImpl getMediaButtonSessionLocked() {
            return com.android.server.media.MediaSessionService.this.isGlobalPriorityActiveLocked() ? com.android.server.media.MediaSessionService.this.mGlobalPrioritySession : this.mPriorityStack.getMediaButtonSession();
        }

        final class OnMediaKeyEventDispatchedListenerRecord implements android.os.IBinder.DeathRecipient {
            public final android.media.session.IOnMediaKeyEventDispatchedListener callback;
            public final int uid;

            OnMediaKeyEventDispatchedListenerRecord(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener, int i) {
                this.callback = iOnMediaKeyEventDispatchedListener;
                this.uid = i;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord.this.mOnMediaKeyEventDispatchedListeners.remove(this.callback.asBinder());
                }
            }
        }

        final class OnMediaKeyEventSessionChangedListenerRecord implements android.os.IBinder.DeathRecipient {
            public final android.media.session.IOnMediaKeyEventSessionChangedListener callback;
            public final int uid;

            OnMediaKeyEventSessionChangedListenerRecord(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, int i) {
                this.callback = iOnMediaKeyEventSessionChangedListener;
                this.uid = i;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord.this.mOnMediaKeyEventSessionChangedListeners.remove(this.callback.asBinder());
                }
            }
        }
    }

    final class SessionsListenerRecord implements android.os.IBinder.DeathRecipient {
        public final android.content.ComponentName componentName;
        public final android.media.session.IActiveSessionsListener listener;
        public final int pid;
        public final int uid;
        public final int userId;

        SessionsListenerRecord(android.media.session.IActiveSessionsListener iActiveSessionsListener, android.content.ComponentName componentName, int i, int i2, int i3) {
            this.listener = iActiveSessionsListener;
            this.componentName = componentName;
            this.userId = i;
            this.pid = i2;
            this.uid = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                com.android.server.media.MediaSessionService.this.mSessionsListeners.remove(this);
            }
        }
    }

    final class Session2TokensListenerRecord implements android.os.IBinder.DeathRecipient {
        public final android.media.session.ISession2TokensListener listener;
        public final int userId;

        Session2TokensListenerRecord(android.media.session.ISession2TokensListener iSession2TokensListener, int i) {
            this.listener = iSession2TokensListener;
            this.userId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                com.android.server.media.MediaSessionService.this.mSession2TokensListenerRecords.remove(this);
            }
        }
    }

    class SessionManagerImpl extends android.media.session.ISessionManager.Stub {
        private static final java.lang.String EXTRA_WAKELOCK_ACQUIRED = "android.media.AudioService.WAKELOCK_ACQUIRED";
        private static final int WAKELOCK_RELEASE_ON_FINISHED = 1980;
        private com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventWakeLockReceiver mKeyEventReceiver;
        private com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler mMediaKeyEventHandler = new com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler(0);
        private com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler mVolumeKeyEventHandler = new com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler(1);

        SessionManagerImpl() {
            this.mKeyEventReceiver = new com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventWakeLockReceiver(com.android.server.media.MediaSessionService.this.mHandler);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void onShellCommand(java.io.FileDescriptor fileDescriptor, java.io.FileDescriptor fileDescriptor2, java.io.FileDescriptor fileDescriptor3, java.lang.String[] strArr, android.os.ShellCallback shellCallback, android.os.ResultReceiver resultReceiver) {
            java.lang.String str;
            java.lang.String[] packagesForUid = com.android.server.media.MediaSessionService.this.mContext.getPackageManager().getPackagesForUid(android.os.Binder.getCallingUid());
            if (packagesForUid != null && packagesForUid.length > 0) {
                str = packagesForUid[0];
            } else {
                str = com.android.server.vibrator.VibratorManagerService.VibratorManagerShellCommand.SHELL_PACKAGE_NAME;
            }
            new com.android.server.media.MediaShellCommand(str).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public android.media.session.ISession createSession(java.lang.String str, android.media.session.ISessionCallback iSessionCallback, java.lang.String str2, android.os.Bundle bundle, int i) throws android.os.RemoteException {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    com.android.server.media.MediaServerUtils.enforcePackageName(com.android.server.media.MediaSessionService.this.mContext, str, callingUid);
                    int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, str);
                    if (iSessionCallback == null) {
                        throw new java.lang.IllegalArgumentException("Controller callback cannot be null");
                    }
                    com.android.server.media.MediaSessionRecord createSessionInternal = com.android.server.media.MediaSessionService.this.createSessionInternal(callingPid, callingUid, handleIncomingUser, str, iSessionCallback, str2, bundle);
                    if (createSessionInternal == null) {
                        throw new java.lang.IllegalStateException("Failed to create a new session record");
                    }
                    android.media.session.ISession sessionBinder = createSessionInternal.getSessionBinder();
                    if (sessionBinder == null) {
                        throw new java.lang.IllegalStateException("Invalid session record");
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return sessionBinder;
                } catch (java.lang.Exception e) {
                    android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Exception in creating a new session", e);
                    throw e;
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public java.util.List<android.media.session.MediaSession.Token> getSessions(android.content.ComponentName componentName, int i) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int verifySessionsRequest = verifySessionsRequest(componentName, i, callingPid, callingUid);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        java.util.Iterator it = com.android.server.media.MediaSessionService.this.getActiveSessionsLocked(verifySessionsRequest).iterator();
                        while (it.hasNext()) {
                            arrayList.add(((com.android.server.media.MediaSessionRecord) it.next()).getSessionToken());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                return arrayList;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public android.media.session.MediaSession.Token getMediaKeyEventSession(java.lang.String str) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaServerUtils.enforcePackageName(com.android.server.media.MediaSessionService.this.mContext, str, callingUid);
                com.android.server.media.MediaSessionService.this.enforceMediaPermissions(str, callingPid, callingUid, identifier);
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked != null) {
                        com.android.server.media.MediaSessionRecordImpl mediaButtonSessionLocked = fullUserRecordLocked.getMediaButtonSessionLocked();
                        if (mediaButtonSessionLocked instanceof com.android.server.media.MediaSessionRecord) {
                            return ((com.android.server.media.MediaSessionRecord) mediaButtonSessionLocked).getSessionToken();
                        }
                        return null;
                    }
                    android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "No matching user record to get the media key event session, userId=" + identifier);
                    return null;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.lang.String getMediaKeyEventSessionPackageName(java.lang.String str) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaServerUtils.enforcePackageName(com.android.server.media.MediaSessionService.this.mContext, str, callingUid);
                com.android.server.media.MediaSessionService.this.enforceMediaPermissions(str, callingPid, callingUid, identifier);
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "No matching user record to get the media key event session package , userId=" + identifier);
                        return "";
                    }
                    com.android.server.media.MediaSessionRecordImpl mediaButtonSessionLocked = fullUserRecordLocked.getMediaButtonSessionLocked();
                    if (mediaButtonSessionLocked instanceof com.android.server.media.MediaSessionRecord) {
                        return mediaButtonSessionLocked.getPackageName();
                    }
                    if (fullUserRecordLocked.mLastMediaButtonReceiverHolder == null) {
                        return "";
                    }
                    return fullUserRecordLocked.mLastMediaButtonReceiverHolder.getPackageName();
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void addSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            if (iActiveSessionsListener == null) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "addSessionsListener: listener is null, ignoring");
                return;
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int verifySessionsRequest = verifySessionsRequest(componentName, i, callingPid, callingUid);
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    if (com.android.server.media.MediaSessionService.this.findIndexOfSessionsListenerLocked(iActiveSessionsListener) != -1) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "ActiveSessionsListener is already added, ignoring");
                        return;
                    }
                    com.android.server.media.MediaSessionService.SessionsListenerRecord sessionsListenerRecord = com.android.server.media.MediaSessionService.this.new SessionsListenerRecord(iActiveSessionsListener, componentName, verifySessionsRequest, callingPid, callingUid);
                    try {
                        iActiveSessionsListener.asBinder().linkToDeath(sessionsListenerRecord, 0);
                        com.android.server.media.MediaSessionService.this.mSessionsListeners.add(sessionsListenerRecord);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(com.android.server.media.MediaSessionService.TAG, "ActiveSessionsListener is dead, ignoring it", e);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeSessionsListener(android.media.session.IActiveSessionsListener iActiveSessionsListener) throws android.os.RemoteException {
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                int findIndexOfSessionsListenerLocked = com.android.server.media.MediaSessionService.this.findIndexOfSessionsListenerLocked(iActiveSessionsListener);
                if (findIndexOfSessionsListenerLocked != -1) {
                    com.android.server.media.MediaSessionService.SessionsListenerRecord sessionsListenerRecord = (com.android.server.media.MediaSessionService.SessionsListenerRecord) com.android.server.media.MediaSessionService.this.mSessionsListeners.remove(findIndexOfSessionsListenerLocked);
                    try {
                        sessionsListenerRecord.listener.asBinder().unlinkToDeath(sessionsListenerRecord, 0);
                    } catch (java.lang.Exception e) {
                    }
                }
            }
        }

        public void addSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener, int i) {
            if (iSession2TokensListener == null) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "addSession2TokensListener: listener is null, ignoring");
                return;
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, null);
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    if (com.android.server.media.MediaSessionService.this.findIndexOfSession2TokensListenerLocked(iSession2TokensListener) >= 0) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "addSession2TokensListener: listener is already added, ignoring");
                    } else {
                        com.android.server.media.MediaSessionService.this.mSession2TokensListenerRecords.add(com.android.server.media.MediaSessionService.this.new Session2TokensListenerRecord(iSession2TokensListener, handleIncomingUser));
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeSession2TokensListener(android.media.session.ISession2TokensListener iSession2TokensListener) {
            android.os.Binder.getCallingPid();
            android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    int findIndexOfSession2TokensListenerLocked = com.android.server.media.MediaSessionService.this.findIndexOfSession2TokensListenerLocked(iSession2TokensListener);
                    if (findIndexOfSession2TokensListenerLocked >= 0) {
                        com.android.server.media.MediaSessionService.Session2TokensListenerRecord session2TokensListenerRecord = (com.android.server.media.MediaSessionService.Session2TokensListenerRecord) com.android.server.media.MediaSessionService.this.mSession2TokensListenerRecords.remove(findIndexOfSession2TokensListenerLocked);
                        try {
                            session2TokensListenerRecord.listener.asBinder().unlinkToDeath(session2TokensListenerRecord, 0);
                        } catch (java.lang.Exception e) {
                        }
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dispatchMediaKeyEvent(java.lang.String str, boolean z, android.view.KeyEvent keyEvent, boolean z2) {
            if (keyEvent == null || !android.view.KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Attempted to dispatch null or non-media key event.");
                return;
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.media.MediaSessionService.DEBUG) {
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "dispatchMediaKeyEvent, pkg=" + str + " pid=" + callingPid + ", uid=" + callingUid + ", asSystem=" + z + ", event=" + keyEvent);
                }
                if (!isUserSetupComplete()) {
                    android.util.Log.i(com.android.server.media.MediaSessionService.TAG, "Not dispatching media key event because user setup is in progress.");
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return;
                }
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    boolean isGlobalPriorityActiveLocked = com.android.server.media.MediaSessionService.this.isGlobalPriorityActiveLocked();
                    if (isGlobalPriorityActiveLocked && callingUid != 1000) {
                        android.util.Log.i(com.android.server.media.MediaSessionService.TAG, "Only the system can dispatch media key event to the global priority session.");
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    if (!isGlobalPriorityActiveLocked && com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mOnMediaKeyListener != null) {
                        android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Send " + keyEvent + " to the media key listener");
                        try {
                            com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mOnMediaKeyListener.onMediaKey(keyEvent, new com.android.server.media.MediaSessionService.SessionManagerImpl.MediaKeyListenerResultReceiver(str, callingPid, callingUid, z, keyEvent, z2));
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return;
                        } catch (android.os.RemoteException e) {
                            android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to send " + keyEvent + " to the media key listener");
                        }
                    }
                    if (isGlobalPriorityActiveLocked) {
                        dispatchMediaKeyEventLocked(str, callingPid, callingUid, z, keyEvent, z2);
                    } else {
                        this.mMediaKeyEventHandler.handleMediaKeyEventLocked(str, callingPid, callingUid, z, keyEvent, z2);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public boolean dispatchMediaKeyEventToSessionAsSystemService(java.lang.String str, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionRecord mediaSessionRecordLocked = com.android.server.media.MediaSessionService.this.getMediaSessionRecordLocked(token);
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "dispatchMediaKeyEventToSessionAsSystemService, pkg=" + str + ", pid=" + callingPid + ", uid=" + callingUid + ", sessionToken=" + token + ", event=" + keyEvent + ", session=" + mediaSessionRecordLocked);
                    if (mediaSessionRecordLocked == null) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to find session to dispatch key event.");
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    return mediaSessionRecordLocked.sendMediaButton(str, callingPid, callingUid, true, keyEvent, 0, null);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void addOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) {
            if (iOnMediaKeyEventDispatchedListener == null) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "addOnMediaKeyEventDispatchedListener: listener is null, ignoring");
                return;
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.media.MediaSessionService.this.hasMediaControlPermission(callingPid, callingUid)) {
                    throw new java.lang.SecurityException("MEDIA_CONTENT_CONTROL permission is required to  add MediaKeyEventDispatchedListener");
                }
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null || fullUserRecordLocked.mFullUserId != identifier) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Only the full user can add the listener, userId=" + identifier);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    fullUserRecordLocked.addOnMediaKeyEventDispatchedListenerLocked(iOnMediaKeyEventDispatchedListener, callingUid);
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "The MediaKeyEventDispatchedListener (" + iOnMediaKeyEventDispatchedListener.asBinder() + ") is added by " + com.android.server.media.MediaSessionService.this.getCallingPackageName(callingUid));
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void removeOnMediaKeyEventDispatchedListener(android.media.session.IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) {
            if (iOnMediaKeyEventDispatchedListener == null) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "removeOnMediaKeyEventDispatchedListener: listener is null, ignoring");
                return;
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.media.MediaSessionService.this.hasMediaControlPermission(callingPid, callingUid)) {
                    throw new java.lang.SecurityException("MEDIA_CONTENT_CONTROL permission is required to  remove MediaKeyEventDispatchedListener");
                }
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null || fullUserRecordLocked.mFullUserId != identifier) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Only the full user can remove the listener, userId=" + identifier);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    fullUserRecordLocked.removeOnMediaKeyEventDispatchedListenerLocked(iOnMediaKeyEventDispatchedListener);
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "The MediaKeyEventDispatchedListener (" + iOnMediaKeyEventDispatchedListener.asBinder() + ") is removed by " + com.android.server.media.MediaSessionService.this.getCallingPackageName(callingUid));
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void addOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, java.lang.String str) {
            if (iOnMediaKeyEventSessionChangedListener == null) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "addOnMediaKeyEventSessionChangedListener: listener is null, ignoring");
                return;
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaServerUtils.enforcePackageName(com.android.server.media.MediaSessionService.this.mContext, str, callingUid);
                com.android.server.media.MediaSessionService.this.enforceMediaPermissions(str, callingPid, callingUid, identifier);
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null || fullUserRecordLocked.mFullUserId != identifier) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Only the full user can add the listener, userId=" + identifier);
                        return;
                    }
                    fullUserRecordLocked.addOnMediaKeyEventSessionChangedListenerLocked(iOnMediaKeyEventSessionChangedListener, callingUid);
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "The MediaKeyEventSessionChangedListener (" + iOnMediaKeyEventSessionChangedListener.asBinder() + ") is added by " + str);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void removeOnMediaKeyEventSessionChangedListener(android.media.session.IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) {
            if (iOnMediaKeyEventSessionChangedListener == null) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "removeOnMediaKeyEventSessionChangedListener: listener is null, ignoring");
                return;
            }
            android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null || fullUserRecordLocked.mFullUserId != identifier) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Only the full user can remove the listener, userId=" + identifier);
                        return;
                    }
                    fullUserRecordLocked.removeOnMediaKeyEventSessionChangedListener(iOnMediaKeyEventSessionChangedListener);
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "The MediaKeyEventSessionChangedListener (" + iOnMediaKeyEventSessionChangedListener.asBinder() + ") is removed by " + com.android.server.media.MediaSessionService.this.getCallingPackageName(callingUid));
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setOnVolumeKeyLongPressListener(android.media.session.IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.media.MediaSessionService.this.mContext.checkPermission("android.permission.SET_VOLUME_KEY_LONG_PRESS_LISTENER", callingPid, callingUid) != 0) {
                    throw new java.lang.SecurityException("Must hold the SET_VOLUME_KEY_LONG_PRESS_LISTENER permission.");
                }
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
                    final com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null || fullUserRecordLocked.mFullUserId != identifier) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Only the full user can set the volume key long-press listener, userId=" + identifier);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    if (fullUserRecordLocked.mOnVolumeKeyLongPressListener != null && fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid != callingUid) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "The volume key long-press listener cannot be reset by another app , mOnVolumeKeyLongPressListener=" + fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid + ", uid=" + callingUid);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    fullUserRecordLocked.mOnVolumeKeyLongPressListener = iOnVolumeKeyLongPressListener;
                    fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid = callingUid;
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "The volume key long-press listener " + iOnVolumeKeyLongPressListener + " is set by " + com.android.server.media.MediaSessionService.this.getCallingPackageName(callingUid));
                    if (fullUserRecordLocked.mOnVolumeKeyLongPressListener != null) {
                        try {
                            fullUserRecordLocked.mOnVolumeKeyLongPressListener.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.media.MediaSessionService.SessionManagerImpl.1
                                @Override // android.os.IBinder.DeathRecipient
                                public void binderDied() {
                                    synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                                        fullUserRecordLocked.mOnVolumeKeyLongPressListener = null;
                                    }
                                }
                            }, 0);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to set death recipient " + fullUserRecordLocked.mOnVolumeKeyLongPressListener);
                            fullUserRecordLocked.mOnVolumeKeyLongPressListener = null;
                        }
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void setOnMediaKeyListener(android.media.session.IOnMediaKeyListener iOnMediaKeyListener) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (com.android.server.media.MediaSessionService.this.mContext.checkPermission("android.permission.SET_MEDIA_KEY_LISTENER", callingPid, callingUid) != 0) {
                    throw new java.lang.SecurityException("Must hold the SET_MEDIA_KEY_LISTENER permission.");
                }
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
                    final com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null || fullUserRecordLocked.mFullUserId != identifier) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Only the full user can set the media key listener, userId=" + identifier);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    if (fullUserRecordLocked.mOnMediaKeyListener != null && fullUserRecordLocked.mOnMediaKeyListenerUid != callingUid) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "The media key listener cannot be reset by another app. , mOnMediaKeyListenerUid=" + fullUserRecordLocked.mOnMediaKeyListenerUid + ", uid=" + callingUid);
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    fullUserRecordLocked.mOnMediaKeyListener = iOnMediaKeyListener;
                    fullUserRecordLocked.mOnMediaKeyListenerUid = callingUid;
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "The media key listener " + fullUserRecordLocked.mOnMediaKeyListener + " is set by " + com.android.server.media.MediaSessionService.this.getCallingPackageName(callingUid));
                    if (fullUserRecordLocked.mOnMediaKeyListener != null) {
                        try {
                            fullUserRecordLocked.mOnMediaKeyListener.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.media.MediaSessionService.SessionManagerImpl.2
                                @Override // android.os.IBinder.DeathRecipient
                                public void binderDied() {
                                    synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                                        fullUserRecordLocked.mOnMediaKeyListener = null;
                                    }
                                }
                            }, 0);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to set death recipient " + fullUserRecordLocked.mOnMediaKeyListener);
                            fullUserRecordLocked.mOnMediaKeyListener = null;
                        }
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void dispatchVolumeKeyEvent(java.lang.String str, java.lang.String str2, boolean z, android.view.KeyEvent keyEvent, int i, boolean z2) {
            if (keyEvent == null || (keyEvent.getKeyCode() != 24 && keyEvent.getKeyCode() != 25 && keyEvent.getKeyCode() != 164)) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Attempted to dispatch null or non-volume key event.");
                return;
            }
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "dispatchVolumeKeyEvent, pkg=" + str + ", opPkg=" + str2 + ", pid=" + callingPid + ", uid=" + callingUid + ", asSystem=" + z + ", event=" + keyEvent + ", stream=" + i + ", musicOnly=" + z2);
            try {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        if (com.android.server.media.MediaSessionService.this.isGlobalPriorityActiveLocked()) {
                            dispatchVolumeKeyEventLocked(str, str2, callingPid, callingUid, z, keyEvent, i, z2);
                        } else {
                            this.mVolumeKeyEventHandler.handleVolumeKeyEventLocked(str, callingPid, callingUid, z, keyEvent, str2, i, z2);
                        }
                    } finally {
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchVolumeKeyEventLocked(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, android.view.KeyEvent keyEvent, int i3, boolean z2) {
            boolean z3;
            int i4;
            int i5;
            int i6 = 1;
            boolean z4 = keyEvent.getAction() == 0;
            boolean z5 = keyEvent.getAction() == 1;
            switch (keyEvent.getKeyCode()) {
                case 24:
                    z3 = false;
                    break;
                case 25:
                    i6 = -1;
                    z3 = false;
                    break;
                case 164:
                    z3 = true;
                    i6 = 0;
                    break;
                default:
                    i6 = 0;
                    z3 = false;
                    break;
            }
            if (z4 || z5) {
                if (z2) {
                    i4 = 4096;
                } else if (z5) {
                    i4 = 4116;
                } else {
                    i4 = 4113;
                }
                if (i6 != 0) {
                    if (!z5) {
                        i5 = i6;
                    } else {
                        i5 = 0;
                    }
                    dispatchAdjustVolumeLocked(str, str2, i, i2, z, i3, i5, i4, z2);
                    return;
                }
                if (z3 && z4 && keyEvent.getRepeatCount() == 0) {
                    dispatchAdjustVolumeLocked(str, str2, i, i2, z, i3, 101, i4, z2);
                }
            }
        }

        public void dispatchVolumeKeyEventToSessionAsSystemService(java.lang.String str, java.lang.String str2, android.view.KeyEvent keyEvent, android.media.session.MediaSession.Token token) {
            int i;
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    com.android.server.media.MediaSessionRecord mediaSessionRecordLocked = com.android.server.media.MediaSessionService.this.getMediaSessionRecordLocked(token);
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "dispatchVolumeKeyEventToSessionAsSystemService, pkg=" + str + ", opPkg=" + str2 + ", pid=" + callingPid + ", uid=" + callingUid + ", sessionToken=" + token + ", event=" + keyEvent + ", session=" + mediaSessionRecordLocked);
                    if (mediaSessionRecordLocked == null) {
                        android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to find session to dispatch key event, token=" + token + ". Fallbacks to the default handling.");
                        dispatchVolumeKeyEventLocked(str, str2, callingPid, callingUid, true, keyEvent, Integer.MIN_VALUE, false);
                        return;
                    }
                    if (com.android.media.flags.Flags.fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling() && !mediaSessionRecordLocked.canHandleVolumeKey()) {
                        android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Session with packageName=" + mediaSessionRecordLocked.getPackageName() + " doesn't support volume adjustment. Fallbacks to the default handling.");
                        dispatchVolumeKeyEventLocked(str, str2, callingPid, callingUid, true, keyEvent, Integer.MIN_VALUE, false);
                        return;
                    }
                    switch (keyEvent.getAction()) {
                        case 0:
                            switch (keyEvent.getKeyCode()) {
                                case 24:
                                    i = 1;
                                    break;
                                case 25:
                                    i = -1;
                                    break;
                                case 164:
                                    i = 101;
                                    break;
                                default:
                                    i = 0;
                                    break;
                            }
                            mediaSessionRecordLocked.adjustVolume(str, str2, callingPid, callingUid, true, i, 1, false);
                            break;
                        case 1:
                            mediaSessionRecordLocked.adjustVolume(str, str2, callingPid, callingUid, true, 0, 4116, false);
                            break;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void dispatchAdjustVolume(java.lang.String str, java.lang.String str2, int i, int i2, int i3) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    dispatchAdjustVolumeLocked(str, str2, callingPid, callingUid, false, i, i2, i3, false);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void registerRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                try {
                    try {
                        com.android.server.media.MediaSessionService.this.enforceStatusBarServicePermission("listen for volume changes", callingPid, callingUid);
                        com.android.server.media.MediaSessionService.this.mRemoteVolumeControllers.register(iRemoteSessionCallback);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void unregisterRemoteSessionCallback(android.media.IRemoteSessionCallback iRemoteSessionCallback) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                try {
                    try {
                        com.android.server.media.MediaSessionService.this.enforceStatusBarServicePermission("listen for volume changes", callingPid, callingUid);
                        com.android.server.media.MediaSessionService.this.mRemoteVolumeControllers.unregister(iRemoteSessionCallback);
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean isGlobalPriorityActive() {
            boolean isGlobalPriorityActiveLocked;
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                isGlobalPriorityActiveLocked = com.android.server.media.MediaSessionService.this.isGlobalPriorityActiveLocked();
            }
            return isGlobalPriorityActiveLocked;
        }

        public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.server.media.MediaServerUtils.checkDumpPermission(com.android.server.media.MediaSessionService.this.mContext, com.android.server.media.MediaSessionService.TAG, printWriter)) {
                printWriter.println("MEDIA SESSION SERVICE (dumpsys media_session)");
                printWriter.println();
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        printWriter.println(com.android.server.media.MediaSessionService.this.mSessionsListeners.size() + " sessions listeners.");
                        printWriter.println("Global priority session is " + com.android.server.media.MediaSessionService.this.mGlobalPrioritySession);
                        if (com.android.server.media.MediaSessionService.this.mGlobalPrioritySession != null) {
                            com.android.server.media.MediaSessionService.this.mGlobalPrioritySession.dump(printWriter, "  ");
                        }
                        printWriter.println("User Records:");
                        int size = com.android.server.media.MediaSessionService.this.mUserRecords.size();
                        for (int i = 0; i < size; i++) {
                            ((com.android.server.media.MediaSessionService.FullUserRecord) com.android.server.media.MediaSessionService.this.mUserRecords.valueAt(i)).dumpLocked(printWriter, "");
                        }
                        com.android.server.media.MediaSessionService.this.mAudioPlayerStateMonitor.dump(com.android.server.media.MediaSessionService.this.mContext, printWriter, "");
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                com.android.server.media.MediaSessionDeviceConfig.dump(printWriter, "");
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:10:0x002c, code lost:
        
            if (hasEnabledNotificationListener(r1, r6, r8) != false) goto L13;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean isTrusted(java.lang.String str, int i, int i2) {
            int callingUid = android.os.Binder.getCallingUid();
            int identifier = android.os.UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            boolean z = false;
            if (((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).filterAppAccess(str, callingUid, identifier)) {
                return false;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (!com.android.server.media.MediaSessionService.this.hasMediaControlPermission(i, i2)) {
                }
                z = true;
                return z;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setCustomMediaKeyDispatcher(java.lang.String str) {
            com.android.server.media.MediaSessionService.this.instantiateCustomDispatcher(str);
        }

        public void setCustomMediaSessionPolicyProvider(java.lang.String str) {
            com.android.server.media.MediaSessionService.this.instantiateCustomProvider(str);
        }

        public boolean hasCustomMediaKeyDispatcher(java.lang.String str) {
            if (com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher == null) {
                return false;
            }
            return android.text.TextUtils.equals(str, com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.getClass().getName());
        }

        public boolean hasCustomMediaSessionPolicyProvider(java.lang.String str) {
            if (com.android.server.media.MediaSessionService.this.mCustomMediaSessionPolicyProvider == null) {
                return false;
            }
            return android.text.TextUtils.equals(str, com.android.server.media.MediaSessionService.this.mCustomMediaSessionPolicyProvider.getClass().getName());
        }

        public int getSessionPolicies(android.media.session.MediaSession.Token token) {
            synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                try {
                    com.android.server.media.MediaSessionRecord mediaSessionRecordLocked = com.android.server.media.MediaSessionService.this.getMediaSessionRecordLocked(token);
                    if (mediaSessionRecordLocked != null) {
                        return mediaSessionRecordLocked.getSessionPolicies();
                    }
                    return 0;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void setSessionPolicies(android.media.session.MediaSession.Token token, int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        com.android.server.media.MediaSessionRecord mediaSessionRecordLocked = com.android.server.media.MediaSessionService.this.getMediaSessionRecordLocked(token);
                        com.android.server.media.MediaSessionService.FullUserRecord fullUserRecordLocked = com.android.server.media.MediaSessionService.this.getFullUserRecordLocked(mediaSessionRecordLocked.getUserId());
                        if (fullUserRecordLocked != null) {
                            mediaSessionRecordLocked.setSessionPolicies(i);
                            fullUserRecordLocked.mPriorityStack.updateMediaButtonSessionBySessionPolicyChange(mediaSessionRecordLocked);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private int verifySessionsRequest(android.content.ComponentName componentName, int i, int i2, int i3) {
            java.lang.String str;
            if (componentName == null) {
                str = null;
            } else {
                str = componentName.getPackageName();
                com.android.server.media.MediaServerUtils.enforcePackageName(com.android.server.media.MediaSessionService.this.mContext, str, i3);
            }
            int handleIncomingUser = handleIncomingUser(i2, i3, i, str);
            com.android.server.media.MediaSessionService.this.enforceMediaPermissions(str, i2, i3, handleIncomingUser);
            return handleIncomingUser;
        }

        private int handleIncomingUser(int i, int i2, int i3, java.lang.String str) {
            int identifier = android.os.UserHandle.getUserHandleForUid(i2).getIdentifier();
            if (i3 == identifier) {
                return i3;
            }
            if (com.android.server.media.MediaSessionService.this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2) == 0) {
                if (i3 == android.os.UserHandle.CURRENT.getIdentifier()) {
                    return android.app.ActivityManager.getCurrentUser();
                }
                return i3;
            }
            throw new java.lang.SecurityException("Permission denied while calling from " + str + " with user id: " + i3 + "; Need to run as either the calling user id (" + identifier + "), or with android.permission.INTERACT_ACROSS_USERS_FULL permission");
        }

        private boolean hasEnabledNotificationListener(int i, java.lang.String str, int i2) {
            if (i != android.os.UserHandle.getUserHandleForUid(i2).getIdentifier()) {
                return false;
            }
            try {
                if (i2 != com.android.server.media.MediaSessionService.this.mContext.getPackageManager().getPackageUidAsUser(str, android.os.UserHandle.getUserId(i2))) {
                    android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to check enabled notification listener. Package name and UID doesn't match");
                    return false;
                }
                if (com.android.server.media.MediaSessionService.this.mNotificationManager.hasEnabledNotificationListener(str, android.os.UserHandle.getUserHandleForUid(i2))) {
                    return true;
                }
                if (com.android.server.media.MediaSessionService.DEBUG) {
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, str + " (uid=" + i2 + ") doesn't have an enabled notification listener");
                }
                return false;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to check enabled notification listener. Package name doesn't exist");
                return false;
            }
        }

        private void dispatchAdjustVolumeLocked(final java.lang.String str, final java.lang.String str2, final int i, final int i2, final boolean z, final int i3, final int i4, final int i5, boolean z2) {
            com.android.server.media.MediaSessionRecordImpl defaultVolumeSession;
            if (com.android.server.media.MediaSessionService.this.isGlobalPriorityActiveLocked()) {
                defaultVolumeSession = com.android.server.media.MediaSessionService.this.mGlobalPrioritySession;
            } else {
                defaultVolumeSession = com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mPriorityStack.getDefaultVolumeSession();
            }
            boolean z3 = isValidLocalStreamType(i3) && android.media.AudioSystem.isStreamActive(i3, 0);
            if (defaultVolumeSession != null && defaultVolumeSession.getUid() != i2 && com.android.server.media.MediaSessionService.this.mAudioPlayerStateMonitor.hasUidPlayedAudioLast(i2)) {
                if (com.android.media.flags.Flags.adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession()) {
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Ignoring session=" + defaultVolumeSession + " and adjusting suggestedStream=" + i3 + " instead");
                    defaultVolumeSession = null;
                } else {
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Session=" + defaultVolumeSession + " will not be not ignored and will receive the volume adjustment event");
                }
            }
            if (defaultVolumeSession == null || z3) {
                android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Adjusting suggestedStream=" + i3 + " by " + i4 + ". flags=" + i5 + ", preferSuggestedStream=" + z3 + ", session=" + defaultVolumeSession);
                if (z2 && !android.media.AudioSystem.isStreamActive(3, 0)) {
                    android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Nothing is playing on the music stream. Skipping volume event, flags=" + i5);
                    return;
                }
                com.android.server.media.MediaSessionService.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaSessionService.SessionManagerImpl.3
                    @Override // java.lang.Runnable
                    public void run() {
                        java.lang.String str3;
                        int i6;
                        int i7;
                        if (z) {
                            str3 = com.android.server.media.MediaSessionService.this.mContext.getOpPackageName();
                            i6 = android.os.Process.myUid();
                            i7 = android.os.Process.myPid();
                        } else {
                            str3 = str2;
                            i6 = i2;
                            i7 = i;
                        }
                        try {
                            com.android.server.media.MediaSessionService.this.mAudioManager.adjustSuggestedStreamVolumeForUid(i3, i4, i5, str3, i6, i7, com.android.server.media.MediaSessionService.this.getContext().getApplicationInfo().targetSdkVersion);
                        } catch (java.lang.IllegalArgumentException | java.lang.SecurityException e) {
                            android.util.Log.e(com.android.server.media.MediaSessionService.TAG, "Cannot adjust volume: direction=" + i4 + ", suggestedStream=" + i3 + ", flags=" + i5 + ", packageName=" + str + ", uid=" + i2 + ", asSystemService=" + z, e);
                        }
                    }
                });
                return;
            }
            android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Adjusting " + defaultVolumeSession + " by " + i4 + ". flags=" + i5 + ", suggestedStream=" + i3 + ", preferSuggestedStream=" + z3);
            defaultVolumeSession.adjustVolume(str, str2, i, i2, z, i4, i5, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchMediaKeyEventLocked(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, boolean z2) {
            com.android.server.media.MediaSessionRecord mediaSessionRecord;
            com.android.server.media.MediaButtonReceiverHolder mediaButtonReceiverHolder;
            com.android.server.media.MediaSessionRecord mediaSessionRecord2;
            com.android.server.media.MediaButtonReceiverHolder mediaButtonReceiverHolder2;
            android.app.PendingIntent mediaButtonReceiver;
            if (com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.getMediaButtonSessionLocked() instanceof com.android.server.media.MediaSession2Record) {
                return;
            }
            if (com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher == null) {
                mediaSessionRecord = null;
                mediaButtonReceiverHolder = null;
            } else {
                android.media.session.MediaSession.Token mediaSession = com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.getMediaSession(keyEvent, i2, z);
                if (mediaSession == null) {
                    mediaSessionRecord = null;
                } else {
                    mediaSessionRecord = com.android.server.media.MediaSessionService.this.getMediaSessionRecordLocked(mediaSession);
                }
                if (mediaSessionRecord == null && (mediaButtonReceiver = com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.getMediaButtonReceiver(keyEvent, i2, z)) != null) {
                    mediaButtonReceiverHolder = com.android.server.media.MediaButtonReceiverHolder.create(com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mFullUserId, mediaButtonReceiver, "");
                } else {
                    mediaButtonReceiverHolder = null;
                }
            }
            if (mediaSessionRecord == null && mediaButtonReceiverHolder == null) {
                com.android.server.media.MediaSessionRecord mediaSessionRecord3 = (com.android.server.media.MediaSessionRecord) com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.getMediaButtonSessionLocked();
                if (mediaSessionRecord3 != null) {
                    mediaSessionRecord2 = mediaSessionRecord3;
                    mediaButtonReceiverHolder2 = mediaButtonReceiverHolder;
                } else {
                    mediaSessionRecord2 = mediaSessionRecord3;
                    mediaButtonReceiverHolder2 = com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mLastMediaButtonReceiverHolder;
                }
            } else {
                mediaSessionRecord2 = mediaSessionRecord;
                mediaButtonReceiverHolder2 = mediaButtonReceiverHolder;
            }
            if (mediaSessionRecord2 != null) {
                android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "Sending " + keyEvent + " to " + mediaSessionRecord2);
                if (z2) {
                    this.mKeyEventReceiver.acquireWakeLockLocked();
                }
                mediaSessionRecord2.sendMediaButton(str, i, i2, z, keyEvent, z2 ? this.mKeyEventReceiver.mLastTimeoutId : -1, this.mKeyEventReceiver);
                try {
                    java.util.Iterator it = com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mOnMediaKeyEventDispatchedListeners.values().iterator();
                    while (it.hasNext()) {
                        ((com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventDispatchedListenerRecord) it.next()).callback.onMediaKeyEventDispatched(keyEvent, mediaSessionRecord2.getPackageName(), mediaSessionRecord2.getSessionToken());
                    }
                    return;
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed to send callback", e);
                    return;
                }
            }
            if (mediaButtonReceiverHolder2 != null) {
                if (z2) {
                    this.mKeyEventReceiver.acquireWakeLockLocked();
                }
                if (mediaButtonReceiverHolder2.send(com.android.server.media.MediaSessionService.this.mContext, keyEvent, z ? com.android.server.media.MediaSessionService.this.mContext.getPackageName() : str, z2 ? this.mKeyEventReceiver.mLastTimeoutId : -1, this.mKeyEventReceiver, com.android.server.media.MediaSessionService.this.mHandler, com.android.server.media.MediaSessionDeviceConfig.getMediaButtonReceiverFgsAllowlistDurationMs())) {
                    java.lang.String packageName = mediaButtonReceiverHolder2.getPackageName();
                    for (com.android.server.media.MediaSessionService.FullUserRecord.OnMediaKeyEventDispatchedListenerRecord onMediaKeyEventDispatchedListenerRecord : com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mOnMediaKeyEventDispatchedListeners.values()) {
                        try {
                            onMediaKeyEventDispatchedListenerRecord.callback.onMediaKeyEventDispatched(keyEvent, packageName, (android.media.session.MediaSession.Token) null);
                        } catch (android.os.RemoteException e2) {
                            android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "Failed notify key event dispatch, uid=" + onMediaKeyEventDispatchedListenerRecord.uid, e2);
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startVoiceInput(boolean z) {
            android.content.Intent intent;
            android.os.PowerManager powerManager = (android.os.PowerManager) com.android.server.media.MediaSessionService.this.mContext.getSystemService("power");
            boolean z2 = false;
            boolean z3 = com.android.server.media.MediaSessionService.this.mKeyguardManager != null && com.android.server.media.MediaSessionService.this.mKeyguardManager.isKeyguardLocked();
            if (z3 || !powerManager.isScreenOn()) {
                intent = new android.content.Intent("android.speech.action.VOICE_SEARCH_HANDS_FREE");
                if (z3 && com.android.server.media.MediaSessionService.this.mKeyguardManager.isKeyguardSecure()) {
                    z2 = true;
                }
                intent.putExtra("android.speech.extras.EXTRA_SECURE", z2);
                android.util.Log.i(com.android.server.media.MediaSessionService.TAG, "voice-based interactions: about to use ACTION_VOICE_SEARCH_HANDS_FREE");
            } else {
                intent = new android.content.Intent("android.speech.action.WEB_SEARCH");
                android.util.Log.i(com.android.server.media.MediaSessionService.TAG, "voice-based interactions: about to use ACTION_WEB_SEARCH");
            }
            if (z) {
                com.android.server.media.MediaSessionService.this.mMediaEventWakeLock.acquire();
            }
            try {
                try {
                    intent.setFlags(276824064);
                    if (com.android.server.media.MediaSessionService.DEBUG) {
                        android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "voiceIntent: " + intent);
                    }
                    com.android.server.media.MediaSessionService.this.mContext.startActivityAsUser(intent, android.os.UserHandle.CURRENT);
                    if (!z) {
                        return;
                    }
                } catch (android.content.ActivityNotFoundException e) {
                    android.util.Log.w(com.android.server.media.MediaSessionService.TAG, "No activity for search: " + e);
                    if (!z) {
                        return;
                    }
                }
                com.android.server.media.MediaSessionService.this.mMediaEventWakeLock.release();
            } catch (java.lang.Throwable th) {
                if (z) {
                    com.android.server.media.MediaSessionService.this.mMediaEventWakeLock.release();
                }
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isVoiceKey(int i) {
            return i == 79 || (!com.android.server.media.MediaSessionService.this.mHasFeatureLeanback && i == 85);
        }

        private boolean isUserSetupComplete() {
            return android.provider.Settings.Secure.getIntForUser(com.android.server.media.MediaSessionService.this.mContext.getContentResolver(), "user_setup_complete", 0, android.os.UserHandle.CURRENT.getIdentifier()) != 0;
        }

        private boolean isValidLocalStreamType(int i) {
            return i >= 0 && i <= 5;
        }

        private class MediaKeyListenerResultReceiver extends android.os.ResultReceiver implements java.lang.Runnable {
            private final boolean mAsSystemService;
            private boolean mHandled;
            private final android.view.KeyEvent mKeyEvent;
            private final boolean mNeedWakeLock;
            private final java.lang.String mPackageName;
            private final int mPid;
            private final int mUid;

            private MediaKeyListenerResultReceiver(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, boolean z2) {
                super(com.android.server.media.MediaSessionService.this.mHandler);
                com.android.server.media.MediaSessionService.this.mHandler.postDelayed(this, 1000L);
                this.mPackageName = str;
                this.mPid = i;
                this.mUid = i2;
                this.mAsSystemService = z;
                this.mKeyEvent = keyEvent;
                this.mNeedWakeLock = z2;
            }

            @Override // java.lang.Runnable
            public void run() {
                android.util.Log.d(com.android.server.media.MediaSessionService.TAG, "The media key listener is timed-out for " + this.mKeyEvent);
                dispatchMediaKeyEvent();
            }

            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i, android.os.Bundle bundle) {
                if (i == 1) {
                    this.mHandled = true;
                    com.android.server.media.MediaSessionService.this.mHandler.removeCallbacks(this);
                } else {
                    dispatchMediaKeyEvent();
                }
            }

            private void dispatchMediaKeyEvent() {
                if (this.mHandled) {
                    return;
                }
                this.mHandled = true;
                com.android.server.media.MediaSessionService.this.mHandler.removeCallbacks(this);
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        if (com.android.server.media.MediaSessionService.this.isGlobalPriorityActiveLocked()) {
                            com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchMediaKeyEventLocked(this.mPackageName, this.mPid, this.mUid, this.mAsSystemService, this.mKeyEvent, this.mNeedWakeLock);
                        } else {
                            com.android.server.media.MediaSessionService.SessionManagerImpl.this.mMediaKeyEventHandler.handleMediaKeyEventLocked(this.mPackageName, this.mPid, this.mUid, this.mAsSystemService, this.mKeyEvent, this.mNeedWakeLock);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        class KeyEventWakeLockReceiver extends android.os.ResultReceiver implements java.lang.Runnable, android.app.PendingIntent.OnFinished {
            private final android.os.Handler mHandler;
            private int mLastTimeoutId;
            private int mRefCount;

            KeyEventWakeLockReceiver(android.os.Handler handler) {
                super(handler);
                this.mRefCount = 0;
                this.mLastTimeoutId = 0;
                this.mHandler = handler;
            }

            public void onTimeout() {
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        if (this.mRefCount == 0) {
                            return;
                        }
                        this.mLastTimeoutId++;
                        this.mRefCount = 0;
                        releaseWakeLockLocked();
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            public void acquireWakeLockLocked() {
                if (this.mRefCount == 0) {
                    com.android.server.media.MediaSessionService.this.mMediaEventWakeLock.acquire();
                }
                this.mRefCount++;
                this.mHandler.removeCallbacks(this);
                this.mHandler.postDelayed(this, 5000L);
            }

            @Override // java.lang.Runnable
            public void run() {
                onTimeout();
            }

            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i, android.os.Bundle bundle) {
                if (i < this.mLastTimeoutId) {
                    return;
                }
                synchronized (com.android.server.media.MediaSessionService.this.mLock) {
                    try {
                        if (this.mRefCount > 0) {
                            this.mRefCount--;
                            if (this.mRefCount == 0) {
                                releaseWakeLockLocked();
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }

            private void releaseWakeLockLocked() {
                com.android.server.media.MediaSessionService.this.mMediaEventWakeLock.release();
                this.mHandler.removeCallbacks(this);
            }

            @Override // android.app.PendingIntent.OnFinished
            public void onSendFinished(android.app.PendingIntent pendingIntent, android.content.Intent intent, int i, java.lang.String str, android.os.Bundle bundle) {
                onReceiveResult(i, null);
            }
        }

        class KeyEventHandler {
            private static final int KEY_TYPE_MEDIA = 0;
            private static final int KEY_TYPE_VOLUME = 1;
            private boolean mIsLongPressing;
            private int mKeyType;
            private java.lang.Runnable mLongPressTimeoutRunnable;
            private int mMultiTapCount;
            private int mMultiTapKeyCode;
            private java.lang.Runnable mMultiTapTimeoutRunnable;
            private android.view.KeyEvent mTrackingFirstDownKeyEvent;

            KeyEventHandler(int i) {
                this.mKeyType = i;
            }

            void handleMediaKeyEventLocked(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, boolean z2) {
                handleKeyEventLocked(str, i, i2, z, keyEvent, z2, null, 0, false);
            }

            void handleVolumeKeyEventLocked(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, java.lang.String str2, int i3, boolean z2) {
                handleKeyEventLocked(str, i, i2, z, keyEvent, false, str2, i3, z2);
            }

            void handleKeyEventLocked(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, boolean z2, java.lang.String str2, int i3, boolean z3) {
                int i4;
                if (keyEvent.isCanceled()) {
                    return;
                }
                if (com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher != null && com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.getOverriddenKeyEvents() != null) {
                    i4 = com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.getOverriddenKeyEvents().get(java.lang.Integer.valueOf(keyEvent.getKeyCode())).intValue();
                } else {
                    i4 = 0;
                }
                cancelTrackingIfNeeded(str, i, i2, z, keyEvent, z2, str2, i3, z3, i4);
                if (!needTracking(keyEvent, i4)) {
                    if (this.mKeyType == 1) {
                        com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchVolumeKeyEventLocked(str, str2, i, i2, z, keyEvent, i3, z3);
                        return;
                    } else {
                        com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, keyEvent, z2);
                        return;
                    }
                }
                if (isFirstDownKeyEvent(keyEvent)) {
                    this.mTrackingFirstDownKeyEvent = keyEvent;
                    this.mIsLongPressing = false;
                    return;
                }
                if (isFirstLongPressKeyEvent(keyEvent)) {
                    this.mIsLongPressing = true;
                }
                if (this.mIsLongPressing) {
                    handleLongPressLocked(keyEvent, z2, i4);
                    return;
                }
                if (keyEvent.getAction() == 1) {
                    this.mTrackingFirstDownKeyEvent = null;
                    if (shouldTrackForMultipleTapsLocked(i4)) {
                        if (this.mMultiTapCount == 0) {
                            this.mMultiTapTimeoutRunnable = createSingleTapRunnable(str, i, i2, z, keyEvent, z2, str2, i3, z3, com.android.server.media.MediaKeyDispatcher.isSingleTapOverridden(i4));
                            if (!com.android.server.media.MediaKeyDispatcher.isSingleTapOverridden(i4) || com.android.server.media.MediaKeyDispatcher.isDoubleTapOverridden(i4) || com.android.server.media.MediaKeyDispatcher.isTripleTapOverridden(i4)) {
                                com.android.server.media.MediaSessionService.this.mHandler.postDelayed(this.mMultiTapTimeoutRunnable, com.android.server.media.MediaSessionService.MULTI_TAP_TIMEOUT);
                                this.mMultiTapCount = 1;
                                this.mMultiTapKeyCode = keyEvent.getKeyCode();
                                return;
                            }
                            this.mMultiTapTimeoutRunnable.run();
                            return;
                        }
                        if (this.mMultiTapCount == 1) {
                            com.android.server.media.MediaSessionService.this.mHandler.removeCallbacks(this.mMultiTapTimeoutRunnable);
                            this.mMultiTapTimeoutRunnable = createDoubleTapRunnable(str, i, i2, z, keyEvent, z2, str2, i3, z3, com.android.server.media.MediaKeyDispatcher.isSingleTapOverridden(i4), com.android.server.media.MediaKeyDispatcher.isDoubleTapOverridden(i4));
                            if (com.android.server.media.MediaKeyDispatcher.isTripleTapOverridden(i4)) {
                                com.android.server.media.MediaSessionService.this.mHandler.postDelayed(this.mMultiTapTimeoutRunnable, com.android.server.media.MediaSessionService.MULTI_TAP_TIMEOUT);
                                this.mMultiTapCount = 2;
                                return;
                            } else {
                                this.mMultiTapTimeoutRunnable.run();
                                return;
                            }
                        }
                        if (this.mMultiTapCount == 2) {
                            com.android.server.media.MediaSessionService.this.mHandler.removeCallbacks(this.mMultiTapTimeoutRunnable);
                            onTripleTap(keyEvent);
                            return;
                        }
                        return;
                    }
                    dispatchDownAndUpKeyEventsLocked(str, i, i2, z, keyEvent, z2, str2, i3, z3);
                }
            }

            private boolean shouldTrackForMultipleTapsLocked(int i) {
                return com.android.server.media.MediaKeyDispatcher.isSingleTapOverridden(i) || com.android.server.media.MediaKeyDispatcher.isDoubleTapOverridden(i) || com.android.server.media.MediaKeyDispatcher.isTripleTapOverridden(i);
            }

            private void cancelTrackingIfNeeded(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, boolean z2, java.lang.String str2, int i3, boolean z3, int i4) {
                if (this.mTrackingFirstDownKeyEvent == null && this.mMultiTapTimeoutRunnable == null) {
                    return;
                }
                if (isFirstDownKeyEvent(keyEvent)) {
                    if (this.mLongPressTimeoutRunnable != null) {
                        com.android.server.media.MediaSessionService.this.mHandler.removeCallbacks(this.mLongPressTimeoutRunnable);
                        this.mLongPressTimeoutRunnable.run();
                    }
                    if (this.mMultiTapTimeoutRunnable != null && keyEvent.getKeyCode() != this.mMultiTapKeyCode) {
                        runExistingMultiTapRunnableLocked();
                    }
                    resetLongPressTracking();
                    return;
                }
                if (this.mTrackingFirstDownKeyEvent != null && this.mTrackingFirstDownKeyEvent.getDownTime() == keyEvent.getDownTime() && this.mTrackingFirstDownKeyEvent.getKeyCode() == keyEvent.getKeyCode() && keyEvent.getAction() == 0) {
                    if (isFirstLongPressKeyEvent(keyEvent)) {
                        if (this.mMultiTapTimeoutRunnable != null) {
                            runExistingMultiTapRunnableLocked();
                        }
                        if ((i4 & 8) == 0) {
                            if (this.mKeyType == 1) {
                                if (com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener == null) {
                                    com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchVolumeKeyEventLocked(str, str2, i, i2, z, keyEvent, i3, z3);
                                    this.mTrackingFirstDownKeyEvent = null;
                                    return;
                                }
                                return;
                            }
                            if (!com.android.server.media.MediaSessionService.SessionManagerImpl.this.isVoiceKey(keyEvent.getKeyCode())) {
                                com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, keyEvent, z2);
                                this.mTrackingFirstDownKeyEvent = null;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (keyEvent.getRepeatCount() > 1 && !this.mIsLongPressing) {
                        resetLongPressTracking();
                    }
                }
            }

            private boolean needTracking(android.view.KeyEvent keyEvent, int i) {
                if (!isFirstDownKeyEvent(keyEvent) && (this.mTrackingFirstDownKeyEvent == null || this.mTrackingFirstDownKeyEvent.getDownTime() != keyEvent.getDownTime() || this.mTrackingFirstDownKeyEvent.getKeyCode() != keyEvent.getKeyCode())) {
                    return false;
                }
                if (i == 0) {
                    if (this.mKeyType == 1) {
                        if (com.android.server.media.MediaSessionService.this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener == null) {
                            return false;
                        }
                    } else if (!com.android.server.media.MediaSessionService.SessionManagerImpl.this.isVoiceKey(keyEvent.getKeyCode())) {
                        return false;
                    }
                }
                return true;
            }

            private void runExistingMultiTapRunnableLocked() {
                com.android.server.media.MediaSessionService.this.mHandler.removeCallbacks(this.mMultiTapTimeoutRunnable);
                this.mMultiTapTimeoutRunnable.run();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void resetMultiTapTrackingLocked() {
                this.mMultiTapCount = 0;
                this.mMultiTapTimeoutRunnable = null;
                this.mMultiTapKeyCode = 0;
            }

            private void handleLongPressLocked(android.view.KeyEvent keyEvent, boolean z, int i) {
                if (com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher != null && com.android.server.media.MediaKeyDispatcher.isLongPressOverridden(i)) {
                    com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.onLongPress(keyEvent);
                    if (this.mLongPressTimeoutRunnable != null) {
                        com.android.server.media.MediaSessionService.this.mHandler.removeCallbacks(this.mLongPressTimeoutRunnable);
                    }
                    if (keyEvent.getAction() == 0) {
                        if (this.mLongPressTimeoutRunnable == null) {
                            this.mLongPressTimeoutRunnable = createLongPressTimeoutRunnable(keyEvent);
                        }
                        com.android.server.media.MediaSessionService.this.mHandler.postDelayed(this.mLongPressTimeoutRunnable, com.android.server.media.MediaSessionService.LONG_PRESS_TIMEOUT);
                        return;
                    }
                    resetLongPressTracking();
                    return;
                }
                if (this.mKeyType == 1) {
                    if (isFirstLongPressKeyEvent(keyEvent)) {
                        com.android.server.media.MediaSessionService.this.dispatchVolumeKeyLongPressLocked(this.mTrackingFirstDownKeyEvent);
                    }
                    com.android.server.media.MediaSessionService.this.dispatchVolumeKeyLongPressLocked(keyEvent);
                } else if (isFirstLongPressKeyEvent(keyEvent) && com.android.server.media.MediaSessionService.SessionManagerImpl.this.isVoiceKey(keyEvent.getKeyCode())) {
                    com.android.server.media.MediaSessionService.SessionManagerImpl.this.startVoiceInput(z);
                    resetLongPressTracking();
                }
            }

            private java.lang.Runnable createLongPressTimeoutRunnable(final android.view.KeyEvent keyEvent) {
                return new java.lang.Runnable() { // from class: com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher != null) {
                            com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.onLongPress(com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.this.createCanceledKeyEvent(keyEvent));
                        }
                        com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.this.resetLongPressTracking();
                    }
                };
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void resetLongPressTracking() {
                this.mTrackingFirstDownKeyEvent = null;
                this.mIsLongPressing = false;
                this.mLongPressTimeoutRunnable = null;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public android.view.KeyEvent createCanceledKeyEvent(android.view.KeyEvent keyEvent) {
                return android.view.KeyEvent.changeTimeRepeat(android.view.KeyEvent.changeAction(keyEvent, 1), java.lang.System.currentTimeMillis(), 0, 32);
            }

            private boolean isFirstLongPressKeyEvent(android.view.KeyEvent keyEvent) {
                return (keyEvent.getFlags() & 128) != 0 && keyEvent.getRepeatCount() == 1;
            }

            private boolean isFirstDownKeyEvent(android.view.KeyEvent keyEvent) {
                return keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void dispatchDownAndUpKeyEventsLocked(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, boolean z2, java.lang.String str2, int i3, boolean z3) {
                android.view.KeyEvent changeAction = android.view.KeyEvent.changeAction(keyEvent, 0);
                if (this.mKeyType == 1) {
                    com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchVolumeKeyEventLocked(str, str2, i, i2, z, changeAction, i3, z3);
                    com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchVolumeKeyEventLocked(str, str2, i, i2, z, keyEvent, i3, z3);
                } else {
                    com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, changeAction, z2);
                    com.android.server.media.MediaSessionService.SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, keyEvent, z2);
                }
            }

            java.lang.Runnable createSingleTapRunnable(final java.lang.String str, final int i, final int i2, final boolean z, final android.view.KeyEvent keyEvent, final boolean z2, final java.lang.String str2, final int i3, final boolean z3, final boolean z4) {
                return new java.lang.Runnable() { // from class: com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.2
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.this.resetMultiTapTrackingLocked();
                        if (z4) {
                            com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.onSingleTap(keyEvent);
                        } else {
                            com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.this.dispatchDownAndUpKeyEventsLocked(str, i, i2, z, keyEvent, z2, str2, i3, z3);
                        }
                    }
                };
            }

            java.lang.Runnable createDoubleTapRunnable(final java.lang.String str, final int i, final int i2, final boolean z, final android.view.KeyEvent keyEvent, final boolean z2, final java.lang.String str2, final int i3, final boolean z3, final boolean z4, final boolean z5) {
                return new java.lang.Runnable() { // from class: com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.3
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.this.resetMultiTapTrackingLocked();
                        if (z5) {
                            com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.onDoubleTap(keyEvent);
                        } else if (z4) {
                            com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.onSingleTap(keyEvent);
                            com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.onSingleTap(keyEvent);
                        } else {
                            com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.this.dispatchDownAndUpKeyEventsLocked(str, i, i2, z, keyEvent, z2, str2, i3, z3);
                            com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.this.dispatchDownAndUpKeyEventsLocked(str, i, i2, z, keyEvent, z2, str2, i3, z3);
                        }
                    }
                };
            }

            private void onTripleTap(android.view.KeyEvent keyEvent) {
                resetMultiTapTrackingLocked();
                com.android.server.media.MediaSessionService.this.mCustomMediaKeyDispatcher.onTripleTap(keyEvent);
            }
        }
    }

    final class MessageHandler extends android.os.Handler {
        private static final int MSG_SESSIONS_1_CHANGED = 1;
        private static final int MSG_SESSIONS_2_CHANGED = 2;
        private final android.util.SparseArray<java.lang.Integer> mIntegerCache = new android.util.SparseArray<>();

        MessageHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.media.MediaSessionService.this.pushSession1Changed(((java.lang.Integer) message.obj).intValue());
                    break;
                case 2:
                    com.android.server.media.MediaSessionService.this.pushSession2Changed(((java.lang.Integer) message.obj).intValue());
                    break;
            }
        }

        public void postSessionsChanged(com.android.server.media.MediaSessionRecordImpl mediaSessionRecordImpl) {
            java.lang.Integer num = this.mIntegerCache.get(mediaSessionRecordImpl.getUserId());
            if (num == null) {
                num = java.lang.Integer.valueOf(mediaSessionRecordImpl.getUserId());
                this.mIntegerCache.put(mediaSessionRecordImpl.getUserId(), num);
            }
            int i = mediaSessionRecordImpl instanceof com.android.server.media.MediaSessionRecord ? 1 : 2;
            removeMessages(i, num);
            obtainMessage(i, num).sendToTarget();
        }
    }
}
