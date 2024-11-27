package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class CurveFitter {
    private final java.util.List<org.apache.commons.math.optimization.fitting.WeightedObservedPoint> observations = new java.util.ArrayList();
    private final org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer optimizer;

    public CurveFitter(org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer differentiableMultivariateVectorialOptimizer) {
        this.optimizer = differentiableMultivariateVectorialOptimizer;
    }

    public void addObservedPoint(double d, double d2) {
        addObservedPoint(1.0d, d, d2);
    }

    public void addObservedPoint(double d, double d2, double d3) {
        this.observations.add(new org.apache.commons.math.optimization.fitting.WeightedObservedPoint(d, d2, d3));
    }

    public void addObservedPoint(org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint) {
        this.observations.add(weightedObservedPoint);
    }

    public org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] getObservations() {
        return (org.apache.commons.math.optimization.fitting.WeightedObservedPoint[]) this.observations.toArray(new org.apache.commons.math.optimization.fitting.WeightedObservedPoint[this.observations.size()]);
    }

    public void clearObservations() {
        this.observations.clear();
    }

    public double[] fit(org.apache.commons.math.optimization.fitting.ParametricRealFunction parametricRealFunction, double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        double[] dArr2 = new double[this.observations.size()];
        double[] dArr3 = new double[this.observations.size()];
        int i = 0;
        for (org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint : this.observations) {
            dArr2[i] = weightedObservedPoint.getY();
            dArr3[i] = weightedObservedPoint.getWeight();
            i++;
        }
        return this.optimizer.optimize(new org.apache.commons.math.optimization.fitting.CurveFitter.TheoreticalValuesFunction(parametricRealFunction), dArr2, dArr3, dArr).getPointRef();
    }

    private class TheoreticalValuesFunction implements org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction {
        private final org.apache.commons.math.optimization.fitting.ParametricRealFunction f;

        public TheoreticalValuesFunction(org.apache.commons.math.optimization.fitting.ParametricRealFunction parametricRealFunction) {
            this.f = parametricRealFunction;
        }

        @Override // org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction
        public org.apache.commons.math.analysis.MultivariateMatrixFunction jacobian() {
            return new org.apache.commons.math.analysis.MultivariateMatrixFunction() { // from class: org.apache.commons.math.optimization.fitting.CurveFitter.TheoreticalValuesFunction.1
                @Override // org.apache.commons.math.analysis.MultivariateMatrixFunction
                public double[][] value(double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
                    double[][] dArr2 = new double[org.apache.commons.math.optimization.fitting.CurveFitter.this.observations.size()][];
                    java.util.Iterator it = org.apache.commons.math.optimization.fitting.CurveFitter.this.observations.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        dArr2[i] = org.apache.commons.math.optimization.fitting.CurveFitter.TheoreticalValuesFunction.this.f.gradient(((org.apache.commons.math.optimization.fitting.WeightedObservedPoint) it.next()).getX(), dArr);
                        i++;
                    }
                    return dArr2;
                }
            };
        }

        @Override // org.apache.commons.math.analysis.MultivariateVectorialFunction
        public double[] value(double[] dArr) throws org.apache.commons.math.FunctionEvaluationException, java.lang.IllegalArgumentException {
            double[] dArr2 = new double[org.apache.commons.math.optimization.fitting.CurveFitter.this.observations.size()];
            java.util.Iterator it = org.apache.commons.math.optimization.fitting.CurveFitter.this.observations.iterator();
            int i = 0;
            while (it.hasNext()) {
                dArr2[i] = this.f.value(((org.apache.commons.math.optimization.fitting.WeightedObservedPoint) it.next()).getX(), dArr);
                i++;
            }
            return dArr2;
        }
    }
}
