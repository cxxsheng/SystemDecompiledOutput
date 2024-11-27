package com.android.server.permission.access.immutable;

/* compiled from: IntMap.kt */
/* loaded from: classes2.dex */
public abstract class IntMap<T> implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIntMap<T>> {

    @org.jetbrains.annotations.NotNull
    private final android.util.SparseArray<T> array;

    public /* synthetic */ IntMap(android.util.SparseArray sparseArray, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(sparseArray);
    }

    private IntMap(android.util.SparseArray<T> sparseArray) {
        this.array = sparseArray;
    }

    @org.jetbrains.annotations.NotNull
    public final android.util.SparseArray<T> getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.array;
    }

    public final int getSize() {
        return this.array.size();
    }

    @org.jetbrains.annotations.Nullable
    public final T get(int key) {
        return this.array.get(key);
    }

    public final int keyAt(int index) {
        return this.array.keyAt(index);
    }

    public final T valueAt(int index) {
        return this.array.valueAt(index);
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIntMap<T> toMutable() {
        return new com.android.server.permission.access.immutable.MutableIntMap<>(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.array.toString();
    }
}
