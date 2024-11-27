package android.telephony;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes3.dex */
public final class CallAttributes implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CallAttributes> CREATOR = new android.os.Parcelable.Creator() { // from class: android.telephony.CallAttributes.1
        @Override // android.os.Parcelable.Creator
        public android.telephony.CallAttributes createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CallAttributes(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public android.telephony.CallAttributes[] newArray(int i) {
            return new android.telephony.CallAttributes[i];
        }
    };
    private android.telephony.CallQuality mCallQuality;
    private int mNetworkType;
    private android.telephony.PreciseCallState mPreciseCallState;

    public CallAttributes(android.telephony.PreciseCallState preciseCallState, int i, android.telephony.CallQuality callQuality) {
        this.mPreciseCallState = preciseCallState;
        this.mNetworkType = i;
        this.mCallQuality = callQuality;
    }

    public java.lang.String toString() {
        return "mPreciseCallState=" + this.mPreciseCallState + " mNetworkType=" + this.mNetworkType + " mCallQuality=" + this.mCallQuality;
    }

    private CallAttributes(android.os.Parcel parcel) {
        this.mPreciseCallState = (android.telephony.PreciseCallState) parcel.readParcelable(android.telephony.PreciseCallState.class.getClassLoader(), android.telephony.PreciseCallState.class);
        this.mNetworkType = parcel.readInt();
        this.mCallQuality = (android.telephony.CallQuality) parcel.readParcelable(android.telephony.CallQuality.class.getClassLoader(), android.telephony.CallQuality.class);
    }

    public android.telephony.PreciseCallState getPreciseCallState() {
        return this.mPreciseCallState;
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public android.telephony.CallQuality getCallQuality() {
        return this.mCallQuality;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPreciseCallState, java.lang.Integer.valueOf(this.mNetworkType), this.mCallQuality);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.CallAttributes) || hashCode() != obj.hashCode()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.CallAttributes callAttributes = (android.telephony.CallAttributes) obj;
        if (!java.util.Objects.equals(this.mPreciseCallState, callAttributes.mPreciseCallState) || this.mNetworkType != callAttributes.mNetworkType || !java.util.Objects.equals(this.mCallQuality, callAttributes.mCallQuality)) {
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
        parcel.writeParcelable(this.mPreciseCallState, i);
        parcel.writeInt(this.mNetworkType);
        parcel.writeParcelable(this.mCallQuality, i);
    }
}
