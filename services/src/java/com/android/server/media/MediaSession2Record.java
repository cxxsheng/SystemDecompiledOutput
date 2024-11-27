package com.android.server.media;

/* loaded from: classes2.dex */
public class MediaSession2Record extends com.android.server.media.MediaSessionRecordImpl {

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.media.MediaController2 mController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.media.HandlerExecutor mHandlerExecutor;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsClosed;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mIsConnected;
    private final java.lang.Object mLock = new java.lang.Object();
    private final int mPid;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mPolicies;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.media.MediaSessionService mService;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.media.Session2Token mSessionToken;
    private static final java.lang.String TAG = "MediaSession2Record";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public MediaSession2Record(android.media.Session2Token session2Token, com.android.server.media.MediaSessionService mediaSessionService, android.os.Looper looper, int i, int i2) {
        synchronized (this.mLock) {
            this.mUniqueId = com.android.server.media.MediaSessionRecordImpl.sNextMediaSessionRecordId.getAndIncrement();
            this.mSessionToken = session2Token;
            this.mService = mediaSessionService;
            this.mHandlerExecutor = new com.android.server.media.HandlerExecutor(new android.os.Handler(looper));
            this.mController = new android.media.MediaController2.Builder(mediaSessionService.getContext(), session2Token).setControllerCallback(this.mHandlerExecutor, new com.android.server.media.MediaSession2Record.Controller2Callback()).build();
            this.mPid = i;
            this.mPolicies = i2;
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public java.lang.String getPackageName() {
        return this.mSessionToken.getPackageName();
    }

    public android.media.Session2Token getSession2Token() {
        return this.mSessionToken;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getUid() {
        return this.mSessionToken.getUid();
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public int getUserId() {
        return android.os.UserHandle.getUserHandleForUid(this.mSessionToken.getUid()).getIdentifier();
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public android.app.ForegroundServiceDelegationOptions getForegroundServiceDelegationOptions() {
        return null;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isSystemPriority() {
        return false;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void adjustVolume(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, int i3, int i4, boolean z2) {
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isActive() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsConnected;
        }
        return z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean checkPlaybackActiveState(boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                z2 = (this.mIsConnected && this.mController.isPlaybackActive()) == z;
            } finally {
            }
        }
        return z2;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isPlaybackTypeLocal() {
        return true;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public void close() {
        synchronized (this.mLock) {
            this.mIsClosed = true;
            this.mController.close();
        }
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean isClosed() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mIsClosed;
        }
        return z;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean sendMediaButton(java.lang.String str, int i, int i2, boolean z, android.view.KeyEvent keyEvent, int i3, android.os.ResultReceiver resultReceiver) {
        return false;
    }

    @Override // com.android.server.media.MediaSessionRecordImpl
    public boolean canHandleVolumeKey() {
        return false;
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
        printWriter.println(str + "uniqueId=" + getUniqueId());
        printWriter.println(str + "token=" + this.mSessionToken);
        printWriter.println(str + "controller=" + this.mController);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("  ");
        printWriter.println(sb.toString() + "playbackActive=" + this.mController.isPlaybackActive());
    }

    public java.lang.String toString() {
        return getPackageName() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + getUniqueId() + " (userId=" + getUserId() + ")";
    }

    private class Controller2Callback extends android.media.MediaController2.ControllerCallback {
        private Controller2Callback() {
        }

        @Override // android.media.MediaController2.ControllerCallback
        public void onConnected(android.media.MediaController2 mediaController2, android.media.Session2CommandGroup session2CommandGroup) {
            com.android.server.media.MediaSessionService mediaSessionService;
            if (com.android.server.media.MediaSession2Record.DEBUG) {
                android.util.Log.d(com.android.server.media.MediaSession2Record.TAG, "connected to " + com.android.server.media.MediaSession2Record.this.mSessionToken + ", allowed=" + session2CommandGroup);
            }
            synchronized (com.android.server.media.MediaSession2Record.this.mLock) {
                com.android.server.media.MediaSession2Record.this.mIsConnected = true;
                mediaSessionService = com.android.server.media.MediaSession2Record.this.mService;
            }
            mediaSessionService.onSessionActiveStateChanged(com.android.server.media.MediaSession2Record.this, null);
        }

        @Override // android.media.MediaController2.ControllerCallback
        public void onDisconnected(android.media.MediaController2 mediaController2) {
            com.android.server.media.MediaSessionService mediaSessionService;
            if (com.android.server.media.MediaSession2Record.DEBUG) {
                android.util.Log.d(com.android.server.media.MediaSession2Record.TAG, "disconnected from " + com.android.server.media.MediaSession2Record.this.mSessionToken);
            }
            synchronized (com.android.server.media.MediaSession2Record.this.mLock) {
                com.android.server.media.MediaSession2Record.this.mIsConnected = false;
                mediaSessionService = com.android.server.media.MediaSession2Record.this.mService;
            }
            mediaSessionService.onSessionDied(com.android.server.media.MediaSession2Record.this);
        }

        @Override // android.media.MediaController2.ControllerCallback
        public void onPlaybackActiveChanged(android.media.MediaController2 mediaController2, boolean z) {
            com.android.server.media.MediaSessionService mediaSessionService;
            if (com.android.server.media.MediaSession2Record.DEBUG) {
                android.util.Log.d(com.android.server.media.MediaSession2Record.TAG, "playback active changed, " + com.android.server.media.MediaSession2Record.this.mSessionToken + ", active=" + z);
            }
            synchronized (com.android.server.media.MediaSession2Record.this.mLock) {
                mediaSessionService = com.android.server.media.MediaSession2Record.this.mService;
            }
            mediaSessionService.onSessionPlaybackStateChanged(com.android.server.media.MediaSession2Record.this, z, null);
        }
    }
}
