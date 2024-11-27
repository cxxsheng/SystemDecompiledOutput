package org.apache.commons.math.ode.jacobians;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface StepHandlerWithJacobians {
    void handleStep(org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians stepInterpolatorWithJacobians, boolean z) throws org.apache.commons.math.ode.DerivativeException;

    boolean requiresDenseOutput();

    void reset();
}
