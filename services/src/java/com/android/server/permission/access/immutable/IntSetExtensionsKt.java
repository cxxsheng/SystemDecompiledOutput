package com.android.server.permission.access.immutable;

/* compiled from: IntSetExtensions.kt */
/* loaded from: classes2.dex */
public final class IntSetExtensionsKt {
    public static final void minusAssign(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntSet $this$minusAssign, int element) {
        $this$minusAssign.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().delete(element);
    }

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.access.immutable.MutableIntSet MutableIntSet(@org.jetbrains.annotations.NotNull int[] values) {
        com.android.server.permission.access.immutable.MutableIntSet $this$MutableIntSet_u24lambda_u245 = new com.android.server.permission.access.immutable.MutableIntSet(null, 1, null);
        plusAssign($this$MutableIntSet_u24lambda_u245, values);
        return $this$MutableIntSet_u24lambda_u245;
    }

    public static final void plusAssign(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIntSet $this$plusAssign, int element) {
        $this$plusAssign.getArray$frameworks__base__services__permission__android_common__services_permission_pre_jarjar().put(element, true);
    }

    public static final void plusAssign(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIntSet $this$plusAssign, @org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.IntSet set) {
        int size = set.getSize();
        for (int index$iv = 0; index$iv < size; index$iv++) {
            int it = set.elementAt(index$iv);
            plusAssign($this$plusAssign, it);
        }
    }

    public static final void plusAssign(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.MutableIntSet $this$plusAssign, @org.jetbrains.annotations.NotNull int[] array) {
        for (int element$iv : array) {
            plusAssign($this$plusAssign, element$iv);
        }
    }
}
