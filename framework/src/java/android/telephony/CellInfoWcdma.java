package android.telephony;

/* loaded from: classes3.dex */
public final class CellInfoWcdma extends android.telephony.CellInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellInfoWcdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellInfoWcdma>() { // from class: android.telephony.CellInfoWcdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoWcdma createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellInfoWcdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoWcdma[] newArray(int i) {
            return new android.telephony.CellInfoWcdma[i];
        }
    };
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellInfoWcdma";
    private android.telephony.CellIdentityWcdma mCellIdentityWcdma;
    private android.telephony.CellSignalStrengthWcdma mCellSignalStrengthWcdma;

    public CellInfoWcdma() {
        this.mCellIdentityWcdma = new android.telephony.CellIdentityWcdma();
        this.mCellSignalStrengthWcdma = new android.telephony.CellSignalStrengthWcdma();
    }

    public CellInfoWcdma(android.telephony.CellInfoWcdma cellInfoWcdma) {
        super(cellInfoWcdma);
        this.mCellIdentityWcdma = cellInfoWcdma.mCellIdentityWcdma.copy();
        this.mCellSignalStrengthWcdma = cellInfoWcdma.mCellSignalStrengthWcdma.copy();
    }

    public CellInfoWcdma(int i, boolean z, long j, android.telephony.CellIdentityWcdma cellIdentityWcdma, android.telephony.CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        super(i, z, j);
        this.mCellIdentityWcdma = cellIdentityWcdma;
        this.mCellSignalStrengthWcdma = cellSignalStrengthWcdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellIdentityWcdma getCellIdentity() {
        return this.mCellIdentityWcdma;
    }

    public void setCellIdentity(android.telephony.CellIdentityWcdma cellIdentityWcdma) {
        this.mCellIdentityWcdma = cellIdentityWcdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellSignalStrengthWcdma getCellSignalStrength() {
        return this.mCellSignalStrengthWcdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellInfo sanitizeLocationInfo() {
        android.telephony.CellInfoWcdma cellInfoWcdma = new android.telephony.CellInfoWcdma(this);
        cellInfoWcdma.mCellIdentityWcdma = this.mCellIdentityWcdma.sanitizeLocationInfo();
        return cellInfoWcdma;
    }

    public void setCellSignalStrength(android.telephony.CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        this.mCellSignalStrengthWcdma = cellSignalStrengthWcdma;
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mCellIdentityWcdma, this.mCellSignalStrengthWcdma);
    }

    @Override // android.telephony.CellInfo
    public boolean equals(java.lang.Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        try {
            android.telephony.CellInfoWcdma cellInfoWcdma = (android.telephony.CellInfoWcdma) obj;
            if (this.mCellIdentityWcdma.equals(cellInfoWcdma.mCellIdentityWcdma)) {
                return this.mCellSignalStrengthWcdma.equals(cellInfoWcdma.mCellSignalStrengthWcdma);
            }
            return false;
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    @Override // android.telephony.CellInfo
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("CellInfoWcdma:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ").append(this.mCellIdentityWcdma);
        stringBuffer.append(" ").append(this.mCellSignalStrengthWcdma);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 4);
        this.mCellIdentityWcdma.writeToParcel(parcel, i);
        this.mCellSignalStrengthWcdma.writeToParcel(parcel, i);
    }

    private CellInfoWcdma(android.os.Parcel parcel) {
        super(parcel);
        this.mCellIdentityWcdma = android.telephony.CellIdentityWcdma.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthWcdma = android.telephony.CellSignalStrengthWcdma.CREATOR.createFromParcel(parcel);
    }

    protected static android.telephony.CellInfoWcdma createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellInfoWcdma(parcel);
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
