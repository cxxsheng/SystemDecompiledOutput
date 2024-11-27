package com.android.server.permission.access.immutable;

/* compiled from: IndexedReferenceMap.kt */
/* loaded from: classes2.dex */
public abstract class IndexedReferenceMap<K, I extends com.android.server.permission.access.immutable.Immutable<M>, M extends I> implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIndexedReferenceMap<K, I, M>> {

    @org.jetbrains.annotations.NotNull
    private final android.util.ArrayMap<K, com.android.server.permission.access.immutable.MutableReference<I, M>> map;

    public /* synthetic */ IndexedReferenceMap(android.util.ArrayMap arrayMap, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayMap);
    }

    private IndexedReferenceMap(android.util.ArrayMap<K, com.android.server.permission.access.immutable.MutableReference<I, M>> arrayMap) {
        this.map = arrayMap;
    }

    @org.jetbrains.annotations.NotNull
    public final android.util.ArrayMap<K, com.android.server.permission.access.immutable.MutableReference<I, M>> getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.map;
    }

    public final int getSize() {
        return this.map.size();
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    @org.jetbrains.annotations.Nullable
    public final I get(K k) {
        com.android.server.permission.access.immutable.MutableReference<I, M> mutableReference = this.map.get(k);
        if (mutableReference != null) {
            return mutableReference.get();
        }
        return null;
    }

    public final int indexOfKey(K k) {
        return this.map.indexOfKey(k);
    }

    public final K keyAt(int index) {
        return this.map.keyAt(index);
    }

    @org.jetbrains.annotations.NotNull
    public final I valueAt(int index) {
        return this.map.valueAt(index).get();
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIndexedReferenceMap<K, I, M> toMutable() {
        return new com.android.server.permission.access.immutable.MutableIndexedReferenceMap<>(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.map.toString();
    }
}
