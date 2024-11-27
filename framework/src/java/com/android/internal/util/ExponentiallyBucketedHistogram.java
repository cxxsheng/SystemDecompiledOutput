package com.android.internal.util;

/* loaded from: classes5.dex */
public class ExponentiallyBucketedHistogram {
    private final int[] mData;

    public ExponentiallyBucketedHistogram(int i) {
        this.mData = new int[com.android.internal.util.Preconditions.checkArgumentInRange(i, 1, 31, "numBuckets")];
    }

    public void add(int i) {
        if (i <= 0) {
            int[] iArr = this.mData;
            iArr[0] = iArr[0] + 1;
        } else {
            int[] iArr2 = this.mData;
            int min = java.lang.Math.min(this.mData.length - 1, 32 - java.lang.Integer.numberOfLeadingZeros(i));
            iArr2[min] = iArr2[min] + 1;
        }
    }

    public void reset() {
        java.util.Arrays.fill(this.mData, 0);
    }

    public void log(java.lang.String str, java.lang.CharSequence charSequence) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(charSequence);
        sb.append('[');
        for (int i = 0; i < this.mData.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            if (i < this.mData.length - 1) {
                sb.append("<");
                sb.append(1 << i);
            } else {
                sb.append(">=");
                sb.append(1 << (i - 1));
            }
            sb.append(": ");
            sb.append(this.mData[i]);
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        android.util.Log.d(str, sb.toString());
    }
}
