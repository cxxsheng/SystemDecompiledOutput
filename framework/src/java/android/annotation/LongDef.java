package android.annotation;

@java.lang.annotation.Target({java.lang.annotation.ElementType.ANNOTATION_TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface LongDef {
    boolean flag() default false;

    java.lang.String[] prefix() default {""};

    long[] value() default {};
}
