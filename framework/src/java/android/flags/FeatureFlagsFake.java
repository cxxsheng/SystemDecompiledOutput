package android.flags;

/* loaded from: classes.dex */
public class FeatureFlagsFake extends android.flags.FeatureFlags {
    private final java.util.Map<android.flags.BooleanFlagBase, java.lang.Boolean> mFlagValues;
    private final java.util.Set<android.flags.BooleanFlagBase> mReadFlags;

    public FeatureFlagsFake(android.flags.IFeatureFlags iFeatureFlags) {
        super(iFeatureFlags);
        this.mFlagValues = new java.util.HashMap();
        this.mReadFlags = new java.util.HashSet();
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(android.flags.BooleanFlag booleanFlag) {
        return requireFlag(booleanFlag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(android.flags.FusedOffFlag fusedOffFlag) {
        return requireFlag(fusedOffFlag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isEnabled(android.flags.FusedOnFlag fusedOnFlag) {
        return requireFlag(fusedOnFlag);
    }

    @Override // android.flags.FeatureFlags
    public boolean isCurrentlyEnabled(android.flags.DynamicBooleanFlag dynamicBooleanFlag) {
        return requireFlag(dynamicBooleanFlag);
    }

    @Override // android.flags.FeatureFlags
    protected void syncInternal(java.util.Set<android.flags.Flag<?>> set) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setFlagValue(android.flags.BooleanFlagBase booleanFlagBase, boolean z) {
        boolean z2 = booleanFlagBase instanceof android.flags.DynamicBooleanFlag;
        if (!z2 && this.mReadFlags.contains(booleanFlagBase)) {
            throw new java.lang.RuntimeException("You can not set the value of a flag after it has been read. Tried to set " + booleanFlagBase + " to " + z + " but it already " + this.mFlagValues.get(booleanFlagBase));
        }
        this.mFlagValues.put(booleanFlagBase, java.lang.Boolean.valueOf(z));
        if (z2) {
            onFlagChange((android.flags.DynamicFlag) booleanFlagBase);
        }
    }

    private boolean requireFlag(android.flags.BooleanFlagBase booleanFlagBase) {
        if (!this.mFlagValues.containsKey(booleanFlagBase)) {
            throw new java.lang.IllegalStateException("Tried to access " + booleanFlagBase + " in test but no overrided specified. You must call #setFlagValue for each flag read in a test.");
        }
        this.mReadFlags.add(booleanFlagBase);
        return this.mFlagValues.get(booleanFlagBase).booleanValue();
    }
}
