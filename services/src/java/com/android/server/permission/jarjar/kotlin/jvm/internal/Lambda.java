package com.android.server.permission.jarjar.kotlin.jvm.internal;

/* compiled from: Lambda.kt */
/* loaded from: classes2.dex */
public abstract class Lambda<R> implements com.android.server.permission.jarjar.kotlin.jvm.internal.FunctionBase<R>, java.io.Serializable {
    private final int arity;

    public Lambda(int arity) {
        this.arity = arity;
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        java.lang.String renderLambdaToString = com.android.server.permission.jarjar.kotlin.jvm.internal.Reflection.renderLambdaToString(this);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(renderLambdaToString, "renderLambdaToString(...)");
        return renderLambdaToString;
    }
}
