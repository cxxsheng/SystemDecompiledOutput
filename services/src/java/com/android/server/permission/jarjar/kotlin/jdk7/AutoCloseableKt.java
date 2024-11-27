package com.android.server.permission.jarjar.kotlin.jdk7;

/* compiled from: AutoCloseableJVM.kt */
/* loaded from: classes2.dex */
public final class AutoCloseableKt {
    public static final void closeFinally(@org.jetbrains.annotations.Nullable java.lang.AutoCloseable $this$closeFinally, @org.jetbrains.annotations.Nullable java.lang.Throwable cause) {
        if ($this$closeFinally != null) {
            if (cause != null) {
                try {
                    $this$closeFinally.close();
                    return;
                } catch (java.lang.Throwable closeException) {
                    com.android.server.permission.jarjar.kotlin.ExceptionsKt__ExceptionsKt.addSuppressed(cause, closeException);
                    return;
                }
            }
            $this$closeFinally.close();
        }
    }
}
