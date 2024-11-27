package android.media;

/* loaded from: classes2.dex */
public final class AudioPlaybackConfiguration implements android.os.Parcelable {
    private static final boolean DEBUG = false;
    public static final java.lang.String EXTRA_PLAYER_EVENT_CHANNEL_MASK = "android.media.extra.PLAYER_EVENT_CHANNEL_MASK";
    public static final java.lang.String EXTRA_PLAYER_EVENT_MUTE = "android.media.extra.PLAYER_EVENT_MUTE";
    public static final java.lang.String EXTRA_PLAYER_EVENT_SAMPLE_RATE = "android.media.extra.PLAYER_EVENT_SAMPLE_RATE";
    public static final java.lang.String EXTRA_PLAYER_EVENT_SPATIALIZED = "android.media.extra.PLAYER_EVENT_SPATIALIZED";

    @android.annotation.SystemApi
    public static final int MUTED_BY_APP_OPS = 8;

    @android.annotation.SystemApi
    public static final int MUTED_BY_CLIENT_VOLUME = 16;

    @android.annotation.SystemApi
    public static final int MUTED_BY_MASTER = 1;

    @android.annotation.SystemApi
    public static final int MUTED_BY_STREAM_MUTED = 4;

    @android.annotation.SystemApi
    public static final int MUTED_BY_STREAM_VOLUME = 2;

    @android.annotation.SystemApi
    public static final int MUTED_BY_VOLUME_SHAPER = 32;
    public static final int PLAYER_DEVICEID_INVALID = 0;
    public static final int PLAYER_PIID_INVALID = -1;

    @android.annotation.SystemApi
    public static final int PLAYER_STATE_IDLE = 1;

    @android.annotation.SystemApi
    public static final int PLAYER_STATE_PAUSED = 3;

    @android.annotation.SystemApi
    public static final int PLAYER_STATE_RELEASED = 0;

    @android.annotation.SystemApi
    public static final int PLAYER_STATE_STARTED = 2;

    @android.annotation.SystemApi
    public static final int PLAYER_STATE_STOPPED = 4;

    @android.annotation.SystemApi
    public static final int PLAYER_STATE_UNKNOWN = -1;

    @android.annotation.SystemApi
    public static final int PLAYER_TYPE_AAUDIO = 13;
    public static final int PLAYER_TYPE_EXTERNAL_PROXY = 15;
    public static final int PLAYER_TYPE_HW_SOURCE = 14;

    @android.annotation.SystemApi
    public static final int PLAYER_TYPE_JAM_AUDIOTRACK = 1;

    @android.annotation.SystemApi
    public static final int PLAYER_TYPE_JAM_MEDIAPLAYER = 2;

    @android.annotation.SystemApi
    public static final int PLAYER_TYPE_JAM_SOUNDPOOL = 3;

    @android.annotation.SystemApi
    public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_BUFFERQUEUE = 11;

    @android.annotation.SystemApi
    public static final int PLAYER_TYPE_SLES_AUDIOPLAYER_URI_FD = 12;

    @android.annotation.SystemApi
    public static final int PLAYER_TYPE_UNKNOWN = -1;
    public static final int PLAYER_UPDATE_DEVICE_ID = 5;
    public static final int PLAYER_UPDATE_FORMAT = 8;
    public static final int PLAYER_UPDATE_MUTED = 7;
    public static final int PLAYER_UPDATE_PORT_ID = 6;
    public static final int PLAYER_UPID_INVALID = -1;
    public static android.media.AudioPlaybackConfiguration.PlayerDeathMonitor sPlayerDeathMonitor;
    private int mClientPid;
    private int mClientUid;
    private int mDeviceId;
    private android.media.AudioPlaybackConfiguration.FormatInfo mFormatInfo;
    private android.media.AudioPlaybackConfiguration.IPlayerShell mIPlayerShell;
    private int mMutedState;
    private android.media.AudioAttributes mPlayerAttr;
    private final int mPlayerIId;
    private int mPlayerState;
    private int mPlayerType;
    private int mSessionId;
    private final java.lang.Object mUpdateablePropLock;
    private static final java.lang.String TAG = new java.lang.String("AudioPlaybackConfiguration");
    public static final android.os.Parcelable.Creator<android.media.AudioPlaybackConfiguration> CREATOR = new android.os.Parcelable.Creator<android.media.AudioPlaybackConfiguration>() { // from class: android.media.AudioPlaybackConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPlaybackConfiguration createFromParcel(android.os.Parcel parcel) {
            return new android.media.AudioPlaybackConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioPlaybackConfiguration[] newArray(int i) {
            return new android.media.AudioPlaybackConfiguration[i];
        }
    };

    public interface PlayerDeathMonitor {
        void playerDeath(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlayerMuteEvent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlayerState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PlayerType {
    }

    public static java.lang.String playerStateToString(int i) {
        switch (i) {
            case -1:
                return "PLAYER_STATE_UNKNOWN";
            case 0:
                return "PLAYER_STATE_RELEASED";
            case 1:
                return "PLAYER_STATE_IDLE";
            case 2:
                return "PLAYER_STATE_STARTED";
            case 3:
                return "PLAYER_STATE_PAUSED";
            case 4:
                return "PLAYER_STATE_STOPPED";
            case 5:
                return "PLAYER_UPDATE_DEVICE_ID";
            case 6:
                return "PLAYER_UPDATE_PORT_ID";
            case 7:
                return "PLAYER_UPDATE_MUTED";
            case 8:
                return "PLAYER_UPDATE_FORMAT";
            default:
                return "invalid state " + i;
        }
    }

    private AudioPlaybackConfiguration(int i) {
        this.mUpdateablePropLock = new java.lang.Object();
        this.mPlayerIId = i;
        this.mIPlayerShell = null;
    }

    public AudioPlaybackConfiguration(android.media.PlayerBase.PlayerIdCard playerIdCard, int i, int i2, int i3) {
        this.mUpdateablePropLock = new java.lang.Object();
        this.mPlayerIId = i;
        this.mPlayerType = playerIdCard.mPlayerType;
        this.mClientUid = i2;
        this.mClientPid = i3;
        this.mMutedState = 0;
        this.mDeviceId = 0;
        this.mPlayerState = 1;
        this.mPlayerAttr = playerIdCard.mAttributes;
        if (sPlayerDeathMonitor != null && playerIdCard.mIPlayer != null) {
            this.mIPlayerShell = new android.media.AudioPlaybackConfiguration.IPlayerShell(this, playerIdCard.mIPlayer);
        } else {
            this.mIPlayerShell = null;
        }
        this.mSessionId = playerIdCard.mSessionId;
        this.mFormatInfo = android.media.AudioPlaybackConfiguration.FormatInfo.DEFAULT;
    }

    public void init() {
        synchronized (this) {
            if (this.mIPlayerShell != null) {
                this.mIPlayerShell.monitorDeath();
            }
        }
    }

    private void setUpdateableFields(int i, int i2, int i3, android.media.AudioPlaybackConfiguration.FormatInfo formatInfo) {
        synchronized (this.mUpdateablePropLock) {
            this.mDeviceId = i;
            this.mSessionId = i2;
            this.mMutedState = i3;
            this.mFormatInfo = formatInfo;
        }
    }

    public static android.media.AudioPlaybackConfiguration anonymizedCopy(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        android.media.AudioPlaybackConfiguration audioPlaybackConfiguration2 = new android.media.AudioPlaybackConfiguration(audioPlaybackConfiguration.mPlayerIId);
        audioPlaybackConfiguration2.mPlayerState = audioPlaybackConfiguration.mPlayerState;
        android.media.AudioAttributes.Builder allowedCapturePolicy = new android.media.AudioAttributes.Builder().setContentType(audioPlaybackConfiguration.mPlayerAttr.getContentType()).setFlags(audioPlaybackConfiguration.mPlayerAttr.getFlags()).setAllowedCapturePolicy(audioPlaybackConfiguration.mPlayerAttr.getAllowedCapturePolicy() != 1 ? 3 : 1);
        if (android.media.AudioAttributes.isSystemUsage(audioPlaybackConfiguration.mPlayerAttr.getSystemUsage())) {
            allowedCapturePolicy.setSystemUsage(audioPlaybackConfiguration.mPlayerAttr.getSystemUsage());
        } else {
            allowedCapturePolicy.setUsage(audioPlaybackConfiguration.mPlayerAttr.getUsage());
        }
        audioPlaybackConfiguration2.mPlayerAttr = allowedCapturePolicy.build();
        audioPlaybackConfiguration2.mPlayerType = -1;
        audioPlaybackConfiguration2.mClientUid = -1;
        audioPlaybackConfiguration2.mClientPid = -1;
        audioPlaybackConfiguration2.mIPlayerShell = null;
        audioPlaybackConfiguration2.setUpdateableFields(0, 0, 0, android.media.AudioPlaybackConfiguration.FormatInfo.DEFAULT);
        return audioPlaybackConfiguration2;
    }

    public android.media.AudioAttributes getAudioAttributes() {
        return this.mPlayerAttr;
    }

    @android.annotation.SystemApi
    public int getClientUid() {
        return this.mClientUid;
    }

    @android.annotation.SystemApi
    public int getClientPid() {
        return this.mClientPid;
    }

    public android.media.AudioDeviceInfo getAudioDeviceInfo() {
        int i;
        synchronized (this.mUpdateablePropLock) {
            i = this.mDeviceId;
        }
        if (i == 0) {
            return null;
        }
        return android.media.AudioManager.getDeviceForPortId(i, 2);
    }

    @android.annotation.SystemApi
    public int getSessionId() {
        int i;
        synchronized (this.mUpdateablePropLock) {
            i = this.mSessionId;
        }
        return i;
    }

    @android.annotation.SystemApi
    public boolean isMuted() {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = this.mMutedState != 0;
        }
        return z;
    }

    @android.annotation.SystemApi
    public int getMutedBy() {
        int i;
        synchronized (this.mUpdateablePropLock) {
            i = this.mMutedState;
        }
        return i;
    }

    @android.annotation.SystemApi
    public int getPlayerType() {
        switch (this.mPlayerType) {
            case 14:
            case 15:
                return -1;
            default:
                return this.mPlayerType;
        }
    }

    @android.annotation.SystemApi
    public int getPlayerState() {
        return this.mPlayerState;
    }

    @android.annotation.SystemApi
    public int getPlayerInterfaceId() {
        return this.mPlayerIId;
    }

    @android.annotation.SystemApi
    public android.media.PlayerProxy getPlayerProxy() {
        android.media.AudioPlaybackConfiguration.IPlayerShell iPlayerShell;
        synchronized (this) {
            iPlayerShell = this.mIPlayerShell;
        }
        if (iPlayerShell == null) {
            return null;
        }
        return new android.media.PlayerProxy(this);
    }

    @android.annotation.SystemApi
    public boolean isSpatialized() {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = this.mFormatInfo.mIsSpatialized;
        }
        return z;
    }

    @android.annotation.SystemApi
    public int getSampleRate() {
        int i;
        synchronized (this.mUpdateablePropLock) {
            i = this.mFormatInfo.mSampleRate;
        }
        return i;
    }

    @android.annotation.SystemApi
    public int getChannelMask() {
        int convertNativeChannelMaskToOutMask;
        synchronized (this.mUpdateablePropLock) {
            convertNativeChannelMaskToOutMask = android.media.AudioFormat.convertNativeChannelMaskToOutMask(this.mFormatInfo.mNativeChannelMask);
        }
        return convertNativeChannelMaskToOutMask;
    }

    android.media.IPlayer getIPlayer() {
        android.media.AudioPlaybackConfiguration.IPlayerShell iPlayerShell;
        synchronized (this) {
            iPlayerShell = this.mIPlayerShell;
        }
        if (iPlayerShell == null) {
            return null;
        }
        return iPlayerShell.getIPlayer();
    }

    public boolean handleAudioAttributesEvent(android.media.AudioAttributes audioAttributes) {
        boolean z = !audioAttributes.equals(this.mPlayerAttr);
        this.mPlayerAttr = audioAttributes;
        return z;
    }

    public boolean handleSessionIdEvent(int i) {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = i != this.mSessionId;
            this.mSessionId = i;
        }
        return z;
    }

    public boolean handleMutedEvent(int i) {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = this.mMutedState != i;
            this.mMutedState = i;
        }
        return z;
    }

    public boolean handleFormatEvent(android.media.AudioPlaybackConfiguration.FormatInfo formatInfo) {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            z = !this.mFormatInfo.equals(formatInfo);
            this.mFormatInfo = formatInfo;
        }
        return z;
    }

    public boolean handleStateEvent(int i, int i2) {
        boolean z;
        synchronized (this.mUpdateablePropLock) {
            boolean z2 = true;
            if (i == 5) {
                z = false;
            } else {
                try {
                    z = this.mPlayerState != i;
                    this.mPlayerState = i;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (i == 2 || i == 5) {
                if (!z && this.mDeviceId == i2) {
                    z2 = false;
                }
                this.mDeviceId = i2;
                z = z2;
            }
            if (z && i == 0 && this.mIPlayerShell != null) {
                this.mIPlayerShell.release();
                this.mIPlayerShell = null;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playerDied() {
        if (sPlayerDeathMonitor != null) {
            sPlayerDeathMonitor.playerDeath(this.mPlayerIId);
        }
    }

    private boolean isMuteAffectingActiveState() {
        return ((this.mMutedState & 16) == 0 && (this.mMutedState & 32) == 0 && (this.mMutedState & 8) == 0) ? false : true;
    }

    @android.annotation.SystemApi
    public boolean isActive() {
        switch (this.mPlayerState) {
            case 2:
                return !isMuteAffectingActiveState();
            default:
                return false;
        }
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("  " + this);
    }

    public int hashCode() {
        int hash;
        synchronized (this.mUpdateablePropLock) {
            hash = java.util.Objects.hash(java.lang.Integer.valueOf(this.mPlayerIId), java.lang.Integer.valueOf(this.mDeviceId), java.lang.Integer.valueOf(this.mMutedState), java.lang.Integer.valueOf(this.mPlayerType), java.lang.Integer.valueOf(this.mClientUid), java.lang.Integer.valueOf(this.mClientPid), java.lang.Integer.valueOf(this.mSessionId));
        }
        return hash;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.media.AudioPlaybackConfiguration.IPlayerShell iPlayerShell;
        synchronized (this.mUpdateablePropLock) {
            parcel.writeInt(this.mPlayerIId);
            parcel.writeInt(this.mDeviceId);
            parcel.writeInt(this.mMutedState);
            parcel.writeInt(this.mPlayerType);
            parcel.writeInt(this.mClientUid);
            parcel.writeInt(this.mClientPid);
            parcel.writeInt(this.mPlayerState);
            this.mPlayerAttr.writeToParcel(parcel, 0);
            synchronized (this) {
                iPlayerShell = this.mIPlayerShell;
            }
            parcel.writeStrongInterface(iPlayerShell == null ? null : iPlayerShell.getIPlayer());
            parcel.writeInt(this.mSessionId);
            this.mFormatInfo.writeToParcel(parcel, 0);
        }
    }

    private AudioPlaybackConfiguration(android.os.Parcel parcel) {
        this.mUpdateablePropLock = new java.lang.Object();
        this.mPlayerIId = parcel.readInt();
        this.mDeviceId = parcel.readInt();
        this.mMutedState = parcel.readInt();
        this.mPlayerType = parcel.readInt();
        this.mClientUid = parcel.readInt();
        this.mClientPid = parcel.readInt();
        this.mPlayerState = parcel.readInt();
        this.mPlayerAttr = android.media.AudioAttributes.CREATOR.createFromParcel(parcel);
        android.media.IPlayer asInterface = android.media.IPlayer.Stub.asInterface(parcel.readStrongBinder());
        this.mIPlayerShell = asInterface != null ? new android.media.AudioPlaybackConfiguration.IPlayerShell(null, asInterface) : null;
        this.mSessionId = parcel.readInt();
        this.mFormatInfo = android.media.AudioPlaybackConfiguration.FormatInfo.CREATOR.createFromParcel(parcel);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof android.media.AudioPlaybackConfiguration)) {
            return false;
        }
        android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = (android.media.AudioPlaybackConfiguration) obj;
        if (this.mPlayerIId == audioPlaybackConfiguration.mPlayerIId && this.mDeviceId == audioPlaybackConfiguration.mDeviceId && this.mMutedState == audioPlaybackConfiguration.mMutedState && this.mPlayerType == audioPlaybackConfiguration.mPlayerType && this.mClientUid == audioPlaybackConfiguration.mClientUid && this.mClientPid == audioPlaybackConfiguration.mClientPid && this.mSessionId == audioPlaybackConfiguration.mSessionId) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        synchronized (this.mUpdateablePropLock) {
            sb.append("AudioPlaybackConfiguration piid:").append(this.mPlayerIId).append(" deviceId:").append(this.mDeviceId).append(" type:").append(toLogFriendlyPlayerType(this.mPlayerType)).append(" u/pid:").append(this.mClientUid).append("/").append(this.mClientPid).append(" state:").append(toLogFriendlyPlayerState(this.mPlayerState)).append(" attr:").append(this.mPlayerAttr).append(" sessionId:").append(this.mSessionId).append(" mutedState:");
            if (this.mMutedState == 0) {
                sb.append("none ");
            } else {
                if ((this.mMutedState & 1) != 0) {
                    sb.append("master ");
                }
                if ((this.mMutedState & 2) != 0) {
                    sb.append("streamVolume ");
                }
                if ((this.mMutedState & 4) != 0) {
                    sb.append("streamMute ");
                }
                if ((this.mMutedState & 8) != 0) {
                    sb.append("appOps ");
                }
                if ((this.mMutedState & 16) != 0) {
                    sb.append("clientVolume ");
                }
                if ((this.mMutedState & 32) != 0) {
                    sb.append("volumeShaper ");
                }
            }
            sb.append(" ").append(this.mFormatInfo);
        }
        return sb.toString();
    }

    static final class IPlayerShell implements android.os.IBinder.DeathRecipient {
        private volatile android.media.IPlayer mIPlayer;
        final android.media.AudioPlaybackConfiguration mMonitor;

        IPlayerShell(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, android.media.IPlayer iPlayer) {
            this.mMonitor = audioPlaybackConfiguration;
            this.mIPlayer = iPlayer;
        }

        synchronized void monitorDeath() {
            if (this.mIPlayer == null) {
                return;
            }
            try {
                this.mIPlayer.asBinder().linkToDeath(this, 0);
            } catch (android.os.RemoteException e) {
                if (this.mMonitor != null) {
                    android.util.Log.w(android.media.AudioPlaybackConfiguration.TAG, "Could not link to client death for piid=" + this.mMonitor.mPlayerIId, e);
                } else {
                    android.util.Log.w(android.media.AudioPlaybackConfiguration.TAG, "Could not link to client death", e);
                }
            }
        }

        android.media.IPlayer getIPlayer() {
            return this.mIPlayer;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            if (this.mMonitor != null) {
                this.mMonitor.playerDied();
            }
        }

        synchronized void release() {
            if (this.mIPlayer == null) {
                return;
            }
            this.mIPlayer.asBinder().unlinkToDeath(this, 0);
            this.mIPlayer = null;
            android.os.Binder.flushPendingCommands();
        }
    }

    public static final class FormatInfo implements android.os.Parcelable {
        final boolean mIsSpatialized;
        final int mNativeChannelMask;
        final int mSampleRate;
        static final android.media.AudioPlaybackConfiguration.FormatInfo DEFAULT = new android.media.AudioPlaybackConfiguration.FormatInfo(false, 0, 0);
        public static final android.os.Parcelable.Creator<android.media.AudioPlaybackConfiguration.FormatInfo> CREATOR = new android.os.Parcelable.Creator<android.media.AudioPlaybackConfiguration.FormatInfo>() { // from class: android.media.AudioPlaybackConfiguration.FormatInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.AudioPlaybackConfiguration.FormatInfo createFromParcel(android.os.Parcel parcel) {
                return new android.media.AudioPlaybackConfiguration.FormatInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.media.AudioPlaybackConfiguration.FormatInfo[] newArray(int i) {
                return new android.media.AudioPlaybackConfiguration.FormatInfo[i];
            }
        };

        public FormatInfo(boolean z, int i, int i2) {
            this.mIsSpatialized = z;
            this.mNativeChannelMask = i;
            this.mSampleRate = i2;
        }

        public java.lang.String toString() {
            return "FormatInfo{isSpatialized=" + this.mIsSpatialized + ", channelMask=0x" + java.lang.Integer.toHexString(this.mNativeChannelMask) + ", sampleRate=" + this.mSampleRate + '}';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.media.AudioPlaybackConfiguration.FormatInfo)) {
                return false;
            }
            android.media.AudioPlaybackConfiguration.FormatInfo formatInfo = (android.media.AudioPlaybackConfiguration.FormatInfo) obj;
            return this.mIsSpatialized == formatInfo.mIsSpatialized && this.mNativeChannelMask == formatInfo.mNativeChannelMask && this.mSampleRate == formatInfo.mSampleRate;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsSpatialized), java.lang.Integer.valueOf(this.mNativeChannelMask), java.lang.Integer.valueOf(this.mSampleRate));
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeBoolean(this.mIsSpatialized);
            parcel.writeInt(this.mNativeChannelMask);
            parcel.writeInt(this.mSampleRate);
        }

        private FormatInfo(android.os.Parcel parcel) {
            this(parcel.readBoolean(), parcel.readInt(), parcel.readInt());
        }
    }

    public static java.lang.String toLogFriendlyPlayerType(int i) {
        switch (i) {
            case -1:
                return "unknown";
            case 0:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
                return "unknown player type " + i + " - FIXME";
            case 1:
                return "android.media.AudioTrack";
            case 2:
                return "android.media.MediaPlayer";
            case 3:
                return "android.media.SoundPool";
            case 11:
                return "OpenSL ES AudioPlayer (Buffer Queue)";
            case 12:
                return "OpenSL ES AudioPlayer (URI/FD)";
            case 13:
                return "AAudio";
            case 14:
                return "hardware source";
            case 15:
                return "external proxy";
        }
    }

    public static java.lang.String toLogFriendlyPlayerState(int i) {
        switch (i) {
            case -1:
                return "unknown";
            case 0:
                return "released";
            case 1:
                return "idle";
            case 2:
                return "started";
            case 3:
                return "paused";
            case 4:
                return "stopped";
            case 5:
                return "device updated";
            case 6:
                return "port updated";
            case 7:
                return "muted updated";
            default:
                return "unknown player state - FIXME";
        }
    }
}
