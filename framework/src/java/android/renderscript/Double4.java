package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Double4 {
    public double w;
    public double x;
    public double y;
    public double z;

    public Double4() {
    }

    public Double4(android.renderscript.Double4 double4) {
        this.x = double4.x;
        this.y = double4.y;
        this.z = double4.z;
        this.w = double4.w;
    }

    public Double4(double d, double d2, double d3, double d4) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.w = d4;
    }

    public static android.renderscript.Double4 add(android.renderscript.Double4 double4, android.renderscript.Double4 double42) {
        android.renderscript.Double4 double43 = new android.renderscript.Double4();
        double43.x = double4.x + double42.x;
        double43.y = double4.y + double42.y;
        double43.z = double4.z + double42.z;
        double43.w = double4.w + double42.w;
        return double43;
    }

    public void add(android.renderscript.Double4 double4) {
        this.x += double4.x;
        this.y += double4.y;
        this.z += double4.z;
        this.w += double4.w;
    }

    public void add(double d) {
        this.x += d;
        this.y += d;
        this.z += d;
        this.w += d;
    }

    public static android.renderscript.Double4 add(android.renderscript.Double4 double4, double d) {
        android.renderscript.Double4 double42 = new android.renderscript.Double4();
        double42.x = double4.x + d;
        double42.y = double4.y + d;
        double42.z = double4.z + d;
        double42.w = double4.w + d;
        return double42;
    }

    public void sub(android.renderscript.Double4 double4) {
        this.x -= double4.x;
        this.y -= double4.y;
        this.z -= double4.z;
        this.w -= double4.w;
    }

    public void sub(double d) {
        this.x -= d;
        this.y -= d;
        this.z -= d;
        this.w -= d;
    }

    public static android.renderscript.Double4 sub(android.renderscript.Double4 double4, double d) {
        android.renderscript.Double4 double42 = new android.renderscript.Double4();
        double42.x = double4.x - d;
        double42.y = double4.y - d;
        double42.z = double4.z - d;
        double42.w = double4.w - d;
        return double42;
    }

    public static android.renderscript.Double4 sub(android.renderscript.Double4 double4, android.renderscript.Double4 double42) {
        android.renderscript.Double4 double43 = new android.renderscript.Double4();
        double43.x = double4.x - double42.x;
        double43.y = double4.y - double42.y;
        double43.z = double4.z - double42.z;
        double43.w = double4.w - double42.w;
        return double43;
    }

    public void mul(android.renderscript.Double4 double4) {
        this.x *= double4.x;
        this.y *= double4.y;
        this.z *= double4.z;
        this.w *= double4.w;
    }

    public void mul(double d) {
        this.x *= d;
        this.y *= d;
        this.z *= d;
        this.w *= d;
    }

    public static android.renderscript.Double4 mul(android.renderscript.Double4 double4, android.renderscript.Double4 double42) {
        android.renderscript.Double4 double43 = new android.renderscript.Double4();
        double43.x = double4.x * double42.x;
        double43.y = double4.y * double42.y;
        double43.z = double4.z * double42.z;
        double43.w = double4.w * double42.w;
        return double43;
    }

    public static android.renderscript.Double4 mul(android.renderscript.Double4 double4, double d) {
        android.renderscript.Double4 double42 = new android.renderscript.Double4();
        double42.x = double4.x * d;
        double42.y = double4.y * d;
        double42.z = double4.z * d;
        double42.w = double4.w * d;
        return double42;
    }

    public void div(android.renderscript.Double4 double4) {
        this.x /= double4.x;
        this.y /= double4.y;
        this.z /= double4.z;
        this.w /= double4.w;
    }

    public void div(double d) {
        this.x /= d;
        this.y /= d;
        this.z /= d;
        this.w /= d;
    }

    public static android.renderscript.Double4 div(android.renderscript.Double4 double4, double d) {
        android.renderscript.Double4 double42 = new android.renderscript.Double4();
        double42.x = double4.x / d;
        double42.y = double4.y / d;
        double42.z = double4.z / d;
        double42.w = double4.w / d;
        return double42;
    }

    public static android.renderscript.Double4 div(android.renderscript.Double4 double4, android.renderscript.Double4 double42) {
        android.renderscript.Double4 double43 = new android.renderscript.Double4();
        double43.x = double4.x / double42.x;
        double43.y = double4.y / double42.y;
        double43.z = double4.z / double42.z;
        double43.w = double4.w / double42.w;
        return double43;
    }

    public double dotProduct(android.renderscript.Double4 double4) {
        return (this.x * double4.x) + (this.y * double4.y) + (this.z * double4.z) + (this.w * double4.w);
    }

    public static double dotProduct(android.renderscript.Double4 double4, android.renderscript.Double4 double42) {
        return (double42.x * double4.x) + (double42.y * double4.y) + (double42.z * double4.z) + (double42.w * double4.w);
    }

    public void addMultiple(android.renderscript.Double4 double4, double d) {
        this.x += double4.x * d;
        this.y += double4.y * d;
        this.z += double4.z * d;
        this.w += double4.w * d;
    }

    public void set(android.renderscript.Double4 double4) {
        this.x = double4.x;
        this.y = double4.y;
        this.z = double4.z;
        this.w = double4.w;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public int length() {
        return 4;
    }

    public double elementSum() {
        return this.x + this.y + this.z + this.w;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            case 2:
                return this.z;
            case 3:
                return this.w;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setAt(int i, double d) {
        switch (i) {
            case 0:
                this.x = d;
                return;
            case 1:
                this.y = d;
                return;
            case 2:
                this.z = d;
                return;
            case 3:
                this.w = d;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void addAt(int i, double d) {
        switch (i) {
            case 0:
                this.x += d;
                return;
            case 1:
                this.y += d;
                return;
            case 2:
                this.z += d;
                return;
            case 3:
                this.w += d;
                return;
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double d, double d2, double d3, double d4) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.w = d4;
    }

    public void copyTo(double[] dArr, int i) {
        dArr[i] = this.x;
        dArr[i + 1] = this.y;
        dArr[i + 2] = this.z;
        dArr[i + 3] = this.w;
    }
}
