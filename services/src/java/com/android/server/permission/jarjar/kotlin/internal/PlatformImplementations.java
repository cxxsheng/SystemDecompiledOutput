package com.android.server.permission.jarjar.kotlin.internal;

/* compiled from: PlatformImplementations.kt */
/* loaded from: classes2.dex */
public class PlatformImplementations {

    /* compiled from: PlatformImplementations.kt */
    private static final class ReflectThrowable {

        @org.jetbrains.annotations.NotNull
        public static final com.android.server.permission.jarjar.kotlin.internal.PlatformImplementations.ReflectThrowable INSTANCE = new com.android.server.permission.jarjar.kotlin.internal.PlatformImplementations.ReflectThrowable();

        @org.jetbrains.annotations.Nullable
        public static final java.lang.reflect.Method addSuppressed;

        @org.jetbrains.annotations.Nullable
        public static final java.lang.reflect.Method getSuppressed;

        private ReflectThrowable() {
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0041 A[LOOP:0: B:2:0x0014->B:10:0x0041, LOOP_END] */
        /* JADX WARN: Removed duplicated region for block: B:11:0x0045 A[EDGE_INSN: B:11:0x0045->B:12:0x0045 BREAK  A[LOOP:0: B:2:0x0014->B:10:0x0041], SYNTHETIC] */
        static {
            java.lang.reflect.Method method;
            java.lang.reflect.Method it;
            boolean z;
            java.lang.Object singleOrNull;
            java.lang.reflect.Method[] throwableMethods = java.lang.Throwable.class.getMethods();
            com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(throwableMethods);
            int length = throwableMethods.length;
            int i = 0;
            int i2 = 0;
            while (true) {
                method = null;
                if (i2 >= length) {
                    it = null;
                    break;
                }
                it = throwableMethods[i2];
                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(it.getName(), "addSuppressed")) {
                    java.lang.Class<?>[] parameterTypes = it.getParameterTypes();
                    com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(parameterTypes, "getParameterTypes(...)");
                    singleOrNull = com.android.server.permission.jarjar.kotlin.collections.ArraysKt___ArraysKt.singleOrNull(parameterTypes);
                    if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(singleOrNull, java.lang.Throwable.class)) {
                        z = true;
                        if (!z) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                z = false;
                if (!z) {
                }
            }
            addSuppressed = it;
            int length2 = throwableMethods.length;
            while (true) {
                if (i >= length2) {
                    break;
                }
                java.lang.reflect.Method it2 = throwableMethods[i];
                if (com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(it2.getName(), "getSuppressed")) {
                    method = it2;
                    break;
                }
                i++;
            }
            getSuppressed = method;
        }
    }

    public void addSuppressed(@org.jetbrains.annotations.NotNull java.lang.Throwable cause, @org.jetbrains.annotations.NotNull java.lang.Throwable exception) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(cause, "cause");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(exception, "exception");
        java.lang.reflect.Method method = com.android.server.permission.jarjar.kotlin.internal.PlatformImplementations.ReflectThrowable.addSuppressed;
        if (method != null) {
            method.invoke(cause, exception);
        }
    }
}
