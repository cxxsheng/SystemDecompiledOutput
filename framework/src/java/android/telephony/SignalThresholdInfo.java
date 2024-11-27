package android.telephony;

/* loaded from: classes3.dex */
public final class SignalThresholdInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.SignalThresholdInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.SignalThresholdInfo>() { // from class: android.telephony.SignalThresholdInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SignalThresholdInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SignalThresholdInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SignalThresholdInfo[] newArray(int i) {
            return new android.telephony.SignalThresholdInfo[i];
        }
    };
    private static final int HYSTERESIS_DB_DEFAULT = 2;
    public static final int HYSTERESIS_DB_MINIMUM = 0;
    public static final int HYSTERESIS_MS_DISABLED = 0;
    public static final int MAXIMUM_NUMBER_OF_THRESHOLDS_ALLOWED = 4;
    public static final int MINIMUM_NUMBER_OF_THRESHOLDS_ALLOWED = 1;
    public static final int SIGNAL_ECNO_MAX_VALUE = 1;
    public static final int SIGNAL_ECNO_MIN_VALUE = -24;
    public static final int SIGNAL_MEASUREMENT_TYPE_ECNO = 9;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSCP = 2;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSRP = 3;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSRQ = 4;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSSI = 1;
    public static final int SIGNAL_MEASUREMENT_TYPE_RSSNR = 5;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSRSRP = 6;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSRSRQ = 7;
    public static final int SIGNAL_MEASUREMENT_TYPE_SSSINR = 8;
    public static final int SIGNAL_MEASUREMENT_TYPE_UNKNOWN = 0;
    public static final int SIGNAL_RSCP_MAX_VALUE = -25;
    public static final int SIGNAL_RSCP_MIN_VALUE = -120;
    public static final int SIGNAL_RSRP_MAX_VALUE = -44;
    public static final int SIGNAL_RSRP_MIN_VALUE = -140;
    public static final int SIGNAL_RSRQ_MAX_VALUE = 3;
    public static final int SIGNAL_RSRQ_MIN_VALUE = -34;
    public static final int SIGNAL_RSSI_MAX_VALUE = -51;
    public static final int SIGNAL_RSSI_MIN_VALUE = -113;
    public static final int SIGNAL_RSSNR_MAX_VALUE = 30;
    public static final int SIGNAL_RSSNR_MIN_VALUE = -20;
    public static final int SIGNAL_SSRSRP_MAX_VALUE = -44;
    public static final int SIGNAL_SSRSRP_MIN_VALUE = -140;
    public static final int SIGNAL_SSRSRQ_MAX_VALUE = 20;
    public static final int SIGNAL_SSRSRQ_MIN_VALUE = -43;
    public static final int SIGNAL_SSSINR_MAX_VALUE = 40;
    public static final int SIGNAL_SSSINR_MIN_VALUE = -23;
    private final int mHysteresisDb;
    private final int mHysteresisMs;
    private final boolean mIsEnabled;
    private final int mRan;
    private final int mSignalMeasurementType;
    private final int[] mThresholds;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SignalMeasurementType {
    }

    private SignalThresholdInfo(int i, int i2, int i3, int i4, int[] iArr, boolean z) {
        java.util.Objects.requireNonNull(iArr, "thresholds must not be null");
        validateRanWithMeasurementType(i, i2);
        validateThresholdRange(i2, iArr);
        this.mRan = i;
        this.mSignalMeasurementType = i2;
        this.mHysteresisMs = i3 < 0 ? 0 : i3;
        this.mHysteresisDb = i4;
        this.mThresholds = iArr;
        this.mIsEnabled = z;
    }

    public static final class Builder {
        private int mRan = 0;
        private int mSignalMeasurementType = 0;
        private int mHysteresisMs = 0;
        private int mHysteresisDb = 2;
        private int[] mThresholds = null;
        private boolean mIsEnabled = false;

        public android.telephony.SignalThresholdInfo.Builder setRadioAccessNetworkType(int i) {
            this.mRan = i;
            return this;
        }

        public android.telephony.SignalThresholdInfo.Builder setSignalMeasurementType(int i) {
            this.mSignalMeasurementType = i;
            return this;
        }

        public android.telephony.SignalThresholdInfo.Builder setHysteresisMs(int i) {
            this.mHysteresisMs = i;
            return this;
        }

        public android.telephony.SignalThresholdInfo.Builder setHysteresisDb(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("hysteresis db value should not be less than 0");
            }
            this.mHysteresisDb = i;
            return this;
        }

        public android.telephony.SignalThresholdInfo.Builder setThresholds(int[] iArr) {
            return setThresholds(iArr, false);
        }

        public android.telephony.SignalThresholdInfo.Builder setThresholds(int[] iArr, boolean z) {
            java.util.Objects.requireNonNull(iArr, "thresholds must not be null");
            if (!z && (iArr.length < 1 || iArr.length > 4)) {
                throw new java.lang.IllegalArgumentException("thresholds length must between 1 and 4");
            }
            this.mThresholds = (int[]) iArr.clone();
            java.util.Arrays.sort(this.mThresholds);
            return this;
        }

        public android.telephony.SignalThresholdInfo.Builder setIsEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        public android.telephony.SignalThresholdInfo build() {
            return new android.telephony.SignalThresholdInfo(this.mRan, this.mSignalMeasurementType, this.mHysteresisMs, this.mHysteresisDb, this.mThresholds, this.mIsEnabled);
        }
    }

    public int getRadioAccessNetworkType() {
        return this.mRan;
    }

    public int getSignalMeasurementType() {
        return this.mSignalMeasurementType;
    }

    public int getHysteresisMs() {
        return this.mHysteresisMs;
    }

    public int getHysteresisDb() {
        return this.mHysteresisDb;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public int[] getThresholds() {
        return (int[]) this.mThresholds.clone();
    }

    public static int getMinimumNumberOfThresholdsAllowed() {
        return 1;
    }

    public static int getMaximumNumberOfThresholdsAllowed() {
        return 4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRan);
        parcel.writeInt(this.mSignalMeasurementType);
        parcel.writeInt(this.mHysteresisMs);
        parcel.writeInt(this.mHysteresisDb);
        parcel.writeIntArray(this.mThresholds);
        parcel.writeBoolean(this.mIsEnabled);
    }

    private SignalThresholdInfo(android.os.Parcel parcel) {
        this.mRan = parcel.readInt();
        this.mSignalMeasurementType = parcel.readInt();
        this.mHysteresisMs = parcel.readInt();
        this.mHysteresisDb = parcel.readInt();
        this.mThresholds = parcel.createIntArray();
        this.mIsEnabled = parcel.readBoolean();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.SignalThresholdInfo)) {
            return false;
        }
        android.telephony.SignalThresholdInfo signalThresholdInfo = (android.telephony.SignalThresholdInfo) obj;
        return this.mRan == signalThresholdInfo.mRan && this.mSignalMeasurementType == signalThresholdInfo.mSignalMeasurementType && this.mHysteresisMs == signalThresholdInfo.mHysteresisMs && this.mHysteresisDb == signalThresholdInfo.mHysteresisDb && java.util.Arrays.equals(this.mThresholds, signalThresholdInfo.mThresholds) && this.mIsEnabled == signalThresholdInfo.mIsEnabled;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mRan), java.lang.Integer.valueOf(this.mSignalMeasurementType), java.lang.Integer.valueOf(this.mHysteresisMs), java.lang.Integer.valueOf(this.mHysteresisDb), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mThresholds)), java.lang.Boolean.valueOf(this.mIsEnabled));
    }

    public java.lang.String toString() {
        return "SignalThresholdInfo{mRan=" + this.mRan + " mSignalMeasurementType=" + this.mSignalMeasurementType + " mHysteresisMs=" + this.mHysteresisMs + " mHysteresisDb=" + this.mHysteresisDb + " mThresholds=" + java.util.Arrays.toString(this.mThresholds) + " mIsEnabled=" + this.mIsEnabled + "}";
    }

    private static boolean isValidThreshold(int i, int i2) {
        switch (i) {
            case 1:
                if (i2 < -113 || i2 > -51) {
                    break;
                }
                break;
            case 2:
                if (i2 < -120 || i2 > -25) {
                    break;
                }
                break;
            case 3:
                if (i2 < -140 || i2 > -44) {
                    break;
                }
            case 4:
                if (i2 < -34 || i2 > 3) {
                    break;
                }
                break;
            case 5:
                if (i2 < -20 || i2 > 30) {
                    break;
                }
                break;
            case 6:
                if (i2 < -140 || i2 > -44) {
                    break;
                }
            case 7:
                if (i2 < -43 || i2 > 20) {
                    break;
                }
                break;
            case 8:
                if (i2 < -23 || i2 > 40) {
                    break;
                }
                break;
            case 9:
                if (i2 < -24 || i2 > 1) {
                    break;
                }
        }
        return false;
    }

    private static boolean isValidRanWithMeasurementType(int i, int i2) {
        switch (i2) {
            case 1:
                return i == 1 || i == 4;
            case 2:
            case 9:
                return i == 2;
            case 3:
            case 4:
            case 5:
                return i == 3;
            case 6:
            case 7:
            case 8:
                return i == 6;
            default:
                return false;
        }
    }

    private void validateRanWithMeasurementType(int i, int i2) {
        if (!isValidRanWithMeasurementType(i, i2)) {
            throw new java.lang.IllegalArgumentException("invalid RAN: " + i + " with signal measurement type: " + i2);
        }
    }

    private void validateThresholdRange(int i, int[] iArr) {
        for (int i2 : iArr) {
            if (!isValidThreshold(i, i2)) {
                throw new java.lang.IllegalArgumentException("invalid signal measurement type: " + i + " with threshold: " + i2);
            }
        }
    }
}
