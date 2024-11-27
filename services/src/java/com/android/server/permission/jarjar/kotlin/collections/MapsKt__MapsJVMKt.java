package com.android.server.permission.jarjar.kotlin.collections;

/* compiled from: MapsJVM.kt */
/* loaded from: classes2.dex */
class MapsKt__MapsJVMKt extends com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapWithDefaultKt {
    public static final int mapCapacity(int expectedSize) {
        if (expectedSize < 0) {
            return expectedSize;
        }
        if (expectedSize < 3) {
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824) {
            return (int) ((expectedSize / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }
}
