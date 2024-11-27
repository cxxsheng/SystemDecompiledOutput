package com.android.server.permission.access.immutable;

/* compiled from: IndexedMapExtensions.kt */
/* loaded from: classes2.dex */
public final class IndexedMapExtensionsKt {
    public static final <K, V> V getWithDefault(@org.jetbrains.annotations.Nullable com.android.server.permission.access.immutable.IndexedMap<K, V> indexedMap, K k, V v) {
        int index;
        if (indexedMap != null && (index = indexedMap.indexOfKey(k)) >= 0) {
            return indexedMap.valueAt(index);
        }
        return v;
    }

    public static final <K, V> V putWithDefault(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIndexedMap<K, V> mutableIndexedMap, K k, V v, V v2) {
        int index = mutableIndexedMap.indexOfKey(k);
        if (index >= 0) {
            V valueAt = mutableIndexedMap.valueAt(index);
            if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(v, valueAt)) {
                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(v, v2)) {
                    mutableIndexedMap.removeAt(index);
                } else {
                    mutableIndexedMap.putAt(index, v);
                }
            }
            return valueAt;
        }
        if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(v, v2)) {
            mutableIndexedMap.put(k, v);
        }
        return v2;
    }
}
