package android.gesture;

/* loaded from: classes.dex */
public class GestureStroke {
    static final float TOUCH_TOLERANCE = 3.0f;
    public final android.graphics.RectF boundingBox;
    public final float length;
    private android.graphics.Path mCachedPath;
    public final float[] points;
    private final long[] timestamps;

    public GestureStroke(java.util.ArrayList<android.gesture.GesturePoint> arrayList) {
        int i;
        int size = arrayList.size();
        float[] fArr = new float[size * 2];
        long[] jArr = new long[size];
        android.graphics.RectF rectF = null;
        int i2 = 0;
        int i3 = 0;
        float f = 0.0f;
        while (i2 < size) {
            android.gesture.GesturePoint gesturePoint = arrayList.get(i2);
            int i4 = i2 * 2;
            fArr[i4] = gesturePoint.x;
            fArr[i4 + 1] = gesturePoint.y;
            jArr[i3] = gesturePoint.timestamp;
            if (rectF == null) {
                rectF = new android.graphics.RectF();
                rectF.top = gesturePoint.y;
                rectF.left = gesturePoint.x;
                rectF.right = gesturePoint.x;
                rectF.bottom = gesturePoint.y;
                i = i2;
                f = 0.0f;
            } else {
                int i5 = (i2 - 1) * 2;
                i = i2;
                float hypot = (float) (f + java.lang.Math.hypot(gesturePoint.x - fArr[i5], gesturePoint.y - fArr[i5 + 1]));
                rectF.union(gesturePoint.x, gesturePoint.y);
                f = hypot;
            }
            i3++;
            i2 = i + 1;
        }
        this.timestamps = jArr;
        this.points = fArr;
        this.boundingBox = rectF;
        this.length = f;
    }

    private GestureStroke(android.graphics.RectF rectF, float f, float[] fArr, long[] jArr) {
        this.boundingBox = new android.graphics.RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        this.length = f;
        this.points = (float[]) fArr.clone();
        this.timestamps = (long[]) jArr.clone();
    }

    public java.lang.Object clone() {
        return new android.gesture.GestureStroke(this.boundingBox, this.length, this.points, this.timestamps);
    }

    void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        if (this.mCachedPath == null) {
            makePath();
        }
        canvas.drawPath(this.mCachedPath, paint);
    }

    public android.graphics.Path getPath() {
        if (this.mCachedPath == null) {
            makePath();
        }
        return this.mCachedPath;
    }

    private void makePath() {
        float[] fArr = this.points;
        int length = fArr.length;
        android.graphics.Path path = null;
        float f = 0.0f;
        float f2 = 0.0f;
        for (int i = 0; i < length; i += 2) {
            float f3 = fArr[i];
            float f4 = fArr[i + 1];
            if (path == null) {
                path = new android.graphics.Path();
                path.moveTo(f3, f4);
                f = f3;
                f2 = f4;
            } else {
                float abs = java.lang.Math.abs(f3 - f);
                float abs2 = java.lang.Math.abs(f4 - f2);
                if (abs >= 3.0f || abs2 >= 3.0f) {
                    path.quadTo(f, f2, (f3 + f) / 2.0f, (f4 + f2) / 2.0f);
                    f = f3;
                    f2 = f4;
                }
            }
        }
        this.mCachedPath = path;
    }

    public android.graphics.Path toPath(float f, float f2, int i) {
        float[] temporalSampling = android.gesture.GestureUtils.temporalSampling(this, i);
        android.graphics.RectF rectF = this.boundingBox;
        android.gesture.GestureUtils.translate(temporalSampling, -rectF.left, -rectF.top);
        float width = f / rectF.width();
        float height = f2 / rectF.height();
        if (width > height) {
            width = height;
        }
        android.gesture.GestureUtils.scale(temporalSampling, width, width);
        int length = temporalSampling.length;
        float f3 = 0.0f;
        android.graphics.Path path = null;
        float f4 = 0.0f;
        for (int i2 = 0; i2 < length; i2 += 2) {
            float f5 = temporalSampling[i2];
            float f6 = temporalSampling[i2 + 1];
            if (path == null) {
                android.graphics.Path path2 = new android.graphics.Path();
                path2.moveTo(f5, f6);
                path = path2;
                f3 = f5;
                f4 = f6;
            } else {
                float abs = java.lang.Math.abs(f5 - f3);
                float abs2 = java.lang.Math.abs(f6 - f4);
                if (abs >= 3.0f || abs2 >= 3.0f) {
                    path.quadTo(f3, f4, (f5 + f3) / 2.0f, (f6 + f4) / 2.0f);
                    f3 = f5;
                    f4 = f6;
                }
            }
        }
        return path;
    }

    void serialize(java.io.DataOutputStream dataOutputStream) throws java.io.IOException {
        float[] fArr = this.points;
        long[] jArr = this.timestamps;
        int length = this.points.length;
        dataOutputStream.writeInt(length / 2);
        for (int i = 0; i < length; i += 2) {
            dataOutputStream.writeFloat(fArr[i]);
            dataOutputStream.writeFloat(fArr[i + 1]);
            dataOutputStream.writeLong(jArr[i / 2]);
        }
    }

    static android.gesture.GestureStroke deserialize(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        int readInt = dataInputStream.readInt();
        java.util.ArrayList arrayList = new java.util.ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(android.gesture.GesturePoint.deserialize(dataInputStream));
        }
        return new android.gesture.GestureStroke(arrayList);
    }

    public void clearPath() {
        if (this.mCachedPath != null) {
            this.mCachedPath.rewind();
        }
    }

    public android.gesture.OrientedBoundingBox computeOrientedBoundingBox() {
        return android.gesture.GestureUtils.computeOrientedBoundingBox(this.points);
    }
}
