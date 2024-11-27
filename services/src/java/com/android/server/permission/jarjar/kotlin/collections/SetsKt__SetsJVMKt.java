package com.android.server.permission.jarjar.kotlin.collections;

/* compiled from: SetsJVM.kt */
/* loaded from: classes2.dex */
class SetsKt__SetsJVMKt {
    @org.jetbrains.annotations.NotNull
    public static final <T> java.util.Set<T> setOf(T t) {
        java.util.Set<T> singleton = java.util.Collections.singleton(t);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(singleton, "singleton(...)");
        return singleton;
    }
}
