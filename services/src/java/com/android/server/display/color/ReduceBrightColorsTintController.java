package com.android.server.display.color;

/* loaded from: classes.dex */
public class ReduceBrightColorsTintController extends com.android.server.display.color.TintController {
    private int mStrength;
    private final float[] mMatrix = new float[16];
    private final float[] mCoefficients = new float[3];

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ void cancelAnimator() {
        super.cancelAnimator();
    }

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ void endAnimator() {
        super.endAnimator();
    }

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ android.animation.ValueAnimator getAnimator() {
        return super.getAnimator();
    }

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ long getTransitionDurationMilliseconds() {
        return super.getTransitionDurationMilliseconds();
    }

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ long getTransitionDurationMilliseconds(boolean z) {
        return super.getTransitionDurationMilliseconds(z);
    }

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ boolean isActivated() {
        return super.isActivated();
    }

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ boolean isActivatedStateNotSet() {
        return super.isActivatedStateNotSet();
    }

    @Override // com.android.server.display.color.TintController
    public /* bridge */ /* synthetic */ void setAnimator(android.animation.ValueAnimator valueAnimator) {
        super.setAnimator(valueAnimator);
    }

    @Override // com.android.server.display.color.TintController
    public void setUp(android.content.Context context, boolean z) {
        java.lang.String[] stringArray = context.getResources().getStringArray(z ? android.R.array.config_priorityOnlyDndExemptPackages : android.R.array.config_protectedNetworks);
        for (int i = 0; i < 3 && i < stringArray.length; i++) {
            this.mCoefficients[i] = java.lang.Float.parseFloat(stringArray[i]);
        }
    }

    @Override // com.android.server.display.color.TintController
    public float[] getMatrix() {
        return isActivated() ? java.util.Arrays.copyOf(this.mMatrix, this.mMatrix.length) : com.android.server.display.color.ColorDisplayService.MATRIX_IDENTITY;
    }

    @Override // com.android.server.display.color.TintController
    public void setMatrix(int i) {
        if (i < 0) {
            i = 0;
        } else if (i > 100) {
            i = 100;
        }
        android.util.Slog.d("ColorDisplayService", "Setting bright color reduction level: " + i);
        this.mStrength = i;
        android.opengl.Matrix.setIdentityM(this.mMatrix, 0);
        float computeComponentValue = computeComponentValue(i);
        this.mMatrix[0] = computeComponentValue;
        this.mMatrix[5] = computeComponentValue;
        this.mMatrix[10] = computeComponentValue;
    }

    private float clamp(float f) {
        if (f > 1.0f) {
            return 1.0f;
        }
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        return f;
    }

    @Override // com.android.server.display.color.TintController
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("    mStrength = " + this.mStrength);
    }

    @Override // com.android.server.display.color.TintController
    public int getLevel() {
        return 250;
    }

    @Override // com.android.server.display.color.TintController
    public boolean isAvailable(android.content.Context context) {
        return android.hardware.display.ColorDisplayManager.isColorTransformAccelerated(context);
    }

    @Override // com.android.server.display.color.TintController
    public void setActivated(java.lang.Boolean bool) {
        super.setActivated(bool);
        android.util.Slog.i("ColorDisplayService", (bool == null || !bool.booleanValue()) ? "Turning off reduce bright colors" : "Turning on reduce bright colors");
    }

    public int getStrength() {
        return this.mStrength;
    }

    public float getOffsetFactor() {
        return this.mCoefficients[0] + this.mCoefficients[1] + this.mCoefficients[2];
    }

    public float getAdjustedBrightness(float f) {
        return computeComponentValue(this.mStrength) * f;
    }

    private float computeComponentValue(int i) {
        float f = i / 100.0f;
        return clamp((f * f * this.mCoefficients[0]) + (f * this.mCoefficients[1]) + this.mCoefficients[2]);
    }
}
