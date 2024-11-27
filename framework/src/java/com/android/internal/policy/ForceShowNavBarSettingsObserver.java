package com.android.internal.policy;

/* loaded from: classes5.dex */
public class ForceShowNavBarSettingsObserver extends android.database.ContentObserver {
    private android.content.Context mContext;
    private java.lang.Runnable mOnChangeRunnable;

    public ForceShowNavBarSettingsObserver(android.os.Handler handler, android.content.Context context) {
        super(handler);
        this.mContext = context;
    }

    public void setOnChangeRunnable(java.lang.Runnable runnable) {
        this.mOnChangeRunnable = runnable;
    }

    public void register() {
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor(android.provider.Settings.Secure.NAV_BAR_FORCE_VISIBLE), false, this, -1);
    }

    public void unregister() {
        this.mContext.getContentResolver().unregisterContentObserver(this);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, java.util.Collection<android.net.Uri> collection, int i, int i2) {
        if (i2 == android.app.ActivityManager.getCurrentUser() && this.mOnChangeRunnable != null) {
            this.mOnChangeRunnable.run();
        }
    }

    public boolean isEnabled() {
        return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.NAV_BAR_FORCE_VISIBLE, 0, -2) == 1;
    }
}
