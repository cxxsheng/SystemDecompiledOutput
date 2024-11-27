package com.android.server.permission.access.util;

/* compiled from: IntExtensions.kt */
/* loaded from: classes2.dex */
public final class IntExtensionsKt {
    public static final boolean hasAnyBit(int $this$hasAnyBit, int bits) {
        return ($this$hasAnyBit & bits) != 0;
    }

    public static final boolean hasBits(int $this$hasBits, int bits) {
        return ($this$hasBits & bits) == bits;
    }

    public static final int andInv(int $this$andInv, int other) {
        return (~other) & $this$andInv;
    }
}
