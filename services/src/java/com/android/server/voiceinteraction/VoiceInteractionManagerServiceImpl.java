package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
class VoiceInteractionManagerServiceImpl implements com.android.server.voiceinteraction.VoiceInteractionSessionConnection.Callback {
    static final java.lang.String CLOSE_REASON_VOICE_INTERACTION = "voiceinteraction";
    static final boolean DEBUG = false;
    private static final long REQUEST_DIRECT_ACTIONS_RETRY_TIME_MS = 200;
    private static final boolean SYSPROP_VISUAL_QUERY_SERVICE_ENABLED = android.os.SystemProperties.getBoolean("ro.hotword.visual_query_service_enabled", false);
    static final java.lang.String TAG = "VoiceInteractionServiceManager";
    com.android.server.voiceinteraction.VoiceInteractionSessionConnection mActiveSession;
    final android.content.ComponentName mComponent;
    final android.content.Context mContext;
    int mDisabledShowContext;
    final android.os.Handler mHandler;
    final android.content.ComponentName mHotwordDetectionComponentName;
    volatile com.android.server.voiceinteraction.HotwordDetectionConnection mHotwordDetectionConnection;
    final android.view.IWindowManager mIWindowManager;
    final android.service.voice.VoiceInteractionServiceInfo mInfo;
    final android.content.pm.PackageManagerInternal mPackageManagerInternal;
    android.service.voice.IVoiceInteractionService mService;
    final com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub mServiceStub;
    final android.content.ComponentName mSessionComponentName;
    final int mUser;
    final boolean mValid;
    final android.content.ComponentName mVisualQueryDetectionComponentName;
    boolean mBound = false;
    final android.content.BroadcastReceiver mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                java.lang.String stringExtra = intent.getStringExtra(com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_KEY);
                if (!com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.CLOSE_REASON_VOICE_INTERACTION.equals(stringExtra) && !android.text.TextUtils.equals("dream", stringExtra) && !com.android.server.policy.PhoneWindowManager.SYSTEM_DIALOG_REASON_ASSIST.equals(stringExtra)) {
                    synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mServiceStub) {
                        if (com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mActiveSession != null && com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mActiveSession.mSession != null) {
                            try {
                                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mActiveSession.mSession.closeSystemDialogs();
                            } catch (android.os.RemoteException e) {
                            }
                        }
                    }
                }
            }
        }
    };
    final android.content.ServiceConnection mConnection = new android.content.ServiceConnection() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mServiceStub) {
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mService = android.service.voice.IVoiceInteractionService.Stub.asInterface(iBinder);
                try {
                    com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mService.ready();
                } catch (android.os.RemoteException e) {
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            synchronized (com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mServiceStub) {
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mService = null;
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.resetHotwordDetectionConnectionLocked();
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(android.content.ComponentName componentName) {
            android.content.pm.ParceledListSlice parceledListSlice;
            android.util.Slog.d(com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.TAG, "onBindingDied to " + componentName);
            java.lang.String packageName = componentName.getPackageName();
            try {
                parceledListSlice = com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mAm.getHistoricalProcessExitReasons(packageName, 0, 1, com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mUser);
            } catch (android.os.RemoteException e) {
                parceledListSlice = null;
            }
            if (parceledListSlice == null) {
                return;
            }
            java.util.List list = parceledListSlice.getList();
            if (list.isEmpty()) {
                return;
            }
            android.app.ApplicationExitInfo applicationExitInfo = (android.app.ApplicationExitInfo) list.get(0);
            if (applicationExitInfo.getReason() == 10 && applicationExitInfo.getSubReason() == 23) {
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mServiceStub.handleUserStop(packageName, com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mUser);
            }
        }
    };
    final java.util.ArrayList<com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener> mAccessibilitySettingsListeners = new java.util.ArrayList<>();
    final android.os.Handler mDirectActionsHandler = new android.os.Handler(true);
    final android.app.IActivityManager mAm = android.app.ActivityManager.getService();
    final android.app.IActivityTaskManager mAtm = android.app.ActivityTaskManager.getService();

    interface DetectorRemoteExceptionListener {
        void onDetectorRemoteException(@android.annotation.NonNull android.os.IBinder iBinder, int i);
    }

    VoiceInteractionManagerServiceImpl(android.content.Context context, android.os.Handler handler, com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub, int i, android.content.ComponentName componentName) {
        android.content.ComponentName componentName2;
        this.mContext = context;
        this.mHandler = handler;
        this.mServiceStub = voiceInteractionManagerServiceStub;
        this.mUser = i;
        this.mComponent = componentName;
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        java.util.Objects.requireNonNull(packageManagerInternal);
        this.mPackageManagerInternal = packageManagerInternal;
        try {
            this.mInfo = new android.service.voice.VoiceInteractionServiceInfo(context.getPackageManager(), componentName, this.mUser);
            if (this.mInfo.getParseError() != null) {
                android.util.Slog.w(TAG, "Bad voice interaction service: " + this.mInfo.getParseError());
                this.mSessionComponentName = null;
                this.mHotwordDetectionComponentName = null;
                this.mVisualQueryDetectionComponentName = null;
                this.mIWindowManager = null;
                this.mValid = false;
                return;
            }
            this.mValid = true;
            this.mSessionComponentName = new android.content.ComponentName(componentName.getPackageName(), this.mInfo.getSessionService());
            java.lang.String hotwordDetectionService = this.mInfo.getHotwordDetectionService();
            if (hotwordDetectionService == null) {
                componentName2 = null;
            } else {
                componentName2 = new android.content.ComponentName(componentName.getPackageName(), hotwordDetectionService);
            }
            this.mHotwordDetectionComponentName = componentName2;
            java.lang.String visualQueryDetectionService = this.mInfo.getVisualQueryDetectionService();
            this.mVisualQueryDetectionComponentName = visualQueryDetectionService != null ? new android.content.ComponentName(componentName.getPackageName(), visualQueryDetectionService) : null;
            this.mIWindowManager = android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService("window"));
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
            this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, null, handler, 2);
            new com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.AccessibilitySettingsContentObserver().register(this.mContext.getContentResolver());
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "Voice interaction service not found: " + componentName, e);
            this.mInfo = null;
            this.mSessionComponentName = null;
            this.mHotwordDetectionComponentName = null;
            this.mVisualQueryDetectionComponentName = null;
            this.mIWindowManager = null;
            this.mValid = false;
        }
    }

    public void grantImplicitAccessLocked(int i, @android.annotation.Nullable android.content.Intent intent) {
        int appId = android.os.UserHandle.getAppId(i);
        this.mPackageManagerInternal.grantImplicitAccess(android.os.UserHandle.getUserId(i), intent, appId, this.mInfo.getServiceInfo().applicationInfo.uid, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x005f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean showSessionLocked(@android.annotation.Nullable android.os.Bundle bundle, int i, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, @android.annotation.Nullable android.os.IBinder iBinder) {
        java.util.List<com.android.server.wm.ActivityAssistInfo> list;
        int nextShowSessionId = this.mServiceStub.getNextShowSessionId();
        android.os.Bundle bundle2 = bundle == null ? new android.os.Bundle() : bundle;
        bundle2.putInt("android.service.voice.SHOW_SESSION_ID", nextShowSessionId);
        try {
            if (this.mService != null) {
                try {
                    this.mService.prepareToShowSession(bundle2, i);
                } catch (android.os.RemoteException e) {
                    e = e;
                    android.util.Slog.w(TAG, "RemoteException while calling prepareToShowSession", e);
                    if (this.mActiveSession == null) {
                    }
                    if (!this.mActiveSession.mBound) {
                    }
                    java.util.List<com.android.server.wm.ActivityAssistInfo> topVisibleActivities = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getTopVisibleActivities();
                    if (iBinder == null) {
                    }
                    return this.mActiveSession.showLocked(bundle2, i, str, this.mDisabledShowContext, iVoiceInteractionSessionShowCallback, list);
                }
            }
        } catch (android.os.RemoteException e2) {
            e = e2;
        }
        if (this.mActiveSession == null) {
            this.mActiveSession = new com.android.server.voiceinteraction.VoiceInteractionSessionConnection(this.mServiceStub, this.mSessionComponentName, this.mUser, this.mContext, this, this.mInfo.getServiceInfo().applicationInfo.uid, this.mHandler);
        }
        if (!this.mActiveSession.mBound) {
            try {
                if (this.mService != null) {
                    android.os.Bundle bundle3 = new android.os.Bundle();
                    bundle3.putInt("android.service.voice.SHOW_SESSION_ID", nextShowSessionId);
                    this.mService.showSessionFailed(bundle3);
                }
            } catch (android.os.RemoteException e3) {
                android.util.Slog.w(TAG, "RemoteException while calling showSessionFailed", e3);
            }
        }
        java.util.List<com.android.server.wm.ActivityAssistInfo> topVisibleActivities2 = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getTopVisibleActivities();
        if (iBinder == null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int size = topVisibleActivities2.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    break;
                }
                com.android.server.wm.ActivityAssistInfo activityAssistInfo = topVisibleActivities2.get(i2);
                if (activityAssistInfo.getActivityToken() != iBinder) {
                    i2++;
                } else {
                    arrayList.add(activityAssistInfo);
                    break;
                }
            }
            list = arrayList;
        } else {
            list = topVisibleActivities2;
        }
        return this.mActiveSession.showLocked(bundle2, i, str, this.mDisabledShowContext, iVoiceInteractionSessionShowCallback, list);
    }

    public void getActiveServiceSupportedActions(java.util.List<java.lang.String> list, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "Not bound to voice interaction service " + this.mComponent);
            try {
                iVoiceActionCheckCallback.onComplete((java.util.List) null);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        try {
            this.mService.getActiveServiceSupportedActions(list, iVoiceActionCheckCallback);
        } catch (android.os.RemoteException e2) {
            android.util.Slog.w(TAG, "RemoteException while calling getActiveServiceSupportedActions", e2);
        }
    }

    public boolean hideSessionLocked() {
        if (this.mActiveSession != null) {
            return this.mActiveSession.hideLocked();
        }
        return false;
    }

    public boolean deliverNewSessionLocked(android.os.IBinder iBinder, android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
        if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
            android.util.Slog.w(TAG, "deliverNewSession does not match active session");
            return false;
        }
        this.mActiveSession.deliverNewSessionLocked(iVoiceInteractionSession, iVoiceInteractor);
        return true;
    }

    public int startVoiceActivityLocked(@android.annotation.Nullable java.lang.String str, int i, int i2, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str2) {
        try {
            if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
                android.util.Slog.w(TAG, "startVoiceActivity does not match active session");
                return -99;
            }
            if (!this.mActiveSession.mShown) {
                android.util.Slog.w(TAG, "startVoiceActivity not allowed on hidden session");
                return -100;
            }
            android.content.Intent intent2 = new android.content.Intent(intent);
            intent2.addCategory("android.intent.category.VOICE");
            intent2.addFlags(android.hardware.audio.common.V2_0.AudioFormat.MP2);
            return this.mAtm.startVoiceActivity(this.mComponent.getPackageName(), str, i, i2, intent2, str2, this.mActiveSession.mSession, this.mActiveSession.mInteractor, 0, (android.app.ProfilerInfo) null, (android.os.Bundle) null, this.mUser);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Unexpected remote error", e);
        }
    }

    public int startAssistantActivityLocked(@android.annotation.Nullable java.lang.String str, int i, int i2, android.os.IBinder iBinder, android.content.Intent intent, java.lang.String str2, @android.annotation.NonNull android.os.Bundle bundle) {
        try {
            if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
                android.util.Slog.w(TAG, "startAssistantActivity does not match active session");
                return -89;
            }
            if (!this.mActiveSession.mShown) {
                android.util.Slog.w(TAG, "startAssistantActivity not allowed on hidden session");
                return -90;
            }
            android.content.Intent intent2 = new android.content.Intent(intent);
            intent2.addFlags(268435456);
            bundle.putInt("android.activity.activityType", 4);
            return this.mAtm.startAssistantActivity(this.mComponent.getPackageName(), str, i, i2, intent2, str2, bundle, this.mUser);
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Unexpected remote error", e);
        }
    }

    public void requestDirectActionsLocked(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.os.IBinder iBinder2, @android.annotation.Nullable android.os.RemoteCallback remoteCallback, @android.annotation.NonNull android.os.RemoteCallback remoteCallback2) {
        if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
            android.util.Slog.w(TAG, "requestDirectActionsLocked does not match active session");
            remoteCallback2.sendResult((android.os.Bundle) null);
            return;
        }
        com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getAttachedNonFinishingActivityForTask(i, null);
        if (attachedNonFinishingActivityForTask == null || attachedNonFinishingActivityForTask.getAssistToken() != iBinder2) {
            android.util.Slog.w(TAG, "Unknown activity to query for direct actions");
            this.mDirectActionsHandler.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.HexConsumer() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda0
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6) {
                    ((com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl) obj).retryRequestDirectActions((android.os.IBinder) obj2, ((java.lang.Integer) obj3).intValue(), (android.os.IBinder) obj4, (android.os.RemoteCallback) obj5, (android.os.RemoteCallback) obj6);
                }
            }, this, iBinder, java.lang.Integer.valueOf(i), iBinder2, remoteCallback, remoteCallback2), REQUEST_DIRECT_ACTIONS_RETRY_TIME_MS);
            return;
        }
        grantImplicitAccessLocked(attachedNonFinishingActivityForTask.getUid(), null);
        try {
            attachedNonFinishingActivityForTask.getApplicationThread().requestDirectActions(attachedNonFinishingActivityForTask.getActivityToken(), this.mActiveSession.mInteractor, remoteCallback, remoteCallback2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("Unexpected remote error", e);
            remoteCallback2.sendResult((android.os.Bundle) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void retryRequestDirectActions(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.NonNull android.os.IBinder iBinder2, @android.annotation.Nullable android.os.RemoteCallback remoteCallback, @android.annotation.NonNull android.os.RemoteCallback remoteCallback2) {
        synchronized (this.mServiceStub) {
            if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
                android.util.Slog.w(TAG, "retryRequestDirectActions does not match active session");
                remoteCallback2.sendResult((android.os.Bundle) null);
                return;
            }
            com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getAttachedNonFinishingActivityForTask(i, null);
            if (attachedNonFinishingActivityForTask == null || attachedNonFinishingActivityForTask.getAssistToken() != iBinder2) {
                android.util.Slog.w(TAG, "Unknown activity to query for direct actions during retrying");
                remoteCallback2.sendResult((android.os.Bundle) null);
            } else {
                try {
                    attachedNonFinishingActivityForTask.getApplicationThread().requestDirectActions(attachedNonFinishingActivityForTask.getActivityToken(), this.mActiveSession.mInteractor, remoteCallback, remoteCallback2);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w("Unexpected remote error", e);
                    remoteCallback2.sendResult((android.os.Bundle) null);
                }
            }
        }
    }

    void performDirectActionLocked(@android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable android.os.Bundle bundle, int i, android.os.IBinder iBinder2, @android.annotation.Nullable android.os.RemoteCallback remoteCallback, @android.annotation.NonNull android.os.RemoteCallback remoteCallback2) {
        if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
            android.util.Slog.w(TAG, "performDirectActionLocked does not match active session");
            remoteCallback2.sendResult((android.os.Bundle) null);
            return;
        }
        com.android.server.wm.ActivityTaskManagerInternal.ActivityTokens attachedNonFinishingActivityForTask = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getAttachedNonFinishingActivityForTask(i, null);
        if (attachedNonFinishingActivityForTask == null || attachedNonFinishingActivityForTask.getAssistToken() != iBinder2) {
            android.util.Slog.w(TAG, "Unknown activity to perform a direct action");
            remoteCallback2.sendResult((android.os.Bundle) null);
            return;
        }
        try {
            attachedNonFinishingActivityForTask.getApplicationThread().performDirectAction(attachedNonFinishingActivityForTask.getActivityToken(), str, bundle, remoteCallback, remoteCallback2);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("Unexpected remote error", e);
            remoteCallback2.sendResult((android.os.Bundle) null);
        }
    }

    public void setKeepAwakeLocked(android.os.IBinder iBinder, boolean z) {
        try {
            if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
                android.util.Slog.w(TAG, "setKeepAwake does not match active session");
            } else {
                this.mAtm.setVoiceKeepAwake(this.mActiveSession.mSession, z);
            }
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Unexpected remote error", e);
        }
    }

    public void closeSystemDialogsLocked(android.os.IBinder iBinder) {
        try {
            if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
                android.util.Slog.w(TAG, "closeSystemDialogs does not match active session");
            } else {
                this.mAm.closeSystemDialogs(CLOSE_REASON_VOICE_INTERACTION);
            }
        } catch (android.os.RemoteException e) {
            throw new java.lang.IllegalStateException("Unexpected remote error", e);
        }
    }

    public void finishLocked(android.os.IBinder iBinder, boolean z) {
        if (this.mActiveSession == null || (!z && iBinder != this.mActiveSession.mToken)) {
            android.util.Slog.w(TAG, "finish does not match active session");
        } else {
            this.mActiveSession.cancelLocked(z);
            this.mActiveSession = null;
        }
    }

    public void setDisabledShowContextLocked(int i, int i2) {
        int i3 = this.mInfo.getServiceInfo().applicationInfo.uid;
        if (i != i3) {
            throw new java.lang.SecurityException("Calling uid " + i + " does not match active uid " + i3);
        }
        this.mDisabledShowContext = i2;
    }

    public int getDisabledShowContextLocked(int i) {
        int i2 = this.mInfo.getServiceInfo().applicationInfo.uid;
        if (i != i2) {
            throw new java.lang.SecurityException("Calling uid " + i + " does not match active uid " + i2);
        }
        return this.mDisabledShowContext;
    }

    public int getUserDisabledShowContextLocked(int i) {
        int i2 = this.mInfo.getServiceInfo().applicationInfo.uid;
        if (i != i2) {
            throw new java.lang.SecurityException("Calling uid " + i + " does not match active uid " + i2);
        }
        if (this.mActiveSession != null) {
            return this.mActiveSession.getUserDisabledShowContextLocked();
        }
        return 0;
    }

    public boolean supportsLocalVoiceInteraction() {
        return this.mInfo.getSupportsLocalInteraction();
    }

    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.mInfo.getServiceInfo().applicationInfo;
    }

    public void startListeningVisibleActivityChangedLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
            android.util.Slog.w(TAG, "startListeningVisibleActivityChangedLocked does not match active session");
        } else {
            this.mActiveSession.startListeningVisibleActivityChangedLocked();
        }
    }

    public void stopListeningVisibleActivityChangedLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        if (this.mActiveSession == null || iBinder != this.mActiveSession.mToken) {
            android.util.Slog.w(TAG, "stopListeningVisibleActivityChangedLocked does not match active session");
        } else {
            this.mActiveSession.stopListeningVisibleActivityChangedLocked();
        }
    }

    public void notifyActivityDestroyedLocked(@android.annotation.NonNull android.os.IBinder iBinder) {
        if (this.mActiveSession == null || !this.mActiveSession.mShown) {
            return;
        }
        this.mActiveSession.notifyActivityDestroyedLocked(iBinder);
    }

    public void notifyActivityEventChangedLocked(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        if (this.mActiveSession == null || !this.mActiveSession.mShown) {
            return;
        }
        this.mActiveSession.notifyActivityEventChangedLocked(iBinder, i);
    }

    public void updateStateLocked(@android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.Nullable android.os.SharedMemory sharedMemory, @android.annotation.NonNull android.os.IBinder iBinder) {
        android.util.Slog.v(TAG, "updateStateLocked");
        if (sharedMemory != null && !sharedMemory.setProtect(android.system.OsConstants.PROT_READ)) {
            android.util.Slog.w(TAG, "Can't set sharedMemory to be read-only");
            throw new java.lang.IllegalStateException("Can't set sharedMemory to be read-only");
        }
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "update State, but no hotword detection connection");
            throw new java.lang.IllegalStateException("Hotword detection connection not found");
        }
        synchronized (this.mHotwordDetectionConnection.mLock) {
            this.mHotwordDetectionConnection.updateStateLocked(persistableBundle, sharedMemory, iBinder);
        }
    }

    private void verifyDetectorForHotwordDetectionLocked(@android.annotation.Nullable android.os.SharedMemory sharedMemory, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) {
        android.util.Slog.v(TAG, "verifyDetectorForHotwordDetectionLocked");
        int i2 = this.mInfo.getServiceInfo().applicationInfo.uid;
        if (this.mHotwordDetectionComponentName == null) {
            android.util.Slog.w(TAG, "Hotword detection service name not found");
            logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback, i, false, i2);
            throw new java.lang.IllegalStateException("Hotword detection service name not found");
        }
        android.content.pm.ServiceInfo serviceInfoLocked = getServiceInfoLocked(this.mHotwordDetectionComponentName, this.mUser);
        if (serviceInfoLocked == null) {
            android.util.Slog.w(TAG, "Hotword detection service info not found");
            logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback, i, false, i2);
            throw new java.lang.IllegalStateException("Hotword detection service info not found");
        }
        if (!isIsolatedProcessLocked(serviceInfoLocked)) {
            android.util.Slog.w(TAG, "Hotword detection service not in isolated process");
            logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback, i, false, i2);
            throw new java.lang.IllegalStateException("Hotword detection service not in isolated process");
        }
        if (!"android.permission.BIND_HOTWORD_DETECTION_SERVICE".equals(serviceInfoLocked.permission)) {
            android.util.Slog.w(TAG, "Hotword detection service does not require permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
            logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback, i, false, i2);
            throw new java.lang.SecurityException("Hotword detection service does not require permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
        }
        if (this.mContext.getPackageManager().checkPermission("android.permission.BIND_HOTWORD_DETECTION_SERVICE", this.mInfo.getServiceInfo().packageName) == 0) {
            android.util.Slog.w(TAG, "Voice interaction service should not hold permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
            logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback, i, false, i2);
            throw new java.lang.SecurityException("Voice interaction service should not hold permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
        }
        if (sharedMemory != null && !sharedMemory.setProtect(android.system.OsConstants.PROT_READ)) {
            android.util.Slog.w(TAG, "Can't set sharedMemory to be read-only");
            logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback, i, false, i2);
            throw new java.lang.IllegalStateException("Can't set sharedMemory to be read-only");
        }
        logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback, i, true, i2);
    }

    private void verifyDetectorForVisualQueryDetectionLocked(@android.annotation.Nullable android.os.SharedMemory sharedMemory) {
        android.util.Slog.v(TAG, "verifyDetectorForVisualQueryDetectionLocked");
        if (this.mVisualQueryDetectionComponentName == null) {
            android.util.Slog.w(TAG, "Visual query detection service name not found");
            throw new java.lang.IllegalStateException("Visual query detection service name not found");
        }
        android.content.pm.ServiceInfo serviceInfoLocked = getServiceInfoLocked(this.mVisualQueryDetectionComponentName, this.mUser);
        if (serviceInfoLocked == null) {
            android.util.Slog.w(TAG, "Visual query detection service info not found");
            throw new java.lang.IllegalStateException("Visual query detection service name not found");
        }
        if (!isIsolatedProcessLocked(serviceInfoLocked)) {
            android.util.Slog.w(TAG, "Visual query detection service not in isolated process");
            throw new java.lang.IllegalStateException("Visual query detection not in isolated process");
        }
        if (!"android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE".equals(serviceInfoLocked.permission)) {
            android.util.Slog.w(TAG, "Visual query detection does not require permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
            throw new java.lang.SecurityException("Visual query detection does not require permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
        }
        if (this.mContext.getPackageManager().checkPermission("android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE", this.mInfo.getServiceInfo().packageName) == 0) {
            android.util.Slog.w(TAG, "Voice interaction service should not hold permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
            throw new java.lang.SecurityException("Voice interaction service should not hold permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
        }
        if (sharedMemory != null && !sharedMemory.setProtect(android.system.OsConstants.PROT_READ)) {
            android.util.Slog.w(TAG, "Can't set sharedMemory to be read-only");
            throw new java.lang.IllegalStateException("Can't set sharedMemory to be read-only");
        }
    }

    public void initAndVerifyDetectorLocked(@android.annotation.NonNull android.media.permission.Identity identity, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.Nullable android.os.SharedMemory sharedMemory, @android.annotation.NonNull android.os.IBinder iBinder, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i) {
        if (i != 3) {
            verifyDetectorForHotwordDetectionLocked(sharedMemory, iHotwordRecognitionStatusCallback, i);
        } else {
            verifyDetectorForVisualQueryDetectionLocked(sharedMemory);
        }
        if (SYSPROP_VISUAL_QUERY_SERVICE_ENABLED && !verifyProcessSharingLocked()) {
            android.util.Slog.w(TAG, "Sandboxed detection service not in shared isolated process");
            throw new java.lang.IllegalStateException("VisualQueryDetectionService or HotworDetectionService not in a shared isolated process. Please make sure to set android:allowSharedIsolatedProcess and android:isolatedProcess to be true and android:externalService to be false in the manifest file");
        }
        if (this.mHotwordDetectionConnection == null) {
            this.mHotwordDetectionConnection = new com.android.server.voiceinteraction.HotwordDetectionConnection(this.mServiceStub, this.mContext, this.mInfo.getServiceInfo().applicationInfo.uid, identity, this.mHotwordDetectionComponentName, this.mVisualQueryDetectionComponentName, this.mUser, false, i, new com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1
                @Override // com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.DetectorRemoteExceptionListener
                public final void onDetectorRemoteException(android.os.IBinder iBinder2, int i2) {
                    com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.lambda$initAndVerifyDetectorLocked$0(iBinder2, i2);
                }
            });
            registerAccessibilityDetectionSettingsListenerLocked(this.mHotwordDetectionConnection.mAccessibilitySettingsListener);
        } else if (i != 3) {
            this.mHotwordDetectionConnection.setDetectorType(i);
        }
        this.mHotwordDetectionConnection.createDetectorLocked(persistableBundle, sharedMemory, iBinder, iHotwordRecognitionStatusCallback, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initAndVerifyDetectorLocked$0(android.os.IBinder iBinder, int i) {
        try {
            this.mService.detectorRemoteExceptionOccurred(iBinder, i);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Fail to notify client detector remote exception occurred.");
        }
    }

    public void destroyDetectorLocked(android.os.IBinder iBinder) {
        android.util.Slog.v(TAG, "destroyDetectorLocked");
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "destroy detector callback, but no hotword detection connection");
        } else {
            this.mHotwordDetectionConnection.destroyDetectorLocked(iBinder);
        }
    }

    private void logDetectorCreateEventIfNeeded(com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, int i, boolean z, int i2) {
        if (iHotwordRecognitionStatusCallback != null) {
            com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorCreateEvent(i, z, i2);
        }
    }

    public void shutdownHotwordDetectionServiceLocked() {
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "shutdown, but no hotword detection connection");
            return;
        }
        this.mHotwordDetectionConnection.cancelLocked();
        unregisterAccessibilityDetectionSettingsListenerLocked(this.mHotwordDetectionConnection.mAccessibilitySettingsListener);
        this.mHotwordDetectionConnection = null;
    }

    public void setVisualQueryDetectionAttentionListenerLocked(@android.annotation.Nullable com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) {
        if (this.mHotwordDetectionConnection == null) {
            return;
        }
        this.mHotwordDetectionConnection.setVisualQueryDetectionAttentionListenerLocked(iVisualQueryDetectionAttentionListener);
    }

    public boolean startPerceivingLocked(android.service.voice.IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) {
        if (this.mHotwordDetectionConnection == null) {
            return false;
        }
        return this.mHotwordDetectionConnection.startPerceivingLocked(iVisualQueryDetectionVoiceInteractionCallback);
    }

    public boolean stopPerceivingLocked() {
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "stopPerceivingLocked() called but connection isn't established");
            return false;
        }
        return this.mHotwordDetectionConnection.stopPerceivingLocked();
    }

    public void startListeningFromMicLocked(android.media.AudioFormat audioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        if (this.mHotwordDetectionConnection == null) {
            return;
        }
        this.mHotwordDetectionConnection.startListeningFromMicLocked(audioFormat, iMicrophoneHotwordDetectionVoiceInteractionCallback);
    }

    public void startListeningFromExternalSourceLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, @android.annotation.Nullable android.os.PersistableBundle persistableBundle, @android.annotation.NonNull android.os.IBinder iBinder, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        if (this.mHotwordDetectionConnection == null) {
            return;
        }
        if (parcelFileDescriptor == null) {
            android.util.Slog.w(TAG, "External source is null for hotword detector");
            throw new java.lang.IllegalStateException("External source is null for hotword detector");
        }
        this.mHotwordDetectionConnection.startListeningFromExternalSourceLocked(parcelFileDescriptor, audioFormat, persistableBundle, iBinder, iMicrophoneHotwordDetectionVoiceInteractionCallback);
    }

    public void startListeningFromWearableLocked(android.os.ParcelFileDescriptor parcelFileDescriptor, android.media.AudioFormat audioFormat, android.os.PersistableBundle persistableBundle, android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback wearableHotwordDetectionCallback) {
        if (this.mHotwordDetectionConnection == null) {
            wearableHotwordDetectionCallback.onError("Unable to start listening from wearable because the hotword detection connection is null.");
        } else {
            this.mHotwordDetectionConnection.startListeningFromWearableLocked(parcelFileDescriptor, audioFormat, persistableBundle, wearableHotwordDetectionCallback);
        }
    }

    public void stopListeningFromMicLocked() {
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "stopListeningFromMicLocked() called but connection isn't established");
        } else {
            this.mHotwordDetectionConnection.stopListeningFromMicLocked();
        }
    }

    public void triggerHardwareRecognitionEventForTestLocked(android.hardware.soundtrigger.SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "triggerHardwareRecognitionEventForTestLocked() called but connection isn't established");
        } else {
            this.mHotwordDetectionConnection.triggerHardwareRecognitionEventForTestLocked(keyphraseRecognitionEvent, iHotwordRecognitionStatusCallback);
        }
    }

    public android.hardware.soundtrigger.IRecognitionStatusCallback createSoundTriggerCallbackLocked(android.content.Context context, com.android.internal.app.IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, android.media.permission.Identity identity) {
        return new com.android.server.voiceinteraction.HotwordDetectionConnection.SoundTriggerCallback(context, iHotwordRecognitionStatusCallback, this.mHotwordDetectionConnection, identity);
    }

    private static android.content.pm.ServiceInfo getServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName, int i) {
        try {
            return android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 786560L, i);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    boolean isIsolatedProcessLocked(@android.annotation.NonNull android.content.pm.ServiceInfo serviceInfo) {
        return (serviceInfo.flags & 2) != 0 && (serviceInfo.flags & 4) == 0;
    }

    boolean verifyProcessSharingLocked() {
        android.content.pm.ServiceInfo serviceInfoLocked = getServiceInfoLocked(this.mHotwordDetectionComponentName, this.mUser);
        android.content.pm.ServiceInfo serviceInfoLocked2 = getServiceInfoLocked(this.mVisualQueryDetectionComponentName, this.mUser);
        if (serviceInfoLocked == null || serviceInfoLocked2 == null) {
            return true;
        }
        return ((serviceInfoLocked.flags & 16) == 0 || (serviceInfoLocked2.flags & 16) == 0) ? false : true;
    }

    void forceRestartHotwordDetector() {
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "Failed to force-restart hotword detection: no hotword detection active");
        } else {
            this.mHotwordDetectionConnection.forceRestart();
        }
    }

    void setDebugHotwordLoggingLocked(boolean z) {
        if (this.mHotwordDetectionConnection == null) {
            android.util.Slog.w(TAG, "Failed to set temporary debug logging: no hotword detection active");
        } else {
            this.mHotwordDetectionConnection.setDebugHotwordLoggingLocked(z);
        }
    }

    void resetHotwordDetectionConnectionLocked() {
        if (this.mHotwordDetectionConnection == null) {
            return;
        }
        this.mHotwordDetectionConnection.cancelLocked();
        unregisterAccessibilityDetectionSettingsListenerLocked(this.mHotwordDetectionConnection.mAccessibilitySettingsListener);
        this.mHotwordDetectionConnection = null;
    }

    public void dumpLocked(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (!this.mValid) {
            printWriter.print("  NOT VALID: ");
            if (this.mInfo == null) {
                printWriter.println("no info");
                return;
            } else {
                printWriter.println(this.mInfo.getParseError());
                return;
            }
        }
        printWriter.print("  mUser=");
        printWriter.println(this.mUser);
        printWriter.print("  mComponent=");
        printWriter.println(this.mComponent.flattenToShortString());
        printWriter.print("  Session service=");
        printWriter.println(this.mInfo.getSessionService());
        printWriter.println("  Service info:");
        this.mInfo.getServiceInfo().dump(new android.util.PrintWriterPrinter(printWriter), "    ");
        printWriter.print("  Recognition service=");
        printWriter.println(this.mInfo.getRecognitionService());
        printWriter.print("  Hotword detection service=");
        printWriter.println(this.mInfo.getHotwordDetectionService());
        printWriter.print("  Settings activity=");
        printWriter.println(this.mInfo.getSettingsActivity());
        printWriter.print("  Supports assist=");
        printWriter.println(this.mInfo.getSupportsAssist());
        printWriter.print("  Supports launch from keyguard=");
        printWriter.println(this.mInfo.getSupportsLaunchFromKeyguard());
        if (this.mDisabledShowContext != 0) {
            printWriter.print("  mDisabledShowContext=");
            printWriter.println(java.lang.Integer.toHexString(this.mDisabledShowContext));
        }
        printWriter.print("  mBound=");
        printWriter.print(this.mBound);
        printWriter.print(" mService=");
        printWriter.println(this.mService);
        if (this.mHotwordDetectionConnection != null) {
            printWriter.println("  Hotword detection connection:");
            this.mHotwordDetectionConnection.dump("    ", printWriter);
        } else {
            printWriter.println("  No Hotword detection connection");
        }
        if (this.mActiveSession != null) {
            printWriter.println("  Active session:");
            this.mActiveSession.dump("    ", printWriter);
        }
    }

    boolean getAccessibilityDetectionEnabled() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "visual_query_accessibility_detection_enabled", 0, this.mUser) == 1;
    }

    void registerAccessibilityDetectionSettingsListenerLocked(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) {
        this.mAccessibilitySettingsListeners.add(iVoiceInteractionAccessibilitySettingsListener);
    }

    void unregisterAccessibilityDetectionSettingsListenerLocked(com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) {
        this.mAccessibilitySettingsListeners.remove(iVoiceInteractionAccessibilitySettingsListener);
    }

    void startLocked() {
        android.content.Intent intent = new android.content.Intent("android.service.voice.VoiceInteractionService");
        intent.setComponent(this.mComponent);
        this.mBound = this.mContext.bindServiceAsUser(intent, this.mConnection, 68161537, new android.os.UserHandle(this.mUser));
        if (!this.mBound) {
            android.util.Slog.w(TAG, "Failed binding to voice interaction service " + this.mComponent);
        }
    }

    public void launchVoiceAssistFromKeyguard() {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "Not bound to voice interaction service " + this.mComponent);
            return;
        }
        try {
            this.mService.launchVoiceAssistFromKeyguard();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException while calling launchVoiceAssistFromKeyguard", e);
        }
    }

    void shutdownLocked() {
        if (this.mActiveSession != null) {
            this.mActiveSession.cancelLocked(false);
            this.mActiveSession = null;
        }
        try {
            if (this.mService != null) {
                this.mService.shutdown();
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException in shutdown", e);
        }
        if (this.mHotwordDetectionConnection != null) {
            this.mHotwordDetectionConnection.cancelLocked();
            unregisterAccessibilityDetectionSettingsListenerLocked(this.mHotwordDetectionConnection.mAccessibilitySettingsListener);
            this.mHotwordDetectionConnection = null;
        }
        if (this.mBound) {
            this.mContext.unbindService(this.mConnection);
            this.mBound = false;
        }
        if (this.mValid) {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        }
    }

    void notifySoundModelsChangedLocked() {
        if (this.mService == null) {
            android.util.Slog.w(TAG, "Not bound to voice interaction service " + this.mComponent);
            return;
        }
        try {
            this.mService.soundModelsChanged();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "RemoteException while calling soundModelsChanged", e);
        }
    }

    @Override // com.android.server.voiceinteraction.VoiceInteractionSessionConnection.Callback
    public void sessionConnectionGone(com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection) {
        synchronized (this.mServiceStub) {
            finishLocked(voiceInteractionSessionConnection.mToken, false);
        }
    }

    @Override // com.android.server.voiceinteraction.VoiceInteractionSessionConnection.Callback
    public void onSessionShown(com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection) {
        this.mServiceStub.onSessionShown();
    }

    @Override // com.android.server.voiceinteraction.VoiceInteractionSessionConnection.Callback
    public void onSessionHidden(com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection) {
        this.mServiceStub.onSessionHidden();
        this.mServiceStub.setSessionWindowVisible(voiceInteractionSessionConnection.mToken, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class AccessibilitySettingsContentObserver extends android.database.ContentObserver {
        private android.net.Uri mAccessibilitySettingsEnabledUri;

        AccessibilitySettingsContentObserver() {
            super(null);
            this.mAccessibilitySettingsEnabledUri = android.provider.Settings.Secure.getUriFor("visual_query_accessibility_detection_enabled");
        }

        public void register(android.content.ContentResolver contentResolver) {
            contentResolver.registerContentObserver(this.mAccessibilitySettingsEnabledUri, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            android.util.Slog.i(com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.TAG, "OnChange called with uri:" + uri);
            if (this.mAccessibilitySettingsEnabledUri.equals(uri)) {
                final boolean z2 = android.provider.Settings.Secure.getIntForUser(com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mContext.getContentResolver(), "visual_query_accessibility_detection_enabled", 0, com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mUser) == 1;
                android.util.Slog.i(com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.TAG, "Notifying listeners with Accessibility setting set to " + z2);
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.this.mAccessibilitySettingsListeners.forEach(new java.util.function.Consumer() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl$AccessibilitySettingsContentObserver$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl.AccessibilitySettingsContentObserver.lambda$onChange$0(z2, (com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$onChange$0(boolean z, com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) {
            try {
                iVoiceInteractionAccessibilitySettingsListener.onAccessibilityDetectionChanged(z);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }
}
