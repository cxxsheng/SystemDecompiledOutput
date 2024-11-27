package android.filterfw.geometry;

/* loaded from: classes.dex */
public class Rectangle extends android.filterfw.geometry.Quad {
    public Rectangle() {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Rectangle(float f, float f2, float f3, float f4) {
        super(r0, r1, new android.filterfw.geometry.Point(f, r5), new android.filterfw.geometry.Point(r6, r5));
        android.filterfw.geometry.Point point = new android.filterfw.geometry.Point(f, f2);
        float f5 = f3 + f;
        android.filterfw.geometry.Point point2 = new android.filterfw.geometry.Point(f5, f2);
        float f6 = f2 + f4;
    }

    public Rectangle(android.filterfw.geometry.Point point, android.filterfw.geometry.Point point2) {
        super(point, point.plus(point2.x, 0.0f), point.plus(0.0f, point2.y), point.plus(point2.x, point2.y));
    }

    public static android.filterfw.geometry.Rectangle fromRotatedRect(android.filterfw.geometry.Point point, android.filterfw.geometry.Point point2, float f) {
        return new android.filterfw.geometry.Rectangle(new android.filterfw.geometry.Point(point.x - (point2.x / 2.0f), point.y - (point2.y / 2.0f)).rotatedAround(point, f), new android.filterfw.geometry.Point(point.x + (point2.x / 2.0f), point.y - (point2.y / 2.0f)).rotatedAround(point, f), new android.filterfw.geometry.Point(point.x - (point2.x / 2.0f), point.y + (point2.y / 2.0f)).rotatedAround(point, f), new android.filterfw.geometry.Point(point.x + (point2.x / 2.0f), point.y + (point2.y / 2.0f)).rotatedAround(point, f));
    }

    private Rectangle(android.filterfw.geometry.Point point, android.filterfw.geometry.Point point2, android.filterfw.geometry.Point point3, android.filterfw.geometry.Point point4) {
        super(point, point2, point3, point4);
    }

    public static android.filterfw.geometry.Rectangle fromCenterVerticalAxis(android.filterfw.geometry.Point point, android.filterfw.geometry.Point point2, android.filterfw.geometry.Point point3) {
        android.filterfw.geometry.Point scaledTo = point2.scaledTo(point3.y / 2.0f);
        android.filterfw.geometry.Point scaledTo2 = point2.rotated90(1).scaledTo(point3.x / 2.0f);
        return new android.filterfw.geometry.Rectangle(point.minus(scaledTo2).minus(scaledTo), point.plus(scaledTo2).minus(scaledTo), point.minus(scaledTo2).plus(scaledTo), point.plus(scaledTo2).plus(scaledTo));
    }

    public float getWidth() {
        return this.p1.minus(this.p0).length();
    }

    public float getHeight() {
        return this.p2.minus(this.p0).length();
    }

    public android.filterfw.geometry.Point center() {
        return this.p0.plus(this.p1).plus(this.p2).plus(this.p3).times(0.25f);
    }

    @Override // android.filterfw.geometry.Quad
    public android.filterfw.geometry.Rectangle scaled(float f) {
        return new android.filterfw.geometry.Rectangle(this.p0.times(f), this.p1.times(f), this.p2.times(f), this.p3.times(f));
    }

    @Override // android.filterfw.geometry.Quad
    public android.filterfw.geometry.Rectangle scaled(float f, float f2) {
        return new android.filterfw.geometry.Rectangle(this.p0.mult(f, f2), this.p1.mult(f, f2), this.p2.mult(f, f2), this.p3.mult(f, f2));
    }
}
