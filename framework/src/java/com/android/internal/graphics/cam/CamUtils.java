package com.android.internal.graphics.cam;

/* loaded from: classes4.dex */
public final class CamUtils {
    static final float[][] XYZ_TO_CAM16RGB = {new float[]{0.401288f, 0.650173f, -0.051461f}, new float[]{-0.250268f, 1.204414f, 0.045854f}, new float[]{-0.002079f, 0.048952f, 0.953127f}};
    static final float[][] CAM16RGB_TO_XYZ = {new float[]{1.8620678f, -1.0112547f, 0.14918678f}, new float[]{0.38752654f, 0.62144744f, -0.00897398f}, new float[]{-0.0158415f, -0.03412294f, 1.0499644f}};
    static final float[] WHITE_POINT_D65 = {95.047f, 100.0f, 108.883f};
    static final double[][] SRGB_TO_XYZ = {new double[]{0.41233895d, 0.35762064d, 0.18051042d}, new double[]{0.2126d, 0.7152d, 0.0722d}, new double[]{0.01932141d, 0.11916382d, 0.95034478d}};
    static final double[][] XYZ_TO_SRGB = {new double[]{3.2413774792388685d, -1.5376652402851851d, -0.49885366846268053d}, new double[]{-0.9691452513005321d, 1.8758853451067872d, 0.04156585616912061d}, new double[]{0.05562093689691305d, -0.20395524564742123d, 1.0571799111220335d}};

    private CamUtils() {
    }

    public static int signum(double d) {
        if (d < 0.0d) {
            return -1;
        }
        if (d == 0.0d) {
            return 0;
        }
        return 1;
    }

    public static int argbFromLstar(double d) {
        double d2 = (d + 16.0d) / 116.0d;
        double d3 = (d > 8.0d ? 1 : (d == 8.0d ? 0 : -1)) > 0 ? d2 * d2 * d2 : d / 903.2962962962963d;
        double d4 = d2 * d2 * d2;
        boolean z = d4 > 0.008856451679035631d;
        double d5 = z ? d4 : d / 903.2962962962963d;
        if (!z) {
            d4 = d / 903.2962962962963d;
        }
        float[] fArr = WHITE_POINT_D65;
        return argbFromXyz(d5 * fArr[0], d3 * fArr[1], d4 * fArr[2]);
    }

    public static int argbFromXyz(double d, double d2, double d3) {
        double[][] dArr = XYZ_TO_SRGB;
        return argbFromRgb(delinearized((dArr[0][0] * d) + (dArr[0][1] * d2) + (dArr[0][2] * d3)), delinearized((dArr[1][0] * d) + (dArr[1][1] * d2) + (dArr[1][2] * d3)), delinearized((dArr[2][0] * d) + (dArr[2][1] * d2) + (dArr[2][2] * d3)));
    }

    public static int argbFromLinrgb(double[] dArr) {
        return argbFromRgb(delinearized(dArr[0]), delinearized(dArr[1]), delinearized(dArr[2]));
    }

    public static int argbFromLinrgbComponents(double d, double d2, double d3) {
        return argbFromRgb(delinearized(d), delinearized(d2), delinearized(d3));
    }

    public static int delinearized(double d) {
        double pow;
        double d2 = d / 100.0d;
        if (d2 <= 0.0031308d) {
            pow = d2 * 12.92d;
        } else {
            pow = (java.lang.Math.pow(d2, 0.4166666666666667d) * 1.055d) - 0.055d;
        }
        return clampInt(0, 255, (int) java.lang.Math.round(pow * 255.0d));
    }

    public static int clampInt(int i, int i2, int i3) {
        if (i3 < i) {
            return i;
        }
        if (i3 > i2) {
            return i2;
        }
        return i3;
    }

    public static int argbFromRgb(int i, int i2, int i3) {
        return ((i & 255) << 16) | (-16777216) | ((i2 & 255) << 8) | (i3 & 255);
    }

    static int intFromLstar(float f) {
        if (f < 1.0f) {
            return -16777216;
        }
        if (f > 99.0f) {
            return -1;
        }
        float f2 = (f + 16.0f) / 116.0f;
        float f3 = (f > 8.0f ? 1 : (f == 8.0f ? 0 : -1)) > 0 ? f2 * f2 * f2 : f / 903.2963f;
        float f4 = f2 * f2 * f2;
        boolean z = f4 > 0.008856452f;
        float f5 = z ? f4 : ((f2 * 116.0f) - 16.0f) / 903.2963f;
        if (!z) {
            f4 = ((f2 * 116.0f) - 16.0f) / 903.2963f;
        }
        return com.android.internal.graphics.ColorUtils.XYZToColor(f5 * WHITE_POINT_D65[0], f3 * WHITE_POINT_D65[1], f4 * WHITE_POINT_D65[2]);
    }

    public static float lstarFromInt(int i) {
        return lstarFromY(yFromInt(i));
    }

    static float lstarFromY(float f) {
        float f2 = f / 100.0f;
        if (f2 <= 0.008856452f) {
            return f2 * 903.2963f;
        }
        return (((float) java.lang.Math.cbrt(f2)) * 116.0f) - 16.0f;
    }

    static float yFromInt(int i) {
        float linearized = linearized(android.graphics.Color.red(i));
        float linearized2 = linearized(android.graphics.Color.green(i));
        float linearized3 = linearized(android.graphics.Color.blue(i));
        double[][] dArr = SRGB_TO_XYZ;
        return (float) ((linearized * dArr[1][0]) + (linearized2 * dArr[1][1]) + (linearized3 * dArr[1][2]));
    }

    static float[] xyzFromInt(int i) {
        float linearized = linearized(android.graphics.Color.red(i));
        float linearized2 = linearized(android.graphics.Color.green(i));
        float linearized3 = linearized(android.graphics.Color.blue(i));
        double[][] dArr = SRGB_TO_XYZ;
        double d = linearized;
        double d2 = linearized2;
        double d3 = linearized3;
        return new float[]{(float) ((dArr[0][0] * d) + (dArr[0][1] * d2) + (dArr[0][2] * d3)), (float) ((dArr[1][0] * d) + (dArr[1][1] * d2) + (dArr[1][2] * d3)), (float) ((d * dArr[2][0]) + (d2 * dArr[2][1]) + (d3 * dArr[2][2]))};
    }

    public static double yFromLstar(double d) {
        if (d > 8.0d) {
            return java.lang.Math.pow((d + 16.0d) / 116.0d, 3.0d) * 100.0d;
        }
        return (d / 903.2962962962963d) * 100.0d;
    }

    static float linearized(int i) {
        float f = i / 255.0f;
        if (f <= 0.04045f) {
            return (f / 12.92f) * 100.0f;
        }
        return ((float) java.lang.Math.pow((f + 0.055f) / 1.055f, 2.4000000953674316d)) * 100.0f;
    }
}
