package android.text;

/* loaded from: classes3.dex */
public class ClientFlags {
    public static boolean noBreakNoHyphenationSpan() {
        return android.text.TextFlags.isFeatureEnabled(com.android.text.flags.Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN);
    }

    public static boolean phraseStrictFallback() {
        return android.text.TextFlags.isFeatureEnabled(com.android.text.flags.Flags.FLAG_PHRASE_STRICT_FALLBACK);
    }

    public static boolean useBoundsForWidth() {
        return android.text.TextFlags.isFeatureEnabled(com.android.text.flags.Flags.FLAG_USE_BOUNDS_FOR_WIDTH);
    }

    public static boolean fixLineHeightForLocale() {
        return android.text.TextFlags.isFeatureEnabled(com.android.text.flags.Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE);
    }

    public static boolean icuBidiMigration() {
        return android.text.TextFlags.isFeatureEnabled(com.android.text.flags.Flags.FLAG_ICU_BIDI_MIGRATION);
    }
}
