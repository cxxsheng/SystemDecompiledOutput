package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class NevilleInterpolator implements org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator, java.io.Serializable {
    static final long serialVersionUID = 3003707660147873733L;

    @Override // org.apache.commons.math.analysis.interpolation.UnivariateRealInterpolator
    public org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm interpolate(double[] dArr, double[] dArr2) throws org.apache.commons.math.MathException {
        return new org.apache.commons.math.analysis.polynomials.PolynomialFunctionLagrangeForm(dArr, dArr2);
    }
}
