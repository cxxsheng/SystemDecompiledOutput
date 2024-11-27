package android.hardware.display;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class AmbientBrightnessDayStats implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.display.AmbientBrightnessDayStats> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.AmbientBrightnessDayStats>() { // from class: android.hardware.display.AmbientBrightnessDayStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.AmbientBrightnessDayStats createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.AmbientBrightnessDayStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.AmbientBrightnessDayStats[] newArray(int i) {
            return new android.hardware.display.AmbientBrightnessDayStats[i];
        }
    };
    private final float[] mBucketBoundaries;
    private final java.time.LocalDate mLocalDate;
    private final float[] mStats;

    public AmbientBrightnessDayStats(java.time.LocalDate localDate, float[] fArr) {
        this(localDate, fArr, null);
    }

    public AmbientBrightnessDayStats(java.time.LocalDate localDate, float[] fArr, float[] fArr2) {
        java.util.Objects.requireNonNull(localDate);
        java.util.Objects.requireNonNull(fArr);
        com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr, 0.0f, Float.MAX_VALUE, "bucketBoundaries");
        if (fArr.length < 1) {
            throw new java.lang.IllegalArgumentException("Bucket boundaries must contain at least 1 value");
        }
        checkSorted(fArr);
        if (fArr2 == null) {
            fArr2 = new float[fArr.length];
        } else {
            com.android.internal.util.Preconditions.checkArrayElementsInRange(fArr2, 0.0f, Float.MAX_VALUE, android.content.Context.STATS_MANAGER);
            if (fArr.length != fArr2.length) {
                throw new java.lang.IllegalArgumentException("Bucket boundaries and stats must be of same size.");
            }
        }
        this.mLocalDate = localDate;
        this.mBucketBoundaries = fArr;
        this.mStats = fArr2;
    }

    public java.time.LocalDate getLocalDate() {
        return this.mLocalDate;
    }

    public float[] getStats() {
        return this.mStats;
    }

    public float[] getBucketBoundaries() {
        return this.mBucketBoundaries;
    }

    private AmbientBrightnessDayStats(android.os.Parcel parcel) {
        this.mLocalDate = java.time.LocalDate.parse(parcel.readString());
        this.mBucketBoundaries = parcel.createFloatArray();
        this.mStats = parcel.createFloatArray();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.hardware.display.AmbientBrightnessDayStats ambientBrightnessDayStats = (android.hardware.display.AmbientBrightnessDayStats) obj;
        if (this.mLocalDate.equals(ambientBrightnessDayStats.mLocalDate) && java.util.Arrays.equals(this.mBucketBoundaries, ambientBrightnessDayStats.mBucketBoundaries) && java.util.Arrays.equals(this.mStats, ambientBrightnessDayStats.mStats)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mLocalDate.hashCode() + 31) * 31) + java.util.Arrays.hashCode(this.mBucketBoundaries)) * 31) + java.util.Arrays.hashCode(this.mStats);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        for (int i = 0; i < this.mBucketBoundaries.length; i++) {
            if (i != 0) {
                sb.append(", ");
                sb2.append(", ");
            }
            sb.append(this.mBucketBoundaries[i]);
            sb2.append(this.mStats[i]);
        }
        return this.mLocalDate + " {" + ((java.lang.CharSequence) sb) + "} {" + ((java.lang.CharSequence) sb2) + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mLocalDate.toString());
        parcel.writeFloatArray(this.mBucketBoundaries);
        parcel.writeFloatArray(this.mStats);
    }

    public void log(float f, float f2) {
        int bucketIndex = getBucketIndex(f);
        if (bucketIndex >= 0) {
            float[] fArr = this.mStats;
            fArr[bucketIndex] = fArr[bucketIndex] + f2;
        }
    }

    private int getBucketIndex(float f) {
        int i = 0;
        if (f < this.mBucketBoundaries[0] || java.lang.Float.isNaN(f)) {
            return -1;
        }
        int length = this.mBucketBoundaries.length - 1;
        while (i < length) {
            int i2 = (i + length) / 2;
            if (this.mBucketBoundaries[i2] <= f && f < this.mBucketBoundaries[i2 + 1]) {
                return i2;
            }
            if (this.mBucketBoundaries[i2] < f) {
                i = i2 + 1;
            } else if (this.mBucketBoundaries[i2] > f) {
                length = i2 - 1;
            }
        }
        return i;
    }

    private static void checkSorted(float[] fArr) {
        if (fArr.length <= 1) {
            return;
        }
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            com.android.internal.util.Preconditions.checkState(f < fArr[i]);
            f = fArr[i];
        }
    }
}
