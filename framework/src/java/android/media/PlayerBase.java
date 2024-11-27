package android.media;

/* loaded from: classes2.dex */
public abstract class PlayerBase {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_APP_OPS = false;
    private static final java.lang.String TAG = "PlayerBase";
    private static android.media.IAudioService sService;
    private com.android.internal.app.IAppOpsService mAppOps;
    private com.android.internal.app.IAppOpsCallback mAppOpsCallback;
    protected android.media.AudioAttributes mAttributes;
    private int mDeviceId;
    private final int mImplType;
    private int mState;
    protected float mLeftVolume = 1.0f;
    protected float mRightVolume = 1.0f;
    protected float mAuxEffectSendLevel = 0.0f;
    private final java.lang.Object mLock = new java.lang.Object();
    private boolean mHasAppOpsPlayAudio = true;
    protected int mPlayerIId = -1;
    private int mStartDelayMs = 0;
    private float mPanMultiplierL = 1.0f;
    private float mPanMultiplierR = 1.0f;
    private float mVolMultiplier = 1.0f;

    abstract int playerApplyVolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation);

    abstract android.media.VolumeShaper.State playerGetVolumeShaperState(int i);

    abstract void playerPause();

    abstract int playerSetAuxEffectSendLevel(boolean z, float f);

    abstract void playerSetVolume(boolean z, float f, float f2);

    abstract void playerStart();

    abstract void playerStop();

    PlayerBase(android.media.AudioAttributes audioAttributes, int i) {
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes");
        }
        this.mAttributes = audioAttributes;
        this.mImplType = i;
        this.mState = 1;
    }

    public int getPlayerIId() {
        int i;
        synchronized (this.mLock) {
            i = this.mPlayerIId;
        }
        return i;
    }

    protected void baseRegisterPlayer(int i) {
        try {
            this.mPlayerIId = getService().trackPlayer(new android.media.PlayerBase.PlayerIdCard(this.mImplType, this.mAttributes, new android.media.PlayerBase.IPlayerWrapper(this), i));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error talking to audio service, player will not be tracked", e);
        }
    }

    void baseUpdateAudioAttributes(android.media.AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes");
        }
        try {
            getService().playerAttributes(this.mPlayerIId, audioAttributes);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error talking to audio service, audio attributes will not be updated", e);
        }
        synchronized (this.mLock) {
            this.mAttributes = audioAttributes;
        }
    }

    void baseUpdateSessionId(int i) {
        try {
            getService().playerSessionId(this.mPlayerIId, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error talking to audio service, the session ID will not be updated", e);
        }
    }

    void baseUpdateDeviceId(android.media.AudioDeviceInfo audioDeviceInfo) {
        int i;
        int i2;
        if (audioDeviceInfo == null) {
            i = 0;
        } else {
            i = audioDeviceInfo.getId();
        }
        synchronized (this.mLock) {
            i2 = this.mPlayerIId;
            this.mDeviceId = i;
        }
        try {
            getService().playerEvent(i2, 5, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error talking to audio service, " + i + " device id will not be tracked for piid=" + i2, e);
        }
    }

    private void updateState(int i, int i2) {
        int i3;
        synchronized (this.mLock) {
            this.mState = i;
            i3 = this.mPlayerIId;
            this.mDeviceId = i2;
        }
        try {
            getService().playerEvent(i3, i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error talking to audio service, " + android.media.AudioPlaybackConfiguration.toLogFriendlyPlayerState(i) + " state will not be tracked for piid=" + i3, e);
        }
    }

    void baseStart(int i) {
        updateState(2, i);
    }

    void baseSetStartDelayMs(int i) {
        synchronized (this.mLock) {
            this.mStartDelayMs = java.lang.Math.max(i, 0);
        }
    }

    protected int getStartDelayMs() {
        int i;
        synchronized (this.mLock) {
            i = this.mStartDelayMs;
        }
        return i;
    }

    void basePause() {
        updateState(3, 0);
    }

    void baseStop() {
        updateState(4, 0);
    }

    void baseSetPan(float f) {
        float min = java.lang.Math.min(java.lang.Math.max(-1.0f, f), 1.0f);
        synchronized (this.mLock) {
            if (min >= 0.0f) {
                this.mPanMultiplierL = 1.0f - min;
                this.mPanMultiplierR = 1.0f;
            } else {
                this.mPanMultiplierL = 1.0f;
                this.mPanMultiplierR = min + 1.0f;
            }
        }
        updatePlayerVolume();
    }

    private void updatePlayerVolume() {
        float f;
        float f2;
        synchronized (this.mLock) {
            f = this.mVolMultiplier * this.mLeftVolume * this.mPanMultiplierL;
            f2 = this.mVolMultiplier * this.mRightVolume * this.mPanMultiplierR;
        }
        playerSetVolume(false, f, f2);
    }

    void setVolumeMultiplier(float f) {
        synchronized (this.mLock) {
            this.mVolMultiplier = f;
        }
        updatePlayerVolume();
    }

    void baseSetVolume(float f, float f2) {
        synchronized (this.mLock) {
            this.mLeftVolume = f;
            this.mRightVolume = f2;
        }
        updatePlayerVolume();
    }

    int baseSetAuxEffectSendLevel(float f) {
        synchronized (this.mLock) {
            this.mAuxEffectSendLevel = f;
        }
        return playerSetAuxEffectSendLevel(false, f);
    }

    void baseRelease() {
        boolean z;
        synchronized (this.mLock) {
            z = false;
            if (this.mState != 0) {
                this.mState = 0;
                z = true;
            }
        }
        if (z) {
            try {
                getService().releasePlayer(this.mPlayerIId);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error talking to audio service, the player will still be tracked", e);
            }
        }
        try {
            if (this.mAppOps != null) {
                this.mAppOps.stopWatchingMode(this.mAppOpsCallback);
            }
        } catch (java.lang.Exception e2) {
        }
    }

    private static android.media.IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        sService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        return sService;
    }

    public void setStartDelayMs(int i) {
        baseSetStartDelayMs(i);
    }

    private static class IPlayerWrapper extends android.media.IPlayer.Stub {
        private final java.lang.ref.WeakReference<android.media.PlayerBase> mWeakPB;

        public IPlayerWrapper(android.media.PlayerBase playerBase) {
            this.mWeakPB = new java.lang.ref.WeakReference<>(playerBase);
        }

        @Override // android.media.IPlayer
        public void start() {
            android.media.PlayerBase playerBase = this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerStart();
            }
        }

        @Override // android.media.IPlayer
        public void pause() {
            android.media.PlayerBase playerBase = this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerPause();
            }
        }

        @Override // android.media.IPlayer
        public void stop() {
            android.media.PlayerBase playerBase = this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerStop();
            }
        }

        @Override // android.media.IPlayer
        public void setVolume(float f) {
            android.media.PlayerBase playerBase = this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.setVolumeMultiplier(f);
            }
        }

        @Override // android.media.IPlayer
        public void setPan(float f) {
            android.media.PlayerBase playerBase = this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.baseSetPan(f);
            }
        }

        @Override // android.media.IPlayer
        public void setStartDelayMs(int i) {
            android.media.PlayerBase playerBase = this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.baseSetStartDelayMs(i);
            }
        }

        @Override // android.media.IPlayer
        public void applyVolumeShaper(android.media.VolumeShaperConfiguration volumeShaperConfiguration, android.media.VolumeShaperOperation volumeShaperOperation) {
            android.media.PlayerBase playerBase = this.mWeakPB.get();
            if (playerBase != null) {
                playerBase.playerApplyVolumeShaper(android.media.VolumeShaper.Configuration.fromParcelable(volumeShaperConfiguration), android.media.VolumeShaper.Operation.fromParcelable(volumeShaperOperation));
            }
        }
    }

    public static class PlayerIdCard implements android.os.Parcelable {
        public static final int AUDIO_ATTRIBUTES_DEFINED = 1;
        public static final int AUDIO_ATTRIBUTES_NONE = 0;
        public static final android.os.Parcelable.Creator<android.media.PlayerBase.PlayerIdCard> CREATOR = new android.os.Parcelable.Creator<android.media.PlayerBase.PlayerIdCard>() { // from class: android.media.PlayerBase.PlayerIdCard.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.PlayerBase.PlayerIdCard createFromParcel(android.os.Parcel parcel) {
                return new android.media.PlayerBase.PlayerIdCard(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.PlayerBase.PlayerIdCard[] newArray(int i) {
                return new android.media.PlayerBase.PlayerIdCard[i];
            }
        };
        public final android.media.AudioAttributes mAttributes;
        public final android.media.IPlayer mIPlayer;
        public final int mPlayerType;
        public final int mSessionId;

        PlayerIdCard(int i, android.media.AudioAttributes audioAttributes, android.media.IPlayer iPlayer, int i2) {
            this.mPlayerType = i;
            this.mAttributes = audioAttributes;
            this.mIPlayer = iPlayer;
            this.mSessionId = i2;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPlayerType), java.lang.Integer.valueOf(this.mSessionId));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mPlayerType);
            this.mAttributes.writeToParcel(parcel, 0);
            parcel.writeStrongBinder(this.mIPlayer == null ? null : this.mIPlayer.asBinder());
            parcel.writeInt(this.mSessionId);
        }

        private PlayerIdCard(android.os.Parcel parcel) {
            this.mPlayerType = parcel.readInt();
            this.mAttributes = android.media.AudioAttributes.CREATOR.createFromParcel(parcel);
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            this.mIPlayer = readStrongBinder == null ? null : android.media.IPlayer.Stub.asInterface(readStrongBinder);
            this.mSessionId = parcel.readInt();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.media.PlayerBase.PlayerIdCard)) {
                return false;
            }
            android.media.PlayerBase.PlayerIdCard playerIdCard = (android.media.PlayerBase.PlayerIdCard) obj;
            if (this.mPlayerType == playerIdCard.mPlayerType && this.mAttributes.equals(playerIdCard.mAttributes) && this.mSessionId == playerIdCard.mSessionId) {
                return true;
            }
            return false;
        }
    }

    public static void deprecateStreamTypeForPlayback(int i, java.lang.String str, java.lang.String str2) throws java.lang.IllegalArgumentException {
        if (i == 10) {
            throw new java.lang.IllegalArgumentException("Use of STREAM_ACCESSIBILITY is reserved for volume control");
        }
        android.util.Log.w(str, "Use of stream types is deprecated for operations other than volume control");
        android.util.Log.w(str, "See the documentation of " + str2 + " for what to use instead with android.media.AudioAttributes to qualify your playback use case");
    }

    protected java.lang.String getCurrentOpPackageName() {
        return android.text.TextUtils.emptyIfNull(android.app.ActivityThread.currentOpPackageName());
    }

    protected static int resolvePlaybackSessionId(android.content.Context context, int i) {
        int deviceId;
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager;
        if (i != 0) {
            return i;
        }
        if (context == null || (deviceId = context.getDeviceId()) == 0 || (virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) context.getSystemService(android.companion.virtual.VirtualDeviceManager.class)) == null || virtualDeviceManager.getDevicePolicy(deviceId, 1) == 0) {
            return 0;
        }
        return virtualDeviceManager.getAudioPlaybackSessionId(deviceId);
    }
}
