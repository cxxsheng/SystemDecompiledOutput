package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CallQuality implements android.os.Parcelable {
    public static final int CALL_QUALITY_BAD = 4;
    public static final int CALL_QUALITY_EXCELLENT = 0;
    public static final int CALL_QUALITY_FAIR = 2;
    public static final int CALL_QUALITY_GOOD = 1;
    public static final int CALL_QUALITY_NOT_AVAILABLE = 5;
    public static final int CALL_QUALITY_POOR = 3;
    public static final android.os.Parcelable.Creator<android.telephony.CallQuality> CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.CallQuality.1
        @Override // android.os.Parcelable.Creator
        public android.telephony.CallQuality createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CallQuality(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public android.telephony.CallQuality[] newArray(int i) {
            return new android.telephony.CallQuality[i];
        }
    };
    private int mAverageRelativeJitter;
    private int mAverageRoundTripTime;
    private int mCallDuration;
    private int mCodecType;
    private int mDownlinkCallQualityLevel;
    private long mMaxPlayoutDelayMillis;
    private int mMaxRelativeJitter;
    private long mMinPlayoutDelayMillis;
    private int mNumDroppedRtpPackets;
    private int mNumNoDataFrames;
    private int mNumRtpDuplicatePackets;
    private int mNumRtpPacketsNotReceived;
    private int mNumRtpPacketsReceived;
    private int mNumRtpPacketsTransmitted;
    private int mNumRtpPacketsTransmittedLost;
    private int mNumRtpSidPacketsReceived;
    private int mNumVoiceFrames;
    private boolean mRtpInactivityDetected;
    private boolean mRxSilenceDetected;
    private boolean mTxSilenceDetected;
    private int mUplinkCallQualityLevel;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallQualityLevel {
    }

    public CallQuality(android.os.Parcel parcel) {
        this.mDownlinkCallQualityLevel = parcel.readInt();
        this.mUplinkCallQualityLevel = parcel.readInt();
        this.mCallDuration = parcel.readInt();
        this.mNumRtpPacketsTransmitted = parcel.readInt();
        this.mNumRtpPacketsReceived = parcel.readInt();
        this.mNumRtpPacketsTransmittedLost = parcel.readInt();
        this.mNumRtpPacketsNotReceived = parcel.readInt();
        this.mAverageRelativeJitter = parcel.readInt();
        this.mMaxRelativeJitter = parcel.readInt();
        this.mAverageRoundTripTime = parcel.readInt();
        this.mCodecType = parcel.readInt();
        this.mRtpInactivityDetected = parcel.readBoolean();
        this.mRxSilenceDetected = parcel.readBoolean();
        this.mTxSilenceDetected = parcel.readBoolean();
        this.mNumVoiceFrames = parcel.readInt();
        this.mNumNoDataFrames = parcel.readInt();
        this.mNumDroppedRtpPackets = parcel.readInt();
        this.mMinPlayoutDelayMillis = parcel.readLong();
        this.mMaxPlayoutDelayMillis = parcel.readLong();
        this.mNumRtpSidPacketsReceived = parcel.readInt();
        this.mNumRtpDuplicatePackets = parcel.readInt();
    }

    public CallQuality() {
    }

    public CallQuality(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11) {
        this(i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, false, false, false);
    }

    public CallQuality(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, boolean z, boolean z2, boolean z3) {
        this.mDownlinkCallQualityLevel = i;
        this.mUplinkCallQualityLevel = i2;
        this.mCallDuration = i3;
        this.mNumRtpPacketsTransmitted = i4;
        this.mNumRtpPacketsReceived = i5;
        this.mNumRtpPacketsTransmittedLost = i6;
        this.mNumRtpPacketsNotReceived = i7;
        this.mAverageRelativeJitter = i8;
        this.mMaxRelativeJitter = i9;
        this.mAverageRoundTripTime = i10;
        this.mCodecType = i11;
        this.mRtpInactivityDetected = z;
        this.mRxSilenceDetected = z2;
        this.mTxSilenceDetected = z3;
    }

    public int getDownlinkCallQualityLevel() {
        return this.mDownlinkCallQualityLevel;
    }

    public int getUplinkCallQualityLevel() {
        return this.mUplinkCallQualityLevel;
    }

    public int getCallDuration() {
        return this.mCallDuration;
    }

    public int getNumRtpPacketsTransmitted() {
        return this.mNumRtpPacketsTransmitted;
    }

    public int getNumRtpPacketsReceived() {
        return this.mNumRtpPacketsReceived;
    }

    public int getNumRtpPacketsTransmittedLost() {
        return this.mNumRtpPacketsTransmittedLost;
    }

    public int getNumRtpPacketsNotReceived() {
        return this.mNumRtpPacketsNotReceived;
    }

    public int getAverageRelativeJitter() {
        return this.mAverageRelativeJitter;
    }

    public int getMaxRelativeJitter() {
        return this.mMaxRelativeJitter;
    }

    public int getAverageRoundTripTime() {
        return this.mAverageRoundTripTime;
    }

    public boolean isRtpInactivityDetected() {
        return this.mRtpInactivityDetected;
    }

    public boolean isIncomingSilenceDetectedAtCallSetup() {
        return this.mRxSilenceDetected;
    }

    public boolean isOutgoingSilenceDetectedAtCallSetup() {
        return this.mTxSilenceDetected;
    }

    public int getNumVoiceFrames() {
        return this.mNumVoiceFrames;
    }

    public int getNumNoDataFrames() {
        return this.mNumNoDataFrames;
    }

    public int getNumDroppedRtpPackets() {
        return this.mNumDroppedRtpPackets;
    }

    public long getMinPlayoutDelayMillis() {
        return this.mMinPlayoutDelayMillis;
    }

    public long getMaxPlayoutDelayMillis() {
        return this.mMaxPlayoutDelayMillis;
    }

    public int getNumRtpSidPacketsReceived() {
        return this.mNumRtpSidPacketsReceived;
    }

    public int getNumRtpDuplicatePackets() {
        return this.mNumRtpDuplicatePackets;
    }

    public int getCodecType() {
        return this.mCodecType;
    }

    public java.lang.String toString() {
        return "CallQuality: {downlinkCallQualityLevel=" + this.mDownlinkCallQualityLevel + " uplinkCallQualityLevel=" + this.mUplinkCallQualityLevel + " callDuration=" + this.mCallDuration + " numRtpPacketsTransmitted=" + this.mNumRtpPacketsTransmitted + " numRtpPacketsReceived=" + this.mNumRtpPacketsReceived + " numRtpPacketsTransmittedLost=" + this.mNumRtpPacketsTransmittedLost + " numRtpPacketsNotReceived=" + this.mNumRtpPacketsNotReceived + " averageRelativeJitter=" + this.mAverageRelativeJitter + " maxRelativeJitter=" + this.mMaxRelativeJitter + " averageRoundTripTime=" + this.mAverageRoundTripTime + " codecType=" + this.mCodecType + " rtpInactivityDetected=" + this.mRtpInactivityDetected + " txSilenceDetected=" + this.mTxSilenceDetected + " rxSilenceDetected=" + this.mRxSilenceDetected + " numVoiceFrames=" + this.mNumVoiceFrames + " numNoDataFrames=" + this.mNumNoDataFrames + " numDroppedRtpPackets=" + this.mNumDroppedRtpPackets + " minPlayoutDelayMillis=" + this.mMinPlayoutDelayMillis + " maxPlayoutDelayMillis=" + this.mMaxPlayoutDelayMillis + " numRtpSidPacketsReceived=" + this.mNumRtpSidPacketsReceived + " numRtpDuplicatePackets=" + this.mNumRtpDuplicatePackets + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDownlinkCallQualityLevel), java.lang.Integer.valueOf(this.mUplinkCallQualityLevel), java.lang.Integer.valueOf(this.mCallDuration), java.lang.Integer.valueOf(this.mNumRtpPacketsTransmitted), java.lang.Integer.valueOf(this.mNumRtpPacketsReceived), java.lang.Integer.valueOf(this.mNumRtpPacketsTransmittedLost), java.lang.Integer.valueOf(this.mNumRtpPacketsNotReceived), java.lang.Integer.valueOf(this.mAverageRelativeJitter), java.lang.Integer.valueOf(this.mMaxRelativeJitter), java.lang.Integer.valueOf(this.mAverageRoundTripTime), java.lang.Integer.valueOf(this.mCodecType), java.lang.Boolean.valueOf(this.mRtpInactivityDetected), java.lang.Boolean.valueOf(this.mRxSilenceDetected), java.lang.Boolean.valueOf(this.mTxSilenceDetected), java.lang.Integer.valueOf(this.mNumVoiceFrames), java.lang.Integer.valueOf(this.mNumNoDataFrames), java.lang.Integer.valueOf(this.mNumDroppedRtpPackets), java.lang.Long.valueOf(this.mMinPlayoutDelayMillis), java.lang.Long.valueOf(this.mMaxPlayoutDelayMillis), java.lang.Integer.valueOf(this.mNumRtpSidPacketsReceived), java.lang.Integer.valueOf(this.mNumRtpDuplicatePackets));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.CallQuality) || hashCode() != obj.hashCode()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.CallQuality callQuality = (android.telephony.CallQuality) obj;
        if (this.mDownlinkCallQualityLevel != callQuality.mDownlinkCallQualityLevel || this.mUplinkCallQualityLevel != callQuality.mUplinkCallQualityLevel || this.mCallDuration != callQuality.mCallDuration || this.mNumRtpPacketsTransmitted != callQuality.mNumRtpPacketsTransmitted || this.mNumRtpPacketsReceived != callQuality.mNumRtpPacketsReceived || this.mNumRtpPacketsTransmittedLost != callQuality.mNumRtpPacketsTransmittedLost || this.mNumRtpPacketsNotReceived != callQuality.mNumRtpPacketsNotReceived || this.mAverageRelativeJitter != callQuality.mAverageRelativeJitter || this.mMaxRelativeJitter != callQuality.mMaxRelativeJitter || this.mAverageRoundTripTime != callQuality.mAverageRoundTripTime || this.mCodecType != callQuality.mCodecType || this.mRtpInactivityDetected != callQuality.mRtpInactivityDetected || this.mRxSilenceDetected != callQuality.mRxSilenceDetected || this.mTxSilenceDetected != callQuality.mTxSilenceDetected || this.mNumVoiceFrames != callQuality.mNumVoiceFrames || this.mNumNoDataFrames != callQuality.mNumNoDataFrames || this.mNumDroppedRtpPackets != callQuality.mNumDroppedRtpPackets || this.mMinPlayoutDelayMillis != callQuality.mMinPlayoutDelayMillis || this.mMaxPlayoutDelayMillis != callQuality.mMaxPlayoutDelayMillis || this.mNumRtpSidPacketsReceived != callQuality.mNumRtpSidPacketsReceived || this.mNumRtpDuplicatePackets != callQuality.mNumRtpDuplicatePackets) {
            return false;
        }
        return true;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDownlinkCallQualityLevel);
        parcel.writeInt(this.mUplinkCallQualityLevel);
        parcel.writeInt(this.mCallDuration);
        parcel.writeInt(this.mNumRtpPacketsTransmitted);
        parcel.writeInt(this.mNumRtpPacketsReceived);
        parcel.writeInt(this.mNumRtpPacketsTransmittedLost);
        parcel.writeInt(this.mNumRtpPacketsNotReceived);
        parcel.writeInt(this.mAverageRelativeJitter);
        parcel.writeInt(this.mMaxRelativeJitter);
        parcel.writeInt(this.mAverageRoundTripTime);
        parcel.writeInt(this.mCodecType);
        parcel.writeBoolean(this.mRtpInactivityDetected);
        parcel.writeBoolean(this.mRxSilenceDetected);
        parcel.writeBoolean(this.mTxSilenceDetected);
        parcel.writeInt(this.mNumVoiceFrames);
        parcel.writeInt(this.mNumNoDataFrames);
        parcel.writeInt(this.mNumDroppedRtpPackets);
        parcel.writeLong(this.mMinPlayoutDelayMillis);
        parcel.writeLong(this.mMaxPlayoutDelayMillis);
        parcel.writeInt(this.mNumRtpSidPacketsReceived);
        parcel.writeInt(this.mNumRtpDuplicatePackets);
    }

    public static final class Builder {
        private int mAverageRelativeJitter;
        private int mAverageRoundTripTime;
        private int mCallDuration;
        private int mCodecType;
        private int mDownlinkCallQualityLevel;
        private long mMaxPlayoutDelayMillis;
        private int mMaxRelativeJitter;
        private long mMinPlayoutDelayMillis;
        private int mNumDroppedRtpPackets;
        private int mNumNoDataFrames;
        private int mNumRtpDuplicatePackets;
        private int mNumRtpPacketsNotReceived;
        private int mNumRtpPacketsReceived;
        private int mNumRtpPacketsTransmitted;
        private int mNumRtpPacketsTransmittedLost;
        private int mNumRtpSidPacketsReceived;
        private int mNumVoiceFrames;
        private boolean mRtpInactivityDetected;
        private boolean mRxSilenceDetected;
        private boolean mTxSilenceDetected;
        private int mUplinkCallQualityLevel;

        public android.telephony.CallQuality.Builder setDownlinkCallQualityLevel(int i) {
            this.mDownlinkCallQualityLevel = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setUplinkCallQualityLevel(int i) {
            this.mUplinkCallQualityLevel = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setCallDurationMillis(int i) {
            this.mCallDuration = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumRtpPacketsTransmitted(int i) {
            this.mNumRtpPacketsTransmitted = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumRtpPacketsReceived(int i) {
            this.mNumRtpPacketsReceived = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumRtpPacketsTransmittedLost(int i) {
            this.mNumRtpPacketsTransmittedLost = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumRtpPacketsNotReceived(int i) {
            this.mNumRtpPacketsNotReceived = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setAverageRelativeJitter(int i) {
            this.mAverageRelativeJitter = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setMaxRelativeJitter(int i) {
            this.mMaxRelativeJitter = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setAverageRoundTripTimeMillis(int i) {
            this.mAverageRoundTripTime = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setCodecType(int i) {
            this.mCodecType = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setRtpInactivityDetected(boolean z) {
            this.mRtpInactivityDetected = z;
            return this;
        }

        public android.telephony.CallQuality.Builder setIncomingSilenceDetectedAtCallSetup(boolean z) {
            this.mRxSilenceDetected = z;
            return this;
        }

        public android.telephony.CallQuality.Builder setOutgoingSilenceDetectedAtCallSetup(boolean z) {
            this.mTxSilenceDetected = z;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumVoiceFrames(int i) {
            this.mNumVoiceFrames = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumNoDataFrames(int i) {
            this.mNumNoDataFrames = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumDroppedRtpPackets(int i) {
            this.mNumDroppedRtpPackets = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setMinPlayoutDelayMillis(long j) {
            this.mMinPlayoutDelayMillis = j;
            return this;
        }

        public android.telephony.CallQuality.Builder setMaxPlayoutDelayMillis(long j) {
            this.mMaxPlayoutDelayMillis = j;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumRtpSidPacketsReceived(int i) {
            this.mNumRtpSidPacketsReceived = i;
            return this;
        }

        public android.telephony.CallQuality.Builder setNumRtpDuplicatePackets(int i) {
            this.mNumRtpDuplicatePackets = i;
            return this;
        }

        public android.telephony.CallQuality build() {
            android.telephony.CallQuality callQuality = new android.telephony.CallQuality();
            callQuality.mDownlinkCallQualityLevel = this.mDownlinkCallQualityLevel;
            callQuality.mUplinkCallQualityLevel = this.mUplinkCallQualityLevel;
            callQuality.mCallDuration = this.mCallDuration;
            callQuality.mNumRtpPacketsTransmitted = this.mNumRtpPacketsTransmitted;
            callQuality.mNumRtpPacketsReceived = this.mNumRtpPacketsReceived;
            callQuality.mNumRtpPacketsTransmittedLost = this.mNumRtpPacketsTransmittedLost;
            callQuality.mNumRtpPacketsNotReceived = this.mNumRtpPacketsNotReceived;
            callQuality.mAverageRelativeJitter = this.mAverageRelativeJitter;
            callQuality.mMaxRelativeJitter = this.mMaxRelativeJitter;
            callQuality.mAverageRoundTripTime = this.mAverageRoundTripTime;
            callQuality.mCodecType = this.mCodecType;
            callQuality.mRtpInactivityDetected = this.mRtpInactivityDetected;
            callQuality.mTxSilenceDetected = this.mTxSilenceDetected;
            callQuality.mRxSilenceDetected = this.mRxSilenceDetected;
            callQuality.mNumVoiceFrames = this.mNumVoiceFrames;
            callQuality.mNumNoDataFrames = this.mNumNoDataFrames;
            callQuality.mNumDroppedRtpPackets = this.mNumDroppedRtpPackets;
            callQuality.mMinPlayoutDelayMillis = this.mMinPlayoutDelayMillis;
            callQuality.mMaxPlayoutDelayMillis = this.mMaxPlayoutDelayMillis;
            callQuality.mNumRtpSidPacketsReceived = this.mNumRtpSidPacketsReceived;
            callQuality.mNumRtpDuplicatePackets = this.mNumRtpDuplicatePackets;
            return callQuality;
        }
    }
}
