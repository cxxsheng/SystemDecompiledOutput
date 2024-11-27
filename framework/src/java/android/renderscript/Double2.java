package android.renderscript;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class Double2 {
    public double x;
    public double y;

    public Double2() {
    }

    public Double2(android.renderscript.Double2 double2) {
        this.x = double2.x;
        this.y = double2.y;
    }

    public Double2(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public static android.renderscript.Double2 add(android.renderscript.Double2 double2, android.renderscript.Double2 double22) {
        android.renderscript.Double2 double23 = new android.renderscript.Double2();
        double23.x = double2.x + double22.x;
        double23.y = double2.y + double22.y;
        return double23;
    }

    public void add(android.renderscript.Double2 double2) {
        this.x += double2.x;
        this.y += double2.y;
    }

    public void add(double d) {
        this.x += d;
        this.y += d;
    }

    public static android.renderscript.Double2 add(android.renderscript.Double2 double2, double d) {
        android.renderscript.Double2 double22 = new android.renderscript.Double2();
        double22.x = double2.x + d;
        double22.y = double2.y + d;
        return double22;
    }

    public void sub(android.renderscript.Double2 double2) {
        this.x -= double2.x;
        this.y -= double2.y;
    }

    public static android.renderscript.Double2 sub(android.renderscript.Double2 double2, android.renderscript.Double2 double22) {
        android.renderscript.Double2 double23 = new android.renderscript.Double2();
        double23.x = double2.x - double22.x;
        double23.y = double2.y - double22.y;
        return double23;
    }

    public void sub(double d) {
        this.x -= d;
        this.y -= d;
    }

    public static android.renderscript.Double2 sub(android.renderscript.Double2 double2, double d) {
        android.renderscript.Double2 double22 = new android.renderscript.Double2();
        double22.x = double2.x - d;
        double22.y = double2.y - d;
        return double22;
    }

    public void mul(android.renderscript.Double2 double2) {
        this.x *= double2.x;
        this.y *= double2.y;
    }

    public static android.renderscript.Double2 mul(android.renderscript.Double2 double2, android.renderscript.Double2 double22) {
        android.renderscript.Double2 double23 = new android.renderscript.Double2();
        double23.x = double2.x * double22.x;
        double23.y = double2.y * double22.y;
        return double23;
    }

    public void mul(double d) {
        this.x *= d;
        this.y *= d;
    }

    public static android.renderscript.Double2 mul(android.renderscript.Double2 double2, double d) {
        android.renderscript.Double2 double22 = new android.renderscript.Double2();
        double22.x = double2.x * d;
        double22.y = double2.y * d;
        return double22;
    }

    public void div(android.renderscript.Double2 double2) {
        this.x /= double2.x;
        this.y /= double2.y;
    }

    public static android.renderscript.Double2 div(android.renderscript.Double2 double2, android.renderscript.Double2 double22) {
        android.renderscript.Double2 double23 = new android.renderscript.Double2();
        double23.x = double2.x / double22.x;
        double23.y = double2.y / double22.y;
        return double23;
    }

    public void div(double d) {
        this.x /= d;
        this.y /= d;
    }

    public static android.renderscript.Double2 div(android.renderscript.Double2 double2, double d) {
        android.renderscript.Double2 double22 = new android.renderscript.Double2();
        double22.x = double2.x / d;
        double22.y = double2.y / d;
        return double22;
    }

    public double dotProduct(android.renderscript.Double2 double2) {
        return (this.x * double2.x) + (this.y * double2.y);
    }

    public static java.lang.Double dotProduct(android.renderscript.Double2 double2, android.renderscript.Double2 double22) {
        return java.lang.Double.valueOf((double22.x * double2.x) + (double22.y * double2.y));
    }

    public void addMultiple(android.renderscript.Double2 double2, double d) {
        this.x += double2.x * d;
        this.y += double2.y * d;
    }

    public void set(android.renderscript.Double2 double2) {
        this.x = double2.x;
        this.y = double2.y;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public int length() {
        return 2;
    }

    public double elementSum() {
        return this.x + this.y;
    }

    public double get(int i) {
        switch (i) {
            case 0:
                return this.x;
            case 1:
                return this.y;
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
            default:
                throw new java.lang.IndexOutOfBoundsException("Index: i");
        }
    }

    public void setValues(double d, double d2) {
        this.x = d;
        this.y = d2;
    }

    public void copyTo(double[] dArr, int i) {
        dArr[i] = this.x;
        dArr[i + 1] = this.y;
    }
}
