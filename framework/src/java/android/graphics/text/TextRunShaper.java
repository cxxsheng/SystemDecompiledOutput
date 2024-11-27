package android.graphics.text;

/* loaded from: classes.dex */
public class TextRunShaper {
    private static native long nativeShapeTextRun(java.lang.String str, int i, int i2, int i3, int i4, boolean z, long j);

    private static native long nativeShapeTextRun(char[] cArr, int i, int i2, int i3, int i4, boolean z, long j);

    private TextRunShaper() {
    }

    public static android.graphics.text.PositionedGlyphs shapeTextRun(char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint) {
        com.android.internal.util.Preconditions.checkNotNull(cArr);
        com.android.internal.util.Preconditions.checkNotNull(paint);
        return new android.graphics.text.PositionedGlyphs(nativeShapeTextRun(cArr, i, i2, i3, i4, z, paint.getNativeInstance()), f, f2);
    }

    public static android.graphics.text.PositionedGlyphs shapeTextRun(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint) {
        com.android.internal.util.Preconditions.checkNotNull(charSequence);
        com.android.internal.util.Preconditions.checkNotNull(paint);
        if (charSequence instanceof java.lang.String) {
            return new android.graphics.text.PositionedGlyphs(nativeShapeTextRun((java.lang.String) charSequence, i, i2, i3, i4, z, paint.getNativeInstance()), f, f2);
        }
        char[] cArr = new char[i4];
        android.text.TextUtils.getChars(charSequence, i3, i3 + i4, cArr, 0);
        return new android.graphics.text.PositionedGlyphs(nativeShapeTextRun(cArr, i - i3, i2, 0, i4, z, paint.getNativeInstance()), f, f2);
    }
}
