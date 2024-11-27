package android.app.usage;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class CacheQuotaHint implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.usage.CacheQuotaHint> CREATOR = new android.os.Parcelable.Creator<android.app.usage.CacheQuotaHint>() { // from class: android.app.usage.CacheQuotaHint.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.CacheQuotaHint createFromParcel(android.os.Parcel parcel) {
            return new android.app.usage.CacheQuotaHint.Builder().setVolumeUuid(parcel.readString()).setUid(parcel.readInt()).setQuota(parcel.readLong()).setUsageStats((android.app.usage.UsageStats) parcel.readParcelable(android.app.usage.UsageStats.class.getClassLoader(), android.app.usage.UsageStats.class)).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.usage.CacheQuotaHint[] newArray(int i) {
            return new android.app.usage.CacheQuotaHint[i];
        }
    };
    public static final long QUOTA_NOT_SET = -1;
    private final long mQuota;
    private final int mUid;
    private final android.app.usage.UsageStats mUsageStats;
    private final java.lang.String mUuid;

    public CacheQuotaHint(android.app.usage.CacheQuotaHint.Builder builder) {
        this.mUuid = builder.mUuid;
        this.mUid = builder.mUid;
        this.mUsageStats = builder.mUsageStats;
        this.mQuota = builder.mQuota;
    }

    public java.lang.String getVolumeUuid() {
        return this.mUuid;
    }

    public int getUid() {
        return this.mUid;
    }

    public long getQuota() {
        return this.mQuota;
    }

    public android.app.usage.UsageStats getUsageStats() {
        return this.mUsageStats;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mUuid);
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mQuota);
        parcel.writeParcelable(this.mUsageStats, 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.app.usage.CacheQuotaHint)) {
            return false;
        }
        android.app.usage.CacheQuotaHint cacheQuotaHint = (android.app.usage.CacheQuotaHint) obj;
        return java.util.Objects.equals(this.mUuid, cacheQuotaHint.mUuid) && java.util.Objects.equals(this.mUsageStats, cacheQuotaHint.mUsageStats) && this.mUid == cacheQuotaHint.mUid && this.mQuota == cacheQuotaHint.mQuota;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUuid, java.lang.Integer.valueOf(this.mUid), this.mUsageStats, java.lang.Long.valueOf(this.mQuota));
    }

    public static final class Builder {
        private long mQuota;
        private int mUid;
        private android.app.usage.UsageStats mUsageStats;
        private java.lang.String mUuid;

        public Builder() {
        }

        public Builder(android.app.usage.CacheQuotaHint cacheQuotaHint) {
            setVolumeUuid(cacheQuotaHint.getVolumeUuid());
            setUid(cacheQuotaHint.getUid());
            setUsageStats(cacheQuotaHint.getUsageStats());
            setQuota(cacheQuotaHint.getQuota());
        }

        public android.app.usage.CacheQuotaHint.Builder setVolumeUuid(java.lang.String str) {
            this.mUuid = str;
            return this;
        }

        public android.app.usage.CacheQuotaHint.Builder setUid(int i) {
            com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "Proposed uid was negative.");
            this.mUid = i;
            return this;
        }

        public android.app.usage.CacheQuotaHint.Builder setUsageStats(android.app.usage.UsageStats usageStats) {
            this.mUsageStats = usageStats;
            return this;
        }

        public android.app.usage.CacheQuotaHint.Builder setQuota(long j) {
            com.android.internal.util.Preconditions.checkArgument(j >= -1);
            this.mQuota = j;
            return this;
        }

        public android.app.usage.CacheQuotaHint build() {
            return new android.app.usage.CacheQuotaHint(this);
        }
    }
}
