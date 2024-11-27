package org.apache.commons.math.analysis.integration;

/* loaded from: classes3.dex */
public interface UnivariateRealIntegrator extends org.apache.commons.math.ConvergingAlgorithm {
    int getMinimalIterationCount();

    double getResult() throws java.lang.IllegalStateException;

    @java.lang.Deprecated
    double integrate(double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException;

    double integrate(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.ConvergenceException, org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException;

    void resetMinimalIterationCount();

    void setMinimalIterationCount(int i);
}
