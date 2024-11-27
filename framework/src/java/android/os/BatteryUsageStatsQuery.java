package android.os;

/* loaded from: classes3.dex */
public final class BatteryUsageStatsQuery implements android.os.Parcelable {
    private static final long DEFAULT_MAX_STATS_AGE_MS = 300000;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_HISTORY = 2;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_POWER_MODELS = 4;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_PROCESS_STATE_DATA = 8;
    public static final int FLAG_BATTERY_USAGE_STATS_INCLUDE_VIRTUAL_UIDS = 16;
    public static final int FLAG_BATTERY_USAGE_STATS_POWER_PROFILE_MODEL = 1;
    private final int mFlags;
    private final long mFromTimestamp;
    private final long mMaxStatsAgeMs;
    private final double mMinConsumedPowerThreshold;
    private final int[] mPowerComponents;
    private final long mToTimestamp;
    private final int[] mUserIds;
    public static final android.os.BatteryUsageStatsQuery DEFAULT = new android.os.BatteryUsageStatsQuery.Builder().build();
    public static final android.os.Parcelable.Creator<android.os.BatteryUsageStatsQuery> CREATOR = new android.os.Parcelable.Creator<android.os.BatteryUsageStatsQuery>() { // from class: android.os.BatteryUsageStatsQuery.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatteryUsageStatsQuery createFromParcel(android.os.Parcel parcel) {
            return new android.os.BatteryUsageStatsQuery(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.BatteryUsageStatsQuery[] newArray(int i) {
            return new android.os.BatteryUsageStatsQuery[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BatteryUsageStatsFlags {
    }

    private BatteryUsageStatsQuery(android.os.BatteryUsageStatsQuery.Builder builder) {
        this.mFlags = builder.mFlags;
        this.mUserIds = builder.mUserIds != null ? builder.mUserIds.toArray() : new int[]{-1};
        this.mMaxStatsAgeMs = builder.mMaxStatsAgeMs;
        this.mMinConsumedPowerThreshold = builder.mMinConsumedPowerThreshold;
        this.mFromTimestamp = builder.mFromTimestamp;
        this.mToTimestamp = builder.mToTimestamp;
        this.mPowerComponents = builder.mPowerComponents;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int[] getUserIds() {
        return this.mUserIds;
    }

    public boolean shouldForceUsePowerProfileModel() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isProcessStateDataNeeded() {
        return (this.mFlags & 8) != 0;
    }

    public int[] getPowerComponents() {
        return this.mPowerComponents;
    }

    public long getMaxStatsAge() {
        return this.mMaxStatsAgeMs;
    }

    public double getMinConsumedPowerThreshold() {
        return this.mMinConsumedPowerThreshold;
    }

    public long getFromTimestamp() {
        return this.mFromTimestamp;
    }

    public long getToTimestamp() {
        return this.mToTimestamp;
    }

    private BatteryUsageStatsQuery(android.os.Parcel parcel) {
        this.mFlags = parcel.readInt();
        this.mUserIds = new int[parcel.readInt()];
        parcel.readIntArray(this.mUserIds);
        this.mMaxStatsAgeMs = parcel.readLong();
        this.mMinConsumedPowerThreshold = parcel.readDouble();
        this.mFromTimestamp = parcel.readLong();
        this.mToTimestamp = parcel.readLong();
        this.mPowerComponents = parcel.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mUserIds.length);
        parcel.writeIntArray(this.mUserIds);
        parcel.writeLong(this.mMaxStatsAgeMs);
        parcel.writeDouble(this.mMinConsumedPowerThreshold);
        parcel.writeLong(this.mFromTimestamp);
        parcel.writeLong(this.mToTimestamp);
        parcel.writeIntArray(this.mPowerComponents);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private int mFlags;
        private long mFromTimestamp;
        private long mMaxStatsAgeMs = 300000;
        private double mMinConsumedPowerThreshold = 0.0d;
        private int[] mPowerComponents;
        private long mToTimestamp;
        private android.util.IntArray mUserIds;

        public android.os.BatteryUsageStatsQuery build() {
            return new android.os.BatteryUsageStatsQuery(this);
        }

        public android.os.BatteryUsageStatsQuery.Builder addUser(android.os.UserHandle userHandle) {
            if (this.mUserIds == null) {
                this.mUserIds = new android.util.IntArray(1);
            }
            this.mUserIds.add(userHandle.getIdentifier());
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder includeBatteryHistory() {
            this.mFlags |= 2;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder includeProcessStateData() {
            this.mFlags |= 8;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder powerProfileModeledOnly() {
            this.mFlags |= 1;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder includePowerModels() {
            this.mFlags |= 4;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder includePowerComponents(int[] iArr) {
            this.mPowerComponents = iArr;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder includeVirtualUids() {
            this.mFlags |= 16;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder aggregateSnapshots(long j, long j2) {
            this.mFromTimestamp = j;
            this.mToTimestamp = j2;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder setMaxStatsAgeMs(long j) {
            this.mMaxStatsAgeMs = j;
            return this;
        }

        public android.os.BatteryUsageStatsQuery.Builder setMinConsumedPowerThreshold(double d) {
            this.mMinConsumedPowerThreshold = d;
            return this;
        }
    }
}
