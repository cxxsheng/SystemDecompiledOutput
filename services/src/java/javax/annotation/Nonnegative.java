package javax.annotation;

@javax.annotation.meta.TypeQualifier(applicableTo = java.lang.Number.class)
@java.lang.annotation.Documented
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface Nonnegative {
    javax.annotation.meta.When when() default javax.annotation.meta.When.ALWAYS;

    public static class Checker implements javax.annotation.meta.TypeQualifierValidator<javax.annotation.Nonnegative> {
        @Override // javax.annotation.meta.TypeQualifierValidator
        public javax.annotation.meta.When forConstantValue(javax.annotation.Nonnegative nonnegative, java.lang.Object obj) {
            if (!(obj instanceof java.lang.Number)) {
                return javax.annotation.meta.When.NEVER;
            }
            java.lang.Number number = (java.lang.Number) obj;
            boolean z = false;
            if (number instanceof java.lang.Long) {
                if (number.longValue() < 0) {
                    z = true;
                }
            } else if (number instanceof java.lang.Double) {
                if (number.doubleValue() < 0.0d) {
                    z = true;
                }
            } else if (number instanceof java.lang.Float) {
                if (number.floatValue() < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    z = true;
                }
            } else if (number.intValue() < 0) {
                z = true;
            }
            if (z) {
                return javax.annotation.meta.When.NEVER;
            }
            return javax.annotation.meta.When.ALWAYS;
        }
    }
}
