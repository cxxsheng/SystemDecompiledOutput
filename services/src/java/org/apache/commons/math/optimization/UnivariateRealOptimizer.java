package org.apache.commons.math.optimization;

/* loaded from: classes3.dex */
public interface UnivariateRealOptimizer extends org.apache.commons.math.ConvergingAlgorithm {
    int getEvaluations();

    double getFunctionValue() throws org.apache.commons.math.FunctionEvaluationException;

    int getMaxEvaluations();

    double getResult();

    double optimize(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException;

    double optimize(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException;

    void setMaxEvaluations(int i);
}
