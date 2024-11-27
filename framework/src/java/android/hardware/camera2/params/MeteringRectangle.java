package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class MeteringRectangle {
    public static final int METERING_WEIGHT_DONT_CARE = 0;
    public static final int METERING_WEIGHT_MAX = 1000;
    public static final int METERING_WEIGHT_MIN = 0;
    private final int mHeight;
    private final int mWeight;
    private final int mWidth;
    private final int mX;
    private final int mY;

    public MeteringRectangle(int i, int i2, int i3, int i4, int i5) {
        this.mX = com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "x must be nonnegative");
        this.mY = com.android.internal.util.Preconditions.checkArgumentNonnegative(i2, "y must be nonnegative");
        this.mWidth = com.android.internal.util.Preconditions.checkArgumentNonnegative(i3, "width must be nonnegative");
        this.mHeight = com.android.internal.util.Preconditions.checkArgumentNonnegative(i4, "height must be nonnegative");
        this.mWeight = com.android.internal.util.Preconditions.checkArgumentInRange(i5, 0, 1000, "meteringWeight");
    }

    public MeteringRectangle(android.graphics.Point point, android.util.Size size, int i) {
        com.android.internal.util.Preconditions.checkNotNull(point, "xy must not be null");
        com.android.internal.util.Preconditions.checkNotNull(size, "dimensions must not be null");
        this.mX = com.android.internal.util.Preconditions.checkArgumentNonnegative(point.x, "x must be nonnegative");
        this.mY = com.android.internal.util.Preconditions.checkArgumentNonnegative(point.y, "y must be nonnegative");
        this.mWidth = com.android.internal.util.Preconditions.checkArgumentNonnegative(size.getWidth(), "width must be nonnegative");
        this.mHeight = com.android.internal.util.Preconditions.checkArgumentNonnegative(size.getHeight(), "height must be nonnegative");
        this.mWeight = com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "meteringWeight must be nonnegative");
    }

    public MeteringRectangle(android.graphics.Rect rect, int i) {
        com.android.internal.util.Preconditions.checkNotNull(rect, "rect must not be null");
        this.mX = com.android.internal.util.Preconditions.checkArgumentNonnegative(rect.left, "rect.left must be nonnegative");
        this.mY = com.android.internal.util.Preconditions.checkArgumentNonnegative(rect.top, "rect.top must be nonnegative");
        this.mWidth = com.android.internal.util.Preconditions.checkArgumentNonnegative(rect.width(), "rect.width must be nonnegative");
        this.mHeight = com.android.internal.util.Preconditions.checkArgumentNonnegative(rect.height(), "rect.height must be nonnegative");
        this.mWeight = com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "meteringWeight must be nonnegative");
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getMeteringWeight() {
        return this.mWeight;
    }

    public android.graphics.Point getUpperLeftPoint() {
        return new android.graphics.Point(this.mX, this.mY);
    }

    public android.util.Size getSize() {
        return new android.util.Size(this.mWidth, this.mHeight);
    }

    public android.graphics.Rect getRect() {
        return new android.graphics.Rect(this.mX, this.mY, this.mX + this.mWidth, this.mY + this.mHeight);
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.hardware.camera2.params.MeteringRectangle) && equals((android.hardware.camera2.params.MeteringRectangle) obj);
    }

    public boolean equals(android.hardware.camera2.params.MeteringRectangle meteringRectangle) {
        return meteringRectangle != null && this.mX == meteringRectangle.mX && this.mY == meteringRectangle.mY && this.mWidth == meteringRectangle.mWidth && this.mHeight == meteringRectangle.mHeight && this.mWeight == meteringRectangle.mWeight;
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mX, this.mY, this.mWidth, this.mHeight, this.mWeight);
    }

    public java.lang.String toString() {
        return java.lang.String.format("(x:%d, y:%d, w:%d, h:%d, wt:%d)", java.lang.Integer.valueOf(this.mX), java.lang.Integer.valueOf(this.mY), java.lang.Integer.valueOf(this.mWidth), java.lang.Integer.valueOf(this.mHeight), java.lang.Integer.valueOf(this.mWeight));
    }
}
