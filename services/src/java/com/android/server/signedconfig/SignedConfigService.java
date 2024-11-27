package com.android.server.signedconfig;

/* loaded from: classes2.dex */
public class SignedConfigService {
    private static final boolean DBG = false;
    private static final java.lang.String KEY_GLOBAL_SETTINGS = "android.settings.global";
    private static final java.lang.String KEY_GLOBAL_SETTINGS_SIGNATURE = "android.settings.global.signature";
    private static final java.lang.String TAG = "SignedConfig";
    private final android.content.Context mContext;
    private final android.content.pm.PackageManagerInternal mPacMan = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);

    private static class UpdateReceiver extends android.content.BroadcastReceiver {
        private UpdateReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            new com.android.server.signedconfig.SignedConfigService(context).handlePackageBroadcast(intent);
        }
    }

    public SignedConfigService(android.content.Context context) {
        this.mContext = context;
    }

    void handlePackageBroadcast(android.content.Intent intent) {
        android.net.Uri data = intent.getData();
        java.lang.String schemeSpecificPart = data == null ? null : data.getSchemeSpecificPart();
        if (schemeSpecificPart == null) {
            return;
        }
        int identifier = this.mContext.getUser().getIdentifier();
        android.content.pm.PackageInfo packageInfo = this.mPacMan.getPackageInfo(schemeSpecificPart, 128L, 1000, identifier);
        if (packageInfo == null) {
            android.util.Slog.w(TAG, "Got null PackageInfo for " + schemeSpecificPart + "; user " + identifier);
            return;
        }
        android.os.Bundle bundle = packageInfo.applicationInfo.metaData;
        if (bundle != null && bundle.containsKey(KEY_GLOBAL_SETTINGS) && bundle.containsKey(KEY_GLOBAL_SETTINGS_SIGNATURE)) {
            com.android.server.signedconfig.SignedConfigEvent signedConfigEvent = new com.android.server.signedconfig.SignedConfigEvent();
            try {
                signedConfigEvent.type = 1;
                signedConfigEvent.fromPackage = schemeSpecificPart;
                new com.android.server.signedconfig.GlobalSettingsConfigApplicator(this.mContext, schemeSpecificPart, signedConfigEvent).applyConfig(new java.lang.String(java.util.Base64.getDecoder().decode(bundle.getString(KEY_GLOBAL_SETTINGS)), java.nio.charset.StandardCharsets.UTF_8), bundle.getString(KEY_GLOBAL_SETTINGS_SIGNATURE));
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.e(TAG, "Failed to base64 decode global settings config from " + schemeSpecificPart);
                signedConfigEvent.status = 2;
            } finally {
                signedConfigEvent.send();
            }
        }
    }

    public static void registerUpdateReceiver(android.content.Context context) {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        context.registerReceiver(new com.android.server.signedconfig.SignedConfigService.UpdateReceiver(), intentFilter);
    }
}
