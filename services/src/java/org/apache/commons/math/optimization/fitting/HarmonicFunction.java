package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class HarmonicFunction implements org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction {
    private final double a;
    private final double omega;
    private final double phi;

    public HarmonicFunction(double d, double d2, double d3) {
        this.a = d;
        this.omega = d2;
        this.phi = d3;
    }

    @Override // org.apache.commons.math.analysis.UnivariateRealFunction
    public double value(double d) {
        return this.a * org.apache.commons.math.util.FastMath.cos((this.omega * d) + this.phi);
    }

    @Override // org.apache.commons.math.analysis.DifferentiableUnivariateRealFunction
    public org.apache.commons.math.optimization.fitting.HarmonicFunction derivative() {
        return new org.apache.commons.math.optimization.fitting.HarmonicFunction(this.a * this.omega, this.omega, this.phi + 1.5707963267948966d);
    }

    public double getAmplitude() {
        return this.a;
    }

    public double getPulsation() {
        return this.omega;
    }

    public double getPhase() {
        return this.phi;
    }
}
