package android.ravenwood.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.CONSTRUCTOR})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface RavenwoodThrow {
    java.lang.Class<?>[] blockedBy() default {};

    java.lang.String reason() default "";
}
