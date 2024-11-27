package com.android.server.permission.jarjar.kotlin.jvm.internal;

/* compiled from: CollectionToArray.kt */
/* loaded from: classes2.dex */
public final class CollectionToArray {

    @org.jetbrains.annotations.NotNull
    private static final java.lang.Object[] EMPTY = new java.lang.Object[0];

    @org.jetbrains.annotations.NotNull
    public static final java.lang.Object[] toArray(@org.jetbrains.annotations.NotNull java.util.Collection<?> collection) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(collection, "collection");
        int size$iv = collection.size();
        if (size$iv == 0) {
            return EMPTY;
        }
        java.util.Iterator iter$iv = collection.iterator();
        if (!iter$iv.hasNext()) {
            return EMPTY;
        }
        java.lang.Object[] result$iv = new java.lang.Object[size$iv];
        int i$iv = 0;
        while (true) {
            int i$iv2 = i$iv + 1;
            result$iv[i$iv] = iter$iv.next();
            if (i$iv2 >= result$iv.length) {
                if (!iter$iv.hasNext()) {
                    return result$iv;
                }
                int newSize$iv = ((i$iv2 * 3) + 1) >>> 1;
                if (newSize$iv <= i$iv2) {
                    if (i$iv2 >= 2147483645) {
                        throw new java.lang.OutOfMemoryError();
                    }
                    newSize$iv = 2147483645;
                }
                java.lang.Object[] copyOf = java.util.Arrays.copyOf(result$iv, newSize$iv);
                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                result$iv = copyOf;
                i$iv = i$iv2;
            } else {
                if (!iter$iv.hasNext()) {
                    java.lang.Object[] result = result$iv;
                    java.lang.Object[] copyOf2 = java.util.Arrays.copyOf(result, i$iv2);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(...)");
                    return copyOf2;
                }
                i$iv = i$iv2;
            }
        }
    }

    @org.jetbrains.annotations.NotNull
    public static final java.lang.Object[] toArray(@org.jetbrains.annotations.NotNull java.util.Collection<?> collection, @org.jetbrains.annotations.Nullable java.lang.Object[] a) {
        java.lang.Object[] objArr;
        java.lang.Object[] copyOf;
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(collection, "collection");
        if (a == null) {
            throw new java.lang.NullPointerException();
        }
        int size$iv = collection.size();
        if (size$iv == 0) {
            if (a.length > 0) {
                a[0] = null;
            }
        } else {
            java.util.Iterator iter$iv = collection.iterator();
            if (!iter$iv.hasNext()) {
                if (a.length > 0) {
                    a[0] = null;
                }
            } else {
                if (size$iv <= a.length) {
                    objArr = a;
                } else {
                    java.lang.Object newInstance = java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size$iv);
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(newInstance, "null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
                    objArr = (java.lang.Object[]) newInstance;
                }
                java.lang.Object[] result$iv = objArr;
                int i$iv = 0;
                while (true) {
                    int i$iv2 = i$iv + 1;
                    result$iv[i$iv] = iter$iv.next();
                    if (i$iv2 >= result$iv.length) {
                        if (!iter$iv.hasNext()) {
                            return result$iv;
                        }
                        int newSize$iv = ((i$iv2 * 3) + 1) >>> 1;
                        if (newSize$iv <= i$iv2) {
                            if (i$iv2 >= 2147483645) {
                                throw new java.lang.OutOfMemoryError();
                            }
                            newSize$iv = 2147483645;
                        }
                        java.lang.Object[] copyOf2 = java.util.Arrays.copyOf(result$iv, newSize$iv);
                        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(...)");
                        result$iv = copyOf2;
                        i$iv = i$iv2;
                    } else {
                        if (!iter$iv.hasNext()) {
                            java.lang.Object[] result = result$iv;
                            if (result == a) {
                                a[i$iv2] = null;
                                copyOf = a;
                            } else {
                                copyOf = java.util.Arrays.copyOf(result, i$iv2);
                                com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
                            }
                            java.lang.Object[] result$iv2 = copyOf;
                            return result$iv2;
                        }
                        i$iv = i$iv2;
                    }
                }
            }
        }
        return a;
    }
}
