package android.content.res;

/* loaded from: classes.dex */
public class FakeFeatureFlagsImpl implements android.content.res.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.content.res.Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO, false), java.util.Map.entry(android.content.res.Flags.FLAG_DEFAULT_LOCALE, false), java.util.Map.entry(android.content.res.Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC, false), java.util.Map.entry(android.content.res.Flags.FLAG_MANIFEST_FLAGGING, false), java.util.Map.entry(android.content.res.Flags.FLAG_NINE_PATCH_FRRO, false), java.util.Map.entry(android.content.res.Flags.FLAG_REGISTER_RESOURCE_PATHS, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.content.res.Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO, android.content.res.Flags.FLAG_DEFAULT_LOCALE, android.content.res.Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC, android.content.res.Flags.FLAG_MANIFEST_FLAGGING, android.content.res.Flags.FLAG_NINE_PATCH_FRRO, android.content.res.Flags.FLAG_REGISTER_RESOURCE_PATHS, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.content.res.FeatureFlags
    public boolean assetFileDescriptorFrro() {
        return getValue(android.content.res.Flags.FLAG_ASSET_FILE_DESCRIPTOR_FRRO);
    }

    @Override // android.content.res.FeatureFlags
    public boolean defaultLocale() {
        return getValue(android.content.res.Flags.FLAG_DEFAULT_LOCALE);
    }

    @Override // android.content.res.FeatureFlags
    public boolean fontScaleConverterPublic() {
        return getValue(android.content.res.Flags.FLAG_FONT_SCALE_CONVERTER_PUBLIC);
    }

    @Override // android.content.res.FeatureFlags
    public boolean manifestFlagging() {
        return getValue(android.content.res.Flags.FLAG_MANIFEST_FLAGGING);
    }

    @Override // android.content.res.FeatureFlags
    public boolean ninePatchFrro() {
        return getValue(android.content.res.Flags.FLAG_NINE_PATCH_FRRO);
    }

    @Override // android.content.res.FeatureFlags
    public boolean registerResourcePaths() {
        return getValue(android.content.res.Flags.FLAG_REGISTER_RESOURCE_PATHS);
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
