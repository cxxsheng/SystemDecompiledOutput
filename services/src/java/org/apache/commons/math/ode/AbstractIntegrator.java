package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public abstract class AbstractIntegrator implements org.apache.commons.math.ode.FirstOrderIntegrator {
    private transient org.apache.commons.math.ode.FirstOrderDifferentialEquations equations;
    private int evaluations;
    private java.util.Collection<org.apache.commons.math.ode.events.EventState> eventsStates;
    protected boolean isLastStep;
    private int maxEvaluations;
    private final java.lang.String name;
    protected boolean resetOccurred;
    private boolean statesInitialized;
    protected java.util.Collection<org.apache.commons.math.ode.sampling.StepHandler> stepHandlers;
    protected double stepSize;
    protected double stepStart;

    public AbstractIntegrator(java.lang.String str) {
        this.name = str;
        this.stepHandlers = new java.util.ArrayList();
        this.stepStart = Double.NaN;
        this.stepSize = Double.NaN;
        this.eventsStates = new java.util.ArrayList();
        this.statesInitialized = false;
        setMaxEvaluations(-1);
        resetEvaluations();
    }

    protected AbstractIntegrator() {
        this(null);
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public java.lang.String getName() {
        return this.name;
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public void addStepHandler(org.apache.commons.math.ode.sampling.StepHandler stepHandler) {
        this.stepHandlers.add(stepHandler);
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public java.util.Collection<org.apache.commons.math.ode.sampling.StepHandler> getStepHandlers() {
        return java.util.Collections.unmodifiableCollection(this.stepHandlers);
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public void clearStepHandlers() {
        this.stepHandlers.clear();
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public void addEventHandler(org.apache.commons.math.ode.events.EventHandler eventHandler, double d, double d2, int i) {
        this.eventsStates.add(new org.apache.commons.math.ode.events.EventState(eventHandler, d, d2, i));
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public java.util.Collection<org.apache.commons.math.ode.events.EventHandler> getEventHandlers() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<org.apache.commons.math.ode.events.EventState> it = this.eventsStates.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getEventHandler());
        }
        return java.util.Collections.unmodifiableCollection(arrayList);
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public void clearEventHandlers() {
        this.eventsStates.clear();
    }

    protected boolean requiresDenseOutput() {
        if (!this.eventsStates.isEmpty()) {
            return true;
        }
        java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it = this.stepHandlers.iterator();
        while (it.hasNext()) {
            if (it.next().requiresDenseOutput()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public double getCurrentStepStart() {
        return this.stepStart;
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public double getCurrentSignedStepsize() {
        return this.stepSize;
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public void setMaxEvaluations(int i) {
        if (i < 0) {
            i = Integer.MAX_VALUE;
        }
        this.maxEvaluations = i;
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override // org.apache.commons.math.ode.ODEIntegrator
    public int getEvaluations() {
        return this.evaluations;
    }

    protected void resetEvaluations() {
        this.evaluations = 0;
    }

    protected void setEquations(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations) {
        this.equations = firstOrderDifferentialEquations;
    }

    public void computeDerivatives(double d, double[] dArr, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException {
        int i = this.evaluations + 1;
        this.evaluations = i;
        if (i > this.maxEvaluations) {
            throw new org.apache.commons.math.ode.DerivativeException(new org.apache.commons.math.MaxEvaluationsExceededException(this.maxEvaluations));
        }
        this.equations.computeDerivatives(d, dArr, dArr2);
    }

    protected void setStateInitialized(boolean z) {
        this.statesInitialized = z;
    }

    protected double acceptStep(org.apache.commons.math.ode.sampling.AbstractStepInterpolator abstractStepInterpolator, double[] dArr, double[] dArr2, double d) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        try {
            double globalPreviousTime = abstractStepInterpolator.getGlobalPreviousTime();
            double globalCurrentTime = abstractStepInterpolator.getGlobalCurrentTime();
            this.resetOccurred = false;
            if (!this.statesInitialized) {
                java.util.Iterator<org.apache.commons.math.ode.events.EventState> it = this.eventsStates.iterator();
                while (it.hasNext()) {
                    it.next().reinitializeBegin(abstractStepInterpolator);
                }
                this.statesInitialized = true;
            }
            final int i = abstractStepInterpolator.isForward() ? 1 : -1;
            java.util.TreeSet treeSet = new java.util.TreeSet(new java.util.Comparator<org.apache.commons.math.ode.events.EventState>() { // from class: org.apache.commons.math.ode.AbstractIntegrator.1
                @Override // java.util.Comparator
                public int compare(org.apache.commons.math.ode.events.EventState eventState, org.apache.commons.math.ode.events.EventState eventState2) {
                    return i * java.lang.Double.compare(eventState.getEventTime(), eventState2.getEventTime());
                }
            });
            for (org.apache.commons.math.ode.events.EventState eventState : this.eventsStates) {
                if (eventState.evaluateStep(abstractStepInterpolator)) {
                    treeSet.add(eventState);
                }
            }
            while (!treeSet.isEmpty()) {
                java.util.Iterator it2 = treeSet.iterator();
                org.apache.commons.math.ode.events.EventState eventState2 = (org.apache.commons.math.ode.events.EventState) it2.next();
                it2.remove();
                double eventTime = eventState2.getEventTime();
                abstractStepInterpolator.setSoftPreviousTime(globalPreviousTime);
                abstractStepInterpolator.setSoftCurrentTime(eventTime);
                abstractStepInterpolator.setInterpolatedTime(eventTime);
                double[] interpolatedState = abstractStepInterpolator.getInterpolatedState();
                eventState2.stepAccepted(eventTime, interpolatedState);
                this.isLastStep = eventState2.stop();
                java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it3 = this.stepHandlers.iterator();
                while (it3.hasNext()) {
                    it3.next().handleStep(abstractStepInterpolator, this.isLastStep);
                }
                if (this.isLastStep) {
                    java.lang.System.arraycopy(interpolatedState, 0, dArr, 0, dArr.length);
                    return eventTime;
                }
                if (eventState2.reset(eventTime, interpolatedState)) {
                    java.lang.System.arraycopy(interpolatedState, 0, dArr, 0, dArr.length);
                    computeDerivatives(eventTime, dArr, dArr2);
                    this.resetOccurred = true;
                    return eventTime;
                }
                abstractStepInterpolator.setSoftPreviousTime(eventTime);
                abstractStepInterpolator.setSoftCurrentTime(globalCurrentTime);
                if (eventState2.evaluateStep(abstractStepInterpolator)) {
                    treeSet.add(eventState2);
                }
                globalPreviousTime = eventTime;
            }
            abstractStepInterpolator.setInterpolatedTime(globalCurrentTime);
            double[] interpolatedState2 = abstractStepInterpolator.getInterpolatedState();
            for (org.apache.commons.math.ode.events.EventState eventState3 : this.eventsStates) {
                eventState3.stepAccepted(globalCurrentTime, interpolatedState2);
                this.isLastStep = this.isLastStep || eventState3.stop();
            }
            this.isLastStep = this.isLastStep || org.apache.commons.math.util.MathUtils.equals(globalCurrentTime, d, 1);
            java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it4 = this.stepHandlers.iterator();
            while (it4.hasNext()) {
                it4.next().handleStep(abstractStepInterpolator, this.isLastStep);
            }
            return globalCurrentTime;
        } catch (org.apache.commons.math.ConvergenceException e) {
            throw new org.apache.commons.math.ode.IntegratorException(e);
        } catch (org.apache.commons.math.ode.events.EventException e2) {
            java.lang.Throwable cause = e2.getCause();
            if (cause != null && (cause instanceof org.apache.commons.math.ode.DerivativeException)) {
                throw ((org.apache.commons.math.ode.DerivativeException) cause);
            }
            throw new org.apache.commons.math.ode.IntegratorException(e2);
        }
    }

    protected void sanityChecks(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.IntegratorException {
        if (firstOrderDifferentialEquations.getDimension() != dArr.length) {
            throw new org.apache.commons.math.ode.IntegratorException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(firstOrderDifferentialEquations.getDimension()), java.lang.Integer.valueOf(dArr.length));
        }
        if (firstOrderDifferentialEquations.getDimension() != dArr2.length) {
            throw new org.apache.commons.math.ode.IntegratorException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(firstOrderDifferentialEquations.getDimension()), java.lang.Integer.valueOf(dArr2.length));
        }
        double d3 = d2 - d;
        if (org.apache.commons.math.util.FastMath.abs(d3) <= org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(d), org.apache.commons.math.util.FastMath.abs(d2)) * 1.0E-12d) {
            throw new org.apache.commons.math.ode.IntegratorException(org.apache.commons.math.exception.util.LocalizedFormats.TOO_SMALL_INTEGRATION_INTERVAL, java.lang.Double.valueOf(org.apache.commons.math.util.FastMath.abs(d3)));
        }
    }

    @java.lang.Deprecated
    protected org.apache.commons.math.ode.events.CombinedEventsManager addEndTimeChecker(double d, double d2, org.apache.commons.math.ode.events.CombinedEventsManager combinedEventsManager) {
        org.apache.commons.math.ode.events.CombinedEventsManager combinedEventsManager2 = new org.apache.commons.math.ode.events.CombinedEventsManager();
        for (org.apache.commons.math.ode.events.EventState eventState : combinedEventsManager.getEventsStates()) {
            combinedEventsManager2.addEventHandler(eventState.getEventHandler(), eventState.getMaxCheckInterval(), eventState.getConvergence(), eventState.getMaxIterationCount());
        }
        combinedEventsManager2.addEventHandler(new org.apache.commons.math.ode.AbstractIntegrator.EndTimeChecker(d2), Double.POSITIVE_INFINITY, org.apache.commons.math.util.FastMath.ulp(org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(d), org.apache.commons.math.util.FastMath.abs(d2))), 100);
        return combinedEventsManager2;
    }

    @java.lang.Deprecated
    private static class EndTimeChecker implements org.apache.commons.math.ode.events.EventHandler {
        private final double endTime;

        public EndTimeChecker(double d) {
            this.endTime = d;
        }

        @Override // org.apache.commons.math.ode.events.EventHandler
        public int eventOccurred(double d, double[] dArr, boolean z) {
            return 0;
        }

        @Override // org.apache.commons.math.ode.events.EventHandler
        public double g(double d, double[] dArr) {
            return d - this.endTime;
        }

        @Override // org.apache.commons.math.ode.events.EventHandler
        public void resetState(double d, double[] dArr) {
        }
    }
}
