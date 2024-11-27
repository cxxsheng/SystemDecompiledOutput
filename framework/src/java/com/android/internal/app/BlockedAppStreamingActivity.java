package com.android.internal.app;

/* loaded from: classes4.dex */
public class BlockedAppStreamingActivity extends com.android.internal.app.AlertActivity {
    private static final java.lang.String BLOCKED_COMPONENT_PLAYSTORE = "com.android.vending";
    private static final java.lang.String BLOCKED_COMPONENT_SETTINGS = "com.android.settings";
    private static final java.lang.String EXTRA_BLOCKED_ACTIVITY_INFO = "com.android.internal.app.extra.BLOCKED_ACTIVITY_INFO";
    private static final java.lang.String EXTRA_STREAMED_DEVICE = "com.android.internal.app.extra.STREAMED_DEVICE";
    private static final java.lang.String PACKAGE_NAME = "com.android.internal.app";
    private static final java.lang.String TAG = "BlockedAppStreamingActivity";

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        java.lang.CharSequence charSequence;
        super.onCreate(bundle);
        android.content.Intent intent = getIntent();
        android.content.pm.ActivityInfo activityInfo = (android.content.pm.ActivityInfo) intent.getParcelableExtra(EXTRA_BLOCKED_ACTIVITY_INFO, android.content.pm.ActivityInfo.class);
        if (activityInfo == null) {
            charSequence = null;
        } else {
            charSequence = activityInfo.loadLabel(getPackageManager());
        }
        if (android.text.TextUtils.isEmpty(charSequence)) {
            android.util.Slog.wtf(TAG, "Invalid activity info: " + activityInfo);
            finish();
            return;
        }
        java.lang.CharSequence charSequenceExtra = intent.getCharSequenceExtra(EXTRA_STREAMED_DEVICE);
        if (!android.text.TextUtils.isEmpty(charSequenceExtra)) {
            if (android.text.TextUtils.equals(activityInfo.packageName, getPackageManager().getPermissionControllerPackageName())) {
                this.mAlertParams.mTitle = getString(com.android.internal.R.string.app_streaming_blocked_title_for_permission_dialog);
                this.mAlertParams.mMessage = getString(com.android.internal.R.string.app_streaming_blocked_message_for_permission_request);
            } else if (android.text.TextUtils.equals(activityInfo.packageName, BLOCKED_COMPONENT_PLAYSTORE)) {
                this.mAlertParams.mTitle = getString(com.android.internal.R.string.app_streaming_blocked_title_for_playstore_dialog);
                this.mAlertParams.mMessage = getString(com.android.internal.R.string.app_streaming_blocked_message, charSequenceExtra);
            } else if (android.text.TextUtils.equals(activityInfo.packageName, BLOCKED_COMPONENT_SETTINGS)) {
                this.mAlertParams.mTitle = getString(com.android.internal.R.string.app_streaming_blocked_title_for_settings_dialog);
                this.mAlertParams.mMessage = getString(com.android.internal.R.string.app_streaming_blocked_message_for_settings_dialog, charSequenceExtra);
            } else {
                this.mAlertParams.mMessage = getString(com.android.internal.R.string.app_streaming_blocked_message, charSequenceExtra);
            }
        } else {
            this.mAlertParams.mMessage = getString(com.android.internal.R.string.app_blocked_message, charSequence);
        }
        this.mAlertParams.mPositiveButtonText = getString(17039370);
        setupAlert();
    }

    public static android.content.Intent createIntent(android.content.pm.ActivityInfo activityInfo, java.lang.CharSequence charSequence) {
        return new android.content.Intent().setClassName("android", com.android.internal.app.BlockedAppStreamingActivity.class.getName()).putExtra(EXTRA_BLOCKED_ACTIVITY_INFO, activityInfo).putExtra(EXTRA_STREAMED_DEVICE, charSequence);
    }
}
