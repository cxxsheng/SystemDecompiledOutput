package com.android.server.permission.access.collection;

/* compiled from: ArraySetExtensions.kt */
/* loaded from: classes2.dex */
public final class ArraySetExtensionsKt {
    @org.jetbrains.annotations.NotNull
    public static final <T> android.util.ArraySet<T> arraySetOf(@org.jetbrains.annotations.NotNull T... tArr) {
        java.util.List asList;
        asList = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysJvmKt.asList(tArr);
        return new android.util.ArraySet<>(asList);
    }
}
