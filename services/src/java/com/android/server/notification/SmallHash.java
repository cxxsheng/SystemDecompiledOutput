package com.android.server.notification;

/* loaded from: classes2.dex */
public class SmallHash {
    public static final int MAX_HASH = 8192;

    public static int hash(java.lang.String str) {
        return hash(java.util.Objects.hashCode(str));
    }

    public static int hash(int i) {
        return java.lang.Math.floorMod(i, 8192);
    }
}
