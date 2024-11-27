package android.telephony;

/* loaded from: classes3.dex */
public final class TelephonyDisplayInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.TelephonyDisplayInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.TelephonyDisplayInfo>() { // from class: android.telephony.TelephonyDisplayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.TelephonyDisplayInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.TelephonyDisplayInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.TelephonyDisplayInfo[] newArray(int i) {
            return new android.telephony.TelephonyDisplayInfo[i];
        }
    };
    public static final int OVERRIDE_NETWORK_TYPE_LTE_ADVANCED_PRO = 2;
    public static final int OVERRIDE_NETWORK_TYPE_LTE_CA = 1;
    public static final int OVERRIDE_NETWORK_TYPE_NONE = 0;
    public static final int OVERRIDE_NETWORK_TYPE_NR_ADVANCED = 5;
    public static final int OVERRIDE_NETWORK_TYPE_NR_NSA = 3;

    @java.lang.Deprecated
    public static final int OVERRIDE_NETWORK_TYPE_NR_NSA_MMWAVE = 4;
    private final boolean mIsRoaming;
    private final int mNetworkType;
    private final int mOverrideNetworkType;

    @java.lang.Deprecated
    public TelephonyDisplayInfo(int i, int i2) {
        this(i, i2, false);
    }

    public TelephonyDisplayInfo(int i, int i2, boolean z) {
        this.mNetworkType = i;
        this.mOverrideNetworkType = i2;
        this.mIsRoaming = z;
    }

    public TelephonyDisplayInfo(android.os.Parcel parcel) {
        this.mNetworkType = parcel.readInt();
        this.mOverrideNetworkType = parcel.readInt();
        this.mIsRoaming = parcel.readBoolean();
    }

    public int getNetworkType() {
        return this.mNetworkType;
    }

    public int getOverrideNetworkType() {
        return this.mOverrideNetworkType;
    }

    public boolean isRoaming() {
        return this.mIsRoaming;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mNetworkType);
        parcel.writeInt(this.mOverrideNetworkType);
        parcel.writeBoolean(this.mIsRoaming);
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
        android.telephony.TelephonyDisplayInfo telephonyDisplayInfo = (android.telephony.TelephonyDisplayInfo) obj;
        if (this.mNetworkType == telephonyDisplayInfo.mNetworkType && this.mOverrideNetworkType == telephonyDisplayInfo.mOverrideNetworkType && this.mIsRoaming == telephonyDisplayInfo.mIsRoaming) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mNetworkType), java.lang.Integer.valueOf(this.mOverrideNetworkType), java.lang.Boolean.valueOf(this.mIsRoaming));
    }

    public static java.lang.String overrideNetworkTypeToString(int i) {
        switch (i) {
            case 0:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 1:
                return "LTE_CA";
            case 2:
                return "LTE_ADV_PRO";
            case 3:
                return com.android.internal.telephony.DctConstants.RAT_NAME_NR_NSA;
            case 4:
                return com.android.internal.telephony.DctConstants.RAT_NAME_NR_NSA_MMWAVE;
            case 5:
                return "NR_ADVANCED";
            default:
                return "UNKNOWN";
        }
    }

    public java.lang.String toString() {
        return "TelephonyDisplayInfo {network=" + android.telephony.TelephonyManager.getNetworkTypeName(this.mNetworkType) + ", overrideNetwork=" + overrideNetworkTypeToString(this.mOverrideNetworkType) + ", isRoaming=" + this.mIsRoaming + "}";
    }
}
