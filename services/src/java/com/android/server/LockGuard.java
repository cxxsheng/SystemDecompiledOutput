package com.android.server;

/* loaded from: classes.dex */
public class LockGuard {
    public static final int INDEX_ACTIVITY = 7;
    public static final int INDEX_APP_OPS = 0;
    public static final int INDEX_DPMS = 8;
    public static final int INDEX_PACKAGES = 3;
    public static final int INDEX_POWER = 1;
    public static final int INDEX_PROC = 6;
    public static final int INDEX_STORAGE = 4;
    public static final int INDEX_USER = 2;
    public static final int INDEX_WINDOW = 5;
    private static final java.lang.String TAG = "LockGuard";
    private static java.lang.Object[] sKnownFixed = new java.lang.Object[9];
    private static android.util.ArrayMap<java.lang.Object, com.android.server.LockGuard.LockInfo> sKnown = new android.util.ArrayMap<>(0, true);

    private static class LockInfo {
        public android.util.ArraySet<java.lang.Object> children;
        public boolean doWtf;
        public java.lang.String label;

        private LockInfo() {
            this.children = new android.util.ArraySet<>(0, true);
        }
    }

    private static com.android.server.LockGuard.LockInfo findOrCreateLockInfo(java.lang.Object obj) {
        com.android.server.LockGuard.LockInfo lockInfo = sKnown.get(obj);
        if (lockInfo == null) {
            com.android.server.LockGuard.LockInfo lockInfo2 = new com.android.server.LockGuard.LockInfo();
            lockInfo2.label = "0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(obj)) + " [" + new java.lang.Throwable().getStackTrace()[2].toString() + "]";
            sKnown.put(obj, lockInfo2);
            return lockInfo2;
        }
        return lockInfo;
    }

    public static java.lang.Object guard(java.lang.Object obj) {
        if (obj == null || java.lang.Thread.holdsLock(obj)) {
            return obj;
        }
        com.android.server.LockGuard.LockInfo findOrCreateLockInfo = findOrCreateLockInfo(obj);
        boolean z = false;
        for (int i = 0; i < findOrCreateLockInfo.children.size(); i++) {
            java.lang.Object valueAt = findOrCreateLockInfo.children.valueAt(i);
            if (valueAt != null && java.lang.Thread.holdsLock(valueAt)) {
                doLog(obj, "Calling thread " + java.lang.Thread.currentThread().getName() + " is holding " + lockToString(valueAt) + " while trying to acquire " + lockToString(obj));
                z = true;
            }
        }
        if (!z) {
            for (int i2 = 0; i2 < sKnown.size(); i2++) {
                java.lang.Object keyAt = sKnown.keyAt(i2);
                if (keyAt != null && keyAt != obj && java.lang.Thread.holdsLock(keyAt)) {
                    sKnown.valueAt(i2).children.add(obj);
                }
            }
        }
        return obj;
    }

    public static void guard(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            java.lang.Object obj = sKnownFixed[i2];
            if (obj != null && java.lang.Thread.holdsLock(obj)) {
                doLog(sKnownFixed[i], "Calling thread " + java.lang.Thread.currentThread().getName() + " is holding " + lockToString(i2) + " while trying to acquire " + lockToString(i));
            }
        }
    }

    private static void doLog(@android.annotation.Nullable java.lang.Object obj, java.lang.String str) {
        if (obj != null && findOrCreateLockInfo(obj).doWtf) {
            final java.lang.RuntimeException runtimeException = new java.lang.RuntimeException(str);
            new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.LockGuard$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.util.Slog.wtf(com.android.server.LockGuard.TAG, runtimeException);
                }
            }).start();
        } else {
            android.util.Slog.w(TAG, str, new java.lang.Throwable());
        }
    }

    public static java.lang.Object installLock(java.lang.Object obj, java.lang.String str) {
        findOrCreateLockInfo(obj).label = str;
        return obj;
    }

    public static java.lang.Object installLock(java.lang.Object obj, int i) {
        return installLock(obj, i, false);
    }

    public static java.lang.Object installLock(java.lang.Object obj, int i, boolean z) {
        sKnownFixed[i] = obj;
        com.android.server.LockGuard.LockInfo findOrCreateLockInfo = findOrCreateLockInfo(obj);
        findOrCreateLockInfo.doWtf = z;
        findOrCreateLockInfo.label = "Lock-" + lockToString(i);
        return obj;
    }

    public static java.lang.Object installNewLock(int i) {
        return installNewLock(i, false);
    }

    public static java.lang.Object installNewLock(int i, boolean z) {
        java.lang.Object obj = new java.lang.Object();
        installLock(obj, i, z);
        return obj;
    }

    private static java.lang.String lockToString(java.lang.Object obj) {
        com.android.server.LockGuard.LockInfo lockInfo = sKnown.get(obj);
        if (lockInfo != null && !android.text.TextUtils.isEmpty(lockInfo.label)) {
            return lockInfo.label;
        }
        return "0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(obj));
    }

    private static java.lang.String lockToString(int i) {
        switch (i) {
            case 0:
                return "APP_OPS";
            case 1:
                return "POWER";
            case 2:
                return "USER";
            case 3:
                return "PACKAGES";
            case 4:
                return "STORAGE";
            case 5:
                return "WINDOW";
            case 6:
                return "PROCESS";
            case 7:
                return "ACTIVITY";
            case 8:
                return "DPMS";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        for (int i = 0; i < sKnown.size(); i++) {
            java.lang.Object keyAt = sKnown.keyAt(i);
            com.android.server.LockGuard.LockInfo valueAt = sKnown.valueAt(i);
            printWriter.println("Lock " + lockToString(keyAt) + ":");
            for (int i2 = 0; i2 < valueAt.children.size(); i2++) {
                printWriter.println("  Child " + lockToString(valueAt.children.valueAt(i2)));
            }
            printWriter.println();
        }
    }
}
