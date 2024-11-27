package android.gesture;

/* loaded from: classes.dex */
public final class GestureUtils {
    private static final float NONUNIFORM_SCALE = (float) java.lang.Math.sqrt(2.0d);
    private static final float SCALING_THRESHOLD = 0.26f;

    private GestureUtils() {
    }

    static void closeStream(java.io.Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (java.io.IOException e) {
                android.util.Log.e(android.gesture.GestureConstants.LOG_TAG, "Could not close stream", e);
            }
        }
    }

    public static float[] spatialSampling(android.gesture.Gesture gesture, int i) {
        return spatialSampling(gesture, i, false);
    }

    public static float[] spatialSampling(android.gesture.Gesture gesture, int i, boolean z) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8 = i - 1;
        float[] fArr = new float[i * i];
        float f9 = 0.0f;
        java.util.Arrays.fill(fArr, 0.0f);
        android.graphics.RectF boundingBox = gesture.getBoundingBox();
        float width = boundingBox.width();
        float height = boundingBox.height();
        float f10 = f8 / width;
        float f11 = f8 / height;
        if (z) {
            if (f10 >= f11) {
                f10 = f11;
            }
            f11 = f10;
        } else {
            float f12 = width / height;
            if (f12 > 1.0f) {
                f12 = 1.0f / f12;
            }
            if (f12 < SCALING_THRESHOLD) {
                if (f10 >= f11) {
                    f10 = f11;
                }
                f11 = f10;
            } else if (f10 > f11) {
                float f13 = NONUNIFORM_SCALE * f11;
                if (f13 < f10) {
                    f10 = f13;
                }
            } else {
                float f14 = NONUNIFORM_SCALE * f10;
                if (f14 < f11) {
                    f11 = f14;
                }
            }
        }
        float f15 = -boundingBox.centerX();
        float f16 = -boundingBox.centerY();
        float f17 = f8 / 2.0f;
        java.util.ArrayList<android.gesture.GestureStroke> strokes = gesture.getStrokes();
        int size = strokes.size();
        int i2 = 0;
        while (i2 < size) {
            float[] fArr2 = strokes.get(i2).points;
            int length = fArr2.length;
            float[] fArr3 = new float[length];
            for (int i3 = 0; i3 < length; i3 += 2) {
                fArr3[i3] = ((fArr2[i3] + f15) * f10) + f17;
                int i4 = i3 + 1;
                fArr3[i4] = ((fArr2[i4] + f16) * f11) + f17;
            }
            int i5 = 0;
            float f18 = -1.0f;
            float f19 = -1.0f;
            while (i5 < length) {
                float f20 = fArr3[i5] < f9 ? f9 : fArr3[i5];
                int i6 = i5 + 1;
                float f21 = fArr3[i6] < f9 ? f9 : fArr3[i6];
                if (f20 <= f8) {
                    f = f20;
                } else {
                    f = f8;
                }
                if (f21 <= f8) {
                    f2 = f21;
                } else {
                    f2 = f8;
                }
                plot(f, f2, fArr, i);
                float f22 = f8;
                float f23 = f18;
                if (f23 != -1.0f) {
                    if (f23 > f) {
                        f3 = f16;
                        f4 = f15;
                        float ceil = (float) java.lang.Math.ceil(f);
                        f7 = f19;
                        float f24 = (f7 - f2) / (f23 - f);
                        while (ceil < f23) {
                            plot(ceil, ((ceil - f) * f24) + f2, fArr, i);
                            ceil += 1.0f;
                            f17 = f17;
                        }
                        f5 = f17;
                        f6 = f10;
                    } else {
                        f3 = f16;
                        f4 = f15;
                        f5 = f17;
                        f7 = f19;
                        if (f23 >= f) {
                            f6 = f10;
                        } else {
                            f6 = f10;
                            float f25 = (f7 - f2) / (f23 - f);
                            for (float ceil2 = (float) java.lang.Math.ceil(f23); ceil2 < f; ceil2 += 1.0f) {
                                plot(ceil2, ((ceil2 - f) * f25) + f2, fArr, i);
                            }
                        }
                    }
                    if (f7 > f2) {
                        float f26 = (f23 - f) / (f7 - f2);
                        for (float ceil3 = (float) java.lang.Math.ceil(f2); ceil3 < f7; ceil3 += 1.0f) {
                            plot(((ceil3 - f2) * f26) + f, ceil3, fArr, i);
                        }
                    } else if (f7 < f2) {
                        float f27 = (f23 - f) / (f7 - f2);
                        for (float ceil4 = (float) java.lang.Math.ceil(f7); ceil4 < f2; ceil4 += 1.0f) {
                            plot(((ceil4 - f2) * f27) + f, ceil4, fArr, i);
                        }
                    }
                } else {
                    f3 = f16;
                    f4 = f15;
                    f5 = f17;
                    f6 = f10;
                }
                i5 += 2;
                f10 = f6;
                f19 = f2;
                f8 = f22;
                f15 = f4;
                f17 = f5;
                f16 = f3;
                f18 = f;
                f9 = 0.0f;
            }
            i2++;
            f15 = f15;
            f9 = 0.0f;
            f16 = f16;
        }
        return fArr;
    }

    private static void plot(float f, float f2, float[] fArr, int i) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f2 < 0.0f) {
            f2 = 0.0f;
        }
        double d = f;
        int floor = (int) java.lang.Math.floor(d);
        int ceil = (int) java.lang.Math.ceil(d);
        double d2 = f2;
        int floor2 = (int) java.lang.Math.floor(d2);
        int ceil2 = (int) java.lang.Math.ceil(d2);
        if (f == floor && f2 == floor2) {
            int i2 = (ceil2 * i) + ceil;
            if (fArr[i2] < 1.0f) {
                fArr[i2] = 1.0f;
                return;
            }
            return;
        }
        double pow = java.lang.Math.pow(r4 - f, 2.0d);
        double pow2 = java.lang.Math.pow(floor2 - f2, 2.0d);
        double pow3 = java.lang.Math.pow(ceil - f, 2.0d);
        double pow4 = java.lang.Math.pow(ceil2 - f2, 2.0d);
        float sqrt = (float) java.lang.Math.sqrt(pow + pow2);
        float sqrt2 = (float) java.lang.Math.sqrt(pow2 + pow3);
        float sqrt3 = (float) java.lang.Math.sqrt(pow + pow4);
        float sqrt4 = (float) java.lang.Math.sqrt(pow3 + pow4);
        float f3 = sqrt + sqrt2 + sqrt3 + sqrt4;
        float f4 = sqrt / f3;
        int i3 = floor2 * i;
        int i4 = i3 + floor;
        if (f4 > fArr[i4]) {
            fArr[i4] = f4;
        }
        float f5 = sqrt2 / f3;
        int i5 = i3 + ceil;
        if (f5 > fArr[i5]) {
            fArr[i5] = f5;
        }
        float f6 = sqrt3 / f3;
        int i6 = ceil2 * i;
        int i7 = floor + i6;
        if (f6 > fArr[i7]) {
            fArr[i7] = f6;
        }
        float f7 = sqrt4 / f3;
        int i8 = i6 + ceil;
        if (f7 > fArr[i8]) {
            fArr[i8] = f7;
        }
    }

    public static float[] temporalSampling(android.gesture.GestureStroke gestureStroke, int i) {
        float f;
        float f2 = gestureStroke.length / (i - 1);
        int i2 = 2;
        int i3 = i * 2;
        float[] fArr = new float[i3];
        float[] fArr2 = gestureStroke.points;
        int i4 = 0;
        float f3 = fArr2[0];
        int i5 = 1;
        float f4 = fArr2[1];
        fArr[0] = f3;
        fArr[1] = f4;
        int length = fArr2.length / 2;
        float f5 = Float.MIN_VALUE;
        float f6 = Float.MIN_VALUE;
        float f7 = Float.MIN_VALUE;
        float f8 = 0.0f;
        while (true) {
            if (i4 >= length) {
                f = f4;
                break;
            }
            if (f6 == f5) {
                i4++;
                if (i4 >= length) {
                    f = f4;
                    break;
                }
                int i6 = i4 * 2;
                float f9 = fArr2[i6];
                f7 = fArr2[i6 + i5];
                f6 = f9;
            }
            float f10 = f6 - f3;
            float f11 = f7 - f4;
            float f12 = f6;
            float f13 = f4;
            float hypot = (float) java.lang.Math.hypot(f10, f11);
            float f14 = f8 + hypot;
            if (f14 >= f2) {
                float f15 = (f2 - f8) / hypot;
                f3 += f10 * f15;
                f4 = f13 + (f15 * f11);
                fArr[i2] = f3;
                int i7 = i2 + 1;
                fArr[i7] = f4;
                i5 = 1;
                i2 = i7 + 1;
                f6 = f12;
                f8 = 0.0f;
            } else {
                i5 = 1;
                f8 = f14;
                f4 = f7;
                f3 = f12;
                f6 = Float.MIN_VALUE;
                f7 = Float.MIN_VALUE;
            }
            f5 = Float.MIN_VALUE;
        }
        while (i2 < i3) {
            fArr[i2] = f3;
            fArr[i2 + 1] = f;
            i2 += 2;
        }
        return fArr;
    }

    static float[] computeCentroid(float[] fArr) {
        int length = fArr.length;
        float f = 0.0f;
        float f2 = 0.0f;
        int i = 0;
        while (i < length) {
            f += fArr[i];
            int i2 = i + 1;
            f2 += fArr[i2];
            i = i2 + 1;
        }
        float f3 = length;
        return new float[]{(f * 2.0f) / f3, (f2 * 2.0f) / f3};
    }

    private static float[][] computeCoVariance(float[] fArr) {
        float[][] fArr2 = (float[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Float.TYPE, 2, 2);
        fArr2[0][0] = 0.0f;
        fArr2[0][1] = 0.0f;
        fArr2[1][0] = 0.0f;
        fArr2[1][1] = 0.0f;
        int length = fArr.length;
        int i = 0;
        while (i < length) {
            float f = fArr[i];
            int i2 = i + 1;
            float f2 = fArr[i2];
            float[] fArr3 = fArr2[0];
            fArr3[0] = fArr3[0] + (f * f);
            float[] fArr4 = fArr2[0];
            fArr4[1] = fArr4[1] + (f * f2);
            fArr2[1][0] = fArr2[0][1];
            float[] fArr5 = fArr2[1];
            fArr5[1] = fArr5[1] + (f2 * f2);
            i = i2 + 1;
        }
        float[] fArr6 = fArr2[0];
        float f3 = length / 2;
        fArr6[0] = fArr6[0] / f3;
        float[] fArr7 = fArr2[0];
        fArr7[1] = fArr7[1] / f3;
        float[] fArr8 = fArr2[1];
        fArr8[0] = fArr8[0] / f3;
        float[] fArr9 = fArr2[1];
        fArr9[1] = fArr9[1] / f3;
        return fArr2;
    }

    static float computeTotalLength(float[] fArr) {
        int length = fArr.length - 4;
        float f = 0.0f;
        for (int i = 0; i < length; i += 2) {
            f = (float) (f + java.lang.Math.hypot(fArr[r3] - fArr[i], fArr[i + 3] - fArr[i + 1]));
        }
        return f;
    }

    static float computeStraightness(float[] fArr) {
        return ((float) java.lang.Math.hypot(fArr[2] - fArr[0], fArr[3] - fArr[1])) / computeTotalLength(fArr);
    }

    static float computeStraightness(float[] fArr, float f) {
        return ((float) java.lang.Math.hypot(fArr[2] - fArr[0], fArr[3] - fArr[1])) / f;
    }

    static float squaredEuclideanDistance(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            float f2 = fArr[i] - fArr2[i];
            f += f2 * f2;
        }
        return f / length;
    }

    static float cosineDistance(float[] fArr, float[] fArr2) {
        int length = fArr.length;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            f += fArr[i] * fArr2[i];
        }
        return (float) java.lang.Math.acos(f);
    }

    static float minimumCosineDistance(float[] fArr, float[] fArr2, int i) {
        int length = fArr.length;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i2 = 0; i2 < length; i2 += 2) {
            int i3 = i2 + 1;
            f += (fArr[i2] * fArr2[i2]) + (fArr[i3] * fArr2[i3]);
            f2 += (fArr[i2] * fArr2[i3]) - (fArr[i3] * fArr2[i2]);
        }
        if (f != 0.0f) {
            double d = f2 / f;
            double atan = java.lang.Math.atan(d);
            if (i > 2 && java.lang.Math.abs(atan) >= 3.141592653589793d / i) {
                return (float) java.lang.Math.acos(f);
            }
            double cos = java.lang.Math.cos(atan);
            return (float) java.lang.Math.acos((f * cos) + (f2 * d * cos));
        }
        return 1.5707964f;
    }

    public static android.gesture.OrientedBoundingBox computeOrientedBoundingBox(java.util.ArrayList<android.gesture.GesturePoint> arrayList) {
        int size = arrayList.size();
        float[] fArr = new float[size * 2];
        for (int i = 0; i < size; i++) {
            android.gesture.GesturePoint gesturePoint = arrayList.get(i);
            int i2 = i * 2;
            fArr[i2] = gesturePoint.x;
            fArr[i2 + 1] = gesturePoint.y;
        }
        return computeOrientedBoundingBox(fArr, computeCentroid(fArr));
    }

    public static android.gesture.OrientedBoundingBox computeOrientedBoundingBox(float[] fArr) {
        int length = fArr.length;
        float[] fArr2 = new float[length];
        for (int i = 0; i < length; i++) {
            fArr2[i] = fArr[i];
        }
        return computeOrientedBoundingBox(fArr2, computeCentroid(fArr2));
    }

    private static android.gesture.OrientedBoundingBox computeOrientedBoundingBox(float[] fArr, float[] fArr2) {
        float atan2;
        translate(fArr, -fArr2[0], -fArr2[1]);
        float[] computeOrientation = computeOrientation(computeCoVariance(fArr));
        if (computeOrientation[0] == 0.0f && computeOrientation[1] == 0.0f) {
            atan2 = -1.5707964f;
        } else {
            atan2 = (float) java.lang.Math.atan2(computeOrientation[1], computeOrientation[0]);
            rotate(fArr, -atan2);
        }
        int length = fArr.length;
        float f = Float.MIN_VALUE;
        int i = 0;
        float f2 = Float.MAX_VALUE;
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MIN_VALUE;
        while (i < length) {
            if (fArr[i] < f2) {
                f2 = fArr[i];
            }
            if (fArr[i] > f) {
                f = fArr[i];
            }
            int i2 = i + 1;
            if (fArr[i2] < f3) {
                f3 = fArr[i2];
            }
            if (fArr[i2] > f4) {
                f4 = fArr[i2];
            }
            i = i2 + 1;
        }
        return new android.gesture.OrientedBoundingBox((float) ((atan2 * 180.0f) / 3.141592653589793d), fArr2[0], fArr2[1], f - f2, f4 - f3);
    }

    private static float[] computeOrientation(float[][] fArr) {
        float[] fArr2 = new float[2];
        if (fArr[0][1] == 0.0f || fArr[1][0] == 0.0f) {
            fArr2[0] = 1.0f;
            fArr2[1] = 0.0f;
        }
        float f = ((-fArr[0][0]) - fArr[1][1]) / 2.0f;
        float sqrt = (float) java.lang.Math.sqrt(java.lang.Math.pow(f, 2.0d) - ((fArr[0][0] * fArr[1][1]) - (fArr[0][1] * fArr[1][0])));
        float f2 = -f;
        float f3 = f2 + sqrt;
        float f4 = f2 - sqrt;
        if (f3 == f4) {
            fArr2[0] = 0.0f;
            fArr2[1] = 0.0f;
        } else {
            if (f3 <= f4) {
                f3 = f4;
            }
            fArr2[0] = 1.0f;
            fArr2[1] = (f3 - fArr[0][0]) / fArr[0][1];
        }
        return fArr2;
    }

    static float[] rotate(float[] fArr, float f) {
        double d = f;
        float cos = (float) java.lang.Math.cos(d);
        float sin = (float) java.lang.Math.sin(d);
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            int i2 = i + 1;
            float f2 = (fArr[i] * cos) - (fArr[i2] * sin);
            float f3 = (fArr[i] * sin) + (fArr[i2] * cos);
            fArr[i] = f2;
            fArr[i2] = f3;
        }
        return fArr;
    }

    static float[] translate(float[] fArr, float f, float f2) {
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            fArr[i] = fArr[i] + f;
            int i2 = i + 1;
            fArr[i2] = fArr[i2] + f2;
        }
        return fArr;
    }

    static float[] scale(float[] fArr, float f, float f2) {
        int length = fArr.length;
        for (int i = 0; i < length; i += 2) {
            fArr[i] = fArr[i] * f;
            int i2 = i + 1;
            fArr[i2] = fArr[i2] * f2;
        }
        return fArr;
    }
}
