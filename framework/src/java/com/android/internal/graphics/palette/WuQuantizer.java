package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public final class WuQuantizer implements com.android.internal.graphics.palette.Quantizer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BITS = 5;
    private static final int MAX_INDEX = 32;
    private static final int SIDE_LENGTH = 33;
    private static final int TOTAL_SIZE = 35937;
    private int[] mColors;
    private com.android.internal.graphics.palette.WuQuantizer.Box[] mCubes;
    private java.util.Map<java.lang.Integer, java.lang.Integer> mInputPixelToCount;
    private double[] mMoments;
    private int[] mMomentsB;
    private int[] mMomentsG;
    private int[] mMomentsR;
    private com.android.internal.graphics.palette.Palette mPalette;
    private int[] mWeights;

    private enum Direction {
        RED,
        GREEN,
        BLUE
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getQuantizedColors() {
        return this.mPalette.getSwatches();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        com.android.internal.graphics.palette.QuantizerMap quantizerMap = new com.android.internal.graphics.palette.QuantizerMap();
        quantizerMap.quantize(iArr, i);
        this.mInputPixelToCount = quantizerMap.getColorToCount();
        java.util.Set<java.lang.Integer> keySet = this.mInputPixelToCount.keySet();
        if (keySet.size() <= i) {
            this.mColors = new int[this.mInputPixelToCount.keySet().size()];
            java.util.Iterator<java.lang.Integer> it = keySet.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                this.mColors[i2] = it.next().intValue();
                i2++;
            }
        } else {
            constructHistogram(this.mInputPixelToCount);
            createMoments();
            this.mColors = createResult(createBoxes(i).mResultCount);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i3 : this.mColors) {
            arrayList.add(new com.android.internal.graphics.palette.Palette.Swatch(i3, 0));
        }
        this.mPalette = com.android.internal.graphics.palette.Palette.from(arrayList);
    }

    public int[] getColors() {
        return this.mColors;
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> inputPixelToCount() {
        return this.mInputPixelToCount;
    }

    private static int getIndex(int i, int i2, int i3) {
        return (i << 10) + (i << 6) + (i2 << 5) + i + i2 + i3;
    }

    private void constructHistogram(java.util.Map<java.lang.Integer, java.lang.Integer> map) {
        this.mWeights = new int[TOTAL_SIZE];
        this.mMomentsR = new int[TOTAL_SIZE];
        this.mMomentsG = new int[TOTAL_SIZE];
        this.mMomentsB = new int[TOTAL_SIZE];
        this.mMoments = new double[TOTAL_SIZE];
        for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : map.entrySet()) {
            int intValue = entry.getKey().intValue();
            int intValue2 = entry.getValue().intValue();
            int red = android.graphics.Color.red(intValue);
            int green = android.graphics.Color.green(intValue);
            int blue = android.graphics.Color.blue(intValue);
            int index = getIndex((red >> 3) + 1, (green >> 3) + 1, (blue >> 3) + 1);
            int[] iArr = this.mWeights;
            iArr[index] = iArr[index] + intValue2;
            int[] iArr2 = this.mMomentsR;
            iArr2[index] = iArr2[index] + (red * intValue2);
            int[] iArr3 = this.mMomentsG;
            iArr3[index] = iArr3[index] + (green * intValue2);
            int[] iArr4 = this.mMomentsB;
            iArr4[index] = iArr4[index] + (blue * intValue2);
            double[] dArr = this.mMoments;
            dArr[index] = dArr[index] + (intValue2 * ((red * red) + (green * green) + (blue * blue)));
        }
    }

    private void createMoments() {
        int i = 1;
        while (true) {
            int i2 = 33;
            if (i < 33) {
                int[] iArr = new int[33];
                int[] iArr2 = new int[33];
                int[] iArr3 = new int[33];
                int[] iArr4 = new int[33];
                double[] dArr = new double[33];
                int i3 = 1;
                while (i3 < i2) {
                    int i4 = 0;
                    int i5 = 0;
                    double d = 0.0d;
                    int i6 = 1;
                    int i7 = 0;
                    int i8 = 0;
                    while (i6 < i2) {
                        int index = getIndex(i, i3, i6);
                        int i9 = i4 + this.mWeights[index];
                        int i10 = i7 + this.mMomentsR[index];
                        i8 += this.mMomentsG[index];
                        i5 += this.mMomentsB[index];
                        d += this.mMoments[index];
                        iArr[i6] = iArr[i6] + i9;
                        iArr2[i6] = iArr2[i6] + i10;
                        iArr3[i6] = iArr3[i6] + i8;
                        iArr4[i6] = iArr4[i6] + i5;
                        dArr[i6] = dArr[i6] + d;
                        int index2 = getIndex(i - 1, i3, i6);
                        this.mWeights[index] = this.mWeights[index2] + iArr[i6];
                        this.mMomentsR[index] = this.mMomentsR[index2] + iArr2[i6];
                        this.mMomentsG[index] = this.mMomentsG[index2] + iArr3[i6];
                        this.mMomentsB[index] = this.mMomentsB[index2] + iArr4[i6];
                        this.mMoments[index] = this.mMoments[index2] + dArr[i6];
                        i6++;
                        i4 = i9;
                        i7 = i10;
                        i2 = 33;
                    }
                    i3++;
                    i2 = 33;
                }
                i++;
            } else {
                return;
            }
        }
    }

    private com.android.internal.graphics.palette.WuQuantizer.CreateBoxesResult createBoxes(int i) {
        this.mCubes = new com.android.internal.graphics.palette.WuQuantizer.Box[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mCubes[i2] = new com.android.internal.graphics.palette.WuQuantizer.Box();
        }
        double[] dArr = new double[i];
        com.android.internal.graphics.palette.WuQuantizer.Box box = this.mCubes[0];
        box.r1 = 32;
        box.g1 = 32;
        box.b1 = 32;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        while (true) {
            if (i5 >= i) {
                break;
            }
            if (cut(this.mCubes[i3], this.mCubes[i5])) {
                dArr[i3] = this.mCubes[i3].vol > 1 ? variance(this.mCubes[i3]) : 0.0d;
                dArr[i5] = this.mCubes[i5].vol > 1 ? variance(this.mCubes[i5]) : 0.0d;
            } else {
                dArr[i3] = 0.0d;
                i5--;
            }
            double d = dArr[0];
            int i6 = 0;
            for (int i7 = 1; i7 <= i5; i7++) {
                if (dArr[i7] > d) {
                    d = dArr[i7];
                    i6 = i7;
                }
            }
            i5++;
            if (d > 0.0d) {
                i4 = i5;
                i3 = i6;
            } else {
                i4 = i5;
                break;
            }
        }
        return new com.android.internal.graphics.palette.WuQuantizer.CreateBoxesResult(i, i4);
    }

    private int[] createResult(int i) {
        int[] iArr = new int[i];
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            com.android.internal.graphics.palette.WuQuantizer.Box box = this.mCubes[i3];
            int volume = volume(box, this.mWeights);
            if (volume > 0) {
                iArr[i2] = android.graphics.Color.rgb(volume(box, this.mMomentsR) / volume, volume(box, this.mMomentsG) / volume, volume(box, this.mMomentsB) / volume);
                i2++;
            }
        }
        int[] iArr2 = new int[i2];
        java.lang.System.arraycopy(iArr, 0, iArr2, 0, i2);
        return iArr2;
    }

    private double variance(com.android.internal.graphics.palette.WuQuantizer.Box box) {
        int volume = volume(box, this.mMomentsR);
        int volume2 = volume(box, this.mMomentsG);
        int volume3 = volume(box, this.mMomentsB);
        return (((((((this.mMoments[getIndex(box.r1, box.g1, box.b1)] - this.mMoments[getIndex(box.r1, box.g1, box.b0)]) - this.mMoments[getIndex(box.r1, box.g0, box.b1)]) + this.mMoments[getIndex(box.r1, box.g0, box.b0)]) - this.mMoments[getIndex(box.r0, box.g1, box.b1)]) + this.mMoments[getIndex(box.r0, box.g1, box.b0)]) + this.mMoments[getIndex(box.r0, box.g0, box.b1)]) - this.mMoments[getIndex(box.r0, box.g0, box.b0)]) - ((((volume * volume) + (volume2 * volume2)) + (volume3 * volume3)) / volume(box, this.mWeights));
    }

    private boolean cut(com.android.internal.graphics.palette.WuQuantizer.Box box, com.android.internal.graphics.palette.WuQuantizer.Box box2) {
        com.android.internal.graphics.palette.WuQuantizer.Direction direction;
        int volume = volume(box, this.mMomentsR);
        int volume2 = volume(box, this.mMomentsG);
        int volume3 = volume(box, this.mMomentsB);
        int volume4 = volume(box, this.mWeights);
        com.android.internal.graphics.palette.WuQuantizer.MaximizeResult maximize = maximize(box, com.android.internal.graphics.palette.WuQuantizer.Direction.RED, box.r0 + 1, box.r1, volume, volume2, volume3, volume4);
        com.android.internal.graphics.palette.WuQuantizer.MaximizeResult maximize2 = maximize(box, com.android.internal.graphics.palette.WuQuantizer.Direction.GREEN, box.g0 + 1, box.g1, volume, volume2, volume3, volume4);
        com.android.internal.graphics.palette.WuQuantizer.MaximizeResult maximize3 = maximize(box, com.android.internal.graphics.palette.WuQuantizer.Direction.BLUE, box.b0 + 1, box.b1, volume, volume2, volume3, volume4);
        double d = maximize.mMaximum;
        double d2 = maximize2.mMaximum;
        double d3 = maximize3.mMaximum;
        if (d < d2 || d < d3) {
            if (d2 >= d && d2 >= d3) {
                direction = com.android.internal.graphics.palette.WuQuantizer.Direction.GREEN;
            } else {
                direction = com.android.internal.graphics.palette.WuQuantizer.Direction.BLUE;
            }
        } else {
            if (maximize.mCutLocation < 0) {
                return false;
            }
            direction = com.android.internal.graphics.palette.WuQuantizer.Direction.RED;
        }
        box2.r1 = box.r1;
        box2.g1 = box.g1;
        box2.b1 = box.b1;
        switch (direction) {
            case RED:
                box.r1 = maximize.mCutLocation;
                box2.r0 = box.r1;
                box2.g0 = box.g0;
                box2.b0 = box.b0;
                break;
            case GREEN:
                box.g1 = maximize2.mCutLocation;
                box2.r0 = box.r0;
                box2.g0 = box.g1;
                box2.b0 = box.b0;
                break;
            case BLUE:
                box.b1 = maximize3.mCutLocation;
                box2.r0 = box.r0;
                box2.g0 = box.g0;
                box2.b0 = box.b1;
                break;
            default:
                throw new java.lang.IllegalArgumentException("unexpected direction " + direction);
        }
        box.vol = (box.r1 - box.r0) * (box.g1 - box.g0) * (box.b1 - box.b0);
        box2.vol = (box2.r1 - box2.r0) * (box2.g1 - box2.g0) * (box2.b1 - box2.b0);
        return true;
    }

    private com.android.internal.graphics.palette.WuQuantizer.MaximizeResult maximize(com.android.internal.graphics.palette.WuQuantizer.Box box, com.android.internal.graphics.palette.WuQuantizer.Direction direction, int i, int i2, int i3, int i4, int i5, int i6) {
        int i7;
        com.android.internal.graphics.palette.WuQuantizer wuQuantizer = this;
        com.android.internal.graphics.palette.WuQuantizer.Box box2 = box;
        com.android.internal.graphics.palette.WuQuantizer.Direction direction2 = direction;
        int bottom = bottom(box2, direction2, wuQuantizer.mMomentsR);
        int bottom2 = bottom(box2, direction2, wuQuantizer.mMomentsG);
        int bottom3 = bottom(box2, direction2, wuQuantizer.mMomentsB);
        int bottom4 = bottom(box2, direction2, wuQuantizer.mWeights);
        int i8 = -1;
        double d = 0.0d;
        int i9 = i;
        while (i9 < i2) {
            int pVar = top(box2, direction2, i9, wuQuantizer.mMomentsR) + bottom;
            int pVar2 = top(box2, direction2, i9, wuQuantizer.mMomentsG) + bottom2;
            int pVar3 = top(box2, direction2, i9, wuQuantizer.mMomentsB) + bottom3;
            int pVar4 = top(box2, direction2, i9, wuQuantizer.mWeights) + bottom4;
            if (pVar4 == 0) {
                i7 = bottom;
            } else {
                i7 = bottom;
                double d2 = (((pVar * pVar) + (pVar2 * pVar2)) + (pVar3 * pVar3)) / pVar4;
                int i10 = i3 - pVar;
                int i11 = i4 - pVar2;
                int i12 = i5 - pVar3;
                int i13 = i6 - pVar4;
                if (i13 != 0) {
                    double d3 = d2 + ((((i10 * i10) + (i11 * i11)) + (i12 * i12)) / i13);
                    if (d3 > d) {
                        d = d3;
                        i8 = i9;
                    }
                }
            }
            i9++;
            wuQuantizer = this;
            box2 = box;
            direction2 = direction;
            bottom = i7;
        }
        return new com.android.internal.graphics.palette.WuQuantizer.MaximizeResult(i8, d);
    }

    private static int volume(com.android.internal.graphics.palette.WuQuantizer.Box box, int[] iArr) {
        return ((((((iArr[getIndex(box.r1, box.g1, box.b1)] - iArr[getIndex(box.r1, box.g1, box.b0)]) - iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)]) - iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)]) + iArr[getIndex(box.r0, box.g0, box.b1)]) - iArr[getIndex(box.r0, box.g0, box.b0)];
    }

    private static int bottom(com.android.internal.graphics.palette.WuQuantizer.Box box, com.android.internal.graphics.palette.WuQuantizer.Direction direction, int[] iArr) {
        switch (direction) {
            case RED:
                return (((-iArr[getIndex(box.r0, box.g1, box.b1)]) + iArr[getIndex(box.r0, box.g1, box.b0)]) + iArr[getIndex(box.r0, box.g0, box.b1)]) - iArr[getIndex(box.r0, box.g0, box.b0)];
            case GREEN:
                return (((-iArr[getIndex(box.r1, box.g0, box.b1)]) + iArr[getIndex(box.r1, box.g0, box.b0)]) + iArr[getIndex(box.r0, box.g0, box.b1)]) - iArr[getIndex(box.r0, box.g0, box.b0)];
            case BLUE:
                return (((-iArr[getIndex(box.r1, box.g1, box.b0)]) + iArr[getIndex(box.r1, box.g0, box.b0)]) + iArr[getIndex(box.r0, box.g1, box.b0)]) - iArr[getIndex(box.r0, box.g0, box.b0)];
            default:
                throw new java.lang.IllegalArgumentException("unexpected direction " + direction);
        }
    }

    private static int top(com.android.internal.graphics.palette.WuQuantizer.Box box, com.android.internal.graphics.palette.WuQuantizer.Direction direction, int i, int[] iArr) {
        switch (direction) {
            case RED:
                return ((iArr[getIndex(i, box.g1, box.b1)] - iArr[getIndex(i, box.g1, box.b0)]) - iArr[getIndex(i, box.g0, box.b1)]) + iArr[getIndex(i, box.g0, box.b0)];
            case GREEN:
                return ((iArr[getIndex(box.r1, i, box.b1)] - iArr[getIndex(box.r1, i, box.b0)]) - iArr[getIndex(box.r0, i, box.b1)]) + iArr[getIndex(box.r0, i, box.b0)];
            case BLUE:
                return ((iArr[getIndex(box.r1, box.g1, i)] - iArr[getIndex(box.r1, box.g0, i)]) - iArr[getIndex(box.r0, box.g1, i)]) + iArr[getIndex(box.r0, box.g0, i)];
            default:
                throw new java.lang.IllegalArgumentException("unexpected direction " + direction);
        }
    }

    private static class MaximizeResult {
        final int mCutLocation;
        final double mMaximum;

        MaximizeResult(int i, double d) {
            this.mCutLocation = i;
            this.mMaximum = d;
        }
    }

    private static class CreateBoxesResult {
        final int mRequestedCount;
        final int mResultCount;

        CreateBoxesResult(int i, int i2) {
            this.mRequestedCount = i;
            this.mResultCount = i2;
        }
    }

    private static class Box {
        public int b0;
        public int b1;
        public int g0;
        public int g1;
        public int r0;
        public int r1;
        public int vol;

        private Box() {
            this.r0 = 0;
            this.r1 = 0;
            this.g0 = 0;
            this.g1 = 0;
            this.b0 = 0;
            this.b1 = 0;
            this.vol = 0;
        }
    }
}
