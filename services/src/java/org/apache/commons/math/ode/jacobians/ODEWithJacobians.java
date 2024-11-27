package org.apache.commons.math.ode.jacobians;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface ODEWithJacobians extends org.apache.commons.math.ode.FirstOrderDifferentialEquations {
    void computeJacobians(double d, double[] dArr, double[] dArr2, double[][] dArr3, double[][] dArr4) throws org.apache.commons.math.ode.DerivativeException;

    int getParametersDimension();
}
