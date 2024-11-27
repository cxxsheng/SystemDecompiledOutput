package com.android.server.display.color;

/* loaded from: classes.dex */
final class GlobalSaturationTintController extends com.android.server.display.color.TintController {
    private final float[] mMatrixGlobalSaturation = new float[16];

    GlobalSaturationTintController() {
    }

    @Override // com.android.server.display.color.TintController
    public void setUp(android.content.Context context, boolean z) {
    }

    @Override // com.android.server.display.color.TintController
    public float[] getMatrix() {
        return java.util.Arrays.copyOf(this.mMatrixGlobalSaturation, this.mMatrixGlobalSaturation.length);
    }

    @Override // com.android.server.display.color.TintController
    public void setMatrix(int i) {
        if (i < 0) {
            i = 0;
        } else if (i > 100) {
            i = 100;
        }
        android.util.Slog.d("ColorDisplayService", "Setting saturation level: " + i);
        if (i == 100) {
            setActivated(false);
            android.opengl.Matrix.setIdentityM(this.mMatrixGlobalSaturation, 0);
            return;
        }
        setActivated(true);
        float f = i * 0.01f;
        float f2 = 1.0f - f;
        float[] fArr = {0.231f * f2, 0.715f * f2, f2 * 0.072f};
        this.mMatrixGlobalSaturation[0] = fArr[0] + f;
        this.mMatrixGlobalSaturation[1] = fArr[0];
        this.mMatrixGlobalSaturation[2] = fArr[0];
        this.mMatrixGlobalSaturation[4] = fArr[1];
        this.mMatrixGlobalSaturation[5] = fArr[1] + f;
        this.mMatrixGlobalSaturation[6] = fArr[1];
        this.mMatrixGlobalSaturation[8] = fArr[2];
        this.mMatrixGlobalSaturation[9] = fArr[2];
        this.mMatrixGlobalSaturation[10] = fArr[2] + f;
    }

    @Override // com.android.server.display.color.TintController
    public int getLevel() {
        return 150;
    }

    @Override // com.android.server.display.color.TintController
    public boolean isAvailable(android.content.Context context) {
        return android.hardware.display.ColorDisplayManager.isColorTransformAccelerated(context);
    }
}
