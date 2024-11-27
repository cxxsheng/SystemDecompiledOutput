package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Collections.kt */
/* loaded from: classes2.dex */
public class CollectionsKt__CollectionsKt extends com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsJVMKt {
    @org.jetbrains.annotations.NotNull
    public static <T> java.util.List<T> emptyList() {
        return com.android.server.permission.jarjar.kotlin.collections.EmptyList.INSTANCE;
    }

    public static final <T> int getLastIndex(@org.jetbrains.annotations.NotNull java.util.List<? extends T> list) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(list, "<this>");
        return list.size() - 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @org.jetbrains.annotations.NotNull
    public static final <T> java.util.List<T> optimizeReadOnlyList(@org.jetbrains.annotations.NotNull java.util.List<? extends T> list) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(list, "<this>");
        switch (list.size()) {
            case 0:
                return emptyList();
            case 1:
                return com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsJVMKt.listOf(list.get(0));
            default:
                return list;
        }
    }

    public static void throwIndexOverflow() {
        throw new java.lang.ArithmeticException("Index overflow has happened.");
    }
}
