package com.android.text.flags;

/* loaded from: classes5.dex */
public final class FeatureFlagsImpl implements com.android.text.flags.FeatureFlags {
    @Override // com.android.text.flags.FeatureFlags
    public boolean deprecateUiFonts() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean escapeClearsFocus() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixDoubleUnderline() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean fixLineHeightForLocale() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean icuBidiMigration() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeCrashWhenDelete() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean insertModeNotUpdateSelection() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean lazyVariationInstance() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean letterSpacingJustification() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean newFontsFallbackXml() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean noBreakNoHyphenationSpan() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean phraseStrictFallback() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useBoundsForWidth() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean useOptimizedBoottimeFontLoading() {
        return true;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean vendorCustomLocaleFallback() {
        return false;
    }

    @Override // com.android.text.flags.FeatureFlags
    public boolean wordStyleAuto() {
        return false;
    }
}
