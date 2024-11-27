package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public class VariationalKMeansQuantizer implements com.android.internal.graphics.palette.Quantizer {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "KMeansQuantizer";
    private final int mInitializations;
    private final com.android.internal.ml.clustering.KMeans mKMeans;
    private final float mMinClusterSqDistance;
    private java.util.List<com.android.internal.graphics.palette.Palette.Swatch> mQuantizedColors;

    public VariationalKMeansQuantizer() {
        this(0.25f);
    }

    public VariationalKMeansQuantizer(float f) {
        this(f, 1);
    }

    public VariationalKMeansQuantizer(float f, int i) {
        this.mKMeans = new com.android.internal.ml.clustering.KMeans(new java.util.Random(0L), 30, 0.0f);
        this.mMinClusterSqDistance = f * f;
        this.mInitializations = i;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        int i2;
        float[] fArr = {0.0f, 0.0f, 0.0f};
        float[][] fArr2 = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, iArr.length, 3);
        for (int i3 = 0; i3 < iArr.length; i3++) {
            com.android.internal.graphics.ColorUtils.colorToHSL(iArr[i3], fArr);
            fArr2[i3][0] = fArr[0] / 360.0f;
            fArr2[i3][1] = fArr[1];
            fArr2[i3][2] = fArr[2];
        }
        java.util.List<com.android.internal.ml.clustering.KMeans.Mean> optimalKMeans = getOptimalKMeans(i, fArr2);
        int i4 = 0;
        while (i4 < optimalKMeans.size()) {
            com.android.internal.ml.clustering.KMeans.Mean mean = optimalKMeans.get(i4);
            float[] centroid = mean.getCentroid();
            i4++;
            int i5 = i4;
            while (i5 < optimalKMeans.size()) {
                com.android.internal.ml.clustering.KMeans.Mean mean2 = optimalKMeans.get(i5);
                if (com.android.internal.ml.clustering.KMeans.sqDistance(centroid, mean2.getCentroid()) >= this.mMinClusterSqDistance) {
                    i2 = i4;
                } else {
                    optimalKMeans.remove(mean2);
                    mean.getItems().addAll(mean2.getItems());
                    int i6 = 0;
                    while (i6 < centroid.length) {
                        centroid[i6] = (float) (centroid[i6] + ((r12[i6] - centroid[i6]) / 2.0d));
                        i6++;
                        i4 = i4;
                    }
                    i2 = i4;
                    i5--;
                }
                i5++;
                i4 = i2;
            }
        }
        this.mQuantizedColors = new java.util.ArrayList();
        for (com.android.internal.ml.clustering.KMeans.Mean mean3 : optimalKMeans) {
            if (mean3.getItems().size() != 0) {
                float[] centroid2 = mean3.getCentroid();
                this.mQuantizedColors.add(new com.android.internal.graphics.palette.Palette.Swatch(com.android.internal.graphics.ColorUtils.HSLToColor(new float[]{centroid2[0] * 360.0f, centroid2[1], centroid2[2]}), mean3.getItems().size()));
            }
        }
    }

    private java.util.List<com.android.internal.ml.clustering.KMeans.Mean> getOptimalKMeans(int i, float[][] fArr) {
        java.util.List<com.android.internal.ml.clustering.KMeans.Mean> list = null;
        double d = -1.7976931348623157E308d;
        for (int i2 = this.mInitializations; i2 > 0; i2--) {
            java.util.List<com.android.internal.ml.clustering.KMeans.Mean> predict = this.mKMeans.predict(i, fArr);
            double score = com.android.internal.ml.clustering.KMeans.score(predict);
            if (list == null || score > d) {
                list = predict;
                d = score;
            }
        }
        return list;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getQuantizedColors() {
        return this.mQuantizedColors;
    }
}
