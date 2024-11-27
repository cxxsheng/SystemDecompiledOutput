package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class AudioFocusInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.AudioFocusInfo> CREATOR = new android.os.Parcelable.Creator<android.media.AudioFocusInfo>() { // from class: android.media.AudioFocusInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioFocusInfo createFromParcel(android.os.Parcel parcel) {
            android.media.AudioFocusInfo audioFocusInfo = new android.media.AudioFocusInfo(android.media.AudioAttributes.CREATOR.createFromParcel(parcel), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            audioFocusInfo.setGen(parcel.readLong());
            return audioFocusInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.AudioFocusInfo[] newArray(int i) {
            return new android.media.AudioFocusInfo[i];
        }
    };
    private final android.media.AudioAttributes mAttributes;
    private final java.lang.String mClientId;
    private final int mClientUid;
    private int mFlags;
    private int mGainRequest;
    private long mGenCount = -1;
    private int mLossReceived;
    private final java.lang.String mPackageName;
    private final int mSdkTarget;

    public AudioFocusInfo(android.media.AudioAttributes audioAttributes, int i, java.lang.String str, java.lang.String str2, int i2, int i3, int i4, int i5) {
        this.mAttributes = audioAttributes == null ? new android.media.AudioAttributes.Builder().build() : audioAttributes;
        this.mClientUid = i;
        this.mClientId = str == null ? "" : str;
        this.mPackageName = str2 == null ? "" : str2;
        this.mGainRequest = i2;
        this.mLossReceived = i3;
        this.mFlags = i4;
        this.mSdkTarget = i5;
    }

    public void setGen(long j) {
        this.mGenCount = j;
    }

    public long getGen() {
        return this.mGenCount;
    }

    public android.media.AudioAttributes getAttributes() {
        return this.mAttributes;
    }

    public int getClientUid() {
        return this.mClientUid;
    }

    public java.lang.String getClientId() {
        return this.mClientId;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getGainRequest() {
        return this.mGainRequest;
    }

    public int getLossReceived() {
        return this.mLossReceived;
    }

    public int getSdkTarget() {
        return this.mSdkTarget;
    }

    public void clearLossReceived() {
        this.mLossReceived = 0;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mAttributes.writeToParcel(parcel, i);
        parcel.writeInt(this.mClientUid);
        parcel.writeString(this.mClientId);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mGainRequest);
        parcel.writeInt(this.mLossReceived);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mSdkTarget);
        parcel.writeLong(this.mGenCount);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mAttributes, java.lang.Integer.valueOf(this.mClientUid), this.mClientId, this.mPackageName, java.lang.Integer.valueOf(this.mGainRequest), java.lang.Integer.valueOf(this.mFlags));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.media.AudioFocusInfo audioFocusInfo = (android.media.AudioFocusInfo) obj;
        if (this.mAttributes.equals(audioFocusInfo.mAttributes) && this.mClientUid == audioFocusInfo.mClientUid && this.mClientId.equals(audioFocusInfo.mClientId) && this.mPackageName.equals(audioFocusInfo.mPackageName) && this.mGainRequest == audioFocusInfo.mGainRequest && this.mLossReceived == audioFocusInfo.mLossReceived && this.mFlags == audioFocusInfo.mFlags && this.mSdkTarget == audioFocusInfo.mSdkTarget) {
            return true;
        }
        return false;
    }
}
