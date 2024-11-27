package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class TelephonyHistogram implements android.os.Parcelable {
    private static final int ABSENT = 0;
    public static final android.os.Parcelable.Creator<android.telephony.TelephonyHistogram> CREATOR = new android.os.Parcelable.Creator<android.telephony.TelephonyHistogram>() { // from class: android.telephony.TelephonyHistogram.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.TelephonyHistogram createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.TelephonyHistogram(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.TelephonyHistogram[] newArray(int i) {
            return new android.telephony.TelephonyHistogram[i];
        }
    };
    private static final int PRESENT = 1;
    private static final int RANGE_CALCULATION_COUNT = 10;
    public static final int TELEPHONY_CATEGORY_RIL = 1;
    private int mAverageTimeMs;
    private final int mBucketCount;
    private final int[] mBucketCounters;
    private final int[] mBucketEndPoints;
    private final int mCategory;
    private final int mId;
    private int[] mInitialTimings;
    private int mMaxTimeMs;
    private int mMinTimeMs;
    private int mSampleCount;

    public TelephonyHistogram(int i, int i2, int i3) {
        if (i3 <= 1) {
            throw new java.lang.IllegalArgumentException("Invalid number of buckets");
        }
        this.mCategory = i;
        this.mId = i2;
        this.mMinTimeMs = Integer.MAX_VALUE;
        this.mMaxTimeMs = 0;
        this.mAverageTimeMs = 0;
        this.mSampleCount = 0;
        this.mInitialTimings = new int[10];
        this.mBucketCount = i3;
        this.mBucketEndPoints = new int[i3 - 1];
        this.mBucketCounters = new int[i3];
    }

    public TelephonyHistogram(android.telephony.TelephonyHistogram telephonyHistogram) {
        this.mCategory = telephonyHistogram.getCategory();
        this.mId = telephonyHistogram.getId();
        this.mMinTimeMs = telephonyHistogram.getMinTime();
        this.mMaxTimeMs = telephonyHistogram.getMaxTime();
        this.mAverageTimeMs = telephonyHistogram.getAverageTime();
        this.mSampleCount = telephonyHistogram.getSampleCount();
        this.mInitialTimings = telephonyHistogram.getInitialTimings();
        this.mBucketCount = telephonyHistogram.getBucketCount();
        this.mBucketEndPoints = telephonyHistogram.getBucketEndPoints();
        this.mBucketCounters = telephonyHistogram.getBucketCounters();
    }

    public int getCategory() {
        return this.mCategory;
    }

    public int getId() {
        return this.mId;
    }

    public int getMinTime() {
        return this.mMinTimeMs;
    }

    public int getMaxTime() {
        return this.mMaxTimeMs;
    }

    public int getAverageTime() {
        return this.mAverageTimeMs;
    }

    public int getSampleCount() {
        return this.mSampleCount;
    }

    private int[] getInitialTimings() {
        return this.mInitialTimings;
    }

    public int getBucketCount() {
        return this.mBucketCount;
    }

    public int[] getBucketEndPoints() {
        if (this.mSampleCount > 1 && this.mSampleCount < 10) {
            int[] iArr = new int[this.mBucketCount - 1];
            calculateBucketEndPoints(iArr);
            return iArr;
        }
        return getDeepCopyOfArray(this.mBucketEndPoints);
    }

    public int[] getBucketCounters() {
        if (this.mSampleCount > 1 && this.mSampleCount < 10) {
            int[] iArr = new int[this.mBucketCount - 1];
            int[] iArr2 = new int[this.mBucketCount];
            calculateBucketEndPoints(iArr);
            for (int i = 0; i < this.mSampleCount; i++) {
                addToBucketCounter(iArr, iArr2, this.mInitialTimings[i]);
            }
            return iArr2;
        }
        return getDeepCopyOfArray(this.mBucketCounters);
    }

    private int[] getDeepCopyOfArray(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        java.lang.System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    private void addToBucketCounter(int[] iArr, int[] iArr2, int i) {
        int i2 = 0;
        while (i2 < iArr.length) {
            if (i > iArr[i2]) {
                i2++;
            } else {
                iArr2[i2] = iArr2[i2] + 1;
                return;
            }
        }
        iArr2[i2] = iArr2[i2] + 1;
    }

    private void calculateBucketEndPoints(int[] iArr) {
        for (int i = 1; i < this.mBucketCount; i++) {
            iArr[i - 1] = this.mMinTimeMs + (((this.mMaxTimeMs - this.mMinTimeMs) * i) / this.mBucketCount);
        }
    }

    public void addTimeTaken(int i) {
        if (this.mSampleCount == 0 || this.mSampleCount == Integer.MAX_VALUE) {
            if (this.mSampleCount == 0) {
                this.mMinTimeMs = i;
                this.mMaxTimeMs = i;
                this.mAverageTimeMs = i;
            } else {
                this.mInitialTimings = new int[10];
            }
            this.mSampleCount = 1;
            java.util.Arrays.fill(this.mInitialTimings, 0);
            this.mInitialTimings[0] = i;
            java.util.Arrays.fill(this.mBucketEndPoints, 0);
            java.util.Arrays.fill(this.mBucketCounters, 0);
            return;
        }
        if (i < this.mMinTimeMs) {
            this.mMinTimeMs = i;
        }
        if (i > this.mMaxTimeMs) {
            this.mMaxTimeMs = i;
        }
        int i2 = this.mSampleCount + 1;
        this.mSampleCount = i2;
        this.mAverageTimeMs = (int) (((this.mAverageTimeMs * this.mSampleCount) + i) / i2);
        if (this.mSampleCount < 10) {
            this.mInitialTimings[this.mSampleCount - 1] = i;
            return;
        }
        if (this.mSampleCount == 10) {
            this.mInitialTimings[this.mSampleCount - 1] = i;
            calculateBucketEndPoints(this.mBucketEndPoints);
            for (int i3 = 0; i3 < 10; i3++) {
                addToBucketCounter(this.mBucketEndPoints, this.mBucketCounters, this.mInitialTimings[i3]);
            }
            this.mInitialTimings = null;
            return;
        }
        addToBucketCounter(this.mBucketEndPoints, this.mBucketCounters, i);
    }

    public java.lang.String toString() {
        java.lang.String str = " Histogram id = " + this.mId + " Time(ms): min = " + this.mMinTimeMs + " max = " + this.mMaxTimeMs + " avg = " + this.mAverageTimeMs + " Count = " + this.mSampleCount;
        if (this.mSampleCount < 10) {
            return str;
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer(" Interval Endpoints:");
        for (int i = 0; i < this.mBucketEndPoints.length; i++) {
            stringBuffer.append(" " + this.mBucketEndPoints[i]);
        }
        stringBuffer.append(" Interval counters:");
        for (int i2 = 0; i2 < this.mBucketCounters.length; i2++) {
            stringBuffer.append(" " + this.mBucketCounters[i2]);
        }
        return str + ((java.lang.Object) stringBuffer);
    }

    public TelephonyHistogram(android.os.Parcel parcel) {
        this.mCategory = parcel.readInt();
        this.mId = parcel.readInt();
        this.mMinTimeMs = parcel.readInt();
        this.mMaxTimeMs = parcel.readInt();
        this.mAverageTimeMs = parcel.readInt();
        this.mSampleCount = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mInitialTimings = new int[10];
            parcel.readIntArray(this.mInitialTimings);
        }
        this.mBucketCount = parcel.readInt();
        this.mBucketEndPoints = new int[this.mBucketCount - 1];
        parcel.readIntArray(this.mBucketEndPoints);
        this.mBucketCounters = new int[this.mBucketCount];
        parcel.readIntArray(this.mBucketCounters);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCategory);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mMinTimeMs);
        parcel.writeInt(this.mMaxTimeMs);
        parcel.writeInt(this.mAverageTimeMs);
        parcel.writeInt(this.mSampleCount);
        if (this.mInitialTimings == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeIntArray(this.mInitialTimings);
        }
        parcel.writeInt(this.mBucketCount);
        parcel.writeIntArray(this.mBucketEndPoints);
        parcel.writeIntArray(this.mBucketCounters);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
