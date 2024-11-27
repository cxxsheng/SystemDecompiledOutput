package android.text.format;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Time {
    public static final int EPOCH_JULIAN_DAY = 2440588;
    public static final int FRIDAY = 5;
    public static final int HOUR = 3;
    public static final int MINUTE = 2;
    public static final int MONDAY = 1;
    public static final int MONDAY_BEFORE_JULIAN_EPOCH = 2440585;
    public static final int MONTH = 5;
    public static final int MONTH_DAY = 4;
    public static final int SATURDAY = 6;
    public static final int SECOND = 1;
    public static final int SUNDAY = 0;
    public static final int THURSDAY = 4;
    public static final java.lang.String TIMEZONE_UTC = "UTC";
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int WEEK_DAY = 7;
    public static final int WEEK_NUM = 9;
    public static final int YEAR = 6;
    public static final int YEAR_DAY = 8;
    private static final java.lang.String Y_M_D = "%Y-%m-%d";
    private static final java.lang.String Y_M_D_T_H_M_S_000 = "%Y-%m-%dT%H:%M:%S.000";
    private static final java.lang.String Y_M_D_T_H_M_S_000_Z = "%Y-%m-%dT%H:%M:%S.000Z";
    public boolean allDay;
    private android.text.format.Time.TimeCalculator calculator;
    public long gmtoff;
    public int hour;
    public int isDst;
    public int minute;
    public int month;
    public int monthDay;
    public int second;
    public java.lang.String timezone;
    public int weekDay;
    public int year;
    public int yearDay;
    private static final int[] DAYS_PER_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int[] sThursdayOffset = {-3, 3, 2, 1, 0, -1, -2};

    public Time(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("timezoneId is null!");
        }
        initialize(str);
    }

    public Time() {
        initialize(java.util.TimeZone.getDefault().getID());
    }

    public Time(android.text.format.Time time) {
        initialize(time.timezone);
        set(time);
    }

    private void initialize(java.lang.String str) {
        this.timezone = str;
        this.year = android.app.settings.SettingsEnums.ACTION_MOBILE_NETWORK_DB_GET_UICC_INFO;
        this.monthDay = 1;
        this.isDst = -1;
        this.calculator = new android.text.format.Time.TimeCalculator(str);
    }

    public long normalize(boolean z) {
        this.calculator.copyFieldsFromTime(this);
        long millis = this.calculator.toMillis(z);
        this.calculator.copyFieldsToTime(this);
        return millis;
    }

    public void switchTimezone(java.lang.String str) {
        this.calculator.copyFieldsFromTime(this);
        this.calculator.switchTimeZone(str);
        this.calculator.copyFieldsToTime(this);
        this.timezone = str;
    }

    public int getActualMaximum(int i) {
        switch (i) {
            case 1:
                return 59;
            case 2:
                return 59;
            case 3:
                return 23;
            case 4:
                int i2 = DAYS_PER_MONTH[this.month];
                if (i2 != 28) {
                    return i2;
                }
                int i3 = this.year;
                if (i3 % 4 == 0) {
                    return (i3 % 100 != 0 || i3 % 400 == 0) ? 29 : 28;
                }
                return 28;
            case 5:
                return 11;
            case 6:
                return 2037;
            case 7:
                return 6;
            case 8:
                int i4 = this.year;
                return (i4 % 4 != 0 || (i4 % 100 == 0 && i4 % 400 != 0)) ? 364 : 365;
            case 9:
                throw new java.lang.RuntimeException("WEEK_NUM not implemented");
            default:
                throw new java.lang.RuntimeException("bad field=" + i);
        }
    }

    public void clear(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("timezone is null!");
        }
        this.timezone = str;
        this.allDay = false;
        this.second = 0;
        this.minute = 0;
        this.hour = 0;
        this.monthDay = 0;
        this.month = 0;
        this.year = 0;
        this.weekDay = 0;
        this.yearDay = 0;
        this.gmtoff = 0L;
        this.isDst = -1;
    }

    public static int compare(android.text.format.Time time, android.text.format.Time time2) {
        if (time == null) {
            throw new java.lang.NullPointerException("a == null");
        }
        if (time2 == null) {
            throw new java.lang.NullPointerException("b == null");
        }
        time.calculator.copyFieldsFromTime(time);
        time2.calculator.copyFieldsFromTime(time2);
        return android.text.format.Time.TimeCalculator.compare(time.calculator, time2.calculator);
    }

    public java.lang.String format(java.lang.String str) {
        this.calculator.copyFieldsFromTime(this);
        return this.calculator.format(str);
    }

    public java.lang.String toString() {
        android.text.format.Time.TimeCalculator timeCalculator = new android.text.format.Time.TimeCalculator(this.timezone);
        timeCalculator.copyFieldsFromTime(this);
        return timeCalculator.toStringInternal();
    }

    public boolean parse(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("time string is null");
        }
        if (parseInternal(str)) {
            this.timezone = TIMEZONE_UTC;
            return true;
        }
        return false;
    }

    private boolean parseInternal(java.lang.String str) {
        int length = str.length();
        if (length < 8) {
            throw new android.util.TimeFormatException("String is too short: \"" + str + "\" Expected at least 8 characters.");
        }
        boolean z = true;
        this.year = getChar(str, 0, 1000) + getChar(str, 1, 100) + getChar(str, 2, 10) + getChar(str, 3, 1);
        this.month = (getChar(str, 4, 10) + getChar(str, 5, 1)) - 1;
        this.monthDay = getChar(str, 6, 10) + getChar(str, 7, 1);
        if (length > 8) {
            if (length < 15) {
                throw new android.util.TimeFormatException("String is too short: \"" + str + "\" If there are more than 8 characters there must be at least 15.");
            }
            checkChar(str, 8, 'T');
            this.allDay = false;
            this.hour = getChar(str, 9, 10) + getChar(str, 10, 1);
            this.minute = getChar(str, 11, 10) + getChar(str, 12, 1);
            this.second = getChar(str, 13, 10) + getChar(str, 14, 1);
            if (length > 15) {
                checkChar(str, 15, 'Z');
                this.weekDay = 0;
                this.yearDay = 0;
                this.isDst = -1;
                this.gmtoff = 0L;
                return z;
            }
        } else {
            this.allDay = true;
            this.hour = 0;
            this.minute = 0;
            this.second = 0;
        }
        z = false;
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
        return z;
    }

    private void checkChar(java.lang.String str, int i, char c) {
        char charAt = str.charAt(i);
        if (charAt != c) {
            throw new android.util.TimeFormatException(java.lang.String.format("Unexpected character 0x%02d at pos=%d.  Expected 0x%02d ('%c').", java.lang.Integer.valueOf(charAt), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(c), java.lang.Character.valueOf(c)));
        }
    }

    private static int getChar(java.lang.String str, int i, int i2) {
        char charAt = str.charAt(i);
        if (java.lang.Character.isDigit(charAt)) {
            return java.lang.Character.getNumericValue(charAt) * i2;
        }
        throw new android.util.TimeFormatException("Parse error at pos=" + i);
    }

    public boolean parse3339(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("time string is null");
        }
        if (parse3339Internal(str)) {
            this.timezone = TIMEZONE_UTC;
            return true;
        }
        return false;
    }

    private boolean parse3339Internal(java.lang.String str) {
        int i;
        int length = str.length();
        if (length < 10) {
            throw new android.util.TimeFormatException("String too short --- expected at least 10 characters.");
        }
        boolean z = true;
        this.year = getChar(str, 0, 1000) + getChar(str, 1, 100) + getChar(str, 2, 10) + getChar(str, 3, 1);
        checkChar(str, 4, '-');
        this.month = (getChar(str, 5, 10) + getChar(str, 6, 1)) - 1;
        checkChar(str, 7, '-');
        this.monthDay = getChar(str, 8, 10) + getChar(str, 9, 1);
        int i2 = 19;
        if (length >= 19) {
            checkChar(str, 10, 'T');
            this.allDay = false;
            int i3 = getChar(str, 11, 10) + getChar(str, 12, 1);
            checkChar(str, 13, com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            int i4 = getChar(str, 14, 10) + getChar(str, 15, 1);
            checkChar(str, 16, com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            this.second = getChar(str, 17, 10) + getChar(str, 18, 1);
            if (19 < length && str.charAt(19) == '.') {
                do {
                    i2++;
                    if (i2 >= length) {
                        break;
                    }
                } while (java.lang.Character.isDigit(str.charAt(i2)));
            }
            if (length <= i2) {
                z = false;
                i = 0;
            } else {
                char charAt = str.charAt(i2);
                switch (charAt) {
                    case '+':
                        i = -1;
                        break;
                    case '-':
                        i = 1;
                        break;
                    case 'Z':
                        i = 0;
                        break;
                    default:
                        throw new android.util.TimeFormatException(java.lang.String.format("Unexpected character 0x%02d at position %d.  Expected + or -", java.lang.Integer.valueOf(charAt), java.lang.Integer.valueOf(i2)));
                }
                if (i != 0) {
                    int i5 = i2 + 6;
                    if (length < i5) {
                        throw new android.util.TimeFormatException(java.lang.String.format("Unexpected length; should be %d characters", java.lang.Integer.valueOf(i5)));
                    }
                    i3 += (getChar(str, i2 + 1, 10) + getChar(str, i2 + 2, 1)) * i;
                    i4 += (getChar(str, i2 + 4, 10) + getChar(str, i2 + 5, 1)) * i;
                }
            }
            this.hour = i3;
            this.minute = i4;
            if (i != 0) {
                normalize(false);
            }
        } else {
            this.allDay = true;
            this.hour = 0;
            this.minute = 0;
            this.second = 0;
            z = false;
        }
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
        return z;
    }

    public static java.lang.String getCurrentTimezone() {
        return java.util.TimeZone.getDefault().getID();
    }

    public void setToNow() {
        set(java.lang.System.currentTimeMillis());
    }

    public long toMillis(boolean z) {
        this.calculator.copyFieldsFromTime(this);
        return this.calculator.toMillis(z);
    }

    public void set(long j) {
        this.allDay = false;
        this.calculator.timezone = this.timezone;
        this.calculator.setTimeInMillis(j);
        this.calculator.copyFieldsToTime(this);
    }

    public java.lang.String format2445() {
        this.calculator.copyFieldsFromTime(this);
        return this.calculator.format2445(!this.allDay);
    }

    public void set(android.text.format.Time time) {
        this.timezone = time.timezone;
        this.allDay = time.allDay;
        this.second = time.second;
        this.minute = time.minute;
        this.hour = time.hour;
        this.monthDay = time.monthDay;
        this.month = time.month;
        this.year = time.year;
        this.weekDay = time.weekDay;
        this.yearDay = time.yearDay;
        this.isDst = time.isDst;
        this.gmtoff = time.gmtoff;
    }

    public void set(int i, int i2, int i3, int i4, int i5, int i6) {
        this.allDay = false;
        this.second = i;
        this.minute = i2;
        this.hour = i3;
        this.monthDay = i4;
        this.month = i5;
        this.year = i6;
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
    }

    public void set(int i, int i2, int i3) {
        this.allDay = true;
        this.second = 0;
        this.minute = 0;
        this.hour = 0;
        this.monthDay = i;
        this.month = i2;
        this.year = i3;
        this.weekDay = 0;
        this.yearDay = 0;
        this.isDst = -1;
        this.gmtoff = 0L;
    }

    public boolean before(android.text.format.Time time) {
        return compare(this, time) < 0;
    }

    public boolean after(android.text.format.Time time) {
        return compare(this, time) > 0;
    }

    public int getWeekNumber() {
        int i = this.yearDay + sThursdayOffset[this.weekDay];
        if (i >= 0 && i <= 364) {
            return (i / 7) + 1;
        }
        android.text.format.Time time = new android.text.format.Time(this);
        time.monthDay += sThursdayOffset[this.weekDay];
        time.normalize(true);
        return (time.yearDay / 7) + 1;
    }

    public java.lang.String format3339(boolean z) {
        if (z) {
            return format(Y_M_D);
        }
        if (TIMEZONE_UTC.equals(this.timezone)) {
            return format(Y_M_D_T_H_M_S_000_Z);
        }
        java.lang.String format = format(Y_M_D_T_H_M_S_000);
        java.lang.String str = this.gmtoff < 0 ? com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE : "+";
        int abs = (int) java.lang.Math.abs(this.gmtoff);
        return java.lang.String.format(java.util.Locale.US, "%s%s%02d:%02d", format, str, java.lang.Integer.valueOf(abs / 3600), java.lang.Integer.valueOf((abs % 3600) / 60));
    }

    public static boolean isEpoch(android.text.format.Time time) {
        return getJulianDay(time.toMillis(true), 0L) == 2440588;
    }

    @java.lang.Deprecated
    public static int getJulianDay(long j, long j2) {
        long j3 = j + (j2 * 1000);
        long j4 = j3 / 86400000;
        if (j3 < 0 && j3 % 86400000 != 0) {
            j4--;
        }
        return (int) (j4 + 2440588);
    }

    public long setJulianDay(int i) {
        long j = (i - EPOCH_JULIAN_DAY) * 86400000;
        set(j);
        this.monthDay += i - getJulianDay(j, this.gmtoff);
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        return normalize(true);
    }

    public static int getWeeksSinceEpochFromJulianDay(int i, int i2) {
        int i3 = 4 - i2;
        if (i3 < 0) {
            i3 += 7;
        }
        return (i - (EPOCH_JULIAN_DAY - i3)) / 7;
    }

    public static int getJulianMondayFromWeeksSinceEpoch(int i) {
        return (i * 7) + MONDAY_BEFORE_JULIAN_EPOCH;
    }

    private static class TimeCalculator {
        private com.android.i18n.timezone.ZoneInfoData mZoneInfoData;
        public java.lang.String timezone;
        public final com.android.i18n.timezone.WallTime wallTime = new com.android.i18n.timezone.WallTime();

        public TimeCalculator(java.lang.String str) {
            this.mZoneInfoData = lookupZoneInfoData(str);
        }

        public long toMillis(boolean z) {
            if (z) {
                this.wallTime.setIsDst(-1);
            }
            int mktime = this.wallTime.mktime(this.mZoneInfoData);
            if (mktime == -1) {
                return -1L;
            }
            return mktime * 1000;
        }

        public void setTimeInMillis(long j) {
            updateZoneInfoFromTimeZone();
            this.wallTime.localtime((int) (j / 1000), this.mZoneInfoData);
        }

        public java.lang.String format(java.lang.String str) {
            if (str == null) {
                str = "%c";
            }
            return new android.text.format.TimeFormatter().format(str, this.wallTime, this.mZoneInfoData);
        }

        private void updateZoneInfoFromTimeZone() {
            if (!this.mZoneInfoData.getID().equals(this.timezone)) {
                this.mZoneInfoData = lookupZoneInfoData(this.timezone);
            }
        }

        private static com.android.i18n.timezone.ZoneInfoData lookupZoneInfoData(java.lang.String str) {
            com.android.i18n.timezone.ZoneInfoData makeZoneInfoData = com.android.i18n.timezone.ZoneInfoDb.getInstance().makeZoneInfoData(str);
            if (makeZoneInfoData == null) {
                makeZoneInfoData = com.android.i18n.timezone.ZoneInfoDb.getInstance().makeZoneInfoData("GMT");
            }
            if (makeZoneInfoData == null) {
                throw new java.lang.AssertionError("GMT not found: \"" + str + "\"");
            }
            return makeZoneInfoData;
        }

        public void switchTimeZone(java.lang.String str) {
            int mktime = this.wallTime.mktime(this.mZoneInfoData);
            this.timezone = str;
            updateZoneInfoFromTimeZone();
            this.wallTime.localtime(mktime, this.mZoneInfoData);
        }

        public java.lang.String format2445(boolean z) {
            char[] cArr = new char[z ? 16 : 8];
            int year = this.wallTime.getYear();
            cArr[0] = toChar(year / 1000);
            int i = year % 1000;
            cArr[1] = toChar(i / 100);
            int i2 = i % 100;
            cArr[2] = toChar(i2 / 10);
            cArr[3] = toChar(i2 % 10);
            int month = this.wallTime.getMonth() + 1;
            cArr[4] = toChar(month / 10);
            cArr[5] = toChar(month % 10);
            int monthDay = this.wallTime.getMonthDay();
            cArr[6] = toChar(monthDay / 10);
            cArr[7] = toChar(monthDay % 10);
            if (!z) {
                return new java.lang.String(cArr, 0, 8);
            }
            cArr[8] = 'T';
            int hour = this.wallTime.getHour();
            cArr[9] = toChar(hour / 10);
            cArr[10] = toChar(hour % 10);
            int minute = this.wallTime.getMinute();
            cArr[11] = toChar(minute / 10);
            cArr[12] = toChar(minute % 10);
            int second = this.wallTime.getSecond();
            cArr[13] = toChar(second / 10);
            cArr[14] = toChar(second % 10);
            if (android.text.format.Time.TIMEZONE_UTC.equals(this.timezone)) {
                cArr[15] = 'Z';
                return new java.lang.String(cArr, 0, 16);
            }
            return new java.lang.String(cArr, 0, 15);
        }

        private char toChar(int i) {
            if (i < 0 || i > 9) {
                return ' ';
            }
            return (char) (i + 48);
        }

        public java.lang.String toStringInternal() {
            return java.lang.String.format("%04d%02d%02dT%02d%02d%02d%s(%d,%d,%d,%d,%d)", java.lang.Integer.valueOf(this.wallTime.getYear()), java.lang.Integer.valueOf(this.wallTime.getMonth() + 1), java.lang.Integer.valueOf(this.wallTime.getMonthDay()), java.lang.Integer.valueOf(this.wallTime.getHour()), java.lang.Integer.valueOf(this.wallTime.getMinute()), java.lang.Integer.valueOf(this.wallTime.getSecond()), this.timezone, java.lang.Integer.valueOf(this.wallTime.getWeekDay()), java.lang.Integer.valueOf(this.wallTime.getYearDay()), java.lang.Integer.valueOf(this.wallTime.getGmtOffset()), java.lang.Integer.valueOf(this.wallTime.getIsDst()), java.lang.Long.valueOf(toMillis(false) / 1000));
        }

        public static int compare(android.text.format.Time.TimeCalculator timeCalculator, android.text.format.Time.TimeCalculator timeCalculator2) {
            if (timeCalculator.timezone.equals(timeCalculator2.timezone)) {
                int year = timeCalculator.wallTime.getYear() - timeCalculator2.wallTime.getYear();
                if (year != 0) {
                    return year;
                }
                int month = timeCalculator.wallTime.getMonth() - timeCalculator2.wallTime.getMonth();
                if (month != 0) {
                    return month;
                }
                int monthDay = timeCalculator.wallTime.getMonthDay() - timeCalculator2.wallTime.getMonthDay();
                if (monthDay != 0) {
                    return monthDay;
                }
                int hour = timeCalculator.wallTime.getHour() - timeCalculator2.wallTime.getHour();
                if (hour != 0) {
                    return hour;
                }
                int minute = timeCalculator.wallTime.getMinute() - timeCalculator2.wallTime.getMinute();
                if (minute != 0) {
                    return minute;
                }
                int second = timeCalculator.wallTime.getSecond() - timeCalculator2.wallTime.getSecond();
                if (second != 0) {
                    return second;
                }
                return 0;
            }
            long millis = timeCalculator.toMillis(false) - timeCalculator2.toMillis(false);
            if (millis < 0) {
                return -1;
            }
            return millis > 0 ? 1 : 0;
        }

        public void copyFieldsToTime(android.text.format.Time time) {
            time.second = this.wallTime.getSecond();
            time.minute = this.wallTime.getMinute();
            time.hour = this.wallTime.getHour();
            time.monthDay = this.wallTime.getMonthDay();
            time.month = this.wallTime.getMonth();
            time.year = this.wallTime.getYear();
            time.weekDay = this.wallTime.getWeekDay();
            time.yearDay = this.wallTime.getYearDay();
            time.isDst = this.wallTime.getIsDst();
            time.gmtoff = this.wallTime.getGmtOffset();
        }

        public void copyFieldsFromTime(android.text.format.Time time) {
            this.wallTime.setSecond(time.second);
            this.wallTime.setMinute(time.minute);
            this.wallTime.setHour(time.hour);
            this.wallTime.setMonthDay(time.monthDay);
            this.wallTime.setMonth(time.month);
            this.wallTime.setYear(time.year);
            this.wallTime.setWeekDay(time.weekDay);
            this.wallTime.setYearDay(time.yearDay);
            this.wallTime.setIsDst(time.isDst);
            this.wallTime.setGmtOffset((int) time.gmtoff);
            if (time.allDay && (time.second != 0 || time.minute != 0 || time.hour != 0)) {
                throw new java.lang.IllegalArgumentException("allDay is true but sec, min, hour are not 0.");
            }
            this.timezone = time.timezone;
            updateZoneInfoFromTimeZone();
        }
    }
}
