package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class SubtypeUtils {
    public static final boolean DEBUG = false;
    static final int NOT_A_SUBTYPE_ID = -1;
    static final java.lang.String SUBTYPE_MODE_KEYBOARD = "keyboard";
    private static final java.lang.String TAG = "SubtypeUtils";
    private static final java.lang.String TAG_ENABLED_WHEN_DEFAULT_IS_NOT_ASCII_CAPABLE = "EnabledWhenDefaultIsNotAsciiCapable";

    @com.android.internal.annotations.GuardedBy({"sCacheLock"})
    private static android.view.inputmethod.InputMethodInfo sCachedInputMethodInfo;

    @com.android.internal.annotations.GuardedBy({"sCacheLock"})
    private static java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> sCachedResult;

    @com.android.internal.annotations.GuardedBy({"sCacheLock"})
    private static android.os.LocaleList sCachedSystemLocales;
    static final java.lang.String SUBTYPE_MODE_ANY = null;
    private static final java.lang.Object sCacheLock = new java.lang.Object();
    private static final com.android.server.inputmethod.LocaleUtils.LocaleExtractor<android.view.inputmethod.InputMethodSubtype> sSubtypeToLocale = new com.android.server.inputmethod.LocaleUtils.LocaleExtractor() { // from class: com.android.server.inputmethod.SubtypeUtils$$ExternalSyntheticLambda0
        @Override // com.android.server.inputmethod.LocaleUtils.LocaleExtractor
        public final java.util.Locale get(java.lang.Object obj) {
            java.util.Locale lambda$static$0;
            lambda$static$0 = com.android.server.inputmethod.SubtypeUtils.lambda$static$0((android.view.inputmethod.InputMethodSubtype) obj);
            return lambda$static$0;
        }
    };

    SubtypeUtils() {
    }

    static boolean containsSubtypeOf(android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable java.util.Locale locale, boolean z, java.lang.String str) {
        if (locale == null) {
            return false;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            android.view.inputmethod.InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
            if (z) {
                java.util.Locale localeObject = subtypeAt.getLocaleObject();
                if (localeObject == null) {
                    continue;
                } else if (android.text.TextUtils.equals(localeObject.getLanguage(), locale.getLanguage())) {
                    if (!android.text.TextUtils.equals(localeObject.getCountry(), locale.getCountry())) {
                        continue;
                    }
                    if (!android.text.TextUtils.isEmpty(str) || str.equalsIgnoreCase(subtypeAt.getMode())) {
                    }
                } else {
                    continue;
                }
            } else {
                if (!android.text.TextUtils.equals(new java.util.Locale(com.android.server.inputmethod.LocaleUtils.getLanguageFromLocaleString(subtypeAt.getLocale())).getLanguage(), locale.getLanguage())) {
                    continue;
                }
                return !android.text.TextUtils.isEmpty(str) ? true : true;
            }
        }
        return false;
    }

    static java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> getSubtypes(android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> arrayList = new java.util.ArrayList<>();
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            arrayList.add(inputMethodInfo.getSubtypeAt(i));
        }
        return arrayList;
    }

    static boolean isValidSubtypeId(android.view.inputmethod.InputMethodInfo inputMethodInfo, int i) {
        return getSubtypeIdFromHashCode(inputMethodInfo, i) != -1;
    }

    static int getSubtypeIdFromHashCode(android.view.inputmethod.InputMethodInfo inputMethodInfo, int i) {
        if (inputMethodInfo != null) {
            int subtypeCount = inputMethodInfo.getSubtypeCount();
            for (int i2 = 0; i2 < subtypeCount; i2++) {
                if (i == inputMethodInfo.getSubtypeAt(i2).hashCode()) {
                    return i2;
                }
            }
            return -1;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.Locale lambda$static$0(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (inputMethodSubtype != null) {
            return inputMethodSubtype.getLocaleObject();
        }
        return null;
    }

    @android.annotation.NonNull
    static java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> getImplicitlyApplicableSubtypes(@android.annotation.NonNull android.os.LocaleList localeList, android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        synchronized (sCacheLock) {
            try {
                if (localeList.equals(sCachedSystemLocales) && sCachedInputMethodInfo == inputMethodInfo) {
                    return new java.util.ArrayList<>(sCachedResult);
                }
                java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> implicitlyApplicableSubtypesImpl = getImplicitlyApplicableSubtypesImpl(localeList, inputMethodInfo);
                synchronized (sCacheLock) {
                    sCachedSystemLocales = localeList;
                    sCachedInputMethodInfo = inputMethodInfo;
                    sCachedResult = new java.util.ArrayList<>(implicitlyApplicableSubtypesImpl);
                }
                return implicitlyApplicableSubtypesImpl;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> getImplicitlyApplicableSubtypesImpl(@android.annotation.NonNull android.os.LocaleList localeList, android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        android.view.inputmethod.InputMethodSubtype findLastResortApplicableSubtype;
        boolean z;
        java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> subtypes = getSubtypes(inputMethodInfo);
        java.lang.String obj = localeList.get(0).toString();
        if (android.text.TextUtils.isEmpty(obj)) {
            return new java.util.ArrayList<>();
        }
        int size = subtypes.size();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (int i = 0; i < size; i++) {
            android.view.inputmethod.InputMethodSubtype inputMethodSubtype = subtypes.get(i);
            if (inputMethodSubtype.overridesImplicitlyEnabledSubtype()) {
                java.lang.String mode = inputMethodSubtype.getMode();
                if (!arrayMap.containsKey(mode)) {
                    arrayMap.put(mode, inputMethodSubtype);
                }
            }
        }
        if (arrayMap.size() > 0) {
            return new java.util.ArrayList<>(arrayMap.values());
        }
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < size; i2++) {
            android.view.inputmethod.InputMethodSubtype inputMethodSubtype2 = subtypes.get(i2);
            java.lang.String mode2 = inputMethodSubtype2.getMode();
            if (SUBTYPE_MODE_KEYBOARD.equals(mode2)) {
                arrayList.add(inputMethodSubtype2);
            } else {
                if (!arrayMap2.containsKey(mode2)) {
                    arrayMap2.put(mode2, new java.util.ArrayList());
                }
                ((java.util.ArrayList) arrayMap2.get(mode2)).add(inputMethodSubtype2);
            }
        }
        java.util.ArrayList<android.view.inputmethod.InputMethodSubtype> arrayList2 = new java.util.ArrayList<>();
        com.android.server.inputmethod.LocaleUtils.filterByLanguage(arrayList, sSubtypeToLocale, localeList, arrayList2);
        if (!arrayList2.isEmpty()) {
            int size2 = arrayList2.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size2) {
                    z = false;
                    break;
                }
                if (!arrayList2.get(i3).isAsciiCapable()) {
                    i3++;
                } else {
                    z = true;
                    break;
                }
            }
            if (!z) {
                int size3 = arrayList.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    android.view.inputmethod.InputMethodSubtype inputMethodSubtype3 = (android.view.inputmethod.InputMethodSubtype) arrayList.get(i4);
                    if (SUBTYPE_MODE_KEYBOARD.equals(inputMethodSubtype3.getMode()) && inputMethodSubtype3.containsExtraValueKey(TAG_ENABLED_WHEN_DEFAULT_IS_NOT_ASCII_CAPABLE)) {
                        arrayList2.add(inputMethodSubtype3);
                    }
                }
            }
        }
        if (arrayList2.isEmpty() && (findLastResortApplicableSubtype = findLastResortApplicableSubtype(subtypes, SUBTYPE_MODE_KEYBOARD, obj, true)) != null) {
            arrayList2.add(findLastResortApplicableSubtype);
        }
        java.util.Iterator it = arrayMap2.values().iterator();
        while (it.hasNext()) {
            com.android.server.inputmethod.LocaleUtils.filterByLanguage((java.util.ArrayList) it.next(), sSubtypeToLocale, localeList, arrayList2);
        }
        return arrayList2;
    }

    static android.view.inputmethod.InputMethodSubtype findLastResortApplicableSubtype(java.util.List<android.view.inputmethod.InputMethodSubtype> list, java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z) {
        android.view.inputmethod.InputMethodSubtype inputMethodSubtype = null;
        if (list == null || list.isEmpty()) {
            return null;
        }
        java.lang.String languageFromLocaleString = com.android.server.inputmethod.LocaleUtils.getLanguageFromLocaleString(str2);
        int size = list.size();
        int i = 0;
        boolean z2 = false;
        android.view.inputmethod.InputMethodSubtype inputMethodSubtype2 = null;
        while (true) {
            if (i >= size) {
                break;
            }
            android.view.inputmethod.InputMethodSubtype inputMethodSubtype3 = list.get(i);
            java.lang.String locale = inputMethodSubtype3.getLocale();
            java.lang.String languageFromLocaleString2 = com.android.server.inputmethod.LocaleUtils.getLanguageFromLocaleString(locale);
            if (str == null || list.get(i).getMode().equalsIgnoreCase(str)) {
                if (inputMethodSubtype == null) {
                    inputMethodSubtype = inputMethodSubtype3;
                }
                if (str2.equals(locale)) {
                    inputMethodSubtype2 = inputMethodSubtype3;
                    break;
                }
                if (!z2 && languageFromLocaleString.equals(languageFromLocaleString2)) {
                    z2 = true;
                    inputMethodSubtype2 = inputMethodSubtype3;
                }
            }
            i++;
        }
        if (inputMethodSubtype2 == null && z) {
            return inputMethodSubtype;
        }
        return inputMethodSubtype2;
    }
}
