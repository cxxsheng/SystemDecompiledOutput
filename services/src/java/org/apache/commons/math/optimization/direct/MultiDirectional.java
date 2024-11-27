package org.apache.commons.math.optimization.direct;

/* loaded from: classes3.dex */
public class MultiDirectional extends org.apache.commons.math.optimization.direct.DirectSearchOptimizer {
    private final double gamma;
    private final double khi;

    public MultiDirectional() {
        this.khi = 2.0d;
        this.gamma = 0.5d;
    }

    public MultiDirectional(double d, double d2) {
        this.khi = d;
        this.gamma = d2;
    }

    @Override // org.apache.commons.math.optimization.direct.DirectSearchOptimizer
    protected void iterateSimplex(java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        boolean z;
        org.apache.commons.math.optimization.RealConvergenceChecker convergenceChecker = getConvergenceChecker();
        do {
            incrementIterationsCounter();
            org.apache.commons.math.optimization.RealPointValuePair[] realPointValuePairArr = this.simplex;
            org.apache.commons.math.optimization.RealPointValuePair realPointValuePair = realPointValuePairArr[0];
            org.apache.commons.math.optimization.RealPointValuePair evaluateNewSimplex = evaluateNewSimplex(realPointValuePairArr, 1.0d, comparator);
            if (comparator.compare(evaluateNewSimplex, realPointValuePair) < 0) {
                org.apache.commons.math.optimization.RealPointValuePair[] realPointValuePairArr2 = this.simplex;
                if (comparator.compare(evaluateNewSimplex, evaluateNewSimplex(realPointValuePairArr, this.khi, comparator)) <= 0) {
                    this.simplex = realPointValuePairArr2;
                    return;
                }
                return;
            }
            if (comparator.compare(evaluateNewSimplex(realPointValuePairArr, this.gamma, comparator), realPointValuePair) < 0) {
                return;
            }
            int iterations = getIterations();
            z = true;
            for (int i = 0; i < this.simplex.length; i++) {
                z &= convergenceChecker.converged(iterations, realPointValuePairArr[i], this.simplex[i]);
            }
        } while (!z);
    }

    private org.apache.commons.math.optimization.RealPointValuePair evaluateNewSimplex(org.apache.commons.math.optimization.RealPointValuePair[] realPointValuePairArr, double d, java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        double[] pointRef = realPointValuePairArr[0].getPointRef();
        int length = pointRef.length;
        this.simplex = new org.apache.commons.math.optimization.RealPointValuePair[length + 1];
        this.simplex[0] = realPointValuePairArr[0];
        for (int i = 1; i <= length; i++) {
            double[] pointRef2 = realPointValuePairArr[i].getPointRef();
            double[] dArr = new double[length];
            for (int i2 = 0; i2 < length; i2++) {
                dArr[i2] = pointRef[i2] + ((pointRef[i2] - pointRef2[i2]) * d);
            }
            this.simplex[i] = new org.apache.commons.math.optimization.RealPointValuePair(dArr, Double.NaN, false);
        }
        evaluateSimplex(comparator);
        return this.simplex[0];
    }
}
