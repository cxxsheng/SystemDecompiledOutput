package android.telephony;

/* loaded from: classes3.dex */
public final class CellSignalStrengthLte extends android.telephony.CellSignalStrength implements android.os.Parcelable {
    private static final boolean DBG = false;
    private static final java.lang.String LOG_TAG = "CellSignalStrengthLte";
    private static final int MAX_LTE_RSRP = -44;
    private static final int MIN_LTE_RSRP = -140;
    private static final int SIGNAL_STRENGTH_LTE_RSSI_ASU_UNKNOWN = 99;
    private static final int SIGNAL_STRENGTH_LTE_RSSI_VALID_ASU_MAX_VALUE = 31;
    private static final int SIGNAL_STRENGTH_LTE_RSSI_VALID_ASU_MIN_VALUE = 0;
    public static final int USE_RSRP = 1;
    public static final int USE_RSRQ = 2;
    public static final int USE_RSSNR = 4;
    private static final int sRsrpBoost = 0;
    private int mCqi;
    private int mCqiTableIndex;
    private int mLevel;
    private int mParametersUseForLevel;
    private int mRsrp;
    private int mRsrq;
    private int mRssi;
    private int mRssnr;
    private int mSignalStrength;
    private int mTimingAdvance;
    private static final int[] sRsrpThresholds = {android.content.pm.PackageManager.INSTALL_FAILED_ABORTED, android.content.pm.PackageManager.INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING, -95, -85};
    private static final int[] sRsrqThresholds = {-19, -17, -14, -12};
    private static final int[] sRssnrThresholds = {-3, 1, 5, 13};
    private static final android.telephony.CellSignalStrengthLte sInvalid = new android.telephony.CellSignalStrengthLte();
    public static final android.os.Parcelable.Creator<android.telephony.CellSignalStrengthLte> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellSignalStrengthLte>() { // from class: android.telephony.CellSignalStrengthLte.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthLte createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellSignalStrengthLte(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthLte[] newArray(int i) {
            return new android.telephony.CellSignalStrengthLte[i];
        }
    };

    public CellSignalStrengthLte() {
        setDefaultValues();
    }

    public CellSignalStrengthLte(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.mRssi = inRangeOrUnavailable(i, -113, -51);
        this.mSignalStrength = this.mRssi;
        this.mRsrp = inRangeOrUnavailable(i2, -140, -43);
        this.mRsrq = inRangeOrUnavailable(i3, -34, 3);
        this.mRssnr = inRangeOrUnavailable(i4, -20, 30);
        this.mCqiTableIndex = inRangeOrUnavailable(i5, 1, 6);
        this.mCqi = inRangeOrUnavailable(i6, 0, 15);
        this.mTimingAdvance = inRangeOrUnavailable(i7, 0, 1282);
        updateLevel(null, null);
    }

    public CellSignalStrengthLte(int i, int i2, int i3, int i4, int i5, int i6) {
        this(i, i2, i3, i4, Integer.MAX_VALUE, i5, i6);
    }

    public CellSignalStrengthLte(android.telephony.CellSignalStrengthLte cellSignalStrengthLte) {
        copyFrom(cellSignalStrengthLte);
    }

    protected void copyFrom(android.telephony.CellSignalStrengthLte cellSignalStrengthLte) {
        this.mSignalStrength = cellSignalStrengthLte.mSignalStrength;
        this.mRssi = cellSignalStrengthLte.mRssi;
        this.mRsrp = cellSignalStrengthLte.mRsrp;
        this.mRsrq = cellSignalStrengthLte.mRsrq;
        this.mRssnr = cellSignalStrengthLte.mRssnr;
        this.mCqiTableIndex = cellSignalStrengthLte.mCqiTableIndex;
        this.mCqi = cellSignalStrengthLte.mCqi;
        this.mTimingAdvance = cellSignalStrengthLte.mTimingAdvance;
        this.mLevel = cellSignalStrengthLte.mLevel;
        this.mParametersUseForLevel = cellSignalStrengthLte.mParametersUseForLevel;
    }

    @Override // android.telephony.CellSignalStrength
    public android.telephony.CellSignalStrengthLte copy() {
        return new android.telephony.CellSignalStrengthLte(this);
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mSignalStrength = Integer.MAX_VALUE;
        this.mRssi = Integer.MAX_VALUE;
        this.mRsrp = Integer.MAX_VALUE;
        this.mRsrq = Integer.MAX_VALUE;
        this.mRssnr = Integer.MAX_VALUE;
        this.mCqiTableIndex = Integer.MAX_VALUE;
        this.mCqi = Integer.MAX_VALUE;
        this.mTimingAdvance = Integer.MAX_VALUE;
        this.mLevel = 0;
        this.mParametersUseForLevel = 1;
    }

    @Override // android.telephony.CellSignalStrength
    public int getLevel() {
        return this.mLevel;
    }

    private boolean isLevelForParameter(int i) {
        return (this.mParametersUseForLevel & i) == i;
    }

    @Override // android.telephony.CellSignalStrength
    public void updateLevel(android.os.PersistableBundle persistableBundle, android.telephony.ServiceState serviceState) {
        int[] intArray;
        int[] intArray2;
        int[] intArray3;
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        int updateLevelWithMeasure;
        int i5 = 1;
        if (persistableBundle == null) {
            this.mParametersUseForLevel = 1;
            int[] iArr = sRsrpThresholds;
            int[] iArr2 = sRsrqThresholds;
            intArray3 = sRssnrThresholds;
            intArray2 = iArr2;
            intArray = iArr;
            z = false;
        } else {
            if (serviceState != null && serviceState.isUsingNonTerrestrialNetwork()) {
                this.mParametersUseForLevel = persistableBundle.getInt(android.telephony.CarrierConfigManager.KEY_PARAMETERS_USED_FOR_NTN_LTE_SIGNAL_BAR_INT);
                intArray = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_NTN_LTE_RSRP_THRESHOLDS_INT_ARRAY);
                intArray2 = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_NTN_LTE_RSRQ_THRESHOLDS_INT_ARRAY);
                intArray3 = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_NTN_LTE_RSSNR_THRESHOLDS_INT_ARRAY);
            } else {
                this.mParametersUseForLevel = persistableBundle.getInt(android.telephony.CarrierConfigManager.KEY_PARAMETERS_USED_FOR_LTE_SIGNAL_BAR_INT);
                intArray = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_LTE_RSRP_THRESHOLDS_INT_ARRAY);
                intArray2 = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_LTE_RSRQ_THRESHOLDS_INT_ARRAY);
                intArray3 = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_LTE_RSSNR_THRESHOLDS_INT_ARRAY);
            }
            if (intArray == null) {
                intArray = sRsrpThresholds;
            }
            if (intArray2 == null) {
                intArray2 = sRsrqThresholds;
            }
            if (intArray3 == null) {
                intArray3 = sRssnrThresholds;
            }
            z = persistableBundle.getBoolean(android.telephony.CarrierConfigManager.KEY_USE_ONLY_RSRP_FOR_LTE_SIGNAL_BAR_BOOL, false);
        }
        if (serviceState == null) {
            i = 0;
        } else {
            i = serviceState.getArfcnRsrpBoost();
        }
        int inRangeOrUnavailable = inRangeOrUnavailable(this.mRsrp + i, -140, -44);
        if (z && (updateLevelWithMeasure = updateLevelWithMeasure(inRangeOrUnavailable, intArray)) != Integer.MAX_VALUE) {
            this.mLevel = updateLevelWithMeasure;
            return;
        }
        if (!isLevelForParameter(1)) {
            i2 = Integer.MAX_VALUE;
        } else {
            i2 = updateLevelWithMeasure(inRangeOrUnavailable, intArray);
        }
        if (!isLevelForParameter(2)) {
            i3 = Integer.MAX_VALUE;
        } else {
            i3 = updateLevelWithMeasure(this.mRsrq, intArray2);
        }
        if (!isLevelForParameter(4)) {
            i4 = Integer.MAX_VALUE;
        } else {
            i4 = updateLevelWithMeasure(this.mRssnr, intArray3);
        }
        this.mLevel = java.lang.Math.min(java.lang.Math.min(i2, i3), i4);
        if (this.mLevel == Integer.MAX_VALUE) {
            if (this.mRssi > -51) {
                i5 = 0;
            } else if (this.mRssi >= -89) {
                i5 = 4;
            } else if (this.mRssi >= -97) {
                i5 = 3;
            } else if (this.mRssi >= -103) {
                i5 = 2;
            } else if (this.mRssi < -113) {
                i5 = 0;
            }
            this.mLevel = i5;
        }
    }

    private int updateLevelWithMeasure(int i, int[] iArr) {
        if (i == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (i >= iArr[3]) {
            return 4;
        }
        if (i >= iArr[2]) {
            return 3;
        }
        if (i >= iArr[1]) {
            return 2;
        }
        if (i >= iArr[0]) {
            return 1;
        }
        return 0;
    }

    public int getRsrq() {
        return this.mRsrq;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public int getRssnr() {
        return this.mRssnr;
    }

    public int getRsrp() {
        return this.mRsrp;
    }

    public int getCqiTableIndex() {
        return this.mCqiTableIndex;
    }

    public int getCqi() {
        return this.mCqi;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mRsrp;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        int i = this.mRsrp;
        if (i == Integer.MAX_VALUE) {
            return 99;
        }
        if (i <= -140) {
            return 0;
        }
        if (i >= -43) {
            return 97;
        }
        return i + 140;
    }

    public int getTimingAdvance() {
        return this.mTimingAdvance;
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRssi), java.lang.Integer.valueOf(this.mRsrp), java.lang.Integer.valueOf(this.mRsrq), java.lang.Integer.valueOf(this.mRssnr), java.lang.Integer.valueOf(this.mCqiTableIndex), java.lang.Integer.valueOf(this.mCqi), java.lang.Integer.valueOf(this.mTimingAdvance), java.lang.Integer.valueOf(this.mLevel));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellSignalStrengthLte)) {
            return false;
        }
        android.telephony.CellSignalStrengthLte cellSignalStrengthLte = (android.telephony.CellSignalStrengthLte) obj;
        return this.mRssi == cellSignalStrengthLte.mRssi && this.mRsrp == cellSignalStrengthLte.mRsrp && this.mRsrq == cellSignalStrengthLte.mRsrq && this.mRssnr == cellSignalStrengthLte.mRssnr && this.mCqiTableIndex == cellSignalStrengthLte.mCqiTableIndex && this.mCqi == cellSignalStrengthLte.mCqi && this.mTimingAdvance == cellSignalStrengthLte.mTimingAdvance && this.mLevel == cellSignalStrengthLte.mLevel;
    }

    public java.lang.String toString() {
        return "CellSignalStrengthLte: rssi=" + this.mRssi + " rsrp=" + this.mRsrp + " rsrq=" + this.mRsrq + " rssnr=" + this.mRssnr + " cqiTableIndex=" + this.mCqiTableIndex + " cqi=" + this.mCqi + " ta=" + this.mTimingAdvance + " level=" + this.mLevel + " parametersUseForLevel=" + this.mParametersUseForLevel;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRssi);
        parcel.writeInt(this.mRsrp);
        parcel.writeInt(this.mRsrq);
        parcel.writeInt(this.mRssnr);
        parcel.writeInt(this.mCqiTableIndex);
        parcel.writeInt(this.mCqi);
        parcel.writeInt(this.mTimingAdvance);
        parcel.writeInt(this.mLevel);
    }

    private CellSignalStrengthLte(android.os.Parcel parcel) {
        this.mRssi = parcel.readInt();
        this.mSignalStrength = this.mRssi;
        this.mRsrp = parcel.readInt();
        this.mRsrq = parcel.readInt();
        this.mRssnr = parcel.readInt();
        this.mCqiTableIndex = parcel.readInt();
        this.mCqi = parcel.readInt();
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

    public static int convertRssnrUnitFromTenDbToDB(int i) {
        return (int) java.lang.Math.floor(i / 10.0f);
    }

    public static int convertRssiAsuToDBm(int i) {
        if (i == 99) {
            return Integer.MAX_VALUE;
        }
        if (i < 0 || i > 31) {
            com.android.telephony.Rlog.e(LOG_TAG, "convertRssiAsuToDBm: invalid RSSI in ASU=" + i);
            return Integer.MAX_VALUE;
        }
        return (i * 2) - 113;
    }
}
