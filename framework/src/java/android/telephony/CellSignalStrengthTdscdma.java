package android.telephony;

/* loaded from: classes3.dex */
public final class CellSignalStrengthTdscdma extends android.telephony.CellSignalStrength implements android.os.Parcelable {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellSignalStrengthTdscdma";
    private static final int TDSCDMA_RSCP_GOOD = -73;
    private static final int TDSCDMA_RSCP_GREAT = -49;
    private static final int TDSCDMA_RSCP_MAX = -24;
    private static final int TDSCDMA_RSCP_MIN = -120;
    private static final int TDSCDMA_RSCP_MODERATE = -97;
    private static final int TDSCDMA_RSCP_POOR = -110;
    private int mBitErrorRate;
    private int mLevel;
    private int mRscp;
    private int mRssi;
    private static final android.telephony.CellSignalStrengthTdscdma sInvalid = new android.telephony.CellSignalStrengthTdscdma();
    public static final android.os.Parcelable.Creator<android.telephony.CellSignalStrengthTdscdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellSignalStrengthTdscdma>() { // from class: android.telephony.CellSignalStrengthTdscdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthTdscdma createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellSignalStrengthTdscdma(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthTdscdma[] newArray(int i) {
            return new android.telephony.CellSignalStrengthTdscdma[i];
        }
    };

    public CellSignalStrengthTdscdma() {
        setDefaultValues();
    }

    public CellSignalStrengthTdscdma(int i, int i2, int i3) {
        this.mRssi = inRangeOrUnavailable(i, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(i2, 0, 7, 99);
        this.mRscp = inRangeOrUnavailable(i3, -120, -24);
        updateLevel(null, null);
    }

    public CellSignalStrengthTdscdma(android.telephony.CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        copyFrom(cellSignalStrengthTdscdma);
    }

    protected void copyFrom(android.telephony.CellSignalStrengthTdscdma cellSignalStrengthTdscdma) {
        this.mRssi = cellSignalStrengthTdscdma.mRssi;
        this.mBitErrorRate = cellSignalStrengthTdscdma.mBitErrorRate;
        this.mRscp = cellSignalStrengthTdscdma.mRscp;
        this.mLevel = cellSignalStrengthTdscdma.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public android.telephony.CellSignalStrengthTdscdma copy() {
        return new android.telephony.CellSignalStrengthTdscdma(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(android.os.PersistableBundle persistableBundle, android.telephony.ServiceState serviceState) {
        if (this.mRscp <= -24) {
            if (this.mRscp < -49) {
                if (this.mRscp < -73) {
                    if (this.mRscp < -97) {
                        if (this.mRscp < -110) {
                            this.mLevel = 0;
                            return;
                        } else {
                            this.mLevel = 1;
                            return;
                        }
                    }
                    this.mLevel = 2;
                    return;
                }
                this.mLevel = 3;
                return;
            }
            this.mLevel = 4;
            return;
        }
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mRscp;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        return this.mRscp != Integer.MAX_VALUE ? getAsuFromRscpDbm(this.mRscp) : this.mRssi != Integer.MAX_VALUE ? getAsuFromRssiDbm(this.mRssi) : getAsuFromRscpDbm(Integer.MAX_VALUE);
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRssi), java.lang.Integer.valueOf(this.mBitErrorRate), java.lang.Integer.valueOf(this.mRscp), java.lang.Integer.valueOf(this.mLevel));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellSignalStrengthTdscdma)) {
            return false;
        }
        android.telephony.CellSignalStrengthTdscdma cellSignalStrengthTdscdma = (android.telephony.CellSignalStrengthTdscdma) obj;
        return this.mRssi == cellSignalStrengthTdscdma.mRssi && this.mBitErrorRate == cellSignalStrengthTdscdma.mBitErrorRate && this.mRscp == cellSignalStrengthTdscdma.mRscp && this.mLevel == cellSignalStrengthTdscdma.mLevel;
    }

    public java.lang.String toString() {
        return "CellSignalStrengthTdscdma: rssi=" + this.mRssi + " ber=" + this.mBitErrorRate + " rscp=" + this.mRscp + " level=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mRscp);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthTdscdma(android.os.Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mRscp = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }
}
