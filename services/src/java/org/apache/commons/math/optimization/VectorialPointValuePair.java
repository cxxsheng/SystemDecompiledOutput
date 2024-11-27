package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class VectorialPointValuePair implements java.io.Serializable {
    private static final long serialVersionUID = 1003888396256744753L;
    private final double[] point;
    private final double[] value;

    public VectorialPointValuePair(double[] dArr, double[] dArr2) {
        this.point = dArr == null ? null : (double[]) dArr.clone();
        this.value = dArr2 != null ? (double[]) dArr2.clone() : null;
    }

    public VectorialPointValuePair(double[] dArr, double[] dArr2, boolean z) {
        this.point = z ? dArr == null ? null : (double[]) dArr.clone() : dArr;
        this.value = z ? dArr2 == null ? null : (double[]) dArr2.clone() : dArr2;
    }

    public double[] getPoint() {
        if (this.point == null) {
            return null;
        }
        return (double[]) this.point.clone();
    }

    public double[] getPointRef() {
        return this.point;
    }

    public double[] getValue() {
        if (this.value == null) {
            return null;
        }
        return (double[]) this.value.clone();
    }

    public double[] getValueRef() {
        return this.value;
    }
}
