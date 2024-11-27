package android.view.translation;

/* loaded from: classes4.dex */
public interface UiTranslationStateCallback {
    void onFinished();

    void onPaused();

    @java.lang.Deprecated
    default void onStarted(java.lang.String str, java.lang.String str2) {
    }

    default void onStarted(android.icu.util.ULocale uLocale, android.icu.util.ULocale uLocale2) {
        onStarted(uLocale.getLanguage(), uLocale2.getLanguage());
    }

    default void onStarted(android.icu.util.ULocale uLocale, android.icu.util.ULocale uLocale2, java.lang.String str) {
        onStarted(uLocale, uLocale2);
    }

    default void onPaused(java.lang.String str) {
        onPaused();
    }

    default void onResumed(android.icu.util.ULocale uLocale, android.icu.util.ULocale uLocale2) {
    }

    default void onResumed(android.icu.util.ULocale uLocale, android.icu.util.ULocale uLocale2, java.lang.String str) {
        onResumed(uLocale, uLocale2);
    }

    default void onFinished(java.lang.String str) {
        onFinished();
    }
}
