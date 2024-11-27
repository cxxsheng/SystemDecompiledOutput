package android.text.format;

/* loaded from: classes3.dex */
public class DateUtils {

    @java.lang.Deprecated
    public static final java.lang.String ABBREV_MONTH_FORMAT = "%b";
    public static final java.lang.String ABBREV_WEEKDAY_FORMAT = "%a";
    public static final long DAY_IN_MILLIS = 86400000;

    @java.lang.Deprecated
    public static final int FORMAT_12HOUR = 64;

    @java.lang.Deprecated
    public static final int FORMAT_24HOUR = 128;
    public static final int FORMAT_ABBREV_ALL = 524288;
    public static final int FORMAT_ABBREV_MONTH = 65536;
    public static final int FORMAT_ABBREV_RELATIVE = 262144;
    public static final int FORMAT_ABBREV_TIME = 16384;
    public static final int FORMAT_ABBREV_WEEKDAY = 32768;

    @java.lang.Deprecated
    public static final int FORMAT_CAP_AMPM = 256;

    @java.lang.Deprecated
    public static final int FORMAT_CAP_MIDNIGHT = 4096;

    @java.lang.Deprecated
    public static final int FORMAT_CAP_NOON = 1024;

    @java.lang.Deprecated
    public static final int FORMAT_CAP_NOON_MIDNIGHT = 5120;
    public static final int FORMAT_NO_MIDNIGHT = 2048;
    public static final int FORMAT_NO_MONTH_DAY = 32;
    public static final int FORMAT_NO_NOON = 512;

    @java.lang.Deprecated
    public static final int FORMAT_NO_NOON_MIDNIGHT = 2560;
    public static final int FORMAT_NO_YEAR = 8;
    public static final int FORMAT_NUMERIC_DATE = 131072;
    public static final int FORMAT_SHOW_DATE = 16;
    public static final int FORMAT_SHOW_TIME = 1;
    public static final int FORMAT_SHOW_WEEKDAY = 2;
    public static final int FORMAT_SHOW_YEAR = 4;

    @java.lang.Deprecated
    public static final int FORMAT_UTC = 8192;
    public static final long HOUR_IN_MILLIS = 3600000;

    @java.lang.Deprecated
    public static final java.lang.String HOUR_MINUTE_24 = "%H:%M";

    @java.lang.Deprecated
    public static final int LENGTH_LONG = 10;

    @java.lang.Deprecated
    public static final int LENGTH_MEDIUM = 20;

    @java.lang.Deprecated
    public static final int LENGTH_SHORT = 30;

    @java.lang.Deprecated
    public static final int LENGTH_SHORTER = 40;

    @java.lang.Deprecated
    public static final int LENGTH_SHORTEST = 50;
    public static final long MINUTE_IN_MILLIS = 60000;
    public static final java.lang.String MONTH_DAY_FORMAT = "%-d";
    public static final java.lang.String MONTH_FORMAT = "%B";
    public static final java.lang.String NUMERIC_MONTH_FORMAT = "%m";
    public static final long SECOND_IN_MILLIS = 1000;
    public static final java.lang.String WEEKDAY_FORMAT = "%A";
    public static final long WEEK_IN_MILLIS = 604800000;
    public static final java.lang.String YEAR_FORMAT = "%Y";
    public static final java.lang.String YEAR_FORMAT_TWO_DIGITS = "%g";

    @java.lang.Deprecated
    public static final long YEAR_IN_MILLIS = 31449600000L;
    private static java.lang.String sElapsedFormatHMMSS;
    private static java.lang.String sElapsedFormatMMSS;
    private static android.content.res.Configuration sLastConfig;
    private static android.text.format.Time sNowTime;
    private static android.text.format.Time sThenTime;
    private static final java.lang.Object sLock = new java.lang.Object();

    @java.lang.Deprecated
    public static final int[] sameYearTable = null;

    @java.lang.Deprecated
    public static final int[] sameMonthTable = null;

    @java.lang.Deprecated
    public static java.lang.String getDayOfWeekString(int i, int i2) {
        int i3;
        android.icu.text.DateFormatSymbols dateFormatSymbols = android.icu.text.DateFormatSymbols.getInstance();
        switch (i2) {
            case 10:
                i3 = 1;
                break;
            case 50:
                i3 = 2;
                break;
            default:
                i3 = 0;
                break;
        }
        return dateFormatSymbols.getWeekdays(0, i3)[i];
    }

    @java.lang.Deprecated
    public static java.lang.String getAMPMString(int i) {
        return android.text.format.DateFormat.getIcuDateFormatSymbols(java.util.Locale.getDefault()).getAmPmStrings()[i + 0];
    }

    @java.lang.Deprecated
    public static java.lang.String getMonthString(int i, int i2) {
        int i3;
        android.icu.text.DateFormatSymbols icuDateFormatSymbols = android.text.format.DateFormat.getIcuDateFormatSymbols(java.util.Locale.getDefault());
        switch (i2) {
            case 10:
                i3 = 1;
                break;
            case 50:
                i3 = 2;
                break;
            default:
                i3 = 0;
                break;
        }
        return icuDateFormatSymbols.getMonths(0, i3)[i];
    }

    public static java.lang.CharSequence getRelativeTimeSpanString(long j) {
        return getRelativeTimeSpanString(j, java.lang.System.currentTimeMillis(), 60000L);
    }

    public static java.lang.CharSequence getRelativeTimeSpanString(long j, long j2, long j3) {
        return getRelativeTimeSpanString(j, j2, j3, 65556);
    }

    public static java.lang.CharSequence getRelativeTimeSpanString(long j, long j2, long j3, int i) {
        return android.text.format.RelativeDateTimeFormatter.getRelativeTimeSpanString(java.util.Locale.getDefault(), java.util.TimeZone.getDefault(), j, j2, j3, i);
    }

    public static java.lang.CharSequence getRelativeDateTimeString(android.content.Context context, long j, long j2, long j3, int i) {
        int i2;
        if ((i & 193) == 1) {
            i2 = i | (android.text.format.DateFormat.is24HourFormat(context) ? 128 : 64);
        } else {
            i2 = i;
        }
        return android.text.format.RelativeDateTimeFormatter.getRelativeDateTimeString(java.util.Locale.getDefault(), java.util.TimeZone.getDefault(), j, java.lang.System.currentTimeMillis(), j2, j3, i2);
    }

    private static void initFormatStrings() {
        synchronized (sLock) {
            initFormatStringsLocked();
        }
    }

    private static void initFormatStringsLocked() {
        android.content.res.Resources system = android.content.res.Resources.getSystem();
        android.content.res.Configuration configuration = system.getConfiguration();
        if (sLastConfig == null || !sLastConfig.equals(configuration)) {
            sLastConfig = configuration;
            sElapsedFormatMMSS = system.getString(com.android.internal.R.string.elapsed_time_short_format_mm_ss);
            sElapsedFormatHMMSS = system.getString(com.android.internal.R.string.elapsed_time_short_format_h_mm_ss);
        }
    }

    public static java.lang.CharSequence formatDuration(long j) {
        return formatDuration(j, 10);
    }

    public static java.lang.CharSequence formatDuration(long j, int i) {
        android.icu.text.MeasureFormat.FormatWidth formatWidth;
        switch (i) {
            case 10:
                formatWidth = android.icu.text.MeasureFormat.FormatWidth.WIDE;
                break;
            case 20:
            case 30:
            case 40:
                formatWidth = android.icu.text.MeasureFormat.FormatWidth.SHORT;
                break;
            case 50:
                formatWidth = android.icu.text.MeasureFormat.FormatWidth.NARROW;
                break;
            default:
                formatWidth = android.icu.text.MeasureFormat.FormatWidth.WIDE;
                break;
        }
        android.icu.text.MeasureFormat measureFormat = android.icu.text.MeasureFormat.getInstance(java.util.Locale.getDefault(), formatWidth);
        if (j >= 3600000) {
            return measureFormat.format(new android.icu.util.Measure(java.lang.Integer.valueOf((int) ((j + android.app.AlarmManager.INTERVAL_HALF_HOUR) / 3600000)), android.icu.util.MeasureUnit.HOUR));
        }
        if (j >= 60000) {
            return measureFormat.format(new android.icu.util.Measure(java.lang.Integer.valueOf((int) ((j + 30000) / 60000)), android.icu.util.MeasureUnit.MINUTE));
        }
        return measureFormat.format(new android.icu.util.Measure(java.lang.Integer.valueOf((int) ((j + 500) / 1000)), android.icu.util.MeasureUnit.SECOND));
    }

    public static java.lang.String formatElapsedTime(long j) {
        return formatElapsedTime(null, j);
    }

    public static java.lang.String formatElapsedTime(java.lang.StringBuilder sb, long j) {
        long j2;
        long j3;
        if (j < 3600) {
            j2 = 0;
        } else {
            j2 = j / 3600;
            j -= 3600 * j2;
        }
        if (j < 60) {
            j3 = 0;
        } else {
            j3 = j / 60;
            j -= 60 * j3;
        }
        if (sb == null) {
            sb = new java.lang.StringBuilder(8);
        } else {
            sb.setLength(0);
        }
        java.util.Formatter formatter = new java.util.Formatter(sb, java.util.Locale.getDefault());
        initFormatStrings();
        if (j2 > 0) {
            return formatter.format(sElapsedFormatHMMSS, java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j)).toString();
        }
        return formatter.format(sElapsedFormatMMSS, java.lang.Long.valueOf(j3), java.lang.Long.valueOf(j)).toString();
    }

    public static final java.lang.CharSequence formatSameDayTime(long j, long j2, int i, int i2) {
        java.text.DateFormat dateInstance;
        java.util.GregorianCalendar gregorianCalendar = new java.util.GregorianCalendar();
        gregorianCalendar.setTimeInMillis(j);
        java.util.Date time = gregorianCalendar.getTime();
        java.util.GregorianCalendar gregorianCalendar2 = new java.util.GregorianCalendar();
        gregorianCalendar2.setTimeInMillis(j2);
        if (gregorianCalendar.get(1) == gregorianCalendar2.get(1) && gregorianCalendar.get(2) == gregorianCalendar2.get(2) && gregorianCalendar.get(5) == gregorianCalendar2.get(5)) {
            dateInstance = java.text.DateFormat.getTimeInstance(i2);
        } else {
            dateInstance = java.text.DateFormat.getDateInstance(i);
        }
        return dateInstance.format(time);
    }

    public static boolean isToday(long j) {
        return isSameDate(j, java.lang.System.currentTimeMillis());
    }

    private static boolean isSameDate(long j, long j2) {
        java.time.ZoneId systemDefault = java.time.ZoneId.systemDefault();
        java.time.LocalDateTime ofInstant = java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(j), systemDefault);
        java.time.LocalDateTime ofInstant2 = java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(j2), systemDefault);
        return ofInstant.getYear() == ofInstant2.getYear() && ofInstant.getMonthValue() == ofInstant2.getMonthValue() && ofInstant.getDayOfMonth() == ofInstant2.getDayOfMonth();
    }

    public static java.lang.String formatDateRange(android.content.Context context, long j, long j2, int i) {
        return formatDateRange(context, new java.util.Formatter(new java.lang.StringBuilder(50), java.util.Locale.getDefault()), j, j2, i).toString();
    }

    public static java.util.Formatter formatDateRange(android.content.Context context, java.util.Formatter formatter, long j, long j2, int i) {
        return formatDateRange(context, formatter, j, j2, i, null);
    }

    public static java.util.Formatter formatDateRange(android.content.Context context, java.util.Formatter formatter, long j, long j2, int i, java.lang.String str) {
        int i2;
        if ((i & 193) != 1) {
            i2 = i;
        } else {
            i2 = i | (android.text.format.DateFormat.is24HourFormat(context) ? 128 : 64);
        }
        try {
            formatter.out().append(android.text.format.DateIntervalFormat.formatDateRange(j, j2, i2, str));
            return formatter;
        } catch (java.io.IOException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static java.lang.String formatDateTime(android.content.Context context, long j, int i) {
        return formatDateRange(context, j, j, i);
    }

    public static java.lang.CharSequence getRelativeTimeSpanString(android.content.Context context, long j, boolean z) {
        java.lang.String formatDateRange;
        int i;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        long abs = java.lang.Math.abs(currentTimeMillis - j);
        synchronized (android.text.format.DateUtils.class) {
            if (sNowTime == null) {
                sNowTime = new android.text.format.Time();
            }
            if (sThenTime == null) {
                sThenTime = new android.text.format.Time();
            }
            sNowTime.set(currentTimeMillis);
            sThenTime.set(j);
            if (abs < 86400000 && sNowTime.weekDay == sThenTime.weekDay) {
                formatDateRange = formatDateRange(context, j, j, 1);
                i = com.android.internal.R.string.preposition_for_time;
            } else if (sNowTime.year != sThenTime.year) {
                formatDateRange = formatDateRange(context, j, j, 131092);
                i = 17041486;
            } else {
                formatDateRange = formatDateRange(context, j, j, 65552);
                i = 17041486;
            }
            if (z) {
                formatDateRange = context.getResources().getString(i, formatDateRange);
            }
        }
        return formatDateRange;
    }

    public static java.lang.CharSequence getRelativeTimeSpanString(android.content.Context context, long j) {
        return getRelativeTimeSpanString(context, j, false);
    }
}
