package com.android.server.permission.jarjar.kotlin.collections;

/* compiled from: CollectionsJVM.kt */
/* loaded from: classes2.dex */
class CollectionsKt__CollectionsJVMKt {
    @org.jetbrains.annotations.NotNull
    public static final <T> java.util.List<T> listOf(T t) {
        java.util.List<T> singletonList = java.util.Collections.singletonList(t);
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(singletonList, "singletonList(...)");
        return singletonList;
    }
}
