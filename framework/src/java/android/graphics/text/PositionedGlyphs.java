package android.graphics.text;

/* loaded from: classes.dex */
public final class PositionedGlyphs {
    public static final float NO_OVERRIDE = Float.MIN_VALUE;
    private static final libcore.util.NativeAllocationRegistry REGISTRY = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.Typeface.class.getClassLoader(), nReleaseFunc());
    private final java.util.ArrayList<android.graphics.fonts.Font> mFonts;
    private final long mLayoutPtr;
    private final float mXOffset;
    private final float mYOffset;

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetAscent(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetDescent(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nGetFakeBold(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nGetFakeItalic(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetFont(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetGlyphCount(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetGlyphId(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetItalicOverride(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetTotalAdvance(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetWeightOverride(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetX(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetY(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nReleaseFunc();

    public float getAdvance() {
        return nGetTotalAdvance(this.mLayoutPtr);
    }

    public float getAscent() {
        return nGetAscent(this.mLayoutPtr);
    }

    public float getDescent() {
        return nGetDescent(this.mLayoutPtr);
    }

    public float getOffsetX() {
        return this.mXOffset;
    }

    public float getOffsetY() {
        return this.mYOffset;
    }

    public int glyphCount() {
        return nGetGlyphCount(this.mLayoutPtr);
    }

    public android.graphics.fonts.Font getFont(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        return this.mFonts.get(i);
    }

    public int getGlyphId(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        return nGetGlyphId(this.mLayoutPtr, i);
    }

    public float getGlyphX(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        return nGetX(this.mLayoutPtr, i) + this.mXOffset;
    }

    public float getGlyphY(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        return nGetY(this.mLayoutPtr, i) + this.mYOffset;
    }

    public boolean getFakeBold(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        return nGetFakeBold(this.mLayoutPtr, i);
    }

    public boolean getFakeItalic(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        return nGetFakeItalic(this.mLayoutPtr, i);
    }

    public float getWeightOverride(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        float nGetWeightOverride = nGetWeightOverride(this.mLayoutPtr, i);
        if (nGetWeightOverride == -1.0f) {
            return Float.MIN_VALUE;
        }
        return nGetWeightOverride;
    }

    public float getItalicOverride(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, glyphCount() - 1, android.graphics.FontListParser.ATTR_INDEX);
        float nGetItalicOverride = nGetItalicOverride(this.mLayoutPtr, i);
        if (nGetItalicOverride == -1.0f) {
            return Float.MIN_VALUE;
        }
        return nGetItalicOverride;
    }

    public PositionedGlyphs(long j, float f, float f2) {
        this.mLayoutPtr = j;
        int nGetGlyphCount = nGetGlyphCount(j);
        this.mFonts = new java.util.ArrayList<>(nGetGlyphCount);
        this.mXOffset = f;
        this.mYOffset = f2;
        long j2 = 0;
        android.graphics.fonts.Font font = null;
        for (int i = 0; i < nGetGlyphCount; i++) {
            long nGetFont = nGetFont(j, i);
            if (j2 != nGetFont) {
                font = new android.graphics.fonts.Font(nGetFont);
                j2 = nGetFont;
            }
            this.mFonts.add(font);
        }
        REGISTRY.registerNativeAllocation(this, j);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.graphics.text.PositionedGlyphs)) {
            return false;
        }
        android.graphics.text.PositionedGlyphs positionedGlyphs = (android.graphics.text.PositionedGlyphs) obj;
        if (this.mXOffset != positionedGlyphs.mXOffset || this.mYOffset != positionedGlyphs.mYOffset || glyphCount() != positionedGlyphs.glyphCount()) {
            return false;
        }
        for (int i = 0; i < glyphCount(); i++) {
            if (getGlyphId(i) != positionedGlyphs.getGlyphId(i) || getGlyphX(i) != positionedGlyphs.getGlyphX(i) || getGlyphY(i) != positionedGlyphs.getGlyphY(i) || !getFont(i).equals(positionedGlyphs.getFont(i))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hash = java.util.Objects.hash(java.lang.Float.valueOf(this.mXOffset), java.lang.Float.valueOf(this.mYOffset));
        for (int i = 0; i < glyphCount(); i++) {
            hash = java.util.Objects.hash(java.lang.Integer.valueOf(hash), java.lang.Integer.valueOf(getGlyphId(i)), java.lang.Float.valueOf(getGlyphX(i)), java.lang.Float.valueOf(getGlyphY(i)), getFont(i));
        }
        return hash;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        for (int i = 0; i < glyphCount(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append("[ ID = " + getGlyphId(i) + ", pos = (" + getGlyphX(i) + "," + getGlyphY(i) + ") font = " + getFont(i) + " ]");
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return "PositionedGlyphs{glyphs = " + sb.toString() + ", mXOffset=" + this.mXOffset + ", mYOffset=" + this.mYOffset + '}';
    }
}
