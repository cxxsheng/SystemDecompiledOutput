package org.apache.commons.math.ode.sampling;

/* loaded from: classes3.dex */
public interface FixedStepHandler {
    void handleStep(double d, double[] dArr, double[] dArr2, boolean z) throws org.apache.commons.math.ode.DerivativeException;
}
