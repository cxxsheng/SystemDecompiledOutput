package org.apache.commons.math.optimization.general;

/* loaded from: classes3.dex */
public class GaussNewtonOptimizer extends org.apache.commons.math.optimization.general.AbstractLeastSquaresOptimizer {
    private final boolean useLU;

    public GaussNewtonOptimizer(boolean z) {
        this.useLU = z;
    }

    @Override // org.apache.commons.math.optimization.general.AbstractLeastSquaresOptimizer
    public org.apache.commons.math.optimization.VectorialPointValuePair doOptimize() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.DecompositionSolver solver;
        org.apache.commons.math.optimization.VectorialPointValuePair vectorialPointValuePair = null;
        int i = 0;
        boolean z = false;
        while (!z) {
            incrementIterationsCounter();
            updateResidualsAndCost();
            updateJacobian();
            org.apache.commons.math.optimization.VectorialPointValuePair vectorialPointValuePair2 = new org.apache.commons.math.optimization.VectorialPointValuePair(this.point, this.objective);
            double[] dArr = new double[this.cols];
            int i2 = this.cols;
            int[] iArr = new int[2];
            iArr[1] = this.cols;
            iArr[i] = i2;
            double[][] dArr2 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, iArr);
            int i3 = i;
            while (i3 < this.rows) {
                double[] dArr3 = this.jacobian[i3];
                double d = this.residualsWeights[i3];
                double d2 = (this.objective[i3] - this.targetValues[i3]) * d;
                for (int i4 = i; i4 < this.cols; i4++) {
                    dArr[i4] = dArr[i4] + (dArr3[i4] * d2);
                }
                int i5 = i;
                while (i5 < this.cols) {
                    double[] dArr4 = dArr2[i5];
                    double d3 = dArr3[i5] * d;
                    for (int i6 = i; i6 < this.cols; i6++) {
                        dArr4[i6] = dArr4[i6] + (dArr3[i6] * d3);
                    }
                    i5++;
                    i = 0;
                }
                i3++;
                i = 0;
            }
            try {
                org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(dArr2);
                if (this.useLU) {
                    solver = new org.apache.commons.math.linear.LUDecompositionImpl(blockRealMatrix).getSolver();
                } else {
                    solver = new org.apache.commons.math.linear.QRDecompositionImpl(blockRealMatrix).getSolver();
                }
                double[] solve = solver.solve(dArr);
                for (int i7 = 0; i7 < this.cols; i7++) {
                    double[] dArr5 = this.point;
                    dArr5[i7] = dArr5[i7] + solve[i7];
                }
                if (vectorialPointValuePair != null) {
                    z = this.checker.converged(getIterations(), vectorialPointValuePair, vectorialPointValuePair2);
                }
                vectorialPointValuePair = vectorialPointValuePair2;
                i = 0;
            } catch (org.apache.commons.math.linear.InvalidMatrixException e) {
                throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, new java.lang.Object[0]);
            }
        }
        return vectorialPointValuePair;
    }
}
