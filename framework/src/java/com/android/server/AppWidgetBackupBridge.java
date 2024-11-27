package com.android.server;

/* loaded from: classes5.dex */
public class AppWidgetBackupBridge {
    private static com.android.server.WidgetBackupProvider sAppWidgetService;

    public static void register(com.android.server.WidgetBackupProvider widgetBackupProvider) {
        sAppWidgetService = widgetBackupProvider;
    }

    public static java.util.List<java.lang.String> getWidgetParticipants(int i) {
        if (sAppWidgetService != null) {
            return sAppWidgetService.getWidgetParticipants(i);
        }
        return null;
    }

    public static byte[] getWidgetState(java.lang.String str, int i) {
        if (sAppWidgetService != null) {
            return sAppWidgetService.getWidgetState(str, i);
        }
        return null;
    }

    public static void systemRestoreStarting(int i) {
        if (sAppWidgetService != null) {
            sAppWidgetService.systemRestoreStarting(i);
        }
    }

    public static void restoreWidgetState(java.lang.String str, byte[] bArr, int i) {
        if (sAppWidgetService != null) {
            sAppWidgetService.restoreWidgetState(str, bArr, i);
        }
    }

    public static void systemRestoreFinished(int i) {
        if (sAppWidgetService != null) {
            sAppWidgetService.systemRestoreFinished(i);
        }
    }
}
