package com.android.internal.power;

/* loaded from: classes5.dex */
public class EnergyConsumerStats {
    private static final int INVALID_STATE = -1;
    public static final int NUMBER_STANDARD_POWER_BUCKETS = 10;
    public static final int POWER_BUCKET_BLUETOOTH = 5;
    public static final int POWER_BUCKET_CAMERA = 8;
    public static final int POWER_BUCKET_CPU = 3;
    public static final int POWER_BUCKET_GNSS = 6;
    public static final int POWER_BUCKET_MOBILE_RADIO = 7;
    public static final int POWER_BUCKET_PHONE = 9;
    public static final int POWER_BUCKET_SCREEN_DOZE = 1;
    public static final int POWER_BUCKET_SCREEN_ON = 0;
    public static final int POWER_BUCKET_SCREEN_OTHER = 2;
    public static final int POWER_BUCKET_UNKNOWN = -1;
    public static final int POWER_BUCKET_WIFI = 4;
    private static final java.lang.String TAG = "MeasuredEnergyStats";
    private final long[] mAccumulatedChargeMicroCoulomb;
    private com.android.internal.os.LongMultiStateCounter[] mAccumulatedMultiStateChargeMicroCoulomb;
    private final com.android.internal.power.EnergyConsumerStats.Config mConfig;
    private int mState = -1;
    private long mStateChangeTimestampMs;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StandardPowerBucket {
    }

    public static class Config {
        private final java.lang.String[] mCustomBucketNames;
        private final java.lang.String[] mStateNames;
        private final boolean[] mSupportedMultiStateBuckets;
        private final boolean[] mSupportedStandardBuckets;

        public Config(boolean[] zArr, java.lang.String[] strArr, int[] iArr, java.lang.String[] strArr2) {
            this.mSupportedStandardBuckets = zArr;
            this.mCustomBucketNames = strArr == null ? new java.lang.String[0] : strArr;
            this.mSupportedMultiStateBuckets = new boolean[zArr.length + this.mCustomBucketNames.length];
            for (int i : iArr) {
                if (this.mSupportedStandardBuckets[i]) {
                    this.mSupportedMultiStateBuckets[i] = true;
                }
            }
            this.mStateNames = strArr2 == null ? new java.lang.String[]{""} : strArr2;
        }

        public boolean isCompatible(com.android.internal.power.EnergyConsumerStats.Config config) {
            return java.util.Arrays.equals(this.mSupportedStandardBuckets, config.mSupportedStandardBuckets) && java.util.Arrays.equals(this.mCustomBucketNames, config.mCustomBucketNames) && java.util.Arrays.equals(this.mSupportedMultiStateBuckets, config.mSupportedMultiStateBuckets) && java.util.Arrays.equals(this.mStateNames, config.mStateNames);
        }

        public static void writeToParcel(com.android.internal.power.EnergyConsumerStats.Config config, android.os.Parcel parcel) {
            if (config == null) {
                parcel.writeBoolean(false);
                return;
            }
            parcel.writeBoolean(true);
            parcel.writeInt(config.mSupportedStandardBuckets.length);
            parcel.writeBooleanArray(config.mSupportedStandardBuckets);
            parcel.writeStringArray(config.mCustomBucketNames);
            int i = 0;
            for (boolean z : config.mSupportedMultiStateBuckets) {
                if (z) {
                    i++;
                }
            }
            int[] iArr = new int[i];
            int i2 = 0;
            for (int i3 = 0; i3 < config.mSupportedMultiStateBuckets.length; i3++) {
                if (config.mSupportedMultiStateBuckets[i3]) {
                    iArr[i2] = i3;
                    i2++;
                }
            }
            parcel.writeInt(i);
            parcel.writeIntArray(iArr);
            parcel.writeStringArray(config.mStateNames);
        }

        public static com.android.internal.power.EnergyConsumerStats.Config createFromParcel(android.os.Parcel parcel) {
            if (!parcel.readBoolean()) {
                return null;
            }
            boolean[] zArr = new boolean[parcel.readInt()];
            parcel.readBooleanArray(zArr);
            java.lang.String[] readStringArray = parcel.readStringArray();
            int[] iArr = new int[parcel.readInt()];
            parcel.readIntArray(iArr);
            return new com.android.internal.power.EnergyConsumerStats.Config(zArr, readStringArray, iArr, parcel.readStringArray());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getNumberOfBuckets() {
            return this.mSupportedStandardBuckets.length + this.mCustomBucketNames.length;
        }

        public boolean isSupportedBucket(int i) {
            return this.mSupportedStandardBuckets[i];
        }

        public java.lang.String[] getCustomBucketNames() {
            return this.mCustomBucketNames;
        }

        public boolean isSupportedMultiStateBucket(int i) {
            return this.mSupportedMultiStateBuckets[i];
        }

        public java.lang.String[] getStateNames() {
            return this.mStateNames;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getBucketName(int i) {
            if (com.android.internal.power.EnergyConsumerStats.isValidStandardBucket(i)) {
                return android.util.DebugUtils.valueToString(com.android.internal.power.EnergyConsumerStats.class, "POWER_BUCKET_", i);
            }
            int indexToCustomBucket = com.android.internal.power.EnergyConsumerStats.indexToCustomBucket(i);
            java.lang.StringBuilder append = new java.lang.StringBuilder().append("CUSTOM_").append(indexToCustomBucket);
            if (!android.text.TextUtils.isEmpty(this.mCustomBucketNames[indexToCustomBucket])) {
                append.append('(').append(this.mCustomBucketNames[indexToCustomBucket]).append(')');
            }
            return append.toString();
        }
    }

    public EnergyConsumerStats(com.android.internal.power.EnergyConsumerStats.Config config) {
        this.mConfig = config;
        this.mAccumulatedChargeMicroCoulomb = new long[config.getNumberOfBuckets()];
        for (int i = 0; i < 10; i++) {
            if (!this.mConfig.mSupportedStandardBuckets[i]) {
                this.mAccumulatedChargeMicroCoulomb[i] = -1;
            }
        }
    }

    public static com.android.internal.power.EnergyConsumerStats createFromParcel(com.android.internal.power.EnergyConsumerStats.Config config, android.os.Parcel parcel) {
        if (!parcel.readBoolean()) {
            return null;
        }
        return new com.android.internal.power.EnergyConsumerStats(config, parcel);
    }

    public EnergyConsumerStats(com.android.internal.power.EnergyConsumerStats.Config config, android.os.Parcel parcel) {
        this.mConfig = config;
        int readInt = parcel.readInt();
        this.mAccumulatedChargeMicroCoulomb = new long[readInt];
        parcel.readLongArray(this.mAccumulatedChargeMicroCoulomb);
        if (parcel.readBoolean()) {
            this.mAccumulatedMultiStateChargeMicroCoulomb = new com.android.internal.os.LongMultiStateCounter[readInt];
            for (int i = 0; i < readInt; i++) {
                if (parcel.readBoolean()) {
                    this.mAccumulatedMultiStateChargeMicroCoulomb[i] = com.android.internal.os.LongMultiStateCounter.CREATOR.createFromParcel(parcel);
                }
            }
            return;
        }
        this.mAccumulatedMultiStateChargeMicroCoulomb = null;
    }

    public void writeToParcel(android.os.Parcel parcel) {
        parcel.writeInt(this.mAccumulatedChargeMicroCoulomb.length);
        parcel.writeLongArray(this.mAccumulatedChargeMicroCoulomb);
        if (this.mAccumulatedMultiStateChargeMicroCoulomb != null) {
            parcel.writeBoolean(true);
            for (com.android.internal.os.LongMultiStateCounter longMultiStateCounter : this.mAccumulatedMultiStateChargeMicroCoulomb) {
                if (longMultiStateCounter != null) {
                    parcel.writeBoolean(true);
                    longMultiStateCounter.writeToParcel(parcel, 0);
                } else {
                    parcel.writeBoolean(false);
                }
            }
            return;
        }
        parcel.writeBoolean(false);
    }

    private void readSummaryFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            int readInt2 = parcel.readInt();
            long readLong = parcel.readLong();
            com.android.internal.os.LongMultiStateCounter longMultiStateCounter = null;
            if (parcel.readBoolean()) {
                com.android.internal.os.LongMultiStateCounter createFromParcel = com.android.internal.os.LongMultiStateCounter.CREATOR.createFromParcel(parcel);
                if (this.mConfig != null && createFromParcel.getStateCount() == this.mConfig.getStateNames().length) {
                    longMultiStateCounter = createFromParcel;
                }
            }
            if (readInt2 < this.mAccumulatedChargeMicroCoulomb.length) {
                setValueIfSupported(readInt2, readLong);
                if (longMultiStateCounter != null) {
                    if (this.mAccumulatedMultiStateChargeMicroCoulomb == null) {
                        this.mAccumulatedMultiStateChargeMicroCoulomb = new com.android.internal.os.LongMultiStateCounter[this.mAccumulatedChargeMicroCoulomb.length];
                    }
                    this.mAccumulatedMultiStateChargeMicroCoulomb[readInt2] = longMultiStateCounter;
                }
            }
        }
    }

    private void writeSummaryToParcel(android.os.Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        int i = 0;
        for (int i2 = 0; i2 < this.mAccumulatedChargeMicroCoulomb.length; i2++) {
            long j = this.mAccumulatedChargeMicroCoulomb[i2];
            if (j > 0) {
                parcel.writeInt(i2);
                parcel.writeLong(j);
                if (this.mAccumulatedMultiStateChargeMicroCoulomb != null && this.mAccumulatedMultiStateChargeMicroCoulomb[i2] != null) {
                    parcel.writeBoolean(true);
                    this.mAccumulatedMultiStateChargeMicroCoulomb[i2].writeToParcel(parcel, 0);
                } else {
                    parcel.writeBoolean(false);
                }
                i++;
            }
        }
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(i);
        parcel.setDataPosition(dataPosition2);
    }

    public void updateStandardBucket(int i, long j) {
        updateStandardBucket(i, j, 0L);
    }

    public void updateStandardBucket(int i, long j, long j2) {
        checkValidStandardBucket(i);
        updateEntry(i, j, j2);
    }

    public void updateCustomBucket(int i, long j) {
        updateCustomBucket(i, j, 0L);
    }

    public void updateCustomBucket(int i, long j, long j2) {
        if (!isValidCustomBucket(i)) {
            android.util.Slog.e(TAG, "Attempted to update invalid custom bucket " + i);
        } else {
            updateEntry(customBucketToIndex(i), j, j2);
        }
    }

    private void updateEntry(int i, long j, long j2) {
        if (this.mAccumulatedChargeMicroCoulomb[i] >= 0) {
            long[] jArr = this.mAccumulatedChargeMicroCoulomb;
            jArr[i] = jArr[i] + j;
            if (this.mState != -1 && this.mConfig.isSupportedMultiStateBucket(i)) {
                if (this.mAccumulatedMultiStateChargeMicroCoulomb == null) {
                    this.mAccumulatedMultiStateChargeMicroCoulomb = new com.android.internal.os.LongMultiStateCounter[this.mAccumulatedChargeMicroCoulomb.length];
                }
                com.android.internal.os.LongMultiStateCounter longMultiStateCounter = this.mAccumulatedMultiStateChargeMicroCoulomb[i];
                if (longMultiStateCounter == null) {
                    longMultiStateCounter = new com.android.internal.os.LongMultiStateCounter(this.mConfig.mStateNames.length);
                    this.mAccumulatedMultiStateChargeMicroCoulomb[i] = longMultiStateCounter;
                    longMultiStateCounter.setState(this.mState, this.mStateChangeTimestampMs);
                    longMultiStateCounter.updateValue(0L, this.mStateChangeTimestampMs);
                }
                longMultiStateCounter.updateValue(this.mAccumulatedChargeMicroCoulomb[i], j2);
                return;
            }
            return;
        }
        android.util.Slog.wtf(TAG, "Attempting to add " + j + " to unavailable bucket " + this.mConfig.getBucketName(i) + " whose value was " + this.mAccumulatedChargeMicroCoulomb[i]);
    }

    public void setState(int i, long j) {
        this.mState = i;
        this.mStateChangeTimestampMs = j;
        if (this.mAccumulatedMultiStateChargeMicroCoulomb == null) {
            this.mAccumulatedMultiStateChargeMicroCoulomb = new com.android.internal.os.LongMultiStateCounter[this.mAccumulatedChargeMicroCoulomb.length];
        }
        for (int i2 = 0; i2 < this.mAccumulatedMultiStateChargeMicroCoulomb.length; i2++) {
            com.android.internal.os.LongMultiStateCounter longMultiStateCounter = this.mAccumulatedMultiStateChargeMicroCoulomb[i2];
            if (longMultiStateCounter == null && this.mConfig.isSupportedMultiStateBucket(i2)) {
                longMultiStateCounter = new com.android.internal.os.LongMultiStateCounter(this.mConfig.mStateNames.length);
                longMultiStateCounter.updateValue(0L, j);
                this.mAccumulatedMultiStateChargeMicroCoulomb[i2] = longMultiStateCounter;
            }
            if (longMultiStateCounter != null) {
                longMultiStateCounter.setState(i, j);
            }
        }
    }

    public long getAccumulatedStandardBucketCharge(int i) {
        checkValidStandardBucket(i);
        return this.mAccumulatedChargeMicroCoulomb[i];
    }

    public long getAccumulatedStandardBucketCharge(int i, int i2) {
        com.android.internal.os.LongMultiStateCounter longMultiStateCounter;
        if (!this.mConfig.isSupportedMultiStateBucket(i)) {
            return -1L;
        }
        if (this.mAccumulatedMultiStateChargeMicroCoulomb == null || (longMultiStateCounter = this.mAccumulatedMultiStateChargeMicroCoulomb[i]) == null) {
            return 0L;
        }
        return longMultiStateCounter.getCount(i2);
    }

    public long getAccumulatedCustomBucketCharge(int i) {
        if (!isValidCustomBucket(i)) {
            return -1L;
        }
        return this.mAccumulatedChargeMicroCoulomb[customBucketToIndex(i)];
    }

    public long[] getAccumulatedCustomBucketCharges() {
        int numberCustomPowerBuckets = getNumberCustomPowerBuckets();
        long[] jArr = new long[numberCustomPowerBuckets];
        for (int i = 0; i < numberCustomPowerBuckets; i++) {
            jArr[i] = this.mAccumulatedChargeMicroCoulomb[customBucketToIndex(i)];
        }
        return jArr;
    }

    public static int getDisplayPowerBucket(int i) {
        if (android.view.Display.isOnState(i)) {
            return 0;
        }
        if (android.view.Display.isDozeState(i)) {
            return 1;
        }
        return 2;
    }

    public static com.android.internal.power.EnergyConsumerStats createAndReadSummaryFromParcel(com.android.internal.power.EnergyConsumerStats.Config config, android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == 0) {
            return null;
        }
        if (config == null) {
            new com.android.internal.power.EnergyConsumerStats(new com.android.internal.power.EnergyConsumerStats.Config(new boolean[readInt], null, new int[0], new java.lang.String[]{""})).readSummaryFromParcel(parcel);
            return null;
        }
        if (readInt != config.getNumberOfBuckets()) {
            android.util.Slog.wtf(TAG, "Size of MeasuredEnergyStats parcel (" + readInt + ") does not match config (" + config.getNumberOfBuckets() + ").");
            new com.android.internal.power.EnergyConsumerStats(config).readSummaryFromParcel(parcel);
            return null;
        }
        com.android.internal.power.EnergyConsumerStats energyConsumerStats = new com.android.internal.power.EnergyConsumerStats(config);
        energyConsumerStats.readSummaryFromParcel(parcel);
        if (!energyConsumerStats.containsInterestingData()) {
            return null;
        }
        return energyConsumerStats;
    }

    private boolean containsInterestingData() {
        for (int i = 0; i < this.mAccumulatedChargeMicroCoulomb.length; i++) {
            if (this.mAccumulatedChargeMicroCoulomb[i] > 0) {
                return true;
            }
        }
        return false;
    }

    public static void writeSummaryToParcel(com.android.internal.power.EnergyConsumerStats energyConsumerStats, android.os.Parcel parcel) {
        if (energyConsumerStats == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(energyConsumerStats.mConfig.getNumberOfBuckets());
            energyConsumerStats.writeSummaryToParcel(parcel);
        }
    }

    private void reset() {
        int numberOfBuckets = this.mConfig.getNumberOfBuckets();
        for (int i = 0; i < numberOfBuckets; i++) {
            setValueIfSupported(i, 0L);
            if (this.mAccumulatedMultiStateChargeMicroCoulomb != null && this.mAccumulatedMultiStateChargeMicroCoulomb[i] != null) {
                this.mAccumulatedMultiStateChargeMicroCoulomb[i].reset();
            }
        }
    }

    public static void resetIfNotNull(com.android.internal.power.EnergyConsumerStats energyConsumerStats) {
        if (energyConsumerStats != null) {
            energyConsumerStats.reset();
        }
    }

    private void setValueIfSupported(int i, long j) {
        if (this.mAccumulatedChargeMicroCoulomb[i] != -1) {
            this.mAccumulatedChargeMicroCoulomb[i] = j;
        }
    }

    public boolean isStandardBucketSupported(int i) {
        checkValidStandardBucket(i);
        return isIndexSupported(i);
    }

    private boolean isIndexSupported(int i) {
        return this.mAccumulatedChargeMicroCoulomb[i] != -1;
    }

    public void dump(java.io.PrintWriter printWriter) {
        com.android.internal.os.LongMultiStateCounter longMultiStateCounter;
        printWriter.print("   ");
        for (int i = 0; i < this.mAccumulatedChargeMicroCoulomb.length; i++) {
            printWriter.print(this.mConfig.getBucketName(i));
            printWriter.print(" : ");
            printWriter.print(this.mAccumulatedChargeMicroCoulomb[i]);
            if (!isIndexSupported(i)) {
                printWriter.print(" (unsupported)");
            }
            if (this.mAccumulatedMultiStateChargeMicroCoulomb != null && (longMultiStateCounter = this.mAccumulatedMultiStateChargeMicroCoulomb[i]) != null) {
                printWriter.print(" [");
                for (int i2 = 0; i2 < this.mConfig.mStateNames.length; i2++) {
                    if (i2 != 0) {
                        printWriter.print(" ");
                    }
                    printWriter.print(this.mConfig.mStateNames[i2]);
                    printWriter.print(": ");
                    printWriter.print(longMultiStateCounter.getCount(i2));
                }
                printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
            if (i != this.mAccumulatedChargeMicroCoulomb.length - 1) {
                printWriter.print(", ");
            }
        }
        printWriter.println();
    }

    public int getNumberCustomPowerBuckets() {
        return this.mAccumulatedChargeMicroCoulomb.length - 10;
    }

    private static int customBucketToIndex(int i) {
        return i + 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int indexToCustomBucket(int i) {
        return i - 10;
    }

    private static void checkValidStandardBucket(int i) {
        if (!isValidStandardBucket(i)) {
            throw new java.lang.IllegalArgumentException("Illegal StandardPowerBucket " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidStandardBucket(int i) {
        return i >= 0 && i < 10;
    }

    public boolean isValidCustomBucket(int i) {
        return i >= 0 && customBucketToIndex(i) < this.mAccumulatedChargeMicroCoulomb.length;
    }
}
