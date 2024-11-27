package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public interface DecompositionSolver {
    org.apache.commons.math.linear.RealMatrix getInverse() throws org.apache.commons.math.linear.InvalidMatrixException;

    boolean isNonSingular();

    org.apache.commons.math.linear.RealMatrix solve(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;

    org.apache.commons.math.linear.RealVector solve(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;

    double[] solve(double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException;
}
