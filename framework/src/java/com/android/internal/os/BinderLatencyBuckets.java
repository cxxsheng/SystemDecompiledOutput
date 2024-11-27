package com.android.internal.os;

/* loaded from: classes4.dex */
public class BinderLatencyBuckets {
    private static final java.lang.String TAG = "BinderLatencyBuckets";
    private final int[] mBuckets;

    public BinderLatencyBuckets(int i, int i2, float f) {
        int i3 = i - 1;
        int[] iArr = new int[i3];
        iArr[0] = i2;
        double d = i2;
        for (int i4 = 1; i4 < i3; i4++) {
            d *= f;
            if (d > 2.147483647E9d) {
                android.util.Slog.w(TAG, "Attempted to create a bucket larger than maxint");
                this.mBuckets = java.util.Arrays.copyOfRange(iArr, 0, i4);
                return;
            }
            int i5 = (int) d;
            int i6 = i4 - 1;
            if (i5 <= iArr[i6]) {
                iArr[i4] = iArr[i6] + 1;
            } else {
                iArr[i4] = i5;
            }
        }
        this.mBuckets = iArr;
    }

    public int sampleToBucket(int i) {
        if (i >= this.mBuckets[this.mBuckets.length - 1]) {
            return this.mBuckets.length;
        }
        int binarySearch = java.util.Arrays.binarySearch(this.mBuckets, i);
        return binarySearch < 0 ? -(binarySearch + 1) : binarySearch + 1;
    }

    public int[] getBuckets() {
        return this.mBuckets;
    }
}
