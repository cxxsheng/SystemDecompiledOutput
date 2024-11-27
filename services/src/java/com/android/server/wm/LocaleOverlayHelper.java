package com.android.server.wm;

/* loaded from: classes3.dex */
final class LocaleOverlayHelper {
    LocaleOverlayHelper() {
    }

    static android.os.LocaleList combineLocalesIfOverlayExists(android.os.LocaleList localeList, android.os.LocaleList localeList2) {
        if (localeList == null || localeList.isEmpty()) {
            return localeList;
        }
        return combineLocales(localeList, localeList2);
    }

    private static android.os.LocaleList combineLocales(android.os.LocaleList localeList, android.os.LocaleList localeList2) {
        java.util.Locale[] localeArr = new java.util.Locale[localeList.size() + localeList2.size()];
        for (int i = 0; i < localeList.size(); i++) {
            localeArr[i] = localeList.get(i);
        }
        for (int i2 = 0; i2 < localeList2.size(); i2++) {
            localeArr[localeList.size() + i2] = localeList2.get(i2);
        }
        return new android.os.LocaleList(localeArr);
    }
}
