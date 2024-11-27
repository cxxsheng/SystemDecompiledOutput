package android.service.chooser;

/* loaded from: classes3.dex */
public class FakeFeatureFlagsImpl implements android.service.chooser.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(android.service.chooser.Flags.FLAG_CHOOSER_ALBUM_TEXT, false), java.util.Map.entry(android.service.chooser.Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, false), java.util.Map.entry(android.service.chooser.Flags.FLAG_ENABLE_CHOOSER_RESULT, false), java.util.Map.entry(android.service.chooser.Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, false), java.util.Map.entry(android.service.chooser.Flags.FLAG_LEGACY_CHOOSER_PINNING_REMOVAL, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(android.service.chooser.Flags.FLAG_CHOOSER_ALBUM_TEXT, android.service.chooser.Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING, android.service.chooser.Flags.FLAG_ENABLE_CHOOSER_RESULT, android.service.chooser.Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA, android.service.chooser.Flags.FLAG_LEGACY_CHOOSER_PINNING_REMOVAL, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean chooserAlbumText() {
        return getValue(android.service.chooser.Flags.FLAG_CHOOSER_ALBUM_TEXT);
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean chooserPayloadToggling() {
        return getValue(android.service.chooser.Flags.FLAG_CHOOSER_PAYLOAD_TOGGLING);
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableChooserResult() {
        return getValue(android.service.chooser.Flags.FLAG_ENABLE_CHOOSER_RESULT);
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean enableSharesheetMetadataExtra() {
        return getValue(android.service.chooser.Flags.FLAG_ENABLE_SHARESHEET_METADATA_EXTRA);
    }

    @Override // android.service.chooser.FeatureFlags
    public boolean legacyChooserPinningRemoval() {
        return getValue(android.service.chooser.Flags.FLAG_LEGACY_CHOOSER_PINNING_REMOVAL);
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
