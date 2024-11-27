package javax.annotation;

@javax.annotation.Syntax("RegEx")
@javax.annotation.meta.TypeQualifierNickname
@java.lang.annotation.Documented
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface RegEx {
    javax.annotation.meta.When when() default javax.annotation.meta.When.ALWAYS;

    public static class Checker implements javax.annotation.meta.TypeQualifierValidator<javax.annotation.RegEx> {
        @Override // javax.annotation.meta.TypeQualifierValidator
        public javax.annotation.meta.When forConstantValue(javax.annotation.RegEx regEx, java.lang.Object obj) {
            if (!(obj instanceof java.lang.String)) {
                return javax.annotation.meta.When.NEVER;
            }
            try {
                java.util.regex.Pattern.compile((java.lang.String) obj);
                return javax.annotation.meta.When.ALWAYS;
            } catch (java.util.regex.PatternSyntaxException e) {
                return javax.annotation.meta.When.NEVER;
            }
        }
    }
}
