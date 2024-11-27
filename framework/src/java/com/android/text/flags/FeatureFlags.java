package com.android.text.flags;

/* loaded from: classes5.dex */
public interface FeatureFlags {
    boolean deprecateUiFonts();

    boolean escapeClearsFocus();

    boolean fixDoubleUnderline();

    boolean fixLineHeightForLocale();

    boolean icuBidiMigration();

    boolean insertModeCrashWhenDelete();

    boolean insertModeNotUpdateSelection();

    boolean lazyVariationInstance();

    boolean letterSpacingJustification();

    boolean newFontsFallbackXml();

    boolean noBreakNoHyphenationSpan();

    boolean phraseStrictFallback();

    boolean useBoundsForWidth();

    boolean useOptimizedBoottimeFontLoading();

    boolean vendorCustomLocaleFallback();

    boolean wordStyleAuto();
}
