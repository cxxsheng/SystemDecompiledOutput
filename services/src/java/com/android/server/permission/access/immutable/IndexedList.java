package com.android.server.permission.access.immutable;

/* compiled from: IndexedList.kt */
/* loaded from: classes2.dex */
public abstract class IndexedList<T> implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIndexedList<T>> {

    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<T> list;

    public /* synthetic */ IndexedList(java.util.ArrayList arrayList, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(arrayList);
    }

    private IndexedList(java.util.ArrayList<T> arrayList) {
        this.list = arrayList;
    }

    @org.jetbrains.annotations.NotNull
    public final java.util.ArrayList<T> getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.list;
    }

    public final int getSize() {
        return this.list.size();
    }

    public final T get(int index) {
        return this.list.get(index);
    }

    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIndexedList<T> toMutable() {
        return new com.android.server.permission.access.immutable.MutableIndexedList<>(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.list.toString();
    }
}
