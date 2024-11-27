package com.android.server;

/* loaded from: classes5.dex */
public final class LocalServices {
    private static final android.util.ArrayMap<java.lang.Class<?>, java.lang.Object> sLocalServiceObjects = new android.util.ArrayMap<>();

    private LocalServices() {
    }

    public static <T> T getService(java.lang.Class<T> cls) {
        T t;
        synchronized (sLocalServiceObjects) {
            t = (T) sLocalServiceObjects.get(cls);
        }
        return t;
    }

    public static <T> void addService(java.lang.Class<T> cls, T t) {
        synchronized (sLocalServiceObjects) {
            if (sLocalServiceObjects.containsKey(cls)) {
                throw new java.lang.IllegalStateException("Overriding service registration");
            }
            sLocalServiceObjects.put(cls, t);
        }
    }

    public static <T> void removeServiceForTest(java.lang.Class<T> cls) {
        synchronized (sLocalServiceObjects) {
            sLocalServiceObjects.remove(cls);
        }
    }

    public static void removeAllServicesForTest() {
        synchronized (sLocalServiceObjects) {
            sLocalServiceObjects.clear();
        }
    }
}
