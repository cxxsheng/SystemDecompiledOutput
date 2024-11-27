package com.android.server.permission.jarjar.kotlin.text;

/* compiled from: CharJVM.kt */
/* loaded from: classes2.dex */
class CharsKt__CharJVMKt {
    public static final int checkRadix(int radix) {
        if (!new com.android.server.permission.jarjar.kotlin.ranges.IntRange(2, 36).contains(radix)) {
            throw new java.lang.IllegalArgumentException("radix " + radix + " was not in valid range " + new com.android.server.permission.jarjar.kotlin.ranges.IntRange(2, 36));
        }
        return radix;
    }
}
