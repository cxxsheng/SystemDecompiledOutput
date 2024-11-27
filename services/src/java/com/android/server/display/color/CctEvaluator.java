package com.android.server.display.color;

/* loaded from: classes.dex */
class CctEvaluator implements android.animation.TypeEvaluator<java.lang.Integer> {
    private static final java.lang.String TAG = "CctEvaluator";
    private final int mIndexOffset;

    @com.android.internal.annotations.VisibleForTesting
    final int[] mSteppedCctsAtOffsetCcts;

    @com.android.internal.annotations.VisibleForTesting
    final int[] mStepsAtOffsetCcts;

    CctEvaluator(int i, int i2, int[] iArr, int[] iArr2) {
        int i3 = (i2 - i) + 1;
        this.mStepsAtOffsetCcts = new int[i3];
        this.mSteppedCctsAtOffsetCcts = new int[i3];
        this.mIndexOffset = i;
        int length = iArr.length;
        if (iArr.length != iArr2.length) {
            android.util.Slog.e(TAG, "Parallel arrays cctRangeMinimums and steps are different lengths; setting step of 1");
            setStepOfOne();
            return;
        }
        if (length == 0) {
            android.util.Slog.e(TAG, "No cctRangeMinimums or steps are set; setting step of 1");
            setStepOfOne();
            return;
        }
        int i4 = Integer.MIN_VALUE;
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = this.mIndexOffset + i6;
            int i8 = i5 + 1;
            while (i8 < length && i7 >= iArr[i8]) {
                int i9 = i8;
                i8++;
                i5 = i9;
            }
            this.mStepsAtOffsetCcts[i6] = iArr2[i5];
            if (i4 == Integer.MIN_VALUE || java.lang.Math.abs(i4 - i7) >= iArr2[i5]) {
                i4 = i7;
            }
            this.mSteppedCctsAtOffsetCcts[i6] = i4;
        }
    }

    @Override // android.animation.TypeEvaluator
    public java.lang.Integer evaluate(float f, java.lang.Integer num, java.lang.Integer num2) {
        int intValue = (int) (num.intValue() + (f * (num2.intValue() - num.intValue())));
        int i = intValue - this.mIndexOffset;
        if (i < 0 || i >= this.mSteppedCctsAtOffsetCcts.length) {
            android.util.Slog.e(TAG, "steppedCctValueAt: returning same since invalid requested index=" + i);
            return java.lang.Integer.valueOf(intValue);
        }
        return java.lang.Integer.valueOf(this.mSteppedCctsAtOffsetCcts[i]);
    }

    private void setStepOfOne() {
        java.util.Arrays.fill(this.mStepsAtOffsetCcts, 1);
        for (int i = 0; i < this.mSteppedCctsAtOffsetCcts.length; i++) {
            this.mSteppedCctsAtOffsetCcts[i] = this.mIndexOffset + i;
        }
    }
}
