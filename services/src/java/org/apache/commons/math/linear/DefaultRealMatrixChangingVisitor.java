package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class DefaultRealMatrixChangingVisitor implements org.apache.commons.math.linear.RealMatrixChangingVisitor {
    @Override // org.apache.commons.math.linear.RealMatrixChangingVisitor
    public void start(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    @Override // org.apache.commons.math.linear.RealMatrixChangingVisitor
    public double visit(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixVisitorException {
        return d;
    }

    @Override // org.apache.commons.math.linear.RealMatrixChangingVisitor
    public double end() {
        return 0.0d;
    }
}
