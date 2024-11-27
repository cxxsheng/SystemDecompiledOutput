package android.text.format;

/* loaded from: classes3.dex */
public final class RelativeDateTimeFormatter {
    private static final android.text.format.RelativeDateTimeFormatter.FormatterCache CACHED_FORMATTERS = new android.text.format.RelativeDateTimeFormatter.FormatterCache();
    public static final long DAY_IN_MILLIS = 86400000;
    private static final int DAY_IN_MS = 86400000;
    private static final int EPOCH_JULIAN_DAY = 2440588;
    public static final long HOUR_IN_MILLIS = 3600000;
    public static final long MINUTE_IN_MILLIS = 60000;
    public static final long SECOND_IN_MILLIS = 1000;
    public static final long WEEK_IN_MILLIS = 604800000;
    public static final long YEAR_IN_MILLIS = 31449600000L;

    static class FormatterCache extends android.util.LruCache<java.lang.String, android.icu.text.RelativeDateTimeFormatter> {
        FormatterCache() {
            super(8);
        }
    }

    private RelativeDateTimeFormatter() {
    }

    public static java.lang.String getRelativeTimeSpanString(java.util.Locale locale, java.util.TimeZone timeZone, long j, long j2, long j3, int i) {
        return getRelativeTimeSpanString(locale, timeZone, j, j2, j3, i, android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
    }

    public static java.lang.String getRelativeTimeSpanString(java.util.Locale locale, java.util.TimeZone timeZone, long j, long j2, long j3, int i, android.icu.text.DisplayContext displayContext) {
        if (locale == null) {
            throw new java.lang.NullPointerException("locale == null");
        }
        if (timeZone == null) {
            throw new java.lang.NullPointerException("tz == null");
        }
        return getRelativeTimeSpanString(android.icu.util.ULocale.forLocale(locale), android.text.format.DateUtilsBridge.icuTimeZone(timeZone), j, j2, j3, i, displayContext);
    }

    private static java.lang.String getRelativeTimeSpanString(android.icu.util.ULocale uLocale, android.icu.util.TimeZone timeZone, long j, long j2, long j3, int i, android.icu.text.DisplayContext displayContext) {
        android.icu.text.RelativeDateTimeFormatter.Style style;
        android.icu.text.RelativeDateTimeFormatter.Direction direction;
        int i2;
        int i3;
        android.icu.text.RelativeDateTimeFormatter.RelativeUnit relativeUnit;
        android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit absoluteUnit;
        java.lang.String format;
        long abs = java.lang.Math.abs(j2 - j);
        boolean z = false;
        boolean z2 = j2 >= j;
        if ((i & 786432) != 0) {
            style = android.icu.text.RelativeDateTimeFormatter.Style.SHORT;
        } else {
            style = android.icu.text.RelativeDateTimeFormatter.Style.LONG;
        }
        if (z2) {
            direction = android.icu.text.RelativeDateTimeFormatter.Direction.LAST;
        } else {
            direction = android.icu.text.RelativeDateTimeFormatter.Direction.NEXT;
        }
        if (abs < 60000 && j3 < 60000) {
            i3 = (int) (abs / 1000);
            relativeUnit = android.icu.text.RelativeDateTimeFormatter.RelativeUnit.SECONDS;
            z = true;
            absoluteUnit = null;
        } else if (abs < 3600000 && j3 < 3600000) {
            i3 = (int) (abs / 60000);
            relativeUnit = android.icu.text.RelativeDateTimeFormatter.RelativeUnit.MINUTES;
            z = true;
            absoluteUnit = null;
        } else if (abs < 86400000 && j3 < 86400000) {
            i3 = (int) (abs / 3600000);
            relativeUnit = android.icu.text.RelativeDateTimeFormatter.RelativeUnit.HOURS;
            z = true;
            absoluteUnit = null;
        } else if (abs < 604800000 && j3 < 604800000) {
            i3 = java.lang.Math.abs(dayDistance(timeZone, j, j2));
            relativeUnit = android.icu.text.RelativeDateTimeFormatter.RelativeUnit.DAYS;
            if (i3 == 2) {
                if (z2) {
                    synchronized (CACHED_FORMATTERS) {
                        format = getFormatter(uLocale, style, displayContext).format(android.icu.text.RelativeDateTimeFormatter.Direction.LAST_2, android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.DAY);
                    }
                } else {
                    synchronized (CACHED_FORMATTERS) {
                        format = getFormatter(uLocale, style, displayContext).format(android.icu.text.RelativeDateTimeFormatter.Direction.NEXT_2, android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.DAY);
                    }
                }
                if (format != null && !format.isEmpty()) {
                    return format;
                }
            } else if (i3 == 1) {
                absoluteUnit = android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.DAY;
            } else if (i3 == 0) {
                android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit absoluteUnit2 = android.icu.text.RelativeDateTimeFormatter.AbsoluteUnit.DAY;
                direction = android.icu.text.RelativeDateTimeFormatter.Direction.THIS;
                absoluteUnit = absoluteUnit2;
            }
            z = true;
            absoluteUnit = null;
        } else if (j3 == 604800000) {
            i3 = (int) (abs / 604800000);
            relativeUnit = android.icu.text.RelativeDateTimeFormatter.RelativeUnit.WEEKS;
            z = true;
            absoluteUnit = null;
        } else {
            android.icu.util.Calendar createIcuCalendar = android.text.format.DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j);
            if ((i & 12) != 0) {
                i2 = i;
            } else {
                if (createIcuCalendar.get(1) != android.text.format.DateUtilsBridge.createIcuCalendar(timeZone, uLocale, j2).get(1)) {
                    i2 = i | 4;
                } else {
                    i2 = i | 8;
                }
            }
            return android.text.format.DateTimeFormat.format(uLocale, createIcuCalendar, i2, displayContext);
        }
        synchronized (CACHED_FORMATTERS) {
            android.icu.text.RelativeDateTimeFormatter formatter = getFormatter(uLocale, style, displayContext);
            if (z) {
                return formatter.format(i3, direction, relativeUnit);
            }
            return formatter.format(direction, absoluteUnit);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
    
        if (r21 < 86400000) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static java.lang.String getRelativeDateTimeString(java.util.Locale locale, java.util.TimeZone timeZone, long j, long j2, long j3, long j4, int i) {
        android.icu.text.RelativeDateTimeFormatter.Style style;
        int i2;
        java.lang.String format;
        java.lang.String combineDateAndTime;
        long j5;
        if (locale == null) {
            throw new java.lang.NullPointerException("locale == null");
        }
        if (timeZone == null) {
            throw new java.lang.NullPointerException("tz == null");
        }
        android.icu.util.ULocale forLocale = android.icu.util.ULocale.forLocale(locale);
        android.icu.util.TimeZone icuTimeZone = android.text.format.DateUtilsBridge.icuTimeZone(timeZone);
        long abs = java.lang.Math.abs(j2 - j);
        long j6 = j4 <= 604800000 ? j4 : 604800000L;
        if ((i & 786432) != 0) {
            style = android.icu.text.RelativeDateTimeFormatter.Style.SHORT;
        } else {
            style = android.icu.text.RelativeDateTimeFormatter.Style.LONG;
        }
        android.icu.util.Calendar createIcuCalendar = android.text.format.DateUtilsBridge.createIcuCalendar(icuTimeZone, forLocale, j);
        android.icu.util.Calendar createIcuCalendar2 = android.text.format.DateUtilsBridge.createIcuCalendar(icuTimeZone, forLocale, j2);
        int abs2 = java.lang.Math.abs(android.text.format.DateUtilsBridge.dayDistance(createIcuCalendar, createIcuCalendar2));
        if (abs < j6) {
            if (abs2 > 0) {
                j5 = 86400000;
            }
            j5 = j3;
            format = getRelativeTimeSpanString(forLocale, icuTimeZone, j, j2, j5, i, android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        } else {
            if (createIcuCalendar.get(1) != createIcuCalendar2.get(1)) {
                i2 = 131092;
            } else {
                i2 = 65560;
            }
            format = android.text.format.DateTimeFormat.format(forLocale, createIcuCalendar, i2, android.icu.text.DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        }
        java.lang.String format2 = android.text.format.DateTimeFormat.format(forLocale, createIcuCalendar, 1, android.icu.text.DisplayContext.CAPITALIZATION_NONE);
        android.icu.text.DisplayContext displayContext = android.icu.text.DisplayContext.CAPITALIZATION_NONE;
        synchronized (CACHED_FORMATTERS) {
            combineDateAndTime = getFormatter(forLocale, style, displayContext).combineDateAndTime(format, format2);
        }
        return combineDateAndTime;
    }

    private static android.icu.text.RelativeDateTimeFormatter getFormatter(android.icu.util.ULocale uLocale, android.icu.text.RelativeDateTimeFormatter.Style style, android.icu.text.DisplayContext displayContext) {
        java.lang.String str = uLocale + "\t" + style + "\t" + displayContext;
        android.icu.text.RelativeDateTimeFormatter relativeDateTimeFormatter = CACHED_FORMATTERS.get(str);
        if (relativeDateTimeFormatter == null) {
            android.icu.text.RelativeDateTimeFormatter relativeDateTimeFormatter2 = android.icu.text.RelativeDateTimeFormatter.getInstance(uLocale, null, style, displayContext);
            CACHED_FORMATTERS.put(str, relativeDateTimeFormatter2);
            return relativeDateTimeFormatter2;
        }
        return relativeDateTimeFormatter;
    }

    private static int dayDistance(android.icu.util.TimeZone timeZone, long j, long j2) {
        return julianDay(timeZone, j2) - julianDay(timeZone, j);
    }

    private static int julianDay(android.icu.util.TimeZone timeZone, long j) {
        return ((int) ((j + timeZone.getOffset(j)) / 86400000)) + 2440588;
    }
}
