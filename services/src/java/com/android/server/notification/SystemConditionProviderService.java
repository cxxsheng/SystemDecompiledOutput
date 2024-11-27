package com.android.server.notification;

/* loaded from: classes2.dex */
public abstract class SystemConditionProviderService extends android.service.notification.ConditionProviderService {
    public abstract android.service.notification.IConditionProvider asInterface();

    public abstract void attachBase(android.content.Context context);

    public abstract void dump(java.io.PrintWriter printWriter, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter);

    public abstract android.content.ComponentName getComponent();

    public abstract boolean isValidConditionId(android.net.Uri uri);

    public abstract void onBootComplete();

    protected static java.lang.String ts(long j) {
        return new java.util.Date(j) + " (" + j + ")";
    }

    protected static java.lang.String formatDuration(long j) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        android.util.TimeUtils.formatDuration(j, sb);
        return sb.toString();
    }

    protected static void dumpUpcomingTime(java.io.PrintWriter printWriter, java.lang.String str, long j, long j2) {
        printWriter.print("      ");
        printWriter.print(str);
        printWriter.print('=');
        if (j > 0) {
            printWriter.printf("%s, in %s, now=%s", ts(j), formatDuration(j - j2), ts(j2));
        } else {
            printWriter.print(j);
        }
        printWriter.println();
    }
}
