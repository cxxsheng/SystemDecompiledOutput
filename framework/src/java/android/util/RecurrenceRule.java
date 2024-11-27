package android.util;

/* loaded from: classes3.dex */
public class RecurrenceRule implements android.os.Parcelable {
    private static final int VERSION_INIT = 0;
    public final java.time.ZonedDateTime end;
    public final java.time.Period period;
    public final java.time.ZonedDateTime start;
    private static final java.lang.String TAG = "RecurrenceRule";
    private static final boolean LOGD = android.util.Log.isLoggable(TAG, 3);
    public static java.time.Clock sClock = java.time.Clock.systemDefaultZone();
    public static final android.os.Parcelable.Creator<android.util.RecurrenceRule> CREATOR = new android.os.Parcelable.Creator<android.util.RecurrenceRule>() { // from class: android.util.RecurrenceRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.RecurrenceRule createFromParcel(android.os.Parcel parcel) {
            return new android.util.RecurrenceRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.util.RecurrenceRule[] newArray(int i) {
            return new android.util.RecurrenceRule[i];
        }
    };

    public RecurrenceRule(java.time.ZonedDateTime zonedDateTime, java.time.ZonedDateTime zonedDateTime2, java.time.Period period) {
        this.start = zonedDateTime;
        this.end = zonedDateTime2;
        this.period = period;
    }

    @java.lang.Deprecated
    public static android.util.RecurrenceRule buildNever() {
        return new android.util.RecurrenceRule(null, null, null);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [java.time.ZonedDateTime] */
    @java.lang.Deprecated
    public static android.util.RecurrenceRule buildRecurringMonthly(int i, java.time.ZoneId zoneId) {
        return new android.util.RecurrenceRule(java.time.ZonedDateTime.of(java.time.ZonedDateTime.now(sClock).withZoneSameInstant(zoneId).toLocalDate().minusYears(1L).withMonth(1).withDayOfMonth(i), java.time.LocalTime.MIDNIGHT, zoneId), null, java.time.Period.ofMonths(1));
    }

    private RecurrenceRule(android.os.Parcel parcel) {
        this.start = convertZonedDateTime(parcel.readString());
        this.end = convertZonedDateTime(parcel.readString());
        this.period = convertPeriod(parcel.readString());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(convertZonedDateTime(this.start));
        parcel.writeString(convertZonedDateTime(this.end));
        parcel.writeString(convertPeriod(this.period));
    }

    public RecurrenceRule(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        int readInt = dataInputStream.readInt();
        switch (readInt) {
            case 0:
                this.start = convertZonedDateTime(android.util.BackupUtils.readString(dataInputStream));
                this.end = convertZonedDateTime(android.util.BackupUtils.readString(dataInputStream));
                this.period = convertPeriod(android.util.BackupUtils.readString(dataInputStream));
                return;
            default:
                throw new java.net.ProtocolException("Unknown version " + readInt);
        }
    }

    public void writeToStream(java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
        dataOutputStream.writeInt(0);
        android.util.BackupUtils.writeString(dataOutputStream, convertZonedDateTime(this.start));
        android.util.BackupUtils.writeString(dataOutputStream, convertZonedDateTime(this.end));
        android.util.BackupUtils.writeString(dataOutputStream, convertPeriod(this.period));
    }

    public java.lang.String toString() {
        return "RecurrenceRule{start=" + this.start + " end=" + this.end + " period=" + this.period + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(this.start, this.end, this.period);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.util.RecurrenceRule)) {
            return false;
        }
        android.util.RecurrenceRule recurrenceRule = (android.util.RecurrenceRule) obj;
        return java.util.Objects.equals(this.start, recurrenceRule.start) && java.util.Objects.equals(this.end, recurrenceRule.end) && java.util.Objects.equals(this.period, recurrenceRule.period);
    }

    public boolean isRecurring() {
        return this.period != null;
    }

    @java.lang.Deprecated
    public boolean isMonthly() {
        return this.start != null && this.period != null && this.period.getYears() == 0 && this.period.getMonths() == 1 && this.period.getDays() == 0;
    }

    public java.util.Iterator<android.util.Range<java.time.ZonedDateTime>> cycleIterator() {
        if (this.period != null) {
            return new android.util.RecurrenceRule.RecurringIterator();
        }
        return new android.util.RecurrenceRule.NonrecurringIterator();
    }

    private class NonrecurringIterator implements java.util.Iterator<android.util.Range<java.time.ZonedDateTime>> {
        boolean hasNext;

        public NonrecurringIterator() {
            this.hasNext = (android.util.RecurrenceRule.this.start == null || android.util.RecurrenceRule.this.end == null) ? false : true;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public android.util.Range<java.time.ZonedDateTime> next() {
            this.hasNext = false;
            return new android.util.Range<>(android.util.RecurrenceRule.this.start, android.util.RecurrenceRule.this.end);
        }
    }

    private class RecurringIterator implements java.util.Iterator<android.util.Range<java.time.ZonedDateTime>> {
        java.time.ZonedDateTime cycleEnd;
        java.time.ZonedDateTime cycleStart;
        int i;

        /* JADX WARN: Multi-variable type inference failed */
        public RecurringIterator() {
            java.time.ZonedDateTime withZoneSameInstant = android.util.RecurrenceRule.this.end != null ? android.util.RecurrenceRule.this.end : java.time.ZonedDateTime.now(android.util.RecurrenceRule.sClock).withZoneSameInstant(android.util.RecurrenceRule.this.start.getZone());
            if (android.util.RecurrenceRule.LOGD) {
                android.util.Log.d(android.util.RecurrenceRule.TAG, "Resolving using anchor " + withZoneSameInstant);
            }
            updateCycle();
            while (withZoneSameInstant.toEpochSecond() > this.cycleEnd.toEpochSecond()) {
                this.i++;
                updateCycle();
            }
            while (withZoneSameInstant.toEpochSecond() <= this.cycleStart.toEpochSecond()) {
                this.i--;
                updateCycle();
            }
        }

        private void updateCycle() {
            this.cycleStart = roundBoundaryTime(android.util.RecurrenceRule.this.start.plus((java.time.temporal.TemporalAmount) android.util.RecurrenceRule.this.period.multipliedBy(this.i)));
            this.cycleEnd = roundBoundaryTime(android.util.RecurrenceRule.this.start.plus((java.time.temporal.TemporalAmount) android.util.RecurrenceRule.this.period.multipliedBy(this.i + 1)));
        }

        private java.time.ZonedDateTime roundBoundaryTime(java.time.ZonedDateTime zonedDateTime) {
            if (android.util.RecurrenceRule.this.isMonthly() && zonedDateTime.getDayOfMonth() < android.util.RecurrenceRule.this.start.getDayOfMonth()) {
                return java.time.ZonedDateTime.of(zonedDateTime.toLocalDate(), java.time.LocalTime.MAX, android.util.RecurrenceRule.this.start.getZone());
            }
            return zonedDateTime;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.cycleStart.toEpochSecond() >= android.util.RecurrenceRule.this.start.toEpochSecond();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Iterator
        public android.util.Range<java.time.ZonedDateTime> next() {
            if (android.util.RecurrenceRule.LOGD) {
                android.util.Log.d(android.util.RecurrenceRule.TAG, "Cycle " + this.i + " from " + this.cycleStart + " to " + this.cycleEnd);
            }
            android.util.Range<java.time.ZonedDateTime> range = new android.util.Range<>(this.cycleStart, this.cycleEnd);
            this.i--;
            updateCycle();
            return range;
        }
    }

    public static java.lang.String convertZonedDateTime(java.time.ZonedDateTime zonedDateTime) {
        if (zonedDateTime != null) {
            return zonedDateTime.toString();
        }
        return null;
    }

    public static java.time.ZonedDateTime convertZonedDateTime(java.lang.String str) {
        if (str != null) {
            return java.time.ZonedDateTime.parse(str);
        }
        return null;
    }

    public static java.lang.String convertPeriod(java.time.Period period) {
        if (period != null) {
            return period.toString();
        }
        return null;
    }

    public static java.time.Period convertPeriod(java.lang.String str) {
        if (str != null) {
            return java.time.Period.parse(str);
        }
        return null;
    }
}
