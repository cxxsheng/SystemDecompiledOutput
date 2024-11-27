package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public class LABPointProvider implements com.android.internal.graphics.palette.PointProvider {
    final android.graphics.ColorSpace.Connector mRgbToLab = android.graphics.ColorSpace.connect(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB), android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.CIE_LAB));
    final android.graphics.ColorSpace.Connector mLabToRgb = android.graphics.ColorSpace.connect(android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.CIE_LAB), android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));

    @Override // com.android.internal.graphics.palette.PointProvider
    public float[] fromInt(int i) {
        return this.mRgbToLab.transform(android.graphics.Color.red(i) / 255.0f, android.graphics.Color.green(i) / 255.0f, android.graphics.Color.blue(i) / 255.0f);
    }

    @Override // com.android.internal.graphics.palette.PointProvider
    public int toInt(float[] fArr) {
        float[] transform = this.mLabToRgb.transform(fArr);
        return android.graphics.Color.rgb(transform[0], transform[1], transform[2]);
    }

    @Override // com.android.internal.graphics.palette.PointProvider
    public float distance(float[] fArr, float[] fArr2) {
        double d = fArr[0] - fArr2[0];
        double d2 = fArr[1] - fArr2[1];
        double d3 = fArr[2] - fArr2[2];
        return (float) ((d * d) + (d2 * d2) + (d3 * d3));
    }
}
