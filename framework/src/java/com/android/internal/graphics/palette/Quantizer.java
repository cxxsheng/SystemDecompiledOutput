package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public interface Quantizer {
    java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getQuantizedColors();

    void quantize(int[] iArr, int i);
}
