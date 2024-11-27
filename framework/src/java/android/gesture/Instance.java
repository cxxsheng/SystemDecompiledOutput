package android.gesture;

/* loaded from: classes.dex */
class Instance {
    private static final float[] ORIENTATIONS = {0.0f, 0.7853982f, 1.5707964f, 2.3561945f, 3.1415927f, 0.0f, -0.7853982f, -1.5707964f, -2.3561945f, -3.1415927f};
    private static final int PATCH_SAMPLE_SIZE = 16;
    private static final int SEQUENCE_SAMPLE_SIZE = 16;
    final long id;
    final java.lang.String label;
    final float[] vector;

    private Instance(long j, float[] fArr, java.lang.String str) {
        this.id = j;
        this.vector = fArr;
        this.label = str;
    }

    private void normalize() {
        float[] fArr = this.vector;
        int length = fArr.length;
        float f = 0.0f;
        for (int i = 0; i < length; i++) {
            f += fArr[i] * fArr[i];
        }
        float sqrt = (float) java.lang.Math.sqrt(f);
        for (int i2 = 0; i2 < length; i2++) {
            fArr[i2] = fArr[i2] / sqrt;
        }
    }

    static android.gesture.Instance createInstance(int i, int i2, android.gesture.Gesture gesture, java.lang.String str) {
        if (i == 2) {
            android.gesture.Instance instance = new android.gesture.Instance(gesture.getID(), temporalSampler(i2, gesture), str);
            instance.normalize();
            return instance;
        }
        return new android.gesture.Instance(gesture.getID(), spatialSampler(gesture), str);
    }

    private static float[] spatialSampler(android.gesture.Gesture gesture) {
        return android.gesture.GestureUtils.spatialSampling(gesture, 16, false);
    }

    private static float[] temporalSampler(int i, android.gesture.Gesture gesture) {
        float[] temporalSampling = android.gesture.GestureUtils.temporalSampling(gesture.getStrokes().get(0), 16);
        float[] computeCentroid = android.gesture.GestureUtils.computeCentroid(temporalSampling);
        float atan2 = (float) java.lang.Math.atan2(temporalSampling[1] - computeCentroid[1], temporalSampling[0] - computeCentroid[0]);
        float f = -atan2;
        if (i != 1) {
            int length = ORIENTATIONS.length;
            for (int i2 = 0; i2 < length; i2++) {
                float f2 = ORIENTATIONS[i2] - atan2;
                if (java.lang.Math.abs(f2) < java.lang.Math.abs(f)) {
                    f = f2;
                }
            }
        }
        android.gesture.GestureUtils.translate(temporalSampling, -computeCentroid[0], -computeCentroid[1]);
        android.gesture.GestureUtils.rotate(temporalSampling, f);
        return temporalSampling;
    }
}
