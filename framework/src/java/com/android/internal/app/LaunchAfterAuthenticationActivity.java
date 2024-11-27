package com.android.internal.app;

/* loaded from: classes4.dex */
public class LaunchAfterAuthenticationActivity extends android.app.Activity {
    private static final java.lang.String EXTRA_ON_SUCCESS_INTENT = "com.android.internal.app.extra.ON_SUCCESS_INTENT";
    private static final java.lang.String TAG = com.android.internal.app.LaunchAfterAuthenticationActivity.class.getSimpleName();

    public static android.content.Intent createLaunchAfterAuthenticationIntent(android.content.IntentSender intentSender) {
        return new android.content.Intent().setClassName("android", com.android.internal.app.LaunchAfterAuthenticationActivity.class.getName()).putExtra(EXTRA_ON_SUCCESS_INTENT, intentSender).setFlags(276824064);
    }

    @Override // android.app.Activity
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        requestDismissKeyguardIfNeeded((android.content.IntentSender) getIntent().getParcelableExtra(EXTRA_ON_SUCCESS_INTENT, android.content.IntentSender.class));
    }

    private void requestDismissKeyguardIfNeeded(final android.content.IntentSender intentSender) {
        android.app.KeyguardManager keyguardManager = (android.app.KeyguardManager) java.util.Objects.requireNonNull((android.app.KeyguardManager) getSystemService(android.app.KeyguardManager.class));
        if (keyguardManager.isKeyguardLocked()) {
            keyguardManager.requestDismissKeyguard(this, new android.app.KeyguardManager.KeyguardDismissCallback() { // from class: com.android.internal.app.LaunchAfterAuthenticationActivity.1
                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissCancelled() {
                    com.android.internal.app.LaunchAfterAuthenticationActivity.this.finish();
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissSucceeded() {
                    if (intentSender != null) {
                        com.android.internal.app.LaunchAfterAuthenticationActivity.this.onUnlocked(intentSender);
                    }
                    com.android.internal.app.LaunchAfterAuthenticationActivity.this.finish();
                }

                @Override // android.app.KeyguardManager.KeyguardDismissCallback
                public void onDismissError() {
                    android.util.Slog.e(com.android.internal.app.LaunchAfterAuthenticationActivity.TAG, "Error while dismissing keyguard.");
                    com.android.internal.app.LaunchAfterAuthenticationActivity.this.finish();
                }
            });
        } else {
            finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUnlocked(android.content.IntentSender intentSender) {
        try {
            intentSender.sendIntent(this, 0, null, null, null);
        } catch (android.content.IntentSender.SendIntentException e) {
            android.util.Slog.e(TAG, "Error while sending original intent", e);
        }
    }
}
