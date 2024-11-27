package org.apache.commons.math.geometry;

/* loaded from: classes3.dex */
public class Vector3D implements java.io.Serializable {
    private static final long serialVersionUID = 5133268763396045979L;
    private final double x;
    private final double y;
    private final double z;
    public static final org.apache.commons.math.geometry.Vector3D ZERO = new org.apache.commons.math.geometry.Vector3D(0.0d, 0.0d, 0.0d);
    public static final org.apache.commons.math.geometry.Vector3D PLUS_I = new org.apache.commons.math.geometry.Vector3D(1.0d, 0.0d, 0.0d);
    public static final org.apache.commons.math.geometry.Vector3D MINUS_I = new org.apache.commons.math.geometry.Vector3D(-1.0d, 0.0d, 0.0d);
    public static final org.apache.commons.math.geometry.Vector3D PLUS_J = new org.apache.commons.math.geometry.Vector3D(0.0d, 1.0d, 0.0d);
    public static final org.apache.commons.math.geometry.Vector3D MINUS_J = new org.apache.commons.math.geometry.Vector3D(0.0d, -1.0d, 0.0d);
    public static final org.apache.commons.math.geometry.Vector3D PLUS_K = new org.apache.commons.math.geometry.Vector3D(0.0d, 0.0d, 1.0d);
    public static final org.apache.commons.math.geometry.Vector3D MINUS_K = new org.apache.commons.math.geometry.Vector3D(0.0d, 0.0d, -1.0d);
    public static final org.apache.commons.math.geometry.Vector3D NaN = new org.apache.commons.math.geometry.Vector3D(Double.NaN, Double.NaN, Double.NaN);
    public static final org.apache.commons.math.geometry.Vector3D POSITIVE_INFINITY = new org.apache.commons.math.geometry.Vector3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final org.apache.commons.math.geometry.Vector3D NEGATIVE_INFINITY = new org.apache.commons.math.geometry.Vector3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    private static final org.apache.commons.math.geometry.Vector3DFormat DEFAULT_FORMAT = org.apache.commons.math.geometry.Vector3DFormat.getInstance();

    public Vector3D(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public Vector3D(double d, double d2) {
        double cos = org.apache.commons.math.util.FastMath.cos(d2);
        this.x = org.apache.commons.math.util.FastMath.cos(d) * cos;
        this.y = org.apache.commons.math.util.FastMath.sin(d) * cos;
        this.z = org.apache.commons.math.util.FastMath.sin(d2);
    }

    public Vector3D(double d, org.apache.commons.math.geometry.Vector3D vector3D) {
        this.x = vector3D.x * d;
        this.y = vector3D.y * d;
        this.z = d * vector3D.z;
    }

    public Vector3D(double d, org.apache.commons.math.geometry.Vector3D vector3D, double d2, org.apache.commons.math.geometry.Vector3D vector3D2) {
        this.x = (vector3D.x * d) + (vector3D2.x * d2);
        this.y = (vector3D.y * d) + (vector3D2.y * d2);
        this.z = (d * vector3D.z) + (d2 * vector3D2.z);
    }

    public Vector3D(double d, org.apache.commons.math.geometry.Vector3D vector3D, double d2, org.apache.commons.math.geometry.Vector3D vector3D2, double d3, org.apache.commons.math.geometry.Vector3D vector3D3) {
        this.x = (vector3D.x * d) + (vector3D2.x * d2) + (vector3D3.x * d3);
        this.y = (vector3D.y * d) + (vector3D2.y * d2) + (vector3D3.y * d3);
        this.z = (d * vector3D.z) + (d2 * vector3D2.z) + (d3 * vector3D3.z);
    }

    public Vector3D(double d, org.apache.commons.math.geometry.Vector3D vector3D, double d2, org.apache.commons.math.geometry.Vector3D vector3D2, double d3, org.apache.commons.math.geometry.Vector3D vector3D3, double d4, org.apache.commons.math.geometry.Vector3D vector3D4) {
        this.x = (vector3D.x * d) + (vector3D2.x * d2) + (vector3D3.x * d3) + (vector3D4.x * d4);
        this.y = (vector3D.y * d) + (vector3D2.y * d2) + (vector3D3.y * d3) + (vector3D4.y * d4);
        this.z = (vector3D.z * d) + (vector3D2.z * d2) + (vector3D3.z * d3) + (vector3D4.z * d4);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getNorm1() {
        return org.apache.commons.math.util.FastMath.abs(this.x) + org.apache.commons.math.util.FastMath.abs(this.y) + org.apache.commons.math.util.FastMath.abs(this.z);
    }

    public double getNorm() {
        return org.apache.commons.math.util.FastMath.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
    }

    public double getNormSq() {
        return (this.x * this.x) + (this.y * this.y) + (this.z * this.z);
    }

    public double getNormInf() {
        return org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(this.x), org.apache.commons.math.util.FastMath.abs(this.y)), org.apache.commons.math.util.FastMath.abs(this.z));
    }

    public double getAlpha() {
        return org.apache.commons.math.util.FastMath.atan2(this.y, this.x);
    }

    public double getDelta() {
        return org.apache.commons.math.util.FastMath.asin(this.z / getNorm());
    }

    public org.apache.commons.math.geometry.Vector3D add(org.apache.commons.math.geometry.Vector3D vector3D) {
        return new org.apache.commons.math.geometry.Vector3D(this.x + vector3D.x, this.y + vector3D.y, this.z + vector3D.z);
    }

    public org.apache.commons.math.geometry.Vector3D add(double d, org.apache.commons.math.geometry.Vector3D vector3D) {
        return new org.apache.commons.math.geometry.Vector3D(this.x + (vector3D.x * d), this.y + (vector3D.y * d), this.z + (d * vector3D.z));
    }

    public org.apache.commons.math.geometry.Vector3D subtract(org.apache.commons.math.geometry.Vector3D vector3D) {
        return new org.apache.commons.math.geometry.Vector3D(this.x - vector3D.x, this.y - vector3D.y, this.z - vector3D.z);
    }

    public org.apache.commons.math.geometry.Vector3D subtract(double d, org.apache.commons.math.geometry.Vector3D vector3D) {
        return new org.apache.commons.math.geometry.Vector3D(this.x - (vector3D.x * d), this.y - (vector3D.y * d), this.z - (d * vector3D.z));
    }

    public org.apache.commons.math.geometry.Vector3D normalize() {
        double norm = getNorm();
        if (norm == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new java.lang.Object[0]);
        }
        return scalarMultiply(1.0d / norm);
    }

    public org.apache.commons.math.geometry.Vector3D orthogonal() {
        double norm = getNorm() * 0.6d;
        if (norm == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_NORM, new java.lang.Object[0]);
        }
        double d = -norm;
        if (this.x >= d && this.x <= norm) {
            double sqrt = 1.0d / org.apache.commons.math.util.FastMath.sqrt((this.y * this.y) + (this.z * this.z));
            return new org.apache.commons.math.geometry.Vector3D(0.0d, sqrt * this.z, (-sqrt) * this.y);
        }
        if (this.y >= d && this.y <= norm) {
            double sqrt2 = 1.0d / org.apache.commons.math.util.FastMath.sqrt((this.x * this.x) + (this.z * this.z));
            return new org.apache.commons.math.geometry.Vector3D((-sqrt2) * this.z, 0.0d, sqrt2 * this.x);
        }
        double sqrt3 = 1.0d / org.apache.commons.math.util.FastMath.sqrt((this.x * this.x) + (this.y * this.y));
        return new org.apache.commons.math.geometry.Vector3D(sqrt3 * this.y, (-sqrt3) * this.x, 0.0d);
    }

    public static double angle(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        double norm = vector3D.getNorm() * vector3D2.getNorm();
        if (norm == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_NORM, new java.lang.Object[0]);
        }
        double dotProduct = dotProduct(vector3D, vector3D2);
        double d = 0.9999d * norm;
        if (dotProduct < (-d) || dotProduct > d) {
            org.apache.commons.math.geometry.Vector3D crossProduct = crossProduct(vector3D, vector3D2);
            if (dotProduct >= 0.0d) {
                return org.apache.commons.math.util.FastMath.asin(crossProduct.getNorm() / norm);
            }
            return 3.141592653589793d - org.apache.commons.math.util.FastMath.asin(crossProduct.getNorm() / norm);
        }
        return org.apache.commons.math.util.FastMath.acos(dotProduct / norm);
    }

    public org.apache.commons.math.geometry.Vector3D negate() {
        return new org.apache.commons.math.geometry.Vector3D(-this.x, -this.y, -this.z);
    }

    public org.apache.commons.math.geometry.Vector3D scalarMultiply(double d) {
        return new org.apache.commons.math.geometry.Vector3D(d * this.x, this.y * d, this.z * d);
    }

    public boolean isNaN() {
        return java.lang.Double.isNaN(this.x) || java.lang.Double.isNaN(this.y) || java.lang.Double.isNaN(this.z);
    }

    public boolean isInfinite() {
        return !isNaN() && (java.lang.Double.isInfinite(this.x) || java.lang.Double.isInfinite(this.y) || java.lang.Double.isInfinite(this.z));
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.geometry.Vector3D)) {
            return false;
        }
        org.apache.commons.math.geometry.Vector3D vector3D = (org.apache.commons.math.geometry.Vector3D) obj;
        if (vector3D.isNaN()) {
            return isNaN();
        }
        return this.x == vector3D.x && this.y == vector3D.y && this.z == vector3D.z;
    }

    public int hashCode() {
        if (isNaN()) {
            return 8;
        }
        return ((org.apache.commons.math.util.MathUtils.hash(this.x) * 23) + (org.apache.commons.math.util.MathUtils.hash(this.y) * 19) + org.apache.commons.math.util.MathUtils.hash(this.z)) * 31;
    }

    public static double dotProduct(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        return (vector3D.x * vector3D2.x) + (vector3D.y * vector3D2.y) + (vector3D.z * vector3D2.z);
    }

    public static org.apache.commons.math.geometry.Vector3D crossProduct(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        return new org.apache.commons.math.geometry.Vector3D((vector3D.y * vector3D2.z) - (vector3D.z * vector3D2.y), (vector3D.z * vector3D2.x) - (vector3D.x * vector3D2.z), (vector3D.x * vector3D2.y) - (vector3D.y * vector3D2.x));
    }

    public static double distance1(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        return org.apache.commons.math.util.FastMath.abs(vector3D2.x - vector3D.x) + org.apache.commons.math.util.FastMath.abs(vector3D2.y - vector3D.y) + org.apache.commons.math.util.FastMath.abs(vector3D2.z - vector3D.z);
    }

    public static double distance(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        double d = vector3D2.x - vector3D.x;
        double d2 = vector3D2.y - vector3D.y;
        double d3 = vector3D2.z - vector3D.z;
        return org.apache.commons.math.util.FastMath.sqrt((d * d) + (d2 * d2) + (d3 * d3));
    }

    public static double distanceInf(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        double abs = org.apache.commons.math.util.FastMath.abs(vector3D2.x - vector3D.x);
        double abs2 = org.apache.commons.math.util.FastMath.abs(vector3D2.y - vector3D.y);
        return org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.max(abs, abs2), org.apache.commons.math.util.FastMath.abs(vector3D2.z - vector3D.z));
    }

    public static double distanceSq(org.apache.commons.math.geometry.Vector3D vector3D, org.apache.commons.math.geometry.Vector3D vector3D2) {
        double d = vector3D2.x - vector3D.x;
        double d2 = vector3D2.y - vector3D.y;
        double d3 = vector3D2.z - vector3D.z;
        return (d * d) + (d2 * d2) + (d3 * d3);
    }

    public java.lang.String toString() {
        return DEFAULT_FORMAT.format(this);
    }
}
