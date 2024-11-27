package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Maps.kt */
/* loaded from: classes2.dex */
public class MapsKt__MapsKt extends com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsJVMKt {
    @org.jetbrains.annotations.NotNull
    public static <K, V> java.util.Map<K, V> emptyMap() {
        com.android.server.permission.jarjar.kotlin.collections.EmptyMap emptyMap = com.android.server.permission.jarjar.kotlin.collections.EmptyMap.INSTANCE;
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(emptyMap, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.MapsKt__MapsKt.emptyMap, V of kotlin.collections.MapsKt__MapsKt.emptyMap>");
        return emptyMap;
    }
}
