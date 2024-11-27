package com.android.server.wm.utils;

/* loaded from: classes3.dex */
public class RegionUtils {
    private RegionUtils() {
    }

    public static void rectListToRegion(java.util.List<android.graphics.Rect> list, android.graphics.Region region) {
        region.setEmpty();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            region.union(list.get(i));
        }
    }

    public static void forEachRect(android.graphics.Region region, java.util.function.Consumer<android.graphics.Rect> consumer) {
        android.graphics.RegionIterator regionIterator = new android.graphics.RegionIterator(region);
        android.graphics.Rect rect = new android.graphics.Rect();
        while (regionIterator.next(rect)) {
            consumer.accept(rect);
        }
    }

    public static void forEachRectReverse(android.graphics.Region region, java.util.function.Consumer<android.graphics.Rect> consumer) {
        android.graphics.RegionIterator regionIterator = new android.graphics.RegionIterator(region);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.graphics.Rect rect = new android.graphics.Rect();
        while (regionIterator.next(rect)) {
            arrayList.add(new android.graphics.Rect(rect));
        }
        java.util.Collections.reverse(arrayList);
        arrayList.forEach(consumer);
    }
}
