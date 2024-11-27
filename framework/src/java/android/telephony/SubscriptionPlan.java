package android.telephony;

/* loaded from: classes3.dex */
public final class SubscriptionPlan implements android.os.Parcelable {
    public static final long BYTES_UNKNOWN = -1;
    public static final long BYTES_UNLIMITED = Long.MAX_VALUE;
    public static final android.os.Parcelable.Creator<android.telephony.SubscriptionPlan> CREATOR = new android.os.Parcelable.Creator<android.telephony.SubscriptionPlan>() { // from class: android.telephony.SubscriptionPlan.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SubscriptionPlan createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.SubscriptionPlan(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.SubscriptionPlan[] newArray(int i) {
            return new android.telephony.SubscriptionPlan[i];
        }
    };
    public static final int LIMIT_BEHAVIOR_BILLED = 1;
    public static final int LIMIT_BEHAVIOR_DISABLED = 0;
    public static final int LIMIT_BEHAVIOR_THROTTLED = 2;
    public static final int LIMIT_BEHAVIOR_UNKNOWN = -1;
    public static final long TIME_UNKNOWN = -1;
    private final android.util.RecurrenceRule cycleRule;
    private int dataLimitBehavior;
    private long dataLimitBytes;
    private long dataUsageBytes;
    private long dataUsageTime;
    private int[] networkTypes;
    private java.lang.CharSequence summary;
    private java.lang.CharSequence title;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LimitBehavior {
    }

    private SubscriptionPlan(android.util.RecurrenceRule recurrenceRule) {
        this.dataLimitBytes = -1L;
        this.dataLimitBehavior = -1;
        this.dataUsageBytes = -1L;
        this.dataUsageTime = -1L;
        this.cycleRule = (android.util.RecurrenceRule) com.android.internal.util.Preconditions.checkNotNull(recurrenceRule);
        this.networkTypes = java.util.Arrays.copyOf(android.telephony.TelephonyManager.getAllNetworkTypes(), android.telephony.TelephonyManager.getAllNetworkTypes().length);
    }

    private SubscriptionPlan(android.os.Parcel parcel) {
        this.dataLimitBytes = -1L;
        this.dataLimitBehavior = -1;
        this.dataUsageBytes = -1L;
        this.dataUsageTime = -1L;
        this.cycleRule = (android.util.RecurrenceRule) parcel.readParcelable(null, android.util.RecurrenceRule.class);
        this.title = parcel.readCharSequence();
        this.summary = parcel.readCharSequence();
        this.dataLimitBytes = parcel.readLong();
        this.dataLimitBehavior = parcel.readInt();
        this.dataUsageBytes = parcel.readLong();
        this.dataUsageTime = parcel.readLong();
        this.networkTypes = parcel.createIntArray();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.cycleRule, i);
        parcel.writeCharSequence(this.title);
        parcel.writeCharSequence(this.summary);
        parcel.writeLong(this.dataLimitBytes);
        parcel.writeInt(this.dataLimitBehavior);
        parcel.writeLong(this.dataUsageBytes);
        parcel.writeLong(this.dataUsageTime);
        parcel.writeIntArray(this.networkTypes);
    }

    public java.lang.String toString() {
        return "SubscriptionPlan{cycleRule=" + this.cycleRule + " title=" + this.title + " summary=" + this.summary + " dataLimitBytes=" + this.dataLimitBytes + " dataLimitBehavior=" + this.dataLimitBehavior + " dataUsageBytes=" + this.dataUsageBytes + " dataUsageTime=" + this.dataUsageTime + " networkTypes=" + java.util.Arrays.toString(this.networkTypes) + "}";
    }

    public int hashCode() {
        return java.util.Objects.hash(this.cycleRule, this.title, this.summary, java.lang.Long.valueOf(this.dataLimitBytes), java.lang.Integer.valueOf(this.dataLimitBehavior), java.lang.Long.valueOf(this.dataUsageBytes), java.lang.Long.valueOf(this.dataUsageTime), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.networkTypes)));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.telephony.SubscriptionPlan)) {
            return false;
        }
        android.telephony.SubscriptionPlan subscriptionPlan = (android.telephony.SubscriptionPlan) obj;
        return java.util.Objects.equals(this.cycleRule, subscriptionPlan.cycleRule) && java.util.Objects.equals(this.title, subscriptionPlan.title) && java.util.Objects.equals(this.summary, subscriptionPlan.summary) && this.dataLimitBytes == subscriptionPlan.dataLimitBytes && this.dataLimitBehavior == subscriptionPlan.dataLimitBehavior && this.dataUsageBytes == subscriptionPlan.dataUsageBytes && this.dataUsageTime == subscriptionPlan.dataUsageTime && java.util.Arrays.equals(this.networkTypes, subscriptionPlan.networkTypes);
    }

    public android.util.RecurrenceRule getCycleRule() {
        return this.cycleRule;
    }

    public java.lang.CharSequence getTitle() {
        return this.title;
    }

    public java.lang.CharSequence getSummary() {
        return this.summary;
    }

    public long getDataLimitBytes() {
        return this.dataLimitBytes;
    }

    public int getDataLimitBehavior() {
        return this.dataLimitBehavior;
    }

    public long getDataUsageBytes() {
        return this.dataUsageBytes;
    }

    public long getDataUsageTime() {
        return this.dataUsageTime;
    }

    public int[] getNetworkTypes() {
        return java.util.Arrays.copyOf(this.networkTypes, this.networkTypes.length);
    }

    public java.util.Iterator<android.util.Range<java.time.ZonedDateTime>> cycleIterator() {
        return this.cycleRule.cycleIterator();
    }

    public static class Builder {
        private final android.telephony.SubscriptionPlan plan;

        public Builder(java.time.ZonedDateTime zonedDateTime, java.time.ZonedDateTime zonedDateTime2, java.time.Period period) {
            this.plan = new android.telephony.SubscriptionPlan(new android.util.RecurrenceRule(zonedDateTime, zonedDateTime2, period));
        }

        public static android.telephony.SubscriptionPlan.Builder createNonrecurring(java.time.ZonedDateTime zonedDateTime, java.time.ZonedDateTime zonedDateTime2) {
            if (!zonedDateTime2.isAfter(zonedDateTime)) {
                throw new java.lang.IllegalArgumentException("End " + zonedDateTime2 + " isn't after start " + zonedDateTime);
            }
            return new android.telephony.SubscriptionPlan.Builder(zonedDateTime, zonedDateTime2, null);
        }

        public static android.telephony.SubscriptionPlan.Builder createRecurring(java.time.ZonedDateTime zonedDateTime, java.time.Period period) {
            if (period.isZero() || period.isNegative()) {
                throw new java.lang.IllegalArgumentException("Period " + period + " must be positive");
            }
            return new android.telephony.SubscriptionPlan.Builder(zonedDateTime, null, period);
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public static android.telephony.SubscriptionPlan.Builder createRecurringMonthly(java.time.ZonedDateTime zonedDateTime) {
            return new android.telephony.SubscriptionPlan.Builder(zonedDateTime, null, java.time.Period.ofMonths(1));
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public static android.telephony.SubscriptionPlan.Builder createRecurringWeekly(java.time.ZonedDateTime zonedDateTime) {
            return new android.telephony.SubscriptionPlan.Builder(zonedDateTime, null, java.time.Period.ofDays(7));
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public static android.telephony.SubscriptionPlan.Builder createRecurringDaily(java.time.ZonedDateTime zonedDateTime) {
            return new android.telephony.SubscriptionPlan.Builder(zonedDateTime, null, java.time.Period.ofDays(1));
        }

        public android.telephony.SubscriptionPlan build() {
            return this.plan;
        }

        public android.telephony.SubscriptionPlan.Builder setTitle(java.lang.CharSequence charSequence) {
            this.plan.title = charSequence;
            return this;
        }

        public android.telephony.SubscriptionPlan.Builder setSummary(java.lang.CharSequence charSequence) {
            this.plan.summary = charSequence;
            return this;
        }

        public android.telephony.SubscriptionPlan.Builder setDataLimit(long j, int i) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Limit bytes must be positive");
            }
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Limit behavior must be defined");
            }
            this.plan.dataLimitBytes = j;
            this.plan.dataLimitBehavior = i;
            return this;
        }

        public android.telephony.SubscriptionPlan.Builder setDataUsage(long j, long j2) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("Usage bytes must be positive");
            }
            if (j2 < 0) {
                throw new java.lang.IllegalArgumentException("Usage time must be positive");
            }
            this.plan.dataUsageBytes = j;
            this.plan.dataUsageTime = j2;
            return this;
        }

        public android.telephony.SubscriptionPlan.Builder setNetworkTypes(int[] iArr) {
            this.plan.networkTypes = java.util.Arrays.copyOf(iArr, iArr.length);
            return this;
        }

        public android.telephony.SubscriptionPlan.Builder resetNetworkTypes() {
            this.plan.networkTypes = java.util.Arrays.copyOf(android.telephony.TelephonyManager.getAllNetworkTypes(), android.telephony.TelephonyManager.getAllNetworkTypes().length);
            return this;
        }
    }
}
