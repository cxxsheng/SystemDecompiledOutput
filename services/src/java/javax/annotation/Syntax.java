package javax.annotation;

@javax.annotation.meta.TypeQualifier(applicableTo = java.lang.String.class)
@java.lang.annotation.Documented
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface Syntax {
    java.lang.String value();

    javax.annotation.meta.When when() default javax.annotation.meta.When.ALWAYS;
}
