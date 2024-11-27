package android.telephony;

/* loaded from: classes3.dex */
public final class ActivityStatsTechSpecificInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.ActivityStatsTechSpecificInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.ActivityStatsTechSpecificInfo>() { // from class: android.telephony.ActivityStatsTechSpecificInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ActivityStatsTechSpecificInfo createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int[] iArr = new int[5];
            parcel.readIntArray(iArr);
            return new android.telephony.ActivityStatsTechSpecificInfo(readInt, readInt2, iArr, parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.ActivityStatsTechSpecificInfo[] newArray(int i) {
            return new android.telephony.ActivityStatsTechSpecificInfo[i];
        }
    };
    private static final int TX_POWER_LEVELS = 5;
    private int mFrequencyRange;
    private int mRat;
    private int mRxTimeMs;
    private int[] mTxTimeMs;

    public ActivityStatsTechSpecificInfo(int i, int i2, int[] iArr, int i3) {
        java.util.Objects.requireNonNull(iArr);
        if (iArr.length != 5) {
            throw new java.lang.IllegalArgumentException("txTimeMs must have length == TX_POWER_LEVELS");
        }
        this.mRat = i;
        this.mFrequencyRange = i2;
        this.mTxTimeMs = iArr;
        this.mRxTimeMs = i3;
    }

    public int getRat() {
        return this.mRat;
    }

    public int getFrequencyRange() {
        return this.mFrequencyRange;
    }

    public long getTransmitTimeMillis(int i) {
        return this.mTxTimeMs[i];
    }

    public int[] getTransmitTimeMillis() {
        return this.mTxTimeMs;
    }

    public long getReceiveTimeMillis() {
        return this.mRxTimeMs;
    }

    public void setRat(int i) {
        this.mRat = i;
    }

    public void setFrequencyRange(int i) {
        this.mFrequencyRange = i;
    }

    public void setReceiveTimeMillis(int i) {
        this.mRxTimeMs = i;
    }

    public void setReceiveTimeMillis(long j) {
        this.mRxTimeMs = (int) j;
    }

    public void setTransmitTimeMillis(int[] iArr) {
        this.mTxTimeMs = java.util.Arrays.copyOf(iArr, 5);
    }

    static /* synthetic */ boolean lambda$isTxPowerValid$0(int i) {
        return i >= 0;
    }

    public boolean isTxPowerValid() {
        return java.util.Arrays.stream(this.mTxTimeMs).allMatch(new java.util.function.IntPredicate() { // from class: android.telephony.ActivityStatsTechSpecificInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return android.telephony.ActivityStatsTechSpecificInfo.lambda$isTxPowerValid$0(i);
            }
        });
    }

    public boolean isRxPowerValid() {
        return getReceiveTimeMillis() >= 0;
    }

    public boolean isTxPowerEmpty() {
        return this.mTxTimeMs == null || this.mTxTimeMs.length == 0 || java.util.Arrays.stream(this.mTxTimeMs).allMatch(new java.util.function.IntPredicate() { // from class: android.telephony.ActivityStatsTechSpecificInfo$$ExternalSyntheticLambda1
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return android.telephony.ActivityStatsTechSpecificInfo.lambda$isTxPowerEmpty$1(i);
            }
        });
    }

    static /* synthetic */ boolean lambda$isTxPowerEmpty$1(int i) {
        return i == 0;
    }

    public boolean isRxPowerEmpty() {
        return getReceiveTimeMillis() == 0;
    }

    public int hashCode() {
        return (java.util.Objects.hash(java.lang.Integer.valueOf(this.mRat), java.lang.Integer.valueOf(this.mFrequencyRange), java.lang.Integer.valueOf(this.mRxTimeMs)) * 31) + java.util.Arrays.hashCode(this.mTxTimeMs);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.telephony.ActivityStatsTechSpecificInfo)) {
            return false;
        }
        android.telephony.ActivityStatsTechSpecificInfo activityStatsTechSpecificInfo = (android.telephony.ActivityStatsTechSpecificInfo) obj;
        return this.mRat == activityStatsTechSpecificInfo.mRat && this.mFrequencyRange == activityStatsTechSpecificInfo.mFrequencyRange && java.util.Arrays.equals(this.mTxTimeMs, activityStatsTechSpecificInfo.mTxTimeMs) && this.mRxTimeMs == activityStatsTechSpecificInfo.mRxTimeMs;
    }

    private static java.lang.String ratToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "GERAN";
            case 2:
                return "UTRAN";
            case 3:
                return "EUTRAN";
            case 4:
                return "CDMA2000";
            case 5:
                return "IWLAN";
            case 6:
                return "NGRAN";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public java.lang.String toString() {
        return "{mRat=" + ratToString(this.mRat) + ",mFrequencyRange=" + android.telephony.ServiceState.frequencyRangeToString(this.mFrequencyRange) + ",mTxTimeMs[]=" + java.util.Arrays.toString(this.mTxTimeMs) + ",mRxTimeMs=" + this.mRxTimeMs + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRat);
        parcel.writeInt(this.mFrequencyRange);
        parcel.writeIntArray(this.mTxTimeMs);
        parcel.writeInt(this.mRxTimeMs);
    }
}
