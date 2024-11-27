package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public class FastMath {
    public static final double E = 2.718281828459045d;
    private static final long HEX_40000000 = 1073741824;
    private static final double LN_2_A = 0.6931470632553101d;
    private static final double LN_2_B = 1.1730463525082348E-7d;
    private static final long MASK_30BITS = -1073741824;
    public static final double PI = 3.141592653589793d;
    private static final double TWO_POWER_52 = 4.503599627370496E15d;
    private static final double[] EXP_INT_TABLE_A = new double[android.net.util.NetworkConstants.ETHER_MTU];
    private static final double[] EXP_INT_TABLE_B = new double[android.net.util.NetworkConstants.ETHER_MTU];
    private static final double[] EXP_FRAC_TABLE_A = new double[com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_HANDSET];
    private static final double[] EXP_FRAC_TABLE_B = new double[com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_HANDSET];
    private static final double[] FACT = new double[20];
    private static final double[][] LN_MANT = new double[1024][];
    private static final double[][] LN_SPLIT_COEF = {new double[]{2.0d, 0.0d}, new double[]{0.6666666269302368d, 3.9736429850260626E-8d}, new double[]{0.3999999761581421d, 2.3841857910019882E-8d}, new double[]{0.2857142686843872d, 1.7029898543501842E-8d}, new double[]{0.2222222089767456d, 1.3245471311735498E-8d}, new double[]{0.1818181574344635d, 2.4384203044354907E-8d}, new double[]{0.1538461446762085d, 9.140260083262505E-9d}, new double[]{0.13333332538604736d, 9.220590270857665E-9d}, new double[]{0.11764700710773468d, 1.2393345855018391E-8d}, new double[]{0.10526403784751892d, 8.251545029714408E-9d}, new double[]{0.0952233225107193d, 1.2675934823758863E-8d}, new double[]{0.08713622391223907d, 1.1430250008909141E-8d}, new double[]{0.07842259109020233d, 2.404307984052299E-9d}, new double[]{0.08371849358081818d, 1.176342548272881E-8d}, new double[]{0.030589580535888672d, 1.2958646899018938E-9d}, new double[]{0.14982303977012634d, 1.225743062930824E-8d}};
    private static final double[][] LN_QUICK_COEF = {new double[]{1.0d, 5.669184079525E-24d}, new double[]{-0.25d, -0.25d}, new double[]{0.3333333134651184d, 1.986821492305628E-8d}, new double[]{-0.25d, -6.663542893624021E-14d}, new double[]{0.19999998807907104d, 1.1921056801463227E-8d}, new double[]{-0.1666666567325592d, -7.800414592973399E-9d}, new double[]{0.1428571343421936d, 5.650007086920087E-9d}, new double[]{-0.12502530217170715d, -7.44321345601866E-11d}, new double[]{0.11113807559013367d, 9.219544613762692E-9d}};
    private static final double[][] LN_HI_PREC_COEF = {new double[]{1.0d, -6.032174644509064E-23d}, new double[]{-0.25d, -0.25d}, new double[]{0.3333333134651184d, 1.9868161777724352E-8d}, new double[]{-0.2499999701976776d, -2.957007209750105E-8d}, new double[]{0.19999954104423523d, 1.5830993332061267E-10d}, new double[]{-0.16624879837036133d, -2.6033824355191673E-8d}};
    private static final double[] SINE_TABLE_A = new double[14];
    private static final double[] SINE_TABLE_B = new double[14];
    private static final double[] COSINE_TABLE_A = new double[14];
    private static final double[] COSINE_TABLE_B = new double[14];
    private static final double[] TANGENT_TABLE_A = new double[14];
    private static final double[] TANGENT_TABLE_B = new double[14];
    private static final long[] RECIP_2PI = {2935890503282001226L, 9154082963658192752L, 3952090531849364496L, 9193070505571053912L, 7910884519577875640L, 113236205062349959L, 4577762542105553359L, -5034868814120038111L, 4208363204685324176L, 5648769086999809661L, 2819561105158720014L, -4035746434778044925L, -302932621132653753L, -2644281811660520851L, -3183605296591799669L, 6722166367014452318L, -3512299194304650054L, -7278142539171889152L};
    private static final long[] PI_O_4_BITS = {-3958705157555305932L, -4267615245585081135L};
    private static final double[] EIGHTHS = {0.0d, 0.125d, 0.25d, 0.375d, 0.5d, 0.625d, 0.75d, 0.875d, 1.0d, 1.125d, 1.25d, 1.375d, 1.5d, 1.625d};
    private static final double[] CBRTTWO = {0.6299605249474366d, 0.7937005259840998d, 1.0d, 1.2599210498948732d, 1.5874010519681994d};

    static {
        FACT[0] = 1.0d;
        for (int i = 1; i < FACT.length; i++) {
            FACT[i] = FACT[i - 1] * i;
        }
        double[] dArr = new double[2];
        double[] dArr2 = new double[2];
        for (int i2 = 0; i2 < 750; i2++) {
            expint(i2, dArr);
            int i3 = i2 + 750;
            EXP_INT_TABLE_A[i3] = dArr[0];
            EXP_INT_TABLE_B[i3] = dArr[1];
            if (i2 != 0) {
                splitReciprocal(dArr, dArr2);
                int i4 = 750 - i2;
                EXP_INT_TABLE_A[i4] = dArr2[0];
                EXP_INT_TABLE_B[i4] = dArr2[1];
            }
        }
        for (int i5 = 0; i5 < EXP_FRAC_TABLE_A.length; i5++) {
            slowexp(i5 / 1024.0d, dArr);
            EXP_FRAC_TABLE_A[i5] = dArr[0];
            EXP_FRAC_TABLE_B[i5] = dArr[1];
        }
        for (int i6 = 0; i6 < LN_MANT.length; i6++) {
            LN_MANT[i6] = slowLog(java.lang.Double.longBitsToDouble((i6 << 42) | 4607182418800017408L));
        }
        buildSinCosTables();
    }

    private FastMath() {
    }

    private static double doubleHighPart(double d) {
        if (d > -2.2250738585072014E-308d && d < Double.MIN_NORMAL) {
            return d;
        }
        return java.lang.Double.longBitsToDouble(java.lang.Double.doubleToLongBits(d) & MASK_30BITS);
    }

    public static double sqrt(double d) {
        return java.lang.Math.sqrt(d);
    }

    public static double cosh(double d) {
        double d2 = d;
        if (d2 != d2) {
            return d2;
        }
        if (d2 > 20.0d) {
            return exp(d) / 2.0d;
        }
        if (d2 < -20.0d) {
            return exp(-d2) / 2.0d;
        }
        double[] dArr = new double[2];
        if (d2 < 0.0d) {
            d2 = -d2;
        }
        exp(d2, 0.0d, dArr);
        double d3 = dArr[0] + dArr[1];
        double d4 = -((d3 - dArr[0]) - dArr[1]);
        double d5 = d3 * 1.073741824E9d;
        double d6 = (d3 + d5) - d5;
        double d7 = d3 - d6;
        double d8 = 1.0d / d3;
        double d9 = 1.073741824E9d * d8;
        double d10 = (d8 + d9) - d9;
        double d11 = d8 - d10;
        double d12 = d11 + (((((1.0d - (d6 * d10)) - (d6 * d11)) - (d7 * d10)) - (d7 * d11)) * d8) + ((-d4) * d8 * d8);
        double d13 = d3 + d10;
        double d14 = d13 + d12;
        return (d14 + d4 + (-((d13 - d3) - d10)) + (-((d14 - d13) - d12))) * 0.5d;
    }

    public static double sinh(double d) {
        boolean z;
        double d2;
        double d3 = d;
        if (d3 != d3) {
            return d3;
        }
        if (d3 > 20.0d) {
            return exp(d) / 2.0d;
        }
        if (d3 < -20.0d) {
            return (-exp(-d3)) / 2.0d;
        }
        if (d3 == 0.0d) {
            return d3;
        }
        if (d3 >= 0.0d) {
            z = false;
        } else {
            d3 = -d3;
            z = true;
        }
        if (d3 > 0.25d) {
            double[] dArr = new double[2];
            exp(d3, 0.0d, dArr);
            double d4 = dArr[0] + dArr[1];
            double d5 = -((d4 - dArr[0]) - dArr[1]);
            double d6 = d4 * 1.073741824E9d;
            double d7 = (d4 + d6) - d6;
            double d8 = d4 - d7;
            double d9 = 1.0d / d4;
            double d10 = 1.073741824E9d * d9;
            double d11 = (d9 + d10) - d10;
            double d12 = d9 - d11;
            double d13 = d12 + (((((1.0d - (d7 * d11)) - (d7 * d12)) - (d8 * d11)) - (d8 * d12)) * d9) + ((-d5) * d9 * d9);
            double d14 = -d11;
            double d15 = -d13;
            double d16 = d4 + d14;
            double d17 = d5 + (-((d16 - d4) - d14));
            double d18 = d16 + d15;
            d2 = (d18 + d17 + (-((d18 - d16) - d15))) * 0.5d;
        } else {
            double[] dArr2 = new double[2];
            expm1(d3, dArr2);
            double d19 = dArr2[0] + dArr2[1];
            double d20 = -((d19 - dArr2[0]) - dArr2[1]);
            double d21 = d19 + 1.0d;
            double d22 = 1.0d / d21;
            double d23 = (-((d21 - 1.0d) - d19)) + d20;
            double d24 = d19 * d22;
            double d25 = d24 * 1.073741824E9d;
            double d26 = (d24 + d25) - d25;
            double d27 = d24 - d26;
            double d28 = 1.073741824E9d * d21;
            double d29 = (d21 + d28) - d28;
            double d30 = d21 - d29;
            double d31 = d27 + (((((d19 - (d29 * d26)) - (d29 * d27)) - (d30 * d26)) - (d30 * d27)) * d22) + (d20 * d22) + ((-d19) * d23 * d22 * d22);
            double d32 = d19 + d26;
            double d33 = d20 + (-((d32 - d19) - d26));
            double d34 = d32 + d31;
            d2 = (d34 + d33 + (-((d34 - d32) - d31))) * 0.5d;
        }
        if (z) {
            return -d2;
        }
        return d2;
    }

    public static double tanh(double d) {
        boolean z;
        double d2;
        double d3 = d;
        if (d3 != d3) {
            return d3;
        }
        if (d3 > 20.0d) {
            return 1.0d;
        }
        if (d3 < -20.0d) {
            return -1.0d;
        }
        if (d3 == 0.0d) {
            return d3;
        }
        if (d3 >= 0.0d) {
            z = false;
        } else {
            d3 = -d3;
            z = true;
        }
        if (d3 >= 0.5d) {
            double[] dArr = new double[2];
            exp(d3 * 2.0d, 0.0d, dArr);
            double d4 = dArr[0] + dArr[1];
            double d5 = -((d4 - dArr[0]) - dArr[1]);
            double d6 = (-1.0d) + d4;
            double d7 = d6 + d5;
            double d8 = (-((d6 + 1.0d) - d4)) + (-((d7 - d6) - d5));
            double d9 = d4 + 1.0d;
            double d10 = d9 + d5;
            double d11 = (-((d9 - 1.0d) - d4)) + (-((d10 - d9) - d5));
            double d12 = d10 * 1.073741824E9d;
            double d13 = (d10 + d12) - d12;
            double d14 = d10 - d13;
            double d15 = d7 / d10;
            double d16 = 1.073741824E9d * d15;
            double d17 = (d15 + d16) - d16;
            double d18 = d15 - d17;
            d2 = d17 + d18 + (((((d7 - (d13 * d17)) - (d13 * d18)) - (d14 * d17)) - (d14 * d18)) / d10) + (d8 / d10) + ((((-d11) * d7) / d10) / d10);
        } else {
            double[] dArr2 = new double[2];
            expm1(d3 * 2.0d, dArr2);
            double d19 = dArr2[0] + dArr2[1];
            double d20 = -((d19 - dArr2[0]) - dArr2[1]);
            double d21 = d19 + 2.0d;
            double d22 = d21 + d20;
            double d23 = (-((d21 - 2.0d) - d19)) + (-((d22 - d21) - d20));
            double d24 = d22 * 1.073741824E9d;
            double d25 = (d22 + d24) - d24;
            double d26 = d22 - d25;
            double d27 = d19 / d22;
            double d28 = 1.073741824E9d * d27;
            double d29 = (d27 + d28) - d28;
            double d30 = d27 - d29;
            d2 = d29 + d30 + (((((d19 - (d25 * d29)) - (d25 * d30)) - (d26 * d29)) - (d26 * d30)) / d22) + (d20 / d22) + ((((-d23) * d19) / d22) / d22);
        }
        if (z) {
            return -d2;
        }
        return d2;
    }

    public static double acosh(double d) {
        return log(d + sqrt((d * d) - 1.0d));
    }

    public static double asinh(double d) {
        boolean z;
        double d2;
        double d3 = d;
        if (d3 >= 0.0d) {
            z = false;
        } else {
            d3 = -d3;
            z = true;
        }
        if (d3 > 0.167d) {
            d2 = log(sqrt((d3 * d3) + 1.0d) + d3);
        } else {
            double d4 = d3 * d3;
            if (d3 > 0.097d) {
                d2 = d3 * (1.0d - ((d4 * (0.3333333333333333d - ((((0.2d - ((((0.14285714285714285d - ((((0.1111111111111111d - ((((0.09090909090909091d - ((((0.07692307692307693d - ((((0.06666666666666667d - (((0.058823529411764705d * d4) * 15.0d) / 16.0d)) * d4) * 13.0d) / 14.0d)) * d4) * 11.0d) / 12.0d)) * d4) * 9.0d) / 10.0d)) * d4) * 7.0d) / 8.0d)) * d4) * 5.0d) / 6.0d)) * d4) * 3.0d) / 4.0d))) / 2.0d));
            } else if (d3 > 0.036d) {
                d2 = d3 * (1.0d - ((d4 * (0.3333333333333333d - ((((0.2d - ((((0.14285714285714285d - ((((0.1111111111111111d - ((((0.09090909090909091d - (((0.07692307692307693d * d4) * 11.0d) / 12.0d)) * d4) * 9.0d) / 10.0d)) * d4) * 7.0d) / 8.0d)) * d4) * 5.0d) / 6.0d)) * d4) * 3.0d) / 4.0d))) / 2.0d));
            } else if (d3 > 0.0036d) {
                d2 = d3 * (1.0d - ((d4 * (0.3333333333333333d - ((((0.2d - ((((0.14285714285714285d - (((0.1111111111111111d * d4) * 7.0d) / 8.0d)) * d4) * 5.0d) / 6.0d)) * d4) * 3.0d) / 4.0d))) / 2.0d));
            } else {
                d2 = d3 * (1.0d - ((d4 * (0.3333333333333333d - (((0.2d * d4) * 3.0d) / 4.0d))) / 2.0d));
            }
        }
        return z ? -d2 : d2;
    }

    public static double atanh(double d) {
        boolean z;
        double d2;
        double d3 = d;
        if (d3 >= 0.0d) {
            z = false;
        } else {
            d3 = -d3;
            z = true;
        }
        if (d3 > 0.15d) {
            d2 = log((d3 + 1.0d) / (1.0d - d3)) * 0.5d;
        } else {
            double d4 = d3 * d3;
            if (d3 > 0.087d) {
                d2 = d3 * ((d4 * ((((((((((((((0.058823529411764705d * d4) + 0.06666666666666667d) * d4) + 0.07692307692307693d) * d4) + 0.09090909090909091d) * d4) + 0.1111111111111111d) * d4) + 0.14285714285714285d) * d4) + 0.2d) * d4) + 0.3333333333333333d)) + 1.0d);
            } else if (d3 > 0.031d) {
                d2 = d3 * ((d4 * ((((((((((0.07692307692307693d * d4) + 0.09090909090909091d) * d4) + 0.1111111111111111d) * d4) + 0.14285714285714285d) * d4) + 0.2d) * d4) + 0.3333333333333333d)) + 1.0d);
            } else if (d3 > 0.003d) {
                d2 = d3 * ((d4 * ((((((0.1111111111111111d * d4) + 0.14285714285714285d) * d4) + 0.2d) * d4) + 0.3333333333333333d)) + 1.0d);
            } else {
                d2 = d3 * ((d4 * ((0.2d * d4) + 0.3333333333333333d)) + 1.0d);
            }
        }
        return z ? -d2 : d2;
    }

    public static double signum(double d) {
        if (d < 0.0d) {
            return -1.0d;
        }
        if (d > 0.0d) {
            return 1.0d;
        }
        return d;
    }

    public static float signum(float f) {
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return -1.0f;
        }
        if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return 1.0f;
        }
        return f;
    }

    public static double nextUp(double d) {
        return nextAfter(d, Double.POSITIVE_INFINITY);
    }

    public static float nextUp(float f) {
        return nextAfter(f, Double.POSITIVE_INFINITY);
    }

    public static double random() {
        return java.lang.Math.random();
    }

    public static double exp(double d) {
        return exp(d, 0.0d, null);
    }

    private static double exp(double d, double d2, double[] dArr) {
        int i;
        double d3;
        double d4;
        double d5;
        if (d < 0.0d) {
            int i2 = (int) (-d);
            if (i2 > 746) {
                if (dArr != null) {
                    dArr[0] = 0.0d;
                    dArr[1] = 0.0d;
                }
                return 0.0d;
            }
            if (i2 > 709) {
                double exp = exp(d + 40.19140625d, d2, dArr) / 2.8504009514401178E17d;
                if (dArr != null) {
                    dArr[0] = dArr[0] / 2.8504009514401178E17d;
                    dArr[1] = dArr[1] / 2.8504009514401178E17d;
                }
                return exp;
            }
            if (i2 == 709) {
                double exp2 = exp(d + 1.494140625d, d2, dArr) / 4.455505956692757d;
                if (dArr != null) {
                    dArr[0] = dArr[0] / 4.455505956692757d;
                    dArr[1] = dArr[1] / 4.455505956692757d;
                }
                return exp2;
            }
            int i3 = i2 + 1;
            int i4 = 750 - i3;
            d3 = EXP_INT_TABLE_A[i4];
            d4 = EXP_INT_TABLE_B[i4];
            i = -i3;
        } else {
            i = (int) d;
            if (i > 709) {
                if (dArr != null) {
                    dArr[0] = Double.POSITIVE_INFINITY;
                    dArr[1] = 0.0d;
                }
                return Double.POSITIVE_INFINITY;
            }
            int i5 = i + 750;
            d3 = EXP_INT_TABLE_A[i5];
            d4 = EXP_INT_TABLE_B[i5];
        }
        double d6 = i;
        int i6 = (int) ((d - d6) * 1024.0d);
        double d7 = EXP_FRAC_TABLE_A[i6];
        double d8 = EXP_FRAC_TABLE_B[i6];
        double d9 = d - (d6 + (i6 / 1024.0d));
        double d10 = (((((((0.04168701738764507d * d9) + 0.1666666505023083d) * d9) + 0.5000000000042687d) * d9) + 1.0d) * d9) - 3.940510424527919E-20d;
        double d11 = d3 * d7;
        double d12 = (d3 * d8) + (d7 * d4) + (d4 * d8);
        double d13 = d12 + d11;
        if (d2 != 0.0d) {
            double d14 = d13 * d2;
            d5 = (d14 * d10) + d14 + (d13 * d10) + d12 + d11;
        } else {
            d5 = (d13 * d10) + d12 + d11;
        }
        if (dArr != null) {
            dArr[0] = d11;
            double d15 = d13 * d2;
            dArr[1] = (d15 * d10) + d15 + (d13 * d10) + d12;
        }
        return d5;
    }

    public static double expm1(double d) {
        return expm1(d, null);
    }

    private static double expm1(double d, double[] dArr) {
        boolean z;
        double d2 = d;
        if (d2 != d2 || d2 == 0.0d) {
            return d2;
        }
        if (d2 <= -1.0d || d2 >= 1.0d) {
            double[] dArr2 = new double[2];
            exp(d2, 0.0d, dArr2);
            if (d2 > 0.0d) {
                return (dArr2[0] - 1.0d) + dArr2[1];
            }
            double d3 = dArr2[0] - 1.0d;
            return d3 + (-((1.0d + d3) - dArr2[0])) + dArr2[1];
        }
        if (d2 >= 0.0d) {
            z = false;
        } else {
            d2 = -d2;
            z = true;
        }
        int i = (int) (d2 * 1024.0d);
        double d4 = EXP_FRAC_TABLE_A[i] - 1.0d;
        double d5 = EXP_FRAC_TABLE_B[i];
        double d6 = d4 + d5;
        double d7 = d6 * 1.073741824E9d;
        double d8 = (d6 + d7) - d7;
        double d9 = (-((d6 - d4) - d5)) + (d6 - d8);
        double d10 = d2 - (i / 1024.0d);
        double d11 = ((((((0.008336750013465571d * d10) + 0.041666663879186654d) * d10) + 0.16666666666745392d) * d10) + 0.49999999999999994d) * d10 * d10;
        double d12 = d10 + d11;
        double d13 = -((d12 - d10) - d11);
        double d14 = d12 * 1.073741824E9d;
        double d15 = (d12 + d14) - d14;
        double d16 = d13 + (d12 - d15);
        double d17 = d15 * d8;
        double d18 = d15 * d9;
        double d19 = d17 + d18;
        double d20 = -((d19 - d17) - d18);
        double d21 = d16 * d8;
        double d22 = d19 + d21;
        double d23 = d20 + (-((d22 - d19) - d21));
        double d24 = d16 * d9;
        double d25 = d22 + d24;
        double d26 = d23 + (-((d25 - d22) - d24));
        double d27 = d25 + d8;
        double d28 = d27 + d15;
        double d29 = d26 + (-((d27 - d8) - d25)) + (-((d28 - d27) - d15));
        double d30 = d28 + d9;
        double d31 = d30 + d16;
        double d32 = d29 + (-((d30 - d28) - d9)) + (-((d31 - d30) - d16));
        if (z) {
            double d33 = d31 + 1.0d;
            double d34 = 1.0d / d33;
            double d35 = (-((d33 - 1.0d) - d31)) + d32;
            double d36 = d31 * d34;
            double d37 = d36 * 1.073741824E9d;
            double d38 = (d36 + d37) - d37;
            double d39 = d36 - d38;
            double d40 = 1.073741824E9d * d33;
            double d41 = (d33 + d40) - d40;
            double d42 = d33 - d41;
            double d43 = d39 + (((((d31 - (d41 * d38)) - (d41 * d39)) - (d42 * d38)) - (d42 * d39)) * d34) + (d32 * d34) + ((-d31) * d35 * d34 * d34);
            d31 = -d38;
            d32 = -d43;
        }
        if (dArr != null) {
            dArr[0] = d31;
            dArr[1] = d32;
        }
        return d31 + d32;
    }

    private static double slowexp(double d, double[] dArr) {
        double[] dArr2 = new double[2];
        double[] dArr3 = new double[2];
        double[] dArr4 = new double[2];
        split(d, dArr2);
        double[] dArr5 = {0.0d, 0.0d};
        for (int i = 19; i >= 0; i--) {
            splitMult(dArr2, dArr5, dArr4);
            dArr5[0] = dArr4[0];
            dArr5[1] = dArr4[1];
            split(FACT[i], dArr4);
            splitReciprocal(dArr4, dArr3);
            splitAdd(dArr5, dArr3, dArr4);
            dArr5[0] = dArr4[0];
            dArr5[1] = dArr4[1];
        }
        if (dArr != null) {
            dArr[0] = dArr5[0];
            dArr[1] = dArr5[1];
        }
        return dArr5[0] + dArr5[1];
    }

    private static void split(double d, double[] dArr) {
        if (d < 8.0E298d && d > -8.0E298d) {
            double d2 = 1.073741824E9d * d;
            dArr[0] = (d + d2) - d2;
            dArr[1] = d - dArr[0];
        } else {
            dArr[0] = (((9.313225746154785E-10d * d) + d) - d) * 1.073741824E9d;
            dArr[1] = d - dArr[0];
        }
    }

    private static void resplit(double[] dArr) {
        double d = dArr[0] + dArr[1];
        double d2 = -((d - dArr[0]) - dArr[1]);
        if (d >= 8.0E298d || d <= -8.0E298d) {
            dArr[0] = (((9.313225746154785E-10d * d) + d) - d) * 1.073741824E9d;
            dArr[1] = (d - dArr[0]) + d2;
        } else {
            double d3 = 1.073741824E9d * d;
            dArr[0] = (d + d3) - d3;
            dArr[1] = (d - dArr[0]) + d2;
        }
    }

    private static void splitMult(double[] dArr, double[] dArr2, double[] dArr3) {
        dArr3[0] = dArr[0] * dArr2[0];
        dArr3[1] = (dArr[0] * dArr2[1]) + (dArr[1] * dArr2[0]) + (dArr[1] * dArr2[1]);
        resplit(dArr3);
    }

    private static void splitAdd(double[] dArr, double[] dArr2, double[] dArr3) {
        dArr3[0] = dArr[0] + dArr2[0];
        dArr3[1] = dArr[1] + dArr2[1];
        resplit(dArr3);
    }

    private static void splitReciprocal(double[] dArr, double[] dArr2) {
        if (dArr[0] == 0.0d) {
            dArr[0] = dArr[1];
            dArr[1] = 0.0d;
        }
        dArr2[0] = 0.9999997615814209d / dArr[0];
        dArr2[1] = ((dArr[0] * 2.384185791015625E-7d) - (dArr[1] * 0.9999997615814209d)) / ((dArr[0] * dArr[0]) + (dArr[0] * dArr[1]));
        if (dArr2[1] != dArr2[1]) {
            dArr2[1] = 0.0d;
        }
        resplit(dArr2);
        for (int i = 0; i < 2; i++) {
            dArr2[1] = dArr2[1] + (((((1.0d - (dArr2[0] * dArr[0])) - (dArr2[0] * dArr[1])) - (dArr2[1] * dArr[0])) - (dArr2[1] * dArr[1])) * (dArr2[0] + dArr2[1]));
        }
    }

    private static void quadMult(double[] dArr, double[] dArr2, double[] dArr3) {
        double[] dArr4 = new double[2];
        double[] dArr5 = new double[2];
        double[] dArr6 = new double[2];
        split(dArr[0], dArr4);
        split(dArr2[0], dArr5);
        splitMult(dArr4, dArr5, dArr6);
        dArr3[0] = dArr6[0];
        dArr3[1] = dArr6[1];
        split(dArr2[1], dArr5);
        splitMult(dArr4, dArr5, dArr6);
        double d = dArr3[0] + dArr6[0];
        dArr3[1] = dArr3[1] - ((d - dArr3[0]) - dArr6[0]);
        dArr3[0] = d;
        double d2 = dArr3[0] + dArr6[1];
        dArr3[1] = dArr3[1] - ((d2 - dArr3[0]) - dArr6[1]);
        dArr3[0] = d2;
        split(dArr[1], dArr4);
        split(dArr2[0], dArr5);
        splitMult(dArr4, dArr5, dArr6);
        double d3 = dArr3[0] + dArr6[0];
        dArr3[1] = dArr3[1] - ((d3 - dArr3[0]) - dArr6[0]);
        dArr3[0] = d3;
        double d4 = dArr3[0] + dArr6[1];
        dArr3[1] = dArr3[1] - ((d4 - dArr3[0]) - dArr6[1]);
        dArr3[0] = d4;
        split(dArr[1], dArr4);
        split(dArr2[1], dArr5);
        splitMult(dArr4, dArr5, dArr6);
        double d5 = dArr3[0] + dArr6[0];
        dArr3[1] = dArr3[1] - ((d5 - dArr3[0]) - dArr6[0]);
        dArr3[0] = d5;
        double d6 = dArr3[0] + dArr6[1];
        dArr3[1] = dArr3[1] - ((d6 - dArr3[0]) - dArr6[1]);
        dArr3[0] = d6;
    }

    private static double expint(int i, double[] dArr) {
        double[] dArr2 = new double[2];
        double[] dArr3 = new double[2];
        double[] dArr4 = {2.718281828459045d, 1.4456468917292502E-16d};
        split(1.0d, dArr3);
        while (i > 0) {
            if ((i & 1) != 0) {
                quadMult(dArr3, dArr4, dArr2);
                dArr3[0] = dArr2[0];
                dArr3[1] = dArr2[1];
            }
            quadMult(dArr4, dArr4, dArr2);
            dArr4[0] = dArr2[0];
            dArr4[1] = dArr2[1];
            i >>= 1;
        }
        if (dArr != null) {
            dArr[0] = dArr3[0];
            dArr[1] = dArr3[1];
            resplit(dArr);
        }
        return dArr3[0] + dArr3[1];
    }

    public static double log(double d) {
        return log(d, null);
    }

    private static double log(double d, double[] dArr) {
        double d2;
        double d3;
        if (d == 0.0d) {
            return Double.NEGATIVE_INFINITY;
        }
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        if (((Long.MIN_VALUE & doubleToLongBits) != 0 || d != d) && d != 0.0d) {
            if (dArr != null) {
                dArr[0] = Double.NaN;
            }
            return Double.NaN;
        }
        if (d == Double.POSITIVE_INFINITY) {
            if (dArr != null) {
                dArr[0] = Double.POSITIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        int i = ((int) (doubleToLongBits >> 52)) - 1023;
        if ((9218868437227405312L & doubleToLongBits) == 0) {
            if (d == 0.0d) {
                if (dArr != null) {
                    dArr[0] = Double.NEGATIVE_INFINITY;
                }
                return Double.NEGATIVE_INFINITY;
            }
            doubleToLongBits <<= 1;
            while ((4503599627370496L & doubleToLongBits) == 0) {
                i--;
                doubleToLongBits <<= 1;
            }
        }
        if ((i == -1 || i == 0) && d < 1.01d && d > 0.99d && dArr == null) {
            double d4 = d - 1.0d;
            double d5 = d4 * 1.073741824E9d;
            double d6 = (d4 + d5) - d5;
            double d7 = d4 - d6;
            double d8 = LN_QUICK_COEF[LN_QUICK_COEF.length - 1][0];
            double d9 = LN_QUICK_COEF[LN_QUICK_COEF.length - 1][1];
            for (int length = LN_QUICK_COEF.length - 2; length >= 0; length--) {
                double d10 = d8 * d6;
                double d11 = (d8 * d7) + (d9 * d6) + (d9 * d7);
                double d12 = d10 * 1.073741824E9d;
                double d13 = (d10 + d12) - d12;
                double d14 = (d10 - d13) + d11;
                double d15 = d13 + LN_QUICK_COEF[length][0];
                double d16 = d15 * 1.073741824E9d;
                d8 = (d15 + d16) - d16;
                d9 = (d15 - d8) + d14 + LN_QUICK_COEF[length][1];
            }
            double d17 = d8 * d6;
            double d18 = (d8 * d7) + (d6 * d9) + (d9 * d7);
            double d19 = 1.073741824E9d * d17;
            double d20 = (d17 + d19) - d19;
            return d20 + (d17 - d20) + d18;
        }
        long j = 4499201580859392L & doubleToLongBits;
        double[] dArr2 = LN_MANT[(int) (j >> 42)];
        double d21 = doubleToLongBits & 4398046511103L;
        double d22 = j + TWO_POWER_52;
        double d23 = d21 / d22;
        if (dArr != null) {
            double d24 = d23 * 1.073741824E9d;
            double d25 = (d23 + d24) - d24;
            double d26 = d23 - d25;
            double d27 = d26 + (((d21 - (d25 * d22)) - (d26 * d22)) / d22);
            double d28 = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1][0];
            double d29 = LN_HI_PREC_COEF[LN_HI_PREC_COEF.length - 1][1];
            for (int length2 = LN_HI_PREC_COEF.length - 2; length2 >= 0; length2--) {
                double d30 = d28 * d25;
                double d31 = (d28 * d27) + (d29 * d25) + (d29 * d27);
                double d32 = d30 * 1.073741824E9d;
                double d33 = (d30 + d32) - d32;
                double d34 = (d30 - d33) + d31;
                double d35 = d33 + LN_HI_PREC_COEF[length2][0];
                double d36 = d35 * 1.073741824E9d;
                d28 = (d35 + d36) - d36;
                d29 = (d35 - d28) + d34 + LN_HI_PREC_COEF[length2][1];
            }
            double d37 = d28 * d25;
            double d38 = (d28 * d27) + (d25 * d29) + (d29 * d27);
            d2 = d37 + d38;
            d3 = -((d2 - d37) - d38);
        } else {
            d2 = (((((((((((-0.16624882440418567d) * d23) + 0.19999954120254515d) * d23) - 0.2499999997677497d) * d23) + 0.3333333333332802d) * d23) - 0.5d) * d23) + 1.0d) * d23;
            d3 = 0.0d;
        }
        double d39 = i;
        double d40 = LN_2_A * d39;
        double d41 = dArr2[0] + d40;
        double d42 = d41 + d2;
        double d43 = (-((d41 - d40) - dArr2[0])) + 0.0d + (-((d42 - d41) - d2));
        double d44 = d39 * LN_2_B;
        double d45 = d42 + d44;
        double d46 = d43 + (-((d45 - d42) - d44));
        double d47 = dArr2[1] + d45;
        double d48 = d46 + (-((d47 - d45) - dArr2[1]));
        double d49 = d47 + d3;
        double d50 = d48 + (-((d49 - d47) - d3));
        if (dArr != null) {
            dArr[0] = d49;
            dArr[1] = d50;
        }
        return d49 + d50;
    }

    public static double log1p(double d) {
        double d2 = d + 1.0d;
        double d3 = -((d2 - 1.0d) - d);
        if (d == -1.0d) {
            return d / 0.0d;
        }
        if (d > 0.0d && 1.0d / d == 0.0d) {
            return d;
        }
        if (d > 1.0E-6d || d < -1.0E-6d) {
            double[] dArr = new double[2];
            double log = log(d2, dArr);
            if (java.lang.Double.isInfinite(log)) {
                return log;
            }
            double d4 = d3 / d2;
            return (((0.5d * d4) + 1.0d) * d4) + dArr[1] + dArr[0];
        }
        return ((((0.333333333333333d * d) - 0.5d) * d) + 1.0d) * d;
    }

    public static double log10(double d) {
        double[] dArr = new double[2];
        double log = log(d, dArr);
        if (java.lang.Double.isInfinite(log)) {
            return log;
        }
        double d2 = dArr[0] * 1.073741824E9d;
        double d3 = (dArr[0] + d2) - d2;
        double d4 = (dArr[0] - d3) + dArr[1];
        return (d4 * 1.9699272335463627E-8d) + (1.9699272335463627E-8d * d3) + (d4 * 0.4342944622039795d) + (d3 * 0.4342944622039795d);
    }

    public static double pow(double d, double d2) {
        double d3;
        double d4;
        double[] dArr = new double[2];
        if (d2 == 0.0d) {
            return 1.0d;
        }
        if (d != d) {
            return d;
        }
        if (d == 0.0d) {
            if ((java.lang.Double.doubleToLongBits(d) & Long.MIN_VALUE) != 0) {
                long j = (long) d2;
                if (d2 < 0.0d && d2 == j && (j & 1) == 1) {
                    return Double.NEGATIVE_INFINITY;
                }
                if (d2 < 0.0d && d2 == j && (j & 1) == 1) {
                    return -0.0d;
                }
                if (d2 > 0.0d && d2 == j && (j & 1) == 1) {
                    return -0.0d;
                }
            }
            if (d2 < 0.0d) {
                return Double.POSITIVE_INFINITY;
            }
            return d2 > 0.0d ? 0.0d : Double.NaN;
        }
        if (d == Double.POSITIVE_INFINITY) {
            return d2 != d2 ? d2 : d2 < 0.0d ? 0.0d : Double.POSITIVE_INFINITY;
        }
        if (d2 == Double.POSITIVE_INFINITY) {
            double d5 = d * d;
            if (d5 == 1.0d) {
                return Double.NaN;
            }
            return d5 > 1.0d ? Double.POSITIVE_INFINITY : 0.0d;
        }
        if (d == Double.NEGATIVE_INFINITY) {
            if (d2 != d2) {
                return d2;
            }
            if (d2 < 0.0d) {
                long j2 = (long) d2;
                return (d2 == ((double) j2) && (j2 & 1) == 1) ? -0.0d : 0.0d;
            }
            if (d2 > 0.0d) {
                long j3 = (long) d2;
                return (d2 == ((double) j3) && (j3 & 1) == 1) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
        }
        if (d2 == Double.NEGATIVE_INFINITY) {
            double d6 = d * d;
            if (d6 == 1.0d) {
                return Double.NaN;
            }
            return d6 < 1.0d ? Double.POSITIVE_INFINITY : 0.0d;
        }
        if (d < 0.0d) {
            if (d2 >= TWO_POWER_52 || d2 <= -4.503599627370496E15d) {
                return pow(-d, d2);
            }
            long j4 = (long) d2;
            if (d2 != j4) {
                return Double.NaN;
            }
            long j5 = j4 & 1;
            double pow = pow(-d, d2);
            return j5 == 0 ? pow : -pow;
        }
        if (d2 < 8.0E298d && d2 > -8.0E298d) {
            double d7 = d2 * 1.073741824E9d;
            d3 = (d2 + d7) - d7;
            d4 = d2 - d3;
        } else {
            double d8 = d2 * 9.313225746154785E-10d;
            d3 = (((9.313225746154785E-10d * d8) + d8) - d8) * 1.073741824E9d * 1.073741824E9d;
            d4 = d2 - d3;
        }
        double log = log(d, dArr);
        if (java.lang.Double.isInfinite(log)) {
            return log;
        }
        double d9 = dArr[0];
        double d10 = 1.073741824E9d * d9;
        double d11 = (d9 + d10) - d10;
        double d12 = dArr[1] + (d9 - d11);
        double d13 = d11 * d3;
        double d14 = (d11 * d4) + (d3 * d12) + (d12 * d4);
        double d15 = d13 + d14;
        double d16 = -((d15 - d13) - d14);
        return exp(d15, ((((((((0.008333333333333333d * d16) + 0.041666666666666664d) * d16) + 0.16666666666666666d) * d16) + 0.5d) * d16) + 1.0d) * d16, null);
    }

    private static double[] slowLog(double d) {
        double[] dArr = new double[2];
        double[] dArr2 = new double[2];
        double[] dArr3 = new double[2];
        split(d, dArr);
        dArr[0] = dArr[0] + 1.0d;
        resplit(dArr);
        splitReciprocal(dArr, dArr3);
        dArr[0] = dArr[0] - 2.0d;
        resplit(dArr);
        double[] dArr4 = {LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][0], LN_SPLIT_COEF[LN_SPLIT_COEF.length - 1][1]};
        splitMult(dArr, dArr3, dArr4);
        dArr[0] = dArr4[0];
        dArr[1] = dArr4[1];
        splitMult(dArr, dArr, dArr2);
        for (int length = LN_SPLIT_COEF.length - 2; length >= 0; length--) {
            splitMult(dArr4, dArr2, dArr3);
            dArr4[0] = dArr3[0];
            dArr4[1] = dArr3[1];
            splitAdd(dArr4, LN_SPLIT_COEF[length], dArr3);
            dArr4[0] = dArr3[0];
            dArr4[1] = dArr3[1];
        }
        splitMult(dArr4, dArr, dArr3);
        dArr4[0] = dArr3[0];
        dArr4[1] = dArr3[1];
        return dArr4;
    }

    private static double slowSin(double d, double[] dArr) {
        double[] dArr2 = new double[2];
        double[] dArr3 = new double[2];
        double[] dArr4 = new double[2];
        split(d, dArr2);
        double[] dArr5 = {0.0d, 0.0d};
        for (int i = 19; i >= 0; i--) {
            splitMult(dArr2, dArr5, dArr4);
            dArr5[0] = dArr4[0];
            dArr5[1] = dArr4[1];
            if ((i & 1) != 0) {
                split(FACT[i], dArr4);
                splitReciprocal(dArr4, dArr3);
                if ((i & 2) != 0) {
                    dArr3[0] = -dArr3[0];
                    dArr3[1] = -dArr3[1];
                }
                splitAdd(dArr5, dArr3, dArr4);
                dArr5[0] = dArr4[0];
                dArr5[1] = dArr4[1];
            }
        }
        if (dArr != null) {
            dArr[0] = dArr5[0];
            dArr[1] = dArr5[1];
        }
        return dArr5[0] + dArr5[1];
    }

    private static double slowCos(double d, double[] dArr) {
        double[] dArr2 = new double[2];
        double[] dArr3 = new double[2];
        double[] dArr4 = new double[2];
        split(d, dArr2);
        double[] dArr5 = {0.0d, 0.0d};
        for (int i = 19; i >= 0; i--) {
            splitMult(dArr2, dArr5, dArr4);
            dArr5[0] = dArr4[0];
            dArr5[1] = dArr4[1];
            if ((i & 1) == 0) {
                split(FACT[i], dArr4);
                splitReciprocal(dArr4, dArr3);
                if ((i & 2) != 0) {
                    dArr3[0] = -dArr3[0];
                    dArr3[1] = -dArr3[1];
                }
                splitAdd(dArr5, dArr3, dArr4);
                dArr5[0] = dArr4[0];
                dArr5[1] = dArr4[1];
            }
        }
        if (dArr != null) {
            dArr[0] = dArr5[0];
            dArr[1] = dArr5[1];
        }
        return dArr5[0] + dArr5[1];
    }

    private static void buildSinCosTables() {
        int i;
        double[] dArr = new double[2];
        int i2 = 0;
        while (true) {
            if (i2 >= 7) {
                break;
            }
            double d = i2 / 8.0d;
            slowSin(d, dArr);
            SINE_TABLE_A[i2] = dArr[0];
            SINE_TABLE_B[i2] = dArr[1];
            slowCos(d, dArr);
            COSINE_TABLE_A[i2] = dArr[0];
            COSINE_TABLE_B[i2] = dArr[1];
            i2++;
        }
        for (i = 7; i < 14; i++) {
            double[] dArr2 = new double[2];
            double[] dArr3 = new double[2];
            double[] dArr4 = new double[2];
            double[] dArr5 = new double[2];
            if ((i & 1) == 0) {
                int i3 = i / 2;
                dArr2[0] = SINE_TABLE_A[i3];
                dArr2[1] = SINE_TABLE_B[i3];
                dArr3[0] = COSINE_TABLE_A[i3];
                dArr3[1] = COSINE_TABLE_B[i3];
                splitMult(dArr2, dArr3, dArr);
                SINE_TABLE_A[i] = dArr[0] * 2.0d;
                SINE_TABLE_B[i] = dArr[1] * 2.0d;
                splitMult(dArr3, dArr3, dArr4);
                splitMult(dArr2, dArr2, dArr5);
                dArr5[0] = -dArr5[0];
                dArr5[1] = -dArr5[1];
                splitAdd(dArr4, dArr5, dArr);
                COSINE_TABLE_A[i] = dArr[0];
                COSINE_TABLE_B[i] = dArr[1];
            } else {
                int i4 = i / 2;
                dArr2[0] = SINE_TABLE_A[i4];
                dArr2[1] = SINE_TABLE_B[i4];
                dArr3[0] = COSINE_TABLE_A[i4];
                dArr3[1] = COSINE_TABLE_B[i4];
                int i5 = i4 + 1;
                dArr4[0] = SINE_TABLE_A[i5];
                dArr4[1] = SINE_TABLE_B[i5];
                double[] dArr6 = {COSINE_TABLE_A[i5], COSINE_TABLE_B[i5]};
                splitMult(dArr2, dArr6, dArr5);
                splitMult(dArr3, dArr4, dArr);
                splitAdd(dArr, dArr5, dArr);
                SINE_TABLE_A[i] = dArr[0];
                SINE_TABLE_B[i] = dArr[1];
                splitMult(dArr3, dArr6, dArr);
                splitMult(dArr2, dArr4, dArr5);
                dArr5[0] = -dArr5[0];
                dArr5[1] = -dArr5[1];
                splitAdd(dArr, dArr5, dArr);
                COSINE_TABLE_A[i] = dArr[0];
                COSINE_TABLE_B[i] = dArr[1];
            }
        }
        for (int i6 = 0; i6 < 14; i6++) {
            double[] dArr7 = new double[2];
            double[] dArr8 = {COSINE_TABLE_A[i6], COSINE_TABLE_B[i6]};
            splitReciprocal(dArr8, dArr7);
            splitMult(new double[]{SINE_TABLE_A[i6], SINE_TABLE_B[i6]}, dArr7, dArr8);
            TANGENT_TABLE_A[i6] = dArr8[0];
            TANGENT_TABLE_B[i6] = dArr8[1];
        }
    }

    private static double polySine(double d) {
        return ((((((2.7553817452272217E-6d * r0) - 1.9841269659586505E-4d) * r0) + 0.008333333333329196d) * r0) - 0.16666666666666666d) * d * d * d;
    }

    private static double polyCosine(double d) {
        return ((((((2.479773539153719E-5d * r4) - 0.0013888888689039883d) * r4) + 0.041666666666621166d) * r4) - 0.49999999999999994d) * d * d;
    }

    private static double sinQ(double d, double d2) {
        int i = (int) ((8.0d * d) + 0.5d);
        double d3 = d - EIGHTHS[i];
        double d4 = SINE_TABLE_A[i];
        double d5 = SINE_TABLE_B[i];
        double d6 = COSINE_TABLE_A[i];
        double d7 = COSINE_TABLE_B[i];
        double polySine = polySine(d3);
        double polyCosine = polyCosine(d3);
        double d8 = 1.073741824E9d * d3;
        double d9 = (d3 + d8) - d8;
        double d10 = polySine + (d3 - d9);
        double d11 = d4 + 0.0d;
        double d12 = d6 * d9;
        double d13 = d11 + d12;
        double d14 = (-((d11 - 0.0d) - d4)) + 0.0d + (-((d13 - d11) - d12)) + (d4 * polyCosine) + (d6 * d10) + d5 + (d7 * d9) + (d5 * polyCosine) + (d7 * d10);
        if (d2 != 0.0d) {
            double d15 = (((d6 + d7) * (polyCosine + 1.0d)) - ((d4 + d5) * (d9 + d10))) * d2;
            double d16 = d13 + d15;
            d14 += -((d16 - d13) - d15);
            d13 = d16;
        }
        return d13 + d14;
    }

    private static double cosQ(double d, double d2) {
        double d3 = 1.5707963267948966d - d;
        return sinQ(d3, (-((d3 - 1.5707963267948966d) + d)) + (6.123233995736766E-17d - d2));
    }

    private static double tanQ(double d, double d2, boolean z) {
        double d3;
        int i = (int) ((8.0d * d) + 0.5d);
        double d4 = d - EIGHTHS[i];
        double d5 = SINE_TABLE_A[i];
        double d6 = SINE_TABLE_B[i];
        double d7 = COSINE_TABLE_A[i];
        double d8 = COSINE_TABLE_B[i];
        double polySine = polySine(d4);
        double polyCosine = polyCosine(d4);
        double d9 = d4 * 1.073741824E9d;
        double d10 = (d4 + d9) - d9;
        double d11 = polySine + (d4 - d10);
        double d12 = d5 + 0.0d;
        double d13 = d7 * d10;
        double d14 = d12 + d13;
        double d15 = (-((d12 - 0.0d) - d5)) + 0.0d + (-((d14 - d12) - d13)) + (d5 * polyCosine) + (d7 * d11) + d6 + (d8 * d10) + (d6 * polyCosine) + (d8 * d11);
        double d16 = d14 + d15;
        double d17 = d7 * 1.0d;
        double d18 = d17 + 0.0d;
        double d19 = -((d16 - d14) - d15);
        double d20 = (-d5) * d10;
        double d21 = d18 + d20;
        double d22 = ((((((-((d18 - 0.0d) - d17)) + 0.0d) + (-((d21 - d18) - d20))) + (d8 * 1.0d)) + (d7 * polyCosine)) + (d8 * polyCosine)) - (((d10 * d6) + (d5 * d11)) + (d6 * d11));
        double d23 = d21 + d22;
        double d24 = -((d23 - d21) - d22);
        if (!z) {
            d3 = d16;
            d24 = d19;
            d19 = d24;
        } else {
            d3 = d23;
            d23 = d16;
        }
        double d25 = d3 / d23;
        double d26 = d25 * 1.073741824E9d;
        double d27 = (d25 + d26) - d26;
        double d28 = d25 - d27;
        double d29 = 1.073741824E9d * d23;
        double d30 = (d23 + d29) - d29;
        double d31 = d23 - d30;
        double d32 = (((((d3 - (d27 * d30)) - (d27 * d31)) - (d30 * d28)) - (d28 * d31)) / d23) + (d24 / d23) + ((((-d3) * d19) / d23) / d23);
        if (d2 != 0.0d) {
            double d33 = d2 + (d25 * d25 * d2);
            if (z) {
                d33 = -d33;
            }
            d32 += d33;
        }
        return d25 + d32;
    }

    private static void reducePayneHanek(double d, double[] dArr) {
        long j;
        long j2;
        long j3;
        int i = (((int) ((r0 >> 52) & 2047)) - 1023) + 1;
        long doubleToLongBits = ((java.lang.Double.doubleToLongBits(d) & 4503599627370495L) | 4503599627370496L) << 11;
        int i2 = i >> 6;
        int i3 = i - (i2 << 6);
        if (i3 != 0) {
            int i4 = 64 - i3;
            j = (i2 == 0 ? 0L : RECIP_2PI[i2 - 1] << i3) | (RECIP_2PI[i2] >>> i4);
            int i5 = i2 + 1;
            j2 = (RECIP_2PI[i2] << i3) | (RECIP_2PI[i5] >>> i4);
            j3 = (RECIP_2PI[i5] << i3) | (RECIP_2PI[i2 + 2] >>> i4);
        } else {
            j = i2 == 0 ? 0L : RECIP_2PI[i2 - 1];
            j2 = RECIP_2PI[i2];
            j3 = RECIP_2PI[i2 + 1];
        }
        long j4 = doubleToLongBits >>> 32;
        long j5 = doubleToLongBits & 4294967295L;
        long j6 = j2 >>> 32;
        long j7 = j2 & 4294967295L;
        long j8 = j4 * j6;
        long j9 = j5 * j7;
        long j10 = j6 * j5;
        long j11 = j7 * j4;
        long j12 = j9 + (j11 << 32);
        long j13 = j8 + (j11 >>> 32);
        boolean z = (j9 & Long.MIN_VALUE) != 0;
        boolean z2 = (j11 & 2147483648L) != 0;
        long j14 = j12 & Long.MIN_VALUE;
        boolean z3 = j14 != 0;
        if ((z && z2) || ((z || z2) && !z3)) {
            j13++;
        }
        boolean z4 = j14 != 0;
        boolean z5 = (j10 & 2147483648L) != 0;
        long j15 = j12 + (j10 << 32);
        long j16 = j13 + (j10 >>> 32);
        long j17 = j15 & Long.MIN_VALUE;
        boolean z6 = j17 != 0;
        if ((z4 && z5) || ((z4 || z5) && !z6)) {
            j16++;
        }
        long j18 = j3 >>> 32;
        long j19 = (j4 * j18) + (((j18 * j5) + ((j3 & 4294967295L) * j4)) >>> 32);
        boolean z7 = j17 != 0;
        boolean z8 = (j19 & Long.MIN_VALUE) != 0;
        long j20 = j15 + j19;
        boolean z9 = (j20 & Long.MIN_VALUE) != 0;
        if ((z7 && z8) || ((z7 || z8) && !z9)) {
            j16++;
        }
        long j21 = j >>> 32;
        long j22 = j & 4294967295L;
        long j23 = j16 + (j5 * j22) + (((j5 * j21) + (j4 * j22)) << 32);
        int i6 = (int) (j23 >>> 62);
        long j24 = (j23 << 2) | (j20 >>> 62);
        long j25 = j20 << 2;
        long j26 = j24 >>> 32;
        long j27 = j24 & 4294967295L;
        long j28 = PI_O_4_BITS[0] >>> 32;
        long j29 = PI_O_4_BITS[0] & 4294967295L;
        long j30 = j26 * j28;
        long j31 = j27 * j29;
        long j32 = j28 * j27;
        long j33 = j29 * j26;
        long j34 = j31 + (j33 << 32);
        long j35 = j30 + (j33 >>> 32);
        boolean z10 = (j31 & Long.MIN_VALUE) != 0;
        boolean z11 = (j33 & 2147483648L) != 0;
        long j36 = j34 & Long.MIN_VALUE;
        boolean z12 = j36 != 0;
        if ((z10 && z11) || ((z10 || z11) && !z12)) {
            j35++;
        }
        boolean z13 = j36 != 0;
        boolean z14 = (j32 & 2147483648L) != 0;
        long j37 = j34 + (j32 << 32);
        long j38 = j35 + (j32 >>> 32);
        long j39 = j37 & Long.MIN_VALUE;
        boolean z15 = j39 != 0;
        if ((z13 && z14) || ((z13 || z14) && !z15)) {
            j38++;
        }
        long j40 = PI_O_4_BITS[1] >>> 32;
        long j41 = (j26 * j40) + (((j27 * j40) + (j26 * (PI_O_4_BITS[1] & 4294967295L))) >>> 32);
        boolean z16 = j39 != 0;
        boolean z17 = (j41 & Long.MIN_VALUE) != 0;
        long j42 = j37 + j41;
        long j43 = j42 & Long.MIN_VALUE;
        boolean z18 = j43 != 0;
        if ((z16 && z17) || ((z16 || z17) && !z18)) {
            j38++;
        }
        long j44 = j25 >>> 32;
        long j45 = PI_O_4_BITS[0] >>> 32;
        long j46 = (j44 * j45) + ((((j25 & 4294967295L) * j45) + (j44 * (PI_O_4_BITS[0] & 4294967295L))) >>> 32);
        boolean z19 = j43 != 0;
        boolean z20 = (j46 & Long.MIN_VALUE) != 0;
        boolean z21 = ((j42 + j46) & Long.MIN_VALUE) != 0;
        if ((z19 && z20) || ((z19 || z20) && !z21)) {
            j38++;
        }
        double d2 = (j38 >>> 12) / TWO_POWER_52;
        double d3 = ((((j38 & 4095) << 40) + (r32 >>> 24)) / TWO_POWER_52) / TWO_POWER_52;
        double d4 = d2 + d3;
        dArr[0] = i6;
        dArr[1] = d4 * 2.0d;
        dArr[2] = (-((d4 - d2) - d3)) * 2.0d;
    }

    public static double sin(double d) {
        double d2;
        boolean z;
        double d3;
        double d4;
        double d5 = 0.0d;
        int i = 0;
        if (d >= 0.0d) {
            d2 = d;
            z = false;
        } else {
            d2 = -d;
            z = true;
        }
        if (d2 == 0.0d) {
            if (java.lang.Double.doubleToLongBits(d) >= 0) {
                return 0.0d;
            }
            return -0.0d;
        }
        if (d2 != d2 || d2 == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        if (d2 > 3294198.0d) {
            double[] dArr = new double[3];
            reducePayneHanek(d2, dArr);
            i = ((int) dArr[0]) & 3;
            d2 = dArr[1];
            d5 = dArr[2];
        } else if (d2 > 1.5707963267948966d) {
            int i2 = (int) (0.6366197723675814d * d2);
            while (true) {
                double d6 = -i2;
                double d7 = 1.570796251296997d * d6;
                double d8 = d2 + d7;
                double d9 = 7.549789948768648E-8d * d6;
                double d10 = d9 + d8;
                double d11 = (-((d8 - d2) - d7)) + (-((d10 - d8) - d9));
                double d12 = d6 * 6.123233995736766E-17d;
                d3 = d12 + d10;
                d4 = d11 + (-((d3 - d10) - d12));
                if (d3 > 0.0d) {
                    break;
                }
                i2--;
            }
            i = i2 & 3;
            d5 = d4;
            d2 = d3;
        }
        if (z) {
            i ^= 2;
        }
        switch (i) {
            case 0:
                return sinQ(d2, d5);
            case 1:
                return cosQ(d2, d5);
            case 2:
                return -sinQ(d2, d5);
            case 3:
                return -cosQ(d2, d5);
            default:
                return Double.NaN;
        }
    }

    public static double cos(double d) {
        double d2;
        double d3;
        double d4 = d;
        double d5 = 0.0d;
        if (d4 < 0.0d) {
            d4 = -d4;
        }
        if (d4 != d4 || d4 == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        int i = 0;
        if (d4 > 3294198.0d) {
            double[] dArr = new double[3];
            reducePayneHanek(d4, dArr);
            i = ((int) dArr[0]) & 3;
            d4 = dArr[1];
            d5 = dArr[2];
        } else if (d4 > 1.5707963267948966d) {
            int i2 = (int) (0.6366197723675814d * d4);
            while (true) {
                double d6 = -i2;
                double d7 = 1.570796251296997d * d6;
                double d8 = d4 + d7;
                double d9 = 7.549789948768648E-8d * d6;
                double d10 = d9 + d8;
                double d11 = (-((d8 - d4) - d7)) + (-((d10 - d8) - d9));
                double d12 = d6 * 6.123233995736766E-17d;
                d2 = d12 + d10;
                d3 = d11 + (-((d2 - d10) - d12));
                if (d2 > 0.0d) {
                    break;
                }
                i2--;
            }
            i = i2 & 3;
            d5 = d3;
            d4 = d2;
        }
        switch (i) {
            case 0:
                return cosQ(d4, d5);
            case 1:
                return -sinQ(d4, d5);
            case 2:
                return -cosQ(d4, d5);
            case 3:
                return sinQ(d4, d5);
            default:
                return Double.NaN;
        }
    }

    public static double tan(double d) {
        double d2;
        boolean z;
        int i;
        double d3;
        double d4;
        double d5;
        double d6 = 0.0d;
        if (d >= 0.0d) {
            d2 = d;
            z = false;
        } else {
            d2 = -d;
            z = true;
        }
        if (d2 == 0.0d) {
            if (java.lang.Double.doubleToLongBits(d) >= 0) {
                return 0.0d;
            }
            return -0.0d;
        }
        if (d2 != d2 || d2 == Double.POSITIVE_INFINITY) {
            return Double.NaN;
        }
        if (d2 > 3294198.0d) {
            double[] dArr = new double[3];
            reducePayneHanek(d2, dArr);
            i = 3 & ((int) dArr[0]);
            d2 = dArr[1];
            d6 = dArr[2];
        } else if (d2 > 1.5707963267948966d) {
            int i2 = (int) (0.6366197723675814d * d2);
            while (true) {
                double d7 = -i2;
                double d8 = 1.570796251296997d * d7;
                double d9 = d2 + d8;
                double d10 = d2;
                double d11 = -((d9 - d2) - d8);
                double d12 = 7.549789948768648E-8d * d7;
                double d13 = d12 + d9;
                double d14 = d7 * 6.123233995736766E-17d;
                d3 = d14 + d13;
                d4 = d11 + (-((d13 - d9) - d12)) + (-((d3 - d13) - d14));
                if (d3 > 0.0d) {
                    break;
                }
                i2--;
                d2 = d10;
            }
            i = 3 & i2;
            d6 = d4;
            d2 = d3;
        } else {
            i = 0;
        }
        if (d2 > 1.5d) {
            double d15 = 1.5707963267948966d - d2;
            double d16 = (-((d15 - 1.5707963267948966d) + d2)) + (6.123233995736766E-17d - d6);
            double d17 = d15 + d16;
            i ^= 1;
            z = !z;
            d6 = -((d17 - d15) - d16);
            d2 = d17;
        }
        if ((i & 1) == 0) {
            d5 = tanQ(d2, d6, false);
        } else {
            d5 = -tanQ(d2, d6, true);
        }
        if (z) {
            return -d5;
        }
        return d5;
    }

    public static double atan(double d) {
        return atan(d, 0.0d, false);
    }

    private static double atan(double d, double d2, boolean z) {
        double d3;
        boolean z2;
        int i;
        double d4;
        double d5;
        double d6 = d;
        if (d6 == 0.0d) {
            return z ? copySign(3.141592653589793d, d6) : d6;
        }
        if (d6 >= 0.0d) {
            d3 = d2;
            z2 = false;
        } else {
            d6 = -d6;
            d3 = -d2;
            z2 = true;
        }
        if (d6 > 1.633123935319537E16d) {
            return z2 ^ z ? -1.5707963267948966d : 1.5707963267948966d;
        }
        if (d6 < 1.0d) {
            i = (int) (((((-1.7168146928204135d) * d6 * d6) + 8.0d) * d6) + 0.5d);
        } else {
            double d7 = 1.0d / d6;
            i = (int) ((-((((-1.7168146928204135d) * d7 * d7) + 8.0d) * d7)) + 13.07d);
        }
        double d8 = d6 - TANGENT_TABLE_A[i];
        double d9 = (-((d8 - d6) + TANGENT_TABLE_A[i])) + (d3 - TANGENT_TABLE_B[i]);
        double d10 = d8 + d9;
        double d11 = -((d10 - d8) - d9);
        double d12 = d6 * 1.073741824E9d;
        double d13 = (d6 + d12) - d12;
        double d14 = d3 + ((d6 + d3) - d13);
        if (i == 0) {
            double d15 = 1.0d / (((d13 + d14) * (TANGENT_TABLE_A[i] + TANGENT_TABLE_B[i])) + 1.0d);
            d5 = d10 * d15;
            d4 = d11 * d15;
        } else {
            double d16 = TANGENT_TABLE_A[i] * d13;
            double d17 = d16 + 1.0d;
            double d18 = (TANGENT_TABLE_A[i] * d14) + (d13 * TANGENT_TABLE_B[i]);
            double d19 = d17 + d18;
            double d20 = (-((d17 - 1.0d) - d16)) + (-((d19 - d17) - d18)) + (d14 * TANGENT_TABLE_B[i]);
            double d21 = d10 / d19;
            double d22 = d21 * 1.073741824E9d;
            double d23 = (d21 + d22) - d22;
            double d24 = d21 - d23;
            double d25 = 1.073741824E9d * d19;
            double d26 = (d19 + d25) - d25;
            double d27 = d19 - d26;
            d4 = (((((d10 - (d23 * d26)) - (d23 * d27)) - (d26 * d24)) - (d24 * d27)) / d19) + ((((-d10) * d20) / d19) / d19) + (d11 / d19);
            d5 = d21;
        }
        double d28 = d5 * d5;
        double d29 = ((((((((((0.07490822288864472d * d28) - 0.09088450866185192d) * d28) + 0.11111095942313305d) * d28) - 0.1428571423679182d) * d28) + 0.19999999999923582d) * d28) - 0.33333333333333287d) * d28 * d5;
        double d30 = d5 + d29;
        double d31 = (-((d30 - d5) - d29)) + (d4 / (d28 + 1.0d));
        double d32 = EIGHTHS[i] + d30;
        double d33 = d32 + d31;
        double d34 = (-((d32 - EIGHTHS[i]) - d30)) + (-((d33 - d32) - d31));
        double d35 = d33 + d34;
        double d36 = -((d35 - d33) - d34);
        if (z) {
            double d37 = 3.141592653589793d - d35;
            d35 = (-((d37 - 3.141592653589793d) + d35)) + (1.2246467991473532E-16d - d36) + d37;
        }
        if (z2 ^ z) {
            return -d35;
        }
        return d35;
    }

    public static double atan2(double d, double d2) {
        if (d2 != d2 || d != d) {
            return Double.NaN;
        }
        if (d == 0.0d) {
            double d3 = d2 * d;
            double d4 = 1.0d / d2;
            double d5 = 1.0d / d;
            if (d4 == 0.0d) {
                if (d2 > 0.0d) {
                    return d;
                }
                return copySign(3.141592653589793d, d);
            }
            if (d2 < 0.0d || d4 < 0.0d) {
                return (d < 0.0d || d5 < 0.0d) ? -3.141592653589793d : 3.141592653589793d;
            }
            return d3;
        }
        if (d == Double.POSITIVE_INFINITY) {
            if (d2 == Double.POSITIVE_INFINITY) {
                return 0.7853981633974483d;
            }
            return d2 == Double.NEGATIVE_INFINITY ? 2.356194490192345d : 1.5707963267948966d;
        }
        if (d == Double.NEGATIVE_INFINITY) {
            if (d2 == Double.POSITIVE_INFINITY) {
                return -0.7853981633974483d;
            }
            return d2 == Double.NEGATIVE_INFINITY ? -2.356194490192345d : -1.5707963267948966d;
        }
        if (d2 == Double.POSITIVE_INFINITY) {
            if (d <= 0.0d) {
                double d6 = 1.0d / d;
                if (d6 <= 0.0d) {
                    if (d < 0.0d || d6 < 0.0d) {
                        return -0.0d;
                    }
                }
            }
            return 0.0d;
        }
        if (d2 == Double.NEGATIVE_INFINITY) {
            if (d <= 0.0d) {
                double d7 = 1.0d / d;
                if (d7 <= 0.0d) {
                    if (d < 0.0d || d7 < 0.0d) {
                        return -3.141592653589793d;
                    }
                }
            }
            return 3.141592653589793d;
        }
        if (d2 == 0.0d) {
            if (d <= 0.0d) {
                double d8 = 1.0d / d;
                if (d8 <= 0.0d) {
                    if (d < 0.0d || d8 < 0.0d) {
                        return -1.5707963267948966d;
                    }
                }
            }
            return 1.5707963267948966d;
        }
        double d9 = d / d2;
        if (java.lang.Double.isInfinite(d9)) {
            return atan(d9, 0.0d, d2 < 0.0d);
        }
        double doubleHighPart = doubleHighPart(d9);
        double d10 = d9 - doubleHighPart;
        double doubleHighPart2 = doubleHighPart(d2);
        double d11 = d2 - doubleHighPart2;
        double d12 = d10 + (((((d - (doubleHighPart * doubleHighPart2)) - (doubleHighPart * d11)) - (doubleHighPart2 * d10)) - (d11 * d10)) / d2);
        double d13 = doubleHighPart + d12;
        double d14 = -((d13 - doubleHighPart) - d12);
        if (d13 == 0.0d) {
            d13 = copySign(0.0d, d);
        }
        return atan(d13, d14, d2 < 0.0d);
    }

    public static double asin(double d) {
        if (d != d || d > 1.0d || d < -1.0d) {
            return Double.NaN;
        }
        if (d == 1.0d) {
            return 1.5707963267948966d;
        }
        if (d == -1.0d) {
            return -1.5707963267948966d;
        }
        if (d == 0.0d) {
            return d;
        }
        double d2 = d * 1.073741824E9d;
        double d3 = (d + d2) - d2;
        double d4 = d - d3;
        double d5 = d3 * d3;
        double d6 = (d3 * d4 * 2.0d) + (d4 * d4);
        double d7 = -d5;
        double d8 = -d6;
        double d9 = d7 + 1.0d;
        double d10 = d9 + d8;
        double d11 = (-((d9 - 1.0d) - d7)) + (-((d10 - d9) - d8));
        double sqrt = sqrt(d10);
        double d12 = sqrt * 1.073741824E9d;
        double d13 = (sqrt + d12) - d12;
        double d14 = sqrt - d13;
        double d15 = 2.0d * sqrt;
        double d16 = d14 + ((((d10 - (d13 * d13)) - ((d13 * 2.0d) * d14)) - (d14 * d14)) / d15);
        double d17 = d / sqrt;
        double d18 = 1.073741824E9d * d17;
        double d19 = (d17 + d18) - d18;
        double d20 = d17 - d19;
        double d21 = d20 + (((((d - (d19 * d13)) - (d19 * d16)) - (d13 * d20)) - (d16 * d20)) / sqrt) + ((((-d) * (d11 / d15)) / sqrt) / sqrt);
        double d22 = d19 + d21;
        return atan(d22, -((d22 - d19) - d21), false);
    }

    public static double acos(double d) {
        if (d != d || d > 1.0d || d < -1.0d) {
            return Double.NaN;
        }
        if (d == -1.0d) {
            return 3.141592653589793d;
        }
        if (d == 1.0d) {
            return 0.0d;
        }
        if (d == 0.0d) {
            return 1.5707963267948966d;
        }
        double d2 = d * 1.073741824E9d;
        double d3 = (d + d2) - d2;
        double d4 = d - d3;
        double d5 = -(d3 * d3);
        double d6 = -((d3 * d4 * 2.0d) + (d4 * d4));
        double d7 = d5 + 1.0d;
        double d8 = -((d7 - 1.0d) - d5);
        double d9 = d7 + d6;
        double d10 = d8 + (-((d9 - d7) - d6));
        double sqrt = sqrt(d9);
        double d11 = 1.073741824E9d * sqrt;
        double d12 = (sqrt + d11) - d11;
        double d13 = sqrt - d12;
        double d14 = sqrt * 2.0d;
        double d15 = d13 + ((((d9 - (d12 * d12)) - ((d12 * 2.0d) * d13)) - (d13 * d13)) / d14) + (d10 / d14);
        double d16 = d12 + d15;
        double d17 = -((d16 - d12) - d15);
        double d18 = d16 / d;
        if (java.lang.Double.isInfinite(d18)) {
            return 1.5707963267948966d;
        }
        double doubleHighPart = doubleHighPart(d18);
        double d19 = d18 - doubleHighPart;
        double d20 = d19 + (((((d16 - (doubleHighPart * d3)) - (doubleHighPart * d4)) - (d3 * d19)) - (d4 * d19)) / d) + (d17 / d);
        double d21 = doubleHighPart + d20;
        return atan(d21, -((d21 - doubleHighPart) - d20), d < 0.0d);
    }

    public static double cbrt(double d) {
        boolean z;
        int i;
        long j;
        double d2;
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        int i2 = ((int) ((doubleToLongBits >> 52) & 2047)) - 1023;
        if (i2 != -1023) {
            z = false;
            i = i2;
            j = doubleToLongBits;
            d2 = d;
        } else {
            if (d == 0.0d) {
                return d;
            }
            d2 = 1.8014398509481984E16d * d;
            j = java.lang.Double.doubleToLongBits(d2);
            i = ((int) (2047 & (j >> 52))) - 1023;
            z = true;
        }
        if (i != 1024) {
            double longBitsToDouble = java.lang.Double.longBitsToDouble((Long.MIN_VALUE & j) | ((((i / 3) + 1023) & 2047) << 52));
            double longBitsToDouble2 = java.lang.Double.longBitsToDouble((j & 4503599627370495L) | 4607182418800017408L);
            double d3 = (((((((((-0.010714690733195933d) * longBitsToDouble2) + 0.0875862700108075d) * longBitsToDouble2) - 0.3058015757857271d) * longBitsToDouble2) + 0.7249995199969751d) * longBitsToDouble2) + 0.5039018405998233d) * CBRTTWO[(i % 3) + 2];
            double d4 = d2 / ((longBitsToDouble * longBitsToDouble) * longBitsToDouble);
            double d5 = d3 + ((d4 - ((d3 * d3) * d3)) / ((d3 * 3.0d) * d3));
            double d6 = d5 + ((d4 - ((d5 * d5) * d5)) / ((d5 * 3.0d) * d5));
            double d7 = d6 * 1.073741824E9d;
            double d8 = (d6 + d7) - d7;
            double d9 = d6 - d8;
            double d10 = d8 * d8;
            double d11 = 1.073741824E9d * d10;
            double d12 = (d10 + d11) - d11;
            double d13 = (d8 * d9 * 2.0d) + (d9 * d9) + (d10 - d12);
            double d14 = (d12 * d9) + (d8 * d13) + (d13 * d9);
            double d15 = d12 * d8;
            double d16 = d4 - d15;
            double d17 = (d6 + ((d16 + ((-((d16 - d4) + d15)) - d14)) / ((3.0d * d6) * d6))) * longBitsToDouble;
            if (z) {
                return d17 * 3.814697265625E-6d;
            }
            return d17;
        }
        return d2;
    }

    public static double toRadians(double d) {
        if (java.lang.Double.isInfinite(d) || d == 0.0d) {
            return d;
        }
        double doubleHighPart = doubleHighPart(d);
        double d2 = d - doubleHighPart;
        double d3 = (d2 * 1.997844754509471E-9d) + (d2 * 0.01745329052209854d) + (1.997844754509471E-9d * doubleHighPart) + (doubleHighPart * 0.01745329052209854d);
        if (d3 == 0.0d) {
            return d3 * d;
        }
        return d3;
    }

    public static double toDegrees(double d) {
        if (java.lang.Double.isInfinite(d) || d == 0.0d) {
            return d;
        }
        double doubleHighPart = doubleHighPart(d);
        double d2 = d - doubleHighPart;
        return (d2 * 3.145894820876798E-6d) + (d2 * 57.2957763671875d) + (3.145894820876798E-6d * doubleHighPart) + (doubleHighPart * 57.2957763671875d);
    }

    public static int abs(int i) {
        return i < 0 ? -i : i;
    }

    public static long abs(long j) {
        return j < 0 ? -j : j;
    }

    public static float abs(float f) {
        return f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? -f : f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : f;
    }

    public static double abs(double d) {
        if (d < 0.0d) {
            return -d;
        }
        if (d == 0.0d) {
            return 0.0d;
        }
        return d;
    }

    public static double ulp(double d) {
        if (java.lang.Double.isInfinite(d)) {
            return Double.POSITIVE_INFINITY;
        }
        return abs(d - java.lang.Double.longBitsToDouble(java.lang.Double.doubleToLongBits(d) ^ 1));
    }

    public static float ulp(float f) {
        if (java.lang.Float.isInfinite(f)) {
            return Float.POSITIVE_INFINITY;
        }
        return abs(f - java.lang.Float.intBitsToFloat(java.lang.Float.floatToIntBits(f) ^ 1));
    }

    public static double scalb(double d, int i) {
        if (i > -1023 && i < 1024) {
            return java.lang.Double.longBitsToDouble((i + 1023) << 52) * d;
        }
        if (!java.lang.Double.isNaN(d) && !java.lang.Double.isInfinite(d) && d != 0.0d) {
            if (i < -2098) {
                return d > 0.0d ? 0.0d : -0.0d;
            }
            if (i > 2097) {
                return d > 0.0d ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
            long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
            long j = Long.MIN_VALUE & doubleToLongBits;
            int i2 = ((int) (doubleToLongBits >>> 52)) & 2047;
            long j2 = doubleToLongBits & 4503599627370495L;
            int i3 = i2 + i;
            if (i < 0) {
                if (i3 > 0) {
                    return java.lang.Double.longBitsToDouble((i3 << 52) | j | j2);
                }
                if (i3 <= -53) {
                    return j == 0 ? 0.0d : -0.0d;
                }
                long j3 = 4503599627370496L | j2;
                long j4 = (1 << (-i3)) & j3;
                long j5 = j3 >>> (1 - i3);
                if (j4 != 0) {
                    j5++;
                }
                return java.lang.Double.longBitsToDouble(j5 | j);
            }
            if (i2 == 0) {
                while ((j2 >>> 52) != 1) {
                    j2 <<= 1;
                    i3--;
                }
                int i4 = i3 + 1;
                long j6 = j2 & 4503599627370495L;
                if (i4 < 2047) {
                    return java.lang.Double.longBitsToDouble(j6 | (i4 << 52) | j);
                }
                return j == 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
            if (i3 < 2047) {
                return java.lang.Double.longBitsToDouble((i3 << 52) | j | j2);
            }
            return j == 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
        }
        return d;
    }

    public static float scalb(float f, int i) {
        if (i > -127 && i < 128) {
            return f * java.lang.Float.intBitsToFloat((i + 127) << 23);
        }
        if (java.lang.Float.isNaN(f) || java.lang.Float.isInfinite(f) || f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return f;
        }
        if (i < -277) {
            if (f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            }
            return -0.0f;
        }
        if (i > 276) {
            return f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
        }
        int floatToIntBits = java.lang.Float.floatToIntBits(f);
        int i2 = Integer.MIN_VALUE & floatToIntBits;
        int i3 = (floatToIntBits >>> 23) & 255;
        int i4 = floatToIntBits & 8388607;
        int i5 = i3 + i;
        if (i < 0) {
            if (i5 > 0) {
                return java.lang.Float.intBitsToFloat(i4 | (i5 << 23) | i2);
            }
            if (i5 > -24) {
                int i6 = i4 | 8388608;
                int i7 = (1 << (-i5)) & i6;
                int i8 = i6 >>> (1 - i5);
                if (i7 != 0) {
                    i8++;
                }
                return java.lang.Float.intBitsToFloat(i8 | i2);
            }
            if (i2 == 0) {
                return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            }
            return -0.0f;
        }
        if (i3 == 0) {
            while ((i4 >>> 23) != 1) {
                i4 <<= 1;
                i5--;
            }
            int i9 = i5 + 1;
            int i10 = i4 & 8388607;
            if (i9 < 255) {
                return java.lang.Float.intBitsToFloat(i10 | (i9 << 23) | i2);
            }
            return i2 == 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
        }
        if (i5 < 255) {
            return java.lang.Float.intBitsToFloat(i4 | (i5 << 23) | i2);
        }
        return i2 == 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
    }

    public static double nextAfter(double d, double d2) {
        if (java.lang.Double.isNaN(d) || java.lang.Double.isNaN(d2)) {
            return Double.NaN;
        }
        if (d == d2) {
            return d2;
        }
        if (java.lang.Double.isInfinite(d)) {
            return d < 0.0d ? -1.7976931348623157E308d : Double.MAX_VALUE;
        }
        if (d == 0.0d) {
            return d2 < 0.0d ? -4.9E-324d : Double.MIN_VALUE;
        }
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        long j = Long.MIN_VALUE & doubleToLongBits;
        if ((d2 < d) ^ (j == 0)) {
            return java.lang.Double.longBitsToDouble(j | ((doubleToLongBits & com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) + 1));
        }
        return java.lang.Double.longBitsToDouble(j | ((doubleToLongBits & com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) - 1));
    }

    public static float nextAfter(float f, double d) {
        double d2 = f;
        if (java.lang.Double.isNaN(d2) || java.lang.Double.isNaN(d)) {
            return Float.NaN;
        }
        if (d2 == d) {
            return (float) d;
        }
        if (java.lang.Float.isInfinite(f)) {
            return f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? -3.4028235E38f : Float.MAX_VALUE;
        }
        if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return d < 0.0d ? -1.4E-45f : Float.MIN_VALUE;
        }
        int floatToIntBits = java.lang.Float.floatToIntBits(f);
        int i = Integer.MIN_VALUE & floatToIntBits;
        if ((d < d2) ^ (i == 0)) {
            return java.lang.Float.intBitsToFloat(((floatToIntBits & Integer.MAX_VALUE) + 1) | i);
        }
        return java.lang.Float.intBitsToFloat(((floatToIntBits & Integer.MAX_VALUE) - 1) | i);
    }

    public static double floor(double d) {
        if (d != d) {
            return d;
        }
        if (d >= TWO_POWER_52 || d <= -4.503599627370496E15d) {
            return d;
        }
        long j = (long) d;
        if (d < 0.0d && j != d) {
            j--;
        }
        if (j == 0) {
            return d * j;
        }
        return j;
    }

    public static double ceil(double d) {
        if (d != d) {
            return d;
        }
        double floor = floor(d);
        if (floor == d) {
            return floor;
        }
        double d2 = floor + 1.0d;
        if (d2 == 0.0d) {
            return d * d2;
        }
        return d2;
    }

    public static double rint(double d) {
        double floor = floor(d);
        double d2 = d - floor;
        if (d2 > 0.5d) {
            if (floor == -1.0d) {
                return -0.0d;
            }
            return floor + 1.0d;
        }
        if (d2 < 0.5d) {
            return floor;
        }
        return (((long) floor) & 1) == 0 ? floor : floor + 1.0d;
    }

    public static long round(double d) {
        return (long) floor(d + 0.5d);
    }

    public static int round(float f) {
        return (int) floor(f + 0.5f);
    }

    public static int min(int i, int i2) {
        return i <= i2 ? i : i2;
    }

    public static long min(long j, long j2) {
        return j <= j2 ? j : j2;
    }

    public static float min(float f, float f2) {
        if (f > f2) {
            return f2;
        }
        if (f < f2) {
            return f;
        }
        if (f != f2) {
            return Float.NaN;
        }
        if (java.lang.Float.floatToRawIntBits(f) == Integer.MIN_VALUE) {
            return f;
        }
        return f2;
    }

    public static double min(double d, double d2) {
        if (d > d2) {
            return d2;
        }
        if (d < d2) {
            return d;
        }
        if (d != d2) {
            return Double.NaN;
        }
        if (java.lang.Double.doubleToRawLongBits(d) == Long.MIN_VALUE) {
            return d;
        }
        return d2;
    }

    public static int max(int i, int i2) {
        return i <= i2 ? i2 : i;
    }

    public static long max(long j, long j2) {
        return j <= j2 ? j2 : j;
    }

    public static float max(float f, float f2) {
        if (f > f2) {
            return f;
        }
        if (f < f2) {
            return f2;
        }
        if (f != f2) {
            return Float.NaN;
        }
        if (java.lang.Float.floatToRawIntBits(f) == Integer.MIN_VALUE) {
            return f2;
        }
        return f;
    }

    public static double max(double d, double d2) {
        if (d > d2) {
            return d;
        }
        if (d < d2) {
            return d2;
        }
        if (d != d2) {
            return Double.NaN;
        }
        if (java.lang.Double.doubleToRawLongBits(d) == Long.MIN_VALUE) {
            return d2;
        }
        return d;
    }

    public static double hypot(double d, double d2) {
        if (java.lang.Double.isInfinite(d) || java.lang.Double.isInfinite(d2)) {
            return Double.POSITIVE_INFINITY;
        }
        if (java.lang.Double.isNaN(d) || java.lang.Double.isNaN(d2)) {
            return Double.NaN;
        }
        int exponent = getExponent(d);
        int exponent2 = getExponent(d2);
        if (exponent > exponent2 + 27) {
            return abs(d);
        }
        if (exponent2 > exponent + 27) {
            return abs(d2);
        }
        int i = (exponent + exponent2) / 2;
        int i2 = -i;
        double scalb = scalb(d, i2);
        double scalb2 = scalb(d2, i2);
        return scalb(sqrt((scalb * scalb) + (scalb2 * scalb2)), i);
    }

    public static double IEEEremainder(double d, double d2) {
        return java.lang.StrictMath.IEEEremainder(d, d2);
    }

    public static double copySign(double d, double d2) {
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        long doubleToLongBits2 = java.lang.Double.doubleToLongBits(d2);
        if ((doubleToLongBits >= 0 && doubleToLongBits2 >= 0) || (doubleToLongBits < 0 && doubleToLongBits2 < 0)) {
            return d;
        }
        return -d;
    }

    public static float copySign(float f, float f2) {
        int floatToIntBits = java.lang.Float.floatToIntBits(f);
        int floatToIntBits2 = java.lang.Float.floatToIntBits(f2);
        if ((floatToIntBits >= 0 && floatToIntBits2 >= 0) || (floatToIntBits < 0 && floatToIntBits2 < 0)) {
            return f;
        }
        return -f;
    }

    public static int getExponent(double d) {
        return ((int) ((java.lang.Double.doubleToLongBits(d) >>> 52) & 2047)) - 1023;
    }

    public static int getExponent(float f) {
        return ((java.lang.Float.floatToIntBits(f) >>> 23) & 255) - 127;
    }
}
