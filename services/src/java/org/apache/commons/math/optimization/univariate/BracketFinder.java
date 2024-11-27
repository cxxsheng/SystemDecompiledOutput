package org.apache.commons.math.optimization.univariate;

/* loaded from: classes3.dex */
public class BracketFinder {
    private static final double EPS_MIN = 1.0E-21d;
    private static final double GOLD = 1.618034d;
    private int evaluations;
    private double fHi;
    private double fLo;
    private double fMid;
    private final double growLimit;
    private double hi;
    private int iterations;
    private double lo;
    private final int maxIterations;
    private double mid;

    public BracketFinder() {
        this(100.0d, 50);
    }

    public BracketFinder(double d, int i) {
        if (d <= 0.0d) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Double.valueOf(d));
        }
        if (i <= 0) {
            throw new org.apache.commons.math.exception.NotStrictlyPositiveException(java.lang.Integer.valueOf(i));
        }
        this.growLimit = d;
        this.maxIterations = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00cc, code lost:
    
        r1 = r6;
        r12 = r14;
        r6 = r38;
        r14 = r4;
        r4 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0198, code lost:
    
        r6 = r38;
        r14 = r4;
        r4 = r8;
        r1 = r16;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x018e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void search(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, org.apache.commons.math.optimization.GoalType goalType, double d, double d2) throws org.apache.commons.math.MaxIterationsExceededException, org.apache.commons.math.FunctionEvaluationException {
        double d3;
        double d4;
        int i;
        long j;
        double eval;
        reset();
        boolean z = goalType == org.apache.commons.math.optimization.GoalType.MINIMIZE;
        double d5 = d;
        double eval2 = eval(univariateRealFunction, d5);
        double d6 = d2;
        double eval3 = eval(univariateRealFunction, d6);
        if (!z ? eval2 > eval3 : eval2 < eval3) {
            eval2 = eval3;
            eval3 = eval2;
        } else {
            d5 = d6;
            d6 = d5;
        }
        double d7 = ((d5 - d6) * GOLD) + d5;
        double eval4 = eval(univariateRealFunction, d7);
        while (true) {
            if (z) {
                if (eval4 >= eval3) {
                    d3 = eval2;
                    break;
                }
                i = this.iterations + 1;
                this.iterations = i;
                if (i <= this.maxIterations) {
                    throw new org.apache.commons.math.MaxIterationsExceededException(this.maxIterations);
                }
                double d8 = d5 - d6;
                double d9 = (eval3 - eval4) * d8;
                double d10 = d5 - d7;
                double d11 = (eval3 - eval2) * d10;
                double d12 = d11 - d9;
                d4 = d5 - (((d10 * d11) - (d8 * d9)) / (java.lang.Math.abs(d12) < EPS_MIN ? 2.0E-21d : d12 * 2.0d));
                double d13 = eval2;
                double d14 = d7 - d5;
                double d15 = (this.growLimit * d14) + d5;
                double d16 = d4 - d7;
                if ((d5 - d4) * d16 > 0.0d) {
                    eval = eval(univariateRealFunction, d4);
                    if (z) {
                        if (eval < eval4) {
                            break;
                        }
                        if (!z) {
                            if (eval > eval3) {
                                break;
                            }
                            double d17 = d7 + (d14 * GOLD);
                            double eval5 = eval(univariateRealFunction, d17);
                            d4 = d17;
                            eval2 = eval3;
                            eval3 = eval4;
                            eval4 = eval5;
                            j = 4609965796492119705L;
                            double d18 = d5;
                            d5 = d7;
                            d7 = d18;
                        } else {
                            if (eval < eval3) {
                                break;
                            }
                            double d172 = d7 + (d14 * GOLD);
                            double eval52 = eval(univariateRealFunction, d172);
                            d4 = d172;
                            eval2 = eval3;
                            eval3 = eval4;
                            eval4 = eval52;
                            j = 4609965796492119705L;
                            double d182 = d5;
                            d5 = d7;
                            d7 = d182;
                        }
                    } else {
                        if (eval > eval4) {
                            break;
                        }
                        if (!z) {
                        }
                    }
                } else {
                    double d19 = d4 - d15;
                    if ((d15 - d7) * d19 >= 0.0d) {
                        double eval6 = eval(univariateRealFunction, d15);
                        d4 = d15;
                        eval2 = eval3;
                        eval3 = eval4;
                        eval4 = eval6;
                        j = 4609965796492119705L;
                        double d20 = d5;
                        d5 = d7;
                        d7 = d20;
                    } else if (d19 * (d7 - d4) > 0.0d) {
                        double eval7 = eval(univariateRealFunction, d4);
                        if (z) {
                            if (eval7 >= eval4) {
                                j = 4609965796492119705L;
                                double d21 = eval3;
                                eval3 = eval4;
                                eval4 = eval7;
                                eval2 = d21;
                                double d22 = d5;
                                d5 = d7;
                                d7 = d22;
                            }
                            j = 4609965796492119705L;
                            double d23 = d4 + (d16 * GOLD);
                            eval2 = eval4;
                            eval4 = eval(univariateRealFunction, d23);
                            eval3 = eval7;
                            d5 = d4;
                            d4 = d23;
                        } else {
                            if (eval7 <= eval4) {
                                j = 4609965796492119705L;
                                double d212 = eval3;
                                eval3 = eval4;
                                eval4 = eval7;
                                eval2 = d212;
                                double d222 = d5;
                                d5 = d7;
                                d7 = d222;
                            }
                            j = 4609965796492119705L;
                            double d232 = d4 + (d16 * GOLD);
                            eval2 = eval4;
                            eval4 = eval(univariateRealFunction, d232);
                            eval3 = eval7;
                            d5 = d4;
                            d4 = d232;
                        }
                    } else {
                        j = 4609965796492119705L;
                        double d24 = d7 + (d14 * GOLD);
                        double d25 = eval3;
                        eval3 = eval4;
                        eval4 = eval(univariateRealFunction, d24);
                        d4 = d24;
                        eval2 = d25;
                        double d26 = d5;
                        d5 = d7;
                        d7 = d26;
                    }
                }
                double d27 = d7;
                d7 = d4;
                d6 = d27;
            } else {
                if (eval4 <= eval3) {
                    d3 = eval2;
                    break;
                }
                i = this.iterations + 1;
                this.iterations = i;
                if (i <= this.maxIterations) {
                }
            }
            this.lo = d5;
            this.mid = d4;
            this.hi = d7;
            this.fLo = r6;
            this.fMid = eval3;
            this.fHi = r1;
        }
        double d28 = eval4;
        double d29 = eval3;
        eval3 = eval;
        this.lo = d5;
        this.mid = d4;
        this.hi = d7;
        this.fLo = d29;
        this.fMid = eval3;
        this.fHi = d28;
    }

    public int getIterations() {
        return this.iterations;
    }

    public int getEvaluations() {
        return this.evaluations;
    }

    public double getLo() {
        return this.lo;
    }

    public double getFLow() {
        return this.fLo;
    }

    public double getHi() {
        return this.hi;
    }

    public double getFHi() {
        return this.fHi;
    }

    public double getMid() {
        return this.mid;
    }

    public double getFMid() {
        return this.fMid;
    }

    private double eval(org.apache.commons.math.analysis.UnivariateRealFunction univariateRealFunction, double d) throws org.apache.commons.math.FunctionEvaluationException {
        this.evaluations++;
        return univariateRealFunction.value(d);
    }

    private void reset() {
        this.iterations = 0;
        this.evaluations = 0;
    }
}
