package android.util;

/* loaded from: classes3.dex */
public class RotationUtils {
    public static android.graphics.Insets rotateInsets(android.graphics.Insets insets, int i) {
        if (insets == null || insets == android.graphics.Insets.NONE) {
            return insets;
        }
        switch (i) {
            case 0:
                return insets;
            case 1:
                return android.graphics.Insets.of(insets.top, insets.right, insets.bottom, insets.left);
            case 2:
                return android.graphics.Insets.of(insets.right, insets.bottom, insets.left, insets.top);
            case 3:
                return android.graphics.Insets.of(insets.bottom, insets.left, insets.top, insets.right);
            default:
                throw new java.lang.IllegalArgumentException("unknown rotation: " + i);
        }
    }

    public static void rotateBounds(android.graphics.Rect rect, android.graphics.Rect rect2, int i, int i2) {
        rotateBounds(rect, rect2, deltaRotation(i, i2));
    }

    public static void rotateBounds(android.graphics.Rect rect, int i, int i2, int i3) {
        int i4 = rect.left;
        int i5 = rect.top;
        switch (i3) {
            case 1:
                rect.left = rect.top;
                rect.top = i - rect.right;
                rect.right = rect.bottom;
                rect.bottom = i - i4;
                break;
            case 2:
                rect.left = i - rect.right;
                rect.right = i - i4;
                rect.top = i2 - rect.bottom;
                rect.bottom = i2 - i5;
                break;
            case 3:
                rect.left = i2 - rect.bottom;
                rect.bottom = rect.right;
                rect.right = i2 - rect.top;
                rect.top = i4;
                break;
        }
    }

    public static void rotateBounds(android.graphics.Rect rect, android.graphics.Rect rect2, int i) {
        rotateBounds(rect, rect2.right, rect2.bottom, i);
    }

    public static int deltaRotation(int i, int i2) {
        int i3 = i2 - i;
        return i3 < 0 ? i3 + 4 : i3;
    }

    public static void rotateSurface(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i) {
        switch (i) {
            case 0:
                transaction.setMatrix(surfaceControl, 1.0f, 0.0f, 0.0f, 1.0f);
                break;
            case 1:
                transaction.setMatrix(surfaceControl, 0.0f, -1.0f, 1.0f, 0.0f);
                break;
            case 2:
                transaction.setMatrix(surfaceControl, -1.0f, 0.0f, 0.0f, -1.0f);
                break;
            case 3:
                transaction.setMatrix(surfaceControl, 0.0f, 1.0f, -1.0f, 0.0f);
                break;
        }
    }

    public static void rotatePoint(android.graphics.Point point, int i, int i2, int i3) {
        int i4 = point.x;
        switch (i) {
            case 1:
                point.x = point.y;
                point.y = i2 - i4;
                break;
            case 2:
                point.x = i2 - point.x;
                point.y = i3 - point.y;
                break;
            case 3:
                point.x = i3 - point.y;
                point.y = i4;
                break;
        }
    }

    public static void rotatePointF(android.graphics.PointF pointF, int i, float f, float f2) {
        float f3 = pointF.x;
        switch (i) {
            case 1:
                pointF.x = pointF.y;
                pointF.y = f - f3;
                break;
            case 2:
                pointF.x = f - pointF.x;
                pointF.y = f2 - pointF.y;
                break;
            case 3:
                pointF.x = f2 - pointF.y;
                pointF.y = f3;
                break;
        }
    }

    public static void transformPhysicalToLogicalCoordinates(int i, int i2, int i3, android.graphics.Matrix matrix) {
        switch (i) {
            case 0:
                matrix.reset();
                return;
            case 1:
                matrix.setRotate(270.0f);
                matrix.postTranslate(0.0f, i2);
                return;
            case 2:
                matrix.setRotate(180.0f);
                matrix.postTranslate(i2, i3);
                return;
            case 3:
                matrix.setRotate(90.0f);
                matrix.postTranslate(i3, 0.0f);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Unknown rotation: " + i);
        }
    }

    public static int reverseRotationDirectionAroundZAxis(int i) {
        if (i == 1) {
            return 3;
        }
        if (i == 3) {
            return 1;
        }
        return i;
    }
}
