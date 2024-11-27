package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface IntDef {
    boolean flag() default false;

    java.lang.String[] prefix() default {};

    java.lang.String[] suffix() default {};

    int[] value() default {};
}
