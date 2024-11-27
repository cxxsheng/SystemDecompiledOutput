package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public class RealPointValuePair implements java.io.Serializable {
    private static final long serialVersionUID = 1003888396256744753L;
    private final double[] point;
    private final double value;

    public RealPointValuePair(double[] dArr, double d) {
        this.point = dArr == null ? null : (double[]) dArr.clone();
        this.value = d;
    }

    public RealPointValuePair(double[] dArr, double d, boolean z) {
        this.point = z ? dArr == null ? null : (double[]) dArr.clone() : dArr;
        this.value = d;
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

    public double getValue() {
        return this.value;
    }
}
