package android.content.res;

/* loaded from: classes.dex */
public class Resources {
    public static final int ID_NULL = 0;
    private static final int MAX_THEME_REFS_FLUSH_SIZE = 512;
    private static final int MIN_THEME_REFS_FLUSH_SIZE = 32;
    private static final boolean PRELOAD_RESOURCES = true;
    static final java.lang.String TAG = "Resources";
    private int mBaseApkAssetsSize;
    private android.content.res.Resources.UpdateCallbacks mCallbacks;
    final java.lang.ClassLoader mClassLoader;
    private android.graphics.drawable.DrawableInflater mDrawableInflater;
    private android.content.res.ResourcesImpl mResourcesImpl;
    private final java.util.ArrayList<java.lang.ref.WeakReference<android.content.res.Resources.Theme>> mThemeRefs;
    private int mThemeRefsNextFlushSize;
    private android.util.TypedValue mTmpValue;
    private final java.lang.Object mTmpValueLock;
    final android.util.Pools.SynchronizedPool<android.content.res.TypedArray> mTypedArrayPool;
    private final java.lang.Object mUpdateLock;
    private static final java.lang.Object sSync = new java.lang.Object();
    static android.content.res.Resources mSystem = null;
    private static final java.util.Set<android.content.res.Resources> sResourcesHistory = java.util.Collections.synchronizedSet(java.util.Collections.newSetFromMap(new java.util.WeakHashMap()));

    public interface UpdateCallbacks extends android.content.res.loader.ResourcesLoader.UpdateCallbacks {
        void onLoadersChanged(android.content.res.Resources resources, java.util.List<android.content.res.loader.ResourcesLoader> list);
    }

    public static int selectDefaultTheme(int i, int i2) {
        return selectSystemTheme(i, i2, 16973829, 16973931, 16974120, 16974143);
    }

    public static int selectSystemTheme(int i, int i2, int i3, int i4, int i5, int i6) {
        if (i != 0) {
            return i;
        }
        if (i2 < 11) {
            return i3;
        }
        if (i2 < 14) {
            return i4;
        }
        if (i2 < 24) {
            return i5;
        }
        return i6;
    }

    public static android.content.res.Resources getSystem() {
        android.content.res.Resources resources;
        synchronized (sSync) {
            resources = mSystem;
            if (resources == null) {
                resources = new android.content.res.Resources();
                mSystem = resources;
            }
        }
        return resources;
    }

    public static class NotFoundException extends java.lang.RuntimeException {
        public NotFoundException() {
        }

        public NotFoundException(java.lang.String str) {
            super(str);
        }

        public NotFoundException(java.lang.String str, java.lang.Exception exc) {
            super(str, exc);
        }
    }

    public class AssetManagerUpdateHandler implements android.content.res.Resources.UpdateCallbacks {
        public AssetManagerUpdateHandler() {
        }

        @Override // android.content.res.Resources.UpdateCallbacks
        public void onLoadersChanged(android.content.res.Resources resources, java.util.List<android.content.res.loader.ResourcesLoader> list) {
            com.android.internal.util.Preconditions.checkArgument(android.content.res.Resources.this == resources);
            android.content.res.ResourcesImpl resourcesImpl = android.content.res.Resources.this.mResourcesImpl;
            resourcesImpl.clearAllCaches();
            resourcesImpl.getAssets().setLoaders(list);
        }

        @Override // android.content.res.loader.ResourcesLoader.UpdateCallbacks
        public void onLoaderUpdated(android.content.res.loader.ResourcesLoader resourcesLoader) {
            android.content.res.ResourcesImpl resourcesImpl = android.content.res.Resources.this.mResourcesImpl;
            android.content.res.AssetManager assets = resourcesImpl.getAssets();
            if (assets.getLoaders().contains(resourcesLoader)) {
                resourcesImpl.clearAllCaches();
                assets.setLoaders(assets.getLoaders());
            }
        }
    }

    @java.lang.Deprecated
    public Resources(android.content.res.AssetManager assetManager, android.util.DisplayMetrics displayMetrics, android.content.res.Configuration configuration) {
        this(null);
        this.mResourcesImpl = new android.content.res.ResourcesImpl(assetManager, displayMetrics, configuration, new android.view.DisplayAdjustments());
    }

    public Resources(java.lang.ClassLoader classLoader) {
        this.mUpdateLock = new java.lang.Object();
        this.mTypedArrayPool = new android.util.Pools.SynchronizedPool<>(5);
        this.mTmpValueLock = new java.lang.Object();
        this.mTmpValue = new android.util.TypedValue();
        this.mCallbacks = null;
        this.mThemeRefs = new java.util.ArrayList<>();
        this.mThemeRefsNextFlushSize = 32;
        this.mClassLoader = classLoader == null ? java.lang.ClassLoader.getSystemClassLoader() : classLoader;
        sResourcesHistory.add(this);
    }

    private Resources() {
        this(null);
        android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
        displayMetrics.setToDefaults();
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        configuration.setToDefaults();
        this.mResourcesImpl = new android.content.res.ResourcesImpl(android.content.res.AssetManager.getSystem(), displayMetrics, configuration, new android.view.DisplayAdjustments());
    }

    public void setImpl(android.content.res.ResourcesImpl resourcesImpl) {
        if (resourcesImpl == this.mResourcesImpl) {
            return;
        }
        this.mBaseApkAssetsSize = com.android.internal.util.ArrayUtils.size(resourcesImpl.getAssets().getApkAssets());
        this.mResourcesImpl = resourcesImpl;
        synchronized (this.mThemeRefs) {
            cleanupThemeReferences();
            int size = this.mThemeRefs.size();
            for (int i = 0; i < size; i++) {
                android.content.res.Resources.Theme theme = this.mThemeRefs.get(i).get();
                if (theme != null) {
                    theme.rebase(this.mResourcesImpl);
                }
            }
        }
    }

    public void setCallbacks(android.content.res.Resources.UpdateCallbacks updateCallbacks) {
        if (this.mCallbacks != null) {
            throw new java.lang.IllegalStateException("callback already registered");
        }
        this.mCallbacks = updateCallbacks;
    }

    public android.content.res.ResourcesImpl getImpl() {
        return this.mResourcesImpl;
    }

    public java.lang.ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    public final android.graphics.drawable.DrawableInflater getDrawableInflater() {
        if (this.mDrawableInflater == null) {
            this.mDrawableInflater = new android.graphics.drawable.DrawableInflater(this, this.mClassLoader);
        }
        return this.mDrawableInflater;
    }

    public android.content.res.ConfigurationBoundResourceCache<android.animation.Animator> getAnimatorCache() {
        return this.mResourcesImpl.getAnimatorCache();
    }

    public android.content.res.ConfigurationBoundResourceCache<android.animation.StateListAnimator> getStateListAnimatorCache() {
        return this.mResourcesImpl.getStateListAnimatorCache();
    }

    public java.lang.CharSequence getText(int i) throws android.content.res.Resources.NotFoundException {
        java.lang.CharSequence resourceText = this.mResourcesImpl.getAssets().getResourceText(i);
        if (resourceText != null) {
            return resourceText;
        }
        throw new android.content.res.Resources.NotFoundException("String resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    public android.graphics.Typeface getFont(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, obtainTempTypedValue, true);
            android.graphics.Typeface loadFont = resourcesImpl.loadFont(this, obtainTempTypedValue, i);
            if (loadFont != null) {
                return loadFont;
            }
            releaseTempTypedValue(obtainTempTypedValue);
            throw new android.content.res.Resources.NotFoundException("Font resource ID #0x" + java.lang.Integer.toHexString(i));
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    android.graphics.Typeface getFont(android.util.TypedValue typedValue, int i) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.loadFont(this, typedValue, i);
    }

    public void preloadFonts(int i) {
        android.content.res.TypedArray obtainTypedArray = obtainTypedArray(i);
        try {
            int length = obtainTypedArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                obtainTypedArray.getFont(i2);
            }
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public java.lang.CharSequence getQuantityText(int i, int i2) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.getQuantityText(i, i2);
    }

    public java.lang.String getString(int i) throws android.content.res.Resources.NotFoundException {
        return getText(i).toString();
    }

    public java.lang.String getString(int i, java.lang.Object... objArr) throws android.content.res.Resources.NotFoundException {
        return java.lang.String.format(this.mResourcesImpl.getConfiguration().getLocales().get(0), getString(i), objArr);
    }

    public java.lang.String getQuantityString(int i, int i2, java.lang.Object... objArr) throws android.content.res.Resources.NotFoundException {
        return java.lang.String.format(this.mResourcesImpl.getConfiguration().getLocales().get(0), getQuantityText(i, i2).toString(), objArr);
    }

    public java.lang.String getQuantityString(int i, int i2) throws android.content.res.Resources.NotFoundException {
        return getQuantityText(i, i2).toString();
    }

    public java.lang.CharSequence getText(int i, java.lang.CharSequence charSequence) {
        java.lang.CharSequence resourceText = i != 0 ? this.mResourcesImpl.getAssets().getResourceText(i) : null;
        return resourceText != null ? resourceText : charSequence;
    }

    public java.lang.CharSequence[] getTextArray(int i) throws android.content.res.Resources.NotFoundException {
        java.lang.CharSequence[] resourceTextArray = this.mResourcesImpl.getAssets().getResourceTextArray(i);
        if (resourceTextArray != null) {
            return resourceTextArray;
        }
        throw new android.content.res.Resources.NotFoundException("Text array resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    public java.lang.String[] getStringArray(int i) throws android.content.res.Resources.NotFoundException {
        java.lang.String[] resourceStringArray = this.mResourcesImpl.getAssets().getResourceStringArray(i);
        if (resourceStringArray != null) {
            return resourceStringArray;
        }
        throw new android.content.res.Resources.NotFoundException("String array resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    public int[] getIntArray(int i) throws android.content.res.Resources.NotFoundException {
        int[] resourceIntArray = this.mResourcesImpl.getAssets().getResourceIntArray(i);
        if (resourceIntArray != null) {
            return resourceIntArray;
        }
        throw new android.content.res.Resources.NotFoundException("Int array resource ID #0x" + java.lang.Integer.toHexString(i));
    }

    public android.content.res.TypedArray obtainTypedArray(int i) throws android.content.res.Resources.NotFoundException {
        android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
        int resourceArraySize = resourcesImpl.getAssets().getResourceArraySize(i);
        if (resourceArraySize < 0) {
            throw new android.content.res.Resources.NotFoundException("Array resource ID #0x" + java.lang.Integer.toHexString(i));
        }
        android.content.res.TypedArray obtain = android.content.res.TypedArray.obtain(this, resourceArraySize);
        obtain.mLength = resourcesImpl.getAssets().getResourceArray(i, obtain.mData);
        obtain.mIndices[0] = 0;
        return obtain;
    }

    public float getDimension(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type == 5) {
                return android.util.TypedValue.complexToDimension(obtainTempTypedValue.data, resourcesImpl.getDisplayMetrics());
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    public int getDimensionPixelOffset(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type == 5) {
                return android.util.TypedValue.complexToDimensionPixelOffset(obtainTempTypedValue.data, resourcesImpl.getDisplayMetrics());
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    public int getDimensionPixelSize(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type == 5) {
                return android.util.TypedValue.complexToDimensionPixelSize(obtainTempTypedValue.data, resourcesImpl.getDisplayMetrics());
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    public float getFraction(int i, int i2, int i3) {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type == 6) {
                return android.util.TypedValue.complexToFraction(obtainTempTypedValue.data, i2, i3);
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getDrawable(int i) throws android.content.res.Resources.NotFoundException {
        android.graphics.drawable.Drawable drawable = getDrawable(i, null);
        if (drawable != null && drawable.canApplyTheme()) {
            android.util.Log.w(TAG, "Drawable " + getResourceName(i) + " has unresolved theme attributes! Consider using Resources.getDrawable(int, Theme) or Context.getDrawable(int).", new java.lang.RuntimeException());
        }
        return drawable;
    }

    public android.graphics.drawable.Drawable getDrawable(int i, android.content.res.Resources.Theme theme) throws android.content.res.Resources.NotFoundException {
        return getDrawableForDensity(i, 0, theme);
    }

    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getDrawableForDensity(int i, int i2) throws android.content.res.Resources.NotFoundException {
        return getDrawableForDensity(i, i2, null);
    }

    public android.graphics.drawable.Drawable getDrawableForDensity(int i, int i2, android.content.res.Resources.Theme theme) {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValueForDensity(i, i2, obtainTempTypedValue, true);
            return loadDrawable(obtainTempTypedValue, i, i2, theme);
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    android.graphics.drawable.Drawable loadDrawable(android.util.TypedValue typedValue, int i, int i2, android.content.res.Resources.Theme theme) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.loadDrawable(this, typedValue, i, i2, theme);
    }

    @java.lang.Deprecated
    public android.graphics.Movie getMovie(int i) throws android.content.res.Resources.NotFoundException {
        java.io.InputStream openRawResource = openRawResource(i);
        android.graphics.Movie decodeStream = android.graphics.Movie.decodeStream(openRawResource);
        try {
            openRawResource.close();
        } catch (java.io.IOException e) {
        }
        return decodeStream;
    }

    @java.lang.Deprecated
    public int getColor(int i) throws android.content.res.Resources.NotFoundException {
        return getColor(i, null);
    }

    public int getColor(int i, android.content.res.Resources.Theme theme) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type >= 16 && obtainTempTypedValue.type <= 31) {
                return obtainTempTypedValue.data;
            }
            if (obtainTempTypedValue.type != 3) {
                throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
            }
            return resourcesImpl.loadColorStateList(this, obtainTempTypedValue, i, theme).getDefaultColor();
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    @java.lang.Deprecated
    public android.content.res.ColorStateList getColorStateList(int i) throws android.content.res.Resources.NotFoundException {
        android.content.res.ColorStateList colorStateList = getColorStateList(i, null);
        if (colorStateList != null && colorStateList.canApplyTheme()) {
            android.util.Log.w(TAG, "ColorStateList " + getResourceName(i) + " has unresolved theme attributes! Consider using Resources.getColorStateList(int, Theme) or Context.getColorStateList(int).", new java.lang.RuntimeException());
        }
        return colorStateList;
    }

    public android.content.res.ColorStateList getColorStateList(int i, android.content.res.Resources.Theme theme) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, obtainTempTypedValue, true);
            return resourcesImpl.loadColorStateList(this, obtainTempTypedValue, i, theme);
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    android.content.res.ColorStateList loadColorStateList(android.util.TypedValue typedValue, int i, android.content.res.Resources.Theme theme) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.loadColorStateList(this, typedValue, i, theme);
    }

    public android.content.res.ComplexColor loadComplexColor(android.util.TypedValue typedValue, int i, android.content.res.Resources.Theme theme) {
        return this.mResourcesImpl.loadComplexColor(this, typedValue, i, theme);
    }

    public boolean getBoolean(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type >= 16 && obtainTempTypedValue.type <= 31) {
                return obtainTempTypedValue.data != 0;
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    public int getInteger(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type >= 16 && obtainTempTypedValue.type <= 31) {
                return obtainTempTypedValue.data;
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    public float getFloat(int i) {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type == 4) {
                return obtainTempTypedValue.getFloat();
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    public android.content.res.XmlResourceParser getLayout(int i) throws android.content.res.Resources.NotFoundException {
        return loadXmlResourceParser(i, android.media.TtmlUtils.TAG_LAYOUT);
    }

    public android.content.res.XmlResourceParser getAnimation(int i) throws android.content.res.Resources.NotFoundException {
        return loadXmlResourceParser(i, "anim");
    }

    public android.content.res.XmlResourceParser getXml(int i) throws android.content.res.Resources.NotFoundException {
        return loadXmlResourceParser(i, "xml");
    }

    public java.io.InputStream openRawResource(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            return openRawResource(i, obtainTempTypedValue);
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    private android.util.TypedValue obtainTempTypedValue() {
        android.util.TypedValue typedValue;
        synchronized (this.mTmpValueLock) {
            typedValue = null;
            if (this.mTmpValue != null) {
                android.util.TypedValue typedValue2 = this.mTmpValue;
                this.mTmpValue = null;
                typedValue = typedValue2;
            }
        }
        if (typedValue == null) {
            return new android.util.TypedValue();
        }
        return typedValue;
    }

    private void releaseTempTypedValue(android.util.TypedValue typedValue) {
        synchronized (this.mTmpValueLock) {
            if (this.mTmpValue == null) {
                this.mTmpValue = typedValue;
            }
        }
    }

    public java.io.InputStream openRawResource(int i, android.util.TypedValue typedValue) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.openRawResource(i, typedValue);
    }

    public android.content.res.AssetFileDescriptor openRawResourceFd(int i) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            return this.mResourcesImpl.openRawResourceFd(i, obtainTempTypedValue);
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    public void getValue(int i, android.util.TypedValue typedValue, boolean z) throws android.content.res.Resources.NotFoundException {
        this.mResourcesImpl.getValue(i, typedValue, z);
    }

    public void getValueForDensity(int i, int i2, android.util.TypedValue typedValue, boolean z) throws android.content.res.Resources.NotFoundException {
        this.mResourcesImpl.getValueForDensity(i, i2, typedValue, z);
    }

    public void getValue(java.lang.String str, android.util.TypedValue typedValue, boolean z) throws android.content.res.Resources.NotFoundException {
        this.mResourcesImpl.getValue(str, typedValue, z);
    }

    public static int getAttributeSetSourceResId(android.util.AttributeSet attributeSet) {
        return android.content.res.ResourcesImpl.getAttributeSetSourceResId(attributeSet);
    }

    public final class Theme {
        private static final int MAX_NUMBER_OF_TRACING_PARENT_THEME = 100;
        private final java.lang.Object mLock;
        private android.content.res.ResourcesImpl.ThemeImpl mThemeImpl;

        private Theme() {
            this.mLock = new java.lang.Object();
        }

        void setImpl(android.content.res.ResourcesImpl.ThemeImpl themeImpl) {
            synchronized (this.mLock) {
                this.mThemeImpl = themeImpl;
            }
        }

        public void applyStyle(int i, boolean z) {
            synchronized (this.mLock) {
                this.mThemeImpl.applyStyle(i, z);
            }
        }

        public void setTo(android.content.res.Resources.Theme theme) {
            synchronized (this.mLock) {
                synchronized (theme.mLock) {
                    this.mThemeImpl.setTo(theme.mThemeImpl);
                }
            }
        }

        public android.content.res.TypedArray obtainStyledAttributes(int[] iArr) {
            android.content.res.TypedArray obtainStyledAttributes;
            synchronized (this.mLock) {
                obtainStyledAttributes = this.mThemeImpl.obtainStyledAttributes(this, null, iArr, 0, 0);
            }
            return obtainStyledAttributes;
        }

        public android.content.res.TypedArray obtainStyledAttributes(int i, int[] iArr) throws android.content.res.Resources.NotFoundException {
            android.content.res.TypedArray obtainStyledAttributes;
            synchronized (this.mLock) {
                obtainStyledAttributes = this.mThemeImpl.obtainStyledAttributes(this, null, iArr, 0, i);
            }
            return obtainStyledAttributes;
        }

        public android.content.res.TypedArray obtainStyledAttributes(android.util.AttributeSet attributeSet, int[] iArr, int i, int i2) {
            android.content.res.TypedArray obtainStyledAttributes;
            synchronized (this.mLock) {
                obtainStyledAttributes = this.mThemeImpl.obtainStyledAttributes(this, attributeSet, iArr, i, i2);
            }
            return obtainStyledAttributes;
        }

        public android.content.res.TypedArray resolveAttributes(int[] iArr, int[] iArr2) {
            android.content.res.TypedArray resolveAttributes;
            synchronized (this.mLock) {
                resolveAttributes = this.mThemeImpl.resolveAttributes(this, iArr, iArr2);
            }
            return resolveAttributes;
        }

        public boolean resolveAttribute(int i, android.util.TypedValue typedValue, boolean z) {
            boolean resolveAttribute;
            synchronized (this.mLock) {
                resolveAttribute = this.mThemeImpl.resolveAttribute(i, typedValue, z);
            }
            return resolveAttribute;
        }

        public int[] getAllAttributes() {
            int[] allAttributes;
            synchronized (this.mLock) {
                allAttributes = this.mThemeImpl.getAllAttributes();
            }
            return allAttributes;
        }

        public android.content.res.Resources getResources() {
            return android.content.res.Resources.this;
        }

        public android.graphics.drawable.Drawable getDrawable(int i) throws android.content.res.Resources.NotFoundException {
            return android.content.res.Resources.this.getDrawable(i, this);
        }

        public int getChangingConfigurations() {
            int changingConfigurations;
            synchronized (this.mLock) {
                changingConfigurations = this.mThemeImpl.getChangingConfigurations();
            }
            return changingConfigurations;
        }

        public void dump(int i, java.lang.String str, java.lang.String str2) {
            synchronized (this.mLock) {
                this.mThemeImpl.dump(i, str, str2);
            }
        }

        long getNativeTheme() {
            long nativeTheme;
            synchronized (this.mLock) {
                nativeTheme = this.mThemeImpl.getNativeTheme();
            }
            return nativeTheme;
        }

        int getAppliedStyleResId() {
            int appliedStyleResId;
            synchronized (this.mLock) {
                appliedStyleResId = this.mThemeImpl.getAppliedStyleResId();
            }
            return appliedStyleResId;
        }

        int getParentThemeIdentifier(int i) {
            int parentThemeIdentifier;
            synchronized (this.mLock) {
                parentThemeIdentifier = this.mThemeImpl.getParentThemeIdentifier(i);
            }
            return parentThemeIdentifier;
        }

        public android.content.res.Resources.ThemeKey getKey() {
            android.content.res.Resources.ThemeKey key;
            synchronized (this.mLock) {
                key = this.mThemeImpl.getKey();
            }
            return key;
        }

        private java.lang.String getResourceNameFromHexString(java.lang.String str) {
            return android.content.res.Resources.this.getResourceName(java.lang.Integer.parseInt(str, 16));
        }

        @android.view.ViewDebug.ExportedProperty(category = "theme", hasAdjacentMapping = true)
        public java.lang.String[] getTheme() {
            java.lang.String[] theme;
            synchronized (this.mLock) {
                theme = this.mThemeImpl.getTheme();
            }
            return theme;
        }

        public void encode(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            viewHierarchyEncoder.beginObject(this);
            java.lang.String[] theme = getTheme();
            for (int i = 0; i < theme.length; i += 2) {
                viewHierarchyEncoder.addProperty(theme[i], theme[i + 1]);
            }
            viewHierarchyEncoder.endObject();
        }

        public void rebase() {
            synchronized (this.mLock) {
                this.mThemeImpl.rebase();
            }
        }

        void rebase(android.content.res.ResourcesImpl resourcesImpl) {
            synchronized (this.mLock) {
                this.mThemeImpl.rebase(resourcesImpl.mAssets);
            }
        }

        public int getExplicitStyle(android.util.AttributeSet attributeSet) {
            int styleAttribute;
            if (attributeSet == null || (styleAttribute = attributeSet.getStyleAttribute()) == 0) {
                return 0;
            }
            java.lang.String resourceTypeName = getResources().getResourceTypeName(styleAttribute);
            if ("attr".equals(resourceTypeName)) {
                android.util.TypedValue typedValue = new android.util.TypedValue();
                if (resolveAttribute(styleAttribute, typedValue, true)) {
                    return typedValue.resourceId;
                }
            } else if ("style".equals(resourceTypeName)) {
                return styleAttribute;
            }
            return 0;
        }

        public int[] getAttributeResolutionStack(int i, int i2, int i3) {
            synchronized (this.mLock) {
                int[] attributeResolutionStack = this.mThemeImpl.getAttributeResolutionStack(i, i2, i3);
                if (attributeResolutionStack != null) {
                    return attributeResolutionStack;
                }
                return new int[0];
            }
        }

        public int hashCode() {
            return getKey().hashCode();
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass() || hashCode() != obj.hashCode()) {
                return false;
            }
            return getKey().equals(((android.content.res.Resources.Theme) obj).getKey());
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append('{');
            int appliedStyleResId = getAppliedStyleResId();
            sb.append("InheritanceMap=[");
            int i = 0;
            while (true) {
                if (appliedStyleResId <= 0) {
                    break;
                }
                if (i > 100) {
                    sb.append(",...");
                    break;
                }
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append("id=0x").append(java.lang.Integer.toHexString(appliedStyleResId));
                sb.append(android.content.res.Resources.this.getResourcePackageName(appliedStyleResId)).append(":").append(android.content.res.Resources.this.getResourceTypeName(appliedStyleResId)).append("/").append(android.content.res.Resources.this.getResourceEntryName(appliedStyleResId));
                i++;
                appliedStyleResId = getParentThemeIdentifier(appliedStyleResId);
            }
            sb.append("], Themes=").append(java.util.Arrays.deepToString(getTheme()));
            sb.append('}');
            return sb.toString();
        }
    }

    static class ThemeKey implements java.lang.Cloneable {
        int mCount;
        boolean[] mForce;
        private int mHashCode = 0;
        int[] mResId;

        ThemeKey() {
        }

        private int findValue(int i, boolean z) {
            for (int i2 = 0; i2 < this.mCount; i2++) {
                if (this.mResId[i2] == i && this.mForce[i2] == z) {
                    return i2;
                }
            }
            return -1;
        }

        private void moveToLast(int i) {
            if (i < 0 || i >= this.mCount - 1) {
                return;
            }
            int i2 = this.mResId[i];
            boolean z = this.mForce[i];
            int i3 = i + 1;
            java.lang.System.arraycopy(this.mResId, i3, this.mResId, i, (this.mCount - i) - 1);
            this.mResId[this.mCount - 1] = i2;
            java.lang.System.arraycopy(this.mForce, i3, this.mForce, i, (this.mCount - i) - 1);
            this.mForce[this.mCount - 1] = z;
        }

        public void append(int i, boolean z) {
            if (this.mResId == null) {
                this.mResId = new int[4];
            }
            if (this.mForce == null) {
                this.mForce = new boolean[4];
            }
            int findValue = findValue(i, z);
            if (findValue >= 0) {
                moveToLast(findValue);
                return;
            }
            this.mResId = com.android.internal.util.GrowingArrayUtils.append(this.mResId, this.mCount, i);
            this.mForce = com.android.internal.util.GrowingArrayUtils.append(this.mForce, this.mCount, z);
            this.mCount++;
            this.mHashCode = (((this.mHashCode * 31) + i) * 31) + (z ? 1 : 0);
        }

        public void setTo(android.content.res.Resources.ThemeKey themeKey) {
            this.mResId = themeKey.mResId == null ? null : (int[]) themeKey.mResId.clone();
            this.mForce = themeKey.mForce != null ? (boolean[]) themeKey.mForce.clone() : null;
            this.mCount = themeKey.mCount;
            this.mHashCode = themeKey.mHashCode;
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass() || hashCode() != obj.hashCode()) {
                return false;
            }
            android.content.res.Resources.ThemeKey themeKey = (android.content.res.Resources.ThemeKey) obj;
            if (this.mCount != themeKey.mCount) {
                return false;
            }
            int i = this.mCount;
            for (int i2 = 0; i2 < i; i2++) {
                if (this.mResId[i2] != themeKey.mResId[i2] || this.mForce[i2] != themeKey.mForce[i2]) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.content.res.Resources.ThemeKey m926clone() {
            android.content.res.Resources.ThemeKey themeKey = new android.content.res.Resources.ThemeKey();
            themeKey.mResId = this.mResId;
            themeKey.mForce = this.mForce;
            themeKey.mCount = this.mCount;
            themeKey.mHashCode = this.mHashCode;
            return themeKey;
        }
    }

    static int nextPowerOf2(int i) {
        if (i < 2) {
            return 2;
        }
        return 1 >> (((int) (java.lang.Math.log(i - 1) / java.lang.Math.log(2.0d))) + 1);
    }

    private void cleanupThemeReferences() {
        if (this.mThemeRefs.size() > this.mThemeRefsNextFlushSize) {
            this.mThemeRefs.removeIf(new java.util.function.Predicate() { // from class: android.content.res.Resources$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean refersTo;
                    refersTo = ((java.lang.ref.WeakReference) obj).refersTo(null);
                    return refersTo;
                }
            });
            this.mThemeRefsNextFlushSize = java.lang.Math.min(java.lang.Math.max(32, nextPowerOf2(this.mThemeRefs.size())), 512);
        }
    }

    public final android.content.res.Resources.Theme newTheme() {
        android.content.res.Resources.Theme theme = new android.content.res.Resources.Theme();
        theme.setImpl(this.mResourcesImpl.newThemeImpl());
        synchronized (this.mThemeRefs) {
            cleanupThemeReferences();
            this.mThemeRefs.add(new java.lang.ref.WeakReference<>(theme));
        }
        return theme;
    }

    public android.content.res.TypedArray obtainAttributes(android.util.AttributeSet attributeSet, int[] iArr) {
        android.content.res.TypedArray obtain = android.content.res.TypedArray.obtain(this, iArr.length);
        android.content.res.XmlBlock.Parser parser = (android.content.res.XmlBlock.Parser) attributeSet;
        this.mResourcesImpl.getAssets().retrieveAttributes(parser, iArr, obtain.mData, obtain.mIndices);
        obtain.mXml = parser;
        return obtain;
    }

    @java.lang.Deprecated
    public void updateConfiguration(android.content.res.Configuration configuration, android.util.DisplayMetrics displayMetrics) {
        updateConfiguration(configuration, displayMetrics, null);
    }

    public void updateConfiguration(android.content.res.Configuration configuration, android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo) {
        this.mResourcesImpl.updateConfiguration(configuration, displayMetrics, compatibilityInfo);
    }

    public static void updateSystemConfiguration(android.content.res.Configuration configuration, android.util.DisplayMetrics displayMetrics, android.content.res.CompatibilityInfo compatibilityInfo) {
        if (mSystem != null) {
            mSystem.updateConfiguration(configuration, displayMetrics, compatibilityInfo);
        }
    }

    public android.util.DisplayMetrics getDisplayMetrics() {
        return this.mResourcesImpl.getDisplayMetrics();
    }

    public android.view.DisplayAdjustments getDisplayAdjustments() {
        return this.mResourcesImpl.getDisplayAdjustments();
    }

    public boolean hasOverrideDisplayAdjustments() {
        return false;
    }

    public android.content.res.Configuration getConfiguration() {
        return this.mResourcesImpl.getConfiguration();
    }

    public android.content.res.Configuration[] getSizeConfigurations() {
        return this.mResourcesImpl.getSizeConfigurations();
    }

    public android.content.res.Configuration[] getSizeAndUiModeConfigurations() {
        return this.mResourcesImpl.getSizeAndUiModeConfigurations();
    }

    public android.content.res.CompatibilityInfo getCompatibilityInfo() {
        return this.mResourcesImpl.getCompatibilityInfo();
    }

    public void setCompatibilityInfo(android.content.res.CompatibilityInfo compatibilityInfo) {
        if (compatibilityInfo != null) {
            this.mResourcesImpl.updateConfiguration(null, null, compatibilityInfo);
        }
    }

    public int getIdentifier(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return this.mResourcesImpl.getIdentifier(str, str2, str3);
    }

    public static boolean resourceHasPackage(int i) {
        return (i >>> 24) != 0;
    }

    public java.lang.String getResourceName(int i) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.getResourceName(i);
    }

    public java.lang.String getResourcePackageName(int i) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.getResourcePackageName(i);
    }

    public java.lang.String getResourceTypeName(int i) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.getResourceTypeName(i);
    }

    public java.lang.String getResourceEntryName(int i) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.getResourceEntryName(i);
    }

    public java.lang.String getLastResourceResolution() throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.getLastResourceResolution();
    }

    public void parseBundleExtras(android.content.res.XmlResourceParser xmlResourceParser, android.os.Bundle bundle) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1) {
                if (next != 3 || xmlResourceParser.getDepth() > depth) {
                    if (next != 3 && next != 4) {
                        if (xmlResourceParser.getName().equals("extra")) {
                            parseBundleExtra("extra", xmlResourceParser, bundle);
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else {
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        }
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public void parseBundleExtra(java.lang.String str, android.util.AttributeSet attributeSet, android.os.Bundle bundle) throws org.xmlpull.v1.XmlPullParserException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(attributeSet, com.android.internal.R.styleable.Extra);
        boolean z = false;
        java.lang.String string = obtainAttributes.getString(0);
        if (string == null) {
            obtainAttributes.recycle();
            throw new org.xmlpull.v1.XmlPullParserException("<" + str + "> requires an android:name attribute at " + attributeSet.getPositionDescription());
        }
        android.util.TypedValue peekValue = obtainAttributes.peekValue(1);
        if (peekValue != null) {
            if (peekValue.type == 3) {
                bundle.putCharSequence(string, peekValue.coerceToString());
            } else if (peekValue.type == 18) {
                if (peekValue.data != 0) {
                    z = true;
                }
                bundle.putBoolean(string, z);
            } else if (peekValue.type >= 16 && peekValue.type <= 31) {
                bundle.putInt(string, peekValue.data);
            } else if (peekValue.type == 4) {
                bundle.putFloat(string, peekValue.getFloat());
            } else {
                obtainAttributes.recycle();
                throw new org.xmlpull.v1.XmlPullParserException("<" + str + "> only supports string, integer, float, color, and boolean at " + attributeSet.getPositionDescription());
            }
            obtainAttributes.recycle();
            return;
        }
        obtainAttributes.recycle();
        throw new org.xmlpull.v1.XmlPullParserException("<" + str + "> requires an android:value or android:resource attribute at " + attributeSet.getPositionDescription());
    }

    public final android.content.res.AssetManager getAssets() {
        return this.mResourcesImpl.getAssets();
    }

    public final void flushLayoutCache() {
        this.mResourcesImpl.flushLayoutCache();
    }

    public final void startPreloading() {
        this.mResourcesImpl.startPreloading();
    }

    public final void finishPreloading() {
        this.mResourcesImpl.finishPreloading();
    }

    public android.util.LongSparseArray<android.graphics.drawable.Drawable.ConstantState> getPreloadedDrawables() {
        return this.mResourcesImpl.getPreloadedDrawables();
    }

    android.content.res.XmlResourceParser loadXmlResourceParser(int i, java.lang.String str) throws android.content.res.Resources.NotFoundException {
        android.util.TypedValue obtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, obtainTempTypedValue, true);
            if (obtainTempTypedValue.type == 3) {
                return loadXmlResourceParser(obtainTempTypedValue.string.toString(), i, obtainTempTypedValue.assetCookie, str);
            }
            throw new android.content.res.Resources.NotFoundException("Resource ID #0x" + java.lang.Integer.toHexString(i) + " type #0x" + java.lang.Integer.toHexString(obtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(obtainTempTypedValue);
        }
    }

    android.content.res.XmlResourceParser loadXmlResourceParser(java.lang.String str, int i, int i2, java.lang.String str2) throws android.content.res.Resources.NotFoundException {
        return this.mResourcesImpl.loadXmlResourceParser(str, i, i2, str2);
    }

    public int calcConfigChanges(android.content.res.Configuration configuration) {
        return this.mResourcesImpl.calcConfigChanges(configuration);
    }

    public static android.content.res.TypedArray obtainAttributes(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet, int[] iArr) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, iArr);
        }
        return theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    private void checkCallbacksRegistered() {
        if (this.mCallbacks == null) {
            this.mCallbacks = new android.content.res.Resources.AssetManagerUpdateHandler();
        }
    }

    public java.util.List<android.content.res.loader.ResourcesLoader> getLoaders() {
        return this.mResourcesImpl.getAssets().getLoaders();
    }

    public void addLoaders(android.content.res.loader.ResourcesLoader... resourcesLoaderArr) {
        synchronized (this.mUpdateLock) {
            checkCallbacksRegistered();
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mResourcesImpl.getAssets().getLoaders());
            android.util.ArraySet arraySet = new android.util.ArraySet(arrayList);
            for (android.content.res.loader.ResourcesLoader resourcesLoader : resourcesLoaderArr) {
                if (!arraySet.contains(resourcesLoader)) {
                    arrayList.add(resourcesLoader);
                }
            }
            if (arraySet.size() == arrayList.size()) {
                return;
            }
            this.mCallbacks.onLoadersChanged(this, arrayList);
            int size = arrayList.size();
            for (int size2 = arraySet.size(); size2 < size; size2++) {
                ((android.content.res.loader.ResourcesLoader) arrayList.get(size2)).registerOnProvidersChangedCallback(this, this.mCallbacks);
            }
        }
    }

    public void removeLoaders(android.content.res.loader.ResourcesLoader... resourcesLoaderArr) {
        synchronized (this.mUpdateLock) {
            checkCallbacksRegistered();
            android.util.ArraySet arraySet = new android.util.ArraySet(resourcesLoaderArr);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.List<android.content.res.loader.ResourcesLoader> loaders = this.mResourcesImpl.getAssets().getLoaders();
            int size = loaders.size();
            for (int i = 0; i < size; i++) {
                android.content.res.loader.ResourcesLoader resourcesLoader = loaders.get(i);
                if (!arraySet.contains(resourcesLoader)) {
                    arrayList.add(resourcesLoader);
                }
            }
            if (loaders.size() == arrayList.size()) {
                return;
            }
            this.mCallbacks.onLoadersChanged(this, arrayList);
            for (android.content.res.loader.ResourcesLoader resourcesLoader2 : resourcesLoaderArr) {
                resourcesLoader2.unregisterOnProvidersChangedCallback(this);
            }
        }
    }

    public void clearLoaders() {
        synchronized (this.mUpdateLock) {
            checkCallbacksRegistered();
            java.util.List<android.content.res.loader.ResourcesLoader> emptyList = java.util.Collections.emptyList();
            java.util.List<android.content.res.loader.ResourcesLoader> loaders = this.mResourcesImpl.getAssets().getLoaders();
            this.mCallbacks.onLoadersChanged(this, emptyList);
            java.util.Iterator<android.content.res.loader.ResourcesLoader> it = loaders.iterator();
            while (it.hasNext()) {
                it.next().unregisterOnProvidersChangedCallback(this);
            }
        }
    }

    public static void preloadResources() {
        try {
            android.content.res.Resources system = getSystem();
            system.startPreloading();
            android.util.Log.i(TAG, "Preloading resources...");
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            android.content.res.TypedArray obtainTypedArray = system.obtainTypedArray(com.android.internal.R.array.preloaded_drawables);
            int preloadDrawables = preloadDrawables(system, obtainTypedArray);
            obtainTypedArray.recycle();
            android.util.Log.i(TAG, "...preloaded " + preloadDrawables + " resources in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis) + "ms.");
            long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
            android.content.res.TypedArray obtainTypedArray2 = system.obtainTypedArray(com.android.internal.R.array.preloaded_color_state_lists);
            int preloadColorStateLists = preloadColorStateLists(system, obtainTypedArray2);
            obtainTypedArray2.recycle();
            android.util.Log.i(TAG, "...preloaded " + preloadColorStateLists + " resources in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis2) + "ms.");
            if (system.getBoolean(com.android.internal.R.bool.config_freeformWindowManagement)) {
                long uptimeMillis3 = android.os.SystemClock.uptimeMillis();
                android.content.res.TypedArray obtainTypedArray3 = system.obtainTypedArray(com.android.internal.R.array.preloaded_freeform_multi_window_drawables);
                int preloadDrawables2 = preloadDrawables(system, obtainTypedArray3);
                obtainTypedArray3.recycle();
                android.util.Log.i(TAG, "...preloaded " + preloadDrawables2 + " resource in " + (android.os.SystemClock.uptimeMillis() - uptimeMillis3) + "ms.");
            }
            system.finishPreloading();
        } catch (java.lang.RuntimeException e) {
            android.util.Log.w(TAG, "Failure preloading resources", e);
        }
    }

    private static int preloadColorStateLists(android.content.res.Resources resources, android.content.res.TypedArray typedArray) {
        int length = typedArray.length();
        for (int i = 0; i < length; i++) {
            int resourceId = typedArray.getResourceId(i, 0);
            if (resourceId != 0 && resources.getColorStateList(resourceId, null) == null) {
                throw new java.lang.IllegalArgumentException("Unable to find preloaded color resource #0x" + java.lang.Integer.toHexString(resourceId) + " (" + typedArray.getString(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        return length;
    }

    private static int preloadDrawables(android.content.res.Resources resources, android.content.res.TypedArray typedArray) {
        int length = typedArray.length();
        for (int i = 0; i < length; i++) {
            int resourceId = typedArray.getResourceId(i, 0);
            if (resourceId != 0 && resources.getDrawable(resourceId, null) == null) {
                throw new java.lang.IllegalArgumentException("Unable to find preloaded drawable resource #0x" + java.lang.Integer.toHexString(resourceId) + " (" + typedArray.getString(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        return length;
    }

    public static void resetPreloadDrawableStateCache() {
        android.content.res.ResourcesImpl.resetDrawableStateCache();
        preloadResources();
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "class=" + getClass());
        printWriter.println(str + "resourcesImpl");
        android.content.res.ResourcesImpl resourcesImpl = this.mResourcesImpl;
        if (resourcesImpl != null) {
            resourcesImpl.dump(printWriter, str + "  ");
        } else {
            printWriter.println(str + "  null");
        }
    }

    public static void dumpHistory(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "history");
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        sResourcesHistory.forEach(new java.util.function.Consumer() { // from class: android.content.res.Resources$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.content.res.Resources.lambda$dumpHistory$1(android.util.ArrayMap.this, (android.content.res.Resources) obj);
            }
        });
        int i = 0;
        for (android.content.res.Resources resources : arrayMap.values()) {
            printWriter.println(str + i);
            resources.dump(printWriter, str + "  ");
            i++;
        }
    }

    static /* synthetic */ void lambda$dumpHistory$1(android.util.ArrayMap arrayMap, android.content.res.Resources resources) {
        if (resources != null) {
            android.content.res.ResourcesImpl resourcesImpl = resources.mResourcesImpl;
            if (resourcesImpl != null) {
                arrayMap.put(java.util.Arrays.asList(resourcesImpl.mAssets.getApkAssets()), resources);
            } else {
                arrayMap.put(null, resources);
            }
        }
    }

    public static void registerResourcePaths(java.lang.String str, android.content.pm.ApplicationInfo applicationInfo) {
        throw new java.lang.UnsupportedOperationException("The implementation has not been done yet.");
    }
}
