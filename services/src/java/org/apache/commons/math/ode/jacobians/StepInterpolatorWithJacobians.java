package org.apache.commons.math.ode.jacobians;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface StepInterpolatorWithJacobians extends java.io.Externalizable {
    org.apache.commons.math.ode.jacobians.StepInterpolatorWithJacobians copy() throws org.apache.commons.math.ode.DerivativeException;

    double getCurrentTime();

    double[][] getInterpolatedDyDp() throws org.apache.commons.math.ode.DerivativeException;

    double[][] getInterpolatedDyDpDot() throws org.apache.commons.math.ode.DerivativeException;

    double[][] getInterpolatedDyDy0() throws org.apache.commons.math.ode.DerivativeException;

    double[][] getInterpolatedDyDy0Dot() throws org.apache.commons.math.ode.DerivativeException;

    double getInterpolatedTime();

    double[] getInterpolatedY() throws org.apache.commons.math.ode.DerivativeException;

    double[] getInterpolatedYDot() throws org.apache.commons.math.ode.DerivativeException;

    double getPreviousTime();

    boolean isForward();

    void setInterpolatedTime(double d);
}
