package android.text.format;

/* loaded from: classes3.dex */
public class DateFormat {

    @java.lang.Deprecated
    public static final char AM_PM = 'a';

    @java.lang.Deprecated
    public static final char CAPITAL_AM_PM = 'A';

    @java.lang.Deprecated
    public static final char DATE = 'd';

    @java.lang.Deprecated
    public static final char DAY = 'E';
    static final long DISALLOW_DUPLICATE_FIELD_IN_SKELETON = 170233598;

    @java.lang.Deprecated
    public static final char HOUR = 'h';

    @java.lang.Deprecated
    public static final char HOUR_OF_DAY = 'k';

    @java.lang.Deprecated
    public static final char MINUTE = 'm';

    @java.lang.Deprecated
    public static final char MONTH = 'M';

    @java.lang.Deprecated
    public static final char QUOTE = '\'';

    @java.lang.Deprecated
    public static final char SECONDS = 's';

    @java.lang.Deprecated
    public static final char STANDALONE_MONTH = 'L';

    @java.lang.Deprecated
    public static final char TIME_ZONE = 'z';

    @java.lang.Deprecated
    public static final char YEAR = 'y';
    private static boolean sIs24Hour;
    private static java.util.Locale sIs24HourLocale;
    private static final java.lang.Object sLocaleLock = new java.lang.Object();

    public static boolean is24HourFormat(android.content.Context context) {
        return is24HourFormat(context, context.getUserId());
    }

    public static boolean is24HourFormat(android.content.Context context, int i) {
        java.lang.String stringForUser = android.provider.Settings.System.getStringForUser(context.getContentResolver(), android.provider.Settings.System.TIME_12_24, i);
        if (stringForUser != null) {
            return stringForUser.equals("24");
        }
        return is24HourLocale(context.getResources().getConfiguration().locale);
    }

    public static boolean is24HourLocale(java.util.Locale locale) {
        boolean z;
        synchronized (sLocaleLock) {
            if (sIs24HourLocale != null && sIs24HourLocale.equals(locale)) {
                return sIs24Hour;
            }
            java.text.DateFormat timeInstance = java.text.DateFormat.getTimeInstance(1, locale);
            if (timeInstance instanceof java.text.SimpleDateFormat) {
                z = hasDesignator(((java.text.SimpleDateFormat) timeInstance).toPattern(), 'H');
            } else {
                z = false;
            }
            synchronized (sLocaleLock) {
                sIs24HourLocale = locale;
                sIs24Hour = z;
            }
            return z;
        }
    }

    public static java.lang.String getBestDateTimePattern(java.util.Locale locale, java.lang.String str) {
        android.icu.util.ULocale forLocale = android.icu.util.ULocale.forLocale(locale);
        return getCompatibleEnglishPattern(forLocale, android.icu.text.DateTimePatternGenerator.getInstance(forLocale).getBestPattern(str, 0, !android.app.compat.CompatChanges.isChangeEnabled(DISALLOW_DUPLICATE_FIELD_IN_SKELETON)));
    }

    public static java.text.DateFormat getTimeFormat(android.content.Context context) {
        return new java.text.SimpleDateFormat(getTimeFormatString(context), context.getResources().getConfiguration().locale);
    }

    public static java.lang.String getTimeFormatString(android.content.Context context) {
        return getTimeFormatString(context, context.getUserId());
    }

    public static java.lang.String getTimeFormatString(android.content.Context context, int i) {
        android.icu.util.ULocale forLocale = android.icu.util.ULocale.forLocale(context.getResources().getConfiguration().locale);
        android.icu.text.DateTimePatternGenerator dateTimePatternGenerator = android.icu.text.DateTimePatternGenerator.getInstance(forLocale);
        return getCompatibleEnglishPattern(forLocale, is24HourFormat(context, i) ? dateTimePatternGenerator.getBestPattern("Hm") : dateTimePatternGenerator.getBestPattern("hm"));
    }

    public static java.text.DateFormat getDateFormat(android.content.Context context) {
        return java.text.DateFormat.getDateInstance(3, context.getResources().getConfiguration().locale);
    }

    public static java.text.DateFormat getLongDateFormat(android.content.Context context) {
        return java.text.DateFormat.getDateInstance(1, context.getResources().getConfiguration().locale);
    }

    public static java.text.DateFormat getMediumDateFormat(android.content.Context context) {
        return java.text.DateFormat.getDateInstance(2, context.getResources().getConfiguration().locale);
    }

    public static char[] getDateFormatOrder(android.content.Context context) {
        return getDateFormatOrder(getDateFormatString(context));
    }

    public static char[] getDateFormatOrder(java.lang.String str) {
        char[] cArr = new char[3];
        int i = 0;
        int i2 = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == 'd' || charAt == 'L' || charAt == 'M' || charAt == 'y') {
                if (charAt == 'd' && !z) {
                    cArr[i2] = DATE;
                    i2++;
                    z = true;
                } else if ((charAt == 'L' || charAt == 'M') && !z2) {
                    cArr[i2] = MONTH;
                    i2++;
                    z2 = true;
                } else if (charAt == 'y' && !z3) {
                    cArr[i2] = 'y';
                    i2++;
                    z3 = true;
                }
            } else if (charAt == 'G') {
                continue;
            } else {
                if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                    throw new java.lang.IllegalArgumentException("Bad pattern character '" + charAt + "' in " + str);
                }
                if (charAt != '\'') {
                    continue;
                } else {
                    if (i < str.length() - 1) {
                        int i3 = i + 1;
                        if (str.charAt(i3) == '\'') {
                            i = i3;
                        }
                    }
                    int indexOf = str.indexOf(39, i + 1);
                    if (indexOf == -1) {
                        throw new java.lang.IllegalArgumentException("Bad quoting in " + str);
                    }
                    i = indexOf + 1;
                }
            }
            i++;
        }
        return cArr;
    }

    private static java.lang.String getDateFormatString(android.content.Context context) {
        java.text.DateFormat dateInstance = java.text.DateFormat.getDateInstance(3, context.getResources().getConfiguration().locale);
        if (dateInstance instanceof java.text.SimpleDateFormat) {
            return ((java.text.SimpleDateFormat) dateInstance).toPattern();
        }
        throw new java.lang.AssertionError("!(df instanceof SimpleDateFormat)");
    }

    public static java.lang.CharSequence format(java.lang.CharSequence charSequence, long j) {
        return format(charSequence, new java.util.Date(j));
    }

    public static java.lang.CharSequence format(java.lang.CharSequence charSequence, java.util.Date date) {
        java.util.GregorianCalendar gregorianCalendar = new java.util.GregorianCalendar();
        gregorianCalendar.setTime(date);
        return format(charSequence, gregorianCalendar);
    }

    public static boolean hasSeconds(java.lang.CharSequence charSequence) {
        return hasDesignator(charSequence, 's');
    }

    public static boolean hasDesignator(java.lang.CharSequence charSequence, char c) {
        if (charSequence == null) {
            return false;
        }
        int length = charSequence.length();
        boolean z = false;
        for (int i = 0; i < length; i++) {
            char charAt = charSequence.charAt(i);
            if (charAt == '\'') {
                z = !z;
            } else if (!z && charAt == c) {
                return true;
            }
        }
        return false;
    }

    public static java.lang.CharSequence format(java.lang.CharSequence charSequence, java.util.Calendar calendar) {
        int i;
        java.lang.String str;
        int i2;
        int i3;
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence);
        android.icu.text.DateFormatSymbols icuDateFormatSymbols = getIcuDateFormatSymbols(java.util.Locale.getDefault());
        java.lang.String[] amPmStrings = icuDateFormatSymbols.getAmPmStrings();
        int length = charSequence.length();
        int i4 = 0;
        while (i4 < length) {
            char charAt = spannableStringBuilder.charAt(i4);
            if (charAt != '\'') {
                int i5 = 1;
                while (true) {
                    i = i4 + i5;
                    if (i < length && spannableStringBuilder.charAt(i) == charAt) {
                        i5++;
                    }
                }
                int i6 = 12;
                switch (charAt) {
                    case 'A':
                    case 'a':
                        str = amPmStrings[calendar.get(9) - 0];
                        break;
                    case 'E':
                    case 'c':
                        str = getDayOfWeekString(icuDateFormatSymbols, calendar.get(7), i5, charAt);
                        break;
                    case 'H':
                    case 'k':
                        str = zeroPad(calendar.get(11), i5);
                        break;
                    case 'K':
                    case 'h':
                        int i7 = calendar.get(10);
                        if (charAt != 'h' || i7 != 0) {
                            i6 = i7;
                        }
                        str = zeroPad(i6, i5);
                        break;
                    case 'L':
                    case 'M':
                        str = getMonthString(icuDateFormatSymbols, calendar.get(2), i5, charAt);
                        break;
                    case 'd':
                        str = zeroPad(calendar.get(5), i5);
                        break;
                    case 'm':
                        str = zeroPad(calendar.get(12), i5);
                        break;
                    case 's':
                        str = zeroPad(calendar.get(13), i5);
                        break;
                    case 'y':
                        str = getYearString(calendar.get(1), i5);
                        break;
                    case 'z':
                        str = getTimeZoneString(calendar, i5);
                        break;
                    default:
                        str = null;
                        break;
                }
                if (str == null) {
                    i2 = length;
                    i3 = i5;
                } else {
                    spannableStringBuilder.replace(i4, i, (java.lang.CharSequence) str);
                    i3 = str.length();
                    i2 = spannableStringBuilder.length();
                }
            } else {
                i3 = appendQuotedText(spannableStringBuilder, i4);
                i2 = spannableStringBuilder.length();
            }
            i4 += i3;
            length = i2;
        }
        if (charSequence instanceof android.text.Spanned) {
            return new android.text.SpannedString(spannableStringBuilder);
        }
        return spannableStringBuilder.toString();
    }

    private static java.lang.String getDayOfWeekString(android.icu.text.DateFormatSymbols dateFormatSymbols, int i, int i2, int i3) {
        int i4 = 1;
        int i5 = i3 == 99 ? 1 : 0;
        if (i2 == 5) {
            i4 = 2;
        } else if (i2 != 4) {
            i4 = 0;
        }
        return dateFormatSymbols.getWeekdays(i5, i4)[i];
    }

    private static java.lang.String getMonthString(android.icu.text.DateFormatSymbols dateFormatSymbols, int i, int i2, int i3) {
        int i4 = i3 == 76 ? 1 : 0;
        if (i2 == 5) {
            return dateFormatSymbols.getMonths(i4, 2)[i];
        }
        if (i2 == 4) {
            return dateFormatSymbols.getMonths(i4, 1)[i];
        }
        if (i2 == 3) {
            return dateFormatSymbols.getMonths(i4, 0)[i];
        }
        return zeroPad(i + 1, i2);
    }

    private static java.lang.String getTimeZoneString(java.util.Calendar calendar, int i) {
        java.util.TimeZone timeZone = calendar.getTimeZone();
        if (i < 2) {
            return formatZoneOffset(calendar.get(16) + calendar.get(15), i);
        }
        return timeZone.getDisplayName(calendar.get(16) != 0, 0);
    }

    private static java.lang.String formatZoneOffset(int i, int i2) {
        int i3 = i / 1000;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (i3 < 0) {
            sb.insert(0, com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            i3 = -i3;
        } else {
            sb.insert(0, "+");
        }
        sb.append(zeroPad(i3 / 3600, 2));
        sb.append(zeroPad((i3 % 3600) / 60, 2));
        return sb.toString();
    }

    private static java.lang.String getYearString(int i, int i2) {
        if (i2 <= 2) {
            return zeroPad(i % 100, 2);
        }
        return java.lang.String.format(java.util.Locale.getDefault(), "%d", java.lang.Integer.valueOf(i));
    }

    public static int appendQuotedText(android.text.SpannableStringBuilder spannableStringBuilder, int i) {
        int length = spannableStringBuilder.length();
        int i2 = i + 1;
        if (i2 < length && spannableStringBuilder.charAt(i2) == '\'') {
            spannableStringBuilder.delete(i, i2);
            return 1;
        }
        spannableStringBuilder.delete(i, i2);
        int i3 = length - 1;
        int i4 = 0;
        while (i < i3) {
            if (spannableStringBuilder.charAt(i) == '\'') {
                int i5 = i + 1;
                if (i5 < i3 && spannableStringBuilder.charAt(i5) == '\'') {
                    spannableStringBuilder.delete(i, i5);
                    i3--;
                    i4++;
                    i = i5;
                } else {
                    spannableStringBuilder.delete(i, i5);
                    break;
                }
            } else {
                i++;
                i4++;
            }
        }
        return i4;
    }

    private static java.lang.String zeroPad(int i, int i2) {
        return java.lang.String.format(java.util.Locale.getDefault(), "%0" + i2 + android.app.blob.XmlTags.ATTR_DESCRIPTION, java.lang.Integer.valueOf(i));
    }

    public static android.icu.text.DateFormatSymbols getIcuDateFormatSymbols(java.util.Locale locale) {
        return new android.icu.text.DateFormatSymbols((java.lang.Class<? extends android.icu.util.Calendar>) android.icu.util.GregorianCalendar.class, locale);
    }

    private static java.lang.String getCompatibleEnglishPattern(android.icu.util.ULocale uLocale, java.lang.String str) {
        if (str == null || uLocale == null || !"en".equals(uLocale.getLanguage())) {
            return str;
        }
        java.lang.String country = uLocale.getCountry();
        if (country != null && !country.isEmpty() && !"US".equals(country)) {
            return str;
        }
        return str.replace((char) 8239, ' ');
    }
}
