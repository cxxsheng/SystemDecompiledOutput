package com.android.server.display;

/* loaded from: classes.dex */
class ExtendedRemoteDisplayHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ExtendedRemoteDisplayHelper";
    private static java.lang.Class sExtRemoteDisplayClass;
    private static java.lang.reflect.Method sExtRemoteDisplayDispose;
    private static java.lang.reflect.Method sExtRemoteDisplayListen;

    ExtendedRemoteDisplayHelper() {
    }

    static {
        try {
            sExtRemoteDisplayClass = java.lang.Class.forName("com.qualcomm.wfd.ExtendedRemoteDisplay");
        } catch (java.lang.Throwable th) {
            android.util.Slog.i(TAG, "ExtendedRemoteDisplay: not available");
        }
        if (sExtRemoteDisplayClass != null) {
            android.util.Slog.i(TAG, "ExtendedRemoteDisplay: is available, finding methods");
            try {
                sExtRemoteDisplayListen = sExtRemoteDisplayClass.getDeclaredMethod("listen", java.lang.String.class, android.media.RemoteDisplay.Listener.class, android.os.Handler.class, android.content.Context.class);
            } catch (java.lang.Throwable th2) {
                android.util.Slog.i(TAG, "ExtendedRemoteDisplay.listen: not available");
            }
            try {
                sExtRemoteDisplayDispose = sExtRemoteDisplayClass.getDeclaredMethod("dispose", new java.lang.Class[0]);
            } catch (java.lang.Throwable th3) {
                android.util.Slog.i(TAG, "ExtendedRemoteDisplay.dispose: not available");
            }
        }
    }

    public static java.lang.Object listen(java.lang.String str, android.media.RemoteDisplay.Listener listener, android.os.Handler handler, android.content.Context context) {
        if (sExtRemoteDisplayListen == null || sExtRemoteDisplayDispose == null) {
            return null;
        }
        try {
            return sExtRemoteDisplayListen.invoke(null, str, listener, handler, context);
        } catch (java.lang.IllegalAccessException e) {
            android.util.Slog.e(TAG, "ExtendedRemoteDisplay.listen: IllegalAccessException", e);
            return null;
        } catch (java.lang.reflect.InvocationTargetException e2) {
            android.util.Slog.e(TAG, "ExtendedRemoteDisplay.listen: InvocationTargetException");
            java.lang.Throwable cause = e2.getCause();
            if (cause instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) cause);
            }
            if (cause instanceof java.lang.Error) {
                throw ((java.lang.Error) cause);
            }
            throw new java.lang.RuntimeException(e2);
        }
    }

    public static void dispose(java.lang.Object obj) {
        try {
            sExtRemoteDisplayDispose.invoke(obj, new java.lang.Object[0]);
        } catch (java.lang.IllegalAccessException e) {
            android.util.Slog.e(TAG, "ExtendedRemoteDisplay.dispose: IllegalAccessException", e);
        } catch (java.lang.reflect.InvocationTargetException e2) {
            android.util.Slog.e(TAG, "ExtendedRemoteDisplay.dispose: InvocationTargetException");
            java.lang.Throwable cause = e2.getCause();
            if (cause instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) cause);
            }
            if (cause instanceof java.lang.Error) {
                throw ((java.lang.Error) cause);
            }
            throw new java.lang.RuntimeException(e2);
        }
    }

    public static boolean isAvailable() {
        return (sExtRemoteDisplayClass == null || sExtRemoteDisplayDispose == null || sExtRemoteDisplayListen == null) ? false : true;
    }
}
