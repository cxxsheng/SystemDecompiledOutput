package com.android.internal.app;

/* loaded from: classes4.dex */
public class DisableCarModeActivity extends android.app.Activity {
    private static final java.lang.String TAG = "DisableCarModeActivity";

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        try {
            android.app.IUiModeManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.UI_MODE_SERVICE)).disableCarModeByCallingPackage(3, getOpPackageName());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to disable car mode", e);
        }
        finish();
    }
}
