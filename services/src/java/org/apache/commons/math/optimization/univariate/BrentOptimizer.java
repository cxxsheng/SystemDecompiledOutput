package org.apache.commons.math.optimization.univariate;

/* loaded from: classes3.dex */
public class BrentOptimizer extends org.apache.commons.math.optimization.univariate.AbstractUnivariateRealOptimizer {
    private static final double GOLDEN_SECTION = (3.0d - org.apache.commons.math.util.FastMath.sqrt(5.0d)) * 0.5d;

    public BrentOptimizer() {
        setMaxEvaluations(1000);
        setMaximalIterationCount(100);
        setAbsoluteAccuracy(1.0E-11d);
        setRelativeAccuracy(1.0E-9d);
    }

    @Override // org.apache.commons.math.optimization.univariate.AbstractUnivariateRealOptimizer
    protected double doOptimize() throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        return localMin(getGoalType() == org.apache.commons.math.optimization.GoalType.MINIMIZE, getMin(), getStartValue(), getMax(), getRelativeAccuracy(), getAbsoluteAccuracy());
    }

    private double localMin(boolean z, double d, double d2, double d3, double d4, double d5) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        double d6;
        double d7;
        double d8;
        double d9;
        double d10;
        long j;
        double d11;
        double d12;
        if (d4 <= 0.0d) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Double.valueOf(d4));
        }
        if (d5 <= 0.0d) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Double.valueOf(d5));
        }
        if (d < d3) {
            d7 = d;
            d6 = d3;
        } else {
            d6 = d;
            d7 = d3;
        }
        double d13 = d2;
        double computeObjectiveValue = computeObjectiveValue(d13);
        if (!z) {
            computeObjectiveValue = -computeObjectiveValue;
        }
        double d14 = 0.0d;
        double d15 = 0.0d;
        double d16 = d6;
        double d17 = d13;
        double d18 = computeObjectiveValue;
        double d19 = d18;
        double d20 = d19;
        double d21 = d7;
        double d22 = d17;
        while (true) {
            double d23 = (d21 + d16) * 0.5d;
            double abs = (org.apache.commons.math.util.FastMath.abs(d13) * d4) + d5;
            double d24 = abs * 2.0d;
            if (org.apache.commons.math.util.FastMath.abs(d13 - d23) <= d24 - ((d16 - d21) * 0.5d)) {
                break;
            }
            if (org.apache.commons.math.util.FastMath.abs(d14) > abs) {
                double d25 = d13 - d22;
                double d26 = (d18 - d19) * d25;
                double d27 = d13 - d17;
                double d28 = (d18 - d20) * d27;
                d9 = d17;
                double d29 = (d27 * d28) - (d25 * d26);
                d8 = d22;
                double d30 = (d28 - d26) * 2.0d;
                if (d30 > 0.0d) {
                    d29 = -d29;
                } else {
                    d30 = -d30;
                }
                double d31 = d21 - d13;
                if (d29 > d30 * d31 && d29 < (d16 - d13) * d30 && org.apache.commons.math.util.FastMath.abs(d29) < org.apache.commons.math.util.FastMath.abs(0.5d * d30 * d14)) {
                    double d32 = d29 / d30;
                    double d33 = d13 + d32;
                    if (d33 - d21 >= d24 && d16 - d33 >= d24) {
                        d14 = d15;
                        d15 = d32;
                    } else if (d13 <= d23) {
                        d14 = d15;
                        d15 = abs;
                    } else {
                        d14 = d15;
                        d15 = -abs;
                    }
                } else {
                    if (d13 < d23) {
                        d31 = d16 - d13;
                    }
                    d15 = GOLDEN_SECTION * d31;
                    d14 = d31;
                }
            } else {
                d8 = d22;
                d9 = d17;
                if (d13 < d23) {
                    d10 = d16 - d13;
                } else {
                    d10 = d21 - d13;
                }
                d14 = d10;
                d15 = GOLDEN_SECTION * d10;
            }
            if (org.apache.commons.math.util.FastMath.abs(d15) < abs) {
                j = 0;
                if (d15 >= 0.0d) {
                    d11 = abs + d13;
                } else {
                    d11 = d13 - abs;
                }
            } else {
                j = 0;
                d11 = d13 + d15;
            }
            double computeObjectiveValue2 = computeObjectiveValue(d11);
            if (!z) {
                computeObjectiveValue2 = -computeObjectiveValue2;
            }
            if (computeObjectiveValue2 <= d18) {
                if (d11 < d13) {
                    d16 = d13;
                } else {
                    d21 = d13;
                }
                d12 = d13;
                d19 = d20;
                d13 = d11;
                d20 = d18;
                d18 = computeObjectiveValue2;
                d17 = d8;
            } else {
                if (d11 < d13) {
                    d21 = d11;
                } else {
                    d16 = d11;
                }
                if (computeObjectiveValue2 > d20) {
                    d12 = d8;
                    if (d12 != d13) {
                        if (computeObjectiveValue2 > d19) {
                            double d34 = d9;
                            if (d34 != d13 && d34 != d12) {
                                d17 = d34;
                            }
                        }
                        d19 = computeObjectiveValue2;
                        d17 = d11;
                    }
                } else {
                    d12 = d8;
                }
                d19 = d20;
                d20 = computeObjectiveValue2;
                d17 = d12;
                d12 = d11;
            }
            incrementIterationsCounter();
            d22 = d12;
        }
        if (!z) {
            d18 = -d18;
        }
        setFunctionValue(d18);
        return d13;
    }
}
