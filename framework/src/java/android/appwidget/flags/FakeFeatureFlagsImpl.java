package android.appwidget.flags;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.appwidget.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.appwidget.flags.Flags.FLAG_DRAW_DATA_PARCEL, false), java.util.Map.entry(android.appwidget.flags.Flags.FLAG_GENERATED_PREVIEWS, false), java.util.Map.entry(android.appwidget.flags.Flags.FLAG_REMOTE_ADAPTER_CONVERSION, false), java.util.Map.entry(android.appwidget.flags.Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.appwidget.flags.Flags.FLAG_DRAW_DATA_PARCEL, android.appwidget.flags.Flags.FLAG_GENERATED_PREVIEWS, android.appwidget.flags.Flags.FLAG_REMOTE_ADAPTER_CONVERSION, android.appwidget.flags.Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean drawDataParcel() {
        return getValue(android.appwidget.flags.Flags.FLAG_DRAW_DATA_PARCEL);
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean generatedPreviews() {
        return getValue(android.appwidget.flags.Flags.FLAG_GENERATED_PREVIEWS);
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean remoteAdapterConversion() {
        return getValue(android.appwidget.flags.Flags.FLAG_REMOTE_ADAPTER_CONVERSION);
    }

    @Override // android.appwidget.flags.FeatureFlags
    public boolean removeAppWidgetServiceIoFromCriticalPath() {
        return getValue(android.appwidget.flags.Flags.FLAG_REMOVE_APP_WIDGET_SERVICE_IO_FROM_CRITICAL_PATH);
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
