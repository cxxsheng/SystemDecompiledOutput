package com.android.server.credentials;

/* loaded from: classes.dex */
public final class CredentialManagerServiceImpl extends com.android.server.infra.AbstractPerUserSystemService<com.android.server.credentials.CredentialManagerServiceImpl, com.android.server.credentials.CredentialManagerService> {
    private static final java.lang.String TAG = "CredManSysServiceImpl";

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.credentials.CredentialProviderInfo mInfo;

    CredentialManagerServiceImpl(@android.annotation.NonNull com.android.server.credentials.CredentialManagerService credentialManagerService, @android.annotation.NonNull java.lang.Object obj, int i, java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        super(credentialManagerService, obj, i);
        android.util.Slog.i(TAG, "CredentialManagerServiceImpl constructed for: " + str);
        synchronized (this.mLock) {
            newServiceInfoLocked(android.content.ComponentName.unflattenFromString(str));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.content.ComponentName getComponentName() {
        return this.mInfo.getServiceInfo().getComponentName();
    }

    CredentialManagerServiceImpl(@android.annotation.NonNull com.android.server.credentials.CredentialManagerService credentialManagerService, @android.annotation.NonNull java.lang.Object obj, int i, android.credentials.CredentialProviderInfo credentialProviderInfo) {
        super(credentialManagerService, obj, i);
        android.util.Slog.i(TAG, "CredentialManagerServiceImpl constructed for: " + credentialProviderInfo.getServiceInfo().getComponentName().flattenToString());
        this.mInfo = credentialProviderInfo;
    }

    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    protected android.content.pm.ServiceInfo newServiceInfoLocked(@android.annotation.NonNull android.content.ComponentName componentName) throws android.content.pm.PackageManager.NameNotFoundException {
        if (this.mInfo != null) {
            android.util.Slog.i(TAG, "newServiceInfoLocked, mInfo not null : " + this.mInfo.getServiceInfo().getComponentName().flattenToString() + " , " + componentName.flattenToString());
        } else {
            android.util.Slog.i(TAG, "newServiceInfoLocked, mInfo null, " + componentName.flattenToString());
        }
        this.mInfo = android.service.credentials.CredentialProviderInfoFactory.create(getContext(), componentName, this.mUserId, false);
        return this.mInfo.getServiceInfo();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    public com.android.server.credentials.ProviderSession initiateProviderSessionForRequestLocked(com.android.server.credentials.RequestSession requestSession, java.util.List<java.lang.String> list) {
        if (!list.isEmpty() && !isServiceCapableLocked(list)) {
            android.util.Slog.i(TAG, "Service does not have the required capabilities");
            return null;
        }
        if (this.mInfo == null) {
            android.util.Slog.w(TAG, "Initiating provider session for request but mInfo is null. This shouldn't happen");
            return null;
        }
        return requestSession.initiateProviderSession(this.mInfo, new com.android.server.credentials.RemoteCredentialService(getContext(), this.mInfo.getServiceInfo().getComponentName(), this.mUserId));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isServiceCapableLocked(java.util.List<java.lang.String> list) {
        if (this.mInfo == null) {
            return false;
        }
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            if (this.mInfo.hasCapability(it.next())) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.credentials.CredentialProviderInfo getCredentialProviderInfo() {
        return this.mInfo;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.infra.AbstractPerUserSystemService
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void handlePackageUpdateLocked(@android.annotation.NonNull java.lang.String str) {
        if (this.mInfo != null && this.mInfo.getServiceInfo() != null && this.mInfo.getServiceInfo().getComponentName().getPackageName().equals(str)) {
            try {
                newServiceInfoLocked(this.mInfo.getServiceInfo().getComponentName());
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Slog.e(TAG, "Issue while updating serviceInfo: " + e.getMessage());
            }
        }
    }
}
