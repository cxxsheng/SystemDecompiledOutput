package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public interface UnivariateRealSolver extends org.apache.commons.math.ConvergingAlgorithm {
    double getFunctionValue();

    double getFunctionValueAccuracy();

    double getResult();

    void resetFunctionValueAccuracy();

    void setFunctionValueAccuracy(double d);

    @java.lang.Deprecated
    double solve(double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException;

    @java.lang.Deprecated
    double solve(double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException;

    @java.lang.Deprecated
    double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException;

    @java.lang.Deprecated
    double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException;
}
