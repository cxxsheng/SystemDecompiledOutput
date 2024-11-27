package com.android.server;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes.dex */
public final class LocalManagerRegistry {

    @android.annotation.NonNull
    private static final java.util.Map<java.lang.Class<?>, java.lang.Object> sManagers = new android.util.ArrayMap();

    private LocalManagerRegistry() {
    }

    @android.annotation.Nullable
    public static <T> T getManager(@android.annotation.NonNull java.lang.Class<T> cls) {
        T t;
        synchronized (sManagers) {
            t = (T) sManagers.get(cls);
        }
        return t;
    }

    @android.annotation.NonNull
    public static <T> T getManagerOrThrow(@android.annotation.NonNull java.lang.Class<T> cls) throws com.android.server.LocalManagerRegistry.ManagerNotFoundException {
        T t = (T) getManager(cls);
        if (t == null) {
            throw new com.android.server.LocalManagerRegistry.ManagerNotFoundException(cls);
        }
        return t;
    }

    public static <T> void addManager(@android.annotation.NonNull java.lang.Class<T> cls, @android.annotation.NonNull T t) {
        java.util.Objects.requireNonNull(cls, "managerClass");
        java.util.Objects.requireNonNull(t, "manager");
        synchronized (sManagers) {
            try {
                if (sManagers.containsKey(cls)) {
                    throw new java.lang.IllegalStateException(cls.getName() + " is already registered");
                }
                sManagers.put(cls, t);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public static class ManagerNotFoundException extends java.lang.Exception {
        public <T> ManagerNotFoundException(@android.annotation.NonNull java.lang.Class<T> cls) {
            super("Local manager " + cls.getName() + " does not exist or is not ready");
        }
    }
}
