package android.app;

/* loaded from: classes.dex */
public class AppGlobals {
    public static android.app.Application getInitialApplication() {
        return android.app.ActivityThread.currentApplication();
    }

    public static java.lang.String getInitialPackage() {
        return android.app.ActivityThread.currentPackageName();
    }

    public static android.content.pm.IPackageManager getPackageManager() {
        return android.app.ActivityThread.getPackageManager();
    }

    public static android.permission.IPermissionManager getPermissionManager() {
        return android.app.ActivityThread.getPermissionManager();
    }

    public static int getIntCoreSetting(java.lang.String str, int i) {
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        if (currentActivityThread != null) {
            return currentActivityThread.getIntCoreSetting(str, i);
        }
        return i;
    }

    public static float getFloatCoreSetting(java.lang.String str, float f) {
        android.app.ActivityThread currentActivityThread = android.app.ActivityThread.currentActivityThread();
        if (currentActivityThread != null) {
            return currentActivityThread.getFloatCoreSetting(str, f);
        }
        return f;
    }
}
