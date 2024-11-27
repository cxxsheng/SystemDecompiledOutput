package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MutableCollections.kt */
/* loaded from: classes2.dex */
public class CollectionsKt__MutableCollectionsKt extends com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__MutableCollectionsJVMKt {
    public static <T> boolean addAll(@org.jetbrains.annotations.NotNull java.util.Collection<? super T> collection, @org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(collection, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "elements");
        if (iterable instanceof java.util.Collection) {
            return collection.addAll((java.util.Collection) iterable);
        }
        boolean result = false;
        for (java.lang.Object item : iterable) {
            if (collection.add(item)) {
                result = true;
            }
        }
        return result;
    }
}
