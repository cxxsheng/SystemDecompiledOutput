package com.android.server.wm;

/* loaded from: classes3.dex */
class KeyguardDisableHandler {
    private static final java.lang.String TAG = "WindowManager";
    private final com.android.server.utils.UserTokenWatcher mAppTokenWatcher;
    private com.android.server.wm.KeyguardDisableHandler.Injector mInjector;
    private final com.android.server.utils.UserTokenWatcher mSystemTokenWatcher;
    private int mCurrentUser = 0;
    private final com.android.server.utils.UserTokenWatcher.Callback mCallback = new com.android.server.utils.UserTokenWatcher.Callback() { // from class: com.android.server.wm.KeyguardDisableHandler.1
        @Override // com.android.server.utils.UserTokenWatcher.Callback
        public void acquired(int i) {
            com.android.server.wm.KeyguardDisableHandler.this.updateKeyguardEnabled(i);
        }

        @Override // com.android.server.utils.UserTokenWatcher.Callback
        public void released(int i) {
            com.android.server.wm.KeyguardDisableHandler.this.updateKeyguardEnabled(i);
        }
    };

    interface Injector {
        boolean dpmRequiresPassword(int i);

        void enableKeyguard(boolean z);

        int getProfileParentId(int i);

        boolean isKeyguardSecure(int i);
    }

    @com.android.internal.annotations.VisibleForTesting
    KeyguardDisableHandler(com.android.server.wm.KeyguardDisableHandler.Injector injector, android.os.Handler handler) {
        this.mInjector = injector;
        this.mAppTokenWatcher = new com.android.server.utils.UserTokenWatcher(this.mCallback, handler, TAG);
        this.mSystemTokenWatcher = new com.android.server.utils.UserTokenWatcher(this.mCallback, handler, TAG);
    }

    public void setCurrentUser(int i) {
        synchronized (this) {
            this.mCurrentUser = i;
            updateKeyguardEnabledLocked(-1);
        }
    }

    void updateKeyguardEnabled(int i) {
        synchronized (this) {
            updateKeyguardEnabledLocked(i);
        }
    }

    private void updateKeyguardEnabledLocked(int i) {
        if (this.mCurrentUser == i || i == -1) {
            this.mInjector.enableKeyguard(shouldKeyguardBeEnabled(this.mCurrentUser));
        }
    }

    void disableKeyguard(android.os.IBinder iBinder, java.lang.String str, int i, int i2) {
        watcherForCallingUid(iBinder, i).acquire(iBinder, str, this.mInjector.getProfileParentId(i2));
    }

    void reenableKeyguard(android.os.IBinder iBinder, int i, int i2) {
        watcherForCallingUid(iBinder, i).release(iBinder, this.mInjector.getProfileParentId(i2));
    }

    private com.android.server.utils.UserTokenWatcher watcherForCallingUid(android.os.IBinder iBinder, int i) {
        if (android.os.Process.isApplicationUid(i)) {
            return this.mAppTokenWatcher;
        }
        if (i == 1000 && (iBinder instanceof com.android.server.wm.LockTaskController.LockTaskToken)) {
            return this.mSystemTokenWatcher;
        }
        throw new java.lang.UnsupportedOperationException("Only apps can use the KeyguardLock API");
    }

    private boolean shouldKeyguardBeEnabled(int i) {
        boolean dpmRequiresPassword = this.mInjector.dpmRequiresPassword(this.mCurrentUser);
        boolean z = false;
        boolean z2 = (dpmRequiresPassword || this.mInjector.isKeyguardSecure(this.mCurrentUser)) ? false : true;
        boolean z3 = !dpmRequiresPassword;
        if ((z2 && this.mAppTokenWatcher.isAcquired(i)) || (z3 && this.mSystemTokenWatcher.isAcquired(i))) {
            z = true;
        }
        return !z;
    }

    static com.android.server.wm.KeyguardDisableHandler create(android.content.Context context, final com.android.server.policy.WindowManagerPolicy windowManagerPolicy, android.os.Handler handler) {
        final com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        return new com.android.server.wm.KeyguardDisableHandler(new com.android.server.wm.KeyguardDisableHandler.Injector() { // from class: com.android.server.wm.KeyguardDisableHandler.2
            @Override // com.android.server.wm.KeyguardDisableHandler.Injector
            public boolean dpmRequiresPassword(int i) {
                return android.app.admin.DevicePolicyCache.getInstance().getPasswordQuality(i) != 0;
            }

            @Override // com.android.server.wm.KeyguardDisableHandler.Injector
            public boolean isKeyguardSecure(int i) {
                return com.android.server.policy.WindowManagerPolicy.this.isKeyguardSecure(i);
            }

            @Override // com.android.server.wm.KeyguardDisableHandler.Injector
            public int getProfileParentId(int i) {
                return userManagerInternal.getProfileParentId(i);
            }

            @Override // com.android.server.wm.KeyguardDisableHandler.Injector
            public void enableKeyguard(boolean z) {
                com.android.server.policy.WindowManagerPolicy.this.enableKeyguard(z);
            }
        }, handler);
    }
}
