package com.android.net.module.util;

/* loaded from: classes5.dex */
public class BinderUtils {

    @java.lang.FunctionalInterface
    public interface ThrowingRunnable<T extends java.lang.Exception> {
        void run() throws java.lang.Exception;
    }

    @java.lang.FunctionalInterface
    public interface ThrowingSupplier<T, E extends java.lang.Exception> {
        T get() throws java.lang.Exception;
    }

    public static final <T extends java.lang.Exception> void withCleanCallingIdentity(com.android.net.module.util.BinderUtils.ThrowingRunnable<T> throwingRunnable) throws java.lang.Exception {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            throwingRunnable.run();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static final <T, E extends java.lang.Exception> T withCleanCallingIdentity(com.android.net.module.util.BinderUtils.ThrowingSupplier<T, E> throwingSupplier) throws java.lang.Exception {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return throwingSupplier.get();
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
