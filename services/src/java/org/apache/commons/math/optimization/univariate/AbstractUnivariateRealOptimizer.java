package org.apache.commons.math.optimization.univariate;

/* loaded from: classes3.dex */
public abstract class AbstractUnivariateRealOptimizer extends org.apache.commons.math.ConvergingAlgorithmImpl implements org.apache.commons.math.optimization.UnivariateRealOptimizer {
    private int evaluations;
    private org.apache.commons.math.analysis.UnivariateRealFunction function;
    protected double functionValue;
    private int maxEvaluations;
    private org.apache.commons.math.optimization.GoalType optimizationGoal;
    protected double result;
    protected boolean resultComputed;
    private double searchMax;
    private double searchMin;
    private double searchStart;

    @java.lang.Deprecated
    protected AbstractUnivariateRealOptimizer(int i, double d) {
        super(i, d);
        this.resultComputed = false;
        setMaxEvaluations(Integer.MAX_VALUE);
    }

    protected AbstractUnivariateRealOptimizer() {
    }

    @java.lang.Deprecated
    protected void checkResultComputed() {
        if (!this.resultComputed) {
            throw new org.apache.commons.math.exception.NoDataException();
        }
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double getResult() {
        if (!this.resultComputed) {
            throw new org.apache.commons.math.exception.NoDataException();
        }
        return this.result;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double getFunctionValue() throws org.apache.commons.math.FunctionEvaluationException {
        if (java.lang.Double.isNaN(this.functionValue)) {
            this.functionValue = this.function.value(getResult());
        }
        return this.functionValue;
    }

    @java.lang.Deprecated
    protected final void setResult(double d, double d2, int i) {
        this.result = d;
        this.functionValue = d2;
        this.iterationCount = i;
        this.resultComputed = true;
    }

    @java.lang.Deprecated
    protected final void clearResult() {
        this.resultComputed = false;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public void setMaxEvaluations(int i) {
        this.maxEvaluations = i;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public int getEvaluations() {
        return this.evaluations;
    }

    public org.apache.commons.math.optimization.GoalType getGoalType() {
        return this.optimizationGoal;
    }

    public double getMin() {
        return this.searchMin;
    }

    public double getMax() {
        return this.searchMax;
    }

    public double getStartValue() {
        return this.searchStart;
    }

    @java.lang.Deprecated
    protected double computeObjectiveValue(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d) throws org.apache.commons.math.FunctionEvaluationException {
        int i = this.evaluations + 1;
        this.evaluations = i;
        if (i > this.maxEvaluations) {
            throw new org.apache.commons.math.FunctionEvaluationException(new org.apache.commons.math.MaxEvaluationsExceededException(this.maxEvaluations), d);
        }
        return univariateRealFunction.value(d);
    }

    protected double computeObjectiveValue(double d) throws org.apache.commons.math.FunctionEvaluationException {
        int i = this.evaluations + 1;
        this.evaluations = i;
        if (i > this.maxEvaluations) {
            this.resultComputed = false;
            throw new org.apache.commons.math.FunctionEvaluationException(new org.apache.commons.math.MaxEvaluationsExceededException(this.maxEvaluations), d);
        }
        return this.function.value(d);
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double optimize(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        this.searchMin = d;
        this.searchMax = d2;
        this.searchStart = d3;
        this.optimizationGoal = goalType;
        this.function = univariateRealFunction;
        this.functionValue = Double.NaN;
        this.evaluations = 0;
        resetIterationsCounter();
        this.result = doOptimize();
        this.resultComputed = true;
        return this.result;
    }

    protected void setFunctionValue(double d) {
        this.functionValue = d;
    }

    @Override // org.apache.commons.math.optimization.UnivariateRealOptimizer
    public double optimize(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return optimize(univariateRealFunction, goalType, d, d2, d + ((d2 - d) * 0.5d));
    }

    protected double doOptimize() throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        throw new org.apache.commons.math.exception.MathUnsupportedOperationException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_OVERRIDEN, new java.lang.Object[0]);
    }
}
