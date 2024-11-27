package android.telephony;

/* loaded from: classes3.dex */
public final class CellSignalStrengthWcdma extends android.telephony.CellSignalStrength implements android.os.Parcelable {
    private static final boolean DBG = false;
    private static final java.lang.String DEFAULT_LEVEL_CALCULATION_METHOD = "rssi";
    public static final java.lang.String LEVEL_CALCULATION_METHOD_RSCP = "rscp";
    public static final java.lang.String LEVEL_CALCULATION_METHOD_RSSI = "rssi";
    private static final java.lang.String LOG_TAG = "CellSignalStrengthWcdma";
    private static final int WCDMA_RSCP_GOOD = -95;
    private static final int WCDMA_RSCP_GREAT = -85;
    private static final int WCDMA_RSCP_MAX = -24;
    private static final int WCDMA_RSCP_MIN = -120;
    private static final int WCDMA_RSCP_MODERATE = -105;
    private static final int WCDMA_RSCP_POOR = -115;
    private static final int WCDMA_RSSI_GREAT = -77;
    private static final int WCDMA_RSSI_MAX = -51;
    private static final int WCDMA_RSSI_MIN = -113;
    private static final int WCDMA_RSSI_MODERATE = -97;
    private static final int WCDMA_RSSI_POOR = -107;
    private int mBitErrorRate;
    private int mEcNo;
    private int mLevel;
    private int mRscp;
    private int mRssi;
    private static final int WCDMA_RSSI_GOOD = -87;
    private static final int[] sRssiThresholds = {-107, -97, WCDMA_RSSI_GOOD, -77};
    private static final int[] sRscpThresholds = {-115, -105, -95, -85};
    private static final android.telephony.CellSignalStrengthWcdma sInvalid = new android.telephony.CellSignalStrengthWcdma();
    public static final android.os.Parcelable.Creator<android.telephony.CellSignalStrengthWcdma> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellSignalStrengthWcdma>() { // from class: android.telephony.CellSignalStrengthWcdma.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthWcdma createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellSignalStrengthWcdma(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthWcdma[] newArray(int i) {
            return new android.telephony.CellSignalStrengthWcdma[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LevelCalculationMethod {
    }

    public CellSignalStrengthWcdma() {
        setDefaultValues();
    }

    public CellSignalStrengthWcdma(int i, int i2, int i3, int i4) {
        this.mRssi = inRangeOrUnavailable(i, -113, -51);
        this.mBitErrorRate = inRangeOrUnavailable(i2, 0, 7, 99);
        this.mRscp = inRangeOrUnavailable(i3, -120, -24);
        this.mEcNo = inRangeOrUnavailable(i4, -24, 1);
        updateLevel(null, null);
    }

    public CellSignalStrengthWcdma(android.telephony.CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        copyFrom(cellSignalStrengthWcdma);
    }

    protected void copyFrom(android.telephony.CellSignalStrengthWcdma cellSignalStrengthWcdma) {
        this.mRssi = cellSignalStrengthWcdma.mRssi;
        this.mBitErrorRate = cellSignalStrengthWcdma.mBitErrorRate;
        this.mRscp = cellSignalStrengthWcdma.mRscp;
        this.mEcNo = cellSignalStrengthWcdma.mEcNo;
        this.mLevel = cellSignalStrengthWcdma.mLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public android.telephony.CellSignalStrengthWcdma copy() {
        return new android.telephony.CellSignalStrengthWcdma(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mRssi = Integer.MAX_VALUE;
        this.mBitErrorRate = Integer.MAX_VALUE;
        this.mRscp = Integer.MAX_VALUE;
        this.mEcNo = Integer.MAX_VALUE;
        this.mLevel = 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.telephony.CellSignalStrength
    public void updateLevel(android.os.PersistableBundle persistableBundle, android.telephony.ServiceState serviceState) {
        java.lang.String string;
        int[] intArray;
        char c;
        int i = 4;
        if (persistableBundle == null) {
            intArray = sRscpThresholds;
            string = "rssi";
        } else {
            string = persistableBundle.getString(android.telephony.CarrierConfigManager.KEY_WCDMA_DEFAULT_SIGNAL_STRENGTH_MEASUREMENT_STRING, "rssi");
            if (android.text.TextUtils.isEmpty(string)) {
                string = "rssi";
            }
            intArray = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_WCDMA_RSCP_THRESHOLDS_INT_ARRAY);
            if (intArray == null || intArray.length != 4) {
                intArray = sRscpThresholds;
            }
        }
        switch (string.hashCode()) {
            case 3509870:
                if (string.equals(LEVEL_CALCULATION_METHOD_RSCP)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3510359:
                if (string.equals("rssi")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (this.mRscp < -120 || this.mRscp > -24) {
                    this.mLevel = 0;
                    return;
                }
                while (i > 0 && this.mRscp < intArray[i - 1]) {
                    i--;
                }
                this.mLevel = i;
                return;
            case 1:
            default:
                loge("Invalid Level Calculation Method for CellSignalStrengthWcdma = " + string);
                break;
            case 2:
                break;
        }
        if (this.mRssi < -113 || this.mRssi > -51) {
            this.mLevel = 0;
            return;
        }
        while (i > 0 && this.mRssi < sRssiThresholds[i - 1]) {
            i--;
        }
        this.mLevel = i;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mRscp != Integer.MAX_VALUE ? this.mRscp : this.mRssi;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        return this.mRscp != Integer.MAX_VALUE ? getAsuFromRscpDbm(this.mRscp) : this.mRssi != Integer.MAX_VALUE ? getAsuFromRssiDbm(this.mRssi) : getAsuFromRscpDbm(Integer.MAX_VALUE);
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getRscp() {
        return this.mRscp;
    }

    public int getEcNo() {
        return this.mEcNo;
    }

    public int getBitErrorRate() {
        return this.mBitErrorRate;
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRssi), java.lang.Integer.valueOf(this.mBitErrorRate), java.lang.Integer.valueOf(this.mRscp), java.lang.Integer.valueOf(this.mEcNo), java.lang.Integer.valueOf(this.mLevel));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellSignalStrengthWcdma)) {
            return false;
        }
        android.telephony.CellSignalStrengthWcdma cellSignalStrengthWcdma = (android.telephony.CellSignalStrengthWcdma) obj;
        return this.mRssi == cellSignalStrengthWcdma.mRssi && this.mBitErrorRate == cellSignalStrengthWcdma.mBitErrorRate && this.mRscp == cellSignalStrengthWcdma.mRscp && this.mEcNo == cellSignalStrengthWcdma.mEcNo && this.mLevel == cellSignalStrengthWcdma.mLevel;
    }

    public java.lang.String toString() {
        return "CellSignalStrengthWcdma: ss=" + this.mRssi + " ber=" + this.mBitErrorRate + " rscp=" + this.mRscp + " ecno=" + this.mEcNo + " level=" + this.mLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mBitErrorRate);
        parcel.writeInt(this.mRscp);
        parcel.writeInt(this.mEcNo);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthWcdma(android.os.Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mBitErrorRate = parcel.readInt();
        this.mRscp = parcel.readInt();
        this.mEcNo = parcel.readInt();
        this.mLevel = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static void log(java.lang.String str) {
        com.android.telephony.Rlog.w(LOG_TAG, str);
    }

    private static void loge(java.lang.String str) {
        com.android.telephony.Rlog.e(LOG_TAG, str);
    }
}
