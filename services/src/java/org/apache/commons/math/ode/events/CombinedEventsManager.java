package org.apache.commons.math.ode.events;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class CombinedEventsManager {
    private final java.util.List<org.apache.commons.math.ode.events.EventState> states = new java.util.ArrayList();
    private org.apache.commons.math.ode.events.EventState first = null;
    private boolean initialized = false;

    public void addEventHandler(org.apache.commons.math.ode.events.EventHandler eventHandler, double d, double d2, int i) {
        this.states.add(new org.apache.commons.math.ode.events.EventState(eventHandler, d, d2, i));
    }

    public java.util.Collection<org.apache.commons.math.ode.events.EventHandler> getEventsHandlers() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<org.apache.commons.math.ode.events.EventState> it = this.states.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getEventHandler());
        }
        return java.util.Collections.unmodifiableCollection(arrayList);
    }

    public void clearEventsHandlers() {
        this.states.clear();
    }

    public java.util.Collection<org.apache.commons.math.ode.events.EventState> getEventsStates() {
        return this.states;
    }

    public boolean isEmpty() {
        return this.states.isEmpty();
    }

    public boolean evaluateStep(org.apache.commons.math.ode.sampling.StepInterpolator stepInterpolator) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        try {
            this.first = null;
            if (this.states.isEmpty()) {
                return false;
            }
            if (!this.initialized) {
                java.util.Iterator<org.apache.commons.math.ode.events.EventState> it = this.states.iterator();
                while (it.hasNext()) {
                    it.next().reinitializeBegin(stepInterpolator);
                }
                this.initialized = true;
            }
            for (org.apache.commons.math.ode.events.EventState eventState : this.states) {
                if (eventState.evaluateStep(stepInterpolator)) {
                    if (this.first == null) {
                        this.first = eventState;
                    } else if (stepInterpolator.isForward()) {
                        if (eventState.getEventTime() < this.first.getEventTime()) {
                            this.first = eventState;
                        }
                    } else if (eventState.getEventTime() > this.first.getEventTime()) {
                        this.first = eventState;
                    }
                }
            }
            return this.first != null;
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

    public double getEventTime() {
        if (this.first == null) {
            return Double.NaN;
        }
        return this.first.getEventTime();
    }

    public void stepAccepted(double d, double[] dArr) throws org.apache.commons.math.ode.IntegratorException {
        try {
            java.util.Iterator<org.apache.commons.math.ode.events.EventState> it = this.states.iterator();
            while (it.hasNext()) {
                it.next().stepAccepted(d, dArr);
            }
        } catch (org.apache.commons.math.ode.events.EventException e) {
            throw new org.apache.commons.math.ode.IntegratorException(e);
        }
    }

    public boolean stop() {
        java.util.Iterator<org.apache.commons.math.ode.events.EventState> it = this.states.iterator();
        while (it.hasNext()) {
            if (it.next().stop()) {
                return true;
            }
        }
        return false;
    }

    public boolean reset(double d, double[] dArr) throws org.apache.commons.math.ode.IntegratorException {
        try {
            java.util.Iterator<org.apache.commons.math.ode.events.EventState> it = this.states.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (it.next().reset(d, dArr)) {
                    z = true;
                }
            }
            return z;
        } catch (org.apache.commons.math.ode.events.EventException e) {
            throw new org.apache.commons.math.ode.IntegratorException(e);
        }
    }
}
