package com.android.server.permission.access.immutable;

/* compiled from: IntMap.kt */
/* loaded from: classes2.dex */
public final class MutableIntMap<T> extends com.android.server.permission.access.immutable.IntMap<T> {
    public MutableIntMap(@org.jetbrains.annotations.NotNull android.util.SparseArray<T> sparseArray) {
        super(sparseArray, null);
    }

    public /* synthetic */ MutableIntMap(android.util.SparseArray sparseArray, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new android.util.SparseArray() : sparseArray);
    }

    public MutableIntMap(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntMap<T> intMap) {
        this(intMap.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().clone());
    }

    @org.jetbrains.annotations.Nullable
    public final T put(int i, T t) {
        return (T) com.android.server.permission.access.immutable.IntMapKt.putReturnOld(getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(), i, t);
    }

    @org.jetbrains.annotations.Nullable
    public final T remove(int i) {
        T t = (T) getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().removeReturnOld(i);
        com.android.server.permission.access.immutable.IntMapKt.gc(getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar());
        return t;
    }

    public final void clear() {
        getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().clear();
    }
}
