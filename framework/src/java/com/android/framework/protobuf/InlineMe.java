package com.android.framework.protobuf;

@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.CONSTRUCTOR})
@java.lang.annotation.Documented
/* loaded from: classes4.dex */
@interface InlineMe {
    java.lang.String[] imports() default {};

    java.lang.String replacement();

    java.lang.String[] staticImports() default {};
}
