package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Sets.kt */
/* loaded from: classes2.dex */
public class SetsKt__SetsKt extends com.android.server.permission.jarjar.kotlin.collections.SetsKt__SetsJVMKt {
    @org.jetbrains.annotations.NotNull
    public static <T> java.util.Set<T> emptySet() {
        return com.android.server.permission.jarjar.kotlin.collections.EmptySet.INSTANCE;
    }

    @org.jetbrains.annotations.NotNull
    public static <T> java.util.Set<T> setOf(@org.jetbrains.annotations.NotNull T... tArr) {
        java.util.Set<T> emptySet;
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "elements");
        if (tArr.length > 0) {
            return com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.toSet(tArr);
        }
        emptySet = emptySet();
        return emptySet;
    }
}
