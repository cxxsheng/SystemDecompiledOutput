package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public abstract class ActivityManagerNative {
    public static android.app.IActivityManager asInterface(android.os.IBinder iBinder) {
        return android.app.IActivityManager.Stub.asInterface(iBinder);
    }

    public static android.app.IActivityManager getDefault() {
        return android.app.ActivityManager.getService();
    }

    public static boolean isSystemReady() {
        return android.app.ActivityManager.isSystemReady();
    }

    public static void broadcastStickyIntent(android.content.Intent intent, java.lang.String str, int i) {
        broadcastStickyIntent(intent, str, -1, i);
    }

    public static void broadcastStickyIntent(android.content.Intent intent, java.lang.String str, int i, int i2) {
        android.app.ActivityManager.broadcastStickyIntent(intent, i, i2);
    }

    public static void noteWakeupAlarm(android.app.PendingIntent pendingIntent, int i, java.lang.String str, java.lang.String str2) {
        android.app.ActivityManager.noteWakeupAlarm(pendingIntent, null, i, str, str2);
    }

    public static void noteAlarmStart(android.app.PendingIntent pendingIntent, int i, java.lang.String str) {
        android.app.ActivityManager.noteAlarmStart(pendingIntent, null, i, str);
    }

    public static void noteAlarmFinish(android.app.PendingIntent pendingIntent, int i, java.lang.String str) {
        android.app.ActivityManager.noteAlarmFinish(pendingIntent, null, i, str);
    }
}
