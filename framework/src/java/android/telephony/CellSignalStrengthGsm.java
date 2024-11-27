package android.telephony;

/* loaded from: classes3.dex */
public final class CellSignalStrengthGsm extends android.telephony.CellSignalStrength implements android.os.Parcelable {
    private static final boolean DBG = false;
    private static final int GSM_RSSI_GOOD = -97;
    private static final int GSM_RSSI_GREAT = -89;
    private static final int GSM_RSSI_MAX = -51;
    private static final int GSM_RSSI_MIN = -113;
    private static final int GSM_RSSI_MODERATE = -103;
    private static final int GSM_RSSI_POOR = -107;
    private static final java.lang.String LOG_TAG = "CellSignalStrengthGsm";
    private int mBitErrorRate;
    private int mLevel;
    private int mRssi;
    private int mTimingAdvance;
    private static final int[] sRssiThresholds = {-107, -103, -97, -89};
    private static final android.telephony.CellSignalStrengthGsm sInvalid = new android.telephony.CellSignalStrengthGsm();
    public static final android.os.Parcelable.Creator<android.telephony.CellSignalStrengthGsm> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellSignalStrengthGsm>() { // from class: android.telephony.CellSignalStrengthGsm.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthGsm createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellSignalStrengthGsm(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthGsm[] newArray(int i) {
            return new android.telephony.CellSignalStrengthGsm[i];
        }
    };

    public CellSignalStrengthGsm() {
        setDefaultValues();
    }

    public CellSignalStrengthGsm(int i, int i2, int i3) {
        this.mRssi = inRangeOrUnavailable(i, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(i2, 0, 7, 99);
        this.mTimingAdvance = inRangeOrUnavailable(i3, 0, 219);
        updateLevel(null, null);
    }

    public CellSignalStrengthGsm(android.telephony.CellSignalStrengthGsm cellSignalStrengthGsm) {
        copyFrom(cellSignalStrengthGsm);
    }

    protected void copyFrom(android.telephony.CellSignalStrengthGsm cellSignalStrengthGsm) {
        this.mRssi = cellSignalStrengthGsm.mRssi;
        this.mBitErrorRate = cellSignalStrengthGsm.mBitErrorRate;
        this.mTimingAdvance = cellSignalStrengthGsm.mTimingAdvance;
        this.mLevel = cellSignalStrengthGsm.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public android.telephony.CellSignalStrengthGsm copy() {
        return new android.telephony.CellSignalStrengthGsm(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mTimingAdvance = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(android.os.PersistableBundle persistableBundle, android.telephony.ServiceState serviceState) {
        int[] intArray;
        int i = 4;
        if (persistableBundle == null) {
            intArray = sRssiThresholds;
        } else {
            intArray = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_GSM_RSSI_THRESHOLDS_INT_ARRAY);
            if (intArray == null || intArray.length != 4) {
                intArray = sRssiThresholds;
            }
        }
        if (this.mRssi < -113 || this.mRssi > -51) {
            this.mLevel = 0;
            return;
        }
        while (i > 0 && this.mRssi < intArray[i - 1]) {
            i--;
        }
        this.mLevel = i;
    }

    public int getTimingAdvance() {
        return this.mTimingAdvance;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mRssi;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        return getAsuFromRssiDbm(this.mRssi);
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRssi), java.lang.Integer.valueOf(this.mBitErrorRate), java.lang.Integer.valueOf(this.mTimingAdvance));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellSignalStrengthGsm)) {
            return false;
        }
        android.telephony.CellSignalStrengthGsm cellSignalStrengthGsm = (android.telephony.CellSignalStrengthGsm) obj;
        return this.mRssi == cellSignalStrengthGsm.mRssi && this.mBitErrorRate == cellSignalStrengthGsm.mBitErrorRate && this.mTimingAdvance == cellSignalStrengthGsm.mTimingAdvance && this.mLevel == cellSignalStrengthGsm.mLevel;
    }

    public java.lang.String toString() {
        return "CellSignalStrengthGsm: rssi=" + this.mRssi + " ber=" + this.mBitErrorRate + " mTa=" + this.mTimingAdvance + " mLevel=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mTimingAdvance);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthGsm(android.os.Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mTimingAdvance = parcel.readInt();
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
