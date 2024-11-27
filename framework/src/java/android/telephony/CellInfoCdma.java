package android.telephony;

/* loaded from: classes3.dex */
public final class CellInfoCdma extends android.telephony.CellInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellInfoCdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellInfoCdma>() { // from class: android.telephony.CellInfoCdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoCdma createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellInfoCdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoCdma[] newArray(int i) {
            return new android.telephony.CellInfoCdma[i];
        }
    };
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellInfoCdma";
    private android.telephony.CellIdentityCdma mCellIdentityCdma;
    private android.telephony.CellSignalStrengthCdma mCellSignalStrengthCdma;

    public CellInfoCdma() {
        this.mCellIdentityCdma = new android.telephony.CellIdentityCdma();
        this.mCellSignalStrengthCdma = new android.telephony.CellSignalStrengthCdma();
    }

    public CellInfoCdma(android.telephony.CellInfoCdma cellInfoCdma) {
        super(cellInfoCdma);
        this.mCellIdentityCdma = cellInfoCdma.mCellIdentityCdma.copy();
        this.mCellSignalStrengthCdma = cellInfoCdma.mCellSignalStrengthCdma.copy();
    }

    public CellInfoCdma(int i, boolean z, long j, android.telephony.CellIdentityCdma cellIdentityCdma, android.telephony.CellSignalStrengthCdma cellSignalStrengthCdma) {
        super(i, z, j);
        this.mCellIdentityCdma = cellIdentityCdma;
        this.mCellSignalStrengthCdma = cellSignalStrengthCdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellIdentityCdma getCellIdentity() {
        return this.mCellIdentityCdma;
    }

    public void setCellIdentity(android.telephony.CellIdentityCdma cellIdentityCdma) {
        this.mCellIdentityCdma = cellIdentityCdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellSignalStrengthCdma getCellSignalStrength() {
        return this.mCellSignalStrengthCdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellInfo sanitizeLocationInfo() {
        android.telephony.CellInfoCdma cellInfoCdma = new android.telephony.CellInfoCdma(this);
        cellInfoCdma.mCellIdentityCdma = this.mCellIdentityCdma.sanitizeLocationInfo();
        return cellInfoCdma;
    }

    public void setCellSignalStrength(android.telephony.CellSignalStrengthCdma cellSignalStrengthCdma) {
        this.mCellSignalStrengthCdma = cellSignalStrengthCdma;
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return super.hashCode() + this.mCellIdentityCdma.hashCode() + this.mCellSignalStrengthCdma.hashCode();
    }

    @Override // android.telephony.CellInfo
    public boolean equals(java.lang.Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        try {
            android.telephony.CellInfoCdma cellInfoCdma = (android.telephony.CellInfoCdma) obj;
            if (this.mCellIdentityCdma.equals(cellInfoCdma.mCellIdentityCdma)) {
                return this.mCellSignalStrengthCdma.equals(cellInfoCdma.mCellSignalStrengthCdma);
            }
            return false;
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    @Override // android.telephony.CellInfo
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("CellInfoCdma:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ").append(this.mCellIdentityCdma);
        stringBuffer.append(" ").append(this.mCellSignalStrengthCdma);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 2);
        this.mCellIdentityCdma.writeToParcel(parcel, i);
        this.mCellSignalStrengthCdma.writeToParcel(parcel, i);
    }

    private CellInfoCdma(android.os.Parcel parcel) {
        super(parcel);
        this.mCellIdentityCdma = android.telephony.CellIdentityCdma.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthCdma = android.telephony.CellSignalStrengthCdma.CREATOR.createFromParcel(parcel);
    }

    protected static android.telephony.CellInfoCdma createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellInfoCdma(parcel);
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
