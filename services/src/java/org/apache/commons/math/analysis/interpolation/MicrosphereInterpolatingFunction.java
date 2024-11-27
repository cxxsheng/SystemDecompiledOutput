package org.apache.commons.math.analysis.interpolation;

/* loaded from: classes3.dex */
public class MicrosphereInterpolatingFunction implements org.apache.commons.math.analysis.MultivariateRealFunction {
    private final double brightnessExponent;
    private final int dimension;
    private final java.util.List<org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement> microsphere;
    private final java.util.Map<org.apache.commons.math.linear.RealVector, java.lang.Double> samples;

    private static class MicrosphereSurfaceElement {
        private double brightestIllumination;
        private java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> brightestSample;
        private final org.apache.commons.math.linear.RealVector normal;

        MicrosphereSurfaceElement(double[] dArr) {
            this.normal = new org.apache.commons.math.linear.ArrayRealVector(dArr);
        }

        org.apache.commons.math.linear.RealVector normal() {
            return this.normal;
        }

        void reset() {
            this.brightestIllumination = 0.0d;
            this.brightestSample = null;
        }

        void store(double d, java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> entry) {
            if (d > this.brightestIllumination) {
                this.brightestIllumination = d;
                this.brightestSample = entry;
            }
        }

        double illumination() {
            return this.brightestIllumination;
        }

        java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> sample() {
            return this.brightestSample;
        }
    }

    public MicrosphereInterpolatingFunction(double[][] dArr, double[] dArr2, int i, int i2, org.apache.commons.math.random.UnitSphereRandomVectorGenerator unitSphereRandomVectorGenerator) throws org.apache.commons.math.DimensionMismatchException, org.apache.commons.math.exception.NoDataException {
        if (dArr.length != 0) {
            if (dArr[0] != null) {
                if (dArr.length != dArr2.length) {
                    throw new org.apache.commons.math.DimensionMismatchException(dArr.length, dArr2.length);
                }
                this.dimension = dArr[0].length;
                this.brightnessExponent = i;
                this.samples = new java.util.HashMap(dArr2.length);
                for (int i3 = 0; i3 < dArr.length; i3++) {
                    double[] dArr3 = dArr[i3];
                    if (dArr3.length != this.dimension) {
                        throw new org.apache.commons.math.DimensionMismatchException(dArr3.length, this.dimension);
                    }
                    this.samples.put(new org.apache.commons.math.linear.ArrayRealVector(dArr3), java.lang.Double.valueOf(dArr2[i3]));
                }
                this.microsphere = new java.util.ArrayList(i2);
                for (int i4 = 0; i4 < i2; i4++) {
                    this.microsphere.add(new org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement(unitSphereRandomVectorGenerator.nextVector()));
                }
                return;
            }
        }
        throw new org.apache.commons.math.exception.NoDataException();
    }

    @Override // org.apache.commons.math.analysis.MultivariateRealFunction
    public double value(double[] dArr) {
        org.apache.commons.math.linear.ArrayRealVector arrayRealVector = new org.apache.commons.math.linear.ArrayRealVector(dArr);
        java.util.Iterator<org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement> it = this.microsphere.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        for (java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> entry : this.samples.entrySet()) {
            org.apache.commons.math.linear.RealVector subtract = entry.getKey().subtract(arrayRealVector);
            double norm = subtract.getNorm();
            if (org.apache.commons.math.util.FastMath.abs(norm) < org.apache.commons.math.util.FastMath.ulp(1.0d)) {
                return entry.getValue().doubleValue();
            }
            for (org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement microsphereSurfaceElement : this.microsphere) {
                microsphereSurfaceElement.store(cosAngle(subtract, microsphereSurfaceElement.normal()) * org.apache.commons.math.util.FastMath.pow(norm, -this.brightnessExponent), entry);
            }
        }
        double d = 0.0d;
        double d2 = 0.0d;
        for (org.apache.commons.math.analysis.interpolation.MicrosphereInterpolatingFunction.MicrosphereSurfaceElement microsphereSurfaceElement2 : this.microsphere) {
            double illumination = microsphereSurfaceElement2.illumination();
            java.util.Map.Entry<org.apache.commons.math.linear.RealVector, java.lang.Double> sample = microsphereSurfaceElement2.sample();
            if (sample != null) {
                d += sample.getValue().doubleValue() * illumination;
                d2 += illumination;
            }
        }
        return d / d2;
    }

    private double cosAngle(org.apache.commons.math.linear.RealVector realVector, org.apache.commons.math.linear.RealVector realVector2) {
        return realVector.dotProduct(realVector2) / (realVector.getNorm() * realVector2.getNorm());
    }
}
