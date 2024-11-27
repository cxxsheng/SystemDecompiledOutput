package com.android.server.permission.access.immutable;

/* compiled from: IntReferenceMapExtensions.kt */
/* loaded from: classes2.dex */
public final class IntReferenceMapExtensionsKt {
    public static final <I extends com.android.server.permission.access.immutable.Immutable<M>, M extends I> void minusAssign(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIntReferenceMap<I, M> mutableIntReferenceMap, int key) {
        mutableIntReferenceMap.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().remove(key);
        com.android.server.permission.jarjar.kotlin.Unit unit = com.android.server.permission.jarjar.kotlin.Unit.INSTANCE;
        com.android.server.permission.access.immutable.IntMapKt.gc(mutableIntReferenceMap.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar());
    }

    /* JADX WARN: Incorrect types in method signature: <I::Lcom/android/server/permission/access/immutable/Immutable<TM;>;M::TI;>(Lcom/android/server/permission/access/immutable/MutableIntReferenceMap<TI;TM;>;ITM;)V */
    public static final void set(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIntReferenceMap $this$set, int key, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.Immutable value) {
        $this$set.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().put(key, new com.android.server.permission.access.immutable.MutableReference(value));
    }
}
