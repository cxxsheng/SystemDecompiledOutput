package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public interface EigenDecomposition {
    org.apache.commons.math.linear.RealMatrix getD();

    double getDeterminant();

    org.apache.commons.math.linear.RealVector getEigenvector(int i);

    double getImagEigenvalue(int i);

    double[] getImagEigenvalues();

    double getRealEigenvalue(int i);

    double[] getRealEigenvalues();

    org.apache.commons.math.linear.DecompositionSolver getSolver();

    org.apache.commons.math.linear.RealMatrix getV();

    org.apache.commons.math.linear.RealMatrix getVT();
}
