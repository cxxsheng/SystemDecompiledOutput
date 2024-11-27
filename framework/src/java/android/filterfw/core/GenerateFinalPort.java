package android.filterfw.core;

@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
/* loaded from: classes.dex */
public @interface GenerateFinalPort {
    boolean hasDefault() default false;

    java.lang.String name() default "";
}
