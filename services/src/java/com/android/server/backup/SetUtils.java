package com.android.server.backup;

/* loaded from: classes.dex */
public final class SetUtils {
    private SetUtils() {
    }

    public static <T> java.util.Set<T> union(java.util.Set<T> set, java.util.Set<T> set2) {
        java.util.HashSet hashSet = new java.util.HashSet(set);
        hashSet.addAll(set2);
        return hashSet;
    }
}
