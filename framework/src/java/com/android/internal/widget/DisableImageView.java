package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class DisableImageView extends android.widget.ImageView {
    public DisableImageView(android.content.Context context) {
        this(context, null, 0, 0);
    }

    public DisableImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public DisableImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DisableImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.graphics.ColorMatrix colorMatrix = new android.graphics.ColorMatrix();
        float[] array = colorMatrix.getArray();
        array[0] = 0.5f;
        array[6] = 0.5f;
        array[12] = 0.5f;
        float f = (int) 127.5f;
        array[4] = f;
        array[9] = f;
        array[14] = f;
        android.graphics.ColorMatrix colorMatrix2 = new android.graphics.ColorMatrix();
        colorMatrix2.setSaturation(0.0f);
        colorMatrix2.preConcat(colorMatrix);
        setColorFilter(new android.graphics.ColorMatrixColorFilter(colorMatrix2));
    }
}
