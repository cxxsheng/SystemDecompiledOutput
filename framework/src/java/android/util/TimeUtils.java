package android.util;

/* loaded from: classes3.dex */
public class TimeUtils {
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    public static final long NANOS_PER_MS = 1000000;
    private static final int SECONDS_PER_DAY = 86400;
    private static final int SECONDS_PER_HOUR = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final java.text.SimpleDateFormat sLoggingFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final java.text.SimpleDateFormat sDumpDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final java.time.Instant MIN_USE_DATE_OF_TIMEZONE = java.time.Instant.ofEpochMilli(1546300800000L);
    private static final java.lang.Object sFormatSync = new java.lang.Object();
    private static char[] sFormatStr = new char[29];
    private static char[] sTmpFormatStr = new char[29];

    public static java.util.TimeZone getTimeZone(int i, boolean z, long j, java.lang.String str) {
        android.icu.util.TimeZone icuTimeZone = getIcuTimeZone(i, z, j, str);
        if (icuTimeZone != null) {
            return java.util.TimeZone.getTimeZone(icuTimeZone.getID());
        }
        return null;
    }

    private static android.icu.util.TimeZone getIcuTimeZone(int i, boolean z, long j, java.lang.String str) {
        com.android.i18n.timezone.CountryTimeZones.OffsetResult lookupByOffsetWithBias;
        if (str == null) {
            return null;
        }
        android.icu.util.TimeZone timeZone = android.icu.util.TimeZone.getDefault();
        com.android.i18n.timezone.CountryTimeZones lookupCountryTimeZones = com.android.i18n.timezone.TimeZoneFinder.getInstance().lookupCountryTimeZones(str);
        if (lookupCountryTimeZones == null || (lookupByOffsetWithBias = lookupCountryTimeZones.lookupByOffsetWithBias(j, timeZone, i, z)) == null) {
            return null;
        }
        return lookupByOffsetWithBias.getTimeZone();
    }

    public static java.util.List<java.lang.String> getTimeZoneIdsForCountryCode(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("countryCode == null");
        }
        com.android.i18n.timezone.CountryTimeZones lookupCountryTimeZones = com.android.i18n.timezone.TimeZoneFinder.getInstance().lookupCountryTimeZones(str.toLowerCase());
        if (lookupCountryTimeZones == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.i18n.timezone.CountryTimeZones.TimeZoneMapping timeZoneMapping : lookupCountryTimeZones.getTimeZoneMappings()) {
            if (timeZoneMapping.isShownInPickerAt(MIN_USE_DATE_OF_TIMEZONE)) {
                arrayList.add(timeZoneMapping.getTimeZoneId());
            }
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    public static java.lang.String getTimeZoneDatabaseVersion() {
        return com.android.i18n.timezone.ZoneInfoDb.getInstance().getVersion();
    }

    private static int accumField(int i, int i2, boolean z, int i3) {
        int i4 = 0;
        if (i > 999) {
            while (i != 0) {
                i4++;
                i /= 10;
            }
            return i4 + i2;
        }
        if (i > 99 || (z && i3 >= 3)) {
            return i2 + 3;
        }
        if (i > 9 || (z && i3 >= 2)) {
            return i2 + 2;
        }
        if (z || i > 0) {
            return i2 + 1;
        }
        return 0;
    }

    private static int printFieldLocked(char[] cArr, int i, char c, int i2, boolean z, int i3) {
        int i4;
        if (z || i > 0) {
            if (i > 999) {
                int i5 = 0;
                while (i != 0 && i5 < sTmpFormatStr.length) {
                    sTmpFormatStr[i5] = (char) ((i % 10) + 48);
                    i5++;
                    i /= 10;
                }
                for (int i6 = i5 - 1; i6 >= 0; i6--) {
                    cArr[i2] = sTmpFormatStr[i6];
                    i2++;
                }
            } else {
                if ((!z || i3 < 3) && i <= 99) {
                    i4 = i2;
                } else {
                    int i7 = i / 100;
                    cArr[i2] = (char) (i7 + 48);
                    i4 = i2 + 1;
                    i -= i7 * 100;
                }
                if ((z && i3 >= 2) || i > 9 || i2 != i4) {
                    int i8 = i / 10;
                    cArr[i4] = (char) (i8 + 48);
                    i4++;
                    i -= i8 * 10;
                }
                cArr[i4] = (char) (i + 48);
                i2 = i4 + 1;
            }
            cArr[i2] = c;
            return i2 + 1;
        }
        return i2;
    }

    private static int formatDurationLocked(long j, int i) {
        char c;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        long j2 = j;
        if (sFormatStr.length < i) {
            sFormatStr = new char[i];
        }
        char[] cArr = sFormatStr;
        int i7 = 0;
        if (j2 == 0) {
            int i8 = i - 1;
            while (i7 < i8) {
                cArr[i7] = ' ';
                i7++;
            }
            cArr[i7] = '0';
            return i7 + 1;
        }
        if (j2 > 0) {
            c = '+';
        } else {
            j2 = -j2;
            c = '-';
        }
        int i9 = (int) (j2 % 1000);
        int floor = (int) java.lang.Math.floor(j2 / 1000);
        if (floor < SECONDS_PER_DAY) {
            i2 = 0;
        } else {
            i2 = floor / SECONDS_PER_DAY;
            floor -= SECONDS_PER_DAY * i2;
        }
        if (floor < 3600) {
            i3 = 0;
        } else {
            i3 = floor / 3600;
            floor -= i3 * 3600;
        }
        if (floor < 60) {
            i4 = floor;
            i5 = 0;
        } else {
            int i10 = floor / 60;
            i4 = floor - (i10 * 60);
            i5 = i10;
        }
        if (i == 0) {
            i6 = 0;
        } else {
            int accumField = accumField(i2, 1, false, 0);
            int accumField2 = accumField + accumField(i3, 1, accumField > 0, 2);
            int accumField3 = accumField2 + accumField(i5, 1, accumField2 > 0, 2);
            int accumField4 = accumField3 + accumField(i4, 1, accumField3 > 0, 2);
            i6 = 0;
            for (int accumField5 = accumField4 + accumField(i9, 2, true, accumField4 > 0 ? 3 : 0) + 1; accumField5 < i; accumField5++) {
                cArr[i6] = ' ';
                i6++;
            }
        }
        cArr[i6] = c;
        int i11 = i6 + 1;
        boolean z = i != 0;
        int printFieldLocked = printFieldLocked(cArr, i2, android.text.format.DateFormat.DATE, i11, false, 0);
        int printFieldLocked2 = printFieldLocked(cArr, i3, android.text.format.DateFormat.HOUR, printFieldLocked, printFieldLocked != i11, z ? 2 : 0);
        int printFieldLocked3 = printFieldLocked(cArr, i5, android.text.format.DateFormat.MINUTE, printFieldLocked2, printFieldLocked2 != i11, z ? 2 : 0);
        int printFieldLocked4 = printFieldLocked(cArr, i4, 's', printFieldLocked3, printFieldLocked3 != i11, z ? 2 : 0);
        int printFieldLocked5 = printFieldLocked(cArr, i9, android.text.format.DateFormat.MINUTE, printFieldLocked4, true, (!z || printFieldLocked4 == i11) ? 0 : 3);
        cArr[printFieldLocked5] = 's';
        return printFieldLocked5 + 1;
    }

    public static void formatDuration(long j, java.lang.StringBuilder sb) {
        synchronized (sFormatSync) {
            sb.append(sFormatStr, 0, formatDurationLocked(j, 0));
        }
    }

    public static void formatDuration(long j, java.lang.StringBuilder sb, int i) {
        synchronized (sFormatSync) {
            sb.append(sFormatStr, 0, formatDurationLocked(j, i));
        }
    }

    public static void formatDuration(long j, java.io.PrintWriter printWriter, int i) {
        synchronized (sFormatSync) {
            printWriter.print(new java.lang.String(sFormatStr, 0, formatDurationLocked(j, i)));
        }
    }

    public static java.lang.String formatDuration(long j) {
        java.lang.String str;
        synchronized (sFormatSync) {
            str = new java.lang.String(sFormatStr, 0, formatDurationLocked(j, 0));
        }
        return str;
    }

    public static void formatDuration(long j, java.io.PrintWriter printWriter) {
        formatDuration(j, printWriter, 0);
    }

    public static void formatDuration(long j, long j2, java.lang.StringBuilder sb) {
        if (j == 0) {
            sb.append("--");
        } else {
            formatDuration(j - j2, sb, 0);
        }
    }

    public static void formatDuration(long j, long j2, java.io.PrintWriter printWriter) {
        if (j == 0) {
            printWriter.print("--");
        } else {
            formatDuration(j - j2, printWriter, 0);
        }
    }

    public static java.lang.String formatUptime(long j) {
        return formatTime(j, android.os.SystemClock.uptimeMillis());
    }

    public static java.lang.String formatRealtime(long j) {
        return formatTime(j, android.os.SystemClock.elapsedRealtime());
    }

    public static java.lang.String formatTime(long j, long j2) {
        long j3 = j - j2;
        if (j3 > 0) {
            return j + " (in " + j3 + " ms)";
        }
        if (j3 < 0) {
            return j + " (" + (-j3) + " ms ago)";
        }
        return j + " (now)";
    }

    public static java.lang.String logTimeOfDay(long j) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        if (j >= 0) {
            calendar.setTimeInMillis(j);
            return java.lang.String.format("%tm-%td %tH:%tM:%tS.%tL", calendar, calendar, calendar, calendar, calendar, calendar);
        }
        return java.lang.Long.toString(j);
    }

    public static java.lang.String formatForLogging(long j) {
        if (j <= 0) {
            return "unknown";
        }
        return sLoggingFormat.format(new java.util.Date(j));
    }

    public static void dumpTime(java.io.PrintWriter printWriter, long j) {
        printWriter.print(sDumpDateFormat.format(new java.util.Date(j)));
    }

    public static boolean isTimeBetween(java.time.LocalTime localTime, java.time.LocalTime localTime2, java.time.LocalTime localTime3) {
        if (!localTime.isBefore(localTime2) || !localTime.isAfter(localTime3)) {
            if (!localTime.isBefore(localTime3) || !localTime.isBefore(localTime2) || !localTime2.isBefore(localTime3)) {
                if (localTime.isAfter(localTime3) && localTime.isAfter(localTime2) && localTime2.isBefore(localTime3)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public static void dumpTimeWithDelta(java.io.PrintWriter printWriter, long j, long j2) {
        printWriter.print(sDumpDateFormat.format(new java.util.Date(j)));
        if (j == j2) {
            printWriter.print(" (now)");
            return;
        }
        printWriter.print(" (");
        formatDuration(j, j2, printWriter);
        printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
    }
}
