package org.apache.commons.math.ode.jacobians;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface ParameterizedODE extends org.apache.commons.math.ode.FirstOrderDifferentialEquations {
    int getParametersDimension();

    void setParameter(int i, double d);
}
