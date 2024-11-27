package org.apache.commons.math.ode;

/* loaded from: classes3.dex */
public class FirstOrderConverter implements org.apache.commons.math.ode.FirstOrderDifferentialEquations {
    private final int dimension;
    private final org.apache.commons.math.ode.SecondOrderDifferentialEquations equations;
    private final double[] z;
    private final double[] zDDot;
    private final double[] zDot;

    public FirstOrderConverter(org.apache.commons.math.ode.SecondOrderDifferentialEquations secondOrderDifferentialEquations) {
        this.equations = secondOrderDifferentialEquations;
        this.dimension = secondOrderDifferentialEquations.getDimension();
        this.z = new double[this.dimension];
        this.zDot = new double[this.dimension];
        this.zDDot = new double[this.dimension];
    }

    @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
    public int getDimension() {
        return this.dimension * 2;
    }

    @Override // org.apache.commons.math.ode.FirstOrderDifferentialEquations
    public void computeDerivatives(double d, double[] dArr, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException {
        java.lang.System.arraycopy(dArr, 0, this.z, 0, this.dimension);
        java.lang.System.arraycopy(dArr, this.dimension, this.zDot, 0, this.dimension);
        this.equations.computeSecondDerivatives(d, this.z, this.zDot, this.zDDot);
        java.lang.System.arraycopy(this.zDot, 0, dArr2, 0, this.dimension);
        java.lang.System.arraycopy(this.zDDot, 0, dArr2, this.dimension, this.dimension);
    }
}
