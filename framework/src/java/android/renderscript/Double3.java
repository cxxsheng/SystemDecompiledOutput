package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Double3 {
    public double x;
    public double y;
    public double z;

    public Double3() {
    }

    public Double3(android.renderscript.Double3 double3) {
        this.x = double3.x;
        this.y = double3.y;
        this.z = double3.z;
    }

    public Double3(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public static android.renderscript.Double3 add(android.renderscript.Double3 double3, android.renderscript.Double3 double32) {
        android.renderscript.Double3 double33 = new android.renderscript.Double3();
        double33.x = double3.x + double32.x;
        double33.y = double3.y + double32.y;
        double33.z = double3.z + double32.z;
        return double33;
    }

    public void add(android.renderscript.Double3 double3) {
        this.x += double3.x;
        this.y += double3.y;
        this.z += double3.z;
    }

    public void add(double d) {
        this.x += d;
        this.y += d;
        this.z += d;
    }

    public static android.renderscript.Double3 add(android.renderscript.Double3 double3, double d) {
        android.renderscript.Double3 double32 = new android.renderscript.Double3();
        double32.x = double3.x + d;
        double32.y = double3.y + d;
        double32.z = double3.z + d;
        return double32;
    }

    public void sub(android.renderscript.Double3 double3) {
        this.x -= double3.x;
        this.y -= double3.y;
        this.z -= double3.z;
    }

    public static android.renderscript.Double3 sub(android.renderscript.Double3 double3, android.renderscript.Double3 double32) {
        android.renderscript.Double3 double33 = new android.renderscript.Double3();
        double33.x = double3.x - double32.x;
        double33.y = double3.y - double32.y;
        double33.z = double3.z - double32.z;
        return double33;
    }

    public void sub(double d) {
        this.x -= d;
        this.y -= d;
        this.z -= d;
    }

    public static android.renderscript.Double3 sub(android.renderscript.Double3 double3, double d) {
        android.renderscript.Double3 double32 = new android.renderscript.Double3();
        double32.x = double3.x - d;
        double32.y = double3.y - d;
        double32.z = double3.z - d;
        return double32;
    }

    public void mul(android.renderscript.Double3 double3) {
        this.x *= double3.x;
        this.y *= double3.y;
        this.z *= double3.z;
    }

    public static android.renderscript.Double3 mul(android.renderscript.Double3 double3, android.renderscript.Double3 double32) {
        android.renderscript.Double3 double33 = new android.renderscript.Double3();
        double33.x = double3.x * double32.x;
        double33.y = double3.y * double32.y;
        double33.z = double3.z * double32.z;
        return double33;
    }

    public void mul(double d) {
        this.x *= d;
        this.y *= d;
        this.z *= d;
    }

    public static android.renderscript.Double3 mul(android.renderscript.Double3 double3, double d) {
        android.renderscript.Double3 double32 = new android.renderscript.Double3();
        double32.x = double3.x * d;
        double32.y = double3.y * d;
        double32.z = double3.z * d;
        return double32;
    }

    public void div(android.renderscript.Double3 double3) {
        this.x /= double3.x;
        this.y /= double3.y;
        this.z /= double3.z;
    }

    public static android.renderscript.Double3 div(android.renderscript.Double3 double3, android.renderscript.Double3 double32) {
        android.renderscript.Double3 double33 = new android.renderscript.Double3();
        double33.x = double3.x / double32.x;
        double33.y = double3.y / double32.y;
        double33.z = double3.z / double32.z;
        return double33;
    }

    public void div(double d) {
        this.x /= d;
        this.y /= d;
        this.z /= d;
    }

    public static android.renderscript.Double3 div(android.renderscript.Double3 double3, double d) {
        android.renderscript.Double3 double32 = new android.renderscript.Double3();
        double32.x = double3.x / d;
        double32.y = double3.y / d;
        double32.z = double3.z / d;
        return double32;
    }

    public double dotProduct(android.renderscript.Double3 double3) {
        return (this.x * double3.x) + (this.y * double3.y) + (this.z * double3.z);
    }

    public static double dotProduct(android.renderscript.Double3 double3, android.renderscript.Double3 double32) {
        return (double32.x * double3.x) + (double32.y * double3.y) + (double32.z * double3.z);
    }

    public void addMultiple(android.renderscript.Double3 double3, double d) {
        this.x += double3.x * d;
        this.y += double3.y * d;
        this.z += double3.z * d;
    }

    public void set(android.renderscript.Double3 double3) {
        this.x = double3.x;
        this.y = double3.y;
        this.z = double3.z;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public int length() {
        return 3;
    }

    public double elementSum() {
        return this.x + this.y + this.z;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            case 2:
                return this.z;
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public void copyTo(double[] dArr, int i) {
        dArr[i] = this.x;
        dArr[i + 1] = this.y;
        dArr[i + 2] = this.z;
    }
}
