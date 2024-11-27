package android.filterfw.geometry;

/* loaded from: classes.dex */
public class Point {
    public float x;
    public float y;

    public Point() {
    }

    public Point(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void set(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public boolean IsInUnitRange() {
        return this.x >= 0.0f && this.x <= 1.0f && this.y >= 0.0f && this.y <= 1.0f;
    }

    public android.filterfw.geometry.Point plus(float f, float f2) {
        return new android.filterfw.geometry.Point(this.x + f, this.y + f2);
    }

    public android.filterfw.geometry.Point plus(android.filterfw.geometry.Point point) {
        return plus(point.x, point.y);
    }

    public android.filterfw.geometry.Point minus(float f, float f2) {
        return new android.filterfw.geometry.Point(this.x - f, this.y - f2);
    }

    public android.filterfw.geometry.Point minus(android.filterfw.geometry.Point point) {
        return minus(point.x, point.y);
    }

    public android.filterfw.geometry.Point times(float f) {
        return new android.filterfw.geometry.Point(this.x * f, this.y * f);
    }

    public android.filterfw.geometry.Point mult(float f, float f2) {
        return new android.filterfw.geometry.Point(this.x * f, this.y * f2);
    }

    public float length() {
        return (float) java.lang.Math.hypot(this.x, this.y);
    }

    public float distanceTo(android.filterfw.geometry.Point point) {
        return point.minus(this).length();
    }

    public android.filterfw.geometry.Point scaledTo(float f) {
        return times(f / length());
    }

    public android.filterfw.geometry.Point normalize() {
        return scaledTo(1.0f);
    }

    public android.filterfw.geometry.Point rotated90(int i) {
        float f = this.x;
        float f2 = this.y;
        int i2 = 0;
        while (i2 < i) {
            i2++;
            float f3 = f2;
            f2 = -f;
            f = f3;
        }
        return new android.filterfw.geometry.Point(f, f2);
    }

    public android.filterfw.geometry.Point rotated(float f) {
        double d = f;
        return new android.filterfw.geometry.Point((float) ((java.lang.Math.cos(d) * this.x) - (java.lang.Math.sin(d) * this.y)), (float) ((java.lang.Math.sin(d) * this.x) + (java.lang.Math.cos(d) * this.y)));
    }

    public android.filterfw.geometry.Point rotatedAround(android.filterfw.geometry.Point point, float f) {
        return minus(point).rotated(f).plus(point);
    }

    public java.lang.String toString() {
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + this.x + ", " + this.y + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
