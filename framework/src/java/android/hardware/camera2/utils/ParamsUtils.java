package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class ParamsUtils {
    private static final int RATIONAL_DENOMINATOR = 1000000;

    public static android.graphics.Rect createRect(android.util.Size size) {
        com.android.internal.util.Preconditions.checkNotNull(size, "size must not be null");
        return new android.graphics.Rect(0, 0, size.getWidth(), size.getHeight());
    }

    public static android.graphics.Rect createRect(android.graphics.RectF rectF) {
        com.android.internal.util.Preconditions.checkNotNull(rectF, "rect must not be null");
        android.graphics.Rect rect = new android.graphics.Rect();
        rectF.roundOut(rect);
        return rect;
    }

    public static android.graphics.Rect mapRect(android.graphics.Matrix matrix, android.graphics.Rect rect) {
        com.android.internal.util.Preconditions.checkNotNull(matrix, "transform must not be null");
        com.android.internal.util.Preconditions.checkNotNull(rect, "rect must not be null");
        android.graphics.RectF rectF = new android.graphics.RectF(rect);
        matrix.mapRect(rectF);
        return createRect(rectF);
    }

    public static android.util.Size createSize(android.graphics.Rect rect) {
        com.android.internal.util.Preconditions.checkNotNull(rect, "rect must not be null");
        return new android.util.Size(rect.width(), rect.height());
    }

    public static android.util.Rational createRational(float f) {
        float f2;
        if (java.lang.Float.isNaN(f)) {
            return android.util.Rational.NaN;
        }
        if (f == Float.POSITIVE_INFINITY) {
            return android.util.Rational.POSITIVE_INFINITY;
        }
        if (f == Float.NEGATIVE_INFINITY) {
            return android.util.Rational.NEGATIVE_INFINITY;
        }
        if (f == 0.0f) {
            return android.util.Rational.ZERO;
        }
        int i = 1000000;
        while (true) {
            f2 = i * f;
            if ((f2 <= -2.1474836E9f || f2 >= 2.1474836E9f) && i != 1) {
                i /= 10;
            }
        }
        return new android.util.Rational((int) f2, i);
    }

    public static void convertRectF(android.graphics.Rect rect, android.graphics.RectF rectF) {
        com.android.internal.util.Preconditions.checkNotNull(rect, "source must not be null");
        com.android.internal.util.Preconditions.checkNotNull(rectF, "destination must not be null");
        rectF.left = rect.left;
        rectF.right = rect.right;
        rectF.bottom = rect.bottom;
        rectF.top = rect.top;
    }

    public static <T> T getOrDefault(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.CaptureRequest.Key<T> key, T t) {
        com.android.internal.util.Preconditions.checkNotNull(captureRequest, "r must not be null");
        com.android.internal.util.Preconditions.checkNotNull(key, "key must not be null");
        com.android.internal.util.Preconditions.checkNotNull(t, "defaultValue must not be null");
        T t2 = (T) captureRequest.get(key);
        if (t2 == null) {
            return t;
        }
        return t2;
    }

    private ParamsUtils() {
        throw new java.lang.AssertionError();
    }
}
