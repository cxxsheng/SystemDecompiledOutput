package com.android.internal.app;

/* loaded from: classes4.dex */
public class SuspendedAppActivity extends com.android.internal.app.AlertActivity implements android.content.DialogInterface.OnClickListener {
    public static final java.lang.String EXTRA_ACTIVITY_OPTIONS = "com.android.internal.app.extra.ACTIVITY_OPTIONS";
    public static final java.lang.String EXTRA_DIALOG_INFO = "com.android.internal.app.extra.DIALOG_INFO";
    public static final java.lang.String EXTRA_SUSPENDED_PACKAGE = "com.android.internal.app.extra.SUSPENDED_PACKAGE";
    public static final java.lang.String EXTRA_SUSPENDING_PACKAGE = "com.android.internal.app.extra.SUSPENDING_PACKAGE";
    public static final java.lang.String EXTRA_UNSUSPEND_INTENT = "com.android.internal.app.extra.UNSUSPEND_INTENT";
    private static final java.lang.String PACKAGE_NAME = "com.android.internal.app";
    private static final java.lang.String TAG = com.android.internal.app.SuspendedAppActivity.class.getSimpleName();
    private android.content.Intent mMoreDetailsIntent;
    private int mNeutralButtonAction;
    private android.content.IntentSender mOnUnsuspend;
    private android.os.Bundle mOptions;
    private android.content.pm.PackageManager mPm;
    private android.content.pm.SuspendDialogInfo mSuppliedDialogInfo;
    private android.content.BroadcastReceiver mSuspendModifiedReceiver = new android.content.BroadcastReceiver() { // from class: com.android.internal.app.SuspendedAppActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            if (android.content.Intent.ACTION_PACKAGES_SUSPENSION_CHANGED.equals(intent.getAction()) && com.android.internal.util.ArrayUtils.contains(intent.getStringArrayExtra(android.content.Intent.EXTRA_CHANGED_PACKAGE_LIST), com.android.internal.app.SuspendedAppActivity.this.mSuspendedPackage) && !com.android.internal.app.SuspendedAppActivity.this.isPackageSuspended(com.android.internal.app.SuspendedAppActivity.this.mSuspendedPackage) && !com.android.internal.app.SuspendedAppActivity.this.isFinishing()) {
                android.util.Slog.w(com.android.internal.app.SuspendedAppActivity.TAG, "Package " + com.android.internal.app.SuspendedAppActivity.this.mSuspendedPackage + " has modified suspension conditions while dialog was visible. Finishing.");
                com.android.internal.app.SuspendedAppActivity.this.finish();
            }
        }
    };
    private java.lang.String mSuspendedPackage;
    private android.content.res.Resources mSuspendingAppResources;
    private java.lang.String mSuspendingPackage;
    private int mUserId;
    private android.app.usage.UsageStatsManager mUsm;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPackageSuspended(java.lang.String str) {
        try {
            return this.mPm.isPackageSuspended(str);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Package " + str + " not found", e);
            return false;
        }
    }

    private java.lang.CharSequence getAppLabel(java.lang.String str) {
        try {
            return this.mPm.getApplicationInfoAsUser(str, 0, this.mUserId).loadLabel(this.mPm);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Package " + str + " not found", e);
            return str;
        }
    }

    private android.content.Intent getMoreDetailsActivity() {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SHOW_SUSPENDED_APP_DETAILS).setPackage(this.mSuspendingPackage);
        android.content.pm.ResolveInfo resolveActivityAsUser = this.mPm.resolveActivityAsUser(intent, 786432, this.mUserId);
        if (resolveActivityAsUser != null && resolveActivityAsUser.activityInfo != null && android.Manifest.permission.SEND_SHOW_SUSPENDED_APP_DETAILS.equals(resolveActivityAsUser.activityInfo.permission)) {
            intent.putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setFlags(335544320);
            return intent;
        }
        return null;
    }

    private android.graphics.drawable.Drawable resolveIcon() {
        int iconResId = this.mSuppliedDialogInfo != null ? this.mSuppliedDialogInfo.getIconResId() : 0;
        if (iconResId != 0 && this.mSuspendingAppResources != null) {
            try {
                return this.mSuspendingAppResources.getDrawable(iconResId, getTheme());
            } catch (android.content.res.Resources.NotFoundException e) {
                android.util.Slog.e(TAG, "Could not resolve drawable resource id " + iconResId);
                return null;
            }
        }
        return null;
    }

    private java.lang.String resolveTitle() {
        if (this.mSuppliedDialogInfo != null) {
            int titleResId = this.mSuppliedDialogInfo.getTitleResId();
            java.lang.String title = this.mSuppliedDialogInfo.getTitle();
            if (titleResId != 0 && this.mSuspendingAppResources != null) {
                try {
                    return this.mSuspendingAppResources.getString(titleResId);
                } catch (android.content.res.Resources.NotFoundException e) {
                    android.util.Slog.e(TAG, "Could not resolve string resource id " + titleResId);
                }
            } else if (title != null) {
                return title;
            }
        }
        return getString(com.android.internal.R.string.app_suspended_title);
    }

    private java.lang.String resolveDialogMessage() {
        java.lang.CharSequence appLabel = getAppLabel(this.mSuspendedPackage);
        if (this.mSuppliedDialogInfo != null) {
            int dialogMessageResId = this.mSuppliedDialogInfo.getDialogMessageResId();
            java.lang.String dialogMessage = this.mSuppliedDialogInfo.getDialogMessage();
            if (dialogMessageResId != 0 && this.mSuspendingAppResources != null) {
                try {
                    return this.mSuspendingAppResources.getString(dialogMessageResId, appLabel);
                } catch (android.content.res.Resources.NotFoundException e) {
                    android.util.Slog.e(TAG, "Could not resolve string resource id " + dialogMessageResId);
                }
            } else if (dialogMessage != null) {
                return java.lang.String.format(getResources().getConfiguration().getLocales().get(0), dialogMessage, appLabel);
            }
        }
        return getString(com.android.internal.R.string.app_suspended_default_message, appLabel, getAppLabel(this.mSuspendingPackage));
    }

    private java.lang.String resolveNeutralButtonText() {
        int i;
        switch (this.mNeutralButtonAction) {
            case 0:
                if (this.mMoreDetailsIntent != null) {
                    i = com.android.internal.R.string.app_suspended_more_details;
                    break;
                } else {
                    return null;
                }
            case 1:
                i = com.android.internal.R.string.app_suspended_unsuspend_message;
                break;
            default:
                android.util.Slog.w(TAG, "Unknown neutral button action: " + this.mNeutralButtonAction);
                return null;
        }
        if (this.mSuppliedDialogInfo != null) {
            int neutralButtonTextResId = this.mSuppliedDialogInfo.getNeutralButtonTextResId();
            java.lang.String neutralButtonText = this.mSuppliedDialogInfo.getNeutralButtonText();
            if (neutralButtonTextResId != 0 && this.mSuspendingAppResources != null) {
                try {
                    return this.mSuspendingAppResources.getString(neutralButtonTextResId);
                } catch (android.content.res.Resources.NotFoundException e) {
                    android.util.Slog.e(TAG, "Could not resolve string resource id " + neutralButtonTextResId);
                }
            } else if (neutralButtonText != null) {
                return neutralButtonText;
            }
        }
        return getString(i);
    }

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mPm = getPackageManager();
        this.mUsm = (android.app.usage.UsageStatsManager) getSystemService(android.app.usage.UsageStatsManager.class);
        getWindow().setType(2008);
        android.content.Intent intent = getIntent();
        this.mOptions = intent.getBundleExtra(EXTRA_ACTIVITY_OPTIONS);
        this.mUserId = intent.getIntExtra(android.content.Intent.EXTRA_USER_ID, -1);
        if (this.mUserId < 0) {
            android.util.Slog.wtf(TAG, "Invalid user: " + this.mUserId);
            finish();
            return;
        }
        this.mSuspendedPackage = intent.getStringExtra(EXTRA_SUSPENDED_PACKAGE);
        this.mSuspendingPackage = intent.getStringExtra(EXTRA_SUSPENDING_PACKAGE);
        this.mSuppliedDialogInfo = (android.content.pm.SuspendDialogInfo) intent.getParcelableExtra(EXTRA_DIALOG_INFO, android.content.pm.SuspendDialogInfo.class);
        this.mOnUnsuspend = (android.content.IntentSender) intent.getParcelableExtra(EXTRA_UNSUSPEND_INTENT, android.content.IntentSender.class);
        if (this.mSuppliedDialogInfo != null) {
            try {
                this.mSuspendingAppResources = createContextAsUser(android.os.UserHandle.of(this.mUserId), 0).getPackageManager().getResourcesForApplication(this.mSuspendingPackage);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, "Could not find resources for " + this.mSuspendingPackage, e);
            }
        }
        this.mNeutralButtonAction = this.mSuppliedDialogInfo != null ? this.mSuppliedDialogInfo.getNeutralButtonAction() : 0;
        this.mMoreDetailsIntent = this.mNeutralButtonAction == 0 ? getMoreDetailsActivity() : null;
        com.android.internal.app.AlertController.AlertParams alertParams = this.mAlertParams;
        alertParams.mIcon = resolveIcon();
        alertParams.mTitle = resolveTitle();
        alertParams.mMessage = resolveDialogMessage();
        alertParams.mPositiveButtonText = getString(17039370);
        alertParams.mNeutralButtonText = resolveNeutralButtonText();
        alertParams.mNeutralButtonListener = this;
        alertParams.mPositiveButtonListener = this;
        requestDismissKeyguardIfNeeded(alertParams.mMessage);
        setupAlert();
        registerReceiverAsUser(this.mSuspendModifiedReceiver, android.os.UserHandle.of(this.mUserId), new android.content.IntentFilter(android.content.Intent.ACTION_PACKAGES_SUSPENSION_CHANGED), null, null);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mSuspendModifiedReceiver);
    }

    private void requestDismissKeyguardIfNeeded(java.lang.CharSequence charSequence) {
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) getSystemService(android.app.KeyguardManager.class);
        if (keyguardManager.isKeyguardLocked()) {
            keyguardManager.requestDismissKeyguard(this, charSequence, new android.app.KeyguardManager.KeyguardDismissCallback() { // from class: com.android.internal.app.SuspendedAppActivity.2
                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissError() {
                    android.util.Slog.e(com.android.internal.app.SuspendedAppActivity.TAG, "Error while dismissing keyguard. Keeping the dialog visible.");
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissCancelled() {
                    android.util.Slog.w(com.android.internal.app.SuspendedAppActivity.TAG, "Keyguard dismiss was cancelled. Finishing.");
                    com.android.internal.app.SuspendedAppActivity.this.finish();
                }
            });
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        switch (i) {
            case -3:
                switch (this.mNeutralButtonAction) {
                    case 0:
                        if (this.mMoreDetailsIntent != null) {
                            startActivityAsUser(this.mMoreDetailsIntent, this.mOptions, android.os.UserHandle.of(this.mUserId));
                            break;
                        } else {
                            android.util.Slog.wtf(TAG, "Neutral button should not have existed!");
                            break;
                        }
                    case 1:
                        try {
                            if (com.android.internal.util.ArrayUtils.contains(android.app.AppGlobals.getPackageManager().setPackagesSuspendedAsUser(new java.lang.String[]{this.mSuspendedPackage}, false, null, null, null, 0, this.mSuspendingPackage, this.mUserId, this.mUserId), this.mSuspendedPackage)) {
                                android.util.Slog.e(TAG, "Could not unsuspend " + this.mSuspendedPackage);
                                break;
                            } else {
                                sendBroadcastAsUser(new android.content.Intent().setAction(android.content.Intent.ACTION_PACKAGE_UNSUSPENDED_MANUALLY).putExtra("android.intent.extra.PACKAGE_NAME", this.mSuspendedPackage).setPackage(this.mSuspendingPackage).addFlags(16777216), android.os.UserHandle.of(this.mUserId));
                                if (this.mOnUnsuspend != null) {
                                    try {
                                        this.mOnUnsuspend.sendIntent(this, 0, null, null, null, null, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                                        break;
                                    } catch (android.content.IntentSender.SendIntentException e) {
                                        android.util.Slog.e(TAG, "Error while starting intent " + this.mOnUnsuspend, e);
                                        break;
                                    }
                                }
                            }
                        } catch (android.os.RemoteException e2) {
                            android.util.Slog.e(TAG, "Can't talk to system process", e2);
                            break;
                        }
                        break;
                    default:
                        android.util.Slog.e(TAG, "Unexpected action on neutral button: " + this.mNeutralButtonAction);
                        break;
                }
        }
        this.mUsm.reportUserInteraction(this.mSuspendingPackage, this.mUserId);
        finish();
    }

    public static android.content.Intent createSuspendedAppInterceptIntent(java.lang.String str, android.content.pm.UserPackage userPackage, android.content.pm.SuspendDialogInfo suspendDialogInfo, android.os.Bundle bundle, android.content.IntentSender intentSender, int i) {
        return new android.content.Intent().setClassName("android", com.android.internal.app.SuspendedAppActivity.class.getName()).putExtra(EXTRA_SUSPENDED_PACKAGE, str).putExtra(EXTRA_DIALOG_INFO, suspendDialogInfo).putExtra(EXTRA_SUSPENDING_PACKAGE, userPackage != null ? userPackage.packageName : null).putExtra(EXTRA_UNSUSPEND_INTENT, intentSender).putExtra(EXTRA_ACTIVITY_OPTIONS, bundle).putExtra(android.content.Intent.EXTRA_USER_ID, i).setFlags(276824064);
    }
}
