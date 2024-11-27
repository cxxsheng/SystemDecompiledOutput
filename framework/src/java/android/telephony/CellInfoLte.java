package android.telephony;

/* loaded from: classes3.dex */
public final class CellInfoLte extends android.telephony.CellInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.CellInfoLte> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellInfoLte>() { // from class: android.telephony.CellInfoLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoLte createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return android.telephony.CellInfoLte.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoLte[] newArray(int i) {
            return new android.telephony.CellInfoLte[i];
        }
    };
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellInfoLte";
    private android.telephony.CellConfigLte mCellConfig;
    private android.telephony.CellIdentityLte mCellIdentityLte;
    private android.telephony.CellSignalStrengthLte mCellSignalStrengthLte;

    public CellInfoLte() {
        this.mCellIdentityLte = new android.telephony.CellIdentityLte();
        this.mCellSignalStrengthLte = new android.telephony.CellSignalStrengthLte();
        this.mCellConfig = new android.telephony.CellConfigLte();
    }

    public CellInfoLte(android.telephony.CellInfoLte cellInfoLte) {
        super(cellInfoLte);
        this.mCellIdentityLte = cellInfoLte.mCellIdentityLte.copy();
        this.mCellSignalStrengthLte = cellInfoLte.mCellSignalStrengthLte.copy();
        this.mCellConfig = new android.telephony.CellConfigLte(cellInfoLte.mCellConfig);
    }

    public CellInfoLte(int i, boolean z, long j, android.telephony.CellIdentityLte cellIdentityLte, android.telephony.CellSignalStrengthLte cellSignalStrengthLte, android.telephony.CellConfigLte cellConfigLte) {
        super(i, z, j);
        this.mCellIdentityLte = cellIdentityLte;
        this.mCellSignalStrengthLte = cellSignalStrengthLte;
        this.mCellConfig = cellConfigLte;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellIdentityLte getCellIdentity() {
        return this.mCellIdentityLte;
    }

    public void setCellIdentity(android.telephony.CellIdentityLte cellIdentityLte) {
        this.mCellIdentityLte = cellIdentityLte;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellSignalStrengthLte getCellSignalStrength() {
        return this.mCellSignalStrengthLte;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellInfo sanitizeLocationInfo() {
        android.telephony.CellInfoLte cellInfoLte = new android.telephony.CellInfoLte(this);
        cellInfoLte.mCellIdentityLte = this.mCellIdentityLte.sanitizeLocationInfo();
        return cellInfoLte;
    }

    public void setCellSignalStrength(android.telephony.CellSignalStrengthLte cellSignalStrengthLte) {
        this.mCellSignalStrengthLte = cellSignalStrengthLte;
    }

    public void setCellConfig(android.telephony.CellConfigLte cellConfigLte) {
        this.mCellConfig = cellConfigLte;
    }

    public android.telephony.CellConfigLte getCellConfig() {
        return this.mCellConfig;
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), java.lang.Integer.valueOf(this.mCellIdentityLte.hashCode()), java.lang.Integer.valueOf(this.mCellSignalStrengthLte.hashCode()), java.lang.Integer.valueOf(this.mCellConfig.hashCode()));
    }

    @Override // android.telephony.CellInfo
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellInfoLte)) {
            return false;
        }
        android.telephony.CellInfoLte cellInfoLte = (android.telephony.CellInfoLte) obj;
        return super.equals(cellInfoLte) && this.mCellIdentityLte.equals(cellInfoLte.mCellIdentityLte) && this.mCellSignalStrengthLte.equals(cellInfoLte.mCellSignalStrengthLte) && this.mCellConfig.equals(cellInfoLte.mCellConfig);
    }

    @Override // android.telephony.CellInfo
    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append("CellInfoLte:{");
        stringBuffer.append(super.toString());
        stringBuffer.append(" ").append(this.mCellIdentityLte);
        stringBuffer.append(" ").append(this.mCellSignalStrengthLte);
        stringBuffer.append(" ").append(this.mCellConfig);
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 3);
        this.mCellIdentityLte.writeToParcel(parcel, i);
        this.mCellSignalStrengthLte.writeToParcel(parcel, i);
        this.mCellConfig.writeToParcel(parcel, i);
    }

    private CellInfoLte(android.os.Parcel parcel) {
        super(parcel);
        this.mCellIdentityLte = android.telephony.CellIdentityLte.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrengthLte = android.telephony.CellSignalStrengthLte.CREATOR.createFromParcel(parcel);
        this.mCellConfig = android.telephony.CellConfigLte.CREATOR.createFromParcel(parcel);
    }

    protected static android.telephony.CellInfoLte createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellInfoLte(parcel);
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
