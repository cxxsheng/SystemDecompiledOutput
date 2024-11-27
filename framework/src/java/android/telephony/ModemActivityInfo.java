package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ModemActivityInfo implements android.os.Parcelable {
    private static final int TX_POWER_LEVELS = 5;
    public static final int TX_POWER_LEVEL_0 = 0;
    public static final int TX_POWER_LEVEL_1 = 1;
    public static final int TX_POWER_LEVEL_2 = 2;
    public static final int TX_POWER_LEVEL_3 = 3;
    public static final int TX_POWER_LEVEL_4 = 4;
    private android.telephony.ActivityStatsTechSpecificInfo[] mActivityStatsTechSpecificInfo;
    private int mIdleTimeMs;
    private int mSizeOfSpecificInfo;
    private int mSleepTimeMs;
    private long mTimestamp;
    private int mTotalRxTimeMs;
    private int[] mTotalTxTimeMs;
    private static final android.util.Range<java.lang.Integer>[] TX_POWER_RANGES = {new android.util.Range<>(Integer.MIN_VALUE, 0), new android.util.Range<>(0, 5), new android.util.Range<>(5, 15), new android.util.Range<>(15, 20), new android.util.Range<>(20, Integer.MAX_VALUE)};
    public static final android.os.Parcelable.Creator<android.telephony.ModemActivityInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.ModemActivityInfo>() { // from class: android.telephony.ModemActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ModemActivityInfo createFromParcel(android.os.Parcel parcel) {
            long readLong = parcel.readLong();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.createTypedArray(android.telephony.ActivityStatsTechSpecificInfo.CREATOR);
            android.telephony.ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = new android.telephony.ActivityStatsTechSpecificInfo[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                activityStatsTechSpecificInfoArr[i] = (android.telephony.ActivityStatsTechSpecificInfo) parcelableArr[i];
            }
            return new android.telephony.ModemActivityInfo(readLong, readInt, readInt2, activityStatsTechSpecificInfoArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ModemActivityInfo[] newArray(int i) {
            return new android.telephony.ModemActivityInfo[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TxPowerLevel {
    }

    public static int getNumTxPowerLevels() {
        return 5;
    }

    public ModemActivityInfo(long j, int i, int i2, int[] iArr, int i3) {
        java.util.Objects.requireNonNull(iArr);
        if (iArr.length != 5) {
            throw new java.lang.IllegalArgumentException("txTimeMs must have length == TX_POWER_LEVELS");
        }
        this.mTimestamp = j;
        this.mSleepTimeMs = i;
        this.mIdleTimeMs = i2;
        this.mTotalTxTimeMs = iArr;
        this.mTotalRxTimeMs = i3;
        this.mActivityStatsTechSpecificInfo = new android.telephony.ActivityStatsTechSpecificInfo[1];
        this.mSizeOfSpecificInfo = this.mActivityStatsTechSpecificInfo.length;
        this.mActivityStatsTechSpecificInfo[0] = new android.telephony.ActivityStatsTechSpecificInfo(0, 0, iArr, i3);
    }

    public ModemActivityInfo(long j, long j2, long j3, int[] iArr, long j4) {
        this(j, (int) j2, (int) j3, iArr, (int) j4);
    }

    public ModemActivityInfo(long j, int i, int i2, android.telephony.ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr) {
        this.mTimestamp = j;
        this.mSleepTimeMs = i;
        this.mIdleTimeMs = i2;
        this.mActivityStatsTechSpecificInfo = activityStatsTechSpecificInfoArr;
        this.mSizeOfSpecificInfo = this.mActivityStatsTechSpecificInfo.length;
        this.mTotalTxTimeMs = new int[5];
        for (int i3 = 0; i3 < getNumTxPowerLevels(); i3++) {
            for (int i4 = 0; i4 < getSpecificInfoLength(); i4++) {
                this.mTotalTxTimeMs[i3] = this.mTotalTxTimeMs[i3] + ((int) this.mActivityStatsTechSpecificInfo[i4].getTransmitTimeMillis(i3));
            }
        }
        this.mTotalRxTimeMs = 0;
        for (int i5 = 0; i5 < getSpecificInfoLength(); i5++) {
            this.mTotalRxTimeMs += (int) this.mActivityStatsTechSpecificInfo[i5].getReceiveTimeMillis();
        }
    }

    public ModemActivityInfo(long j, long j2, long j3, android.telephony.ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr) {
        this(j, (int) j2, (int) j3, activityStatsTechSpecificInfoArr);
    }

    public java.lang.String toString() {
        return "ModemActivityInfo{ mTimestamp=" + this.mTimestamp + " mSleepTimeMs=" + this.mSleepTimeMs + " mIdleTimeMs=" + this.mIdleTimeMs + " mActivityStatsTechSpecificInfo=" + java.util.Arrays.toString(this.mActivityStatsTechSpecificInfo) + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mTimestamp);
        parcel.writeInt(this.mSleepTimeMs);
        parcel.writeInt(this.mIdleTimeMs);
        parcel.writeTypedArray(this.mActivityStatsTechSpecificInfo, i);
    }

    public long getTimestampMillis() {
        return this.mTimestamp;
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public long getTransmitDurationMillisAtPowerLevel(int i) {
        long j = 0;
        for (int i2 = 0; i2 < getSpecificInfoLength(); i2++) {
            j += this.mActivityStatsTechSpecificInfo[i2].getTransmitTimeMillis(i);
        }
        return j;
    }

    public long getTransmitDurationMillisAtPowerLevel(int i, int i2) {
        for (int i3 = 0; i3 < getSpecificInfoLength(); i3++) {
            if (this.mActivityStatsTechSpecificInfo[i3].getRat() == i2) {
                return this.mActivityStatsTechSpecificInfo[i3].getTransmitTimeMillis(i);
            }
        }
        return 0L;
    }

    public long getTransmitDurationMillisAtPowerLevel(int i, int i2, int i3) {
        for (int i4 = 0; i4 < getSpecificInfoLength(); i4++) {
            if (this.mActivityStatsTechSpecificInfo[i4].getRat() == i2 && this.mActivityStatsTechSpecificInfo[i4].getFrequencyRange() == i3) {
                return this.mActivityStatsTechSpecificInfo[i4].getTransmitTimeMillis(i);
            }
        }
        return 0L;
    }

    public android.util.Range<java.lang.Integer> getTransmitPowerRange(int i) {
        return TX_POWER_RANGES[i];
    }

    public int getSpecificInfoRat(int i) {
        return this.mActivityStatsTechSpecificInfo[i].getRat();
    }

    public int getSpecificInfoFrequencyRange(int i) {
        return this.mActivityStatsTechSpecificInfo[i].getFrequencyRange();
    }

    public void setTransmitTimeMillis(int[] iArr) {
        this.mTotalTxTimeMs = java.util.Arrays.copyOf(iArr, 5);
    }

    public void setTransmitTimeMillis(int i, int[] iArr) {
        for (int i2 = 0; i2 < getSpecificInfoLength(); i2++) {
            if (this.mActivityStatsTechSpecificInfo[i2].getRat() == i) {
                this.mActivityStatsTechSpecificInfo[i2].setTransmitTimeMillis(iArr);
            }
        }
    }

    public void setTransmitTimeMillis(int i, int i2, int[] iArr) {
        for (int i3 = 0; i3 < getSpecificInfoLength(); i3++) {
            if (this.mActivityStatsTechSpecificInfo[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                this.mActivityStatsTechSpecificInfo[i3].setTransmitTimeMillis(iArr);
            }
        }
    }

    public int[] getTransmitTimeMillis() {
        return this.mTotalTxTimeMs;
    }

    public int[] getTransmitTimeMillis(int i) {
        for (int i2 = 0; i2 < this.mActivityStatsTechSpecificInfo.length; i2++) {
            if (this.mActivityStatsTechSpecificInfo[i2].getRat() == i) {
                return this.mActivityStatsTechSpecificInfo[i2].getTransmitTimeMillis();
            }
        }
        return new int[5];
    }

    public int[] getTransmitTimeMillis(int i, int i2) {
        for (int i3 = 0; i3 < this.mActivityStatsTechSpecificInfo.length; i3++) {
            if (this.mActivityStatsTechSpecificInfo[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                return this.mActivityStatsTechSpecificInfo[i3].getTransmitTimeMillis();
            }
        }
        return new int[5];
    }

    public long getSleepTimeMillis() {
        return this.mSleepTimeMs;
    }

    public void setSleepTimeMillis(int i) {
        this.mSleepTimeMs = i;
    }

    public void setSleepTimeMillis(long j) {
        this.mSleepTimeMs = (int) j;
    }

    public android.telephony.ModemActivityInfo getDelta(android.telephony.ModemActivityInfo modemActivityInfo) {
        android.telephony.ActivityStatsTechSpecificInfo[] activityStatsTechSpecificInfoArr = new android.telephony.ActivityStatsTechSpecificInfo[modemActivityInfo.getSpecificInfoLength()];
        for (int i = 0; i < modemActivityInfo.getSpecificInfoLength(); i++) {
            boolean z = false;
            for (int i2 = 0; i2 < getSpecificInfoLength(); i2++) {
                int rat = this.mActivityStatsTechSpecificInfo[i2].getRat();
                if (rat == modemActivityInfo.mActivityStatsTechSpecificInfo[i].getRat() && !z) {
                    if (this.mActivityStatsTechSpecificInfo[i2].getRat() == 6) {
                        if (modemActivityInfo.mActivityStatsTechSpecificInfo[i].getFrequencyRange() == this.mActivityStatsTechSpecificInfo[i2].getFrequencyRange()) {
                            int frequencyRange = this.mActivityStatsTechSpecificInfo[i2].getFrequencyRange();
                            int[] iArr = new int[5];
                            for (int i3 = 0; i3 < 5; i3++) {
                                iArr[i3] = (int) (modemActivityInfo.getTransmitDurationMillisAtPowerLevel(i3, rat, frequencyRange) - getTransmitDurationMillisAtPowerLevel(i3, rat, frequencyRange));
                            }
                            activityStatsTechSpecificInfoArr[i] = new android.telephony.ActivityStatsTechSpecificInfo(rat, frequencyRange, iArr, (int) (modemActivityInfo.getReceiveTimeMillis(rat, frequencyRange) - getReceiveTimeMillis(rat, frequencyRange)));
                            z = true;
                        }
                    } else {
                        int[] iArr2 = new int[5];
                        for (int i4 = 0; i4 < 5; i4++) {
                            iArr2[i4] = (int) (modemActivityInfo.getTransmitDurationMillisAtPowerLevel(i4, rat) - getTransmitDurationMillisAtPowerLevel(i4, rat));
                        }
                        activityStatsTechSpecificInfoArr[i] = new android.telephony.ActivityStatsTechSpecificInfo(rat, 0, iArr2, (int) (modemActivityInfo.getReceiveTimeMillis(rat) - getReceiveTimeMillis(rat)));
                        z = true;
                    }
                }
            }
            if (!z) {
                activityStatsTechSpecificInfoArr[i] = modemActivityInfo.mActivityStatsTechSpecificInfo[i];
            }
        }
        return new android.telephony.ModemActivityInfo(modemActivityInfo.getTimestampMillis(), modemActivityInfo.getSleepTimeMillis() - getSleepTimeMillis(), modemActivityInfo.getIdleTimeMillis() - getIdleTimeMillis(), activityStatsTechSpecificInfoArr);
    }

    public long getIdleTimeMillis() {
        return this.mIdleTimeMs;
    }

    public void setIdleTimeMillis(int i) {
        this.mIdleTimeMs = i;
    }

    public void setIdleTimeMillis(long j) {
        this.mIdleTimeMs = (int) j;
    }

    public long getReceiveTimeMillis() {
        return this.mTotalRxTimeMs;
    }

    public long getReceiveTimeMillis(int i) {
        for (int i2 = 0; i2 < this.mActivityStatsTechSpecificInfo.length; i2++) {
            if (this.mActivityStatsTechSpecificInfo[i2].getRat() == i) {
                return this.mActivityStatsTechSpecificInfo[i2].getReceiveTimeMillis();
            }
        }
        return 0L;
    }

    public long getReceiveTimeMillis(int i, int i2) {
        for (int i3 = 0; i3 < this.mActivityStatsTechSpecificInfo.length; i3++) {
            if (this.mActivityStatsTechSpecificInfo[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                return this.mActivityStatsTechSpecificInfo[i3].getReceiveTimeMillis();
            }
        }
        return 0L;
    }

    public void setReceiveTimeMillis(int i) {
        this.mTotalRxTimeMs = i;
    }

    public void setReceiveTimeMillis(long j) {
        this.mTotalRxTimeMs = (int) j;
    }

    public void setReceiveTimeMillis(int i, long j) {
        for (int i2 = 0; i2 < this.mActivityStatsTechSpecificInfo.length; i2++) {
            if (this.mActivityStatsTechSpecificInfo[i2].getRat() == i) {
                this.mActivityStatsTechSpecificInfo[i2].setReceiveTimeMillis(j);
            }
        }
    }

    public void setReceiveTimeMillis(int i, int i2, long j) {
        for (int i3 = 0; i3 < this.mActivityStatsTechSpecificInfo.length; i3++) {
            if (this.mActivityStatsTechSpecificInfo[i3].getRat() == i && this.mActivityStatsTechSpecificInfo[i3].getFrequencyRange() == i2) {
                this.mActivityStatsTechSpecificInfo[i3].setReceiveTimeMillis(j);
            }
        }
    }

    public int getSpecificInfoLength() {
        return this.mSizeOfSpecificInfo;
    }

    public boolean isValid() {
        if (this.mActivityStatsTechSpecificInfo == null) {
            return false;
        }
        boolean z = true;
        boolean z2 = true;
        for (int i = 0; i < getSpecificInfoLength(); i++) {
            if (!this.mActivityStatsTechSpecificInfo[i].isTxPowerValid()) {
                z = false;
            }
            if (!this.mActivityStatsTechSpecificInfo[i].isRxPowerValid()) {
                z2 = false;
            }
        }
        return z && z2 && getIdleTimeMillis() >= 0 && getSleepTimeMillis() >= 0 && !isEmpty();
    }

    public boolean isEmpty() {
        boolean z = true;
        boolean z2 = true;
        for (int i = 0; i < getSpecificInfoLength(); i++) {
            if (!this.mActivityStatsTechSpecificInfo[i].isTxPowerEmpty()) {
                z = false;
            }
            if (!this.mActivityStatsTechSpecificInfo[i].isRxPowerEmpty()) {
                z2 = false;
            }
        }
        return z && getIdleTimeMillis() == 0 && getSleepTimeMillis() == 0 && z2;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.ModemActivityInfo modemActivityInfo = (android.telephony.ModemActivityInfo) obj;
        if (this.mTimestamp == modemActivityInfo.mTimestamp && this.mSleepTimeMs == modemActivityInfo.mSleepTimeMs && this.mIdleTimeMs == modemActivityInfo.mIdleTimeMs && this.mSizeOfSpecificInfo == modemActivityInfo.mSizeOfSpecificInfo && java.util.Arrays.equals(this.mActivityStatsTechSpecificInfo, modemActivityInfo.mActivityStatsTechSpecificInfo)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Objects.hash(java.lang.Long.valueOf(this.mTimestamp), java.lang.Integer.valueOf(this.mSleepTimeMs), java.lang.Integer.valueOf(this.mIdleTimeMs), java.lang.Integer.valueOf(this.mTotalRxTimeMs)) * 31) + java.util.Arrays.hashCode(this.mTotalTxTimeMs);
    }
}
