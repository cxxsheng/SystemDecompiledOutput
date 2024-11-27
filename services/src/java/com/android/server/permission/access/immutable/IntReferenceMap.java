package com.android.server.permission.access.immutable;

/* compiled from: IntReferenceMap.kt */
/* loaded from: classes2.dex */
public abstract class IntReferenceMap<I extends com.android.server.permission.access.immutable.Immutable<M>, M extends I> implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIntReferenceMap<I, M>> {

    @org.jetbrains.annotations.NotNull
    private final android.util.SparseArray<com.android.server.permission.access.immutable.MutableReference<I, M>> array;

    public /* synthetic */ IntReferenceMap(android.util.SparseArray sparseArray, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(sparseArray);
    }

    private IntReferenceMap(android.util.SparseArray<com.android.server.permission.access.immutable.MutableReference<I, M>> sparseArray) {
        this.array = sparseArray;
    }

    @org.jetbrains.annotations.NotNull
    public final android.util.SparseArray<com.android.server.permission.access.immutable.MutableReference<I, M>> getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.array;
    }

    public final int getSize() {
        return this.array.size();
    }

    public final boolean contains(int key) {
        return this.array.contains(key);
    }

    @org.jetbrains.annotations.Nullable
    public final I get(int key) {
        com.android.server.permission.access.immutable.MutableReference<I, M> mutableReference = this.array.get(key);
        if (mutableReference != null) {
            return mutableReference.get();
        }
        return null;
    }

    public final int indexOfKey(int key) {
        return this.array.indexOfKey(key);
    }

    public final int keyAt(int index) {
        return this.array.keyAt(index);
    }

    @org.jetbrains.annotations.NotNull
    public final I valueAt(int index) {
        return this.array.valueAt(index).get();
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIntReferenceMap<I, M> toMutable() {
        return new com.android.server.permission.access.immutable.MutableIntReferenceMap<>(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.array.toString();
    }
}
