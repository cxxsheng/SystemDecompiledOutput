package android.os;

/* loaded from: classes3.dex */
public class UserBatteryConsumer extends android.os.BatteryConsumer {
    static final int COLUMN_COUNT = 2;
    private static final int COLUMN_INDEX_USER_ID = 1;
    static final int CONSUMER_TYPE_USER = 2;

    UserBatteryConsumer(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        super(batteryConsumerData);
    }

    private UserBatteryConsumer(android.os.UserBatteryConsumer.Builder builder) {
        super(builder.mData, builder.mPowerComponentsBuilder.build());
    }

    public int getUserId() {
        return this.mData.getInt(1);
    }

    @Override // android.os.BatteryConsumer
    public void dump(java.io.PrintWriter printWriter, boolean z) {
        double consumedPower = getConsumedPower();
        printWriter.print("User ");
        printWriter.print(getUserId());
        printWriter.print(": ");
        printWriter.print(android.os.BatteryStats.formatCharge(consumedPower));
        printWriter.print(" ( ");
        this.mPowerComponents.dump(printWriter, z);
        printWriter.print(" ) ");
    }

    void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        if (getConsumedPower() == 0.0d) {
            return;
        }
        typedXmlSerializer.startTag(null, "user");
        typedXmlSerializer.attributeInt(null, "user_id", getUserId());
        this.mPowerComponents.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, "user");
    }

    static void createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.os.BatteryUsageStats.Builder builder) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.os.UserBatteryConsumer.Builder orCreateUserBatteryConsumerBuilder = builder.getOrCreateUserBatteryConsumerBuilder(typedXmlPullParser.getAttributeInt(null, "user_id"));
        int eventType = typedXmlPullParser.getEventType();
        if (eventType != 2 || !typedXmlPullParser.getName().equals("user")) {
            throw new org.xmlpull.v1.XmlPullParserException("Invalid XML parser state");
        }
        while (true) {
            if ((eventType != 3 || !typedXmlPullParser.getName().equals("user")) && eventType != 1) {
                if (eventType == 2 && typedXmlPullParser.getName().equals("power_components")) {
                    android.os.PowerComponents.parseXml(typedXmlPullParser, orCreateUserBatteryConsumerBuilder.mPowerComponentsBuilder);
                }
                eventType = typedXmlPullParser.next();
            } else {
                return;
            }
        }
    }

    public static final class Builder extends android.os.BatteryConsumer.BaseBuilder<android.os.UserBatteryConsumer.Builder> {
        private java.util.List<android.os.UidBatteryConsumer.Builder> mUidBatteryConsumers;

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder addConsumedPower(int i, double d, int i2) {
            return super.addConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder addConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
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

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder setConsumedPower(int i, double d) {
            return super.setConsumedPower(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder setConsumedPower(int i, double d, int i2) {
            return super.setConsumedPower(i, d, i2);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder setConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            return super.setConsumedPower(key, d, i);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder setConsumedPowerForCustomComponent(int i, double d) {
            return super.setConsumedPowerForCustomComponent(i, d);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder setUsageDurationForCustomComponentMillis(int i, long j) {
            return super.setUsageDurationForCustomComponentMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder setUsageDurationMillis(int i, long j) {
            return super.setUsageDurationMillis(i, j);
        }

        /* JADX WARN: Type inference failed for: r1v1, types: [android.os.BatteryConsumer$BaseBuilder, android.os.UserBatteryConsumer$Builder] */
        @Override // android.os.BatteryConsumer.BaseBuilder
        public /* bridge */ /* synthetic */ android.os.UserBatteryConsumer.Builder setUsageDurationMillis(android.os.BatteryConsumer.Key key, long j) {
            return super.setUsageDurationMillis(key, j);
        }

        Builder(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, int i, double d) {
            super(batteryConsumerData, 2, d);
            batteryConsumerData.putLong(1, i);
        }

        public void addUidBatteryConsumer(android.os.UidBatteryConsumer.Builder builder) {
            if (this.mUidBatteryConsumers == null) {
                this.mUidBatteryConsumers = new java.util.ArrayList();
            }
            this.mUidBatteryConsumers.add(builder);
        }

        public android.os.UserBatteryConsumer build() {
            if (this.mUidBatteryConsumers != null) {
                for (int size = this.mUidBatteryConsumers.size() - 1; size >= 0; size--) {
                    this.mPowerComponentsBuilder.addPowerAndDuration(this.mUidBatteryConsumers.get(size).mPowerComponentsBuilder);
                }
            }
            return new android.os.UserBatteryConsumer(this);
        }
    }
}
