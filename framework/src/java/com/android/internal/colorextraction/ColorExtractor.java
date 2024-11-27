package com.android.internal.colorextraction;

/* loaded from: classes4.dex */
public class ColorExtractor implements android.app.WallpaperManager.OnColorsChangedListener {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ColorExtractor";
    public static final int TYPE_DARK = 1;
    public static final int TYPE_EXTRA_DARK = 2;
    public static final int TYPE_NORMAL = 0;
    private static final int[] sGradientTypes = {0, 1, 2};
    private final android.content.Context mContext;
    private final com.android.internal.colorextraction.types.ExtractionType mExtractionType;
    protected final android.util.SparseArray<com.android.internal.colorextraction.ColorExtractor.GradientColors[]> mGradientColors;
    protected android.app.WallpaperColors mLockColors;
    private final java.util.ArrayList<java.lang.ref.WeakReference<com.android.internal.colorextraction.ColorExtractor.OnColorsChangedListener>> mOnColorsChangedListeners;
    protected android.app.WallpaperColors mSystemColors;

    public interface OnColorsChangedListener {
        void onColorsChanged(com.android.internal.colorextraction.ColorExtractor colorExtractor, int i);
    }

    public ColorExtractor(android.content.Context context) {
        this(context, new com.android.internal.colorextraction.types.Tonal(context), true, (android.app.WallpaperManager) context.getSystemService(android.app.WallpaperManager.class));
    }

    public ColorExtractor(android.content.Context context, com.android.internal.colorextraction.types.ExtractionType extractionType, boolean z, android.app.WallpaperManager wallpaperManager) {
        this.mContext = context;
        this.mExtractionType = extractionType;
        this.mGradientColors = new android.util.SparseArray<>();
        int[] iArr = {2, 1};
        for (int i = 0; i < 2; i++) {
            int i2 = iArr[i];
            com.android.internal.colorextraction.ColorExtractor.GradientColors[] gradientColorsArr = new com.android.internal.colorextraction.ColorExtractor.GradientColors[sGradientTypes.length];
            this.mGradientColors.append(i2, gradientColorsArr);
            for (int i3 : sGradientTypes) {
                gradientColorsArr[i3] = new com.android.internal.colorextraction.ColorExtractor.GradientColors();
            }
        }
        this.mOnColorsChangedListeners = new java.util.ArrayList<>();
        if (wallpaperManager.isWallpaperSupported()) {
            wallpaperManager.addOnColorsChangedListener(this, null);
            initExtractColors(wallpaperManager, z);
        }
    }

    private void initExtractColors(android.app.WallpaperManager wallpaperManager, boolean z) {
        if (!z) {
            new com.android.internal.colorextraction.ColorExtractor.LoadWallpaperColors().executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR, wallpaperManager);
            return;
        }
        this.mSystemColors = wallpaperManager.getWallpaperColors(1);
        this.mLockColors = wallpaperManager.getWallpaperColors(2);
        extractWallpaperColors();
    }

    private class LoadWallpaperColors extends android.os.AsyncTask<android.app.WallpaperManager, java.lang.Void, java.lang.Void> {
        private android.app.WallpaperColors mLockColors;
        private android.app.WallpaperColors mSystemColors;

        private LoadWallpaperColors() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public java.lang.Void doInBackground(android.app.WallpaperManager... wallpaperManagerArr) {
            this.mSystemColors = wallpaperManagerArr[0].getWallpaperColors(1);
            this.mLockColors = wallpaperManagerArr[0].getWallpaperColors(2);
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(java.lang.Void r2) {
            com.android.internal.colorextraction.ColorExtractor.this.mSystemColors = this.mSystemColors;
            com.android.internal.colorextraction.ColorExtractor.this.mLockColors = this.mLockColors;
            com.android.internal.colorextraction.ColorExtractor.this.extractWallpaperColors();
            com.android.internal.colorextraction.ColorExtractor.this.triggerColorsChanged(3);
        }
    }

    protected void extractWallpaperColors() {
        com.android.internal.colorextraction.ColorExtractor.GradientColors[] gradientColorsArr = this.mGradientColors.get(1);
        com.android.internal.colorextraction.ColorExtractor.GradientColors[] gradientColorsArr2 = this.mGradientColors.get(2);
        extractInto(this.mSystemColors, gradientColorsArr[0], gradientColorsArr[1], gradientColorsArr[2]);
        extractInto(this.mLockColors, gradientColorsArr2[0], gradientColorsArr2[1], gradientColorsArr2[2]);
    }

    public com.android.internal.colorextraction.ColorExtractor.GradientColors getColors(int i) {
        return getColors(i, 1);
    }

    public com.android.internal.colorextraction.ColorExtractor.GradientColors getColors(int i, int i2) {
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            throw new java.lang.IllegalArgumentException("type should be TYPE_NORMAL, TYPE_DARK or TYPE_EXTRA_DARK");
        }
        if (i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("which should be FLAG_SYSTEM or FLAG_NORMAL");
        }
        return this.mGradientColors.get(i)[i2];
    }

    public android.app.WallpaperColors getWallpaperColors(int i) {
        if (i == 2) {
            return this.mLockColors;
        }
        if (i == 1) {
            return this.mSystemColors;
        }
        throw new java.lang.IllegalArgumentException("Invalid value for which: " + i);
    }

    @Override // android.app.WallpaperManager.OnColorsChangedListener
    public void onColorsChanged(android.app.WallpaperColors wallpaperColors, int i) {
        boolean z;
        boolean z2 = true;
        if ((i & 2) == 0) {
            z = false;
        } else {
            this.mLockColors = wallpaperColors;
            com.android.internal.colorextraction.ColorExtractor.GradientColors[] gradientColorsArr = this.mGradientColors.get(2);
            extractInto(wallpaperColors, gradientColorsArr[0], gradientColorsArr[1], gradientColorsArr[2]);
            z = true;
        }
        if ((i & 1) == 0) {
            z2 = z;
        } else {
            this.mSystemColors = wallpaperColors;
            com.android.internal.colorextraction.ColorExtractor.GradientColors[] gradientColorsArr2 = this.mGradientColors.get(1);
            extractInto(wallpaperColors, gradientColorsArr2[0], gradientColorsArr2[1], gradientColorsArr2[2]);
        }
        if (z2) {
            triggerColorsChanged(i);
        }
    }

    protected void triggerColorsChanged(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mOnColorsChangedListeners);
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            java.lang.ref.WeakReference weakReference = (java.lang.ref.WeakReference) arrayList.get(i2);
            com.android.internal.colorextraction.ColorExtractor.OnColorsChangedListener onColorsChangedListener = (com.android.internal.colorextraction.ColorExtractor.OnColorsChangedListener) weakReference.get();
            if (onColorsChangedListener == null) {
                this.mOnColorsChangedListeners.remove(weakReference);
            } else {
                onColorsChangedListener.onColorsChanged(this, i);
            }
        }
    }

    private void extractInto(android.app.WallpaperColors wallpaperColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors2, com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors3) {
        this.mExtractionType.extractInto(wallpaperColors, gradientColors, gradientColors2, gradientColors3);
    }

    public void destroy() {
        android.app.WallpaperManager wallpaperManager = (android.app.WallpaperManager) this.mContext.getSystemService(android.app.WallpaperManager.class);
        if (wallpaperManager != null) {
            wallpaperManager.removeOnColorsChangedListener(this);
        }
    }

    public void addOnColorsChangedListener(com.android.internal.colorextraction.ColorExtractor.OnColorsChangedListener onColorsChangedListener) {
        this.mOnColorsChangedListeners.add(new java.lang.ref.WeakReference<>(onColorsChangedListener));
    }

    public void removeOnColorsChangedListener(com.android.internal.colorextraction.ColorExtractor.OnColorsChangedListener onColorsChangedListener) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mOnColorsChangedListeners);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            java.lang.ref.WeakReference weakReference = (java.lang.ref.WeakReference) arrayList.get(i);
            if (weakReference.get() == onColorsChangedListener) {
                this.mOnColorsChangedListeners.remove(weakReference);
                return;
            }
        }
    }

    public static class GradientColors {
        private int[] mColorPalette;
        private int mMainColor;
        private int mSecondaryColor;
        private boolean mSupportsDarkText;

        public void setMainColor(int i) {
            this.mMainColor = i;
        }

        public void setSecondaryColor(int i) {
            this.mSecondaryColor = i;
        }

        public void setColorPalette(int[] iArr) {
            this.mColorPalette = iArr;
        }

        public void setSupportsDarkText(boolean z) {
            this.mSupportsDarkText = z;
        }

        public void set(com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors) {
            this.mMainColor = gradientColors.mMainColor;
            this.mSecondaryColor = gradientColors.mSecondaryColor;
            this.mColorPalette = gradientColors.mColorPalette;
            this.mSupportsDarkText = gradientColors.mSupportsDarkText;
        }

        public int getMainColor() {
            return this.mMainColor;
        }

        public int getSecondaryColor() {
            return this.mSecondaryColor;
        }

        public int[] getColorPalette() {
            return this.mColorPalette;
        }

        public boolean supportsDarkText() {
            return this.mSupportsDarkText;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            com.android.internal.colorextraction.ColorExtractor.GradientColors gradientColors = (com.android.internal.colorextraction.ColorExtractor.GradientColors) obj;
            return gradientColors.mMainColor == this.mMainColor && gradientColors.mSecondaryColor == this.mSecondaryColor && gradientColors.mSupportsDarkText == this.mSupportsDarkText;
        }

        public int hashCode() {
            return (((this.mMainColor * 31) + this.mSecondaryColor) * 31) + (!this.mSupportsDarkText ? 1 : 0);
        }

        public java.lang.String toString() {
            return "GradientColors(" + java.lang.Integer.toHexString(this.mMainColor) + ", " + java.lang.Integer.toHexString(this.mSecondaryColor) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
