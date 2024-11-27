package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class SystemProperties {
    public static final int PROP_NAME_MAX = Integer.MAX_VALUE;
    public static final int PROP_VALUE_MAX = 91;
    private static final java.lang.String TAG = "SystemProperties";
    private static final boolean TRACK_KEY_ACCESS = false;
    private static final java.util.ArrayList<java.lang.Runnable> sChangeCallbacks = new java.util.ArrayList<>();
    private static final java.util.HashMap<java.lang.String, android.util.MutableInt> sRoReads = null;

    private static native void native_add_change_callback();

    @dalvik.annotation.optimization.FastNative
    private static native long native_find(java.lang.String str);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.FastNative
    public static native java.lang.String native_get(long j);

    @dalvik.annotation.optimization.FastNative
    private static native java.lang.String native_get(java.lang.String str, java.lang.String str2);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native boolean native_get_boolean(long j, boolean z);

    @dalvik.annotation.optimization.FastNative
    private static native boolean native_get_boolean(java.lang.String str, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native int native_get_int(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native int native_get_int(java.lang.String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @dalvik.annotation.optimization.CriticalNative
    public static native long native_get_long(long j, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native long native_get_long(java.lang.String str, long j);

    private static native void native_init$ravenwood(java.util.Map<java.lang.String, java.lang.String> map, java.util.function.Predicate<java.lang.String> predicate, java.util.function.Predicate<java.lang.String> predicate2, java.lang.Runnable runnable);

    private static native void native_report_sysprop_change();

    private static native void native_reset$ravenwood();

    private static native void native_set(java.lang.String str, java.lang.String str2);

    private static void onKeyAccess(java.lang.String str) {
    }

    public static void init$ravenwood(java.util.Map<java.lang.String, java.lang.String> map, java.util.function.Predicate<java.lang.String> predicate, java.util.function.Predicate<java.lang.String> predicate2) {
        native_init$ravenwood(map, predicate, predicate2, new java.lang.Runnable() { // from class: android.os.SystemProperties$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.os.SystemProperties.callChangeCallbacks();
            }
        });
        synchronized (sChangeCallbacks) {
            sChangeCallbacks.clear();
        }
    }

    public static void reset$ravenwood() {
        native_reset$ravenwood();
        synchronized (sChangeCallbacks) {
            sChangeCallbacks.clear();
        }
    }

    private static java.lang.String native_get(java.lang.String str) {
        return native_get(str, "");
    }

    @android.annotation.SystemApi
    public static java.lang.String get(java.lang.String str) {
        return native_get(str);
    }

    @android.annotation.SystemApi
    public static java.lang.String get(java.lang.String str, java.lang.String str2) {
        return native_get(str, str2);
    }

    @android.annotation.SystemApi
    public static int getInt(java.lang.String str, int i) {
        return native_get_int(str, i);
    }

    @android.annotation.SystemApi
    public static long getLong(java.lang.String str, long j) {
        return native_get_long(str, j);
    }

    @android.annotation.SystemApi
    public static boolean getBoolean(java.lang.String str, boolean z) {
        return native_get_boolean(str, z);
    }

    public static void set(java.lang.String str, java.lang.String str2) {
        if (str2 != null && !str.startsWith("ro.") && str2.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 91) {
            throw new java.lang.IllegalArgumentException("value of system property '" + str + "' is longer than 91 bytes: " + str2);
        }
        native_set(str, str2);
    }

    public static void addChangeCallback(java.lang.Runnable runnable) {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.size() == 0) {
                native_add_change_callback();
            }
            sChangeCallbacks.add(runnable);
        }
    }

    public static void removeChangeCallback(java.lang.Runnable runnable) {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.contains(runnable)) {
                sChangeCallbacks.remove(runnable);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void callChangeCallbacks() {
        synchronized (sChangeCallbacks) {
            if (sChangeCallbacks.size() == 0) {
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList(sChangeCallbacks);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            for (int i = 0; i < arrayList.size(); i++) {
                try {
                    try {
                        ((java.lang.Runnable) arrayList.get(i)).run();
                    } catch (java.lang.Throwable th) {
                        android.util.Log.e(TAG, "Exception in SystemProperties change callback", th);
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    public static void reportSyspropChanged() {
        native_report_sysprop_change();
    }

    public static java.lang.String digestOf(java.lang.String... strArr) {
        java.util.Arrays.sort(strArr);
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-1");
            for (java.lang.String str : strArr) {
                messageDigest.update((str + "=" + get(str) + "\n").getBytes(java.nio.charset.StandardCharsets.UTF_8));
            }
            return libcore.util.HexEncoding.encodeToString(messageDigest.digest()).toLowerCase();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private SystemProperties() {
    }

    public static android.os.SystemProperties.Handle find(java.lang.String str) {
        long native_find = native_find(str);
        if (native_find == 0) {
            return null;
        }
        return new android.os.SystemProperties.Handle(native_find);
    }

    public static final class Handle {
        private final long mNativeHandle;

        public java.lang.String get() {
            return android.os.SystemProperties.native_get(this.mNativeHandle);
        }

        public int getInt(int i) {
            return android.os.SystemProperties.native_get_int(this.mNativeHandle, i);
        }

        public long getLong(long j) {
            return android.os.SystemProperties.native_get_long(this.mNativeHandle, j);
        }

        public boolean getBoolean(boolean z) {
            return android.os.SystemProperties.native_get_boolean(this.mNativeHandle, z);
        }

        private Handle(long j) {
            this.mNativeHandle = j;
        }
    }
}
