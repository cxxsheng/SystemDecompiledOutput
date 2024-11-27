package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public interface CholeskyDecomposition {
    double getDeterminant();

    org.apache.commons.math.linear.RealMatrix getL();

    org.apache.commons.math.linear.RealMatrix getLT();

    org.apache.commons.math.linear.DecompositionSolver getSolver();
}
