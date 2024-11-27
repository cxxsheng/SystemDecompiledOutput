package android.telephony;

/* loaded from: classes3.dex */
public final class CellInfoGsm extends android.telephony.CellInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellInfoGsm> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellInfoGsm>() { // from class: android.telephony.CellInfoGsm.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoGsm createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellInfoGsm.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoGsm[] newArray(int i) {
            return new android.telephony.CellInfoGsm[i];
        }
    };
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellInfoGsm";
    private android.telephony.CellIdentityGsm mCellIdentityGsm;
    private android.telephony.CellSignalStrengthGsm mCellSignalStrengthGsm;

    public CellInfoGsm() {
        this.mCellIdentityGsm = new android.telephony.CellIdentityGsm();
        this.mCellSignalStrengthGsm = new android.telephony.CellSignalStrengthGsm();
    }

    public CellInfoGsm(android.telephony.CellInfoGsm cellInfoGsm) {
        super(cellInfoGsm);
        this.mCellIdentityGsm = cellInfoGsm.mCellIdentityGsm.copy();
        this.mCellSignalStrengthGsm = cellInfoGsm.mCellSignalStrengthGsm.copy();
    }

    public CellInfoGsm(int i, boolean z, long j, android.telephony.CellIdentityGsm cellIdentityGsm, android.telephony.CellSignalStrengthGsm cellSignalStrengthGsm) {
        super(i, z, j);
        this.mCellIdentityGsm = cellIdentityGsm;
        this.mCellSignalStrengthGsm = cellSignalStrengthGsm;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellIdentityGsm getCellIdentity() {
        return this.mCellIdentityGsm;
    }

    public void setCellIdentity(android.telephony.CellIdentityGsm cellIdentityGsm) {
        this.mCellIdentityGsm = cellIdentityGsm;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellSignalStrengthGsm getCellSignalStrength() {
        return this.mCellSignalStrengthGsm;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellInfo sanitizeLocationInfo() {
        android.telephony.CellInfoGsm cellInfoGsm = new android.telephony.CellInfoGsm(this);
        cellInfoGsm.mCellIdentityGsm = this.mCellIdentityGsm.sanitizeLocationInfo();
        return cellInfoGsm;
    }

    public void setCellSignalStrength(android.telephony.CellSignalStrengthGsm cellSignalStrengthGsm) {
        this.mCellSignalStrengthGsm = cellSignalStrengthGsm;
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return super.hashCode() + this.mCellIdentityGsm.hashCode() + this.mCellSignalStrengthGsm.hashCode();
    }

    @Override // android.telephony.CellInfo
    public boolean equals(java.lang.Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        try {
            android.telephony.CellInfoGsm cellInfoGsm = (android.telephony.CellInfoGsm) obj;
            if (this.mCellIdentityGsm.equals(cellInfoGsm.mCellIdentityGsm)) {
                return this.mCellSignalStrengthGsm.equals(cellInfoGsm.mCellSignalStrengthGsm);
            }
            return false;
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    @Override // android.telephony.CellInfo
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("CellInfoGsm:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ").append(this.mCellIdentityGsm);
        stringBuffer.append(" ").append(this.mCellSignalStrengthGsm);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 1);
        this.mCellIdentityGsm.writeToParcel(parcel, i);
        this.mCellSignalStrengthGsm.writeToParcel(parcel, i);
    }

    private CellInfoGsm(android.os.Parcel parcel) {
        super(parcel);
        this.mCellIdentityGsm = android.telephony.CellIdentityGsm.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthGsm = android.telephony.CellSignalStrengthGsm.CREATOR.createFromParcel(parcel);
    }

    protected static android.telephony.CellInfoGsm createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellInfoGsm(parcel);
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
