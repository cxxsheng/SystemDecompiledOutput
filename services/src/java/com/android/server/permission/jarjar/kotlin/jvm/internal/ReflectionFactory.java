package com.android.server.permission.jarjar.kotlin.jvm.internal;

/* loaded from: classes2.dex */
public class ReflectionFactory {
    public java.lang.String renderLambdaToString(com.android.server.permission.jarjar.kotlin.jvm.internal.Lambda lambda) {
        return renderLambdaToString((com.android.server.permission.jarjar.kotlin.jvm.internal.FunctionBase) lambda);
    }

    public java.lang.String renderLambdaToString(com.android.server.permission.jarjar.kotlin.jvm.internal.FunctionBase lambda) {
        java.lang.String result = lambda.getClass().getGenericInterfaces()[0].toString();
        return result.startsWith("com.android.server.permission.jarjar.kotlin.jvm.functions.") ? result.substring("com.android.server.permission.jarjar.kotlin.jvm.functions.".length()) : result;
    }
}
