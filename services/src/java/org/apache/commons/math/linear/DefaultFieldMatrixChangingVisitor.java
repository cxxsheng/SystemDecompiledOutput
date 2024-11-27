package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class DefaultFieldMatrixChangingVisitor<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> {
    private final T zero;

    public DefaultFieldMatrixChangingVisitor(T t) {
        this.zero = t;
    }

    @Override // org.apache.commons.math.linear.FieldMatrixChangingVisitor
    public void start(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    @Override // org.apache.commons.math.linear.FieldMatrixChangingVisitor
    public T visit(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixVisitorException {
        return t;
    }

    @Override // org.apache.commons.math.linear.FieldMatrixChangingVisitor
    public T end() {
        return this.zero;
    }
}
