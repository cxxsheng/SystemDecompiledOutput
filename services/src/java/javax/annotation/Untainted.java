package javax.annotation;

@javax.annotation.meta.TypeQualifier
@java.lang.annotation.Documented
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface Untainted {
    javax.annotation.meta.When when() default javax.annotation.meta.When.ALWAYS;
}
