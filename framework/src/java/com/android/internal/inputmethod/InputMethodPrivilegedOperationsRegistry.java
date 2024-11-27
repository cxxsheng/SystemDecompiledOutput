package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class InputMethodPrivilegedOperationsRegistry {
    private static final java.lang.Object sLock = new java.lang.Object();
    private static com.android.internal.inputmethod.InputMethodPrivilegedOperations sNop;
    private static java.util.WeakHashMap<android.os.IBinder, java.lang.ref.WeakReference<com.android.internal.inputmethod.InputMethodPrivilegedOperations>> sRegistry;

    private InputMethodPrivilegedOperationsRegistry() {
    }

    private static com.android.internal.inputmethod.InputMethodPrivilegedOperations getNopOps() {
        if (sNop == null) {
            sNop = new com.android.internal.inputmethod.InputMethodPrivilegedOperations();
        }
        return sNop;
    }

    public static void put(android.os.IBinder iBinder, com.android.internal.inputmethod.InputMethodPrivilegedOperations inputMethodPrivilegedOperations) {
        synchronized (sLock) {
            if (sRegistry == null) {
                sRegistry = new java.util.WeakHashMap<>();
            }
            sRegistry.put(iBinder, new java.lang.ref.WeakReference<>(inputMethodPrivilegedOperations));
        }
    }

    public static com.android.internal.inputmethod.InputMethodPrivilegedOperations get(android.os.IBinder iBinder) {
        synchronized (sLock) {
            if (sRegistry == null) {
                return getNopOps();
            }
            java.lang.ref.WeakReference<com.android.internal.inputmethod.InputMethodPrivilegedOperations> weakReference = sRegistry.get(iBinder);
            if (weakReference == null) {
                return getNopOps();
            }
            com.android.internal.inputmethod.InputMethodPrivilegedOperations inputMethodPrivilegedOperations = weakReference.get();
            if (inputMethodPrivilegedOperations != null) {
                return inputMethodPrivilegedOperations;
            }
            return getNopOps();
        }
    }

    public static void remove(android.os.IBinder iBinder) {
        synchronized (sLock) {
            if (sRegistry == null) {
                return;
            }
            sRegistry.remove(iBinder);
            if (sRegistry.isEmpty()) {
                sRegistry = null;
            }
        }
    }
}
