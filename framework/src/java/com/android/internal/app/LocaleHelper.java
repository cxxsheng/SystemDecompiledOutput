package com.android.internal.app;

/* loaded from: classes4.dex */
public class LocaleHelper {
    public static java.lang.String toSentenceCase(java.lang.String str, java.util.Locale locale) {
        return android.icu.text.CaseMap.toTitle().wholeString().noLowercase().apply(locale, null, str);
    }

    public static java.lang.String normalizeForSearch(java.lang.String str, java.util.Locale locale) {
        return str.toUpperCase();
    }

    private static boolean shouldUseDialectName(java.util.Locale locale) {
        java.lang.String language = locale.getLanguage();
        return "fa".equals(language) || "ro".equals(language) || "zh".equals(language);
    }

    public static java.lang.String getDisplayName(java.util.Locale locale, java.util.Locale locale2, boolean z) {
        java.lang.String displayName;
        android.icu.util.ULocale forLocale = android.icu.util.ULocale.forLocale(locale2);
        if (shouldUseDialectName(locale)) {
            displayName = android.icu.util.ULocale.getDisplayNameWithDialect(locale.toLanguageTag(), forLocale);
        } else {
            displayName = android.icu.util.ULocale.getDisplayName(locale.toLanguageTag(), forLocale);
        }
        return z ? toSentenceCase(displayName, locale2) : displayName;
    }

    public static java.lang.String getDisplayName(java.util.Locale locale, boolean z) {
        return getDisplayName(locale, java.util.Locale.getDefault(), z);
    }

    public static java.lang.String getDisplayCountry(java.util.Locale locale, java.util.Locale locale2) {
        java.lang.String languageTag = locale.toLanguageTag();
        android.icu.util.ULocale forLocale = android.icu.util.ULocale.forLocale(locale2);
        java.lang.String displayCountry = android.icu.util.ULocale.getDisplayCountry(languageTag, forLocale);
        if (locale.getUnicodeLocaleType("nu") != null) {
            return java.lang.String.format("%s (%s)", displayCountry, android.icu.util.ULocale.getDisplayKeywordValue(languageTag, "numbers", forLocale));
        }
        return displayCountry;
    }

    public static java.lang.String getDisplayCountry(java.util.Locale locale) {
        return android.icu.util.ULocale.getDisplayCountry(locale.toLanguageTag(), android.icu.util.ULocale.getDefault());
    }

    public static java.lang.String getDisplayLocaleList(android.os.LocaleList localeList, java.util.Locale locale, int i) {
        int size;
        int i2;
        if (locale == null) {
            locale = java.util.Locale.getDefault();
        }
        boolean z = localeList.size() > i;
        if (z) {
            size = i + 1;
            i2 = i;
        } else {
            size = localeList.size();
            i2 = size;
        }
        java.lang.String[] strArr = new java.lang.String[size];
        for (int i3 = 0; i3 < i2; i3++) {
            strArr[i3] = getDisplayName(localeList.get(i3), locale, false);
        }
        if (z) {
            strArr[i] = android.text.TextUtils.getEllipsisString(android.text.TextUtils.TruncateAt.END);
        }
        return android.icu.text.ListFormatter.getInstance(locale).format(strArr);
    }

    public static java.lang.String getDisplayNumberingSystemKeyValue(java.util.Locale locale, java.util.Locale locale2) {
        return new android.icu.util.ULocale.Builder().setUnicodeLocaleKeyword("nu", android.icu.text.NumberingSystem.getInstance(locale).getName()).build().getDisplayKeywordValue("numbers", android.icu.util.ULocale.forLocale(locale2));
    }

    public static java.util.Locale addLikelySubtags(java.util.Locale locale) {
        return android.icu.util.ULocale.addLikelySubtags(android.icu.util.ULocale.forLocale(locale)).toLocale();
    }

    public static final class LocaleInfoComparator implements java.util.Comparator<com.android.internal.app.LocaleStore.LocaleInfo> {
        private static final java.lang.String PREFIX_ARABIC = "ال";
        private final java.text.Collator mCollator;
        private final boolean mCountryMode;

        public LocaleInfoComparator(java.util.Locale locale, boolean z) {
            this.mCollator = java.text.Collator.getInstance(locale);
            this.mCountryMode = z;
        }

        private java.lang.String removePrefixForCompare(java.util.Locale locale, java.lang.String str) {
            if ("ar".equals(locale.getLanguage()) && str.startsWith(PREFIX_ARABIC)) {
                return str.substring(PREFIX_ARABIC.length());
            }
            return str;
        }

        @Override // java.util.Comparator
        public int compare(com.android.internal.app.LocaleStore.LocaleInfo localeInfo, com.android.internal.app.LocaleStore.LocaleInfo localeInfo2) {
            if (localeInfo.isAppCurrentLocale() || localeInfo2.isAppCurrentLocale()) {
                return localeInfo.isAppCurrentLocale() ? -1 : 1;
            }
            if (localeInfo.isSystemLocale() || localeInfo2.isSystemLocale()) {
                return localeInfo.isSystemLocale() ? -1 : 1;
            }
            if (localeInfo.isSuggested() == localeInfo2.isSuggested()) {
                return this.mCollator.compare(removePrefixForCompare(localeInfo.getLocale(), localeInfo.getLabel(this.mCountryMode)), removePrefixForCompare(localeInfo2.getLocale(), localeInfo2.getLabel(this.mCountryMode)));
            }
            return localeInfo.isSuggested() ? -1 : 1;
        }
    }
}
