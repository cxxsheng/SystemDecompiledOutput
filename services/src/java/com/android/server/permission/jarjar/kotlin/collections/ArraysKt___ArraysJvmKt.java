package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _ArraysJvm.kt */
/* loaded from: classes2.dex */
public class ArraysKt___ArraysJvmKt extends com.android.server.permission.jarjar.kotlin.collections.ArraysKt__ArraysKt {
    @org.jetbrains.annotations.NotNull
    public static <T> java.util.List<T> asList(@org.jetbrains.annotations.NotNull T[] tArr) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        java.util.List<T> asList = com.android.server.permission.jarjar.kotlin.collections.ArraysUtilJVM.asList(tArr);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(asList, "asList(...)");
        return asList;
    }
}
