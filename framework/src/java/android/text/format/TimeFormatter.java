package android.text.format;

/* loaded from: classes3.dex */
class TimeFormatter {
    private static final int DAYSPERLYEAR = 366;
    private static final int DAYSPERNYEAR = 365;
    private static final int DAYSPERWEEK = 7;
    private static final int FORCE_LOWER_CASE = -1;
    private static final int HOURSPERDAY = 24;
    private static final int MINSPERHOUR = 60;
    private static final int MONSPERYEAR = 12;
    private static final int SECSPERMIN = 60;
    private static android.icu.text.DateFormatSymbols sDateFormatSymbols;
    private static java.lang.String sDateOnlyFormat;
    private static java.lang.String sDateTimeFormat;
    private static android.icu.text.DecimalFormatSymbols sDecimalFormatSymbols;
    private static java.util.Locale sLocale;
    private static java.lang.String sTimeOnlyFormat;
    private final android.icu.text.DateFormatSymbols dateFormatSymbols;
    private final java.lang.String dateOnlyFormat;
    private final java.lang.String dateTimeFormat;
    private final android.icu.text.DecimalFormatSymbols decimalFormatSymbols;
    private java.util.Formatter numberFormatter;
    private java.lang.StringBuilder outputBuilder;
    private final java.lang.String timeOnlyFormat;

    public TimeFormatter() {
        synchronized (android.text.format.TimeFormatter.class) {
            java.util.Locale locale = java.util.Locale.getDefault();
            if (sLocale == null || !locale.equals(sLocale)) {
                sLocale = locale;
                sDateFormatSymbols = android.text.format.DateFormat.getIcuDateFormatSymbols(locale);
                sDecimalFormatSymbols = android.icu.text.DecimalFormatSymbols.getInstance(locale);
                android.content.res.Resources system = android.content.res.Resources.getSystem();
                sTimeOnlyFormat = system.getString(com.android.internal.R.string.time_of_day);
                sDateOnlyFormat = system.getString(com.android.internal.R.string.month_day_year);
                sDateTimeFormat = system.getString(com.android.internal.R.string.date_and_time);
            }
            this.dateFormatSymbols = sDateFormatSymbols;
            this.decimalFormatSymbols = sDecimalFormatSymbols;
            this.dateTimeFormat = sDateTimeFormat;
            this.timeOnlyFormat = sTimeOnlyFormat;
            this.dateOnlyFormat = sDateOnlyFormat;
        }
    }

    java.lang.String formatMillisWithFixedFormat(long j) {
        java.time.LocalDateTime ofInstant = java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(j), java.time.ZoneId.systemDefault());
        java.lang.StringBuilder sb = new java.lang.StringBuilder(19);
        sb.append(ofInstant.getYear());
        sb.append('-');
        append2DigitNumber(sb, ofInstant.getMonthValue());
        sb.append('-');
        append2DigitNumber(sb, ofInstant.getDayOfMonth());
        sb.append(' ');
        append2DigitNumber(sb, ofInstant.getHour());
        sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        append2DigitNumber(sb, ofInstant.getMinute());
        sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
        append2DigitNumber(sb, ofInstant.getSecond());
        return localizeDigits(sb.toString());
    }

    private static void append2DigitNumber(java.lang.StringBuilder sb, int i) {
        if (i < 10) {
            sb.append('0');
        }
        sb.append(i);
    }

    public java.lang.String format(java.lang.String str, com.android.i18n.timezone.WallTime wallTime, com.android.i18n.timezone.ZoneInfoData zoneInfoData) {
        try {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            this.outputBuilder = sb;
            this.numberFormatter = new java.util.Formatter(sb, java.util.Locale.US);
            formatInternal(str, wallTime, zoneInfoData);
            return localizeDigits(sb.toString());
        } finally {
            this.outputBuilder = null;
            this.numberFormatter = null;
        }
    }

    private java.lang.String localizeDigits(java.lang.String str) {
        if (this.decimalFormatSymbols.getZeroDigit() == '0') {
            return str;
        }
        int length = str.length();
        int zeroDigit = this.decimalFormatSymbols.getZeroDigit() - '0';
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                charAt = (char) (charAt + zeroDigit);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    private void formatInternal(java.lang.String str, com.android.i18n.timezone.WallTime wallTime, com.android.i18n.timezone.ZoneInfoData zoneInfoData) {
        boolean z;
        java.nio.CharBuffer wrap = java.nio.CharBuffer.wrap(str);
        while (wrap.remaining() > 0) {
            if (wrap.get(wrap.position()) != '%') {
                z = true;
            } else {
                z = handleToken(wrap, wallTime, zoneInfoData);
            }
            if (z) {
                this.outputBuilder.append(wrap.get(wrap.position()));
            }
            wrap.position(wrap.position() + 1);
        }
    }

    private boolean handleToken(java.nio.CharBuffer charBuffer, com.android.i18n.timezone.WallTime wallTime, com.android.i18n.timezone.ZoneInfoData zoneInfoData) {
        int i;
        java.lang.String str;
        java.lang.String str2;
        char c = 0;
        while (true) {
            if (charBuffer.remaining() <= 1) {
                return true;
            }
            charBuffer.position(charBuffer.position() + 1);
            char c2 = charBuffer.get(charBuffer.position());
            char c3 = '-';
            java.lang.String str3 = "?";
            switch (c2) {
                case '#':
                case '-':
                case '0':
                case '^':
                case '_':
                    c = c2;
                    break;
                case '$':
                case '%':
                case '&':
                case '\'':
                case '(':
                case ')':
                case '*':
                case ',':
                case '.':
                case '/':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case ':':
                case ';':
                case '<':
                case '=':
                case '>':
                case '?':
                case '@':
                case 'J':
                case 'K':
                case 'L':
                case 'N':
                case 'Q':
                case '[':
                case '\\':
                case ']':
                case '`':
                case 'f':
                case 'i':
                case 'o':
                case 'q':
                default:
                    return true;
                case '+':
                    formatInternal("%a %b %e %H:%M:%S %Z %Y", wallTime, zoneInfoData);
                    return false;
                case 'A':
                    if (wallTime.getWeekDay() >= 0 && wallTime.getWeekDay() < 7) {
                        str3 = this.dateFormatSymbols.getWeekdays(0, 1)[wallTime.getWeekDay() + 1];
                    }
                    modifyAndAppend(str3, c);
                    return false;
                case 'B':
                    if (c == '-') {
                        if (wallTime.getMonth() >= 0 && wallTime.getMonth() < 12) {
                            str3 = this.dateFormatSymbols.getMonths(1, 1)[wallTime.getMonth()];
                        }
                        modifyAndAppend(str3, c);
                    } else {
                        if (wallTime.getMonth() >= 0 && wallTime.getMonth() < 12) {
                            str3 = this.dateFormatSymbols.getMonths(0, 1)[wallTime.getMonth()];
                        }
                        modifyAndAppend(str3, c);
                    }
                    return false;
                case 'C':
                    outputYear(wallTime.getYear(), true, false, c);
                    return false;
                case 'D':
                    formatInternal("%m/%d/%y", wallTime, zoneInfoData);
                    return false;
                case 'E':
                case 'O':
                    break;
                case 'F':
                    formatInternal("%Y-%m-%d", wallTime, zoneInfoData);
                    return false;
                case 'G':
                case 'V':
                case 'g':
                    int year = wallTime.getYear();
                    int yearDay = wallTime.getYearDay();
                    int weekDay = wallTime.getWeekDay();
                    while (true) {
                        int i2 = isLeap(year) ? 366 : 365;
                        int i3 = (((yearDay + 11) - weekDay) % 7) - 3;
                        int i4 = i3 - (i2 % 7);
                        if (i4 < -3) {
                            i4 += 7;
                        }
                        if (yearDay >= i4 + i2) {
                            year++;
                            i = 1;
                        } else if (yearDay >= i3) {
                            i = ((yearDay - i3) / 7) + 1;
                        } else {
                            year--;
                            yearDay += isLeap(year) ? 366 : 365;
                        }
                    }
                    if (c2 == 'V') {
                        this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(i));
                    } else if (c2 == 'g') {
                        outputYear(year, false, true, c);
                    } else {
                        outputYear(year, true, true, c);
                    }
                    return false;
                case 'H':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getHour()));
                    return false;
                case 'I':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getHour() % 12 != 0 ? wallTime.getHour() % 12 : 12));
                    return false;
                case 'M':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getMinute()));
                    return false;
                case 'P':
                    if (wallTime.getHour() >= 12) {
                        str = this.dateFormatSymbols.getAmPmStrings()[1];
                    } else {
                        str = this.dateFormatSymbols.getAmPmStrings()[0];
                    }
                    modifyAndAppend(str, -1);
                    return false;
                case 'R':
                    formatInternal(android.text.format.DateUtils.HOUR_MINUTE_24, wallTime, zoneInfoData);
                    return false;
                case 'S':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getSecond()));
                    return false;
                case 'T':
                    formatInternal("%H:%M:%S", wallTime, zoneInfoData);
                    return false;
                case 'U':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(((wallTime.getYearDay() + 7) - wallTime.getWeekDay()) / 7));
                    return false;
                case 'W':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(((wallTime.getYearDay() + 7) - (wallTime.getWeekDay() != 0 ? wallTime.getWeekDay() - 1 : 6)) / 7));
                    return false;
                case 'X':
                    formatInternal(this.timeOnlyFormat, wallTime, zoneInfoData);
                    return false;
                case 'Y':
                    outputYear(wallTime.getYear(), true, true, c);
                    return false;
                case 'Z':
                    if (wallTime.getIsDst() < 0) {
                        return false;
                    }
                    modifyAndAppend(java.util.TimeZone.getTimeZone(zoneInfoData.getID()).getDisplayName(wallTime.getIsDst() != 0, 0), c);
                    return false;
                case 'a':
                    if (wallTime.getWeekDay() >= 0 && wallTime.getWeekDay() < 7) {
                        str3 = this.dateFormatSymbols.getWeekdays(0, 0)[wallTime.getWeekDay() + 1];
                    }
                    modifyAndAppend(str3, c);
                    return false;
                case 'b':
                case 'h':
                    if (wallTime.getMonth() >= 0 && wallTime.getMonth() < 12) {
                        str3 = this.dateFormatSymbols.getMonths(0, 0)[wallTime.getMonth()];
                    }
                    modifyAndAppend(str3, c);
                    return false;
                case 'c':
                    formatInternal(this.dateTimeFormat, wallTime, zoneInfoData);
                    return false;
                case 'd':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getMonthDay()));
                    return false;
                case 'e':
                    this.numberFormatter.format(getFormat(c, "%2d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getMonthDay()));
                    return false;
                case 'j':
                    this.numberFormatter.format(getFormat(c, "%03d", "%3d", "%d", "%03d"), java.lang.Integer.valueOf(wallTime.getYearDay() + 1));
                    return false;
                case 'k':
                    this.numberFormatter.format(getFormat(c, "%2d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getHour()));
                    return false;
                case 'l':
                    this.numberFormatter.format(getFormat(c, "%2d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getHour() % 12 != 0 ? wallTime.getHour() % 12 : 12));
                    return false;
                case 'm':
                    this.numberFormatter.format(getFormat(c, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(wallTime.getMonth() + 1));
                    return false;
                case 'n':
                    this.outputBuilder.append('\n');
                    return false;
                case 'p':
                    if (wallTime.getHour() >= 12) {
                        str2 = this.dateFormatSymbols.getAmPmStrings()[1];
                    } else {
                        str2 = this.dateFormatSymbols.getAmPmStrings()[0];
                    }
                    modifyAndAppend(str2, c);
                    return false;
                case 'r':
                    formatInternal("%I:%M:%S %p", wallTime, zoneInfoData);
                    return false;
                case 's':
                    this.outputBuilder.append(java.lang.Integer.toString(wallTime.mktime(zoneInfoData)));
                    return false;
                case 't':
                    this.outputBuilder.append('\t');
                    return false;
                case 'u':
                    this.numberFormatter.format("%d", java.lang.Integer.valueOf(wallTime.getWeekDay() != 0 ? wallTime.getWeekDay() : 7));
                    return false;
                case 'v':
                    formatInternal("%e-%b-%Y", wallTime, zoneInfoData);
                    return false;
                case 'w':
                    this.numberFormatter.format("%d", java.lang.Integer.valueOf(wallTime.getWeekDay()));
                    return false;
                case 'x':
                    formatInternal(this.dateOnlyFormat, wallTime, zoneInfoData);
                    return false;
                case 'y':
                    outputYear(wallTime.getYear(), false, true, c);
                    return false;
                case 'z':
                    if (wallTime.getIsDst() < 0) {
                        return false;
                    }
                    int gmtOffset = wallTime.getGmtOffset();
                    if (gmtOffset < 0) {
                        gmtOffset = -gmtOffset;
                    } else {
                        c3 = '+';
                    }
                    this.outputBuilder.append(c3);
                    int i5 = gmtOffset / 60;
                    this.numberFormatter.format(getFormat(c, "%04d", "%4d", "%d", "%04d"), java.lang.Integer.valueOf(((i5 / 60) * 100) + (i5 % 60)));
                    return false;
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void modifyAndAppend(java.lang.CharSequence charSequence, int i) {
        int i2 = 0;
        switch (i) {
            case -1:
                while (i2 < charSequence.length()) {
                    this.outputBuilder.append(brokenToLower(charSequence.charAt(i2)));
                    i2++;
                }
                break;
            case 35:
                while (i2 < charSequence.length()) {
                    char charAt = charSequence.charAt(i2);
                    if (brokenIsUpper(charAt)) {
                        charAt = brokenToLower(charAt);
                    } else if (brokenIsLower(charAt)) {
                        charAt = brokenToUpper(charAt);
                    }
                    this.outputBuilder.append(charAt);
                    i2++;
                }
                break;
            case 94:
                while (i2 < charSequence.length()) {
                    this.outputBuilder.append(brokenToUpper(charSequence.charAt(i2)));
                    i2++;
                }
                break;
            default:
                this.outputBuilder.append(charSequence);
                break;
        }
    }

    private void outputYear(int i, boolean z, boolean z2, int i2) {
        int i3 = i % 100;
        int i4 = (i / 100) + (i3 / 100);
        int i5 = i3 % 100;
        if (i5 < 0 && i4 > 0) {
            i5 += 100;
            i4--;
        } else if (i4 < 0 && i5 > 0) {
            i5 -= 100;
            i4++;
        }
        if (z) {
            if (i4 != 0 || i5 >= 0) {
                this.numberFormatter.format(getFormat(i2, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(i4));
            } else {
                this.outputBuilder.append("-0");
            }
        }
        if (z2) {
            if (i5 < 0) {
                i5 = -i5;
            }
            this.numberFormatter.format(getFormat(i2, "%02d", "%2d", "%d", "%02d"), java.lang.Integer.valueOf(i5));
        }
    }

    private static java.lang.String getFormat(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        switch (i) {
            case 45:
                return str3;
            case 48:
                return str4;
            case 95:
                return str2;
            default:
                return str;
        }
    }

    private static boolean isLeap(int i) {
        return i % 4 == 0 && (i % 100 != 0 || i % 400 == 0);
    }

    private static boolean brokenIsUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private static boolean brokenIsLower(char c) {
        return c >= 'a' && c <= 'z';
    }

    private static char brokenToLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return (char) ((c - android.text.format.DateFormat.CAPITAL_AM_PM) + 97);
        }
        return c;
    }

    private static char brokenToUpper(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) ((c - android.text.format.DateFormat.AM_PM) + 65);
        }
        return c;
    }
}
