package org.apache.commons.math.optimization.fitting;

/* loaded from: classes3.dex */
public class GaussianParametersGuesser {
    private final org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] observations;
    private double[] parameters;

    public GaussianParametersGuesser(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr) {
        if (weightedObservedPointArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        if (weightedObservedPointArr.length < 3) {
            throw new org.apache.commons.math.exception.NumberIsTooSmallException(java.lang.Integer.valueOf(weightedObservedPointArr.length), 3, true);
        }
        this.observations = (org.apache.commons.math.optimization.fitting.WeightedObservedPoint[]) weightedObservedPointArr.clone();
    }

    public double[] guess() {
        if (this.parameters == null) {
            this.parameters = basicGuess(this.observations);
        }
        return (double[]) this.parameters.clone();
    }

    private double[] basicGuess(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr) {
        double x;
        java.util.Arrays.sort(weightedObservedPointArr, createWeightedObservedPointComparator());
        double[] dArr = new double[4];
        dArr[0] = weightedObservedPointArr[findMinY(weightedObservedPointArr)].getY();
        int findMaxY = findMaxY(weightedObservedPointArr);
        dArr[1] = weightedObservedPointArr[findMaxY].getY();
        dArr[2] = weightedObservedPointArr[findMaxY].getX();
        try {
            double d = dArr[0] + ((dArr[1] - dArr[0]) / 2.0d);
            x = interpolateXAtY(weightedObservedPointArr, findMaxY, 1, d) - interpolateXAtY(weightedObservedPointArr, findMaxY, -1, d);
        } catch (org.apache.commons.math.exception.OutOfRangeException e) {
            x = weightedObservedPointArr[weightedObservedPointArr.length - 1].getX() - weightedObservedPointArr[0].getX();
        }
        dArr[3] = x / (java.lang.Math.sqrt(java.lang.Math.log(2.0d) * 2.0d) * 2.0d);
        return dArr;
    }

    private int findMinY(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr) {
        int i = 0;
        for (int i2 = 1; i2 < weightedObservedPointArr.length; i2++) {
            if (weightedObservedPointArr[i2].getY() < weightedObservedPointArr[i].getY()) {
                i = i2;
            }
        }
        return i;
    }

    private int findMaxY(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr) {
        int i = 0;
        for (int i2 = 1; i2 < weightedObservedPointArr.length; i2++) {
            if (weightedObservedPointArr[i2].getY() > weightedObservedPointArr[i].getY()) {
                i = i2;
            }
        }
        return i;
    }

    private double interpolateXAtY(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr, int i, int i2, double d) throws org.apache.commons.math.exception.OutOfRangeException {
        if (i2 == 0) {
            throw new org.apache.commons.math.exception.ZeroException();
        }
        org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] interpolationPointsForY = getInterpolationPointsForY(weightedObservedPointArr, i, i2, d);
        org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint = interpolationPointsForY[0];
        org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint2 = interpolationPointsForY[1];
        if (weightedObservedPoint.getY() == d) {
            return weightedObservedPoint.getX();
        }
        if (weightedObservedPoint2.getY() == d) {
            return weightedObservedPoint2.getX();
        }
        return weightedObservedPoint.getX() + (((d - weightedObservedPoint.getY()) * (weightedObservedPoint2.getX() - weightedObservedPoint.getX())) / (weightedObservedPoint2.getY() - weightedObservedPoint.getY()));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x003f A[LOOP:0: B:2:0x0003->B:7:0x003f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] getInterpolationPointsForY(org.apache.commons.math.optimization.fitting.WeightedObservedPoint[] weightedObservedPointArr, int i, int i2, double d) throws org.apache.commons.math.exception.OutOfRangeException {
        int i3;
        int i4;
        if (i2 == 0) {
            throw new org.apache.commons.math.exception.ZeroException();
        }
        while (true) {
            int i5 = i + i2;
            if (i2 < 0) {
                if (i5 < 0) {
                    break;
                }
                i4 = i + i2;
                if (!isBetween(d, weightedObservedPointArr[i].getY(), weightedObservedPointArr[i4].getY())) {
                    if (i2 < 0) {
                        return new org.apache.commons.math.optimization.fitting.WeightedObservedPoint[]{weightedObservedPointArr[i4], weightedObservedPointArr[i]};
                    }
                    return new org.apache.commons.math.optimization.fitting.WeightedObservedPoint[]{weightedObservedPointArr[i], weightedObservedPointArr[i4]};
                }
                i = i4;
            } else {
                if (i5 >= weightedObservedPointArr.length) {
                    break;
                }
                i4 = i + i2;
                if (!isBetween(d, weightedObservedPointArr[i].getY(), weightedObservedPointArr[i4].getY())) {
                }
            }
        }
        double d2 = Double.POSITIVE_INFINITY;
        double d3 = Double.NEGATIVE_INFINITY;
        for (org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint : weightedObservedPointArr) {
            d2 = java.lang.Math.min(d2, weightedObservedPoint.getY());
            d3 = java.lang.Math.max(d3, weightedObservedPoint.getY());
        }
        throw new org.apache.commons.math.exception.OutOfRangeException(java.lang.Double.valueOf(d), java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d3));
    }

    private boolean isBetween(double d, double d2, double d3) {
        return (d >= d2 && d <= d3) || (d >= d3 && d <= d2);
    }

    private java.util.Comparator<org.apache.commons.math.optimization.fitting.WeightedObservedPoint> createWeightedObservedPointComparator() {
        return new java.util.Comparator<org.apache.commons.math.optimization.fitting.WeightedObservedPoint>() { // from class: org.apache.commons.math.optimization.fitting.GaussianParametersGuesser.1
            @Override // java.util.Comparator
            public int compare(org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint, org.apache.commons.math.optimization.fitting.WeightedObservedPoint weightedObservedPoint2) {
                if (weightedObservedPoint == null && weightedObservedPoint2 == null) {
                    return 0;
                }
                if (weightedObservedPoint == null) {
                    return -1;
                }
                if (weightedObservedPoint2 == null) {
                    return 1;
                }
                if (weightedObservedPoint.getX() < weightedObservedPoint2.getX()) {
                    return -1;
                }
                if (weightedObservedPoint.getX() > weightedObservedPoint2.getX()) {
                    return 1;
                }
                if (weightedObservedPoint.getY() < weightedObservedPoint2.getY()) {
                    return -1;
                }
                if (weightedObservedPoint.getY() > weightedObservedPoint2.getY()) {
                    return 1;
                }
                if (weightedObservedPoint.getWeight() < weightedObservedPoint2.getWeight()) {
                    return -1;
                }
                if (weightedObservedPoint.getWeight() <= weightedObservedPoint2.getWeight()) {
                    return 0;
                }
                return 1;
            }
        };
    }
}
