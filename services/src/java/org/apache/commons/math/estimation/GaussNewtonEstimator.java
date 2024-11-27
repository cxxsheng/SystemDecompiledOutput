package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class GaussNewtonEstimator extends org.apache.commons.math.estimation.AbstractEstimator implements java.io.Serializable {
    private static final double DEFAULT_CONVERGENCE = 1.0E-6d;
    private static final double DEFAULT_STEADY_STATE_THRESHOLD = 1.0E-6d;
    private static final long serialVersionUID = 5485001826076289109L;
    private double convergence;
    private double steadyStateThreshold;

    public GaussNewtonEstimator() {
        this.steadyStateThreshold = 1.0E-6d;
        this.convergence = 1.0E-6d;
    }

    public GaussNewtonEstimator(int i, double d, double d2) {
        setMaxCostEval(i);
        this.steadyStateThreshold = d2;
        this.convergence = d;
    }

    public void setConvergence(double d) {
        this.convergence = d;
    }

    public void setSteadyStateThreshold(double d) {
        this.steadyStateThreshold = d;
    }

    @Override // org.apache.commons.math.estimation.AbstractEstimator, org.apache.commons.math.estimation.Estimator
    public void estimate(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException {
        int i;
        initializeEstimate(estimationProblem);
        double[] dArr = new double[this.parameters.length];
        org.apache.commons.math.linear.ArrayRealVector arrayRealVector = new org.apache.commons.math.linear.ArrayRealVector(this.parameters.length);
        double[] dataRef = arrayRealVector.getDataRef();
        org.apache.commons.math.linear.RealMatrix createRealMatrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(this.parameters.length, this.parameters.length);
        while (true) {
            incrementJacobianEvaluationsCounter();
            org.apache.commons.math.linear.RealVector arrayRealVector2 = new org.apache.commons.math.linear.ArrayRealVector(this.parameters.length);
            org.apache.commons.math.linear.RealMatrix createRealMatrix2 = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(this.parameters.length, this.parameters.length);
            int i2 = 0;
            while (i2 < this.measurements.length) {
                if (this.measurements[i2].isIgnored()) {
                    i = i2;
                } else {
                    double weight = this.measurements[i2].getWeight();
                    double residual = this.measurements[i2].getResidual();
                    for (int i3 = 0; i3 < this.parameters.length; i3++) {
                        dArr[i3] = this.measurements[i2].getPartial(this.parameters[i3]);
                        dataRef[i3] = weight * residual * dArr[i3];
                    }
                    for (int i4 = 0; i4 < this.parameters.length; i4++) {
                        double d = dArr[i4];
                        int i5 = 0;
                        while (i5 < this.parameters.length) {
                            createRealMatrix.setEntry(i4, i5, weight * d * dArr[i5]);
                            i5++;
                            i2 = i2;
                        }
                    }
                    i = i2;
                    createRealMatrix2 = createRealMatrix2.add(createRealMatrix);
                    arrayRealVector2 = arrayRealVector2.add(arrayRealVector);
                }
                i2 = i + 1;
            }
            try {
                org.apache.commons.math.linear.RealVector solve = new org.apache.commons.math.linear.LUDecompositionImpl(createRealMatrix2).getSolver().solve(arrayRealVector2);
                for (int i6 = 0; i6 < this.parameters.length; i6++) {
                    this.parameters[i6].setEstimate(this.parameters[i6].getEstimate() + solve.getEntry(i6));
                }
                double d2 = this.cost;
                updateResidualsAndCost();
                if (getCostEvaluations() >= 2 && (org.apache.commons.math.util.FastMath.abs(d2 - this.cost) <= this.cost * this.steadyStateThreshold || org.apache.commons.math.util.FastMath.abs(this.cost) <= this.convergence)) {
                    return;
                }
            } catch (org.apache.commons.math.linear.InvalidMatrixException e) {
                throw new org.apache.commons.math.estimation.EstimationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, new java.lang.Object[0]);
            }
        }
    }
}
