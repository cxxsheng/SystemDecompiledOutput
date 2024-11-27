package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class VolumeInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.VolumeInfo> CREATOR = new android.os.Parcelable.Creator<android.media.VolumeInfo>() { // from class: android.media.VolumeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.VolumeInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.VolumeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.VolumeInfo[] newArray(int i) {
            return new android.media.VolumeInfo[i];
        }
    };
    public static final int INDEX_NOT_SET = -100;
    private static final java.lang.String TAG = "VolumeInfo";
    private static android.media.VolumeInfo sDefaultVolumeInfo;
    private static android.media.IAudioService sService;
    private final boolean mHasMuteCommand;
    private final boolean mIsMuted;
    private final int mMaxVolIndex;
    private final int mMinVolIndex;
    private final int mStreamType;
    private final boolean mUsesStreamType;
    private final android.media.audiopolicy.AudioVolumeGroup mVolGroup;
    private final int mVolIndex;

    private VolumeInfo(boolean z, boolean z2, boolean z3, int i, int i2, int i3, android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup, int i4) {
        this.mUsesStreamType = z;
        this.mHasMuteCommand = z2;
        this.mIsMuted = z3;
        this.mVolIndex = i;
        this.mMinVolIndex = i2;
        this.mMaxVolIndex = i3;
        this.mVolGroup = audioVolumeGroup;
        this.mStreamType = i4;
    }

    public boolean hasStreamType() {
        return this.mUsesStreamType;
    }

    public int getStreamType() {
        if (!this.mUsesStreamType) {
            throw new java.lang.IllegalStateException("VolumeInfo doesn't use stream types");
        }
        return this.mStreamType;
    }

    public boolean hasVolumeGroup() {
        return !this.mUsesStreamType;
    }

    public android.media.audiopolicy.AudioVolumeGroup getVolumeGroup() {
        if (this.mUsesStreamType) {
            throw new java.lang.IllegalStateException("VolumeInfo doesn't use AudioVolumeGroup");
        }
        return this.mVolGroup;
    }

    public boolean hasMuteCommand() {
        return this.mHasMuteCommand;
    }

    public boolean isMuted() {
        return this.mIsMuted;
    }

    public int getVolumeIndex() {
        return this.mVolIndex;
    }

    public int getMinVolumeIndex() {
        return this.mMinVolIndex;
    }

    public int getMaxVolumeIndex() {
        return this.mMaxVolIndex;
    }

    public static android.media.VolumeInfo getDefaultVolumeInfo() {
        if (sService == null) {
            sService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        }
        if (sDefaultVolumeInfo == null) {
            try {
                sDefaultVolumeInfo = sService.getDefaultVolumeInfo();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling getDefaultVolumeInfo", e);
                return new android.media.VolumeInfo.Builder(3).build();
            }
        }
        return sDefaultVolumeInfo;
    }

    public static final class Builder {
        private boolean mHasMuteCommand;
        private boolean mIsMuted;
        private int mMaxVolIndex;
        private int mMinVolIndex;
        private int mStreamType;
        private boolean mUsesStreamType;
        private android.media.audiopolicy.AudioVolumeGroup mVolGroup;
        private int mVolIndex;

        public Builder(int i) {
            this.mUsesStreamType = true;
            this.mStreamType = 3;
            this.mHasMuteCommand = false;
            this.mIsMuted = false;
            this.mVolIndex = -100;
            this.mMinVolIndex = -100;
            this.mMaxVolIndex = -100;
            if (!android.media.AudioManager.isPublicStreamType(i)) {
                throw new java.lang.IllegalArgumentException("Not a valid public stream type " + i);
            }
            this.mUsesStreamType = true;
            this.mStreamType = i;
        }

        public Builder(android.media.audiopolicy.AudioVolumeGroup audioVolumeGroup) {
            this.mUsesStreamType = true;
            this.mStreamType = 3;
            this.mHasMuteCommand = false;
            this.mIsMuted = false;
            this.mVolIndex = -100;
            this.mMinVolIndex = -100;
            this.mMaxVolIndex = -100;
            java.util.Objects.requireNonNull(audioVolumeGroup);
            this.mUsesStreamType = false;
            this.mStreamType = Integer.MIN_VALUE;
            this.mVolGroup = audioVolumeGroup;
        }

        public Builder(android.media.VolumeInfo volumeInfo) {
            this.mUsesStreamType = true;
            this.mStreamType = 3;
            this.mHasMuteCommand = false;
            this.mIsMuted = false;
            this.mVolIndex = -100;
            this.mMinVolIndex = -100;
            this.mMaxVolIndex = -100;
            java.util.Objects.requireNonNull(volumeInfo);
            this.mUsesStreamType = volumeInfo.mUsesStreamType;
            this.mStreamType = volumeInfo.mStreamType;
            this.mHasMuteCommand = volumeInfo.mHasMuteCommand;
            this.mIsMuted = volumeInfo.mIsMuted;
            this.mVolIndex = volumeInfo.mVolIndex;
            this.mMinVolIndex = volumeInfo.mMinVolIndex;
            this.mMaxVolIndex = volumeInfo.mMaxVolIndex;
            this.mVolGroup = volumeInfo.mVolGroup;
        }

        public android.media.VolumeInfo.Builder setMuted(boolean z) {
            this.mHasMuteCommand = true;
            this.mIsMuted = z;
            return this;
        }

        public android.media.VolumeInfo.Builder setVolumeIndex(int i) {
            if (i != -100 && i < 0) {
                throw new java.lang.IllegalArgumentException("Volume index cannot be negative");
            }
            this.mVolIndex = i;
            return this;
        }

        public android.media.VolumeInfo.Builder setMinVolumeIndex(int i) {
            if (i != -100 && i < 0) {
                throw new java.lang.IllegalArgumentException("Min volume index cannot be negative");
            }
            this.mMinVolIndex = i;
            return this;
        }

        public android.media.VolumeInfo.Builder setMaxVolumeIndex(int i) {
            if (i != -100 && i < 0) {
                throw new java.lang.IllegalArgumentException("Max volume index cannot be negative");
            }
            this.mMaxVolIndex = i;
            return this;
        }

        public android.media.VolumeInfo build() {
            if (this.mVolIndex != -100) {
                if (this.mMinVolIndex != -100 && this.mVolIndex < this.mMinVolIndex) {
                    throw new java.lang.IllegalArgumentException("Volume index:" + this.mVolIndex + " lower than min index:" + this.mMinVolIndex);
                }
                if (this.mMaxVolIndex != -100 && this.mVolIndex > this.mMaxVolIndex) {
                    throw new java.lang.IllegalArgumentException("Volume index:" + this.mVolIndex + " greater than max index:" + this.mMaxVolIndex);
                }
            }
            if (this.mMinVolIndex != -100 && this.mMaxVolIndex != -100 && this.mMinVolIndex > this.mMaxVolIndex) {
                throw new java.lang.IllegalArgumentException("Min volume index:" + this.mMinVolIndex + " greater than max index:" + this.mMaxVolIndex);
            }
            return new android.media.VolumeInfo(this.mUsesStreamType, this.mHasMuteCommand, this.mIsMuted, this.mVolIndex, this.mMinVolIndex, this.mMaxVolIndex, this.mVolGroup, this.mStreamType);
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mUsesStreamType), java.lang.Boolean.valueOf(this.mHasMuteCommand), java.lang.Integer.valueOf(this.mStreamType), java.lang.Boolean.valueOf(this.mIsMuted), java.lang.Integer.valueOf(this.mVolIndex), java.lang.Integer.valueOf(this.mMinVolIndex), java.lang.Integer.valueOf(this.mMaxVolIndex), this.mVolGroup);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.VolumeInfo volumeInfo = (android.media.VolumeInfo) obj;
        if (this.mUsesStreamType == volumeInfo.mUsesStreamType && this.mStreamType == volumeInfo.mStreamType && this.mHasMuteCommand == volumeInfo.mHasMuteCommand && this.mIsMuted == volumeInfo.mIsMuted && this.mVolIndex == volumeInfo.mVolIndex && this.mMinVolIndex == volumeInfo.mMinVolIndex && this.mMaxVolIndex == volumeInfo.mMaxVolIndex && java.util.Objects.equals(this.mVolGroup, volumeInfo.mVolGroup)) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        return new java.lang.String("VolumeInfo:" + (this.mUsesStreamType ? " streamType:" + this.mStreamType : " volGroup:" + this.mVolGroup) + (this.mHasMuteCommand ? " muted:" + this.mIsMuted : "[no mute cmd]") + (this.mVolIndex != -100 ? " volIndex:" + this.mVolIndex : "") + (this.mMinVolIndex != -100 ? " min:" + this.mMinVolIndex : "") + (this.mMaxVolIndex != -100 ? " max:" + this.mMaxVolIndex : ""));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mUsesStreamType);
        parcel.writeInt(this.mStreamType);
        parcel.writeBoolean(this.mHasMuteCommand);
        parcel.writeBoolean(this.mIsMuted);
        parcel.writeInt(this.mVolIndex);
        parcel.writeInt(this.mMinVolIndex);
        parcel.writeInt(this.mMaxVolIndex);
        if (!this.mUsesStreamType) {
            this.mVolGroup.writeToParcel(parcel, 0);
        }
    }

    private VolumeInfo(android.os.Parcel parcel) {
        this.mUsesStreamType = parcel.readBoolean();
        this.mStreamType = parcel.readInt();
        this.mHasMuteCommand = parcel.readBoolean();
        this.mIsMuted = parcel.readBoolean();
        this.mVolIndex = parcel.readInt();
        this.mMinVolIndex = parcel.readInt();
        this.mMaxVolIndex = parcel.readInt();
        if (!this.mUsesStreamType) {
            this.mVolGroup = android.media.audiopolicy.AudioVolumeGroup.CREATOR.createFromParcel(parcel);
        } else {
            this.mVolGroup = null;
        }
    }
}
