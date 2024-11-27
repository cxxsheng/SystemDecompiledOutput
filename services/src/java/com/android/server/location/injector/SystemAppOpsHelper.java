package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemAppOpsHelper extends com.android.server.location.injector.AppOpsHelper {
    private android.app.AppOpsManager mAppOps;
    private final android.content.Context mContext;

    public SystemAppOpsHelper(android.content.Context context) {
        this.mContext = context;
    }

    public void onSystemReady() {
        if (this.mAppOps != null) {
            return;
        }
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        java.util.Objects.requireNonNull(appOpsManager);
        this.mAppOps = appOpsManager;
        this.mAppOps.startWatchingMode(0, (java.lang.String) null, 1, new android.app.AppOpsManager.OnOpChangedListener() { // from class: com.android.server.location.injector.SystemAppOpsHelper$$ExternalSyntheticLambda1
            @Override // android.app.AppOpsManager.OnOpChangedListener
            public final void onOpChanged(java.lang.String str, java.lang.String str2) {
                com.android.server.location.injector.SystemAppOpsHelper.this.lambda$onSystemReady$1(str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$0(java.lang.String str) {
        notifyAppOpChanged(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSystemReady$1(java.lang.String str, final java.lang.String str2) {
        com.android.server.FgThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.location.injector.SystemAppOpsHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.location.injector.SystemAppOpsHelper.this.lambda$onSystemReady$0(str2);
            }
        });
    }

    @Override // com.android.server.location.injector.AppOpsHelper
    public boolean startOpNoThrow(int i, android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.internal.util.Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mAppOps.startOpNoThrow(i, callerIdentity.getUid(), callerIdentity.getPackageName(), false, callerIdentity.getAttributionTag(), callerIdentity.getListenerId()) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.AppOpsHelper
    public void finishOp(int i, android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.internal.util.Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mAppOps.finishOp(i, callerIdentity.getUid(), callerIdentity.getPackageName(), callerIdentity.getAttributionTag());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.AppOpsHelper
    public boolean checkOpNoThrow(int i, android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.internal.util.Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mAppOps.checkOpNoThrow(i, callerIdentity.getUid(), callerIdentity.getPackageName()) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.AppOpsHelper
    public boolean noteOp(int i, android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.internal.util.Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mAppOps.noteOp(i, callerIdentity.getUid(), callerIdentity.getPackageName(), callerIdentity.getAttributionTag(), callerIdentity.getListenerId()) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.location.injector.AppOpsHelper
    public boolean noteOpNoThrow(int i, android.location.util.identity.CallerIdentity callerIdentity) {
        com.android.internal.util.Preconditions.checkState(this.mAppOps != null);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mAppOps.noteOpNoThrow(i, callerIdentity.getUid(), callerIdentity.getPackageName(), callerIdentity.getAttributionTag(), callerIdentity.getListenerId()) == 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
