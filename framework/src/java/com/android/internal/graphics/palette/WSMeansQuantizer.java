package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public final class WSMeansQuantizer implements com.android.internal.graphics.palette.Quantizer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final int MAX_ITERATIONS = 10;
    private static final float MIN_MOVEMENT_DISTANCE = 3.0f;
    private static final java.lang.String TAG = "QuantizerWsmeans";
    private int[] mClusterIndices;
    private int[] mClusterPopulations;
    private float[][] mClusters;
    private java.util.Map<java.lang.Integer, java.lang.Integer> mInputPixelToCount;
    private com.android.internal.graphics.palette.Palette mPalette;
    private int[] mPixels;
    private final com.android.internal.graphics.palette.PointProvider mPointProvider;
    private float[][] mPoints;
    private int[][] mIndexMatrix = new int[0][];
    private float[][] mDistanceMatrix = new float[0][];

    public WSMeansQuantizer(int[] iArr, com.android.internal.graphics.palette.PointProvider pointProvider, java.util.Map<java.lang.Integer, java.lang.Integer> map) {
        int i = 0;
        this.mPointProvider = pointProvider;
        this.mClusters = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, iArr.length, 3);
        int length = iArr.length;
        int i2 = 0;
        while (i < length) {
            this.mClusters[i2] = pointProvider.fromInt(iArr[i]);
            i++;
            i2++;
        }
        this.mInputPixelToCount = map;
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getQuantizedColors() {
        return this.mPalette.getSwatches();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        if (this.mInputPixelToCount == null) {
            com.android.internal.graphics.palette.QuantizerMap quantizerMap = new com.android.internal.graphics.palette.QuantizerMap();
            quantizerMap.quantize(iArr, i);
            this.mInputPixelToCount = quantizerMap.getColorToCount();
        }
        this.mPoints = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, this.mInputPixelToCount.size(), 3);
        this.mPixels = new int[this.mInputPixelToCount.size()];
        java.util.Iterator<java.lang.Integer> it = this.mInputPixelToCount.keySet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            this.mPixels[i2] = intValue;
            this.mPoints[i2] = this.mPointProvider.fromInt(intValue);
            i2++;
        }
        if (this.mClusters.length > 0) {
            i = java.lang.Math.min(i, this.mClusters.length);
        }
        int min = java.lang.Math.min(i, this.mPoints.length);
        initializeClusters(min);
        for (int i3 = 0; i3 < 10; i3++) {
            calculateClusterDistances(min);
            if (!reassignPoints(min)) {
                break;
            }
            recalculateClusterCenters(min);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i4 = 0; i4 < min; i4++) {
            arrayList.add(new com.android.internal.graphics.palette.Palette.Swatch(this.mPointProvider.toInt(this.mClusters[i4]), this.mClusterPopulations[i4]));
        }
        this.mPalette = com.android.internal.graphics.palette.Palette.from(arrayList);
    }

    private void initializeClusters(int i) {
        if (!(this.mClusters.length > 0)) {
            int length = i - this.mClusters.length;
            java.util.Random random = new java.util.Random(272008L);
            java.util.ArrayList arrayList = new java.util.ArrayList(length);
            java.util.HashSet hashSet = new java.util.HashSet();
            for (int i2 = 0; i2 < length; i2++) {
                int nextInt = random.nextInt(this.mPoints.length);
                while (hashSet.contains(java.lang.Integer.valueOf(nextInt)) && hashSet.size() < this.mPoints.length) {
                    nextInt = random.nextInt(this.mPoints.length);
                }
                hashSet.add(java.lang.Integer.valueOf(nextInt));
                arrayList.add(this.mPoints[nextInt]);
            }
            float[][] fArr = (float[][]) arrayList.toArray();
            float[][] fArr2 = (float[][]) java.util.Arrays.copyOf(this.mClusters, i);
            java.lang.System.arraycopy(fArr, 0, fArr2, fArr2.length, fArr.length);
            this.mClusters = fArr2;
        }
        this.mClusterIndices = new int[this.mPixels.length];
        this.mClusterPopulations = new int[this.mPixels.length];
        java.util.Random random2 = new java.util.Random(272008L);
        for (int i3 = 0; i3 < this.mPixels.length; i3++) {
            this.mClusterIndices[i3] = random2.nextInt(i);
            this.mClusterPopulations[i3] = this.mInputPixelToCount.get(java.lang.Integer.valueOf(this.mPixels[i3])).intValue();
        }
    }

    void calculateClusterDistances(int i) {
        if (this.mDistanceMatrix.length != i) {
            this.mDistanceMatrix = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, i, i);
        }
        int i2 = 0;
        while (i2 <= i) {
            int i3 = i2 + 1;
            for (int i4 = i3; i4 < i; i4++) {
                float distance = this.mPointProvider.distance(this.mClusters[i2], this.mClusters[i4]);
                this.mDistanceMatrix[i4][i2] = distance;
                this.mDistanceMatrix[i2][i4] = distance;
            }
            i2 = i3;
        }
        if (this.mIndexMatrix.length != i) {
            this.mIndexMatrix = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, i, i);
        }
        for (int i5 = 0; i5 < i; i5++) {
            java.util.ArrayList arrayList = new java.util.ArrayList(i);
            for (int i6 = 0; i6 < i; i6++) {
                arrayList.add(new com.android.internal.graphics.palette.WSMeansQuantizer.Distance(i6, this.mDistanceMatrix[i5][i6]));
            }
            arrayList.sort(new java.util.Comparator() { // from class: com.android.internal.graphics.palette.WSMeansQuantizer$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int compare;
                    compare = java.lang.Float.compare(((com.android.internal.graphics.palette.WSMeansQuantizer.Distance) obj).getDistance(), ((com.android.internal.graphics.palette.WSMeansQuantizer.Distance) obj2).getDistance());
                    return compare;
                }
            });
            for (int i7 = 0; i7 < i; i7++) {
                this.mIndexMatrix[i5][i7] = ((com.android.internal.graphics.palette.WSMeansQuantizer.Distance) arrayList.get(i7)).getIndex();
            }
        }
    }

    boolean reassignPoints(int i) {
        boolean z = false;
        for (int i2 = 0; i2 < this.mPoints.length; i2++) {
            float[] fArr = this.mPoints[i2];
            int i3 = this.mClusterIndices[i2];
            float distance = this.mPointProvider.distance(fArr, this.mClusters[i3]);
            float f = distance;
            int i4 = -1;
            for (int i5 = 1; i5 < i; i5++) {
                int i6 = this.mIndexMatrix[i3][i5];
                if (this.mDistanceMatrix[i3][i6] >= 4.0f * distance) {
                    break;
                }
                float distance2 = this.mPointProvider.distance(fArr, this.mClusters[i6]);
                if (distance2 < f) {
                    i4 = i6;
                    f = distance2;
                }
            }
            if (i4 != -1 && ((float) java.lang.Math.abs(java.lang.Math.sqrt(f) - java.lang.Math.sqrt(distance))) > 3.0f) {
                this.mClusterIndices[i2] = i4;
                z = true;
            }
        }
        return z;
    }

    void recalculateClusterCenters(int i) {
        this.mClusterPopulations = new int[i];
        float[] fArr = new float[i];
        float[] fArr2 = new float[i];
        float[] fArr3 = new float[i];
        for (int i2 = 0; i2 < this.mPoints.length; i2++) {
            int i3 = this.mClusterIndices[i2];
            float[] fArr4 = this.mPoints[i2];
            int intValue = this.mInputPixelToCount.get(java.lang.Integer.valueOf(this.mPixels[i2])).intValue();
            int[] iArr = this.mClusterPopulations;
            iArr[i3] = iArr[i3] + intValue;
            float f = intValue;
            fArr[i3] = fArr[i3] + (fArr4[0] * f);
            fArr2[i3] = fArr2[i3] + (fArr4[1] * f);
            fArr3[i3] = fArr3[i3] + (fArr4[2] * f);
        }
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = this.mClusterPopulations[i4];
            float f2 = fArr[i4];
            float f3 = fArr2[i4];
            float f4 = fArr3[i4];
            float f5 = i5;
            this.mClusters[i4][0] = f2 / f5;
            this.mClusters[i4][1] = f3 / f5;
            this.mClusters[i4][2] = f4 / f5;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Distance {
        private final float mDistance;
        private final int mIndex;

        int getIndex() {
            return this.mIndex;
        }

        float getDistance() {
            return this.mDistance;
        }

        Distance(int i, float f) {
            this.mIndex = i;
            this.mDistance = f;
        }
    }
}
