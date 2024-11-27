package android.companion;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.companion.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.companion.Flags.FLAG_ASSOCIATION_TAG, false), java.util.Map.entry(android.companion.Flags.FLAG_COMPANION_TRANSPORT_APIS, false), java.util.Map.entry(android.companion.Flags.FLAG_DEVICE_PRESENCE, false), java.util.Map.entry(android.companion.Flags.FLAG_NEW_ASSOCIATION_BUILDER, false), java.util.Map.entry(android.companion.Flags.FLAG_PERM_SYNC_USER_CONSENT, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.companion.Flags.FLAG_ASSOCIATION_TAG, android.companion.Flags.FLAG_COMPANION_TRANSPORT_APIS, android.companion.Flags.FLAG_DEVICE_PRESENCE, android.companion.Flags.FLAG_NEW_ASSOCIATION_BUILDER, android.companion.Flags.FLAG_PERM_SYNC_USER_CONSENT, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.companion.FeatureFlags
    public boolean associationTag() {
        return getValue(android.companion.Flags.FLAG_ASSOCIATION_TAG);
    }

    @Override // android.companion.FeatureFlags
    public boolean companionTransportApis() {
        return getValue(android.companion.Flags.FLAG_COMPANION_TRANSPORT_APIS);
    }

    @Override // android.companion.FeatureFlags
    public boolean devicePresence() {
        return getValue(android.companion.Flags.FLAG_DEVICE_PRESENCE);
    }

    @Override // android.companion.FeatureFlags
    public boolean newAssociationBuilder() {
        return getValue(android.companion.Flags.FLAG_NEW_ASSOCIATION_BUILDER);
    }

    @Override // android.companion.FeatureFlags
    public boolean permSyncUserConsent() {
        return getValue(android.companion.Flags.FLAG_PERM_SYNC_USER_CONSENT);
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
