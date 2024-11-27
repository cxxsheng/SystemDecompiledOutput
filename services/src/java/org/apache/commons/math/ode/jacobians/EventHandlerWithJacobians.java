package org.apache.commons.math.ode.jacobians;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface EventHandlerWithJacobians {
    public static final int CONTINUE = 3;
    public static final int RESET_DERIVATIVES = 2;
    public static final int RESET_STATE = 1;
    public static final int STOP = 0;

    int eventOccurred(double d, double[] dArr, double[][] dArr2, double[][] dArr3, boolean z) throws org.apache.commons.math.ode.events.EventException;

    double g(double d, double[] dArr, double[][] dArr2, double[][] dArr3) throws org.apache.commons.math.ode.events.EventException;

    void resetState(double d, double[] dArr, double[][] dArr2, double[][] dArr3) throws org.apache.commons.math.ode.events.EventException;
}
