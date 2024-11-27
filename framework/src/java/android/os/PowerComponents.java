package android.os;

/* loaded from: classes3.dex */
class PowerComponents {
    private final android.os.BatteryConsumer.BatteryConsumerData mData;

    PowerComponents(android.os.PowerComponents.Builder builder) {
        this.mData = builder.mData;
    }

    PowerComponents(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData) {
        this.mData = batteryConsumerData;
    }

    public double getConsumedPower(android.os.BatteryConsumer.Dimensions dimensions) {
        if (dimensions.powerComponent != -1) {
            return this.mData.getDouble(this.mData.getKeyOrThrow(dimensions.powerComponent, dimensions.processState).mPowerColumnIndex);
        }
        if (dimensions.processState != 0) {
            if (!this.mData.layout.processStateDataIncluded) {
                throw new java.lang.IllegalArgumentException("No data included in BatteryUsageStats for " + dimensions);
            }
            android.os.BatteryConsumer.Key[] keyArr = this.mData.layout.processStateKeys[dimensions.processState];
            double d = 0.0d;
            for (int length = keyArr.length - 1; length >= 0; length--) {
                d += this.mData.getDouble(keyArr[length].mPowerColumnIndex);
            }
            return d;
        }
        return this.mData.getDouble(this.mData.layout.totalConsumedPowerColumnIndex);
    }

    public double getConsumedPower(android.os.BatteryConsumer.Key key) {
        return this.mData.getDouble(key.mPowerColumnIndex);
    }

    public double getConsumedPowerForCustomComponent(int i) {
        int i2 = i - 1000;
        if (i2 >= 0 && i2 < this.mData.layout.customPowerComponentCount) {
            return this.mData.getDouble(this.mData.layout.firstCustomConsumedPowerColumn + i2);
        }
        throw new java.lang.IllegalArgumentException("Unsupported custom power component ID: " + i);
    }

    public java.lang.String getCustomPowerComponentName(int i) {
        int i2 = i - 1000;
        if (i2 >= 0 && i2 < this.mData.layout.customPowerComponentCount) {
            try {
                return this.mData.layout.customPowerComponentNames[i2];
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                throw new java.lang.IllegalArgumentException("Unsupported custom power component ID: " + i);
            }
        }
        throw new java.lang.IllegalArgumentException("Unsupported custom power component ID: " + i);
    }

    int getPowerModel(android.os.BatteryConsumer.Key key) {
        if (key.mPowerModelColumnIndex == -1) {
            throw new java.lang.IllegalStateException("Power model IDs were not requested in the BatteryUsageStatsQuery");
        }
        return this.mData.getInt(key.mPowerModelColumnIndex);
    }

    public long getUsageDurationMillis(android.os.BatteryConsumer.Key key) {
        return this.mData.getLong(key.mDurationColumnIndex);
    }

    public long getUsageDurationForCustomComponentMillis(int i) {
        int i2 = i - 1000;
        if (i2 >= 0 && i2 < this.mData.layout.customPowerComponentCount) {
            return this.mData.getLong(this.mData.layout.firstCustomUsageDurationColumn + i2);
        }
        throw new java.lang.IllegalArgumentException("Unsupported custom power component ID: " + i);
    }

    public void dump(java.io.PrintWriter printWriter, boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.String str = "";
        int i = 0;
        while (i < 18) {
            android.os.BatteryConsumer.Key[] keys = this.mData.getKeys(i);
            int length = keys.length;
            int i2 = 0;
            while (i2 < length) {
                android.os.BatteryConsumer.Key key = keys[i2];
                double consumedPower = getConsumedPower(key);
                int i3 = i;
                long usageDurationMillis = getUsageDurationMillis(key);
                if (!z || consumedPower != 0.0d || usageDurationMillis != 0) {
                    sb.append(str);
                    sb.append(key.toShortString());
                    sb.append("=");
                    sb.append(android.os.BatteryStats.formatCharge(consumedPower));
                    if (usageDurationMillis != 0) {
                        sb.append(" (");
                        android.os.BatteryStats.formatTimeMsNoSpace(sb, usageDurationMillis);
                        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    }
                    str = " ";
                }
                i2++;
                i = i3;
            }
            i++;
        }
        int i4 = this.mData.layout.customPowerComponentCount;
        for (int i5 = 1000; i5 < i4 + 1000; i5++) {
            double consumedPowerForCustomComponent = getConsumedPowerForCustomComponent(i5);
            if (!z || consumedPowerForCustomComponent != 0.0d) {
                sb.append(str);
                sb.append(getCustomPowerComponentName(i5));
                sb.append("=");
                sb.append(android.os.BatteryStats.formatCharge(consumedPowerForCustomComponent));
                str = " ";
            }
        }
        printWriter.print(sb);
    }

    boolean hasStatsProtoData() {
        return writeStatsProtoImpl(null);
    }

    void writeStatsProto(android.util.proto.ProtoOutputStream protoOutputStream) {
        writeStatsProtoImpl(protoOutputStream);
    }

    private boolean writeStatsProtoImpl(android.util.proto.ProtoOutputStream protoOutputStream) {
        int i;
        int i2;
        boolean z = false;
        for (int i3 = 0; i3 < 18; i3++) {
            android.os.BatteryConsumer.Key[] keys = this.mData.getKeys(i3);
            int length = keys.length;
            int i4 = 0;
            while (i4 < length) {
                android.os.BatteryConsumer.Key key = keys[i4];
                long convertMahToDeciCoulombs = android.os.BatteryConsumer.convertMahToDeciCoulombs(getConsumedPower(key));
                long usageDurationMillis = getUsageDurationMillis(key);
                if (convertMahToDeciCoulombs == 0 && usageDurationMillis == 0) {
                    i = length;
                    i2 = i4;
                } else {
                    if (protoOutputStream == null) {
                        return true;
                    }
                    if (key.processState == 0) {
                        i = length;
                        i2 = i4;
                        writePowerComponentUsage(protoOutputStream, 2246267895810L, i3, convertMahToDeciCoulombs, usageDurationMillis);
                    } else {
                        i = length;
                        i2 = i4;
                        writePowerUsageSlice(protoOutputStream, i3, convertMahToDeciCoulombs, usageDurationMillis, key.processState);
                    }
                    z = true;
                }
                i4 = i2 + 1;
                length = i;
            }
        }
        for (int i5 = 0; i5 < this.mData.layout.customPowerComponentCount; i5++) {
            int i6 = i5 + 1000;
            long convertMahToDeciCoulombs2 = android.os.BatteryConsumer.convertMahToDeciCoulombs(getConsumedPowerForCustomComponent(i6));
            long usageDurationForCustomComponentMillis = getUsageDurationForCustomComponentMillis(i6);
            if (convertMahToDeciCoulombs2 != 0 || usageDurationForCustomComponentMillis != 0) {
                if (protoOutputStream == null) {
                    return true;
                }
                writePowerComponentUsage(protoOutputStream, 2246267895810L, i6, convertMahToDeciCoulombs2, usageDurationForCustomComponentMillis);
                z = true;
            }
        }
        return z;
    }

    private void writePowerUsageSlice(android.util.proto.ProtoOutputStream protoOutputStream, int i, long j, long j2, int i2) {
        int i3;
        long start = protoOutputStream.start(2246267895811L);
        writePowerComponentUsage(protoOutputStream, 1146756268033L, i, j, j2);
        switch (i2) {
            case 1:
                i3 = 1;
                break;
            case 2:
                i3 = 2;
                break;
            case 3:
                i3 = 3;
                break;
            case 4:
                i3 = 4;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Unknown process state: " + i2);
        }
        protoOutputStream.write(1159641169922L, i3);
        protoOutputStream.end(start);
    }

    private void writePowerComponentUsage(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i, long j2, long j3) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, i);
        protoOutputStream.write(1112396529666L, j2);
        protoOutputStream.write(1112396529667L, j3);
        protoOutputStream.end(start);
    }

    void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        android.os.BatteryConsumer.Key[] keyArr;
        int i;
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3 = "power_components";
        typedXmlSerializer.startTag(null, "power_components");
        int i2 = 0;
        while (true) {
            double d = 0.0d;
            if (i2 >= 18) {
                break;
            }
            android.os.BatteryConsumer.Key[] keys = this.mData.getKeys(i2);
            int length = keys.length;
            int i3 = 0;
            while (i3 < length) {
                android.os.BatteryConsumer.Key key = keys[i3];
                java.lang.String str4 = str3;
                double consumedPower = getConsumedPower(key);
                long usageDurationMillis = getUsageDurationMillis(key);
                if (consumedPower == d && usageDurationMillis == 0) {
                    keyArr = keys;
                    i = length;
                } else {
                    typedXmlSerializer.startTag(null, android.service.notification.ZenModeDiff.RuleDiff.FIELD_COMPONENT);
                    typedXmlSerializer.attributeInt(null, "id", i2);
                    if (key.processState == 0) {
                        keyArr = keys;
                        i = length;
                        str = null;
                    } else {
                        keyArr = keys;
                        i = length;
                        str = null;
                        typedXmlSerializer.attributeInt(null, "process_state", key.processState);
                    }
                    if (consumedPower != d) {
                        typedXmlSerializer.attributeDouble(str, android.content.Context.POWER_SERVICE, consumedPower);
                    }
                    if (usageDurationMillis != 0) {
                        typedXmlSerializer.attributeLong(str, "duration", usageDurationMillis);
                    }
                    if (!this.mData.layout.powerModelsIncluded) {
                        str2 = null;
                    } else {
                        int powerModel = getPowerModel(key);
                        str2 = null;
                        typedXmlSerializer.attributeInt(null, "model", powerModel);
                    }
                    typedXmlSerializer.endTag(str2, android.service.notification.ZenModeDiff.RuleDiff.FIELD_COMPONENT);
                }
                i3++;
                str3 = str4;
                keys = keyArr;
                length = i;
                d = 0.0d;
            }
            i2++;
        }
        java.lang.String str5 = str3;
        int i4 = this.mData.layout.customPowerComponentCount + 1000;
        for (int i5 = 1000; i5 < i4; i5++) {
            double consumedPowerForCustomComponent = getConsumedPowerForCustomComponent(i5);
            long usageDurationForCustomComponentMillis = getUsageDurationForCustomComponentMillis(i5);
            if (consumedPowerForCustomComponent != 0.0d || usageDurationForCustomComponentMillis != 0) {
                typedXmlSerializer.startTag(null, "custom_component");
                typedXmlSerializer.attributeInt(null, "id", i5);
                if (consumedPowerForCustomComponent != 0.0d) {
                    typedXmlSerializer.attributeDouble(null, android.content.Context.POWER_SERVICE, consumedPowerForCustomComponent);
                }
                if (usageDurationForCustomComponentMillis != 0) {
                    typedXmlSerializer.attributeLong(null, "duration", usageDurationForCustomComponentMillis);
                }
                typedXmlSerializer.endTag(null, "custom_component");
            }
        }
        typedXmlSerializer.endTag(null, str5);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static void parseXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.os.PowerComponents.Builder builder) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        char c;
        char c2;
        char c3;
        int eventType = typedXmlPullParser.getEventType();
        int i = 2;
        if (eventType != 2 || !typedXmlPullParser.getName().equals("power_components")) {
            throw new org.xmlpull.v1.XmlPullParserException("Invalid XML parser state");
        }
        while (true) {
            if ((eventType != 3 || !typedXmlPullParser.getName().equals("power_components")) && eventType != 1) {
                if (eventType == i) {
                    java.lang.String name = typedXmlPullParser.getName();
                    switch (name.hashCode()) {
                        case -1399907075:
                            if (name.equals(android.service.notification.ZenModeDiff.RuleDiff.FIELD_COMPONENT)) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case -437794385:
                            if (name.equals("custom_component")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    long j = 0;
                    double d = 0.0d;
                    switch (c) {
                        case 0:
                            int i2 = -1;
                            int i3 = 0;
                            int i4 = 0;
                            for (int i5 = 0; i5 < typedXmlPullParser.getAttributeCount(); i5++) {
                                java.lang.String attributeName = typedXmlPullParser.getAttributeName(i5);
                                switch (attributeName.hashCode()) {
                                    case -1992012396:
                                        if (attributeName.equals("duration")) {
                                            c2 = 3;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 3355:
                                        if (attributeName.equals("id")) {
                                            c2 = 0;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 104069929:
                                        if (attributeName.equals("model")) {
                                            c2 = 4;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 106858757:
                                        if (attributeName.equals(android.content.Context.POWER_SERVICE)) {
                                            c2 = 2;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    case 1664710337:
                                        if (attributeName.equals("process_state")) {
                                            c2 = 1;
                                            break;
                                        }
                                        c2 = 65535;
                                        break;
                                    default:
                                        c2 = 65535;
                                        break;
                                }
                                switch (c2) {
                                    case 0:
                                        i2 = typedXmlPullParser.getAttributeInt(i5);
                                        break;
                                    case 1:
                                        i3 = typedXmlPullParser.getAttributeInt(i5);
                                        break;
                                    case 2:
                                        d = typedXmlPullParser.getAttributeDouble(i5);
                                        break;
                                    case 3:
                                        j = typedXmlPullParser.getAttributeLong(i5);
                                        break;
                                    case 4:
                                        i4 = typedXmlPullParser.getAttributeInt(i5);
                                        break;
                                }
                            }
                            android.os.BatteryConsumer.Key key = builder.mData.getKey(i2, i3);
                            builder.setConsumedPower(key, d, i4);
                            builder.setUsageDurationMillis(key, j);
                            break;
                        case 1:
                            int i6 = -1;
                            for (int i7 = 0; i7 < typedXmlPullParser.getAttributeCount(); i7++) {
                                java.lang.String attributeName2 = typedXmlPullParser.getAttributeName(i7);
                                switch (attributeName2.hashCode()) {
                                    case -1992012396:
                                        if (attributeName2.equals("duration")) {
                                            c3 = 2;
                                            break;
                                        }
                                        c3 = 65535;
                                        break;
                                    case 3355:
                                        if (attributeName2.equals("id")) {
                                            c3 = 0;
                                            break;
                                        }
                                        c3 = 65535;
                                        break;
                                    case 106858757:
                                        if (attributeName2.equals(android.content.Context.POWER_SERVICE)) {
                                            c3 = 1;
                                            break;
                                        }
                                        c3 = 65535;
                                        break;
                                    default:
                                        c3 = 65535;
                                        break;
                                }
                                switch (c3) {
                                    case 0:
                                        i6 = typedXmlPullParser.getAttributeInt(i7);
                                        break;
                                    case 1:
                                        d = typedXmlPullParser.getAttributeDouble(i7);
                                        break;
                                    case 2:
                                        j = typedXmlPullParser.getAttributeLong(i7);
                                        break;
                                }
                            }
                            builder.setConsumedPowerForCustomComponent(i6, d);
                            builder.setUsageDurationForCustomComponentMillis(i6, j);
                            break;
                    }
                }
                eventType = typedXmlPullParser.next();
                i = 2;
            } else {
                return;
            }
        }
    }

    static final class Builder {
        private static final byte POWER_MODEL_UNINITIALIZED = -1;
        private final android.os.BatteryConsumer.BatteryConsumerData mData;
        private final double mMinConsumedPowerThreshold;

        Builder(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData, double d) {
            this.mData = batteryConsumerData;
            this.mMinConsumedPowerThreshold = d;
            for (android.os.BatteryConsumer.Key[] keyArr : this.mData.layout.keys) {
                for (android.os.BatteryConsumer.Key key : keyArr) {
                    if (key.mPowerModelColumnIndex != -1) {
                        this.mData.putInt(key.mPowerModelColumnIndex, -1);
                    }
                }
            }
        }

        public android.os.PowerComponents.Builder setConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            this.mData.putDouble(key.mPowerColumnIndex, d);
            if (key.mPowerModelColumnIndex != -1) {
                this.mData.putInt(key.mPowerModelColumnIndex, i);
            }
            return this;
        }

        public android.os.PowerComponents.Builder addConsumedPower(android.os.BatteryConsumer.Key key, double d, int i) {
            this.mData.putDouble(key.mPowerColumnIndex, this.mData.getDouble(key.mPowerColumnIndex) + d);
            if (key.mPowerModelColumnIndex != -1) {
                this.mData.putInt(key.mPowerModelColumnIndex, i);
            }
            return this;
        }

        public android.os.PowerComponents.Builder setConsumedPowerForCustomComponent(int i, double d) {
            int i2 = i - 1000;
            if (i2 < 0 || i2 >= this.mData.layout.customPowerComponentCount) {
                throw new java.lang.IllegalArgumentException("Unsupported custom power component ID: " + i);
            }
            this.mData.putDouble(this.mData.layout.firstCustomConsumedPowerColumn + i2, d);
            return this;
        }

        public android.os.PowerComponents.Builder setUsageDurationMillis(android.os.BatteryConsumer.Key key, long j) {
            this.mData.putLong(key.mDurationColumnIndex, j);
            return this;
        }

        public android.os.PowerComponents.Builder setUsageDurationForCustomComponentMillis(int i, long j) {
            int i2 = i - 1000;
            if (i2 < 0 || i2 >= this.mData.layout.customPowerComponentCount) {
                throw new java.lang.IllegalArgumentException("Unsupported custom power component ID: " + i);
            }
            this.mData.putLong(this.mData.layout.firstCustomUsageDurationColumn + i2, j);
            return this;
        }

        public void addPowerAndDuration(android.os.PowerComponents.Builder builder) {
            addPowerAndDuration(builder.mData);
        }

        public void addPowerAndDuration(android.os.PowerComponents powerComponents) {
            addPowerAndDuration(powerComponents.mData);
        }

        private void addPowerAndDuration(android.os.BatteryConsumer.BatteryConsumerData batteryConsumerData) {
            android.os.BatteryConsumer.Key key;
            boolean z;
            if (this.mData.layout.customPowerComponentCount != batteryConsumerData.layout.customPowerComponentCount) {
                throw new java.lang.IllegalArgumentException("Number of custom power components does not match: " + batteryConsumerData.layout.customPowerComponentCount + ", expected: " + this.mData.layout.customPowerComponentCount);
            }
            for (int i = 17; i >= 0; i--) {
                for (android.os.BatteryConsumer.Key key2 : this.mData.layout.keys[i]) {
                    android.os.BatteryConsumer.Key[] keyArr = batteryConsumerData.layout.keys[i];
                    int length = keyArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            key = null;
                            break;
                        }
                        key = keyArr[i2];
                        if (key.equals(key2)) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (key != null) {
                        this.mData.putDouble(key2.mPowerColumnIndex, this.mData.getDouble(key2.mPowerColumnIndex) + batteryConsumerData.getDouble(key.mPowerColumnIndex));
                        this.mData.putLong(key2.mDurationColumnIndex, this.mData.getLong(key2.mDurationColumnIndex) + batteryConsumerData.getLong(key.mDurationColumnIndex));
                        if (key2.mPowerModelColumnIndex != -1) {
                            if (key.mPowerModelColumnIndex == -1) {
                                z = true;
                            } else {
                                int i3 = this.mData.getInt(key2.mPowerModelColumnIndex);
                                int i4 = batteryConsumerData.getInt(key.mPowerModelColumnIndex);
                                if (i3 == -1) {
                                    this.mData.putInt(key2.mPowerModelColumnIndex, i4);
                                } else if (i3 != i4 && i4 != -1) {
                                    z = true;
                                }
                                z = false;
                            }
                            if (z) {
                                this.mData.putInt(key2.mPowerModelColumnIndex, 0);
                            }
                        }
                    }
                }
            }
            for (int i5 = this.mData.layout.customPowerComponentCount - 1; i5 >= 0; i5--) {
                int i6 = this.mData.layout.firstCustomConsumedPowerColumn + i5;
                this.mData.putDouble(i6, this.mData.getDouble(i6) + batteryConsumerData.getDouble(batteryConsumerData.layout.firstCustomConsumedPowerColumn + i5));
                int i7 = this.mData.layout.firstCustomUsageDurationColumn + i5;
                this.mData.putLong(i7, this.mData.getLong(i7) + batteryConsumerData.getLong(batteryConsumerData.layout.firstCustomUsageDurationColumn + i5));
            }
        }

        public double getTotalPower() {
            double d = 0.0d;
            for (int i = 0; i < 18; i++) {
                d += this.mData.getDouble(this.mData.getKeyOrThrow(i, 0).mPowerColumnIndex);
            }
            for (int i2 = 0; i2 < this.mData.layout.customPowerComponentCount; i2++) {
                d += this.mData.getDouble(this.mData.layout.firstCustomConsumedPowerColumn + i2);
            }
            return d;
        }

        public android.os.PowerComponents build() {
            for (android.os.BatteryConsumer.Key[] keyArr : this.mData.layout.keys) {
                for (android.os.BatteryConsumer.Key key : keyArr) {
                    if (key.mPowerModelColumnIndex != -1 && this.mData.getInt(key.mPowerModelColumnIndex) == -1) {
                        this.mData.putInt(key.mPowerModelColumnIndex, 0);
                    }
                    if (this.mMinConsumedPowerThreshold != 0.0d && this.mData.getDouble(key.mPowerColumnIndex) < this.mMinConsumedPowerThreshold) {
                        this.mData.putDouble(key.mPowerColumnIndex, 0.0d);
                    }
                }
            }
            if (this.mData.getDouble(this.mData.layout.totalConsumedPowerColumnIndex) == 0.0d) {
                this.mData.putDouble(this.mData.layout.totalConsumedPowerColumnIndex, getTotalPower());
            }
            return new android.os.PowerComponents(this);
        }
    }
}
