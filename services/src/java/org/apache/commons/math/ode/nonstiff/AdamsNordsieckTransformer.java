package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public class AdamsNordsieckTransformer {
    private static final java.util.Map<java.lang.Integer, org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer> CACHE = new java.util.HashMap();
    private final double[] c1;
    private final org.apache.commons.math.linear.Array2DRowRealMatrix initialization;
    private final org.apache.commons.math.linear.Array2DRowRealMatrix update;

    private AdamsNordsieckTransformer(int i) {
        org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.BigFraction> buildP = buildP(i);
        org.apache.commons.math.linear.FieldDecompositionSolver solver = new org.apache.commons.math.linear.FieldLUDecompositionImpl(buildP).getSolver();
        org.apache.commons.math.fraction.BigFraction[] bigFractionArr = new org.apache.commons.math.fraction.BigFraction[i];
        java.util.Arrays.fill(bigFractionArr, org.apache.commons.math.fraction.BigFraction.ONE);
        org.apache.commons.math.fraction.BigFraction[] bigFractionArr2 = (org.apache.commons.math.fraction.BigFraction[]) solver.solve(bigFractionArr);
        org.apache.commons.math.fraction.BigFraction[][] data = buildP.getData();
        for (int length = data.length - 1; length > 0; length--) {
            data[length] = data[length - 1];
        }
        data[0] = new org.apache.commons.math.fraction.BigFraction[i];
        java.util.Arrays.fill(data[0], org.apache.commons.math.fraction.BigFraction.ZERO);
        org.apache.commons.math.linear.FieldMatrix solve = solver.solve(new org.apache.commons.math.linear.Array2DRowFieldMatrix(data, false));
        buildP.walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultFieldMatrixChangingVisitor<org.apache.commons.math.fraction.BigFraction>(org.apache.commons.math.fraction.BigFraction.ZERO) { // from class: org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer.1
            @Override // org.apache.commons.math.linear.DefaultFieldMatrixChangingVisitor, org.apache.commons.math.linear.FieldMatrixChangingVisitor
            public org.apache.commons.math.fraction.BigFraction visit(int i2, int i3, org.apache.commons.math.fraction.BigFraction bigFraction) {
                return (i3 & 1) == 1 ? bigFraction : bigFraction.negate();
            }
        });
        this.initialization = org.apache.commons.math.linear.MatrixUtils.bigFractionMatrixToRealMatrix(new org.apache.commons.math.linear.FieldLUDecompositionImpl(buildP).getSolver().getInverse());
        this.update = org.apache.commons.math.linear.MatrixUtils.bigFractionMatrixToRealMatrix(solve);
        this.c1 = new double[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.c1[i2] = bigFractionArr2[i2].doubleValue();
        }
    }

    public static org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer getInstance(int i) {
        org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer adamsNordsieckTransformer;
        synchronized (CACHE) {
            try {
                adamsNordsieckTransformer = CACHE.get(java.lang.Integer.valueOf(i));
                if (adamsNordsieckTransformer == null) {
                    adamsNordsieckTransformer = new org.apache.commons.math.ode.nonstiff.AdamsNordsieckTransformer(i);
                    CACHE.put(java.lang.Integer.valueOf(i), adamsNordsieckTransformer);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return adamsNordsieckTransformer;
    }

    public int getNSteps() {
        return this.c1.length;
    }

    private org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.BigFraction> buildP(int i) {
        org.apache.commons.math.fraction.BigFraction[][] bigFractionArr = (org.apache.commons.math.fraction.BigFraction[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) org.apache.commons.math.fraction.BigFraction.class, i, i);
        int i2 = 0;
        while (i2 < bigFractionArr.length) {
            org.apache.commons.math.fraction.BigFraction[] bigFractionArr2 = bigFractionArr[i2];
            i2++;
            int i3 = -i2;
            int i4 = i3;
            for (int i5 = 0; i5 < bigFractionArr2.length; i5++) {
                bigFractionArr2[i5] = new org.apache.commons.math.fraction.BigFraction((i5 + 2) * i4);
                i4 *= i3;
            }
        }
        return new org.apache.commons.math.linear.Array2DRowFieldMatrix(bigFractionArr, false);
    }

    public org.apache.commons.math.linear.Array2DRowRealMatrix initializeHighOrderDerivatives(double[] dArr, double[][] dArr2) {
        for (double[] dArr3 : dArr2) {
            for (int i = 0; i < dArr.length; i++) {
                dArr3[i] = dArr3[i] - dArr[i];
            }
        }
        return this.initialization.multiply(new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr2, false));
    }

    public org.apache.commons.math.linear.Array2DRowRealMatrix updateHighOrderDerivativesPhase1(org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix) {
        return this.update.multiply(array2DRowRealMatrix);
    }

    public void updateHighOrderDerivativesPhase2(double[] dArr, double[] dArr2, org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix) {
        double[][] dataRef = array2DRowRealMatrix.getDataRef();
        for (int i = 0; i < dataRef.length; i++) {
            double[] dArr3 = dataRef[i];
            double d = this.c1[i];
            for (int i2 = 0; i2 < dArr3.length; i2++) {
                dArr3[i2] = dArr3[i2] + ((dArr[i2] - dArr2[i2]) * d);
            }
        }
    }
}
