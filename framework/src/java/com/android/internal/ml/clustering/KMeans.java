package com.android.internal.ml.clustering;

/* loaded from: classes4.dex */
public class KMeans {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "KMeans";
    private final int mMaxIterations;
    private final java.util.Random mRandomState;
    private float mSqConvergenceEpsilon;

    public KMeans() {
        this(new java.util.Random());
    }

    public KMeans(java.util.Random random) {
        this(random, 30, 0.005f);
    }

    public KMeans(java.util.Random random, int i, float f) {
        this.mRandomState = random;
        this.mMaxIterations = i;
        this.mSqConvergenceEpsilon = f * f;
    }

    public java.util.List<com.android.internal.ml.clustering.KMeans.Mean> predict(int i, float[][] fArr) {
        checkDataSetSanity(fArr);
        int length = fArr[0].length;
        java.util.ArrayList<com.android.internal.ml.clustering.KMeans.Mean> arrayList = new java.util.ArrayList<>();
        for (int i2 = 0; i2 < i; i2++) {
            com.android.internal.ml.clustering.KMeans.Mean mean = new com.android.internal.ml.clustering.KMeans.Mean(length);
            for (int i3 = 0; i3 < length; i3++) {
                mean.mCentroid[i3] = this.mRandomState.nextFloat();
            }
            arrayList.add(mean);
        }
        for (int i4 = 0; i4 < this.mMaxIterations && !step(arrayList, fArr); i4++) {
        }
        return arrayList;
    }

    public static double score(java.util.List<com.android.internal.ml.clustering.KMeans.Mean> list) {
        int size = list.size();
        double d = 0.0d;
        for (int i = 0; i < size; i++) {
            com.android.internal.ml.clustering.KMeans.Mean mean = list.get(i);
            for (int i2 = 0; i2 < size; i2++) {
                if (mean != list.get(i2)) {
                    d += java.lang.Math.sqrt(sqDistance(mean.mCentroid, r7.mCentroid));
                }
            }
        }
        return d;
    }

    public void checkDataSetSanity(float[][] fArr) {
        if (fArr == null) {
            throw new java.lang.IllegalArgumentException("Data set is null.");
        }
        if (fArr.length == 0) {
            throw new java.lang.IllegalArgumentException("Data set is empty.");
        }
        if (fArr[0] == null) {
            throw new java.lang.IllegalArgumentException("Bad data set format.");
        }
        int length = fArr[0].length;
        int length2 = fArr.length;
        for (int i = 1; i < length2; i++) {
            if (fArr[i] == null || fArr[i].length != length) {
                throw new java.lang.IllegalArgumentException("Bad data set format.");
            }
        }
    }

    private boolean step(java.util.ArrayList<com.android.internal.ml.clustering.KMeans.Mean> arrayList, float[][] fArr) {
        boolean z = true;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).mClosestItems.clear();
        }
        for (int length = fArr.length - 1; length >= 0; length--) {
            float[] fArr2 = fArr[length];
            nearestMean(fArr2, arrayList).mClosestItems.add(fArr2);
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            com.android.internal.ml.clustering.KMeans.Mean mean = arrayList.get(size2);
            if (mean.mClosestItems.size() != 0) {
                float[] fArr3 = mean.mCentroid;
                mean.mCentroid = new float[fArr3.length];
                for (int i = 0; i < mean.mClosestItems.size(); i++) {
                    for (int i2 = 0; i2 < mean.mCentroid.length; i2++) {
                        float[] fArr4 = mean.mCentroid;
                        fArr4[i2] = fArr4[i2] + mean.mClosestItems.get(i)[i2];
                    }
                }
                for (int i3 = 0; i3 < mean.mCentroid.length; i3++) {
                    float[] fArr5 = mean.mCentroid;
                    fArr5[i3] = fArr5[i3] / mean.mClosestItems.size();
                }
                if (sqDistance(fArr3, mean.mCentroid) > this.mSqConvergenceEpsilon) {
                    z = false;
                }
            }
        }
        return z;
    }

    public static com.android.internal.ml.clustering.KMeans.Mean nearestMean(float[] fArr, java.util.List<com.android.internal.ml.clustering.KMeans.Mean> list) {
        int size = list.size();
        com.android.internal.ml.clustering.KMeans.Mean mean = null;
        float f = Float.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            com.android.internal.ml.clustering.KMeans.Mean mean2 = list.get(i);
            float sqDistance = sqDistance(fArr, mean2.mCentroid);
            if (sqDistance < f) {
                mean = mean2;
                f = sqDistance;
            }
        }
        return mean;
    }

    public static float sqDistance(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            f += (fArr[i] - fArr2[i]) * (fArr[i] - fArr2[i]);
        }
        return f;
    }

    public static class Mean {
        float[] mCentroid;
        final java.util.ArrayList<float[]> mClosestItems = new java.util.ArrayList<>();

        public Mean(int i) {
            this.mCentroid = new float[i];
        }

        public Mean(float... fArr) {
            this.mCentroid = fArr;
        }

        public float[] getCentroid() {
            return this.mCentroid;
        }

        public java.util.List<float[]> getItems() {
            return this.mClosestItems;
        }

        public java.lang.String toString() {
            return "Mean(centroid: " + java.util.Arrays.toString(this.mCentroid) + ", size: " + this.mClosestItems.size() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
