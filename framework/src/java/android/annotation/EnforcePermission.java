package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
/* loaded from: classes.dex */
public @interface EnforcePermission {
    java.lang.String[] allOf() default {};

    java.lang.String[] anyOf() default {};

    java.lang.String value() default "";
}
