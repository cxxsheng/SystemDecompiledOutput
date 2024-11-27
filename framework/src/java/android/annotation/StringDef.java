package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface StringDef {
    java.lang.String[] prefix() default {};

    java.lang.String[] suffix() default {};

    java.lang.String[] value() default {};
}
