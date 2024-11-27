package android.os;

/* loaded from: classes3.dex */
public class SELinux {
    private static final int SELINUX_ANDROID_RESTORECON_CROSS_FILESYSTEMS = 64;
    private static final int SELINUX_ANDROID_RESTORECON_DATADATA = 16;
    private static final int SELINUX_ANDROID_RESTORECON_FORCE = 8;
    private static final int SELINUX_ANDROID_RESTORECON_NOCHANGE = 1;
    private static final int SELINUX_ANDROID_RESTORECON_RECURSE = 4;
    private static final int SELINUX_ANDROID_RESTORECON_SKIPCE = 32;
    private static final int SELINUX_ANDROID_RESTORECON_SKIP_SEHASH = 128;
    private static final int SELINUX_ANDROID_RESTORECON_VERBOSE = 2;
    private static final java.lang.String TAG = "SELinux";

    public static final native boolean checkSELinuxAccess(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4);

    public static final native java.lang.String fileSelabelLookup(java.lang.String str);

    public static final native java.lang.String getContext();

    public static final native java.lang.String getFileContext(java.io.FileDescriptor fileDescriptor);

    public static final native java.lang.String getFileContext(java.lang.String str);

    public static final native java.lang.String getPeerContext(java.io.FileDescriptor fileDescriptor);

    public static final native java.lang.String getPidContext(int i);

    public static final native boolean isSELinuxEnabled();

    public static final native boolean isSELinuxEnforced();

    private static native boolean native_restorecon(java.lang.String str, int i);

    public static final native boolean setFSCreateContext(java.lang.String str);

    public static final native boolean setFileContext(java.lang.String str, java.lang.String str2);

    public static boolean restorecon(java.lang.String str) throws java.lang.NullPointerException {
        if (str == null) {
            throw new java.lang.NullPointerException();
        }
        return native_restorecon(str, 0);
    }

    public static boolean restorecon(java.io.File file) throws java.lang.NullPointerException {
        try {
            return native_restorecon(file.getCanonicalPath(), 0);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error getting canonical path. Restorecon failed for " + file.getPath(), e);
            return false;
        }
    }

    public static boolean restoreconRecursive(java.io.File file) {
        try {
            return native_restorecon(file.getCanonicalPath(), 132);
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Error getting canonical path. Restorecon failed for " + file.getPath(), e);
            return false;
        }
    }
}
