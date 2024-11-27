package com.android.server.permission.jarjar.kotlin.collections;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: _Collections.kt */
/* loaded from: classes2.dex */
public class CollectionsKt___CollectionsKt extends com.android.server.permission.jarjar.kotlin.collections.CollectionsKt___CollectionsJvmKt {
    public static <T> T elementAt(@org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable, int i) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "<this>");
        if (iterable instanceof java.util.List) {
            return (T) ((java.util.List) iterable).get(i);
        }
        return (T) elementAtOrElse(iterable, i, new com.android.server.permission.jarjar.kotlin.collections.CollectionsKt___CollectionsKt$elementAt$1(i));
    }

    public static final <T> T elementAtOrElse(@org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable, int i, @org.jetbrains.annotations.NotNull com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<? super java.lang.Integer, ? extends T> function1) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(function1, "defaultValue");
        if (iterable instanceof java.util.List) {
            java.util.List list = (java.util.List) iterable;
            return (i < 0 || i > com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsKt.getLastIndex(list)) ? function1.invoke(java.lang.Integer.valueOf(i)) : (T) list.get(i);
        }
        if (i < 0) {
            return function1.invoke(java.lang.Integer.valueOf(i));
        }
        int i2 = 0;
        for (T t : iterable) {
            int i3 = i2 + 1;
            if (i != i2) {
                i2 = i3;
            } else {
                return t;
            }
        }
        return function1.invoke(java.lang.Integer.valueOf(i));
    }

    @org.jetbrains.annotations.NotNull
    public static final <T, C extends java.util.Collection<? super T>> C toCollection(@org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable, @org.jetbrains.annotations.NotNull C c) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(c, "destination");
        java.util.Iterator<? extends T> it = iterable.iterator();
        while (it.hasNext()) {
            c.add(it.next());
        }
        return c;
    }

    @org.jetbrains.annotations.NotNull
    public static <T> java.util.List<T> toList(@org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "<this>");
        if (iterable instanceof java.util.Collection) {
            switch (((java.util.Collection) iterable).size()) {
                case 0:
                    return com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsKt.emptyList();
                case 1:
                    return com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsJVMKt.listOf(iterable instanceof java.util.List ? ((java.util.List) iterable).get(0) : iterable.iterator().next());
                default:
                    return toMutableList((java.util.Collection) iterable);
            }
        }
        return com.android.server.permission.jarjar.kotlin.collections.CollectionsKt__CollectionsKt.optimizeReadOnlyList(toMutableList(iterable));
    }

    @org.jetbrains.annotations.NotNull
    public static final <T> java.util.List<T> toMutableList(@org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "<this>");
        if (iterable instanceof java.util.Collection) {
            return toMutableList((java.util.Collection) iterable);
        }
        return (java.util.List) toCollection(iterable, new java.util.ArrayList());
    }

    @org.jetbrains.annotations.NotNull
    public static final <T> java.util.List<T> toMutableList(@org.jetbrains.annotations.NotNull java.util.Collection<? extends T> collection) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(collection, "<this>");
        return new java.util.ArrayList(collection);
    }

    @org.jetbrains.annotations.NotNull
    public static final <T, A extends java.lang.Appendable> A joinTo(@org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable, @org.jetbrains.annotations.NotNull A a, @org.jetbrains.annotations.NotNull java.lang.CharSequence separator, @org.jetbrains.annotations.NotNull java.lang.CharSequence prefix, @org.jetbrains.annotations.NotNull java.lang.CharSequence postfix, int limit, @org.jetbrains.annotations.NotNull java.lang.CharSequence truncated, @org.jetbrains.annotations.Nullable com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<? super T, ? extends java.lang.CharSequence> function1) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(a, "buffer");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(separator, "separator");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(prefix, "prefix");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(postfix, "postfix");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(truncated, "truncated");
        a.append(prefix);
        int count = 0;
        for (java.lang.Object element : iterable) {
            count++;
            if (count > 1) {
                a.append(separator);
            }
            if (limit >= 0 && count > limit) {
                break;
            }
            com.android.server.permission.jarjar.kotlin.text.StringsKt__AppendableKt.appendElement(a, element, function1);
        }
        if (limit >= 0 && count > limit) {
            a.append(truncated);
        }
        a.append(postfix);
        return a;
    }

    public static /* synthetic */ java.lang.String joinToString$default(java.lang.Iterable iterable, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, int i, java.lang.CharSequence charSequence4, com.android.server.permission.jarjar.kotlin.jvm.functions.Function1 function1, int i2, java.lang.Object obj) {
        if ((i2 & 1) != 0) {
            charSequence = ", ";
        }
        java.lang.CharSequence charSequence5 = (i2 & 2) != 0 ? "" : charSequence2;
        java.lang.CharSequence charSequence6 = (i2 & 4) == 0 ? charSequence3 : "";
        if ((i2 & 8) != 0) {
            i = -1;
        }
        int i3 = i;
        if ((i2 & 16) != 0) {
            charSequence4 = "...";
        }
        java.lang.CharSequence charSequence7 = charSequence4;
        if ((i2 & 32) != 0) {
            function1 = null;
        }
        return joinToString(iterable, charSequence, charSequence5, charSequence6, i3, charSequence7, function1);
    }

    @org.jetbrains.annotations.NotNull
    public static final <T> java.lang.String joinToString(@org.jetbrains.annotations.NotNull java.lang.Iterable<? extends T> iterable, @org.jetbrains.annotations.NotNull java.lang.CharSequence separator, @org.jetbrains.annotations.NotNull java.lang.CharSequence prefix, @org.jetbrains.annotations.NotNull java.lang.CharSequence postfix, int limit, @org.jetbrains.annotations.NotNull java.lang.CharSequence truncated, @org.jetbrains.annotations.Nullable com.android.server.permission.jarjar.kotlin.jvm.functions.Function1<? super T, ? extends java.lang.CharSequence> function1) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(iterable, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(separator, "separator");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(prefix, "prefix");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(postfix, "postfix");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(truncated, "truncated");
        java.lang.String sb = ((java.lang.StringBuilder) joinTo(iterable, new java.lang.StringBuilder(), separator, prefix, postfix, limit, truncated, function1)).toString();
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
        return sb;
    }
}
