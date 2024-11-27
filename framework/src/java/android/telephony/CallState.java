package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CallState implements android.os.Parcelable {
    public static final int CALL_CLASSIFICATION_BACKGROUND = 2;
    public static final int CALL_CLASSIFICATION_FOREGROUND = 1;
    public static final int CALL_CLASSIFICATION_MAX = 3;
    public static final int CALL_CLASSIFICATION_RINGING = 0;
    public static final int CALL_CLASSIFICATION_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.telephony.CallState> CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.CallState.1
        @Override // android.os.Parcelable.Creator
        public android.telephony.CallState createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CallState(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public android.telephony.CallState[] newArray(int i) {
            return new android.telephony.CallState[i];
        }
    };
    private final int mCallClassification;
    private final android.telephony.CallQuality mCallQuality;
    private java.lang.String mImsCallId;
    private int mImsCallServiceType;
    private int mImsCallType;
    private final int mNetworkType;
    private final int mPreciseCallState;

    private CallState(int i, int i2, android.telephony.CallQuality callQuality, int i3, java.lang.String str, int i4, int i5) {
        this.mPreciseCallState = i;
        this.mNetworkType = i2;
        this.mCallQuality = callQuality;
        this.mCallClassification = i3;
        this.mImsCallId = str;
        this.mImsCallServiceType = i4;
        this.mImsCallType = i5;
    }

    public java.lang.String toString() {
        return "mPreciseCallState=" + this.mPreciseCallState + " mNetworkType=" + this.mNetworkType + " mCallQuality=" + this.mCallQuality + " mCallClassification" + this.mCallClassification + " mImsCallId=" + this.mImsCallId + " mImsCallServiceType=" + this.mImsCallServiceType + " mImsCallType=" + this.mImsCallType;
    }

    private CallState(android.os.Parcel parcel) {
        this.mPreciseCallState = parcel.readInt();
        this.mNetworkType = parcel.readInt();
        this.mCallQuality = (android.telephony.CallQuality) parcel.readParcelable(android.telephony.CallQuality.class.getClassLoader(), android.telephony.CallQuality.class);
        this.mCallClassification = parcel.readInt();
        this.mImsCallId = parcel.readString();
        this.mImsCallServiceType = parcel.readInt();
        this.mImsCallType = parcel.readInt();
    }

    public int getCallState() {
        return this.mPreciseCallState;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public android.telephony.CallQuality getCallQuality() {
        return this.mCallQuality;
    }

    public int getCallClassification() {
        return this.mCallClassification;
    }

    public java.lang.String getImsCallSessionId() {
        return this.mImsCallId;
    }

    public int getImsCallServiceType() {
        return this.mImsCallServiceType;
    }

    public int getImsCallType() {
        return this.mImsCallType;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPreciseCallState), java.lang.Integer.valueOf(this.mNetworkType), this.mCallQuality, java.lang.Integer.valueOf(this.mCallClassification), this.mImsCallId, java.lang.Integer.valueOf(this.mImsCallServiceType), java.lang.Integer.valueOf(this.mImsCallType));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.CallState) || hashCode() != obj.hashCode()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.CallState callState = (android.telephony.CallState) obj;
        if (this.mPreciseCallState != callState.mPreciseCallState || this.mNetworkType != callState.mNetworkType || !java.util.Objects.equals(this.mCallQuality, callState.mCallQuality) || this.mCallClassification != callState.mCallClassification || !java.util.Objects.equals(this.mImsCallId, callState.mImsCallId) || this.mImsCallType != callState.mImsCallType || this.mImsCallServiceType != callState.mImsCallServiceType) {
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
        parcel.writeInt(this.mPreciseCallState);
        parcel.writeInt(this.mNetworkType);
        parcel.writeParcelable(this.mCallQuality, i);
        parcel.writeInt(this.mCallClassification);
        parcel.writeString(this.mImsCallId);
        parcel.writeInt(this.mImsCallServiceType);
        parcel.writeInt(this.mImsCallType);
    }

    public static final class Builder {
        private java.lang.String mImsCallId;
        private int mPreciseCallState;
        private int mNetworkType = 0;
        private android.telephony.CallQuality mCallQuality = null;
        private int mCallClassification = -1;
        private int mImsCallServiceType = 0;
        private int mImsCallType = 0;

        public Builder(int i) {
            this.mPreciseCallState = i;
        }

        public android.telephony.CallState.Builder setNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public android.telephony.CallState.Builder setCallQuality(android.telephony.CallQuality callQuality) {
            this.mCallQuality = callQuality;
            return this;
        }

        public android.telephony.CallState.Builder setCallClassification(int i) {
            this.mCallClassification = i;
            return this;
        }

        public android.telephony.CallState.Builder setImsCallSessionId(java.lang.String str) {
            this.mImsCallId = str;
            return this;
        }

        public android.telephony.CallState.Builder setImsCallServiceType(int i) {
            this.mImsCallServiceType = i;
            return this;
        }

        public android.telephony.CallState.Builder setImsCallType(int i) {
            this.mImsCallType = i;
            return this;
        }

        public android.telephony.CallState build() {
            return new android.telephony.CallState(this.mPreciseCallState, this.mNetworkType, this.mCallQuality, this.mCallClassification, this.mImsCallId, this.mImsCallServiceType, this.mImsCallType);
        }
    }
}
