package org.apache.commons.math.optimization.direct;

/* loaded from: classes3.dex */
public class PowellOptimizer extends org.apache.commons.math.optimization.general.AbstractScalarDifferentiableOptimizer {
    public static final double DEFAULT_LS_ABSOLUTE_TOLERANCE = 1.0E-11d;
    public static final double DEFAULT_LS_RELATIVE_TOLERANCE = 1.0E-7d;
    private final org.apache.commons.math.optimization.direct.PowellOptimizer.LineSearch line;

    public PowellOptimizer() {
        this(1.0E-7d, 1.0E-11d);
    }

    public PowellOptimizer(double d) {
        this(d, 1.0E-11d);
    }

    public PowellOptimizer(double d, double d2) {
        this.line = new org.apache.commons.math.optimization.direct.PowellOptimizer.LineSearch(d, d2);
    }

    @Override // org.apache.commons.math.optimization.general.AbstractScalarDifferentiableOptimizer
    protected org.apache.commons.math.optimization.RealPointValuePair doOptimize() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        double d;
        org.apache.commons.math.optimization.RealPointValuePair realPointValuePair;
        org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2;
        int i;
        boolean z;
        double[] dArr = (double[]) this.point.clone();
        int length = dArr.length;
        int i2 = 0;
        double[][] dArr2 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length);
        for (int i3 = 0; i3 < length; i3++) {
            dArr2[i3][i3] = 1.0d;
        }
        double computeObjectiveValue = computeObjectiveValue(dArr);
        double[] dArr3 = (double[]) dArr.clone();
        while (true) {
            incrementIterationsCounter();
            int i4 = i2;
            int i5 = i4;
            d = computeObjectiveValue;
            double d2 = 0.0d;
            while (i4 < length) {
                double[] copyOf = copyOf(dArr2[i4], length);
                this.line.search(dArr, copyOf);
                double valueAtOptimum = this.line.getValueAtOptimum();
                int i6 = length;
                double[][] dArr4 = dArr2;
                dArr = newPointAndDirection(dArr, copyOf, this.line.getOptimum())[0];
                double d3 = d - valueAtOptimum;
                if (d3 > d2) {
                    i5 = i4;
                    d2 = d3;
                }
                i4++;
                d = valueAtOptimum;
                length = i6;
                dArr2 = dArr4;
            }
            int i7 = length;
            double[][] dArr5 = dArr2;
            realPointValuePair = new org.apache.commons.math.optimization.RealPointValuePair(dArr3, computeObjectiveValue);
            realPointValuePair2 = new org.apache.commons.math.optimization.RealPointValuePair(dArr, d);
            if (getConvergenceChecker().converged(getIterations(), realPointValuePair, realPointValuePair2)) {
                break;
            }
            length = i7;
            double[] dArr6 = new double[length];
            double[] dArr7 = new double[length];
            for (int i8 = 0; i8 < length; i8++) {
                dArr6[i8] = dArr[i8] - dArr3[i8];
                dArr7[i8] = (dArr[i8] * 2.0d) - dArr3[i8];
            }
            dArr3 = (double[]) dArr.clone();
            double computeObjectiveValue2 = computeObjectiveValue(dArr7);
            if (computeObjectiveValue <= computeObjectiveValue2) {
                i = 0;
                z = true;
            } else {
                double d4 = (computeObjectiveValue - d) - d2;
                double d5 = ((computeObjectiveValue + computeObjectiveValue2) - (d * 2.0d)) * 2.0d * d4 * d4;
                double d6 = computeObjectiveValue - computeObjectiveValue2;
                if (d5 - ((d2 * d6) * d6) >= 0.0d) {
                    i = 0;
                    z = true;
                } else {
                    this.line.search(dArr, dArr6);
                    double valueAtOptimum2 = this.line.getValueAtOptimum();
                    double[][] newPointAndDirection = newPointAndDirection(dArr, dArr6, this.line.getOptimum());
                    i = 0;
                    double[] dArr8 = newPointAndDirection[0];
                    int i9 = length - 1;
                    dArr5[i5] = dArr5[i9];
                    z = true;
                    dArr5[i9] = newPointAndDirection[1];
                    dArr = dArr8;
                    computeObjectiveValue = valueAtOptimum2;
                    i2 = i;
                    dArr2 = dArr5;
                }
            }
            computeObjectiveValue = d;
            i2 = i;
            dArr2 = dArr5;
        }
        return this.goal == org.apache.commons.math.optimization.GoalType.MINIMIZE ? d < computeObjectiveValue ? realPointValuePair2 : realPointValuePair : d > computeObjectiveValue ? realPointValuePair2 : realPointValuePair;
    }

    private double[][] newPointAndDirection(double[] dArr, double[] dArr2, double d) {
        int length = dArr.length;
        double[][] dArr3 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, 2, length);
        double[] dArr4 = dArr3[0];
        double[] dArr5 = dArr3[1];
        for (int i = 0; i < length; i++) {
            dArr5[i] = dArr2[i] * d;
            dArr4[i] = dArr[i] + dArr5[i];
        }
        return dArr3;
    }

    private class LineSearch {
        private final org.apache.commons.math.optimization.univariate.AbstractUnivariateRealOptimizer optim = new org.apache.commons.math.optimization.univariate.BrentOptimizer();
        private final org.apache.commons.math.optimization.univariate.BracketFinder bracket = new org.apache.commons.math.optimization.univariate.BracketFinder();
        private double optimum = Double.NaN;
        private double valueAtOptimum = Double.NaN;

        public LineSearch(double d, double d2) {
            this.optim.setRelativeAccuracy(d);
            this.optim.setAbsoluteAccuracy(d2);
        }

        public void search(final double[] dArr, final double[] dArr2) throws org.apache.commons.math.optimization.OptimizationException, org.apache.commons.math.FunctionEvaluationException {
            this.optimum = Double.NaN;
            this.valueAtOptimum = Double.NaN;
            try {
                final int length = dArr.length;
                org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction = new org.apache.commons.math.analysis.UnivariateRealFunction() { // from class: org.apache.commons.math.optimization.direct.PowellOptimizer.LineSearch.1
                    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
                    public double value(double d) throws org.apache.commons.math.FunctionEvaluationException {
                        double[] dArr3 = new double[length];
                        for (int i = 0; i < length; i++) {
                            dArr3[i] = dArr[i] + (dArr2[i] * d);
                        }
                        return org.apache.commons.math.optimization.direct.PowellOptimizer.this.computeObjectiveValue(dArr3);
                    }
                };
                this.bracket.search(univariateRealFunction, ((org.apache.commons.math.optimization.general.AbstractScalarDifferentiableOptimizer) org.apache.commons.math.optimization.direct.PowellOptimizer.this).goal, 0.0d, 1.0d);
                this.optimum = this.optim.optimize(univariateRealFunction, ((org.apache.commons.math.optimization.general.AbstractScalarDifferentiableOptimizer) org.apache.commons.math.optimization.direct.PowellOptimizer.this).goal, this.bracket.getLo(), this.bracket.getHi(), this.bracket.getMid());
                this.valueAtOptimum = this.optim.getFunctionValue();
            } catch (org.apache.commons.math.MaxIterationsExceededException e) {
                throw new org.apache.commons.math.optimization.OptimizationException(e);
            }
        }

        public double getOptimum() {
            return this.optimum;
        }

        public double getValueAtOptimum() {
            return this.valueAtOptimum;
        }
    }

    private double[] copyOf(double[] dArr, int i) {
        double[] dArr2 = new double[i];
        java.lang.System.arraycopy(dArr, 0, dArr2, 0, java.lang.Math.min(dArr.length, i));
        return dArr2;
    }
}
