package android.nfc;

/* loaded from: classes2.dex */
public class FakeFeatureFlagsImpl implements android.nfc.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.nfc.Flags.FLAG_ENABLE_NFC_CHARGING, false), java.util.Map.entry(android.nfc.Flags.FLAG_ENABLE_NFC_MAINLINE, false), java.util.Map.entry(android.nfc.Flags.FLAG_ENABLE_NFC_READER_OPTION, false), java.util.Map.entry(android.nfc.Flags.FLAG_ENABLE_NFC_SET_DISCOVERY_TECH, false), java.util.Map.entry(android.nfc.Flags.FLAG_ENABLE_NFC_USER_RESTRICTION, false), java.util.Map.entry(android.nfc.Flags.FLAG_ENABLE_TAG_DETECTION_BROADCASTS, false), java.util.Map.entry(android.nfc.Flags.FLAG_NFC_OBSERVE_MODE, false), java.util.Map.entry(android.nfc.Flags.FLAG_NFC_OBSERVE_MODE_ST_SHIM, false), java.util.Map.entry(android.nfc.Flags.FLAG_NFC_READ_POLLING_LOOP, false), java.util.Map.entry(android.nfc.Flags.FLAG_NFC_READ_POLLING_LOOP_ST_SHIM, false), java.util.Map.entry(android.nfc.Flags.FLAG_NFC_VENDOR_CMD, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.nfc.Flags.FLAG_ENABLE_NFC_CHARGING, android.nfc.Flags.FLAG_ENABLE_NFC_MAINLINE, android.nfc.Flags.FLAG_ENABLE_NFC_READER_OPTION, android.nfc.Flags.FLAG_ENABLE_NFC_SET_DISCOVERY_TECH, android.nfc.Flags.FLAG_ENABLE_NFC_USER_RESTRICTION, android.nfc.Flags.FLAG_ENABLE_TAG_DETECTION_BROADCASTS, android.nfc.Flags.FLAG_NFC_OBSERVE_MODE, android.nfc.Flags.FLAG_NFC_OBSERVE_MODE_ST_SHIM, android.nfc.Flags.FLAG_NFC_READ_POLLING_LOOP, android.nfc.Flags.FLAG_NFC_READ_POLLING_LOOP_ST_SHIM, android.nfc.Flags.FLAG_NFC_VENDOR_CMD, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcCharging() {
        return getValue(android.nfc.Flags.FLAG_ENABLE_NFC_CHARGING);
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcMainline() {
        return getValue(android.nfc.Flags.FLAG_ENABLE_NFC_MAINLINE);
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcReaderOption() {
        return getValue(android.nfc.Flags.FLAG_ENABLE_NFC_READER_OPTION);
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcSetDiscoveryTech() {
        return getValue(android.nfc.Flags.FLAG_ENABLE_NFC_SET_DISCOVERY_TECH);
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableNfcUserRestriction() {
        return getValue(android.nfc.Flags.FLAG_ENABLE_NFC_USER_RESTRICTION);
    }

    @Override // android.nfc.FeatureFlags
    public boolean enableTagDetectionBroadcasts() {
        return getValue(android.nfc.Flags.FLAG_ENABLE_TAG_DETECTION_BROADCASTS);
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcObserveMode() {
        return getValue(android.nfc.Flags.FLAG_NFC_OBSERVE_MODE);
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcObserveModeStShim() {
        return getValue(android.nfc.Flags.FLAG_NFC_OBSERVE_MODE_ST_SHIM);
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcReadPollingLoop() {
        return getValue(android.nfc.Flags.FLAG_NFC_READ_POLLING_LOOP);
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcReadPollingLoopStShim() {
        return getValue(android.nfc.Flags.FLAG_NFC_READ_POLLING_LOOP_ST_SHIM);
    }

    @Override // android.nfc.FeatureFlags
    public boolean nfcVendorCmd() {
        return getValue(android.nfc.Flags.FLAG_NFC_VENDOR_CMD);
    }

    public void setFlag(java.lang.String str, boolean z) {
        if (!this.mFlagMap.containsKey(str)) {
            throw new java.lang.IllegalArgumentException("no such flag " + str);
        }
        this.mFlagMap.put(str, java.lang.Boolean.valueOf(z));
    }

    public void resetAll() {
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Boolean>> it = this.mFlagMap.entrySet().iterator();
        while (it.hasNext()) {
            it.next().setValue(null);
        }
    }

    public boolean isFlagReadOnlyOptimized(java.lang.String str) {
        if (this.mReadOnlyFlagsSet.contains(str) && isOptimizationEnabled()) {
            return true;
        }
        return false;
    }

    private boolean getValue(java.lang.String str) {
        java.lang.Boolean bool = this.mFlagMap.get(str);
        if (bool == null) {
            throw new java.lang.IllegalArgumentException(str + " is not set");
        }
        return bool.booleanValue();
    }

    private boolean isOptimizationEnabled() {
        return false;
    }
}
