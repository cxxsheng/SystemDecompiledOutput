package android.graphics;

/* loaded from: classes.dex */
public class Paint {
    public static final int ANTI_ALIAS_FLAG = 1;
    public static final int AUTO_HINTING_TEXT_FLAG = 2048;
    public static final int BIDI_DEFAULT_LTR = 2;
    public static final int BIDI_DEFAULT_RTL = 3;
    private static final int BIDI_FLAG_MASK = 7;
    public static final int BIDI_FORCE_LTR = 4;
    public static final int BIDI_FORCE_RTL = 5;
    public static final int BIDI_LTR = 0;
    private static final int BIDI_MAX_FLAG_VALUE = 5;
    public static final int BIDI_RTL = 1;
    public static final int CURSOR_AFTER = 0;
    public static final int CURSOR_AT = 4;
    public static final int CURSOR_AT_OR_AFTER = 1;
    public static final int CURSOR_AT_OR_BEFORE = 3;
    public static final int CURSOR_BEFORE = 2;
    private static final int CURSOR_OPT_MAX_VALUE = 4;
    public static final long DEPRECATE_UI_FONT = 279646685;
    public static final int DEV_KERN_TEXT_FLAG = 256;
    public static final int DIRECTION_LTR = 0;
    public static final int DIRECTION_RTL = 1;
    public static final int DITHER_FLAG = 4;
    private static final int ELEGANT_TEXT_HEIGHT_DISABLED = 1;
    private static final int ELEGANT_TEXT_HEIGHT_ENABLED = 0;
    private static final int ELEGANT_TEXT_HEIGHT_UNSET = -1;
    public static final int EMBEDDED_BITMAP_TEXT_FLAG = 1024;
    public static final int END_HYPHEN_EDIT_INSERT_ARMENIAN_HYPHEN = 3;
    public static final int END_HYPHEN_EDIT_INSERT_HYPHEN = 2;
    public static final int END_HYPHEN_EDIT_INSERT_MAQAF = 4;
    public static final int END_HYPHEN_EDIT_INSERT_UCAS_HYPHEN = 5;
    public static final int END_HYPHEN_EDIT_INSERT_ZWJ_AND_HYPHEN = 6;
    public static final int END_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int END_HYPHEN_EDIT_REPLACE_WITH_HYPHEN = 1;
    public static final int FAKE_BOLD_TEXT_FLAG = 32;
    public static final int FILTER_BITMAP_FLAG = 2;
    static final int HIDDEN_DEFAULT_PAINT_FLAGS = 1282;
    public static final int HINTING_OFF = 0;
    public static final int HINTING_ON = 1;
    public static final int LCD_RENDER_TEXT_FLAG = 512;
    public static final int LINEAR_TEXT_FLAG = 64;
    public static final int START_HYPHEN_EDIT_INSERT_HYPHEN = 1;
    public static final int START_HYPHEN_EDIT_INSERT_ZWJ = 2;
    public static final int START_HYPHEN_EDIT_NO_EDIT = 0;
    public static final int STRIKE_THRU_TEXT_FLAG = 16;
    public static final int SUBPIXEL_TEXT_FLAG = 128;
    public static final int TEXT_RUN_FLAG_LEFT_EDGE = 8192;
    public static final int TEXT_RUN_FLAG_RIGHT_EDGE = 16384;
    public static final int UNDERLINE_TEXT_FLAG = 8;
    public static final int VERTICAL_TEXT_FLAG = 4096;
    public int mBidiFlags;
    private long mColor;
    private android.graphics.ColorFilter mColorFilter;
    private float mCompatScaling;
    private java.lang.String mFontFeatureSettings;
    private java.lang.String mFontVariationSettings;
    private boolean mHasCompatScaling;
    private float mInvCompatScaling;
    private android.os.LocaleList mLocales;
    private android.graphics.MaskFilter mMaskFilter;
    private long mNativeColorFilter;
    private long mNativePaint;
    private long mNativeShader;
    private android.graphics.PathEffect mPathEffect;
    private android.graphics.Shader mShader;
    private long mShadowLayerColor;
    private float mShadowLayerDx;
    private float mShadowLayerDy;
    private float mShadowLayerRadius;
    private android.graphics.Typeface mTypeface;
    private android.graphics.Xfermode mXfermode;
    private static final java.lang.Object sCacheLock = new java.lang.Object();
    private static final java.util.HashMap<java.lang.String, java.lang.Integer> sMinikinLocaleListIdCache = new java.util.HashMap<>();
    static final android.graphics.Paint.Style[] sStyleArray = {android.graphics.Paint.Style.FILL, android.graphics.Paint.Style.STROKE, android.graphics.Paint.Style.FILL_AND_STROKE};
    static final android.graphics.Paint.Cap[] sCapArray = {android.graphics.Paint.Cap.BUTT, android.graphics.Paint.Cap.ROUND, android.graphics.Paint.Cap.SQUARE};
    static final android.graphics.Paint.Join[] sJoinArray = {android.graphics.Paint.Join.MITER, android.graphics.Paint.Join.ROUND, android.graphics.Paint.Join.BEVEL};
    static final android.graphics.Paint.Align[] sAlignArray = {android.graphics.Paint.Align.LEFT, android.graphics.Paint.Align.CENTER, android.graphics.Paint.Align.RIGHT};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CursorOption {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EndHyphenEdit {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PaintFlag {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StartHyphenEdit {
    }

    @dalvik.annotation.optimization.CriticalNative
    private static native float nAscent(long j);

    private static native int nBreakText(long j, java.lang.String str, boolean z, float f, int i, float[] fArr);

    private static native int nBreakText(long j, char[] cArr, int i, int i2, float f, int i3, float[] fArr);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nDescent(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nEqualsForTextMeasurement(long j, long j2);

    private static native void nGetCharArrayBounds(long j, char[] cArr, int i, int i2, int i3, android.graphics.Rect rect);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetElegantTextHeight(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetEndHyphenEdit(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nGetFillPath(long j, long j2, long j3);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetFlags(long j);

    @dalvik.annotation.optimization.FastNative
    private static native float nGetFontMetrics(long j, android.graphics.Paint.FontMetrics fontMetrics, boolean z);

    @dalvik.annotation.optimization.FastNative
    private static native int nGetFontMetricsInt(long j, android.graphics.Paint.FontMetricsInt fontMetricsInt, boolean z);

    private static native void nGetFontMetricsIntForText(long j, java.lang.String str, int i, int i2, int i3, int i4, boolean z, android.graphics.Paint.FontMetricsInt fontMetricsInt);

    private static native void nGetFontMetricsIntForText(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, android.graphics.Paint.FontMetricsInt fontMetricsInt);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetHinting(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetLetterSpacing(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    private static native int nGetOffsetForAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, float f);

    private static native float nGetRunAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5);

    private static native float nGetRunCharacterAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, android.graphics.RectF rectF);

    private static native float nGetRunCharacterAdvance(long j, char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, android.graphics.RectF rectF, android.graphics.Paint.RunInfo runInfo);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetStartHyphenEdit(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetStrikeThruPosition(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetStrikeThruThickness(long j);

    private static native void nGetStringBounds(long j, java.lang.String str, int i, int i2, int i3, android.graphics.Rect rect);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetStrokeCap(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetStrokeJoin(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetStrokeMiter(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetStrokeWidth(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetStyle(long j);

    private static native float nGetTextAdvances(long j, java.lang.String str, int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

    private static native float nGetTextAdvances(long j, char[] cArr, int i, int i2, int i3, int i4, int i5, float[] fArr, int i6);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetTextAlign(long j);

    private static native void nGetTextPath(long j, int i, java.lang.String str, int i2, int i3, float f, float f2, long j2);

    private static native void nGetTextPath(long j, int i, char[] cArr, int i2, int i3, float f, float f2, long j2);

    private native int nGetTextRunCursor(long j, java.lang.String str, int i, int i2, int i3, int i4, int i5);

    private native int nGetTextRunCursor(long j, char[] cArr, int i, int i2, int i3, int i4, int i5);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetTextScaleX(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetTextSize(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetTextSkewX(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetUnderlinePosition(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetUnderlineThickness(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetWordSpacing(long j);

    private static native boolean nHasGlyph(long j, int i, java.lang.String str);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nHasShadowLayer(long j);

    private static native long nInit();

    private static native long nInitWithPaint(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nReset(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSet(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetAlpha(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetAntiAlias(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetColor(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetColor(long j, long j2, long j3);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nSetColorFilter(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetDither(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetElegantTextHeight(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetEndHyphenEdit(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetFakeBoldText(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetFilterBitmap(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetFlags(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetFontFeatureSettings(long j, java.lang.String str);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetHinting(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetLetterSpacing(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetLinearText(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nSetMaskFilter(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nSetPathEffect(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nSetShader(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetShadowLayer(long j, float f, float f2, float f3, long j2, long j3);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetStartHyphenEdit(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetStrikeThruText(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetStrokeCap(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetStrokeJoin(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetStrokeMiter(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetStrokeWidth(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetStyle(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetSubpixelText(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetTextAlign(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int nSetTextLocales(long j, java.lang.String str);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetTextLocalesByMinikinLocaleListId(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetTextScaleX(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetTextSize(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetTextSkewX(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetTypeface(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetUnderlineText(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetWordSpacing(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetXfermode(long j, int i);

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Paint.class.getClassLoader(), android.graphics.Paint.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public enum Style {
        FILL(0),
        STROKE(1),
        FILL_AND_STROKE(2);

        final int nativeInt;

        Style(int i) {
            this.nativeInt = i;
        }
    }

    public enum Cap {
        BUTT(0),
        ROUND(1),
        SQUARE(2);

        final int nativeInt;

        Cap(int i) {
            this.nativeInt = i;
        }
    }

    public enum Join {
        MITER(0),
        ROUND(1),
        BEVEL(2);

        final int nativeInt;

        Join(int i) {
            this.nativeInt = i;
        }
    }

    public enum Align {
        LEFT(0),
        CENTER(1),
        RIGHT(2);

        final int nativeInt;

        Align(int i) {
            this.nativeInt = i;
        }
    }

    public Paint() {
        this(1);
    }

    public Paint(int i) {
        this.mBidiFlags = 2;
        this.mNativePaint = nInit();
        android.graphics.Paint.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativePaint);
        setFlags(i | 1282);
        this.mInvCompatScaling = 1.0f;
        this.mCompatScaling = 1.0f;
        setTextLocales(android.os.LocaleList.getAdjustedDefault());
        this.mColor = android.graphics.Color.pack(-16777216);
        resetElegantTextHeight();
    }

    public Paint(android.graphics.Paint paint) {
        this.mBidiFlags = 2;
        this.mNativePaint = nInitWithPaint(paint.getNativeInstance());
        android.graphics.Paint.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativePaint);
        setClassVariablesFrom(paint);
    }

    public void reset() {
        nReset(this.mNativePaint);
        setFlags(1283);
        this.mColor = android.graphics.Color.pack(-16777216);
        this.mColorFilter = null;
        this.mMaskFilter = null;
        this.mPathEffect = null;
        this.mShader = null;
        this.mNativeShader = 0L;
        this.mTypeface = null;
        this.mXfermode = null;
        this.mHasCompatScaling = false;
        this.mCompatScaling = 1.0f;
        this.mInvCompatScaling = 1.0f;
        this.mBidiFlags = 2;
        setTextLocales(android.os.LocaleList.getAdjustedDefault());
        resetElegantTextHeight();
        this.mFontFeatureSettings = null;
        this.mFontVariationSettings = null;
        this.mShadowLayerRadius = 0.0f;
        this.mShadowLayerDx = 0.0f;
        this.mShadowLayerDy = 0.0f;
        this.mShadowLayerColor = android.graphics.Color.pack(0);
    }

    public void set(android.graphics.Paint paint) {
        if (this != paint) {
            nSet(this.mNativePaint, paint.mNativePaint);
            setClassVariablesFrom(paint);
        }
    }

    private void setClassVariablesFrom(android.graphics.Paint paint) {
        this.mColor = paint.mColor;
        this.mColorFilter = paint.mColorFilter;
        this.mMaskFilter = paint.mMaskFilter;
        this.mPathEffect = paint.mPathEffect;
        this.mShader = paint.mShader;
        this.mNativeShader = paint.mNativeShader;
        this.mTypeface = paint.mTypeface;
        this.mXfermode = paint.mXfermode;
        this.mHasCompatScaling = paint.mHasCompatScaling;
        this.mCompatScaling = paint.mCompatScaling;
        this.mInvCompatScaling = paint.mInvCompatScaling;
        this.mBidiFlags = paint.mBidiFlags;
        this.mLocales = paint.mLocales;
        this.mFontFeatureSettings = paint.mFontFeatureSettings;
        this.mFontVariationSettings = paint.mFontVariationSettings;
        this.mShadowLayerRadius = paint.mShadowLayerRadius;
        this.mShadowLayerDx = paint.mShadowLayerDx;
        this.mShadowLayerDy = paint.mShadowLayerDy;
        this.mShadowLayerColor = paint.mShadowLayerColor;
    }

    public void setCompatibilityScaling(float f) {
        if (f == 1.0d) {
            this.mHasCompatScaling = false;
            this.mInvCompatScaling = 1.0f;
            this.mCompatScaling = 1.0f;
        } else {
            this.mHasCompatScaling = true;
            this.mCompatScaling = f;
            this.mInvCompatScaling = 1.0f / f;
        }
    }

    public synchronized long getNativeInstance() {
        long nativeInstance = this.mShader == null ? 0L : this.mShader.getNativeInstance(isFilterBitmap());
        if (nativeInstance != this.mNativeShader) {
            this.mNativeShader = nativeInstance;
            nSetShader(this.mNativePaint, this.mNativeShader);
        }
        long nativeInstance2 = this.mColorFilter != null ? this.mColorFilter.getNativeInstance() : 0L;
        if (nativeInstance2 != this.mNativeColorFilter) {
            this.mNativeColorFilter = nativeInstance2;
            nSetColorFilter(this.mNativePaint, this.mNativeColorFilter);
        }
        return this.mNativePaint;
    }

    public int getBidiFlags() {
        return this.mBidiFlags;
    }

    public void setBidiFlags(int i) {
        int i2 = i & 7;
        if (i2 > 5) {
            throw new java.lang.IllegalArgumentException("unknown bidi flag: " + i2);
        }
        this.mBidiFlags = i2;
    }

    public int getFlags() {
        return nGetFlags(this.mNativePaint);
    }

    public void setFlags(int i) {
        nSetFlags(this.mNativePaint, i);
    }

    public int getHinting() {
        return nGetHinting(this.mNativePaint);
    }

    public void setHinting(int i) {
        nSetHinting(this.mNativePaint, i);
    }

    public final boolean isAntiAlias() {
        return (getFlags() & 1) != 0;
    }

    public void setAntiAlias(boolean z) {
        nSetAntiAlias(this.mNativePaint, z);
    }

    public final boolean isDither() {
        return (getFlags() & 4) != 0;
    }

    public void setDither(boolean z) {
        nSetDither(this.mNativePaint, z);
    }

    public final boolean isLinearText() {
        return (getFlags() & 64) != 0;
    }

    public void setLinearText(boolean z) {
        nSetLinearText(this.mNativePaint, z);
    }

    public final boolean isSubpixelText() {
        return (getFlags() & 128) != 0;
    }

    public void setSubpixelText(boolean z) {
        nSetSubpixelText(this.mNativePaint, z);
    }

    public final boolean isUnderlineText() {
        return (getFlags() & 8) != 0;
    }

    public float getUnderlinePosition() {
        return nGetUnderlinePosition(this.mNativePaint);
    }

    public float getUnderlineThickness() {
        return nGetUnderlineThickness(this.mNativePaint);
    }

    public void setUnderlineText(boolean z) {
        nSetUnderlineText(this.mNativePaint, z);
    }

    public final boolean isStrikeThruText() {
        return (getFlags() & 16) != 0;
    }

    public float getStrikeThruPosition() {
        return nGetStrikeThruPosition(this.mNativePaint);
    }

    public float getStrikeThruThickness() {
        return nGetStrikeThruThickness(this.mNativePaint);
    }

    public void setStrikeThruText(boolean z) {
        nSetStrikeThruText(this.mNativePaint, z);
    }

    public final boolean isFakeBoldText() {
        return (getFlags() & 32) != 0;
    }

    public void setFakeBoldText(boolean z) {
        nSetFakeBoldText(this.mNativePaint, z);
    }

    public final boolean isFilterBitmap() {
        return (getFlags() & 2) != 0;
    }

    public void setFilterBitmap(boolean z) {
        nSetFilterBitmap(this.mNativePaint, z);
    }

    public android.graphics.Paint.Style getStyle() {
        return sStyleArray[nGetStyle(this.mNativePaint)];
    }

    public void setStyle(android.graphics.Paint.Style style) {
        nSetStyle(this.mNativePaint, style.nativeInt);
    }

    public int getColor() {
        return android.graphics.Color.toArgb(this.mColor);
    }

    public long getColorLong() {
        return this.mColor;
    }

    public void setColor(int i) {
        nSetColor(this.mNativePaint, i);
        this.mColor = android.graphics.Color.pack(i);
    }

    public void setColor(long j) {
        nSetColor(this.mNativePaint, android.graphics.Color.colorSpace(j).getNativeInstance(), j);
        this.mColor = j;
    }

    public int getAlpha() {
        return java.lang.Math.round(android.graphics.Color.alpha(this.mColor) * 255.0f);
    }

    public void setAlpha(int i) {
        this.mColor = android.graphics.Color.pack(android.graphics.Color.red(this.mColor), android.graphics.Color.green(this.mColor), android.graphics.Color.blue(this.mColor), i * 0.003921569f, android.graphics.Color.colorSpace(this.mColor));
        nSetAlpha(this.mNativePaint, i);
    }

    public void setARGB(int i, int i2, int i3, int i4) {
        setColor((i << 24) | (i2 << 16) | (i3 << 8) | i4);
    }

    public float getStrokeWidth() {
        return nGetStrokeWidth(this.mNativePaint);
    }

    public void setStrokeWidth(float f) {
        nSetStrokeWidth(this.mNativePaint, f);
    }

    public float getStrokeMiter() {
        return nGetStrokeMiter(this.mNativePaint);
    }

    public void setStrokeMiter(float f) {
        nSetStrokeMiter(this.mNativePaint, f);
    }

    public android.graphics.Paint.Cap getStrokeCap() {
        return sCapArray[nGetStrokeCap(this.mNativePaint)];
    }

    public void setStrokeCap(android.graphics.Paint.Cap cap) {
        nSetStrokeCap(this.mNativePaint, cap.nativeInt);
    }

    public android.graphics.Paint.Join getStrokeJoin() {
        return sJoinArray[nGetStrokeJoin(this.mNativePaint)];
    }

    public void setStrokeJoin(android.graphics.Paint.Join join) {
        nSetStrokeJoin(this.mNativePaint, join.nativeInt);
    }

    public boolean getFillPath(android.graphics.Path path, android.graphics.Path path2) {
        return nGetFillPath(this.mNativePaint, path.readOnlyNI(), path2.mutateNI());
    }

    public android.graphics.Shader getShader() {
        return this.mShader;
    }

    public android.graphics.Shader setShader(android.graphics.Shader shader) {
        if (this.mShader != shader) {
            this.mNativeShader = -1L;
            nSetShader(this.mNativePaint, 0L);
        }
        this.mShader = shader;
        return shader;
    }

    public android.graphics.ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    public android.graphics.ColorFilter setColorFilter(android.graphics.ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mNativeColorFilter = -1L;
        }
        this.mColorFilter = colorFilter;
        return colorFilter;
    }

    public android.graphics.Xfermode getXfermode() {
        return this.mXfermode;
    }

    public android.graphics.BlendMode getBlendMode() {
        if (this.mXfermode == null) {
            return null;
        }
        return android.graphics.BlendMode.fromValue(this.mXfermode.porterDuffMode);
    }

    public android.graphics.Xfermode setXfermode(android.graphics.Xfermode xfermode) {
        return installXfermode(xfermode);
    }

    private android.graphics.Xfermode installXfermode(android.graphics.Xfermode xfermode) {
        int i = xfermode != null ? xfermode.porterDuffMode : android.graphics.Xfermode.DEFAULT;
        if (i != (this.mXfermode != null ? this.mXfermode.porterDuffMode : android.graphics.Xfermode.DEFAULT)) {
            nSetXfermode(this.mNativePaint, i);
        }
        this.mXfermode = xfermode;
        return xfermode;
    }

    public void setBlendMode(android.graphics.BlendMode blendMode) {
        installXfermode(blendMode != null ? blendMode.getXfermode() : null);
    }

    public android.graphics.PathEffect getPathEffect() {
        return this.mPathEffect;
    }

    public android.graphics.PathEffect setPathEffect(android.graphics.PathEffect pathEffect) {
        long j;
        if (pathEffect == null) {
            j = 0;
        } else {
            j = pathEffect.native_instance;
        }
        nSetPathEffect(this.mNativePaint, j);
        this.mPathEffect = pathEffect;
        return pathEffect;
    }

    public android.graphics.MaskFilter getMaskFilter() {
        return this.mMaskFilter;
    }

    public android.graphics.MaskFilter setMaskFilter(android.graphics.MaskFilter maskFilter) {
        long j;
        if (maskFilter == null) {
            j = 0;
        } else {
            j = maskFilter.native_instance;
        }
        nSetMaskFilter(this.mNativePaint, j);
        this.mMaskFilter = maskFilter;
        return maskFilter;
    }

    public android.graphics.Typeface getTypeface() {
        return this.mTypeface;
    }

    public android.graphics.Typeface setTypeface(android.graphics.Typeface typeface) {
        nSetTypeface(this.mNativePaint, typeface == null ? 0L : typeface.native_instance);
        this.mTypeface = typeface;
        return typeface;
    }

    @java.lang.Deprecated
    public android.graphics.Rasterizer getRasterizer() {
        return null;
    }

    @java.lang.Deprecated
    public android.graphics.Rasterizer setRasterizer(android.graphics.Rasterizer rasterizer) {
        return rasterizer;
    }

    public void setShadowLayer(float f, float f2, float f3, int i) {
        setShadowLayer(f, f2, f3, android.graphics.Color.pack(i));
    }

    public void setShadowLayer(float f, float f2, float f3, long j) {
        nSetShadowLayer(this.mNativePaint, f, f2, f3, android.graphics.Color.colorSpace(j).getNativeInstance(), j);
        this.mShadowLayerRadius = f;
        this.mShadowLayerDx = f2;
        this.mShadowLayerDy = f3;
        this.mShadowLayerColor = j;
    }

    public void clearShadowLayer() {
        setShadowLayer(0.0f, 0.0f, 0.0f, 0);
    }

    public boolean hasShadowLayer() {
        return nHasShadowLayer(this.mNativePaint);
    }

    public float getShadowLayerRadius() {
        return this.mShadowLayerRadius;
    }

    public float getShadowLayerDx() {
        return this.mShadowLayerDx;
    }

    public float getShadowLayerDy() {
        return this.mShadowLayerDy;
    }

    public int getShadowLayerColor() {
        return android.graphics.Color.toArgb(this.mShadowLayerColor);
    }

    public long getShadowLayerColorLong() {
        return this.mShadowLayerColor;
    }

    public android.graphics.Paint.Align getTextAlign() {
        return sAlignArray[nGetTextAlign(this.mNativePaint)];
    }

    public void setTextAlign(android.graphics.Paint.Align align) {
        nSetTextAlign(this.mNativePaint, align.nativeInt);
    }

    public java.util.Locale getTextLocale() {
        return this.mLocales.get(0);
    }

    public android.os.LocaleList getTextLocales() {
        return this.mLocales;
    }

    public void setTextLocale(java.util.Locale locale) {
        if (locale == null) {
            throw new java.lang.IllegalArgumentException("locale cannot be null");
        }
        if (this.mLocales != null && this.mLocales.size() == 1 && locale.equals(this.mLocales.get(0))) {
            return;
        }
        this.mLocales = new android.os.LocaleList(locale);
        syncTextLocalesWithMinikin();
    }

    public void setTextLocales(android.os.LocaleList localeList) {
        if (localeList == null || localeList.isEmpty()) {
            throw new java.lang.IllegalArgumentException("locales cannot be null or empty");
        }
        if (localeList.equals(this.mLocales)) {
            return;
        }
        this.mLocales = localeList;
        syncTextLocalesWithMinikin();
    }

    private void syncTextLocalesWithMinikin() {
        java.lang.String languageTags = this.mLocales.toLanguageTags();
        synchronized (sCacheLock) {
            java.lang.Integer num = sMinikinLocaleListIdCache.get(languageTags);
            if (num == null) {
                sMinikinLocaleListIdCache.put(languageTags, java.lang.Integer.valueOf(nSetTextLocales(this.mNativePaint, languageTags)));
            } else {
                nSetTextLocalesByMinikinLocaleListId(this.mNativePaint, num.intValue());
            }
        }
    }

    public boolean isElegantTextHeight() {
        switch (nGetElegantTextHeight(this.mNativePaint)) {
            case 0:
                return true;
            case 1:
                return false;
            default:
                return com.android.text.flags.Flags.deprecateUiFonts();
        }
    }

    public void setElegantTextHeight(boolean z) {
        nSetElegantTextHeight(this.mNativePaint, !z ? 1 : 0);
    }

    private void resetElegantTextHeight() {
        if (android.app.compat.CompatChanges.isChangeEnabled(DEPRECATE_UI_FONT)) {
            nSetElegantTextHeight(this.mNativePaint, -1);
        } else {
            nSetElegantTextHeight(this.mNativePaint, 1);
        }
    }

    public float getTextSize() {
        return nGetTextSize(this.mNativePaint);
    }

    public void setTextSize(float f) {
        nSetTextSize(this.mNativePaint, f);
    }

    public float getTextScaleX() {
        return nGetTextScaleX(this.mNativePaint);
    }

    public void setTextScaleX(float f) {
        nSetTextScaleX(this.mNativePaint, f);
    }

    public float getTextSkewX() {
        return nGetTextSkewX(this.mNativePaint);
    }

    public void setTextSkewX(float f) {
        nSetTextSkewX(this.mNativePaint, f);
    }

    public float getLetterSpacing() {
        return nGetLetterSpacing(this.mNativePaint);
    }

    public void setLetterSpacing(float f) {
        nSetLetterSpacing(this.mNativePaint, f);
    }

    public float getWordSpacing() {
        return nGetWordSpacing(this.mNativePaint);
    }

    public void setWordSpacing(float f) {
        nSetWordSpacing(this.mNativePaint, f);
    }

    public java.lang.String getFontFeatureSettings() {
        return this.mFontFeatureSettings;
    }

    public void setFontFeatureSettings(java.lang.String str) {
        if (str != null && str.equals("")) {
            str = null;
        }
        if (str != null || this.mFontFeatureSettings != null) {
            if (str != null && str.equals(this.mFontFeatureSettings)) {
                return;
            }
            this.mFontFeatureSettings = str;
            nSetFontFeatureSettings(this.mNativePaint, str);
        }
    }

    public java.lang.String getFontVariationSettings() {
        return this.mFontVariationSettings;
    }

    public boolean setFontVariationSettings(java.lang.String str) {
        java.lang.String nullIfEmpty = android.text.TextUtils.nullIfEmpty(str);
        if (nullIfEmpty == this.mFontVariationSettings || (nullIfEmpty != null && nullIfEmpty.equals(this.mFontVariationSettings))) {
            return true;
        }
        if (nullIfEmpty == null || nullIfEmpty.length() == 0) {
            this.mFontVariationSettings = null;
            setTypeface(android.graphics.Typeface.createFromTypefaceWithVariation(this.mTypeface, java.util.Collections.emptyList()));
            return true;
        }
        android.graphics.Typeface typeface = this.mTypeface == null ? android.graphics.Typeface.DEFAULT : this.mTypeface;
        android.graphics.fonts.FontVariationAxis[] fromFontVariationSettings = android.graphics.fonts.FontVariationAxis.fromFontVariationSettings(nullIfEmpty);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.graphics.fonts.FontVariationAxis fontVariationAxis : fromFontVariationSettings) {
            if (typeface.isSupportedAxes(fontVariationAxis.getOpenTypeTagValue())) {
                arrayList.add(fontVariationAxis);
            }
        }
        if (arrayList.isEmpty()) {
            return false;
        }
        this.mFontVariationSettings = nullIfEmpty;
        setTypeface(android.graphics.Typeface.createFromTypefaceWithVariation(typeface, arrayList));
        return true;
    }

    public int getStartHyphenEdit() {
        return nGetStartHyphenEdit(this.mNativePaint);
    }

    public int getEndHyphenEdit() {
        return nGetEndHyphenEdit(this.mNativePaint);
    }

    public void setStartHyphenEdit(int i) {
        nSetStartHyphenEdit(this.mNativePaint, i);
    }

    public void setEndHyphenEdit(int i) {
        nSetEndHyphenEdit(this.mNativePaint, i);
    }

    public float ascent() {
        return nAscent(this.mNativePaint);
    }

    public float descent() {
        return nDescent(this.mNativePaint);
    }

    public static class FontMetrics {
        public float ascent;
        public float bottom;
        public float descent;
        public float leading;
        public float top;

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof android.graphics.Paint.FontMetrics)) {
                return false;
            }
            android.graphics.Paint.FontMetrics fontMetrics = (android.graphics.Paint.FontMetrics) obj;
            if (fontMetrics.top == this.top && fontMetrics.ascent == this.ascent && fontMetrics.descent == this.descent && fontMetrics.bottom == this.bottom && fontMetrics.leading == this.leading) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Float.valueOf(this.top), java.lang.Float.valueOf(this.ascent), java.lang.Float.valueOf(this.descent), java.lang.Float.valueOf(this.bottom), java.lang.Float.valueOf(this.leading));
        }

        public java.lang.String toString() {
            return "FontMetrics{top=" + this.top + ", ascent=" + this.ascent + ", descent=" + this.descent + ", bottom=" + this.bottom + ", leading=" + this.leading + '}';
        }
    }

    public float getFontMetrics(android.graphics.Paint.FontMetrics fontMetrics) {
        return nGetFontMetrics(this.mNativePaint, fontMetrics, false);
    }

    public android.graphics.Paint.FontMetrics getFontMetrics() {
        android.graphics.Paint.FontMetrics fontMetrics = new android.graphics.Paint.FontMetrics();
        getFontMetrics(fontMetrics);
        return fontMetrics;
    }

    public void getFontMetricsForLocale(android.graphics.Paint.FontMetrics fontMetrics) {
        nGetFontMetrics(this.mNativePaint, fontMetrics, true);
    }

    public void getFontMetricsInt(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        int i5;
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("text must not be null");
        }
        if (i < 0 || i >= charSequence.length()) {
            throw new java.lang.IllegalArgumentException("start argument is out of bounds.");
        }
        if (i2 < 0 || i + i2 > charSequence.length()) {
            throw new java.lang.IllegalArgumentException("count argument is out of bounds.");
        }
        if (i3 < 0 || i3 >= charSequence.length()) {
            throw new java.lang.IllegalArgumentException("ctxStart argument is out of bounds.");
        }
        if (i4 < 0 || (i5 = i3 + i4) > charSequence.length()) {
            throw new java.lang.IllegalArgumentException("ctxCount argument is out of bounds.");
        }
        if (fontMetricsInt == null) {
            throw new java.lang.IllegalArgumentException("outMetrics must not be null.");
        }
        if (i2 == 0) {
            getFontMetricsInt(fontMetricsInt);
            return;
        }
        if (charSequence instanceof java.lang.String) {
            nGetFontMetricsIntForText(this.mNativePaint, (java.lang.String) charSequence, i, i2, i3, i4, z, fontMetricsInt);
            return;
        }
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i4);
        try {
            android.text.TextUtils.getChars(charSequence, i3, i5, obtain, 0);
            nGetFontMetricsIntForText(this.mNativePaint, obtain, i - i3, i2, 0, i4, z, fontMetricsInt);
        } finally {
            android.graphics.TemporaryBuffer.recycle(obtain);
        }
    }

    public void getFontMetricsInt(char[] cArr, int i, int i2, int i3, int i4, boolean z, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        if (cArr == null) {
            throw new java.lang.IllegalArgumentException("text must not be null");
        }
        if (i < 0 || i >= cArr.length) {
            throw new java.lang.IllegalArgumentException("start argument is out of bounds.");
        }
        if (i2 < 0 || i + i2 > cArr.length) {
            throw new java.lang.IllegalArgumentException("count argument is out of bounds.");
        }
        if (i3 < 0 || i3 >= cArr.length) {
            throw new java.lang.IllegalArgumentException("ctxStart argument is out of bounds.");
        }
        if (i4 < 0 || i3 + i4 > cArr.length) {
            throw new java.lang.IllegalArgumentException("ctxCount argument is out of bounds.");
        }
        if (fontMetricsInt == null) {
            throw new java.lang.IllegalArgumentException("outMetrics must not be null.");
        }
        if (i2 == 0) {
            getFontMetricsInt(fontMetricsInt);
        } else {
            nGetFontMetricsIntForText(this.mNativePaint, cArr, i, i2, i3, i4, z, fontMetricsInt);
        }
    }

    public static class FontMetricsInt {
        public int ascent;
        public int bottom;
        public int descent;
        public int leading;
        public int top;

        public void set(android.graphics.Paint.FontMetricsInt fontMetricsInt) {
            this.top = fontMetricsInt.top;
            this.ascent = fontMetricsInt.ascent;
            this.descent = fontMetricsInt.descent;
            this.bottom = fontMetricsInt.bottom;
            this.leading = fontMetricsInt.leading;
        }

        public void set(android.graphics.Paint.FontMetrics fontMetrics) {
            this.top = (int) java.lang.Math.floor(fontMetrics.top);
            this.ascent = java.lang.Math.round(fontMetrics.ascent);
            this.descent = java.lang.Math.round(fontMetrics.descent);
            this.bottom = (int) java.lang.Math.ceil(fontMetrics.bottom);
            this.leading = java.lang.Math.round(fontMetrics.leading);
        }

        public java.lang.String toString() {
            return "FontMetricsInt: top=" + this.top + " ascent=" + this.ascent + " descent=" + this.descent + " bottom=" + this.bottom + " leading=" + this.leading;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.graphics.Paint.FontMetricsInt)) {
                return false;
            }
            android.graphics.Paint.FontMetricsInt fontMetricsInt = (android.graphics.Paint.FontMetricsInt) obj;
            return this.top == fontMetricsInt.top && this.ascent == fontMetricsInt.ascent && this.descent == fontMetricsInt.descent && this.bottom == fontMetricsInt.bottom && this.leading == fontMetricsInt.leading;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.top), java.lang.Integer.valueOf(this.ascent), java.lang.Integer.valueOf(this.descent), java.lang.Integer.valueOf(this.bottom), java.lang.Integer.valueOf(this.leading));
        }
    }

    public int getFontMetricsInt(android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        return nGetFontMetricsInt(this.mNativePaint, fontMetricsInt, false);
    }

    public android.graphics.Paint.FontMetricsInt getFontMetricsInt() {
        android.graphics.Paint.FontMetricsInt fontMetricsInt = new android.graphics.Paint.FontMetricsInt();
        getFontMetricsInt(fontMetricsInt);
        return fontMetricsInt;
    }

    public void getFontMetricsIntForLocale(android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        nGetFontMetricsInt(this.mNativePaint, fontMetricsInt, true);
    }

    public static final class RunInfo {
        private int mClusterCount = 0;

        public int getClusterCount() {
            return this.mClusterCount;
        }

        public void setClusterCount(int i) {
            this.mClusterCount = i;
        }
    }

    public float getFontSpacing() {
        return getFontMetrics(null);
    }

    public float measureText(char[] cArr, int i, int i2) {
        if (cArr == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        if ((i | i2) < 0 || i + i2 > cArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0.0f;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (this.mHasCompatScaling) {
                float textSize = getTextSize();
                setTextSize(this.mCompatScaling * textSize);
                float nGetTextAdvances = nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0);
                setTextSize(textSize);
                return (float) java.lang.Math.ceil(nGetTextAdvances * this.mInvCompatScaling);
            }
            return (float) java.lang.Math.ceil(nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0));
        } finally {
            setFlags(flags);
        }
    }

    public float measureText(java.lang.String str, int i, int i2) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        if ((i | i2 | (i2 - i) | (str.length() - i2)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (str.length() == 0 || i == i2) {
            return 0.0f;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (this.mHasCompatScaling) {
                float textSize = getTextSize();
                setTextSize(this.mCompatScaling * textSize);
                float nGetTextAdvances = nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0);
                setTextSize(textSize);
                return (float) java.lang.Math.ceil(nGetTextAdvances * this.mInvCompatScaling);
            }
            return (float) java.lang.Math.ceil(nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, (float[]) null, 0));
        } finally {
            setFlags(flags);
        }
    }

    public float measureText(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        return measureText(str, 0, str.length());
    }

    public float measureText(java.lang.CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (charSequence.length() == 0 || i == i2) {
            return 0.0f;
        }
        if (charSequence instanceof java.lang.String) {
            return measureText((java.lang.String) charSequence, i, i2);
        }
        if ((charSequence instanceof android.text.SpannedString) || (charSequence instanceof android.text.SpannableString)) {
            return measureText(charSequence.toString(), i, i2);
        }
        if (charSequence instanceof android.text.GraphicsOperations) {
            return ((android.text.GraphicsOperations) charSequence).measureText(i, i2, this);
        }
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i3);
        android.text.TextUtils.getChars(charSequence, i, i2, obtain, 0);
        float measureText = measureText(obtain, 0, i3);
        android.graphics.TemporaryBuffer.recycle(obtain);
        return measureText;
    }

    public int breakText(char[] cArr, int i, int i2, float f, float[] fArr) {
        if (cArr == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        if (i < 0 || cArr.length - i < java.lang.Math.abs(i2)) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0;
        }
        if (!this.mHasCompatScaling) {
            return nBreakText(this.mNativePaint, cArr, i, i2, f, this.mBidiFlags, fArr);
        }
        float textSize = getTextSize();
        setTextSize(this.mCompatScaling * textSize);
        int nBreakText = nBreakText(this.mNativePaint, cArr, i, i2, f * this.mCompatScaling, this.mBidiFlags, fArr);
        setTextSize(textSize);
        if (fArr != null) {
            fArr[0] = fArr[0] * this.mInvCompatScaling;
        }
        return nBreakText;
    }

    public int breakText(java.lang.CharSequence charSequence, int i, int i2, boolean z, float f, float[] fArr) {
        int breakText;
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (charSequence.length() == 0 || i == i2) {
            return 0;
        }
        if (i == 0 && (charSequence instanceof java.lang.String) && i2 == charSequence.length()) {
            return breakText((java.lang.String) charSequence, z, f, fArr);
        }
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i3);
        android.text.TextUtils.getChars(charSequence, i, i2, obtain, 0);
        if (z) {
            breakText = breakText(obtain, 0, i3, f, fArr);
        } else {
            breakText = breakText(obtain, 0, -i3, f, fArr);
        }
        android.graphics.TemporaryBuffer.recycle(obtain);
        return breakText;
    }

    public int breakText(java.lang.String str, boolean z, float f, float[] fArr) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        if (str.length() == 0) {
            return 0;
        }
        if (!this.mHasCompatScaling) {
            return nBreakText(this.mNativePaint, str, z, f, this.mBidiFlags, fArr);
        }
        float textSize = getTextSize();
        setTextSize(this.mCompatScaling * textSize);
        int nBreakText = nBreakText(this.mNativePaint, str, z, f * this.mCompatScaling, this.mBidiFlags, fArr);
        setTextSize(textSize);
        if (fArr != null) {
            fArr[0] = fArr[0] * this.mInvCompatScaling;
        }
        return nBreakText;
    }

    public int getTextWidths(char[] cArr, int i, int i2, float[] fArr) {
        if (cArr == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        if ((i | i2) < 0 || i + i2 > cArr.length || i2 > fArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, fArr, 0);
                return i2;
            }
            float textSize = getTextSize();
            setTextSize(this.mCompatScaling * textSize);
            nGetTextAdvances(this.mNativePaint, cArr, i, i2, i, i2, this.mBidiFlags, fArr, 0);
            setTextSize(textSize);
            for (int i3 = 0; i3 < i2; i3++) {
                fArr[i3] = fArr[i3] * this.mInvCompatScaling;
            }
            return i2;
        } finally {
            setFlags(flags);
        }
    }

    public int getTextWidths(java.lang.CharSequence charSequence, int i, int i2, float[] fArr) {
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (i3 > fArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        if (charSequence.length() == 0 || i == i2) {
            return 0;
        }
        if (charSequence instanceof java.lang.String) {
            return getTextWidths((java.lang.String) charSequence, i, i2, fArr);
        }
        if ((charSequence instanceof android.text.SpannedString) || (charSequence instanceof android.text.SpannableString)) {
            return getTextWidths(charSequence.toString(), i, i2, fArr);
        }
        if (charSequence instanceof android.text.GraphicsOperations) {
            return ((android.text.GraphicsOperations) charSequence).getTextWidths(i, i2, fArr, this);
        }
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i3);
        android.text.TextUtils.getChars(charSequence, i, i2, obtain, 0);
        int textWidths = getTextWidths(obtain, 0, i3, fArr);
        android.graphics.TemporaryBuffer.recycle(obtain);
        return textWidths;
    }

    public int getTextWidths(java.lang.String str, int i, int i2, float[] fArr) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        int i3 = i2 - i;
        if ((i | i2 | i3 | (str.length() - i2)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (i3 > fArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        if (str.length() == 0 || i == i2) {
            return 0;
        }
        int flags = getFlags();
        setFlags(getFlags() | 24576);
        try {
            if (!this.mHasCompatScaling) {
                nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, fArr, 0);
                return i3;
            }
            float textSize = getTextSize();
            setTextSize(this.mCompatScaling * textSize);
            nGetTextAdvances(this.mNativePaint, str, i, i2, i, i2, this.mBidiFlags, fArr, 0);
            setTextSize(textSize);
            for (int i4 = 0; i4 < i3; i4++) {
                fArr[i4] = fArr[i4] * this.mInvCompatScaling;
            }
            return i3;
        } finally {
            setFlags(flags);
        }
    }

    public int getTextWidths(java.lang.String str, float[] fArr) {
        return getTextWidths(str, 0, str.length(), fArr);
    }

    public float getTextRunAdvances(char[] cArr, int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5) {
        if (cArr == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        int i6 = i3 + i4;
        if ((i | i2 | i3 | i4 | i5 | (i - i3) | (i4 - i2) | (i6 - (i + i2)) | (cArr.length - i6) | (fArr == null ? 0 : fArr.length - (i5 + i2))) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (cArr.length == 0 || i2 == 0) {
            return 0.0f;
        }
        if (!this.mHasCompatScaling) {
            return nGetTextAdvances(this.mNativePaint, cArr, i, i2, i3, i4, z ? 5 : 4, fArr, i5);
        }
        float textSize = getTextSize();
        setTextSize(this.mCompatScaling * textSize);
        float nGetTextAdvances = nGetTextAdvances(this.mNativePaint, cArr, i, i2, i3, i4, z ? 5 : 4, fArr, i5);
        setTextSize(textSize);
        if (fArr != null) {
            int i7 = i5 + i2;
            for (int i8 = i5; i8 < i7; i8++) {
                fArr[i8] = fArr[i8] * this.mInvCompatScaling;
            }
        }
        return nGetTextAdvances * this.mInvCompatScaling;
    }

    public int getTextRunCursor(char[] cArr, int i, int i2, boolean z, int i3, int i4) {
        int i5 = i + i2;
        if ((i | i5 | i3 | (i5 - i) | (i3 - i) | (i5 - i3) | (cArr.length - i5) | i4) < 0 || i4 > 4) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return nGetTextRunCursor(this.mNativePaint, cArr, i, i2, z ? 1 : 0, i3, i4);
    }

    public int getTextRunCursor(java.lang.CharSequence charSequence, int i, int i2, boolean z, int i3, int i4) {
        if ((charSequence instanceof java.lang.String) || (charSequence instanceof android.text.SpannedString) || (charSequence instanceof android.text.SpannableString)) {
            return getTextRunCursor(charSequence.toString(), i, i2, z, i3, i4);
        }
        if (charSequence instanceof android.text.GraphicsOperations) {
            return ((android.text.GraphicsOperations) charSequence).getTextRunCursor(i, i2, z, i3, i4, this);
        }
        int i5 = i2 - i;
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i5);
        android.text.TextUtils.getChars(charSequence, i, i2, obtain, 0);
        int textRunCursor = getTextRunCursor(obtain, 0, i5, z, i3 - i, i4);
        android.graphics.TemporaryBuffer.recycle(obtain);
        if (textRunCursor == -1) {
            return -1;
        }
        return textRunCursor + i;
    }

    public int getTextRunCursor(java.lang.String str, int i, int i2, boolean z, int i3, int i4) {
        if ((i | i2 | i3 | (i2 - i) | (i3 - i) | (i2 - i3) | (str.length() - i2) | i4) < 0 || i4 > 4) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return nGetTextRunCursor(this.mNativePaint, str, i, i2, z ? 1 : 0, i3, i4);
    }

    public void getTextPath(char[] cArr, int i, int i2, float f, float f2, android.graphics.Path path) {
        if ((i | i2) >= 0 && i + i2 <= cArr.length) {
            nGetTextPath(this.mNativePaint, this.mBidiFlags, cArr, i, i2, f, f2, path.mutateNI());
            return;
        }
        throw new java.lang.ArrayIndexOutOfBoundsException();
    }

    public void getTextPath(java.lang.String str, int i, int i2, float f, float f2, android.graphics.Path path) {
        if ((i | i2 | (i2 - i) | (str.length() - i2)) >= 0) {
            nGetTextPath(this.mNativePaint, this.mBidiFlags, str, i, i2, f, f2, path.mutateNI());
            return;
        }
        throw new java.lang.IndexOutOfBoundsException();
    }

    public void getTextBounds(java.lang.String str, int i, int i2, android.graphics.Rect rect) {
        if ((i | i2 | (i2 - i) | (str.length() - i2)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (rect == null) {
            throw new java.lang.NullPointerException("need bounds Rect");
        }
        nGetStringBounds(this.mNativePaint, str, i, i2, this.mBidiFlags, rect);
    }

    public void getTextBounds(java.lang.CharSequence charSequence, int i, int i2, android.graphics.Rect rect) {
        int i3 = i2 - i;
        if ((i | i2 | i3 | (charSequence.length() - i2)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (rect == null) {
            throw new java.lang.NullPointerException("need bounds Rect");
        }
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i3);
        android.text.TextUtils.getChars(charSequence, i, i2, obtain, 0);
        getTextBounds(obtain, 0, i3, rect);
        android.graphics.TemporaryBuffer.recycle(obtain);
    }

    public void getTextBounds(char[] cArr, int i, int i2, android.graphics.Rect rect) {
        if ((i | i2) < 0 || i + i2 > cArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        if (rect == null) {
            throw new java.lang.NullPointerException("need bounds Rect");
        }
        nGetCharArrayBounds(this.mNativePaint, cArr, i, i2, this.mBidiFlags, rect);
    }

    public boolean hasGlyph(java.lang.String str) {
        return nHasGlyph(this.mNativePaint, this.mBidiFlags, str);
    }

    public float getRunAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5) {
        if (cArr != null) {
            if ((i3 | i | i5 | i2 | i4 | (i - i3) | (i5 - i) | (i2 - i5) | (i4 - i2) | (cArr.length - i4)) < 0) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            if (i2 == i) {
                return 0.0f;
            }
            return nGetRunAdvance(this.mNativePaint, cArr, i, i2, i3, i4, z, i5);
        }
        throw new java.lang.IllegalArgumentException("text cannot be null");
    }

    public float getRunAdvance(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, int i5) {
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        int i6 = i - i3;
        if ((i3 | i | i5 | i2 | i4 | i6 | (i5 - i) | (i2 - i5) | (i4 - i2) | (charSequence.length() - i4)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (i2 == i) {
            return 0.0f;
        }
        int i7 = i4 - i3;
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i7);
        android.text.TextUtils.getChars(charSequence, i3, i4, obtain, 0);
        float runAdvance = getRunAdvance(obtain, i6, i2 - i3, 0, i7, z, i5 - i3);
        android.graphics.TemporaryBuffer.recycle(obtain);
        return runAdvance;
    }

    public float getRunCharacterAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6) {
        return getRunCharacterAdvance(cArr, i, i2, i3, i4, z, i5, fArr, i6, (android.graphics.RectF) null, (android.graphics.Paint.RunInfo) null);
    }

    public float getRunCharacterAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, android.graphics.RectF rectF, android.graphics.Paint.RunInfo runInfo) {
        if (cArr != null) {
            if (i3 < 0 || i4 > cArr.length) {
                throw new java.lang.IndexOutOfBoundsException("Invalid Context Range: " + i3 + ", " + i4 + " must be in 0, " + cArr.length);
            }
            if (i < i3 || i4 < i2) {
                throw new java.lang.IndexOutOfBoundsException("Invalid start/end range: " + i + ", " + i2 + " must be in " + i3 + ", " + i4);
            }
            if (i5 < i || i2 < i5) {
                throw new java.lang.IndexOutOfBoundsException("Invalid offset position: " + i5 + " must be in " + i + ", " + i2);
            }
            if (fArr != null && fArr.length < (i6 - i) + i2) {
                throw new java.lang.IndexOutOfBoundsException("Given array doesn't have enough space to receive the result, advances.length: " + fArr.length + " advanceIndex: " + i6 + " needed space: " + (i5 - i));
            }
            if (i2 == i) {
                if (runInfo != null) {
                    runInfo.setClusterCount(0);
                    return 0.0f;
                }
                return 0.0f;
            }
            return nGetRunCharacterAdvance(this.mNativePaint, cArr, i, i2, i3, i4, z, i5, fArr, i6, rectF, runInfo);
        }
        throw new java.lang.IllegalArgumentException("text cannot be null");
    }

    public float getRunCharacterAdvance(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6) {
        return getRunCharacterAdvance(charSequence, i, i2, i3, i4, z, i5, fArr, i6, (android.graphics.RectF) null, (android.graphics.Paint.RunInfo) null);
    }

    public float getRunCharacterAdvance(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, android.graphics.RectF rectF, android.graphics.Paint.RunInfo runInfo) {
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        if (i3 < 0 || i4 > charSequence.length()) {
            throw new java.lang.IndexOutOfBoundsException("Invalid Context Range: " + i3 + ", " + i4 + " must be in 0, " + charSequence.length());
        }
        if (i < i3 || i4 < i2) {
            throw new java.lang.IndexOutOfBoundsException("Invalid start/end range: " + i + ", " + i2 + " must be in " + i3 + ", " + i4);
        }
        if (i5 < i || i2 < i5) {
            throw new java.lang.IndexOutOfBoundsException("Invalid offset position: " + i5 + " must be in " + i + ", " + i2);
        }
        if (fArr != null && fArr.length < (i6 - i) + i2) {
            throw new java.lang.IndexOutOfBoundsException("Given array doesn't have enough space to receive the result, advances.length: " + fArr.length + " advanceIndex: " + i6 + " needed space: " + (i5 - i));
        }
        if (i2 == i) {
            return 0.0f;
        }
        int i7 = i4 - i3;
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i7);
        android.text.TextUtils.getChars(charSequence, i3, i4, obtain, 0);
        float runCharacterAdvance = getRunCharacterAdvance(obtain, i - i3, i2 - i3, 0, i7, z, i5 - i3, fArr, i6, rectF, runInfo);
        android.graphics.TemporaryBuffer.recycle(obtain);
        return runCharacterAdvance;
    }

    public int getOffsetForAdvance(char[] cArr, int i, int i2, int i3, int i4, boolean z, float f) {
        if (cArr != null) {
            if ((i3 | i | i2 | i4 | (i - i3) | (i2 - i) | (i4 - i2) | (cArr.length - i4)) < 0) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return nGetOffsetForAdvance(this.mNativePaint, cArr, i, i2, i3, i4, z, f);
        }
        throw new java.lang.IllegalArgumentException("text cannot be null");
    }

    public int getOffsetForAdvance(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, boolean z, float f) {
        if (charSequence == null) {
            throw new java.lang.IllegalArgumentException("text cannot be null");
        }
        int i5 = i - i3;
        if ((i3 | i | i2 | i4 | i5 | (i2 - i) | (i4 - i2) | (charSequence.length() - i4)) < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int i6 = i4 - i3;
        char[] obtain = android.graphics.TemporaryBuffer.obtain(i6);
        android.text.TextUtils.getChars(charSequence, i3, i4, obtain, 0);
        int offsetForAdvance = getOffsetForAdvance(obtain, i5, i2 - i3, 0, i6, z, f) + i3;
        android.graphics.TemporaryBuffer.recycle(obtain);
        return offsetForAdvance;
    }

    public boolean equalsForTextMeasurement(android.graphics.Paint paint) {
        return nEqualsForTextMeasurement(this.mNativePaint, paint.mNativePaint);
    }
}
