package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class NrVopsSupportInfo extends android.telephony.VopsSupportInfo {
    public static final android.os.Parcelable.Creator<android.telephony.NrVopsSupportInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.NrVopsSupportInfo>() { // from class: android.telephony.NrVopsSupportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NrVopsSupportInfo createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return new android.telephony.NrVopsSupportInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.NrVopsSupportInfo[] newArray(int i) {
            return new android.telephony.NrVopsSupportInfo[i];
        }
    };
    public static final int NR_STATUS_EMC_5GCN_ONLY = 1;
    public static final int NR_STATUS_EMC_EUTRA_5GCN_ONLY = 2;
    public static final int NR_STATUS_EMC_NOT_SUPPORTED = 0;
    public static final int NR_STATUS_EMC_NR_EUTRA_5GCN = 3;
    public static final int NR_STATUS_EMF_5GCN_ONLY = 1;
    public static final int NR_STATUS_EMF_EUTRA_5GCN_ONLY = 2;
    public static final int NR_STATUS_EMF_NOT_SUPPORTED = 0;
    public static final int NR_STATUS_EMF_NR_EUTRA_5GCN = 3;
    public static final int NR_STATUS_VOPS_3GPP_SUPPORTED = 1;
    public static final int NR_STATUS_VOPS_NON_3GPP_SUPPORTED = 2;
    public static final int NR_STATUS_VOPS_NOT_SUPPORTED = 0;
    private final int mEmcSupport;
    private final int mEmfSupport;
    private final int mVopsSupport;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NrEmcStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NrEmfStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NrVopsStatus {
    }

    public NrVopsSupportInfo(int i, int i2, int i3) {
        this.mVopsSupport = i;
        this.mEmcSupport = i2;
        this.mEmfSupport = i3;
    }

    public int getVopsSupport() {
        return this.mVopsSupport;
    }

    public int getEmcSupport() {
        return this.mEmcSupport;
    }

    public int getEmfSupport() {
        return this.mEmfSupport;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isVopsSupported() {
        return this.mVopsSupport != 0;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceSupported() {
        return this.mEmcSupport != 0;
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean isEmergencyServiceFallbackSupported() {
        return this.mEmfSupport != 0;
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.VopsSupportInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 6);
        parcel.writeInt(this.mVopsSupport);
        parcel.writeInt(this.mEmcSupport);
        parcel.writeInt(this.mEmfSupport);
    }

    @Override // android.telephony.VopsSupportInfo
    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.NrVopsSupportInfo)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        android.telephony.NrVopsSupportInfo nrVopsSupportInfo = (android.telephony.NrVopsSupportInfo) obj;
        if (this.mVopsSupport != nrVopsSupportInfo.mVopsSupport || this.mEmcSupport != nrVopsSupportInfo.mEmcSupport || this.mEmfSupport != nrVopsSupportInfo.mEmfSupport) {
            return false;
        }
        return true;
    }

    @Override // android.telephony.VopsSupportInfo
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mVopsSupport), java.lang.Integer.valueOf(this.mEmcSupport), java.lang.Integer.valueOf(this.mEmfSupport));
    }

    public java.lang.String toString() {
        return "NrVopsSupportInfo :  mVopsSupport = " + this.mVopsSupport + " mEmcSupport = " + this.mEmcSupport + " mEmfSupport = " + this.mEmfSupport;
    }

    protected static android.telephony.NrVopsSupportInfo createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.NrVopsSupportInfo(parcel);
    }

    private NrVopsSupportInfo(android.os.Parcel parcel) {
        this.mVopsSupport = parcel.readInt();
        this.mEmcSupport = parcel.readInt();
        this.mEmfSupport = parcel.readInt();
    }
}
