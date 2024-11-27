package android.os;

/* loaded from: classes3.dex */
public final class AggregateBatteryConsumer extends android.os.BatteryConsumer {
    static final int COLUMN_COUNT = 3;
    static final int COLUMN_INDEX_CONSUMED_POWER = 2;
    static final int COLUMN_INDEX_SCOPE = 1;
    static final int CONSUMER_TYPE_AGGREGATE = 0;

    AggregateBatteryConsumer(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        super(batteryConsumerData);
    }

    private AggregateBatteryConsumer(android.os.AggregateBatteryConsumer.Builder builder) {
        super(builder.mData, builder.mPowerComponentsBuilder.build());
    }

    int getScope() {
        return this.mData.getInt(1);
    }

    @Override // android.os.BatteryConsumer
    public void dump(java.io.PrintWriter printWriter, boolean z) {
        this.mPowerComponents.dump(printWriter, z);
    }

    @Override // android.os.BatteryConsumer
    public double getConsumedPower() {
        return this.mData.getDouble(2);
    }

    void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, int i) throws java.io.IOException {
        typedXmlSerializer.startTag(null, "aggregate");
        typedXmlSerializer.attributeInt(null, "scope", i);
        typedXmlSerializer.attributeDouble(null, android.content.Context.POWER_SERVICE, getConsumedPower());
        this.mPowerComponents.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, "aggregate");
    }

    static void parseXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.os.BatteryUsageStats.Builder builder) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.os.AggregateBatteryConsumer.Builder aggregateBatteryConsumerBuilder = builder.getAggregateBatteryConsumerBuilder(typedXmlPullParser.getAttributeInt(null, "scope"));
        int eventType = typedXmlPullParser.getEventType();
        if (eventType != 2 || !typedXmlPullParser.getName().equals("aggregate")) {
            throw new org.xmlpull.v1.XmlPullParserException("Invalid XML parser state");
        }
        aggregateBatteryConsumerBuilder.setConsumedPower(typedXmlPullParser.getAttributeDouble(null, android.content.Context.POWER_SERVICE));
        while (true) {
            if ((eventType != 3 || !typedXmlPullParser.getName().equals("aggregate")) && eventType != 1) {
                if (eventType == 2 && typedXmlPullParser.getName().equals("power_components")) {
                    android.os.PowerComponents.parseXml(typedXmlPullParser, aggregateBatteryConsumerBuilder.mPowerComponentsBuilder);
                }
                eventType = typedXmlPullParser.next();
            } else {
                return;
            }
        }
    }

    void writePowerComponentModelProto(android.util.proto.ProtoOutputStream protoOutputStream) {
        for (int i = 0; i < 18; i++) {
            int powerModel = getPowerModel(i);
            if (powerModel != 0) {
                long start = protoOutputStream.start(2246267895816L);
                protoOutputStream.write(1120986464257L, i);
                protoOutputStream.write(1159641169922L, powerModelToProtoEnum(powerModel));
                protoOutputStream.end(start);
            }
        }
    }

    public static final class Builder extends android.os.BatteryConsumer.BaseBuilder<android.os.AggregateBatteryConsumer.Builder> {
        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder addConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
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

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder setConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder setConsumedPowerForCustomComponent(int i, double d) {
            return super.setConsumedPowerForCustomComponent(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder setUsageDurationForCustomComponentMillis(int i, long j) {
            return super.setUsageDurationForCustomComponentMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.AggregateBatteryConsumer$Builder, android.os.BatteryConsumer$BaseBuilder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.AggregateBatteryConsumer.Builder setUsageDurationMillis(android.os.BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        public Builder(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, int i, double d) {
            super(batteryConsumerData, 0, d);
            batteryConsumerData.putInt(1, i);
        }

        public android.os.AggregateBatteryConsumer.Builder setConsumedPower(double d) {
            this.mData.putDouble(2, d);
            return this;
        }

        public void add(android.os.AggregateBatteryConsumer aggregateBatteryConsumer) {
            setConsumedPower(this.mData.getDouble(2) + aggregateBatteryConsumer.getConsumedPower());
            this.mPowerComponentsBuilder.addPowerAndDuration(aggregateBatteryConsumer.mPowerComponents);
        }

        public android.os.AggregateBatteryConsumer build() {
            return new android.os.AggregateBatteryConsumer(this);
        }
    }
}
