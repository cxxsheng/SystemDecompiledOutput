package com.android.internal.app;

/* loaded from: classes4.dex */
public class SetScreenLockDialogActivity extends com.android.internal.app.AlertActivity implements android.content.DialogInterface.OnClickListener, android.content.DialogInterface.OnDismissListener {
    public static final java.lang.String EXTRA_LAUNCH_REASON = "launch_reason";
    public static final java.lang.String EXTRA_ORIGIN_USER_ID = "origin_user_id";
    public static final int LAUNCH_REASON_DISABLE_QUIET_MODE = 1;
    public static final int LAUNCH_REASON_PRIVATE_SPACE_SETTINGS_ACCESS = 2;
    public static final int LAUNCH_REASON_UNKNOWN = -1;
    private static final java.lang.String PACKAGE_NAME = "android";
    private static final java.lang.String TAG = "SetScreenLockDialog";
    private int mOriginUserId;
    private int mReason;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LaunchReason {
    }

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        if (!android.os.Flags.allowPrivateProfile() || !android.multiuser.Flags.showSetScreenLockDialog()) {
            finish();
            return;
        }
        android.content.Intent intent = getIntent();
        this.mReason = intent.getIntExtra(EXTRA_LAUNCH_REASON, -1);
        this.mOriginUserId = intent.getIntExtra(EXTRA_ORIGIN_USER_ID, -10000);
        if (this.mReason == -1) {
            android.util.Log.e(TAG, "Invalid launch reason: " + this.mReason);
            finish();
            return;
        }
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) getSystemService(android.app.KeyguardManager.class);
        if (keyguardManager == null) {
            android.util.Log.e(TAG, "Error fetching keyguard manager");
            return;
        }
        if (keyguardManager.isDeviceSecure()) {
            android.util.Log.w(TAG, "Closing the activity since screen lock is already set");
            return;
        }
        android.util.Log.d(TAG, "Launching screen lock setup dialog due to " + this.mReason);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(com.android.internal.R.string.set_up_screen_lock_title).setOnDismissListener(this).setPositiveButton(com.android.internal.R.string.set_up_screen_lock_action_label, this).setNegativeButton(17039360, this);
        setLaunchUserSpecificMessage(builder);
        android.app.AlertDialog create = builder.create();
        create.create();
        getWindow().setHideOverlayWindows(true);
        create.getButton(-1).setFilterTouchesWhenObscured(true);
        create.show();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        if (i == -1) {
            android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_BIOMETRIC_ENROLL);
            intent.putExtra(android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, 32768);
            startActivity(intent);
            return;
        }
        finish();
    }

    private void setLaunchUserSpecificMessage(android.app.AlertDialog.Builder builder) {
        android.content.pm.UserInfo userInfo;
        if (this.mReason == 2) {
            builder.setMessage(com.android.internal.R.string.private_space_set_up_screen_lock_message);
            return;
        }
        android.os.UserManager userManager = (android.os.UserManager) getApplicationContext().getSystemService(android.os.UserManager.class);
        if (userManager != null && (userInfo = userManager.getUserInfo(this.mOriginUserId)) != null && userInfo.isPrivateProfile()) {
            builder.setMessage(com.android.internal.R.string.private_space_set_up_screen_lock_message);
        }
    }

    public static android.content.Intent createBaseIntent(int i) {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(new android.content.ComponentName("android", com.android.internal.app.SetScreenLockDialogActivity.class.getName()));
        intent.setFlags(276824064);
        intent.putExtra(EXTRA_LAUNCH_REASON, i);
        return intent;
    }
}
