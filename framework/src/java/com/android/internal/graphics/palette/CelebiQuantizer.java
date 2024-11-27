package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public class CelebiQuantizer implements com.android.internal.graphics.palette.Quantizer {
    private java.util.List<com.android.internal.graphics.palette.Palette.Swatch> mSwatches;

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        com.android.internal.graphics.palette.WuQuantizer wuQuantizer = new com.android.internal.graphics.palette.WuQuantizer();
        wuQuantizer.quantize(iArr, i);
        com.android.internal.graphics.palette.WSMeansQuantizer wSMeansQuantizer = new com.android.internal.graphics.palette.WSMeansQuantizer(wuQuantizer.getColors(), new com.android.internal.graphics.palette.LABPointProvider(), wuQuantizer.inputPixelToCount());
        wSMeansQuantizer.quantize(iArr, i);
        this.mSwatches = wSMeansQuantizer.getQuantizedColors();
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getQuantizedColors() {
        return this.mSwatches;
    }
}
