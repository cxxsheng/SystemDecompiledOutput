package com.android.server.permission.access.immutable;

/* compiled from: IntSet.kt */
/* loaded from: classes2.dex */
public final class MutableIntSet extends com.android.server.permission.access.immutable.IntSet {
    public MutableIntSet() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public MutableIntSet(@org.jetbrains.annotations.NotNull android.util.SparseBooleanArray array) {
        super(array, null);
    }

    public /* synthetic */ MutableIntSet(android.util.SparseBooleanArray sparseBooleanArray, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new android.util.SparseBooleanArray() : sparseBooleanArray);
    }

    public MutableIntSet(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntSet intSet) {
        this(intSet.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().clone());
    }

    public final void clear() {
        getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().clear();
    }
}
