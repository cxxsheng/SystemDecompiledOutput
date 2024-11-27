package android.graphics.text;

/* loaded from: classes.dex */
public class LineBreaker {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int JUSTIFICATION_MODE_INTER_CHARACTER = 2;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.text.LineBreaker.class.getClassLoader(), nGetReleaseFunc());
    private final long mNativePtr;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BreakStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HyphenationFrequency {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface JustificationMode {
    }

    private static native long nComputeLineBreaks(long j, char[] cArr, long j2, int i, float f, int i2, float f2, float[] fArr, float f3, int i3);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native float nGetLineAscent(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native int nGetLineBreakOffset(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native int nGetLineCount(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native float nGetLineDescent(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native int nGetLineFlag(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native float nGetLineWidth(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetReleaseFunc();

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native long nGetReleaseResultFunc();

    @dalvik.annotation.optimization.FastNative
    private static native long nInit(int i, int i2, boolean z, int[] iArr, boolean z2);

    public static final class Builder {
        private int mBreakStrategy = 0;
        private int mHyphenationFrequency = 0;
        private int mJustificationMode = 0;
        private int[] mIndents = null;
        private boolean mUseBoundsForWidth = false;

        public android.graphics.text.LineBreaker.Builder setBreakStrategy(int i) {
            this.mBreakStrategy = i;
            return this;
        }

        public android.graphics.text.LineBreaker.Builder setHyphenationFrequency(int i) {
            this.mHyphenationFrequency = i;
            return this;
        }

        public android.graphics.text.LineBreaker.Builder setJustificationMode(int i) {
            this.mJustificationMode = i;
            return this;
        }

        public android.graphics.text.LineBreaker.Builder setIndents(int[] iArr) {
            this.mIndents = iArr;
            return this;
        }

        public android.graphics.text.LineBreaker.Builder setUseBoundsForWidth(boolean z) {
            this.mUseBoundsForWidth = z;
            return this;
        }

        public android.graphics.text.LineBreaker build() {
            return new android.graphics.text.LineBreaker(this.mBreakStrategy, this.mHyphenationFrequency, this.mJustificationMode, this.mIndents, this.mUseBoundsForWidth);
        }
    }

    public static class ParagraphConstraints {
        private float mWidth = 0.0f;
        private float mFirstWidth = 0.0f;
        private int mFirstWidthLineCount = 0;
        private float[] mVariableTabStops = null;
        private float mDefaultTabStop = 0.0f;

        public void setWidth(float f) {
            this.mWidth = f;
        }

        public void setIndent(float f, int i) {
            this.mFirstWidth = f;
            this.mFirstWidthLineCount = i;
        }

        public void setTabStops(float[] fArr, float f) {
            this.mVariableTabStops = fArr;
            this.mDefaultTabStop = f;
        }

        public float getWidth() {
            return this.mWidth;
        }

        public float getFirstWidth() {
            return this.mFirstWidth;
        }

        public int getFirstWidthLineCount() {
            return this.mFirstWidthLineCount;
        }

        public float[] getTabStops() {
            return this.mVariableTabStops;
        }

        public float getDefaultTabStop() {
            return this.mDefaultTabStop;
        }
    }

    public static class Result {
        private static final int END_HYPHEN_MASK = 7;
        private static final int HYPHEN_MASK = 255;
        private static final int START_HYPHEN_BITS_SHIFT = 3;
        private static final int START_HYPHEN_MASK = 24;
        private static final int TAB_MASK = 536870912;
        private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.text.LineBreaker.Result.class.getClassLoader(), android.graphics.text.LineBreaker.nGetReleaseResultFunc());
        private final long mPtr;

        private Result(long j) {
            this.mPtr = j;
            sRegistry.registerNativeAllocation(this, this.mPtr);
        }

        public int getLineCount() {
            return android.graphics.text.LineBreaker.nGetLineCount(this.mPtr);
        }

        public int getLineBreakOffset(int i) {
            return android.graphics.text.LineBreaker.nGetLineBreakOffset(this.mPtr, i);
        }

        public float getLineWidth(int i) {
            return android.graphics.text.LineBreaker.nGetLineWidth(this.mPtr, i);
        }

        public float getLineAscent(int i) {
            return android.graphics.text.LineBreaker.nGetLineAscent(this.mPtr, i);
        }

        public float getLineDescent(int i) {
            return android.graphics.text.LineBreaker.nGetLineDescent(this.mPtr, i);
        }

        public boolean hasLineTab(int i) {
            return (android.graphics.text.LineBreaker.nGetLineFlag(this.mPtr, i) & 536870912) != 0;
        }

        public int getStartLineHyphenEdit(int i) {
            return (android.graphics.text.LineBreaker.nGetLineFlag(this.mPtr, i) & 24) >> 3;
        }

        public int getEndLineHyphenEdit(int i) {
            return android.graphics.text.LineBreaker.nGetLineFlag(this.mPtr, i) & 7;
        }
    }

    private LineBreaker(int i, int i2, int i3, int[] iArr, boolean z) {
        this.mNativePtr = nInit(i, i2, i3 == 1, iArr, z);
        sRegistry.registerNativeAllocation(this, this.mNativePtr);
    }

    public android.graphics.text.LineBreaker.Result computeLineBreaks(android.graphics.text.MeasuredText measuredText, android.graphics.text.LineBreaker.ParagraphConstraints paragraphConstraints, int i) {
        return new android.graphics.text.LineBreaker.Result(nComputeLineBreaks(this.mNativePtr, measuredText.getChars(), measuredText.getNativePtr(), measuredText.getChars().length, paragraphConstraints.mFirstWidth, paragraphConstraints.mFirstWidthLineCount, paragraphConstraints.mWidth, paragraphConstraints.mVariableTabStops, paragraphConstraints.mDefaultTabStop, i));
    }
}
