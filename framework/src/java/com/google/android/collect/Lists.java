package com.google.android.collect;

/* loaded from: classes5.dex */
public class Lists {
    public static <E> java.util.ArrayList<E> newArrayList() {
        return new java.util.ArrayList<>();
    }

    public static <E> java.util.ArrayList<E> newArrayList(E... eArr) {
        java.util.ArrayList<E> arrayList = new java.util.ArrayList<>(((eArr.length * 110) / 100) + 5);
        java.util.Collections.addAll(arrayList, eArr);
        return arrayList;
    }
}
