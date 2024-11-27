package org.apache.commons.math.optimization.general;

/* loaded from: classes3.dex */
public abstract class AbstractScalarDifferentiableOptimizer implements org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer {
    public static final int DEFAULT_MAX_ITERATIONS = 100;

    @java.lang.Deprecated
    protected org.apache.commons.math.optimization.RealConvergenceChecker checker;
    private int evaluations;
    private org.apache.commons.math.analysis.DifferentiableMultivariateRealFunction function;

    @java.lang.Deprecated
    protected org.apache.commons.math.optimization.GoalType goal;
    private org.apache.commons.math.analysis.MultivariateVectorialFunction gradient;
    private int gradientEvaluations;
    private int iterations;
    private int maxEvaluations;
    private int maxIterations;

    @java.lang.Deprecated
    protected double[] point;

    protected abstract org.apache.commons.math.optimization.RealPointValuePair doOptimize() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException;

    protected AbstractScalarDifferentiableOptimizer() {
        setConvergenceChecker(new org.apache.commons.math.optimization.SimpleScalarValueChecker());
        setMaxIterations(100);
        setMaxEvaluations(Integer.MAX_VALUE);
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public void setMaxIterations(int i) {
        this.maxIterations = i;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public int getMaxIterations() {
        return this.maxIterations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public int getIterations() {
        return this.iterations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public void setMaxEvaluations(int i) {
        this.maxEvaluations = i;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public int getEvaluations() {
        return this.evaluations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public int getGradientEvaluations() {
        return this.gradientEvaluations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public void setConvergenceChecker(org.apache.commons.math.optimization.RealConvergenceChecker realConvergenceChecker) {
        this.checker = realConvergenceChecker;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public org.apache.commons.math.optimization.RealConvergenceChecker getConvergenceChecker() {
        return this.checker;
    }

    protected void incrementIterationsCounter() throws org.apache.commons.math.optimization.OptimizationException {
        int i = this.iterations + 1;
        this.iterations = i;
        if (i > this.maxIterations) {
            throw new org.apache.commons.math.optimization.OptimizationException(new org.apache.commons.math.MaxIterationsExceededException(this.maxIterations));
        }
    }

    protected double[] computeObjectiveGradient(double[] dArr) throws org.apache.commons.math.FunctionEvaluationException {
        this.gradientEvaluations++;
        return this.gradient.value(dArr);
    }

    protected double computeObjectiveValue(double[] dArr) throws org.apache.commons.math.FunctionEvaluationException {
        int i = this.evaluations + 1;
        this.evaluations = i;
        if (i > this.maxEvaluations) {
            throw new org.apache.commons.math.FunctionEvaluationException(new org.apache.commons.math.MaxEvaluationsExceededException(this.maxEvaluations), dArr);
        }
        return this.function.value(dArr);
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateRealOptimizer
    public org.apache.commons.math.optimization.RealPointValuePair optimize(org.apache.commons.math.analysis.DifferentiableMultivariateRealFunction differentiableMultivariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        this.iterations = 0;
        this.evaluations = 0;
        this.gradientEvaluations = 0;
        this.function = differentiableMultivariateRealFunction;
        this.gradient = differentiableMultivariateRealFunction.gradient();
        this.goal = goalType;
        this.point = (double[]) dArr.clone();
        return doOptimize();
    }
}
