package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class MediaQualityStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.MediaQualityStatus> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.MediaQualityStatus>() { // from class: android.telephony.ims.MediaQualityStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.MediaQualityStatus createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.MediaQualityStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.MediaQualityStatus[] newArray(int i) {
            return new android.telephony.ims.MediaQualityStatus[i];
        }
    };
    public static final int MEDIA_SESSION_TYPE_AUDIO = 1;
    public static final int MEDIA_SESSION_TYPE_VIDEO = 2;
    private final java.lang.String mImsCallSessionId;
    private final int mMediaSessionType;
    private final long mRtpInactivityTimeMillis;
    private final int mRtpJitterMillis;
    private final int mRtpPacketLossRate;
    private final int mTransportType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MediaSessionType {
    }

    public MediaQualityStatus(java.lang.String str, int i, int i2, int i3, int i4, long j) {
        this.mImsCallSessionId = str;
        this.mMediaSessionType = i;
        this.mTransportType = i2;
        this.mRtpPacketLossRate = i3;
        this.mRtpJitterMillis = i4;
        this.mRtpInactivityTimeMillis = j;
    }

    public java.lang.String getCallSessionId() {
        return this.mImsCallSessionId;
    }

    public int getMediaSessionType() {
        return this.mMediaSessionType;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getRtpPacketLossRate() {
        return this.mRtpPacketLossRate;
    }

    public int getRtpJitterMillis() {
        return this.mRtpJitterMillis;
    }

    public long getRtpInactivityMillis() {
        return this.mRtpInactivityTimeMillis;
    }

    private MediaQualityStatus(android.os.Parcel parcel) {
        this.mImsCallSessionId = parcel.readString();
        this.mMediaSessionType = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mRtpPacketLossRate = parcel.readInt();
        this.mRtpJitterMillis = parcel.readInt();
        this.mRtpInactivityTimeMillis = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mImsCallSessionId);
        parcel.writeInt(this.mMediaSessionType);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mRtpPacketLossRate);
        parcel.writeInt(this.mRtpJitterMillis);
        parcel.writeLong(this.mRtpInactivityTimeMillis);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.MediaQualityStatus mediaQualityStatus = (android.telephony.ims.MediaQualityStatus) obj;
        if (this.mImsCallSessionId != null && this.mImsCallSessionId.equals(mediaQualityStatus.mImsCallSessionId) && this.mMediaSessionType == mediaQualityStatus.mMediaSessionType && this.mTransportType == mediaQualityStatus.mTransportType && this.mRtpPacketLossRate == mediaQualityStatus.mRtpPacketLossRate && this.mRtpJitterMillis == mediaQualityStatus.mRtpJitterMillis && this.mRtpInactivityTimeMillis == mediaQualityStatus.mRtpInactivityTimeMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mImsCallSessionId, java.lang.Integer.valueOf(this.mMediaSessionType), java.lang.Integer.valueOf(this.mTransportType), java.lang.Integer.valueOf(this.mRtpPacketLossRate), java.lang.Integer.valueOf(this.mRtpJitterMillis), java.lang.Long.valueOf(this.mRtpInactivityTimeMillis));
    }

    public java.lang.String toString() {
        return "MediaThreshold{mImsCallSessionId=" + this.mImsCallSessionId + ", mMediaSessionType=" + this.mMediaSessionType + ", mTransportType=" + this.mTransportType + ", mRtpPacketLossRate=" + this.mRtpPacketLossRate + ", mRtpJitterMillis=" + this.mRtpJitterMillis + ", mRtpInactivityTimeMillis=" + this.mRtpInactivityTimeMillis + "}";
    }
}
