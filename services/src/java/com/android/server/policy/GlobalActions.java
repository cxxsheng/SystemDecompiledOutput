package com.android.server.policy;

/* loaded from: classes2.dex */
class GlobalActions implements com.android.server.policy.GlobalActionsProvider.GlobalActionsListener {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "GlobalActions";
    private final android.content.Context mContext;
    private boolean mDeviceProvisioned;
    private boolean mGlobalActionsAvailable;
    private boolean mKeyguardShowing;
    private com.android.server.policy.LegacyGlobalActions mLegacyGlobalActions;
    private boolean mShowing;
    private final com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs mWindowManagerFuncs;
    private final java.lang.Runnable mShowTimeout = new java.lang.Runnable() { // from class: com.android.server.policy.GlobalActions.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.policy.GlobalActions.this.ensureLegacyCreated();
            com.android.server.policy.GlobalActions.this.mLegacyGlobalActions.showDialog(com.android.server.policy.GlobalActions.this.mKeyguardShowing, com.android.server.policy.GlobalActions.this.mDeviceProvisioned);
        }
    };
    private final android.os.Handler mHandler = new android.os.Handler();
    private final com.android.server.policy.GlobalActionsProvider mGlobalActionsProvider = (com.android.server.policy.GlobalActionsProvider) com.android.server.LocalServices.getService(com.android.server.policy.GlobalActionsProvider.class);

    public GlobalActions(android.content.Context context, com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs windowManagerFuncs) {
        this.mContext = context;
        this.mWindowManagerFuncs = windowManagerFuncs;
        if (this.mGlobalActionsProvider != null) {
            this.mGlobalActionsProvider.setGlobalActionsListener(this);
        } else {
            android.util.Slog.i(TAG, "No GlobalActionsProvider found, defaulting to LegacyGlobalActions");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ensureLegacyCreated() {
        if (this.mLegacyGlobalActions != null) {
            return;
        }
        this.mLegacyGlobalActions = new com.android.server.policy.LegacyGlobalActions(this.mContext, this.mWindowManagerFuncs, new java.lang.Runnable() { // from class: com.android.server.policy.GlobalActions$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.policy.GlobalActions.this.onGlobalActionsDismissed();
            }
        });
    }

    public void showDialog(boolean z, boolean z2) {
        if (this.mGlobalActionsProvider != null && this.mGlobalActionsProvider.isGlobalActionsDisabled()) {
            return;
        }
        this.mKeyguardShowing = z;
        this.mDeviceProvisioned = z2;
        this.mShowing = true;
        if (this.mGlobalActionsAvailable) {
            this.mHandler.postDelayed(this.mShowTimeout, 5000L);
            this.mGlobalActionsProvider.showGlobalActions();
        } else {
            ensureLegacyCreated();
            this.mLegacyGlobalActions.showDialog(this.mKeyguardShowing, this.mDeviceProvisioned);
        }
    }

    @Override // com.android.server.policy.GlobalActionsProvider.GlobalActionsListener
    public void onGlobalActionsShown() {
        this.mHandler.removeCallbacks(this.mShowTimeout);
    }

    @Override // com.android.server.policy.GlobalActionsProvider.GlobalActionsListener
    public void onGlobalActionsDismissed() {
        this.mShowing = false;
    }

    @Override // com.android.server.policy.GlobalActionsProvider.GlobalActionsListener
    public void onGlobalActionsAvailableChanged(boolean z) {
        this.mGlobalActionsAvailable = z;
        if (this.mShowing && !this.mGlobalActionsAvailable) {
            this.mHandler.removeCallbacks(this.mShowTimeout);
            ensureLegacyCreated();
            this.mLegacyGlobalActions.showDialog(this.mKeyguardShowing, this.mDeviceProvisioned);
        }
    }
}
