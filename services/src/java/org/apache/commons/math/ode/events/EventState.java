package org.apache.commons.math.ode.events;

/* loaded from: classes3.dex */
public class EventState {
    private final double convergence;
    private boolean forward;
    private final org.apache.commons.math.ode.events.EventHandler handler;
    private final double maxCheckInterval;
    private final int maxIterationCount;
    private double t0 = Double.NaN;
    private double g0 = Double.NaN;
    private boolean g0Positive = true;
    private boolean pendingEvent = false;
    private double pendingEventTime = Double.NaN;
    private double previousEventTime = Double.NaN;
    private boolean increasing = true;
    private int nextAction = 3;

    public EventState(org.apache.commons.math.ode.events.EventHandler eventHandler, double d, double d2, int i) {
        this.handler = eventHandler;
        this.maxCheckInterval = d;
        this.convergence = org.apache.commons.math.util.FastMath.abs(d2);
        this.maxIterationCount = i;
    }

    public org.apache.commons.math.ode.events.EventHandler getEventHandler() {
        return this.handler;
    }

    public double getMaxCheckInterval() {
        return this.maxCheckInterval;
    }

    public double getConvergence() {
        return this.convergence;
    }

    public int getMaxIterationCount() {
        return this.maxIterationCount;
    }

    public void reinitializeBegin(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator) throws org.apache.commons.math.ode.events.EventException {
        try {
            this.t0 = stepInterpolator.getPreviousTime() + (stepInterpolator.isForward() ? getConvergence() : -getConvergence());
            stepInterpolator.setInterpolatedTime(this.t0);
            this.g0 = this.handler.g(this.t0, stepInterpolator.getInterpolatedState());
            if (this.g0 == 0.0d) {
                double previousTime = stepInterpolator.getPreviousTime();
                stepInterpolator.setInterpolatedTime(previousTime);
                this.g0Positive = this.handler.g(previousTime, stepInterpolator.getInterpolatedState()) <= 0.0d;
                return;
            }
            this.g0Positive = this.g0 >= 0.0d;
        } catch (org.apache.commons.math.ode.DerivativeException e) {
            throw new org.apache.commons.math.ode.events.EventException(e);
        }
    }

    public boolean evaluateStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.events.EventException, org.apache.commons.math.ConvergenceException {
        double d;
        int i;
        double solve;
        final org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator2 = stepInterpolator;
        try {
            this.forward = stepInterpolator.isForward();
            double currentTime = stepInterpolator.getCurrentTime();
            if (org.apache.commons.math.util.FastMath.abs(currentTime - this.t0) < this.convergence) {
                return false;
            }
            double d2 = this.forward ? this.t0 + this.convergence : this.t0 - this.convergence;
            double d3 = currentTime - d2;
            int max = org.apache.commons.math.util.FastMath.max(1, (int) org.apache.commons.math.util.FastMath.ceil(org.apache.commons.math.util.FastMath.abs(d3) / this.maxCheckInterval));
            double d4 = d3 / max;
            double d5 = this.t0;
            double d6 = this.g0;
            int i2 = 0;
            while (i2 < max) {
                i2++;
                double d7 = (i2 * d4) + d2;
                stepInterpolator2.setInterpolatedTime(d7);
                double g = this.handler.g(d7, stepInterpolator.getInterpolatedState());
                if (this.g0Positive ^ (g >= 0.0d)) {
                    this.increasing = g >= d6;
                    org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction = new org.apache.commons.math.analysis.UnivariateRealFunction() { // from class: org.apache.commons.math.ode.events.EventState.1
                        @Override // org.apache.commons.math.analysis.UnivariateRealFunction
                        public double value(double d8) {
                            try {
                                stepInterpolator2.setInterpolatedTime(d8);
                                return org.apache.commons.math.ode.events.EventState.this.handler.g(d8, stepInterpolator2.getInterpolatedState());
                            } catch (org.apache.commons.math.ode.DerivativeException e) {
                                throw new org.apache.commons.math.ode.events.EventState.EmbeddedDerivativeException(e);
                            } catch (org.apache.commons.math.ode.events.EventException e2) {
                                throw new org.apache.commons.math.ode.events.EventState.EmbeddedEventException(e2);
                            }
                        }
                    };
                    d = d4;
                    org.apache.commons.math.analysis.solvers.BrentSolver brentSolver = new org.apache.commons.math.analysis.solvers.BrentSolver(this.convergence);
                    if (d6 * g >= 0.0d) {
                        i = max;
                        double d8 = (this.forward ? 0.25d : -0.25d) * this.convergence;
                        for (int i3 = 0; i3 < 4 && d6 * g > 0.0d; i3++) {
                            d5 += d8;
                            try {
                                d6 = univariateRealFunction.value(d5);
                            } catch (org.apache.commons.math.FunctionEvaluationException e) {
                                throw new org.apache.commons.math.ode.DerivativeException(e);
                            }
                        }
                        if (d6 * g > 0.0d) {
                            throw new org.apache.commons.math.exception.MathInternalError();
                        }
                    } else {
                        i = max;
                    }
                    if (d5 <= d7) {
                        try {
                            solve = brentSolver.solve(this.maxIterationCount, univariateRealFunction, d5, d7);
                        } catch (org.apache.commons.math.FunctionEvaluationException e2) {
                            throw new org.apache.commons.math.ode.DerivativeException(e2);
                        }
                    } else {
                        solve = brentSolver.solve(this.maxIterationCount, univariateRealFunction, d7, d5);
                    }
                    if (!java.lang.Double.isNaN(this.previousEventTime)) {
                        boolean z = (org.apache.commons.math.util.FastMath.abs(solve - d5) <= this.convergence && org.apache.commons.math.util.FastMath.abs(solve - this.previousEventTime) <= this.convergence) ? true : true;
                    }
                    if (!java.lang.Double.isNaN(this.previousEventTime)) {
                        if (org.apache.commons.math.util.FastMath.abs(this.previousEventTime - solve) > this.convergence) {
                        }
                    }
                    this.pendingEventTime = solve;
                    this.pendingEvent = true;
                    return true;
                }
                d = d4;
                i = max;
                d5 = d7;
                d6 = g;
                d4 = d;
                max = i;
                stepInterpolator2 = stepInterpolator;
            }
            this.pendingEvent = false;
            this.pendingEventTime = Double.NaN;
            return false;
        } catch (org.apache.commons.math.ode.events.EventState.EmbeddedDerivativeException e3) {
            throw e3.getDerivativeException();
        } catch (org.apache.commons.math.ode.events.EventState.EmbeddedEventException e4) {
            throw e4.getEventException();
        }
    }

    public double getEventTime() {
        if (this.pendingEvent) {
            return this.pendingEventTime;
        }
        return Double.POSITIVE_INFINITY;
    }

    public void stepAccepted(double d, double[] dArr) throws org.apache.commons.math.ode.events.EventException {
        this.t0 = d;
        this.g0 = this.handler.g(d, dArr);
        if (this.pendingEvent && org.apache.commons.math.util.FastMath.abs(this.pendingEventTime - d) <= this.convergence) {
            this.previousEventTime = d;
            this.g0Positive = this.increasing;
            this.nextAction = this.handler.eventOccurred(d, dArr, true ^ (this.increasing ^ this.forward));
        } else {
            this.g0Positive = this.g0 >= 0.0d;
            this.nextAction = 3;
        }
    }

    public boolean stop() {
        return this.nextAction == 0;
    }

    public boolean reset(double d, double[] dArr) throws org.apache.commons.math.ode.events.EventException {
        if (!this.pendingEvent || org.apache.commons.math.util.FastMath.abs(this.pendingEventTime - d) > this.convergence) {
            return false;
        }
        if (this.nextAction == 1) {
            this.handler.resetState(d, dArr);
        }
        this.pendingEvent = false;
        this.pendingEventTime = Double.NaN;
        return this.nextAction == 1 || this.nextAction == 2;
    }

    private static class EmbeddedDerivativeException extends java.lang.RuntimeException {
        private static final long serialVersionUID = 3574188382434584610L;
        private final org.apache.commons.math.ode.DerivativeException derivativeException;

        public EmbeddedDerivativeException(org.apache.commons.math.ode.DerivativeException derivativeException) {
            this.derivativeException = derivativeException;
        }

        public org.apache.commons.math.ode.DerivativeException getDerivativeException() {
            return this.derivativeException;
        }
    }

    private static class EmbeddedEventException extends java.lang.RuntimeException {
        private static final long serialVersionUID = -1337749250090455474L;
        private final org.apache.commons.math.ode.events.EventException eventException;

        public EmbeddedEventException(org.apache.commons.math.ode.events.EventException eventException) {
            this.eventException = eventException;
        }

        public org.apache.commons.math.ode.events.EventException getEventException() {
            return this.eventException;
        }
    }
}
