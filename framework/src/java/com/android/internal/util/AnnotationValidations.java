package com.android.internal.util;

/* loaded from: classes5.dex */
public class AnnotationValidations {
    private AnnotationValidations() {
    }

    public static void validate(java.lang.Class<android.annotation.UserIdInt> cls, android.annotation.UserIdInt userIdInt, int i) {
        if ((i != -10000 && i < -3) || i > 21474) {
            invalid(cls, java.lang.Integer.valueOf(i));
        }
    }

    public static void validate(java.lang.Class<android.annotation.AppIdInt> cls, android.annotation.AppIdInt appIdInt, int i) {
        if (i / 100000 != 0 || i < 0) {
            invalid(cls, java.lang.Integer.valueOf(i));
        }
    }

    public static void validate(java.lang.Class<android.annotation.IntRange> cls, android.annotation.IntRange intRange, int i, java.lang.String str, long j, java.lang.String str2, long j2) {
        validate(cls, intRange, i, str, j);
        validate(cls, intRange, i, str2, j2);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void validate(java.lang.Class<android.annotation.IntRange> cls, android.annotation.IntRange intRange, int i, java.lang.String str, long j) {
        char c;
        switch (str.hashCode()) {
            case 3707:
                if (str.equals("to")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3151786:
                if (str.equals("from")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (i < j) {
                    invalid(cls, java.lang.Integer.valueOf(i), str, java.lang.Long.valueOf(j));
                    break;
                }
                break;
            case 1:
                if (i > j) {
                    invalid(cls, java.lang.Integer.valueOf(i), str, java.lang.Long.valueOf(j));
                    break;
                }
                break;
        }
    }

    public static void validate(java.lang.Class<android.annotation.IntRange> cls, android.annotation.IntRange intRange, long j, java.lang.String str, long j2, java.lang.String str2, long j3) {
        validate(cls, intRange, j, str, j2);
        validate(cls, intRange, j, str2, j3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void validate(java.lang.Class<android.annotation.IntRange> cls, android.annotation.IntRange intRange, long j, java.lang.String str, long j2) {
        char c;
        switch (str.hashCode()) {
            case 3707:
                if (str.equals("to")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3151786:
                if (str.equals("from")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (j < j2) {
                    invalid(cls, java.lang.Long.valueOf(j), str, java.lang.Long.valueOf(j2));
                    break;
                }
                break;
            case 1:
                if (j > j2) {
                    invalid(cls, java.lang.Long.valueOf(j), str, java.lang.Long.valueOf(j2));
                    break;
                }
                break;
        }
    }

    public static void validate(java.lang.Class<android.annotation.FloatRange> cls, android.annotation.FloatRange floatRange, float f, java.lang.String str, float f2, java.lang.String str2, float f3) {
        validate(cls, floatRange, f, str, f2);
        validate(cls, floatRange, f, str2, f3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void validate(java.lang.Class<android.annotation.FloatRange> cls, android.annotation.FloatRange floatRange, float f, java.lang.String str, float f2) {
        char c;
        switch (str.hashCode()) {
            case 3707:
                if (str.equals("to")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3151786:
                if (str.equals("from")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (f < f2) {
                    invalid(cls, java.lang.Float.valueOf(f), str, java.lang.Float.valueOf(f2));
                    break;
                }
                break;
            case 1:
                if (f > f2) {
                    invalid(cls, java.lang.Float.valueOf(f), str, java.lang.Float.valueOf(f2));
                    break;
                }
                break;
        }
    }

    public static void validate(java.lang.Class<android.annotation.NonNull> cls, android.annotation.NonNull nonNull, java.lang.Object obj) {
        if (obj == null) {
            throw new java.lang.NullPointerException();
        }
    }

    public static void validate(java.lang.Class<android.annotation.Size> cls, android.annotation.Size size, int i, java.lang.String str, int i2, java.lang.String str2, int i3) {
        validate(cls, size, i, str, i2);
        validate(cls, size, i, str2, i3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static void validate(java.lang.Class<android.annotation.Size> cls, android.annotation.Size size, int i, java.lang.String str, int i2) {
        char c;
        switch (str.hashCode()) {
            case 107876:
                if (str.equals("max")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 108114:
                if (str.equals("min")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 111972721:
                if (str.equals("value")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 653829648:
                if (str.equals("multiple")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (i2 != -1 && i != i2) {
                    invalid(cls, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2));
                    break;
                }
                break;
            case 1:
                if (i < i2) {
                    invalid(cls, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2));
                    break;
                }
                break;
            case 2:
                if (i > i2) {
                    invalid(cls, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2));
                    break;
                }
                break;
            case 3:
                if (i % i2 != 0) {
                    invalid(cls, java.lang.Integer.valueOf(i), str, java.lang.Integer.valueOf(i2));
                    break;
                }
                break;
        }
    }

    public static void validate(java.lang.Class<android.content.pm.PackageManager.PermissionResult> cls, android.content.pm.PackageManager.PermissionResult permissionResult, int i) {
        validateIntEnum(cls, i, 0);
    }

    public static void validate(java.lang.Class<android.content.pm.PackageManager.PackageInfoFlagsBits> cls, android.content.pm.PackageManager.PackageInfoFlagsBits packageInfoFlagsBits, long j) {
        validateLongFlags(cls, j, com.android.internal.util.BitUtils.flagsUpTo(536870912));
    }

    public static void validate(java.lang.Class<android.content.Intent.Flags> cls, android.content.Intent.Flags flags, int i) {
        validateIntFlags(cls, i, com.android.internal.util.BitUtils.flagsUpTo(Integer.MIN_VALUE));
    }

    @java.lang.Deprecated
    public static void validate(java.lang.Class<? extends java.lang.annotation.Annotation> cls, java.lang.annotation.Annotation annotation, java.lang.Object obj, java.lang.Object... objArr) {
    }

    @java.lang.Deprecated
    public static void validate(java.lang.Class<? extends java.lang.annotation.Annotation> cls, java.lang.annotation.Annotation annotation, java.lang.Object obj) {
    }

    @java.lang.Deprecated
    public static void validate(java.lang.Class<? extends java.lang.annotation.Annotation> cls, java.lang.annotation.Annotation annotation, int i, java.lang.Object... objArr) {
    }

    public static void validate(java.lang.Class<? extends java.lang.annotation.Annotation> cls, java.lang.annotation.Annotation annotation, int i) {
        if ((("android.annotation".equals(cls.getPackageName()) && cls.getSimpleName().endsWith("Res")) || android.annotation.ColorInt.class.equals(cls)) && i < 0) {
            invalid(cls, java.lang.Integer.valueOf(i));
        }
    }

    public static void validate(java.lang.Class<? extends java.lang.annotation.Annotation> cls, java.lang.annotation.Annotation annotation, long j) {
        if ("android.annotation".equals(cls.getPackageName()) && cls.getSimpleName().endsWith("Long") && j < 0) {
            invalid(cls, java.lang.Long.valueOf(j));
        }
    }

    private static void validateIntEnum(java.lang.Class<? extends java.lang.annotation.Annotation> cls, int i, int i2) {
        if (i > i2) {
            invalid(cls, java.lang.Integer.valueOf(i));
        }
    }

    private static void validateIntFlags(java.lang.Class<? extends java.lang.annotation.Annotation> cls, int i, int i2) {
        if ((i2 & i) != i2) {
            invalid(cls, "0x" + java.lang.Integer.toHexString(i));
        }
    }

    private static void validateLongFlags(java.lang.Class<? extends java.lang.annotation.Annotation> cls, long j, int i) {
        long j2 = i;
        if ((j2 & j) != j2) {
            invalid(cls, "0x" + java.lang.Long.toHexString(j));
        }
    }

    private static void invalid(java.lang.Class<? extends java.lang.annotation.Annotation> cls, java.lang.Object obj) {
        invalid("@" + cls.getSimpleName(), obj);
    }

    private static void invalid(java.lang.Class<? extends java.lang.annotation.Annotation> cls, java.lang.Object obj, java.lang.String str, java.lang.Object obj2) {
        invalid("@" + cls.getSimpleName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + ("value".equals(str) ? "" : str + " = ") + obj2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, obj);
    }

    private static void invalid(java.lang.String str, java.lang.Object obj) {
        throw new java.lang.IllegalStateException("Invalid " + str + ": " + obj);
    }
}
