package org.apache.commons.math.ode.sampling;

/* loaded from: classes3.dex */
public interface StepHandler {
    void handleStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator, boolean z) throws org.apache.commons.math.ode.DerivativeException;

    boolean requiresDenseOutput();

    void reset();
}
