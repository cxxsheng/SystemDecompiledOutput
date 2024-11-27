package android.service.restrictions;

/* loaded from: classes3.dex */
public abstract class RestrictionsReceiver extends android.content.BroadcastReceiver {
    private static final java.lang.String TAG = "RestrictionsReceiver";

    public abstract void onRequestPermission(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.PersistableBundle persistableBundle);

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if (android.content.RestrictionsManager.ACTION_REQUEST_PERMISSION.equals(intent.getAction())) {
            onRequestPermission(context, intent.getStringExtra(android.content.RestrictionsManager.EXTRA_PACKAGE_NAME), intent.getStringExtra(android.content.RestrictionsManager.EXTRA_REQUEST_TYPE), intent.getStringExtra(android.content.RestrictionsManager.EXTRA_REQUEST_ID), (android.os.PersistableBundle) intent.getParcelableExtra(android.content.RestrictionsManager.EXTRA_REQUEST_BUNDLE, android.os.PersistableBundle.class));
        }
    }
}
