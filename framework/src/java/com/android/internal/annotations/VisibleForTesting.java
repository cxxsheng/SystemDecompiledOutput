package com.android.internal.annotations;

@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.CLASS)
/* loaded from: classes4.dex */
public @interface VisibleForTesting {

    public enum Visibility {
        PROTECTED,
        PACKAGE,
        PRIVATE
    }

    com.android.internal.annotations.VisibleForTesting.Visibility visibility() default com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE;
}
