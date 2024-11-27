package android.graphics.text;

/* loaded from: classes.dex */
public class MeasuredText {
    private static final java.lang.String TAG = "MeasuredText";
    private final int mBottom;
    private final char[] mChars;
    private final boolean mComputeBounds;
    private final boolean mComputeHyphenation;
    private final boolean mComputeLayout;
    private final long mNativePtr;
    private final int mTop;

    private static native void nGetBounds(long j, char[] cArr, int i, int i2, android.graphics.Rect rect);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetCharWidthAt(long j, int i);

    private static native long nGetExtent(long j, char[] cArr, int i, int i2);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetMemoryUsage(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native long nGetReleaseFunc();

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetWidth(long j, int i, int i2);

    private MeasuredText(long j, char[] cArr, boolean z, boolean z2, boolean z3, int i, int i2) {
        this.mNativePtr = j;
        this.mChars = cArr;
        this.mComputeHyphenation = z;
        this.mComputeLayout = z2;
        this.mComputeBounds = z3;
        this.mTop = i;
        this.mBottom = i2;
    }

    public char[] getChars() {
        return this.mChars;
    }

    public float getWidth(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= this.mChars.length, "start(%d) must be 0 <= start <= %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.mChars.length));
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 && i2 <= this.mChars.length, "end(%d) must be 0 <= end <= %d", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(this.mChars.length));
        com.android.internal.util.Preconditions.checkArgument(i <= i2, "start(%d) is larger than end(%d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        return nGetWidth(this.mNativePtr, i, i2);
    }

    public int getMemoryUsage() {
        return nGetMemoryUsage(this.mNativePtr);
    }

    public void getBounds(int i, int i2, android.graphics.Rect rect) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= this.mChars.length, "start(%d) must be 0 <= start <= %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.mChars.length));
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 && i2 <= this.mChars.length, "end(%d) must be 0 <= end <= %d", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(this.mChars.length));
        com.android.internal.util.Preconditions.checkArgument(i <= i2, "start(%d) is larger than end(%d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        com.android.internal.util.Preconditions.checkNotNull(rect);
        nGetBounds(this.mNativePtr, this.mChars, i, i2, rect);
    }

    public void getFontMetricsInt(int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= this.mChars.length, "start(%d) must be 0 <= start <= %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.mChars.length));
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 && i2 <= this.mChars.length, "end(%d) must be 0 <= end <= %d", java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(this.mChars.length));
        com.android.internal.util.Preconditions.checkArgument(i <= i2, "start(%d) is larger than end(%d)", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        java.util.Objects.requireNonNull(fontMetricsInt);
        long nGetExtent = nGetExtent(this.mNativePtr, this.mChars, i, i2);
        fontMetricsInt.ascent = (int) (nGetExtent >> 32);
        fontMetricsInt.descent = (int) (nGetExtent & (-1));
        fontMetricsInt.top = java.lang.Math.min(fontMetricsInt.ascent, this.mTop);
        fontMetricsInt.bottom = java.lang.Math.max(fontMetricsInt.descent, this.mBottom);
    }

    public float getCharWidthAt(int i) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i < this.mChars.length, "offset(%d) is larger than text length %d" + i, java.lang.Integer.valueOf(this.mChars.length));
        return nGetCharWidthAt(this.mNativePtr, i);
    }

    public long getNativePtr() {
        return this.mNativePtr;
    }

    public static final class Builder {
        public static final int HYPHENATION_MODE_FAST = 2;
        public static final int HYPHENATION_MODE_NONE = 0;
        public static final int HYPHENATION_MODE_NORMAL = 1;
        private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.text.MeasuredText.class.getClassLoader(), android.graphics.text.MeasuredText.nGetReleaseFunc());
        private int mBottom;
        private android.graphics.Paint.FontMetricsInt mCachedMetrics;
        private boolean mComputeBounds;
        private boolean mComputeHyphenation;
        private boolean mComputeLayout;
        private int mCurrentOffset;
        private boolean mFastHyphenation;
        private android.graphics.text.MeasuredText mHintMt;
        private long mNativePtr;
        private final char[] mText;
        private int mTop;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface HyphenationMode {
        }

        private static native void nAddReplacementRun(long j, long j2, int i, int i2, float f);

        private static native void nAddStyleRun(long j, long j2, int i, int i2, boolean z, int i3, int i4, boolean z2);

        private static native long nBuildMeasuredText(long j, long j2, char[] cArr, boolean z, boolean z2, boolean z3, boolean z4);

        private static native void nFreeBuilder(long j);

        private static native long nInitBuilder();

        public Builder(char[] cArr) {
            this.mComputeHyphenation = false;
            this.mComputeLayout = true;
            this.mComputeBounds = true;
            this.mFastHyphenation = false;
            this.mCurrentOffset = 0;
            this.mHintMt = null;
            this.mTop = 0;
            this.mBottom = 0;
            this.mCachedMetrics = new android.graphics.Paint.FontMetricsInt();
            com.android.internal.util.Preconditions.checkNotNull(cArr);
            this.mText = cArr;
            this.mNativePtr = nInitBuilder();
        }

        public Builder(android.graphics.text.MeasuredText measuredText) {
            this.mComputeHyphenation = false;
            this.mComputeLayout = true;
            this.mComputeBounds = true;
            this.mFastHyphenation = false;
            this.mCurrentOffset = 0;
            this.mHintMt = null;
            this.mTop = 0;
            this.mBottom = 0;
            this.mCachedMetrics = new android.graphics.Paint.FontMetricsInt();
            com.android.internal.util.Preconditions.checkNotNull(measuredText);
            this.mText = measuredText.mChars;
            this.mNativePtr = nInitBuilder();
            if (!measuredText.mComputeLayout) {
                throw new java.lang.IllegalArgumentException("The input MeasuredText must not be created with setComputeLayout(false).");
            }
            this.mComputeHyphenation = measuredText.mComputeHyphenation;
            this.mComputeLayout = measuredText.mComputeLayout;
            this.mHintMt = measuredText;
        }

        public android.graphics.text.MeasuredText.Builder appendStyleRun(android.graphics.Paint paint, int i, boolean z) {
            return appendStyleRun(paint, null, i, z);
        }

        public android.graphics.text.MeasuredText.Builder appendStyleRun(android.graphics.Paint paint, android.graphics.text.LineBreakConfig lineBreakConfig, int i, boolean z) {
            com.android.internal.util.Preconditions.checkNotNull(paint);
            com.android.internal.util.Preconditions.checkArgument(i > 0, "length can not be negative");
            int i2 = this.mCurrentOffset + i;
            com.android.internal.util.Preconditions.checkArgument(i2 <= this.mText.length, "Style exceeds the text length");
            nAddStyleRun(this.mNativePtr, paint.getNativeInstance(), android.graphics.text.LineBreakConfig.getResolvedLineBreakStyle(lineBreakConfig), android.graphics.text.LineBreakConfig.getResolvedLineBreakWordStyle(lineBreakConfig), android.graphics.text.LineBreakConfig.getResolvedHyphenation(lineBreakConfig) == 1, this.mCurrentOffset, i2, z);
            this.mCurrentOffset = i2;
            paint.getFontMetricsInt(this.mCachedMetrics);
            this.mTop = java.lang.Math.min(this.mTop, this.mCachedMetrics.top);
            this.mBottom = java.lang.Math.max(this.mBottom, this.mCachedMetrics.bottom);
            return this;
        }

        public android.graphics.text.MeasuredText.Builder appendReplacementRun(android.graphics.Paint paint, int i, float f) {
            com.android.internal.util.Preconditions.checkArgument(i > 0, "length can not be negative");
            int i2 = this.mCurrentOffset + i;
            com.android.internal.util.Preconditions.checkArgument(i2 <= this.mText.length, "Replacement exceeds the text length");
            nAddReplacementRun(this.mNativePtr, paint.getNativeInstance(), this.mCurrentOffset, i2, f);
            this.mCurrentOffset = i2;
            return this;
        }

        @java.lang.Deprecated
        public android.graphics.text.MeasuredText.Builder setComputeHyphenation(boolean z) {
            setComputeHyphenation(z ? 1 : 0);
            return this;
        }

        public android.graphics.text.MeasuredText.Builder setComputeHyphenation(int i) {
            switch (i) {
                case 0:
                    this.mComputeHyphenation = false;
                    this.mFastHyphenation = false;
                    return this;
                case 1:
                    this.mComputeHyphenation = true;
                    this.mFastHyphenation = false;
                    return this;
                case 2:
                    this.mComputeHyphenation = true;
                    this.mFastHyphenation = true;
                    return this;
                default:
                    android.util.Log.e(android.graphics.text.MeasuredText.TAG, "Unknown hyphenation mode: " + i);
                    this.mComputeHyphenation = false;
                    this.mFastHyphenation = false;
                    return this;
            }
        }

        public android.graphics.text.MeasuredText.Builder setComputeLayout(boolean z) {
            this.mComputeLayout = z;
            return this;
        }

        public android.graphics.text.MeasuredText.Builder setComputeBounds(boolean z) {
            this.mComputeBounds = z;
            return this;
        }

        public android.graphics.text.MeasuredText build() {
            long nativePtr;
            ensureNativePtrNoReuse();
            if (this.mCurrentOffset != this.mText.length) {
                throw new java.lang.IllegalStateException("Style info has not been provided for all text.");
            }
            if (this.mHintMt != null && this.mHintMt.mComputeHyphenation != this.mComputeHyphenation) {
                throw new java.lang.IllegalArgumentException("The hyphenation configuration is different from given hint MeasuredText");
            }
            try {
                if (this.mHintMt != null) {
                    nativePtr = this.mHintMt.getNativePtr();
                } else {
                    nativePtr = 0;
                }
                long nBuildMeasuredText = nBuildMeasuredText(this.mNativePtr, nativePtr, this.mText, this.mComputeHyphenation, this.mComputeLayout, this.mComputeBounds, this.mFastHyphenation);
                android.graphics.text.MeasuredText measuredText = new android.graphics.text.MeasuredText(nBuildMeasuredText, this.mText, this.mComputeHyphenation, this.mComputeLayout, this.mComputeBounds, this.mTop, this.mBottom);
                sRegistry.registerNativeAllocation(measuredText, nBuildMeasuredText);
                return measuredText;
            } finally {
                nFreeBuilder(this.mNativePtr);
                this.mNativePtr = 0L;
            }
        }

        private void ensureNativePtrNoReuse() {
            if (this.mNativePtr == 0) {
                throw new java.lang.IllegalStateException("Builder can not be reused.");
            }
        }
    }
}
