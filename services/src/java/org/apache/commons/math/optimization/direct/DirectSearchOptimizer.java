package org.apache.commons.math.optimization.direct;

/* loaded from: classes3.dex */
public abstract class DirectSearchOptimizer implements org.apache.commons.math.optimization.MultivariateRealOptimizer {
    private org.apache.commons.math.optimization.RealConvergenceChecker checker;
    private int evaluations;
    private org.apache.commons.math.analysis.MultivariateRealFunction f;
    private int iterations;
    private int maxEvaluations;
    private int maxIterations;
    protected org.apache.commons.math.optimization.RealPointValuePair[] simplex;
    private double[][] startConfiguration;

    protected abstract void iterateSimplex(java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException;

    protected DirectSearchOptimizer() {
        setConvergenceChecker(new org.apache.commons.math.optimization.SimpleScalarValueChecker());
        setMaxIterations(Integer.MAX_VALUE);
        setMaxEvaluations(Integer.MAX_VALUE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:
    
        r1 = r6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setStartConfiguration(double[] dArr) throws java.lang.IllegalArgumentException {
        int length = dArr.length;
        this.startConfiguration = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length);
        int i = 0;
        while (i < length) {
            double[] dArr2 = this.startConfiguration[i];
            int i2 = 0;
            while (true) {
                int i3 = i + 1;
                if (i2 < i3) {
                    if (dArr[i2] == 0.0d) {
                        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.EQUAL_VERTICES_IN_SIMPLEX, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i2 + 1));
                    }
                    i2++;
                    java.lang.System.arraycopy(dArr, 0, dArr2, 0, i2);
                }
            }
        }
    }

    public void setStartConfiguration(double[][] dArr) throws java.lang.IllegalArgumentException {
        boolean z;
        int length = dArr.length - 1;
        if (length < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.SIMPLEX_NEED_ONE_POINT, new java.lang.Object[0]);
        }
        this.startConfiguration = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length);
        double[] dArr2 = dArr[0];
        for (int i = 0; i < length + 1; i++) {
            double[] dArr3 = dArr[i];
            if (dArr3.length != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr3.length), java.lang.Integer.valueOf(length));
            }
            for (int i2 = 0; i2 < i; i2++) {
                double[] dArr4 = dArr[i2];
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        if (dArr3[i3] == dArr4[i3]) {
                            i3++;
                        } else {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.EQUAL_VERTICES_IN_SIMPLEX, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                }
            }
            if (i > 0) {
                double[] dArr5 = this.startConfiguration[i - 1];
                for (int i4 = 0; i4 < length; i4++) {
                    dArr5[i4] = dArr3[i4] - dArr2[i4];
                }
            }
        }
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public void setMaxIterations(int i) {
        this.maxIterations = i;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getMaxIterations() {
        return this.maxIterations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public void setMaxEvaluations(int i) {
        this.maxEvaluations = i;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getIterations() {
        return this.iterations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public int getEvaluations() {
        return this.evaluations;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public void setConvergenceChecker(org.apache.commons.math.optimization.RealConvergenceChecker realConvergenceChecker) {
        this.checker = realConvergenceChecker;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public org.apache.commons.math.optimization.RealConvergenceChecker getConvergenceChecker() {
        return this.checker;
    }

    @Override // org.apache.commons.math.optimization.MultivariateRealOptimizer
    public org.apache.commons.math.optimization.RealPointValuePair optimize(org.apache.commons.math.analysis.MultivariateRealFunction multivariateRealFunction, final org.apache.commons.math.optimization.GoalType goalType, double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        if (this.startConfiguration == null || this.startConfiguration.length != dArr.length) {
            double[] dArr2 = new double[dArr.length];
            java.util.Arrays.fill(dArr2, 1.0d);
            setStartConfiguration(dArr2);
        }
        this.f = multivariateRealFunction;
        java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator = new java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair>() { // from class: org.apache.commons.math.optimization.direct.DirectSearchOptimizer.1
            @Override // java.util.Comparator
            public int compare(org.apache.commons.math.optimization.RealPointValuePair realPointValuePair, org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2) {
                double value = realPointValuePair.getValue();
                double value2 = realPointValuePair2.getValue();
                return goalType == org.apache.commons.math.optimization.GoalType.MINIMIZE ? java.lang.Double.compare(value, value2) : java.lang.Double.compare(value2, value);
            }
        };
        this.iterations = 0;
        this.evaluations = 0;
        buildSimplex(dArr);
        evaluateSimplex(comparator);
        org.apache.commons.math.optimization.RealPointValuePair[] realPointValuePairArr = new org.apache.commons.math.optimization.RealPointValuePair[this.simplex.length];
        while (true) {
            if (this.iterations > 0) {
                boolean z = true;
                for (int i = 0; i < this.simplex.length; i++) {
                    z &= this.checker.converged(this.iterations, realPointValuePairArr[i], this.simplex[i]);
                }
                if (z) {
                    return this.simplex[0];
                }
            }
            java.lang.System.arraycopy(this.simplex, 0, realPointValuePairArr, 0, this.simplex.length);
            iterateSimplex(comparator);
        }
    }

    protected void incrementIterationsCounter() throws org.apache.commons.math.optimization.OptimizationException {
        int i = this.iterations + 1;
        this.iterations = i;
        if (i > this.maxIterations) {
            throw new org.apache.commons.math.optimization.OptimizationException(new org.apache.commons.math.MaxIterationsExceededException(this.maxIterations));
        }
    }

    protected double evaluate(double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
        int i = this.evaluations + 1;
        this.evaluations = i;
        if (i > this.maxEvaluations) {
            throw new org.apache.commons.math.FunctionEvaluationException(new org.apache.commons.math.MaxEvaluationsExceededException(this.maxEvaluations), dArr);
        }
        return this.f.value(dArr);
    }

    private void buildSimplex(double[] dArr) throws java.lang.IllegalArgumentException {
        int length = dArr.length;
        if (length != this.startConfiguration.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(this.startConfiguration.length));
        }
        this.simplex = new org.apache.commons.math.optimization.RealPointValuePair[length + 1];
        this.simplex[0] = new org.apache.commons.math.optimization.RealPointValuePair(dArr, Double.NaN);
        int i = 0;
        while (i < length) {
            double[] dArr2 = this.startConfiguration[i];
            double[] dArr3 = new double[length];
            for (int i2 = 0; i2 < length; i2++) {
                dArr3[i2] = dArr[i2] + dArr2[i2];
            }
            i++;
            this.simplex[i] = new org.apache.commons.math.optimization.RealPointValuePair(dArr3, Double.NaN);
        }
    }

    protected void evaluateSimplex(java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        for (int i = 0; i < this.simplex.length; i++) {
            org.apache.commons.math.optimization.RealPointValuePair realPointValuePair = this.simplex[i];
            double[] pointRef = realPointValuePair.getPointRef();
            if (java.lang.Double.isNaN(realPointValuePair.getValue())) {
                this.simplex[i] = new org.apache.commons.math.optimization.RealPointValuePair(pointRef, evaluate(pointRef), false);
            }
        }
        java.util.Arrays.sort(this.simplex, comparator);
    }

    protected void replaceWorstPoint(org.apache.commons.math.optimization.RealPointValuePair realPointValuePair, java.util.Comparator<org.apache.commons.math.optimization.RealPointValuePair> comparator) {
        int length = this.simplex.length - 1;
        for (int i = 0; i < length; i++) {
            if (comparator.compare(this.simplex[i], realPointValuePair) > 0) {
                org.apache.commons.math.optimization.RealPointValuePair realPointValuePair2 = this.simplex[i];
                this.simplex[i] = realPointValuePair;
                realPointValuePair = realPointValuePair2;
            }
        }
        this.simplex[length] = realPointValuePair;
    }
}
