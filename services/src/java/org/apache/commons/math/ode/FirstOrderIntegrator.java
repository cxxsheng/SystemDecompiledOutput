package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public interface FirstOrderIntegrator extends org.apache.commons.math.ode.ODEIntegrator {
    double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException;
}
