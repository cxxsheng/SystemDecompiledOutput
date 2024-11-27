package org.apache.commons.math.optimization.general;

/* loaded from: classes3.dex */
public abstract class AbstractLeastSquaresOptimizer implements org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer {
    public static final int DEFAULT_MAX_ITERATIONS = 100;
    protected org.apache.commons.math.optimization.VectorialConvergenceChecker checker;
    protected int cols;
    protected double cost;
    private org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction function;
    private int iterations;
    private org.apache.commons.math.analysis.MultivariateMatrixFunction jF;
    protected double[][] jacobian;
    private int jacobianEvaluations;
    private int maxEvaluations;
    private int maxIterations;
    protected double[] objective;
    private int objectiveEvaluations;
    protected double[] point;
    protected double[] residuals;
    protected double[] residualsWeights;
    protected int rows;
    protected double[] targetValues;
    protected double[][] wjacobian;
    protected double[] wresiduals;

    protected abstract org.apache.commons.math.optimization.VectorialPointValuePair doOptimize() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException;

    protected AbstractLeastSquaresOptimizer() {
        setConvergenceChecker(new org.apache.commons.math.optimization.SimpleVectorialValueChecker());
        setMaxIterations(100);
        setMaxEvaluations(Integer.MAX_VALUE);
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public void setMaxIterations(int i) {
        this.maxIterations = i;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public int getMaxIterations() {
        return this.maxIterations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public int getIterations() {
        return this.iterations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public void setMaxEvaluations(int i) {
        this.maxEvaluations = i;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public int getEvaluations() {
        return this.objectiveEvaluations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public int getJacobianEvaluations() {
        return this.jacobianEvaluations;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public void setConvergenceChecker(org.apache.commons.math.optimization.VectorialConvergenceChecker vectorialConvergenceChecker) {
        this.checker = vectorialConvergenceChecker;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public org.apache.commons.math.optimization.VectorialConvergenceChecker getConvergenceChecker() {
        return this.checker;
    }

    protected void incrementIterationsCounter() throws org.apache.commons.math.optimization.OptimizationException {
        int i = this.iterations + 1;
        this.iterations = i;
        if (i > this.maxIterations) {
            throw new org.apache.commons.math.optimization.OptimizationException(new org.apache.commons.math.MaxIterationsExceededException(this.maxIterations));
        }
    }

    protected void updateJacobian() throws org.apache.commons.math.FunctionEvaluationException {
        this.jacobianEvaluations++;
        this.jacobian = this.jF.value(this.point);
        if (this.jacobian.length != this.rows) {
            throw new org.apache.commons.math.FunctionEvaluationException(this.point, org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(this.jacobian.length), java.lang.Integer.valueOf(this.rows));
        }
        for (int i = 0; i < this.rows; i++) {
            double[] dArr = this.jacobian[i];
            double sqrt = org.apache.commons.math.util.FastMath.sqrt(this.residualsWeights[i]);
            for (int i2 = 0; i2 < this.cols; i2++) {
                dArr[i2] = dArr[i2] * (-1.0d);
                this.wjacobian[i][i2] = dArr[i2] * sqrt;
            }
        }
    }

    protected void updateResidualsAndCost() throws org.apache.commons.math.FunctionEvaluationException {
        int i = this.objectiveEvaluations + 1;
        this.objectiveEvaluations = i;
        if (i > this.maxEvaluations) {
            throw new org.apache.commons.math.FunctionEvaluationException(new org.apache.commons.math.MaxEvaluationsExceededException(this.maxEvaluations), this.point);
        }
        this.objective = this.function.value(this.point);
        if (this.objective.length != this.rows) {
            throw new org.apache.commons.math.FunctionEvaluationException(this.point, org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(this.objective.length), java.lang.Integer.valueOf(this.rows));
        }
        this.cost = 0.0d;
        for (int i2 = 0; i2 < this.rows; i2++) {
            double d = this.targetValues[i2] - this.objective[i2];
            this.residuals[i2] = d;
            this.wresiduals[i2] = org.apache.commons.math.util.FastMath.sqrt(this.residualsWeights[i2]) * d;
            this.cost += this.residualsWeights[i2] * d * d;
        }
        this.cost = org.apache.commons.math.util.FastMath.sqrt(this.cost);
    }

    public double getRMS() {
        return org.apache.commons.math.util.FastMath.sqrt(getChiSquare() / this.rows);
    }

    public double getChiSquare() {
        return this.cost * this.cost;
    }

    public double[][] getCovariances() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        updateJacobian();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, this.cols, this.cols);
        for (int i = 0; i < this.cols; i++) {
            for (int i2 = i; i2 < this.cols; i2++) {
                double d = 0.0d;
                for (int i3 = 0; i3 < this.rows; i3++) {
                    d += this.wjacobian[i3][i] * this.wjacobian[i3][i2];
                }
                dArr[i][i2] = d;
                dArr[i2][i] = d;
            }
        }
        try {
            return new org.apache.commons.math.linear.LUDecompositionImpl(org.apache.commons.math.linear.MatrixUtils.createRealMatrix(dArr)).getSolver().getInverse().getData();
        } catch (org.apache.commons.math.linear.InvalidMatrixException e) {
            throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_COMPUTE_COVARIANCE_SINGULAR_PROBLEM, new java.lang.Object[0]);
        }
    }

    public double[] guessParametersErrors() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException {
        if (this.rows <= this.cols) {
            throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.NO_DEGREES_OF_FREEDOM, java.lang.Integer.valueOf(this.rows), java.lang.Integer.valueOf(this.cols));
        }
        int i = this.cols;
        double[] dArr = new double[i];
        double sqrt = org.apache.commons.math.util.FastMath.sqrt(getChiSquare() / (this.rows - this.cols));
        double[][] covariances = getCovariances();
        for (int i2 = 0; i2 < i; i2++) {
            dArr[i2] = org.apache.commons.math.util.FastMath.sqrt(covariances[i2][i2]) * sqrt;
        }
        return dArr;
    }

    @Override // org.apache.commons.math.optimization.DifferentiableMultivariateVectorialOptimizer
    public org.apache.commons.math.optimization.VectorialPointValuePair optimize(org.apache.commons.math.analysis.DifferentiableMultivariateVectorialFunction differentiableMultivariateVectorialFunction, double[] dArr, double[] dArr2, double[] dArr3) throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        if (dArr.length != dArr2.length) {
            throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr2.length));
        }
        this.iterations = 0;
        this.objectiveEvaluations = 0;
        this.jacobianEvaluations = 0;
        this.function = differentiableMultivariateVectorialFunction;
        this.jF = differentiableMultivariateVectorialFunction.jacobian();
        this.targetValues = (double[]) dArr.clone();
        this.residualsWeights = (double[]) dArr2.clone();
        this.point = (double[]) dArr3.clone();
        this.residuals = new double[dArr.length];
        this.rows = dArr.length;
        this.cols = this.point.length;
        this.jacobian = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, this.rows, this.cols);
        this.wjacobian = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, this.rows, this.cols);
        this.wresiduals = new double[this.rows];
        this.cost = Double.POSITIVE_INFINITY;
        return doOptimize();
    }
}
