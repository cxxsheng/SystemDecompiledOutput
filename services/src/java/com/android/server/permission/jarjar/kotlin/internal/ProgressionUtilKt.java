package com.android.server.permission.jarjar.kotlin.internal;

/* compiled from: progressionUtil.kt */
/* loaded from: classes2.dex */
public final class ProgressionUtilKt {
    private static final int mod(int a, int b) {
        int mod = a % b;
        return mod >= 0 ? mod : mod + b;
    }

    private static final int differenceModulo(int a, int b, int c) {
        return mod(mod(a, c) - mod(b, c), c);
    }

    public static final int getProgressionLastElement(int start, int end, int step) {
        if (step > 0) {
            if (start < end) {
                return end - differenceModulo(end, start, step);
            }
        } else {
            if (step >= 0) {
                throw new java.lang.IllegalArgumentException("Step is zero.");
            }
            if (start > end) {
                return differenceModulo(start, end, -step) + end;
            }
        }
        return end;
    }
}
