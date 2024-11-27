package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Add missing generic type declarations: [T] */
/* compiled from: _Collections.kt */
/* loaded from: classes2.dex */
final class CollectionsKt___CollectionsKt$elementAt$1<T> extends com.android.server.permission.jarjar.kotlin.jvm.internal.Lambda implements com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<java.lang.Integer, T> {
    final /* synthetic */ int $index;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CollectionsKt___CollectionsKt$elementAt$1(int i) {
        super(1);
        this.$index = i;
    }

    public final T invoke(int it) {
        throw new java.lang.IndexOutOfBoundsException("Collection doesn't contain element at index " + this.$index + '.');
    }

    @Override // com.android.server.permission.jarjar.kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ java.lang.Object invoke(java.lang.Integer num) {
        return invoke(num.intValue());
    }
}
