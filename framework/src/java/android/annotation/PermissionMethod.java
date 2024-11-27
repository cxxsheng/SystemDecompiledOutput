package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
/* loaded from: classes.dex */
public @interface PermissionMethod {
    boolean anyOf() default false;

    boolean orSelf() default false;

    java.lang.String[] value() default {};
}
