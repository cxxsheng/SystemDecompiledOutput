package android.app.admin;

/* loaded from: classes.dex */
public final class SystemUpdatePolicy implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.SystemUpdatePolicy> CREATOR = new android.os.Parcelable.Creator<android.app.admin.SystemUpdatePolicy>() { // from class: android.app.admin.SystemUpdatePolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.SystemUpdatePolicy createFromParcel(android.os.Parcel parcel) {
            android.app.admin.SystemUpdatePolicy systemUpdatePolicy = new android.app.admin.SystemUpdatePolicy();
            systemUpdatePolicy.mPolicyType = parcel.readInt();
            systemUpdatePolicy.mMaintenanceWindowStart = parcel.readInt();
            systemUpdatePolicy.mMaintenanceWindowEnd = parcel.readInt();
            int readInt = parcel.readInt();
            systemUpdatePolicy.mFreezePeriods.ensureCapacity(readInt);
            for (int i = 0; i < readInt; i++) {
                systemUpdatePolicy.mFreezePeriods.add(new android.app.admin.FreezePeriod(java.time.MonthDay.of(parcel.readInt(), parcel.readInt()), java.time.MonthDay.of(parcel.readInt(), parcel.readInt())));
            }
            return systemUpdatePolicy;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.SystemUpdatePolicy[] newArray(int i) {
            return new android.app.admin.SystemUpdatePolicy[i];
        }
    };
    static final int FREEZE_PERIOD_MAX_LENGTH = 90;
    static final int FREEZE_PERIOD_MIN_SEPARATION = 60;
    private static final java.lang.String KEY_FREEZE_END = "end";
    private static final java.lang.String KEY_FREEZE_START = "start";
    private static final java.lang.String KEY_FREEZE_TAG = "freeze";
    private static final java.lang.String KEY_INSTALL_WINDOW_END = "install_window_end";
    private static final java.lang.String KEY_INSTALL_WINDOW_START = "install_window_start";
    private static final java.lang.String KEY_POLICY_TYPE = "policy_type";
    private static final java.lang.String TAG = "SystemUpdatePolicy";
    public static final int TYPE_INSTALL_AUTOMATIC = 1;
    public static final int TYPE_INSTALL_WINDOWED = 2;

    @android.annotation.SystemApi
    public static final int TYPE_PAUSE = 4;
    public static final int TYPE_POSTPONE = 3;
    private static final int TYPE_UNKNOWN = -1;
    private static final int WINDOW_BOUNDARY = 1440;
    private final java.util.ArrayList<android.app.admin.FreezePeriod> mFreezePeriods;
    private int mMaintenanceWindowEnd;
    private int mMaintenanceWindowStart;
    private int mPolicyType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface SystemUpdatePolicyType {
    }

    public static final class ValidationFailedException extends java.lang.IllegalArgumentException implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.admin.SystemUpdatePolicy.ValidationFailedException> CREATOR = new android.os.Parcelable.Creator<android.app.admin.SystemUpdatePolicy.ValidationFailedException>() { // from class: android.app.admin.SystemUpdatePolicy.ValidationFailedException.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.admin.SystemUpdatePolicy.ValidationFailedException createFromParcel(android.os.Parcel parcel) {
                return new android.app.admin.SystemUpdatePolicy.ValidationFailedException(parcel.readInt(), parcel.readString());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.admin.SystemUpdatePolicy.ValidationFailedException[] newArray(int i) {
                return new android.app.admin.SystemUpdatePolicy.ValidationFailedException[i];
            }
        };
        public static final int ERROR_COMBINED_FREEZE_PERIOD_TOO_CLOSE = 6;
        public static final int ERROR_COMBINED_FREEZE_PERIOD_TOO_LONG = 5;
        public static final int ERROR_DUPLICATE_OR_OVERLAP = 2;
        public static final int ERROR_NEW_FREEZE_PERIOD_TOO_CLOSE = 4;
        public static final int ERROR_NEW_FREEZE_PERIOD_TOO_LONG = 3;
        public static final int ERROR_NONE = 0;
        public static final int ERROR_UNKNOWN = 1;
        private final int mErrorCode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface ValidationFailureType {
        }

        private ValidationFailedException(int i, java.lang.String str) {
            super(str);
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public static android.app.admin.SystemUpdatePolicy.ValidationFailedException duplicateOrOverlapPeriods() {
            return new android.app.admin.SystemUpdatePolicy.ValidationFailedException(2, "Found duplicate or overlapping periods");
        }

        public static android.app.admin.SystemUpdatePolicy.ValidationFailedException freezePeriodTooLong(java.lang.String str) {
            return new android.app.admin.SystemUpdatePolicy.ValidationFailedException(3, str);
        }

        public static android.app.admin.SystemUpdatePolicy.ValidationFailedException freezePeriodTooClose(java.lang.String str) {
            return new android.app.admin.SystemUpdatePolicy.ValidationFailedException(4, str);
        }

        public static android.app.admin.SystemUpdatePolicy.ValidationFailedException combinedPeriodTooLong(java.lang.String str) {
            return new android.app.admin.SystemUpdatePolicy.ValidationFailedException(5, str);
        }

        public static android.app.admin.SystemUpdatePolicy.ValidationFailedException combinedPeriodTooClose(java.lang.String str) {
            return new android.app.admin.SystemUpdatePolicy.ValidationFailedException(6, str);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mErrorCode);
            parcel.writeString(getMessage());
        }
    }

    private SystemUpdatePolicy() {
        this.mPolicyType = -1;
        this.mFreezePeriods = new java.util.ArrayList<>();
    }

    public static android.app.admin.SystemUpdatePolicy createAutomaticInstallPolicy() {
        android.app.admin.SystemUpdatePolicy systemUpdatePolicy = new android.app.admin.SystemUpdatePolicy();
        systemUpdatePolicy.mPolicyType = 1;
        return systemUpdatePolicy;
    }

    public static android.app.admin.SystemUpdatePolicy createWindowedInstallPolicy(int i, int i2) {
        if (i < 0 || i >= 1440 || i2 < 0 || i2 >= 1440) {
            throw new java.lang.IllegalArgumentException("startTime and endTime must be inside [0, 1440)");
        }
        android.app.admin.SystemUpdatePolicy systemUpdatePolicy = new android.app.admin.SystemUpdatePolicy();
        systemUpdatePolicy.mPolicyType = 2;
        systemUpdatePolicy.mMaintenanceWindowStart = i;
        systemUpdatePolicy.mMaintenanceWindowEnd = i2;
        return systemUpdatePolicy;
    }

    public static android.app.admin.SystemUpdatePolicy createPostponeInstallPolicy() {
        android.app.admin.SystemUpdatePolicy systemUpdatePolicy = new android.app.admin.SystemUpdatePolicy();
        systemUpdatePolicy.mPolicyType = 3;
        return systemUpdatePolicy;
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    public int getInstallWindowStart() {
        if (this.mPolicyType == 2) {
            return this.mMaintenanceWindowStart;
        }
        return -1;
    }

    public int getInstallWindowEnd() {
        if (this.mPolicyType == 2) {
            return this.mMaintenanceWindowEnd;
        }
        return -1;
    }

    public boolean isValid() {
        try {
            validateType();
            validateFreezePeriods();
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    public void validateType() {
        if (this.mPolicyType == 1 || this.mPolicyType == 3) {
            return;
        }
        if (this.mPolicyType == 2) {
            if (this.mMaintenanceWindowStart < 0 || this.mMaintenanceWindowStart >= 1440 || this.mMaintenanceWindowEnd < 0 || this.mMaintenanceWindowEnd >= 1440) {
                throw new java.lang.IllegalArgumentException("Invalid maintenance window");
            }
            return;
        }
        throw new java.lang.IllegalArgumentException("Invalid system update policy type.");
    }

    public android.app.admin.SystemUpdatePolicy setFreezePeriods(java.util.List<android.app.admin.FreezePeriod> list) {
        android.app.admin.FreezePeriod.validatePeriods(list);
        this.mFreezePeriods.clear();
        this.mFreezePeriods.addAll(list);
        return this;
    }

    public java.util.List<android.app.admin.FreezePeriod> getFreezePeriods() {
        return java.util.Collections.unmodifiableList(this.mFreezePeriods);
    }

    public android.util.Pair<java.time.LocalDate, java.time.LocalDate> getCurrentFreezePeriod(java.time.LocalDate localDate) {
        java.util.Iterator<android.app.admin.FreezePeriod> it = this.mFreezePeriods.iterator();
        while (it.hasNext()) {
            android.app.admin.FreezePeriod next = it.next();
            if (next.contains(localDate)) {
                return next.toCurrentOrFutureRealDates(localDate);
            }
        }
        return null;
    }

    private long timeUntilNextFreezePeriod(long j) {
        java.time.LocalDate localDate;
        android.app.admin.FreezePeriod next;
        java.util.List<android.app.admin.FreezePeriod> canonicalizePeriods = android.app.admin.FreezePeriod.canonicalizePeriods(this.mFreezePeriods);
        java.time.LocalDate millisToDate = millisToDate(j);
        java.util.Iterator<android.app.admin.FreezePeriod> it = canonicalizePeriods.iterator();
        do {
            if (!it.hasNext()) {
                localDate = null;
            } else {
                next = it.next();
                if (next.after(millisToDate)) {
                    localDate = next.toCurrentOrFutureRealDates(millisToDate).first;
                }
            }
            if (localDate == null) {
                localDate = canonicalizePeriods.get(0).toCurrentOrFutureRealDates(millisToDate).first;
            }
            return dateToMillis(localDate) - j;
        } while (!next.contains(millisToDate));
        throw new java.lang.IllegalArgumentException("Given date is inside a freeze period");
    }

    public void validateFreezePeriods() {
        android.app.admin.FreezePeriod.validatePeriods(this.mFreezePeriods);
    }

    public void validateAgainstPreviousFreezePeriod(java.time.LocalDate localDate, java.time.LocalDate localDate2, java.time.LocalDate localDate3) {
        android.app.admin.FreezePeriod.validateAgainstPreviousFreezePeriod(this.mFreezePeriods, localDate, localDate2, localDate3);
    }

    @android.annotation.SystemApi
    public static class InstallationOption {
        private long mEffectiveTime;
        private final int mType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface InstallationOptionType {
        }

        InstallationOption(int i, long j) {
            this.mType = i;
            this.mEffectiveTime = j;
        }

        public int getType() {
            return this.mType;
        }

        public long getEffectiveTime() {
            return this.mEffectiveTime;
        }

        protected void limitEffectiveTime(long j) {
            this.mEffectiveTime = java.lang.Long.min(this.mEffectiveTime, j);
        }
    }

    @android.annotation.SystemApi
    public android.app.admin.SystemUpdatePolicy.InstallationOption getInstallationOptionAt(long j) {
        android.util.Pair<java.time.LocalDate, java.time.LocalDate> currentFreezePeriod = getCurrentFreezePeriod(millisToDate(j));
        if (currentFreezePeriod != null) {
            return new android.app.admin.SystemUpdatePolicy.InstallationOption(4, dateToMillis(roundUpLeapDay(currentFreezePeriod.second).plusDays(1L)) - j);
        }
        android.app.admin.SystemUpdatePolicy.InstallationOption installationOptionRegardlessFreezeAt = getInstallationOptionRegardlessFreezeAt(j);
        if (this.mFreezePeriods.size() > 0) {
            installationOptionRegardlessFreezeAt.limitEffectiveTime(timeUntilNextFreezePeriod(j));
        }
        return installationOptionRegardlessFreezeAt;
    }

    private android.app.admin.SystemUpdatePolicy.InstallationOption getInstallationOptionRegardlessFreezeAt(long j) {
        if (this.mPolicyType == 1 || this.mPolicyType == 3) {
            return new android.app.admin.SystemUpdatePolicy.InstallationOption(this.mPolicyType, Long.MAX_VALUE);
        }
        if (this.mPolicyType == 2) {
            java.util.Calendar.getInstance().setTimeInMillis(j);
            long millis = java.util.concurrent.TimeUnit.HOURS.toMillis(r0.get(11)) + java.util.concurrent.TimeUnit.MINUTES.toMillis(r0.get(12)) + java.util.concurrent.TimeUnit.SECONDS.toMillis(r0.get(13)) + r0.get(14);
            long millis2 = java.util.concurrent.TimeUnit.MINUTES.toMillis(this.mMaintenanceWindowStart);
            long millis3 = java.util.concurrent.TimeUnit.MINUTES.toMillis(this.mMaintenanceWindowEnd);
            long millis4 = java.util.concurrent.TimeUnit.DAYS.toMillis(1L);
            if ((millis2 <= millis && millis <= millis3) || (millis2 > millis3 && (millis2 <= millis || millis <= millis3))) {
                return new android.app.admin.SystemUpdatePolicy.InstallationOption(1, ((millis3 - millis) + millis4) % millis4);
            }
            return new android.app.admin.SystemUpdatePolicy.InstallationOption(4, ((millis2 - millis) + millis4) % millis4);
        }
        throw new java.lang.RuntimeException("Unknown policy type");
    }

    private static java.time.LocalDate roundUpLeapDay(java.time.LocalDate localDate) {
        if (localDate.isLeapYear() && localDate.getMonthValue() == 2 && localDate.getDayOfMonth() == 28) {
            return localDate.plusDays(1L);
        }
        return localDate;
    }

    private static java.time.LocalDate millisToDate(long j) {
        return java.time.Instant.ofEpochMilli(j).atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [java.time.ZonedDateTime] */
    private static long dateToMillis(java.time.LocalDate localDate) {
        return java.time.LocalDateTime.of(localDate, java.time.LocalTime.MIN).atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public java.lang.String toString() {
        return java.lang.String.format("SystemUpdatePolicy (type: %d, windowStart: %d, windowEnd: %d, freezes: [%s])", java.lang.Integer.valueOf(this.mPolicyType), java.lang.Integer.valueOf(this.mMaintenanceWindowStart), java.lang.Integer.valueOf(this.mMaintenanceWindowEnd), this.mFreezePeriods.stream().map(new java.util.function.Function() { // from class: android.app.admin.SystemUpdatePolicy$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String freezePeriod;
                freezePeriod = ((android.app.admin.FreezePeriod) obj).toString();
                return freezePeriod;
            }
        }).collect(java.util.stream.Collectors.joining(",")));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPolicyType);
        parcel.writeInt(this.mMaintenanceWindowStart);
        parcel.writeInt(this.mMaintenanceWindowEnd);
        int size = this.mFreezePeriods.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            android.app.admin.FreezePeriod freezePeriod = this.mFreezePeriods.get(i2);
            parcel.writeInt(freezePeriod.getStart().getMonthValue());
            parcel.writeInt(freezePeriod.getStart().getDayOfMonth());
            parcel.writeInt(freezePeriod.getEnd().getMonthValue());
            parcel.writeInt(freezePeriod.getEnd().getDayOfMonth());
        }
    }

    public static android.app.admin.SystemUpdatePolicy restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        try {
            android.app.admin.SystemUpdatePolicy systemUpdatePolicy = new android.app.admin.SystemUpdatePolicy();
            systemUpdatePolicy.mPolicyType = typedXmlPullParser.getAttributeInt(null, KEY_POLICY_TYPE, -1);
            systemUpdatePolicy.mMaintenanceWindowStart = typedXmlPullParser.getAttributeInt(null, KEY_INSTALL_WINDOW_START, 0);
            systemUpdatePolicy.mMaintenanceWindowEnd = typedXmlPullParser.getAttributeInt(null, KEY_INSTALL_WINDOW_END, 0);
            int depth = typedXmlPullParser.getDepth();
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4 && typedXmlPullParser.getName().equals("freeze")) {
                    systemUpdatePolicy.mFreezePeriods.add(new android.app.admin.FreezePeriod(java.time.MonthDay.parse(typedXmlPullParser.getAttributeValue(null, "start")), java.time.MonthDay.parse(typedXmlPullParser.getAttributeValue(null, "end"))));
                }
            }
            return systemUpdatePolicy;
        } catch (java.io.IOException | java.lang.NumberFormatException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.w(TAG, "Load xml failed", e);
            return null;
        }
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attributeInt(null, KEY_POLICY_TYPE, this.mPolicyType);
        typedXmlSerializer.attributeInt(null, KEY_INSTALL_WINDOW_START, this.mMaintenanceWindowStart);
        typedXmlSerializer.attributeInt(null, KEY_INSTALL_WINDOW_END, this.mMaintenanceWindowEnd);
        for (int i = 0; i < this.mFreezePeriods.size(); i++) {
            android.app.admin.FreezePeriod freezePeriod = this.mFreezePeriods.get(i);
            typedXmlSerializer.startTag(null, "freeze");
            typedXmlSerializer.attribute(null, "start", freezePeriod.getStart().toString());
            typedXmlSerializer.attribute(null, "end", freezePeriod.getEnd().toString());
            typedXmlSerializer.endTag(null, "freeze");
        }
    }
}
