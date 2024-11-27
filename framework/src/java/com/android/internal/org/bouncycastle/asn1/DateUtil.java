package com.android.internal.org.bouncycastle.asn1;

/* loaded from: classes4.dex */
class DateUtil {
    private static java.lang.Long ZERO = longValueOf(0);
    private static final java.util.Map localeCache = new java.util.HashMap();
    static java.util.Locale EN_Locale = forEN();

    DateUtil() {
    }

    private static java.util.Locale forEN() {
        if ("en".equalsIgnoreCase(java.util.Locale.getDefault().getLanguage())) {
            return java.util.Locale.getDefault();
        }
        java.util.Locale[] availableLocales = java.util.Locale.getAvailableLocales();
        for (int i = 0; i != availableLocales.length; i++) {
            if ("en".equalsIgnoreCase(availableLocales[i].getLanguage())) {
                return availableLocales[i];
            }
        }
        return java.util.Locale.getDefault();
    }

    static java.util.Date epochAdjust(java.util.Date date) throws java.text.ParseException {
        java.util.Locale locale = java.util.Locale.getDefault();
        if (locale == null) {
            return date;
        }
        synchronized (localeCache) {
            java.lang.Long l = (java.lang.Long) localeCache.get(locale);
            if (l == null) {
                long time = new java.text.SimpleDateFormat("yyyyMMddHHmmssz").parse("19700101000000GMT+00:00").getTime();
                if (time == 0) {
                    l = ZERO;
                } else {
                    l = longValueOf(time);
                }
                localeCache.put(locale, l);
            }
            if (l == ZERO) {
                return date;
            }
            return new java.util.Date(date.getTime() - l.longValue());
        }
    }

    private static java.lang.Long longValueOf(long j) {
        return java.lang.Long.valueOf(j);
    }
}
