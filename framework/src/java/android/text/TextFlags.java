package android.text;

/* loaded from: classes3.dex */
public final class TextFlags {
    public static final java.lang.String ENABLE_NEW_CONTEXT_MENU = "TextEditing__enable_new_context_menu";
    public static final boolean ENABLE_NEW_CONTEXT_MENU_DEFAULT = true;
    public static final java.lang.String KEY_ENABLE_NEW_CONTEXT_MENU = "text__enable_new_context_menu";
    public static final java.lang.String NAMESPACE = "text";
    public static final java.lang.String[] TEXT_ACONFIGS_FLAGS = {com.android.text.flags.Flags.FLAG_NO_BREAK_NO_HYPHENATION_SPAN, com.android.text.flags.Flags.FLAG_PHRASE_STRICT_FALLBACK, com.android.text.flags.Flags.FLAG_USE_BOUNDS_FOR_WIDTH, com.android.text.flags.Flags.FLAG_FIX_LINE_HEIGHT_FOR_LOCALE, com.android.text.flags.Flags.FLAG_ICU_BIDI_MIGRATION};
    public static final boolean[] TEXT_ACONFIG_DEFAULT_VALUE = {com.android.text.flags.Flags.noBreakNoHyphenationSpan(), com.android.text.flags.Flags.phraseStrictFallback(), com.android.text.flags.Flags.useBoundsForWidth(), com.android.text.flags.Flags.fixLineHeightForLocale(), com.android.text.flags.Flags.icuBidiMigration()};

    public static java.lang.String getKeyForFlag(java.lang.String str) {
        return "text__" + str;
    }

    public static boolean isFeatureEnabled(java.lang.String str) {
        return android.app.AppGlobals.getIntCoreSetting(getKeyForFlag(str), 0) != 0;
    }
}
