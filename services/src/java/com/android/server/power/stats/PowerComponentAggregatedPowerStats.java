package com.android.server.power.stats;

/* loaded from: classes2.dex */
class PowerComponentAggregatedPowerStats {
    private static final long UNKNOWN = -1;
    static final java.lang.String XML_ATTR_ID = "id";
    private static final java.lang.String XML_ATTR_UID = "uid";
    private static final java.lang.String XML_TAG_DEVICE_STATS = "device-stats";
    static final java.lang.String XML_TAG_POWER_COMPONENT = "power_component";
    private static final java.lang.String XML_TAG_UID_STATS = "uid-stats";

    @android.annotation.NonNull
    private final com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent mConfig;
    private final com.android.server.power.stats.MultiStateStats.States[] mDeviceStateConfig;
    private final int[] mDeviceStates;
    private com.android.server.power.stats.MultiStateStats mDeviceStats;
    private com.android.internal.os.PowerStats.Descriptor mPowerStatsDescriptor;
    private com.android.server.power.stats.MultiStateStats.Factory mStatsFactory;
    private final com.android.server.power.stats.MultiStateStats.States[] mUidStateConfig;
    private com.android.server.power.stats.MultiStateStats.Factory mUidStatsFactory;
    public final int powerComponentId;
    private final android.util.SparseArray<com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats> mUidStats = new android.util.SparseArray<>();
    private long mPowerStatsTimestamp = -1;

    private static class UidStats {
        public int[] states;
        public com.android.server.power.stats.MultiStateStats stats;

        private UidStats() {
        }
    }

    PowerComponentAggregatedPowerStats(com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent powerComponent) {
        this.mConfig = powerComponent;
        this.powerComponentId = powerComponent.getPowerComponentId();
        this.mDeviceStateConfig = powerComponent.getDeviceStateConfig();
        this.mUidStateConfig = powerComponent.getUidStateConfig();
        this.mDeviceStates = new int[this.mDeviceStateConfig.length];
    }

    @android.annotation.NonNull
    public com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent getConfig() {
        return this.mConfig;
    }

    @android.annotation.Nullable
    public com.android.internal.os.PowerStats.Descriptor getPowerStatsDescriptor() {
        return this.mPowerStatsDescriptor;
    }

    void setState(int i, int i2, long j) {
        if (this.mDeviceStats == null) {
            createDeviceStats();
        }
        this.mDeviceStates[i] = i2;
        if (this.mDeviceStateConfig[i].isTracked() && this.mDeviceStats != null) {
            this.mDeviceStats.setState(i, i2, j);
        }
        if (this.mUidStateConfig[i].isTracked()) {
            for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
                com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats valueAt = this.mUidStats.valueAt(size);
                if (valueAt.stats == null) {
                    createUidStats(valueAt);
                }
                valueAt.states[i] = i2;
                if (valueAt.stats != null) {
                    valueAt.stats.setState(i, i2, j);
                }
            }
        }
    }

    void setUidState(int i, int i2, int i3, long j) {
        if (!this.mUidStateConfig[i2].isTracked()) {
            return;
        }
        com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats = getUidStats(i);
        if (uidStats.stats == null) {
            createUidStats(uidStats);
        }
        uidStats.states[i2] = i3;
        if (uidStats.stats != null) {
            uidStats.stats.setState(i2, i3, j);
        }
    }

    void setDeviceStats(int[] iArr, long[] jArr) {
        this.mDeviceStats.setStats(iArr, jArr);
    }

    void setUidStats(int i, int[] iArr, long[] jArr) {
        getUidStats(i).stats.setStats(iArr, jArr);
    }

    boolean isCompatible(com.android.internal.os.PowerStats powerStats) {
        return this.mPowerStatsDescriptor == null || this.mPowerStatsDescriptor.equals(powerStats.descriptor);
    }

    void addPowerStats(com.android.internal.os.PowerStats powerStats, long j) {
        this.mPowerStatsDescriptor = powerStats.descriptor;
        if (this.mDeviceStats == null) {
            createDeviceStats();
        }
        this.mDeviceStats.increment(powerStats.stats, j);
        for (int size = powerStats.uidStats.size() - 1; size >= 0; size--) {
            com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats = getUidStats(powerStats.uidStats.keyAt(size));
            if (uidStats.stats == null) {
                createUidStats(uidStats);
            }
            uidStats.stats.increment((long[]) powerStats.uidStats.valueAt(size), j);
        }
        this.mPowerStatsTimestamp = j;
    }

    void reset() {
        this.mStatsFactory = null;
        this.mUidStatsFactory = null;
        this.mDeviceStats = null;
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            this.mUidStats.valueAt(size).stats = null;
        }
    }

    private com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats getUidStats(int i) {
        com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats = this.mUidStats.get(i);
        if (uidStats == null) {
            com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats2 = new com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats();
            uidStats2.states = new int[this.mUidStateConfig.length];
            this.mUidStats.put(i, uidStats2);
            return uidStats2;
        }
        return uidStats;
    }

    void collectUids(java.util.Collection<java.lang.Integer> collection) {
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            if (this.mUidStats.valueAt(size).stats != null) {
                collection.add(java.lang.Integer.valueOf(this.mUidStats.keyAt(size)));
            }
        }
    }

    boolean getDeviceStats(long[] jArr, int[] iArr) {
        if (iArr.length != this.mDeviceStateConfig.length) {
            throw new java.lang.IllegalArgumentException("Invalid number of tracked states: " + iArr.length + " expected: " + this.mDeviceStateConfig.length);
        }
        if (this.mDeviceStats != null) {
            this.mDeviceStats.getStats(jArr, iArr);
            return true;
        }
        return false;
    }

    boolean getUidStats(long[] jArr, int i, int[] iArr) {
        if (iArr.length != this.mUidStateConfig.length) {
            throw new java.lang.IllegalArgumentException("Invalid number of tracked states: " + iArr.length + " expected: " + this.mUidStateConfig.length);
        }
        com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats = this.mUidStats.get(i);
        if (uidStats != null && uidStats.stats != null) {
            uidStats.stats.getStats(jArr, iArr);
            return true;
        }
        return false;
    }

    private void createDeviceStats() {
        if (this.mStatsFactory == null) {
            if (this.mPowerStatsDescriptor == null) {
                return;
            } else {
                this.mStatsFactory = new com.android.server.power.stats.MultiStateStats.Factory(this.mPowerStatsDescriptor.statsArrayLength, this.mDeviceStateConfig);
            }
        }
        this.mDeviceStats = this.mStatsFactory.create();
        if (this.mPowerStatsTimestamp != -1) {
            for (int i = 0; i < this.mDeviceStateConfig.length; i++) {
                this.mDeviceStats.setState(i, this.mDeviceStates[i], this.mPowerStatsTimestamp);
            }
        }
    }

    private void createUidStats(com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats) {
        if (this.mUidStatsFactory == null) {
            if (this.mPowerStatsDescriptor == null) {
                return;
            } else {
                this.mUidStatsFactory = new com.android.server.power.stats.MultiStateStats.Factory(this.mPowerStatsDescriptor.uidStatsArrayLength, this.mUidStateConfig);
            }
        }
        uidStats.stats = this.mUidStatsFactory.create();
        for (int i = 0; i < this.mUidStateConfig.length; i++) {
            if (this.mPowerStatsTimestamp != -1) {
                uidStats.stats.setState(i, uidStats.states[i], this.mPowerStatsTimestamp);
            }
        }
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        if (this.mPowerStatsDescriptor == null) {
            return;
        }
        typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_POWER_COMPONENT);
        typedXmlSerializer.attributeInt((java.lang.String) null, XML_ATTR_ID, this.powerComponentId);
        this.mPowerStatsDescriptor.writeXml(typedXmlSerializer);
        if (this.mDeviceStats != null) {
            typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_DEVICE_STATS);
            this.mDeviceStats.writeXml(typedXmlSerializer);
            typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_DEVICE_STATS);
        }
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            int keyAt = this.mUidStats.keyAt(size);
            com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats valueAt = this.mUidStats.valueAt(size);
            if (valueAt.stats != null) {
                typedXmlSerializer.startTag((java.lang.String) null, XML_TAG_UID_STATS);
                typedXmlSerializer.attributeInt((java.lang.String) null, "uid", keyAt);
                valueAt.stats.writeXml(typedXmlSerializer);
                typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_UID_STATS);
            }
        }
        typedXmlSerializer.endTag((java.lang.String) null, XML_TAG_POWER_COMPONENT);
        typedXmlSerializer.flush();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0071, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0032, code lost:
    
        if (r0.equals(com.android.server.power.stats.PowerComponentAggregatedPowerStats.XML_TAG_DEVICE_STATS) != false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int eventType = typedXmlPullParser.getEventType();
        while (true) {
            char c = 1;
            if (eventType == 1) {
                return true;
            }
            if (eventType == 2) {
                java.lang.String name = typedXmlPullParser.getName();
                switch (name.hashCode()) {
                    case -2016846232:
                        break;
                    case -748366993:
                        if (name.equals("descriptor")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1541808354:
                        if (name.equals(XML_TAG_UID_STATS)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        this.mPowerStatsDescriptor = com.android.internal.os.PowerStats.Descriptor.createFromXml(typedXmlPullParser);
                        if (this.mPowerStatsDescriptor != null) {
                            break;
                        } else {
                            return false;
                        }
                    case 1:
                        if (this.mDeviceStats == null) {
                            createDeviceStats();
                        }
                        if (!this.mDeviceStats.readFromXml(typedXmlPullParser)) {
                            return false;
                        }
                        break;
                    case 2:
                        com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats = getUidStats(typedXmlPullParser.getAttributeInt((java.lang.String) null, "uid"));
                        if (uidStats.stats == null) {
                            createUidStats(uidStats);
                        }
                        if (!uidStats.stats.readFromXml(typedXmlPullParser)) {
                            return false;
                        }
                        break;
                }
            }
            eventType = typedXmlPullParser.next();
        }
    }

    void dumpDevice(android.util.IndentingPrintWriter indentingPrintWriter) {
        if (this.mDeviceStats != null) {
            indentingPrintWriter.println(this.mPowerStatsDescriptor.name);
            indentingPrintWriter.increaseIndent();
            this.mDeviceStats.dump(indentingPrintWriter, new java.util.function.Function() { // from class: com.android.server.power.stats.PowerComponentAggregatedPowerStats$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$dumpDevice$0;
                    lambda$dumpDevice$0 = com.android.server.power.stats.PowerComponentAggregatedPowerStats.this.lambda$dumpDevice$0((long[]) obj);
                    return lambda$dumpDevice$0;
                }
            });
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$dumpDevice$0(long[] jArr) {
        return this.mConfig.getProcessor().deviceStatsToString(this.mPowerStatsDescriptor, jArr);
    }

    void dumpUid(android.util.IndentingPrintWriter indentingPrintWriter, int i) {
        com.android.server.power.stats.PowerComponentAggregatedPowerStats.UidStats uidStats = this.mUidStats.get(i);
        if (uidStats != null && uidStats.stats != null) {
            indentingPrintWriter.println(this.mPowerStatsDescriptor.name);
            indentingPrintWriter.increaseIndent();
            uidStats.stats.dump(indentingPrintWriter, new java.util.function.Function() { // from class: com.android.server.power.stats.PowerComponentAggregatedPowerStats$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String lambda$dumpUid$1;
                    lambda$dumpUid$1 = com.android.server.power.stats.PowerComponentAggregatedPowerStats.this.lambda$dumpUid$1((long[]) obj);
                    return lambda$dumpUid$1;
                }
            });
            indentingPrintWriter.decreaseIndent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$dumpUid$1(long[] jArr) {
        return this.mConfig.getProcessor().uidStatsToString(this.mPowerStatsDescriptor, jArr);
    }
}
