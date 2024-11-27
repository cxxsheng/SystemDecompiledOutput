package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.PACKAGE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface UserHandleAware {
    int enabledSinceTargetSdkVersion() default 0;

    java.lang.String[] requiresAnyOfPermissionsIfNotCaller() default {};

    java.lang.String[] requiresAnyOfPermissionsIfNotCallerProfileGroup() default {};

    java.lang.String requiresPermissionIfNotCaller() default "";
}
