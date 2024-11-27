package android.telephony;

/* loaded from: classes3.dex */
public final class CellSignalStrengthNr extends android.telephony.CellSignalStrength implements android.os.Parcelable {
    private static final java.lang.String TAG = "CellSignalStrengthNr";
    public static final int UNKNOWN_ASU_LEVEL = 99;
    public static final int USE_SSRSRP = 1;
    public static final int USE_SSRSRQ = 2;
    public static final int USE_SSSINR = 4;
    private static final boolean VDBG = false;
    private java.util.List<java.lang.Integer> mCsiCqiReport;
    private int mCsiCqiTableIndex;
    private int mCsiRsrp;
    private int mCsiRsrq;
    private int mCsiSinr;
    private int mLevel;
    private int mParametersUseForLevel;
    private int mSsRsrp;
    private int[] mSsRsrpThresholds;
    private int mSsRsrq;
    private int[] mSsRsrqThresholds;
    private int mSsSinr;
    private int[] mSsSinrThresholds;
    private int mTimingAdvance;
    private static final android.telephony.CellSignalStrengthNr sInvalid = new android.telephony.CellSignalStrengthNr();
    public static final android.os.Parcelable.Creator<android.telephony.CellSignalStrengthNr> CREATOR = new android.os.Parcelable.Creator<android.telephony.CellSignalStrengthNr>() { // from class: android.telephony.CellSignalStrengthNr.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthNr createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.CellSignalStrengthNr(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.CellSignalStrengthNr[] newArray(int i) {
            return new android.telephony.CellSignalStrengthNr[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignalLevelAndReportCriteriaSource {
    }

    public CellSignalStrengthNr() {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        setDefaultValues();
    }

    public CellSignalStrengthNr(int i, int i2, int i3, int i4, java.util.List<java.lang.Byte> list, int i5, int i6, int i7, int i8) {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        this.mCsiRsrp = inRangeOrUnavailable(i, -156, -31);
        this.mCsiRsrq = inRangeOrUnavailable(i2, -20, -3);
        this.mCsiSinr = inRangeOrUnavailable(i3, -23, 23);
        this.mCsiCqiTableIndex = inRangeOrUnavailable(i4, 1, 3);
        this.mCsiCqiReport = (java.util.List) list.stream().map(new java.util.function.Function() { // from class: android.telephony.CellSignalStrengthNr$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer valueOf;
                valueOf = java.lang.Integer.valueOf(android.telephony.CellSignalStrengthNr.inRangeOrUnavailable(java.lang.Byte.toUnsignedInt(((java.lang.Byte) obj).byteValue()), 0, 15));
                return valueOf;
            }
        }).collect(java.util.stream.Collectors.toList());
        this.mSsRsrp = inRangeOrUnavailable(i5, -156, -31);
        this.mSsRsrq = inRangeOrUnavailable(i6, -43, 20);
        this.mSsSinr = inRangeOrUnavailable(i7, -23, 40);
        this.mTimingAdvance = inRangeOrUnavailable(i8, 0, 1282);
        updateLevel(null, null);
    }

    public CellSignalStrengthNr(int i, int i2, int i3, int i4, int i5, int i6) {
        this(i, i2, i3, Integer.MAX_VALUE, java.util.Collections.emptyList(), i4, i5, i6, Integer.MAX_VALUE);
    }

    public static int flip(int i) {
        return i != Integer.MAX_VALUE ? -i : i;
    }

    public int getSsRsrp() {
        return this.mSsRsrp;
    }

    public int getSsRsrq() {
        return this.mSsRsrq;
    }

    public int getSsSinr() {
        return this.mSsSinr;
    }

    public int getCsiRsrp() {
        return this.mCsiRsrp;
    }

    public int getCsiRsrq() {
        return this.mCsiRsrq;
    }

    public int getCsiSinr() {
        return this.mCsiSinr;
    }

    public int getCsiCqiTableIndex() {
        return this.mCsiCqiTableIndex;
    }

    public java.util.List<java.lang.Integer> getCsiCqiReport() {
        return this.mCsiCqiReport;
    }

    public int getTimingAdvanceMicros() {
        return this.mTimingAdvance;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCsiRsrp);
        parcel.writeInt(this.mCsiRsrq);
        parcel.writeInt(this.mCsiSinr);
        parcel.writeInt(this.mCsiCqiTableIndex);
        parcel.writeList(this.mCsiCqiReport);
        parcel.writeInt(this.mSsRsrp);
        parcel.writeInt(this.mSsRsrq);
        parcel.writeInt(this.mSsSinr);
        parcel.writeInt(this.mLevel);
        parcel.writeInt(this.mTimingAdvance);
    }

    private CellSignalStrengthNr(android.os.Parcel parcel) {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        this.mCsiRsrp = parcel.readInt();
        this.mCsiRsrq = parcel.readInt();
        this.mCsiSinr = parcel.readInt();
        this.mCsiCqiTableIndex = parcel.readInt();
        this.mCsiCqiReport = parcel.readArrayList(java.lang.Integer.class.getClassLoader(), java.lang.Integer.class);
        this.mSsRsrp = parcel.readInt();
        this.mSsRsrq = parcel.readInt();
        this.mSsSinr = parcel.readInt();
        this.mLevel = parcel.readInt();
        this.mTimingAdvance = parcel.readInt();
    }

    @Override // android.telephony.CellSignalStrength
    public void setDefaultValues() {
        this.mCsiRsrp = Integer.MAX_VALUE;
        this.mCsiRsrq = Integer.MAX_VALUE;
        this.mCsiSinr = Integer.MAX_VALUE;
        this.mCsiCqiTableIndex = Integer.MAX_VALUE;
        this.mCsiCqiReport = java.util.Collections.emptyList();
        this.mSsRsrp = Integer.MAX_VALUE;
        this.mSsRsrq = Integer.MAX_VALUE;
        this.mSsSinr = Integer.MAX_VALUE;
        this.mLevel = 0;
        this.mParametersUseForLevel = 1;
        this.mTimingAdvance = Integer.MAX_VALUE;
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
        int i;
        int i2;
        int i3;
        if (persistableBundle != null) {
            this.mParametersUseForLevel = persistableBundle.getInt(android.telephony.CarrierConfigManager.KEY_PARAMETERS_USE_FOR_5G_NR_SIGNAL_BAR_INT, 1);
            this.mSsRsrpThresholds = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_5G_NR_SSRSRP_THRESHOLDS_INT_ARRAY);
            this.mSsRsrqThresholds = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_5G_NR_SSRSRQ_THRESHOLDS_INT_ARRAY);
            this.mSsSinrThresholds = persistableBundle.getIntArray(android.telephony.CarrierConfigManager.KEY_5G_NR_SSSINR_THRESHOLDS_INT_ARRAY);
        } else {
            this.mParametersUseForLevel = 1;
        }
        if (!isLevelForParameter(1)) {
            i = Integer.MAX_VALUE;
        } else {
            if (serviceState == null) {
                i3 = 0;
            } else {
                i3 = serviceState.getArfcnRsrpBoost();
            }
            i = updateLevelWithMeasure(this.mSsRsrp + i3, this.mSsRsrpThresholds);
        }
        if (!isLevelForParameter(2)) {
            i2 = Integer.MAX_VALUE;
        } else {
            i2 = updateLevelWithMeasure(this.mSsRsrq, this.mSsRsrqThresholds);
        }
        this.mLevel = java.lang.Math.min(java.lang.Math.min(i, i2), isLevelForParameter(4) ? updateLevelWithMeasure(this.mSsSinr, this.mSsSinrThresholds) : Integer.MAX_VALUE);
    }

    private int updateLevelWithMeasure(int i, int[] iArr) {
        if (i == Integer.MAX_VALUE) {
            return 0;
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
        return i >= iArr[0] ? 1 : 0;
    }

    @Override // android.telephony.CellSignalStrength
    public int getAsuLevel() {
        int dbm = getDbm();
        if (dbm == Integer.MAX_VALUE) {
            return 99;
        }
        if (dbm <= -140) {
            return 0;
        }
        if (dbm >= -43) {
            return 97;
        }
        return dbm + 140;
    }

    @Override // android.telephony.CellSignalStrength
    public int getDbm() {
        return this.mSsRsrp;
    }

    public CellSignalStrengthNr(android.telephony.CellSignalStrengthNr cellSignalStrengthNr) {
        this.mSsRsrpThresholds = new int[]{-110, -90, -80, -65};
        this.mSsRsrqThresholds = new int[]{-31, -19, -7, 6};
        this.mSsSinrThresholds = new int[]{-5, 5, 15, 30};
        this.mCsiRsrp = cellSignalStrengthNr.mCsiRsrp;
        this.mCsiRsrq = cellSignalStrengthNr.mCsiRsrq;
        this.mCsiSinr = cellSignalStrengthNr.mCsiSinr;
        this.mCsiCqiTableIndex = cellSignalStrengthNr.mCsiCqiTableIndex;
        this.mCsiCqiReport = cellSignalStrengthNr.mCsiCqiReport;
        this.mSsRsrp = cellSignalStrengthNr.mSsRsrp;
        this.mSsRsrq = cellSignalStrengthNr.mSsRsrq;
        this.mSsSinr = cellSignalStrengthNr.mSsSinr;
        this.mLevel = cellSignalStrengthNr.mLevel;
        this.mParametersUseForLevel = cellSignalStrengthNr.mParametersUseForLevel;
        this.mTimingAdvance = cellSignalStrengthNr.mTimingAdvance;
    }

    @Override // android.telephony.CellSignalStrength
    public android.telephony.CellSignalStrengthNr copy() {
        return new android.telephony.CellSignalStrengthNr(this);
    }

    @Override // android.telephony.CellSignalStrength
    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mCsiRsrp), java.lang.Integer.valueOf(this.mCsiRsrq), java.lang.Integer.valueOf(this.mCsiSinr), java.lang.Integer.valueOf(this.mCsiCqiTableIndex), this.mCsiCqiReport, java.lang.Integer.valueOf(this.mSsRsrp), java.lang.Integer.valueOf(this.mSsRsrq), java.lang.Integer.valueOf(this.mSsSinr), java.lang.Integer.valueOf(this.mLevel), java.lang.Integer.valueOf(this.mTimingAdvance));
    }

    @Override // android.telephony.CellSignalStrength
    public boolean isValid() {
        return !equals(sInvalid);
    }

    @Override // android.telephony.CellSignalStrength
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.CellSignalStrengthNr)) {
            return false;
        }
        android.telephony.CellSignalStrengthNr cellSignalStrengthNr = (android.telephony.CellSignalStrengthNr) obj;
        return this.mCsiRsrp == cellSignalStrengthNr.mCsiRsrp && this.mCsiRsrq == cellSignalStrengthNr.mCsiRsrq && this.mCsiSinr == cellSignalStrengthNr.mCsiSinr && this.mCsiCqiTableIndex == cellSignalStrengthNr.mCsiCqiTableIndex && this.mCsiCqiReport.equals(cellSignalStrengthNr.mCsiCqiReport) && this.mSsRsrp == cellSignalStrengthNr.mSsRsrp && this.mSsRsrq == cellSignalStrengthNr.mSsRsrq && this.mSsSinr == cellSignalStrengthNr.mSsSinr && this.mLevel == cellSignalStrengthNr.mLevel && this.mTimingAdvance == cellSignalStrengthNr.mTimingAdvance;
    }

    public java.lang.String toString() {
        return "CellSignalStrengthNr:{" + (" csiRsrp = " + this.mCsiRsrp) + (" csiRsrq = " + this.mCsiRsrq) + (" csiCqiTableIndex = " + this.mCsiCqiTableIndex) + (" csiCqiReport = " + this.mCsiCqiReport) + (" ssRsrp = " + this.mSsRsrp) + (" ssRsrq = " + this.mSsRsrq) + (" ssSinr = " + this.mSsSinr) + (" level = " + this.mLevel) + (" parametersUseForLevel = " + this.mParametersUseForLevel) + (" timingAdvance = " + this.mTimingAdvance) + " }";
    }
}
