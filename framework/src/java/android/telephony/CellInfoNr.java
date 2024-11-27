package android.telephony;

/* loaded from: classes3.dex */
public final class CellInfoNr extends android.telephony.CellInfo {
    public static final android.os.Parcelable.Creator<android.telephony.CellInfoNr> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellInfoNr>() { // from class: android.telephony.CellInfoNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoNr createFromParcel(android.os.Parcel parcel) {
            parcel.readInt();
            return new android.telephony.CellInfoNr(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellInfoNr[] newArray(int i) {
            return new android.telephony.CellInfoNr[i];
        }
    };
    private static final java.lang.String TAG = "CellInfoNr";
    private android.telephony.CellIdentityNr mCellIdentity;
    private final android.telephony.CellSignalStrengthNr mCellSignalStrength;

    @Override // android.telephony.CellInfo
    public /* bridge */ /* synthetic */ android.telephony.CellIdentityNr getCellIdentity() {
        return (android.telephony.CellIdentityNr) getCellIdentity();
    }

    @Override // android.telephony.CellInfo
    public /* bridge */ /* synthetic */ android.telephony.CellSignalStrengthNr getCellSignalStrength() {
        return (android.telephony.CellSignalStrengthNr) getCellSignalStrength();
    }

    public CellInfoNr() {
        this.mCellIdentity = new android.telephony.CellIdentityNr();
        this.mCellSignalStrength = new android.telephony.CellSignalStrengthNr();
    }

    private CellInfoNr(android.os.Parcel parcel) {
        super(parcel);
        this.mCellIdentity = android.telephony.CellIdentityNr.CREATOR.createFromParcel(parcel);
        this.mCellSignalStrength = android.telephony.CellSignalStrengthNr.CREATOR.createFromParcel(parcel);
    }

    private CellInfoNr(android.telephony.CellInfoNr cellInfoNr, boolean z) {
        super(cellInfoNr);
        this.mCellIdentity = z ? cellInfoNr.mCellIdentity.sanitizeLocationInfo() : cellInfoNr.mCellIdentity;
        this.mCellSignalStrength = cellInfoNr.mCellSignalStrength;
    }

    public CellInfoNr(int i, boolean z, long j, android.telephony.CellIdentityNr cellIdentityNr, android.telephony.CellSignalStrengthNr cellSignalStrengthNr) {
        super(i, z, j);
        this.mCellIdentity = cellIdentityNr;
        this.mCellSignalStrength = cellSignalStrengthNr;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellIdentity getCellIdentity() {
        return this.mCellIdentity;
    }

    public void setCellIdentity(android.telephony.CellIdentityNr cellIdentityNr) {
        this.mCellIdentity = cellIdentityNr;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellSignalStrength getCellSignalStrength() {
        return this.mCellSignalStrength;
    }

    @Override // android.telephony.CellInfo
    public android.telephony.CellInfo sanitizeLocationInfo() {
        return new android.telephony.CellInfoNr(this, true);
    }

    @Override // android.telephony.CellInfo
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(super.hashCode()), this.mCellIdentity, this.mCellSignalStrength);
    }

    @Override // android.telephony.CellInfo
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellInfoNr)) {
            return false;
        }
        android.telephony.CellInfoNr cellInfoNr = (android.telephony.CellInfoNr) obj;
        return super.equals(cellInfoNr) && this.mCellIdentity.equals(cellInfoNr.mCellIdentity) && this.mCellSignalStrength.equals(cellInfoNr.mCellSignalStrength);
    }

    @Override // android.telephony.CellInfo
    public java.lang.String toString() {
        return "CellInfoNr:{" + (" " + super.toString()) + (" " + this.mCellIdentity) + (" " + this.mCellSignalStrength) + " }";
    }

    @Override // android.telephony.CellInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i, 6);
        this.mCellIdentity.writeToParcel(parcel, i);
        this.mCellSignalStrength.writeToParcel(parcel, i);
    }

    protected static android.telephony.CellInfoNr createFromParcelBody(android.os.Parcel parcel) {
        return new android.telephony.CellInfoNr(parcel);
    }
}
