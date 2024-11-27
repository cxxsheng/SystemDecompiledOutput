package com.android.internal.app;

/* loaded from: classes4.dex */
public class ShutdownActivity extends android.app.Activity {
    private static final java.lang.String TAG = "ShutdownActivity";
    private boolean mConfirm;
    private boolean mReboot;
    private boolean mUserRequested;

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        final java.lang.String stringExtra;
        super.onCreate(bundle);
        android.content.Intent intent = getIntent();
        this.mReboot = android.content.Intent.ACTION_REBOOT.equals(intent.getAction());
        this.mConfirm = intent.getBooleanExtra(android.content.Intent.EXTRA_KEY_CONFIRM, false);
        this.mUserRequested = intent.getBooleanExtra(android.content.Intent.EXTRA_USER_REQUESTED_SHUTDOWN, false);
        if (this.mUserRequested) {
            stringExtra = android.os.PowerManager.SHUTDOWN_USER_REQUESTED;
        } else {
            stringExtra = intent.getStringExtra(android.content.Intent.EXTRA_REASON);
        }
        java.lang.String str = "onCreate(): confirm=" + this.mConfirm;
        java.lang.String str2 = TAG;
        android.util.Slog.i(TAG, str);
        java.lang.Thread thread = new java.lang.Thread(str2) { // from class: com.android.internal.app.ShutdownActivity.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                android.os.IPowerManager asInterface = android.os.IPowerManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.POWER_SERVICE));
                try {
                    if (com.android.internal.app.ShutdownActivity.this.mReboot) {
                        asInterface.reboot(com.android.internal.app.ShutdownActivity.this.mConfirm, null, false);
                    } else {
                        asInterface.shutdown(com.android.internal.app.ShutdownActivity.this.mConfirm, stringExtra, false);
                    }
                } catch (android.os.RemoteException e) {
                }
            }
        };
        thread.start();
        finish();
        try {
            thread.join();
        } catch (java.lang.InterruptedException e) {
        }
    }
}
