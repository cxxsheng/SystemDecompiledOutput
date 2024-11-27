package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public interface FieldDecompositionSolver<T extends org.apache.commons.math.FieldElement<T>> {
    org.apache.commons.math.linear.FieldMatrix<T> getInverse() throws org.apache.commons.math.linear.InvalidMatrixException;

    boolean isNonSingular();

    org.apache.commons.math.linear.FieldMatrix<T> solve(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;

    org.apache.commons.math.linear.FieldVector<T> solve(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;

    T[] solve(T[] tArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;
}
