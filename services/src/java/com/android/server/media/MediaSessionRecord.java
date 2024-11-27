package com.android.server.media;

/* loaded from: classes2.dex */
public class MediaSessionRecord extends com.android.server.media.MediaSessionRecordImpl implements android.os.IBinder.DeathRecipient {
    private static final int OPTIMISTIC_VOLUME_TIMEOUT = 1000;
    static final long THROW_FOR_ACTIVITY_MEDIA_BUTTON_RECEIVER = 272737196;
    static final long THROW_FOR_INVALID_BROADCAST_RECEIVER = 270049379;
    private android.media.AudioAttributes mAudioAttrs;
    private android.media.AudioManager mAudioManager;
    private final android.content.Context mContext;
    private final com.android.server.media.MediaSessionRecord.ControllerStub mController;
    private android.os.Bundle mExtras;
    private long mFlags;
    private final android.app.ForegroundServiceDelegationOptions mForegroundServiceDelegationOptions;
    private final com.android.server.media.MediaSessionRecord.MessageHandler mHandler;
    private android.app.PendingIntent mLaunchIntent;
    private com.android.server.media.MediaButtonReceiverHolder mMediaButtonReceiverHolder;
    private android.media.MediaMetadata mMetadata;
    private java.lang.String mMetadataDescription;
    private final int mOwnerPid;
    private final int mOwnerUid;
    private final java.lang.String mPackageName;
    private android.media.session.PlaybackState mPlaybackState;
    private int mPolicies;
    private java.util.List<android.media.session.MediaSession.QueueItem> mQueue;
    private java.lang.CharSequence mQueueTitle;
    private int mRatingType;
    private final com.android.server.media.MediaSessionService mService;
    private final com.android.server.media.MediaSessionRecord.SessionStub mSession;
    private final com.android.server.media.MediaSessionRecord.SessionCb mSessionCb;
    private final android.os.Bundle mSessionInfo;
    private final android.media.session.MediaSession.Token mSessionToken;
    private final java.lang.String mTag;
    private final com.android.server.uri.UriGrantsManagerInternal mUgmInternal;
    private final int mUserId;
    private final boolean mVolumeAdjustmentForRemoteGroupSessions;
    private java.lang.String mVolumeControlId;
    private static final java.lang.String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    private static final java.lang.String TAG = "MediaSessionRecord";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.util.List<java.lang.Integer> ALWAYS_PRIORITY_STATES = java.util.Arrays.asList(4, 5, 9, 10);
    private static final java.util.List<java.lang.Integer> TRANSITION_PRIORITY_STATES = java.util.Arrays.asList(6, 8, 3);
    private static final android.media.AudioAttributes DEFAULT_ATTRIBUTES = new android.media.AudioAttributes.Builder().setUsage(1).build();
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.concurrent.CopyOnWriteArrayList<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> mControllerCallbackHolders = new java.util.concurrent.CopyOnWriteArrayList<>();
    private int mVolumeType = 1;
    private int mVolumeControlType = 2;
    private int mMaxVolume = 0;
    private int mCurrentVolume = 0;
    private int mOptimisticVolume = -1;
    private boolean mIsActive = false;
    private boolean mDestroyed = false;
    private long mDuration = -1;
    private final java.lang.Runnable mClearOptimisticVolumeRunnable = new java.lang.Runnable() { // from class: com.android.server.media.MediaSessionRecord.3
        @Override // java.lang.Runnable
        public void run() {
            boolean z = com.android.server.media.MediaSessionRecord.this.mOptimisticVolume != com.android.server.media.MediaSessionRecord.this.mCurrentVolume;
            com.android.server.media.MediaSessionRecord.this.mOptimisticVolume = -1;
            if (z) {
                com.android.server.media.MediaSessionRecord.this.pushVolumeUpdate();
            }
        }
    };

    private static int getVolumeStream(@android.annotation.Nullable android.media.AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return DEFAULT_ATTRIBUTES.getVolumeControlStream();
        }
        int volumeControlStream = audioAttributes.getVolumeControlStream();
        if (volumeControlStream == Integer.MIN_VALUE) {
            return DEFAULT_ATTRIBUTES.getVolumeControlStream();
        }
        return volumeControlStream;
    }

    public MediaSessionRecord(int i, int i2, int i3, java.lang.String str, android.media.session.ISessionCallback iSessionCallback, java.lang.String str2, android.os.Bundle bundle, com.android.server.media.MediaSessionService mediaSessionService, android.os.Looper looper, int i4) throws android.os.RemoteException {
        this.mUniqueId = com.android.server.media.MediaSessionRecordImpl.sNextMediaSessionRecordId.getAndIncrement();
        this.mOwnerPid = i;
        this.mOwnerUid = i2;
        this.mUserId = i3;
        this.mPackageName = str;
        this.mTag = str2;
        this.mSessionInfo = bundle;
        this.mController = new com.android.server.media.MediaSessionRecord.ControllerStub();
        this.mSessionToken = new android.media.session.MediaSession.Token(i2, this.mController);
        this.mSession = new com.android.server.media.MediaSessionRecord.SessionStub();
        this.mSessionCb = new com.android.server.media.MediaSessionRecord.SessionCb(iSessionCallback);
        this.mService = mediaSessionService;
        this.mContext = this.mService.getContext();
        this.mHandler = new com.android.server.media.MediaSessionRecord.MessageHandler(looper);
        this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService("audio");
        this.mAudioAttrs = DEFAULT_ATTRIBUTES;
        this.mPolicies = i4;
        this.mUgmInternal = (com.android.server.uri.UriGrantsManagerInternal) com.android.server.LocalServices.getService(com.android.server.uri.UriGrantsManagerInternal.class);
        this.mVolumeAdjustmentForRemoteGroupSessions = this.mContext.getResources().getBoolean(android.R.bool.config_viewBasedRotaryEncoderHapticsEnabled);
        this.mForegroundServiceDelegationOptions = createForegroundServiceDelegationOptions();
        this.mSessionCb.mCb.asBinder().linkToDeath(this, 0);
    }

    private android.app.ForegroundServiceDelegationOptions createForegroundServiceDelegationOptions() {
        return new android.app.ForegroundServiceDelegationOptions.Builder().setClientPid(this.mOwnerPid).setClientUid(getUid()).setClientPackageName(getPackageName()).setClientAppThread((android.app.IApplicationThread) null).setSticky(false).setClientInstanceName("MediaSessionFgsDelegate_" + getUid() + "_" + this.mOwnerPid + "_" + getPackageName()).setForegroundServiceTypes(0).setDelegationService(2).build();
    }

    public android.media.session.ISession getSessionBinder() {
        return this.mSession;
    }

    public android.media.session.MediaSession.Token getSessionToken() {
        return this.mSessionToken;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public com.android.server.media.MediaButtonReceiverHolder getMediaButtonReceiver() {
        return this.mMediaButtonReceiverHolder;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getUid() {
        return this.mOwnerUid;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getUserId() {
        return this.mUserId;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isSystemPriority() {
        return (this.mFlags & 65536) != 0;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void adjustVolume(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, int i3, int i4, boolean z2) {
        int i5 = i4 & 4;
        int i6 = (checkPlaybackActiveState(true) || isSystemPriority()) ? i4 & (-5) : i4;
        if (this.mVolumeType == 1) {
            postAdjustLocalVolume(getVolumeStream(this.mAudioAttrs), i3, i6, str2, i, i2, z, z2, i5);
            return;
        }
        if (this.mVolumeControlType == 0) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Session does not support volume adjustment");
            }
        } else if (i3 == 101 || i3 == -100 || i3 == 100) {
            android.util.Slog.w(TAG, "Muting remote playback is not supported");
        } else {
            if (DEBUG) {
                android.util.Slog.w(TAG, "adjusting volume, pkg=" + str + ", asSystemService=" + z + ", dir=" + i3);
            }
            this.mSessionCb.adjustVolume(str, i, i2, z, i3);
            int i7 = this.mOptimisticVolume < 0 ? this.mCurrentVolume : this.mOptimisticVolume;
            this.mOptimisticVolume = i7 + i3;
            this.mOptimisticVolume = java.lang.Math.max(0, java.lang.Math.min(this.mOptimisticVolume, this.mMaxVolume));
            this.mHandler.removeCallbacks(this.mClearOptimisticVolumeRunnable);
            this.mHandler.postDelayed(this.mClearOptimisticVolumeRunnable, 1000L);
            if (i7 != this.mOptimisticVolume) {
                pushVolumeUpdate();
            }
            if (DEBUG) {
                android.util.Slog.d(TAG, "Adjusted optimistic volume to " + this.mOptimisticVolume + " max is " + this.mMaxVolume);
            }
        }
        this.mService.notifyRemoteVolumeChanged(i6, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVolumeTo(java.lang.String str, final java.lang.String str2, final int i, final int i2, final int i3, final int i4) {
        if (this.mVolumeType == 1) {
            final int volumeStream = getVolumeStream(this.mAudioAttrs);
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaSessionRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        com.android.server.media.MediaSessionRecord.this.mAudioManager.setStreamVolumeForUid(volumeStream, i3, i4, str2, i2, i, com.android.server.media.MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                    } catch (java.lang.IllegalArgumentException | java.lang.SecurityException e) {
                        android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Cannot set volume: stream=" + volumeStream + ", value=" + i3 + ", flags=" + i4, e);
                    }
                }
            });
            return;
        }
        if (this.mVolumeControlType != 2) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Session does not support setting volume");
            }
        } else {
            int max = java.lang.Math.max(0, java.lang.Math.min(i3, this.mMaxVolume));
            this.mSessionCb.setVolumeTo(str, i, i2, max);
            int i5 = this.mOptimisticVolume < 0 ? this.mCurrentVolume : this.mOptimisticVolume;
            this.mOptimisticVolume = java.lang.Math.max(0, java.lang.Math.min(max, this.mMaxVolume));
            this.mHandler.removeCallbacks(this.mClearOptimisticVolumeRunnable);
            this.mHandler.postDelayed(this.mClearOptimisticVolumeRunnable, 1000L);
            if (i5 != this.mOptimisticVolume) {
                pushVolumeUpdate();
            }
            if (DEBUG) {
                android.util.Slog.d(TAG, "Set optimistic volume to " + this.mOptimisticVolume + " max is " + this.mMaxVolume);
            }
        }
        this.mService.notifyRemoteVolumeChanged(i4, this);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isActive() {
        return this.mIsActive && !this.mDestroyed;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean checkPlaybackActiveState(boolean z) {
        return this.mPlaybackState != null && this.mPlaybackState.isActive() == z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isPlaybackTypeLocal() {
        return this.mVolumeType == 1;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        this.mService.onSessionDied(this);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void close() {
        ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).logFgsApiEnd(4, android.os.Binder.getCallingUid(), android.os.Binder.getCallingPid());
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                this.mSessionCb.mCb.asBinder().unlinkToDeath(this, 0);
                this.mDestroyed = true;
                this.mPlaybackState = null;
                this.mHandler.post(9);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mDestroyed;
        }
        return z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean sendMediaButton(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, int i3, android.os.ResultReceiver resultReceiver) {
        return this.mSessionCb.sendMediaButton(str, i, i2, z, keyEvent, i3, resultReceiver);
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean canHandleVolumeKey() {
        if (isPlaybackTypeLocal()) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Local MediaSessionRecord can handle volume key");
            }
            return true;
        }
        if (this.mVolumeControlType == 0) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Local MediaSessionRecord with FIXED volume control can't handle volume key");
            }
            return false;
        }
        if (this.mVolumeAdjustmentForRemoteGroupSessions) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Volume adjustment for remote group sessions allowed so MediaSessionRecord can handle volume key");
            }
            return true;
        }
        java.util.List<android.media.RoutingSessionInfo> routingSessions = android.media.MediaRouter2Manager.getInstance(this.mContext).getRoutingSessions(this.mPackageName);
        if (DEBUG) {
            android.util.Slog.d(TAG, "Found " + routingSessions.size() + " routing sessions for package name " + this.mPackageName);
        }
        boolean z = true;
        boolean z2 = false;
        for (android.media.RoutingSessionInfo routingSessionInfo : routingSessions) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Found routingSessionInfo: " + routingSessionInfo);
            }
            if (!routingSessionInfo.isSystemSession()) {
                if (routingSessionInfo.getVolumeHandling() != 0) {
                    z2 = true;
                } else {
                    z2 = true;
                    z = false;
                }
            }
        }
        if (!z2 && DEBUG) {
            android.util.Slog.d(TAG, "Package " + this.mPackageName + " has a remote media session but no associated routing session");
        }
        return z2 && z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getSessionPolicies() {
        int i;
        synchronized (this.mLock) {
            i = this.mPolicies;
        }
        return i;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void setSessionPolicies(int i) {
        synchronized (this.mLock) {
            this.mPolicies = i;
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + this.mTag + " " + this);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("  ");
        java.lang.String sb2 = sb.toString();
        printWriter.println(sb2 + "ownerPid=" + this.mOwnerPid + ", ownerUid=" + this.mOwnerUid + ", userId=" + this.mUserId);
        java.lang.StringBuilder sb3 = new java.lang.StringBuilder();
        sb3.append(sb2);
        sb3.append("package=");
        sb3.append(this.mPackageName);
        printWriter.println(sb3.toString());
        printWriter.println(sb2 + "launchIntent=" + this.mLaunchIntent);
        printWriter.println(sb2 + "mediaButtonReceiver=" + this.mMediaButtonReceiverHolder);
        printWriter.println(sb2 + "active=" + this.mIsActive);
        printWriter.println(sb2 + "flags=" + this.mFlags);
        printWriter.println(sb2 + "rating type=" + this.mRatingType);
        printWriter.println(sb2 + "controllers: " + this.mControllerCallbackHolders.size());
        java.lang.StringBuilder sb4 = new java.lang.StringBuilder();
        sb4.append(sb2);
        sb4.append("state=");
        sb4.append(this.mPlaybackState == null ? null : this.mPlaybackState.toString());
        printWriter.println(sb4.toString());
        printWriter.println(sb2 + "audioAttrs=" + this.mAudioAttrs);
        printWriter.append((java.lang.CharSequence) sb2).append("volumeType=").append(toVolumeTypeString(this.mVolumeType)).append(", controlType=").append(toVolumeControlTypeString(this.mVolumeControlType)).append(", max=").append(java.lang.Integer.toString(this.mMaxVolume)).append(", current=").append(java.lang.Integer.toString(this.mCurrentVolume)).append(", volumeControlId=").append(this.mVolumeControlId).println();
        printWriter.println(sb2 + "metadata: " + this.mMetadataDescription);
        java.lang.StringBuilder sb5 = new java.lang.StringBuilder();
        sb5.append(sb2);
        sb5.append("queueTitle=");
        sb5.append((java.lang.Object) this.mQueueTitle);
        sb5.append(", size=");
        sb5.append(this.mQueue == null ? 0 : this.mQueue.size());
        printWriter.println(sb5.toString());
    }

    private static java.lang.String toVolumeControlTypeString(int i) {
        switch (i) {
            case 0:
                return "FIXED";
            case 1:
                return "RELATIVE";
            case 2:
                return "ABSOLUTE";
            default:
                return android.text.TextUtils.formatSimple("unknown(%d)", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
        }
    }

    private static java.lang.String toVolumeTypeString(int i) {
        switch (i) {
            case 1:
                return "LOCAL";
            case 2:
                return "REMOTE";
            default:
                return android.text.TextUtils.formatSimple("unknown(%d)", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
        }
    }

    public java.lang.String toString() {
        return this.mPackageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.mTag + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + getUniqueId() + " (userId=" + this.mUserId + ")";
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public android.app.ForegroundServiceDelegationOptions getForegroundServiceDelegationOptions() {
        return this.mForegroundServiceDelegationOptions;
    }

    private void postAdjustLocalVolume(final int i, final int i2, final int i3, java.lang.String str, int i4, int i5, boolean z, final boolean z2, final int i6) {
        final java.lang.String str2;
        final int i7;
        final int i8;
        if (DEBUG) {
            android.util.Slog.w(TAG, "adjusting local volume, stream=" + i + ", dir=" + i2 + ", asSystemService=" + z + ", useSuggested=" + z2);
        }
        if (z) {
            java.lang.String opPackageName = this.mContext.getOpPackageName();
            i7 = android.os.Process.myPid();
            i8 = 1000;
            str2 = opPackageName;
        } else {
            str2 = str;
            i7 = i4;
            i8 = i5;
        }
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.media.MediaSessionRecord.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (z2) {
                        if (android.media.AudioSystem.isStreamActive(i, 0)) {
                            com.android.server.media.MediaSessionRecord.this.mAudioManager.adjustSuggestedStreamVolumeForUid(i, i2, i3, str2, i8, i7, com.android.server.media.MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                        } else {
                            com.android.server.media.MediaSessionRecord.this.mAudioManager.adjustSuggestedStreamVolumeForUid(Integer.MIN_VALUE, i2, i3 | i6, str2, i8, i7, com.android.server.media.MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                        }
                    } else {
                        com.android.server.media.MediaSessionRecord.this.mAudioManager.adjustStreamVolumeForUid(i, i2, i3, str2, i8, i7, com.android.server.media.MediaSessionRecord.this.mContext.getApplicationInfo().targetSdkVersion);
                    }
                } catch (java.lang.IllegalArgumentException | java.lang.SecurityException e) {
                    android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Cannot adjust volume: direction=" + i2 + ", stream=" + i + ", flags=" + i3 + ", opPackageName=" + str2 + ", uid=" + i8 + ", useSuggested=" + z2 + ", previousFlagPlaySound=" + i6, e);
                }
            }
        });
    }

    private void logCallbackException(java.lang.String str, com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder iSessionControllerCallbackHolder, java.lang.Exception exc) {
        android.util.Slog.v(TAG, str + ", this=" + this + ", callback package=" + iSessionControllerCallbackHolder.mPackageName + ", exception=" + exc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushPlaybackStateUpdate() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                android.media.session.PlaybackState playbackState = this.mPlaybackState;
                java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                java.util.ArrayList arrayList = null;
                while (it.hasNext()) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                    try {
                        next.mCallback.onPlaybackStateChanged(playbackState);
                    } catch (android.os.DeadObjectException e) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(next);
                        logCallbackException("Removing dead callback in pushPlaybackStateUpdate", next, e);
                    } catch (android.os.RemoteException e2) {
                        logCallbackException("unexpected exception in pushPlaybackStateUpdate", next, e2);
                    }
                }
                if (arrayList != null) {
                    this.mControllerCallbackHolders.removeAll(arrayList);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushMetadataUpdate() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                android.media.MediaMetadata mediaMetadata = this.mMetadata;
                java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                java.util.ArrayList arrayList = null;
                while (it.hasNext()) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                    try {
                        next.mCallback.onMetadataChanged(mediaMetadata);
                    } catch (android.os.DeadObjectException e) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(next);
                        logCallbackException("Removing dead callback in pushMetadataUpdate", next, e);
                    } catch (android.os.RemoteException e2) {
                        logCallbackException("unexpected exception in pushMetadataUpdate", next, e2);
                    }
                }
                if (arrayList != null) {
                    this.mControllerCallbackHolders.removeAll(arrayList);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushQueueUpdate() {
        android.content.pm.ParceledListSlice parceledListSlice;
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                java.util.ArrayList arrayList = this.mQueue == null ? null : new java.util.ArrayList(this.mQueue);
                java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                java.util.ArrayList arrayList2 = null;
                while (it.hasNext()) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                    if (arrayList == null) {
                        parceledListSlice = null;
                    } else {
                        parceledListSlice = new android.content.pm.ParceledListSlice(arrayList);
                        parceledListSlice.setInlineCountLimit(1);
                    }
                    try {
                        next.mCallback.onQueueChanged(parceledListSlice);
                    } catch (android.os.DeadObjectException e) {
                        if (arrayList2 == null) {
                            arrayList2 = new java.util.ArrayList();
                        }
                        arrayList2.add(next);
                        logCallbackException("Removing dead callback in pushQueueUpdate", next, e);
                    } catch (android.os.RemoteException e2) {
                        logCallbackException("unexpected exception in pushQueueUpdate", next, e2);
                    }
                }
                if (arrayList2 != null) {
                    this.mControllerCallbackHolders.removeAll(arrayList2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushQueueTitleUpdate() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                java.lang.CharSequence charSequence = this.mQueueTitle;
                java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                java.util.ArrayList arrayList = null;
                while (it.hasNext()) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                    try {
                        next.mCallback.onQueueTitleChanged(charSequence);
                    } catch (android.os.DeadObjectException e) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(next);
                        logCallbackException("Removing dead callback in pushQueueTitleUpdate", next, e);
                    } catch (android.os.RemoteException e2) {
                        logCallbackException("unexpected exception in pushQueueTitleUpdate", next, e2);
                    }
                }
                if (arrayList != null) {
                    this.mControllerCallbackHolders.removeAll(arrayList);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushExtrasUpdate() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                android.os.Bundle bundle = this.mExtras;
                java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                java.util.ArrayList arrayList = null;
                while (it.hasNext()) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                    try {
                        next.mCallback.onExtrasChanged(bundle);
                    } catch (android.os.DeadObjectException e) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(next);
                        logCallbackException("Removing dead callback in pushExtrasUpdate", next, e);
                    } catch (android.os.RemoteException e2) {
                        logCallbackException("unexpected exception in pushExtrasUpdate", next, e2);
                    }
                }
                if (arrayList != null) {
                    this.mControllerCallbackHolders.removeAll(arrayList);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushVolumeUpdate() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                android.media.session.MediaController.PlaybackInfo volumeAttributes = getVolumeAttributes();
                java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                java.util.ArrayList arrayList = null;
                while (it.hasNext()) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                    try {
                        next.mCallback.onVolumeInfoChanged(volumeAttributes);
                    } catch (android.os.DeadObjectException e) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(next);
                        logCallbackException("Removing dead callback in pushVolumeUpdate", next, e);
                    } catch (android.os.RemoteException e2) {
                        logCallbackException("unexpected exception in pushVolumeUpdate", next, e2);
                    }
                }
                if (arrayList != null) {
                    this.mControllerCallbackHolders.removeAll(arrayList);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushEvent(java.lang.String str, android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    return;
                }
                java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                java.util.ArrayList arrayList = null;
                while (it.hasNext()) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                    try {
                        next.mCallback.onEvent(str, bundle);
                    } catch (android.os.DeadObjectException e) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(next);
                        logCallbackException("Removing dead callback in pushEvent", next, e);
                    } catch (android.os.RemoteException e2) {
                        logCallbackException("unexpected exception in pushEvent", next, e2);
                    }
                }
                if (arrayList != null) {
                    this.mControllerCallbackHolders.removeAll(arrayList);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pushSessionDestroyed() {
        synchronized (this.mLock) {
            try {
                if (this.mDestroyed) {
                    java.util.Iterator<com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder> it = this.mControllerCallbackHolders.iterator();
                    while (it.hasNext()) {
                        com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder next = it.next();
                        try {
                            next.mCallback.asBinder().unlinkToDeath(next.mDeathMonitor, 0);
                            next.mCallback.onSessionDestroyed();
                        } catch (android.os.DeadObjectException e) {
                            logCallbackException("Removing dead callback in pushSessionDestroyed", next, e);
                        } catch (android.os.RemoteException e2) {
                            logCallbackException("unexpected exception in pushSessionDestroyed", next, e2);
                        } catch (java.util.NoSuchElementException e3) {
                            logCallbackException("error unlinking to binder death", next, e3);
                        }
                    }
                    this.mControllerCallbackHolders.clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.media.session.PlaybackState getStateWithUpdatedPosition() {
        long j;
        synchronized (this.mLock) {
            try {
                android.media.session.PlaybackState playbackState = null;
                if (this.mDestroyed) {
                    return null;
                }
                android.media.session.PlaybackState playbackState2 = this.mPlaybackState;
                long j2 = this.mDuration;
                if (playbackState2 != null && (playbackState2.getState() == 3 || playbackState2.getState() == 4 || playbackState2.getState() == 5)) {
                    long lastPositionUpdateTime = playbackState2.getLastPositionUpdateTime();
                    long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                    if (lastPositionUpdateTime > 0) {
                        long playbackSpeed = ((long) (playbackState2.getPlaybackSpeed() * (elapsedRealtime - lastPositionUpdateTime))) + playbackState2.getPosition();
                        if (j2 >= 0 && playbackSpeed > j2) {
                            j = j2;
                        } else if (playbackSpeed >= 0) {
                            j = playbackSpeed;
                        } else {
                            j = 0;
                        }
                        android.media.session.PlaybackState.Builder builder = new android.media.session.PlaybackState.Builder(playbackState2);
                        builder.setState(playbackState2.getState(), j, playbackState2.getPlaybackSpeed(), elapsedRealtime);
                        playbackState = builder.build();
                    }
                }
                return playbackState == null ? playbackState2 : playbackState;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getControllerHolderIndexForCb(android.media.session.ISessionControllerCallback iSessionControllerCallback) {
        android.os.IBinder asBinder = iSessionControllerCallback.asBinder();
        for (int size = this.mControllerCallbackHolders.size() - 1; size >= 0; size--) {
            if (asBinder.equals(this.mControllerCallbackHolders.get(size).mCallback.asBinder())) {
                return size;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.media.session.MediaController.PlaybackInfo getVolumeAttributes() {
        synchronized (this.mLock) {
            try {
                if (this.mVolumeType == 2) {
                    return new android.media.session.MediaController.PlaybackInfo(this.mVolumeType, this.mVolumeControlType, this.mMaxVolume, this.mOptimisticVolume != -1 ? this.mOptimisticVolume : this.mCurrentVolume, this.mAudioAttrs, this.mVolumeControlId);
                }
                int i = this.mVolumeType;
                android.media.AudioAttributes audioAttributes = this.mAudioAttrs;
                int volumeStream = getVolumeStream(audioAttributes);
                return new android.media.session.MediaController.PlaybackInfo(i, 2, this.mAudioManager.getStreamMaxVolume(volumeStream), this.mAudioManager.getStreamVolume(volumeStream), audioAttributes, null);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
    public static boolean componentNameExists(@android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.content.Context context, int i) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MEDIA_BUTTON");
        intent.addFlags(268435456);
        intent.setComponent(componentName);
        return !context.getPackageManager().queryBroadcastReceiversAsUser(intent, android.content.pm.PackageManager.ResolveInfoFlags.of(0L), android.os.UserHandle.of(i)).isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SessionStub extends android.media.session.ISession.Stub {
        private SessionStub() {
        }

        public void destroySession() throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaSessionRecord.this.mService.onSessionDied(com.android.server.media.MediaSessionRecord.this);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void sendEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
            com.android.server.media.MediaSessionRecord.this.mHandler.post(6, str, bundle == null ? null : new android.os.Bundle(bundle));
        }

        public android.media.session.ISessionController getController() throws android.os.RemoteException {
            return com.android.server.media.MediaSessionRecord.this.mController;
        }

        public void setActive(boolean z) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            int callingPid = android.os.Binder.getCallingPid();
            if (z) {
                ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).logFgsApiBegin(4, callingUid, callingPid);
            } else {
                ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).logFgsApiEnd(4, callingUid, callingPid);
            }
            com.android.server.media.MediaSessionRecord.this.mIsActive = z;
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaSessionRecord.this.mService.onSessionActiveStateChanged(com.android.server.media.MediaSessionRecord.this, com.android.server.media.MediaSessionRecord.this.mPlaybackState);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                com.android.server.media.MediaSessionRecord.this.mHandler.post(7);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void setFlags(int i) throws android.os.RemoteException {
            int i2 = 65536 & i;
            if (i2 != 0) {
                com.android.server.media.MediaSessionRecord.this.mService.enforcePhoneStatePermission(android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
            }
            com.android.server.media.MediaSessionRecord.this.mFlags = i;
            if (i2 != 0) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.media.MediaSessionRecord.this.mService.setGlobalPrioritySession(com.android.server.media.MediaSessionRecord.this);
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            com.android.server.media.MediaSessionRecord.this.mHandler.post(7);
        }

        public void setMediaButtonReceiver(@android.annotation.Nullable android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if ((com.android.server.media.MediaSessionRecord.this.mPolicies & 1) != 0) {
                    return;
                }
                if (pendingIntent == null || !pendingIntent.isActivity()) {
                    com.android.server.media.MediaSessionRecord.this.mMediaButtonReceiverHolder = com.android.server.media.MediaButtonReceiverHolder.create(com.android.server.media.MediaSessionRecord.this.mUserId, pendingIntent, com.android.server.media.MediaSessionRecord.this.mPackageName);
                    com.android.server.media.MediaSessionRecord.this.mService.onMediaButtonReceiverChanged(com.android.server.media.MediaSessionRecord.this);
                } else {
                    if (android.app.compat.CompatChanges.isChangeEnabled(com.android.server.media.MediaSessionRecord.THROW_FOR_ACTIVITY_MEDIA_BUTTON_RECEIVER, callingUid)) {
                        throw new java.lang.IllegalArgumentException("The media button receiver cannot be set to an activity.");
                    }
                    android.util.Slog.w(com.android.server.media.MediaSessionRecord.TAG, "Ignoring invalid media button receiver targeting an activity.");
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @android.annotation.RequiresPermission("android.permission.INTERACT_ACROSS_USERS")
        public void setMediaButtonBroadcastReceiver(android.content.ComponentName componentName) throws android.os.RemoteException {
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            if (componentName != null) {
                try {
                    if (!android.text.TextUtils.equals(com.android.server.media.MediaSessionRecord.this.mPackageName, componentName.getPackageName())) {
                        android.util.EventLog.writeEvent(1397638484, "238177121", -1, "");
                        throw new java.lang.IllegalArgumentException("receiver does not belong to package name provided to MediaSessionRecord. Pkg = " + com.android.server.media.MediaSessionRecord.this.mPackageName + ", Receiver Pkg = " + componentName.getPackageName());
                    }
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            if ((com.android.server.media.MediaSessionRecord.this.mPolicies & 1) != 0) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            if (com.android.server.media.MediaSessionRecord.componentNameExists(componentName, com.android.server.media.MediaSessionRecord.this.mContext, com.android.server.media.MediaSessionRecord.this.mUserId)) {
                com.android.server.media.MediaSessionRecord.this.mMediaButtonReceiverHolder = com.android.server.media.MediaButtonReceiverHolder.create(com.android.server.media.MediaSessionRecord.this.mUserId, componentName);
                com.android.server.media.MediaSessionRecord.this.mService.onMediaButtonReceiverChanged(com.android.server.media.MediaSessionRecord.this);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return;
            }
            if (android.app.compat.CompatChanges.isChangeEnabled(com.android.server.media.MediaSessionRecord.THROW_FOR_INVALID_BROADCAST_RECEIVER, callingUid)) {
                throw new java.lang.IllegalArgumentException("Invalid component name: " + componentName);
            }
            android.util.Slog.w(com.android.server.media.MediaSessionRecord.TAG, "setMediaButtonBroadcastReceiver(): Ignoring invalid component name=" + componentName);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }

        public void setLaunchPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
            com.android.server.media.MediaSessionRecord.this.mLaunchIntent = pendingIntent;
        }

        public void setMetadata(android.media.MediaMetadata mediaMetadata, long j, java.lang.String str) throws android.os.RemoteException {
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                com.android.server.media.MediaSessionRecord.this.mDuration = j;
                com.android.server.media.MediaSessionRecord.this.mMetadataDescription = str;
                com.android.server.media.MediaSessionRecord.this.mMetadata = sanitizeMediaMetadata(mediaMetadata);
            }
            com.android.server.media.MediaSessionRecord.this.mHandler.post(1);
        }

        private android.media.MediaMetadata sanitizeMediaMetadata(android.media.MediaMetadata mediaMetadata) {
            if (mediaMetadata == null) {
                return null;
            }
            android.media.MediaMetadata.Builder builder = new android.media.MediaMetadata.Builder(mediaMetadata);
            for (java.lang.String str : com.android.server.media.MediaSessionRecord.ART_URIS) {
                java.lang.String string = mediaMetadata.getString(str);
                if (!android.text.TextUtils.isEmpty(string)) {
                    android.net.Uri parse = android.net.Uri.parse(string);
                    if (com.android.server.wm.ActivityTaskManagerInternal.ASSIST_KEY_CONTENT.equals(parse.getScheme())) {
                        try {
                            com.android.server.media.MediaSessionRecord.this.mUgmInternal.checkGrantUriPermission(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), android.content.ContentProvider.getUriWithoutUserId(parse), 1, android.content.ContentProvider.getUserIdFromUri(parse, com.android.server.media.MediaSessionRecord.this.getUserId()));
                        } catch (java.lang.SecurityException e) {
                            builder.putString(str, null);
                        }
                    }
                }
            }
            android.media.MediaMetadata build = builder.build();
            build.size();
            return build;
        }

        public void setPlaybackState(android.media.session.PlaybackState playbackState) throws android.os.RemoteException {
            boolean z = false;
            int state = com.android.server.media.MediaSessionRecord.this.mPlaybackState == null ? 0 : com.android.server.media.MediaSessionRecord.this.mPlaybackState.getState();
            int state2 = playbackState == null ? 0 : playbackState.getState();
            if (com.android.server.media.MediaSessionRecord.ALWAYS_PRIORITY_STATES.contains(java.lang.Integer.valueOf(state2)) || (!com.android.server.media.MediaSessionRecord.TRANSITION_PRIORITY_STATES.contains(java.lang.Integer.valueOf(state)) && com.android.server.media.MediaSessionRecord.TRANSITION_PRIORITY_STATES.contains(java.lang.Integer.valueOf(state2)))) {
                z = true;
            }
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                com.android.server.media.MediaSessionRecord.this.mPlaybackState = playbackState;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaSessionRecord.this.mService.onSessionPlaybackStateChanged(com.android.server.media.MediaSessionRecord.this, z, com.android.server.media.MediaSessionRecord.this.mPlaybackState);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                com.android.server.media.MediaSessionRecord.this.mHandler.post(2);
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public void resetQueue() throws android.os.RemoteException {
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                com.android.server.media.MediaSessionRecord.this.mQueue = null;
            }
            com.android.server.media.MediaSessionRecord.this.mHandler.post(3);
        }

        public android.os.IBinder getBinderForSetQueue() throws android.os.RemoteException {
            return new android.media.session.ParcelableListBinder(android.media.session.MediaSession.QueueItem.class, new java.util.function.Consumer() { // from class: com.android.server.media.MediaSessionRecord$SessionStub$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.media.MediaSessionRecord.SessionStub.this.lambda$getBinderForSetQueue$0((java.util.List) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$getBinderForSetQueue$0(java.util.List list) {
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                com.android.server.media.MediaSessionRecord.this.mQueue = list;
            }
            com.android.server.media.MediaSessionRecord.this.mHandler.post(3);
        }

        public void setQueueTitle(java.lang.CharSequence charSequence) throws android.os.RemoteException {
            com.android.server.media.MediaSessionRecord.this.mQueueTitle = charSequence;
            com.android.server.media.MediaSessionRecord.this.mHandler.post(4);
        }

        public void setExtras(android.os.Bundle bundle) throws android.os.RemoteException {
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                com.android.server.media.MediaSessionRecord.this.mExtras = bundle == null ? null : new android.os.Bundle(bundle);
            }
            com.android.server.media.MediaSessionRecord.this.mHandler.post(5);
        }

        public void setRatingType(int i) throws android.os.RemoteException {
            com.android.server.media.MediaSessionRecord.this.mRatingType = i;
        }

        public void setCurrentVolume(int i) throws android.os.RemoteException {
            com.android.server.media.MediaSessionRecord.this.mCurrentVolume = i;
            com.android.server.media.MediaSessionRecord.this.mHandler.post(8);
        }

        public void setPlaybackToLocal(android.media.AudioAttributes audioAttributes) throws android.os.RemoteException {
            boolean z;
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                try {
                    z = com.android.server.media.MediaSessionRecord.this.mVolumeType == 2;
                    com.android.server.media.MediaSessionRecord.this.mVolumeType = 1;
                    com.android.server.media.MediaSessionRecord.this.mVolumeControlId = null;
                    if (audioAttributes != null) {
                        com.android.server.media.MediaSessionRecord.this.mAudioAttrs = audioAttributes;
                    } else {
                        android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Received null audio attributes, using existing attributes");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.media.MediaSessionRecord.this.mService.onSessionPlaybackTypeChanged(com.android.server.media.MediaSessionRecord.this);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    com.android.server.media.MediaSessionRecord.this.mHandler.post(8);
                } catch (java.lang.Throwable th2) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th2;
                }
            }
        }

        public void setPlaybackToRemote(int i, int i2, java.lang.String str) throws android.os.RemoteException {
            boolean z;
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                z = true;
                if (com.android.server.media.MediaSessionRecord.this.mVolumeType != 1) {
                    z = false;
                }
                com.android.server.media.MediaSessionRecord.this.mVolumeType = 2;
                com.android.server.media.MediaSessionRecord.this.mVolumeControlType = i;
                com.android.server.media.MediaSessionRecord.this.mMaxVolume = i2;
                com.android.server.media.MediaSessionRecord.this.mVolumeControlId = str;
            }
            if (z) {
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    com.android.server.media.MediaSessionRecord.this.mService.onSessionPlaybackTypeChanged(com.android.server.media.MediaSessionRecord.this);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    com.android.server.media.MediaSessionRecord.this.mHandler.post(8);
                } catch (java.lang.Throwable th) {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }
    }

    class SessionCb {
        private final android.media.session.ISessionCallback mCb;

        SessionCb(android.media.session.ISessionCallback iSessionCallback) {
            this.mCb = iSessionCallback;
        }

        public boolean sendMediaButton(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, int i3, android.os.ResultReceiver resultReceiver) {
            try {
                if (android.view.KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
                    com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "action=" + android.view.KeyEvent.actionToString(keyEvent.getAction()) + ";code=" + android.view.KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
                }
                if (z) {
                    this.mCb.onMediaButton(com.android.server.media.MediaSessionRecord.this.mContext.getPackageName(), android.os.Process.myPid(), 1000, createMediaButtonIntent(keyEvent), i3, resultReceiver);
                    return true;
                }
                this.mCb.onMediaButton(str, i, i2, createMediaButtonIntent(keyEvent), i3, resultReceiver);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in sendMediaRequest.", e);
                return false;
            }
        }

        public boolean sendMediaButton(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent) {
            try {
                if (android.view.KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
                    com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "action=" + android.view.KeyEvent.actionToString(keyEvent.getAction()) + ";code=" + android.view.KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
                }
                if (z) {
                    this.mCb.onMediaButton(com.android.server.media.MediaSessionRecord.this.mContext.getPackageName(), android.os.Process.myPid(), 1000, createMediaButtonIntent(keyEvent), 0, (android.os.ResultReceiver) null);
                    return true;
                }
                this.mCb.onMediaButtonFromController(str, i, i2, createMediaButtonIntent(keyEvent));
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in sendMediaRequest.", e);
                return false;
            }
        }

        public void sendCommand(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:" + str2);
                this.mCb.onCommand(str, i, i2, str2, bundle, resultReceiver);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in sendCommand.", e);
            }
        }

        public void sendCustomAction(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:custom-" + str2);
                this.mCb.onCustomAction(str, i, i2, str2, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in sendCustomAction.", e);
            }
        }

        public void prepare(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepare");
                this.mCb.onPrepare(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in prepare.", e);
            }
        }

        public void prepareFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepareFromMediaId");
                this.mCb.onPrepareFromMediaId(str, i, i2, str2, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in prepareFromMediaId.", e);
            }
        }

        public void prepareFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepareFromSearch");
                this.mCb.onPrepareFromSearch(str, i, i2, str2, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in prepareFromSearch.", e);
            }
        }

        public void prepareFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:prepareFromUri");
                this.mCb.onPrepareFromUri(str, i, i2, uri, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in prepareFromUri.", e);
            }
        }

        public void play(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:play");
                this.mCb.onPlay(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in play.", e);
            }
        }

        public void playFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:playFromMediaId");
                this.mCb.onPlayFromMediaId(str, i, i2, str2, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in playFromMediaId.", e);
            }
        }

        public void playFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:playFromSearch");
                this.mCb.onPlayFromSearch(str, i, i2, str2, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in playFromSearch.", e);
            }
        }

        public void playFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:playFromUri");
                this.mCb.onPlayFromUri(str, i, i2, uri, bundle);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in playFromUri.", e);
            }
        }

        public void skipToTrack(java.lang.String str, int i, int i2, long j) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:skipToTrack");
                this.mCb.onSkipToTrack(str, i, i2, j);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in skipToTrack", e);
            }
        }

        public void pause(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:pause");
                this.mCb.onPause(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in pause.", e);
            }
        }

        public void stop(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:stop");
                this.mCb.onStop(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in stop.", e);
            }
        }

        public void next(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:next");
                this.mCb.onNext(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in next.", e);
            }
        }

        public void previous(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:previous");
                this.mCb.onPrevious(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in previous.", e);
            }
        }

        public void fastForward(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:fastForward");
                this.mCb.onFastForward(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in fastForward.", e);
            }
        }

        public void rewind(java.lang.String str, int i, int i2) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:rewind");
                this.mCb.onRewind(str, i, i2);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in rewind.", e);
            }
        }

        public void seekTo(java.lang.String str, int i, int i2, long j) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:seekTo");
                this.mCb.onSeekTo(str, i, i2, j);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in seekTo.", e);
            }
        }

        public void rate(java.lang.String str, int i, int i2, android.media.Rating rating) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:rate");
                this.mCb.onRate(str, i, i2, rating);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in rate.", e);
            }
        }

        public void setPlaybackSpeed(java.lang.String str, int i, int i2, float f) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:setPlaybackSpeed");
                this.mCb.onSetPlaybackSpeed(str, i, i2, f);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in setPlaybackSpeed.", e);
            }
        }

        public void adjustVolume(java.lang.String str, int i, int i2, boolean z, int i3) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:adjustVolume");
                if (z) {
                    this.mCb.onAdjustVolume(com.android.server.media.MediaSessionRecord.this.mContext.getPackageName(), android.os.Process.myPid(), 1000, i3);
                } else {
                    this.mCb.onAdjustVolume(str, i, i2, i3);
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in adjustVolume.", e);
            }
        }

        public void setVolumeTo(java.lang.String str, int i, int i2, int i3) {
            try {
                com.android.server.media.MediaSessionRecord.this.mService.tempAllowlistTargetPkgIfPossible(com.android.server.media.MediaSessionRecord.this.getUid(), com.android.server.media.MediaSessionRecord.this.getPackageName(), i, i2, str, "MediaSessionRecord:setVolumeTo");
                this.mCb.onSetVolumeTo(str, i, i2, i3);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.media.MediaSessionRecord.TAG, "Remote failure in setVolumeTo.", e);
            }
        }

        private android.content.Intent createMediaButtonIntent(android.view.KeyEvent keyEvent) {
            android.content.Intent intent = new android.content.Intent("android.intent.action.MEDIA_BUTTON");
            intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
            return intent;
        }
    }

    class ControllerStub extends android.media.session.ISessionController.Stub {
        ControllerStub() {
        }

        public void sendCommand(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.sendCommand(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2, bundle, resultReceiver);
        }

        public boolean sendMediaButton(java.lang.String str, android.view.KeyEvent keyEvent) {
            return com.android.server.media.MediaSessionRecord.this.mSessionCb.sendMediaButton(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), false, keyEvent);
        }

        public void registerCallback(java.lang.String str, final android.media.session.ISessionControllerCallback iSessionControllerCallback) {
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                if (com.android.server.media.MediaSessionRecord.this.mDestroyed) {
                    try {
                        iSessionControllerCallback.onSessionDestroyed();
                    } catch (java.lang.Exception e) {
                    }
                    return;
                }
                if (com.android.server.media.MediaSessionRecord.this.getControllerHolderIndexForCb(iSessionControllerCallback) < 0) {
                    com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder iSessionControllerCallbackHolder = com.android.server.media.MediaSessionRecord.this.new ISessionControllerCallbackHolder(iSessionControllerCallback, str, android.os.Binder.getCallingUid(), new android.os.IBinder.DeathRecipient() { // from class: com.android.server.media.MediaSessionRecord$ControllerStub$$ExternalSyntheticLambda0
                        @Override // android.os.IBinder.DeathRecipient
                        public final void binderDied() {
                            com.android.server.media.MediaSessionRecord.ControllerStub.this.lambda$registerCallback$0(iSessionControllerCallback);
                        }
                    });
                    com.android.server.media.MediaSessionRecord.this.mControllerCallbackHolders.add(iSessionControllerCallbackHolder);
                    if (com.android.server.media.MediaSessionRecord.DEBUG) {
                        android.util.Slog.d(com.android.server.media.MediaSessionRecord.TAG, "registering controller callback " + iSessionControllerCallback + " from controller" + str);
                    }
                    try {
                        iSessionControllerCallback.asBinder().linkToDeath(iSessionControllerCallbackHolder.mDeathMonitor, 0);
                    } catch (android.os.RemoteException e2) {
                        lambda$registerCallback$0(iSessionControllerCallback);
                        android.util.Slog.w(com.android.server.media.MediaSessionRecord.TAG, "registerCallback failed to linkToDeath", e2);
                    }
                }
            }
        }

        /* renamed from: unregisterCallback, reason: merged with bridge method [inline-methods] */
        public void lambda$registerCallback$0(android.media.session.ISessionControllerCallback iSessionControllerCallback) {
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                int controllerHolderIndexForCb = com.android.server.media.MediaSessionRecord.this.getControllerHolderIndexForCb(iSessionControllerCallback);
                if (controllerHolderIndexForCb != -1) {
                    try {
                        iSessionControllerCallback.asBinder().unlinkToDeath(((com.android.server.media.MediaSessionRecord.ISessionControllerCallbackHolder) com.android.server.media.MediaSessionRecord.this.mControllerCallbackHolders.get(controllerHolderIndexForCb)).mDeathMonitor, 0);
                    } catch (java.util.NoSuchElementException e) {
                        android.util.Slog.w(com.android.server.media.MediaSessionRecord.TAG, "error unlinking to binder death", e);
                    }
                    com.android.server.media.MediaSessionRecord.this.mControllerCallbackHolders.remove(controllerHolderIndexForCb);
                }
                if (com.android.server.media.MediaSessionRecord.DEBUG) {
                    android.util.Slog.d(com.android.server.media.MediaSessionRecord.TAG, "unregistering callback " + iSessionControllerCallback.asBinder());
                }
            }
        }

        public java.lang.String getPackageName() {
            return com.android.server.media.MediaSessionRecord.this.mPackageName;
        }

        public java.lang.String getTag() {
            return com.android.server.media.MediaSessionRecord.this.mTag;
        }

        public android.os.Bundle getSessionInfo() {
            return com.android.server.media.MediaSessionRecord.this.mSessionInfo;
        }

        public android.app.PendingIntent getLaunchPendingIntent() {
            return com.android.server.media.MediaSessionRecord.this.mLaunchIntent;
        }

        public long getFlags() {
            return com.android.server.media.MediaSessionRecord.this.mFlags;
        }

        public android.media.session.MediaController.PlaybackInfo getVolumeAttributes() {
            return com.android.server.media.MediaSessionRecord.this.getVolumeAttributes();
        }

        public void adjustVolume(java.lang.String str, java.lang.String str2, int i, int i2) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaSessionRecord.this.adjustVolume(str, str2, callingPid, callingUid, false, i, i2, false);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void setVolumeTo(java.lang.String str, java.lang.String str2, int i, int i2) {
            int callingPid = android.os.Binder.getCallingPid();
            int callingUid = android.os.Binder.getCallingUid();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                com.android.server.media.MediaSessionRecord.this.setVolumeTo(str, str2, callingPid, callingUid, i, i2);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public void prepare(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.prepare(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void prepareFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.prepareFromMediaId(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2, bundle);
        }

        public void prepareFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.prepareFromSearch(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2, bundle);
        }

        public void prepareFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.prepareFromUri(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), uri, bundle);
        }

        public void play(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.play(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void playFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.playFromMediaId(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2, bundle);
        }

        public void playFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.playFromSearch(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2, bundle);
        }

        public void playFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.playFromUri(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), uri, bundle);
        }

        public void skipToQueueItem(java.lang.String str, long j) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.skipToTrack(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), j);
        }

        public void pause(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.pause(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void stop(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.stop(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void next(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.next(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void previous(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.previous(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void fastForward(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.fastForward(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void rewind(java.lang.String str) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.rewind(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        }

        public void seekTo(java.lang.String str, long j) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.seekTo(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), j);
        }

        public void rate(java.lang.String str, android.media.Rating rating) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.rate(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), rating);
        }

        public void setPlaybackSpeed(java.lang.String str, float f) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.setPlaybackSpeed(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), f);
        }

        public void sendCustomAction(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
            com.android.server.media.MediaSessionRecord.this.mSessionCb.sendCustomAction(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), str2, bundle);
        }

        public android.media.MediaMetadata getMetadata() {
            android.media.MediaMetadata mediaMetadata;
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                mediaMetadata = com.android.server.media.MediaSessionRecord.this.mMetadata;
            }
            return mediaMetadata;
        }

        public android.media.session.PlaybackState getPlaybackState() {
            return com.android.server.media.MediaSessionRecord.this.getStateWithUpdatedPosition();
        }

        public android.content.pm.ParceledListSlice getQueue() {
            android.content.pm.ParceledListSlice parceledListSlice;
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                parceledListSlice = com.android.server.media.MediaSessionRecord.this.mQueue == null ? null : new android.content.pm.ParceledListSlice(com.android.server.media.MediaSessionRecord.this.mQueue);
            }
            return parceledListSlice;
        }

        public java.lang.CharSequence getQueueTitle() {
            return com.android.server.media.MediaSessionRecord.this.mQueueTitle;
        }

        public android.os.Bundle getExtras() {
            android.os.Bundle bundle;
            synchronized (com.android.server.media.MediaSessionRecord.this.mLock) {
                bundle = com.android.server.media.MediaSessionRecord.this.mExtras;
            }
            return bundle;
        }

        public int getRatingType() {
            return com.android.server.media.MediaSessionRecord.this.mRatingType;
        }
    }

    private class ISessionControllerCallbackHolder {
        private final android.media.session.ISessionControllerCallback mCallback;
        private final android.os.IBinder.DeathRecipient mDeathMonitor;
        private final java.lang.String mPackageName;
        private final int mUid;

        ISessionControllerCallbackHolder(android.media.session.ISessionControllerCallback iSessionControllerCallback, java.lang.String str, int i, android.os.IBinder.DeathRecipient deathRecipient) {
            this.mCallback = iSessionControllerCallback;
            this.mPackageName = str;
            this.mUid = i;
            this.mDeathMonitor = deathRecipient;
        }
    }

    private class MessageHandler extends android.os.Handler {
        private static final int MSG_DESTROYED = 9;
        private static final int MSG_SEND_EVENT = 6;
        private static final int MSG_UPDATE_EXTRAS = 5;
        private static final int MSG_UPDATE_METADATA = 1;
        private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
        private static final int MSG_UPDATE_QUEUE = 3;
        private static final int MSG_UPDATE_QUEUE_TITLE = 4;
        private static final int MSG_UPDATE_SESSION_STATE = 7;
        private static final int MSG_UPDATE_VOLUME = 8;

        public MessageHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    com.android.server.media.MediaSessionRecord.this.pushMetadataUpdate();
                    break;
                case 2:
                    com.android.server.media.MediaSessionRecord.this.pushPlaybackStateUpdate();
                    break;
                case 3:
                    com.android.server.media.MediaSessionRecord.this.pushQueueUpdate();
                    break;
                case 4:
                    com.android.server.media.MediaSessionRecord.this.pushQueueTitleUpdate();
                    break;
                case 5:
                    com.android.server.media.MediaSessionRecord.this.pushExtrasUpdate();
                    break;
                case 6:
                    com.android.server.media.MediaSessionRecord.this.pushEvent((java.lang.String) message.obj, message.getData());
                    break;
                case 8:
                    com.android.server.media.MediaSessionRecord.this.pushVolumeUpdate();
                    break;
                case 9:
                    com.android.server.media.MediaSessionRecord.this.pushSessionDestroyed();
                    break;
            }
        }

        public void post(int i) {
            post(i, null);
        }

        public void post(int i, java.lang.Object obj) {
            obtainMessage(i, obj).sendToTarget();
        }

        public void post(int i, java.lang.Object obj, android.os.Bundle bundle) {
            android.os.Message obtainMessage = obtainMessage(i, obj);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        }
    }
}
