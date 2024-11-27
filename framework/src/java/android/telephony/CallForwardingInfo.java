package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class CallForwardingInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CallForwardingInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.CallForwardingInfo>() { // from class: android.telephony.CallForwardingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CallForwardingInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CallForwardingInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CallForwardingInfo[] newArray(int i) {
            return new android.telephony.CallForwardingInfo[i];
        }
    };
    public static final int REASON_ALL = 4;
    public static final int REASON_ALL_CONDITIONAL = 5;
    public static final int REASON_BUSY = 1;
    public static final int REASON_NOT_REACHABLE = 3;
    public static final int REASON_NO_REPLY = 2;
    public static final int REASON_UNCONDITIONAL = 0;
    private static final java.lang.String TAG = "CallForwardingInfo";
    private boolean mEnabled;
    private java.lang.String mNumber;
    private int mReason;
    private int mTimeSeconds;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallForwardingReason {
    }

    public CallForwardingInfo(boolean z, int i, java.lang.String str, int i2) {
        this.mEnabled = z;
        this.mReason = i;
        this.mNumber = str;
        this.mTimeSeconds = i2;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public int getReason() {
        return this.mReason;
    }

    public java.lang.String getNumber() {
        return this.mNumber;
    }

    public int getTimeoutSeconds() {
        return this.mTimeSeconds;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mNumber);
        parcel.writeBoolean(this.mEnabled);
        parcel.writeInt(this.mReason);
        parcel.writeInt(this.mTimeSeconds);
    }

    private CallForwardingInfo(android.os.Parcel parcel) {
        this.mNumber = parcel.readString();
        this.mEnabled = parcel.readBoolean();
        this.mReason = parcel.readInt();
        this.mTimeSeconds = parcel.readInt();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.CallForwardingInfo)) {
            return false;
        }
        android.telephony.CallForwardingInfo callForwardingInfo = (android.telephony.CallForwardingInfo) obj;
        return this.mEnabled == callForwardingInfo.mEnabled && this.mNumber == callForwardingInfo.mNumber && this.mReason == callForwardingInfo.mReason && this.mTimeSeconds == callForwardingInfo.mTimeSeconds;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mEnabled), this.mNumber, java.lang.Integer.valueOf(this.mReason), java.lang.Integer.valueOf(this.mTimeSeconds));
    }

    public java.lang.String toString() {
        return "[CallForwardingInfo: enabled=" + this.mEnabled + ", reason= " + this.mReason + ", timeSec= " + this.mTimeSeconds + " seconds, number=" + com.android.telephony.Rlog.pii(TAG, this.mNumber) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
