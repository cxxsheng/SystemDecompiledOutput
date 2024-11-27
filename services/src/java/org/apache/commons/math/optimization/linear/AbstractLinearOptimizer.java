package org.apache.commons.math.optimization.linear;

/* loaded from: classes3.dex */
public abstract class AbstractLinearOptimizer implements org.apache.commons.math.optimization.linear.LinearOptimizer {
    public static final int DEFAULT_MAX_ITERATIONS = 100;
    protected org.apache.commons.math.optimization.linear.LinearObjectiveFunction function;
    protected org.apache.commons.math.optimization.GoalType goal;
    private int iterations;
    protected java.util.Collection<org.apache.commons.math.optimization.linear.LinearConstraint> linearConstraints;
    private int maxIterations;
    protected boolean nonNegative;

    protected abstract org.apache.commons.math.optimization.RealPointValuePair doOptimize() throws org.apache.commons.math.optimization.OptimizationException;

    protected AbstractLinearOptimizer() {
        setMaxIterations(100);
    }

    @Override // org.apache.commons.math.optimization.linear.LinearOptimizer
    public void setMaxIterations(int i) {
        this.maxIterations = i;
    }

    @Override // org.apache.commons.math.optimization.linear.LinearOptimizer
    public int getMaxIterations() {
        return this.maxIterations;
    }

    @Override // org.apache.commons.math.optimization.linear.LinearOptimizer
    public int getIterations() {
        return this.iterations;
    }

    protected void incrementIterationsCounter() throws org.apache.commons.math.optimization.OptimizationException {
        int i = this.iterations + 1;
        this.iterations = i;
        if (i > this.maxIterations) {
            throw new org.apache.commons.math.optimization.OptimizationException(new org.apache.commons.math.MaxIterationsExceededException(this.maxIterations));
        }
    }

    @Override // org.apache.commons.math.optimization.linear.LinearOptimizer
    public org.apache.commons.math.optimization.RealPointValuePair optimize(org.apache.commons.math.optimization.linear.LinearObjectiveFunction linearObjectiveFunction, java.util.Collection<org.apache.commons.math.optimization.linear.LinearConstraint> collection, org.apache.commons.math.optimization.GoalType goalType, boolean z) throws org.apache.commons.math.optimization.OptimizationException {
        this.function = linearObjectiveFunction;
        this.linearConstraints = collection;
        this.goal = goalType;
        this.nonNegative = z;
        this.iterations = 0;
        return doOptimize();
    }
}
