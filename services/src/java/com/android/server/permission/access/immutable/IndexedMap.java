package com.android.server.permission.access.immutable;

/* compiled from: IndexedMap.kt */
/* loaded from: classes2.dex */
public abstract class IndexedMap<K, V> implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIndexedMap<K, V>> {

    @org.jetbrains.annotations.NotNull
    private final android.util.ArrayMap<K, V> map;

    public /* synthetic */ IndexedMap(android.util.ArrayMap arrayMap, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayMap);
    }

    private IndexedMap(android.util.ArrayMap<K, V> arrayMap) {
        this.map = arrayMap;
    }

    @org.jetbrains.annotations.NotNull
    public final android.util.ArrayMap<K, V> getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.map;
    }

    public final int getSize() {
        return this.map.size();
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    public final boolean contains(K k) {
        return this.map.containsKey(k);
    }

    @org.jetbrains.annotations.Nullable
    public final V get(K k) {
        return this.map.get(k);
    }

    public final int indexOfKey(K k) {
        return this.map.indexOfKey(k);
    }

    public final K keyAt(int index) {
        return this.map.keyAt(index);
    }

    public final V valueAt(int index) {
        return this.map.valueAt(index);
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIndexedMap<K, V> toMutable() {
        return new com.android.server.permission.access.immutable.MutableIndexedMap<>(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.map.toString();
    }
}
