package com.android.server.permission.access.immutable;

/* compiled from: IndexedListSet.kt */
/* loaded from: classes2.dex */
public abstract class IndexedListSet<T> implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIndexedListSet<T>> {

    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<T> list;

    public /* synthetic */ IndexedListSet(java.util.ArrayList arrayList, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayList);
    }

    private IndexedListSet(java.util.ArrayList<T> arrayList) {
        this.list = arrayList;
    }

    @org.jetbrains.annotations.NotNull
    public final java.util.ArrayList<T> getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.list;
    }

    public final int getSize() {
        return this.list.size();
    }

    public final boolean isEmpty() {
        return this.list.isEmpty();
    }

    public final boolean contains(T t) {
        return this.list.contains(t);
    }

    public final T elementAt(int index) {
        return this.list.get(index);
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIndexedListSet<T> toMutable() {
        return new com.android.server.permission.access.immutable.MutableIndexedListSet<>(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.list.toString();
    }
}
