package android.graphics.drawable;

/* loaded from: classes.dex */
public abstract class Drawable {
    private int mLayoutDirection;
    private static final android.graphics.Rect ZERO_BOUNDS_RECT = new android.graphics.Rect();
    static final android.graphics.PorterDuff.Mode DEFAULT_TINT_MODE = android.graphics.PorterDuff.Mode.SRC_IN;
    static final android.graphics.BlendMode DEFAULT_BLEND_MODE = android.graphics.BlendMode.SRC_IN;
    private int[] mStateSet = android.util.StateSet.WILD_CARD;
    private int mLevel = 0;
    private int mChangingConfigurations = 0;
    private android.graphics.Rect mBounds = ZERO_BOUNDS_RECT;
    private java.lang.ref.WeakReference<android.graphics.drawable.Drawable.Callback> mCallback = null;
    private boolean mVisible = true;
    protected int mSrcDensityOverride = 0;
    private boolean mSetBlendModeInvoked = false;
    private boolean mSetTintModeInvoked = false;

    public interface Callback {
        void invalidateDrawable(android.graphics.drawable.Drawable drawable);

        void scheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable, long j);

        void unscheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable);
    }

    public abstract void draw(android.graphics.Canvas canvas);

    @java.lang.Deprecated
    public abstract int getOpacity();

    public abstract void setAlpha(int i);

    public abstract void setColorFilter(android.graphics.ColorFilter colorFilter);

    public void setBounds(int i, int i2, int i3, int i4) {
        android.graphics.Rect rect = this.mBounds;
        if (rect == ZERO_BOUNDS_RECT) {
            rect = new android.graphics.Rect();
            this.mBounds = rect;
        }
        if (rect.left != i || rect.top != i2 || rect.right != i3 || rect.bottom != i4) {
            if (!rect.isEmpty()) {
                invalidateSelf();
            }
            this.mBounds.set(i, i2, i3, i4);
            onBoundsChange(this.mBounds);
        }
    }

    public void setBounds(android.graphics.Rect rect) {
        setBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public final void copyBounds(android.graphics.Rect rect) {
        rect.set(this.mBounds);
    }

    public final android.graphics.Rect copyBounds() {
        return new android.graphics.Rect(this.mBounds);
    }

    public final android.graphics.Rect getBounds() {
        if (this.mBounds == ZERO_BOUNDS_RECT) {
            this.mBounds = new android.graphics.Rect();
        }
        return this.mBounds;
    }

    public android.graphics.Rect getDirtyBounds() {
        return getBounds();
    }

    public void setChangingConfigurations(int i) {
        this.mChangingConfigurations = i;
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations;
    }

    @java.lang.Deprecated
    public void setDither(boolean z) {
    }

    public void setFilterBitmap(boolean z) {
    }

    public boolean isFilterBitmap() {
        return false;
    }

    public final void setCallback(android.graphics.drawable.Drawable.Callback callback) {
        this.mCallback = callback != null ? new java.lang.ref.WeakReference<>(callback) : null;
    }

    public android.graphics.drawable.Drawable.Callback getCallback() {
        if (this.mCallback != null) {
            return this.mCallback.get();
        }
        return null;
    }

    public void invalidateSelf() {
        android.graphics.drawable.Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleSelf(java.lang.Runnable runnable, long j) {
        android.graphics.drawable.Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public void unscheduleSelf(java.lang.Runnable runnable) {
        android.graphics.drawable.Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public int getLayoutDirection() {
        return this.mLayoutDirection;
    }

    public final boolean setLayoutDirection(int i) {
        if (this.mLayoutDirection != i) {
            this.mLayoutDirection = i;
            return onLayoutDirectionChanged(i);
        }
        return false;
    }

    public boolean onLayoutDirectionChanged(int i) {
        return false;
    }

    public int getAlpha() {
        return 255;
    }

    public void setXfermode(android.graphics.Xfermode xfermode) {
    }

    @java.lang.Deprecated
    public void setColorFilter(int i, android.graphics.PorterDuff.Mode mode) {
        if (getColorFilter() instanceof android.graphics.PorterDuffColorFilter) {
            android.graphics.PorterDuffColorFilter porterDuffColorFilter = (android.graphics.PorterDuffColorFilter) getColorFilter();
            if (porterDuffColorFilter.getColor() == i && porterDuffColorFilter.getMode() == mode) {
                return;
            }
        }
        setColorFilter(new android.graphics.PorterDuffColorFilter(i, mode));
    }

    public void setTint(int i) {
        setTintList(android.content.res.ColorStateList.valueOf(i));
    }

    public void setTintList(android.content.res.ColorStateList colorStateList) {
    }

    public void setTintMode(android.graphics.PorterDuff.Mode mode) {
        if (!this.mSetTintModeInvoked) {
            this.mSetTintModeInvoked = true;
            android.graphics.BlendMode fromValue = mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null;
            if (fromValue == null) {
                fromValue = DEFAULT_BLEND_MODE;
            }
            setTintBlendMode(fromValue);
            this.mSetTintModeInvoked = false;
        }
    }

    public void setTintBlendMode(android.graphics.BlendMode blendMode) {
        if (!this.mSetBlendModeInvoked) {
            this.mSetBlendModeInvoked = true;
            android.graphics.PorterDuff.Mode blendModeToPorterDuffMode = android.graphics.BlendMode.blendModeToPorterDuffMode(blendMode);
            if (blendModeToPorterDuffMode == null) {
                blendModeToPorterDuffMode = DEFAULT_TINT_MODE;
            }
            setTintMode(blendModeToPorterDuffMode);
            this.mSetBlendModeInvoked = false;
        }
    }

    public android.graphics.ColorFilter getColorFilter() {
        return null;
    }

    public void clearColorFilter() {
        setColorFilter(null);
    }

    public void setHotspot(float f, float f2) {
    }

    public void setHotspotBounds(int i, int i2, int i3, int i4) {
    }

    public void getHotspotBounds(android.graphics.Rect rect) {
        rect.set(getBounds());
    }

    public boolean isProjected() {
        return false;
    }

    public boolean isStateful() {
        return false;
    }

    public boolean hasFocusStateSpecified() {
        return false;
    }

    public boolean setState(int[] iArr) {
        if (!java.util.Arrays.equals(this.mStateSet, iArr)) {
            this.mStateSet = iArr;
            return onStateChange(iArr);
        }
        return false;
    }

    public int[] getState() {
        return this.mStateSet;
    }

    public void jumpToCurrentState() {
    }

    public android.graphics.drawable.Drawable getCurrent() {
        return this;
    }

    public final boolean setLevel(int i) {
        if (this.mLevel != i) {
            this.mLevel = i;
            return onLevelChange(i);
        }
        return false;
    }

    public final int getLevel() {
        return this.mLevel;
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean z3 = this.mVisible != z;
        if (z3) {
            this.mVisible = z;
            invalidateSelf();
        }
        return z3;
    }

    public final boolean isVisible() {
        return this.mVisible;
    }

    public void setAutoMirrored(boolean z) {
    }

    public boolean isAutoMirrored() {
        return false;
    }

    public void applyTheme(android.content.res.Resources.Theme theme) {
    }

    public boolean canApplyTheme() {
        return false;
    }

    public static int resolveOpacity(int i, int i2) {
        if (i == i2) {
            return i;
        }
        if (i == 0 || i2 == 0) {
            return 0;
        }
        if (i == -3 || i2 == -3) {
            return -3;
        }
        if (i == -2 || i2 == -2) {
            return -2;
        }
        return -1;
    }

    public android.graphics.Region getTransparentRegion() {
        return null;
    }

    protected boolean onStateChange(int[] iArr) {
        return false;
    }

    protected boolean onLevelChange(int i) {
        return false;
    }

    protected void onBoundsChange(android.graphics.Rect rect) {
    }

    public int getIntrinsicWidth() {
        return -1;
    }

    public int getIntrinsicHeight() {
        return -1;
    }

    public int getMinimumWidth() {
        int intrinsicWidth = getIntrinsicWidth();
        if (intrinsicWidth > 0) {
            return intrinsicWidth;
        }
        return 0;
    }

    public int getMinimumHeight() {
        int intrinsicHeight = getIntrinsicHeight();
        if (intrinsicHeight > 0) {
            return intrinsicHeight;
        }
        return 0;
    }

    public boolean getPadding(android.graphics.Rect rect) {
        rect.set(0, 0, 0, 0);
        return false;
    }

    public android.graphics.Insets getOpticalInsets() {
        return android.graphics.Insets.NONE;
    }

    public void getOutline(android.graphics.Outline outline) {
        outline.setRect(getBounds());
        outline.setAlpha(0.0f);
    }

    public android.graphics.drawable.Drawable mutate() {
        return this;
    }

    public void clearMutated() {
    }

    public static android.graphics.drawable.Drawable createFromStream(java.io.InputStream inputStream, java.lang.String str) {
        android.os.Trace.traceBegin(8192L, str != null ? str : "Unknown drawable");
        try {
            return createFromResourceStream(null, null, inputStream, str);
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    public static android.graphics.drawable.Drawable createFromResourceStream(android.content.res.Resources resources, android.util.TypedValue typedValue, java.io.InputStream inputStream, java.lang.String str) {
        android.os.Trace.traceBegin(8192L, str != null ? str : "Unknown drawable");
        try {
            return createFromResourceStream(resources, typedValue, inputStream, str, null);
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    public static android.graphics.drawable.Drawable createFromResourceStream(android.content.res.Resources resources, android.util.TypedValue typedValue, java.io.InputStream inputStream, java.lang.String str, android.graphics.BitmapFactory.Options options) {
        byte[] bArr;
        android.graphics.Rect rect;
        if (inputStream == null) {
            return null;
        }
        if (options == null) {
            return getBitmapDrawable(resources, typedValue, inputStream);
        }
        android.graphics.Rect rect2 = new android.graphics.Rect();
        options.inScreenDensity = resolveDensity(resources, 0);
        android.graphics.Bitmap decodeResourceStream = android.graphics.BitmapFactory.decodeResourceStream(resources, typedValue, inputStream, rect2, options);
        if (decodeResourceStream == null) {
            return null;
        }
        byte[] ninePatchChunk = decodeResourceStream.getNinePatchChunk();
        if (ninePatchChunk == null || !android.graphics.NinePatch.isNinePatchChunk(ninePatchChunk)) {
            bArr = null;
            rect = null;
        } else {
            bArr = ninePatchChunk;
            rect = rect2;
        }
        android.graphics.Rect rect3 = new android.graphics.Rect();
        decodeResourceStream.getOpticalInsets(rect3);
        return drawableFromBitmap(resources, decodeResourceStream, bArr, rect, rect3, str);
    }

    private static android.graphics.drawable.Drawable getBitmapDrawable(android.content.res.Resources resources, android.util.TypedValue typedValue, java.io.InputStream inputStream) {
        android.graphics.ImageDecoder.Source createSource;
        int i;
        try {
            if (typedValue != null) {
                if (typedValue.density == 0) {
                    i = 160;
                } else if (typedValue.density == 65535) {
                    i = 0;
                } else {
                    i = typedValue.density;
                }
                createSource = android.graphics.ImageDecoder.createSource(resources, inputStream, i);
            } else {
                createSource = android.graphics.ImageDecoder.createSource(resources, inputStream);
            }
            return android.graphics.ImageDecoder.decodeDrawable(createSource, new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.Drawable$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                    android.graphics.drawable.Drawable.lambda$getBitmapDrawable$1(imageDecoder, imageInfo, source);
                }
            });
        } catch (java.io.IOException e) {
            android.util.Log.e("Drawable", "Unable to decode stream: " + e);
            return null;
        }
    }

    static /* synthetic */ void lambda$getBitmapDrawable$1(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
        imageDecoder.setAllocator(1);
        imageDecoder.setOnPartialImageListener(new android.graphics.ImageDecoder.OnPartialImageListener() { // from class: android.graphics.drawable.Drawable$$ExternalSyntheticLambda1
            @Override // android.graphics.ImageDecoder.OnPartialImageListener
            public final boolean onPartialImage(android.graphics.ImageDecoder.DecodeException decodeException) {
                return android.graphics.drawable.Drawable.lambda$getBitmapDrawable$0(decodeException);
            }
        });
    }

    static /* synthetic */ boolean lambda$getBitmapDrawable$0(android.graphics.ImageDecoder.DecodeException decodeException) {
        return decodeException.getError() == 2;
    }

    public static android.graphics.drawable.Drawable createFromXml(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return createFromXml(resources, xmlPullParser, null);
    }

    public static android.graphics.drawable.Drawable createFromXml(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return createFromXmlForDensity(resources, xmlPullParser, 0, theme);
    }

    public static android.graphics.drawable.Drawable createFromXmlForDensity(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, int i, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
        }
        android.graphics.drawable.Drawable createFromXmlInnerForDensity = createFromXmlInnerForDensity(resources, xmlPullParser, asAttributeSet, i, theme);
        if (createFromXmlInnerForDensity == null) {
            throw new java.lang.RuntimeException("Unknown initial tag: " + xmlPullParser.getName());
        }
        return createFromXmlInnerForDensity;
    }

    public static android.graphics.drawable.Drawable createFromXmlInner(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return createFromXmlInner(resources, xmlPullParser, attributeSet, null);
    }

    public static android.graphics.drawable.Drawable createFromXmlInner(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return createFromXmlInnerForDensity(resources, xmlPullParser, attributeSet, 0, theme);
    }

    static android.graphics.drawable.Drawable createFromXmlInnerForDensity(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, int i, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        return resources.getDrawableInflater().inflateFromXmlForDensity(xmlPullParser.getName(), xmlPullParser, attributeSet, i, theme);
    }

    public static android.graphics.drawable.Drawable createFromPath(java.lang.String str) {
        if (str == null) {
            return null;
        }
        android.os.Trace.traceBegin(8192L, str);
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(str);
            try {
                android.graphics.drawable.Drawable bitmapDrawable = getBitmapDrawable(null, null, fileInputStream);
                fileInputStream.close();
                return bitmapDrawable;
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.IOException e) {
            return null;
        } finally {
            android.os.Trace.traceEnd(8192L);
        }
    }

    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        inflate(resources, xmlPullParser, attributeSet, null);
    }

    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.Drawable);
        this.mVisible = obtainAttributes.getBoolean(0, this.mVisible);
        obtainAttributes.recycle();
    }

    void inflateWithAttributes(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.res.TypedArray typedArray, int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mVisible = typedArray.getBoolean(i, this.mVisible);
    }

    final void setSrcDensityOverride(int i) {
        this.mSrcDensityOverride = i;
    }

    public static abstract class ConstantState {
        public abstract int getChangingConfigurations();

        public abstract android.graphics.drawable.Drawable newDrawable();

        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return newDrawable();
        }

        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources, android.content.res.Resources.Theme theme) {
            return newDrawable(resources);
        }

        public boolean canApplyTheme() {
            return false;
        }
    }

    public android.graphics.drawable.Drawable.ConstantState getConstantState() {
        return null;
    }

    private static android.graphics.drawable.Drawable drawableFromBitmap(android.content.res.Resources resources, android.graphics.Bitmap bitmap, byte[] bArr, android.graphics.Rect rect, android.graphics.Rect rect2, java.lang.String str) {
        if (bArr != null) {
            return new android.graphics.drawable.NinePatchDrawable(resources, bitmap, bArr, rect, rect2, str);
        }
        return new android.graphics.drawable.BitmapDrawable(resources, bitmap);
    }

    android.graphics.PorterDuffColorFilter updateTintFilter(android.graphics.PorterDuffColorFilter porterDuffColorFilter, android.content.res.ColorStateList colorStateList, android.graphics.PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        if (porterDuffColorFilter == null || porterDuffColorFilter.getColor() != colorForState || porterDuffColorFilter.getMode() != mode) {
            return new android.graphics.PorterDuffColorFilter(colorForState, mode);
        }
        return porterDuffColorFilter;
    }

    android.graphics.BlendModeColorFilter updateBlendModeFilter(android.graphics.BlendModeColorFilter blendModeColorFilter, android.content.res.ColorStateList colorStateList, android.graphics.BlendMode blendMode) {
        if (colorStateList == null || blendMode == null) {
            return null;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        if (blendModeColorFilter == null || blendModeColorFilter.getColor() != colorForState || blendModeColorFilter.getMode() != blendMode) {
            return new android.graphics.BlendModeColorFilter(colorForState, blendMode);
        }
        return blendModeColorFilter;
    }

    protected static android.content.res.TypedArray obtainAttributes(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet, int[] iArr) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, iArr);
        }
        return theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    static float scaleFromDensity(float f, int i, int i2) {
        return (f * i2) / i;
    }

    static int scaleFromDensity(int i, int i2, int i3, boolean z) {
        if (i == 0 || i2 == i3) {
            return i;
        }
        float f = (i3 * i) / i2;
        if (!z) {
            return (int) f;
        }
        int round = java.lang.Math.round(f);
        if (round != 0) {
            return round;
        }
        if (i > 0) {
            return 1;
        }
        return -1;
    }

    static int resolveDensity(android.content.res.Resources resources, int i) {
        if (resources != null) {
            i = resources.getDisplayMetrics().densityDpi;
        }
        if (i == 0) {
            return 160;
        }
        return i;
    }

    static void rethrowAsRuntimeException(java.lang.Exception exc) throws java.lang.RuntimeException {
        java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(exc);
        runtimeException.setStackTrace(new java.lang.StackTraceElement[0]);
        throw runtimeException;
    }

    public static android.graphics.PorterDuff.Mode parseTintMode(int i, android.graphics.PorterDuff.Mode mode) {
        switch (i) {
            case 3:
                return android.graphics.PorterDuff.Mode.SRC_OVER;
            case 5:
                return android.graphics.PorterDuff.Mode.SRC_IN;
            case 9:
                return android.graphics.PorterDuff.Mode.SRC_ATOP;
            case 14:
                return android.graphics.PorterDuff.Mode.MULTIPLY;
            case 15:
                return android.graphics.PorterDuff.Mode.SCREEN;
            case 16:
                return android.graphics.PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }

    public static android.graphics.BlendMode parseBlendMode(int i, android.graphics.BlendMode blendMode) {
        switch (i) {
            case 3:
                return android.graphics.BlendMode.SRC_OVER;
            case 5:
                return android.graphics.BlendMode.SRC_IN;
            case 9:
                return android.graphics.BlendMode.SRC_ATOP;
            case 14:
                return android.graphics.BlendMode.MODULATE;
            case 15:
                return android.graphics.BlendMode.SCREEN;
            case 16:
                return android.graphics.BlendMode.PLUS;
            default:
                return blendMode;
        }
    }
}
