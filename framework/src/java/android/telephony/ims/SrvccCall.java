package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SrvccCall implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.SrvccCall> CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.SrvccCall>() { // from class: android.telephony.ims.SrvccCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SrvccCall createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.ims.SrvccCall(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ims.SrvccCall[] newArray(int i) {
            return new android.telephony.ims.SrvccCall[i];
        }
    };
    private static final java.lang.String TAG = "SrvccCall";
    private java.lang.String mCallId;
    private int mCallState;
    private android.telephony.ims.ImsCallProfile mImsCallProfile;

    private SrvccCall(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public SrvccCall(java.lang.String str, int i, android.telephony.ims.ImsCallProfile imsCallProfile) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("callId is null");
        }
        if (imsCallProfile == null) {
            throw new java.lang.IllegalArgumentException("imsCallProfile is null");
        }
        this.mCallId = str;
        this.mCallState = i;
        this.mImsCallProfile = imsCallProfile;
    }

    public android.telephony.ims.ImsCallProfile getImsCallProfile() {
        return this.mImsCallProfile;
    }

    public java.lang.String getCallId() {
        return this.mCallId;
    }

    public int getPreciseCallState() {
        return this.mCallState;
    }

    public java.lang.String toString() {
        return "{ callId=" + this.mCallId + ", callState=" + this.mCallState + ", imsCallProfile=" + this.mImsCallProfile + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ims.SrvccCall srvccCall = (android.telephony.ims.SrvccCall) obj;
        if (this.mImsCallProfile.equals(srvccCall.mImsCallProfile) && this.mCallId.equals(srvccCall.mCallId) && this.mCallState == srvccCall.mCallState) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Objects.hash(this.mImsCallProfile, this.mCallId) * 31) + this.mCallState;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCallId);
        parcel.writeInt(this.mCallState);
        parcel.writeParcelable(this.mImsCallProfile, 0);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        this.mCallId = parcel.readString();
        this.mCallState = parcel.readInt();
        this.mImsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readParcelable(android.telephony.ims.ImsCallProfile.class.getClassLoader(), android.telephony.ims.ImsCallProfile.class);
    }
}
