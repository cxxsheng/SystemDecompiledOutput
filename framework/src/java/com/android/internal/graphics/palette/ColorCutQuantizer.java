package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
final class ColorCutQuantizer implements com.android.internal.graphics.palette.Quantizer {
    static final int COMPONENT_BLUE = -1;
    static final int COMPONENT_GREEN = -2;
    static final int COMPONENT_RED = -3;
    private static final java.lang.String LOG_TAG = "ColorCutQuantizer";
    private static final boolean LOG_TIMINGS = false;
    private static final int QUANTIZE_WORD_MASK = 31;
    private static final int QUANTIZE_WORD_WIDTH = 5;
    private static final java.util.Comparator<com.android.internal.graphics.palette.ColorCutQuantizer.Vbox> VBOX_COMPARATOR_VOLUME = new java.util.Comparator<com.android.internal.graphics.palette.ColorCutQuantizer.Vbox>() { // from class: com.android.internal.graphics.palette.ColorCutQuantizer.1
        @Override // java.util.Comparator
        public int compare(com.android.internal.graphics.palette.ColorCutQuantizer.Vbox vbox, com.android.internal.graphics.palette.ColorCutQuantizer.Vbox vbox2) {
            return vbox2.getVolume() - vbox.getVolume();
        }
    };
    int[] mColors;
    int[] mHistogram;
    java.util.List<com.android.internal.graphics.palette.Palette.Swatch> mQuantizedColors;
    private final float[] mTempHsl = new float[3];
    android.util.TimingLogger mTimingLogger;

    ColorCutQuantizer() {
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        this.mTimingLogger = null;
        int[] iArr2 = new int[32768];
        this.mHistogram = iArr2;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int quantizeFromRgb888 = quantizeFromRgb888(iArr[i2]);
            iArr[i2] = quantizeFromRgb888;
            iArr2[quantizeFromRgb888] = iArr2[quantizeFromRgb888] + 1;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < 32768; i4++) {
            if (iArr2[i4] > 0) {
                i3++;
            }
        }
        int[] iArr3 = new int[i3];
        this.mColors = iArr3;
        int i5 = 0;
        for (int i6 = 0; i6 < 32768; i6++) {
            if (iArr2[i6] > 0) {
                iArr3[i5] = i6;
                i5++;
            }
        }
        if (i3 <= i) {
            this.mQuantizedColors = new java.util.ArrayList();
            for (int i7 = 0; i7 < i3; i7++) {
                int i8 = iArr3[i7];
                this.mQuantizedColors.add(new com.android.internal.graphics.palette.Palette.Swatch(approximateToRgb888(i8), iArr2[i8]));
            }
            return;
        }
        this.mQuantizedColors = quantizePixels(i);
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getQuantizedColors() {
        return this.mQuantizedColors;
    }

    private java.util.List<com.android.internal.graphics.palette.Palette.Swatch> quantizePixels(int i) {
        java.util.PriorityQueue<com.android.internal.graphics.palette.ColorCutQuantizer.Vbox> priorityQueue = new java.util.PriorityQueue<>(i, VBOX_COMPARATOR_VOLUME);
        priorityQueue.offer(new com.android.internal.graphics.palette.ColorCutQuantizer.Vbox(0, this.mColors.length - 1));
        splitBoxes(priorityQueue, i);
        return generateAverageColors(priorityQueue);
    }

    private void splitBoxes(java.util.PriorityQueue<com.android.internal.graphics.palette.ColorCutQuantizer.Vbox> priorityQueue, int i) {
        com.android.internal.graphics.palette.ColorCutQuantizer.Vbox poll;
        while (priorityQueue.size() < i && (poll = priorityQueue.poll()) != null && poll.canSplit()) {
            priorityQueue.offer(poll.splitBox());
            priorityQueue.offer(poll);
        }
    }

    private java.util.List<com.android.internal.graphics.palette.Palette.Swatch> generateAverageColors(java.util.Collection<com.android.internal.graphics.palette.ColorCutQuantizer.Vbox> collection) {
        java.util.ArrayList arrayList = new java.util.ArrayList(collection.size());
        java.util.Iterator<com.android.internal.graphics.palette.ColorCutQuantizer.Vbox> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getAverageColor());
        }
        return arrayList;
    }

    private class Vbox {
        private final int mLowerIndex;
        private int mMaxBlue;
        private int mMaxGreen;
        private int mMaxRed;
        private int mMinBlue;
        private int mMinGreen;
        private int mMinRed;
        private int mPopulation;
        private int mUpperIndex;

        Vbox(int i, int i2) {
            this.mLowerIndex = i;
            this.mUpperIndex = i2;
            fitBox();
        }

        final int getVolume() {
            return ((this.mMaxRed - this.mMinRed) + 1) * ((this.mMaxGreen - this.mMinGreen) + 1) * ((this.mMaxBlue - this.mMinBlue) + 1);
        }

        final boolean canSplit() {
            return getColorCount() > 1;
        }

        final int getColorCount() {
            return (this.mUpperIndex + 1) - this.mLowerIndex;
        }

        final void fitBox() {
            int[] iArr = com.android.internal.graphics.palette.ColorCutQuantizer.this.mColors;
            int[] iArr2 = com.android.internal.graphics.palette.ColorCutQuantizer.this.mHistogram;
            int i = Integer.MAX_VALUE;
            int i2 = Integer.MIN_VALUE;
            int i3 = Integer.MIN_VALUE;
            int i4 = Integer.MIN_VALUE;
            int i5 = 0;
            int i6 = Integer.MAX_VALUE;
            int i7 = Integer.MAX_VALUE;
            for (int i8 = this.mLowerIndex; i8 <= this.mUpperIndex; i8++) {
                int i9 = iArr[i8];
                i5 += iArr2[i9];
                int quantizedRed = com.android.internal.graphics.palette.ColorCutQuantizer.quantizedRed(i9);
                int quantizedGreen = com.android.internal.graphics.palette.ColorCutQuantizer.quantizedGreen(i9);
                int quantizedBlue = com.android.internal.graphics.palette.ColorCutQuantizer.quantizedBlue(i9);
                if (quantizedRed > i2) {
                    i2 = quantizedRed;
                }
                if (quantizedRed < i) {
                    i = quantizedRed;
                }
                if (quantizedGreen > i3) {
                    i3 = quantizedGreen;
                }
                if (quantizedGreen < i6) {
                    i6 = quantizedGreen;
                }
                if (quantizedBlue > i4) {
                    i4 = quantizedBlue;
                }
                if (quantizedBlue < i7) {
                    i7 = quantizedBlue;
                }
            }
            this.mMinRed = i;
            this.mMaxRed = i2;
            this.mMinGreen = i6;
            this.mMaxGreen = i3;
            this.mMinBlue = i7;
            this.mMaxBlue = i4;
            this.mPopulation = i5;
        }

        final com.android.internal.graphics.palette.ColorCutQuantizer.Vbox splitBox() {
            if (!canSplit()) {
                throw new java.lang.IllegalStateException("Can not split a box with only 1 color");
            }
            int findSplitPoint = findSplitPoint();
            com.android.internal.graphics.palette.ColorCutQuantizer.Vbox vbox = com.android.internal.graphics.palette.ColorCutQuantizer.this.new Vbox(findSplitPoint + 1, this.mUpperIndex);
            this.mUpperIndex = findSplitPoint;
            fitBox();
            return vbox;
        }

        final int getLongestColorDimension() {
            int i = this.mMaxRed - this.mMinRed;
            int i2 = this.mMaxGreen - this.mMinGreen;
            int i3 = this.mMaxBlue - this.mMinBlue;
            if (i >= i2 && i >= i3) {
                return -3;
            }
            if (i2 >= i && i2 >= i3) {
                return -2;
            }
            return -1;
        }

        final int findSplitPoint() {
            int longestColorDimension = getLongestColorDimension();
            int[] iArr = com.android.internal.graphics.palette.ColorCutQuantizer.this.mColors;
            int[] iArr2 = com.android.internal.graphics.palette.ColorCutQuantizer.this.mHistogram;
            com.android.internal.graphics.palette.ColorCutQuantizer.modifySignificantOctet(iArr, longestColorDimension, this.mLowerIndex, this.mUpperIndex);
            java.util.Arrays.sort(iArr, this.mLowerIndex, this.mUpperIndex + 1);
            com.android.internal.graphics.palette.ColorCutQuantizer.modifySignificantOctet(iArr, longestColorDimension, this.mLowerIndex, this.mUpperIndex);
            int i = this.mPopulation / 2;
            int i2 = 0;
            for (int i3 = this.mLowerIndex; i3 <= this.mUpperIndex; i3++) {
                i2 += iArr2[iArr[i3]];
                if (i2 >= i) {
                    return java.lang.Math.min(this.mUpperIndex - 1, i3);
                }
            }
            return this.mLowerIndex;
        }

        final com.android.internal.graphics.palette.Palette.Swatch getAverageColor() {
            int[] iArr = com.android.internal.graphics.palette.ColorCutQuantizer.this.mColors;
            int[] iArr2 = com.android.internal.graphics.palette.ColorCutQuantizer.this.mHistogram;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = this.mLowerIndex; i5 <= this.mUpperIndex; i5++) {
                int i6 = iArr[i5];
                int i7 = iArr2[i6];
                i2 += i7;
                i += com.android.internal.graphics.palette.ColorCutQuantizer.quantizedRed(i6) * i7;
                i3 += com.android.internal.graphics.palette.ColorCutQuantizer.quantizedGreen(i6) * i7;
                i4 += i7 * com.android.internal.graphics.palette.ColorCutQuantizer.quantizedBlue(i6);
            }
            float f = i2;
            return new com.android.internal.graphics.palette.Palette.Swatch(com.android.internal.graphics.palette.ColorCutQuantizer.approximateToRgb888(java.lang.Math.round(i / f), java.lang.Math.round(i3 / f), java.lang.Math.round(i4 / f)), i2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static void modifySignificantOctet(int[] iArr, int i, int i2, int i3) {
        switch (i) {
            case -2:
                while (i2 <= i3) {
                    int i4 = iArr[i2];
                    iArr[i2] = quantizedBlue(i4) | (quantizedGreen(i4) << 10) | (quantizedRed(i4) << 5);
                    i2++;
                }
                break;
            case -1:
                while (i2 <= i3) {
                    int i5 = iArr[i2];
                    iArr[i2] = quantizedRed(i5) | (quantizedBlue(i5) << 10) | (quantizedGreen(i5) << 5);
                    i2++;
                }
                break;
        }
    }

    private static int quantizeFromRgb888(int i) {
        return modifyWordWidth(android.graphics.Color.blue(i), 8, 5) | (modifyWordWidth(android.graphics.Color.red(i), 8, 5) << 10) | (modifyWordWidth(android.graphics.Color.green(i), 8, 5) << 5);
    }

    static int approximateToRgb888(int i, int i2, int i3) {
        return android.graphics.Color.rgb(modifyWordWidth(i, 5, 8), modifyWordWidth(i2, 5, 8), modifyWordWidth(i3, 5, 8));
    }

    private static int approximateToRgb888(int i) {
        return approximateToRgb888(quantizedRed(i), quantizedGreen(i), quantizedBlue(i));
    }

    static int quantizedRed(int i) {
        return (i >> 10) & 31;
    }

    static int quantizedGreen(int i) {
        return (i >> 5) & 31;
    }

    static int quantizedBlue(int i) {
        return i & 31;
    }

    private static int modifyWordWidth(int i, int i2, int i3) {
        int i4;
        if (i3 > i2) {
            i4 = i << (i3 - i2);
        } else {
            i4 = i >> (i2 - i3);
        }
        return i4 & ((1 << i3) - 1);
    }
}
