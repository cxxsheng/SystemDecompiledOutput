package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class BisectionSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {
    @java.lang.Deprecated
    public BisectionSolver(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction) {
        super(univariateRealFunction, 100, 1.0E-6d);
    }

    public BisectionSolver() {
        super(100, 1.0E-6d);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(i, univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        clearResult();
        verifyInterval(d, d2);
        for (int i = 0; i < this.maximalIterationCount; i++) {
            double midpoint = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.midpoint(d, d2);
            if (univariateRealFunction.value(midpoint) * univariateRealFunction.value(d) > 0.0d) {
                d = midpoint;
            } else {
                d2 = midpoint;
            }
            if (org.apache.commons.math.util.FastMath.abs(d2 - d) <= this.absoluteAccuracy) {
                double midpoint2 = org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.midpoint(d, d2);
                setResult(midpoint2, i);
                return midpoint2;
            }
        }
        throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
    }
}
