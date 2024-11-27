package android.util;

/* loaded from: classes3.dex */
public class ExceptionUtils {
    public static java.lang.RuntimeException wrap(java.io.IOException iOException) {
        throw new android.os.ParcelableException(iOException);
    }

    public static void maybeUnwrapIOException(java.lang.RuntimeException runtimeException) throws java.io.IOException {
        if (runtimeException instanceof android.os.ParcelableException) {
            ((android.os.ParcelableException) runtimeException).maybeRethrow(java.io.IOException.class);
        }
    }

    public static java.lang.String getCompleteMessage(java.lang.String str, java.lang.Throwable th) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (str != null) {
            sb.append(str).append(": ");
        }
        sb.append(th.getMessage());
        while (true) {
            th = th.getCause();
            if (th != null) {
                sb.append(": ").append(th.getMessage());
            } else {
                return sb.toString();
            }
        }
    }

    public static java.lang.String getCompleteMessage(java.lang.Throwable th) {
        return getCompleteMessage(null, th);
    }

    public static <E extends java.lang.Throwable> void propagateIfInstanceOf(java.lang.Throwable th, java.lang.Class<E> cls) throws java.lang.Throwable {
        if (th != null && cls.isInstance(th)) {
            throw cls.cast(th);
        }
    }

    public static <E extends java.lang.Exception> java.lang.RuntimeException propagate(java.lang.Throwable th, java.lang.Class<E> cls) throws java.lang.Exception {
        propagateIfInstanceOf(th, cls);
        return propagate(th);
    }

    public static java.lang.RuntimeException propagate(java.lang.Throwable th) {
        com.android.internal.util.Preconditions.checkNotNull(th);
        propagateIfInstanceOf(th, java.lang.Error.class);
        propagateIfInstanceOf(th, java.lang.RuntimeException.class);
        throw new java.lang.RuntimeException(th);
    }

    public static java.lang.Throwable getRootCause(java.lang.Throwable th) {
        while (th.getCause() != null) {
            th = th.getCause();
        }
        return th;
    }

    public static java.lang.Throwable appendCause(java.lang.Throwable th, java.lang.Throwable th2) {
        if (th2 != null) {
            getRootCause(th).initCause(th2);
        }
        return th;
    }
}
