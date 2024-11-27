package com.android.server.permission.access.immutable;

/* compiled from: IndexedSet.kt */
/* loaded from: classes2.dex */
public abstract class IndexedSet<T> implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIndexedSet<T>> {

    @org.jetbrains.annotations.NotNull
    private final android.util.ArraySet<T> set;

    public /* synthetic */ IndexedSet(android.util.ArraySet arraySet, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(arraySet);
    }

    private IndexedSet(android.util.ArraySet<T> arraySet) {
        this.set = arraySet;
    }

    @org.jetbrains.annotations.NotNull
    public final android.util.ArraySet<T> getSet$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.set;
    }

    public final int getSize() {
        return this.set.size();
    }

    public final boolean isEmpty() {
        return this.set.isEmpty();
    }

    public final boolean contains(T t) {
        return this.set.contains(t);
    }

    public final T elementAt(int i) {
        java.lang.Object elementAt;
        elementAt = com.android.server.permission.jarjar.kotlin.collections.CollectionsKt___CollectionsKt.elementAt(this.set, i);
        return (T) elementAt;
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIndexedSet<T> toMutable() {
        return new com.android.server.permission.access.immutable.MutableIndexedSet<>(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.set.toString();
    }
}
