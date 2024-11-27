package com.android.internal.app;

/* loaded from: classes4.dex */
public class BlockedAppActivity extends com.android.internal.app.AlertActivity {
    private static final java.lang.String EXTRA_BLOCKED_PACKAGE = "com.android.internal.app.extra.BLOCKED_PACKAGE";
    private static final java.lang.String PACKAGE_NAME = "com.android.internal.app";
    private static final java.lang.String TAG = "BlockedAppActivity";

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.content.Intent intent = getIntent();
        int intExtra = intent.getIntExtra(android.content.Intent.EXTRA_USER_ID, -1);
        if (intExtra < 0) {
            android.util.Slog.wtf(TAG, "Invalid user: " + intExtra);
            finish();
            return;
        }
        java.lang.String stringExtra = intent.getStringExtra(EXTRA_BLOCKED_PACKAGE);
        if (android.text.TextUtils.isEmpty(stringExtra)) {
            android.util.Slog.wtf(TAG, "Invalid package: " + stringExtra);
            finish();
            return;
        }
        java.lang.CharSequence appLabel = getAppLabel(intExtra, stringExtra);
        this.mAlertParams.mTitle = getString(com.android.internal.R.string.app_blocked_title);
        this.mAlertParams.mMessage = getString(com.android.internal.R.string.app_blocked_message, appLabel);
        this.mAlertParams.mPositiveButtonText = getString(17039370);
        setupAlert();
    }

    private java.lang.CharSequence getAppLabel(int i, java.lang.String str) {
        android.content.pm.PackageManager packageManager = getPackageManager();
        try {
            return packageManager.getApplicationInfoAsUser(str, 0, i).loadLabel(packageManager);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Package " + str + " not found", e);
            return str;
        }
    }

    public static android.content.Intent createIntent(int i, java.lang.String str) {
        return new android.content.Intent().setClassName("android", com.android.internal.app.BlockedAppActivity.class.getName()).putExtra(android.content.Intent.EXTRA_USER_ID, i).putExtra(EXTRA_BLOCKED_PACKAGE, str);
    }
}
