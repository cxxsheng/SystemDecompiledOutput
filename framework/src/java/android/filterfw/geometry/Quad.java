package android.filterfw.geometry;

/* loaded from: classes.dex */
public class Quad {
    public android.filterfw.geometry.Point p0;
    public android.filterfw.geometry.Point p1;
    public android.filterfw.geometry.Point p2;
    public android.filterfw.geometry.Point p3;

    public Quad() {
    }

    public Quad(android.filterfw.geometry.Point point, android.filterfw.geometry.Point point2, android.filterfw.geometry.Point point3, android.filterfw.geometry.Point point4) {
        this.p0 = point;
        this.p1 = point2;
        this.p2 = point3;
        this.p3 = point4;
    }

    public boolean IsInUnitRange() {
        return this.p0.IsInUnitRange() && this.p1.IsInUnitRange() && this.p2.IsInUnitRange() && this.p3.IsInUnitRange();
    }

    public android.filterfw.geometry.Quad translated(android.filterfw.geometry.Point point) {
        return new android.filterfw.geometry.Quad(this.p0.plus(point), this.p1.plus(point), this.p2.plus(point), this.p3.plus(point));
    }

    public android.filterfw.geometry.Quad translated(float f, float f2) {
        return new android.filterfw.geometry.Quad(this.p0.plus(f, f2), this.p1.plus(f, f2), this.p2.plus(f, f2), this.p3.plus(f, f2));
    }

    public android.filterfw.geometry.Quad scaled(float f) {
        return new android.filterfw.geometry.Quad(this.p0.times(f), this.p1.times(f), this.p2.times(f), this.p3.times(f));
    }

    public android.filterfw.geometry.Quad scaled(float f, float f2) {
        return new android.filterfw.geometry.Quad(this.p0.mult(f, f2), this.p1.mult(f, f2), this.p2.mult(f, f2), this.p3.mult(f, f2));
    }

    public android.filterfw.geometry.Rectangle boundingBox() {
        java.util.List asList = java.util.Arrays.asList(java.lang.Float.valueOf(this.p0.x), java.lang.Float.valueOf(this.p1.x), java.lang.Float.valueOf(this.p2.x), java.lang.Float.valueOf(this.p3.x));
        java.util.List asList2 = java.util.Arrays.asList(java.lang.Float.valueOf(this.p0.y), java.lang.Float.valueOf(this.p1.y), java.lang.Float.valueOf(this.p2.y), java.lang.Float.valueOf(this.p3.y));
        float floatValue = ((java.lang.Float) java.util.Collections.min(asList)).floatValue();
        float floatValue2 = ((java.lang.Float) java.util.Collections.min(asList2)).floatValue();
        return new android.filterfw.geometry.Rectangle(floatValue, floatValue2, ((java.lang.Float) java.util.Collections.max(asList)).floatValue() - floatValue, ((java.lang.Float) java.util.Collections.max(asList2)).floatValue() - floatValue2);
    }

    public float getBoundingWidth() {
        java.util.List asList = java.util.Arrays.asList(java.lang.Float.valueOf(this.p0.x), java.lang.Float.valueOf(this.p1.x), java.lang.Float.valueOf(this.p2.x), java.lang.Float.valueOf(this.p3.x));
        return ((java.lang.Float) java.util.Collections.max(asList)).floatValue() - ((java.lang.Float) java.util.Collections.min(asList)).floatValue();
    }

    public float getBoundingHeight() {
        java.util.List asList = java.util.Arrays.asList(java.lang.Float.valueOf(this.p0.y), java.lang.Float.valueOf(this.p1.y), java.lang.Float.valueOf(this.p2.y), java.lang.Float.valueOf(this.p3.y));
        return ((java.lang.Float) java.util.Collections.max(asList)).floatValue() - ((java.lang.Float) java.util.Collections.min(asList)).floatValue();
    }

    public java.lang.String toString() {
        return "{" + this.p0 + ", " + this.p1 + ", " + this.p2 + ", " + this.p3 + "}";
    }
}
