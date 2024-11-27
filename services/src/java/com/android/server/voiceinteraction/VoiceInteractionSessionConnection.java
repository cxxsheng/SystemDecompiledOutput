package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
final class VoiceInteractionSessionConnection implements android.content.ServiceConnection, com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks {
    static final int BOOST_TIMEOUT_MS = 300;
    static final boolean DEBUG = false;
    private static final int LOW_POWER_STANDBY_ALLOWLIST_TIMEOUT_MS = 120000;
    static final int MAX_POWER_BOOST_TIMEOUT = 10000;
    static final int POWER_BOOST_TIMEOUT_MS = java.lang.Integer.parseInt(java.lang.System.getProperty("vendor.powerhal.interaction.max", "200"));
    static final java.lang.String TAG = "VoiceInteractionServiceManager";
    final android.app.AppOpsManager mAppOps;
    com.android.server.am.AssistDataRequester mAssistDataRequester;
    boolean mBound;
    final com.android.server.voiceinteraction.VoiceInteractionSessionConnection.Callback mCallback;
    final int mCallingUid;
    boolean mCanceled;
    final android.content.Context mContext;
    boolean mFullyBound;
    final android.os.Handler mHandler;
    com.android.internal.app.IVoiceInteractor mInteractor;
    private boolean mListeningVisibleActivity;
    final java.lang.Object mLock;
    private boolean mLowPowerStandbyAllowlisted;
    final android.os.IBinder mPermissionOwner;
    android.service.voice.IVoiceInteractionSessionService mService;
    android.service.voice.IVoiceInteractionSession mSession;
    final android.content.ComponentName mSessionComponentName;
    private com.android.server.voiceinteraction.VoiceInteractionSessionConnection.PowerBoostSetter mSetPowerBoostRunnable;
    android.os.Bundle mShowArgs;
    int mShowFlags;
    boolean mShown;
    final int mUser;
    final android.os.IBinder mToken = new android.os.Binder();
    java.util.ArrayList<com.android.internal.app.IVoiceInteractionSessionShowCallback> mPendingShowCallbacks = new java.util.ArrayList<>();
    private java.util.List<com.android.server.wm.ActivityAssistInfo> mPendingHandleAssistWithoutData = new java.util.ArrayList();
    private final java.util.concurrent.ScheduledExecutorService mScheduledExecutorService = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
    private final android.util.ArrayMap<android.os.IBinder, android.service.voice.VisibleActivityInfo> mVisibleActivityInfoForToken = new android.util.ArrayMap<>();
    private final java.lang.Runnable mRemoveFromLowPowerStandbyAllowlistRunnable = new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionSessionConnection$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.removeFromLowPowerStandbyAllowlist();
        }
    };
    com.android.internal.app.IVoiceInteractionSessionShowCallback mShowCallback = new com.android.internal.app.IVoiceInteractionSessionShowCallback.Stub() { // from class: com.android.server.voiceinteraction.VoiceInteractionSessionConnection.1
        public void onFailed() throws android.os.RemoteException {
            synchronized (com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mLock) {
                com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.notifyPendingShowCallbacksFailedLocked();
            }
        }

        public void onShown() throws android.os.RemoteException {
            synchronized (com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mLock) {
                com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.notifyPendingShowCallbacksShownLocked();
            }
        }
    };
    final android.content.ServiceConnection mFullConnection = new android.content.ServiceConnection() { // from class: com.android.server.voiceinteraction.VoiceInteractionSessionConnection.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
        }
    };
    private java.lang.Runnable mShowAssistDisclosureRunnable = new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionSessionConnection.3
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.showAssistDisclosure();
            }
        }
    };
    final android.app.IActivityTaskManager mActivityTaskManager = android.app.ActivityTaskManager.getService();
    final android.app.IActivityManager mAm = android.app.ActivityManager.getService();
    final com.android.server.uri.UriGrantsManagerInternal mUgmInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
    final android.view.IWindowManager mIWindowManager = android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService("window"));
    private final android.os.PowerManagerInternal mPowerManagerInternal = (android.os.PowerManagerInternal) com.android.server.LocalServices.getService(android.os.PowerManagerInternal.class);
    private final com.android.server.power.LowPowerStandbyControllerInternal mLowPowerStandbyControllerInternal = (com.android.server.power.LowPowerStandbyControllerInternal) com.android.server.LocalServices.getService(com.android.server.power.LowPowerStandbyControllerInternal.class);
    private final android.os.Handler mFgHandler = com.android.server.FgThread.getHandler();
    final android.content.Intent mBindIntent = new android.content.Intent("android.service.voice.VoiceInteractionService");

    public interface Callback {
        void onSessionHidden(com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection);

        void onSessionShown(com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection);

        void sessionConnectionGone(com.android.server.voiceinteraction.VoiceInteractionSessionConnection voiceInteractionSessionConnection);
    }

    class PowerBoostSetter implements java.lang.Runnable {
        private boolean mCanceled;
        private final java.time.Instant mExpiryTime;

        PowerBoostSetter(java.time.Instant instant) {
            this.mExpiryTime = instant;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mLock) {
                try {
                    if (this.mCanceled) {
                        return;
                    }
                    if (java.time.Instant.now().isBefore(this.mExpiryTime)) {
                        com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mPowerManagerInternal.setPowerBoost(0, 300);
                        if (com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mSetPowerBoostRunnable != null) {
                            com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mFgHandler.postDelayed(com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mSetPowerBoostRunnable, com.android.server.voiceinteraction.VoiceInteractionSessionConnection.POWER_BOOST_TIMEOUT_MS);
                        }
                    } else {
                        android.util.Slog.w(com.android.server.voiceinteraction.VoiceInteractionSessionConnection.TAG, "Reset power boost INTERACTION because reaching max timeout.");
                        com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mPowerManagerInternal.setPowerBoost(0, -1);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        void cancel() {
            synchronized (com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.mLock) {
                this.mCanceled = true;
            }
        }
    }

    public VoiceInteractionSessionConnection(java.lang.Object obj, android.content.ComponentName componentName, int i, android.content.Context context, com.android.server.voiceinteraction.VoiceInteractionSessionConnection.Callback callback, int i2, android.os.Handler handler) {
        this.mLock = obj;
        this.mSessionComponentName = componentName;
        this.mUser = i;
        this.mContext = context;
        this.mCallback = callback;
        this.mCallingUid = i2;
        this.mHandler = handler;
        this.mAppOps = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
        this.mAssistDataRequester = new com.android.server.am.AssistDataRequester(this.mContext, this.mIWindowManager, (android.app.AppOpsManager) this.mContext.getSystemService("appops"), this, this.mLock, 49, 50);
        this.mPermissionOwner = this.mUgmInternal.newUriPermissionOwner("voicesession:" + componentName.flattenToShortString());
        this.mBindIntent.setComponent(this.mSessionComponentName);
        this.mBound = this.mContext.bindServiceAsUser(this.mBindIntent, this, 1048625, new android.os.UserHandle(this.mUser));
        if (this.mBound) {
            try {
                this.mIWindowManager.addWindowToken(this.mToken, 2031, 0, (android.os.Bundle) null);
                return;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, "Failed adding window token", e);
                return;
            }
        }
        android.util.Slog.w(TAG, "Failed binding to voice interaction session service " + this.mSessionComponentName);
    }

    public int getUserDisabledShowContextLocked() {
        int i;
        if (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_structure_enabled", 1, this.mUser) != 0) {
            i = 0;
        } else {
            i = 1;
        }
        if (android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "assist_screenshot_enabled", 1, this.mUser) == 0) {
            return i | 2;
        }
        return i;
    }

    public boolean showLocked(@android.annotation.NonNull android.os.Bundle bundle, int i, @android.annotation.Nullable java.lang.String str, int i2, @android.annotation.Nullable com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, @android.annotation.NonNull java.util.List<com.android.server.wm.ActivityAssistInfo> list) {
        boolean z;
        if (this.mBound) {
            if (!this.mFullyBound) {
                this.mFullyBound = this.mContext.bindServiceAsUser(this.mBindIntent, this.mFullConnection, 404226049, new android.os.UserHandle(this.mUser));
            }
            this.mShown = true;
            this.mShowArgs = bundle;
            this.mShowFlags = i;
            int userDisabledShowContextLocked = i2 | getUserDisabledShowContextLocked();
            boolean z2 = (i & 1) != 0;
            boolean z3 = (i & 2) != 0;
            boolean z4 = z2 || z3;
            if (z4) {
                int size = list.size();
                java.util.ArrayList arrayList = new java.util.ArrayList(size);
                for (int i3 = 0; i3 < size; i3++) {
                    arrayList.add(list.get(i3).getActivityToken());
                }
                boolean z5 = (userDisabledShowContextLocked & 1) == 0;
                try {
                    z = this.mActivityTaskManager.isAssistDataAllowed();
                } catch (android.os.RemoteException e) {
                    z = false;
                }
                if (z5 && z) {
                    java.util.ArrayList<? extends android.os.Parcelable> arrayList2 = new java.util.ArrayList<>(size);
                    for (int i4 = 0; i4 < size; i4++) {
                        arrayList2.add(list.get(i4).getComponentName());
                    }
                    this.mShowArgs.putParcelableArrayList("android.service.voice.FOREGROUND_ACTIVITIES", arrayList2);
                }
                this.mAssistDataRequester.requestAssistData(arrayList, z2, z3, z5, (userDisabledShowContextLocked & 2) == 0, this.mCallingUid, this.mSessionComponentName.getPackageName(), str);
                if ((this.mAssistDataRequester.getPendingDataCount() > 0 || this.mAssistDataRequester.getPendingScreenshotCount() > 0) && com.android.internal.app.AssistUtils.shouldDisclose(this.mContext, this.mSessionComponentName)) {
                    this.mHandler.post(this.mShowAssistDisclosureRunnable);
                }
            }
            if (this.mSession != null) {
                try {
                    this.mSession.show(this.mShowArgs, this.mShowFlags, iVoiceInteractionSessionShowCallback);
                    this.mShowArgs = null;
                    this.mShowFlags = 0;
                } catch (android.os.RemoteException e2) {
                }
                if (z4) {
                    this.mAssistDataRequester.processPendingAssistData();
                } else {
                    doHandleAssistWithoutData(list);
                }
            } else {
                if (iVoiceInteractionSessionShowCallback != null) {
                    this.mPendingShowCallbacks.add(iVoiceInteractionSessionShowCallback);
                }
                if (!z4) {
                    this.mPendingHandleAssistWithoutData = list;
                }
            }
            if (this.mSetPowerBoostRunnable != null) {
                this.mSetPowerBoostRunnable.cancel();
            }
            this.mSetPowerBoostRunnable = new com.android.server.voiceinteraction.VoiceInteractionSessionConnection.PowerBoostSetter(java.time.Instant.now().plusMillis(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY));
            this.mFgHandler.post(this.mSetPowerBoostRunnable);
            if (this.mLowPowerStandbyControllerInternal != null) {
                this.mLowPowerStandbyControllerInternal.addToAllowlist(this.mCallingUid, 1);
                this.mLowPowerStandbyAllowlisted = true;
                this.mFgHandler.removeCallbacks(this.mRemoveFromLowPowerStandbyAllowlistRunnable);
                this.mFgHandler.postDelayed(this.mRemoveFromLowPowerStandbyAllowlistRunnable, 120000L);
            }
            this.mCallback.onSessionShown(this);
            return true;
        }
        if (iVoiceInteractionSessionShowCallback != null) {
            try {
                iVoiceInteractionSessionShowCallback.onFailed();
            } catch (android.os.RemoteException e3) {
            }
        }
        return false;
    }

    private void doHandleAssistWithoutData(java.util.List<com.android.server.wm.ActivityAssistInfo> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            com.android.server.wm.ActivityAssistInfo activityAssistInfo = list.get(i);
            try {
                this.mSession.handleAssist(activityAssistInfo.getTaskId(), activityAssistInfo.getAssistToken(), (android.os.Bundle) null, (android.app.assist.AssistStructure) null, (android.app.assist.AssistContent) null, i, size);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public boolean canHandleReceivedAssistDataLocked() {
        return this.mSession != null;
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public void onAssistDataReceivedLocked(android.os.Bundle bundle, int i, int i2) {
        android.content.ClipData clipData;
        if (this.mSession == null) {
            return;
        }
        if (bundle == null) {
            try {
                this.mSession.handleAssist(-1, (android.os.IBinder) null, (android.os.Bundle) null, (android.app.assist.AssistStructure) null, (android.app.assist.AssistContent) null, 0, 0);
                return;
            } catch (android.os.RemoteException e) {
                return;
            }
        }
        int i3 = bundle.getInt(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_TASK_ID);
        android.os.IBinder binder = bundle.getBinder(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_ACTIVITY_ID);
        android.os.Bundle bundle2 = bundle.getBundle("data");
        android.app.assist.AssistStructure assistStructure = (android.app.assist.AssistStructure) bundle.getParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_STRUCTURE, android.app.assist.AssistStructure.class);
        android.app.assist.AssistContent assistContent = (android.app.assist.AssistContent) bundle.getParcelable(com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT, android.app.assist.AssistContent.class);
        int i4 = bundle2 != null ? bundle2.getInt("android.intent.extra.ASSIST_UID", -1) : -1;
        if (i4 >= 0 && assistContent != null) {
            android.content.Intent intent = assistContent.getIntent();
            if (intent != null && (clipData = intent.getClipData()) != null && android.content.Intent.isAccessUriMode(intent.getFlags())) {
                grantClipDataPermissions(clipData, intent.getFlags(), i4, this.mCallingUid, this.mSessionComponentName.getPackageName());
            }
            android.content.ClipData clipData2 = assistContent.getClipData();
            if (clipData2 != null) {
                grantClipDataPermissions(clipData2, 1, i4, this.mCallingUid, this.mSessionComponentName.getPackageName());
            }
        }
        try {
            this.mSession.handleAssist(i3, binder, bundle2, assistStructure, assistContent, i, i2);
        } catch (android.os.RemoteException e2) {
        }
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public void onAssistScreenshotReceivedLocked(android.graphics.Bitmap bitmap) {
        if (this.mSession == null) {
            return;
        }
        try {
            this.mSession.handleScreenshot(bitmap);
        } catch (android.os.RemoteException e) {
        }
    }

    void grantUriPermission(android.net.Uri uri, int i, int i2, int i3, java.lang.String str) {
        if (!com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                this.mUgmInternal.checkGrantUriPermission(i2, null, android.content.ContentProvider.getUriWithoutUserId(uri), i, android.content.ContentProvider.getUserIdFromUri(uri, android.os.UserHandle.getUserId(i2)));
                int userIdFromUri = android.content.ContentProvider.getUserIdFromUri(uri, this.mUser);
                android.app.UriGrantsManager.getService().grantUriPermissionFromOwner(this.mPermissionOwner, i2, str, android.content.ContentProvider.getUriWithoutUserId(uri), 1, userIdFromUri, this.mUser);
            } catch (android.os.RemoteException e) {
            } catch (java.lang.SecurityException e2) {
                android.util.Slog.w(TAG, "Can't propagate permission", e2);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    void grantClipDataItemPermission(android.content.ClipData.Item item, int i, int i2, int i3, java.lang.String str) {
        if (item.getUri() != null) {
            grantUriPermission(item.getUri(), i, i2, i3, str);
        }
        android.content.Intent intent = item.getIntent();
        if (intent != null && intent.getData() != null) {
            grantUriPermission(intent.getData(), i, i2, i3, str);
        }
    }

    void grantClipDataPermissions(android.content.ClipData clipData, int i, int i2, int i3, java.lang.String str) {
        int itemCount = clipData.getItemCount();
        for (int i4 = 0; i4 < itemCount; i4++) {
            grantClipDataItemPermission(clipData.getItemAt(i4), i, i2, i3, str);
        }
    }

    public boolean hideLocked() {
        if (!this.mBound) {
            return false;
        }
        if (this.mShown) {
            this.mShown = false;
            this.mShowArgs = null;
            this.mShowFlags = 0;
            this.mAssistDataRequester.cancel();
            this.mPendingShowCallbacks.clear();
            if (this.mSession != null) {
                try {
                    this.mSession.hide();
                } catch (android.os.RemoteException e) {
                }
            }
            this.mUgmInternal.revokeUriPermissionFromOwner(this.mPermissionOwner, null, 3, this.mUser);
            if (this.mSession != null) {
                try {
                    android.app.ActivityTaskManager.getService().finishVoiceTask(this.mSession);
                } catch (android.os.RemoteException e2) {
                }
            }
            if (this.mSetPowerBoostRunnable != null) {
                this.mSetPowerBoostRunnable.cancel();
                this.mSetPowerBoostRunnable = null;
            }
            this.mPowerManagerInternal.setPowerBoost(0, -1);
            if (this.mLowPowerStandbyControllerInternal != null) {
                removeFromLowPowerStandbyAllowlist();
            }
            this.mCallback.onSessionHidden(this);
        }
        if (this.mFullyBound) {
            this.mContext.unbindService(this.mFullConnection);
            this.mFullyBound = false;
            return true;
        }
        return true;
    }

    public void cancelLocked(boolean z) {
        this.mListeningVisibleActivity = false;
        this.mVisibleActivityInfoForToken.clear();
        hideLocked();
        this.mCanceled = true;
        if (this.mBound) {
            if (this.mSession != null) {
                try {
                    this.mSession.destroy();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Voice interation session already dead");
                }
            }
            if (z && this.mSession != null) {
                try {
                    android.app.ActivityTaskManager.getService().finishVoiceTask(this.mSession);
                } catch (android.os.RemoteException e2) {
                }
            }
            this.mContext.unbindService(this);
            try {
                this.mIWindowManager.removeWindowToken(this.mToken, 0);
            } catch (android.os.RemoteException e3) {
                android.util.Slog.w(TAG, "Failed removing window token", e3);
            }
            this.mBound = false;
            this.mService = null;
            this.mSession = null;
            this.mInteractor = null;
        }
        if (this.mFullyBound) {
            this.mContext.unbindService(this.mFullConnection);
            this.mFullyBound = false;
        }
    }

    public boolean deliverNewSessionLocked(android.service.voice.IVoiceInteractionSession iVoiceInteractionSession, com.android.internal.app.IVoiceInteractor iVoiceInteractor) {
        this.mSession = iVoiceInteractionSession;
        this.mInteractor = iVoiceInteractor;
        if (this.mShown) {
            try {
                iVoiceInteractionSession.show(this.mShowArgs, this.mShowFlags, this.mShowCallback);
                this.mShowArgs = null;
                this.mShowFlags = 0;
            } catch (android.os.RemoteException e) {
            }
            this.mAssistDataRequester.processPendingAssistData();
            if (!this.mPendingHandleAssistWithoutData.isEmpty()) {
                doHandleAssistWithoutData(this.mPendingHandleAssistWithoutData);
                this.mPendingHandleAssistWithoutData.clear();
                return true;
            }
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPendingShowCallbacksShownLocked() {
        for (int i = 0; i < this.mPendingShowCallbacks.size(); i++) {
            try {
                this.mPendingShowCallbacks.get(i).onShown();
            } catch (android.os.RemoteException e) {
            }
        }
        this.mPendingShowCallbacks.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyPendingShowCallbacksFailedLocked() {
        for (int i = 0; i < this.mPendingShowCallbacks.size(); i++) {
            try {
                this.mPendingShowCallbacks.get(i).onFailed();
            } catch (android.os.RemoteException e) {
            }
        }
        this.mPendingShowCallbacks.clear();
    }

    void startListeningVisibleActivityChangedLocked() {
        if (!this.mShown || this.mCanceled || this.mSession == null) {
            return;
        }
        this.mListeningVisibleActivity = true;
        this.mVisibleActivityInfoForToken.clear();
        android.util.ArrayMap<android.os.IBinder, android.service.voice.VisibleActivityInfo> topVisibleActivityInfosLocked = getTopVisibleActivityInfosLocked();
        if (topVisibleActivityInfosLocked == null || topVisibleActivityInfosLocked.isEmpty()) {
            return;
        }
        notifyVisibleActivitiesChangedLocked(topVisibleActivityInfosLocked, 1);
        this.mVisibleActivityInfoForToken.putAll((android.util.ArrayMap<? extends android.os.IBinder, ? extends android.service.voice.VisibleActivityInfo>) topVisibleActivityInfosLocked);
    }

    void stopListeningVisibleActivityChangedLocked() {
        this.mListeningVisibleActivity = false;
        this.mVisibleActivityInfoForToken.clear();
    }

    void notifyActivityEventChangedLocked(@android.annotation.NonNull final android.os.IBinder iBinder, final int i) {
        if (!this.mListeningVisibleActivity) {
            return;
        }
        this.mScheduledExecutorService.execute(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionSessionConnection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.lambda$notifyActivityEventChangedLocked$0(iBinder, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyActivityEventChangedLocked$0(android.os.IBinder iBinder, int i) {
        synchronized (this.mLock) {
            handleVisibleActivitiesLocked(iBinder, i);
        }
    }

    private android.util.ArrayMap<android.os.IBinder, android.service.voice.VisibleActivityInfo> getTopVisibleActivityInfosLocked() {
        java.util.List<com.android.server.wm.ActivityAssistInfo> topVisibleActivities = ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).getTopVisibleActivities();
        if (topVisibleActivities.isEmpty()) {
            android.util.Slog.w(TAG, "no visible activity");
            return null;
        }
        int size = topVisibleActivities.size();
        android.util.ArrayMap<android.os.IBinder, android.service.voice.VisibleActivityInfo> arrayMap = new android.util.ArrayMap<>(size);
        for (int i = 0; i < size; i++) {
            com.android.server.wm.ActivityAssistInfo activityAssistInfo = topVisibleActivities.get(i);
            arrayMap.put(activityAssistInfo.getActivityToken(), new android.service.voice.VisibleActivityInfo(activityAssistInfo.getTaskId(), activityAssistInfo.getAssistToken()));
        }
        return arrayMap;
    }

    private void handleVisibleActivitiesLocked(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        android.service.voice.VisibleActivityInfo visibleActivityInfoFromTopVisibleActivity;
        boolean z;
        if (!this.mListeningVisibleActivity || !this.mShown || this.mCanceled || this.mSession == null) {
            return;
        }
        if (i == 1 || i == 2) {
            if (this.mVisibleActivityInfoForToken.containsKey(iBinder) || (visibleActivityInfoFromTopVisibleActivity = getVisibleActivityInfoFromTopVisibleActivity(iBinder)) == null) {
                return;
            } else {
                z = true;
            }
        } else {
            z = false;
            if (i == 3) {
                if (getVisibleActivityInfoFromTopVisibleActivity(iBinder) != null || (visibleActivityInfoFromTopVisibleActivity = this.mVisibleActivityInfoForToken.get(iBinder)) == null) {
                    return;
                }
            } else if (i == 4) {
                visibleActivityInfoFromTopVisibleActivity = this.mVisibleActivityInfoForToken.get(iBinder);
                if (visibleActivityInfoFromTopVisibleActivity == null) {
                    return;
                }
            } else {
                android.util.Slog.w(TAG, "notifyActivityEventChangedLocked unexpected type=" + i);
                return;
            }
        }
        try {
            this.mSession.notifyVisibleActivityInfoChanged(visibleActivityInfoFromTopVisibleActivity, z ? 1 : 2);
        } catch (android.os.RemoteException e) {
        }
        if (z) {
            this.mVisibleActivityInfoForToken.put(iBinder, visibleActivityInfoFromTopVisibleActivity);
        } else {
            this.mVisibleActivityInfoForToken.remove(iBinder);
        }
    }

    private void notifyVisibleActivitiesChangedLocked(android.util.ArrayMap<android.os.IBinder, android.service.voice.VisibleActivityInfo> arrayMap, int i) {
        if (arrayMap == null || arrayMap.isEmpty() || this.mSession == null) {
            return;
        }
        for (int i2 = 0; i2 < arrayMap.size(); i2++) {
            try {
                this.mSession.notifyVisibleActivityInfoChanged(arrayMap.valueAt(i2), i);
            } catch (android.os.RemoteException e) {
                return;
            }
        }
    }

    private android.service.voice.VisibleActivityInfo getVisibleActivityInfoFromTopVisibleActivity(@android.annotation.NonNull android.os.IBinder iBinder) {
        android.util.ArrayMap<android.os.IBinder, android.service.voice.VisibleActivityInfo> topVisibleActivityInfosLocked = getTopVisibleActivityInfosLocked();
        if (topVisibleActivityInfosLocked == null) {
            return null;
        }
        return topVisibleActivityInfosLocked.get(iBinder);
    }

    void notifyActivityDestroyedLocked(@android.annotation.NonNull final android.os.IBinder iBinder) {
        if (!this.mListeningVisibleActivity) {
            return;
        }
        this.mScheduledExecutorService.execute(new java.lang.Runnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionSessionConnection$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.voiceinteraction.VoiceInteractionSessionConnection.this.lambda$notifyActivityDestroyedLocked$1(iBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyActivityDestroyedLocked$1(android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            try {
                if (this.mListeningVisibleActivity) {
                    if (!this.mShown || this.mCanceled || this.mSession == null) {
                        return;
                    }
                    android.service.voice.VisibleActivityInfo remove = this.mVisibleActivityInfoForToken.remove(iBinder);
                    if (remove != null) {
                        try {
                            this.mSession.notifyVisibleActivityInfoChanged(remove, 2);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeFromLowPowerStandbyAllowlist() {
        synchronized (this.mLock) {
            try {
                if (this.mLowPowerStandbyAllowlisted) {
                    this.mFgHandler.removeCallbacks(this.mRemoveFromLowPowerStandbyAllowlistRunnable);
                    this.mLowPowerStandbyControllerInternal.removeFromAllowlist(this.mCallingUid, 1);
                    this.mLowPowerStandbyAllowlisted = false;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
        synchronized (this.mLock) {
            this.mService = android.service.voice.IVoiceInteractionSessionService.Stub.asInterface(iBinder);
            if (!this.mCanceled) {
                try {
                    this.mService.newSession(this.mToken, this.mShowArgs, this.mShowFlags);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed adding window token", e);
                }
            }
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(android.content.ComponentName componentName) {
        this.mCallback.sessionConnectionGone(this);
        synchronized (this.mLock) {
            this.mService = null;
        }
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("mToken=");
        printWriter.println(this.mToken);
        printWriter.print(str);
        printWriter.print("mShown=");
        printWriter.println(this.mShown);
        printWriter.print(str);
        printWriter.print("mShowArgs=");
        printWriter.println(this.mShowArgs);
        printWriter.print(str);
        printWriter.print("mShowFlags=0x");
        printWriter.println(java.lang.Integer.toHexString(this.mShowFlags));
        printWriter.print(str);
        printWriter.print("mBound=");
        printWriter.println(this.mBound);
        if (this.mBound) {
            printWriter.print(str);
            printWriter.print("mService=");
            printWriter.println(this.mService);
            printWriter.print(str);
            printWriter.print("mSession=");
            printWriter.println(this.mSession);
            printWriter.print(str);
            printWriter.print("mInteractor=");
            printWriter.println(this.mInteractor);
        }
        this.mAssistDataRequester.dump(str, printWriter);
    }
}
