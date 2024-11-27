package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class InputMethodInfoUtils {
    private static final java.lang.String TAG = "InputMethodInfoUtils";
    private static final java.util.Locale[] SEARCH_ORDER_OF_FALLBACK_LOCALES = {java.util.Locale.ENGLISH, java.util.Locale.US, java.util.Locale.UK};
    private static final java.util.Locale ENGLISH_LOCALE = new java.util.Locale("en");

    InputMethodInfoUtils() {
    }

    private static final class InputMethodListBuilder {

        @android.annotation.NonNull
        private final java.util.LinkedHashSet<android.view.inputmethod.InputMethodInfo> mInputMethodSet;

        private InputMethodListBuilder() {
            this.mInputMethodSet = new java.util.LinkedHashSet<>();
        }

        com.android.server.inputmethod.InputMethodInfoUtils.InputMethodListBuilder fillImes(java.util.List<android.view.inputmethod.InputMethodInfo> list, android.content.Context context, boolean z, @android.annotation.Nullable java.util.Locale locale, boolean z2, java.lang.String str) {
            for (int i = 0; i < list.size(); i++) {
                android.view.inputmethod.InputMethodInfo inputMethodInfo = list.get(i);
                if (com.android.server.inputmethod.InputMethodInfoUtils.isSystemImeThatHasSubtypeOf(inputMethodInfo, context, z, locale, z2, str)) {
                    this.mInputMethodSet.add(inputMethodInfo);
                }
            }
            return this;
        }

        com.android.server.inputmethod.InputMethodInfoUtils.InputMethodListBuilder fillAuxiliaryImes(java.util.List<android.view.inputmethod.InputMethodInfo> list, android.content.Context context) {
            java.util.Iterator<android.view.inputmethod.InputMethodInfo> it = this.mInputMethodSet.iterator();
            while (it.hasNext()) {
                if (it.next().isAuxiliaryIme()) {
                    return this;
                }
            }
            boolean z = false;
            for (int i = 0; i < list.size(); i++) {
                android.view.inputmethod.InputMethodInfo inputMethodInfo = list.get(i);
                if (com.android.server.inputmethod.InputMethodInfoUtils.isSystemAuxilialyImeThatHasAutomaticSubtype(inputMethodInfo, context, true)) {
                    this.mInputMethodSet.add(inputMethodInfo);
                    z = true;
                }
            }
            if (z) {
                return this;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                android.view.inputmethod.InputMethodInfo inputMethodInfo2 = list.get(i2);
                if (com.android.server.inputmethod.InputMethodInfoUtils.isSystemAuxilialyImeThatHasAutomaticSubtype(inputMethodInfo2, context, false)) {
                    this.mInputMethodSet.add(inputMethodInfo2);
                }
            }
            return this;
        }

        public boolean isEmpty() {
            return this.mInputMethodSet.isEmpty();
        }

        @android.annotation.NonNull
        public java.util.ArrayList<android.view.inputmethod.InputMethodInfo> build() {
            return new java.util.ArrayList<>(this.mInputMethodSet);
        }
    }

    private static com.android.server.inputmethod.InputMethodInfoUtils.InputMethodListBuilder getMinimumKeyboardSetWithSystemLocale(java.util.List<android.view.inputmethod.InputMethodInfo> list, android.content.Context context, @android.annotation.Nullable java.util.Locale locale, @android.annotation.Nullable java.util.Locale locale2) {
        com.android.server.inputmethod.InputMethodInfoUtils.InputMethodListBuilder inputMethodListBuilder = new com.android.server.inputmethod.InputMethodInfoUtils.InputMethodListBuilder();
        inputMethodListBuilder.fillImes(list, context, true, locale, true, "keyboard");
        if (!inputMethodListBuilder.isEmpty()) {
            return inputMethodListBuilder;
        }
        inputMethodListBuilder.fillImes(list, context, true, locale, false, "keyboard");
        if (!inputMethodListBuilder.isEmpty()) {
            return inputMethodListBuilder;
        }
        inputMethodListBuilder.fillImes(list, context, true, locale2, true, "keyboard");
        if (!inputMethodListBuilder.isEmpty()) {
            return inputMethodListBuilder;
        }
        inputMethodListBuilder.fillImes(list, context, true, locale2, false, "keyboard");
        if (!inputMethodListBuilder.isEmpty()) {
            return inputMethodListBuilder;
        }
        inputMethodListBuilder.fillImes(list, context, false, locale2, true, "keyboard");
        if (!inputMethodListBuilder.isEmpty()) {
            return inputMethodListBuilder;
        }
        inputMethodListBuilder.fillImes(list, context, false, locale2, false, "keyboard");
        if (!inputMethodListBuilder.isEmpty()) {
            return inputMethodListBuilder;
        }
        android.util.Slog.w(TAG, "No software keyboard is found. imis=" + java.util.Arrays.toString(list.toArray()) + " systemLocale=" + locale + " fallbackLocale=" + locale2);
        return inputMethodListBuilder;
    }

    static java.util.ArrayList<android.view.inputmethod.InputMethodInfo> getDefaultEnabledImes(android.content.Context context, java.util.List<android.view.inputmethod.InputMethodInfo> list, boolean z) {
        java.util.Locale fallbackLocaleForDefaultIme = getFallbackLocaleForDefaultIme(list, context);
        java.util.Locale systemLocaleFromContext = com.android.server.inputmethod.LocaleUtils.getSystemLocaleFromContext(context);
        com.android.server.inputmethod.InputMethodInfoUtils.InputMethodListBuilder minimumKeyboardSetWithSystemLocale = getMinimumKeyboardSetWithSystemLocale(list, context, systemLocaleFromContext, fallbackLocaleForDefaultIme);
        if (!z) {
            minimumKeyboardSetWithSystemLocale.fillImes(list, context, true, systemLocaleFromContext, true, com.android.server.inputmethod.SubtypeUtils.SUBTYPE_MODE_ANY).fillAuxiliaryImes(list, context);
        }
        return minimumKeyboardSetWithSystemLocale.build();
    }

    static java.util.ArrayList<android.view.inputmethod.InputMethodInfo> getDefaultEnabledImes(android.content.Context context, java.util.List<android.view.inputmethod.InputMethodInfo> list) {
        return getDefaultEnabledImes(context, list, false);
    }

    @android.annotation.Nullable
    static android.view.inputmethod.InputMethodInfo chooseSystemVoiceIme(@android.annotation.NonNull com.android.server.inputmethod.InputMethodMap inputMethodMap, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        android.view.inputmethod.InputMethodInfo inputMethodInfo = inputMethodMap.get(str2);
        if (inputMethodInfo != null && inputMethodInfo.isSystem() && inputMethodInfo.getPackageName().equals(str)) {
            return inputMethodInfo;
        }
        int size = inputMethodMap.size();
        android.view.inputmethod.InputMethodInfo inputMethodInfo2 = null;
        for (int i = 0; i < size; i++) {
            android.view.inputmethod.InputMethodInfo valueAt = inputMethodMap.valueAt(i);
            if (valueAt.isSystem() && android.text.TextUtils.equals(valueAt.getPackageName(), str)) {
                if (inputMethodInfo2 != null) {
                    android.util.Slog.e(TAG, "At most one InputMethodService can be published in systemSpeechRecognizer: " + str + ". Ignoring all of them.");
                    return null;
                }
                inputMethodInfo2 = valueAt;
            }
        }
        return inputMethodInfo2;
    }

    static android.view.inputmethod.InputMethodInfo getMostApplicableDefaultIME(java.util.List<android.view.inputmethod.InputMethodInfo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        int i = -1;
        while (size > 0) {
            size--;
            android.view.inputmethod.InputMethodInfo inputMethodInfo = list.get(size);
            if (!inputMethodInfo.isAuxiliaryIme()) {
                if (inputMethodInfo.isSystem() && com.android.server.inputmethod.SubtypeUtils.containsSubtypeOf(inputMethodInfo, ENGLISH_LOCALE, false, "keyboard")) {
                    return inputMethodInfo;
                }
                if (i < 0 && inputMethodInfo.isSystem()) {
                    i = size;
                }
            }
        }
        return list.get(java.lang.Math.max(i, 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSystemAuxilialyImeThatHasAutomaticSubtype(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.content.Context context, boolean z) {
        if (!inputMethodInfo.isSystem()) {
            return false;
        }
        if ((z && !inputMethodInfo.isDefault(context)) || !inputMethodInfo.isAuxiliaryIme()) {
            return false;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            if (inputMethodInfo.getSubtypeAt(i).overridesImplicitlyEnabledSubtype()) {
                return true;
            }
        }
        return false;
    }

    @android.annotation.Nullable
    private static java.util.Locale getFallbackLocaleForDefaultIme(java.util.List<android.view.inputmethod.InputMethodInfo> list, android.content.Context context) {
        for (java.util.Locale locale : SEARCH_ORDER_OF_FALLBACK_LOCALES) {
            for (int i = 0; i < list.size(); i++) {
                if (isSystemImeThatHasSubtypeOf(list.get(i), context, true, locale, true, "keyboard")) {
                    return locale;
                }
            }
        }
        for (java.util.Locale locale2 : SEARCH_ORDER_OF_FALLBACK_LOCALES) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (isSystemImeThatHasSubtypeOf(list.get(i2), context, false, locale2, true, "keyboard")) {
                    return locale2;
                }
            }
        }
        android.util.Slog.w(TAG, "Found no fallback locale. imis=" + java.util.Arrays.toString(list.toArray()));
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSystemImeThatHasSubtypeOf(android.view.inputmethod.InputMethodInfo inputMethodInfo, android.content.Context context, boolean z, @android.annotation.Nullable java.util.Locale locale, boolean z2, java.lang.String str) {
        if (!inputMethodInfo.isSystem()) {
            return false;
        }
        if (!z || inputMethodInfo.isDefault(context)) {
            return com.android.server.inputmethod.SubtypeUtils.containsSubtypeOf(inputMethodInfo, locale, z2, str);
        }
        return false;
    }
}
