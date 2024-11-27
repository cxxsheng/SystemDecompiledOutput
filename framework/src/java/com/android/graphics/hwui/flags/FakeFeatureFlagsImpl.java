package com.android.graphics.hwui.flags;

/* loaded from: classes4.dex */
public class FakeFeatureFlagsImpl implements com.android.graphics.hwui.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_ANIMATE_HDR_TRANSITIONS, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_CLIP_SHADER, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_CLIP_SURFACEVIEWS, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_GAINMAP_ANIMATIONS, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_HDR_10BIT_PLUS, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_HIGH_CONTRAST_TEXT_LUMINANCE, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_LIMITED_HDR, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_MATRIX_44, false), java.util.Map.entry(com.android.graphics.hwui.flags.Flags.FLAG_REQUESTED_FORMATS_V, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.graphics.hwui.flags.Flags.FLAG_ANIMATE_HDR_TRANSITIONS, com.android.graphics.hwui.flags.Flags.FLAG_CLIP_SHADER, com.android.graphics.hwui.flags.Flags.FLAG_CLIP_SURFACEVIEWS, com.android.graphics.hwui.flags.Flags.FLAG_GAINMAP_ANIMATIONS, com.android.graphics.hwui.flags.Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA, com.android.graphics.hwui.flags.Flags.FLAG_HDR_10BIT_PLUS, com.android.graphics.hwui.flags.Flags.FLAG_HIGH_CONTRAST_TEXT_LUMINANCE, com.android.graphics.hwui.flags.Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT, com.android.graphics.hwui.flags.Flags.FLAG_LIMITED_HDR, com.android.graphics.hwui.flags.Flags.FLAG_MATRIX_44, com.android.graphics.hwui.flags.Flags.FLAG_REQUESTED_FORMATS_V, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean animateHdrTransitions() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_ANIMATE_HDR_TRANSITIONS);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipShader() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_CLIP_SHADER);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean clipSurfaceviews() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_CLIP_SURFACEVIEWS);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapAnimations() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_GAINMAP_ANIMATIONS);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean gainmapConstructorWithMetadata() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_GAINMAP_CONSTRUCTOR_WITH_METADATA);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean hdr10bitPlus() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_HDR_10BIT_PLUS);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextLuminance() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_HIGH_CONTRAST_TEXT_LUMINANCE);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean highContrastTextSmallTextRect() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_HIGH_CONTRAST_TEXT_SMALL_TEXT_RECT);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean limitedHdr() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_LIMITED_HDR);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean matrix44() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_MATRIX_44);
    }

    @Override // com.android.graphics.hwui.flags.FeatureFlags
    public boolean requestedFormatsV() {
        return getValue(com.android.graphics.hwui.flags.Flags.FLAG_REQUESTED_FORMATS_V);
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
