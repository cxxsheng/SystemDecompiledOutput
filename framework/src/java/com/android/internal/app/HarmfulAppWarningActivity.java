package com.android.internal.app;

/* loaded from: classes4.dex */
public class HarmfulAppWarningActivity extends com.android.internal.app.AlertActivity implements android.content.DialogInterface.OnClickListener {
    private static final java.lang.String EXTRA_HARMFUL_APP_WARNING = "harmful_app_warning";
    private static final java.lang.String TAG = com.android.internal.app.HarmfulAppWarningActivity.class.getSimpleName();
    private java.lang.String mHarmfulAppWarning;
    private java.lang.String mPackageName;
    private android.content.IntentSender mTarget;

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        android.content.Intent intent = getIntent();
        this.mPackageName = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
        this.mTarget = (android.content.IntentSender) intent.getParcelableExtra(android.content.Intent.EXTRA_INTENT, android.content.IntentSender.class);
        this.mHarmfulAppWarning = intent.getStringExtra(EXTRA_HARMFUL_APP_WARNING);
        if (this.mPackageName == null || this.mTarget == null || this.mHarmfulAppWarning == null) {
            android.util.Log.wtf(TAG, "Invalid intent: " + intent.toString());
            finish();
        }
        try {
            android.content.pm.ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(this.mPackageName, 0);
            com.android.internal.app.AlertController.AlertParams alertParams = this.mAlertParams;
            alertParams.mTitle = getString(com.android.internal.R.string.harmful_app_warning_title);
            alertParams.mView = createView(applicationInfo);
            alertParams.mPositiveButtonText = getString(com.android.internal.R.string.harmful_app_warning_uninstall);
            alertParams.mPositiveButtonListener = this;
            alertParams.mNegativeButtonText = getString(com.android.internal.R.string.harmful_app_warning_open_anyway);
            alertParams.mNegativeButtonListener = this;
            this.mAlert.installContent(this.mAlertParams);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Could not show warning because package does not exist ", e);
            finish();
        }
    }

    private android.view.View createView(android.content.pm.ApplicationInfo applicationInfo) {
        android.view.View inflate = getLayoutInflater().inflate(com.android.internal.R.layout.harmful_app_warning_dialog, (android.view.ViewGroup) null);
        ((android.widget.TextView) inflate.findViewById(com.android.internal.R.id.app_name_text)).lambda$setTextAsync$0(applicationInfo.loadSafeLabel(getPackageManager(), 1000.0f, 5));
        ((android.widget.TextView) inflate.findViewById(16908299)).lambda$setTextAsync$0(this.mHarmfulAppWarning);
        return inflate;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        switch (i) {
            case -2:
                getPackageManager().setHarmfulAppWarning(this.mPackageName, null);
                try {
                    startIntentSenderForResult((android.content.IntentSender) getIntent().getParcelableExtra(android.content.Intent.EXTRA_INTENT, android.content.IntentSender.class), -1, (android.content.Intent) null, 0, 0, 0, android.app.ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle());
                } catch (android.content.IntentSender.SendIntentException e) {
                    android.util.Log.e(TAG, "Error while starting intent sender", e);
                }
                com.android.internal.app.EventLogTags.writeHarmfulAppWarningLaunchAnyway(this.mPackageName);
                finish();
                break;
            case -1:
                getPackageManager().deletePackage(this.mPackageName, null, 0);
                com.android.internal.app.EventLogTags.writeHarmfulAppWarningUninstall(this.mPackageName);
                finish();
                break;
        }
    }

    public static android.content.Intent createHarmfulAppWarningIntent(android.content.Context context, java.lang.String str, android.content.IntentSender intentSender, java.lang.CharSequence charSequence) {
        android.content.Intent intent = new android.content.Intent();
        intent.setClass(context, com.android.internal.app.HarmfulAppWarningActivity.class);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra(android.content.Intent.EXTRA_INTENT, intentSender);
        intent.putExtra(EXTRA_HARMFUL_APP_WARNING, charSequence);
        return intent;
    }
}
