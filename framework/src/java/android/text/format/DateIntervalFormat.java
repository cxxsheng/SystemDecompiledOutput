package android.text.format;

/* loaded from: classes3.dex */
public final class DateIntervalFormat {
    private static final android.util.LruCache<java.lang.String, android.icu.text.DateIntervalFormat> CACHED_FORMATTERS = new android.util.LruCache<>(8);

    private DateIntervalFormat() {
    }

    public static java.lang.String formatDateRange(long j, long j2, int i, java.lang.String str) {
        if ((i & 8192) != 0) {
            str = android.text.format.Time.TIMEZONE_UTC;
        }
        return formatDateRange(android.icu.util.ULocale.getDefault(), android.text.format.DateUtilsBridge.icuTimeZone(str != null ? java.util.TimeZone.getTimeZone(str) : java.util.TimeZone.getDefault()), j, j2, i);
    }

    public static java.lang.String formatDateRange(android.icu.util.ULocale uLocale, android.icu.util.TimeZone timeZone, long j, long j2, int i) {
        android.icu.util.Calendar createIcuCalendar;
        java.lang.String stringBuffer;
        android.icu.util.Calendar createIcuCalendar2 = android.text.format.DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j);
        if (j == j2) {
            createIcuCalendar = createIcuCalendar2;
        } else {
            createIcuCalendar = android.text.format.DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j2);
        }
        if (isExactlyMidnight(createIcuCalendar)) {
            boolean z = (i & 1) == 1;
            boolean z2 = android.text.format.DateUtilsBridge.dayDistance(createIcuCalendar2, createIcuCalendar) == 1;
            if ((!z && j != j2) || (z2 && !android.text.format.DateUtilsBridge.isDisplayMidnightUsingSkeleton(createIcuCalendar2))) {
                createIcuCalendar.add(5, -1);
            }
        }
        java.lang.String skeleton = android.text.format.DateUtilsBridge.toSkeleton(createIcuCalendar2, createIcuCalendar, i);
        synchronized (CACHED_FORMATTERS) {
            stringBuffer = getFormatter(skeleton, uLocale, timeZone).format(createIcuCalendar2, createIcuCalendar, new java.lang.StringBuffer(), new java.text.FieldPosition(0)).toString();
        }
        return stringBuffer;
    }

    private static android.icu.text.DateIntervalFormat getFormatter(java.lang.String str, android.icu.util.ULocale uLocale, android.icu.util.TimeZone timeZone) {
        java.lang.String str2 = str + "\t" + uLocale + "\t" + timeZone;
        android.icu.text.DateIntervalFormat dateIntervalFormat = CACHED_FORMATTERS.get(str2);
        if (dateIntervalFormat != null) {
            return dateIntervalFormat;
        }
        android.icu.text.DateIntervalFormat dateIntervalFormat2 = android.icu.text.DateIntervalFormat.getInstance(str, uLocale);
        dateIntervalFormat2.setTimeZone(timeZone);
        CACHED_FORMATTERS.put(str2, dateIntervalFormat2);
        return dateIntervalFormat2;
    }

    private static boolean isExactlyMidnight(android.icu.util.Calendar calendar) {
        return calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0 && calendar.get(14) == 0;
    }
}
