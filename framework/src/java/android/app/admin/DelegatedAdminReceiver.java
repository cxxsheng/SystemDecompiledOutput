package android.app.admin;

/* loaded from: classes.dex */
public class DelegatedAdminReceiver extends android.content.BroadcastReceiver {
    private static final java.lang.String TAG = "DelegatedAdminReceiver";

    public java.lang.String onChoosePrivateKeyAlias(android.content.Context context, android.content.Intent intent, int i, android.net.Uri uri, java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("onChoosePrivateKeyAlias should be implemented");
    }

    public void onNetworkLogsAvailable(android.content.Context context, android.content.Intent intent, long j, int i) {
        throw new java.lang.UnsupportedOperationException("onNetworkLogsAvailable should be implemented");
    }

    public void onSecurityLogsAvailable(android.content.Context context, android.content.Intent intent) {
        throw new java.lang.UnsupportedOperationException("onSecurityLogsAvailable should be implemented");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(android.content.Context context, android.content.Intent intent) {
        java.lang.String action = intent.getAction();
        if (android.app.admin.DeviceAdminReceiver.ACTION_CHOOSE_PRIVATE_KEY_ALIAS.equals(action)) {
            setResultData(onChoosePrivateKeyAlias(context, intent, intent.getIntExtra(android.app.admin.DeviceAdminReceiver.EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID, -1), (android.net.Uri) intent.getParcelableExtra(android.app.admin.DeviceAdminReceiver.EXTRA_CHOOSE_PRIVATE_KEY_URI, android.net.Uri.class), intent.getStringExtra(android.app.admin.DeviceAdminReceiver.EXTRA_CHOOSE_PRIVATE_KEY_ALIAS)));
            return;
        }
        if (android.app.admin.DeviceAdminReceiver.ACTION_NETWORK_LOGS_AVAILABLE.equals(action)) {
            onNetworkLogsAvailable(context, intent, intent.getLongExtra(android.app.admin.DeviceAdminReceiver.EXTRA_NETWORK_LOGS_TOKEN, -1L), intent.getIntExtra(android.app.admin.DeviceAdminReceiver.EXTRA_NETWORK_LOGS_COUNT, 0));
        } else if (android.app.admin.DeviceAdminReceiver.ACTION_SECURITY_LOGS_AVAILABLE.equals(action)) {
            onSecurityLogsAvailable(context, intent);
        } else {
            android.util.Log.w(TAG, "Unhandled broadcast: " + action);
        }
    }
}
