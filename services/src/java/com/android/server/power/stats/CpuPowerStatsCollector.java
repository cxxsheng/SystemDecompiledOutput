package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class CpuPowerStatsCollector extends com.android.server.power.stats.PowerStatsCollector {
    private static final int DEFAULT_CPU_POWER_BRACKETS = 3;
    private static final int DEFAULT_CPU_POWER_BRACKETS_PER_ENERGY_CONSUMER = 2;
    private static final long ENERGY_UNSPECIFIED = -1;
    private static final long NANOS_PER_MILLIS = 1000000;
    private static final long POWER_STATS_ENERGY_CONSUMERS_TIMEOUT = 20000;
    private static final java.lang.String TAG = "CpuPowerStatsCollector";
    private int[] mCpuEnergyConsumerIds;
    private com.android.internal.os.PowerStats mCpuPowerStats;
    private final com.android.internal.os.CpuScalingPolicies mCpuScalingPolicies;
    private long[] mCpuTimeByScalingStep;
    private final int mDefaultCpuPowerBrackets;
    private final int mDefaultCpuPowerBracketsPerEnergyConsumer;
    private boolean mIsInitialized;
    private boolean mIsPerUidTimeInStateSupported;
    private final com.android.server.power.stats.CpuPowerStatsCollector.KernelCpuStatsReader mKernelCpuStatsReader;
    private long[] mLastConsumedEnergyUws;
    private long mLastUpdateTimestampNanos;
    private long mLastUpdateUptimeMillis;
    private int mLastVoltageMv;
    private com.android.server.power.stats.CpuPowerStatsCollector.CpuStatsArrayLayout mLayout;
    private final com.android.internal.os.PowerProfile mPowerProfile;
    private com.android.internal.os.PowerStats.Descriptor mPowerStatsDescriptor;
    private android.power.PowerStatsInternal mPowerStatsInternal;
    private final java.util.function.Supplier<android.power.PowerStatsInternal> mPowerStatsSupplier;
    private long[] mTempCpuTimeByScalingStep;
    private long[] mTempUidStats;
    private final com.android.server.power.stats.PowerStatsUidResolver mUidResolver;
    private final android.util.SparseArray<com.android.server.power.stats.CpuPowerStatsCollector.UidStats> mUidStats;
    private final java.util.function.IntSupplier mVoltageSupplier;

    @com.android.internal.annotations.VisibleForNative
    interface KernelCpuStatsCallback {
        @com.android.internal.annotations.Keep
        void processUidStats(int i, long[] jArr);
    }

    public static class KernelCpuStatsReader {
        protected native boolean nativeIsSupportedFeature();

        protected native long nativeReadCpuStats(com.android.server.power.stats.CpuPowerStatsCollector.KernelCpuStatsCallback kernelCpuStatsCallback, int[] iArr, long j, long[] jArr, long[] jArr2);
    }

    private static class UidStats {
        public long[] stats;
        public long[] timeByPowerBracket;

        private UidStats() {
        }
    }

    public static class CpuStatsArrayLayout extends com.android.server.power.stats.PowerStatsCollector.StatsArrayLayout {
        private static final java.lang.String EXTRA_DEVICE_TIME_BY_CLUSTER_COUNT = "dcc";
        private static final java.lang.String EXTRA_DEVICE_TIME_BY_CLUSTER_POSITION = "dc";
        private static final java.lang.String EXTRA_DEVICE_TIME_BY_SCALING_STEP_COUNT = "dtc";
        private static final java.lang.String EXTRA_DEVICE_TIME_BY_SCALING_STEP_POSITION = "dt";
        private static final java.lang.String EXTRA_UID_BRACKETS_POSITION = "ub";
        private static final java.lang.String EXTRA_UID_STATS_SCALING_STEP_TO_POWER_BRACKET = "us";
        private int mDeviceCpuTimeByClusterCount;
        private int mDeviceCpuTimeByClusterPosition;
        private int mDeviceCpuTimeByScalingStepCount;
        private int mDeviceCpuTimeByScalingStepPosition;
        private int[] mScalingStepToPowerBracketMap;
        private int mUidPowerBracketCount;
        private int mUidPowerBracketsPosition;

        public void addDeviceSectionCpuTimeByScalingStep(int i) {
            this.mDeviceCpuTimeByScalingStepPosition = addDeviceSection(i);
            this.mDeviceCpuTimeByScalingStepCount = i;
        }

        public int getCpuScalingStepCount() {
            return this.mDeviceCpuTimeByScalingStepCount;
        }

        public void setTimeByScalingStep(long[] jArr, int i, long j) {
            jArr[this.mDeviceCpuTimeByScalingStepPosition + i] = j;
        }

        public long getTimeByScalingStep(long[] jArr, int i) {
            return jArr[this.mDeviceCpuTimeByScalingStepPosition + i];
        }

        public void addDeviceSectionCpuTimeByCluster(int i) {
            this.mDeviceCpuTimeByClusterPosition = addDeviceSection(i);
            this.mDeviceCpuTimeByClusterCount = i;
        }

        public int getCpuClusterCount() {
            return this.mDeviceCpuTimeByClusterCount;
        }

        public void setTimeByCluster(long[] jArr, int i, long j) {
            jArr[this.mDeviceCpuTimeByClusterPosition + i] = j;
        }

        public long getTimeByCluster(long[] jArr, int i) {
            return jArr[this.mDeviceCpuTimeByClusterPosition + i];
        }

        public void addUidSectionCpuTimeByPowerBracket(int[] iArr) {
            this.mScalingStepToPowerBracketMap = iArr;
            updatePowerBracketCount();
            this.mUidPowerBracketsPosition = addUidSection(this.mUidPowerBracketCount);
        }

        private void updatePowerBracketCount() {
            this.mUidPowerBracketCount = 1;
            for (int i : this.mScalingStepToPowerBracketMap) {
                if (i >= this.mUidPowerBracketCount) {
                    this.mUidPowerBracketCount = i + 1;
                }
            }
        }

        public int[] getScalingStepToPowerBracketMap() {
            return this.mScalingStepToPowerBracketMap;
        }

        public int getCpuPowerBracketCount() {
            return this.mUidPowerBracketCount;
        }

        public void setUidTimeByPowerBracket(long[] jArr, int i, long j) {
            jArr[this.mUidPowerBracketsPosition + i] = j;
        }

        public long getUidTimeByPowerBracket(long[] jArr, int i) {
            return jArr[this.mUidPowerBracketsPosition + i];
        }

        @Override // com.android.server.power.stats.PowerStatsCollector.StatsArrayLayout
        public void toExtras(android.os.PersistableBundle persistableBundle) {
            super.toExtras(persistableBundle);
            persistableBundle.putInt(EXTRA_DEVICE_TIME_BY_SCALING_STEP_POSITION, this.mDeviceCpuTimeByScalingStepPosition);
            persistableBundle.putInt(EXTRA_DEVICE_TIME_BY_SCALING_STEP_COUNT, this.mDeviceCpuTimeByScalingStepCount);
            persistableBundle.putInt(EXTRA_DEVICE_TIME_BY_CLUSTER_POSITION, this.mDeviceCpuTimeByClusterPosition);
            persistableBundle.putInt(EXTRA_DEVICE_TIME_BY_CLUSTER_COUNT, this.mDeviceCpuTimeByClusterCount);
            persistableBundle.putInt(EXTRA_UID_BRACKETS_POSITION, this.mUidPowerBracketsPosition);
            putIntArray(persistableBundle, EXTRA_UID_STATS_SCALING_STEP_TO_POWER_BRACKET, this.mScalingStepToPowerBracketMap);
        }

        @Override // com.android.server.power.stats.PowerStatsCollector.StatsArrayLayout
        public void fromExtras(android.os.PersistableBundle persistableBundle) {
            super.fromExtras(persistableBundle);
            this.mDeviceCpuTimeByScalingStepPosition = persistableBundle.getInt(EXTRA_DEVICE_TIME_BY_SCALING_STEP_POSITION);
            this.mDeviceCpuTimeByScalingStepCount = persistableBundle.getInt(EXTRA_DEVICE_TIME_BY_SCALING_STEP_COUNT);
            this.mDeviceCpuTimeByClusterPosition = persistableBundle.getInt(EXTRA_DEVICE_TIME_BY_CLUSTER_POSITION);
            this.mDeviceCpuTimeByClusterCount = persistableBundle.getInt(EXTRA_DEVICE_TIME_BY_CLUSTER_COUNT);
            this.mUidPowerBracketsPosition = persistableBundle.getInt(EXTRA_UID_BRACKETS_POSITION);
            this.mScalingStepToPowerBracketMap = getIntArray(persistableBundle, EXTRA_UID_STATS_SCALING_STEP_TO_POWER_BRACKET);
            if (this.mScalingStepToPowerBracketMap == null) {
                this.mScalingStepToPowerBracketMap = new int[this.mDeviceCpuTimeByScalingStepCount];
            }
            updatePowerBracketCount();
        }
    }

    public CpuPowerStatsCollector(com.android.internal.os.CpuScalingPolicies cpuScalingPolicies, com.android.internal.os.PowerProfile powerProfile, com.android.server.power.stats.PowerStatsUidResolver powerStatsUidResolver, java.util.function.IntSupplier intSupplier, android.os.Handler handler, long j) {
        this(cpuScalingPolicies, powerProfile, handler, new com.android.server.power.stats.CpuPowerStatsCollector.KernelCpuStatsReader(), powerStatsUidResolver, new java.util.function.Supplier() { // from class: com.android.server.power.stats.CpuPowerStatsCollector$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.power.PowerStatsInternal lambda$new$0;
                lambda$new$0 = com.android.server.power.stats.CpuPowerStatsCollector.lambda$new$0();
                return lambda$new$0;
            }
        }, intSupplier, j, com.android.internal.os.Clock.SYSTEM_CLOCK, 3, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.power.PowerStatsInternal lambda$new$0() {
        return (android.power.PowerStatsInternal) com.android.server.LocalServices.getService(android.power.PowerStatsInternal.class);
    }

    public CpuPowerStatsCollector(com.android.internal.os.CpuScalingPolicies cpuScalingPolicies, com.android.internal.os.PowerProfile powerProfile, android.os.Handler handler, com.android.server.power.stats.CpuPowerStatsCollector.KernelCpuStatsReader kernelCpuStatsReader, com.android.server.power.stats.PowerStatsUidResolver powerStatsUidResolver, java.util.function.Supplier<android.power.PowerStatsInternal> supplier, java.util.function.IntSupplier intSupplier, long j, com.android.internal.os.Clock clock, int i, int i2) {
        super(handler, j, clock);
        this.mUidStats = new android.util.SparseArray<>();
        this.mCpuEnergyConsumerIds = new int[0];
        this.mCpuScalingPolicies = cpuScalingPolicies;
        this.mPowerProfile = powerProfile;
        this.mKernelCpuStatsReader = kernelCpuStatsReader;
        this.mUidResolver = powerStatsUidResolver;
        this.mPowerStatsSupplier = supplier;
        this.mVoltageSupplier = intSupplier;
        this.mDefaultCpuPowerBrackets = i;
        this.mDefaultCpuPowerBracketsPerEnergyConsumer = i2;
    }

    private boolean ensureInitialized() {
        if (this.mIsInitialized) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        this.mIsPerUidTimeInStateSupported = this.mKernelCpuStatsReader.nativeIsSupportedFeature();
        this.mPowerStatsInternal = this.mPowerStatsSupplier.get();
        if (this.mPowerStatsInternal != null) {
            readCpuEnergyConsumerIds();
        }
        int scalingStepCount = this.mCpuScalingPolicies.getScalingStepCount();
        this.mCpuTimeByScalingStep = new long[scalingStepCount];
        this.mTempCpuTimeByScalingStep = new long[scalingStepCount];
        int[] initPowerBrackets = initPowerBrackets();
        this.mLayout = new com.android.server.power.stats.CpuPowerStatsCollector.CpuStatsArrayLayout();
        this.mLayout.addDeviceSectionCpuTimeByScalingStep(scalingStepCount);
        this.mLayout.addDeviceSectionCpuTimeByCluster(this.mCpuScalingPolicies.getPolicies().length);
        this.mLayout.addDeviceSectionUsageDuration();
        this.mLayout.addDeviceSectionEnergyConsumers(this.mCpuEnergyConsumerIds.length);
        this.mLayout.addDeviceSectionPowerEstimate();
        this.mLayout.addUidSectionCpuTimeByPowerBracket(initPowerBrackets);
        this.mLayout.addUidSectionPowerEstimate();
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        this.mLayout.toExtras(persistableBundle);
        this.mPowerStatsDescriptor = new com.android.internal.os.PowerStats.Descriptor(1, this.mLayout.getDeviceStatsArrayLength(), this.mLayout.getUidStatsArrayLength(), persistableBundle);
        this.mCpuPowerStats = new com.android.internal.os.PowerStats(this.mPowerStatsDescriptor);
        this.mTempUidStats = new long[this.mLayout.getCpuPowerBracketCount()];
        this.mIsInitialized = true;
        return true;
    }

    private void readCpuEnergyConsumerIds() {
        android.hardware.power.stats.EnergyConsumer[] energyConsumerInfo = this.mPowerStatsInternal.getEnergyConsumerInfo();
        if (energyConsumerInfo == null) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.power.stats.EnergyConsumer energyConsumer : energyConsumerInfo) {
            if (energyConsumer.type == 2) {
                arrayList.add(energyConsumer);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        arrayList.sort(java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.power.stats.CpuPowerStatsCollector$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$readCpuEnergyConsumerIds$1;
                lambda$readCpuEnergyConsumerIds$1 = com.android.server.power.stats.CpuPowerStatsCollector.lambda$readCpuEnergyConsumerIds$1((android.hardware.power.stats.EnergyConsumer) obj);
                return lambda$readCpuEnergyConsumerIds$1;
            }
        }));
        this.mCpuEnergyConsumerIds = new int[arrayList.size()];
        for (int i = 0; i < this.mCpuEnergyConsumerIds.length; i++) {
            this.mCpuEnergyConsumerIds[i] = ((android.hardware.power.stats.EnergyConsumer) arrayList.get(i)).id;
        }
        this.mLastConsumedEnergyUws = new long[arrayList.size()];
        java.util.Arrays.fill(this.mLastConsumedEnergyUws, -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$readCpuEnergyConsumerIds$1(android.hardware.power.stats.EnergyConsumer energyConsumer) {
        return java.lang.Integer.valueOf(energyConsumer.ordinal);
    }

    private int[] initPowerBrackets() {
        if (this.mPowerProfile.getCpuPowerBracketCount() != -1) {
            return initPowerBracketsFromPowerProfile();
        }
        if (this.mCpuEnergyConsumerIds.length == 0 || this.mCpuEnergyConsumerIds.length == 1) {
            return initDefaultPowerBrackets(this.mDefaultCpuPowerBrackets);
        }
        if (this.mCpuScalingPolicies.getPolicies().length == this.mCpuEnergyConsumerIds.length) {
            return initPowerBracketsByCluster(this.mDefaultCpuPowerBracketsPerEnergyConsumer);
        }
        android.util.Slog.i(TAG, "Assigning a single power brackets to each CPU_CLUSTER energy consumer. Number of CPU clusters (" + this.mCpuScalingPolicies.getPolicies().length + ") does not match the number of energy consumers (" + this.mCpuEnergyConsumerIds.length + ").  Using default power bucket assignment.");
        return initDefaultPowerBrackets(this.mDefaultCpuPowerBrackets);
    }

    private int[] initPowerBracketsFromPowerProfile() {
        int[] iArr = new int[this.mCpuScalingPolicies.getScalingStepCount()];
        int i = 0;
        for (int i2 : this.mCpuScalingPolicies.getPolicies()) {
            int[] frequencies = this.mCpuScalingPolicies.getFrequencies(i2);
            int i3 = 0;
            while (i3 < frequencies.length) {
                iArr[i] = this.mPowerProfile.getCpuPowerBracketForScalingStep(i2, i3);
                i3++;
                i++;
            }
        }
        return iArr;
    }

    private int[] initPowerBracketsByCluster(int i) {
        int[] iArr = new int[this.mCpuScalingPolicies.getScalingStepCount()];
        int i2 = 0;
        int i3 = 0;
        for (int i4 : this.mCpuScalingPolicies.getPolicies()) {
            int[] frequencies = this.mCpuScalingPolicies.getFrequencies(i4);
            double[] dArr = new double[frequencies.length];
            for (int i5 = 0; i5 < frequencies.length; i5++) {
                dArr[i5] = this.mPowerProfile.getAveragePowerForCpuScalingStep(i4, i5);
            }
            int[] iArr2 = new int[frequencies.length];
            mapScalingStepsToDefaultBrackets(iArr2, dArr, i);
            int i6 = 0;
            int i7 = 0;
            while (i6 < frequencies.length) {
                int i8 = iArr2[i6] + i3;
                int i9 = i2 + 1;
                iArr[i2] = i8;
                if (i8 > i7) {
                    i7 = i8;
                }
                i6++;
                i2 = i9;
            }
            i3 = i7 + 1;
        }
        return iArr;
    }

    private int[] initDefaultPowerBrackets(int i) {
        int[] iArr = new int[this.mCpuScalingPolicies.getScalingStepCount()];
        double[] dArr = new double[this.mCpuScalingPolicies.getScalingStepCount()];
        int i2 = 0;
        for (int i3 : this.mCpuScalingPolicies.getPolicies()) {
            int[] frequencies = this.mCpuScalingPolicies.getFrequencies(i3);
            int i4 = 0;
            while (i4 < frequencies.length) {
                dArr[i2] = this.mPowerProfile.getAveragePowerForCpuScalingStep(i3, i4);
                i4++;
                i2++;
            }
        }
        mapScalingStepsToDefaultBrackets(iArr, dArr, i);
        return iArr;
    }

    private static void mapScalingStepsToDefaultBrackets(int[] iArr, double[] dArr, int i) {
        double d = Double.MAX_VALUE;
        double d2 = Double.MIN_VALUE;
        int i2 = 0;
        for (double d3 : dArr) {
            if (d3 < d) {
                d = d3;
            }
            if (d3 > d2) {
                d2 = d3;
            }
        }
        if (dArr.length > i) {
            double log = java.lang.Math.log(d);
            double log2 = (java.lang.Math.log(d2) - log) / i;
            while (i2 < dArr.length) {
                int log3 = (int) ((java.lang.Math.log(dArr[i2]) - log) / log2);
                if (log3 >= i) {
                    log3 = i - 1;
                }
                iArr[i2] = log3;
                i2++;
            }
            return;
        }
        while (i2 < iArr.length) {
            iArr[i2] = i2;
            i2++;
        }
    }

    public void dumpCpuPowerBracketsLocked(java.io.PrintWriter printWriter) {
        if (!ensureInitialized() || this.mLayout == null) {
            return;
        }
        printWriter.println("CPU power brackets; cluster/freq in MHz(avg current in mA):");
        for (int i = 0; i < this.mLayout.getCpuPowerBracketCount(); i++) {
            printWriter.print("    ");
            printWriter.print(i);
            printWriter.print(": ");
            printWriter.println(getCpuPowerBracketDescription(i));
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.lang.String getCpuPowerBracketDescription(int i) {
        if (!ensureInitialized()) {
            return "";
        }
        int[] scalingStepToPowerBracketMap = this.mLayout.getScalingStepToPowerBracketMap();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int[] policies = this.mCpuScalingPolicies.getPolicies();
        int i2 = 0;
        for (int i3 : policies) {
            int[] frequencies = this.mCpuScalingPolicies.getFrequencies(i3);
            for (int i4 = 0; i4 < frequencies.length; i4++) {
                if (scalingStepToPowerBracketMap[i2] == i) {
                    if (sb.length() != 0) {
                        sb.append(", ");
                    }
                    if (policies.length > 1) {
                        sb.append(i3);
                        sb.append('/');
                    }
                    sb.append(frequencies[i4] / 1000);
                    sb.append('(');
                    sb.append(java.lang.String.format(java.util.Locale.US, "%.1f", java.lang.Double.valueOf(this.mPowerProfile.getAveragePowerForCpuScalingStep(i3, i4))));
                    sb.append(')');
                }
                i2++;
            }
        }
        return sb.toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.internal.os.PowerStats.Descriptor getPowerStatsDescriptor() {
        if (!ensureInitialized()) {
            return null;
        }
        return this.mPowerStatsDescriptor;
    }

    @Override // com.android.server.power.stats.PowerStatsCollector
    protected com.android.internal.os.PowerStats collectStats() {
        if (!ensureInitialized() || !this.mIsPerUidTimeInStateSupported) {
            return null;
        }
        this.mCpuPowerStats.uidStats.clear();
        long nativeReadCpuStats = this.mKernelCpuStatsReader.nativeReadCpuStats(new com.android.server.power.stats.CpuPowerStatsCollector.KernelCpuStatsCallback() { // from class: com.android.server.power.stats.CpuPowerStatsCollector$$ExternalSyntheticLambda0
            @Override // com.android.server.power.stats.CpuPowerStatsCollector.KernelCpuStatsCallback
            public final void processUidStats(int i, long[] jArr) {
                com.android.server.power.stats.CpuPowerStatsCollector.this.processUidStats(i, jArr);
            }
        }, this.mLayout.getScalingStepToPowerBracketMap(), this.mLastUpdateTimestampNanos, this.mTempCpuTimeByScalingStep, this.mTempUidStats);
        for (int cpuScalingStepCount = this.mLayout.getCpuScalingStepCount() - 1; cpuScalingStepCount >= 0; cpuScalingStepCount--) {
            this.mLayout.setTimeByScalingStep(this.mCpuPowerStats.stats, cpuScalingStepCount, this.mTempCpuTimeByScalingStep[cpuScalingStepCount] - this.mCpuTimeByScalingStep[cpuScalingStepCount]);
            this.mCpuTimeByScalingStep[cpuScalingStepCount] = this.mTempCpuTimeByScalingStep[cpuScalingStepCount];
        }
        this.mCpuPowerStats.durationMs = (nativeReadCpuStats - this.mLastUpdateTimestampNanos) / NANOS_PER_MILLIS;
        this.mLastUpdateTimestampNanos = nativeReadCpuStats;
        long uptimeMillis = this.mClock.uptimeMillis();
        long j = uptimeMillis - this.mLastUpdateUptimeMillis;
        this.mLastUpdateUptimeMillis = uptimeMillis;
        if (j > this.mCpuPowerStats.durationMs) {
            j = this.mCpuPowerStats.durationMs;
        }
        this.mLayout.setUsageDuration(this.mCpuPowerStats.stats, j);
        if (this.mCpuEnergyConsumerIds.length != 0) {
            collectEnergyConsumers();
        }
        return this.mCpuPowerStats;
    }

    private void collectEnergyConsumers() {
        android.hardware.power.stats.EnergyConsumerResult[] energyConsumerResultArr;
        int asInt = this.mVoltageSupplier.getAsInt();
        if (asInt <= 0) {
            android.util.Slog.wtf(TAG, "Unexpected battery voltage (" + asInt + " mV) when querying energy consumers");
            return;
        }
        int i = this.mLastVoltageMv != 0 ? (this.mLastVoltageMv + asInt) / 2 : asInt;
        this.mLastVoltageMv = asInt;
        try {
            energyConsumerResultArr = this.mPowerStatsInternal.getEnergyConsumedAsync(this.mCpuEnergyConsumerIds).get(POWER_STATS_ENERGY_CONSUMERS_TIMEOUT, java.util.concurrent.TimeUnit.MILLISECONDS);
        } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.e(TAG, "Could not obtain energy consumers from PowerStatsService", e);
            energyConsumerResultArr = null;
        }
        if (energyConsumerResultArr == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mCpuEnergyConsumerIds.length; i2++) {
            int i3 = this.mCpuEnergyConsumerIds[i2];
            int length = energyConsumerResultArr.length;
            int i4 = 0;
            while (true) {
                if (i4 < length) {
                    android.hardware.power.stats.EnergyConsumerResult energyConsumerResult = energyConsumerResultArr[i4];
                    if (energyConsumerResult.id != i3) {
                        i4++;
                    } else {
                        long j = this.mLastConsumedEnergyUws[i2] != -1 ? energyConsumerResult.energyUWs - this.mLastConsumedEnergyUws[i2] : 0L;
                        this.mLayout.setConsumedEnergy(this.mCpuPowerStats.stats, i2, uJtoUc(j >= 0 ? j : 0L, i));
                        this.mLastConsumedEnergyUws[i2] = energyConsumerResult.energyUWs;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processUidStats(int i, long[] jArr) {
        int mapUid;
        int cpuPowerBracketCount = this.mLayout.getCpuPowerBracketCount();
        com.android.server.power.stats.CpuPowerStatsCollector.UidStats uidStats = this.mUidStats.get(i);
        if (uidStats == null) {
            uidStats = new com.android.server.power.stats.CpuPowerStatsCollector.UidStats();
            uidStats.timeByPowerBracket = new long[cpuPowerBracketCount];
            uidStats.stats = new long[this.mLayout.getUidStatsArrayLength()];
            this.mUidStats.put(i, uidStats);
        }
        boolean z = false;
        for (int i2 = cpuPowerBracketCount - 1; i2 >= 0; i2--) {
            long j = jArr[i2] - uidStats.timeByPowerBracket[i2];
            if (j != 0) {
                z = true;
            }
            this.mLayout.setUidTimeByPowerBracket(uidStats.stats, i2, j);
            uidStats.timeByPowerBracket[i2] = jArr[i2];
        }
        if (z) {
            if (android.os.Process.isSdkSandboxUid(i)) {
                mapUid = android.os.Process.getAppUidForSdkSandboxUid(i);
            } else {
                mapUid = this.mUidResolver.mapUid(i);
            }
            long[] jArr2 = (long[]) this.mCpuPowerStats.uidStats.get(mapUid);
            if (jArr2 == null) {
                this.mCpuPowerStats.uidStats.put(mapUid, uidStats.stats);
                return;
            }
            for (int i3 = 0; i3 < jArr2.length; i3++) {
                jArr2[i3] = jArr2[i3] + uidStats.stats[i3];
            }
        }
    }
}
