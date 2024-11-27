package com.android.server.autofill.ui;

/* loaded from: classes.dex */
class OverlayControl {

    @android.annotation.NonNull
    private final android.app.AppOpsManager mAppOpsManager;
    private final android.os.IBinder mToken = new android.os.Binder();

    OverlayControl(@android.annotation.NonNull android.content.Context context) {
        this.mAppOpsManager = (android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class);
    }

    void hideOverlays() {
        setOverlayAllowed(false);
    }

    void showOverlays() {
        setOverlayAllowed(true);
    }

    private void setOverlayAllowed(boolean z) {
        if (this.mAppOpsManager != null) {
            this.mAppOpsManager.setUserRestrictionForUser(24, !z, this.mToken, null, -1);
            this.mAppOpsManager.setUserRestrictionForUser(45, !z, this.mToken, null, -1);
        }
    }
}
