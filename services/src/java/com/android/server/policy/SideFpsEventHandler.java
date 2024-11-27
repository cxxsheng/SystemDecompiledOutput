package com.android.server.policy;

/* loaded from: classes2.dex */
public class SideFpsEventHandler implements android.view.View.OnClickListener {
    private static final int DEBOUNCE_DELAY_MILLIS = 500;
    private static final java.lang.String TAG = "SideFpsEventHandler";
    private int mBiometricState;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private com.android.server.policy.SideFpsToast mDialog;
    private com.android.server.policy.SideFpsEventHandler.DialogProvider mDialogProvider;
    private final int mDismissDialogTimeout;
    private android.hardware.fingerprint.FingerprintManager mFingerprintManager;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private long mLastPowerPressTime;

    @android.annotation.NonNull
    private final android.os.PowerManager mPowerManager;

    @android.annotation.NonNull
    private final java.util.concurrent.atomic.AtomicBoolean mSideFpsEventHandlerReady;
    private final java.lang.Runnable mTurnOffDialog;

    interface DialogProvider {
        com.android.server.policy.SideFpsToast provideDialog(android.content.Context context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        dismissDialog("mTurnOffDialog");
    }

    SideFpsEventHandler(android.content.Context context, android.os.Handler handler, android.os.PowerManager powerManager) {
        this(context, handler, powerManager, new com.android.server.policy.SideFpsEventHandler.DialogProvider() { // from class: com.android.server.policy.SideFpsEventHandler$$ExternalSyntheticLambda1
            @Override // com.android.server.policy.SideFpsEventHandler.DialogProvider
            public final com.android.server.policy.SideFpsToast provideDialog(android.content.Context context2) {
                com.android.server.policy.SideFpsToast lambda$new$1;
                lambda$new$1 = com.android.server.policy.SideFpsEventHandler.lambda$new$1(context2);
                return lambda$new$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.policy.SideFpsToast lambda$new$1(android.content.Context context) {
        com.android.server.policy.SideFpsToast sideFpsToast = new com.android.server.policy.SideFpsToast(context);
        sideFpsToast.getWindow().setType(2017);
        sideFpsToast.requestWindowFeature(1);
        return sideFpsToast;
    }

    @com.android.internal.annotations.VisibleForTesting
    SideFpsEventHandler(android.content.Context context, android.os.Handler handler, android.os.PowerManager powerManager, com.android.server.policy.SideFpsEventHandler.DialogProvider dialogProvider) {
        this.mTurnOffDialog = new java.lang.Runnable() { // from class: com.android.server.policy.SideFpsEventHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.policy.SideFpsEventHandler.this.lambda$new$0();
            }
        };
        this.mContext = context;
        this.mHandler = handler;
        this.mPowerManager = powerManager;
        this.mBiometricState = 0;
        this.mSideFpsEventHandlerReady = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mDialogProvider = dialogProvider;
        context.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.policy.SideFpsEventHandler.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                if (com.android.server.policy.SideFpsEventHandler.this.mDialog != null) {
                    com.android.server.policy.SideFpsEventHandler.this.mDialog.dismiss();
                    com.android.server.policy.SideFpsEventHandler.this.mDialog = null;
                }
            }
        }, new android.content.IntentFilter("android.intent.action.SCREEN_OFF"));
        this.mDismissDialogTimeout = context.getResources().getInteger(android.R.integer.config_screenshotChordKeyTimeout);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        goToSleep(this.mLastPowerPressTime);
    }

    public void notifyPowerPressed() {
        android.util.Log.i(TAG, "notifyPowerPressed");
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return;
        }
        if (this.mFingerprintManager == null && this.mSideFpsEventHandlerReady.get()) {
            this.mFingerprintManager = (android.hardware.fingerprint.FingerprintManager) this.mContext.getSystemService(android.hardware.fingerprint.FingerprintManager.class);
        }
        if (this.mFingerprintManager == null) {
            return;
        }
        this.mFingerprintManager.onPowerPressed();
    }

    public boolean shouldConsumeSinglePress(final long j) {
        if (!this.mSideFpsEventHandlerReady.get()) {
            return false;
        }
        switch (this.mBiometricState) {
            case 1:
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.policy.SideFpsEventHandler$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.policy.SideFpsEventHandler.this.lambda$shouldConsumeSinglePress$2(j);
                    }
                });
                break;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$shouldConsumeSinglePress$2(long j) {
        if (this.mHandler.hasCallbacks(this.mTurnOffDialog)) {
            android.util.Log.v(TAG, "Detected a tap to turn off dialog, ignoring");
            this.mHandler.removeCallbacks(this.mTurnOffDialog);
        }
        showDialog(j, "Enroll Power Press");
        this.mHandler.postDelayed(this.mTurnOffDialog, this.mDismissDialogTimeout);
    }

    private void goToSleep(long j) {
        this.mPowerManager.goToSleep(j, 4, 0);
    }

    public void onFingerprintSensorReady() {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return;
        }
        final android.hardware.fingerprint.FingerprintManager fingerprintManager = (android.hardware.fingerprint.FingerprintManager) this.mContext.getSystemService(android.hardware.fingerprint.FingerprintManager.class);
        fingerprintManager.addAuthenticatorsRegisteredCallback(new android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.server.policy.SideFpsEventHandler.2
            public void onAllAuthenticatorsRegistered(java.util.List<android.hardware.fingerprint.FingerprintSensorPropertiesInternal> list) {
                if (fingerprintManager.isPowerbuttonFps()) {
                    fingerprintManager.registerBiometricStateListener(new com.android.server.policy.SideFpsEventHandler.AnonymousClass2.AnonymousClass1());
                    com.android.server.policy.SideFpsEventHandler.this.mSideFpsEventHandlerReady.set(true);
                }
            }

            /* renamed from: com.android.server.policy.SideFpsEventHandler$2$1, reason: invalid class name */
            class AnonymousClass1 extends android.hardware.biometrics.BiometricStateListener {

                @android.annotation.Nullable
                private java.lang.Runnable mStateRunnable = null;

                AnonymousClass1() {
                }

                public void onStateChanged(final int i) {
                    android.util.Log.d(com.android.server.policy.SideFpsEventHandler.TAG, "onStateChanged : " + i);
                    if (this.mStateRunnable != null) {
                        com.android.server.policy.SideFpsEventHandler.this.mHandler.removeCallbacks(this.mStateRunnable);
                        this.mStateRunnable = null;
                    }
                    if (i == 0) {
                        this.mStateRunnable = new java.lang.Runnable() { // from class: com.android.server.policy.SideFpsEventHandler$2$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.policy.SideFpsEventHandler.AnonymousClass2.AnonymousClass1.this.lambda$onStateChanged$0(i);
                            }
                        };
                        com.android.server.policy.SideFpsEventHandler.this.mHandler.postDelayed(this.mStateRunnable, 500L);
                        com.android.server.policy.SideFpsEventHandler.this.dismissDialog("STATE_IDLE");
                        return;
                    }
                    com.android.server.policy.SideFpsEventHandler.this.mBiometricState = i;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onStateChanged$0(int i) {
                    com.android.server.policy.SideFpsEventHandler.this.mBiometricState = i;
                }

                public void onBiometricAction(int i) {
                    android.util.Log.d(com.android.server.policy.SideFpsEventHandler.TAG, "onBiometricAction " + i);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog(java.lang.String str) {
        android.util.Log.d(TAG, "Dismissing dialog with reason: " + str);
        if (this.mDialog != null && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    private void showDialog(long j, java.lang.String str) {
        android.util.Log.d(TAG, "Showing dialog with reason: " + str);
        if (this.mDialog != null && this.mDialog.isShowing()) {
            android.util.Log.d(TAG, "Ignoring show dialog");
            return;
        }
        this.mDialog = this.mDialogProvider.provideDialog(this.mContext);
        this.mLastPowerPressTime = j;
        this.mDialog.show();
        this.mDialog.setOnClickListener(this);
    }
}
