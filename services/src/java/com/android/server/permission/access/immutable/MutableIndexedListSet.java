package com.android.server.permission.access.immutable;

/* compiled from: IndexedListSet.kt */
/* loaded from: classes2.dex */
public final class MutableIndexedListSet<T> extends com.android.server.permission.access.immutable.IndexedListSet<T> {
    public MutableIndexedListSet() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public MutableIndexedListSet(@org.jetbrains.annotations.NotNull java.util.ArrayList<T> arrayList) {
        super(arrayList, null);
    }

    public /* synthetic */ MutableIndexedListSet(java.util.ArrayList arrayList, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new java.util.ArrayList() : arrayList);
    }

    public MutableIndexedListSet(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedListSet<T> indexedListSet) {
        this(new java.util.ArrayList(indexedListSet.getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar()));
    }

    public final boolean add(T t) {
        if (getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().contains(t)) {
            return false;
        }
        getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().add(t);
        return true;
    }

    public final boolean remove(T t) {
        return getList$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().remove(t);
    }
}
