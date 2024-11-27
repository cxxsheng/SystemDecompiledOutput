package android.text.format;

/* loaded from: classes3.dex */
public final class DateUtilsBridge {
    public static android.icu.util.TimeZone icuTimeZone(java.util.TimeZone timeZone) {
        android.icu.util.TimeZone timeZone2 = android.icu.util.TimeZone.getTimeZone(timeZone.getID());
        timeZone2.freeze();
        return timeZone2;
    }

    public static android.icu.util.Calendar createIcuCalendar(android.icu.util.TimeZone timeZone, android.icu.util.ULocale uLocale, long j) {
        android.icu.util.GregorianCalendar gregorianCalendar = new android.icu.util.GregorianCalendar(timeZone, uLocale);
        gregorianCalendar.setTimeInMillis(j);
        return gregorianCalendar;
    }

    public static java.lang.String toSkeleton(android.icu.util.Calendar calendar, int i) {
        return toSkeleton(calendar, calendar, i);
    }

    public static java.lang.String toSkeleton(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2, int i) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        if ((524288 & i) != 0) {
            i |= 114688;
        }
        if ((131072 & i) != 0) {
            str = android.hardware.gnss.GnssSignalType.CODE_TYPE_M;
        } else if ((65536 & i) == 0) {
            str = "MMMM";
        } else {
            str = "MMM";
        }
        if ((32768 & i) == 0) {
            str2 = "EEEE";
        } else {
            str2 = "EEE";
        }
        int i2 = i & 128;
        if (i2 != 0) {
            str3 = "H";
        } else if ((i & 64) == 0) {
            str3 = "j";
        } else {
            str3 = "h";
        }
        if ((i & 16384) == 0 || i2 != 0) {
            str3 = str3 + "m";
        } else if (!onTheHour(calendar) || !onTheHour(calendar2)) {
            str3 = str3 + "m";
        }
        if (fallOnDifferentDates(calendar, calendar2)) {
            i |= 16;
        }
        if (fallInSameMonth(calendar, calendar2) && (i & 32) != 0) {
            i = i & (-3) & (-2);
        }
        if ((i & 19) == 0) {
            i |= 16;
        }
        if ((i & 16) != 0 && (i & 4) == 0 && (i & 8) == 0 && (!fallInSameYear(calendar, calendar2) || !isThisYear(calendar))) {
            i |= 4;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 48) != 0) {
            if ((i & 4) != 0) {
                sb.append("y");
            }
            sb.append(str);
            if ((i & 32) == 0) {
                sb.append(android.app.blob.XmlTags.ATTR_DESCRIPTION);
            }
        }
        if ((i & 2) != 0) {
            sb.append(str2);
        }
        if ((i & 1) != 0) {
            sb.append(str3);
        }
        return sb.toString();
    }

    public static int dayDistance(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        return calendar2.get(20) - calendar.get(20);
    }

    public static boolean isDisplayMidnightUsingSkeleton(android.icu.util.Calendar calendar) {
        return calendar.get(11) == 0 && calendar.get(12) == 0;
    }

    private static boolean onTheHour(android.icu.util.Calendar calendar) {
        return calendar.get(12) == 0 && calendar.get(13) == 0;
    }

    private static boolean fallOnDifferentDates(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        return (calendar.get(1) == calendar2.get(1) && calendar.get(2) == calendar2.get(2) && calendar.get(5) == calendar2.get(5)) ? false : true;
    }

    private static boolean fallInSameMonth(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        return calendar.get(2) == calendar2.get(2);
    }

    private static boolean fallInSameYear(android.icu.util.Calendar calendar, android.icu.util.Calendar calendar2) {
        return calendar.get(1) == calendar2.get(1);
    }

    private static boolean isThisYear(android.icu.util.Calendar calendar) {
        android.icu.util.Calendar calendar2 = (android.icu.util.Calendar) calendar.clone();
        calendar2.setTimeInMillis(java.lang.System.currentTimeMillis());
        return calendar.get(1) == calendar2.get(1);
    }
}
