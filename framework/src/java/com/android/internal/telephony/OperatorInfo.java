package com.android.internal.telephony;

/* loaded from: classes5.dex */
public class OperatorInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.telephony.OperatorInfo> CREATOR = new android.os.Parcelable.Creator<com.android.internal.telephony.OperatorInfo>() { // from class: com.android.internal.telephony.OperatorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.OperatorInfo createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.telephony.OperatorInfo(parcel.readString(), parcel.readString(), parcel.readString(), (com.android.internal.telephony.OperatorInfo.State) parcel.readSerializable(com.android.internal.telephony.OperatorInfo.State.class.getClassLoader(), com.android.internal.telephony.OperatorInfo.State.class), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.telephony.OperatorInfo[] newArray(int i) {
            return new com.android.internal.telephony.OperatorInfo[i];
        }
    };
    private java.lang.String mOperatorAlphaLong;
    private java.lang.String mOperatorAlphaShort;
    private java.lang.String mOperatorNumeric;
    private int mRan;
    private com.android.internal.telephony.OperatorInfo.State mState;

    public enum State {
        UNKNOWN,
        AVAILABLE,
        CURRENT,
        FORBIDDEN
    }

    public java.lang.String getOperatorAlphaLong() {
        return this.mOperatorAlphaLong;
    }

    public java.lang.String getOperatorAlphaShort() {
        return this.mOperatorAlphaShort;
    }

    public java.lang.String getOperatorNumeric() {
        return this.mOperatorNumeric;
    }

    public com.android.internal.telephony.OperatorInfo.State getState() {
        return this.mState;
    }

    public int getRan() {
        return this.mRan;
    }

    OperatorInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.OperatorInfo.State state) {
        this.mState = com.android.internal.telephony.OperatorInfo.State.UNKNOWN;
        this.mRan = 0;
        this.mOperatorAlphaLong = str;
        this.mOperatorAlphaShort = str2;
        this.mOperatorNumeric = str3;
        this.mState = state;
    }

    OperatorInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, com.android.internal.telephony.OperatorInfo.State state, int i) {
        this(str, str2, str3, state);
        this.mRan = i;
    }

    public OperatorInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this(str, str2, str3, rilStateToState(str4));
    }

    public OperatorInfo(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        this(str, str2, str3);
        this.mRan = i;
    }

    public OperatorInfo(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this(str, str2, str3, com.android.internal.telephony.OperatorInfo.State.UNKNOWN);
    }

    private static com.android.internal.telephony.OperatorInfo.State rilStateToState(java.lang.String str) {
        if (str.equals("unknown")) {
            return com.android.internal.telephony.OperatorInfo.State.UNKNOWN;
        }
        if (str.equals("available")) {
            return com.android.internal.telephony.OperatorInfo.State.AVAILABLE;
        }
        if (str.equals(android.provider.Telephony.Carriers.CURRENT)) {
            return com.android.internal.telephony.OperatorInfo.State.CURRENT;
        }
        if (str.equals("forbidden")) {
            return com.android.internal.telephony.OperatorInfo.State.FORBIDDEN;
        }
        throw new java.lang.RuntimeException("RIL impl error: Invalid network state '" + str + "'");
    }

    public java.lang.String toString() {
        return "OperatorInfo " + this.mOperatorAlphaLong + "/" + this.mOperatorAlphaShort + "/" + this.mOperatorNumeric + "/" + this.mState + "/" + this.mRan;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mOperatorAlphaLong);
        parcel.writeString(this.mOperatorAlphaShort);
        parcel.writeString(this.mOperatorNumeric);
        parcel.writeSerializable(this.mState);
        parcel.writeInt(this.mRan);
    }
}
