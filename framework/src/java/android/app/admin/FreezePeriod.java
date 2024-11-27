package android.app.admin;

/* loaded from: classes.dex */
public class FreezePeriod {
    static final int DAYS_IN_YEAR = 365;
    private static final int SENTINEL_YEAR = 2001;
    private static final java.lang.String TAG = "FreezePeriod";
    private final java.time.MonthDay mEnd;
    private final int mEndDay;
    private final java.time.MonthDay mStart;
    private final int mStartDay;

    public FreezePeriod(java.time.MonthDay monthDay, java.time.MonthDay monthDay2) {
        this.mStart = monthDay;
        this.mStartDay = this.mStart.atYear(2001).getDayOfYear();
        this.mEnd = monthDay2;
        this.mEndDay = this.mEnd.atYear(2001).getDayOfYear();
    }

    public java.time.MonthDay getStart() {
        return this.mStart;
    }

    public java.time.MonthDay getEnd() {
        return this.mEnd;
    }

    private FreezePeriod(int i, int i2) {
        this.mStartDay = i;
        this.mStart = dayOfYearToMonthDay(i);
        this.mEndDay = i2;
        this.mEnd = dayOfYearToMonthDay(i2);
    }

    int getLength() {
        return (getEffectiveEndDay() - this.mStartDay) + 1;
    }

    boolean isWrapped() {
        return this.mEndDay < this.mStartDay;
    }

    int getEffectiveEndDay() {
        if (!isWrapped()) {
            return this.mEndDay;
        }
        return this.mEndDay + 365;
    }

    boolean contains(java.time.LocalDate localDate) {
        int dayOfYearDisregardLeapYear = dayOfYearDisregardLeapYear(localDate);
        return !isWrapped() ? this.mStartDay <= dayOfYearDisregardLeapYear && dayOfYearDisregardLeapYear <= this.mEndDay : this.mStartDay <= dayOfYearDisregardLeapYear || dayOfYearDisregardLeapYear <= this.mEndDay;
    }

    boolean after(java.time.LocalDate localDate) {
        return this.mStartDay > dayOfYearDisregardLeapYear(localDate);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v3, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    android.util.Pair<java.time.LocalDate, java.time.LocalDate> toCurrentOrFutureRealDates(java.time.LocalDate localDate) {
        ?? r0;
        int dayOfYearDisregardLeapYear = dayOfYearDisregardLeapYear(localDate);
        int i = 0;
        if (contains(localDate)) {
            if (this.mStartDay <= dayOfYearDisregardLeapYear) {
                r0 = isWrapped();
            } else {
                i = -1;
                r0 = 0;
            }
        } else if (this.mStartDay > dayOfYearDisregardLeapYear) {
            r0 = isWrapped();
        } else {
            i = 1;
            r0 = 1;
        }
        return new android.util.Pair<>(java.time.LocalDate.ofYearDay(2001, this.mStartDay).withYear(localDate.getYear() + i), java.time.LocalDate.ofYearDay(2001, this.mEndDay).withYear(localDate.getYear() + r0));
    }

    public java.lang.String toString() {
        java.time.format.DateTimeFormatter ofPattern = java.time.format.DateTimeFormatter.ofPattern("MMM dd");
        return java.time.LocalDate.ofYearDay(2001, this.mStartDay).format(ofPattern) + " - " + java.time.LocalDate.ofYearDay(2001, this.mEndDay).format(ofPattern);
    }

    private static java.time.MonthDay dayOfYearToMonthDay(int i) {
        java.time.LocalDate ofYearDay = java.time.LocalDate.ofYearDay(2001, i);
        return java.time.MonthDay.of(ofYearDay.getMonth(), ofYearDay.getDayOfMonth());
    }

    private static int dayOfYearDisregardLeapYear(java.time.LocalDate localDate) {
        return localDate.withYear(2001).getDayOfYear();
    }

    public static int distanceWithoutLeapYear(java.time.LocalDate localDate, java.time.LocalDate localDate2) {
        return (dayOfYearDisregardLeapYear(localDate) - dayOfYearDisregardLeapYear(localDate2)) + ((localDate.getYear() - localDate2.getYear()) * 365);
    }

    static java.util.List<android.app.admin.FreezePeriod> canonicalizePeriods(java.util.List<android.app.admin.FreezePeriod> list) {
        boolean[] zArr = new boolean[365];
        for (android.app.admin.FreezePeriod freezePeriod : list) {
            for (int i = freezePeriod.mStartDay; i <= freezePeriod.getEffectiveEndDay(); i++) {
                zArr[(i - 1) % 365] = true;
            }
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i2 = 0;
        while (i2 < 365) {
            if (!zArr[i2]) {
                i2++;
            } else {
                int i3 = i2 + 1;
                while (i2 < 365 && zArr[i2]) {
                    i2++;
                }
                arrayList.add(new android.app.admin.FreezePeriod(i3, i2));
            }
        }
        int size = arrayList.size() - 1;
        if (size > 0 && ((android.app.admin.FreezePeriod) arrayList.get(size)).mEndDay == 365 && ((android.app.admin.FreezePeriod) arrayList.get(0)).mStartDay == 1) {
            arrayList.set(size, new android.app.admin.FreezePeriod(((android.app.admin.FreezePeriod) arrayList.get(size)).mStartDay, ((android.app.admin.FreezePeriod) arrayList.get(0)).mEndDay));
            arrayList.remove(0);
        }
        return arrayList;
    }

    static void validatePeriods(java.util.List<android.app.admin.FreezePeriod> list) {
        int i;
        java.util.List<android.app.admin.FreezePeriod> canonicalizePeriods = canonicalizePeriods(list);
        if (canonicalizePeriods.size() != list.size()) {
            throw android.app.admin.SystemUpdatePolicy.ValidationFailedException.duplicateOrOverlapPeriods();
        }
        int i2 = 0;
        while (i2 < canonicalizePeriods.size()) {
            android.app.admin.FreezePeriod freezePeriod = canonicalizePeriods.get(i2);
            if (freezePeriod.getLength() > 90) {
                throw android.app.admin.SystemUpdatePolicy.ValidationFailedException.freezePeriodTooLong("Freeze period " + freezePeriod + " is too long: " + freezePeriod.getLength() + " days");
            }
            android.app.admin.FreezePeriod freezePeriod2 = i2 > 0 ? canonicalizePeriods.get(i2 - 1) : canonicalizePeriods.get(canonicalizePeriods.size() - 1);
            if (freezePeriod2 != freezePeriod) {
                if (i2 == 0 && !freezePeriod2.isWrapped()) {
                    i = (freezePeriod.mStartDay + (365 - freezePeriod2.mEndDay)) - 1;
                } else {
                    i = (freezePeriod.mStartDay - freezePeriod2.mEndDay) - 1;
                }
                if (i < 60) {
                    throw android.app.admin.SystemUpdatePolicy.ValidationFailedException.freezePeriodTooClose("Freeze periods " + freezePeriod2 + " and " + freezePeriod + " are too close together: " + i + " days apart");
                }
            }
            i2++;
        }
    }

    static void validateAgainstPreviousFreezePeriod(java.util.List<android.app.admin.FreezePeriod> list, java.time.LocalDate localDate, java.time.LocalDate localDate2, java.time.LocalDate localDate3) {
        if (list.size() == 0 || localDate == null || localDate2 == null) {
            return;
        }
        if (localDate.isAfter(localDate3) || localDate2.isAfter(localDate3)) {
            android.util.Log.w(TAG, "Previous period (" + localDate + "," + localDate2 + ") is after current date " + localDate3);
        }
        java.util.List<android.app.admin.FreezePeriod> canonicalizePeriods = canonicalizePeriods(list);
        android.app.admin.FreezePeriod freezePeriod = canonicalizePeriods.get(0);
        for (android.app.admin.FreezePeriod freezePeriod2 : canonicalizePeriods) {
            if (freezePeriod2.contains(localDate3) || freezePeriod2.mStartDay > dayOfYearDisregardLeapYear(localDate3)) {
                freezePeriod = freezePeriod2;
                break;
            }
        }
        android.util.Pair<java.time.LocalDate, java.time.LocalDate> currentOrFutureRealDates = freezePeriod.toCurrentOrFutureRealDates(localDate3);
        if (localDate3.isAfter(currentOrFutureRealDates.first)) {
            currentOrFutureRealDates = new android.util.Pair<>(localDate3, currentOrFutureRealDates.second);
        }
        if (currentOrFutureRealDates.first.isAfter(currentOrFutureRealDates.second)) {
            throw new java.lang.IllegalStateException("Current freeze dates inverted: " + currentOrFutureRealDates.first + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + currentOrFutureRealDates.second);
        }
        java.lang.String str = "Prev: " + localDate + "," + localDate2 + "; cur: " + currentOrFutureRealDates.first + "," + currentOrFutureRealDates.second;
        long distanceWithoutLeapYear = distanceWithoutLeapYear(currentOrFutureRealDates.first, localDate2) - 1;
        if (distanceWithoutLeapYear > 0) {
            if (distanceWithoutLeapYear < 60) {
                throw android.app.admin.SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooClose("Previous freeze period too close to new period: " + distanceWithoutLeapYear + ", " + str);
            }
        } else {
            long distanceWithoutLeapYear2 = distanceWithoutLeapYear(currentOrFutureRealDates.second, localDate) + 1;
            if (distanceWithoutLeapYear2 > 90) {
                throw android.app.admin.SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooLong("Combined freeze period exceeds maximum days: " + distanceWithoutLeapYear2 + ", " + str);
            }
        }
    }
}
