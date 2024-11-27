package com.android.internal.annotations;

@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
/* loaded from: classes4.dex */
public @interface GuardedBy {
    java.lang.String[] anyOf() default {};

    java.lang.String[] value() default {};
}
