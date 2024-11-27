package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public final class QuantizerMap implements com.android.internal.graphics.palette.Quantizer {
    private java.util.HashMap<java.lang.Integer, java.lang.Integer> mColorToCount;
    private com.android.internal.graphics.palette.Palette mPalette;

    @Override // com.android.internal.graphics.palette.Quantizer
    public void quantize(int[] iArr, int i) {
        java.util.HashMap<java.lang.Integer, java.lang.Integer> hashMap = new java.util.HashMap<>();
        for (int i2 : iArr) {
            hashMap.merge(java.lang.Integer.valueOf(i2), 1, new android.app.backup.BackupRestoreEventLogger$$ExternalSyntheticLambda0());
        }
        this.mColorToCount = hashMap;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.Integer> entry : hashMap.entrySet()) {
            arrayList.add(new com.android.internal.graphics.palette.Palette.Swatch(entry.getKey().intValue(), entry.getValue().intValue()));
        }
        this.mPalette = com.android.internal.graphics.palette.Palette.from(arrayList);
    }

    @Override // com.android.internal.graphics.palette.Quantizer
    public java.util.List<com.android.internal.graphics.palette.Palette.Swatch> getQuantizedColors() {
        return this.mPalette.getSwatches();
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> getColorToCount() {
        return this.mColorToCount;
    }
}
