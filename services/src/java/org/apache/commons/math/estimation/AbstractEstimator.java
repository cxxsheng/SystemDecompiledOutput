package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class AbstractEstimator implements org.apache.commons.math.estimation.Estimator {
    public static final int DEFAULT_MAX_COST_EVALUATIONS = 100;
    protected int cols;
    protected double cost;
    private int costEvaluations;
    protected double[] jacobian;
    private int jacobianEvaluations;
    private int maxCostEval;
    protected org.apache.commons.math.estimation.WeightedMeasurement[] measurements;
    protected org.apache.commons.math.estimation.EstimatedParameter[] parameters;
    protected double[] residuals;
    protected int rows;

    @Override // org.apache.commons.math.estimation.Estimator
    public abstract void estimate(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException;

    protected AbstractEstimator() {
        setMaxCostEval(100);
    }

    public final void setMaxCostEval(int i) {
        this.maxCostEval = i;
    }

    public final int getCostEvaluations() {
        return this.costEvaluations;
    }

    public final int getJacobianEvaluations() {
        return this.jacobianEvaluations;
    }

    protected void updateJacobian() {
        incrementJacobianEvaluationsCounter();
        java.util.Arrays.fill(this.jacobian, 0.0d);
        int i = 0;
        for (int i2 = 0; i2 < this.rows; i2++) {
            org.apache.commons.math.estimation.WeightedMeasurement weightedMeasurement = this.measurements[i2];
            double d = -org.apache.commons.math.util.FastMath.sqrt(weightedMeasurement.getWeight());
            int i3 = 0;
            while (i3 < this.cols) {
                this.jacobian[i] = weightedMeasurement.getPartial(this.parameters[i3]) * d;
                i3++;
                i++;
            }
        }
    }

    protected final void incrementJacobianEvaluationsCounter() {
        this.jacobianEvaluations++;
    }

    protected void updateResidualsAndCost() throws org.apache.commons.math.estimation.EstimationException {
        int i = this.costEvaluations + 1;
        this.costEvaluations = i;
        if (i > this.maxCostEval) {
            throw new org.apache.commons.math.estimation.EstimationException(org.apache.commons.math.exception.util.LocalizedFormats.MAX_EVALUATIONS_EXCEEDED, java.lang.Integer.valueOf(this.maxCostEval));
        }
        this.cost = 0.0d;
        for (int i2 = 0; i2 < this.rows; i2++) {
            org.apache.commons.math.estimation.WeightedMeasurement weightedMeasurement = this.measurements[i2];
            double residual = weightedMeasurement.getResidual();
            this.residuals[i2] = org.apache.commons.math.util.FastMath.sqrt(weightedMeasurement.getWeight()) * residual;
            this.cost += weightedMeasurement.getWeight() * residual * residual;
        }
        this.cost = org.apache.commons.math.util.FastMath.sqrt(this.cost);
    }

    @Override // org.apache.commons.math.estimation.Estimator
    public double getRMS(org.apache.commons.math.estimation.EstimationProblem estimationProblem) {
        org.apache.commons.math.estimation.WeightedMeasurement[] measurements = estimationProblem.getMeasurements();
        double d = 0.0d;
        for (int i = 0; i < measurements.length; i++) {
            double residual = measurements[i].getResidual();
            d += measurements[i].getWeight() * residual * residual;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d / measurements.length);
    }

    public double getChiSquare(org.apache.commons.math.estimation.EstimationProblem estimationProblem) {
        org.apache.commons.math.estimation.WeightedMeasurement[] measurements = estimationProblem.getMeasurements();
        double d = 0.0d;
        for (int i = 0; i < measurements.length; i++) {
            double residual = measurements[i].getResidual();
            d += (residual * residual) / measurements[i].getWeight();
        }
        return d;
    }

    @Override // org.apache.commons.math.estimation.Estimator
    public double[][] getCovariances(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException {
        updateJacobian();
        int length = estimationProblem.getMeasurements().length;
        int length2 = estimationProblem.getUnboundParameters().length;
        int i = length * length2;
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length2, length2);
        for (int i2 = 0; i2 < length2; i2++) {
            for (int i3 = i2; i3 < length2; i3++) {
                double d = 0.0d;
                for (int i4 = 0; i4 < i; i4 += length2) {
                    d += this.jacobian[i4 + i2] * this.jacobian[i4 + i3];
                }
                dArr[i2][i3] = d;
                dArr[i3][i2] = d;
            }
        }
        try {
            return new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(dArr)).getSolver().getInverse().getData();
        } catch (org.apache.commons.math.linear.InvalidMatrixException e) {
            throw new org.apache.commons.math.estimation.EstimationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_COMPUTE_COVARIANCE_SINGULAR_PROBLEM, new java.lang.Object[0]);
        }
    }

    @Override // org.apache.commons.math.estimation.Estimator
    public double[] guessParametersErrors(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException {
        int length = estimationProblem.getMeasurements().length;
        int length2 = estimationProblem.getUnboundParameters().length;
        if (length <= length2) {
            throw new org.apache.commons.math.estimation.EstimationException(org.apache.commons.math.exception.util.LocalizedFormats.NO_DEGREES_OF_FREEDOM, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(length2));
        }
        int length3 = estimationProblem.getUnboundParameters().length;
        double[] dArr = new double[length3];
        double sqrt = org.apache.commons.math.util.FastMath.sqrt(getChiSquare(estimationProblem) / (length - length2));
        double[][] covariances = getCovariances(estimationProblem);
        for (int i = 0; i < length3; i++) {
            dArr[i] = org.apache.commons.math.util.FastMath.sqrt(covariances[i][i]) * sqrt;
        }
        return dArr;
    }

    protected void initializeEstimate(org.apache.commons.math.estimation.EstimationProblem estimationProblem) {
        this.costEvaluations = 0;
        this.jacobianEvaluations = 0;
        this.measurements = estimationProblem.getMeasurements();
        this.parameters = estimationProblem.getUnboundParameters();
        this.rows = this.measurements.length;
        this.cols = this.parameters.length;
        this.jacobian = new double[this.rows * this.cols];
        this.residuals = new double[this.rows];
        this.cost = Double.POSITIVE_INFINITY;
    }
}
