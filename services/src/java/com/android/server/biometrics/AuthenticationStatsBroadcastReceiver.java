package com.android.server.biometrics;

/* loaded from: classes.dex */
public class AuthenticationStatsBroadcastReceiver extends android.content.BroadcastReceiver {
    private static final java.lang.String TAG = "AuthenticationStatsBroadcastReceiver";

    @android.annotation.NonNull
    private final java.util.function.Consumer<com.android.server.biometrics.AuthenticationStatsCollector> mCollectorConsumer;
    private final int mModality;

    public AuthenticationStatsBroadcastReceiver(@android.annotation.NonNull android.content.Context context, int i, @android.annotation.NonNull java.util.function.Consumer<com.android.server.biometrics.AuthenticationStatsCollector> consumer) {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        context.registerReceiver(this, intentFilter);
        this.mCollectorConsumer = consumer;
        this.mModality = i;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.content.Intent intent) {
        if (intent.getIntExtra("android.intent.extra.user_handle", com.android.server.am.ProcessList.INVALID_ADJ) != -10000 && "android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
            android.util.Slog.d(TAG, "Received: " + intent.getAction());
            this.mCollectorConsumer.accept(new com.android.server.biometrics.AuthenticationStatsCollector(context, this.mModality, new com.android.server.biometrics.sensors.BiometricNotificationImpl()));
            context.unregisterReceiver(this);
        }
    }
}
