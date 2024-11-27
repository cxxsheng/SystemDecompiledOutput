package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemLocationPermissionsHelper extends com.android.server.location.injector.LocationPermissionsHelper {
    private final android.content.Context mContext;
    private boolean mInited;

    public SystemLocationPermissionsHelper(android.content.Context context, com.android.server.location.injector.AppOpsHelper appOpsHelper) {
        super(appOpsHelper);
        this.mContext = context;
    }

    public void onSystemReady() {
        if (this.mInited) {
            return;
        }
        this.mContext.getPackageManager().addOnPermissionsChangeListener(new android.content.pm.PackageManager.OnPermissionsChangedListener() { // from class: com.android.server.location.injector.SystemLocationPermissionsHelper$$ExternalSyntheticLambda0
            public final void onPermissionsChanged(int i) {
                com.android.server.location.injector.SystemLocationPermissionsHelper.this.lambda$onSystemReady$1(i);
            }
        });
        this.mInited = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0(int i) {
        notifyLocationPermissionsChanged(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$1(final int i) {
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.injector.SystemLocationPermissionsHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.injector.SystemLocationPermissionsHelper.this.lambda$onSystemReady$0(i);
            }
        });
    }

    @Override // com.android.server.location.injector.LocationPermissionsHelper
    protected boolean hasPermission(java.lang.String str, android.location.util.identity.CallerIdentity callerIdentity) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mContext.checkPermission(str, callerIdentity.getPid(), callerIdentity.getUid()) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
