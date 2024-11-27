package com.android.text.flags;

/* loaded from: classes5.dex */
public class FakeFeatureFlagsImpl implements com.android.text.flags.FeatureFlags {
    private java.util.Map<java.lang.String, java.lang.Boolean> mFlagMap = new java.util.HashMap(java.util.Map.ofEntries(java.util.Map.entry(com.android.text.flags.Flags.FLAG_DEPRECATE_UI_FONTS, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_ESCAPE_CLEARS_FOCUS, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_FIX_DOUBLE_UNDERLINE, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_ICU_BIDI_MIGRATION, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_LAZY_VARIATION_INSTANCE, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_LETTER_SPACING_JUSTIFICATION, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_NEW_FONTS_FALLBACK_XML, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_PHRASE_STRICT_FALLBACK, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_USE_BOUNDS_FOR_WIDTH, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_VENDOR_CUSTOM_LOCALE_FALLBACK, false), java.util.Map.entry(com.android.text.flags.Flags.FLAG_WORD_STYLE_AUTO, false)));
    private java.util.Set<java.lang.String> mReadOnlyFlagsSet = new java.util.HashSet(java.util.Arrays.asList(com.android.text.flags.Flags.FLAG_DEPRECATE_UI_FONTS, com.android.text.flags.Flags.FLAG_ESCAPE_CLEARS_FOCUS, com.android.text.flags.Flags.FLAG_FIX_DOUBLE_UNDERLINE, com.android.text.flags.Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, com.android.text.flags.Flags.FLAG_ICU_BIDI_MIGRATION, com.android.text.flags.Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE, com.android.text.flags.Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION, com.android.text.flags.Flags.FLAG_LAZY_VARIATION_INSTANCE, com.android.text.flags.Flags.FLAG_LETTER_SPACING_JUSTIFICATION, com.android.text.flags.Flags.FLAG_NEW_FONTS_FALLBACK_XML, com.android.text.flags.Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, com.android.text.flags.Flags.FLAG_PHRASE_STRICT_FALLBACK, com.android.text.flags.Flags.FLAG_USE_BOUNDS_FOR_WIDTH, com.android.text.flags.Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING, com.android.text.flags.Flags.FLAG_VENDOR_CUSTOM_LOCALE_FALLBACK, com.android.text.flags.Flags.FLAG_WORD_STYLE_AUTO, ""));

    public FakeFeatureFlagsImpl() {
        resetAll();
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean deprecateUiFonts() {
        return getValue(com.android.text.flags.Flags.FLAG_DEPRECATE_UI_FONTS);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean escapeClearsFocus() {
        return getValue(com.android.text.flags.Flags.FLAG_ESCAPE_CLEARS_FOCUS);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixDoubleUnderline() {
        return getValue(com.android.text.flags.Flags.FLAG_FIX_DOUBLE_UNDERLINE);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixLineHeightForLocale() {
        return getValue(com.android.text.flags.Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean icuBidiMigration() {
        return getValue(com.android.text.flags.Flags.FLAG_ICU_BIDI_MIGRATION);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeCrashWhenDelete() {
        return getValue(com.android.text.flags.Flags.FLAG_INSERT_MODE_CRASH_WHEN_DELETE);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeNotUpdateSelection() {
        return getValue(com.android.text.flags.Flags.FLAG_INSERT_MODE_NOT_UPDATE_SELECTION);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean lazyVariationInstance() {
        return getValue(com.android.text.flags.Flags.FLAG_LAZY_VARIATION_INSTANCE);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean letterSpacingJustification() {
        return getValue(com.android.text.flags.Flags.FLAG_LETTER_SPACING_JUSTIFICATION);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean newFontsFallbackXml() {
        return getValue(com.android.text.flags.Flags.FLAG_NEW_FONTS_FALLBACK_XML);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean noBreakNoHyphenationSpan() {
        return getValue(com.android.text.flags.Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean phraseStrictFallback() {
        return getValue(com.android.text.flags.Flags.FLAG_PHRASE_STRICT_FALLBACK);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useBoundsForWidth() {
        return getValue(com.android.text.flags.Flags.FLAG_USE_BOUNDS_FOR_WIDTH);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useOptimizedBoottimeFontLoading() {
        return getValue(com.android.text.flags.Flags.FLAG_USE_OPTIMIZED_BOOTTIME_FONT_LOADING);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean vendorCustomLocaleFallback() {
        return getValue(com.android.text.flags.Flags.FLAG_VENDOR_CUSTOM_LOCALE_FALLBACK);
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean wordStyleAuto() {
        return getValue(com.android.text.flags.Flags.FLAG_WORD_STYLE_AUTO);
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
