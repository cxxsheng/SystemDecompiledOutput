package com.android.server.permission.access.immutable;

/* compiled from: IntReferenceMap.kt */
/* loaded from: classes2.dex */
public final class MutableIntReferenceMap<I extends com.android.server.permission.access.immutable.Immutable<M>, M extends I> extends com.android.server.permission.access.immutable.IntReferenceMap<I, M> {
    public MutableIntReferenceMap() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ MutableIntReferenceMap(android.util.SparseArray sparseArray, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new android.util.SparseArray() : sparseArray);
    }

    public MutableIntReferenceMap(@org.jetbrains.annotations.NotNull android.util.SparseArray<com.android.server.permission.access.immutable.MutableReference<I, M>> sparseArray) {
        super(sparseArray, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MutableIntReferenceMap(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntReferenceMap<I, M> intReferenceMap) {
        this($this$_init__u24lambda_u240);
        android.util.SparseArray $this$_init__u24lambda_u240 = intReferenceMap.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().clone();
        int size = $this$_init__u24lambda_u240.size();
        for (int i = 0; i < size; i++) {
            $this$_init__u24lambda_u240.setValueAt(i, $this$_init__u24lambda_u240.valueAt(i).toImmutable());
        }
    }

    /* JADX WARN: Incorrect return type in method signature: (I)TM; */
    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.Immutable mutate(int key) {
        com.android.server.permission.access.immutable.MutableReference<I, M> mutableReference = getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().get(key);
        if (mutableReference != null) {
            return mutableReference.mutate();
        }
        return null;
    }

    /* JADX WARN: Incorrect types in method signature: (ITM;)TI; */
    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.Immutable put(int key, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.Immutable value) {
        com.android.server.permission.access.immutable.MutableReference mutableReference = (com.android.server.permission.access.immutable.MutableReference) com.android.server.permission.access.immutable.IntMapKt.putReturnOld(getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(), key, new com.android.server.permission.access.immutable.MutableReference(value));
        if (mutableReference != null) {
            return mutableReference.get();
        }
        return null;
    }

    /* JADX WARN: Incorrect return type in method signature: (I)TM; */
    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.Immutable mutateAt(int index) {
        return getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().valueAt(index).mutate();
    }

    @org.jetbrains.annotations.NotNull
    public final I removeAt(int i) {
        java.lang.Object removeAtReturnOld = com.android.server.permission.access.immutable.IntMapKt.removeAtReturnOld(getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(), i);
        com.android.server.permission.access.immutable.IntMapKt.gc(getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar());
        return (I) ((com.android.server.permission.access.immutable.MutableReference) removeAtReturnOld).get();
    }
}
