package com.android.server.permission.access.immutable;

/* compiled from: IntSet.kt */
/* loaded from: classes2.dex */
public abstract class IntSet implements com.android.server.permission.access.immutable.Immutable<com.android.server.permission.access.immutable.MutableIntSet> {

    @org.jetbrains.annotations.NotNull
    private final android.util.SparseBooleanArray array;

    public /* synthetic */ IntSet(android.util.SparseBooleanArray sparseBooleanArray, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this(sparseBooleanArray);
    }

    private IntSet(android.util.SparseBooleanArray array) {
        this.array = array;
    }

    @org.jetbrains.annotations.NotNull
    public final android.util.SparseBooleanArray getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar() {
        return this.array;
    }

    public final int getSize() {
        return this.array.size();
    }

    public final int elementAt(int index) {
        return this.array.keyAt(index);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.server.permission.access.immutable.Immutable
    @org.jetbrains.annotations.NotNull
    public com.android.server.permission.access.immutable.MutableIntSet toMutable() {
        return new com.android.server.permission.access.immutable.MutableIntSet(this);
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return this.array.toString();
    }
}
