package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public interface SecondOrderDifferentialEquations {
    void computeSecondDerivatives(double d, double[] dArr, double[] dArr2, double[] dArr3) throws org.apache.commons.math.ode.DerivativeException;

    int getDimension();
}
