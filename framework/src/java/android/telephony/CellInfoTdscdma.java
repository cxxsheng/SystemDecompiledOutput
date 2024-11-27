package android.telephony;

/* loaded from: classes3.dex */
public final class CellInfoTdscdma extends android.telephony.CellInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellInfoTdscdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellInfoTdscdma>() { // from class: android.telephony.CellInfoTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoTdscdma createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellInfoTdscdma.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoTdscdma[] newArray(int i) {
            return new android.telephony.CellInfoTdscdma[i];
        }
    };
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellInfoTdscdma";
    private android.telephony.CellIdentityTdscdma mCellIdentityTdscdma;
    private android.telephony.CellSignalStrengthTdscdma mCellSignalStrengthTdscdma;

    public CellInfoTdscdma() {
        this.mCellIdentityTdscdma = new android.telephony.CellIdentityTdscdma();
        this.mCellSignalStrengthTdscdma = new android.telephony.CellSignalStrengthTdscdma();
    }

    public CellInfoTdscdma(android.telephony.CellInfoTdscdma cellInfoTdscdma) {
        super(cellInfoTdscdma);
        this.mCellIdentityTdscdma = cellInfoTdscdma.mCellIdentityTdscdma.copy();
        this.mCellSignalStrengthTdscdma = cellInfoTdscdma.mCellSignalStrengthTdscdma.copy();
    }

    public CellInfoTdscdma(int i, boolean z, long j, android.telephony.CellIdentityTdscdma cellIdentityTdscdma, android.telephony.CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        super(i, z, j);
        this.mCellIdentityTdscdma = cellIdentityTdscdma;
        this.mCellSignalStrengthTdscdma = cellSignalStrengthTdscdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellIdentityTdscdma getCellIdentity() {
        return this.mCellIdentityTdscdma;
    }

    public void setCellIdentity(android.telephony.CellIdentityTdscdma cellIdentityTdscdma) {
        this.mCellIdentityTdscdma = cellIdentityTdscdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellSignalStrengthTdscdma getCellSignalStrength() {
        return this.mCellSignalStrengthTdscdma;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellInfo sanitizeLocationInfo() {
        android.telephony.CellInfoTdscdma cellInfoTdscdma = new android.telephony.CellInfoTdscdma(this);
        cellInfoTdscdma.mCellIdentityTdscdma = this.mCellIdentityTdscdma.sanitizeLocationInfo();
        return cellInfoTdscdma;
    }

    public void setCellSignalStrength(android.telephony.CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        this.mCellSignalStrengthTdscdma = cellSignalStrengthTdscdma;
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mCellIdentityTdscdma, this.mCellSignalStrengthTdscdma);
    }

    @Override // android.telephony.CellInfo
    public boolean equals(java.lang.Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        try {
            android.telephony.CellInfoTdscdma cellInfoTdscdma = (android.telephony.CellInfoTdscdma) obj;
            if (this.mCellIdentityTdscdma.equals(cellInfoTdscdma.mCellIdentityTdscdma)) {
                return this.mCellSignalStrengthTdscdma.equals(cellInfoTdscdma.mCellSignalStrengthTdscdma);
            }
            return false;
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    @Override // android.telephony.CellInfo
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("CellInfoTdscdma:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ").append(this.mCellIdentityTdscdma);
        stringBuffer.append(" ").append(this.mCellSignalStrengthTdscdma);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 5);
        this.mCellIdentityTdscdma.writeToParcel(parcel, i);
        this.mCellSignalStrengthTdscdma.writeToParcel(parcel, i);
    }

    private CellInfoTdscdma(android.os.Parcel parcel) {
        super(parcel);
        this.mCellIdentityTdscdma = android.telephony.CellIdentityTdscdma.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthTdscdma = android.telephony.CellSignalStrengthTdscdma.CREATOR.createFromParcel(parcel);
    }

    protected static android.telephony.CellInfoTdscdma createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellInfoTdscdma(parcel);
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
