package com.android.internal.app;

/* loaded from: classes4.dex */
public class UnlaunchableAppActivity extends android.app.Activity implements android.content.DialogInterface.OnDismissListener, android.content.DialogInterface.OnClickListener {
    private static final java.lang.String EXTRA_UNLAUNCHABLE_REASON = "unlaunchable_reason";
    private static final java.lang.String TAG = "UnlaunchableAppActivity";
    private static final int UNLAUNCHABLE_REASON_QUIET_MODE = 1;
    private int mReason;
    private android.content.IntentSender mTarget;
    private android.telecom.TelecomManager mTelecomManager;
    private int mUserId;

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        boolean z;
        android.app.AlertDialog.Builder builder;
        super.onCreate(bundle);
        requestWindowFeature(1);
        android.content.Intent intent = getIntent();
        this.mTelecomManager = (android.telecom.TelecomManager) getSystemService(android.telecom.TelecomManager.class);
        this.mReason = intent.getIntExtra(EXTRA_UNLAUNCHABLE_REASON, -1);
        this.mUserId = intent.getIntExtra(android.content.Intent.EXTRA_USER_HANDLE, -10000);
        this.mTarget = (android.content.IntentSender) intent.getParcelableExtra(android.content.Intent.EXTRA_INTENT, android.content.IntentSender.class);
        java.lang.String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
        android.os.UserManager userManager = android.os.UserManager.get(this);
        if (this.mUserId == -10000) {
            android.util.Log.wtf(TAG, "Invalid user id: " + this.mUserId + ". Stopping.");
            finish();
            return;
        }
        if (!android.os.Flags.allowPrivateProfile() || userManager.isManagedProfile(this.mUserId)) {
            if (this.mReason != 1) {
                android.util.Log.wtf(TAG, "Invalid unlaunchable type: " + this.mReason);
                finish();
                return;
            }
            if (stringExtra != null && stringExtra.equals(this.mTelecomManager.getDefaultDialerPackage(android.os.UserHandle.of(this.mUserId)))) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                builder = new android.app.AlertDialog.Builder(this, com.android.internal.R.style.AlertDialogWithEmergencyButton);
                builder.setNeutralButton(com.android.internal.R.string.work_mode_emergency_call_button, this);
            } else {
                builder = new android.app.AlertDialog.Builder(this);
            }
            builder.setTitle(getDialogTitle()).setOnDismissListener(this).setPositiveButton(com.android.internal.R.string.work_mode_turn_on, this).setNegativeButton(17039360, (android.content.DialogInterface.OnClickListener) null);
            android.app.AlertDialog create = builder.create();
            create.create();
            if (z) {
                create.getWindow().findViewById(com.android.internal.R.id.parentPanel).setPadding(0, 0, 0, 30);
                create.getWindow().findViewById(16908315).setOutlineProvider(null);
            }
            getWindow().setHideOverlayWindows(true);
            create.getButton(-1).setFilterTouchesWhenObscured(true);
            create.show();
            return;
        }
        android.util.Log.e(TAG, "Unlaunchable activity for target package " + stringExtra + " called for a non-managed-profile " + this.mUserId);
        finish();
    }

    private java.lang.String getDialogTitle() {
        return ((android.app.admin.DevicePolicyManager) getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.UNLAUNCHABLE_APP_WORK_PAUSED_TITLE, new java.util.function.Supplier() { // from class: com.android.internal.app.UnlaunchableAppActivity$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getDialogTitle$0;
                lambda$getDialogTitle$0 = com.android.internal.app.UnlaunchableAppActivity.this.lambda$getDialogTitle$0();
                return lambda$getDialogTitle$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getDialogTitle$0() {
        return getString(com.android.internal.R.string.work_mode_off_title);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        if (this.mReason != 1) {
            return;
        }
        if (i == -1) {
            final android.os.UserManager userManager = android.os.UserManager.get(this);
            new android.os.Handler(android.os.Looper.getMainLooper()).post(new java.lang.Runnable() { // from class: com.android.internal.app.UnlaunchableAppActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.app.UnlaunchableAppActivity.this.lambda$onClick$1(userManager);
                }
            });
        } else if (i == -3) {
            launchEmergencyDialer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(android.os.UserManager userManager) {
        userManager.requestQuietModeEnabled(false, android.os.UserHandle.of(this.mUserId), this.mTarget);
    }

    private void launchEmergencyDialer() {
        startActivity(this.mTelecomManager.createLaunchEmergencyDialerIntent(null).setFlags(343932928));
    }

    private static final android.content.Intent createBaseIntent() {
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(new android.content.ComponentName("android", com.android.internal.app.UnlaunchableAppActivity.class.getName()));
        intent.setFlags(276824064);
        return intent;
    }

    public static android.content.Intent createInQuietModeDialogIntent(int i) {
        android.content.Intent createBaseIntent = createBaseIntent();
        createBaseIntent.putExtra(EXTRA_UNLAUNCHABLE_REASON, 1);
        createBaseIntent.putExtra(android.content.Intent.EXTRA_USER_HANDLE, i);
        return createBaseIntent;
    }

    public static android.content.Intent createInQuietModeDialogIntent(int i, android.content.IntentSender intentSender, android.content.pm.ResolveInfo resolveInfo) {
        android.content.Intent createInQuietModeDialogIntent = createInQuietModeDialogIntent(i);
        createInQuietModeDialogIntent.putExtra(android.content.Intent.EXTRA_INTENT, intentSender);
        if (resolveInfo != null) {
            createInQuietModeDialogIntent.putExtra("android.intent.extra.PACKAGE_NAME", resolveInfo.getComponentInfo().packageName);
        }
        return createInQuietModeDialogIntent;
    }
}
