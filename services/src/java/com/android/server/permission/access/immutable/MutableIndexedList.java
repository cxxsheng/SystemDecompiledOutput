package com.android.server.permission.access.immutable;

/* compiled from: IndexedList.kt */
/* loaded from: classes2.dex */
public final class MutableIndexedList<T> extends com.android.server.permission.access.immutable.IndexedList<T> {
    public MutableIndexedList(@org.jetbrains.annotations.NotNull java.util.ArrayList<T> arrayList) {
        super(arrayList, null);
    }

    public /* synthetic */ MutableIndexedList(java.util.ArrayList arrayList, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new java.util.ArrayList() : arrayList);
    }

    public MutableIndexedList(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedList<T> indexedList) {
        this(new java.util.ArrayList(indexedList.getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar()));
    }

    public final void add(T t) {
        getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().add(t);
    }
}
