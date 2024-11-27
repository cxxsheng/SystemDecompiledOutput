package android.text;

/* loaded from: classes3.dex */
public interface GraphicsOperations extends java.lang.CharSequence {
    void drawText(android.graphics.BaseCanvas baseCanvas, int i, int i2, float f, float f2, android.graphics.Paint paint);

    void drawTextRun(android.graphics.BaseCanvas baseCanvas, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint);

    float getTextRunAdvances(int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5, android.graphics.Paint paint);

    int getTextRunCursor(int i, int i2, boolean z, int i3, int i4, android.graphics.Paint paint);

    int getTextWidths(int i, int i2, float[] fArr, android.graphics.Paint paint);

    float measureText(int i, int i2, android.graphics.Paint paint);
}
