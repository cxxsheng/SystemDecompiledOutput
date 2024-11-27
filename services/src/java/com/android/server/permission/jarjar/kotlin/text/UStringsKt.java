package com.android.server.permission.jarjar.kotlin.text;

/* compiled from: UStrings.kt */
/* loaded from: classes2.dex */
public final class UStringsKt {
    @org.jetbrains.annotations.NotNull
    /* renamed from: toString-V7xB4Y4, reason: not valid java name */
    public static final java.lang.String m5924toStringV7xB4Y4(int $this$toString_u2dV7xB4Y4, int radix) {
        java.lang.String l = java.lang.Long.toString($this$toString_u2dV7xB4Y4 & 4294967295L, com.android.server.permission.jarjar.kotlin.text.CharsKt__CharJVMKt.checkRadix(radix));
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(l, "toString(...)");
        return l;
    }
}
