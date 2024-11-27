package com.android.server.permission.access.immutable;

/* compiled from: IndexedReferenceMap.kt */
/* loaded from: classes2.dex */
public final class MutableIndexedReferenceMap<K, I extends com.android.server.permission.access.immutable.Immutable<M>, M extends I> extends com.android.server.permission.access.immutable.IndexedReferenceMap<K, I, M> {
    public MutableIndexedReferenceMap() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ MutableIndexedReferenceMap(android.util.ArrayMap arrayMap, int i, com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new android.util.ArrayMap() : arrayMap);
    }

    public MutableIndexedReferenceMap(@org.jetbrains.annotations.NotNull android.util.ArrayMap<K, com.android.server.permission.access.immutable.MutableReference<I, M>> arrayMap) {
        super(arrayMap, null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MutableIndexedReferenceMap(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IndexedReferenceMap<K, I, M> indexedReferenceMap) {
        this($this$_init__u24lambda_u240);
        android.util.ArrayMap $this$_init__u24lambda_u240 = new android.util.ArrayMap(indexedReferenceMap.getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar());
        int size = $this$_init__u24lambda_u240.size();
        for (int i = 0; i < size; i++) {
            $this$_init__u24lambda_u240.setValueAt(i, ((com.android.server.permission.access.immutable.MutableReference) $this$_init__u24lambda_u240.valueAt(i)).toImmutable());
        }
    }

    /* JADX WARN: Incorrect return type in method signature: (TK;)TM; */
    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.Immutable mutate(java.lang.Object key) {
        com.android.server.permission.access.immutable.MutableReference<I, M> mutableReference = getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().get(key);
        if (mutableReference != null) {
            return mutableReference.mutate();
        }
        return null;
    }

    /* JADX WARN: Incorrect types in method signature: (TK;TM;)TI; */
    /* JADX WARN: Multi-variable type inference failed */
    @org.jetbrains.annotations.Nullable
    public final com.android.server.permission.access.immutable.Immutable put(java.lang.Object obj, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.Immutable value) {
        com.android.server.permission.access.immutable.MutableReference<I, M> put = getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().put(obj, new com.android.server.permission.access.immutable.MutableReference<>(value));
        if (put != null) {
            return put.get();
        }
        return null;
    }

    @org.jetbrains.annotations.Nullable
    public final I remove(K k) {
        com.android.server.permission.access.immutable.MutableReference<I, M> remove = getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().remove(k);
        if (remove != null) {
            return remove.get();
        }
        return null;
    }

    @org.jetbrains.annotations.NotNull
    public final I removeAt(int index) {
        return getMap$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().removeAt(index).get();
    }
}
