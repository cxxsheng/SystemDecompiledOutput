package android.os;

/* loaded from: classes3.dex */
public final class UidBatteryConsumer extends android.os.BatteryConsumer {
    static final int COLUMN_COUNT = 6;
    static final int COLUMN_INDEX_PACKAGE_WITH_HIGHEST_DRAIN = 2;
    static final int COLUMN_INDEX_TIME_IN_BACKGROUND = 4;
    static final int COLUMN_INDEX_TIME_IN_FOREGROUND = 3;
    static final int COLUMN_INDEX_TIME_IN_FOREGROUND_SERVICE = 5;
    static final int COLUMN_INDEX_UID = 1;
    static final int CONSUMER_TYPE_UID = 1;
    public static final int STATE_BACKGROUND = 1;
    public static final int STATE_FOREGROUND = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    UidBatteryConsumer(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        super(batteryConsumerData);
    }

    private UidBatteryConsumer(android.os.UidBatteryConsumer.Builder builder) {
        super(builder.mData, builder.mPowerComponentsBuilder.build());
    }

    public int getUid() {
        return this.mData.getInt(1);
    }

    public java.lang.String getPackageWithHighestDrain() {
        return this.mData.getString(2);
    }

    @java.lang.Deprecated
    public long getTimeInStateMs(int i) {
        switch (i) {
            case 0:
                return this.mData.getInt(3);
            case 1:
                return this.mData.getInt(4) + this.mData.getInt(5);
            default:
                return 0L;
        }
    }

    public long getTimeInProcessStateMs(int i) {
        switch (i) {
            case 1:
                return this.mData.getInt(3);
            case 2:
                return this.mData.getInt(4);
            case 3:
                return this.mData.getInt(5);
            default:
                return 0L;
        }
    }

    @Override // android.os.BatteryConsumer
    public void dump(java.io.PrintWriter printWriter, boolean z) {
        printWriter.print("UID ");
        android.os.UserHandle.formatUid(printWriter, getUid());
        printWriter.print(": ");
        printWriter.print(android.os.BatteryStats.formatCharge(getConsumedPower()));
        if (this.mData.layout.processStateDataIncluded) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            appendProcessStateData(sb, 1, z);
            appendProcessStateData(sb, 2, z);
            appendProcessStateData(sb, 3, z);
            appendProcessStateData(sb, 4, z);
            printWriter.print(sb);
        }
        printWriter.print(" ( ");
        this.mPowerComponents.dump(printWriter, z);
        printWriter.print(" ) ");
    }

    private void appendProcessStateData(java.lang.StringBuilder sb, int i, boolean z) {
        double consumedPower = this.mPowerComponents.getConsumedPower(new android.os.BatteryConsumer.Dimensions(-1, i));
        if (consumedPower == 0.0d && z) {
            return;
        }
        sb.append(" ").append(processStateToString(i)).append(": ").append(android.os.BatteryStats.formatCharge(consumedPower));
    }

    static android.os.UidBatteryConsumer create(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        return new android.os.UidBatteryConsumer(batteryConsumerData);
    }

    void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        if (getConsumedPower() == 0.0d) {
            return;
        }
        typedXmlSerializer.startTag(null, "uid");
        typedXmlSerializer.attributeInt(null, "uid", getUid());
        java.lang.String packageWithHighestDrain = getPackageWithHighestDrain();
        if (!android.text.TextUtils.isEmpty(packageWithHighestDrain)) {
            typedXmlSerializer.attribute(null, "highest_drain_package", packageWithHighestDrain);
        }
        typedXmlSerializer.attributeLong(null, "time_in_foreground", getTimeInProcessStateMs(1));
        typedXmlSerializer.attributeLong(null, "time_in_background", getTimeInProcessStateMs(2));
        typedXmlSerializer.attributeLong(null, "time_in_foreground_service", getTimeInProcessStateMs(3));
        this.mPowerComponents.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, "uid");
    }

    static void createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.os.BatteryUsageStats.Builder builder) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.os.UidBatteryConsumer.Builder orCreateUidBatteryConsumerBuilder = builder.getOrCreateUidBatteryConsumerBuilder(typedXmlPullParser.getAttributeInt(null, "uid"));
        int eventType = typedXmlPullParser.getEventType();
        if (eventType != 2 || !typedXmlPullParser.getName().equals("uid")) {
            throw new org.xmlpull.v1.XmlPullParserException("Invalid XML parser state");
        }
        orCreateUidBatteryConsumerBuilder.setPackageWithHighestDrain(typedXmlPullParser.getAttributeValue(null, "highest_drain_package"));
        orCreateUidBatteryConsumerBuilder.setTimeInProcessStateMs(1, typedXmlPullParser.getAttributeLong(null, "time_in_foreground"));
        orCreateUidBatteryConsumerBuilder.setTimeInProcessStateMs(2, typedXmlPullParser.getAttributeLong(null, "time_in_background"));
        orCreateUidBatteryConsumerBuilder.setTimeInProcessStateMs(3, typedXmlPullParser.getAttributeLong(null, "time_in_foreground_service"));
        while (true) {
            if ((eventType != 3 || !typedXmlPullParser.getName().equals("uid")) && eventType != 1) {
                if (eventType == 2 && typedXmlPullParser.getName().equals("power_components")) {
                    android.os.PowerComponents.parseXml(typedXmlPullParser, orCreateUidBatteryConsumerBuilder.mPowerComponentsBuilder);
                }
                eventType = typedXmlPullParser.next();
            } else {
                return;
            }
        }
    }

    public static final class Builder extends android.os.BatteryConsumer.BaseBuilder<android.os.UidBatteryConsumer.Builder> {
        private static final java.lang.String PACKAGE_NAME_UNINITIALIZED = "";
        private final android.os.BatteryStats.Uid mBatteryStatsUid;
        private boolean mExcludeFromBatteryUsageStats;
        private final boolean mIsVirtualUid;
        private java.lang.String mPackageWithHighestDrain;
        private final int mUid;

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder addConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            return super.addConsumedPower(key, d, i);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.BatteryConsumer.Key getKey(int i, int i2) {
            return super.getKey(i, i2);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.BatteryConsumer.Key[] getKeys(int i) {
            return super.getKeys(i);
        }

        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ double getTotalPower() {
            return super.getTotalPower();
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder setConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder setConsumedPowerForCustomComponent(int i, double d) {
            return super.setConsumedPowerForCustomComponent(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder setUsageDurationForCustomComponentMillis(int i, long j) {
            return super.setUsageDurationForCustomComponentMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UidBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UidBatteryConsumer.Builder setUsageDurationMillis(android.os.BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        public Builder(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, android.os.BatteryStats.Uid uid, double d) {
            this(batteryConsumerData, uid, uid.getUid(), d);
        }

        public Builder(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, int i, double d) {
            this(batteryConsumerData, null, i, d);
        }

        private Builder(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, android.os.BatteryStats.Uid uid, int i, double d) {
            super(batteryConsumerData, 1, d);
            this.mPackageWithHighestDrain = "";
            this.mBatteryStatsUid = uid;
            this.mUid = i;
            this.mIsVirtualUid = this.mUid == 1090;
            batteryConsumerData.putLong(1, this.mUid);
        }

        public android.os.BatteryStats.Uid getBatteryStatsUid() {
            if (this.mBatteryStatsUid == null) {
                throw new java.lang.IllegalStateException("UidBatteryConsumer.Builder was initialized without a BatteryStats.Uid");
            }
            return this.mBatteryStatsUid;
        }

        public int getUid() {
            return this.mUid;
        }

        public boolean isVirtualUid() {
            return this.mIsVirtualUid;
        }

        public android.os.UidBatteryConsumer.Builder setPackageWithHighestDrain(java.lang.String str) {
            this.mPackageWithHighestDrain = android.text.TextUtils.nullIfEmpty(str);
            return this;
        }

        @java.lang.Deprecated
        public android.os.UidBatteryConsumer.Builder setTimeInStateMs(int i, long j) {
            switch (i) {
                case 0:
                    this.mData.putLong(3, j);
                    return this;
                case 1:
                    this.mData.putLong(4, j);
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported state: " + i);
            }
        }

        public android.os.UidBatteryConsumer.Builder setTimeInProcessStateMs(int i, long j) {
            switch (i) {
                case 1:
                    this.mData.putLong(3, j);
                    return this;
                case 2:
                    this.mData.putLong(4, j);
                    return this;
                case 3:
                    this.mData.putLong(5, j);
                    return this;
                default:
                    throw new java.lang.IllegalArgumentException("Unsupported process state: " + i);
            }
        }

        public android.os.UidBatteryConsumer.Builder excludeFromBatteryUsageStats() {
            this.mExcludeFromBatteryUsageStats = true;
            return this;
        }

        public android.os.UidBatteryConsumer.Builder add(android.os.UidBatteryConsumer uidBatteryConsumer) {
            this.mPowerComponentsBuilder.addPowerAndDuration(uidBatteryConsumer.mPowerComponents);
            setTimeInProcessStateMs(1, this.mData.getLong(3) + uidBatteryConsumer.getTimeInProcessStateMs(1));
            setTimeInProcessStateMs(2, this.mData.getLong(4) + uidBatteryConsumer.getTimeInProcessStateMs(2));
            setTimeInProcessStateMs(3, this.mData.getLong(5) + uidBatteryConsumer.getTimeInProcessStateMs(3));
            if (this.mPackageWithHighestDrain == "") {
                this.mPackageWithHighestDrain = uidBatteryConsumer.getPackageWithHighestDrain();
            } else if (!android.text.TextUtils.equals(this.mPackageWithHighestDrain, uidBatteryConsumer.getPackageWithHighestDrain())) {
                this.mPackageWithHighestDrain = null;
            }
            return this;
        }

        public boolean isExcludedFromBatteryUsageStats() {
            return this.mExcludeFromBatteryUsageStats;
        }

        public android.os.UidBatteryConsumer build() {
            if (this.mPackageWithHighestDrain == "") {
                this.mPackageWithHighestDrain = null;
            }
            if (this.mPackageWithHighestDrain != null) {
                this.mData.putString(2, this.mPackageWithHighestDrain);
            }
            return new android.os.UidBatteryConsumer(this);
        }
    }
}
