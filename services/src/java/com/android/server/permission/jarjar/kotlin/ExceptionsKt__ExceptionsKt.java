package com.android.server.permission.jarjar.kotlin;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Exceptions.kt */
/* loaded from: classes2.dex */
public class ExceptionsKt__ExceptionsKt {
    public static void addSuppressed(@org.jetbrains.annotations.NotNull java.lang.Throwable $this$addSuppressed, @org.jetbrains.annotations.NotNull java.lang.Throwable exception) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter($this$addSuppressed, "<this>");
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(exception, "exception");
        if ($this$addSuppressed != exception) {
            com.android.server.permission.jarjar.kotlin.internal.PlatformImplementationsKt.IMPLEMENTATIONS.addSuppressed($this$addSuppressed, exception);
        }
    }
}
