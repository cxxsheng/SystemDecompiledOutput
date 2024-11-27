package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class LteVopsSupportInfo extends android.telephony.VopsSupportInfo {
    public static final android.os.Parcelable.Creator<android.telephony.LteVopsSupportInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.LteVopsSupportInfo>() { // from class: android.telephony.LteVopsSupportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.LteVopsSupportInfo createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return new android.telephony.LteVopsSupportInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.LteVopsSupportInfo[] newArray(int i) {
            return new android.telephony.LteVopsSupportInfo[i];
        }
    };

    @java.lang.Deprecated
    public static final int LTE_STATUS_NOT_AVAILABLE = 1;
    public static final int LTE_STATUS_NOT_SUPPORTED = 3;
    public static final int LTE_STATUS_SUPPORTED = 2;
    private final int mEmcBearerSupport;
    private final int mVopsSupport;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LteVopsStatus {
    }

    public LteVopsSupportInfo(int i, int i2) {
        this.mVopsSupport = i;
        this.mEmcBearerSupport = i2;
    }

    public int getVopsSupport() {
        return this.mVopsSupport;
    }

    public int getEmcBearerSupport() {
        return this.mEmcBearerSupport;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isVopsSupported() {
        return this.mVopsSupport == 2;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceSupported() {
        return this.mEmcBearerSupport == 2;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceFallbackSupported() {
        return false;
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 3);
        parcel.writeInt(this.mVopsSupport);
        parcel.writeInt(this.mEmcBearerSupport);
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.LteVopsSupportInfo)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.LteVopsSupportInfo lteVopsSupportInfo = (android.telephony.LteVopsSupportInfo) obj;
        if (this.mVopsSupport != lteVopsSupportInfo.mVopsSupport || this.mEmcBearerSupport != lteVopsSupportInfo.mEmcBearerSupport) {
            return false;
        }
        return true;
    }

    @Override // android.telephony.VopsSupportInfo
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mVopsSupport), java.lang.Integer.valueOf(this.mEmcBearerSupport));
    }

    public java.lang.String toString() {
        return "LteVopsSupportInfo :  mVopsSupport = " + this.mVopsSupport + " mEmcBearerSupport = " + this.mEmcBearerSupport;
    }

    protected static android.telephony.LteVopsSupportInfo createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.LteVopsSupportInfo(parcel);
    }

    private LteVopsSupportInfo(android.os.Parcel parcel) {
        this.mVopsSupport = parcel.readInt();
        this.mEmcBearerSupport = parcel.readInt();
    }
}
