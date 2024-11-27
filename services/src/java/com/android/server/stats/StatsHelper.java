package com.android.server.stats;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public final class StatsHelper {
    private StatsHelper() {
    }

    public static void sendStatsdReadyBroadcast(@android.annotation.NonNull android.content.Context context) {
        context.sendBroadcastAsUser(new android.content.Intent("android.app.action.STATSD_STARTED").addFlags(16777216), android.os.UserHandle.SYSTEM, "android.permission.DUMP");
    }
}
