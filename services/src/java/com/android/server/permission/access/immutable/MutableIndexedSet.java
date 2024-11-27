package com.android.server.permission.access.immutable;

/* compiled from: IndexedSet.kt */
/* loaded from: classes2.dex */
public final class MutableIndexedSet<T> extends com.android.server.permission.access.immutable.IndexedSet<T> {
    public MutableIndexedSet() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public MutableIndexedSet(@org.jetbrains.annotations.NotNull android.util.ArraySet<T> arraySet) {
        super(arraySet, null);
    }

    public /* synthetic */ MutableIndexedSet(android.util.ArraySet arraySet, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new android.util.ArraySet() : arraySet);
    }

    public MutableIndexedSet(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedSet<T> indexedSet) {
        this(new android.util.ArraySet((android.util.ArraySet) indexedSet.getSet$frameworks__base__services__permission__android_common__services_permission_pre_jarjar()));
    }

    public final boolean add(T t) {
        return getSet$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().add(t);
    }
}
