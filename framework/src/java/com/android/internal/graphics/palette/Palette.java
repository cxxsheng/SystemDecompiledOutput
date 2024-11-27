package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public final class Palette {
    static final int DEFAULT_CALCULATE_NUMBER_COLORS = 16;
    static final com.android.internal.graphics.palette.Palette.Filter DEFAULT_FILTER = new com.android.internal.graphics.palette.Palette.Filter() { // from class: com.android.internal.graphics.palette.Palette.1
        private static final float BLACK_MAX_LIGHTNESS = 0.05f;
        private static final float WHITE_MIN_LIGHTNESS = 0.95f;

        @Override // com.android.internal.graphics.palette.Palette.Filter
        public boolean isAllowed(int i, float[] fArr) {
            return (isWhite(fArr) || isBlack(fArr) || isNearRedILine(fArr)) ? false : true;
        }

        private boolean isBlack(float[] fArr) {
            return fArr[2] <= BLACK_MAX_LIGHTNESS;
        }

        private boolean isWhite(float[] fArr) {
            return fArr[2] >= WHITE_MIN_LIGHTNESS;
        }

        private boolean isNearRedILine(float[] fArr) {
            return fArr[0] >= 10.0f && fArr[0] <= 37.0f && fArr[1] <= 0.82f;
        }
    };
    static final int DEFAULT_RESIZE_BITMAP_AREA = 12544;
    static final java.lang.String LOG_TAG = "Palette";
    private final com.android.internal.graphics.palette.Palette.Swatch mDominantSwatch = findDominantSwatch();
    private final java.util.List<com.android.internal.graphics.palette.Palette.Swatch> mSwatches;

    public interface Filter {
        boolean isAllowed(int i, float[] fArr);
    }

    public interface PaletteAsyncListener {
        void onGenerated(com.android.internal.graphics.palette.Palette palette);
    }

    public static com.android.internal.graphics.palette.Palette.Builder from(android.graphics.Bitmap bitmap, com.android.internal.graphics.palette.Quantizer quantizer) {
        return new com.android.internal.graphics.palette.Palette.Builder(bitmap, quantizer);
    }

    public static com.android.internal.graphics.palette.Palette from(java.util.List<com.android.internal.graphics.palette.Palette.Swatch> list) {
        return new com.android.internal.graphics.palette.Palette.Builder(list).generate();
    }

    Palette(java.util.List<com.android.internal.graphics.palette.Palette.Swatch> list) {
        this.mSwatches = list;
    }

    public java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getSwatches() {
        return java.util.Collections.unmodifiableList(this.mSwatches);
    }

    public com.android.internal.graphics.palette.Palette.Swatch getDominantSwatch() {
        return this.mDominantSwatch;
    }

    private com.android.internal.graphics.palette.Palette.Swatch findDominantSwatch() {
        int size = this.mSwatches.size();
        int i = Integer.MIN_VALUE;
        com.android.internal.graphics.palette.Palette.Swatch swatch = null;
        for (int i2 = 0; i2 < size; i2++) {
            com.android.internal.graphics.palette.Palette.Swatch swatch2 = this.mSwatches.get(i2);
            if (swatch2.getPopulation() > i) {
                i = swatch2.getPopulation();
                swatch = swatch2;
            }
        }
        return swatch;
    }

    public static class Swatch {
        private final android.graphics.Color mColor;
        private final int mPopulation;

        public Swatch(int i, int i2) {
            this.mColor = android.graphics.Color.valueOf(i);
            this.mPopulation = i2;
        }

        public int getInt() {
            return this.mColor.toArgb();
        }

        public int getPopulation() {
            return this.mPopulation;
        }

        public java.lang.String toString() {
            return getClass().getSimpleName() + " [" + this.mColor + "] [Population: " + this.mPopulation + ']';
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            com.android.internal.graphics.palette.Palette.Swatch swatch = (com.android.internal.graphics.palette.Palette.Swatch) obj;
            if (this.mPopulation == swatch.mPopulation && this.mColor.toArgb() == swatch.mColor.toArgb()) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.mColor.toArgb() * 31) + this.mPopulation;
        }
    }

    public static class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final android.graphics.Bitmap mBitmap;
        private com.android.internal.graphics.palette.Quantizer mQuantizer;
        private android.graphics.Rect mRegion;
        private final java.util.List<com.android.internal.graphics.palette.Palette.Swatch> mSwatches;
        private int mMaxColors = 16;
        private int mResizeArea = com.android.internal.graphics.palette.Palette.DEFAULT_RESIZE_BITMAP_AREA;
        private int mResizeMaxDimension = -1;

        public Builder(android.graphics.Bitmap bitmap, com.android.internal.graphics.palette.Quantizer quantizer) {
            this.mQuantizer = new com.android.internal.graphics.palette.ColorCutQuantizer();
            if (bitmap == null || bitmap.isRecycled()) {
                throw new java.lang.IllegalArgumentException("Bitmap is not valid");
            }
            this.mSwatches = null;
            this.mBitmap = bitmap;
            this.mQuantizer = quantizer == null ? new com.android.internal.graphics.palette.ColorCutQuantizer() : quantizer;
        }

        public Builder(java.util.List<com.android.internal.graphics.palette.Palette.Swatch> list) {
            this.mQuantizer = new com.android.internal.graphics.palette.ColorCutQuantizer();
            if (list == null || list.isEmpty()) {
                throw new java.lang.IllegalArgumentException("List of Swatches is not valid");
            }
            this.mSwatches = list;
            this.mBitmap = null;
            this.mQuantizer = null;
        }

        public com.android.internal.graphics.palette.Palette.Builder maximumColorCount(int i) {
            this.mMaxColors = i;
            return this;
        }

        @java.lang.Deprecated
        public com.android.internal.graphics.palette.Palette.Builder resizeBitmapSize(int i) {
            this.mResizeMaxDimension = i;
            this.mResizeArea = -1;
            return this;
        }

        public com.android.internal.graphics.palette.Palette.Builder resizeBitmapArea(int i) {
            this.mResizeArea = i;
            this.mResizeMaxDimension = -1;
            return this;
        }

        public com.android.internal.graphics.palette.Palette.Builder setRegion(int i, int i2, int i3, int i4) {
            if (this.mBitmap != null) {
                if (this.mRegion == null) {
                    this.mRegion = new android.graphics.Rect();
                }
                this.mRegion.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                if (!this.mRegion.intersect(i, i2, i3, i4)) {
                    throw new java.lang.IllegalArgumentException("The given region must intersect with the Bitmap's dimensions.");
                }
            }
            return this;
        }

        public com.android.internal.graphics.palette.Palette.Builder clearRegion() {
            this.mRegion = null;
            return this;
        }

        public com.android.internal.graphics.palette.Palette generate() {
            java.util.List<com.android.internal.graphics.palette.Palette.Swatch> list;
            if (this.mBitmap != null) {
                android.graphics.Bitmap scaleBitmapDown = scaleBitmapDown(this.mBitmap);
                android.graphics.Rect rect = this.mRegion;
                if (scaleBitmapDown != this.mBitmap && rect != null) {
                    double width = scaleBitmapDown.getWidth() / this.mBitmap.getWidth();
                    rect.left = (int) java.lang.Math.floor(rect.left * width);
                    rect.top = (int) java.lang.Math.floor(rect.top * width);
                    rect.right = java.lang.Math.min((int) java.lang.Math.ceil(rect.right * width), scaleBitmapDown.getWidth());
                    rect.bottom = java.lang.Math.min((int) java.lang.Math.ceil(rect.bottom * width), scaleBitmapDown.getHeight());
                }
                this.mQuantizer.quantize(getPixelsFromBitmap(scaleBitmapDown), this.mMaxColors);
                if (scaleBitmapDown != this.mBitmap) {
                    scaleBitmapDown.recycle();
                }
                list = this.mQuantizer.getQuantizedColors();
            } else if (this.mSwatches != null) {
                list = this.mSwatches;
            } else {
                throw new java.lang.AssertionError();
            }
            return new com.android.internal.graphics.palette.Palette(list);
        }

        @java.lang.Deprecated
        public android.os.AsyncTask<android.graphics.Bitmap, java.lang.Void, com.android.internal.graphics.palette.Palette> generate(final com.android.internal.graphics.palette.Palette.PaletteAsyncListener paletteAsyncListener) {
            return new android.os.AsyncTask<android.graphics.Bitmap, java.lang.Void, com.android.internal.graphics.palette.Palette>() { // from class: com.android.internal.graphics.palette.Palette.Builder.1
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public com.android.internal.graphics.palette.Palette doInBackground(android.graphics.Bitmap... bitmapArr) {
                    try {
                        return com.android.internal.graphics.palette.Palette.Builder.this.generate();
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(com.android.internal.graphics.palette.Palette.LOG_TAG, "Exception thrown during async generate", e);
                        return null;
                    }
                }

                /* JADX INFO: Access modifiers changed from: protected */
                @Override // android.os.AsyncTask
                public void onPostExecute(com.android.internal.graphics.palette.Palette palette) {
                    paletteAsyncListener.onGenerated(palette);
                }
            }.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, this.mBitmap);
        }

        private int[] getPixelsFromBitmap(android.graphics.Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] iArr = new int[width * height];
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            if (this.mRegion == null) {
                return iArr;
            }
            int width2 = this.mRegion.width();
            int height2 = this.mRegion.height();
            int[] iArr2 = new int[width2 * height2];
            for (int i = 0; i < height2; i++) {
                java.lang.System.arraycopy(iArr, ((this.mRegion.top + i) * width) + this.mRegion.left, iArr2, i * width2, width2);
            }
            return iArr2;
        }

        private android.graphics.Bitmap scaleBitmapDown(android.graphics.Bitmap bitmap) {
            int max;
            double d = -1.0d;
            if (this.mResizeArea > 0) {
                int width = bitmap.getWidth() * bitmap.getHeight();
                if (width > this.mResizeArea) {
                    d = java.lang.Math.sqrt(this.mResizeArea / width);
                }
            } else if (this.mResizeMaxDimension > 0 && (max = java.lang.Math.max(bitmap.getWidth(), bitmap.getHeight())) > this.mResizeMaxDimension) {
                d = this.mResizeMaxDimension / max;
            }
            if (d <= 0.0d) {
                return bitmap;
            }
            return android.graphics.Bitmap.createScaledBitmap(bitmap, (int) java.lang.Math.ceil(bitmap.getWidth() * d), (int) java.lang.Math.ceil(bitmap.getHeight() * d), false);
        }
    }
}
