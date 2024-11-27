package com.android.server.permission.access.immutable;

/* compiled from: IndexedMap.kt */
/* loaded from: classes2.dex */
public final class MutableIndexedMap<K, V> extends com.android.server.permission.access.immutable.IndexedMap<K, V> {
    public MutableIndexedMap() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public MutableIndexedMap(@org.jetbrains.annotations.NotNull android.util.ArrayMap<K, V> arrayMap) {
        super(arrayMap, null);
    }

    public /* synthetic */ MutableIndexedMap(android.util.ArrayMap arrayMap, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new android.util.ArrayMap() : arrayMap);
    }

    public MutableIndexedMap(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedMap<K, V> indexedMap) {
        this(new android.util.ArrayMap(indexedMap.getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar()));
    }

    @org.jetbrains.annotations.Nullable
    public final V put(K k, V v) {
        return getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().put(k, v);
    }

    @org.jetbrains.annotations.Nullable
    public final V remove(K k) {
        return getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().remove(k);
    }

    public final V putAt(int index, V v) {
        return getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().setValueAt(index, v);
    }

    public final V removeAt(int index) {
        return getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().removeAt(index);
    }
}
