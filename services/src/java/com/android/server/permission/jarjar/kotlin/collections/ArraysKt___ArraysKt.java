package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _Arrays.kt */
/* loaded from: classes2.dex */
public class ArraysKt___ArraysKt extends com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysJvmKt {
    public static <T> boolean contains(@org.jetbrains.annotations.NotNull T[] tArr, T t) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        return indexOf(tArr, t) >= 0;
    }

    public static <T> T first(@org.jetbrains.annotations.NotNull T[] tArr) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        if (tArr.length == 0) {
            throw new java.util.NoSuchElementException("Array is empty.");
        }
        return tArr[0];
    }

    public static final <T> int indexOf(@org.jetbrains.annotations.NotNull T[] tArr, T t) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        if (t == null) {
            int length = tArr.length;
            for (int index = 0; index < length; index++) {
                if (tArr[index] == null) {
                    return index;
                }
            }
            return -1;
        }
        int length2 = tArr.length;
        for (int index2 = 0; index2 < length2; index2++) {
            if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(t, tArr[index2])) {
                return index2;
            }
        }
        return -1;
    }

    @org.jetbrains.annotations.Nullable
    public static <T> T singleOrNull(@org.jetbrains.annotations.NotNull T[] tArr) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        if (tArr.length == 1) {
            return tArr[0];
        }
        return null;
    }

    @org.jetbrains.annotations.NotNull
    public static final <T, C extends java.util.Collection<? super T>> C toCollection(@org.jetbrains.annotations.NotNull T[] tArr, @org.jetbrains.annotations.NotNull C c) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(c, "destination");
        for (T t : tArr) {
            c.add(t);
        }
        return c;
    }

    @org.jetbrains.annotations.NotNull
    public static final <T> java.util.Set<T> toSet(@org.jetbrains.annotations.NotNull T[] tArr) {
        java.util.Set<T> emptySet;
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        switch (tArr.length) {
            case 0:
                emptySet = com.android.server.permission.jarjar.kotlin.collections.SetsKt__SetsKt.emptySet();
                return emptySet;
            case 1:
                return com.android.server.permission.jarjar.kotlin.collections.SetsKt__SetsJVMKt.setOf(tArr[0]);
            default:
                return (java.util.Set) toCollection(tArr, new java.util.LinkedHashSet(com.android.server.permission.jarjar.kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(tArr.length)));
        }
    }

    @org.jetbrains.annotations.NotNull
    public static final <T, A extends java.lang.Appendable> A joinTo(@org.jetbrains.annotations.NotNull T[] tArr, @org.jetbrains.annotations.NotNull A a, @org.jetbrains.annotations.NotNull java.lang.CharSequence separator, @org.jetbrains.annotations.NotNull java.lang.CharSequence prefix, @org.jetbrains.annotations.NotNull java.lang.CharSequence postfix, int limit, @org.jetbrains.annotations.NotNull java.lang.CharSequence truncated, @org.jetbrains.annotations.Nullable com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<? super T, ? extends java.lang.CharSequence> function1) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(tArr, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(a, "buffer");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(separator, "separator");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(prefix, "prefix");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(postfix, "postfix");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(truncated, "truncated");
        a.append(prefix);
        int count = 0;
        for (T t : tArr) {
            count++;
            if (count > 1) {
                a.append(separator);
            }
            if (limit >= 0 && count > limit) {
                break;
            }
            com.android.server.permission.jarjar.kotlin.text.StringsKt__AppendableKt.appendElement(a, t, function1);
        }
        if (limit >= 0 && count > limit) {
            a.append(truncated);
        }
        a.append(postfix);
        return a;
    }
}
