package org.apache.commons.math.optimization.direct;

/* loaded from: classes3.dex */
public class NelderMead extends org.apache.commons.math.optimization.direct.DirectSearchOptimizer {
    private final double gamma;
    private final double khi;
    private final double rho;
    private final double sigma;

    public NelderMead() {
        this.rho = 1.0d;
        this.khi = 2.0d;
        this.gamma = 0.5d;
        this.sigma = 0.5d;
    }

    public NelderMead(double d, double d2, double d3, double d4) {
        this.rho = d;
        this.khi = d2;
        this.gamma = d3;
        this.sigma = d4;
    }

    @Override // org.apache.commons.math.optimization.direct.DirectSearchOptimizer
    protected void iterateSimplex(java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        incrementIterationsCounter();
        int length = this.simplex.length - 1;
        org.apache.commons.math.optimization.RealPointValuePair realPointValuePair = this.simplex[0];
        org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2 = this.simplex[length - 1];
        org.apache.commons.math.optimization.RealPointValuePair realPointValuePair3 = this.simplex[length];
        double[] pointRef = realPointValuePair3.getPointRef();
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            double[] pointRef2 = this.simplex[i].getPointRef();
            for (int i2 = 0; i2 < length; i2++) {
                dArr[i2] = dArr[i2] + pointRef2[i2];
            }
        }
        double d = 1.0d / length;
        for (int i3 = 0; i3 < length; i3++) {
            dArr[i3] = dArr[i3] * d;
        }
        double[] dArr2 = new double[length];
        for (int i4 = 0; i4 < length; i4++) {
            dArr2[i4] = dArr[i4] + (this.rho * (dArr[i4] - pointRef[i4]));
        }
        org.apache.commons.math.optimization.RealPointValuePair realPointValuePair4 = new org.apache.commons.math.optimization.RealPointValuePair(dArr2, evaluate(dArr2), false);
        if (comparator.compare(realPointValuePair, realPointValuePair4) <= 0 && comparator.compare(realPointValuePair4, realPointValuePair2) < 0) {
            replaceWorstPoint(realPointValuePair4, comparator);
            return;
        }
        if (comparator.compare(realPointValuePair4, realPointValuePair) < 0) {
            double[] dArr3 = new double[length];
            for (int i5 = 0; i5 < length; i5++) {
                dArr3[i5] = dArr[i5] + (this.khi * (dArr2[i5] - dArr[i5]));
            }
            org.apache.commons.math.optimization.RealPointValuePair realPointValuePair5 = new org.apache.commons.math.optimization.RealPointValuePair(dArr3, evaluate(dArr3), false);
            if (comparator.compare(realPointValuePair5, realPointValuePair4) < 0) {
                replaceWorstPoint(realPointValuePair5, comparator);
                return;
            } else {
                replaceWorstPoint(realPointValuePair4, comparator);
                return;
            }
        }
        if (comparator.compare(realPointValuePair4, realPointValuePair3) < 0) {
            double[] dArr4 = new double[length];
            for (int i6 = 0; i6 < length; i6++) {
                dArr4[i6] = dArr[i6] + (this.gamma * (dArr2[i6] - dArr[i6]));
            }
            org.apache.commons.math.optimization.RealPointValuePair realPointValuePair6 = new org.apache.commons.math.optimization.RealPointValuePair(dArr4, evaluate(dArr4), false);
            if (comparator.compare(realPointValuePair6, realPointValuePair4) <= 0) {
                replaceWorstPoint(realPointValuePair6, comparator);
                return;
            }
        } else {
            double[] dArr5 = new double[length];
            for (int i7 = 0; i7 < length; i7++) {
                dArr5[i7] = dArr[i7] - (this.gamma * (dArr[i7] - pointRef[i7]));
            }
            org.apache.commons.math.optimization.RealPointValuePair realPointValuePair7 = new org.apache.commons.math.optimization.RealPointValuePair(dArr5, evaluate(dArr5), false);
            if (comparator.compare(realPointValuePair7, realPointValuePair3) < 0) {
                replaceWorstPoint(realPointValuePair7, comparator);
                return;
            }
        }
        double[] pointRef3 = this.simplex[0].getPointRef();
        for (int i8 = 1; i8 < this.simplex.length; i8++) {
            double[] point = this.simplex[i8].getPoint();
            for (int i9 = 0; i9 < length; i9++) {
                point[i9] = pointRef3[i9] + (this.sigma * (point[i9] - pointRef3[i9]));
            }
            this.simplex[i8] = new org.apache.commons.math.optimization.RealPointValuePair(point, Double.NaN, false);
        }
        evaluateSimplex(comparator);
    }
}
