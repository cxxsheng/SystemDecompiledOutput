package android.telephony.ims.feature;

/* loaded from: classes3.dex */
public final class ConnectionFailureInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ims.feature.ConnectionFailureInfo> CREATOR;
    public static final int REASON_ACCESS_DENIED = 1;
    public static final int REASON_NAS_FAILURE = 2;
    public static final int REASON_NONE = 0;
    public static final int REASON_NO_SERVICE = 7;
    public static final int REASON_PDN_NOT_AVAILABLE = 8;
    public static final int REASON_RACH_FAILURE = 3;
    public static final int REASON_RF_BUSY = 9;
    public static final int REASON_RLC_FAILURE = 4;
    public static final int REASON_RRC_REJECT = 5;
    public static final int REASON_RRC_TIMEOUT = 6;
    public static final int REASON_UNSPECIFIED = 65535;
    private static final android.util.SparseArray<java.lang.String> sReasonMap = new android.util.SparseArray<>();
    private final int mCauseCode;
    private final int mReason;
    private final int mWaitTimeMillis;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FailureReason {
    }

    static {
        sReasonMap.set(0, android.security.keystore.KeyProperties.DIGEST_NONE);
        sReasonMap.set(1, "ACCESS_DENIED");
        sReasonMap.set(2, "NAS_FAILURE");
        sReasonMap.set(3, "RACH_FAILURE");
        sReasonMap.set(4, "RLC_FAILURE");
        sReasonMap.set(5, "RRC_REJECT");
        sReasonMap.set(6, "RRC_TIMEOUT");
        sReasonMap.set(7, "NO_SERVICE");
        sReasonMap.set(8, "PDN_NOT_AVAILABLE");
        sReasonMap.set(9, "RF_BUSY");
        sReasonMap.set(65535, "UNSPECIFIED");
        CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.feature.ConnectionFailureInfo>() { // from class: android.telephony.ims.feature.ConnectionFailureInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.ims.feature.ConnectionFailureInfo createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.ims.feature.ConnectionFailureInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.ims.feature.ConnectionFailureInfo[] newArray(int i) {
                return new android.telephony.ims.feature.ConnectionFailureInfo[i];
            }
        };
    }

    private ConnectionFailureInfo(android.os.Parcel parcel) {
        this.mReason = parcel.readInt();
        this.mCauseCode = parcel.readInt();
        this.mWaitTimeMillis = parcel.readInt();
    }

    public ConnectionFailureInfo(int i, int i2, int i3) {
        this.mReason = i;
        this.mCauseCode = i2;
        this.mWaitTimeMillis = i3;
    }

    public int getReason() {
        return this.mReason;
    }

    public int getCauseCode() {
        return this.mCauseCode;
    }

    public int getWaitTimeMillis() {
        return this.mWaitTimeMillis;
    }

    public java.lang.String toString() {
        return "ConnectionFailureInfo :: {" + this.mReason + " : " + sReasonMap.get(this.mReason, "UNKNOWN") + ", " + this.mCauseCode + ", " + this.mWaitTimeMillis + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mReason);
        parcel.writeInt(this.mCauseCode);
        parcel.writeInt(this.mWaitTimeMillis);
    }
}
