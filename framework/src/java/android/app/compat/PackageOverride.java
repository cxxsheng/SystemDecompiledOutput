package android.app.compat;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class PackageOverride {
    public static final int VALUE_DISABLED = 2;
    public static final int VALUE_ENABLED = 1;
    public static final int VALUE_UNDEFINED = 0;
    private final boolean mEnabled;
    private final long mMaxVersionCode;
    private final long mMinVersionCode;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EvaluatedOverride {
    }

    private PackageOverride(long j, long j2, boolean z) {
        this.mMinVersionCode = j;
        this.mMaxVersionCode = j2;
        this.mEnabled = z;
    }

    public int evaluate(long j) {
        if (j < this.mMinVersionCode || j > this.mMaxVersionCode) {
            return 0;
        }
        return this.mEnabled ? 1 : 2;
    }

    public int evaluateForAllVersions() {
        if (this.mMinVersionCode == Long.MIN_VALUE && this.mMaxVersionCode == Long.MAX_VALUE) {
            return this.mEnabled ? 1 : 2;
        }
        return 0;
    }

    public long getMinVersionCode() {
        return this.mMinVersionCode;
    }

    public long getMaxVersionCode() {
        return this.mMaxVersionCode;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void writeToParcel(android.os.Parcel parcel) {
        parcel.writeLong(this.mMinVersionCode);
        parcel.writeLong(this.mMaxVersionCode);
        parcel.writeBoolean(this.mEnabled);
    }

    public static android.app.compat.PackageOverride createFromParcel(android.os.Parcel parcel) {
        return new android.app.compat.PackageOverride(parcel.readLong(), parcel.readLong(), parcel.readBoolean());
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.compat.PackageOverride packageOverride = (android.app.compat.PackageOverride) obj;
        if (this.mMinVersionCode == packageOverride.mMinVersionCode && this.mMaxVersionCode == packageOverride.mMaxVersionCode && this.mEnabled == packageOverride.mEnabled) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mMinVersionCode), java.lang.Long.valueOf(this.mMaxVersionCode), java.lang.Boolean.valueOf(this.mEnabled));
    }

    public java.lang.String toString() {
        if (this.mMinVersionCode == Long.MIN_VALUE && this.mMaxVersionCode == Long.MAX_VALUE) {
            return java.lang.Boolean.toString(this.mEnabled);
        }
        return java.lang.String.format("[%d,%d,%b]", java.lang.Long.valueOf(this.mMinVersionCode), java.lang.Long.valueOf(this.mMaxVersionCode), java.lang.Boolean.valueOf(this.mEnabled));
    }

    public static final class Builder {
        private boolean mEnabled;
        private long mMinVersionCode = Long.MIN_VALUE;
        private long mMaxVersionCode = Long.MAX_VALUE;

        public android.app.compat.PackageOverride.Builder setMinVersionCode(long j) {
            this.mMinVersionCode = j;
            return this;
        }

        public android.app.compat.PackageOverride.Builder setMaxVersionCode(long j) {
            this.mMaxVersionCode = j;
            return this;
        }

        public android.app.compat.PackageOverride.Builder setEnabled(boolean z) {
            this.mEnabled = z;
            return this;
        }

        public android.app.compat.PackageOverride build() {
            if (this.mMinVersionCode > this.mMaxVersionCode) {
                throw new java.lang.IllegalArgumentException("minVersionCode must not be larger than maxVersionCode");
            }
            return new android.app.compat.PackageOverride(this.mMinVersionCode, this.mMaxVersionCode, this.mEnabled);
        }
    }
}
