package com.android.server.permission.access.immutable;

/* compiled from: IndexedSetExtensions.kt */
/* loaded from: classes2.dex */
public final class IndexedSetExtensionsKt {
    @org.jetbrains.annotations.NotNull
    public static final <T> com.android.server.permission.access.immutable.IndexedSet<T> indexedSetOf(@org.jetbrains.annotations.NotNull T... tArr) {
        java.util.List asList;
        asList = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysJvmKt.asList(tArr);
        return new com.android.server.permission.access.immutable.MutableIndexedSet(new android.util.ArraySet(asList));
    }

    public static final <T> void plusAssign(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIndexedSet<T> mutableIndexedSet, @org.jetbrains.annotations.NotNull java.util.Collection<? extends T> collection) {
        java.util.Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            mutableIndexedSet.add(it.next());
        }
    }
}
