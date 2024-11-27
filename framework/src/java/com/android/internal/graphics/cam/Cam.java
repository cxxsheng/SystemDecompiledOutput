package com.android.internal.graphics.cam;

/* loaded from: classes4.dex */
public class Cam {
    private static final float CHROMA_SEARCH_ENDPOINT = 0.4f;
    private static final float DE_MAX = 1.0f;
    private static final float DL_MAX = 0.2f;
    private static final float LIGHTNESS_SEARCH_ENDPOINT = 0.01f;
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;
    private final float mJ;
    private final float mJstar;
    private final float mM;
    private final float mQ;
    private final float mS;

    public float getHue() {
        return this.mHue;
    }

    public float getChroma() {
        return this.mChroma;
    }

    public float getJ() {
        return this.mJ;
    }

    public float getQ() {
        return this.mQ;
    }

    public float getM() {
        return this.mM;
    }

    public float getS() {
        return this.mS;
    }

    public float getJstar() {
        return this.mJstar;
    }

    public float getAstar() {
        return this.mAstar;
    }

    public float getBstar() {
        return this.mBstar;
    }

    Cam(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.mHue = f;
        this.mChroma = f2;
        this.mJ = f3;
        this.mQ = f4;
        this.mM = f5;
        this.mS = f6;
        this.mJstar = f7;
        this.mAstar = f8;
        this.mBstar = f9;
    }

    public static int getInt(float f, float f2, float f3) {
        return getInt(f, f2, f3, com.android.internal.graphics.cam.Frame.DEFAULT);
    }

    public static com.android.internal.graphics.cam.Cam fromInt(int i) {
        return fromIntInFrame(i, com.android.internal.graphics.cam.Frame.DEFAULT);
    }

    public static com.android.internal.graphics.cam.Cam fromIntInFrame(int i, com.android.internal.graphics.cam.Frame frame) {
        float f;
        float[] xyzFromInt = com.android.internal.graphics.cam.CamUtils.xyzFromInt(i);
        float[][] fArr = com.android.internal.graphics.cam.CamUtils.XYZ_TO_CAM16RGB;
        float f2 = (xyzFromInt[0] * fArr[0][0]) + (xyzFromInt[1] * fArr[0][1]) + (xyzFromInt[2] * fArr[0][2]);
        float f3 = (xyzFromInt[0] * fArr[1][0]) + (xyzFromInt[1] * fArr[1][1]) + (xyzFromInt[2] * fArr[1][2]);
        float f4 = (xyzFromInt[0] * fArr[2][0]) + (xyzFromInt[1] * fArr[2][1]) + (xyzFromInt[2] * fArr[2][2]);
        float f5 = frame.getRgbD()[0] * f2;
        float f6 = frame.getRgbD()[1] * f3;
        float f7 = frame.getRgbD()[2] * f4;
        float pow = (float) java.lang.Math.pow((frame.getFl() * java.lang.Math.abs(f5)) / 100.0d, 0.42d);
        float pow2 = (float) java.lang.Math.pow((frame.getFl() * java.lang.Math.abs(f6)) / 100.0d, 0.42d);
        float pow3 = (float) java.lang.Math.pow((frame.getFl() * java.lang.Math.abs(f7)) / 100.0d, 0.42d);
        float signum = ((java.lang.Math.signum(f5) * 400.0f) * pow) / (pow + 27.13f);
        float signum2 = ((java.lang.Math.signum(f6) * 400.0f) * pow2) / (pow2 + 27.13f);
        float signum3 = ((java.lang.Math.signum(f7) * 400.0f) * pow3) / (pow3 + 27.13f);
        double d = signum3;
        float f8 = ((float) (((signum * 11.0d) + (signum2 * (-12.0d))) + d)) / 11.0f;
        float f9 = ((float) ((signum + signum2) - (d * 2.0d))) / 9.0f;
        float f10 = signum2 * 20.0f;
        float f11 = (((signum * 20.0f) + f10) + (21.0f * signum3)) / 20.0f;
        float f12 = (((signum * 40.0f) + f10) + signum3) / 20.0f;
        float atan2 = (((float) java.lang.Math.atan2(f9, f8)) * 180.0f) / 3.1415927f;
        if (atan2 < 0.0f) {
            f = atan2 + 360.0f;
        } else {
            if (atan2 >= 360.0f) {
                atan2 -= 360.0f;
            }
            f = atan2;
        }
        float f13 = (3.1415927f * f) / 180.0f;
        float pow4 = ((float) java.lang.Math.pow((f12 * frame.getNbb()) / frame.getAw(), frame.getC() * frame.getZ())) * 100.0f;
        float flRoot = frame.getFlRoot() * (4.0f / frame.getC()) * ((float) java.lang.Math.sqrt(pow4 / 100.0f)) * (frame.getAw() + 4.0f);
        float pow5 = ((float) java.lang.Math.pow((((((((float) (java.lang.Math.cos((((((double) f) < 20.14d ? 360.0f + f : f) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * frame.getNc()) * frame.getNcb()) * ((float) java.lang.Math.sqrt((f8 * f8) + (f9 * f9)))) / (f11 + 0.305f), 0.9d)) * ((float) java.lang.Math.pow(1.64d - java.lang.Math.pow(0.29d, frame.getN()), 0.73d)) * ((float) java.lang.Math.sqrt(pow4 / 100.0d));
        float flRoot2 = pow5 * frame.getFlRoot();
        float sqrt = ((float) java.lang.Math.sqrt((r2 * frame.getC()) / (frame.getAw() + 4.0f))) * 50.0f;
        float f14 = (1.7f * pow4) / ((0.007f * pow4) + 1.0f);
        float log = ((float) java.lang.Math.log((0.0228f * flRoot2) + 1.0f)) * 43.85965f;
        double d2 = f13;
        return new com.android.internal.graphics.cam.Cam(f, pow5, pow4, flRoot, flRoot2, sqrt, f14, log * ((float) java.lang.Math.cos(d2)), log * ((float) java.lang.Math.sin(d2)));
    }

    private static com.android.internal.graphics.cam.Cam fromJch(float f, float f2, float f3) {
        return fromJchInFrame(f, f2, f3, com.android.internal.graphics.cam.Frame.DEFAULT);
    }

    private static com.android.internal.graphics.cam.Cam fromJchInFrame(float f, float f2, float f3, com.android.internal.graphics.cam.Frame frame) {
        float c = (4.0f / frame.getC()) * ((float) java.lang.Math.sqrt(f / 100.0d)) * (frame.getAw() + 4.0f) * frame.getFlRoot();
        float flRoot = f2 * frame.getFlRoot();
        float sqrt = ((float) java.lang.Math.sqrt(((f2 / ((float) java.lang.Math.sqrt(r4))) * frame.getC()) / (frame.getAw() + 4.0f))) * 50.0f;
        float f4 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float log = ((float) java.lang.Math.log((flRoot * 0.0228d) + 1.0d)) * 43.85965f;
        double d = (3.1415927f * f3) / 180.0f;
        return new com.android.internal.graphics.cam.Cam(f3, f2, f, c, flRoot, sqrt, f4, log * ((float) java.lang.Math.cos(d)), log * ((float) java.lang.Math.sin(d)));
    }

    public float distance(com.android.internal.graphics.cam.Cam cam) {
        float jstar = getJstar() - cam.getJstar();
        float astar = getAstar() - cam.getAstar();
        float bstar = getBstar() - cam.getBstar();
        return (float) (java.lang.Math.pow(java.lang.Math.sqrt((jstar * jstar) + (astar * astar) + (bstar * bstar)), 0.63d) * 1.41d);
    }

    public int viewedInSrgb() {
        return viewed(com.android.internal.graphics.cam.Frame.DEFAULT);
    }

    public int viewed(com.android.internal.graphics.cam.Frame frame) {
        float f;
        if (getChroma() == 0.0d || getJ() == 0.0d) {
            f = 0.0f;
        } else {
            f = getChroma() / ((float) java.lang.Math.sqrt(getJ() / 100.0d));
        }
        float pow = (float) java.lang.Math.pow(f / java.lang.Math.pow(1.64d - java.lang.Math.pow(0.29d, frame.getN()), 0.73d), 1.1111111111111112d);
        double hue = (getHue() * 3.1415927f) / 180.0f;
        float cos = ((float) (java.lang.Math.cos(2.0d + hue) + 3.8d)) * 0.25f;
        float aw = frame.getAw() * ((float) java.lang.Math.pow(getJ() / 100.0d, (1.0d / frame.getC()) / frame.getZ()));
        float nc = cos * 3846.1538f * frame.getNc() * frame.getNcb();
        float nbb = aw / frame.getNbb();
        float sin = (float) java.lang.Math.sin(hue);
        float cos2 = (float) java.lang.Math.cos(hue);
        float f2 = (((0.305f + nbb) * 23.0f) * pow) / (((nc * 23.0f) + ((11.0f * pow) * cos2)) + ((pow * 108.0f) * sin));
        float f3 = cos2 * f2;
        float f4 = f2 * sin;
        float f5 = nbb * 460.0f;
        float f6 = (((451.0f * f3) + f5) + (288.0f * f4)) / 1403.0f;
        float f7 = ((f5 - (891.0f * f3)) - (261.0f * f4)) / 1403.0f;
        float signum = java.lang.Math.signum(f6) * (100.0f / frame.getFl()) * ((float) java.lang.Math.pow((float) java.lang.Math.max(0.0d, (java.lang.Math.abs(f6) * 27.13d) / (400.0d - java.lang.Math.abs(f6))), 2.380952380952381d));
        float signum2 = java.lang.Math.signum(f7) * (100.0f / frame.getFl()) * ((float) java.lang.Math.pow((float) java.lang.Math.max(0.0d, (java.lang.Math.abs(f7) * 27.13d) / (400.0d - java.lang.Math.abs(f7))), 2.380952380952381d));
        float signum3 = java.lang.Math.signum(((f5 - (f3 * 220.0f)) - (f4 * 6300.0f)) / 1403.0f) * (100.0f / frame.getFl()) * ((float) java.lang.Math.pow((float) java.lang.Math.max(0.0d, (java.lang.Math.abs(r8) * 27.13d) / (400.0d - java.lang.Math.abs(r8))), 2.380952380952381d));
        float f8 = signum / frame.getRgbD()[0];
        float f9 = signum2 / frame.getRgbD()[1];
        float f10 = signum3 / frame.getRgbD()[2];
        float[][] fArr = com.android.internal.graphics.cam.CamUtils.CAM16RGB_TO_XYZ;
        return com.android.internal.graphics.ColorUtils.XYZToColor((fArr[0][0] * f8) + (fArr[0][1] * f9) + (fArr[0][2] * f10), (fArr[1][0] * f8) + (fArr[1][1] * f9) + (fArr[1][2] * f10), (f8 * fArr[2][0]) + (f9 * fArr[2][1]) + (f10 * fArr[2][2]));
    }

    public static int getInt(float f, float f2, float f3, com.android.internal.graphics.cam.Frame frame) {
        if (frame == com.android.internal.graphics.cam.Frame.DEFAULT) {
            return com.android.internal.graphics.cam.HctSolver.solveToInt(f, f2, f3);
        }
        if (f2 < 1.0d || java.lang.Math.round(f3) <= 0.0d || java.lang.Math.round(f3) >= 100.0d) {
            return com.android.internal.graphics.cam.CamUtils.intFromLstar(f3);
        }
        float min = f < 0.0f ? 0.0f : java.lang.Math.min(360.0f, f);
        com.android.internal.graphics.cam.Cam cam = null;
        boolean z = true;
        float f4 = 0.0f;
        float f5 = f2;
        while (java.lang.Math.abs(f4 - f2) >= 0.4f) {
            com.android.internal.graphics.cam.Cam findCamByJ = findCamByJ(min, f5, f3);
            if (z) {
                if (findCamByJ == null) {
                    f5 = ((f2 - f4) / 2.0f) + f4;
                    z = false;
                } else {
                    return findCamByJ.viewed(frame);
                }
            } else {
                if (findCamByJ == null) {
                    f2 = f5;
                } else {
                    f4 = f5;
                    cam = findCamByJ;
                }
                f5 = ((f2 - f4) / 2.0f) + f4;
            }
        }
        if (cam == null) {
            return com.android.internal.graphics.cam.CamUtils.intFromLstar(f3);
        }
        return cam.viewed(frame);
    }

    private static com.android.internal.graphics.cam.Cam findCamByJ(float f, float f2, float f3) {
        float f4 = 100.0f;
        float f5 = 1000.0f;
        float f6 = 0.0f;
        com.android.internal.graphics.cam.Cam cam = null;
        float f7 = 1000.0f;
        while (java.lang.Math.abs(f6 - f4) > 0.01f) {
            float f8 = ((f4 - f6) / 2.0f) + f6;
            int viewedInSrgb = fromJch(f8, f2, f).viewedInSrgb();
            float lstarFromInt = com.android.internal.graphics.cam.CamUtils.lstarFromInt(viewedInSrgb);
            float abs = java.lang.Math.abs(f3 - lstarFromInt);
            if (abs < 0.2f) {
                com.android.internal.graphics.cam.Cam fromInt = fromInt(viewedInSrgb);
                float distance = fromInt.distance(fromJch(fromInt.getJ(), fromInt.getChroma(), f));
                if (distance <= 1.0f) {
                    cam = fromInt;
                    f5 = abs;
                    f7 = distance;
                }
            }
            if (f5 == 0.0f && f7 == 0.0f) {
                break;
            }
            if (lstarFromInt < f3) {
                f6 = f8;
            } else {
                f4 = f8;
            }
        }
        return cam;
    }
}
