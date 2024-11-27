package com.android.server.textservices;

/* loaded from: classes2.dex */
final class LocaleUtils {
    LocaleUtils() {
    }

    public static java.util.ArrayList<java.util.Locale> getSuitableLocalesForSpellChecker(@android.annotation.Nullable java.util.Locale locale) {
        java.util.Locale locale2;
        java.util.Locale locale3;
        java.util.Locale locale4;
        java.util.Locale locale5;
        java.util.Locale locale6 = null;
        if (locale != null) {
            java.lang.String language = locale.getLanguage();
            boolean z = !android.text.TextUtils.isEmpty(language);
            java.lang.String country = locale.getCountry();
            boolean z2 = !android.text.TextUtils.isEmpty(country);
            java.lang.String variant = locale.getVariant();
            boolean z3 = !android.text.TextUtils.isEmpty(variant);
            if (z && z2 && z3) {
                locale4 = new java.util.Locale(language, country, variant);
            } else {
                locale4 = null;
            }
            if (z && z2) {
                locale5 = new java.util.Locale(language, country);
            } else {
                locale5 = null;
            }
            if (z) {
                locale6 = new java.util.Locale(language);
            }
            locale3 = locale5;
            locale2 = locale6;
            locale6 = locale4;
        } else {
            locale2 = null;
            locale3 = null;
        }
        java.util.ArrayList<java.util.Locale> arrayList = new java.util.ArrayList<>();
        if (locale6 != null) {
            arrayList.add(locale6);
        }
        if (java.util.Locale.ENGLISH.equals(locale2)) {
            if (locale3 != null) {
                arrayList.add(locale3);
                if (!java.util.Locale.US.equals(locale3)) {
                    arrayList.add(java.util.Locale.US);
                }
                if (!java.util.Locale.UK.equals(locale3)) {
                    arrayList.add(java.util.Locale.UK);
                }
                arrayList.add(java.util.Locale.ENGLISH);
            } else {
                arrayList.add(java.util.Locale.ENGLISH);
                arrayList.add(java.util.Locale.US);
                arrayList.add(java.util.Locale.UK);
            }
        } else {
            if (locale3 != null) {
                arrayList.add(locale3);
            }
            if (locale2 != null) {
                arrayList.add(locale2);
            }
            arrayList.add(java.util.Locale.US);
            arrayList.add(java.util.Locale.UK);
            arrayList.add(java.util.Locale.ENGLISH);
        }
        return arrayList;
    }
}
