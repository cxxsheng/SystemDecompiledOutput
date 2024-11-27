package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemAppForegroundHelper extends com.android.server.location.injector.AppForegroundHelper {
    private android.app.ActivityManager mActivityManager;
    private final android.content.Context mContext;

    public SystemAppForegroundHelper(android.content.Context context) {
        this.mContext = context;
    }

    public void onSystemReady() {
        if (this.mActivityManager != null) {
            return;
        }
        android.app.ActivityManager activityManager = (android.app.ActivityManager) this.mContext.getSystemService(android.app.ActivityManager.class);
        java.util.Objects.requireNonNull(activityManager);
        this.mActivityManager = activityManager;
        this.mActivityManager.addOnUidImportanceListener(new android.app.ActivityManager.OnUidImportanceListener() { // from class: com.android.server.location.injector.SystemAppForegroundHelper$$ExternalSyntheticLambda0
            public final void onUidImportance(int i, int i2) {
                com.android.server.location.injector.SystemAppForegroundHelper.this.onAppForegroundChanged(i, i2);
            }
        }, 125);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppForegroundChanged(final int i, int i2) {
        final boolean isForeground = com.android.server.location.injector.AppForegroundHelper.isForeground(i2);
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.injector.SystemAppForegroundHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.injector.SystemAppForegroundHelper.this.lambda$onAppForegroundChanged$0(i, isForeground);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAppForegroundChanged$0(int i, boolean z) {
        notifyAppForeground(i, z);
    }

    @Override // com.android.server.location.injector.AppForegroundHelper
    public boolean isAppForeground(int i) {
        com.android.internal.util.Preconditions.checkState(this.mActivityManager != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return com.android.server.location.injector.AppForegroundHelper.isForeground(this.mActivityManager.getUidImportance(i));
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
