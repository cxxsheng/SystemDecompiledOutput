package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class DefaultFieldMatrixPreservingVisitor<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> {
    private final T zero;

    public DefaultFieldMatrixPreservingVisitor(T t) {
        this.zero = t;
    }

    @Override // org.apache.commons.math.linear.FieldMatrixPreservingVisitor
    public void start(int i, int i2, int i3, int i4, int i5, int i6) {
    }

    @Override // org.apache.commons.math.linear.FieldMatrixPreservingVisitor
    public void visit(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixVisitorException {
    }

    @Override // org.apache.commons.math.linear.FieldMatrixPreservingVisitor
    public T end() {
        return this.zero;
    }
}
