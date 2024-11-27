package com.android.server.permission.access.immutable;

/* compiled from: IndexedListSetExtensions.kt */
/* loaded from: classes2.dex */
public final class IndexedListSetExtensionsKt {
    @org.jetbrains.annotations.NotNull
    public static final <T> com.android.server.permission.access.immutable.MutableIndexedListSet<T> plus(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedListSet<T> indexedListSet, T t) {
        com.android.server.permission.access.immutable.MutableIndexedListSet $this$plus_u24lambda_u244 = indexedListSet.toMutable();
        $this$plus_u24lambda_u244.add(t);
        return $this$plus_u24lambda_u244;
    }
}
