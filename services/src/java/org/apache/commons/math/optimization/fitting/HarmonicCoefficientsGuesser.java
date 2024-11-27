package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class HarmonicCoefficientsGuesser {
    private final org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] observations;
    private double phi;
    private double a = Double.NaN;
    private double omega = Double.NaN;

    public HarmonicCoefficientsGuesser(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr) {
        this.observations = (org.apache.commons.math.optimization.fitting.WeightedObservedPoint[]) weightedObservedPointArr.clone();
    }

    public void guess() throws org.apache.commons.math.optimization.OptimizationException {
        sortObservations();
        guessAOmega();
        guessPhi();
    }

    private void sortObservations() {
        org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint = this.observations[0];
        for (int i = 1; i < this.observations.length; i++) {
            org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint2 = this.observations[i];
            if (weightedObservedPoint2.getX() >= weightedObservedPoint.getX()) {
                weightedObservedPoint = weightedObservedPoint2;
            } else {
                int i2 = i - 1;
                org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint3 = this.observations[i2];
                while (i2 >= 0 && weightedObservedPoint2.getX() < weightedObservedPoint3.getX()) {
                    this.observations[i2 + 1] = weightedObservedPoint3;
                    int i3 = i2 - 1;
                    if (i2 == 0) {
                        i2 = i3;
                    } else {
                        weightedObservedPoint3 = this.observations[i3];
                        i2 = i3;
                    }
                }
                this.observations[i2 + 1] = weightedObservedPoint2;
                weightedObservedPoint = this.observations[i];
            }
        }
    }

    private void guessAOmega() throws org.apache.commons.math.optimization.OptimizationException {
        double x = this.observations[0].getX();
        double y = this.observations[0].getY();
        int i = 1;
        double d = x;
        double d2 = 0.0d;
        double d3 = 0.0d;
        double d4 = 0.0d;
        double d5 = 0.0d;
        double d6 = 0.0d;
        double d7 = 0.0d;
        double d8 = 0.0d;
        while (i < this.observations.length) {
            double x2 = this.observations[i].getX();
            double y2 = this.observations[i].getY();
            double d9 = x2 - d;
            double d10 = y2 - y;
            double d11 = ((((y * y) + (y * y2)) + (y2 * y2)) * d9) / 3.0d;
            double d12 = x2 - x;
            d7 += d11;
            d8 += (d10 * d10) / d9;
            d6 += d12 * d12;
            d2 += d7 * d7;
            d4 += d12 * d7;
            d3 += d12 * d8;
            d5 += d7 * d8;
            i++;
            d = x2;
            y = y2;
        }
        double d13 = (d2 * d3) - (d4 * d5);
        double d14 = (d3 * d4) - (d5 * d6);
        double d15 = (d6 * d2) - (d4 * d4);
        double d16 = d13 / d14;
        if (d16 >= 0.0d) {
            double d17 = d14 / d15;
            if (d17 >= 0.0d) {
                this.a = org.apache.commons.math.util.FastMath.sqrt(d16);
                this.omega = org.apache.commons.math.util.FastMath.sqrt(d17);
                return;
            }
        }
        throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_FIRST_GUESS_HARMONIC_COEFFICIENTS, new java.lang.Object[0]);
    }

    private void guessPhi() {
        double x = this.observations[0].getX();
        double y = this.observations[0].getY();
        double d = 0.0d;
        int i = 1;
        double d2 = 0.0d;
        while (i < this.observations.length) {
            double x2 = this.observations[i].getX();
            double y2 = this.observations[i].getY();
            double d3 = (y2 - y) / (x2 - x);
            double d4 = this.omega * x2;
            double cos = org.apache.commons.math.util.FastMath.cos(d4);
            double sin = org.apache.commons.math.util.FastMath.sin(d4);
            d2 += ((this.omega * y2) * cos) - (d3 * sin);
            d += (this.omega * y2 * sin) + (d3 * cos);
            i++;
            y = y2;
            x = x2;
        }
        this.phi = org.apache.commons.math.util.FastMath.atan2(-d, d2);
    }

    public double getGuessedAmplitude() {
        return this.a;
    }

    public double getGuessedPulsation() {
        return this.omega;
    }

    public double getGuessedPhase() {
        return this.phi;
    }
}
