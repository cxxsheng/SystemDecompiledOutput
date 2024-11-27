package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public interface DifferentiableMultivariateRealOptimizer {
    org.apache.commons.math.optimization.RealConvergenceChecker getConvergenceChecker();

    int getEvaluations();

    int getGradientEvaluations();

    int getIterations();

    int getMaxEvaluations();

    int getMaxIterations();

    org.apache.commons.math.optimization.RealPointValuePair optimize(org.apache.commons.math.analysis.DifferentiableMultivariateRealFunction differentiableMultivariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException;

    void setConvergenceChecker(org.apache.commons.math.optimization.RealConvergenceChecker realConvergenceChecker);

    void setMaxEvaluations(int i);

    void setMaxIterations(int i);
}
