package com.google.android.collect;

/* loaded from: classes5.dex */
public class Sets {
    public static <K> java.util.HashSet<K> newHashSet() {
        return new java.util.HashSet<>();
    }

    public static <E> java.util.HashSet<E> newHashSet(E... eArr) {
        java.util.HashSet<E> hashSet = new java.util.HashSet<>(((eArr.length * 4) / 3) + 1);
        java.util.Collections.addAll(hashSet, eArr);
        return hashSet;
    }

    public static <E> java.util.SortedSet<E> newSortedSet() {
        return new java.util.TreeSet();
    }

    public static <E> java.util.SortedSet<E> newSortedSet(E... eArr) {
        java.util.TreeSet treeSet = new java.util.TreeSet();
        java.util.Collections.addAll(treeSet, eArr);
        return treeSet;
    }

    public static <E> android.util.ArraySet<E> newArraySet() {
        return new android.util.ArraySet<>();
    }

    public static <E> android.util.ArraySet<E> newArraySet(E... eArr) {
        android.util.ArraySet<E> arraySet = new android.util.ArraySet<>(((eArr.length * 4) / 3) + 1);
        java.util.Collections.addAll(arraySet, eArr);
        return arraySet;
    }
}
