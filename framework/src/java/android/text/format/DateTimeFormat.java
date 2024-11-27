package android.text.format;

/* loaded from: classes3.dex */
class DateTimeFormat {
    private static final android.text.format.DateTimeFormat.FormatterCache CACHED_FORMATTERS = new android.text.format.DateTimeFormat.FormatterCache();

    static class FormatterCache extends android.util.LruCache<java.lang.String, android.icu.text.DateFormat> {
        FormatterCache() {
            super(8);
        }
    }

    private DateTimeFormat() {
    }

    public static java.lang.String format(android.icu.util.ULocale uLocale, android.icu.util.Calendar calendar, int i, android.icu.text.DisplayContext displayContext) {
        java.lang.String format;
        java.lang.String skeleton = android.text.format.DateUtilsBridge.toSkeleton(calendar, i);
        java.lang.String str = skeleton + "\t" + uLocale + "\t" + calendar.getTimeZone();
        synchronized (CACHED_FORMATTERS) {
            android.icu.text.DateFormat dateFormat = CACHED_FORMATTERS.get(str);
            if (dateFormat == null) {
                android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat(android.icu.text.DateTimePatternGenerator.getInstance(uLocale).getBestPattern(skeleton), uLocale);
                CACHED_FORMATTERS.put(str, simpleDateFormat);
                dateFormat = simpleDateFormat;
            }
            dateFormat.setContext(displayContext);
            format = dateFormat.format(calendar);
        }
        return format;
    }
}
