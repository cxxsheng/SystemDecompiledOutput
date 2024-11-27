package android.os;

/* loaded from: classes3.dex */
public final class BatteryUsageStats implements android.os.Parcelable, java.io.Closeable {
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_ALL_APPS = 1;
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_COUNT = 2;
    public static final int AGGREGATE_BATTERY_CONSUMER_SCOPE_DEVICE = 0;
    private static final long BATTERY_CONSUMER_CURSOR_WINDOW_SIZE = 3500000;
    private static final int STATSD_PULL_ATOM_MAX_BYTES = 45000;
    private static final double WEIGHT_BACKGROUND_STATE = 8.333333333333333E-5d;
    private static final double WEIGHT_CONSUMED_POWER = 1.0d;
    private static final double WEIGHT_FOREGROUND_STATE = 2.777777777777778E-5d;
    static final java.lang.String XML_ATTR_BATTERY_CAPACITY = "battery_capacity";
    static final java.lang.String XML_ATTR_BATTERY_REMAINING = "battery_remaining";
    static final java.lang.String XML_ATTR_CHARGE_REMAINING = "charge_remaining";
    static final java.lang.String XML_ATTR_DISCHARGE_DURATION = "discharge_duration";
    static final java.lang.String XML_ATTR_DISCHARGE_LOWER = "discharge_lower";
    static final java.lang.String XML_ATTR_DISCHARGE_PERCENT = "discharge_pct";
    static final java.lang.String XML_ATTR_DISCHARGE_UPPER = "discharge_upper";
    static final java.lang.String XML_ATTR_DURATION = "duration";
    static final java.lang.String XML_ATTR_END_TIMESTAMP = "end_timestamp";
    static final java.lang.String XML_ATTR_HIGHEST_DRAIN_PACKAGE = "highest_drain_package";
    static final java.lang.String XML_ATTR_ID = "id";
    static final java.lang.String XML_ATTR_MODEL = "model";
    static final java.lang.String XML_ATTR_POWER = "power";
    static final java.lang.String XML_ATTR_PREFIX_CUSTOM_COMPONENT = "custom_component_";
    static final java.lang.String XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA = "includes_proc_state_data";
    static final java.lang.String XML_ATTR_PROCESS_STATE = "process_state";
    static final java.lang.String XML_ATTR_SCOPE = "scope";
    static final java.lang.String XML_ATTR_START_TIMESTAMP = "start_timestamp";
    static final java.lang.String XML_ATTR_TIME_IN_BACKGROUND = "time_in_background";
    static final java.lang.String XML_ATTR_TIME_IN_FOREGROUND = "time_in_foreground";
    static final java.lang.String XML_ATTR_TIME_IN_FOREGROUND_SERVICE = "time_in_foreground_service";
    static final java.lang.String XML_ATTR_UID = "uid";
    static final java.lang.String XML_ATTR_USER_ID = "user_id";
    static final java.lang.String XML_TAG_AGGREGATE = "aggregate";
    static final java.lang.String XML_TAG_BATTERY_USAGE_STATS = "battery_usage_stats";
    static final java.lang.String XML_TAG_COMPONENT = "component";
    static final java.lang.String XML_TAG_CUSTOM_COMPONENT = "custom_component";
    static final java.lang.String XML_TAG_POWER_COMPONENTS = "power_components";
    static final java.lang.String XML_TAG_UID = "uid";
    static final java.lang.String XML_TAG_USER = "user";
    private final android.os.AggregateBatteryConsumer[] mAggregateBatteryConsumers;
    private final double mBatteryCapacityMah;
    private android.database.CursorWindow mBatteryConsumersCursorWindow;
    private final com.android.internal.os.BatteryStatsHistory mBatteryStatsHistory;
    private final long mBatteryTimeRemainingMs;
    private final long mChargeTimeRemainingMs;
    private final java.lang.String[] mCustomPowerComponentNames;
    private final long mDischargeDurationMs;
    private final int mDischargePercentage;
    private final double mDischargedPowerLowerBound;
    private final double mDischargedPowerUpperBound;
    private final boolean mIncludesPowerModels;
    private final boolean mIncludesProcessStateData;
    private final long mStatsDurationMs;
    private final long mStatsEndTimestampMs;
    private final long mStatsStartTimestampMs;
    private final java.util.List<android.os.UidBatteryConsumer> mUidBatteryConsumers;
    private final java.util.List<android.os.UserBatteryConsumer> mUserBatteryConsumers;
    private static final int[] UID_USAGE_TIME_PROCESS_STATES = {1, 2, 3};
    public static final android.os.Parcelable.Creator<android.os.BatteryUsageStats> CREATOR = new android.os.Parcelable.Creator<android.os.BatteryUsageStats>() { // from class: android.os.BatteryUsageStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatteryUsageStats createFromParcel(android.os.Parcel parcel) {
            return new android.os.BatteryUsageStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatteryUsageStats[] newArray(int i) {
            return new android.os.BatteryUsageStats[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AggregateBatteryConsumerScope {
    }

    private BatteryUsageStats(android.os.BatteryUsageStats.Builder builder) {
        this.mStatsStartTimestampMs = builder.mStatsStartTimestampMs;
        this.mStatsEndTimestampMs = builder.mStatsEndTimestampMs;
        this.mStatsDurationMs = builder.getStatsDuration();
        this.mBatteryCapacityMah = builder.mBatteryCapacityMah;
        this.mDischargePercentage = builder.mDischargePercentage;
        this.mDischargedPowerLowerBound = builder.mDischargedPowerLowerBoundMah;
        this.mDischargedPowerUpperBound = builder.mDischargedPowerUpperBoundMah;
        this.mDischargeDurationMs = builder.mDischargeDurationMs;
        this.mBatteryStatsHistory = builder.mBatteryStatsHistory;
        this.mBatteryTimeRemainingMs = builder.mBatteryTimeRemainingMs;
        this.mChargeTimeRemainingMs = builder.mChargeTimeRemainingMs;
        this.mCustomPowerComponentNames = builder.mCustomPowerComponentNames;
        this.mIncludesPowerModels = builder.mIncludePowerModels;
        this.mIncludesProcessStateData = builder.mIncludesProcessStateData;
        this.mBatteryConsumersCursorWindow = builder.mBatteryConsumersCursorWindow;
        int size = builder.mUidBatteryConsumerBuilders.size();
        this.mUidBatteryConsumers = new java.util.ArrayList(size);
        double d = 0.0d;
        for (int i = 0; i < size; i++) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) builder.mUidBatteryConsumerBuilders.valueAt(i);
            if (!builder2.isExcludedFromBatteryUsageStats()) {
                android.os.UidBatteryConsumer build = builder2.build();
                d += build.getConsumedPower();
                this.mUidBatteryConsumers.add(build);
            }
        }
        int size2 = builder.mUserBatteryConsumerBuilders.size();
        this.mUserBatteryConsumers = new java.util.ArrayList(size2);
        for (int i2 = 0; i2 < size2; i2++) {
            android.os.UserBatteryConsumer build2 = ((android.os.UserBatteryConsumer.Builder) builder.mUserBatteryConsumerBuilders.valueAt(i2)).build();
            d += build2.getConsumedPower();
            this.mUserBatteryConsumers.add(build2);
        }
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(d);
        this.mAggregateBatteryConsumers = new android.os.AggregateBatteryConsumer[2];
        for (int i3 = 0; i3 < 2; i3++) {
            this.mAggregateBatteryConsumers[i3] = builder.mAggregateBatteryConsumersBuilders[i3].build();
        }
    }

    public long getStatsStartTimestamp() {
        return this.mStatsStartTimestampMs;
    }

    public long getStatsEndTimestamp() {
        return this.mStatsEndTimestampMs;
    }

    public long getStatsDuration() {
        return this.mStatsDurationMs;
    }

    public double getConsumedPower() {
        return this.mAggregateBatteryConsumers[0].getConsumedPower();
    }

    public double getBatteryCapacity() {
        return this.mBatteryCapacityMah;
    }

    public int getDischargePercentage() {
        return this.mDischargePercentage;
    }

    public android.util.Range<java.lang.Double> getDischargedPowerRange() {
        return android.util.Range.create(java.lang.Double.valueOf(this.mDischargedPowerLowerBound), java.lang.Double.valueOf(this.mDischargedPowerUpperBound));
    }

    public long getDischargeDurationMs() {
        return this.mDischargeDurationMs;
    }

    public long getBatteryTimeRemainingMs() {
        return this.mBatteryTimeRemainingMs;
    }

    public long getChargeTimeRemainingMs() {
        return this.mChargeTimeRemainingMs;
    }

    public android.os.AggregateBatteryConsumer getAggregateBatteryConsumer(int i) {
        return this.mAggregateBatteryConsumers[i];
    }

    public java.util.List<android.os.UidBatteryConsumer> getUidBatteryConsumers() {
        return this.mUidBatteryConsumers;
    }

    public java.util.List<android.os.UserBatteryConsumer> getUserBatteryConsumers() {
        return this.mUserBatteryConsumers;
    }

    public java.lang.String[] getCustomPowerComponentNames() {
        return this.mCustomPowerComponentNames;
    }

    public boolean isProcessStateDataIncluded() {
        return this.mIncludesProcessStateData;
    }

    public com.android.internal.os.BatteryStatsHistoryIterator iterateBatteryStatsHistory() {
        if (this.mBatteryStatsHistory == null) {
            throw new java.lang.IllegalStateException("Battery history was not requested in the BatteryUsageStatsQuery");
        }
        return new com.android.internal.os.BatteryStatsHistoryIterator(this.mBatteryStatsHistory, 0L, -1L);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BatteryUsageStats(android.os.Parcel parcel) {
        this.mStatsStartTimestampMs = parcel.readLong();
        this.mStatsEndTimestampMs = parcel.readLong();
        this.mStatsDurationMs = parcel.readLong();
        this.mBatteryCapacityMah = parcel.readDouble();
        this.mDischargePercentage = parcel.readInt();
        this.mDischargedPowerLowerBound = parcel.readDouble();
        this.mDischargedPowerUpperBound = parcel.readDouble();
        this.mDischargeDurationMs = parcel.readLong();
        this.mBatteryTimeRemainingMs = parcel.readLong();
        this.mChargeTimeRemainingMs = parcel.readLong();
        this.mCustomPowerComponentNames = parcel.readStringArray();
        this.mIncludesPowerModels = parcel.readBoolean();
        this.mIncludesProcessStateData = parcel.readBoolean();
        this.mBatteryConsumersCursorWindow = android.database.CursorWindow.newFromParcel(parcel);
        android.os.BatteryConsumer.BatteryConsumerDataLayout createBatteryConsumerDataLayout = android.os.BatteryConsumer.createBatteryConsumerDataLayout(this.mCustomPowerComponentNames, this.mIncludesPowerModels, this.mIncludesProcessStateData);
        int numRows = this.mBatteryConsumersCursorWindow.getNumRows();
        this.mAggregateBatteryConsumers = new android.os.AggregateBatteryConsumer[2];
        this.mUidBatteryConsumers = new java.util.ArrayList(numRows);
        this.mUserBatteryConsumers = new java.util.ArrayList();
        for (int i = 0; i < numRows; i++) {
            android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData = new android.os.BatteryConsumer.BatteryConsumerData(this.mBatteryConsumersCursorWindow, i, createBatteryConsumerDataLayout);
            switch (this.mBatteryConsumersCursorWindow.getInt(i, 0)) {
                case 0:
                    android.os.AggregateBatteryConsumer aggregateBatteryConsumer = new android.os.AggregateBatteryConsumer(batteryConsumerData);
                    this.mAggregateBatteryConsumers[aggregateBatteryConsumer.getScope()] = aggregateBatteryConsumer;
                    break;
                case 1:
                    this.mUidBatteryConsumers.add(new android.os.UidBatteryConsumer(batteryConsumerData));
                    break;
                case 2:
                    this.mUserBatteryConsumers.add(new android.os.UserBatteryConsumer(batteryConsumerData));
                    break;
            }
        }
        if (parcel.readBoolean()) {
            this.mBatteryStatsHistory = com.android.internal.os.BatteryStatsHistory.createFromBatteryUsageStatsParcel(parcel);
        } else {
            this.mBatteryStatsHistory = null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mStatsStartTimestampMs);
        parcel.writeLong(this.mStatsEndTimestampMs);
        parcel.writeLong(this.mStatsDurationMs);
        parcel.writeDouble(this.mBatteryCapacityMah);
        parcel.writeInt(this.mDischargePercentage);
        parcel.writeDouble(this.mDischargedPowerLowerBound);
        parcel.writeDouble(this.mDischargedPowerUpperBound);
        parcel.writeLong(this.mDischargeDurationMs);
        parcel.writeLong(this.mBatteryTimeRemainingMs);
        parcel.writeLong(this.mChargeTimeRemainingMs);
        parcel.writeStringArray(this.mCustomPowerComponentNames);
        parcel.writeBoolean(this.mIncludesPowerModels);
        parcel.writeBoolean(this.mIncludesProcessStateData);
        this.mBatteryConsumersCursorWindow.writeToParcel(parcel, i);
        if (this.mBatteryStatsHistory != null) {
            parcel.writeBoolean(true);
            this.mBatteryStatsHistory.writeToBatteryUsageStatsParcel(parcel);
        } else {
            parcel.writeBoolean(false);
        }
    }

    public byte[] getStatsProto() {
        int i = 78750;
        for (int i2 = 0; i2 < 3; i2++) {
            android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
            writeStatsProto(protoOutputStream, i);
            int rawSize = protoOutputStream.getRawSize();
            byte[] bytes = protoOutputStream.getBytes();
            if (bytes.length <= STATSD_PULL_ATOM_MAX_BYTES) {
                return bytes;
            }
            i = (int) (((rawSize * 45000) / bytes.length) - 1024);
        }
        android.util.proto.ProtoOutputStream protoOutputStream2 = new android.util.proto.ProtoOutputStream();
        writeStatsProto(protoOutputStream2, STATSD_PULL_ATOM_MAX_BYTES);
        return protoOutputStream2.getBytes();
    }

    public void dumpToProto(java.io.FileDescriptor fileDescriptor) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        writeStatsProto(protoOutputStream, Integer.MAX_VALUE);
        protoOutputStream.flush();
    }

    private void writeStatsProto(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        android.os.AggregateBatteryConsumer aggregateBatteryConsumer = getAggregateBatteryConsumer(0);
        protoOutputStream.write(1112396529665L, getStatsStartTimestamp());
        protoOutputStream.write(1112396529666L, getStatsEndTimestamp());
        protoOutputStream.write(1112396529667L, getStatsDuration());
        protoOutputStream.write(1120986464262L, getDischargePercentage());
        protoOutputStream.write(1112396529671L, getDischargeDurationMs());
        aggregateBatteryConsumer.writeStatsProto(protoOutputStream, 1146756268036L);
        if (this.mIncludesPowerModels) {
            aggregateBatteryConsumer.writePowerComponentModelProto(protoOutputStream);
        }
        writeUidBatteryConsumersProto(protoOutputStream, i);
    }

    private void writeUidBatteryConsumersProto(android.util.proto.ProtoOutputStream protoOutputStream, int i) {
        int i2;
        java.util.List<android.os.UidBatteryConsumer> list;
        int i3;
        java.util.List<android.os.UidBatteryConsumer> list2;
        int i4;
        java.util.List<android.os.UidBatteryConsumer> uidBatteryConsumers = getUidBatteryConsumers();
        uidBatteryConsumers.sort(java.util.Comparator.comparingDouble(new java.util.function.ToDoubleFunction() { // from class: android.os.BatteryUsageStats$$ExternalSyntheticLambda0
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(java.lang.Object obj) {
                double uidBatteryConsumerWeight;
                uidBatteryConsumerWeight = android.os.BatteryUsageStats.this.getUidBatteryConsumerWeight((android.os.UidBatteryConsumer) obj);
                return uidBatteryConsumerWeight;
            }
        }).reversed());
        int size = uidBatteryConsumers.size();
        int i5 = 0;
        int i6 = 0;
        while (i6 < size) {
            android.os.UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(i6);
            long timeInStateMs = uidBatteryConsumer.getTimeInStateMs(i5);
            long timeInStateMs2 = uidBatteryConsumer.getTimeInStateMs(1);
            boolean hasStatsProtoData = uidBatteryConsumer.hasStatsProtoData();
            if (timeInStateMs != 0 || timeInStateMs2 != 0 || hasStatsProtoData) {
                i2 = i6;
                long start = protoOutputStream.start(2246267895813L);
                protoOutputStream.write(1120986464257L, uidBatteryConsumer.getUid());
                if (hasStatsProtoData) {
                    uidBatteryConsumer.writeStatsProto(protoOutputStream, 1146756268034L);
                }
                protoOutputStream.write(1112396529667L, timeInStateMs);
                protoOutputStream.write(1112396529668L, timeInStateMs2);
                int[] iArr = UID_USAGE_TIME_PROCESS_STATES;
                int length = iArr.length;
                int i7 = 0;
                while (i7 < length) {
                    int i8 = iArr[i7];
                    long timeInProcessStateMs = uidBatteryConsumer.getTimeInProcessStateMs(i8);
                    if (timeInProcessStateMs <= 0) {
                        list2 = uidBatteryConsumers;
                        i4 = size;
                    } else {
                        list2 = uidBatteryConsumers;
                        i4 = size;
                        long start2 = protoOutputStream.start(2246267895813L);
                        protoOutputStream.write(1159641169921L, i8);
                        protoOutputStream.write(1112396529666L, timeInProcessStateMs);
                        protoOutputStream.end(start2);
                    }
                    i7++;
                    uidBatteryConsumers = list2;
                    size = i4;
                }
                list = uidBatteryConsumers;
                i3 = size;
                protoOutputStream.end(start);
                if (protoOutputStream.getRawSize() >= i) {
                    return;
                }
            } else {
                list = uidBatteryConsumers;
                i3 = size;
                i2 = i6;
            }
            i6 = i2 + 1;
            uidBatteryConsumers = list;
            size = i3;
            i5 = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public double getUidBatteryConsumerWeight(android.os.UidBatteryConsumer uidBatteryConsumer) {
        return (uidBatteryConsumer.getConsumedPower() * WEIGHT_CONSUMED_POWER) + (uidBatteryConsumer.getTimeInStateMs(0) * WEIGHT_FOREGROUND_STATE) + (uidBatteryConsumer.getTimeInStateMs(1) * WEIGHT_BACKGROUND_STATE);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        int i;
        int i2;
        java.lang.String str2;
        int i3;
        int i4;
        android.os.BatteryConsumer.Key[] keyArr;
        android.os.AggregateBatteryConsumer aggregateBatteryConsumer;
        int i5;
        printWriter.print(str);
        printWriter.println("  Estimated power use (mAh):");
        printWriter.print(str);
        printWriter.print("    Capacity: ");
        printWriter.print(android.os.BatteryStats.formatCharge(getBatteryCapacity()));
        printWriter.print(", Computed drain: ");
        printWriter.print(android.os.BatteryStats.formatCharge(getConsumedPower()));
        android.util.Range<java.lang.Double> dischargedPowerRange = getDischargedPowerRange();
        printWriter.print(", actual drain: ");
        printWriter.print(android.os.BatteryStats.formatCharge(dischargedPowerRange.getLower().doubleValue()));
        if (!dischargedPowerRange.getLower().equals(dischargedPowerRange.getUpper())) {
            printWriter.print(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            printWriter.print(android.os.BatteryStats.formatCharge(dischargedPowerRange.getUpper().doubleValue()));
        }
        printWriter.println();
        printWriter.println("    Global");
        int i6 = 0;
        android.os.AggregateBatteryConsumer aggregateBatteryConsumer2 = getAggregateBatteryConsumer(0);
        android.os.AggregateBatteryConsumer aggregateBatteryConsumer3 = getAggregateBatteryConsumer(1);
        int i7 = 0;
        while (i7 < 18) {
            android.os.BatteryConsumer.Key[] keys = aggregateBatteryConsumer2.getKeys(i7);
            int length = keys.length;
            int i8 = i6;
            while (i8 < length) {
                android.os.BatteryConsumer.Key key = keys[i8];
                double consumedPower = aggregateBatteryConsumer2.getConsumedPower(key);
                double consumedPower2 = aggregateBatteryConsumer3.getConsumedPower(key);
                if (consumedPower == 0.0d && consumedPower2 == 0.0d) {
                    i3 = length;
                    i4 = i8;
                    keyArr = keys;
                    aggregateBatteryConsumer = aggregateBatteryConsumer3;
                    i5 = i7;
                } else {
                    java.lang.String powerComponentIdToString = android.os.BatteryConsumer.powerComponentIdToString(i7);
                    if (key.processState == 0) {
                        str2 = powerComponentIdToString;
                    } else {
                        str2 = powerComponentIdToString + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + android.os.BatteryConsumer.processStateToString(key.processState) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
                    }
                    i3 = length;
                    i4 = i8;
                    keyArr = keys;
                    int powerModel = this.mIncludesPowerModels ? aggregateBatteryConsumer2.getPowerModel(key) : i6;
                    aggregateBatteryConsumer = aggregateBatteryConsumer3;
                    i5 = i7;
                    printPowerComponent(printWriter, str, str2, consumedPower, consumedPower2, powerModel, aggregateBatteryConsumer2.getUsageDurationMillis(key));
                }
                i8 = i4 + 1;
                aggregateBatteryConsumer3 = aggregateBatteryConsumer;
                keys = keyArr;
                i7 = i5;
                length = i3;
                i6 = 0;
            }
            i7++;
            i6 = 0;
        }
        android.os.AggregateBatteryConsumer aggregateBatteryConsumer4 = aggregateBatteryConsumer3;
        int i9 = 1000;
        int i10 = 1000;
        while (i10 < this.mCustomPowerComponentNames.length + i9) {
            double consumedPowerForCustomComponent = aggregateBatteryConsumer2.getConsumedPowerForCustomComponent(i10);
            double consumedPowerForCustomComponent2 = aggregateBatteryConsumer4.getConsumedPowerForCustomComponent(i10);
            if (consumedPowerForCustomComponent == 0.0d && consumedPowerForCustomComponent2 == 0.0d) {
                i = i9;
                i2 = i10;
            } else {
                i = i9;
                i2 = i10;
                printPowerComponent(printWriter, str, aggregateBatteryConsumer2.getCustomPowerComponentName(i10), consumedPowerForCustomComponent, consumedPowerForCustomComponent2, 0, aggregateBatteryConsumer2.getUsageDurationForCustomComponentMillis(i10));
            }
            i10 = i2 + 1;
            i9 = i;
        }
        dumpSortedBatteryConsumers(printWriter, str, getUidBatteryConsumers());
        dumpSortedBatteryConsumers(printWriter, str, getUserBatteryConsumers());
        printWriter.println();
    }

    private void printPowerComponent(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, double d, double d2, int i, long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str).append("      ").append(str2).append(": ").append(android.os.BatteryStats.formatCharge(d));
        if (i != 0 && i != 1) {
            sb.append(" [");
            sb.append(android.os.BatteryConsumer.powerModelToString(i));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        sb.append(" apps: ").append(android.os.BatteryStats.formatCharge(d2));
        if (j != 0) {
            sb.append(" duration: ");
            android.os.BatteryStats.formatTimeMs(sb, j);
        }
        printWriter.println(sb.toString());
    }

    private void dumpSortedBatteryConsumers(java.io.PrintWriter printWriter, java.lang.String str, java.util.List<? extends android.os.BatteryConsumer> list) {
        list.sort(java.util.Comparator.comparingDouble(new java.util.function.ToDoubleFunction() { // from class: android.os.BatteryUsageStats$$ExternalSyntheticLambda1
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(java.lang.Object obj) {
                return ((android.os.BatteryConsumer) obj).getConsumedPower();
            }
        }).reversed());
        for (android.os.BatteryConsumer batteryConsumer : list) {
            if (batteryConsumer.getConsumedPower() != 0.0d) {
                printWriter.print(str);
                printWriter.print("    ");
                batteryConsumer.dump(printWriter);
                printWriter.println();
            }
        }
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(null, XML_TAG_BATTERY_USAGE_STATS);
        for (int i = 0; i < this.mCustomPowerComponentNames.length; i++) {
            typedXmlSerializer.attribute(null, XML_ATTR_PREFIX_CUSTOM_COMPONENT + i, this.mCustomPowerComponentNames[i]);
        }
        typedXmlSerializer.attributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA, this.mIncludesProcessStateData);
        typedXmlSerializer.attributeLong(null, XML_ATTR_START_TIMESTAMP, this.mStatsStartTimestampMs);
        typedXmlSerializer.attributeLong(null, XML_ATTR_END_TIMESTAMP, this.mStatsEndTimestampMs);
        typedXmlSerializer.attributeLong(null, "duration", this.mStatsDurationMs);
        typedXmlSerializer.attributeDouble(null, XML_ATTR_BATTERY_CAPACITY, this.mBatteryCapacityMah);
        typedXmlSerializer.attributeInt(null, XML_ATTR_DISCHARGE_PERCENT, this.mDischargePercentage);
        typedXmlSerializer.attributeDouble(null, XML_ATTR_DISCHARGE_LOWER, this.mDischargedPowerLowerBound);
        typedXmlSerializer.attributeDouble(null, XML_ATTR_DISCHARGE_UPPER, this.mDischargedPowerUpperBound);
        typedXmlSerializer.attributeLong(null, XML_ATTR_DISCHARGE_DURATION, this.mDischargeDurationMs);
        typedXmlSerializer.attributeLong(null, XML_ATTR_BATTERY_REMAINING, this.mBatteryTimeRemainingMs);
        typedXmlSerializer.attributeLong(null, XML_ATTR_CHARGE_REMAINING, this.mChargeTimeRemainingMs);
        for (int i2 = 0; i2 < 2; i2++) {
            this.mAggregateBatteryConsumers[i2].writeToXml(typedXmlSerializer, i2);
        }
        java.util.Iterator<android.os.UidBatteryConsumer> it = this.mUidBatteryConsumers.iterator();
        while (it.hasNext()) {
            it.next().writeToXml(typedXmlSerializer);
        }
        java.util.Iterator<android.os.UserBatteryConsumer> it2 = this.mUserBatteryConsumers.iterator();
        while (it2.hasNext()) {
            it2.next().writeToXml(typedXmlSerializer);
        }
        typedXmlSerializer.endTag(null, XML_TAG_BATTERY_USAGE_STATS);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.os.BatteryUsageStats createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.os.BatteryUsageStats.Builder builder;
        boolean z;
        int eventType = typedXmlPullParser.getEventType();
        while (true) {
            builder = null;
            if (eventType == 1) {
                break;
            }
            if (eventType == 2 && typedXmlPullParser.getName().equals(XML_TAG_BATTERY_USAGE_STATS)) {
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int i = 0;
                while (true) {
                    int attributeIndex = typedXmlPullParser.getAttributeIndex(null, XML_ATTR_PREFIX_CUSTOM_COMPONENT + i);
                    if (attributeIndex == -1) {
                        break;
                    }
                    arrayList.add(typedXmlPullParser.getAttributeValue(attributeIndex));
                    i++;
                }
                android.os.BatteryUsageStats.Builder builder2 = new android.os.BatteryUsageStats.Builder((java.lang.String[]) arrayList.toArray(new java.lang.String[0]), true, typedXmlPullParser.getAttributeBoolean(null, XML_ATTR_PREFIX_INCLUDES_PROC_STATE_DATA, false), 0.0d);
                builder2.setStatsStartTimestamp(typedXmlPullParser.getAttributeLong(null, XML_ATTR_START_TIMESTAMP));
                builder2.setStatsEndTimestamp(typedXmlPullParser.getAttributeLong(null, XML_ATTR_END_TIMESTAMP));
                builder2.setStatsDuration(typedXmlPullParser.getAttributeLong(null, "duration"));
                builder2.setBatteryCapacity(typedXmlPullParser.getAttributeDouble(null, XML_ATTR_BATTERY_CAPACITY));
                builder2.setDischargePercentage(typedXmlPullParser.getAttributeInt(null, XML_ATTR_DISCHARGE_PERCENT));
                builder2.setDischargedPowerRange(typedXmlPullParser.getAttributeDouble(null, XML_ATTR_DISCHARGE_LOWER), typedXmlPullParser.getAttributeDouble(null, XML_ATTR_DISCHARGE_UPPER));
                builder2.setDischargeDurationMs(typedXmlPullParser.getAttributeLong(null, XML_ATTR_DISCHARGE_DURATION));
                builder2.setBatteryTimeRemainingMs(typedXmlPullParser.getAttributeLong(null, XML_ATTR_BATTERY_REMAINING));
                builder2.setChargeTimeRemainingMs(typedXmlPullParser.getAttributeLong(null, XML_ATTR_CHARGE_REMAINING));
                eventType = typedXmlPullParser.next();
                builder = builder2;
            } else {
                eventType = typedXmlPullParser.next();
            }
        }
        if (builder == null) {
            throw new org.xmlpull.v1.XmlPullParserException("No root element");
        }
        while (eventType != 1) {
            if (eventType == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case 115792:
                        if (name.equals("uid")) {
                            z = true;
                            break;
                        }
                        z = -1;
                        break;
                    case 3599307:
                        if (name.equals("user")) {
                            z = 2;
                            break;
                        }
                        z = -1;
                        break;
                    case 175177151:
                        if (name.equals(XML_TAG_AGGREGATE)) {
                            z = false;
                            break;
                        }
                        z = -1;
                        break;
                    default:
                        z = -1;
                        break;
                }
                switch (z) {
                    case false:
                        android.os.AggregateBatteryConsumer.parseXml(typedXmlPullParser, builder);
                        break;
                    case true:
                        android.os.UidBatteryConsumer.createFromXml(typedXmlPullParser, builder);
                        break;
                    case true:
                        android.os.UserBatteryConsumer.createFromXml(typedXmlPullParser, builder);
                        break;
                }
            }
            eventType = typedXmlPullParser.next();
        }
        return builder.build();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.mBatteryConsumersCursorWindow.close();
        this.mBatteryConsumersCursorWindow = null;
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mBatteryConsumersCursorWindow != null) {
            this.mBatteryConsumersCursorWindow.close();
        }
        super.finalize();
    }

    public java.lang.String toString() {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        java.io.PrintWriter printWriter = new java.io.PrintWriter(stringWriter);
        dump(printWriter, "");
        printWriter.flush();
        return stringWriter.toString();
    }

    public static final class Builder {
        private final android.os.AggregateBatteryConsumer.Builder[] mAggregateBatteryConsumersBuilders;
        private double mBatteryCapacityMah;
        private final android.os.BatteryConsumer.BatteryConsumerDataLayout mBatteryConsumerDataLayout;
        private final android.database.CursorWindow mBatteryConsumersCursorWindow;
        private com.android.internal.os.BatteryStatsHistory mBatteryStatsHistory;
        private long mBatteryTimeRemainingMs;
        private long mChargeTimeRemainingMs;
        private final java.lang.String[] mCustomPowerComponentNames;
        private long mDischargeDurationMs;
        private int mDischargePercentage;
        private double mDischargedPowerLowerBoundMah;
        private double mDischargedPowerUpperBoundMah;
        private final boolean mIncludePowerModels;
        private final boolean mIncludesProcessStateData;
        private final double mMinConsumedPowerThreshold;
        private long mStatsDurationMs;
        private long mStatsEndTimestampMs;
        private long mStatsStartTimestampMs;
        private final android.util.SparseArray<android.os.UidBatteryConsumer.Builder> mUidBatteryConsumerBuilders;
        private final android.util.SparseArray<android.os.UserBatteryConsumer.Builder> mUserBatteryConsumerBuilders;

        public Builder(java.lang.String[] strArr) {
            this(strArr, false, false, 0.0d);
        }

        public Builder(java.lang.String[] strArr, boolean z, boolean z2, double d) {
            this.mStatsDurationMs = -1L;
            this.mBatteryTimeRemainingMs = -1L;
            this.mChargeTimeRemainingMs = -1L;
            this.mAggregateBatteryConsumersBuilders = new android.os.AggregateBatteryConsumer.Builder[2];
            this.mUidBatteryConsumerBuilders = new android.util.SparseArray<>();
            this.mUserBatteryConsumerBuilders = new android.util.SparseArray<>();
            this.mBatteryConsumersCursorWindow = new android.database.CursorWindow((java.lang.String) null, android.os.BatteryUsageStats.BATTERY_CONSUMER_CURSOR_WINDOW_SIZE);
            this.mBatteryConsumerDataLayout = android.os.BatteryConsumer.createBatteryConsumerDataLayout(strArr, z, z2);
            this.mBatteryConsumersCursorWindow.setNumColumns(this.mBatteryConsumerDataLayout.columnCount);
            this.mCustomPowerComponentNames = strArr;
            this.mIncludePowerModels = z;
            this.mIncludesProcessStateData = z2;
            this.mMinConsumedPowerThreshold = d;
            for (int i = 0; i < 2; i++) {
                this.mAggregateBatteryConsumersBuilders[i] = new android.os.AggregateBatteryConsumer.Builder(android.os.BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), i, this.mMinConsumedPowerThreshold);
            }
        }

        public boolean isProcessStateDataNeeded() {
            return this.mIncludesProcessStateData;
        }

        public android.os.BatteryUsageStats build() {
            return new android.os.BatteryUsageStats(this);
        }

        public android.os.BatteryUsageStats.Builder setBatteryCapacity(double d) {
            this.mBatteryCapacityMah = d;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setStatsStartTimestamp(long j) {
            this.mStatsStartTimestampMs = j;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setStatsEndTimestamp(long j) {
            this.mStatsEndTimestampMs = j;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setStatsDuration(long j) {
            this.mStatsDurationMs = j;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getStatsDuration() {
            if (this.mStatsDurationMs != -1) {
                return this.mStatsDurationMs;
            }
            return this.mStatsEndTimestampMs - this.mStatsStartTimestampMs;
        }

        public android.os.BatteryUsageStats.Builder setDischargePercentage(int i) {
            this.mDischargePercentage = i;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setDischargedPowerRange(double d, double d2) {
            this.mDischargedPowerLowerBoundMah = d;
            this.mDischargedPowerUpperBoundMah = d2;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setDischargeDurationMs(long j) {
            this.mDischargeDurationMs = j;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setBatteryTimeRemainingMs(long j) {
            this.mBatteryTimeRemainingMs = j;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setChargeTimeRemainingMs(long j) {
            this.mChargeTimeRemainingMs = j;
            return this;
        }

        public android.os.BatteryUsageStats.Builder setBatteryHistory(com.android.internal.os.BatteryStatsHistory batteryStatsHistory) {
            this.mBatteryStatsHistory = batteryStatsHistory;
            return this;
        }

        public android.os.AggregateBatteryConsumer.Builder getAggregateBatteryConsumerBuilder(int i) {
            return this.mAggregateBatteryConsumersBuilders[i];
        }

        public android.os.UidBatteryConsumer.Builder getOrCreateUidBatteryConsumerBuilder(android.os.BatteryStats.Uid uid) {
            int uid2 = uid.getUid();
            android.os.UidBatteryConsumer.Builder builder = this.mUidBatteryConsumerBuilders.get(uid2);
            if (builder == null) {
                android.os.UidBatteryConsumer.Builder builder2 = new android.os.UidBatteryConsumer.Builder(android.os.BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), uid, this.mMinConsumedPowerThreshold);
                this.mUidBatteryConsumerBuilders.put(uid2, builder2);
                return builder2;
            }
            return builder;
        }

        public android.os.UidBatteryConsumer.Builder getOrCreateUidBatteryConsumerBuilder(int i) {
            android.os.UidBatteryConsumer.Builder builder = this.mUidBatteryConsumerBuilders.get(i);
            if (builder == null) {
                android.os.UidBatteryConsumer.Builder builder2 = new android.os.UidBatteryConsumer.Builder(android.os.BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), i, this.mMinConsumedPowerThreshold);
                this.mUidBatteryConsumerBuilders.put(i, builder2);
                return builder2;
            }
            return builder;
        }

        public android.os.UserBatteryConsumer.Builder getOrCreateUserBatteryConsumerBuilder(int i) {
            android.os.UserBatteryConsumer.Builder builder = this.mUserBatteryConsumerBuilders.get(i);
            if (builder == null) {
                android.os.UserBatteryConsumer.Builder builder2 = new android.os.UserBatteryConsumer.Builder(android.os.BatteryConsumer.BatteryConsumerData.create(this.mBatteryConsumersCursorWindow, this.mBatteryConsumerDataLayout), i, this.mMinConsumedPowerThreshold);
                this.mUserBatteryConsumerBuilders.put(i, builder2);
                return builder2;
            }
            return builder;
        }

        public android.util.SparseArray<android.os.UidBatteryConsumer.Builder> getUidBatteryConsumerBuilders() {
            return this.mUidBatteryConsumerBuilders;
        }

        public android.os.BatteryUsageStats.Builder add(android.os.BatteryUsageStats batteryUsageStats) {
            if (!java.util.Arrays.equals(this.mCustomPowerComponentNames, batteryUsageStats.mCustomPowerComponentNames)) {
                throw new java.lang.IllegalArgumentException("BatteryUsageStats have different custom power components");
            }
            if (this.mIncludesProcessStateData && !batteryUsageStats.mIncludesProcessStateData) {
                throw new java.lang.IllegalArgumentException("Added BatteryUsageStats does not include process state data");
            }
            if (this.mUserBatteryConsumerBuilders.size() != 0 || !batteryUsageStats.getUserBatteryConsumers().isEmpty()) {
                throw new java.lang.UnsupportedOperationException("Combining UserBatteryConsumers is not supported");
            }
            this.mDischargedPowerLowerBoundMah += batteryUsageStats.mDischargedPowerLowerBound;
            this.mDischargedPowerUpperBoundMah += batteryUsageStats.mDischargedPowerUpperBound;
            this.mDischargePercentage += batteryUsageStats.mDischargePercentage;
            this.mDischargeDurationMs += batteryUsageStats.mDischargeDurationMs;
            this.mStatsDurationMs = getStatsDuration() + batteryUsageStats.getStatsDuration();
            if (this.mStatsStartTimestampMs == 0 || batteryUsageStats.mStatsStartTimestampMs < this.mStatsStartTimestampMs) {
                this.mStatsStartTimestampMs = batteryUsageStats.mStatsStartTimestampMs;
            }
            boolean z = batteryUsageStats.mStatsEndTimestampMs > this.mStatsEndTimestampMs;
            if (z) {
                this.mStatsEndTimestampMs = batteryUsageStats.mStatsEndTimestampMs;
            }
            for (int i = 0; i < 2; i++) {
                getAggregateBatteryConsumerBuilder(i).add(batteryUsageStats.mAggregateBatteryConsumers[i]);
            }
            for (android.os.UidBatteryConsumer uidBatteryConsumer : batteryUsageStats.getUidBatteryConsumers()) {
                getOrCreateUidBatteryConsumerBuilder(uidBatteryConsumer.getUid()).add(uidBatteryConsumer);
            }
            if (z) {
                this.mBatteryCapacityMah = batteryUsageStats.mBatteryCapacityMah;
                this.mBatteryTimeRemainingMs = batteryUsageStats.mBatteryTimeRemainingMs;
                this.mChargeTimeRemainingMs = batteryUsageStats.mChargeTimeRemainingMs;
            }
            return this;
        }

        void dump(java.io.PrintWriter printWriter) {
            int numRows = this.mBatteryConsumersCursorWindow.getNumRows();
            int i = this.mBatteryConsumerDataLayout.columnCount;
            for (int i2 = 0; i2 < numRows; i2++) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                for (int i3 = 0; i3 < i; i3++) {
                    switch (this.mBatteryConsumersCursorWindow.getType(i2, i3)) {
                        case 0:
                            sb.append("null, ");
                            break;
                        case 1:
                            sb.append(this.mBatteryConsumersCursorWindow.getInt(i2, i3)).append(", ");
                            break;
                        case 2:
                            sb.append(this.mBatteryConsumersCursorWindow.getFloat(i2, i3)).append(", ");
                            break;
                        case 3:
                            sb.append(this.mBatteryConsumersCursorWindow.getString(i2, i3)).append(", ");
                            break;
                        case 4:
                            sb.append("BLOB, ");
                            break;
                    }
                }
                sb.setLength(sb.length() - 2);
                printWriter.println(sb);
            }
        }
    }
}
