package org.apache.commons.math.ode.sampling;

/* loaded from: classes3.dex */
public class DummyStepHandler implements org.apache.commons.math.ode.sampling.StepHandler {
    private DummyStepHandler() {
    }

    public static org.apache.commons.math.ode.sampling.DummyStepHandler getInstance() {
        return org.apache.commons.math.ode.sampling.DummyStepHandler.LazyHolder.INSTANCE;
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public boolean requiresDenseOutput() {
        return false;
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public void reset() {
    }

    @Override // org.apache.commons.math.ode.sampling.StepHandler
    public void handleStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator, boolean z) {
    }

    private static class LazyHolder {
        private static final org.apache.commons.math.ode.sampling.DummyStepHandler INSTANCE = new org.apache.commons.math.ode.sampling.DummyStepHandler();

        private LazyHolder() {
        }
    }

    private java.lang.Object readResolve() {
        return org.apache.commons.math.ode.sampling.DummyStepHandler.LazyHolder.INSTANCE;
    }
}
