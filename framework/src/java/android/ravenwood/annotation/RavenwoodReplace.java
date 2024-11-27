package android.ravenwood.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface RavenwoodReplace {
    java.lang.Class<?>[] blockedBy() default {};

    java.lang.String reason() default "";
}
