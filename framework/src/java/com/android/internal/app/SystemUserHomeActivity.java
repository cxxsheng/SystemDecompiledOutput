package com.android.internal.app;

/* loaded from: classes4.dex */
public class SystemUserHomeActivity extends android.app.Activity {
    private static final java.lang.String TAG = "SystemUserHome";

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.util.Log.i(TAG, "onCreate");
        setContentView(com.android.internal.R.layout.system_user_home);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.i(TAG, "onDestroy");
    }
}
