package org.apache.commons.math.analysis.solvers;

/* loaded from: classes3.dex */
public class NewtonSolver extends org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl {
    @java.lang.Deprecated
    public NewtonSolver(org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction differentiableUnivariateRealFunction) {
        super(differentiableUnivariateRealFunction, 100, 1.0E-6d);
    }

    @java.lang.Deprecated
    public NewtonSolver() {
        super(100, 1.0E-6d);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(this.f, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return solve(univariateRealFunction, d, d2, org.apache.commons.math.analysis.solvers.UnivariateRealSolverUtils.midpoint(d, d2));
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolverImpl
    public double solve(int i, org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        setMaximalIterationCount(i);
        return solve(univariateRealFunction, d, d2, d3);
    }

    @Override // org.apache.commons.math.analysis.solvers.UnivariateRealSolver
    @java.lang.Deprecated
    public double solve(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2, double d3) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        try {
            org.apache.commons.math.analysis.UnivariateRealFunction derivative = ((org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction) univariateRealFunction).derivative();
            clearResult();
            verifySequence(d, d3, d2);
            double d4 = d3;
            int i = 0;
            while (i < this.maximalIterationCount) {
                double value = d4 - (univariateRealFunction.value(d4) / derivative.value(d4));
                if (org.apache.commons.math.util.FastMath.abs(value - d4) <= this.absoluteAccuracy) {
                    setResult(value, i);
                    return value;
                }
                i++;
                d4 = value;
            }
            throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
        } catch (java.lang.ClassCastException e) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FUNCTION_NOT_DIFFERENTIABLE, new java.lang.Object[0]);
        }
    }
}
