package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public interface QRDecomposition {
    org.apache.commons.math.linear.RealMatrix getH();

    org.apache.commons.math.linear.RealMatrix getQ();

    org.apache.commons.math.linear.RealMatrix getQT();

    org.apache.commons.math.linear.RealMatrix getR();

    org.apache.commons.math.linear.DecompositionSolver getSolver();
}
