package org.apache.commons.math.optimization.general;

/* loaded from: classes3.dex */
public class NonLinearConjugateGradientOptimizer extends org.apache.commons.math.optimization.general.AbstractScalarDifferentiableOptimizer {
    private final org.apache.commons.math.optimization.general.ConjugateGradientFormula updateFormula;
    private org.apache.commons.math.optimization.general.Preconditioner preconditioner = null;
    private org.apache.commons.math.analysis.solvers.UnivariateRealSolver solver = null;
    private double initialStep = 1.0d;

    public NonLinearConjugateGradientOptimizer(org.apache.commons.math.optimization.general.ConjugateGradientFormula conjugateGradientFormula) {
        this.updateFormula = conjugateGradientFormula;
    }

    public void setPreconditioner(org.apache.commons.math.optimization.general.Preconditioner preconditioner) {
        this.preconditioner = preconditioner;
    }

    public void setLineSearchSolver(org.apache.commons.math.analysis.solvers.UnivariateRealSolver univariateRealSolver) {
        this.solver = univariateRealSolver;
    }

    public void setInitialStep(double d) {
        if (d <= 0.0d) {
            this.initialStep = 1.0d;
        } else {
            this.initialStep = d;
        }
    }

    @Override // org.apache.commons.math.optimization.general.AbstractScalarDifferentiableOptimizer
    protected org.apache.commons.math.optimization.RealPointValuePair doOptimize() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        double d;
        try {
            org.apache.commons.math.optimization.RealPointValuePair realPointValuePair = null;
            byte b = 0;
            if (this.preconditioner == null) {
                this.preconditioner = new org.apache.commons.math.optimization.general.NonLinearConjugateGradientOptimizer.IdentityPreconditioner();
            }
            if (this.solver == null) {
                this.solver = new org.apache.commons.math.analysis.solvers.BrentSolver();
            }
            int length = this.point.length;
            double[] computeObjectiveGradient = computeObjectiveGradient(this.point);
            if (this.goal == org.apache.commons.math.optimization.GoalType.MINIMIZE) {
                for (int i = 0; i < length; i++) {
                    computeObjectiveGradient[i] = -computeObjectiveGradient[i];
                }
            }
            double[] precondition = this.preconditioner.precondition(this.point, computeObjectiveGradient);
            double[] dArr = (double[]) precondition.clone();
            double d2 = 0.0d;
            for (int i2 = 0; i2 < length; i2++) {
                d2 += computeObjectiveGradient[i2] * dArr[i2];
            }
            double d3 = d2;
            double[] dArr2 = precondition;
            double[] dArr3 = dArr;
            while (true) {
                org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2 = new org.apache.commons.math.optimization.RealPointValuePair(this.point, computeObjectiveValue(this.point));
                if (realPointValuePair != null && this.checker.converged(getIterations(), realPointValuePair, realPointValuePair2)) {
                    return realPointValuePair2;
                }
                incrementIterationsCounter();
                for (double d4 : dArr3) {
                }
                org.apache.commons.math.optimization.general.NonLinearConjugateGradientOptimizer.LineSearchFunction lineSearchFunction = new org.apache.commons.math.optimization.general.NonLinearConjugateGradientOptimizer.LineSearchFunction(dArr3);
                double solve = this.solver.solve(lineSearchFunction, 0.0d, findUpperBound(lineSearchFunction, 0.0d, this.initialStep));
                for (int i3 = 0; i3 < this.point.length; i3++) {
                    double[] dArr4 = this.point;
                    dArr4[i3] = dArr4[i3] + (dArr3[i3] * solve);
                }
                double[] computeObjectiveGradient2 = computeObjectiveGradient(this.point);
                if (this.goal == org.apache.commons.math.optimization.GoalType.MINIMIZE) {
                    for (int i4 = 0; i4 < length; i4++) {
                        computeObjectiveGradient2[i4] = -computeObjectiveGradient2[i4];
                    }
                }
                double[] precondition2 = this.preconditioner.precondition(this.point, computeObjectiveGradient2);
                double d5 = 0.0d;
                for (int i5 = 0; i5 < length; i5++) {
                    d5 += computeObjectiveGradient2[i5] * precondition2[i5];
                }
                if (this.updateFormula == org.apache.commons.math.optimization.general.ConjugateGradientFormula.FLETCHER_REEVES) {
                    d = d5 / d3;
                } else {
                    double d6 = 0.0d;
                    for (int i6 = 0; i6 < computeObjectiveGradient2.length; i6++) {
                        d6 += computeObjectiveGradient2[i6] * dArr2[i6];
                    }
                    d = (d5 - d6) / d3;
                }
                if (getIterations() % length == 0 || d < 0.0d) {
                    dArr3 = (double[]) precondition2.clone();
                } else {
                    for (int i7 = 0; i7 < length; i7++) {
                        dArr3[i7] = precondition2[i7] + (dArr3[i7] * d);
                    }
                }
                dArr2 = precondition2;
                d3 = d5;
                realPointValuePair = realPointValuePair2;
            }
        } catch (org.apache.commons.math.ConvergenceException e) {
            throw new org.apache.commons.math.optimization.OptimizationException(e);
        }
    }

    private double findUpperBound(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d, double d2) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        double value = univariateRealFunction.value(d);
        while (d2 < Double.MAX_VALUE) {
            double d3 = d + d2;
            double value2 = univariateRealFunction.value(d3);
            if (value * value2 > 0.0d) {
                d2 *= org.apache.commons.math.util.FastMath.max(2.0d, value / value2);
            } else {
                return d3;
            }
        }
        throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_BRACKET_OPTIMUM_IN_LINE_SEARCH, new java.lang.Object[0]);
    }

    private static class IdentityPreconditioner implements org.apache.commons.math.optimization.general.Preconditioner {
        private IdentityPreconditioner() {
        }

        @Override // org.apache.commons.math.optimization.general.Preconditioner
        public double[] precondition(double[] dArr, double[] dArr2) {
            return (double[]) dArr2.clone();
        }
    }

    private class LineSearchFunction implements org.apache.commons.math.analysis.UnivariateRealFunction {
        private final double[] searchDirection;

        public LineSearchFunction(double[] dArr) {
            this.searchDirection = dArr;
        }

        @Override // org.apache.commons.math.analysis.UnivariateRealFunction
        public double value(double d) throws org.apache.commons.math.FunctionEvaluationException {
            double[] dArr = (double[]) org.apache.commons.math.optimization.general.NonLinearConjugateGradientOptimizer.this.point.clone();
            for (int i = 0; i < dArr.length; i++) {
                dArr[i] = dArr[i] + (this.searchDirection[i] * d);
            }
            double[] computeObjectiveGradient = org.apache.commons.math.optimization.general.NonLinearConjugateGradientOptimizer.this.computeObjectiveGradient(dArr);
            double d2 = 0.0d;
            for (int i2 = 0; i2 < computeObjectiveGradient.length; i2++) {
                d2 += computeObjectiveGradient[i2] * this.searchDirection[i2];
            }
            return d2;
        }
    }
}
